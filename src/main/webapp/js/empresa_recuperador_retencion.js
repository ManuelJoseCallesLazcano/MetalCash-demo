$(document).ready(function() {
    recuperarRetencion()

    $('#lista_retencion').change(function() {
        recuperarRetencion();
    });

    function recuperarRetencion(){
        $.ajax({
            url:"/demo-liquidaciones/retencion/mostrar",
            dataType: 'json',
            data: {
                retencionId: $('#lista_retencion').val()
            },
            success: function(data) {
                /*descripcion = retencion.descripcion
                 tipoDeRetencion = retencion.tipoDeRetencion
                 cantidadDescuento = retencion.cantidadDescuento
                 unidadDeDescuento = retencion.unidadDeDescuento
                 asignacionDelDescuento = retencion.asignacionDelDescuento*/
                $('#descripcion').val(data.descripcion),
                    $('#tipoDeRetencion').val(data.tipoDeRetencion),
                    $('#cantidad').val(data.cantidadDescuento),
                    $('#unidadDeDescuento').val(data.unidadDeDescuento),
                    $('#asignacion').val(data.asignacionDelDescuento)
            },
            error: function(request, status, error) {

            }
        });
    }
});
