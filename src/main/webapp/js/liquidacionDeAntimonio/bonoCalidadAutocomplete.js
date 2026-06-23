$(document).ready(function() {
    $('#porcentajeAntimonio').keyup(function() {
        $.ajax({
            url:"/demo-liquidaciones/bonoCalidad/bonoCalidadAntimonioJSON",
            dataType: 'json',
            data: {
                empresaId: $('#empresaId').val(),
                porcentajeAntimonio: $('#porcentajeAntimonio').val()
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
                    $.jGrowl("No existen bonificacion para la Ley de Antimonio ingresada.");
                }
            }
        });
    });
});





