$(document).ready(function() {
    $("#placaAutomovil").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/automovil/automovilesJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
            $("#automovil\\.id").val(ui.item.automovilId); // actualizando campo oculto para id de client
            $("#caracteristicasAutomovil").val(ui.item.modelo+" "+ui.item.color);
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("No existen resultados para la PLACA buscada.");
            }
        }
    });
});
