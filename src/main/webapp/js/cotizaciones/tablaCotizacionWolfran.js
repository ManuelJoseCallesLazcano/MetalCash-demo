$(document).ready(function () {
    setInterval(function(){
        actualizarContenido();
    }, 100);

    jQuery("#list4").jqGrid({
        datatype: "local",
        height: 300,
        colNames: ["LEY","PRECIO $us"],
        colModel:[ {name:'LEY',index:'LEY', width:100},
            {name:'PRECIO',index:'PRECIO', width:100, editable: true}],
        multiselect: false,
        cellEdit: true,
        cellsubmit: 'clientArray',
        caption: "TABLA DE COTIZACIONES",
    });

    var mydata = $("#tablaDeCotizaciones").val();

    if(mydata=="")
        inicializarContenido();
    else{
        mydata = $.parseJSON(mydata);
        for(var i=0;i<=mydata.length;i++)
            jQuery("#list4").jqGrid('addRowData',i+1,mydata[i]);
    }

    function inicializarContenido(){
        var tabla = new Array();
        for(var i=5;i<=70;i++){
            var fila = new Object();
            fila.LEY = i;
            fila.PRECIO = 0;
            tabla.push(fila);
            $("#tablaDeCotizaciones").val(JSON.stringify(tabla));
        }

        $("#list4").jqGrid("clearGridData", true);
        for(var i=0;i<=tabla.length;i++)
            jQuery("#list4").jqGrid('addRowData',i+1,tabla[i]);
    }

    function actualizarContenido(){
        var precios = JSON.stringify($("#list4").jqGrid('getGridParam','data'));
        $("#tablaDeCotizaciones").val(precios);
    }

    function toFixed( number, precision ) {
        var multiplier = Math.pow( 10, precision );
        return Math.round( number * multiplier ) / multiplier;
    }

});

