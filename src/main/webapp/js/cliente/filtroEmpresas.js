$(document).ready(function() {
    filtrar();

    function filtrar(){
        $.ajax({
            url: "/demo-liquidaciones/cliente/getEmpresasSegunUsuario",
            //data: "id=" + $("#empresa").val(),
            cache: false,
            success: function(html) {
                $("#empresa").html(html);
            }
        });
    }
});
