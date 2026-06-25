package org.socymet.recepcion

import org.smart.parametros.GestionMinera
import org.socymet.cotizaciones.CotizacionDeDolar
import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Chofer
import org.socymet.proveedor.EmpresaSeccion
import org.socymet.seguridad.SecUser

import java.text.DecimalFormat
import java.text.SimpleDateFormat

class RecepcionDeComplejo extends Recepcion {
    static auditable = true

    static searchable = true

    Date gestionMinera

    Integer numeroRecepcion //e.g.: GENERADO: 1, PARA MOSTRAR: CM000001
    Integer loteComplejo //e.g.: GENERADO: 1, PARA MOSTRAR: CM000001
    Integer lotePlomoPlata //e.g.: GENERADO: 1, PARA MOSTRAR: CM000001
    Integer loteZincPlata //e.g.: GENERADO: 1, PARA MOSTRAR: CM000001
    Integer loteCobrePlata //e.g.: GENERADO: 1, PARA MOSTRAR: CM000001
    Integer codigoDepositoComplejo
    String codigoLote="-"

    Integer cantidadSacos = 0   // cantidad de sacos (numérico); sincroniza el String legado cantidadDeSacos

    EmpresaSeccion empresaSeccion

    String ciChofer
    String nombreChofer
    String placa

    String condicionDeEntrega
    String tipoDeMineral
    String naturalezaMineral

    BigDecimal pesoNeto = 0 //para proceso caracteristico de Miner's House para pago de transporte

    String estadoAnalisis
    String estadoDelLote

    String nombreComposito="-"

    //costos de laboratorio
    String detalleLaboratorio1 = "-"
    BigDecimal costoLaboratorio1 = 0
    String detalleLaboratorio2
    BigDecimal costoLaboratorio2
    String detalleLaboratorio3
    BigDecimal costoLaboratorio3
    String detalleLaboratorio4
    BigDecimal costoLaboratorio4
    BigDecimal totalCostoLaboratorio = 0

    transient springSecurityService

    static constraints = {
        deposito nullable: false
        gestionMinera()
        numeroRecepcion(nullable: true)
        loteComplejo(nullable: true)
        lotePlomoPlata(nullable: true)
        loteZincPlata(nullable: true)
        loteCobrePlata(nullable: true)
        codigoDepositoComplejo(nullable: true)
        codigoLote()
        empresaSeccion(nullable: true)
        fechaDeRecepcion(nullable: false, validator: { val ->
            if (val != null && val > new Date()) return 'fechaFutura'
        })
        cliente(nullable: false)
        empresa(nullable: false)
        chofer(nullable: false)
        automovil(nullable: false)
        ciChofer()
        nombreChofer()
        placa()
        //informacion de cantidades y otros
//        tipoDeMineral inList: ["COMPLEJO","PB-AG","ZN-AG"], nullable: false //cmn
        condicionDeEntrega inList: ["SPOT","TERM-CON"], nullable: false
        tipoDeMineral inList: ["ZN PB AG"], nullable: false
        naturalezaMineral inList: ["SULFURO","OXIDO"], nullable: false, blank: false
        numeroDeDocumento blank: false
        documentacionCompleta nullable: false

        tipoDeMaterial inList: ["BROZA","CONCENTRADO"]
        cantidadDeSacos(blank: false)
        cantidadSacos(nullable: true)
        pesoNeto(nullable: false, min: 0.01G)
        pesoTara(nullable: false, min: 0.0G, validator: { val, obj ->
            if (val != null && obj.pesoNeto != null && val >= obj.pesoNeto) return 'taraExcedeBruto'
        })
        pesoBruto(nullable: false, min: 0.01G)
        costoDeTransporte(nullable: false, min: 0.0G)
        costoManipuleo nullable: false
        anticipoAutorizado min: 0.0, nullable: false
        estadoAnalisis(inList: ["CON ANALISIS","SIN ANALISIS"])
        estadoDelLote(inList: ["NO LIQUIDADO","LIQUIDADO","Baja"],blank: false,nullable: false)
        observaciones blank: false, nullable: false
        //referencias que no tienen que ser mostradas en vistas
        estadoAnticipo blank:  true, nullable: true
        transportePagado(blank: true, nullable: true)
        manipuleoPagado(blank: true, nullable: true)
        usuario(nullable: true)

        cotizacionDeDolar(nullable: true)
        alicuota(nullable: false)
        cotizacionDiariaDeMinerales(nullable: false)
        cotizacionQuincenalDeMinerales(nullable: false)

        nombreComposito()

        //costos de laboratorio
        detalleLaboratorio1(blank:false, nullable: false)
        costoLaboratorio1(blank:false, min: 0.0, nullable: false)
        detalleLaboratorio2(blank:true, nullable: true)
        costoLaboratorio2(blank:true, min: 0.0, nullable: true)
        detalleLaboratorio3(blank:true, nullable: true)
        costoLaboratorio3(blank:true, min: 0.0, nullable: true)
        detalleLaboratorio4(blank:true, nullable: true)
        costoLaboratorio4(blank:true, min: 0.0, nullable: true)
        totalCostoLaboratorio(blank:false, min: 0.0, nullable: false)
    }

