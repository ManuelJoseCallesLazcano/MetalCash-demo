$(document).ready(function() {
    filtrar();

    function filtrar(){
        $.ajax({
            url: "/demo-liquidaciones/pagoDeRetenciones/getEmpresasSegunUsuario",
            //data: "id=" + $("#empresa").val(),
            cache: false,
            success: function(html) {
                $("#empresa").html(html);
            }
        });
    }
});
