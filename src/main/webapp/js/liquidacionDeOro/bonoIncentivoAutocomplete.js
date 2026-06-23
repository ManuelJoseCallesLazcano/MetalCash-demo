$(document).ready(function() {
    $('#porcentajePlata').keyup(function() {
        $.ajax({
            url:"/demo-liquidaciones/bonoIncentivo/bonoIncentivoJSON",
            dataType: 'json',
            data: {
                empresaId: $('#empresaId').val(),
                kilosNetosSecos: $('#kilosNetosSecos').val(),
                porcentajePlata: $('#porcentajePlata').val()
            },
            success: function(data) {
                $('#bonoIncentivo').val(data.bono)
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





