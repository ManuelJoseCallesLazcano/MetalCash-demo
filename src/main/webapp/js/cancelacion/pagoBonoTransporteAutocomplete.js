$(document).ready(function() {
    var vista=$('#vista').val();
    var tipoSeleccionOriginal=$('#tipoSeleccion').val();
    var empresaOriginal=$('#empresa').val();
    var cuadrillaOriginal=$('#cuadrilla').val();

    cargarNumeroMesesPagables();
    nombreCobradorAutocomplete();
    controlTipoSeleccion();

    //definir tabla de lotes
    $("#acumulacionPorMesTabla").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["FECHA","CANTIDAD KG"],
        colModel:[ {name:'fecha',index:'fecha', width:150},
            {name:'cantidadAcumulada',index:'cantidadAcumulada', width:200} ],
        multiselect: true,
        caption: "ACUMULACION POR MESES"
    });
    //crear tabla de lotes
    crearTabla();

    $("#agregar").bind("click",acumular);
    $("#quitar").bind("click",eliminarLote);
    $("#tipoSeleccion").bind("change",controlTipoSeleccion);
    $("#empresa").bind("change",filtrarCuadrillas);

    function controlTipoSeleccion(){
        var tipo="";
        if(vista=="create"){
            tipo=$('#tipoSeleccion').val();
            if(tipo=="INDIVIDUAL"){
                $('#_nombreCliente').show();
                $('#_empresa').hide();
                $('#_nombreEmpresa').show();
                $('#_cuadrilla').hide();
                $('#nombreCliente').val("");
            }
            if(tipo=="CUADRILLA"){
                $('#_nombreCliente').hide();
                $('#_empresa').show();
                $('#_nombreEmpresa').hide();
                $('#_cuadrilla').show();
                filtrarCuadrillas();
                $('#nombreCliente').val("-");
            }
        }else{
            $("#tipoSeleccion").val(tipoSeleccionOriginal);
            tipo=$('#tipoSeleccion').val();
            if(tipo=="INDIVIDUAL"){
                $('#_nombreCliente').show();
                $('#_empresa').hide();
                $('#_nombreEmpresa').show();
                $('#_cuadrilla').hide();
                $('#nombreCliente').attr("readonly","readonly");
            }
            if(tipo=="CUADRILLA"){
                $('#_nombreCliente').hide();
                $('#_empresa').show();
                $('#_nombreEmpresa').hide();
                $('#_cuadrilla').show();
                filtrarCuadrillas();
                $('#nombreCliente').val("-");
            }
        }
    }

    function cargarNumeroMesesPagables(){
        $.ajax({
            url: "/demo-liquidaciones/parametrosGenerales/mesesPagablesBonoTransporte",
            cache: false,
            success: function(data) {
                $("#numeroMesesPagables").val(data.mesesPagables);
            }
        });
    }

    function filtrarCuadrillas(){
        if(vista=="edit"){
            $('#empresa').val(empresaOriginal);
        }
        $.ajax({
            url: "/demo-liquidaciones/empresa/cuadrillasDeEmpresaParaBono",
            data: "id=" + $("#empresa").val(),
            cache: false,
            success: function(html) {
                $("#cuadrilla").html(html);
                $("#nombreEmpresa").val($("#empresa option:selected").text());
                if(vista=="edit"){
                    $('#cuadrilla').val(cuadrillaOriginal);
                }
            }
        });
    }

    function acumular(){
        $.ajax({
            url: "/demo-liquidaciones/pagoBonoTransporte/acumulacionPorMesJSON",
            dataType: 'json',
            data: {
                automovilId: $('#automovil').val(),
                numeroMesesPagables: $('#numeroMesesPagables').val(),
                fechaInicial_year: $('#fechaInicial_year').val(),
                fechaInicial_month: $('#fechaInicial_month').val(),
                fechaFinal_year: $('#fechaFinal_year').val(),
                fechaFinal_month: $('#fechaFinal_month').val()
            },
            success: function(data) {
                $('#acumulacionPorMes').val(data.acumulados);
                $('#lotesBono').val(data.lotes);
                $('#bonoPorTonelada').val(data.bonoPorTonelada);
                $('#tipoDeCambio').val(data.tipoDeCambio);
                $('#totalKilosBrutos').val(data.pesoBruto);//
                $('#totalPagable').val(data.totalPagable);//

                crearTabla();
                $('#numeroMesesAcumulados').val(data.numeroMesesAcumulados);
                //notificacionCancelados
                if(data.notificacionCancelados!="")
                    $.notify("Los siguientes periodos ya han sido pagados:\n"+data.notificacionCancelados,{autoHide: false,clickToHide: true,className: "warn"});
            },
            error: function(request, status, error) {

            }
        });
    }

    function crearTabla(){
        var lotes=""
        var mydata = $("#acumulacionPorMes").val();
        var cantidadAcumulada=0;
        var numeroMesesAcumulados=0;
        if(mydata==""||mydata==null)
            mydata = [];
        else
            mydata = $.parseJSON(mydata);
        $("#acumulacionPorMesTabla").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            lotes=lotes+mydata[i].lote+", ";
            cantidadAcumulada+=mydata[i].cantidadAcumulada;
            numeroMesesAcumulados=(mydata[i].cantidadAcumulada!=0)?numeroMesesAcumulados+1:numeroMesesAcumulados;
//            total+=mydata[i].costoDeTransporte;
            $("#acumulacionPorMesTabla").jqGrid('addRowData',i+1,mydata[i]);
        }

        var bonoPorTonelada = $("#bonoPorTonelada").val();
        var tipoDeCambio = $("#tipoDeCambio").val();
        var totalPagable = $("#totalPagable").val();
        var totalPagableEntero = ""+parseInt(totalPagable);
        var totalPagableDecimal = getDecimal(totalPagable);

