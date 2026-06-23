$(document).ready(function () {
    $("#generar").bind("click",actualizarContenido);

    jQuery("#list4").jqGrid({
        datatype: "local",
        height: 300,
        colNames: ["LEY","$us/PUNTO","VALOR TONELADA"],
        colModel:[ {name:'ley',index:'ley', width:90},
            {name:'punto',index:'punto', width:90},
            {name:'vpt',index:'vpt', width:90} ],
        multiselect: false,
        caption: "TABLA DE PRECIOS"
    });

    var mydata = $("#tablaDePrecios").val();

    if(mydata=="")
        mydata = [];
    else
        mydata = $.parseJSON(mydata);

    for(var i=0;i<=mydata.length;i++)
        jQuery("#list4").jqGrid('addRowData',i+1,mydata[i]);

    function actualizarContenido(){
        var leyInicial = parseFloat($("#leyInicial").val());
        var leyFinal = parseFloat($("#leyFinal").val());
        var valorPorPunto = parseFloat($("#valorPorPunto").val());        

        var tabla = new Array();

        while(leyInicial<=leyFinal){
            var fila = new Object();
            fila.ley = toFixed(leyInicial,2);
            fila.punto = toFixed(valorPorPunto,2);
            fila.vpt = leyInicial*valorPorPunto;
            leyInicial++;

            tabla.push(fila);
            $("#tablaDePrecios").val(JSON.stringify(tabla));
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
