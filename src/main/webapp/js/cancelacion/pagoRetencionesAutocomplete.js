$(document).ready(function() {
    var totalesRetencionesSeleccionadas = new Array();
    var lotesRetencionesSeleccionadas = new Array();

    clienteAutocomplete();

    //definir tabla de retenciones
    $("#tablaRetenciones").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["RETENCION"],
        colModel:[ {name:'retencion',index:'retencion', width:200} ],
        caption: "RETENCIONES"
    });
    //definir tabla de retenciones seleccionadas
    $("#tablaRetencionesSeleccionadas").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["RETENCION","TOTAL"],
        colModel:[ {name:'retencion',index:'retencion', width:200},
            {name:'total',index:'total', width:100} ],
        caption: "RETENCIONES SELECCIONADAS"
    });

    $("#buscarRetenciones").bind("click",buscarRetenciones);
    $("#adicionarRetencion").bind("click",adicionarRetencion);

    crearTablaRetencionesSeleccionadas();

    function buscarRetenciones(){
        $.ajax({
            url: "/demo-liquidaciones/retencionPorPagarComplejo/buscarRetencionesJSON",
            dataType: 'json',
            data: {
                periodo_year: $('#periodo_year').val(),
                periodo_month: $('#periodo_month').val(),
                quincena: $('#quincena').val(),
                empresaId: $('#empresa').val()
            },
            success: function(data) {
                $('#retenciones').val(data.retenciones);
                crearTablaRetenciones();
            },
            error: function(request, status, error) {
            }
        });
    }

    function adicionarRetencion(){
        deshabilitarControles();

        var filaId = parseInt(jQuery('#tablaRetenciones').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        var mydata = $.parseJSON($("#retenciones").val());
        var nuevoMydata = new Array()

        var retencionSeleccionada = mydata[filaId].retencion;
        $.ajax({
            url: "/demo-liquidaciones/retencionPorPagarComplejo/adicionarRetencionSeleccionadaJSON",
            dataType: 'json',
            data: {
                periodo_year: $('#periodo_year').val(),
                periodo_month: $('#periodo_month').val(),
                quincena: $('#quincena').val(),
                empresaId: $('#empresa').val(),
                retencionSeleccionada: retencionSeleccionada
            },
            success: function(data) {
                totalesRetencionesSeleccionadas.push(data.totalRetencion);
                lotesRetencionesSeleccionadas.push(data.lotesRetenciones);
                $("#retencionesSeleccionadas").val(JSON.stringify(totalesRetencionesSeleccionadas));
                $("#lotes").val(JSON.stringify(lotesRetencionesSeleccionadas));
                crearTablaRetencionesSeleccionadas();
                //eliminar la retencion seleccionada para eliminar duplicidad
                for(var i=0;i<mydata.length;i++){
                    if(filaId!=i) // copiar todas las filas menos la seleccionada
                        nuevoMydata.push(mydata[i]);
                }
                $("#retenciones").val(JSON.stringify(nuevoMydata));
                crearTablaRetenciones();
            },
            error: function(request, status, error) {

            }
        });
    }

    function crearTablaRetenciones(){
        var mydata = $("#retenciones").val();
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);
        $("#tablaRetenciones").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            $("#tablaRetenciones").jqGrid('addRowData',i+1,mydata[i]);
        }
    }

    function crearTablaRetencionesSeleccionadas(){
        var mydata = $("#retencionesSeleccionadas").val();
        var totalPagable=0;
        var retenciones="POR RETENCIONES: ";
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);
        $("#tablaRetencionesSeleccionadas").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            $("#tablaRetencionesSeleccionadas").jqGrid('addRowData',i+1,mydata[i]);
            totalPagable+=mydata[i].total;
            retenciones=retenciones+mydata[i].retencion+", ";
        }

        var totalPagableEntero = ""+parseInt(totalPagable);
        var totalPagableDecimal = getDecimal(totalPagable);
        $("#descripcion").val(retenciones.substring(0,retenciones.length-2));
        $("#totalPagable").val((isNaN(totalPagable)) ?"?":toFixed(totalPagable,2).toString());

        if(totalPagable>=1000&&totalPagable<2000)
            $( "#totalPagableLiteral" ).val("UN "+CifrasEnLetras.convertirNumeroEnLetras(totalPagableEntero).toUpperCase()+" "+totalPagableDecimal+"/100 BOLIVIANOS");
        else
            $( "#totalPagableLiteral" ).val(CifrasEnLetras.convertirNumeroEnLetras(totalPagableEntero).toUpperCase()+" "+totalPagableDecimal+"/100 BOLIVIANOS");
    }

    function deshabilitarControles(){
        $("#buscarRetenciones").attr("disabled","true");

        $("#_periodo1").hide();
        $("#_periodo2").show();
        $("#periodo2").val($("#periodo_month").val()+"/"+$("#periodo_year").val());

        $("#_quincena1").hide();
        $("#_quincena2").show();
        $("#quincena2").val($("#quincena").val());

        $("#_empresa1").hide();
        $("#_empresa2").show();
        $("#empresa2").val($( "#empresa option:selected" ).text());

        $.notify("Se han bloqueado los controles para BUSCAR por seguridad.",{autoHide: true,clickToHide: true,className: "info"});
    }

    function clienteAutocomplete(){
        $("#ci").autocomplete({
            source: function(request, response){
                $.ajax({
                    url: "/demo-liquidaciones/pagoDeRetenciones/nombreCobradorJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
                $("#beneficiario").val(ui.item.beneficiario);
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
});

