$(document).ready(function() {
    filtrar();

    function filtrar(){
        $.ajax({
            url: "/demo-liquidaciones/pagoTransporte/getEmpresasSegunUsuario",
            //data: "id=" + $("#empresa").val(),
            cache: false,
            success: function(html) {
                $("#empresa").html(html);
            }
        });
    }
});
