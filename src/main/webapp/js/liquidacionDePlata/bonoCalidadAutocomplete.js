$(document).ready(function() {
    $('#porcentajePlata').keyup(function() {
        $.ajax({
            url:"/demo-liquidaciones/bonoCalidad/bonoCalidadPlataJSON",
            dataType: 'json',
            data: {
                empresaId: $('#empresaId').val(),
                porcentajePlata: $('#porcentajePlata').val()
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
                    $.jGrowl("No existen bonificacion para la Ley de Plata ingresada.");
                }
            }
        });
    });
});





