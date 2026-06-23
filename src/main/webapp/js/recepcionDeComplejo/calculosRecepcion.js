$(document).ready(function() {
    $("#pesoNeto,#pesoTara,#pesoBruto").numeric({
        allowMinus   : false,
        allowThouSep : false,
        maxDecimalPlaces:2
    });
        //definir grupo de radiobuttons
        $(function() {
            $( "#radio" ).buttonset();
        });

        $(function() {
            $( "#radio2" ).buttonset();
        });

        $(function() {
            $( "#radio3" ).buttonset();
        });

        $('input:radio[name="radio"]').change(
            function(){
                if (this.checked && this.value == 'complejo') {
                    $("#tipoDeMineral").val("COMPLEJO").trigger('change');
                }
                if (this.checked && this.value == 'plomoPlata') {
                    $("#tipoDeMineral").val("PB-AG").trigger('change');
                }
                if (this.checked && this.value == 'zincPlata') {
                    $("#tipoDeMineral").val("ZN-AG").trigger('change');
                }
                if (this.checked && this.value == 'cobrePlata') {
                    $("#tipoDeMineral").val("CU-AG").trigger('change');
                }
            }
        );

        $('input:radio[name="radio2"]').change(
            function(){
                if (this.checked && this.value == 'sulfuro') {
                    $("#naturalezaMineral").val("SULFURO");
                }
                if (this.checked && this.value == 'oxido') {
                    $("#naturalezaMineral").val("OXIDO");
                }
            }
        );

        $('input:radio[name="radio3"]').change(
            function(){
                if (this.checked && this.value == 'spot') {
                    $("#condicionDeEntrega").val("SPOT");
                }
                if (this.checked && this.value == 'termCon') {
                    $("#condicionDeEntrega").val("TERM-CON");
                }
            }
        );

        $('#tieneDocumentos').prop("checked",$("#documentacionCompleta").val()=="1");

        desplegarLote();
        tieneDocumentacion();
        establecerRadios();

        $("#tipoDeMineral").bind("change",desplegarLote);

        $("#pesoNeto,#pesoTara").bind("keyup",calcularPesoBruto);

        $("#pesoBruto").bind("keyup",function (){
            // $("#pesoNeto,#pesoTara").val(0)
        });

        $('#tieneDocumentos').change(function() {
            tieneDocumentacion();
        });

        // $("#deposito,#tipoDeMineral").change(function() {
        //     siguienteLote();
        // });

        $("#costoLaboratorio1, #costoLaboratorio2, #costoLaboratorio3, #costoLaboratorio4").bind("keyup",calcularTotalCostoLaboratorio);

        function tieneDocumentacion(){
            if ($('#tieneDocumentos').is(":checked")){
                $("#documentacionCompleta").val(1);
                $("#numeroDeDocumento").val("0").prop("readonly",false);
            }else{
                $("#documentacionCompleta").val(0);
                $("#numeroDeDocumento").val("?").prop("readonly",true);
            }
        }

        function establecerRadios(){
            var t = $("#tipoDeMineral").val();
            var n = $("#naturalezaMineral").val();
            if(t=="COMPLEJO"){
                $('#radioComplejo').prop('checked',true);
            }
            if(t=="PB-AG"){
                $('#radioPlomoPlata').prop('checked',true);
            }
            if(t=="ZN-AG"){
                $('#radioZincPlata').prop('checked',true);
            }
            if(t=="CU-AG"){
                $('#radioCobrePlata').prop('checked',true);
            }

            if(n=="SULFURO"){
                $('#radioSulfuro').prop('checked',true);
            }
            if(n=="OXIDO"){
                $('#radioOxido').prop('checked',true);
            }
        }
    }
);

function desplegarLote(){
    var e = $("#tipoDeMineral").val();
    if(e=="COMPLEJO"){
        $("#_loteComplejo").show();
        $("#_lotePlomoPlata").hide();
        $("#_loteZincPlata").hide();
        $("#_loteCobrePlata").hide();
    }
    if(e=="PB-AG"){
        $("#_loteComplejo").hide();
        $("#_lotePlomoPlata").show();
        $("#_loteZincPlata").hide();
        $("#_loteCobrePlata").hide();
    }
    if(e=="ZN-AG"){
        $("#_loteComplejo").hide();
        $("#_lotePlomoPlata").hide();
        $("#_loteZincPlata").show();
        $("#_loteCobrePlata").hide();
    }
    if(e=="CU-AG"){
        $("#_loteComplejo").hide();
        $("#_lotePlomoPlata").hide();
        $("#_loteZincPlata").hide();
        $("#_loteCobrePlata").show();
    }
}

function calcularPesoBruto(){
    let pesoNeto = parseFloat($("#pesoNeto").val())
    let pesoTara = parseFloat($("#pesoTara").val())
    let pesoBruto = pesoNeto-pesoTara
    $("#pesoBruto").val(isNaN(pesoBruto)?"?":pesoBruto)
}

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
