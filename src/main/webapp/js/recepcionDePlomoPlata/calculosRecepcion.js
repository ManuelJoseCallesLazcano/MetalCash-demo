$(document).ready(function() {
        $("#costoLaboratorio1, #costoLaboratorio2, #costoLaboratorio3, #costoLaboratorio4").bind("keyup",calcularTotalCostoLaboratorio);
    }
);


function calcularTotalCostoLaboratorio(){
    var costoLaboratorio1 = parseFloat($("#costoLaboratorio1").val());
    costoLaboratorio1 = isNaN(costoLaboratorio1)?0:costoLaboratorio1;
    var costoLaboratorio2 = parseFloat($("#costoLaboratorio2").val());
    costoLaboratorio2 = isNaN(costoLaboratorio2)?0:costoLaboratorio2;
    var costoLaboratorio3 = parseFloat($("#costoLaboratorio3").val());
    costoLaboratorio3 = isNaN(costoLaboratorio3)?0:costoLaboratorio3;
    var costoLaboratorio4 = parseFloat($("#costoLaboratorio4").val());
    costoLaboratorio4 = isNaN(costoLaboratorio4)?0:costoLaboratorio4;
    //calculos
    var totalCostoLaboratorio = costoLaboratorio1+costoLaboratorio2+costoLaboratorio3+costoLaboratorio4;
    $("#totalCostoLaboratorio").val((isNaN(totalCostoLaboratorio)) ?"?":toFixed(totalCostoLaboratorio,2).toString());
}

function toFixed( number, precision ) {
    var multiplier = Math.pow( 10, precision );
    return Math.round( number * multiplier ) / multiplier;
}
