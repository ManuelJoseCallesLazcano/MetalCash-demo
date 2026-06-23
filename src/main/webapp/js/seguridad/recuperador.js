$(document).ready(function() {
    $.ajax({
        url:"/demo-liquidaciones/miCuenta/misDatos",
        dataType: 'json',
        data: {
            // no es necesario enviar datos
        },
        success: function(data) {
            $('#nombre').val(data.nombre),
            $('#cuenta').val(data.cuenta)
        },
        error: function(request, status, error) {

        }
    });

    $("#actualizar").bind("click",actualizar);

    function actualizar(){
        var cuenta = $('#cuenta').val();
        var contrasena = $('#contrasena').val();
        var confirmarContrasena = $('#confirmarContrasena').val();

        if(cuenta==""){
            $.notify("El campo Cuenta no puede estar vacio.",{autoHide: false,clickToHide: true,className: "error"});
            return;
        }

        if(contrasena==""){
            $.notify("El campo Contrasena no puede estar vacio.",{autoHide: false,clickToHide: true,className: "error"});
            return;
        }

        if(confirmarContrasena!=contrasena){
            $.notify("Las contraseñas no coinciden.",{autoHide: false,clickToHide: true,className: "error"});
            return;
        }

        $.ajax({
            url: "/demo-liquidaciones/miCuenta/actualizar",
            dataType: 'json',
            data: {
                cuenta: $('#cuenta').val(),
                contrasena: $('#contrasena').val()
            },
            success: function(data) {
//                $('#numeroMesesAcumulados').val(data.numeroMesesAcumulados);
                //notificacionCancelados
                $.notify("Su información ha sido actualizada!",{autoHide: false,clickToHide: true,className: "info"});
            },
            error: function(request, status, error) {

            }
        });
    }
});
