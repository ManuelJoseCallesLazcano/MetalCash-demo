$(document).ready(function() {
    //definir tabla de lotes
    //CODIGO 	DESCRIPCION 	TIPO 	CANTIDAD 	UNIDAD 	ASIGNACION
    $("#tablaRetenciones").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["CODIGO","DESCRIPCION","TIPO","CANTIDAD","UNIDAD","ASIGNACION"],
        colModel:[ {name:'CODIGO',index:'CODIGO', width:70},
            {name:'DESCRIPCION',index:'DESCRIPCION', width:150},
            {name:'TIPO',index:'TIPO', width:100},
            {name:'CANTIDAD',index:'CANTIDAD', width:100},
            {name:'UNIDAD',index:'UNIDAD', width:80},
            {name:'ASIGNACION',index:'ASIGNACION', width:100}
        ],
        multiselect: false,
        caption: "DETALLE DE RETENCIONES",
        onSelectRow: function(id){
            cargarRetencion();
        }
    });

    var mydata = $("#retenciones").val();

    if(mydata=="")
        mydata = [];
    else
        mydata = $.parseJSON(mydata);

    for(var i=0;i<=mydata.length;i++)
        jQuery("#tablaRetenciones").jqGrid('addRowData',i+1,mydata[i]);

    $("#agregar").bind("click",agregarRetencion);
    $("#actualizar").bind("click",actualizarRetencion);
    $("#quitar").bind("click",eliminarLote);

    function agregarRetencion(){
        var codigo = parseInt($("#lista_retencion").val());
        if(!existeCodigo(codigo)){
            var fila = new Object();
            fila.CODIGO=$("#lista_retencion").val();
            fila.DESCRIPCION=$("#descripcion").val();
            fila.TIPO=$("#tipoDeRetencion").val();
            fila.CANTIDAD=$("#cantidad").val();
            fila.UNIDAD=$("#unidadDeDescuento").val();
            fila.ASIGNACION=$("#asignacion").val();

            mydata.push(fila);
            $("#retenciones").val(JSON.stringify(mydata));

            $("#tablaRetenciones").jqGrid("clearGridData", true);

            for(var i=0;i<mydata.length;i++){
                $("#tablaRetenciones").jqGrid('addRowData',i+1,mydata[i]);
            }
        }else
            $.notify("La Retencion seleccionada ya esta en la lista.",{autoHide: false,clickToHide: true,className: "error"});

    }

    function eliminarLote(){
        var filaId = parseInt($('#tablaRetenciones').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
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
        $("#retenciones").val(JSON.stringify(mydata));

        $("#tablaRetenciones").jqGrid("clearGridData", true);

        for(i=0;i<mydata.length;i++){
            $("#tablaRetenciones").jqGrid('addRowData',i+1,mydata[i]);
        }
    }

    function cargarRetencion(){
        var filaId = parseInt($('#tablaRetenciones').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //
        var fila = mydata[filaId];
        $("#lista_retencion").val(fila.CODIGO).trigger('change.select2');
        $("#descripcion").val(fila.DESCRIPCION);
        $("#tipoDeRetencion").val(fila.TIPO);
        $("#cantidad").val(fila.CANTIDAD);
        $("#unidadDeDescuento").val(fila.UNIDAD);
        $("#asignacion").val(fila.ASIGNACION);
    }

    function actualizarRetencion(){
        var filaId = parseInt($('#tablaRetenciones').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //
        var fila = mydata[filaId];
        fila.CODIGO=$("#lista_retencion").val();
        fila.DESCRIPCION=$("#descripcion").val();
        fila.TIPO=$("#tipoDeRetencion").val();
        fila.CANTIDAD=$("#cantidad").val();
        fila.UNIDAD=$("#unidadDeDescuento").val();
        fila.ASIGNACION=$("#asignacion").val();

        $("#retenciones").val(JSON.stringify(mydata));

        $("#tablaRetenciones").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            $("#tablaRetenciones").jqGrid('addRowData',i+1,mydata[i]);
        }
    }

    function existeCodigo(codigo){
        for(var i=0;i<mydata.length;i++){
            if(mydata[i].CODIGO==codigo)
                return true;
        }
        return false;
    }
});