//        $("#descripcion").val("POR LOTES: "+lotes.substring(0,lotes.length-2));//para quitar la coma y el espacio del final
        $("#numeroMesesAcumulados").val((isNaN(numeroMesesAcumulados)) ?"?":toFixed(numeroMesesAcumulados,2).toString());
        $("#totalKilosNetosSecos").val((isNaN(cantidadAcumulada)) ?"?":toFixed(cantidadAcumulada,2).toString());
        $("#totalPagable").val((isNaN(totalPagable)) ?"?":toFixed(totalPagable,2).toString());

        if(totalPagableEntero>=1000&&totalPagableEntero<2000)
            $( "#totalPagableLiteral" ).val("UN "+CifrasEnLetras.convertirNumeroEnLetras(totalPagableEntero).toUpperCase()+" "+totalPagableDecimal+"/100 BOLIVIANOS");
        else
            $( "#totalPagableLiteral" ).val(CifrasEnLetras.convertirNumeroEnLetras(totalPagableEntero).toUpperCase()+" "+totalPagableDecimal+"/100 BOLIVIANOS");
    }

    function eliminarLote(){
        //var filaId = parseInt($('#acumulacionPorMesTabla').jqGrid('getGridParam','selarrrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        var filas = $('#acumulacionPorMesTabla').jqGrid('getGridParam','selarrrow'); //restar uno porque la funcion considera el indice inicial como 1

        var mydata = $.parseJSON($("#acumulacionPorMes").val());
        var nuevoMydata = new Array()

        for(var i=0;i<mydata.length;i++){
            if(!existeValor(i+1,filas)) // copiar todas las filas menos la seleccionada
                nuevoMydata.push(mydata[i]);
        }
        //actualizar la cadena JSON en el campo de texto
        $("#acumulacionPorMes").val(JSON.stringify(nuevoMydata));
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

    function nombreCobradorAutocomplete(){
        $("#ci").autocomplete({
            source: function(request, response){
                $.ajax({
                    url: "/demo-liquidaciones/pagoBonoTransporte/nombreCobradorJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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

    $("#nombreCliente").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/pagoBonoTransporte/clientesPorNombreJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
            $("#cliente").val(ui.item.clienteId); // actualizando campo oculto para id de cliente
            $("#empresa").val(ui.item.empresaId); // actualizando campo oculto para id de empresa
            $("#nombreEmpresa").val(ui.item.nombreEmpresa);
            $("#bonoPorTonelada").val(ui.item.bonoPorTonelada);
            $("#tipoDeCambio").val(ui.item.tipoDeCambio);
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("No existen resultados para el NOMBRE buscado.");
            }
        }
    });

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

