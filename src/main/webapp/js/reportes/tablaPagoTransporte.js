$(document).ready(function () {
    $("#generar").bind("click",actualizarContenido);

    jQuery("#tablaRecepciones").jqGrid({
        datatype: "local",
        height: 300,
        colNames: ["Lote","Fecha Recepcion","Cliente","Empresa","Sacos","Peso Bruto","Costo Trans."],
        colModel:[ {name:'LOTE',index:'LOTE', width:80},
            {name:'FECHA_RECEPCION',index:'FECHA_RECEPCION', width:80},
            {name:'CLIENTE',index:'CLIENTE', width:250},
            {name:'EMPRESA',index:'EMPRESA', width:250},
            {name:'CANTIDAD_SACOS',index:'CANTIDAD_SACOS', width:80},
            {name:'PESO_BRUTO',index:'PESO_BRUTO', width:80},
            {name:'COSTO_TRANSPORTE',index:'COSTO_TRANSPORTE', width:80} ],
        multiselect: false,
        caption: "RECEPCIONES"
    });

    var datosTabla = $("#tablaDeRecepciones").val();

    if(datosTabla=="")
        datosTabla = [];
    else
        datosTabla = $.parseJSON(datosTabla);

    for(var i=0;i<=datosTabla.length;i++)
        jQuery("#tablaDeRecepciones").jqGrid('addRowData',i+1,datosTabla[i]);

    function actualizarContenido(){
        var cotizacionInicial = parseFloat($("#cotizacionInicial").val());
        var cotizacionFinal = parseFloat($("#cotizacionFinal").val());
        
        var ley5valorIncrementable = parseFloat($("#ley5valorIncrementable").val());
        var ley5valorInicial = parseFloat($("#ley5valorInicial").val());
        var ley10valorIncrementable = parseFloat($("#ley10valorIncrementable").val());
        var ley10valorInicial = parseFloat($("#ley10valorInicial").val());
        var ley15valorIncrementable = parseFloat($("#ley15valorIncrementable").val());
        var ley15valorInicial = parseFloat($("#ley15valorInicial").val());
        var ley20valorIncrementable = parseFloat($("#ley20valorIncrementable").val());
        var ley20valorInicial = parseFloat($("#ley20valorInicial").val());
        var ley25valorIncrementable = parseFloat($("#ley25valorIncrementable").val());
        var ley25valorInicial = parseFloat($("#ley25valorInicial").val());
        var ley30valorIncrementable = parseFloat($("#ley30valorIncrementable").val());
        var ley30valorInicial = parseFloat($("#ley30valorInicial").val());
        var ley35valorIncrementable = parseFloat($("#ley35valorIncrementable").val());
        var ley35valorInicial = parseFloat($("#ley35valorInicial").val());
        var ley40valorIncrementable = parseFloat($("#ley40valorIncrementable").val());
        var ley40valorInicial = parseFloat($("#ley40valorInicial").val());
        var ley50valorIncrementable = parseFloat($("#ley50valorIncrementable").val());
        var ley50valorInicial = parseFloat($("#ley50valorInicial").val());
        var ley60valorIncrementable = parseFloat($("#ley60valorIncrementable").val());
        var ley60valorInicial = parseFloat($("#ley60valorInicial").val());
        var ley70valorIncrementable = parseFloat($("#ley70valorIncrementable").val());
        var ley70valorInicial = parseFloat($("#ley70valorInicial").val());
        var ley75valorIncrementable = parseFloat($("#ley75valorIncrementable").val());
        var ley75valorInicial = parseFloat($("#ley75valorInicial").val());

        var tabla = new Array();

        while(cotizacionInicial<cotizacionFinal){
            var fila = new Object();
            fila.COT = toFixed(cotizacionInicial,2);
            cotizacionInicial = cotizacionInicial + 0.01;

            fila.L5 = toFixed(ley5valorInicial,2);
            ley5valorInicial = ley5valorInicial + ley5valorIncrementable;
            fila.L10 = toFixed(ley10valorInicial,2);
            ley10valorInicial = ley10valorInicial + ley10valorIncrementable;
            fila.L15 = toFixed(ley15valorInicial,2);
            ley15valorInicial = ley15valorInicial + ley15valorIncrementable;
            fila.L20 = toFixed(ley20valorInicial,2);
            ley20valorInicial = ley20valorInicial + ley20valorIncrementable;
            fila.L25 = toFixed(ley25valorInicial,2);
            ley25valorInicial = ley25valorInicial + ley25valorIncrementable;
            fila.L30 = toFixed(ley30valorInicial,2);
            ley30valorInicial = ley30valorInicial + ley30valorIncrementable;
            fila.L35 = toFixed(ley35valorInicial,2);
            ley35valorInicial = ley35valorInicial + ley35valorIncrementable;
            fila.L40 = toFixed(ley40valorInicial,2);
            ley40valorInicial = ley40valorInicial + ley40valorIncrementable;
            fila.L50 = toFixed(ley50valorInicial,2);
            ley50valorInicial = ley50valorInicial + ley50valorIncrementable;
            fila.L60 = toFixed(ley60valorInicial,2);
            ley60valorInicial = ley60valorInicial + ley60valorIncrementable;
            fila.L70 = toFixed(ley70valorInicial,2);
            ley70valorInicial = ley70valorInicial + ley70valorIncrementable;
            fila.L75 = toFixed(ley75valorInicial,2);
            ley75valorInicial = ley75valorInicial + ley75valorIncrementable;

            tabla.push(fila);
            $("#tablaDeCotizaciones").val(JSON.stringify(tabla));
        }

        $("#list4").jqGrid("clearGridData", true);
        for(var i=0;i<=tabla.length;i++)
            jQuery("#list4").jqGrid('addRowData',i+1,tabla[i]);
    }

    function toFixed( number, precision ) {
        var multiplier = Math.pow( 10, precision );
        return Math.round( number * multiplier ) / multiplier;
    }

});
