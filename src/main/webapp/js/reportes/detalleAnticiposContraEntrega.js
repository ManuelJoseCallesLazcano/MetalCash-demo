$(document).ready(function() {
    $("#FECHA_INICIAL_1").val(getFechaInicial());
    $("#FECHA_INICIAL_2").val(getFechaInicial());
    $("#FECHA_INICIAL_3").val(getFechaInicial());
    $("#FECHA_INICIAL_4").val(getFechaInicial());
    $("#FECHA_FINAL_1").val(getFechaFinal());
    $("#FECHA_FINAL_2").val(getFechaFinal());
    $("#FECHA_FINAL_3").val(getFechaFinal());
    $("#FECHA_FINAL_4").val(getFechaFinal());
    
    $("#fechaInicial_day,#fechaInicial_month,#fechaInicial_year").change(function() {       
        $("#FECHA_INICIAL_1").val(getFechaInicial());
        $("#FECHA_INICIAL_2").val(getFechaInicial());
        $("#FECHA_INICIAL_3").val(getFechaInicial());
        $("#FECHA_INICIAL_4").val(getFechaInicial());
    });

    $("#fechaFinal_day,#fechaFinal_month,#fechaFinal_year").change(function() {
        $("#FECHA_FINAL_1").val(getFechaFinal());
        $("#FECHA_FINAL_2").val(getFechaFinal());
        $("#FECHA_FINAL_3").val(getFechaFinal());
        $("#FECHA_FINAL_4").val(getFechaFinal());
    });

    $("#empresa" ).change(function() {
        var empr=$("#empresa").val();
        $("#EMPRESA_ID_2").val(empr);
        $("#EMPRESA_ID_4").val(empr);
    });

    $("#numeroAnticipoInicial" ).keyup(function() {
        var li=$("#numeroAnticipoInicial").val();
        $("#NUMERO_ANTICIPO_INICIAL_3").val(li);
        $("#NUMERO_ANTICIPO_INICIAL_4").val(li);
    });

    $("#numeroAnticipoFinal" ).keyup(function() {
        var lf=$("#numeroAnticipoFinal").val();
        $("#NUMERO_ANTICIPO_FINAL_3").val(lf);
        $("#NUMERO_ANTICIPO_FINAL_4").val(lf);
    });

    $("#fechas").click(function(){ //_numeroAnticipoInicial
        if ($("#fechas").prop("checked")) {
            //alert("seleccionado: FECHAS!");
            $( "#_empresa" ).hide();
            $( "#_fechaInicial" ).show();
            $( "#_fechaFinal" ).show();
            $( "#_numeroAnticipoInicial" ).hide();
            $( "#_numeroAnticipoFinal" ).hide();

            $( "#_ReportePorFechas" ).show();
            $( "#_ReportePorFechasEmpresa" ).hide();
            $( "#_ReportePorNumeroAnticipo" ).hide();
            $( "#_ReportePorNumeroAnticipoEmpresa" ).hide();
        }
    });

    $("#fechasEmpresa").click(function(){
        if ($("#fechasEmpresa").prop("checked")) {
            //alert("seleccionado: FECHAS Y EMPRESA!");
            $( "#_empresa" ).show();
            $( "#_fechaInicial" ).show();
            $( "#_fechaFinal" ).show();
            $( "#_numeroAnticipoInicial" ).hide();
            $( "#_numeroAnticipoFinal" ).hide();

            $( "#_ReportePorFechas" ).hide();
            $( "#_ReportePorFechasEmpresa" ).show();
            $( "#_ReportePorNumeroAnticipo" ).hide();
            $( "#_ReportePorNumeroAnticipoEmpresa" ).hide();
        }
    });

    $("#lotes").click(function(){
        if ($("#lotes").prop("checked")) {
            //alert("seleccionado: LOTES!");
            $( "#_empresa" ).hide();
            $( "#_fechaInicial" ).hide();
            $( "#_fechaFinal" ).hide();
            $( "#_numeroAnticipoInicial" ).show();
            $( "#_numeroAnticipoFinal" ).show();

            $( "#_ReportePorFechas" ).hide();
            $( "#_ReportePorFechasEmpresa" ).hide();
            $( "#_ReportePorNumeroAnticipo" ).show();
            $( "#_ReportePorNumeroAnticipoEmpresa" ).hide();
        }
    });

    $("#lotesEmpresa").click(function(){
        if ($("#lotesEmpresa").prop("checked")) {
            //alert("seleccionado: LOTES Y EMPRESA!");
            $( "#_empresa" ).show();
            $( "#_fechaInicial" ).hide();
            $( "#_fechaFinal" ).hide();
            $( "#_numeroAnticipoInicial" ).show();
            $( "#_numeroAnticipoFinal" ).show();

            $( "#_ReportePorFechas" ).hide();
            $( "#_ReportePorFechasEmpresa" ).hide();
            $( "#_ReportePorNumeroAnticipo" ).hide();
            $( "#_ReportePorNumeroAnticipoEmpresa" ).show();
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

