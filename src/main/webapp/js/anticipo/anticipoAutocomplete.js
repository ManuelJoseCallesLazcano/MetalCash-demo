$(document).ready(function() {
//    $("#agregar").prop("disabled",true);
//    $("#quitar").prop("disabled",true);
    //definir tabla de lotes
    $("#lotesAsignados").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["LOTE","recepcionId","CLIENTE","EMPRESA","FECHA REC.","PESO BRUTO KGS","TIPO MATERIAL","ANTICIPO SUGERIDO"],
        colModel:[ {name:'lote',index:'lote', width:70},
            {name:'recepcionId',index:'recepcionId', width:5, hidden: true},
            {name:'nombreCliente',index:'nombreCliente', width:150},
            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:80},
            {name:'pesoBruto',index:'pesoBruto', width:80},
            {name:'tipoDeMaterial',index:'tipoDeMaterial', width:100},
            {name:'anticipoSugerido',index:'anticipoSugerido', width:100}
        ],
        multiselect: false,
        caption: "LOTES ASIGNADOS"
    });
    //crear tabla de lotes
    crearTabla();

    $("#agregar").bind("click",agregarLote);
    $("#quitar").bind("click",eliminarLote);
    $("#primerAnticipo,#segundoAnticipo,#tercerAnticipo").bind("keyup",calcularTotalAnticipo);

    function agregarLote(){
        $.ajax({
            url: "/demo-liquidaciones/recepcionDeComplejo/recepcionesAnticipoJSON",
            dataType: 'json',
            data: {
                tipoDeMineral: $('#tipoDeMineral').val(),
                loteInicial: $('#loteInicial').val(),
                loteFinal: $('#loteFinal').val(),
                depositoId: $('#depositoId').val(),
                empresaId: $('#empresa\\.id').val(),
                clienteId: $('#cliente\\.id').val()
            },
            success: function(data) {
                $('#lotes').val(data.lotes);
                crearTabla();
            },
            error: function(request, status, error) {

            }
        });
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

    function calcularTotalAnticipo(){
        var primerAnticipo = 0;
        var segundoAnticipo = 0;
        var tercerAnticipo = 0;
        //if(isNaN($("#primerAnticipo").val())||$("#primerAnticipo").val()==""){
        if(isNaN(transFloat($("#primerAnticipo").val()))||$("#primerAnticipo").val()==""){
            primerAnticipo=0;
            $("#primerAnticipo").val(0);
        }else{
            primerAnticipo=transFloat($("#primerAnticipo").val())
        }
        if(isNaN($("#segundoAnticipo").val())||$("#segundoAnticipo").val()==""){
            segundoAnticipo=0;
            $("#segundoAnticipo").val(0);
        }else{
            segundoAnticipo=transFloat($("#segundoAnticipo").val())
        }
        if(isNaN($("#tercerAnticipo").val())||$("#tercerAnticipo").val()==""){
            tercerAnticipo=0;
            $("#tercerAnticipo").val(0);
        }else{
            tercerAnticipo=transFloat($("#tercerAnticipo").val())
        }
        //alert("contenido de primerAnticipo:"+primerAnticipo+"\nsegundoAnticipo:"+segundoAnticipo+"\ntercerAnticipo:"+tercerAnticipo);
        var total = primerAnticipo + segundoAnticipo + tercerAnticipo;
        var totalPagado = transFloat($("#totalPagado").val());
        var totalPorPagar = total - totalPagado;

        $("#totalPorPagar").val(totalPorPagar);
        $("#totalAnticipos").val(total);
        var numero = parseInt($( "#totalAnticipos" ).val());
        if(numero>=1000&&numero<2000)
            $( "#literalTotalAnticipos" ).val("UN "+CifrasEnLetras.convertirNumeroEnLetras(numero).toUpperCase()+" 00/100 BOLIVIANOS");
        else
            $( "#literalTotalAnticipos" ).val(CifrasEnLetras.convertirNumeroEnLetras(numero).toUpperCase()+" 00/100 BOLIVIANOS");
    }

    function transFloat(numeroString){
        var numero = numeroString.replace(',','');
        return parseFloat(numero);
    }
});

