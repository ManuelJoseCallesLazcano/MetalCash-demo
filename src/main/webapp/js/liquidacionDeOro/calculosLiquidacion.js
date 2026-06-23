/******* VARIABLES GLOBALES *******/
//var kilosNetosHumedos;
var kilosNetosSecos;
var kilosFinosZinc;
var kilosFinosPlomo;
var kilosFinosPlata;
var librasFinasDeZinc;
var librasFinasDePlomo;
var onzasTroyDePlata;
var valorOficialBrutoDeZinc;
var valorOficialBrutoDeZincEnBolivianos;
var valorOficialBrutoDePlomo;
var valorOficialBrutoDePlomoEnBolivianos;
var valorOficialBrutoDePlata;
var valorOficialBrutoDePlataEnBolivianos;
var valorPorTonelada;
var valorNetoMineral;
var valorNetoMineralEnBolivianos;

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

//        $("#pesoBruto, #merma, #humedad,#kilosNetosSecos, #porcentajeZinc, #porcentajePlomo, #porcentajePlata").bind("keyup",calcularValorOficialBruto).bind("keyup",generarTablaRetencionesComplejo);
//        $("#pesoBruto, #merma, #humedad,#porcentajePlomo, #porcentajePlata, #porcentajeZinc, #dolarPuntoPlomo, #dolarPuntoPlata, #dolarPuntoZinc").bind("keyup",calcularValorPorTonelada).bind("keyup",generarTablaRetencionesComplejo);
//        $("#pesoBruto, #merma, #humedad,#porcentajePlomo, #porcentajePlata, #porcentajeZinc, #dolarPuntoPlomo, #dolarPuntoPlata, #dolarPuntoZinc, #porcentajeRegalia").bind("keyup",calcularValorNetoMineral).bind("keyup",generarTablaRetencionesComplejo);
//        $("#pesoBruto, #merma, #humedad,#porcentajePlomo, #porcentajePlata, #porcentajeZinc, #dolarPuntoPlomo, #dolarPuntoPlata, #dolarPuntoZinc").bind("keyup",calcularRegaliaMinera).bind("keyup",generarTablaRetencionesComplejo);
//        $("#pesoBruto, #merma, #humedad,#porcentajePlomo, #porcentajePlata, #porcentajeZinc, #dolarPuntoPlomo, #dolarPuntoPlata, #dolarPuntoZinc").bind("keyup",calcularValorDeCompra).bind("keyup",generarTablaRetencionesComplejo);
//        $("#porcentajeRegalia").bind("keyup",calcularRegaliaMineraConPorcentaje).bind("keyup",generarTablaRetencionesComplejo);
//        $("#porcentajePlomo, #porcentajePlata, #porcentajeZinc, #dolarPuntoPlomo, #dolarPuntoPlata, #dolarPuntoZinc, #porcentajeRegalia, #totalAnticiposContraFuturaEntrega").bind("keyup",calcularTotalLiquidoPagable.bind("keyup",generarTablaRetencionesComplejo));
//        $("#costoLaboratorio1, #costoLaboratorio2, #costoLaboratorio3, #costoLaboratorio4").bind("keyup",calcularTotalCostoLaboratorio);

        inicializarVariablesGlobales();
    }
);

function calcularValorOficialBruto(){
    //var valorOficialBrutoDeZincEnBolivianos = parseFloat($("#valorOficialBrutoDeZincEnBolivianos").val());
    //var valorOficialBrutoDePlomoEnBolivianos = parseFloat($("#valorOficialBrutoDePlomoEnBolivianos").val());
    //var valorOficialBrutoDePlataEnBolivianos = parseFloat($("#valorOficialBrutoDePlataEnBolivianos").val());
    //calculos
    var valorOficialBruto = valorOficialBrutoDeZincEnBolivianos + valorOficialBrutoDePlomoEnBolivianos + valorOficialBrutoDePlataEnBolivianos;
    $("#valorOficialBruto").val((isNaN(valorOficialBruto)) ?"?":toFixed(valorOficialBruto,2).toString());
}

