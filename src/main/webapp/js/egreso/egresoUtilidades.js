$(document).ready(function() {
    $(".chosen-select").chosen();

    $( "#importeDialogo" ).dialog({
        height: 100,
        width: 400,
        position: { my: "right bottom", at: "right bottom"}
    });

    $("#ci").bind("keyup",clienteAutocomplete);
    importeAutocomplete();
    $("#identificador").bind("keyup",importeAutocomplete);
    $("#importe").bind("keyup",literales);

    filtrarSubcuentas();
    $("#cuenta").bind("change",filtrarSubcuentas);

    $("#operacion").bind("change",controlOperacion);

    literales();
    //clienteAutocomplete();

    function controlOperacion(){
        if($("#operacion").val()=="OTROS PAGOS"){
            $("#identificador").val("-");
            $("#_identificador").hide();
        }else{
            $("#identificador").val("");
            $("#_identificador").show();
        }
    }

    function filtrarSubcuentas(){
        $.ajax({
            url: "/demo-liquidaciones/subcuenta/subcuentasDeCuenta",
            data: {
                cuentaId: $("#cuenta").val()
            },
            cache: false,
            success: function(html) {
                $("#subcuenta").html(html);
            }
        });
    }

    function clienteAutocomplete(){
        $("#ci").autocomplete({
            source: function(request, response){
                $.ajax({
                    url: "/demo-liquidaciones/movimientoCaja/nombreJSON", // /demo-liquidaciones/recepcionDeComplejo/create
                    data: {
                        ci:$("#ci").val()
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
                //$("#nombreCobrador").val(ui.item.nombreCobrador);
                $("#nombre").val(ui.item.nombre);
            },
            response: function(event, ui) {
                if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                    //$.jGrowl("No existen resultados para el CI buscado.");
                }
            }
        });
    }

    function importeAutocomplete(){
        $("#identificador").autocomplete({
            source: function(request, response){
                $.ajax({
                    url: "/demo-liquidaciones/egreso/importeJSON", // /demo-liquidaciones/recepcionDeComplejo/create
                    data: {
                        operacion:$("#operacion").val(),
                        identificador:$("#identificador").val()
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
                //$("#nombreCobrador").val(ui.item.nombreCobrador);
                $("#importe").val(ui.item.importe);
                literales();
            },
            response: function(event, ui) {
                if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                    //$.jGrowl("No existen resultados para el CI buscado.");
                }
            }
        });
    }

    function literales(){
        if(isNaN(transFloat($( "#importe" ).val()))){
            //alert("not a number");
            return;
        }
        $( "#importeFormateado" ).text(accounting.formatMoney($( "#importe" ).val(),"Bs ", 2, ".", ","));

        var numeroBolivianos = parseInt($( "#importe" ).val());

        var decimalBolivianos = getDecimal(redondear2(parseFloat($( "#importe" ).val())));
        if(numeroBolivianos>=1000&&numeroBolivianos<2000)
            $( "#importeLiteral" ).val("UN "+CifrasEnLetras.convertirNumeroEnLetras(numeroBolivianos).toUpperCase()+" "+decimalBolivianos+"/100 BOLIVIANOS");
        else
            $( "#importeLiteral" ).val(CifrasEnLetras.convertirNumeroEnLetras(numeroBolivianos).toUpperCase()+" "+decimalBolivianos+"/100 BOLIVIANOS");
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

