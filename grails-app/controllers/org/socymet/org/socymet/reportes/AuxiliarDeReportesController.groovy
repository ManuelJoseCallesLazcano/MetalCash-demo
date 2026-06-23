package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

@Transactional
class AuxiliarDeReportesController {

    def index() {}

    def recepcionesPagoTransporteFechasJSON () {
        def fechaInicial = params.fechaInicial.toString()
        def fechaFinal = params.fechaFinal.toString()
        //Date myDate = params.date('test', 'dd-MM-yyyy')
    }
}