function calcularValorPorTonelada(){
    var dolarPuntoZinc = parseFloat($("#dolarPuntoZinc").val());
    var porcentajeZinc = parseFloat($("#porcentajeZinc").val());
    var dolarPuntoPlomo = parseFloat($("#dolarPuntoPlomo").val());
    var porcentajePlomo = parseFloat($("#porcentajePlomo").val());
    var dolarPuntoPlata = parseFloat($("#dolarPuntoPlata").val());
    var porcentajePlata = parseFloat($("#porcentajePlata").val());
    //calculos
    //var valorPorTonelada = dolarPuntoZinc*porcentajeZinc + dolarPuntoPlomo*porcentajePlomo + dolarPuntoPlata*porcentajePlata;
    valorPorTonelada = dolarPuntoZinc*porcentajeZinc + dolarPuntoPlomo*porcentajePlomo + dolarPuntoPlata*porcentajePlata;
    $("#valorPorTonelada").val((isNaN(valorPorTonelada)) ?"?":toFixed(valorPorTonelada,2).toString());
}

function calcularValorNetoMineral(){
    //var kilosNetosSecos = parseFloat($("#kilosNetosSecos").val());
    //var valorPorTonelada = parseFloat($("#valorPorTonelada").val());
    var tipoDeCambioComercial = parseFloat($("#tipoDeCambioComercial").val());
    //calculos
    //var valorNetoMineral = tipoDeCambioComercial*kilosNetosSecos*valorPorTonelada/1000;
    valorNetoMineral = kilosNetosSecos*valorPorTonelada/1000;
    valorNetoMineralEnBolivianos = valorNetoMineral*tipoDeCambioComercial;
    $("#valorNetoMineral").val((isNaN(valorNetoMineral)) ?"?":toFixed(valorNetoMineral,2).toString());
    var valorNetoMineral2 = transFloat($("#valorNetoMineral").val());
    valorNetoMineralEnBolivianos = valorNetoMineral2*tipoDeCambioComercial;
    $("#valorNetoMineralEnBolivianos").val((isNaN(valorNetoMineralEnBolivianos)) ?"?":toFixed(valorNetoMineralEnBolivianos,2).toString());
}

function calcularRegaliaMineraConPorcentaje(){
    //var valorNetoMineralEnBolivianos = parseFloat($("#valorNetoMineralEnBolivianos").val());
    var porcentajeRegalia = parseFloat($("#porcentajeRegalia").val());
    //calculos
    var regaliaMinera = valorNetoMineralEnBolivianos*porcentajeRegalia/100;
    if(isNaN(regaliaMinera)){
        calcularRegaliaMinera();
    }else
        $("#regaliaMinera").val(toFixed(regaliaMinera,2).toString());
    //$("#regaliaMinera").val((isNaN(regaliaMinera)) ?"?":toFixed(regaliaMinera,2).toString());
    //refrescar tabla de retenciones
    //generar();
}

function calcularRegaliaMinera(){
    //var valorOficialBrutoDeZincEnBolivianos = parseFloat($("#valorOficialBrutoDeZincEnBolivianos").val());
    var alicuotaDeZinc = parseFloat($("#alicuotaDeZinc").val());
    //var valorOficialBrutoDePlomoEnBolivianos = parseFloat($("#valorOficialBrutoDePlomoEnBolivianos").val());
    var alicuotaDePlomo = parseFloat($("#alicuotaDePlomo").val());
    //var valorOficialBrutoDePlataEnBolivianos = parseFloat($("#valorOficialBrutoDePlataEnBolivianos").val());
    var alicuotaDePlata = parseFloat($("#alicuotaDePlata").val());
    var tipoDeCambioOficial = transFloat($("#tipoDeCambioOficial").val());
    //calculos
    //var regaliaMinera = tipoDeCambioOficial*(valorOficialBrutoDeZincEnBolivianos*alicuotaDeZinc/100 + valorOficialBrutoDePlomoEnBolivianos*alicuotaDePlomo/100 + valorOficialBrutoDePlataEnBolivianos*alicuotaDePlata/100);
    var regaliaMinera = (valorOficialBrutoDeZincEnBolivianos*alicuotaDeZinc/100 + valorOficialBrutoDePlomoEnBolivianos*alicuotaDePlomo/100 + valorOficialBrutoDePlataEnBolivianos*alicuotaDePlata/100);
    $("#regaliaMinera").val((isNaN(regaliaMinera)) ?"?":toFixed(regaliaMinera,2).toString());
}

function calcularValorDeCompra(){
    //var valorNetoMineralEnBolivianos = parseFloat($("#valorNetoMineralEnBolivianos").val());
    var bonoCalidad = parseFloat($("#bonoCalidad").val());
    var bonoIncentivo = parseFloat($("#bonoIncentivo").val());
    //calculos
    var valorDeCompra = valorNetoMineralEnBolivianos+bonoCalidad+bonoIncentivo;
    $("#valorDeCompra").val((isNaN(valorDeCompra)) ?"?":toFixed(valorDeCompra,2).toString());
}

