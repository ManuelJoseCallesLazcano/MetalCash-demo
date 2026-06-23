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
        /*$("#humedad").bind("keyup",calcularKilosSecosAntimonio);
        $("#humedad, #porcentajeAntimonio").bind("keyup",calcularKilosFinosAntimonio);
        $("#humedad, #porcentajeAntimonio").bind("keyup",calcularLibrasFinasAntimonio);
        $("#humedad, #porcentajeAntimonio").bind("keyup",calcularValorBrutoAntimonio);
        $("#humedad, #porcentajeAntimonio").bind("keyup",calcularRegaliaMinera);
        $("#humedad, #porcentajeAntimonio").bind("keyup",generarTablaRetencionesAntimonio);*/

        $("#humedad, #porcentajeAntimonio").bind("keyup",calcularKilosSecosAntimonio);
        $("#humedad, #porcentajeAntimonio").bind("keyup",calcularKilosFinosAntimonio);
        $("#humedad, #porcentajeAntimonio").bind("keyup",calcularLibrasFinasAntimonio);
        $("#humedad, #porcentajeAntimonio").bind("keyup",calcularValorBrutoAntimonio);
        $("#humedad, #porcentajeAntimonio").bind("keyup",calcularRegaliaMinera);
        $("#humedad, #porcentajeAntimonio").bind("keyup",generarTablaRetencionesAntimonio);
        $("#humedad, #porcentajeAntimonio").bind("keyup",calcularValorPorTonelada);
        $("#humedad, #porcentajeAntimonio").bind("keyup",calcularValorNetoMineral);
        $("#humedad, #porcentajeAntimonio").bind("keyup",calcularTotalLiquidoPagable);
        
        $("#tablaCotizacionAntimonio").bind("change",calcularValorPorTonelada).bind("keyup",generarTablaRetencionesAntimonio);
        $("#valorPorToneladaManual").bind("keyup",calcularValorNetoMineral).bind("keyup",generarTablaRetencionesAntimonio);
        $("#puntoDeBajada").bind("keyup",calcularValorPorTonelada).bind("keyup",generarTablaRetencionesAntimonio);
        $("#radio1, #radio2").bind("click",controlRadioButtons);
        $("#porcentajeRegalia").bind("keyup",calcularRegaliaMineraConPorcentaje).bind("keyup",generarTablaRetencionesAntimonio);
        $("#totalAnticiposContraEntrega, #totalAnticiposContraFuturaEntrega").bind("keyup",calcularTotalLiquidoPagable);
    }
);

/******* VARIABLES GLOBALES *******/
var kilosNetosHumedos = transFloat($("#kilosNetosHumedos").val());
var kilosNetosSecos = transFloat($("#kilosNetosSecos").val());
var kilosFinosAntimonio = transFloat($("#kilosFinosAntimonio").val());
var librasFinasDeAntimonio = transFloat($("#librasFinasDeAntimonio").val());
var valorOficialBruto = transFloat($("#valorOficialBruto").val());
var valorNetoMineral = transFloat($("#valorNetoMineral").val());
var valorNetoMineralEnBolivianos = transFloat($("#valorNetoMineralEnBolivianos").val());

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

function calcularKilosNetosHumedosAntimonio(){
    var pesoBruto = transFloat($("#pesoBruto").val());
    var tara = transFloat($("#tara").val());
    var cantidadDeSacos = transFloat($("#cantidadDeSacos").val());
    //calculos
    //alert("Peso bruto: "+pesoBruto+" tara: "+tara+"cantidad sacos: "+cantidadDeSacos);
    kilosNetosHumedos = pesoBruto - cantidadDeSacos*tara;
    $("#kilosNetosHumedos").val((isNaN(kilosNetosHumedos)) ?"?":toFixed(kilosNetosHumedos,2).toString());
}

function calcularKilosSecosAntimonio(){
    var kilosNetosHumedos = transFloat($("#kilosNetosHumedos").val());
    var humedad = transFloat($("#humedad").val());
    kilosNetosSecos = kilosNetosHumedos - kilosNetosHumedos*humedad/100;
    $("#kilosNetosSecos").val((isNaN(kilosNetosSecos)) ?"?":toFixed(kilosNetosSecos,2).toString());
}

function calcularKilosFinosAntimonio(){
    //var kilosNetosSecos = transFloat($("#kilosNetosSecos").val());
    var porcentajeAntimonio = transFloat($("#porcentajeAntimonio").val());
    //calculos
    kilosFinosAntimonio = kilosNetosSecos*porcentajeAntimonio/100;
    $("#kilosFinosAntimonio").val((isNaN(kilosFinosAntimonio)) ?"?":toFixed(kilosFinosAntimonio,2).toString());
}

function calcularLibrasFinasAntimonio(){
    var kilosFinosAntimonio = transFloat($("#kilosFinosAntimonio").val());
    //calculos
    librasFinasDeAntimonio = kilosFinosAntimonio*2.2046223;
    $("#librasFinasDeAntimonio").val((isNaN(librasFinasDeAntimonio)) ?"?":toFixed(librasFinasDeAntimonio,2).toString());
}

function calcularValorBrutoAntimonio(){
    //var librasFinasDeAntimonio = transFloat($("#librasFinasDeAntimonio").val());
    var cotizacionQuincenalDeAntimonio = transFloat($("#cotizacionQuincenalDeAntimonio").val());
    var tipoDeCambioComercial = transFloat($("#tipoDeCambioComercial").val());
    //calculos
    valorOficialBruto = kilosFinosAntimonio*cotizacionQuincenalDeAntimonio/1000;
    $("#valorOficialBruto").val((isNaN(valorOficialBruto)) ?"?":toFixed(valorOficialBruto,2).toString());
}

