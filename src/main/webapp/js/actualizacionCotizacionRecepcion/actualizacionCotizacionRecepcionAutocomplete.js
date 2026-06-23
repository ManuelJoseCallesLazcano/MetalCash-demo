$(document).ready(function() {
    $("#agregar,#quitar,#actualizar").button();
//    $("#agregar").prop("disabled",true);
//    $("#quitar").prop("disabled",true);
    //definir tabla de detalleLotes
    $("#detalleLotesTabla").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["LOTE","recepcionId","CLIENTE","EMPRESA","FECHA RECEPCION","FECHA COT. QUINCENAL","FECHA COT. DIARIA"],
        colModel:[ {name:'lote',index:'lote', width:70},
            {name:'recepcionId',index:'recepcionId', width:5, hidden: true},
            {name:'nombreCliente',index:'nombreCliente', width:120},
            {name:'nombreEmpresa',index:'nombreEmpresa', width:120},
            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:80},
            {name:'fechaDeCotizacionQuincenal',index:'fechaDeCotizacionQuincenal', width:180},
            {name:'fechaDeCotizacionDiaria',index:'fechaDeCotizacionDiaria', width:180}
        ],
        multiselect: true,
        caption: "LOTES ASIGNADOS"
    });
    //crear tabla de detalleLotes
    crearTabla();
    controlarTipoCotizacion();

    $("#agregar").bind("click",agregarLote);
    $("#quitar").bind("click",eliminarLote);
    $("#actualizar").bind("click",actualizacionLotes);
    $("#tipoCotizacion").bind("change",controlarTipoCotizacion);

    function controlarTipoCotizacion(){
        var tipoCotizacion=$("#tipoCotizacion").val();
        if(tipoCotizacion=="COTIZACION DIARIA"){
            $("#_cotizacionDiariaDeMinerales").show();
            $("#_cotizacionQuincenalDeMinerales").hide();
        }else{
            $("#_cotizacionDiariaDeMinerales").hide();
            $("#_cotizacionQuincenalDeMinerales").show();
        }
    }

    function agregarLote(){
        $.ajax({
            url: "/demo-liquidaciones/recepcionDeComplejo/filtradoLotesActualizacionCotizacionJSON",
            dataType: 'json',
            data: {
                tipoCotizacion: $('#tipoCotizacion').val(),
                cotizacionDiariaDeMineralesId: $('#cotizacionDiariaDeMinerales').val(),
                cotizacionQuincenalDeMineralesId: $('#cotizacionQuincenalDeMinerales').val(),
                fechaInicial_year: $('#fechaInicial_year').val(),
                fechaInicial_month: $('#fechaInicial_month').val(),
                fechaInicial_day: $('#fechaInicial_day').val(),
                fechaFinal_year: $('#fechaFinal_year').val(),
                fechaFinal_month: $('#fechaFinal_month').val(),
                fechaFinal_day: $('#fechaFinal_day').val()
            },
            success: function(data) {
                $('#detalleLotes').val(data.detalleLotes);
                crearTabla();
            },
            error: function(request, status, error) {

            }
        });
    }

    function actualizacionLotes(){
        $.ajax({
            url: "/demo-liquidaciones/recepcionDeComplejo/actualizacionCotizacionRecepcionJSON",
            dataType: 'json',
            data: {
                tipoCotizacion: $('#tipoCotizacion').val(),
                cotizacionDiariaDeMineralesId: $('#cotizacionDiariaDeMinerales').val(),
                cotizacionQuincenalDeMineralesId: $('#cotizacionQuincenalDeMinerales').val(),
                detalleLotes: $('#detalleLotes').val()
            },
            success: function(data) {
                $.notify("¡Cotizaciones actualizadas!",{autoHide: false,clickToHide: true,className: "success"});
                $('#detalleLotes').val(data._detalleLotes);
                crearTabla();
            },
            error: function(request, status, error) {

            }
        });
    }

    function crearTabla(){
        var mydata = $("#detalleLotes").val();
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);
        $("#detalleLotesTabla").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            detalleLotes=detalleLotes+mydata[i].lote+", ";
            $("#detalleLotesTabla").jqGrid('addRowData',i+1,mydata[i]);
        }
    }

    function eliminarLote(){
        // var filaId = parseInt($('#detalleLotesTabla').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        // //alert("fila: "+filaId);
        // var mydata = $.parseJSON($("#detalleLotes").val());
        // var nuevoMydata = new Array()
        //
        // for(var i=0;i<mydata.length;i++){
        //     if(filaId!=i) // copiar todas las filas menos la seleccionada
        //         nuevoMydata.push(mydata[i]);
        // }
        // //actualizar la cadena JSON en el campo de texto
        // $("#detalleLotes").val(JSON.stringify(nuevoMydata));
        // crearTabla();

        var filas = $('#detalleLotesTabla').jqGrid('getGridParam','selarrrow'); //restar uno porque la funcion considera el indice inicial como 1
//        for(var i=0; i<filas.length; i++)
//            alert("fila: "+filas[i]);

        var mydata = $.parseJSON($("#detalleLotes").val());
        var nuevoMydata = new Array()

        for(var i=0;i<mydata.length;i++){
            if(!existeValor(i+1,filas)) // copiar todas las filas menos la seleccionada
                nuevoMydata.push(mydata[i]);
        }
        //actualizar la cadena JSON en el campo de texto
        $("#detalleLotes").val(JSON.stringify(nuevoMydata));
        crearTabla();
    }

    function existeValor(valor,array){
        var pos=0;
        var existe=false;
        while(pos<array.length && !existe){
            //alert("comparando array: "+array[pos]+" y valor: "+valor);
            if(array[pos]==valor)
                existe=true;
            else
                pos++;
        }
        return existe;
    }

    function transFloat(numeroString){
        var numero = numeroString.replace(',','');
        return parseFloat(numero);
    }
});

