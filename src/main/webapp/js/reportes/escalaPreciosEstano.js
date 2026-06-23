$(document).ready(function() {
    cargarCotizacionDiariaEstano()

    $("#fechaCotizacion_day,#fechaCotizacion_month,#fechaCotizacion_year").change(function() {
        cargarCotizacionDiariaEstano()
    });

    function cargarCotizacionDiariaEstano(){
        $.ajax({
            url:"/demo-liquidaciones/cotizacionDiariaDeMinerales/cotizacionDiariaEstano",
            dataType: 'json',
            data: {
                fechaCotizacion_year: $('#fechaCotizacion_year').val(),
                fechaCotizacion_month: $('#fechaCotizacion_month').val(),
                fechaCotizacion_day: $('#fechaCotizacion_day').val()
            },
            success: function(data) {
                $('#cotizacionEstano').val(""+data.cotDiaEstano);
            },
            error: function(request, status, error) {

            }
        });
    }
});
