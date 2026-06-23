$(document).ready(function() {
    $("#FECHA_INICIAL_1").val(getFechaInicial());
    $("#FECHA_INICIAL_2").val(getFechaInicial());
    $("#FECHA_INICIAL_3").val(getFechaInicial());
    $("#FECHA_INICIAL_4").val(getFechaInicial());
    $("#FECHA_INICIAL_5").val(getFechaInicial());
    $("#FECHA_FINAL_1").val(getFechaFinal());
    $("#FECHA_FINAL_2").val(getFechaFinal());
    $("#FECHA_FINAL_3").val(getFechaFinal());
    $("#FECHA_FINAL_4").val(getFechaFinal());
    $("#FECHA_FINAL_5").val(getFechaFinal());
    
    $("#fechaInicial_day,#fechaInicial_month,#fechaInicial_year").change(function() {       
        $("#FECHA_INICIAL_1").val(getFechaInicial());
        $("#FECHA_INICIAL_2").val(getFechaInicial());
        $("#FECHA_INICIAL_3").val(getFechaInicial());
        $("#FECHA_INICIAL_4").val(getFechaInicial());
        $("#FECHA_INICIAL_5").val(getFechaInicial());
    });

    $("#fechaFinal_day,#fechaFinal_month,#fechaFinal_year").change(function() {
        $("#FECHA_FINAL_1").val(getFechaFinal());
        $("#FECHA_FINAL_2").val(getFechaFinal());
        $("#FECHA_FINAL_3").val(getFechaFinal());
        $("#FECHA_FINAL_4").val(getFechaFinal());
        $("#FECHA_FINAL_5").val(getFechaFinal());
    });

    $("#elemento" ).change(function() {
        var elemento=$("#elemento").val();

        if(elemento=="Estano"){
            $( "#_ReporteEstano" ).show();
            $( "#_ReportePlata" ).hide();
            $( "#_ReporteWolfran" ).hide();
            $( "#_ReporteAntimonio" ).hide();
            $( "#_ReporteComplejo" ).hide();
        }
        if(elemento=="Plata"){
            $( "#_ReporteEstano" ).hide();
            $( "#_ReportePlata" ).show();
            $( "#_ReporteWolfran" ).hide();
            $( "#_ReporteAntimonio" ).hide();
            $( "#_ReporteComplejo" ).hide();
        }
        if(elemento=="Wolfran"){
            $( "#_ReporteEstano" ).hide();
            $( "#_ReportePlata" ).hide();
            $( "#_ReporteWolfran" ).show();
            $( "#_ReporteAntimonio" ).hide();
            $( "#_ReporteComplejo" ).hide();
        }
        if(elemento=="Antimonio"){
            $( "#_ReporteEstano" ).hide();
            $( "#_ReportePlata" ).hide();
            $( "#_ReporteWolfran" ).hide();
            $( "#_ReporteAntimonio" ).show();
            $( "#_ReporteComplejo" ).hide();
        }
        if(elemento=="Complejo"){
            $( "#_ReporteEstano" ).hide();
            $( "#_ReportePlata" ).hide();
            $( "#_ReporteWolfran" ).hide();
            $( "#_ReporteAntimonio" ).hide();
            $( "#_ReporteComplejo" ).show();
        }
    });

    function getFechaInicial(){
        var day=$("#fechaInicial_day").val().toString();
        day=(day.length<2)?"0"+day:day;
        var month=$("#fechaInicial_month").val().toString();
        month=(month.length<2)?"0"+month:month;
        var year=$("#fechaInicial_year").val().toString();
        //'2013-12-03' 
        return year+"-"+month+"-"+day;
    }

    function getFechaFinal(){
        var day=$("#fechaFinal_day").val().toString();
        day=(day.length<2)?"0"+day:day;
        var month=$("#fechaFinal_month").val().toString();
        month=(month.length<2)?"0"+month:month;
        var year=$("#fechaFinal_year").val().toString();
        //'2013-12-03' 
        return year+"-"+month+"-"+day;
    }
});

