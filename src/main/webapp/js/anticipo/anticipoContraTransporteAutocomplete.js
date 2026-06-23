$(document).ready(function() {
    //Llamada ajax para cargar informacion de costo de transporte, unidad monetaria, etc. para EDITAR
    //la informacion de recepcion, en el controller esta el codigo para soportar errores cuando
    //se este invocando la llamada sobre informacion vacia
    /*$.ajax({
        url:"/demo-liquidaciones/empresa/datosTransporteComplejosJSON",
        dataType: 'json',
        data: {
            empresaId: $("#empresa\\.id").val()
        },
        success: function(data) {
            $('#costoTransporteComplejos').val(data.costoTransporteComplejos),
                $('#unidadMonetariaComplejos').val(data.unidadMonetariaComplejos),
                $('#unidadDeCobroComplejos').val(data.unidadDeCobroComplejos)
            $('#tipoDeCambioComercial').val(data.tipoDeCambioComercial)
        },
        error: function(request, status, error) {

        }
    });*/

    $("#lote").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/anticipoContraTransporte/recepcionesJSON", // /demo-liquidaciones/anticipoContraTransporte/recepcionesJSON
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
            $('#costoDeTransporte').val(ui.item.costoDeTransporte)
            //$.jGrowl("Seleccionado: "+$("#empresa\\.id").val());
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("El Lote no existe o ya fue liquidado.");
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

