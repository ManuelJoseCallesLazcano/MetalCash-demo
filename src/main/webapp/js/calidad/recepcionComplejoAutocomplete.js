$(document).ready(function() {
    $(".chosen-select").chosen({search_contains: true});

    function recuperar(){
        $.ajax({
            url: "/demo-liquidaciones/controlCalidadComplejo/recepcionCalidadComplejoJSON",
            dataType: 'json',
            data: {
                recepcionId: $('#recepcionDeComplejo').val()
            },
            success: function(data) {
                $('#nombreCliente').val(data.nombreCliente);
                $('#empresa\\.id').val(data.empresaId);
                $('#nombreEmpresa').val(data.nombreEmpresa);
                $('#fechaDeRecepcion').val(data.fechaDeRecepcion);
                $('#cantidadDeSacos').val(data.cantidadDeSacos);
                $('#pesoBruto').val(data.pesoBruto);
                $('#estadoDelLote').val(data.estadoDelLote);//condicionDeEntrega
                $('#condicionDeEntrega').val(data.condicionDeEntrega);
                desplegarMineralesPenalizables();
            },
            error: function(request, status, error) {
            }
        });
    }

    recuperar();

    $("#recepcionDeComplejo").bind("change",recuperar);

    $("#modoValoracion" ).change(function() {
        desplegarCondiciones();
    });

    function desplegarMineralesPenalizables(){
        var condicionDeEntrega=$("#condicionDeEntrega").val();
        if(condicionDeEntrega=="SPOT"){
            $("#_tituloPenalizables").hide();
            $("#_porcentajeArsenico").hide();
            $("#_porcentajeAntimonio").hide();
            $("#_porcentajeSilice").hide();
            $("#_porcentajeBismuto").hide();
            $("#_porcentajeEstano").hide();
            $("#_porcentajeZinc").hide();

            $("#porcentajeArsenico").val(0);
            $("#porcentajeAntimonio").val(0);
            $("#porcentajeSilice").val(0);
            $("#porcentajeBismuto").val(0);
            $("#porcentajeEstano").val(0);
            $("#porcentajeZinc").val(0);
        }else{
            $("#_tituloPenalizables").show();
            $("#_porcentajeArsenico").show();
            $("#_porcentajeAntimonio").show();
            $("#_porcentajeSilice").show();
            $("#_porcentajeBismuto").show();
            $("#_porcentajeEstano").show();
            $("#_porcentajeZinc").show();

            $("#porcentajeArsenico").val("");
            $("#porcentajeAntimonio").val("");
            $("#porcentajeSilice").val("");
            $("#porcentajeBismuto").val("");
            $("#porcentajeEstano").val("");
            $("#porcentajeZinc").val("");
        }
    }

    function desplegarCondiciones(){
        var modo = $("#modoValoracion").val();
        if(modo=="TABLA"){
            $("#_tablaComplejo").show();
            $("#_terminosDeContrato").hide();
        }else{
            $("#_tablaComplejo").hide();
            $("#_terminosDeContrato").show();
        }
    }
});