$(document).ready(function() {
    $(".chosen-select").chosen({search_contains: true});

    $( "#importe" ).keyup(function() {
        var numero = parseInt($( "#importe" ).val());
        let importe = parseFloat($( "#importe" ).val())
        let saldoActual = parseFloat($('#saldoActual').val())
        let saldoPorPagar = saldoActual - importe
        $('#saldoPorPagar').val(isNaN(saldoPorPagar)?"?":saldoPorPagar)

        if(numero>=1000&&numero<2000)
            $( "#importeLiteral" ).val("UN "+CifrasEnLetras.convertirNumeroEnLetras(numero).toUpperCase()+" 00/100 BOLIVIANOS");
        else
            $( "#importeLiteral" ).val(CifrasEnLetras.convertirNumeroEnLetras(numero).toUpperCase()+" 00/100 BOLIVIANOS");
    });

    //BUSQUEDA DE CLIENTES POR NOMBRE
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
            $("#nombreEmpresa").val(ui.item.nombreEmpresa);
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("No existen resultados para el NOMBRE buscado.");
            }
        }
    });
});

