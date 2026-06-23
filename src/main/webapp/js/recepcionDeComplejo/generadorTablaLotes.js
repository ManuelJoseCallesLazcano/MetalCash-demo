$(document).ready(function() {
    setInterval(function(){
        actualizarContenido();
    }, 100);

    $("#lotesGenerados").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["LOTE","TIPO DE MATERIAL","SACOS","PESO BRUTO KGS","COSTO TRANSPORTE","ANTICIPO SUGERIDO"],
        colModel:[ {name:'lote',index:'lote', width:70},
            {name:'tipoDeMaterial',index:'tipoDeMaterial', width:130, editable: true, edittype:"select", editoptions:{value:"CONCENTRADO:CONCENTRADO;MOLIDO 1/4:MOLIDO 1/4;MOLIDO 1/8:MOLIDO 1/8;BROZA:BROZA"}},
            {name:'sacos',index:'sacos', editable: true, edittype:"text", width:100, editoptions: {dataEvents: [{type: 'keyup',fn: function(e){
                var i = $('#lotesGenerados').jqGrid ('getGridParam', 'selrow');
                var rowData = $('#lotesGenerados').jqGrid('getRowData', i);
                var tipoDeMaterial = rowData['tipoDeMaterial'];
                var pesoBruto = rowData['pesoBruto'];
                calcularTransporte(tipoDeMaterial,$(this).val(),pesoBruto,i);
                //alert("tipoDeMaterial: "+tipoDeMaterial);
            }}]}},
            {name:'pesoBruto',index:'pesoBruto', editable: true, edittype:"text", width:100, editoptions: {dataEvents: [{type: 'keyup',fn: function(e){
                var i = $('#lotesGenerados').jqGrid ('getGridParam', 'selrow');
                var rowData = $('#lotesGenerados').jqGrid('getRowData', i);
                var tipoDeMaterial = rowData['tipoDeMaterial'];
                var sacos = rowData['sacos'];
                calcularTransporte(tipoDeMaterial,sacos,$(this).val(),i);
            }}]}},
            {name:'costoDeTransporte',index:'costoDeTransporte', editable: true, edittype:"text", width:100},
            {name:'anticipoAutorizado',index:'anticipoAutorizado', editable: true, edittype:"text", width:100}
        ],
        cellEdit: true,
        cellsubmit: 'clientArray',
        url: '/demo-liquidaciones/',
        editurl: '/demo-liquidaciones/',
        multiselect: false,
        caption: "LOTES RECEPCIONADOS"
    });

    crearTabla();

    $("#generar").bind("click",generarFilas);
    $("#mostrar").bind("click",mostrarContenido);

    function calcularTransporte(tipoDeMaterial,sacos,pesoBruto,fila){
        var costo = 0;
        if(tipoDeMaterial=="CONCENTRADO"){
            costo = calcularCostoTransporteConcentrados(sacos,pesoBruto);
        }else{
            costo = calcularCostoTransporteComplejos(sacos,pesoBruto);
        }
        $("#lotesGenerados").jqGrid('setCell', fila, 'costoDeTransporte', costo);
    }

    function generarFilas(){
        var loteInicial = parseInt($("#loteInicial").val());
        var loteFinal = parseInt($("#loteFinal").val());

        //if($("#loteInicial").val()&&$("#loteFinal").val().isNumber()){
        if(!isNaN(loteInicial)&&!isNaN(loteFinal)){
            var tabla = new Array();

            for(var i=loteInicial;i<=loteFinal;i++){
                var fila = new Object();
                fila.lote = i;
                fila.tipoDeMaterial = "";
                fila.sacos = 0;
                fila.pesoBruto = 0;
                fila.costoDeTransporte = 0;
                fila.anticipoAutorizado = 0;

                tabla.push(fila);
                $("#lotes").val(JSON.stringify(tabla));
            }

            $("#lotesGenerados").jqGrid("clearGridData", true);
            for(var i=0;i<=tabla.length;i++)
                jQuery("#lotesGenerados").jqGrid('addRowData',i+1,tabla[i]);

        }else{
            $.notify("Usted ha ingresado información incorrecta.",{autoHide: true,clickToHide: true,className: "error"});
        }

    }

    function crearTabla(){
        var lotes=""
        var mydata = $("#lotes").val();
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);
        $("#lotesAsignados").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            lotes=lotes+mydata[i].lote+", ";
            $("#lotesAsignados").jqGrid('addRowData',i+1,mydata[i]);
        }

        $("#descripcion").val("POR LOTES: "+lotes.substring(0,lotes.length-2));//para quitar la coma y el espacio del final
    }

    function eliminarLote(){
        var filaId = parseInt($('#lotesAsignados').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //alert("fila: "+filaId);
        var mydata = $.parseJSON($("#lotes").val());
        var nuevoMydata = new Array()

        for(var i=0;i<mydata.length;i++){
            if(filaId!=i) // copiar todas las filas menos la seleccionada
                nuevoMydata.push(mydata[i]);
        }
        //actualizar la cadena JSON en el campo de texto
        $("#lotes").val(JSON.stringify(nuevoMydata));
        crearTabla();
    }

    function actualizarContenido(){
        var lotes = JSON.stringify($("#lotesGenerados").jqGrid('getGridParam','data'));
        $("#lotes").val(lotes);
    }

    function mostrarContenido(){
        var lotes = JSON.stringify($("#lotesGenerados").jqGrid('getGridParam','data'));
        var data = $("#lotesGenerados").jqGrid('getGridParam','data');
        var blah = "";
        for(var i=0;i<data.length;i++){
            blah=blah+data[i].tipoDeMaterial+"\n"
        }
        //console.log(blah);
        alert(blah);
        $("#lotes").val(lotes);
    }

    function calcularCostoTransporteComplejos(cantidadDeSacos,pesoBruto){
        // DATOS RECUPERADOS DESDE LA BASE DE DATOS
        var costoTransporteComplejos = parseFloat($("#costoTransporteComplejos").val());
        var unidadMonetariaComplejos = $("#unidadMonetariaComplejos").val();
        var unidadDeCobroComplejos = $("#unidadDeCobroComplejos").val();
        var tipoDeCambioComercial = parseFloat($("#tipoDeCambioComercial").val());
        var costoDeTransporte = 0;
        
        if(unidadMonetariaComplejos == "Bs" && unidadDeCobroComplejos == "TONELADA"){
            costoDeTransporte = pesoBruto*costoTransporteComplejos/1000;            
        }

        if(unidadMonetariaComplejos == "Bs" && unidadDeCobroComplejos == "SACO"){
            costoDeTransporte = cantidadDeSacos*costoTransporteComplejos;
        }

        if(unidadMonetariaComplejos == "$us" && unidadDeCobroComplejos == "TONELADA"){
            costoDeTransporte = tipoDeCambioComercial*pesoBruto*costoTransporteComplejos/1000;
        }

        if(unidadMonetariaComplejos == "$us" && unidadDeCobroComplejos == "SACO"){
            costoDeTransporte = tipoDeCambioComercial*cantidadDeSacos*costoTransporteComplejos;
        }

        return (isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString();
    }

    function calcularCostoTransporteConcentrados(cantidadDeSacos,pesoBruto){
        // DATOS RECUPERADOS DESDE LA BASE DE DATOS
        var costoTransporteConcentrados = parseFloat($("#costoTransporteConcentrados").val());
        var unidadMonetariaConcentrados = $("#unidadMonetariaConcentrados").val();
        var unidadDeCobroConcentrados = $("#unidadDeCobroConcentrados").val();
        var tipoDeCambioComercial = parseFloat($("#tipoDeCambioComercial").val());
        var costoDeTransporte = 0;

        if(unidadMonetariaConcentrados == "Bs" && unidadDeCobroConcentrados == "TONELADA"){
            costoDeTransporte = pesoBruto*costoTransporteConcentrados/1000;
        }

        if(unidadMonetariaConcentrados == "Bs" && unidadDeCobroConcentrados == "SACO"){
            costoDeTransporte = cantidadDeSacos*costoTransporteConcentrados;
        }

        if(unidadMonetariaConcentrados == "$us" && unidadDeCobroConcentrados == "TONELADA"){
            costoDeTransporte = tipoDeCambioComercial*pesoBruto*costoTransporteConcentrados/1000;
        }

        if(unidadMonetariaConcentrados == "$us" && unidadDeCobroConcentrados == "SACO"){
            costoDeTransporte = tipoDeCambioComercial*cantidadDeSacos*costoTransporteConcentrados;
        }

        return (isNaN(costoDeTransporte)) ?"?":costoDeTransporte.toString();
    }
});
