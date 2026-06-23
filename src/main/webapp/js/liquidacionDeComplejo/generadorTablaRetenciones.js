$(document).ready(function() {
        $("#merma, #humedad, #porcentajeZinc, #porcentajePlomo, #porcentajePlata, #dolarPuntoPlomo, #dolarPuntoPlata, #dolarPuntoZinc, #porcentajeRegalia").bind("keyup",generar);
    }
);

function generar(){
    var retencionesJSON = jQuery.parseJSON($("#retenciones").val());
    var valorOficialBruto = parseFloat($("#valorOficialBruto").val());
    var valorDeCompra = parseFloat($("#valorDeCompra").val());
    var cantidadDeSacos = parseFloat($("#cantidadDeSacos").val());
    var regaliaMinera = parseFloat($("#regaliaMinera").val());
    var totalRetenciones = regaliaMinera;
    $.each(retencionesJSON,function(){
        if(this.DESCRIPCION=="REGALIA MINERA")
            this.MONTO = regaliaMinera;
        else{
            var descuento = parseFloat(this.CANTIDAD);
            var unidad = this.UNIDAD;
            var asignacion = this.ASIGNACION;
            var monto=0;
            if(unidad=="%"&&asignacion=="VBV")
                monto = valorOficialBruto*descuento/100;
            if(unidad=="%"&&asignacion=="VNV")
                monto = valorDeCompra*descuento/100;
            if(unidad=="Bs"&&asignacion=="SACO")
                monto = cantidadDeSacos*descuento;
            if(unidad=="Bs"&&asignacion=="FIJO")
                monto = descuento;
            monto=isNaN(monto)?0:toFixed(monto,2)
            this.MONTO = monto.toString();
            //alert("descuento = "+this.CANTIDAD+" por "+this.DESCRIPCION+" monto = "+this.MONTO);
            totalRetenciones+=monto;
        }
    });
    var totalPagado = valorDeCompra - totalRetenciones;
    $("#retenciones").val(JSON.stringify(retencionesJSON));
    $("#totalRetenciones").val((isNaN(totalRetenciones)) ?"?":toFixed(totalRetenciones,2).toString());
    $("#totalPagado").val((isNaN(totalPagado)) ?"?":toFixed(totalPagado,2).toString());
    $("#tabla_retenciones").find("tr:gt(0)").remove();
    buildHtmlTableToEdit("retenciones","tabla_retenciones")
}

function toFixed( number, precision ) {
    var multiplier = Math.pow( 10, precision );
    return Math.round( number * multiplier ) / multiplier;
}

function eliminarFilaRetencion() {
    try {
        var table = document.getElementById("tabla_retenciones");
        var rowCount = table.rows.length;
        for (var i = 0; i < rowCount; i++) {
            var row = table.rows[i];
            var chkbox = row.cells[0].childNodes[0];
            if (null != chkbox && true == chkbox.checked) {
                table.deleteRow(i);
                rowCount--;
                i--;
            }
        }
    }
    catch (e) {
        alert(e);
    }
    document.getElementById("retenciones").value=JSON.stringify($("#tabla_retenciones").tableToJSON({ignoreColumns: [0]}));
    generar();
}
