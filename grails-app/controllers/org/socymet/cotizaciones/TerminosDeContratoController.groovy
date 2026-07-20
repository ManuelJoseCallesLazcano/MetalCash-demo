package org.socymet.cotizaciones
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.recepcion.RecepcionDePlomoPlata
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN'])
@Transactional
class TerminosDeContratoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    /**
     * Valor por Tonelada (VPT, $us/TM) por TÉRMINOS DE CONTRATO. Basado en getValorToneladaPlomoPlata.
     * Recibe el lote (RecepcionDeComplejo), las leyes finales (zinc, plomo, plata) y el término.
     * Usa la cotización diaria DEL LOTE; imprime los resultados intermedios en consola para verificación.
     * Lo consume el form de LiquidacionDeComplejo (modo VPT = TERMINOS DE CONTRATO).
     */
    @Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
    def calcularVPT() {
        def recepcion = RecepcionDeComplejo.get(params.recepcionDeComplejoId)
        def terminos = TerminosDeContrato.get(params.terminoId)
        def leyZinc  = params.leyZinc?.toString()?.isBigDecimal()  ? params.leyZinc.toBigDecimal()  : 0.0G
        def leyPlomo = params.leyPlomo?.toString()?.isBigDecimal() ? params.leyPlomo.toBigDecimal() : 0.0G
        def leyPlata = params.leyPlata?.toString()?.isBigDecimal() ? params.leyPlata.toBigDecimal() : 0.0G

        BigDecimal vpt = 0.0G
        if (recepcion?.cotizacionDiariaDeMinerales && terminos) {
            vpt = valorToneladaContrato(recepcion, terminos, leyZinc, leyPlomo, leyPlata, 0.0G)
        } else {
            System.out.println("calcularVPT (CONTRATO): falta recepción/cotización diaria o término → VPT 0")
        }
        vpt = (vpt ?: 0.0G).setScale(2, java.math.RoundingMode.HALF_UP)
        System.out.println("calcularVPT (CONTRATO) → VPT redondeado: ${vpt}")

        render([
            vpt    : vpt,
            modo   : 'CONTRATO',
            recepcionDeComplejoId: recepcion?.id,
            terminoId: terminos?.id,
            leyZinc: leyZinc, leyPlomo: leyPlomo, leyPlata: leyPlata
        ] as JSON)
    }

    /**
     * Valor por tonelada según términos de contrato (mismo modelo que getValorToneladaPlomoPlata).
     * Todo en BigDecimal con guardas contra nulos para asegurar precisión. Imprime intermedios.
     */
    private BigDecimal valorToneladaContrato(recepcion, terminos, BigDecimal porcentajeZinc, BigDecimal porcentajePlomo, BigDecimal porcentajePlata, BigDecimal porcentajeCobre) {
        def cot = recepcion.cotizacionDiariaDeMinerales

        BigDecimal cotizacionZinc  = (cot.zinc  ?: 0.0G) * 2204.6223
        BigDecimal cotizacionPlomo = (cot.plomo ?: 0.0G) * 2204.6223
        BigDecimal cotizacionPlata = (cot.plata ?: 0.0G)
        BigDecimal cotizacionCobre = (cot.cobre ?: 0.0G) * 2204.6223

        BigDecimal leyPagableZinc  = ((porcentajeZinc  ?: 0.0G) - (terminos.deduccionUnitariaZinc  ?: 0.0G)).max(0.0G)
        BigDecimal leyPagablePlomo = ((porcentajePlomo ?: 0.0G) - (terminos.deduccionUnitariaPlomo ?: 0.0G)).max(0.0G)
        BigDecimal leyPagablePlata = (100 * (porcentajePlata ?: 0.0G) / 31.1035 - (terminos.deduccionUnitariaPlata ?: 0.0G)).max(0.0G)
        BigDecimal leyPagableCobre = ((porcentajeCobre ?: 0.0G) - (terminos.deduccionUnitariaCobre ?: 0.0G)).max(0.0G)

        BigDecimal leyFinalPagableZinc  = leyPagableZinc  * (terminos.porcentajePagableLMEZinc  ?: 0.0G) / 100
        BigDecimal leyFinalPagablePlomo = leyPagablePlomo * (terminos.porcentajePagableLMEPlomo ?: 0.0G) / 100
        BigDecimal leyFinalPagablePlata = leyPagablePlata * (terminos.porcentajePagableLMEPlata ?: 0.0G) / 100
        BigDecimal leyFinalPagableCobre = leyPagableCobre * (terminos.porcentajePagableLMECobre ?: 0.0G) / 100

        BigDecimal valorBrutoZinc  = leyFinalPagableZinc  * cotizacionZinc  / 100
        BigDecimal valorBrutoPlomo = leyFinalPagablePlomo * cotizacionPlomo / 100
        BigDecimal valorBrutoPlata = leyFinalPagablePlata * cotizacionPlata
        BigDecimal valorBrutoCobre = leyFinalPagableCobre * cotizacionCobre / 100
        BigDecimal valorBruto = valorBrutoZinc + valorBrutoPlomo + valorBrutoPlata + valorBrutoCobre

        BigDecimal cotizacionBasadaZinc  = (cotizacionZinc  > (terminos.baseZincPlata  ?: 0.0G)) ? cotizacionZinc  - (terminos.baseZincPlata  ?: 0.0G) : 0.0G
        BigDecimal cotizacionBasadaPlomo = (cotizacionPlomo > (terminos.basePlomoPlata ?: 0.0G)) ? cotizacionPlomo - (terminos.basePlomoPlata ?: 0.0G) : 0.0G
        BigDecimal cotizacionBasadaCobre = (cotizacionCobre > (terminos.baseCobre ?: 0.0G)) ? cotizacionCobre - (terminos.baseCobre ?: 0.0G) : 0.0G

        BigDecimal cotizacionEscaladaZinc  = cotizacionBasadaZinc  * (terminos.escaladorZincPlata  ?: 0.0G)
        BigDecimal cotizacionEscaladaPlomo = cotizacionBasadaPlomo * (terminos.escaladorPlomoPlata ?: 0.0G)
        BigDecimal cotizacionEscaladaCobre = cotizacionBasadaCobre * (terminos.escaladorCobre ?: 0.0G)

        BigDecimal deduccionMaquilaFinalZinc  = cotizacionEscaladaZinc  + (terminos.maquilaZincPlata  ?: 0.0G)
        BigDecimal deduccionMaquilaFinalPlomo = cotizacionEscaladaPlomo + (terminos.maquilaPlomoPlata ?: 0.0G)
        BigDecimal deduccionMaquilaFinalCobre = cotizacionEscaladaCobre + (terminos.maquilaCobre ?: 0.0G)
        BigDecimal deduccionMaquilaFinal = deduccionMaquilaFinalZinc + deduccionMaquilaFinalPlomo + deduccionMaquilaFinalCobre

        BigDecimal deduccionRefinacionOnzaPlataZincPlata  = (100 * (porcentajePlata ?: 0.0G) / 31.1035) * (terminos.deduccionRefinacionOnzaZincPlata  ?: 0.0G)
        BigDecimal deduccionRefinacionOnzaPlataPlomoPlata = (100 * (porcentajePlata ?: 0.0G) / 31.1035) * (terminos.deduccionRefinacionOnzaPlomoPlata ?: 0.0G)
        BigDecimal deduccionRefinacionOnzaPlataCobrePlata = (100 * (porcentajePlata ?: 0.0G) / 31.1035) * (terminos.deduccionRefinacionOnzaCobrePlata ?: 0.0G)
        BigDecimal deduccionRefinacionOnzaPlataFinal = deduccionRefinacionOnzaPlataZincPlata + deduccionRefinacionOnzaPlataPlomoPlata + deduccionRefinacionOnzaPlataCobrePlata

        BigDecimal deduccionRefinacionLibraZinc  = leyPagableZinc  * 2204.6223 * (terminos.deduccionRefinacionLibraZinc  ?: 0.0G) / 100
        BigDecimal deduccionRefinacionLibraPlomo = leyPagablePlomo * 2204.6223 * (terminos.deduccionRefinacionLibraPlomo ?: 0.0G) / 100
        BigDecimal deduccionRefinacionLibraCobre = leyPagableCobre * 2204.6223 * (terminos.deduccionRefinacionLibraCobre ?: 0.0G) / 100
        BigDecimal deduccionRefinacionLibraFinal = deduccionRefinacionLibraZinc + deduccionRefinacionLibraPlomo + deduccionRefinacionLibraCobre

        BigDecimal penalidadCastigableArsenico  = ((terminos.porcentajeArsenico  ?: 0.0G) - (terminos.arsenicoLibre  ?: 0.0G)).max(0.0G)
        BigDecimal penalidadCastigableAntimonio = ((terminos.porcentajeAntimonio ?: 0.0G) - (terminos.antimonioLibre ?: 0.0G)).max(0.0G)
        BigDecimal penalidadCastigableBismuto   = ((terminos.porcentajeBismuto   ?: 0.0G) - (terminos.bismutoLibre   ?: 0.0G)).max(0.0G)
        BigDecimal penalidadCastigableEstano    = ((terminos.porcentajeEstano    ?: 0.0G) - (terminos.estanoLibre    ?: 0.0G)).max(0.0G)
        BigDecimal penalidadCastigableHierro    = ((terminos.porcentajeHierro    ?: 0.0G) - (terminos.hierroLibre    ?: 0.0G)).max(0.0G)
        BigDecimal penalidadCastigableSilice    = ((terminos.porcentajeSilice    ?: 0.0G) - (terminos.siliceLibre    ?: 0.0G)).max(0.0G)
        BigDecimal penalidadCastigableZinc      = ((terminos.porcentajeZinc      ?: 0.0G) - (terminos.zincLibre      ?: 0.0G)).max(0.0G)

        BigDecimal penalidadCastigableArsenicoFinal  = penalidadCastigableArsenico  * (terminos.costoUnitarioArsenico  ?: 0.0G) / (((terminos.porcentajeUnitarioArsenico  ?: 0.0G) <= 0) ? 1.0G : terminos.porcentajeUnitarioArsenico)
        BigDecimal penalidadCastigableAntimonioFinal = penalidadCastigableAntimonio * (terminos.costoUnitarioAntimonio ?: 0.0G) / (((terminos.porcentajeUnitarioAntimonio ?: 0.0G) <= 0) ? 1.0G : terminos.porcentajeUnitarioAntimonio)
        BigDecimal penalidadCastigableBismutoFinal   = penalidadCastigableBismuto   * (terminos.costoUnitarioBismuto   ?: 0.0G) / (((terminos.porcentajeUnitarioBismuto   ?: 0.0G) <= 0) ? 1.0G : terminos.porcentajeUnitarioBismuto)
        BigDecimal penalidadCastigableEstanoFinal    = penalidadCastigableEstano    * (terminos.costoUnitarioEstano    ?: 0.0G) / (((terminos.porcentajeUnitarioEstano    ?: 0.0G) <= 0) ? 1.0G : terminos.porcentajeUnitarioEstano)
        BigDecimal penalidadCastigableHierroFinal    = penalidadCastigableHierro    * (terminos.costoUnitarioHierro    ?: 0.0G) / (((terminos.porcentajeUnitarioHierro    ?: 0.0G) <= 0) ? 1.0G : terminos.porcentajeUnitarioHierro)
        BigDecimal penalidadCastigableSiliceFinal    = penalidadCastigableSilice    * (terminos.costoUnitarioSilice    ?: 0.0G) / (((terminos.porcentajeUnitarioSilice    ?: 0.0G) <= 0) ? 1.0G : terminos.porcentajeUnitarioSilice)
        BigDecimal penalidadCastigableZincFinal      = penalidadCastigableZinc      * (terminos.costoUnitarioZinc      ?: 0.0G) / (((terminos.porcentajeUnitarioZinc      ?: 0.0G) <= 0) ? 1.0G : terminos.porcentajeUnitarioZinc)
        BigDecimal penalidadCastigableFinal = penalidadCastigableArsenicoFinal + penalidadCastigableAntimonioFinal + penalidadCastigableBismutoFinal + penalidadCastigableEstanoFinal + penalidadCastigableHierroFinal + penalidadCastigableSiliceFinal + penalidadCastigableZincFinal

        BigDecimal otrosGastos = (terminos.transporteInterno ?: 0.0G) + (terminos.laboratorio ?: 0.0G) + (terminos.molienda ?: 0.0G) + (terminos.manipuleo ?: 0.0G) + (terminos.margenAdministrativo ?: 0.0G) + (terminos.transporteAPuerto ?: 0.0G) + (terminos.rollBack ?: 0.0G)

        BigDecimal valorTonelada = valorBruto - deduccionMaquilaFinal - deduccionRefinacionOnzaPlataFinal - deduccionRefinacionLibraFinal - penalidadCastigableFinal - otrosGastos

        System.out.println("==== calcularVPT (CONTRATO) — término: ${terminos.nombreContrato} (${terminos.tipoDeMineral}) ====")
        System.out.println("leyes finales -> Zn: ${porcentajeZinc} | Pb: ${porcentajePlomo} | Ag(DM): ${porcentajePlata}")
        System.out.println("cotización tonelada -> Zn: ${cotizacionZinc} | Pb: ${cotizacionPlomo} | Ag: ${cotizacionPlata}")
        System.out.println("ley pagable -> Zn: ${leyPagableZinc} | Pb: ${leyPagablePlomo} | Ag: ${leyPagablePlata}")
        System.out.println("ley final pagable -> Zn: ${leyFinalPagableZinc} | Pb: ${leyFinalPagablePlomo} | Ag: ${leyFinalPagablePlata}")
        System.out.println("valor bruto -> Zn: ${valorBrutoZinc} | Pb: ${valorBrutoPlomo} | Ag: ${valorBrutoPlata} | TOTAL: ${valorBruto}")
        System.out.println("maquila final: ${deduccionMaquilaFinal} (Zn: ${deduccionMaquilaFinalZinc}, Pb: ${deduccionMaquilaFinalPlomo})")
        System.out.println("refinación onza Ag -> Zn-Ag: ${deduccionRefinacionOnzaPlataZincPlata} | Pb-Ag: ${deduccionRefinacionOnzaPlataPlomoPlata} | Cu-Ag: ${deduccionRefinacionOnzaPlataCobrePlata} | TOTAL: ${deduccionRefinacionOnzaPlataFinal}")
        System.out.println("refinación libra final: ${deduccionRefinacionLibraFinal}")
        System.out.println("penalidades final: ${penalidadCastigableFinal}")
        System.out.println("otros gastos: ${otrosGastos}")
        System.out.println("VALOR TONELADA (sin redondear): ${valorTonelada}")

        return valorTonelada
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 50, 1000)
        params.sort = params.sort ?: "nombreContrato"
        params.order = params.order ?: "asc"

        // Buscador: nombre del contrato
        def q = params.q?.trim()
        def results = TerminosDeContrato.createCriteria().list(
                max: params.max, offset: params.offset ?: 0,
                sort: params.sort, order: params.order) {
            if (q) {
                ilike('nombreContrato', "%${q}%")
            }
        }
        [terminosDeContratoInstanceList: results, terminosDeContratoInstanceTotal: results.totalCount, q: q]
    }

    def create() {
        [terminosDeContratoInstance: new TerminosDeContrato(params)]
    }

    def save() {
        def terminosDeContratoInstance = new TerminosDeContrato(params)
        if (!terminosDeContratoInstance.save(flush: true)) {
            render(view: "create", model: [terminosDeContratoInstance: terminosDeContratoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato'), terminosDeContratoInstance.toString()])
        redirect(action: "show", id: terminosDeContratoInstance.id)
    }

    def show(Long id) {
        def terminosDeContratoInstance = TerminosDeContrato.get(id)
        if (!terminosDeContratoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato'), id])
            redirect(action: "list")
            return
        }

        [terminosDeContratoInstance: terminosDeContratoInstance]
    }

    def edit(Long id) {
        def terminosDeContratoInstance = TerminosDeContrato.get(id)
        if (!terminosDeContratoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato'), id])
            redirect(action: "list")
            return
        }

        [terminosDeContratoInstance: terminosDeContratoInstance]
    }

    def update(Long id, Long version) {
        def terminosDeContratoInstance = TerminosDeContrato.get(id)
        if (!terminosDeContratoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (terminosDeContratoInstance.version > version) {
                terminosDeContratoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato')] as Object[],
                        "Another user has updated this TerminosDeContrato while you were editing")
                render(view: "edit", model: [terminosDeContratoInstance: terminosDeContratoInstance])
                return
            }
        }

        terminosDeContratoInstance.properties = params

        if (!terminosDeContratoInstance.save(flush: true)) {
            render(view: "edit", model: [terminosDeContratoInstance: terminosDeContratoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato'), terminosDeContratoInstance.toString()])
        redirect(action: "show", id: terminosDeContratoInstance.id)
    }

    def delete(Long id) {
        def terminosDeContratoInstance = TerminosDeContrato.get(id)
        if (!terminosDeContratoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato'), id])
            redirect(action: "list")
            return
        }

        try {
            terminosDeContratoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato'), id])
            redirect(action: "show", id: id)
        }
    }

    def getValorToneladaPlomoPlata(Long recepcionId, TerminosDeContrato terminosDeContrato, BigDecimal porcentajeZinc, BigDecimal porcentajePlomo, BigDecimal porcentajePlata, BigDecimal porcentajeCobre){
        def recepcion = RecepcionDeComplejo.get(recepcionId)
        def empresa = recepcion.empresa

        //def terminos = TerminosDeContrato.findByEmpresa(empresa)
        def terminos = TerminosDeContrato.get(terminosDeContrato.id)

        def cotizacionZinc = recepcion.cotizacionDiariaDeMinerales.zinc*2204.6223
        def cotizacionPlomo = recepcion.cotizacionDiariaDeMinerales.plomo*2204.6223
        def cotizacionPlata = recepcion.cotizacionDiariaDeMinerales.plata
        def cotizacionCobre = recepcion.cotizacionDiariaDeMinerales.cobre*2204.6223

        def leyPagableZinc = porcentajeZinc - terminos.deduccionUnitariaZinc
        def leyPagablePlomo = porcentajePlomo - terminos.deduccionUnitariaPlomo
        def leyPagablePlata = 100*porcentajePlata/31.1035 - terminos.deduccionUnitariaPlata
        def leyPagableCobre = porcentajeCobre - terminos.deduccionUnitariaCobre
        leyPagableZinc = (leyPagableZinc>0)?leyPagableZinc:0
        leyPagablePlomo = (leyPagablePlomo>0)?leyPagablePlomo:0
        leyPagablePlata = (leyPagablePlata>0)?leyPagablePlata:0
        leyPagableCobre = (leyPagableCobre>0)?leyPagableCobre:0


        def leyFinalPagableZinc = leyPagableZinc*terminos.porcentajePagableLMEZinc/100
        def leyFinalPagablePlomo = leyPagablePlomo*terminos.porcentajePagableLMEPlomo/100
        def leyFinalPagablePlata = leyPagablePlata*terminos.porcentajePagableLMEPlata/100
        def leyFinalPagableCobre = leyPagableCobre*terminos.porcentajePagableLMECobre/100

        def valorBrutoZinc = leyFinalPagableZinc*cotizacionZinc/100
        def valorBrutoPlomo = leyFinalPagablePlomo*cotizacionPlomo/100
        def valorBrutoPlata = leyFinalPagablePlata*cotizacionPlata
        def valorBrutoCobre = leyFinalPagableCobre*cotizacionCobre/100
        def valorBruto = valorBrutoZinc + valorBrutoPlomo + valorBrutoPlata + valorBrutoCobre

        def cotizacionBasadaZinc = (cotizacionZinc>terminos.baseZincPlata)?cotizacionZinc-terminos.baseZincPlata:0
        def cotizacionBasadaPlomo = (cotizacionPlomo>terminos.basePlomoPlata)?cotizacionPlomo-terminos.basePlomoPlata:0
        def cotizacionBasadaCobre = (cotizacionCobre>terminos.baseCobre)?cotizacionCobre-terminos.baseCobre:0

        def cotizacionEscaladaZinc = cotizacionBasadaZinc*terminos.escaladorZincPlata
        def cotizacionEscaladaPlomo = cotizacionBasadaPlomo*terminos.escaladorPlomoPlata
        def cotizacionEscaladaCobre = cotizacionBasadaCobre*terminos.escaladorCobre

        def deduccionMaquilaFinalZinc = cotizacionEscaladaZinc + terminos.maquilaZincPlata
        def deduccionMaquilaFinalPlomo = cotizacionEscaladaPlomo + terminos.maquilaPlomoPlata
        def deduccionMaquilaFinalCobre = cotizacionEscaladaCobre + terminos.maquilaCobre
        def deduccionMaquilaFinal = deduccionMaquilaFinalZinc + deduccionMaquilaFinalPlomo + deduccionMaquilaFinalCobre

        def deduccionRefinacionOnzaPlataZincPlata = leyFinalPagablePlata*terminos.deduccionRefinacionOnzaZincPlata
        def deduccionRefinacionOnzaPlataPlomoPlata = leyFinalPagablePlata*terminos.deduccionRefinacionOnzaPlomoPlata
        def deduccionRefinacionOnzaPlataCobrePlata = leyFinalPagablePlata*terminos.deduccionRefinacionOnzaCobrePlata
        def deduccionRefinacionOnzaPlataFinal = deduccionRefinacionOnzaPlataZincPlata + deduccionRefinacionOnzaPlataPlomoPlata + deduccionRefinacionOnzaPlataCobrePlata

        def deduccionRefinacionLibraZinc = leyPagableZinc*2204.6223*terminos.deduccionRefinacionLibraZinc/100
        def deduccionRefinacionLibraPlomo = leyPagablePlomo*2204.6223*terminos.deduccionRefinacionLibraPlomo/100
        def deduccionRefinacionLibraCobre = leyPagableCobre*2204.6223*terminos.deduccionRefinacionLibraCobre/100
        def deduccionRefinacionLibraFinal = deduccionRefinacionLibraZinc + deduccionRefinacionLibraPlomo + deduccionRefinacionLibraCobre

        def penalidadCastigableArsenico = terminos.porcentajeArsenico - terminos.arsenicoLibre
        penalidadCastigableArsenico = (penalidadCastigableArsenico<0)?0:penalidadCastigableArsenico
        def penalidadCastigableAntimonio = terminos.porcentajeAntimonio - terminos.antimonioLibre
        penalidadCastigableAntimonio = (penalidadCastigableAntimonio<0)?0:penalidadCastigableAntimonio
        def penalidadCastigableBismuto = terminos.porcentajeBismuto - terminos.bismutoLibre
        penalidadCastigableBismuto = (penalidadCastigableBismuto<0)?0:penalidadCastigableBismuto
        def penalidadCastigableEstano = terminos.porcentajeEstano - terminos.estanoLibre
        penalidadCastigableEstano = (penalidadCastigableEstano<0)?0:penalidadCastigableEstano
        def penalidadCastigableHierro = terminos.porcentajeHierro - terminos.hierroLibre
        penalidadCastigableHierro = (penalidadCastigableHierro<0)?0:penalidadCastigableHierro
        def penalidadCastigableSilice = terminos.porcentajeSilice - terminos.siliceLibre
        penalidadCastigableSilice = (penalidadCastigableSilice<0)?0:penalidadCastigableSilice
        def penalidadCastigableZinc = terminos.porcentajeZinc - terminos.zincLibre
        penalidadCastigableZinc = (penalidadCastigableZinc<0)?0:penalidadCastigableZinc

        def penalidadCastigableArsenicoFinal = penalidadCastigableArsenico*terminos.costoUnitarioArsenico/((terminos.porcentajeUnitarioArsenico<=0)?1:terminos.porcentajeUnitarioArsenico)
        def penalidadCastigableAntimonioFinal = penalidadCastigableAntimonio*terminos.costoUnitarioAntimonio/((terminos.porcentajeUnitarioAntimonio<=0)?1:terminos.porcentajeUnitarioAntimonio)
        def penalidadCastigableBismutoFinal = penalidadCastigableBismuto*terminos.costoUnitarioBismuto/((terminos.porcentajeUnitarioBismuto<=0)?1:terminos.porcentajeUnitarioBismuto)
        def penalidadCastigableEstanoFinal = penalidadCastigableEstano*terminos.costoUnitarioEstano/((terminos.porcentajeUnitarioEstano<=0)?1:terminos.porcentajeUnitarioEstano)
        def penalidadCastigableHierroFinal = penalidadCastigableHierro*terminos.costoUnitarioHierro/((terminos.porcentajeUnitarioHierro<=0)?1:terminos.porcentajeUnitarioHierro)
        def penalidadCastigableSiliceFinal = penalidadCastigableSilice*terminos.costoUnitarioSilice/((terminos.porcentajeUnitarioSilice<=0)?1:terminos.porcentajeUnitarioSilice)
        def penalidadCastigableZincFinal = penalidadCastigableZinc*terminos.costoUnitarioZinc/((terminos.porcentajeUnitarioZinc<=0)?1:terminos.porcentajeUnitarioZinc)
        def penalidadCastigableFinal = penalidadCastigableArsenicoFinal + penalidadCastigableAntimonioFinal + penalidadCastigableBismutoFinal + penalidadCastigableEstanoFinal + penalidadCastigableHierroFinal + penalidadCastigableSiliceFinal +penalidadCastigableZincFinal

        def valorTonelada = valorBruto - deduccionMaquilaFinal - deduccionRefinacionOnzaPlataFinal- deduccionRefinacionLibraFinal- penalidadCastigableFinal- terminos.transporteInterno- terminos.laboratorio- terminos.molienda- terminos.manipuleo- terminos.margenAdministrativo- terminos.transporteAPuerto- terminos.rollBack

        System.out.println("cotizacionZinc: ${cotizacionZinc}")
        System.out.println("cotizacionPlomo:${cotizacionPlomo}")
        System.out.println("cotizacionPlata:${cotizacionPlata}")
        System.out.println("cotizacionCobre:${cotizacionCobre}")

        System.out.println("leyPagableZinc: ${leyPagableZinc}")
        System.out.println("leyPagablePlomo:${leyPagablePlomo}")
        System.out.println("leyPagablePlata:${leyPagablePlata}")
        System.out.println("leyPagableCobre:${leyPagableCobre}")

        System.out.println("leyFinalPagableZinc: ${leyFinalPagableZinc}")
        System.out.println("leyFinalPagablePlomo:${leyFinalPagablePlomo}")
        System.out.println("leyFinalPagablePlata:${leyFinalPagablePlata}")
        System.out.println("leyFinalPagableCobre:${leyFinalPagableCobre}")

        System.out.println("valorBrutoZinc: ${valorBrutoZinc}")
        System.out.println("valorBrutoPlomo:${valorBrutoPlomo}")
        System.out.println("valorBrutoPlata:${valorBrutoPlata}")
        System.out.println("valorBrutoCobre:${valorBrutoCobre}")
        System.out.println("valorBruto:${valorBruto}")

        System.out.println("cotizacionBasadaZinc: ${cotizacionBasadaZinc}")
        System.out.println("cotizacionBasadaPlomo:${cotizacionBasadaPlomo}")
        System.out.println("cotizacionBasadaCobre:${cotizacionBasadaCobre}")

        System.out.println("cotizacionEscaladaZinc: ${cotizacionEscaladaZinc}")
        System.out.println("cotizacionEscaladaPlomo:${cotizacionEscaladaPlomo}")
        System.out.println("cotizacionEscaladaCobre:${cotizacionEscaladaCobre}")

        System.out.println("deduccionMaquilaFinalZinc: ${deduccionMaquilaFinalZinc}")
        System.out.println("deduccionMaquilaFinalPlomo: ${deduccionMaquilaFinalPlomo}")
        System.out.println("deduccionMaquilaFinalCobre: ${deduccionMaquilaFinalCobre}")
        System.out.println("deduccionMaquilaFinal: ${deduccionMaquilaFinal}")

        System.out.println("deduccionRefinacionOnzaPlataZincPlata: ${deduccionRefinacionOnzaPlataZincPlata}")
        System.out.println("deduccionRefinacionOnzaPlataPlomoPlata: ${deduccionRefinacionOnzaPlataPlomoPlata}")
        System.out.println("deduccionRefinacionOnzaPlataCobrePlata: ${deduccionRefinacionOnzaPlataCobrePlata}")
        System.out.println("deduccionRefinacionOnzaPlataFinal: ${deduccionRefinacionOnzaPlataFinal}")

        System.out.println("deduccionRefinacionLibraZinc: ${deduccionRefinacionLibraZinc}")
        System.out.println("deduccionRefinacionLibraPlomo: ${deduccionRefinacionLibraPlomo}")
        System.out.println("deduccionRefinacionLibraCobre: ${deduccionRefinacionLibraCobre}")
        System.out.println("deduccionRefinacionLibraFinal: ${deduccionRefinacionLibraFinal}")

        System.out.println("penalidadCastigableArsenico: ${penalidadCastigableArsenico}")
        System.out.println("penalidadCastigableAntimonio: ${penalidadCastigableAntimonio}")
        System.out.println("penalidadCastigableBismuto: ${penalidadCastigableBismuto}")
        System.out.println("penalidadCastigableEstano: ${penalidadCastigableEstano}")
        System.out.println("penalidadCastigableHierro: ${penalidadCastigableHierro}")
        System.out.println("penalidadCastigableSilice: ${penalidadCastigableSilice}")
        System.out.println("penalidadCastigableZinc: ${penalidadCastigableZinc}")

        System.out.println("penalidadCastigableArsenicoFinal: ${penalidadCastigableArsenicoFinal}")
        System.out.println("penalidadCastigableAntimonioFinal: ${penalidadCastigableAntimonioFinal}")
        System.out.println("penalidadCastigableBismutoFinal: ${penalidadCastigableBismutoFinal}")
        System.out.println("penalidadCastigableEstanoFinal: ${penalidadCastigableEstanoFinal}")
        System.out.println("penalidadCastigableHierroFinal: ${penalidadCastigableHierroFinal}")
        System.out.println("penalidadCastigableSiliceFinal: ${penalidadCastigableSiliceFinal}")
        System.out.println("penalidadCastigableZincFinal: ${penalidadCastigableZincFinal}")
        System.out.println("penalidadCastigableFinal: ${penalidadCastigableFinal}")

        System.out.println("valorTonelada: ${valorTonelada}")

        return valorTonelada
    }

    def getValorTonelada = {
        def recepcion = RecepcionDeComplejo.get(params.recepcionId.toLong())
        def empresa = recepcion.empresa
        def porcentajeZinc = new BigDecimal(params.porcentajeZinc.toString())
        def porcentajePlomo = new BigDecimal(params.porcentajePlomo.toString())
        def porcentajePlata = new BigDecimal(params.porcentajePlata.toString())
        def porcentajeCobre = new BigDecimal(params.porcentajeCobre.toString())

        def terminos = TerminosDeContrato.get(params.terminosId.toLong())

        def cotizacionZinc = recepcion.cotizacionDiariaDeMinerales.zinc*2204.6223
        def cotizacionPlomo = recepcion.cotizacionDiariaDeMinerales.plomo*2204.6223
        def cotizacionPlata = recepcion.cotizacionDiariaDeMinerales.plata
        def cotizacionCobre = recepcion.cotizacionDiariaDeMinerales.cobre*2204.6223

        def leyPagableZinc = porcentajeZinc - terminos.deduccionUnitariaZinc
        def leyPagablePlomo = porcentajePlomo - terminos.deduccionUnitariaPlomo
        def leyPagablePlata = 100*porcentajePlata/31.1035 - terminos.deduccionUnitariaPlata
        def leyPagableCobre = porcentajeCobre - terminos.deduccionUnitariaCobre
        leyPagableZinc = (leyPagableZinc>0)?leyPagableZinc:0
        leyPagablePlomo = (leyPagablePlomo>0)?leyPagablePlomo:0
        leyPagablePlata = (leyPagablePlata>0)?leyPagablePlata:0
        leyPagableCobre = (leyPagableCobre>0)?leyPagableCobre:0


        def leyFinalPagableZinc = leyPagableZinc*terminos.porcentajePagableLMEZinc/100
        def leyFinalPagablePlomo = leyPagablePlomo*terminos.porcentajePagableLMEPlomo/100
        def leyFinalPagablePlata = leyPagablePlata*terminos.porcentajePagableLMEPlata/100
        def leyFinalPagableCobre = leyPagableCobre*terminos.porcentajePagableLMECobre/100

        def valorBrutoZinc = leyFinalPagableZinc*cotizacionZinc/100
        def valorBrutoPlomo = leyFinalPagablePlomo*cotizacionPlomo/100
        def valorBrutoPlata = leyFinalPagablePlata*cotizacionPlata
        def valorBrutoCobre = leyFinalPagableCobre*cotizacionCobre/100
        def valorBruto = valorBrutoZinc + valorBrutoPlomo + valorBrutoPlata + valorBrutoCobre

        def cotizacionBasadaZinc = (cotizacionZinc>terminos.baseZincPlata)?cotizacionZinc-terminos.baseZincPlata:0
        def cotizacionBasadaPlomo = (cotizacionPlomo>terminos.basePlomoPlata)?cotizacionPlomo-terminos.basePlomoPlata:0
        def cotizacionBasadaCobre = (cotizacionCobre>terminos.baseCobre)?cotizacionCobre-terminos.baseCobre:0

        def cotizacionEscaladaZinc = cotizacionBasadaZinc*terminos.escaladorZincPlata
        def cotizacionEscaladaPlomo = cotizacionBasadaPlomo*terminos.escaladorPlomoPlata
        def cotizacionEscaladaCobre = cotizacionBasadaCobre*terminos.escaladorCobre

        def deduccionMaquilaFinalZinc = cotizacionEscaladaZinc + terminos.maquilaZincPlata
        def deduccionMaquilaFinalPlomo = cotizacionEscaladaPlomo + terminos.maquilaPlomoPlata
        def deduccionMaquilaFinalCobre = cotizacionEscaladaCobre + terminos.maquilaCobre
        def deduccionMaquilaFinal = deduccionMaquilaFinalZinc + deduccionMaquilaFinalPlomo + deduccionMaquilaFinalCobre

        def deduccionRefinacionOnzaPlataZincPlata = leyPagablePlata*terminos.deduccionRefinacionOnzaZincPlata
        def deduccionRefinacionOnzaPlataPlomoPlata = leyPagablePlata*terminos.deduccionRefinacionOnzaPlomoPlata
        def deduccionRefinacionOnzaPlataCobrePlata = leyPagablePlata*terminos.deduccionRefinacionOnzaCobrePlata
        def deduccionRefinacionOnzaPlataFinal = deduccionRefinacionOnzaPlataZincPlata + deduccionRefinacionOnzaPlataPlomoPlata + deduccionRefinacionOnzaPlataCobrePlata

        def deduccionRefinacionLibraZinc = leyPagableZinc*2204.6223*terminos.deduccionRefinacionLibraZinc/100
        def deduccionRefinacionLibraPlomo = leyPagablePlomo*2204.6223*terminos.deduccionRefinacionLibraPlomo/100
        def deduccionRefinacionLibraCobre = leyPagableCobre*2204.6223*terminos.deduccionRefinacionLibraCobre/100
        def deduccionRefinacionLibraFinal = deduccionRefinacionLibraZinc + deduccionRefinacionLibraPlomo + deduccionRefinacionLibraCobre

        def penalidadCastigableArsenico = terminos.porcentajeArsenico - terminos.arsenicoLibre
        penalidadCastigableArsenico = (penalidadCastigableArsenico<0)?0:penalidadCastigableArsenico
        def penalidadCastigableAntimonio = terminos.porcentajeAntimonio - terminos.antimonioLibre
        penalidadCastigableAntimonio = (penalidadCastigableAntimonio<0)?0:penalidadCastigableAntimonio
        def penalidadCastigableBismuto = terminos.porcentajeBismuto - terminos.bismutoLibre
        penalidadCastigableBismuto = (penalidadCastigableBismuto<0)?0:penalidadCastigableBismuto
        def penalidadCastigableEstano = terminos.porcentajeEstano - terminos.estanoLibre
        penalidadCastigableEstano = (penalidadCastigableEstano<0)?0:penalidadCastigableEstano
        def penalidadCastigableHierro = terminos.porcentajeHierro - terminos.hierroLibre
        penalidadCastigableHierro = (penalidadCastigableHierro<0)?0:penalidadCastigableHierro
        def penalidadCastigableSilice = terminos.porcentajeSilice - terminos.siliceLibre
        penalidadCastigableSilice = (penalidadCastigableSilice<0)?0:penalidadCastigableSilice
        def penalidadCastigableZinc = terminos.porcentajeZinc - terminos.zincLibre
        penalidadCastigableZinc = (penalidadCastigableZinc<0)?0:penalidadCastigableZinc

        def penalidadCastigableArsenicoFinal = penalidadCastigableArsenico*terminos.costoUnitarioArsenico/((terminos.porcentajeUnitarioArsenico<=0)?1:terminos.porcentajeUnitarioArsenico)
        def penalidadCastigableAntimonioFinal = penalidadCastigableAntimonio*terminos.costoUnitarioAntimonio/((terminos.porcentajeUnitarioAntimonio<=0)?1:terminos.porcentajeUnitarioAntimonio)
        def penalidadCastigableBismutoFinal = penalidadCastigableBismuto*terminos.costoUnitarioBismuto/((terminos.porcentajeUnitarioBismuto<=0)?1:terminos.porcentajeUnitarioBismuto)
        def penalidadCastigableEstanoFinal = penalidadCastigableEstano*terminos.costoUnitarioEstano/((terminos.porcentajeUnitarioEstano<=0)?1:terminos.porcentajeUnitarioEstano)
        def penalidadCastigableHierroFinal = penalidadCastigableHierro*terminos.costoUnitarioHierro/((terminos.porcentajeUnitarioHierro<=0)?1:terminos.porcentajeUnitarioHierro)
        def penalidadCastigableSiliceFinal = penalidadCastigableSilice*terminos.costoUnitarioSilice/((terminos.porcentajeUnitarioSilice<=0)?1:terminos.porcentajeUnitarioSilice)
        def penalidadCastigableZincFinal = penalidadCastigableZinc*terminos.costoUnitarioZinc/((terminos.porcentajeUnitarioZinc<=0)?1:terminos.porcentajeUnitarioZinc)
        def penalidadCastigableFinal = penalidadCastigableArsenicoFinal + penalidadCastigableAntimonioFinal + penalidadCastigableBismutoFinal + penalidadCastigableEstanoFinal + penalidadCastigableHierroFinal + penalidadCastigableSiliceFinal +penalidadCastigableZincFinal

        def valorTonelada = valorBruto - deduccionMaquilaFinal - deduccionRefinacionOnzaPlataFinal- deduccionRefinacionLibraFinal- penalidadCastigableFinal- terminos.transporteInterno- terminos.laboratorio- terminos.molienda- terminos.manipuleo- terminos.margenAdministrativo- terminos.transporteAPuerto- terminos.rollBack

        System.out.println("cotizacionZinc: ${cotizacionZinc}")
        System.out.println("cotizacionPlomo:${cotizacionPlomo}")
        System.out.println("cotizacionPlata:${cotizacionPlata}")
        System.out.println("cotizacionCobre:${cotizacionCobre}")

        System.out.println("leyPagableZinc: ${leyPagableZinc}")
        System.out.println("leyPagablePlomo:${leyPagablePlomo}")
        System.out.println("leyPagablePlata:${leyPagablePlata}")
        System.out.println("leyPagableCobre:${leyPagableCobre}")

        System.out.println("leyFinalPagableZinc: ${leyFinalPagableZinc}")
        System.out.println("leyFinalPagablePlomo:${leyFinalPagablePlomo}")
        System.out.println("leyFinalPagablePlata:${leyFinalPagablePlata}")
        System.out.println("leyFinalPagableCobre:${leyFinalPagableCobre}")

        System.out.println("valorBrutoZinc: ${valorBrutoZinc}")
        System.out.println("valorBrutoPlomo:${valorBrutoPlomo}")
        System.out.println("valorBrutoPlata:${valorBrutoPlata}")
        System.out.println("valorBrutoCobre:${valorBrutoCobre}")
        System.out.println("valorBruto:${valorBruto}")

        System.out.println("cotizacionBasadaZinc: ${cotizacionBasadaZinc}")
        System.out.println("cotizacionBasadaPlomo:${cotizacionBasadaPlomo}")
        System.out.println("cotizacionBasadaCobre:${cotizacionBasadaCobre}")

        System.out.println("cotizacionEscaladaZinc: ${cotizacionEscaladaZinc}")
        System.out.println("cotizacionEscaladaPlomo:${cotizacionEscaladaPlomo}")
        System.out.println("cotizacionEscaladaCobre:${cotizacionEscaladaCobre}")

        System.out.println("deduccionMaquilaFinalZinc: ${deduccionMaquilaFinalZinc}")
        System.out.println("deduccionMaquilaFinalPlomo: ${deduccionMaquilaFinalPlomo}")
        System.out.println("deduccionMaquilaFinalCobre: ${deduccionMaquilaFinalCobre}")
        System.out.println("deduccionMaquilaFinal: ${deduccionMaquilaFinal}")

        System.out.println("deduccionRefinacionOnzaPlataZincPlata: ${deduccionRefinacionOnzaPlataZincPlata}")
        System.out.println("deduccionRefinacionOnzaPlataPlomoPlata: ${deduccionRefinacionOnzaPlataPlomoPlata}")
        System.out.println("deduccionRefinacionOnzaPlataCobrePlata: ${deduccionRefinacionOnzaPlataCobrePlata}")
        System.out.println("deduccionRefinacionOnzaPlataFinal: ${deduccionRefinacionOnzaPlataFinal}")

        System.out.println("deduccionRefinacionLibraZinc: ${deduccionRefinacionLibraZinc}")
        System.out.println("deduccionRefinacionLibraPlomo: ${deduccionRefinacionLibraPlomo}")
        System.out.println("deduccionRefinacionLibraCobre: ${deduccionRefinacionLibraCobre}")
        System.out.println("deduccionRefinacionLibraFinal: ${deduccionRefinacionLibraFinal}")

        System.out.println("penalidadCastigableArsenico: ${penalidadCastigableArsenico}")
        System.out.println("penalidadCastigableAntimonio: ${penalidadCastigableAntimonio}")
        System.out.println("penalidadCastigableBismuto: ${penalidadCastigableBismuto}")
        System.out.println("penalidadCastigableEstano: ${penalidadCastigableEstano}")
        System.out.println("penalidadCastigableHierro: ${penalidadCastigableHierro}")
        System.out.println("penalidadCastigableSilice: ${penalidadCastigableSilice}")
        System.out.println("penalidadCastigableZinc: ${penalidadCastigableZinc}")

        System.out.println("penalidadCastigableArsenicoFinal: ${penalidadCastigableArsenicoFinal}")
        System.out.println("penalidadCastigableAntimonioFinal: ${penalidadCastigableAntimonioFinal}")
        System.out.println("penalidadCastigableBismutoFinal: ${penalidadCastigableBismutoFinal}")
        System.out.println("penalidadCastigableEstanoFinal: ${penalidadCastigableEstanoFinal}")
        System.out.println("penalidadCastigableHierroFinal: ${penalidadCastigableHierroFinal}")
        System.out.println("penalidadCastigableSiliceFinal: ${penalidadCastigableSiliceFinal}")
        System.out.println("penalidadCastigableZincFinal: ${penalidadCastigableZincFinal}")
        System.out.println("penalidadCastigableFinal: ${penalidadCastigableFinal}")

        System.out.println("valorTonelada: ${valorTonelada}")

        render([vpt: valorTonelada] as JSON)
    }

    def valorToneladaPlomoPlata = {
        def recepcion = RecepcionDePlomoPlata.get(params.recepcionId)
        def empresa = recepcion.empresa
        def porcentajeZinc = new BigDecimal(params.porcentajeZinc.toString())
        def porcentajePlomo = new BigDecimal(params.porcentajePlomo.toString())
        def porcentajePlata = new BigDecimal(params.porcentajePlata.toString())
        def porcentajeCobre = new BigDecimal(params.porcentajeCobre.toString())

        def terminos = TerminosDeContrato.findByEmpresa(empresa)

        def cotizacionZinc = recepcion.cotizacionDiariaDeMinerales.zinc*2204.6223
        def cotizacionPlomo = recepcion.cotizacionDiariaDeMinerales.plomo*2204.6223
        def cotizacionPlata = recepcion.cotizacionDiariaDeMinerales.plata
        def cotizacionCobre = recepcion.cotizacionDiariaDeMinerales.estano*2204.6223

        def leyPagableZinc = porcentajeZinc - terminos.deduccionUnitariaZinc
        def leyPagablePlomo = porcentajePlomo - terminos.deduccionUnitariaPlomo
        def leyPagablePlata = porcentajePlata/31.1035 - terminos.deduccionUnitariaPlata
        def leyPagableCobre = porcentajeCobre - terminos.deduccionUnitariaCobre

        def leyFinalPagableZinc = leyPagableZinc*terminos.porcentajePagableLMEZinc/100
        def leyFinalPagablePlomo = leyPagablePlomo*terminos.porcentajePagableLMEPlomo/100
        def leyFinalPagablePlata = leyPagablePlata*terminos.porcentajePagableLMEPlata/100
        def leyFinalPagableCobre = leyPagableCobre*terminos.porcentajePagableLMECobre/100

        def valorBrutoZinc = leyFinalPagableZinc*cotizacionZinc/100
        def valorBrutoPlomo = leyFinalPagablePlomo*cotizacionPlomo/100
        def valorBrutoPlata = leyFinalPagablePlata*cotizacionPlata
        def valorBrutoCobre = leyFinalPagableCobre*cotizacionCobre/100
        def valorBruto = valorBrutoZinc + valorBrutoPlomo + valorBrutoPlata + valorBrutoCobre

        def cotizacionBasadaZinc = (cotizacionZinc>terminos.baseZincPlata)?cotizacionZinc-terminos.baseZincPlata:0
        def cotizacionBasadaPlomo = (cotizacionPlomo>terminos.basePlomoPlata)?cotizacionPlomo-terminos.basePlomoPlata:0
        def cotizacionBasadaCobre = (cotizacionCobre>terminos.baseCobre)?cotizacionCobre-terminos.baseCobre:0

        def cotizacionEscaladaZinc = cotizacionBasadaZinc*terminos.escaladorZincPlata
        def cotizacionEscaladaPlomo = cotizacionBasadaPlomo*terminos.escaladorPlomoPlata
        def cotizacionEscaladaCobre = cotizacionBasadaCobre*terminos.escaladorCobre

        def deduccionMaquilaFinalZinc = cotizacionEscaladaZinc + terminos.maquilaZincPlata
        def deduccionMaquilaFinalPlomo = cotizacionEscaladaPlomo + terminos.maquilaPlomoPlata
        def deduccionMaquilaFinalCobre = cotizacionEscaladaCobre + terminos.maquilaCobre
        def deduccionMaquilaFinal = deduccionMaquilaFinalZinc + deduccionMaquilaFinalPlomo + deduccionMaquilaFinalCobre

        def deduccionRefinacionOnzaPlataZincPlata = leyPagablePlata*terminos.deduccionRefinacionOnzaZincPlata
        def deduccionRefinacionOnzaPlataPlomoPlata = leyPagablePlata*terminos.deduccionRefinacionOnzaPlomoPlata
        def deduccionRefinacionOnzaPlataCobrePlata = leyPagablePlata*terminos.deduccionRefinacionOnzaCobrePlata
        def deduccionRefinacionOnzaPlataFinal = deduccionRefinacionOnzaPlataZincPlata + deduccionRefinacionOnzaPlataPlomoPlata + deduccionRefinacionOnzaPlataCobrePlata

        def deduccionRefinacionLibraZinc = leyPagableZinc*2204.6223*terminos.deduccionRefinacionLibraZinc/100
        def deduccionRefinacionLibraPlomo = leyPagablePlomo*2204.6223*terminos.deduccionRefinacionLibraPlomo/100
        def deduccionRefinacionLibraCobre = leyPagableCobre*2204.6223*terminos.deduccionRefinacionLibraCobre/100
        def deduccionRefinacionLibraFinal = deduccionRefinacionLibraZinc + deduccionRefinacionLibraPlomo + deduccionRefinacionLibraCobre

        def penalidadCastigableArsenico = terminos.porcentajeArsenico - terminos.arsenicoLibre
        def penalidadCastigableAntimonio = terminos.porcentajeAntimonio - terminos.antimonioLibre
        def penalidadCastigableBismuto = terminos.porcentajeBismuto - terminos.bismutoLibre
        def penalidadCastigableEstano = terminos.porcentajeEstano - terminos.estanoLibre
        def penalidadCastigableHierro = terminos.porcentajeHierro - terminos.hierroLibre
        def penalidadCastigableSilice = terminos.porcentajeSilice - terminos.siliceLibre
        def penalidadCastigableZinc = terminos.porcentajeZinc - terminos.zincLibre

        def penalidadCastigableArsenicoFinal = penalidadCastigableArsenico*terminos.costoUnitarioArsenico/((terminos.porcentajeUnitarioArsenico<=0)?1:terminos.porcentajeUnitarioArsenico)
        def penalidadCastigableAntimonioFinal = penalidadCastigableAntimonio*terminos.costoUnitarioAntimonio/((terminos.porcentajeUnitarioAntimonio<=0)?1:terminos.porcentajeUnitarioAntimonio)
        def penalidadCastigableBismutoFinal = penalidadCastigableBismuto*terminos.costoUnitarioBismuto/((terminos.porcentajeUnitarioBismuto<=0)?1:terminos.porcentajeUnitarioBismuto)
        def penalidadCastigableEstanoFinal = penalidadCastigableEstano*terminos.costoUnitarioEstano/((terminos.porcentajeUnitarioEstano<=0)?1:terminos.porcentajeUnitarioEstano)
        def penalidadCastigableHierroFinal = penalidadCastigableHierro*terminos.costoUnitarioHierro/((terminos.porcentajeUnitarioHierro<=0)?1:terminos.porcentajeUnitarioHierro)
        def penalidadCastigableSiliceFinal = penalidadCastigableSilice*terminos.costoUnitarioSilice/((terminos.porcentajeUnitarioSilice<=0)?1:terminos.porcentajeUnitarioSilice)
        def penalidadCastigableZincFinal = penalidadCastigableZinc*terminos.costoUnitarioZinc/((terminos.porcentajeUnitarioZinc<=0)?1:terminos.porcentajeUnitarioZinc)
        def penalidadCastigableFinal = penalidadCastigableArsenicoFinal + penalidadCastigableAntimonioFinal + penalidadCastigableBismutoFinal + penalidadCastigableEstanoFinal + penalidadCastigableHierroFinal + penalidadCastigableSiliceFinal +penalidadCastigableZincFinal

        def valorTonelada = valorBruto - deduccionMaquilaFinal - deduccionRefinacionOnzaPlataFinal- deduccionRefinacionLibraFinal- penalidadCastigableFinal- terminos.transporteInterno- terminos.laboratorio- terminos.molienda- terminos.manipuleo- terminos.margenAdministrativo- terminos.transporteAPuerto- terminos.rollBack
                                
        System.out.println("cotizacionZinc: ${cotizacionZinc}")
        System.out.println("cotizacionPlomo:${cotizacionPlomo}")
        System.out.println("cotizacionPlata:${cotizacionPlata}")
        System.out.println("cotizacionCobre:${cotizacionCobre}")

        System.out.println("leyPagableZinc: ${leyPagableZinc}")
        System.out.println("leyPagablePlomo:${leyPagablePlomo}")
        System.out.println("leyPagablePlata:${leyPagablePlata}")
        System.out.println("leyPagableCobre:${leyPagableCobre}")

        System.out.println("leyFinalPagableZinc: ${leyFinalPagableZinc}")
        System.out.println("leyFinalPagablePlomo:${leyFinalPagablePlomo}")
        System.out.println("leyFinalPagablePlata:${leyFinalPagablePlata}")
        System.out.println("leyFinalPagableCobre:${leyFinalPagableCobre}")

        System.out.println("valorBrutoZinc: ${valorBrutoZinc}")
        System.out.println("valorBrutoPlomo:${valorBrutoPlomo}")
        System.out.println("valorBrutoPlata:${valorBrutoPlata}")
        System.out.println("valorBrutoCobre:${valorBrutoCobre}")
        System.out.println("valorBruto:${valorBruto}")

        System.out.println("cotizacionBasadaZinc: ${cotizacionBasadaZinc}")
        System.out.println("cotizacionBasadaPlomo:${cotizacionBasadaPlomo}")
        System.out.println("cotizacionBasadaCobre:${cotizacionBasadaCobre}")

        System.out.println("cotizacionEscaladaZinc: ${cotizacionEscaladaZinc}")
        System.out.println("cotizacionEscaladaPlomo:${cotizacionEscaladaPlomo}")
        System.out.println("cotizacionEscaladaCobre:${cotizacionEscaladaCobre}")

        System.out.println("deduccionMaquilaFinalZinc: ${deduccionMaquilaFinalZinc}")
        System.out.println("deduccionMaquilaFinalPlomo: ${deduccionMaquilaFinalPlomo}")
        System.out.println("deduccionMaquilaFinalCobre: ${deduccionMaquilaFinalCobre}")
        System.out.println("deduccionMaquilaFinal: ${deduccionMaquilaFinal}")

        System.out.println("deduccionRefinacionOnzaPlataZincPlata: ${deduccionRefinacionOnzaPlataZincPlata}")
        System.out.println("deduccionRefinacionOnzaPlataPlomoPlata: ${deduccionRefinacionOnzaPlataPlomoPlata}")
        System.out.println("deduccionRefinacionOnzaPlataCobrePlata: ${deduccionRefinacionOnzaPlataCobrePlata}")
        System.out.println("deduccionRefinacionOnzaPlataFinal: ${deduccionRefinacionOnzaPlataFinal}")

        System.out.println("deduccionRefinacionLibraZinc: ${deduccionRefinacionLibraZinc}")
        System.out.println("deduccionRefinacionLibraPlomo: ${deduccionRefinacionLibraPlomo}")
        System.out.println("deduccionRefinacionLibraCobre: ${deduccionRefinacionLibraCobre}")
        System.out.println("deduccionRefinacionLibraFinal: ${deduccionRefinacionLibraFinal}")
        
        System.out.println("penalidadCastigableArsenico: ${penalidadCastigableArsenico}")
        System.out.println("penalidadCastigableAntimonio: ${penalidadCastigableAntimonio}")
        System.out.println("penalidadCastigableBismuto: ${penalidadCastigableBismuto}")
        System.out.println("penalidadCastigableEstano: ${penalidadCastigableEstano}")
        System.out.println("penalidadCastigableHierro: ${penalidadCastigableHierro}")
        System.out.println("penalidadCastigableSilice: ${penalidadCastigableSilice}")
        System.out.println("penalidadCastigableZinc: ${penalidadCastigableZinc}")

        System.out.println("penalidadCastigableArsenicoFinal: ${penalidadCastigableArsenicoFinal}")
        System.out.println("penalidadCastigableAntimonioFinal: ${penalidadCastigableAntimonioFinal}")
        System.out.println("penalidadCastigableBismutoFinal: ${penalidadCastigableBismutoFinal}")
        System.out.println("penalidadCastigableEstanoFinal: ${penalidadCastigableEstanoFinal}")
        System.out.println("penalidadCastigableHierroFinal: ${penalidadCastigableHierroFinal}")
        System.out.println("penalidadCastigableSiliceFinal: ${penalidadCastigableSiliceFinal}")
        System.out.println("penalidadCastigableZincFinal: ${penalidadCastigableZincFinal}")
        System.out.println("penalidadCastigableFinal: ${penalidadCastigableFinal}")

        System.out.println("valorTonelada: ${valorTonelada}")
        
        render([vt: valorTonelada] as JSON)
    }

    def getTerminosIds = { ->
        def terminos = TerminosDeContrato.list([sort: "id"])
        //<g:select id="tablaComplejo" name="tablaComplejo.id" from="${org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo.list()}" optionKey="id" value="${controlCalidadComplejoInstance?.tablaComplejo?.id}" class="many-to-one"/>
        //render g.select(name: "tablaComplejo.id",id: "tablaComplejo",from: terminos,optionKey: "id",value: "${controlCalidadComplejoInstance?.tablaComplejo?.id}",class: "many-to-one")
        //render g.select(name: "tablaComplejo.id",id: "tablaComplejo",from: terminos,optionKey: "id",class: "many-to-one")
        def ids=""
        terminos.each {
            ids=ids+it.id+"-"
        }
        return ids
    }

    def getValorToneladaParaCotizacion(CotizacionDiariaDeMinerales cotizacionDiaria, TerminosDeContrato terminosDeContrato, BigDecimal porcentajeZinc, BigDecimal porcentajePlomo, BigDecimal porcentajePlata, BigDecimal porcentajeCobre){
        def terminos = TerminosDeContrato.get(terminosDeContrato.id)

        def cotizacionZinc = cotizacionDiaria.zinc*2204.6223
        def cotizacionPlomo = cotizacionDiaria.plomo*2204.6223
        def cotizacionPlata = cotizacionDiaria.plata
        def cotizacionCobre = cotizacionDiaria.cobre*2204.6223

        def leyPagableZinc = porcentajeZinc - terminos.deduccionUnitariaZinc
        def leyPagablePlomo = porcentajePlomo - terminos.deduccionUnitariaPlomo
        def leyPagablePlata = 100*porcentajePlata/31.1035 - terminos.deduccionUnitariaPlata
        def leyPagableCobre = porcentajeCobre - terminos.deduccionUnitariaCobre
        leyPagableZinc = (leyPagableZinc>0)?leyPagableZinc:0
        leyPagablePlomo = (leyPagablePlomo>0)?leyPagablePlomo:0
        leyPagablePlata = (leyPagablePlata>0)?leyPagablePlata:0
        leyPagableCobre = (leyPagableCobre>0)?leyPagableCobre:0


        def leyFinalPagableZinc = leyPagableZinc*terminos.porcentajePagableLMEZinc/100
        def leyFinalPagablePlomo = leyPagablePlomo*terminos.porcentajePagableLMEPlomo/100
        def leyFinalPagablePlata = leyPagablePlata*terminos.porcentajePagableLMEPlata/100
        def leyFinalPagableCobre = leyPagableCobre*terminos.porcentajePagableLMECobre/100

        def valorBrutoZinc = leyFinalPagableZinc*cotizacionZinc/100
        def valorBrutoPlomo = leyFinalPagablePlomo*cotizacionPlomo/100
        def valorBrutoPlata = leyFinalPagablePlata*cotizacionPlata
        def valorBrutoCobre = leyFinalPagableCobre*cotizacionCobre/100
        def valorBruto = valorBrutoZinc + valorBrutoPlomo + valorBrutoPlata + valorBrutoCobre

        def cotizacionBasadaZinc = (cotizacionZinc>terminos.baseZincPlata)?cotizacionZinc-terminos.baseZincPlata:0
        def cotizacionBasadaPlomo = (cotizacionPlomo>terminos.basePlomoPlata)?cotizacionPlomo-terminos.basePlomoPlata:0
        def cotizacionBasadaCobre = (cotizacionCobre>terminos.baseCobre)?cotizacionCobre-terminos.baseCobre:0

        def cotizacionEscaladaZinc = cotizacionBasadaZinc*terminos.escaladorZincPlata
        def cotizacionEscaladaPlomo = cotizacionBasadaPlomo*terminos.escaladorPlomoPlata
        def cotizacionEscaladaCobre = cotizacionBasadaCobre*terminos.escaladorCobre

        def deduccionMaquilaFinalZinc = cotizacionEscaladaZinc + terminos.maquilaZincPlata
        def deduccionMaquilaFinalPlomo = cotizacionEscaladaPlomo + terminos.maquilaPlomoPlata
        def deduccionMaquilaFinalCobre = cotizacionEscaladaCobre + terminos.maquilaCobre
        def deduccionMaquilaFinal = deduccionMaquilaFinalZinc + deduccionMaquilaFinalPlomo + deduccionMaquilaFinalCobre

        def deduccionRefinacionOnzaPlataZincPlata = leyPagablePlata*terminos.deduccionRefinacionOnzaZincPlata
        def deduccionRefinacionOnzaPlataPlomoPlata = leyPagablePlata*terminos.deduccionRefinacionOnzaPlomoPlata
        def deduccionRefinacionOnzaPlataCobrePlata = leyPagablePlata*terminos.deduccionRefinacionOnzaCobrePlata
        def deduccionRefinacionOnzaPlataFinal = deduccionRefinacionOnzaPlataZincPlata + deduccionRefinacionOnzaPlataPlomoPlata + deduccionRefinacionOnzaPlataCobrePlata

        def deduccionRefinacionLibraZinc = leyPagableZinc*2204.6223*terminos.deduccionRefinacionLibraZinc/100
        def deduccionRefinacionLibraPlomo = leyPagablePlomo*2204.6223*terminos.deduccionRefinacionLibraPlomo/100
        def deduccionRefinacionLibraCobre = leyPagableCobre*2204.6223*terminos.deduccionRefinacionLibraCobre/100
        def deduccionRefinacionLibraFinal = deduccionRefinacionLibraZinc + deduccionRefinacionLibraPlomo + deduccionRefinacionLibraCobre

        def penalidadCastigableArsenico = terminos.porcentajeArsenico - terminos.arsenicoLibre
        penalidadCastigableArsenico = (penalidadCastigableArsenico<0)?0:penalidadCastigableArsenico
        def penalidadCastigableAntimonio = terminos.porcentajeAntimonio - terminos.antimonioLibre
        penalidadCastigableAntimonio = (penalidadCastigableAntimonio<0)?0:penalidadCastigableAntimonio
        def penalidadCastigableBismuto = terminos.porcentajeBismuto - terminos.bismutoLibre
        penalidadCastigableBismuto = (penalidadCastigableBismuto<0)?0:penalidadCastigableBismuto
        def penalidadCastigableEstano = terminos.porcentajeEstano - terminos.estanoLibre
        penalidadCastigableEstano = (penalidadCastigableEstano<0)?0:penalidadCastigableEstano
        def penalidadCastigableHierro = terminos.porcentajeHierro - terminos.hierroLibre
        penalidadCastigableHierro = (penalidadCastigableHierro<0)?0:penalidadCastigableHierro
        def penalidadCastigableSilice = terminos.porcentajeSilice - terminos.siliceLibre
        penalidadCastigableSilice = (penalidadCastigableSilice<0)?0:penalidadCastigableSilice
        def penalidadCastigableZinc = terminos.porcentajeZinc - terminos.zincLibre
        penalidadCastigableZinc = (penalidadCastigableZinc<0)?0:penalidadCastigableZinc

        def penalidadCastigableArsenicoFinal = penalidadCastigableArsenico*terminos.costoUnitarioArsenico/((terminos.porcentajeUnitarioArsenico<=0)?1:terminos.porcentajeUnitarioArsenico)
        def penalidadCastigableAntimonioFinal = penalidadCastigableAntimonio*terminos.costoUnitarioAntimonio/((terminos.porcentajeUnitarioAntimonio<=0)?1:terminos.porcentajeUnitarioAntimonio)
        def penalidadCastigableBismutoFinal = penalidadCastigableBismuto*terminos.costoUnitarioBismuto/((terminos.porcentajeUnitarioBismuto<=0)?1:terminos.porcentajeUnitarioBismuto)
        def penalidadCastigableEstanoFinal = penalidadCastigableEstano*terminos.costoUnitarioEstano/((terminos.porcentajeUnitarioEstano<=0)?1:terminos.porcentajeUnitarioEstano)
        def penalidadCastigableHierroFinal = penalidadCastigableHierro*terminos.costoUnitarioHierro/((terminos.porcentajeUnitarioHierro<=0)?1:terminos.porcentajeUnitarioHierro)
        def penalidadCastigableSiliceFinal = penalidadCastigableSilice*terminos.costoUnitarioSilice/((terminos.porcentajeUnitarioSilice<=0)?1:terminos.porcentajeUnitarioSilice)
        def penalidadCastigableZincFinal = penalidadCastigableZinc*terminos.costoUnitarioZinc/((terminos.porcentajeUnitarioZinc<=0)?1:terminos.porcentajeUnitarioZinc)
        def penalidadCastigableFinal = penalidadCastigableArsenicoFinal + penalidadCastigableAntimonioFinal + penalidadCastigableBismutoFinal + penalidadCastigableEstanoFinal + penalidadCastigableHierroFinal + penalidadCastigableSiliceFinal +penalidadCastigableZincFinal

        def valorTonelada = valorBruto - deduccionMaquilaFinal - deduccionRefinacionOnzaPlataFinal- deduccionRefinacionLibraFinal- penalidadCastigableFinal- terminos.transporteInterno- terminos.laboratorio- terminos.molienda- terminos.manipuleo- terminos.margenAdministrativo- terminos.transporteAPuerto- terminos.rollBack

        System.out.println("cotizacionZinc: ${cotizacionZinc}")
        System.out.println("cotizacionPlomo:${cotizacionPlomo}")
        System.out.println("cotizacionPlata:${cotizacionPlata}")
        System.out.println("cotizacionCobre:${cotizacionCobre}")

        System.out.println("leyPagableZinc: ${leyPagableZinc}")
        System.out.println("leyPagablePlomo:${leyPagablePlomo}")
        System.out.println("leyPagablePlata:${leyPagablePlata}")
        System.out.println("leyPagableCobre:${leyPagableCobre}")

        System.out.println("leyFinalPagableZinc: ${leyFinalPagableZinc}")
        System.out.println("leyFinalPagablePlomo:${leyFinalPagablePlomo}")
        System.out.println("leyFinalPagablePlata:${leyFinalPagablePlata}")
        System.out.println("leyFinalPagableCobre:${leyFinalPagableCobre}")

        System.out.println("valorBrutoZinc: ${valorBrutoZinc}")
        System.out.println("valorBrutoPlomo:${valorBrutoPlomo}")
        System.out.println("valorBrutoPlata:${valorBrutoPlata}")
        System.out.println("valorBrutoCobre:${valorBrutoCobre}")
        System.out.println("valorBruto:${valorBruto}")

        System.out.println("cotizacionBasadaZinc: ${cotizacionBasadaZinc}")
        System.out.println("cotizacionBasadaPlomo:${cotizacionBasadaPlomo}")
        System.out.println("cotizacionBasadaCobre:${cotizacionBasadaCobre}")

        System.out.println("cotizacionEscaladaZinc: ${cotizacionEscaladaZinc}")
        System.out.println("cotizacionEscaladaPlomo:${cotizacionEscaladaPlomo}")
        System.out.println("cotizacionEscaladaCobre:${cotizacionEscaladaCobre}")

        System.out.println("deduccionMaquilaFinalZinc: ${deduccionMaquilaFinalZinc}")
        System.out.println("deduccionMaquilaFinalPlomo: ${deduccionMaquilaFinalPlomo}")
        System.out.println("deduccionMaquilaFinalCobre: ${deduccionMaquilaFinalCobre}")
        System.out.println("deduccionMaquilaFinal: ${deduccionMaquilaFinal}")

        System.out.println("deduccionRefinacionOnzaPlataZincPlata: ${deduccionRefinacionOnzaPlataZincPlata}")
        System.out.println("deduccionRefinacionOnzaPlataPlomoPlata: ${deduccionRefinacionOnzaPlataPlomoPlata}")
        System.out.println("deduccionRefinacionOnzaPlataCobrePlata: ${deduccionRefinacionOnzaPlataCobrePlata}")
        System.out.println("deduccionRefinacionOnzaPlataFinal: ${deduccionRefinacionOnzaPlataFinal}")

        System.out.println("deduccionRefinacionLibraZinc: ${deduccionRefinacionLibraZinc}")
        System.out.println("deduccionRefinacionLibraPlomo: ${deduccionRefinacionLibraPlomo}")
        System.out.println("deduccionRefinacionLibraCobre: ${deduccionRefinacionLibraCobre}")
        System.out.println("deduccionRefinacionLibraFinal: ${deduccionRefinacionLibraFinal}")

        System.out.println("penalidadCastigableArsenico: ${penalidadCastigableArsenico}")
        System.out.println("penalidadCastigableAntimonio: ${penalidadCastigableAntimonio}")
        System.out.println("penalidadCastigableBismuto: ${penalidadCastigableBismuto}")
        System.out.println("penalidadCastigableEstano: ${penalidadCastigableEstano}")
        System.out.println("penalidadCastigableHierro: ${penalidadCastigableHierro}")
        System.out.println("penalidadCastigableSilice: ${penalidadCastigableSilice}")
        System.out.println("penalidadCastigableZinc: ${penalidadCastigableZinc}")

        System.out.println("penalidadCastigableArsenicoFinal: ${penalidadCastigableArsenicoFinal}")
        System.out.println("penalidadCastigableAntimonioFinal: ${penalidadCastigableAntimonioFinal}")
        System.out.println("penalidadCastigableBismutoFinal: ${penalidadCastigableBismutoFinal}")
        System.out.println("penalidadCastigableEstanoFinal: ${penalidadCastigableEstanoFinal}")
        System.out.println("penalidadCastigableHierroFinal: ${penalidadCastigableHierroFinal}")
        System.out.println("penalidadCastigableSiliceFinal: ${penalidadCastigableSiliceFinal}")
        System.out.println("penalidadCastigableZincFinal: ${penalidadCastigableZincFinal}")
        System.out.println("penalidadCastigableFinal: ${penalidadCastigableFinal}")

        System.out.println("valorTonelada: ${valorTonelada}")

        return valorTonelada
    }

    def crearReporte = {
        def realPath = org.socymet.util.ReportesRuntime.realPath("/reports/images/")
        params.realPath = realPath+"/"
        params.SUBREPORT_DIR = "${org.socymet.util.ReportesRuntime.realPath('/reports')}/"
        chain(controller:'jasper',action:'index',params:params)
    }
}
