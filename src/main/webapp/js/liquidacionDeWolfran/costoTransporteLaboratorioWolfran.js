$(document).ready(function() {
    $("#lote").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/costoTransporteLaboratorioWolfran/recepcionesJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
            $('#recepcionId').val(ui.item.recepcionId),
            $('#nombreCliente').val(ui.item.nombreCliente),
            $('#nombreEmpresa').val(ui.item.nombreEmpresa),
            $('#fechaDeRecepcion').val(ui.item.fechaDeRecepcion),
            $('#pesoBruto').val(ui.item.pesoBruto),
            $('#costoDeTransporteAnterior').val(ui.item.costoDeTransporteAnterior)
            $('#detalleLaboratorio1Anterior').val(ui.item.detalleLaboratorio1Anterior)
            $('#costoLaboratorio1Anterior').val(ui.item.costoLaboratorio1Anterior)
            $('#detalleLaboratorio2Anterior').val(ui.item.detalleLaboratorio2Anterior)
            $('#costoLaboratorio2Anterior').val(ui.item.costoLaboratorio2Anterior)
            $('#detalleLaboratorio3Anterior').val(ui.item.detalleLaboratorio3Anterior)
            $('#costoLaboratorio3Anterior').val(ui.item.costoLaboratorio3Anterior)
            $('#detalleLaboratorio4Anterior').val(ui.item.detalleLaboratorio4Anterior)
            $('#costoLaboratorio4Anterior').val(ui.item.costoLaboratorio4Anterior)
            $('#totalCostoLaboratorioAnterior').val(ui.item.totalCostoLaboratorioAnterior)
            //$.jGrowl("Seleccionado: "+$("#empresa\\.id").val());
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("El Lote no existe o ya fue cancelado.");
            }
        }
    });

    $("#costoLaboratorio1Nuevo, #costoLaboratorio2Nuevo, #costoLaboratorio3Nuevo, #costoLaboratorio4Nuevo").bind("keyup",calcularTotalCostoLaboratorio);

    function calcularTotalCostoLaboratorio(){
        var costoLaboratorio1Nuevo = parseFloat($("#costoLaboratorio1Nuevo").val());
        costoLaboratorio1Nuevo = isNaN(costoLaboratorio1Nuevo)?0:costoLaboratorio1Nuevo;
        var costoLaboratorio2Nuevo = parseFloat($("#costoLaboratorio2Nuevo").val());
        costoLaboratorio2Nuevo = isNaN(costoLaboratorio2Nuevo)?0:costoLaboratorio2Nuevo;
        var costoLaboratorio3Nuevo = parseFloat($("#costoLaboratorio3Nuevo").val());
        costoLaboratorio3Nuevo = isNaN(costoLaboratorio3Nuevo)?0:costoLaboratorio3Nuevo;
        var costoLaboratorio4Nuevo = parseFloat($("#costoLaboratorio4Nuevo").val());
        costoLaboratorio4Nuevo = isNaN(costoLaboratorio4Nuevo)?0:costoLaboratorio4Nuevo;
        //calculos
        var totalCostoLaboratorioNuevo = costoLaboratorio1Nuevo+costoLaboratorio2Nuevo+costoLaboratorio3Nuevo+costoLaboratorio4Nuevo;
        $("#totalCostoLaboratorioNuevo").val((isNaN(totalCostoLaboratorioNuevo)) ?"?":toFixed(totalCostoLaboratorioNuevo,2).toString());
    }

    function toFixed( number, precision ) {
        var multiplier = Math.pow( 10, precision );
        return Math.round( number * multiplier ) / multiplier;
    }
});

