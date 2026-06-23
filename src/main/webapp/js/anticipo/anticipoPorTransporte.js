$(document).ready(function() {
    // seleccionarDespliegue();

    $(".chosen-select").chosen({
        width: '400px'
    });

    $("#importe").bind("keyup",convertir);

    recuperarDatosChofer();

    $("#recepcionDeComplejo" ).change(function() {
        recuperarDatosChofer();
    });

    $("#solicitante,#empresa,#automovil" ).change(function() {
        recuperarUltimoSaldo();
        seleccionarDespliegue();
    });

    function recuperarDatosChofer(){
        // console.log('buscando lote', $('#recepcionDeComplejo').val());
        $.ajax({
            url: "/demo-liquidaciones/anticipoPorTransporte/recuperarDatosChoferJSON",
            dataType: 'json',
            data: {
                recepcionId: $('#recepcionDeComplejo').val()
            },
            success: function(data) {
                $('#ci').val(data.ciChofer);
                $('#nombreCobrador').val(data.nombreChofer);
                // $.notify(data.notificacion,{autoHide: false,clickToHide: true,className: "info"});
            },
            error: function(request, status, error) {

            }
        });
    }

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
                $('#ultimoSaldo').val(data.saldoCuenta);
                $.notify(data.notificacion,{autoHide: false,clickToHide: true,className: "info"});
            },
            error: function(request, status, error) {

            }
        });
    }

    function convertir(){
        var total = transFloat($("#importe").val());
        if(!isNaN(total)){
            var totalEntero = ""+parseInt(total);
            var totalDecimal = getDecimal(total);
            if(total>=1000&&total<2000)
                $( "#importeLiteral" ).val("UN "+CifrasEnLetras.convertirNumeroEnLetras(totalEntero).toUpperCase()+" "+totalDecimal+"/100 BOLIVIANOS");
            else
                $( "#importeLiteral" ).val(CifrasEnLetras.convertirNumeroEnLetras(totalEntero).toUpperCase()+" "+totalDecimal+"/100 BOLIVIANOS");
        }else
            $( "#importeLiteral" ).val("?");
        //alert("convirtiendo...");
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
        // if(e=="Empresa"){
        //     $("#_empresa").show();
        //     $("#_automovil").hide();
        // }
        // if(e=="Particular"){
        //     $("#_empresa").hide();
        //     $("#_automovil").show();
        // }
    }
});


