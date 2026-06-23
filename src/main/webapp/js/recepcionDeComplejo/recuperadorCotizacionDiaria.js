$(document).ready(function() {
    $("#fechaDeRecepcion_day, #fechaDeRecepcion_month, #fechaDeRecepcion_year").bind("change", recuperarCotizacionDiaria);

    recuperarCotizacionDiaria()

    function recuperarCotizacionDiaria(){
        $.ajax({
            url: "/demo-liquidaciones/cotizacionDiariaDeMinerales/obtenerCotizaciones",
            data: {
                day: $("#fechaDeRecepcion_day").val(),
                month: $("#fechaDeRecepcion_month").val(),
                year: $("#fechaDeRecepcion_year").val()
            },
            success: function(data){
                $('#cotizacionDiariaDeMinerales').val(data.cotizacionDiariaId);
                $('#cotizacionQuincenalDeMinerales').val(data.cotizacionQuincenalId);
                $('#alicuota').val(data.alicuotaId);

                $('#hayCotizacionDiaria').html(data.hayCotizacionDiaria===1?"":"Cotizaci&oacute;n no encontrada. Utilizando &uacute;ltima registrada.");
                $('#hayCotizacionQuincenal').html(data.hayCotizacionQuincenal===1?"":"Cotizaci&oacute;n no encontrada. Utilizando &uacute;ltima registrada.");
                $('#hayAlicuota').html(data.hayAlicuota===1?"":"Al&iacute;cuota no encontrada. Utilizando &uacute;ltima registrada.");
            },
            error: function(){
            }
        });
    }
});
