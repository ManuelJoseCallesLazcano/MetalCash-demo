$(document).ready(function() {
    $("#detalleTabla").jqGrid({
        datatype: "local",
        height: 300,
        colNames: ["movimientoCajaId","FECHA","ingresoId","egresoId","CI","NOMBRE","DETALLE","DEBE","HABER","SALDO","cajaId","usuarioId"],
        colModel:[
            {name:'movimientoCajaId',index:'movimientoCajaId', width:100, hidden: true},
            {name:'fechaMovimiento',index:'fechaMovimiento', width:120},
            {name:'ingresoId',index:'ingresoId', width:100, hidden: true},
            {name:'egresoId',index:'egresoId', width:100, hidden: true},
            {name:'ci',index:'ci', width:80},
            {name:'nombre',index:'nombre', width:150},
            {name:'concepto',index:'concepto', width:200},
            {name:'debe',index:'debe', width:70, align: "right"},
            {name:'haber',index:'haber', width:70, align: "right"},
            {name:'saldo',index:'saldo', width:70, align: "right"},
            {name:'cajaId',index:'cajaId', width:100, hidden: true},
            {name:'usuarioId',index:'usuarioId', width:100, hidden: true}
            ],
        multiselect: false,
        rownumbers: true,
        caption: "MOVIMIENTOS A CONSOLIDAR"
    });

    //$( "#importeDialogo" ).dialog({
    //    height: 100,
    //    width: 400,
    //    position: { my: "right top", at: "right top"}
    //});
    $('#filtrar').bind("click",cargarMovimientos);

    function cargarMovimientos(){
        $.ajax({
            url: "/demo-liquidaciones/movimientoCaja/movimientoCajaJSON",
            dataType: 'json',
            data: {
                fechaCierreCaja_day: $('#fechaCierreCaja_day').val(),
                fechaCierreCaja_month: $('#fechaCierreCaja_month').val(),
                fechaCierreCaja_year: $('#fechaCierreCaja_year').val()
            },
            success: function(data) {
                //JSON.stringify
                //$.parseJSON
                $('#detalle').val(JSON.stringify(data));
                crearTabla();
            },
            error: function(request, status, error) {
            }
        });
    }

    //setInterval(cargarMovimientos, 1000);
    //
    //function cargarMovimientos(){
    //    $.ajax({
    //        url: "/Liquidaciones/cierreCaja/fechaServidor",
    //        dataType: 'json',
    //        data: {
    //        },
    //        success: function(data) {
    //            $('#fechaCierreCajaServidor').val(data.fecha);
    //        },
    //        error: function(request, status, error) {
    //        }
    //    });
    //
    //    $.ajax({
    //        url: "/Liquidaciones/movimientoCaja/movimientoCajaJSON",
    //        dataType: 'json',
    //        data: {
    //        },
    //        success: function(data) {
    //            //JSON.stringify
    //            //$.parseJSON
    //            $('#detalle').val(JSON.stringify(data));
    //            crearTabla();
    //        },
    //        error: function(request, status, error) {
    //        }
    //    });
    //}

    function crearTabla(){
        var objeto=null;
        var mydata = $("#detalle").val();
        /*debeTotal()
         haberTotal()
         saldoTotal()*/
        var debeTotal = 0;
        var haberTotal = 0;
        var saldoTotal = 0;
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);
        $("#detalleTabla").jqGrid("clearGridData", true);

        if(mydata.length>0){
            for(var i=0;i<mydata.length;i++){
                objeto = mydata[i];
                debeTotal += objeto.debe;
                haberTotal += objeto.haber;
                $("#detalleTabla").jqGrid('addRowData',i+1,objeto);
            }
            saldoTotal = mydata[mydata.length-1].saldo;
        }
        //$( "#concepto" ).val(concepto.substring(0,concepto.length-2));
        //$("#descripcion").val("POR LOTES: "+lotes.substring(0,lotes.length-2));//para quitar la coma y el espacio del final
        //necesario redondear
        $( "#debeTotal" ).val(redondear2(debeTotal));
        $( "#haberTotal" ).val(redondear2(haberTotal));
        $( "#saldoTotal" ).val(redondear2(saldoTotal));
    }

    function cuentasAutocomplete(){
        $("#identificador").autocomplete({
            source: function(request, response){
                $.ajax({
                    url: "/Liquidaciones/cuentasPorPagar/cuentasPorPagarJSON", // /Liquidaciones/recepcionDeComplejo/create
                    data: {
                        operacion: $("#operacion").val(),
                        identificador: $("#identificador").val()
                    },
                    success: function(data){
                        response(data); // set the response
                    },
                    error: function(){
                    }
                });
            },
            minLength: 1, // triggered only after minimum 1 characters have been entered.
            select: function(event, ui) { // event handler when user selects a company from the list.
                /*mapaCuentas.put("cuentasPorPagarId", cuenta.id)
                 mapaCuentas.put("idOperacion", cuenta.idOperacion)
                 mapaCuentas.put("importe", cuenta.importe)*/
                $("#idCuentasPorPagar").val(ui.item.cuentasPorPagarId);//
                $("#idIdentificador").val(ui.item.idOperacion);//
                $("#glosa").val(ui.item.glosa);//
                $("#beneficiario").val(ui.item.beneficiario);//
                $("#valorPagable").val(ui.item.importe);//
            },
            response: function(event, ui) {
                if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                    //$.jGrowl("No existen resultados para el CI buscado.");
                    $.notify("No existen resultados para la operacion buscada",{autoHide: true,clickToHide: true,className: "error"});
                }
            }
        });
    }

    function transFloat(numeroString){
        var numero = numeroString.replace(',','');
        return parseFloat(numero);
    }

    function redondear2( number ) {
        //var multiplier = Math.pow( 10, precision );
        var multiplier = Math.pow( 10, 2 );
        return Math.round( number * multiplier ) / multiplier;
    }
});

