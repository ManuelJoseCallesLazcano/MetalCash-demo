$(document).ready(function () {
    $("#leyOro,#valorTonelada").numeric({
        allowMinus   : false,
        allowThouSep : false,
        maxDecimalPlaces:2
    });

    $("#agregar").bind("click",agregarFila);
    $("#actualizar").bind("click",actualizarFila);
    $("#eliminar").bind("click",eliminarFila);
    
    jQuery("#tablaPreciosOro").jqGrid({
        datatype: "local",
        height: 300,
        colNames: ["LEY gr/TMS","PRECIO TMS Bs"],
        colModel:[ {name:'leyOro',index:'leyOro', width:120},
            {name:'valorTonelada',index:'valorTonelada', width:120} ],
        multiselect: false,
        caption: "TABLA DE PRECIOS",
        onSelectRow: function(id){
            cargarFila();
        }
    });

    var mydata = $("#tablaPrecios").val();

    if(mydata=="")
        mydata = [];
    else
        mydata = $.parseJSON(mydata);

    for(var i=0;i<=mydata.length;i++)
        jQuery("#tablaPreciosOro").jqGrid('addRowData',i+1,mydata[i]);

    function agregarFila(){
        var leyOro = parseFloat($("#leyOro").val());
        var valorTonelada = parseFloat($("#valorTonelada").val());

        var fila = new Object();
        fila.leyOro=leyOro;
        fila.valorTonelada=valorTonelada;
        mydata.push(fila);
        $("#tablaPrecios").val(JSON.stringify(mydata));

        $("#tablaPreciosOro").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            $("#tablaPreciosOro").jqGrid('addRowData',i+1,mydata[i]);
        }

        limpiarCampos();
        $("#leyOro").focus();
    }

    function cargarFila(){
        var filaId = parseInt($('#tablaPreciosOro').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //
        var fila = mydata[filaId];
        $("#leyOro").val(fila.leyOro);
        $("#valorTonelada").val(fila.valorTonelada);
    }

    function actualizarFila(){
        var filaId = parseInt($('#tablaPreciosOro').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //
        var fila = mydata[filaId];
        fila.leyOro=$("#leyOro").val();
        fila.valorTonelada=$("#valorTonelada").val();

        $("#tablaPrecios").val(JSON.stringify(mydata));

        $("#tablaPreciosOro").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            $("#tablaPreciosOro").jqGrid('addRowData',i+1,mydata[i]);
        }

        limpiarCampos();
    }

    function eliminarFila(){
        var filaId = parseInt($('#tablaPreciosOro').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //alert("fila: "+filaId);
        var newmydata=[];
        var pos1=0;
        var pos2=0;
        while(pos1<mydata.length){
            if(pos1!=filaId){
                newmydata[pos2]=mydata[pos1];
                pos1++;
                pos2++;
            }else
                pos1++;
        }

        mydata=[];
        for(var i=0;i<newmydata.length;i++){
            mydata[i]=newmydata[i];
        }
        $("#tablaPrecios").val(JSON.stringify(mydata));

        $("#tablaPreciosOro").jqGrid("clearGridData", true);

        for(i=0;i<mydata.length;i++){
            $("#tablaPreciosOro").jqGrid('addRowData',i+1,mydata[i]);
        }
    }

    function limpiarCampos(){
       $("#leyOro").val("");
       $("#valorTonelada").val("");
    }

    function toFixed( number, precision ) {
        var multiplier = Math.pow( 10, precision );
        return Math.round( number * multiplier ) / multiplier;
    }
});
