$(document).ready(function() {
    var asignarPorPorcentaje = {
        "VBV" : "VBV",
        "VNV" : "VNV"
    };

    var asignarPorBolivianos = {
        "TON. BRUTA" : "TON. BRUTA",
        "SACO" : "SACO",
        "FIJO" : "FIJO"
    };

    $("#unidadDeDescuento").change(function() {
        cambiarAsignacionDelDescuento();
    });

    function cambiarAsignacionDelDescuento(){
        var op=$("#unidadDeDescuento").val();
        var opciones=null;

        if(op=="%")
            opciones=asignarPorPorcentaje;

        if(op=="Bs")
            opciones=asignarPorBolivianos;

        //primero quitamos las opciones
        var mySelect = $('#asignacionDelDescuento');
        mySelect.empty();
        $.each(opciones, function(val, text) {
            mySelect.append(
                $('<option></option>').val(val).html(text)
            );
        });
    }
});