    def beforeValidate = {
        // Peso Bruto Húmedo (neto) = Peso Bruto (carga + envase) − Tara; el servidor es la fuente de verdad
        if (this.pesoNeto != null && this.pesoTara != null) {
            this.pesoBruto = (this.pesoNeto - this.pesoTara).max(0.0G)
        }
        // Mantener el String legado cantidadDeSacos en sync con el numérico cantidadSacos
        if (this.cantidadSacos != null) this.cantidadDeSacos = this.cantidadSacos.toString()
//        log.error("********** VALIDATING!!! ***********")
//        if(this.ciChofer==null || this.nombreChofer==null){
//            def cho1 = Chofer.get(this.chofer.id)
//            this.ciChofer=cho1.ci
//            this.nombreChofer=cho1.nombre
//        }else{
//            if(!Chofer.findByCiAndNombre(this.ciChofer,this.nombreChofer)){
//                def cho = new Chofer(
//                        ci: this.ciChofer,
//                        nombre: this.nombreChofer,
//                        telefono: "0",
//                        celular: "0"
//                )
//                cho.save(failOnError: true)
//                this.chofer = cho
//            }
//        }
//
//        if(this.placa==null){
//            def auto1 = Automovil.get(this.automovil.id)
//            this.placa = auto1.placa
//        }else{
//            if(!Automovil.findByPlaca(this.placa)){
//                def auto = new Automovil(
//                        placa: this.placa?this.placa:UUID.randomUUID(),
//                        modelo: "-",
//                        color: "-"
//                )
//                auto.save(failOnError: true)
//                this.automovil = auto
//            }
//        }
    }

    def beforeInsert = {
        this.gestionMinera = gestionMineraActiva()
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser

        def maxNumeroRecepcion = RecepcionDeComplejo.createCriteria().get {
            projections {
                max('numeroRecepcion')
            }
        }
        this.numeroRecepcion = (maxNumeroRecepcion ?: 0) + 1

        this.loteComplejo = siguienteLoteComplejo(deposito, empresa, this.gestionMinera)
        this.codigoLote = formatearCodigoLote(deposito, empresa, this.loteComplejo, this.gestionMinera)
        this.codigoDepositoComplejo = RecepcionDeComplejo.countByDeposito(this.deposito) + 1

        this.estadoAnticipo = "SIN ANTICIPO"
        this.transportePagado = "NO"
        this.manipuleoPagado = "NO"
        this.estadoAnalisis = "SIN ANALISIS"

        this.usuario = usuarioActual
        this.cotizacionDeDolar = CotizacionDeDolar.findByActivo(1)
    }

    static afterInsert = {
    }

    /** Gestión minera actualmente activa. */
    static Date gestionMineraActiva() {
        GestionMinera.findByEstado("ACTIVO").gestion
    }

    /** Siguiente número de lote de complejo para el depósito/empresa/gestión dados. */
    static Integer siguienteLoteComplejo(deposito, empresa, Date gestionMinera) {
        def results = RecepcionDeComplejo.createCriteria().list {
            like("deposito", deposito)
            like("empresa", empresa)
            eq("gestionMinera", gestionMinera)
            projections {
                max('loteComplejo')
            }
        }
        (results.get(0) ?: 0) + 1
    }

    /** Código de lote mostrado al usuario (lote de turno) y persistido en codigoLote. */
    static String formatearCodigoLote(deposito, empresa, Integer loteComplejo, Date gestionMinera) {
        def decimalFormat = new DecimalFormat("000")
        "${deposito?.codigoDeposito}-${empresa?.codigoEmpresa}${decimalFormat.format(loteComplejo)}/${new SimpleDateFormat('yy').format(gestionMinera)}"
    }

    String toString(){
        def decimalFormat = new DecimalFormat("000")
        if (loteComplejo)
            return formatearCodigoLote(deposito, empresa, loteComplejo, gestionMinera)
        if (lotePlomoPlata)
            return "${deposito?.codigoDeposito}-PBAG${decimalFormat.format(lotePlomoPlata)}"
        if (loteZincPlata)
            return "${deposito?.codigoDeposito}-ZNAG${decimalFormat.format(loteZincPlata)}"
        if (loteCobrePlata)
            return "${deposito?.codigoDeposito}-CUAG${decimalFormat.format(loteCobrePlata)}"

        return ""
    }
}
