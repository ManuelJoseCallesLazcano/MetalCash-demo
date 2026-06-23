$(document).ready(function() {
//    $("#tipoDeMineral" ).change(function() {
//        desplegarCondiciones();
//    });
    calcularCostoTransporte();

    $("#tipoDeMaterial").bind("change",calcularCostoTransporte);
    $("#pesoBruto, #cantidadDeSacos").bind("keyup",calcularCostoTransporte);
});

function calcularCostoTransporte(){
    var tipoDeMaterial = $("#tipoDeMaterial").val();
    if(tipoDeMaterial=="CONCENTRADO"){
        calcularCostoTransporteConcentrados();
    }else{
        calcularCostoTransporteComplejos();
    }
}
function calcularCostoTransporteComplejos(){
    // DATOS RECUPERADOS DESDE LA BASE DE DATOS
    var costoTransporteComplejos = parseFloat($("#costoTransporteComplejos").val());
    var unidadMonetariaComplejos = $("#unidadMonetariaComplejos").val();
    var unidadDeCobroComplejos = $("#unidadDeCobroComplejos").val();
    var tipoDeCambioComercial = parseFloat($("#tipoDeCambioComercial").val());

    if(unidadMonetariaComplejos == "Bs" && unidadDeCobroComplejos == "TONELADA"){
        var pesoBruto = parseFloat($("#pesoBruto").val());
        var costoDeTransporte = pesoBruto*costoTransporteComplejos/1000;
        //$("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString());
        $("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":toFixed(costoDeTransporte,2));
    }

    if(unidadMonetariaComplejos == "Bs" && unidadDeCobroComplejos == "SACO"){
        var cantidadDeSacos = parseFloat($("#cantidadDeSacos").val());
        var costoDeTransporte = cantidadDeSacos*costoTransporteComplejos;
        //$("#costoDeTransporte").val(costoDeTransporte.toString());
        //$("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString());
        $("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":toFixed(costoDeTransporte,2));
    }

    if(unidadMonetariaComplejos == "$us" && unidadDeCobroComplejos == "TONELADA"){
        var pesoBruto = parseFloat($("#pesoBruto").val());
        var costoDeTransporte = tipoDeCambioComercial*pesoBruto*costoTransporteComplejos/1000;
        //$("#costoDeTransporte").val(costoDeTransporte.toString());
        //$("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString());
        $("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":toFixed(costoDeTransporte,2));
    }

    if(unidadMonetariaComplejos == "$us" && unidadDeCobroComplejos == "SACO"){
        var cantidadDeSacos = parseFloat($("#cantidadDeSacos").val());
        var costoDeTransporte = tipoDeCambioComercial*cantidadDeSacos*costoTransporteComplejos;
        //$("#costoDeTransporte").val(costoDeTransporte.toString());
        //$("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString());
        $("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":toFixed(costoDeTransporte,2));
    }
    //anulamos el calculo para establecer el valor durante la liquidacion
    //$("#costoDeTransporte").val(0);
}

function calcularCostoTransporteConcentrados(){
    // DATOS RECUPERADOS DESDE LA BASE DE DATOS
    var costoTransporteConcentrados = parseFloat($("#costoTransporteConcentrados").val());
    var unidadMonetariaConcentrados = $("#unidadMonetariaConcentrados").val();
    var unidadDeCobroConcentrados = $("#unidadDeCobroConcentrados").val();
    var tipoDeCambioComercial = parseFloat($("#tipoDeCambioComercial").val());

    if(unidadMonetariaConcentrados == "Bs" && unidadDeCobroConcentrados == "TONELADA"){
        var pesoBruto = parseFloat($("#pesoBruto").val());
        var costoDeTransporte = pesoBruto*costoTransporteConcentrados/1000;
        //$("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString());
        $("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":toFixed(costoDeTransporte,2));
    }

    if(unidadMonetariaConcentrados == "Bs" && unidadDeCobroConcentrados == "SACO"){
        var cantidadDeSacos = parseFloat($("#cantidadDeSacos").val());
        var costoDeTransporte = cantidadDeSacos*costoTransporteConcentrados;
        //$("#costoDeTransporte").val(costoDeTransporte.toString());
        //$("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString());
        $("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":toFixed(costoDeTransporte,2));
    }

    if(unidadMonetariaConcentrados == "$us" && unidadDeCobroConcentrados == "TONELADA"){
        var pesoBruto = parseFloat($("#pesoBruto").val());
        var costoDeTransporte = tipoDeCambioComercial*pesoBruto*costoTransporteConcentrados/1000;
        //$("#costoDeTransporte").val(costoDeTransporte.toString());
        //$("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString());
        $("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":toFixed(costoDeTransporte,2));
    }

    if(unidadMonetariaConcentrados == "$us" && unidadDeCobroConcentrados == "SACO"){
        var cantidadDeSacos = parseFloat($("#cantidadDeSacos").val());
        var costoDeTransporte = tipoDeCambioComercial*cantidadDeSacos*costoTransporteConcentrados;
        //$("#costoDeTransporte").val(costoDeTransporte.toString());
        //$("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString());
        $("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":toFixed(costoDeTransporte,2));
    }
}

function toFixed( number, precision ) {
    var multiplier = Math.pow( 10, precision );
    return Math.round( number * multiplier ) / multiplier;
}
