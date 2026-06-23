$(document).ready(function() {
    $("#ciChofer").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/chofer/choferesJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
            $("#chofer\\.id").val(ui.item.choferId); // actualizando campo oculto para id de cliente
            $("#nombreChofer").val(ui.item.nombreChofer);
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("No existen resultados para el CI buscado.");
            }
        }
    });

    //BUSQUEDA DE CHOFERES POR NOMBRE
    $("#nombreChofer").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/chofer/choferesPorNombreJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
            $("#chofer\\.id").val(ui.item.choferId); // actualizando campo oculto para id de cliente
            $("#ciChofer").val(ui.item.ciChofer);
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("No existen resultados para el CI buscado.");
            }
        }
    });
});
