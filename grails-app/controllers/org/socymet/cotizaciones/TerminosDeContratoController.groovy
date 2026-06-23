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

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
//        params.max = Math.min(max ?: 10, 100)
        params.max = 1000

//        if(!params.sort){
//            def sortField = session.getAt('sortField')
//            if(!sortField){
//                sortField = "empresa"
//                session['sortField'] = "empresa"
//            }//end if no sort field in session
//            params.sort = sortField
//        }//end if no sort param
//        else
//            session['sortField'] = params.sort
//
//        if(!params.order){
//            def sortOrder = session['sortOrder']
//            if(!sortOrder){
//                sortOrder = "asc"
//                session['sortOrder'] = "asc"
//            }//end if no sort order field in session
//            params.order = sortOrder
//        }//end if no sort param
//        else
//            session['sortOrder']  = params.order

        params.each { name, value ->
            log.error("name: $name -> value: $value")
        }

        [terminosDeContratoInstanceList: TerminosDeContrato.list(params), terminosDeContratoInstanceTotal: TerminosDeContrato.count()]
//        [terminosDeContratoInstanceList: TerminosDeContrato.Estano(params,[sort: 'empresa']), terminosDeContratoInstanceTotal: TerminosDeContrato.count()]
//        [terminosDeContratoInstanceList: TerminosDeContrato.list(params).sort { a, b-> a.empresa.nombreDeEmpresa.compareTo(b.empresa.nombreDeEmpresa)}, terminosDeContratoInstanceTotal: TerminosDeContrato.count()]
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

        flash.message = message(code: 'default.created.message', args: [message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato'), terminosDeContratoInstance.id])
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

        flash.message = message(code: 'default.updated.message', args: [message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato'), terminosDeContratoInstance.id])
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
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath = realPath+"/"
        params.SUBREPORT_DIR = "${servletContext.getRealPath('/reports')}/"
        chain(controller:'jasper',action:'index',params:params)
    }
}
