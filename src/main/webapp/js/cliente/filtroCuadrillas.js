$(document).ready(function() {
    $('.chosen-select').chosen({
        width: '400px'
    });

    filtrar();

    $("#empresa").change(function() {
        filtrar();
    });

    function filtrar(){
        $.ajax({
            url: "/demo-liquidaciones/empresa/cuadrillasDeEmpresa",
            data: "id=" + $("#empresa").val(),
            cache: false,
            success: function(html) {
                $("#cuadrilla").html(html);
            }
        });
    }
});
