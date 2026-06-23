$(document).ready(function() {
        $("#gastoPorChanqueo, #gastoPorManipuleo, #gastoPorAnalisis, #gastoPorAnticipo, #gastoPorTransporte, #otrosGastos").bind("keyup",calcularTotalDeGastos);
        $("#tipoDeBaja").bind("change",activarDesactivarCampos);
        $('#tipoDeBaja').trigger('change');
    }
);

function activarDesactivarCampos(){
    //$("#gastoPorChanqueo, #gastoPorManipuleo, #gastoPorAnalisis, #gastoPorAnticipo, #gastoPorTransporte, #otrosGastos").val("");
    $("#gastoPorChanqueo, #gastoPorManipuleo, #gastoPorTransporte, #otrosGastos").val("");
    if($('#tipoDeBaja').find(":selected").text()=="Baja por retiro"){
        $("#loteDestino").prop('disabled', true);
        $("#loteDestino").css({'background-color' : '#ddd'});

        $("#recepcionDestinoId").prop('disabled', true);
        $("#recepcionDestinoId").css({'background-color' : '#ddd'});

        $("#gastoPorChanqueo").prop('disabled', false);
        $("#gastoPorChanqueo").css({'background-color' : '#fff'});

        $("#gastoPorManipuleo").prop('disabled', false);
        $("#gastoPorManipuleo").css({'background-color' : '#fff'});

        /*$("#gastoPorAnalisis").prop('disabled', false);
        $("#gastoPorAnalisis").css({'background-color' : '#fff'});

        $("#gastoPorAnticipo").prop('disabled', false);
        $("#gastoPorAnticipo").css({'background-color' : '#fff'});*/

        $("#gastoPorTransporte").prop('disabled', false);
        $("#gastoPorTransporte").css({'background-color' : '#fff'});

        $("#otrosGastos").prop('disabled', false);
        $("#otrosGastos").css({'background-color' : '#fff'});
    }
    if($('#tipoDeBaja').find(":selected").text()=="Baja por transferencia"){
        $("#loteDestino").prop('disabled', false);
        $("#loteDestino").css({'background-color' : '#fff'});

        $("#recepcionDestinoId").prop('disabled', false);
        $("#recepcionDestinoId").css({'background-color' : '#fff'});

        $("#gastoPorChanqueo").prop('disabled', true);
        $("#gastoPorChanqueo").css({'background-color' : '#ddd'});
        //$("#gastoPorChanqueo").val("");

        $("#gastoPorManipuleo").prop('disabled', true);
        $("#gastoPorManipuleo").css({'background-color' : '#ddd'});
        //$("#gastoPorManipuleo").val("");

        /*$("#gastoPorAnalisis").prop('disabled', false);
        $("#gastoPorAnalisis").css({'background-color' : '#fff'});

        $("#gastoPorAnticipo").prop('disabled', false);
        $("#gastoPorAnticipo").css({'background-color' : '#fff'});*/

        $("#gastoPorTransporte").prop('disabled', true);
        $("#gastoPorTransporte").css({'background-color' : '#ddd'});
        //$("#gastoPorTransporte").val("");

        $("#otrosGastos").prop('disabled', true);
        $("#otrosGastos").css({'background-color' : '#ddd'});
        //$("#otrosGastos").val("");
        calcularTotalDeGastos();
    }
}

function calcularTotalDeGastos(){
    var gastoPorChanqueo = parseFloat($("#gastoPorChanqueo").val());
    gastoPorChanqueo = isNaN(gastoPorChanqueo)?0:gastoPorChanqueo;
    var gastoPorManipuleo = parseFloat($("#gastoPorManipuleo").val());
    gastoPorManipuleo = isNaN(gastoPorManipuleo)?0:gastoPorManipuleo;
    var gastoPorAnalisis = parseFloat($("#gastoPorAnalisis").val());
    gastoPorAnalisis = isNaN(gastoPorAnalisis)?0:gastoPorAnalisis;
    var gastoPorAnticipo = parseFloat($("#gastoPorAnticipo").val());
    gastoPorAnticipo = isNaN(gastoPorAnticipo)?0:gastoPorAnticipo;
    var gastoPorTransporte = parseFloat($("#gastoPorTransporte").val());
    gastoPorTransporte = isNaN(gastoPorTransporte)?0:gastoPorTransporte;
    var otrosGastos = parseFloat($("#otrosGastos").val());
    otrosGastos = isNaN(otrosGastos)?0:otrosGastos;
    //calculos
    var totalDeGastos = gastoPorChanqueo+gastoPorManipuleo+gastoPorAnalisis+gastoPorAnticipo+gastoPorTransporte+otrosGastos;
    $("#totalDeGastos").val((isNaN(totalDeGastos)) ?"?":toFixed(totalDeGastos,2).toString());
}


function toFixed( number, precision ) {
    var multiplier = Math.pow( 10, precision );
    return Math.round( number * multiplier ) / multiplier;
}
