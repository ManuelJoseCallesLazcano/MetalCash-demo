$(document).ready(function() {
        // CREACION DE TABLA DE RETENCIONES UTILIZANDO COMPONENTE jqGrid
        jQuery("#tablaRetenciones").jqGrid({
            datatype: "local",
            height: 200,
            colNames: ["CODIGO","DESCRIPCION","TIPO","CANTIDAD","UNIDAD","MONTO","ASIGNACION"],
            colModel:[
                {name:'CODIGO',index:'CODIGO', width:60},
                {name:'DESCRIPCION',index:'DESCRIPCION', width:200},
                {name:'TIPO',index:'TIPO', width:80},
                {name:'CANTIDAD',index:'CANTIDAD', width:80},
                {name:'UNIDAD',index:'UNIDAD', width:80},
                {name:'MONTO',index:'MONTO', width:80},
                {name:'ASIGNACION',index:'ASIGNACION', width:80} ],
            multiselect: false,
            caption: "RETENCIONES"
        });

        var mydata = $("#retenciones").val();
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);

        for(var i=0;i<=mydata.length;i++)
            jQuery("#tablaRetenciones").jqGrid('addRowData',i+1,mydata[i]);
        //BINDING ENTRE COMPONENTES Y FUNCIONES PARA REALIZAR CALCULOS
        $("#humedad, #porcentajeWolfran").bind("keyup",calcularKilosSecosWolfran);
        $("#humedad, #porcentajeWolfran").bind("keyup",calcularKilosFinosWolfran);
        $("#humedad, #porcentajeWolfran").bind("keyup",calcularLibrasFinasWolfran);
        $("#humedad, #porcentajeWolfran").bind("keyup",calcularValorBrutoWolfran);
        $("#humedad, #porcentajeWolfran").bind("keyup",calcularRegaliaMinera);
        $("#humedad, #porcentajeWolfran").bind("keyup",generarTablaRetencionesWolfran);
        $("#humedad, #porcentajeWolfran").bind("keyup",calcularValorPorTonelada);
        $("#humedad, #porcentajeWolfran").bind("keyup",calcularValorNetoMineral);
        $("#humedad, #porcentajeWolfran").bind("keyup",calcularTotalLiquidoPagable);
        $("#tablaCotizacionWolfran").bind("change",calcularValorPorTonelada).bind("keyup",generarTablaRetencionesWolfran);
        $("#valorPorToneladaManual").bind("keyup",calcularValorNetoMineral).bind("keyup",generarTablaRetencionesWolfran);
        $("#puntoDeBajada").bind("keyup",calcularValorPorTonelada).bind("keyup",generarTablaRetencionesWolfran);
        $("#radio1, #radio2").bind("click",controlRadioButtons);
        $("#porcentajeRegalia").bind("keyup",calcularRegaliaMineraConPorcentaje).bind("keyup",generarTablaRetencionesWolfran);
        $("#totalAnticiposContraEntrega, #totalAnticiposContraFuturaEntrega").bind("keyup",calcularTotalLiquidoPagable);
    }
);

/******* VARIABLES GLOBALES *******/
//var kilosNetosHumedos;
var kilosNetosSecos;
var kilosFinosWolfran;
var librasFinasDeWolfran;
var valorOficialBruto;
var valorNetoMineral;
var valorNetoMineralEnBolivianos;
var valorDeCompra;
var regaliaMinera;
var totalLiquidoPagable;
var totalPagado;


function controlRadioButtons(){
    var value = $("input[name=RadioGroup1]:checked").val();
    if(value=="1"){
        $("#valorPorToneladaManual").removeAttr("disabled");
        $("#puntoDeBajada").attr("disabled",true).val("");
    }
    if(value=="2"){
        $("#puntoDeBajada").removeAttr("disabled");
        $("#valorPorToneladaManual").attr("disabled",true).val("");
    }
}

function calcularKilosNetosHumedosWolfran(){
    var pesoBruto = parseFloat($("#pesoBruto").val());
    var tara = parseFloat($("#tara").val());
    var cantidadDeSacos = parseFloat($("#cantidadDeSacos").val());
    //calculos
    //alert("Peso bruto: "+pesoBruto+" tara: "+tara+"cantidad sacos: "+cantidadDeSacos);
    var kilosNetosHumedos = pesoBruto - cantidadDeSacos*tara;
    $("#kilosNetosHumedos").val((isNaN(kilosNetosHumedos)) ?"?":toFixed(kilosNetosHumedos,2).toString());
}

function calcularKilosSecosWolfran(){
    var kilosNetosHumedos = parseFloat($("#kilosNetosHumedos").val());
    var humedad = parseFloat($("#humedad").val());
    kilosNetosSecos = kilosNetosHumedos - kilosNetosHumedos*humedad/100;
    $("#kilosNetosSecos").val((isNaN(kilosNetosSecos)) ?"?":toFixed(kilosNetosSecos,2).toString());
}