function calcularTotalLiquidoPagable(){
    var totalPagado = parseFloat($("#totalPagado").val());
    var totalAnticiposContraEntrega = parseFloat($("#totalAnticiposContraEntrega").val());
    var totalAnticiposContraFuturaEntrega = parseFloat($("#totalAnticiposContraFuturaEntrega").val());
    //calculos
    var totalLiquidoPagable = totalPagado-totalAnticiposContraEntrega-totalAnticiposContraFuturaEntrega;
    $("#totalLiquidoPagable").val((isNaN(totalLiquidoPagable)) ?"?":toFixed(totalLiquidoPagable,2).toString());
}

function calcularTotalCostoLaboratorio(){
    var costoLaboratorio1 = parseFloat($("#costoLaboratorio1").val());
    costoLaboratorio1 = isNaN(costoLaboratorio1)?0:costoLaboratorio1;
    var costoLaboratorio2 = parseFloat($("#costoLaboratorio2").val());
    costoLaboratorio2 = isNaN(costoLaboratorio2)?0:costoLaboratorio2;
    var costoLaboratorio3 = parseFloat($("#costoLaboratorio3").val());
    costoLaboratorio3 = isNaN(costoLaboratorio3)?0:costoLaboratorio3;
    var costoLaboratorio4 = parseFloat($("#costoLaboratorio4").val());
    costoLaboratorio4 = isNaN(costoLaboratorio4)?0:costoLaboratorio4;
    //calculos
    var totalCostoLaboratorio = costoLaboratorio1+costoLaboratorio2+costoLaboratorio3+costoLaboratorio4;
    $("#totalCostoLaboratorio").val((isNaN(totalCostoLaboratorio)) ?"?":toFixed(totalCostoLaboratorio,2).toString());
}

function generarTablaRetencionesComplejo(){
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

    var totalPagado = valorDeCompra - totalRetenciones;
    //$("#retenciones").val(JSON.stringify(retencionesJSON));
    $("#totalRetenciones").val((isNaN(totalRetenciones)) ?"?":toFixed(totalRetenciones,2).toString());
    $("#totalPagado").val((isNaN(totalPagado)) ?"?":toFixed(totalPagado,2).toString());

    calcularTotalLiquidoPagable();
}

function eliminarRetencionComplejo(){
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
    generarTablaRetencionesComplejo();
}

function inicializarVariablesGlobales(){
    kilosNetosSecos=transFloat($("#kilosNetosSecos").val());
    kilosFinosZinc=transFloat($("#kilosFinosZinc").val());
    kilosFinosPlomo=transFloat($("#kilosFinosPlomo").val());
    kilosFinosPlata=transFloat($("#kilosFinosPlata").val());
    librasFinasDeZinc=transFloat($("#librasFinasDeZinc").val());
    librasFinasDePlomo=transFloat($("#librasFinasDePlomo").val());
    onzasTroyDePlata=transFloat($("#onzasTroyDePlata").val());
    valorOficialBrutoDeZinc=transFloat($("#valorOficialBrutoDeZinc").val());
    valorOficialBrutoDeZincEnBolivianos=transFloat($("#valorOficialBrutoDeZincEnBolivianos").val());
    valorOficialBrutoDePlomo=transFloat($("#valorOficialBrutoDePlomo").val());
    valorOficialBrutoDePlomoEnBolivianos=transFloat($("#valorOficialBrutoDePlomoEnBolivianos").val());
    valorOficialBrutoDePlata=transFloat($("#valorOficialBrutoDePlata").val());
    valorOficialBrutoDePlataEnBolivianos=transFloat($("#valorOficialBrutoDePlataEnBolivianos").val());
    valorPorTonelada=transFloat($("#valorPorTonelada").val());
    valorNetoMineral=transFloat($("#valorNetoMineral").val());
    valorNetoMineralEnBolivianos=transFloat($("#valorNetoMineralEnBolivianos").val());
}

function toFixed( number, precision ) {
    var multiplier = Math.pow( 10, precision );
    return Math.round( number * multiplier ) / multiplier;
}

function transFloat(numeroString){
    var numero = numeroString.replace(',','');
    return parseFloat(numero);
}