package org.socymet.recepcion

import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.utilidades.NumeroALiteral

class BajaDeRecepcionDeAntimonio {
    String lote
    //DATOS DE LA RECEPCION (DUPLICANDO INFORMACION PARA FACILITAR LA GENERACION DE REPORTES)
    Integer recepcionId
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    BigDecimal pesoBruto
    //DATOS DE LA BAJA DEL LOTE
    Date fechaDeBaja
    String motivoDeBaja
    String tipoDeBaja
    String loteDestino
    Integer recepcionDestinoId
    BigDecimal gastoPorChanqueo
    BigDecimal gastoPorManipuleo
    BigDecimal gastoPorAnalisis
    BigDecimal gastoPorAnticipo
    BigDecimal gastoPorTransporte
    BigDecimal otrosGastos
    BigDecimal totalDeGastos
    String literal
    String observaciones

    transient springSecurityService

    static constraints = {
        lote(blank: false)
        recepcionId(blank: false)
        nombreCliente(blank: false)
        nombreEmpresa(blank: false)
        fechaDeRecepcion(blank: false)
        pesoBruto(blank: false, min: 0.0)
        fechaDeBaja nullable: false
        motivoDeBaja inList: ["Ley baja","Proveedor desconforme por su ley","Proveedor desconforme con el precio del mineral"], blank: false
        tipoDeBaja inList: ["Baja por retiro", "Baja por transferencia"], blank: false
        loteDestino nullable: true
        recepcionDestinoId nullable: true, min: 0
        gastoPorChanqueo nullable: true, min: 0.0
        gastoPorManipuleo nullable: true, min: 0.0
        gastoPorAnalisis nullable: true, min: 0.0
        gastoPorAnticipo nullable: true, min: 0.0
        gastoPorTransporte nullable: true, min: 0.0
        otrosGastos nullable: true, min: 0.0
        totalDeGastos nullable: false, min: 0.0
        literal display:false,nullable: true
        observaciones blank: true
    }

    def beforeInsert = {
        fechaDeBaja = new java.util.Date()
        def conversor = new NumeroALiteral()
        literal=conversor.Convertir(totalDeGastos.toString(),true)
    }

    def afterInsert = {
        //actualizar el estado del anterior lote recepcionado
        def recepcionAnterior = RecepcionDeAntimonio.get(recepcionId)
        recepcionAnterior.estadoDelLote = "Baja"
        recepcionAnterior.save()
        //eliminar los anticipos vinculados al lote recepcionado
        def anticipoContraEntregas = AnticipoContraEntrega.findAllByRecepcionId(recepcionId)
        anticipoContraEntregas.each {
            it.delete()
        }

        if(tipoDeBaja.equals("Baja por transferencia")){
            def conversor=new NumeroALiteral()
            //actualizar el lote al que se transfiere para adicionar los gastos por analisis y anticipos
            def nuevaRecepcion = RecepcionDeAntimonio.get(recepcionDestinoId)
            nuevaRecepcion.detalleLaboratorio1 = "Costo de laboratorio transferido del lote ${lote}"
            nuevaRecepcion.costoLaboratorio1 = gastoPorAnalisis
            nuevaRecepcion.totalCostoLaboratorio = gastoPorAnalisis
            nuevaRecepcion.save()
            def nuevoAnticipo = new AnticipoContraEntrega(lote: loteDestino,
                    recepcionId: recepcionDestinoId,
                    nombreCliente: nombreCliente,
                    nombreEmpresa: nombreEmpresa,
                    fechaDeRecepcion: new java.text.SimpleDateFormat('dd/MM/yyyy').format(nuevaRecepcion.fechaDeRecepcion),
                    pesoBruto: nuevaRecepcion.pesoBruto,
                    fechaDeAnticipo: fechaDeBaja,
                    importe: totalDeGastos,
                    importeLiteral:conversor.Convertir(totalDeGastos.toString(),true),
                    observaciones: "Total de anticipos transferidos del lote ${lote}",
                    usuario: springSecurityService.getCurrentUser())
            nuevoAnticipo.save(failOnError: true)
        }
    }
}
