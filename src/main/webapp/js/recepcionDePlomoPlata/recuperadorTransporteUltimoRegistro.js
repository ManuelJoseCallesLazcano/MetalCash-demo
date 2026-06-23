$(document).ready(function() {
    $.ajax({
        url:"/demo-liquidaciones/recepcionDePlomoPlata/datosTransporteUltimaRecepcionJSON",
        dataType: 'json',
        data: {
            // no es necesario enviar datos
        },
        success: function(data) {
            $('#empresa\\.id').val(data.empresaId),
            $('#nombreEmpresa').val(data.nombreEmpresa),
            $('#chofer\\.id').val(data.choferId),
            $('#ciChofer').val(data.ciChofer),
            $('#nombreChofer').val(data.nombreChofer),
            $('#automovil\\.id').val(data.automovilId),
            $('#placa').val(data.placaAutomovil),
            $('#modelo').val(data.modeloAutomovil),
            $('#color').val(data.colorAutomovil)
        },
        error: function(request, status, error) {

        }
    });

    $.jGrowl("ATENCION: Se ha recuperado la informacion de Transporte del ultimo lote recepcionado. MODIFIQUE SI ES NECESARIO.", { life: 20000 });
});
