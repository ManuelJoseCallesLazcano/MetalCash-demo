$(document).ready(function() {
    setInterval(function(){
        actualizarContenidoEstano();
        actualizarContenidoPlata();
        actualizarContenidoAntimonio();
        actualizarContenidoComplejo();
        actualizarContenidoWolfran();
    }, 100);

    var tablaId;
    var fechaInicial;
    var fechaFinal;
    var loteInicial;
    var loteFinal;
    var empresaId;

    $("#buscarEstano").click(function(){
        var tipoBusqueda = $("#tipoReporte").val();
        if(tipoBusqueda=="fechas"){
            tablaId = $("#tablaCotizacionEstano").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            //alert("ESTANO FI: "+fechaInicial+" FF: "+fechaFinal+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeEstano/recepcionesPresupuestoFechasJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    fechaInicial: fechaInicial,
                    fechaFinal: fechaFinal
                },
                success: function(data) {
                    $('#resultadoEstano').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaEstano").jqGrid("clearGridData", true);
                    jQuery("#tablaEstano").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","% Sn"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionEstano',index:'cotizacionEstano', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeEstano',index:'porcentajeEstano', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoEstano").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaEstano").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="fechasEmpresa"){
            tablaId = $("#tablaCotizacionEstano").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            empresaId = $("#empresa").val();
            //alert("ESTANO FI: "+fechaInicial+" FF: "+fechaFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeEstano/recepcionesPresupuestoFechasEmpresaJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    fechaInicial: fechaInicial,
                    fechaFinal: fechaFinal,
                    empresaId: empresaId
                },
                success: function(data) {
                    $('#resultadoEstano').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaEstano").jqGrid("clearGridData", true);
                    jQuery("#tablaEstano").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","% Sn"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionEstano',index:'cotizacionEstano', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeEstano',index:'porcentajeEstano', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoEstano").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaEstano").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="lotes"){
            tablaId = $("#tablaCotizacionEstano").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            //alert("ESTANO LI: "+loteInicial+" LF: "+loteFinal+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeEstano/recepcionesPresupuestoLotesJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    loteInicial: loteInicial,
                    loteFinal: loteFinal
                },
                success: function(data) {
                    $('#resultadoEstano').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaEstano").jqGrid("clearGridData", true);
                    jQuery("#tablaEstano").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","% Sn"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionEstano',index:'cotizacionEstano', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeEstano',index:'porcentajeEstano', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoEstano").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaEstano").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="lotesEmpresa"){
            tablaId = $("#tablaCotizacionEstano").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            empresaId = $("#empresa").val();
            //alert("ESTANO LI: "+loteInicial+" LF: "+loteFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeEstano/recepcionesPresupuestoLotesEmpresaJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    loteInicial: loteInicial,
                    loteFinal: loteFinal,
                    empresaId: empresaId
                },
                success: function(data) {
                    $('#resultadoEstano').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaEstano").jqGrid("clearGridData", true);
                    jQuery("#tablaEstano").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","% Sn"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionEstano',index:'cotizacionEstano', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeEstano',index:'porcentajeEstano', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoEstano").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaEstano").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
    });

    $("#buscarPlata").click(function(){
        var tipoBusqueda = $("#tipoReporte").val();
        if(tipoBusqueda=="fechas"){
            tablaId = $("#tablaCotizacionPlata").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            //alert("Plata FI: "+fechaInicial+" FF: "+fechaFinal+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDePlata/recepcionesPresupuestoFechasJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    fechaInicial: fechaInicial,
                    fechaFinal: fechaFinal
                },
                success: function(data) {
                    $('#resultadoPlata').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaPlata").jqGrid("clearGridData", true);
                    jQuery("#tablaPlata").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","DM Ag"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionPlata',index:'cotizacionPlata', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajePlata',index:'porcentajePlata', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoPlata").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaPlata").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="fechasEmpresa"){
            tablaId = $("#tablaCotizacionPlata").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            empresaId = $("#empresa").val();
            //alert("Plata FI: "+fechaInicial+" FF: "+fechaFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDePlata/recepcionesPresupuestoFechasEmpresaJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    fechaInicial: fechaInicial,
                    fechaFinal: fechaFinal,
                    empresaId: empresaId
                },
                success: function(data) {
                    $('#resultadoPlata').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaPlata").jqGrid("clearGridData", true);
                    jQuery("#tablaPlata").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","DM Ag"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionPlata',index:'cotizacionPlata', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajePlata',index:'porcentajePlata', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoPlata").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaPlata").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="lotes"){
            tablaId = $("#tablaCotizacionPlata").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            //alert("Plata LI: "+loteInicial+" LF: "+loteFinal+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDePlata/recepcionesPresupuestoLotesJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    loteInicial: loteInicial,
                    loteFinal: loteFinal
                },
                success: function(data) {
                    $('#resultadoPlata').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaPlata").jqGrid("clearGridData", true);
                    jQuery("#tablaPlata").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","DM Ag"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionPlata',index:'cotizacionPlata', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajePlata',index:'porcentajePlata', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoPlata").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaPlata").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="lotesEmpresa"){
            tablaId = $("#tablaCotizacionPlata").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            empresaId = $("#empresa").val();
            //alert("Plata LI: "+loteInicial+" LF: "+loteFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDePlata/recepcionesPresupuestoLotesEmpresaJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    loteInicial: loteInicial,
                    loteFinal: loteFinal,
                    empresaId: empresaId
                },
                success: function(data) {
                    $('#resultadoPlata').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaPlata").jqGrid("clearGridData", true);
                    jQuery("#tablaPlata").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","DM Ag"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionPlata',index:'cotizacionPlata', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajePlata',index:'porcentajePlata', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoPlata").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaPlata").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
    });

    $("#buscarAntimonio").click(function(){
        var tipoBusqueda = $("#tipoReporte").val();
        if(tipoBusqueda=="fechas"){
            tablaId = $("#tablaCotizacionAntimonio").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            //alert("Antimonio FI: "+fechaInicial+" FF: "+fechaFinal+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeAntimonio/recepcionesPresupuestoFechasJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    fechaInicial: fechaInicial,
                    fechaFinal: fechaFinal
                },
                success: function(data) {
                    $('#resultadoAntimonio').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaAntimonio").jqGrid("clearGridData", true);
                    jQuery("#tablaAntimonio").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","% Sb"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionAntimonio',index:'cotizacionAntimonio', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeAntimonio',index:'porcentajeAntimonio', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoAntimonio").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaAntimonio").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="fechasEmpresa"){
            tablaId = $("#tablaCotizacionAntimonio").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            empresaId = $("#empresa").val();
            //alert("Antimonio FI: "+fechaInicial+" FF: "+fechaFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeAntimonio/recepcionesPresupuestoFechasEmpresaJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    fechaInicial: fechaInicial,
                    fechaFinal: fechaFinal,
                    empresaId: empresaId
                },
                success: function(data) {
                    $('#resultadoAntimonio').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaAntimonio").jqGrid("clearGridData", true);
                    jQuery("#tablaAntimonio").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","% Sb"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionAntimonio',index:'cotizacionAntimonio', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeAntimonio',index:'porcentajeAntimonio', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoAntimonio").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaAntimonio").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="lotes"){
            tablaId = $("#tablaCotizacionAntimonio").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            //alert("Antimonio LI: "+loteInicial+" LF: "+loteFinal+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeAntimonio/recepcionesPresupuestoLotesJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    loteInicial: loteInicial,
                    loteFinal: loteFinal
                },
                success: function(data) {
                    $('#resultadoAntimonio').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaAntimonio").jqGrid("clearGridData", true);
                    jQuery("#tablaAntimonio").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","% Sb"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionAntimonio',index:'cotizacionAntimonio', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeAntimonio',index:'porcentajeAntimonio', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoAntimonio").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaAntimonio").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="lotesEmpresa"){
            tablaId = $("#tablaCotizacionAntimonio").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            empresaId = $("#empresa").val();
            //alert("Antimonio LI: "+loteInicial+" LF: "+loteFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeAntimonio/recepcionesPresupuestoLotesEmpresaJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    loteInicial: loteInicial,
                    loteFinal: loteFinal,
                    empresaId: empresaId
                },
                success: function(data) {
                    $('#resultadoAntimonio').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaAntimonio").jqGrid("clearGridData", true);
                    jQuery("#tablaAntimonio").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","% Sb"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionAntimonio',index:'cotizacionAntimonio', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeAntimonio',index:'porcentajeAntimonio', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoAntimonio").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaAntimonio").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
    });

    $("#buscarWolfran").click(function(){
        var tipoBusqueda = $("#tipoReporte").val();
        if(tipoBusqueda=="fechas"){
            tablaId = $("#tablaCotizacionWolfran").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            //alert("Wolfran FI: "+fechaInicial+" FF: "+fechaFinal+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeWolfran/recepcionesPresupuestoFechasJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    fechaInicial: fechaInicial,
                    fechaFinal: fechaFinal
                },
                success: function(data) {
                    $('#resultadoWolfran').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaWolfran").jqGrid("clearGridData", true);
                    jQuery("#tablaWolfran").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","% WO3"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionWolfran',index:'cotizacionWolfran', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeWolfran',index:'porcentajeWolfran', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoWolfran").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaWolfran").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="fechasEmpresa"){
            tablaId = $("#tablaCotizacionWolfran").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            empresaId = $("#empresa").val();
            //alert("Wolfran FI: "+fechaInicial+" FF: "+fechaFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeWolfran/recepcionesPresupuestoFechasEmpresaJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    fechaInicial: fechaInicial,
                    fechaFinal: fechaFinal,
                    empresaId: empresaId
                },
                success: function(data) {
                    $('#resultadoWolfran').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaWolfran").jqGrid("clearGridData", true);
                    jQuery("#tablaWolfran").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","% WO3"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionWolfran',index:'cotizacionWolfran', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeWolfran',index:'porcentajeWolfran', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoWolfran").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaWolfran").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="lotes"){
            tablaId = $("#tablaCotizacionWolfran").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            //alert("Wolfran LI: "+loteInicial+" LF: "+loteFinal+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeWolfran/recepcionesPresupuestoLotesJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    loteInicial: loteInicial,
                    loteFinal: loteFinal
                },
                success: function(data) {
                    $('#resultadoWolfran').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaWolfran").jqGrid("clearGridData", true);
                    jQuery("#tablaWolfran").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","% WO3"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionWolfran',index:'cotizacionWolfran', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeWolfran',index:'porcentajeWolfran', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoWolfran").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaWolfran").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="lotesEmpresa"){
            tablaId = $("#tablaCotizacionWolfran").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            empresaId = $("#empresa").val();
            //alert("Wolfran LI: "+loteInicial+" LF: "+loteFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeWolfran/recepcionesPresupuestoLotesEmpresaJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    loteInicial: loteInicial,
                    loteFinal: loteFinal,
                    empresaId: empresaId
                },
                success: function(data) {
                    $('#resultadoWolfran').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaWolfran").jqGrid("clearGridData", true);
                    jQuery("#tablaWolfran").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. DIA","% H2O","% WO3"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionWolfran',index:'cotizacionWolfran', width:80},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeWolfran',index:'porcentajeWolfran', width:80, editable: true} ],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoWolfran").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaWolfran").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
    });

    $("#buscarComplejo").click(function(){
        var tipoBusqueda = $("#tipoReporte").val();
        if(tipoBusqueda=="fechas"){
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            //alert("Complejo FI: "+fechaInicial+" FF: "+fechaFinal+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeComplejo/recepcionesPresupuestoFechasJSON",
                dataType: 'json',
                data: {
                    fechaInicial: fechaInicial,
                    fechaFinal: fechaFinal
                },
                success: function(data) {
                    $('#resultadoComplejo').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaComplejo").jqGrid("clearGridData", true);
                    jQuery("#tablaComplejo").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. Zn", "COT. Pb", "COT. Ag","MERMA","% H2O","% Zn","% Pb","% Ag","Pto. Zn","Pto. Pb","Pto. Ag"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionZinc',index:'cotizacionZinc', width:80},
                            {name:'cotizacionPlomo',index:'cotizacionPlomo', width:80},
                            {name:'cotizacionPlata',index:'cotizacionPlata', width:80},
                            {name:'merma',index:'merma', width:80, editable: true},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeZinc',index:'porcentajeZinc', width:80, editable: true},
                            {name:'porcentajePlomo',index:'porcentajePlomo', width:80, editable: true},
                            {name:'porcentajePlata',index:'porcentajePlata', width:80, editable: true},
                            {name:'puntoZinc',index:'puntoZinc', width:80, editable: true},
                            {name:'puntoPlomo',index:'puntoPlomo', width:80, editable: true},
                            {name:'puntoPlata',index:'puntoPlata', width:80, editable: true}],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoComplejo").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaComplejo").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="fechasEmpresa"){
            tablaId = $("#tablaCotizacionComplejo").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            empresaId = $("#empresa").val();
            //alert("Complejo FI: "+fechaInicial+" FF: "+fechaFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeComplejo/recepcionesPresupuestoFechasEmpresaJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    fechaInicial: fechaInicial,
                    fechaFinal: fechaFinal,
                    empresaId: empresaId
                },
                success: function(data) {
                    $('#resultadoComplejo').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaComplejo").jqGrid("clearGridData", true);
                    jQuery("#tablaComplejo").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. Zn", "COT. Pb", "COT. Ag","MERMA","% H2O","% Zn","% Pb","% Ag","Pto. Zn","Pto. Pb","Pto. Ag"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionZinc',index:'cotizacionZinc', width:80},
                            {name:'cotizacionPlomo',index:'cotizacionPlomo', width:80},
                            {name:'cotizacionPlata',index:'cotizacionPlata', width:80},
                            {name:'merma',index:'merma', width:80, editable: true},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeZinc',index:'porcentajeZinc', width:80, editable: true},
                            {name:'porcentajePlomo',index:'porcentajePlomo', width:80, editable: true},
                            {name:'porcentajePlata',index:'porcentajePlata', width:80, editable: true},
                            {name:'puntoZinc',index:'puntoZinc', width:80, editable: true},
                            {name:'puntoPlomo',index:'puntoPlomo', width:80, editable: true},
                            {name:'puntoPlata',index:'puntoPlata', width:80, editable: true}],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoComplejo").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaComplejo").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="lotes"){
            tablaId = $("#tablaCotizacionComplejo").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            //alert("Complejo LI: "+loteInicial+" LF: "+loteFinal+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeComplejo/recepcionesPresupuestoLotesJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    loteInicial: loteInicial,
                    loteFinal: loteFinal
                },
                success: function(data) {
                    $('#resultadoComplejo').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaComplejo").jqGrid("clearGridData", true);
                    jQuery("#tablaComplejo").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. Zn", "COT. Pb", "COT. Ag","MERMA","% H2O","% Zn","% Pb","% Ag","Pto. Zn","Pto. Pb","Pto. Ag"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionZinc',index:'cotizacionZinc', width:80},
                            {name:'cotizacionPlomo',index:'cotizacionPlomo', width:80},
                            {name:'cotizacionPlata',index:'cotizacionPlata', width:80},
                            {name:'merma',index:'merma', width:80, editable: true},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeZinc',index:'porcentajeZinc', width:80, editable: true},
                            {name:'porcentajePlomo',index:'porcentajePlomo', width:80, editable: true},
                            {name:'porcentajePlata',index:'porcentajePlata', width:80, editable: true},
                            {name:'puntoZinc',index:'puntoZinc', width:80, editable: true},
                            {name:'puntoPlomo',index:'puntoPlomo', width:80, editable: true},
                            {name:'puntoPlata',index:'puntoPlata', width:80, editable: true}],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoComplejo").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaComplejo").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
        if(tipoBusqueda=="lotesEmpresa"){
            tablaId = $("#tablaCotizacionComplejo").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            empresaId = $("#empresa").val();
            //alert("Complejo LI: "+loteInicial+" LF: "+loteFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
            $.ajax({
                url:"/demo-liquidaciones/recepcionDeComplejo/recepcionesPresupuestoLotesEmpresaJSON",
                dataType: 'json',
                data: {
                    tablaId: tablaId,
                    loteInicial: loteInicial,
                    loteFinal: loteFinal,
                    empresaId: empresaId
                },
                success: function(data) {
                    $('#resultadoComplejo').val(JSON.stringify(data))
                    //eliminar el resultado anterior
                    $("#tablaComplejo").jqGrid("clearGridData", true);
                    jQuery("#tablaComplejo").jqGrid({
                        datatype: "local",
                        height: 200,
                        width: 900,
                        colNames: ["FECHA","LOTE","PROCEDENCIA","PROVEEDOR","SACOS","KNH", "COT. Zn", "COT. Pb", "COT. Ag","MERMA","% H2O","% Zn","% Pb","% Ag","Pto. Zn","Pto. Pb","Pto. Ag"],
                        colModel:[
                            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:60},
                            {name:'lote',index:'lote', width:50},
                            {name:'nombreEmpresa',index:'nombreEmpresa', width:200},
                            {name:'nombreCliente',index:'nombreCliente', width:200},
                            {name:'cantidadDeSacos',index:'cantidadDeSacos', width:80},
                            {name:'kilosNetosHumedos',index:'kilosNetosHumedos', width:80},
                            {name:'cotizacionZinc',index:'cotizacionZinc', width:80},
                            {name:'cotizacionPlomo',index:'cotizacionPlomo', width:80},
                            {name:'cotizacionPlata',index:'cotizacionPlata', width:80},
                            {name:'merma',index:'merma', width:80, editable: true},
                            {name:'humedad',index:'humedad', width:80, editable: true},
                            {name:'porcentajeZinc',index:'porcentajeZinc', width:80, editable: true},
                            {name:'porcentajePlomo',index:'porcentajePlomo', width:80, editable: true},
                            {name:'porcentajePlata',index:'porcentajePlata', width:80, editable: true},
                            {name:'puntoZinc',index:'puntoZinc', width:80, editable: true},
                            {name:'puntoPlomo',index:'puntoPlomo', width:80, editable: true},
                            {name:'puntoPlata',index:'puntoPlata', width:80, editable: true}],
                        multiselect: false,
                        excel: true,
                        cellEdit: true,
                        cellsubmit: 'clientArray',
                        caption: "RESULTADOS:"
                    });

                    var mydata = $.parseJSON($("#resultadoComplejo").val());
                    for(var i=0;i<=mydata.length;i++)
                        jQuery("#tablaComplejo").jqGrid('addRowData',i+1,mydata[i]);
                },
                error: function(request, status, error) {
                }
            });
        }
    });

    $("#elemento" ).change(function() {
        var elem=$("#elemento").val();
        $("#ELEMENTO_1").val(elem);
        $("#ELEMENTO_CLASS_1").val(getElementoClass(elem));
        $("#ELEMENTO_2").val(elem);
        $("#ELEMENTO_CLASS_2").val(getElementoClass(elem));
        $("#ELEMENTO_3").val(elem);
        $("#ELEMENTO_CLASS_3").val(getElementoClass(elem));
        $("#ELEMENTO_4").val(elem);
        $("#ELEMENTO_CLASS_4").val(getElementoClass(elem));

        if(elem=="Estano"){
            $( "#_tablaCotizacionEstano" ).show();
            $( "#_tablaCotizacionPlata" ).hide();
            $( "#_tablaCotizacionAntimonio" ).hide();
            $( "#_tablaCotizacionWolfran" ).hide();

            $( "#_resultadosEstano" ).show();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
        }
        if(elem=="Plata"){
            $( "#_tablaCotizacionEstano" ).hide();
            $( "#_tablaCotizacionPlata" ).show();
            $( "#_tablaCotizacionAntimonio" ).hide();
            $( "#_tablaCotizacionWolfran" ).hide();

            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).show();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
        }
        if(elem=="Wolfran"){
            $( "#_tablaCotizacionEstano" ).hide();
            $( "#_tablaCotizacionPlata" ).hide();
            $( "#_tablaCotizacionAntimonio" ).hide();
            $( "#_tablaCotizacionWolfran" ).show();

            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).show();
            $( "#_resultadosComplejo" ).hide();
        }
        if(elem=="Antimonio"){
            $( "#_tablaCotizacionEstano" ).hide();
            $( "#_tablaCotizacionPlata" ).hide();
            $( "#_tablaCotizacionAntimonio" ).show();
            $( "#_tablaCotizacionWolfran" ).hide();

            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).show();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
        }
        if(elem=="Complejo"){
            $( "#_tablaCotizacionEstano" ).hide();
            $( "#_tablaCotizacionPlata" ).hide();
            $( "#_tablaCotizacionAntimonio" ).hide();
            $( "#_tablaCotizacionWolfran" ).hide();

            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).show();
        }
    });

    $("#tablaCotizacionEstano" ).change(function() {
        $("#tablaCotizacionEstanoId").val($("#tablaCotizacionEstano").val());
    });
    $("#tablaCotizacionPlata" ).change(function() {
        $("#tablaCotizacionPlataId").val($("#tablaCotizacionPlata").val());
    });
    $("#tablaCotizacionAntimonio" ).change(function() {
        $("#tablaCotizacionAntimonioId").val($("#tablaCotizacionAntimonio").val());
    });
    $("#tablaCotizacionWolfran" ).change(function() {
        $("#tablaCotizacionWolfranId").val($("#tablaCotizacionWolfran").val());
    });
    

    $("#fechaInicial_day,#fechaInicial_month,#fechaInicial_year").change(function() {       
        $("#FECHA_INICIAL_1").val(getFechaInicial());
        $("#FECHA_INICIAL_2").val(getFechaInicial());
        $("#FECHA_INICIAL_3").val(getFechaInicial());
        $("#FECHA_INICIAL_4").val(getFechaInicial());
    });

    $("#fechaFinal_day,#fechaFinal_month,#fechaFinal_year").change(function() {
        $("#FECHA_FINAL_1").val(getFechaFinal());
        $("#FECHA_FINAL_2").val(getFechaFinal());
        $("#FECHA_FINAL_3").val(getFechaFinal());
        $("#FECHA_FINAL_4").val(getFechaFinal());
    });

    $("#empresa" ).change(function() {
        var empr=$("#empresa").val();
        $("#EMPRESA_ID_2").val(empr);
        $("#EMPRESA_ID_4").val(empr);
    });

    $("#loteInicial" ).keyup(function() {
        var li=$("#loteInicial").val();
        $("#LOTE_INICIAL_3").val(li);
        $("#LOTE_INICIAL_4").val(li);
    });

    $("#loteFinal" ).keyup(function() {
        var lf=$("#loteFinal").val();
        $("#LOTE_FINAL_3").val(lf);
        $("#LOTE_FINAL_4").val(lf);
    });

    $("#fechas").click(function(){
        if ($("#fechas").prop("checked")) {
            //alert("seleccionado: FECHAS!");
            $( "#_empresa" ).hide();
            $( "#_fechaInicial" ).show();
            $( "#_fechaFinal" ).show();
            $( "#_loteInicial" ).hide();
            $( "#_loteFinal" ).hide();

            $( "#tipoReporte" ).val("fechas");
        }
    });

    $("#fechasEmpresa").click(function(){
        if ($("#fechasEmpresa").prop("checked")) {
            //alert("seleccionado: FECHAS Y EMPRESA!");
            $( "#_empresa" ).show();
            $( "#_fechaInicial" ).show();
            $( "#_fechaFinal" ).show();
            $( "#_loteInicial" ).hide();
            $( "#_loteFinal" ).hide();

            $( "#tipoReporte" ).val("fechasEmpresa");
        }
    });

    $("#lotes").click(function(){
        if ($("#lotes").prop("checked")) {
            //alert("seleccionado: LOTES!");
            $( "#_empresa" ).hide();
            $( "#_fechaInicial" ).hide();
            $( "#_fechaFinal" ).hide();
            $( "#_loteInicial" ).show();
            $( "#_loteFinal" ).show();

            $( "#tipoReporte" ).val("lotes");
        }
    });

    $("#lotesEmpresa").click(function(){
        if ($("#lotesEmpresa").prop("checked")) {
            //alert("seleccionado: LOTES Y EMPRESA!");
            $( "#_empresa" ).show();
            $( "#_fechaInicial" ).hide();
            $( "#_fechaFinal" ).hide();
            $( "#_loteInicial" ).show();
            $( "#_loteFinal" ).show();

            $( "#tipoReporte" ).val("lotesEmpresa");
        }
    });

    function getFechaInicial(){
        var day=$("#fechaInicial_day").val().toString();
        day=(day.length<2)?"0"+day:day;
        var month=$("#fechaInicial_month").val().toString();
        month=(month.length<2)?"0"+month:month;
        var year=$("#fechaInicial_year").val().toString();
        //'2013-12-03' 
        return year+"-"+month+"-"+day;
    }

    function getFechaFinal(){
        var day=$("#fechaFinal_day").val().toString();
        day=(day.length<2)?"0"+day:day;
        var month=$("#fechaFinal_month").val().toString();
        month=(month.length<2)?"0"+month:month;
        var year=$("#fechaFinal_year").val().toString();
        //'2013-12-03' 
        return year+"-"+month+"-"+day;
    }

    function getElementoClass(elem){
        //"Estano","Plata","Wolfran","Antimonio","Complejo","Plomo Plata","Zinc Plata"
        /*if(elem=="Estano") return "'org.socymet.recepcion.RecepcionDeEstano'";
        if(elem=="Plata") return "'org.socymet.recepcion.RecepcionDePlata'";
        if(elem=="Wolfran") return "'org.socymet.recepcion.RecepcionDeWolfran'";
        if(elem=="Antimonio") return "'org.socymet.recepcion.RecepcionDeAntimonio'";
        if(elem=="Complejo") return "'org.socymet.recepcion.RecepcionDeComplejo'";
        if(elem=="Plomo Plata") return "'org.socymet.recepcion.RecepcionDePlomoPlata'";
        if(elem=="Zinc Plata") return "'org.socymet.recepcion.RecepcionDeZincPlata'";*/
        if(elem=="Estano") return "org.socymet.recepcion.RecepcionDeEstano";
        if(elem=="Plata") return "org.socymet.recepcion.RecepcionDePlata";
        if(elem=="Wolfran") return "org.socymet.recepcion.RecepcionDeWolfran";
        if(elem=="Antimonio") return "org.socymet.recepcion.RecepcionDeAntimonio";
        if(elem=="Complejo") return "org.socymet.recepcion.RecepcionDeComplejo";
        if(elem=="Plomo Plata") return "org.socymet.recepcion.RecepcionDePlomoPlata";
        if(elem=="Zinc Plata") return "org.socymet.recepcion.RecepcionDeZincPlata";
        return "wtf?";
    }

    function actualizarContenidoEstano(){
        var precios = JSON.stringify($("#tablaEstano").jqGrid('getGridParam','data'));
        $("#resultadoEstano").val(precios);
    }
    function actualizarContenidoPlata(){
        var precios = JSON.stringify($("#tablaPlata").jqGrid('getGridParam','data'));
        $("#resultadoPlata").val(precios);
    }
    function actualizarContenidoAntimonio(){
        var precios = JSON.stringify($("#tablaAntimonio").jqGrid('getGridParam','data'));
        $("#resultadoAntimonio").val(precios);
    }
    function actualizarContenidoWolfran(){
        var precios = JSON.stringify($("#tablaWolfran").jqGrid('getGridParam','data'));
        $("#resultadoWolfran").val(precios);
    }
    function actualizarContenidoComplejo(){
        var precios = JSON.stringify($("#tablaComplejo").jqGrid('getGridParam','data'));
        $("#resultadoComplejo").val(precios);
    }
});

