$(document).ready(function() {
    $(".chosen-select").chosen({search_contains: true});
    $("#buscar").button();

    desplegarFiltro();

    $("#modoBusqueda").change(function() {
        desplegarFiltro();
    });

    function desplegarFiltro() {
        var modoBusqueda = $("#modoBusqueda").val();
        switch (modoBusqueda) {
            case "-TODOS-":
                $("#_clienteId").hide();
                $("#_empresaId").hide();
                break;
            case "CLIENTE":
                $("#_clienteId").show();
                $("#_empresaId").hide();
                break;
            case "EMPRESA":
                $("#_clienteId").hide();
                $("#_empresaId").show();
                break;
        }
    }
});
