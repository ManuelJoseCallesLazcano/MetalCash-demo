$(document).ready(function() {
    $("#lote").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/recepcionDeAntimonio/recepcionesJSON", // /demo-liquidaciones/recepcionDeAntimonio/create
                data: request,
                success: function(data){
                    response(data); // set the response
                },
                error: function(){
                }
            });
        },
        minLength: 1, // triggered only after minimum 1 characters have been entered.
        select: function(event, ui) { // event handler when user selects a company from the list.
            //recepcionDeAntimonio.id
            //$('#liquidacionId').val(ui.item.liquidacionId),
            $('#recepcionDeAntimonio\\.id').val(ui.item.recepcionId),
            $('#nombreCliente').val(ui.item.nombreCliente),
            $('#empresaId').val(ui.item.empresaId);
            $('#nombreEmpresa').val(ui.item.nombreEmpresa);
            $('#retenciones').val(ui.item.retenciones);
            $('#fechaDeRecepcion').val(ui.item.fechaDeRecepcion);
            $('#cantidadDeSacos').val(ui.item.cantidadDeSacos);
            $('#tara').val(ui.item.tara);
            $('#pesoBruto').val(ui.item.pesoBruto);
            $('#estadoDelLote').val(ui.item.estadoDelLote);
            $('#cotizacionDiariaDeAntimonio').val(ui.item.cotizacionDiariaDeAntimonio);
            $('#cotizacionQuincenalDeAntimonio').val(ui.item.cotizacionQuincenalDeAntimonio);
            $('#alicuotaDeAntimonio').val(ui.item.alicuotaDeAntimonio);            
            $('#tipoDeCambioOficial').val(ui.item.tipoDeCambioOficial);
            $('#tipoDeCambioComercial').val(ui.item.tipoDeCambioComercial);
            $('#detalleLaboratorio1').val(ui.item.detalleLaboratorio1);
            $('#costoLaboratorio1').val(ui.item.costoLaboratorio1);
            $('#detalleLaboratorio2').val(ui.item.detalleLaboratorio2);
            $('#costoLaboratorio2').val(ui.item.costoLaboratorio2);
            $('#detalleLaboratorio3').val(ui.item.detalleLaboratorio3);
            $('#costoLaboratorio3').val(ui.item.costoLaboratorio3);
            $('#detalleLaboratorio4').val(ui.item.detalleLaboratorio4);
            $('#costoLaboratorio4').val(ui.item.costoLaboratorio4);
            $('#totalCostoLaboratorio').val(ui.item.totalCostoLaboratorio);
            $('#totalAnticiposContraEntrega').val(ui.item.totalAnticiposContraEntrega);
            $('#totalAnticiposContraFuturaEntrega').val(ui.item.totalAnticiposContraFuturaEntrega);
            if(ui.item.totalAnticiposContraFuturaEntrega!="0"){
                $.jGrowl("El Cliente tiene una deuda pendiente por Anticipo Contra Futura Entrega de: Bs "+ui.item.totalAnticiposContraFuturaEntrega, {sticky: true, header: 'ATENCION'});
            }
            // INVOCAR A LAS FUNCIONES QUE DEBAN REALIZAR CALCULOS CON LOS DATOS RECUPERADOS
            calcularKilosNetosHumedosAntimonio();
            generarTablaRetencionesAntimonio();
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("El Lote no existe o ya fue liquidado.");
            }
        }
    });
});



