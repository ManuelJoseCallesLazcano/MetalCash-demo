$(document).ready(function () {
    $("#tipoDeMineral").change(function() {
        desplegarCampos();
    });

    desplegarCampos();

    function desplegarCampos(){
        var e=$("#tipoDeMineral").val();
        if(e=="PB-AG"){
            $("#_deduccionUnitariaZinc").hide();
            $("#deduccionUnitariaZinc").val("0");

            $("#_deduccionUnitariaPlomo").show();
//            $("#deduccionUnitariaPlomo").val("");

            $("#_deduccionUnitariaPlata").show();
//            $("#deduccionUnitariaPlata").val("");

            $("#_deduccionUnitariaCobre").hide();
            $("#deduccionUnitariaCobre").val("0");

            $("#_porcentajePagableLMEZinc").hide();
            $("#porcentajePagableLMEZinc").val("0");

            $("#_porcentajePagableLMEPlomo").show();
//            $("#porcentajePagableLMEPlomo").val("");

            $("#_porcentajePagableLMEPlata").show();
//            $("#porcentajePagableLMEPlata").val("");

            $("#_porcentajePagableLMECobre").hide();
            $("#porcentajePagableLMECobre").val("0");

            $("#_maquilaZincPlata").hide();
            $("#maquilaZincPlata").val("0");
            $("#_baseZincPlata").hide();
            $("#baseZincPlata").val("0");
            $("#_escaladorZincPlata").hide();
            $("#escaladorZincPlata").val("0");

            $("#_maquilaPlomoPlata").show();
//            $("#maquilaPlomoPlata").val("");
            $("#_basePlomoPlata").show();
//            $("#basePlomoPlata").val("");
            $("#_escaladorPlomoPlata").show();
//            $("#escaladorPlomoPlata").val("");

            $("#_maquilaCobre").hide();
            $("#maquilaCobre").val("0");
            $("#_baseCobre").hide();
            $("#baseCobre").val("0");
            $("#_escaladorCobre").hide();
            $("#escaladorCobre").val("0");

            $("#_deduccionRefinacionOnzaZincPlata").hide();
            $("#deduccionRefinacionOnzaZincPlata").val("0");

            $("#_deduccionRefinacionOnzaPlomoPlata").show();
//            $("#deduccionRefinacionOnzaPlomoPlata").val("");

            $("#_deduccionRefinacionOnzaCobrePlata").hide();
            $("#deduccionRefinacionOnzaCobrePlata").val("0");

            $("#_deduccionRefinacionLibraZinc").hide();
            $("#deduccionRefinacionLibraZinc").val("0");

            $("#_deduccionRefinacionLibraPlomo").show();
//            $("#deduccionRefinacionLibraPlomo").val("");

            $("#_deduccionRefinacionLibraCobre").hide();
            $("#deduccionRefinacionLibraCobre").val("0");
        }
        if(e=="ZN-AG"){
            $("#_deduccionUnitariaZinc").show();
//            $("#deduccionUnitariaZinc").val("");

            $("#_deduccionUnitariaPlomo").hide();
            $("#deduccionUnitariaPlomo").val("0");

            $("#_deduccionUnitariaPlata").show();
//            $("#deduccionUnitariaPlata").val("");

            $("#_deduccionUnitariaCobre").hide();
            $("#deduccionUnitariaCobre").val("0");

            $("#_porcentajePagableLMEZinc").show();
//            $("#porcentajePagableLMEZinc").val("");

            $("#_porcentajePagableLMEPlomo").hide();
            $("#porcentajePagableLMEPlomo").val("0");

            $("#_porcentajePagableLMEPlata").show();
//            $("#porcentajePagableLMEPlata").val("");

            $("#_porcentajePagableLMECobre").hide();
            $("#porcentajePagableLMECobre").val("0");            

            $("#_maquilaZincPlata").show();
//            $("#maquilaZincPlata").val("");
            $("#_baseZincPlata").show();
//            $("#baseZincPlata").val("");
            $("#_escaladorZincPlata").show();
//            $("#escaladorZincPlata").val("");

            $("#_maquilaPlomoPlata").hide();
            $("#maquilaPlomoPlata").val("0");
            $("#_basePlomoPlata").hide();
            $("#basePlomoPlata").val("0");
            $("#_escaladorPlomoPlata").hide();
            $("#escaladorPlomoPlata").val("0");

            $("#_maquilaCobre").hide();
            $("#maquilaCobre").val("0");
            $("#_baseCobre").hide();
            $("#baseCobre").val("0");
            $("#_escaladorCobre").hide();
            $("#escaladorCobre").val("0");

            $("#_deduccionRefinacionOnzaZincPlata").show();
//            $("#deduccionRefinacionOnzaZincPlata").val("");

            $("#_deduccionRefinacionOnzaPlomoPlata").hide();
            $("#deduccionRefinacionOnzaPlomoPlata").val("0");

            $("#_deduccionRefinacionOnzaCobrePlata").hide();
            $("#deduccionRefinacionOnzaCobrePlata").val("0");

            $("#_deduccionRefinacionLibraZinc").show();
//            $("#deduccionRefinacionLibraZinc").val("");

            $("#_deduccionRefinacionLibraPlomo").hide();
            $("#deduccionRefinacionLibraPlomo").val("0");

            $("#_deduccionRefinacionLibraCobre").hide();
            $("#deduccionRefinacionLibraCobre").val("0");
        }
        if(e=="CU-AG"){
            $("#_deduccionUnitariaZinc").hide();
            $("#deduccionUnitariaZinc").val("0");

            $("#_deduccionUnitariaPlomo").hide();
            $("#deduccionUnitariaPlomo").val("0");

            $("#_deduccionUnitariaPlata").show();
//            $("#deduccionUnitariaPlata").val("");

            $("#_deduccionUnitariaCobre").show();
//            $("#deduccionUnitariaCobre").val("");

            $("#_porcentajePagableLMEZinc").hide();
            $("#porcentajePagableLMEZinc").val("0");

            $("#_porcentajePagableLMEPlomo").hide();
            $("#porcentajePagableLMEPlomo").val("0");

            $("#_porcentajePagableLMEPlata").show();
//            $("#porcentajePagableLMEPlata").val("");

            $("#_porcentajePagableLMECobre").show();
//            $("#porcentajePagableLMECobre").val("");

            $("#_maquilaZincPlata").hide();
            $("#maquilaZincPlata").val("0");
            $("#_baseZincPlata").hide();
            $("#baseZincPlata").val("0");
            $("#_escaladorZincPlata").hide();
            $("#escaladorZincPlata").val("0");

            $("#_maquilaPlomoPlata").hide();
            $("#maquilaPlomoPlata").val("0");
            $("#_basePlomoPlata").hide();
            $("#basePlomoPlata").val("0");
            $("#_escaladorPlomoPlata").hide();
            $("#escaladorPlomoPlata").val("0");

            $("#_maquilaCobre").show();
//            $("#maquilaCobre").val("");
            $("#_baseCobre").show();
//            $("#baseCobre").val("");
            $("#_escaladorCobre").show();
//            $("#escaladorCobre").val("");

            $("#_deduccionRefinacionOnzaZincPlata").hide();
            $("#deduccionRefinacionOnzaZincPlata").val("0");

            $("#_deduccionRefinacionOnzaPlomoPlata").hide();
            $("#deduccionRefinacionOnzaPlomoPlata").val("0");

            $("#_deduccionRefinacionOnzaCobrePlata").show();
//            $("#deduccionRefinacionOnzaCobrePlata").val("");

            $("#_deduccionRefinacionLibraZinc").hide();
            $("#deduccionRefinacionLibraZinc").val("0");

            $("#_deduccionRefinacionLibraPlomo").hide();
            $("#deduccionRefinacionLibraPlomo").val("0");

            $("#_deduccionRefinacionLibraCobre").show();
//            $("#deduccionRefinacionLibraCobre").val("");
        }
    }
});


