$(document).ready(function() {
    $("#cliente").bind("change", recuperarEstadoCuenta);

    recuperarEstadoCuenta()

    function recuperarEstadoCuenta(){
        $.ajax({
            url: "/demo-liquidaciones/estadoDeCuenta/ultimoEstadoCuenta",
            data: {
                clienteId: $("#cliente").val()
            },
            success: function(data){
                $('#mensaje').html(data.mensaje==="-"?"":data.mensaje);
                $('#saldoActual, #saldoPorPagar').val(data.saldoActual)
            },
            error: function(){
            }
        });
    }
});
