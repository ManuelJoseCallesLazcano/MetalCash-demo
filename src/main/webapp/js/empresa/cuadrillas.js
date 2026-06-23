$(document).ready(function() {
    $("#tablaCuadrillas").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["NOMBRE DE CUADRILLA"],
        colModel:[ {name:'nombreCuadrilla',index:'nombreCuadrilla', width:300}],
        multiselect: false,
        caption: "CUADRILLAS",
        onSelectRow: function(id){
            cargarCuadrilla();
        }
    });

    var mydata = $("#cuadrillas").val();

    if(mydata==""||mydata==null)
        mydata = [];
    else
        mydata = $.parseJSON(mydata);
    $("#tablaCuadrillas").jqGrid("clearGridData", true);
    for(var i=0;i<mydata.length;i++)
        $("#tablaCuadrillas").jqGrid('addRowData',i+1,mydata[i]);

    $("#agregarCuadrilla").bind("click",agregarCuadrilla);
    $("#actualizarCuadrilla").bind("click",actualizarCuadrilla);
    $("#quitarCuadrilla").bind("click",eliminarCuadrilla);

    function agregarCuadrilla(){
        var nombreCuadrilla = $("#nombreCuadrilla").val();
        if(!existeCuadrilla(nombreCuadrilla)){
            var fila = new Object();
            fila.nombreCuadrilla=nombreCuadrilla;
            mydata.push(fila);
            $("#cuadrillas").val(JSON.stringify(mydata));
            $("#tablaCuadrillas").jqGrid("clearGridData", true);

            for(var i=0;i<mydata.length;i++){
                $("#tablaCuadrillas").jqGrid('addRowData',i+1,mydata[i]);
            }
        }else
            $.notify("La Cuadrilla ya esta en la lista.",{autoHide: false,clickToHide: true,className: "error"});

    }

    function eliminarCuadrilla(){
        var filaId = parseInt($('#tablaCuadrillas').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
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
        $("#cuadrillas").val(JSON.stringify(mydata));

        $("#tablaCuadrillas").jqGrid("clearGridData", true);

        for(i=0;i<mydata.length;i++){
            $("#tablaCuadrillas").jqGrid('addRowData',i+1,mydata[i]);
        }
    }

    function cargarCuadrilla(){
        var filaId = parseInt($('#tablaCuadrillas').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //
        var fila = mydata[filaId];
        $("#nombreCuadrilla").val(fila.nombreCuadrilla);
    }

    function actualizarCuadrilla(){
        var filaId = parseInt($('#tablaCuadrillas').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //
        var fila = mydata[filaId];
        fila.nombreCuadrilla=$("#nombreCuadrilla").val();
        $("#cuadrillas").val(JSON.stringify(mydata));

        $("#tablaCuadrillas").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            $("#tablaCuadrillas").jqGrid('addRowData',i+1,mydata[i]);
        }
    }

    function existeCuadrilla(nombreCuadrilla){
        for(var i=0;i<mydata.length;i++){
            if(mydata[i].nombreCuadrilla==nombreCuadrilla)
                return true;
        }
        return false;
    }
});


