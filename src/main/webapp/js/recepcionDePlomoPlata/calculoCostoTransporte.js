$(document).ready(function() {
$("#pesoBruto, #cantidadDeSacos").bind("keyup",calcularCostoTransporte);
}
    );

function calcularCostoTransporte(){
    // DATOS RECUPERADOS DESDE LA BASE DE DATOS
    var costoTransportePlata = parseFloat($("#costoTransportePlata").val());
    var unidadMonetariaPlata = $("#unidadMonetariaPlata").val();
    var unidadDeCobroPlata = $("#unidadDeCobroPlata").val();
    var tipoDeCambioComercial = parseFloat($("#tipoDeCambioComercial").val());

    if(unidadMonetariaPlata == "Bs" && unidadDeCobroPlata == "TONELADA"){
        var pesoBruto = parseFloat($("#pesoBruto").val());
        var costoDeTransporte = pesoBruto*costoTransportePlata/1000;
        $("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString());
    }

    if(unidadMonetariaPlata == "Bs" && unidadDeCobroPlata == "SACO"){
        var cantidadDeSacos = parseFloat($("#cantidadDeSacos").val());
        var costoDeTransporte = cantidadDeSacos*costoTransportePlata;
        //$("#costoDeTransporte").val(costoDeTransporte.toString());
        $("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString());
    }

    if(unidadMonetariaPlata == "$us" && unidadDeCobroPlata == "TONELADA"){
        var pesoBruto = parseFloat($("#pesoBruto").val());
        var costoDeTransporte = tipoDeCambioComercial*pesoBruto*costoTransportePlata/1000;
        //$("#costoDeTransporte").val(costoDeTransporte.toString());
        $("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString());
    }

    if(unidadMonetariaPlata == "$us" && unidadDeCobroPlata == "SACO"){
        var cantidadDeSacos = parseFloat($("#cantidadDeSacos").val());
        var costoDeTransporte = tipoDeCambioComercial*cantidadDeSacos*costoTransportePlata;
        //$("#costoDeTransporte").val(costoDeTransporte.toString());
        $("#costoDeTransporte").val((isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString());
    }
}
