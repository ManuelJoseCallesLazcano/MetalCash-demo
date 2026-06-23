$(document).ready(function() {
    $("#lote").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/liquidacionDeComplejo/liquidacionesJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
            $('#liquidacionId').val(ui.item.liquidacionId),
            $('#nombreCliente').val(ui.item.nombreCliente),
            $('#nombreEmpresa').val(ui.item.nombreEmpresa);
            $('#fechaDeRecepcion').val(ui.item.fechaDeRecepcion);
            $('#fechaDeLiquidacion').val(ui.item.fechaDeLiquidacion);
            $('#kilosNetosSecos').val(ui.item.kilosNetosSecos);
            $('#porcentajeZinc').val(ui.item.porcentajeZinc);
            $('#porcentajePlomo').val(ui.item.porcentajePlomo);
            $('#porcentajePlata').val(ui.item.porcentajePlata);
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("El Lote no existe o aun no fue liquidado.");
            }
        }
    });
});