function calcularKilosFinosWolfran(){
    //var kilosNetosSecos = parseFloat($("#kilosNetosSecos").val());
    var porcentajeWolfran = parseFloat($("#porcentajeWolfran").val());
    //calculos
    kilosFinosWolfran = kilosNetosSecos*porcentajeWolfran/100;
    $("#kilosFinosWolfran").val((isNaN(kilosFinosWolfran)) ?"?":toFixed(kilosFinosWolfran,2).toString());
}

function calcularLibrasFinasWolfran(){
    var kilosFinosWolfran = parseFloat($("#kilosFinosWolfran").val());
    //calculos
    librasFinasDeWolfran = kilosFinosWolfran*2.2046223;
    $("#librasFinasDeWolfran").val((isNaN(librasFinasDeWolfran)) ?"?":toFixed(librasFinasDeWolfran,2).toString());
}

function calcularValorBrutoWolfran(){
    var librasFinasDeWolfran = parseFloat($("#librasFinasDeWolfran").val());
    var cotizacionQuincenalDeWolfran = parseFloat($("#cotizacionQuincenalDeWolfran").val());
    var tipoDeCambioComercial = parseFloat($("#tipoDeCambioComercial").val());
    //calculos
    valorOficialBruto = kilosFinosWolfran/10.16*cotizacionQuincenalDeWolfran;
    $("#valorOficialBruto").val((isNaN(valorOficialBruto)) ?"?":toFixed(valorOficialBruto,2).toString());
}

function calcularValorPorTonelada(){
    var puntoDeBajada = parseFloat($("#puntoDeBajada").val());
    if(isNaN(puntoDeBajada))
        puntoDeBajada = 0;
    var leyWolfran = parseFloat($("#porcentajeWolfran").val()) - puntoDeBajada;

    $.ajax({
        url:"/demo-liquidaciones/tablaCotizacionWolfran/getValorPorTonelada",
        dataType: 'json',
        data: {
            tablaCotizacionWolfran: $('#tablaCotizacionWolfran').val(),
            porcentajeWolfran: leyWolfran
        },
        success: function(data) {
            //toFixed(parseFloat(""+data.vpt),2).toString();
            var valorPorTonelada = parseFloat(""+data.vpt);
            $('#valorPorTonelada').val(toFixed(valorPorTonelada,2).toString());
            $("#valorPorToneladaManual").val("");
            calcularValorNetoMineral();
        },
        error: function(request, status, error) {

        }
    });

}

function calcularValorNetoMineral(){
    var valorPorToneladaManual = parseFloat($("#valorPorToneladaManual").val());
    var valorPorTonelada = parseFloat($("#valorPorTonelada").val());
    var valorPorToneladaFinal = 0;

    if(isNaN(valorPorToneladaManual)){
        valorPorToneladaFinal = valorPorTonelada;
        $("#valorPorToneladaManual").val("");
    }else{
        valorPorToneladaFinal = valorPorToneladaManual;
        $('#tablaCotizacionWolfran').val('0');
        $("#valorPorTonelada").val(valorPorToneladaFinal);
    }
    //alert("valor tonelada: "+valorPorToneladaFinal);
    var kilosNetosSecos = parseFloat($("#kilosNetosSecos").val());
    var tipoDeCambioComercial = parseFloat($("#tipoDeCambioComercial").val());
    valorNetoMineral = kilosNetosSecos*valorPorToneladaFinal/1000;
    valorNetoMineralEnBolivianos = valorNetoMineral*tipoDeCambioComercial;

    $("#valorNetoMineral").val((isNaN(valorNetoMineral)) ?"?":toFixed(valorNetoMineral,2).toString());
    $("#valorNetoMineralEnBolivianos").val((isNaN(valorNetoMineralEnBolivianos)) ?"?":toFixed(valorNetoMineralEnBolivianos,2).toString());

    calcularValorDeCompra();
}

function calcularValorDeCompra(){
    var valorNetoMineralEnBolivianos = parseFloat($("#valorNetoMineralEnBolivianos").val());
    var bonoCalidad = parseFloat($("#bonoCalidad").val());
    var bonoIncentivo = parseFloat($("#bonoIncentivo").val());
    //calculos
    valorDeCompra = valorNetoMineralEnBolivianos+bonoCalidad+bonoIncentivo;
    $("#valorDeCompra").val((isNaN(valorDeCompra)) ?"?":toFixed(valorDeCompra,2).toString());

    generarTablaRetencionesWolfran();
}

function calcularRegaliaMineraConPorcentaje(){
    //var valorOficialBruto = parseFloat($("#valorOficialBruto").val());
    var valorNetoMineralEnBolivianos = transFloat($("#valorNetoMineralEnBolivianos").val());
    var porcentajeRegalia = parseFloat($("#porcentajeRegalia").val());
    //calculos
    regaliaMinera = valorNetoMineralEnBolivianos*porcentajeRegalia/100;
    if(isNaN(regaliaMinera)){
        calcularRegaliaMinera();
    }else
        $("#regaliaMinera").val(toFixed(regaliaMinera,2).toString());
    //$("#regaliaMinera").val((isNaN(regaliaMinera)) ?"?":toFixed(regaliaMinera,2).toString());
    //refrescar tabla de retenciones
    //generar();
}

