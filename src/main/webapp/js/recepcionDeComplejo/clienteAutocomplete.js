$(document).ready(function() {
    $('.chosen-select').chosen({
        width: '400px'
    });

    $.ajax({
        url:"/demo-liquidaciones/recepcionDeComplejo/ready",
        dataType: 'json',
        data: {
        },
        success: function(data) {
            if(data.working==0){
                $('#create-recepcionDeComplejo').hide();
                $.notify("PERIODO DE PRUEBA DEL SISTEMA EXPIRADO",{autoHide: false,clickToHide: true,className: "error",position: "top left"});
            }

        },
        error: function(request, status, error) {

        }
    });

    filtrarCuadrillas();

    //verificacion de la existencia de cotizacion diaria al dia
    $.ajax({
        url:"/demo-liquidaciones/cotizacionDiariaDeMinerales/cotizacionDiaria",
        dataType: 'json',
        data: {
            fechaDeRecepcion_day: $("#fechaDeRecepcion_day").val(),
            fechaDeRecepcion_month: $("#fechaDeRecepcion_month").val(),
            fechaDeRecepcion_year: $("#fechaDeRecepcion_year").val()
        },
        success: function(data) {
            if(data.estadoCotizacion=="expirada")
                //$.jGrowl("<html>No ha sido registrada la Cotizacion Diaria para hoy. <br><a href='/demo-liquidaciones/cotizacionDiariaDeMinerales/create'>Haga clic aqui!</a></html>");
                $.notify("No ha sido registrada la Cotizacion Diaria para hoy!",{autoHide: false,clickToHide: true,className: "warn",position: "top left"});
        },
        error: function(request, status, error) {

        }
    });
    //Llamada ajax para cargar informacion de costo de transporte, unidad monetaria, etc. para EDITAR
    //la informacion de recepcion, en el controller esta el codigo para soportar errores cuando
    //se este invocando la llamada sobre informacion vacia
    $.ajax({
        url:"/demo-liquidaciones/empresa/datosTransporteComplejosJSON",
        dataType: 'json',
        data: {
            empresaId: $("#empresa\\.id").val()
        },
        success: function(data) {
            $('#costoTransporteComplejos').val(data.costoTransporteComplejos),
            $('#unidadMonetariaComplejos').val(data.unidadMonetariaComplejos),
            $('#unidadDeCobroComplejos').val(data.unidadDeCobroComplejos),
            $('#costoTransporteConcentrados').val(data.costoTransporteConcentrados),
            $('#unidadMonetariaConcentrados').val(data.unidadMonetariaConcentrados),
            $('#unidadDeCobroConcentrados').val(data.unidadDeCobroConcentrados),
            $('#tipoDeCambioComercial').val(data.tipoDeCambioComercial)
        },
        error: function(request, status, error) {

        }
    });

    $("#ciCliente").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/cliente/clientesJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
            $("#cliente\\.id").val(ui.item.clienteId); // actualizando campo oculto para id de cliente
            $("#empresa\\.id").val(ui.item.empresaId); // actualizando campo oculto para id de empresa
            $("#nombreCliente").val(ui.item.nombreCliente);
            $("#nombreEmpresa").val(ui.item.nombreEmpresa);
            //$.jGrowl("Seleccionado: "+$("#empresa\\.id").val());
            //RECUPERANDO DATOS DE COSTO DE TRANSPORTE PARA LA EMPRESA SELECCIONADA
            $.ajax({
                url:"/demo-liquidaciones/empresa/datosTransporteComplejosJSON",
                dataType: 'json',
                data: {
                    empresaId: $("#empresa\\.id").val()
                },
                success: function(data) {
                    $('#costoTransporteComplejos').val(data.costoTransporteComplejos),
                    $('#unidadMonetariaComplejos').val(data.unidadMonetariaComplejos),
                    $('#unidadDeCobroComplejos').val(data.unidadDeCobroComplejos),
                    $('#costoTransporteConcentrados').val(data.costoTransporteConcentrados),
                    $('#unidadMonetariaConcentrados').val(data.unidadMonetariaConcentrados),
                    $('#unidadDeCobroConcentrados').val(data.unidadDeCobroConcentrados),
                    $('#tipoDeCambioComercial').val(data.tipoDeCambioComercial)
                },
                error: function(request, status, error) {

                }
            });

            siguienteLote()
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("No existen resultados para el CI buscado.");
            }
        }
    });

    //BUSQUEDA DE CLIENTES POR NOMBRE
    $("#nombreCliente").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/cliente/clientesPorNombreJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
            $("#cliente\\.id").val(ui.item.clienteId); // actualizando campo oculto para id de cliente
            $("#empresa\\.id").val(ui.item.empresaId); // actualizando campo oculto para id de empresa
            $("#ciCliente").val(ui.item.ciCliente);
            $("#nombreEmpresa").val(ui.item.nombreEmpresa);
            //$.jGrowl("Seleccionado: "+$("#empresa\\.id").val());
            //FILTRANDO LISTA DE SECCIONES, SI CORRESPONDE
            filtrarCuadrillas();
            //RECUPERANDO DATOS DE COSTO DE TRANSPORTE PARA LA EMPRESA SELECCIONADA
            $.ajax({
                url:"/demo-liquidaciones/empresa/datosTransporteComplejosJSON",
                dataType: 'json',
                data: {
                    empresaId: $("#empresa\\.id").val()
                },
                success: function(data) {
                    $('#costoTransporteComplejos').val(data.costoTransporteComplejos),
                    $('#unidadMonetariaComplejos').val(data.unidadMonetariaComplejos),
                    $('#unidadDeCobroComplejos').val(data.unidadDeCobroComplejos),
                    $('#costoTransporteConcentrados').val(data.costoTransporteConcentrados),
                    $('#unidadMonetariaConcentrados').val(data.unidadMonetariaConcentrados),
                    $('#unidadDeCobroConcentrados').val(data.unidadDeCobroConcentrados),
                    $('#tipoDeCambioComercial').val(data.tipoDeCambioComercial)
                },
                error: function(request, status, error) {

                }
            });

            siguienteLote()
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("No existen resultados para el NOMBRE buscado.");
            }
        }
    });

    function filtrarCuadrillas() {
        $.ajax({
            url:"/demo-liquidaciones/recepcionDeComplejo/seccionesDeEmpresa",
            data: {
                recepcionId: $("#recepcionId").val() === '0'? 1000000: parseInt($("#recepcionId").val()),
                // recepcionId: 100000,
                empresaId: $("#empresa\\.id").val()
            },
            success: function(html) {
                $('#empresaSeccion').html(html).trigger("chosen:updated");
                if ($('#empresaSeccion option').length > 0) $('#_empresaSeccion').show();
                else $('#_empresaSeccion').hide();
                // console.log("cantidad opciones: ",$('#empresaSeccion option').length);
            },
            error: function(request, status, error) {
            }
        });
    }

    function siguienteLote(){
        $.ajax({
            url:"/demo-liquidaciones/recepcionDeComplejo/numeroDeLote",
            dataType: 'json',
            data: {
                empresaId: $("#empresa\\.id").val()
            },
            success: function(data) {
                // $.notify("LOTE DE TURNO: "+data.lote,{autoHide: false,clickToHide: true,className: "info",position: "top left"});
                $("#loteTurno").html(data.lote)
            },
            error: function(request, status, error) {

            }
        });
    }
});
