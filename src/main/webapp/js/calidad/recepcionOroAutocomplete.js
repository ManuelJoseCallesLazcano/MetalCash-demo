$(document).ready(function() {
    $("#lote").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/recepcionDeOro/recepcionesCalidadOroJSON", // /demo-liquidaciones/recepcionDeOro/create
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
            $('#recepcionDeOro\\.id').val(ui.item.recepcionId),
            $('#nombreCliente').val(ui.item.nombreCliente),
            $('#empresa\\.id').val(ui.item.empresaId);
            $('#nombreEmpresa').val(ui.item.nombreEmpresa);
            $('#fechaDeRecepcion').val(ui.item.fechaDeRecepcion);
            $('#cantidadDeSacos').val(ui.item.cantidadDeSacos);
            $('#pesoBruto').val(ui.item.pesoBruto);
            $('#estadoDelLote').val(ui.item.estadoDelLote);//condicionDeEntrega
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("El Lote no existe.");
            }
        }
    });
});



