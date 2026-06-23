$(document).ready(function () {
    $("#generar").bind("click",actualizarContenido);

    jQuery("#list4").jqGrid({
        datatype: "local",
        width: 900,
        shrinkToFit: false,
        height: 300,
        colNames: ["COT.","Ley 10DM","Ley 20DM","Ley 30DM","Ley 40DM","Ley 50DM","Ley 60DM","Ley 70DM","Ley 80DM","Ley 90DM","Ley 100DM","Ley 150DM","Ley 200DM","Ley 300DM","Ley 400DM","Ley 500DM","Ley 600DM","Ley 700DM","Ley 800DM","Ley 900DM","Ley 1000DM"],
        colModel:[ {name:'COT',index:'COT', width:70},
            {name:'L10',index:'L10', width:70},
            {name:'L20',index:'L20', width:70},
            {name:'L30',index:'L30', width:70},
            {name:'L40',index:'L40', width:70},
            {name:'L50',index:'L50', width:70},
            {name:'L60',index:'L60', width:70},
            {name:'L70',index:'L70', width:70},
            {name:'L80',index:'L80', width:70},
            {name:'L90',index:'L90', width:70},
            {name:'L100',index:'L100', width:70},
            {name:'L150',index:'L150', width:70},
            {name:'L200',index:'L200', width:70},
            {name:'L300',index:'L300', width:70},
            {name:'L400',index:'L400', width:70},
            {name:'L500',index:'L500', width:70},
            {name:'L600',index:'L600', width:70},
            {name:'L700',index:'L700', width:70},
            {name:'L800',index:'L800', width:70},
            {name:'L900',index:'L900', width:70},
            {name:'L1000',index:'L1000', width:70} ],
        multiselect: false,
        caption: "TABLA DE COTIZACIONES"
    });

    var mydata = $("#tablaDeCotizaciones").val();

    if(mydata=="")
        mydata = [];
    else
        mydata = $.parseJSON(mydata);

    for(var i=0;i<=mydata.length;i++)
        jQuery("#list4").jqGrid('addRowData',i+1,mydata[i]);

    function actualizarContenido(){
        var cotizacionInicial = parseFloat($("#cotizacionInicial").val());
        var cotizacionFinal = parseFloat($("#cotizacionFinal").val());

        var ley10valorIncrementable = parseFloat($("#ley10valorIncrementable").val());
        var ley10valorInicial = parseFloat($("#ley10valorInicial").val());
        var ley20valorIncrementable = parseFloat($("#ley20valorIncrementable").val());
        var ley20valorInicial = parseFloat($("#ley20valorInicial").val());
        var ley30valorIncrementable = parseFloat($("#ley30valorIncrementable").val());
        var ley30valorInicial = parseFloat($("#ley30valorInicial").val());
        var ley40valorIncrementable = parseFloat($("#ley40valorIncrementable").val());
        var ley40valorInicial = parseFloat($("#ley40valorInicial").val());
        var ley50valorIncrementable = parseFloat($("#ley50valorIncrementable").val());
        var ley50valorInicial = parseFloat($("#ley50valorInicial").val());
        var ley60valorIncrementable = parseFloat($("#ley60valorIncrementable").val());
        var ley60valorInicial = parseFloat($("#ley60valorInicial").val());
        var ley70valorIncrementable = parseFloat($("#ley70valorIncrementable").val());
        var ley70valorInicial = parseFloat($("#ley70valorInicial").val());
        var ley80valorIncrementable = parseFloat($("#ley80valorIncrementable").val());
        var ley80valorInicial = parseFloat($("#ley80valorInicial").val());
        var ley90valorIncrementable = parseFloat($("#ley90valorIncrementable").val());
        var ley90valorInicial = parseFloat($("#ley90valorInicial").val());
        var ley100valorIncrementable = parseFloat($("#ley100valorIncrementable").val());
        var ley100valorInicial = parseFloat($("#ley100valorInicial").val());
        var ley150valorIncrementable = parseFloat($("#ley150valorIncrementable").val());
        var ley150valorInicial = parseFloat($("#ley150valorInicial").val());
        var ley200valorIncrementable = parseFloat($("#ley200valorIncrementable").val());
        var ley200valorInicial = parseFloat($("#ley200valorInicial").val());
        var ley300valorIncrementable = parseFloat($("#ley300valorIncrementable").val());
        var ley300valorInicial = parseFloat($("#ley300valorInicial").val());
        var ley400valorIncrementable = parseFloat($("#ley400valorIncrementable").val());
        var ley400valorInicial = parseFloat($("#ley400valorInicial").val());
        var ley500valorIncrementable = parseFloat($("#ley500valorIncrementable").val());
        var ley500valorInicial = parseFloat($("#ley500valorInicial").val());
        var ley600valorIncrementable = parseFloat($("#ley600valorIncrementable").val());
        var ley600valorInicial = parseFloat($("#ley600valorInicial").val());
        var ley700valorIncrementable = parseFloat($("#ley700valorIncrementable").val());
        var ley700valorInicial = parseFloat($("#ley700valorInicial").val());
        var ley800valorIncrementable = parseFloat($("#ley800valorIncrementable").val());
        var ley800valorInicial = parseFloat($("#ley800valorInicial").val());
        var ley900valorIncrementable = parseFloat($("#ley900valorIncrementable").val());
        var ley900valorInicial = parseFloat($("#ley900valorInicial").val());
        var ley1000valorIncrementable = parseFloat($("#ley1000valorIncrementable").val());
        var ley1000valorInicial = parseFloat($("#ley1000valorInicial").val());
        var tabla = new Array();

        while(cotizacionInicial<cotizacionFinal){
            var fila = new Object();
            fila.COT = toFixed(cotizacionInicial,2);
            cotizacionInicial = cotizacionInicial + 0.01;

            fila.L10 = toFixed(ley10valorInicial,2);
            ley10valorInicial = ley10valorInicial + ley10valorIncrementable;
            
            fila.L20 = toFixed(ley20valorInicial,2);
            ley20valorInicial = ley20valorInicial + ley20valorIncrementable;
            
            fila.L30 = toFixed(ley30valorInicial,2);
            ley30valorInicial = ley30valorInicial + ley30valorIncrementable;
            
            fila.L40 = toFixed(ley40valorInicial,2);
            ley40valorInicial = ley40valorInicial + ley40valorIncrementable;
            
            fila.L50 = toFixed(ley50valorInicial,2);
            ley50valorInicial = ley50valorInicial + ley50valorIncrementable;
            
            fila.L60 = toFixed(ley60valorInicial,2);
            ley60valorInicial = ley60valorInicial + ley60valorIncrementable;
            
            fila.L70 = toFixed(ley70valorInicial,2);
            ley70valorInicial = ley70valorInicial + ley70valorIncrementable;

            fila.L80 = toFixed(ley80valorInicial,2);
            ley80valorInicial = ley80valorInicial + ley80valorIncrementable;

            fila.L90 = toFixed(ley90valorInicial,2);
            ley90valorInicial = ley90valorInicial + ley90valorIncrementable;

            fila.L100 = toFixed(ley100valorInicial,2);
            ley100valorInicial = ley100valorInicial + ley100valorIncrementable;

            fila.L150 = toFixed(ley150valorInicial,2);
            ley150valorInicial = ley150valorInicial + ley150valorIncrementable;

            fila.L200 = toFixed(ley200valorInicial,2);
            ley200valorInicial = ley200valorInicial + ley200valorIncrementable;

            fila.L300 = toFixed(ley300valorInicial,2);
            ley300valorInicial = ley300valorInicial + ley300valorIncrementable;

            fila.L400 = toFixed(ley400valorInicial,2);
            ley400valorInicial = ley400valorInicial + ley400valorIncrementable;

            fila.L500 = toFixed(ley500valorInicial,2);
            ley500valorInicial = ley500valorInicial + ley500valorIncrementable;

            fila.L600 = toFixed(ley600valorInicial,2);
            ley600valorInicial = ley600valorInicial + ley600valorIncrementable;

            fila.L700 = toFixed(ley700valorInicial,2);
            ley700valorInicial = ley700valorInicial + ley700valorIncrementable;

            fila.L800 = toFixed(ley800valorInicial,2);
            ley800valorInicial = ley800valorInicial + ley800valorIncrementable;

            fila.L900 = toFixed(ley900valorInicial,2);
            ley900valorInicial = ley900valorInicial + ley900valorIncrementable;

            fila.L1000 = toFixed(ley1000valorInicial,2);
            ley1000valorInicial = ley1000valorInicial + ley1000valorIncrementable;
            
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

