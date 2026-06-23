$(document).ready(function() {
    $(".chosen-select").chosen({
        search_contains: true,
        width: '450px'
    });

    $("#precioTonelada").numeric({
        allowMinus   : false,
        allowThouSep : false,
        maxDecimalPlaces:2
    });

    clienteAutocomplete();

    seleccionarDespliegue();

    $("#precioTonelada" ).keyup(calcular);

    $("#solicitante,#empresa,#automovil" ).change(function() {
        recuperarUltimoSaldo();
        seleccionarDespliegue();
    });

    function recuperarUltimoSaldo(){
        $.ajax({
            url: "/demo-liquidaciones/estadoCuentaTransporte/obtenerSaldoJSON",
            dataType: 'json',
            data: {
                solicitante: $('#solicitante').val(),
                empresaId: $('#empresa').val(),
                automovilId: $('#automovil').val()
            },
            success: function(data) {
                // var saldo = parseFloat(""+data.saldoCuenta);
                // saldo=(saldo<0)?-1*saldo:saldo
                // $('#totalAnticipos').val(saldo);
                // $.notify(data.notificacion,{autoHide: false,clickToHide: true,className: "info"});
                crearTabla();
            },
            error: function(request, status, error) {

            }
        });
    }

    //definir tabla de lotes
    $("#lotesAsignados").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["LOTE","recepcionId","EMPRESA","PROVEEDOR","CHOFER","PLACA","FECHA REC.","precioTonelada","P. BRUTO KG","TIPO MAT.","COSTO TRANS.","anticipoTransporte"],
        colModel:[ {name:'lote',index:'lote', width:70},
            {name:'recepcionId',index:'recepcionId', width:5, hidden: true},
            {name:'nombreEmpresa',index:'nombreEmpresa', width:160},
            {name:'nombreCliente',index:'nombreCliente', width:130},
            {name:'nombreChofer',index:'nombreChofer', width:130},
            {name:'placaAutomovil',index:'placaAutomovil', width:60},
            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:70},
            {name:'precioTonelada',index:'precioTonelada', width:60, hidden: true},
            {name:'pesoBruto',index:'pesoBruto', width:60},
            {name:'tipoDeMaterial',index:'tipoDeMaterial', width:80},
            {name:'costoDeTransporte',index:'costoDeTransporte', width:80},
            {name:'anticipoTransporte',index:'anticipoTransporte', width:80, hidden: true}
        ],
        multiselect: false,
        onSelectRow: function(id){
            cargarDetalle();
        },
        caption: "LOTES CON TRANSPORTE POR PAGAR"
    });
    //crear tabla de lotes
    crearTabla();

    $("#agregar").bind("click",agregarLote);
    $("#quitar").bind("click",eliminarLote);
    $("#actualizar").bind("click",actualizarLotes);

    function agregarLote(){
        $.ajax({
            url: "/demo-liquidaciones/recepcionDeComplejo/recepcionesTransporteJSON",
            dataType: 'json',
            data: {
                solicitante: $('#solicitante').val(),
                loteInicial: $('#loteInicial').val(),
                loteFinal: $('#loteFinal').val(),
                depositoId: $('#depositoId').val(),
                empresaId: $('#empresa').val(),
                automovilId: $('#automovil').val()
            },
            success: function(data) {
                $('#lotes').val(data.lotes);
                crearTabla();
            },
            error: function(request, status, error) {

            }
        });
    }

    function actualizarLotes(){
        $.ajax({
            url: "/demo-liquidaciones/recepcionDeComplejo/recepcionesTransporteActualizadoJSON",
            dataType: 'json',
            data: {
                lotes: $('#lotes').val()
            },
            success: function(data) {
                $('#lotes').val(data.lotes);
                crearTabla();
            },
            error: function(request, status, error) {

            }
        });
    }

    function cargarDetalle(){
        var filaId = parseInt($('#lotesAsignados').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        var mydata = $.parseJSON($("#lotes").val());
        var fila = mydata[filaId];
        //alert("fila: "+fila);
        $("#recepcionId").val(fila.recepcionId);
        $("#pesoBruto").val(fila.pesoBruto);
        $("#precioTonelada").val(fila.precioTonelada);
        $("#totalAnticipos").val(fila.anticipoTransporte);
        $("#descripcion").val("POR EL LOTE: "+fila.lote);
        calcular()
    }

    function calcular(){
        let pesoBruto = transFloat($("#pesoBruto").val())
        let precioTonelada = transFloat($("#precioTonelada").val())
        let totalAnticipos = transFloat($("#totalAnticipos").val())
        let total = (pesoBruto*precioTonelada)*6.86/1000
        let totalPagable = total - totalAnticipos

        $("#total").val(isNaN(total)?"?":toFixed(total,2))
        $("#totalPagable").val(isNaN(totalPagable)?"?":toFixed(totalPagable,2))

        var totalPagableEntero = ""+parseInt(totalPagable);
        var totalPagableDecimal = getDecimal(totalPagable);
        if(totalPagable>=1000&&totalPagable<2000)
            $( "#totalPagableLiteral" ).val("UN "+CifrasEnLetras.convertirNumeroEnLetras(totalPagableEntero).toUpperCase()+" "+totalPagableDecimal+"/100 BOLIVIANOS");
        else
            $( "#totalPagableLiteral" ).val(CifrasEnLetras.convertirNumeroEnLetras(totalPagableEntero).toUpperCase()+" "+totalPagableDecimal+"/100 BOLIVIANOS");
    }

    function crearTabla(){
        var lotes=""
        var mydata = $("#lotes").val();
        var total=0;
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);
        $("#lotesAsignados").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            lotes=lotes+mydata[i].lote+", ";
            total+=mydata[i].costoDeTransporte;
            $("#lotesAsignados").jqGrid('addRowData',i+1,mydata[i]);
        }

        // var totalAnticipos = transFloat($("#totalAnticipos").val());
        // var totalPagable = total-totalAnticipos;
        //
        // var totalPagableEntero = ""+parseInt(totalPagable);
        // var totalPagableDecimal = getDecimal(totalPagable);
        //
        // $("#descripcion").val("POR LOTES: "+lotes.substring(0,lotes.length-2));//para quitar la coma y el espacio del final
        // $("#total").val((isNaN(total)) ?"?":toFixed(total,2).toString());
        // $("#totalPagable").val((isNaN(totalPagable)) ?"?":toFixed(totalPagable,2).toString());
        //
        // if(total>=1000&&total<2000)
        //     $( "#totalPagableLiteral" ).val("UN "+CifrasEnLetras.convertirNumeroEnLetras(totalPagableEntero).toUpperCase()+" "+totalPagableDecimal+"/100 BOLIVIANOS");
        // else
        //     $( "#totalPagableLiteral" ).val(CifrasEnLetras.convertirNumeroEnLetras(totalPagableEntero).toUpperCase()+" "+totalPagableDecimal+"/100 BOLIVIANOS");
    }

    function eliminarLote(){
        //var filaId = parseInt($('#lotesAsignados').jqGrid('getGridParam','selarrrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        var filas = $('#lotesAsignados').jqGrid('getGridParam','selarrrow'); //restar uno porque la funcion considera el indice inicial como 1
//        for(var i=0; i<filas.length; i++)
//            alert("fila: "+filas[i]);

        var mydata = $.parseJSON($("#lotes").val());
        var nuevoMydata = new Array()

        for(var i=0;i<mydata.length;i++){
            if(!existeValor(i+1,filas)) // copiar todas las filas menos la seleccionada
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
                    url: "/demo-liquidaciones/pagoTransporte/nombreCobradorJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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

    function seleccionarDespliegue(){
        var e=$("#solicitante").val();
        if(e=="Empresa"){
            $("#_empresa").show();
            $("#_automovil").hide();
        }
        if(e=="Particular"){
            $("#_empresa").hide();
            $("#_automovil").show();
        }
    }
});