function calcularValorPorTonelada(){
    var puntoDeBajada = transFloat($("#puntoDeBajada").val());
    if(isNaN(puntoDeBajada))
        puntoDeBajada = 0;
    var leyAntimonio = transFloat($("#porcentajeAntimonio").val()) - puntoDeBajada;

    $.ajax({
        url:"/demo-liquidaciones/tablaCotizacionAntimonio/getValorPorTonelada",
        dataType: 'json',
        data: {
            tablaCotizacionAntimonio: $('#tablaCotizacionAntimonio').val(),
            porcentajeAntimonio: leyAntimonio
        },
        success: function(data) {
            //toFixed(transFloat(""+data.vpt),2).toString();
            var valorPorTonelada = transFloat(""+data.vpt);
            $('#valorPorTonelada').val(toFixed(valorPorTonelada,2).toString());
            $("#valorPorToneladaManual").val("");
            calcularValorNetoMineral();
        },
        error: function(request, status, error) {

        }
    });

}

function calcularValorNetoMineral(){
    var valorPorToneladaManual = transFloat($("#valorPorToneladaManual").val());
    var valorPorTonelada = transFloat($("#valorPorTonelada").val());
    var valorPorToneladaFinal = 0;

    if(isNaN(valorPorToneladaManual)){
        valorPorToneladaFinal = valorPorTonelada;
        $("#valorPorToneladaManual").val("");
    }else{
        valorPorToneladaFinal = valorPorToneladaManual;
        $('#tablaCotizacionAntimonio').val('0');
        $("#valorPorTonelada").val(valorPorToneladaFinal);
    }
    //alert("valor tonelada: "+valorPorToneladaFinal);
    var kilosNetosSecos = transFloat($("#kilosNetosSecos").val());
    var tipoDeCambioComercial = transFloat($("#tipoDeCambioComercial").val());
    valorNetoMineral = kilosNetosSecos*valorPorToneladaFinal/1000;
    valorNetoMineralEnBolivianos = valorNetoMineral*tipoDeCambioComercial;

    $("#valorNetoMineral").val((isNaN(valorNetoMineral)) ?"?":toFixed(valorNetoMineral,2).toString());
    $("#valorNetoMineralEnBolivianos").val((isNaN(valorNetoMineralEnBolivianos)) ?"?":toFixed(valorNetoMineralEnBolivianos,2).toString());

    calcularValorDeCompra();
}

function calcularValorDeCompra(){
    var valorNetoMineralEnBolivianos = transFloat($("#valorNetoMineralEnBolivianos").val());
    var bonoCalidad = transFloat($("#bonoCalidad").val());
    var bonoIncentivo = transFloat($("#bonoIncentivo").val());
    //calculos
    valorDeCompra = valorNetoMineralEnBolivianos+bonoCalidad+bonoIncentivo;
    $("#valorDeCompra").val((isNaN(valorDeCompra)) ?"?":toFixed(valorDeCompra,2).toString());

    generarTablaRetencionesAntimonio();
}

function calcularRegaliaMineraConPorcentaje(){
    //var valorOficialBruto = transFloat($("#valorOficialBruto").val());
    var valorNetoMineralEnBolivianos = transFloat($("#valorNetoMineralEnBolivianos").val());
    var porcentajeRegalia = transFloat($("#porcentajeRegalia").val());
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
    //var valorOficialBruto = transFloat($("#valorOficialBruto").val());
    var alicuotaDeAntimonio = transFloat($("#alicuotaDeAntimonio").val());
    /*REVISAR E INCLUIR EL TIPO DE CAMBIO OFICIAL EN EL CALCULO DE LA REGALIA MINERA*/
    var tipoDeCambioOficial = transFloat($("#tipoDeCambioOficial").val());
    //calculos
    var regaliaMinera = valorOficialBruto*tipoDeCambioOficial*alicuotaDeAntimonio/100;
    $("#regaliaMinera").val((isNaN(regaliaMinera)) ?"?":toFixed(regaliaMinera,2).toString());
}

function calcularTotalLiquidoPagable(){
    var totalPagado = transFloat($("#totalPagado").val());
    var totalAnticiposContraEntrega = transFloat($("#totalAnticiposContraEntrega").val());
    var totalAnticiposContraFuturaEntrega = transFloat($("#totalAnticiposContraFuturaEntrega").val());
    //calculos
    totalLiquidoPagable = totalPagado-totalAnticiposContraEntrega-totalAnticiposContraFuturaEntrega;
    $("#totalLiquidoPagable").val((isNaN(totalLiquidoPagable)) ?"?":toFixed(totalLiquidoPagable,2).toString());
}

function generarTablaRetencionesAntimonio(){
    var retencionesJSON = jQuery.parseJSON($("#retenciones").val());
    var valorOficialBruto = transFloat($("#valorOficialBruto").val());
    var valorDeCompra = transFloat($("#valorDeCompra").val());
    var cantidadDeSacos = transFloat($("#cantidadDeSacos").val());
    var regaliaMinera = transFloat($("#regaliaMinera").val());
    var totalRetenciones = regaliaMinera;

    regaliaMinera = (isNaN(regaliaMinera))?0:regaliaMinera;

    var tabla = new Array();
    $.each(retencionesJSON,function() {
        var monto = 0;
        if(this.DESCRIPCION=="REGALIA MINERA")
            monto = regaliaMinera;
        else{
            var descuento = transFloat(this.CANTIDAD);
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

function eliminarRetencionAntimonio(){
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
    generarTablaRetencionesAntimonio();
}

function toFixed( number, precision ) {
    var multiplier = Math.pow( 10, precision );
    return Math.round( number * multiplier ) / multiplier;
}

function transFloat(numeroString){
    var numero = numeroString.replace(',','');
    return parseFloat(numero);
}
