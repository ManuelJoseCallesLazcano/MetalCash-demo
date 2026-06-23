$(document).ready(function() {
    $("#lote").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/bajaDeRecepcionDePlomoPlata/recepcionesJSON", // /demo-liquidaciones/recepcionDePlomoPlata/create
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
            $('#recepcionId').val(ui.item.recepcionId),
                $('#nombreCliente').val(ui.item.nombreCliente),
                $('#nombreEmpresa').val(ui.item.nombreEmpresa),
                $('#fechaDeRecepcion').val(ui.item.fechaDeRecepcion)
            $('#pesoBruto').val(ui.item.pesoBruto)
            /*mapaRecepcion.put("gastoPorAnticipo", getTotalAnticiposContraEntrega(recepcion.id))
             mapaRecepcion.put("gastoPorAnalisis", recepcion.totalCostoLaboratorio)*/
            $('#gastoPorAnticipo').val(ui.item.gastoPorAnticipo)
            $('#gastoPorAnalisis').val(ui.item.gastoPorAnalisis)
            //$.jGrowl("Seleccionado: "+$("#empresa\\.id").val());
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("No existen resultados para el Lote buscado.");
            }
        }
    });

    $("#loteDestino").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/bajaDeRecepcionDePlomoPlata/recepcionesJSON", // /demo-liquidaciones/recepcionDePlomoPlata/create
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
            $('#recepcionDestinoId').val(ui.item.recepcionId)
            /*$('#nombreCliente').val(ui.item.nombreCliente),
             $('#nombreEmpresa').val(ui.item.nombreEmpresa),
             $('#fechaDeRecepcion').val(ui.item.fechaDeRecepcion)
             $('#pesoBruto').val(ui.item.pesoBruto)
             $('#gastoPorAnticipo').val(ui.item.gastoPorAnticipo)
             $('#gastoPorAnalisis').val(ui.item.gastoPorAnalisis)*/
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("No existen resultados para el Lote buscado.");
            }
        }
    });
});





