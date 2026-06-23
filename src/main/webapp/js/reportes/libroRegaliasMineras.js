$(document).ready(function() {
    $("#elemento" ).change(function() {
        var elem=$("#elemento").val();

        if(elem=="Estano"){
            $( "#_resultadosEstano" ).show();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
        }
        if(elem=="Plata"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).show();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
        }
        if(elem=="Wolfran"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).show();
            $( "#_resultadosComplejo" ).hide();
        }
        if(elem=="Antimonio"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).show();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
        }
        if(elem=="Complejo"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).show();
        }

        if(elem=="Plomo Plata"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).show();
        }

        if(elem=="Zinc Plata"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).show();
        }
    });
});

