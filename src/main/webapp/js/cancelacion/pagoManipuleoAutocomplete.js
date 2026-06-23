$(document).ready(function() {
    setInterval(function(){
        actualizarContenido();
    }, 100);
    cargarCostosManipuleo();
    clienteAutocomplete();

    $("#lotesAsignados").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["LOTE","recepcionId","FECHA REC.","P. BRUTO","TIPO MAT.","PESADA VACIADA","CARGUIO MAQUINA","EMBOLSADA ARRUMADA","SOLO COMUNEADA","SOLO VACIADA","SOLO PESADA","SOLO EMBOLSADA","COSTO MANIPULEO"],
        colModel:[ {name:'lote',index:'lote', width:84},
            {name:'recepcionId',index:'recepcionId', width:5, hidden: true},
            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:70},
            {name:'pesoBruto',index:'pesoBruto', width:60},
            {name:'tipoDeMaterial',index:'tipoDeMaterial', width:80},
            {name:'pesadaVaciada',index:'pesadaVaciada', width:68, edittype:"checkbox",editoptions: {value:"SI:NO"},editable: true,formatter: "checkbox", formatoptions: {disabled : false} },
            {name:'carguioMaquina',index:'carguioMaquina', width:68, edittype:"checkbox",editoptions: {value:"SI:NO"},editable: true,formatter: "checkbox", formatoptions: {disabled : false} },
            {name:'embolsadaArrumada',index:'embolsadaArrumada', width:68, edittype:"checkbox",editoptions: {value:"SI:NO"},editable: true,formatter: "checkbox", formatoptions: {disabled : false} },
            {name:'soloComuneada',index:'soloComuneada', width:68, edittype:"checkbox",editoptions: {value:"SI:NO"},editable: true,formatter: "checkbox", formatoptions: {disabled : false} },
            {name:'soloVaciada',index:'soloVaciada', width:68, edittype:"checkbox",editoptions: {value:"SI:NO"},editable: true,formatter: "checkbox", formatoptions: {disabled : false} },
            {name:'soloPesada',index:'soloPesada', width:68, edittype:"checkbox",editoptions: {value:"SI:NO"},editable: true,formatter: "checkbox", formatoptions: {disabled : false} },
            {name:'soloEmbolsada',index:'soloEmbolsada', width:68, edittype:"checkbox",editoptions: {value:"SI:NO"},editable: true,formatter: "checkbox", formatoptions: {disabled : false} },
            {name:'costoManipuleo',index:'costoManipuleo', width:80} ],
        beforeSelectRow: function (rowid, e) {
            var $target = $(e.target), $td = $target.closest("td"),
                iCol = $.jgrid.getCellIndex($td[0]),
                colModel = $(this).jqGrid("getGridParam", "colModel");
            if (iCol >= 0 && $target.is(":checkbox")) {
//                alert("checkbox is " +
//                    ($target.is(":checked")? "checked" : "unchecked") +
//                    " in the column \"" + colModel[iCol].name +
//                    "\" in the row with rowid=\"" + rowid + "\"");
                var rowData = $('#lotesAsignados').jqGrid('getRowData', rowid);
                var _costoManipuleo=transFloat(rowData.costoManipuleo);
                var _pesadaVaciada=0;
                var _carguioMaquina=0;
                var _embolsadaArrumada=0;
                var _soloComuneada=0;
                var _soloVaciada=0;
                var _soloPesada=0;
                var _soloEmbolsada=0;

                switch(colModel[iCol].name){
                    case "pesadaVaciada":
                        _pesadaVaciada= $target.is(":checked")? transFloat($("#pesadaVaciada").val()):-transFloat($("#pesadaVaciada").val());
                        _costoManipuleo=_costoManipuleo+_pesadaVaciada;
                        break;
                    case "carguioMaquina":
                        _carguioMaquina= $target.is(":checked")? transFloat($("#carguioMaquina").val()):-transFloat($("#carguioMaquina").val());
                        _costoManipuleo=_costoManipuleo+_carguioMaquina;
                        break;
                    case "embolsadaArrumada":
                        _embolsadaArrumada= $target.is(":checked")? transFloat($("#embolsadaArrumada").val()):-transFloat($("#embolsadaArrumada").val());
                        _costoManipuleo=_costoManipuleo+_embolsadaArrumada;
                        break;
                    case "soloComuneada":
                        _soloComuneada= $target.is(":checked")? transFloat($("#soloComuneada").val()):-transFloat($("#soloComuneada").val());
                        _costoManipuleo=_costoManipuleo+_soloComuneada;
                        break;
                    case "soloVaciada":
                        _soloVaciada= $target.is(":checked")? transFloat($("#soloVaciada").val()):-transFloat($("#soloVaciada").val());
                        _costoManipuleo=_costoManipuleo+_soloVaciada;
                        break;
                    case "soloPesada":
                        _soloPesada= $target.is(":checked")? transFloat($("#soloPesada").val()):-transFloat($("#soloPesada").val());
                        _costoManipuleo=_costoManipuleo+_soloPesada;
                        break;
                    case "soloEmbolsada":
                        _soloEmbolsada= $target.is(":checked")? transFloat($("#soloEmbolsada").val()):-transFloat($("#soloEmbolsada").val());
                        _costoManipuleo=_costoManipuleo+_soloEmbolsada;
                        break;
                }
                rowData.costoManipuleo = _costoManipuleo;
                $('#lotesAsignados').jqGrid('setRowData', rowid, rowData);
            }
            return true;
        },
        multiselect: false,
        caption: "LOTES A PAGAR"
    });
    //crear tabla de lotes
    crearTabla();

    $("#agregar").bind("click",agregarLote);
    $("#quitar").bind("click",eliminarLote);
    $("#todosPesadaVaciada").bind("click",manejarTodosPesadaVaciada);

    function agregarLote(){
        $.ajax({
            url: "/demo-liquidaciones/pagoManipuleo/recepcionesJSON",
            dataType: 'json',
            data: {
                depositoId: $('#deposito').val()
            },
            success: function(data) {
                $('#lotes').val(data.lotes);
                crearTabla();
            },
            error: function(request, status, error) {

            }
        });
    }

    function manejarTodosPesadaVaciada(){
        var lotes = JSON.stringify($("#lotesAsignados").jqGrid('getGridParam','data'));
        $("#lotes").val(lotes);

        var mydata = $("#lotes").val();
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);

        if($("#todosPesadaVaciada").is(":checked")){
//            alert("checked!");
            for(var i=0;i<mydata.length;i++){
                mydata[i].pesadaVaciada="SI";
            }
        }else{
//            alert("not checked...");
            for(var i=0;i<mydata.length;i++){
                mydata[i].pesadaVaciada="NO";
            }
        }
        $("#lotesAsignados").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            $("#lotesAsignados").jqGrid('addRowData',i+1,mydata[i]);
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
            $("#lotesAsignados").jqGrid('addRowData',i+1,mydata[i]);
        }
    }

    /*cuando se elimina un lote se actualizan las sumas.
    *quiza si se tiene una funcion que modifique una columna especifica
     * a un valor cualquiera de SI o NO entonces pueda actualizar la suma
      * segun la seleccion*/

    function eliminarLote(){
        var filaId = parseInt($('#lotesAsignados').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
//        var filas = $('#lotesAsignados').jqGrid('getGridParam','selarrrow'); //restar uno porque la funcion considera el indice inicial como 1
//        for(var i=0; i<filas.length; i++)
//            alert("fila: "+filas[i]);

        var mydata = $.parseJSON($("#lotes").val());
        var nuevoMydata = new Array();

        for(var i=0;i<mydata.length;i++){
//            alert("analizando: "+mydata[i].lote+" i="+i+" filaId="+filaId);
            if(i!=filaId) // copiar todas las filas menos la seleccionada
                nuevoMydata.push(mydata[i]);
        }
        //actualizar la cadena JSON en el campo de texto
        $("#lotes").val(JSON.stringify(nuevoMydata));
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

    function clienteAutocomplete(){
        $("#ci").autocomplete({
            source: function(request, response){
                $.ajax({
                    url: "/demo-liquidaciones/pagoManipuleo/nombreCobradorJSON", // /demo-liquidaciones/recepcionDeComplejo/create
                    data: request,
                    success: function(data){
                        response(data); // set the response
                    },
                    error: function(){
                    }
                });
            },
            minLength: 1, // triggered only after minimum 1 characters have been entered.
            select: function(event, ui) { // event handler when user selects a company from the list.
                $("#nombreCobrador").val(ui.item.nombreCobrador);
            },
            response: function(event, ui) {
                if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                    $.jGrowl("No existen resultados para el CI buscado.");
                }
            }
        });
    }

    function cargarCostosManipuleo(){
        $.ajax({
            url: "/demo-liquidaciones/parametrosGenerales/costosManipuleo",
            dataType: 'json',
            data: {
            },
            success: function(data) {
                $('#pesadaVaciada').val(data.pesadaVaciada);
                $('#carguioMaquina').val(data.carguioMaquina);
                $('#embolsadaArrumada').val(data.embolsadaArrumada);
                $('#soloComuneada').val(data.soloComuneada);
                $('#soloVaciada').val(data.soloVaciada);
                $('#soloPesada').val(data.soloPesada);
                $('#soloEmbolsada').val(data.soloEmbolsada);
            },
            error: function(request, status, error) {
            }
        });
    }

    function actualizarContenido(){
        var lotes = JSON.stringify($("#lotesAsignados").jqGrid('getGridParam','data'));
        $("#lotes").val(lotes);

        var mydata = $("#lotes").val();
        var total=0;
        var descripcion="";
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);

        for(var i=0;i<mydata.length;i++){
            descripcion=descripcion+mydata[i].lote+", ";
            total+=mydata[i].costoManipuleo;
        }

        var totalPagable = total;

        var totalPagableEntero = ""+parseInt(totalPagable);
        var totalPagableDecimal = getDecimal(totalPagable);

        $("#descripcion").val("POR LOTES: "+descripcion.substring(0,descripcion.length-2));//para quitar la coma y el espacio del final
        $("#total").val((isNaN(total)) ?"?":toFixed(total,2).toString());
        $("#totalPagable").val((isNaN(totalPagable)) ?"?":toFixed(totalPagable,2).toString());

        if(total>=1000&&total<2000)
            $( "#totalPagableLiteral" ).val("UN "+CifrasEnLetras.convertirNumeroEnLetras(totalPagableEntero).toUpperCase()+" "+totalPagableDecimal+"/100 BOLIVIANOS");
        else
            $( "#totalPagableLiteral" ).val(CifrasEnLetras.convertirNumeroEnLetras(totalPagableEntero).toUpperCase()+" "+totalPagableDecimal+"/100 BOLIVIANOS");
    }

    function transFloat(numeroString){
        var numero = numeroString.replace(',','');
        return parseFloat(numero);
    }

    function toFixed( number, precision ) {
        var multiplier = Math.pow( 10, precision );
        return Math.round( number * multiplier ) / multiplier;
    }

    function getDecimal(numero){
        var n=Math.round((numero*100)%100);
        var ns=""+n;
        return (ns.length<2)?"0"+ns:ns;
    }
});

