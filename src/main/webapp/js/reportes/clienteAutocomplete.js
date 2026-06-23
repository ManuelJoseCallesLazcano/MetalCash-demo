$(document).ready(function() {
    // BUSQUEDA DE CLIENTES POR CI
    $("#ciCliente").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/cliente/clientesJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
            $("#cliente\\.id").val(ui.item.clienteId); // actualizando campo oculto para id de cliente
            $("#empresa\\.id").val(ui.item.empresaId); // actualizando campo oculto para id de empresa
            $("#nombreCliente").val(ui.item.nombreCliente);
            $("#nombreEmpresa").val(ui.item.nombreEmpresa);
            //$.jGrowl("Seleccionado: "+$("#empresa\\.id").val());
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("No existen resultados para el CI buscado.");
            }
        }
    });

    // BUSQUEDA DE CLIENTES POR NOMBRE
    $("#nombreCliente").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/cliente/clientesPorNombreJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
            $("#cliente\\.id").val(ui.item.clienteId); // actualizando campo oculto para id de cliente
            $("#empresa\\.id").val(ui.item.empresaId); // actualizando campo oculto para id de empresa
            $("#ciCliente").val(ui.item.ciCliente);
            $("#nombreEmpresa").val(ui.item.nombreEmpresa);
            //$.jGrowl("Seleccionado: "+$("#empresa\\.id").val());
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("No existen resultados para el NOMBRE buscado.");
            }
        }
    });
});

