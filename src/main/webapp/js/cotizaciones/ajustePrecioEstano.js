$(document).ready(function () {
    var estado=$('#cotizacionDiariaDeMinerales').val();
    cargarFilas();

    $("#margen").bind("keyup",cargarFilas).bind("blur",cargarFilas);
    $("#cotizacionDiariaDeMinerales" ).change(function() {
        evitarCambioCotizacion();
        //alert( "Handler for .change() called." );
    });

    jQuery("#tablaFilaOriginal").jqGrid({
        datatype: "local",
        height: 50,
        colNames: ["COT.","Ley 5%","Ley 10%","Ley 15%","Ley 20%","Ley 25%","Ley 30%","Ley 35%","Ley 40%","Ley 50%","Ley 60%","Ley 70%","Ley 75%"],
        colModel:[ {name:'COT',index:'COT', width:65},
            {name:'L5',index:'L5', width:65},
            {name:'L10',index:'L10', width:65},
            {name:'L15',index:'L15', width:65},
            {name:'L20',index:'L20', width:65},
            {name:'L25',index:'L25', width:65},
            {name:'L30',index:'L30', width:65},
            {name:'L35',index:'L35', width:65},
            {name:'L40',index:'L40', width:65},
            {name:'L50',index:'L50', width:65},
            {name:'L60',index:'L60', width:65},
            {name:'L70',index:'L70', width:65},
            {name:'L75',index:'L75', width:65} ],
        multiselect: false,
        caption: "FILA ORIGINAL"
    });

    jQuery("#tablaFilaAjustada").jqGrid({
        datatype: "local",
        height: 50,
        colNames: ["COT.","Ley 5%","Ley 10%","Ley 15%","Ley 20%","Ley 25%","Ley 30%","Ley 35%","Ley 40%","Ley 50%","Ley 60%","Ley 70%","Ley 75%"],
        colModel:[ {name:'COT',index:'COT', width:65},
            {name:'L5',index:'L5', width:65},
            {name:'L10',index:'L10', width:65},
            {name:'L15',index:'L15', width:65},
            {name:'L20',index:'L20', width:65},
            {name:'L25',index:'L25', width:65},
            {name:'L30',index:'L30', width:65},
            {name:'L35',index:'L35', width:65},
            {name:'L40',index:'L40', width:65},
            {name:'L50',index:'L50', width:65},
            {name:'L60',index:'L60', width:65},
            {name:'L70',index:'L70', width:65},
            {name:'L75',index:'L75', width:65} ],
        multiselect: false,
        caption: "FILA AJUSTADA"
    });

    function cargarFilas(){
        if(isNaN(parseFloat($('#margen').val()))){
            $.notify("Valor para Margen incorrecto.",{autoHide: true,clickToHide: true,className: "error"});
            return;
        }
        $.ajax({
            url:"/demo-liquidaciones/tablaCotizacionEstano/getFilas",
            dataType: 'json',
            data: {
                tablaCotizacionEstanoId:$('#tablaCotizacionEstano').val(),
                cotizacionDiariaDeMineralesId:$('#cotizacionDiariaDeMinerales').val(),
                margen:$('#margen').val()
            },
            success: function(data) {
                $('#filaOriginal').val(data.filaOriginal);
                $('#filaAjustada').val(data.filaAjustada);
                crearTabla($("#filaOriginal"),$("#tablaFilaOriginal"));
                crearTabla($("#filaAjustada"),$("#tablaFilaAjustada"));
            },
            error: function(request, status, error) {

            }
        });
    }

    function evitarCambioCotizacion(){
        $('#cotizacionDiariaDeMinerales').val(estado);
        $.notify("La Cotización no puede modificarse. \nSe ajusta a la última cotización registrada.",{autoHide: true,clickToHide: true,className: "warn"});
    }
    function crearTabla(datos,tabla){
        var mydata = datos.val();
        
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);

        var fila = new Array();
        fila.push(mydata)
        tabla.jqGrid("clearGridData", true);
        for(var i=0;i<=fila.length;i++)
            tabla.jqGrid('addRowData',i+1,fila[i]);
    }
    //
    //function actualizarContenido(){
    //    var cotizacionInicial = parseFloat($("#cotizacionInicial").val());
    //    var cotizacionFinal = parseFloat($("#cotizacionFinal").val());
    //
    //    var ley5valorIncrementable = parseFloat($("#ley5valorIncrementable").val());
    //    var ley5valorInicial = parseFloat($("#ley5valorInicial").val());
    //    var ley10valorIncrementable = parseFloat($("#ley10valorIncrementable").val());
    //    var ley10valorInicial = parseFloat($("#ley10valorInicial").val());
    //    var ley15valorIncrementable = parseFloat($("#ley15valorIncrementable").val());
    //    var ley15valorInicial = parseFloat($("#ley15valorInicial").val());
    //    var ley20valorIncrementable = parseFloat($("#ley20valorIncrementable").val());
    //    var ley20valorInicial = parseFloat($("#ley20valorInicial").val());
    //    var ley25valorIncrementable = parseFloat($("#ley25valorIncrementable").val());
    //    var ley25valorInicial = parseFloat($("#ley25valorInicial").val());
    //    var ley30valorIncrementable = parseFloat($("#ley30valorIncrementable").val());
    //    var ley30valorInicial = parseFloat($("#ley30valorInicial").val());
    //    var ley35valorIncrementable = parseFloat($("#ley35valorIncrementable").val());
    //    var ley35valorInicial = parseFloat($("#ley35valorInicial").val());
    //    var ley40valorIncrementable = parseFloat($("#ley40valorIncrementable").val());
    //    var ley40valorInicial = parseFloat($("#ley40valorInicial").val());
    //    var ley50valorIncrementable = parseFloat($("#ley50valorIncrementable").val());
    //    var ley50valorInicial = parseFloat($("#ley50valorInicial").val());
    //    var ley60valorIncrementable = parseFloat($("#ley60valorIncrementable").val());
    //    var ley60valorInicial = parseFloat($("#ley60valorInicial").val());
    //    var ley70valorIncrementable = parseFloat($("#ley70valorIncrementable").val());
    //    var ley70valorInicial = parseFloat($("#ley70valorInicial").val());
    //    var ley75valorIncrementable = parseFloat($("#ley75valorIncrementable").val());
    //    var ley75valorInicial = parseFloat($("#ley75valorInicial").val());
    //
    //    var tabla = new Array();
    //
    //    while(cotizacionInicial<cotizacionFinal){
    //        var fila = new Object();
    //        fila.COT = toFixed(cotizacionInicial,2);
    //        cotizacionInicial = cotizacionInicial + 0.01;
    //
    //        fila.L5 = toFixed(ley5valorInicial,2);
    //        ley5valorInicial = ley5valorInicial + ley5valorIncrementable;
    //        fila.L10 = toFixed(ley10valorInicial,2);
    //        ley10valorInicial = ley10valorInicial + ley10valorIncrementable;
    //        fila.L15 = toFixed(ley15valorInicial,2);
    //        ley15valorInicial = ley15valorInicial + ley15valorIncrementable;
    //        fila.L20 = toFixed(ley20valorInicial,2);
    //        ley20valorInicial = ley20valorInicial + ley20valorIncrementable;
    //        fila.L25 = toFixed(ley25valorInicial,2);
    //        ley25valorInicial = ley25valorInicial + ley25valorIncrementable;
    //        fila.L30 = toFixed(ley30valorInicial,2);
    //        ley30valorInicial = ley30valorInicial + ley30valorIncrementable;
    //        fila.L35 = toFixed(ley35valorInicial,2);
    //        ley35valorInicial = ley35valorInicial + ley35valorIncrementable;
    //        fila.L40 = toFixed(ley40valorInicial,2);
    //        ley40valorInicial = ley40valorInicial + ley40valorIncrementable;
    //        fila.L50 = toFixed(ley50valorInicial,2);
    //        ley50valorInicial = ley50valorInicial + ley50valorIncrementable;
    //        fila.L60 = toFixed(ley60valorInicial,2);
    //        ley60valorInicial = ley60valorInicial + ley60valorIncrementable;
    //        fila.L70 = toFixed(ley70valorInicial,2);
    //        ley70valorInicial = ley70valorInicial + ley70valorIncrementable;
    //        fila.L75 = toFixed(ley75valorInicial,2);
    //        ley75valorInicial = ley75valorInicial + ley75valorIncrementable;
    //
    //        tabla.push(fila);
    //        $("#tablaDeCotizaciones").val(JSON.stringify(tabla));
    //    }
    //
    //    $("#tablaFilaAjustada").jqGrid("clearGridData", true);
    //    for(var i=0;i<=tabla.length;i++)
    //        jQuery("#tablaFilaAjustada").jqGrid('addRowData',i+1,tabla[i]);
    //}

    function toFixed( number, precision ) {
        var multiplier = Math.pow( 10, precision );
        return Math.round( number * multiplier ) / multiplier;
    }

});
