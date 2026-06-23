$(document).ready(function() {
    $("#elemento" ).change(function() {
        var elem=$("#elemento").val();

        if(elem=="Estano"){
            $( "#_resultadosEstano" ).show();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
            $( "#_resultadosCobrePlata" ).hide();
        }
        if(elem=="Plata"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).show();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
            $( "#_resultadosCobrePlata" ).hide();
        }
        if(elem=="Wolfran"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).show();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
            $( "#_resultadosCobrePlata" ).hide();
        }
        if(elem=="Antimonio"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).show();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
            $( "#_resultadosCobrePlata" ).hide();
        }
        if(elem=="Complejo"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).show();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
            $( "#_resultadosCobrePlata" ).hide();
        }

        if(elem=="Plomo-Plata"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).show();
            $( "#_resultadosZincPlata" ).hide();
            $( "#_resultadosCobrePlata" ).hide();
        }

        if(elem=="Zinc-Plata"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).show();
            $( "#_resultadosCobrePlata" ).hide();
        }

        if(elem=="Cobre-Plata"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
            $( "#_resultadosCobrePlata" ).show();
        }
    });
});

