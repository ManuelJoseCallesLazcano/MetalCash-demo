$(document).ready(function() {
    $("#lote").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/buscadorLotes/localizadorLoteJSON", // /Liquidaciones/recepcionDeComplejo/create
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
            $('#recepcionId').val(ui.item.recepcionId);
            $('#controlCalidadId').val(ui.item.controlCalidadId);
            $('#liquidacionId').val(ui.item.liquidacionId);
            $('#anticipoId').val(ui.item.anticipoId);
            $('#pagoTransporteId').val(ui.item.pagoTransporteId);

            var pathname = window.location.pathname;
            pathname = pathname.substring(0,pathname.indexOf("bu"));

            var elem
            //recepcion
            elem= $('<a>', {
                href: pathname+ui.item.recepcionId,
                text: pathname+ui.item.recepcionId,
                target: "_blank"
            });
            //$('#_recepcionId').replaceWith(elem);
            $('#_recepcionId').html(elem);

            //control de calidad
            elem= $('<a>', {
                href: pathname+ui.item.controlCalidadId,
                text: pathname+ui.item.controlCalidadId,
                target: "_blank"
            });
            $('#_controlCalidadId').html(ui.item.controlCalidadId==0?"<p>NO EXISTE</p>":elem);

            //liquidacion
            elem= $('<a>', {
                href: pathname+ui.item.liquidacionId,
                text: pathname+ui.item.liquidacionId,
                target: "_blank"
            });
            $('#_liquidacionId').html(ui.item.liquidacionId==0?"<p>NO EXISTE</p>":elem);

            //anticipo
            elem= $('<a>', {
                href: pathname+ui.item.anticipoId,
                text: pathname+ui.item.anticipoId,
                target: "_blank"
            });
            $('#_anticipoId').html(ui.item.anticipoId==0?"<p>NO EXISTE</p>":elem);

            //pago de transporte
            elem= $('<a>', {
                href: pathname+ui.item.pagoTransporteId,
                text: pathname+ui.item.pagoTransporteId,
                target: "_blank"
            });
            $('#_pagoTransporteId').html(ui.item.pagoTransporteId==0?"<p>NO EXISTE</p>":elem);
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("El Lote no existe.");
            }
        }
    });
});




