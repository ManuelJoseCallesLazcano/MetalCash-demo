$(document).ready(function() {
    $('#porcentajeWolfran').keyup(function() {
        $.ajax({
            url:"/demo-liquidaciones/bonoCalidad/bonoCalidadWolfranJSON",
            dataType: 'json',
            data: {
                empresaId: $('#empresaId').val(),
                porcentajeWolfran: $('#porcentajeWolfran').val()
            },
            success: function(data) {
                var kilosNetosSecos = transFloat($("#kilosNetosSecos").val());
                var bono = transFloat(""+data.bono);
                var bonoCalidad = kilosNetosSecos*bono/1000;
                $('#bonoCalidad').val(toFixed(bonoCalidad,2));
            },
            error: function(request, status, error) {

            },
            response: function(event, ui) {
                if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                    $.jGrowl("No existen bonificacion para la Ley de Wolfran ingresada.");
                }
            }
        });
    });
});





