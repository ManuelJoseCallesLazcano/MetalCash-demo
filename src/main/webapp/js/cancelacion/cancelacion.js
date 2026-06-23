$(document).ready(function() {
    $("#lote").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/cancelacion/recepcionesJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
            /*"label", elemento+liq
             "value", elemento+liq
             "liquidacionId", liqu
             "nombreCliente", liqu
             "nombreEmpresa", liqu
             "fechaDeRecepcion", l
             "fechaDeLiquidacion",
             "totalLiquidoPagable"*/
            $('#liquidacionId').val(ui.item.liquidacionId),
            $('#nombreCliente').val(ui.item.nombreCliente),
            $('#nombreEmpresa').val(ui.item.nombreEmpresa),
            $('#fechaDeRecepcion').val(ui.item.fechaDeRecepcion),
            $('#fechaDeLiquidacion').val(ui.item.fechaDeLiquidacion),
            $('#totalLiquidoPagable').val(ui.item.totalLiquidoPagable)
            //$.jGrowl("Seleccionado: "+$("#empresa\\.id").val());
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("El Lote no existe o ya fue cancelado.");
            }
        }
    });

    $( "#importe" ).keyup(function() {
        var numero = parseInt($( "#importe" ).val());
        if(numero>=1000&&numero<2000)
            $( "#importeLiteral" ).val("UN "+CifrasEnLetras.convertirNumeroEnLetras(numero).toUpperCase()+" 00/100 BOLIVIANOS");
        else
            $( "#importeLiteral" ).val(CifrasEnLetras.convertirNumeroEnLetras(numero).toUpperCase()+" 00/100 BOLIVIANOS");
    });
});