function calcularRegaliaMinera(){
    var valorOficialBruto = parseFloat($("#valorOficialBruto").val());
    var alicuotaDeWolfran = parseFloat($("#alicuotaDeWolfran").val());
    var tipoDeCambioOficial = transFloat($("#tipoDeCambioOficial").val());
    //calculos
    regaliaMinera = valorOficialBruto*tipoDeCambioOficial*alicuotaDeWolfran/100;
    $("#regaliaMinera").val((isNaN(regaliaMinera)) ?"?":toFixed(regaliaMinera,2).toString());
}

function calcularTotalLiquidoPagable(){
    var totalPagado = parseFloat($("#totalPagado").val());
    var totalAnticiposContraEntrega = parseFloat($("#totalAnticiposContraEntrega").val());
    var totalAnticiposContraFuturaEntrega = parseFloat($("#totalAnticiposContraFuturaEntrega").val());
    //calculos
    totalLiquidoPagable = totalPagado-totalAnticiposContraEntrega-totalAnticiposContraFuturaEntrega;
    $("#totalLiquidoPagable").val((isNaN(totalLiquidoPagable)) ?"?":toFixed(totalLiquidoPagable,2).toString());
}

function generarTablaRetencionesWolfran(){
    var retencionesJSON = jQuery.parseJSON($("#retenciones").val());
    var valorOficialBruto = parseFloat($("#valorOficialBruto").val());
    var valorDeCompra = parseFloat($("#valorDeCompra").val());
    var cantidadDeSacos = parseFloat($("#cantidadDeSacos").val());
    var regaliaMinera = parseFloat($("#regaliaMinera").val());
    var totalRetenciones = regaliaMinera;

    regaliaMinera = (isNaN(regaliaMinera))?0:regaliaMinera;

    var tabla = new Array();
    $.each(retencionesJSON,function() {
        var monto = 0;
        if(this.DESCRIPCION=="REGALIA MINERA")
            monto = regaliaMinera;
        else{
            var descuento = parseFloat(this.CANTIDAD);
            var unidad = this.UNIDAD;
            var asignacion = this.ASIGNACION;
            if(unidad=="%"&&asignacion=="VBV")
                monto = valorOficialBruto*descuento/100;
            if(unidad=="%"&&asignacion=="VNV")
                monto = valorDeCompra*descuento/100;
            if(unidad=="Bs"&&asignacion=="SACO")
                monto = cantidadDeSacos*descuento;
            if(unidad=="Bs"&&asignacion=="FIJO")
                monto = descuento;
            monto=isNaN(monto)?0:toFixed(monto,2)
            //alert("descuento = "+this.CANTIDAD+" por "+this.DESCRIPCION+" monto = "+this.MONTO);
            totalRetenciones+=monto;
        }
        var fila = new Object();
        fila.CODIGO = this.CODIGO;
        fila.DESCRIPCION = this.DESCRIPCION;
        fila.TIPO = this.TIPO;
        fila.CANTIDAD = this.CANTIDAD;
        fila.UNIDAD = this.UNIDAD;
        fila.MONTO = monto;
        fila.ASIGNACION = this.ASIGNACION;

        tabla.push(fila);
        $("#retenciones").val(JSON.stringify(tabla));
    });

    $("#tablaRetenciones").jqGrid("clearGridData", true);
    for(var i=0;i<=tabla.length;i++)
        jQuery("#tablaRetenciones").jqGrid('addRowData',i+1,tabla[i]);

    totalPagado = valorDeCompra - totalRetenciones;
    //$("#retenciones").val(JSON.stringify(retencionesJSON));
    $("#totalRetenciones").val((isNaN(totalRetenciones)) ?"?":toFixed(totalRetenciones,2).toString());
    $("#totalPagado").val((isNaN(totalPagado)) ?"?":toFixed(totalPagado,2).toString());

    calcularTotalLiquidoPagable();
}

function eliminarRetencionWolfran(){
    var filaId = parseInt(jQuery('#tablaRetenciones').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
    //alert("fila: "+filaId);
    var mydata = $.parseJSON($("#retenciones").val());
    var nuevoMydata = new Array()

    for(var i=0;i<mydata.length;i++){
        if(filaId!=i) // copiar todas las filas menos la seleccionada
            nuevoMydata.push(mydata[i]);
    }
    //actualizar la cadena JSON en el campo de texto
    $("#retenciones").val(JSON.stringify(nuevoMydata));
    //alert("Nuevas retenciones JSON: "+JSON.stringify(nuevoMydata));
    generarTablaRetencionesWolfran();
}

function toFixed( number, precision ) {
    var multiplier = Math.pow( 10, precision );
    return Math.round( number * multiplier ) / multiplier;
}

function transFloat(numeroString){
    var numero = numeroString.replace(',','');
    return parseFloat(numero);
}
