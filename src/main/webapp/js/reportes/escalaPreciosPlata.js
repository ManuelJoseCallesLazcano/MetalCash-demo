$(document).ready(function() {
    cargarCotizacionDiariaPlata()

    $("#fechaCotizacion_day,#fechaCotizacion_month,#fechaCotizacion_year").change(function() {
        cargarCotizacionDiariaPlata()
    });

    function cargarCotizacionDiariaPlata(){
        $.ajax({
            url:"/demo-liquidaciones/cotizacionDiariaDeMinerales/cotizacionDiariaPlata",
            dataType: 'json',
            data: {
                fechaCotizacion_year: $('#fechaCotizacion_year').val(),
                fechaCotizacion_month: $('#fechaCotizacion_month').val(),
                fechaCotizacion_day: $('#fechaCotizacion_day').val()
            },
            success: function(data) {
                $('#cotizacionPlata').val(""+data.cotDiaPlata);
            },
            error: function(request, status, error) {

            }
        });
    }
});
