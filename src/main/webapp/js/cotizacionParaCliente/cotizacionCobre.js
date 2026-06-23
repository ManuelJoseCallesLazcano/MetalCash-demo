$(document).ready(function() {
    var cotizacionSeleccionada=0;
    cargarCotizacion();

    //tablaCobrePlata
    $('#valorar').click(function() {
        $.ajax({
            url:"/demo-liquidaciones/cotizacionDeCobrePlata/cotizar",
            dataType: 'json',
            data: {
                cotizacionDiariaId: $('#cotizacionDiaria').val(),
                porcentajeCobre: $('#leyCobre').val(),
                porcentajePlata: $('#leyPlata').val(),
                modoValoracion: $('#modoValoracion').val(),
                tablaCobrePlataId: $('#tablaCobrePlata').val(),                
                terminosDeContratoId: $('#terminosDeContrato').val()
            },
            success: function(data) {
                $('#valorTonelada').val(data.valorPorTonelada);
                calcularValorEstimado();
                //$.notify(data.notificacionValoresTonelada,{autoHide: false,clickToHide: true,className: "info"});
            },
            error: function(request, status, error) {

            }
        });        
    });

    $('#fechaDeCotizacion_year, #fechaDeCotizacion_month, #fechaDeCotizacion_day').change(function() {
        cargarCotizacion();
    });

    //para evitar que se puedan hacer cotizaciones con una cotizacion diaria que no corresponde a la fecha de la cotizacion
    $('#cotizacionDiaria').change(function() {
        $('#cotizacionDiaria').val(cotizacionSeleccionada);
    });

    $("#pesoBruto").keyup(function(){
        calcularValorEstimado();
    });

    function calcularValorEstimado(){
        var valorTonelada = transFloat($("#valorTonelada").val());
        var pesoBruto = transFloat($("#pesoBruto").val());
        var valorEstimado = valorTonelada*pesoBruto/1000;
        $("#valorEstimado").val((isNaN(valorEstimado)) ?"?":toFixed(valorEstimado,2).toString());
    }

    function cargarCotizacion(){
        $.ajax({
            url:"/demo-liquidaciones/cotizacionDiariaDeMinerales/cotizacionDiariaPorFecha",
            dataType: 'json',
            data: {
                fechaDeCotizacion_year: $('#fechaDeCotizacion_year').val(),
                fechaDeCotizacion_month: $('#fechaDeCotizacion_month').val(),
                fechaDeCotizacion_day: $('#fechaDeCotizacion_day').val()
            },
            success: function(data) {
                $('#cotizacionDiaria').val(data.cotizacionId);
                cotizacionSeleccionada = $('#cotizacionDiaria').val();
            },
            error: function(request, status, error) {

            }
        });
    }
    
    $("#modoValoracion" ).change(function() {
        var e=$("#modoValoracion").val();
        if(e=="TABLA"){
            $("#_tablaCobrePlata").show();
            $("#_terminosDeContrato").hide();
        }
        if(e=="TERMINOS DE CONTRATO"){
            $("#_tablaCobrePlata").hide();
            $("#_terminosDeContrato").show();
        }
        if(e==""){
            $("#_tablaCobrePlata").hide();
            $("#_terminosDeContrato").hide();
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
});
