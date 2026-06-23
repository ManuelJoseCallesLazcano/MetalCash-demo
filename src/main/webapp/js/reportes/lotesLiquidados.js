$(document).ready(function() {
    $(".chosen-select").chosen({
        width: '350px'
    });

    var tablaId;
    var fechaInicial;
    var fechaFinal;
    var loteInicial;
    var loteFinal;
    var empresaId;

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

    $("#fechas").click(function(){
        if ($("#fechas").prop("checked")) {
            //alert("seleccionado: FECHAS!");
            $( "#_empresa" ).hide();
            $( "#_fechaInicial" ).show();
            $( "#_fechaFinal" ).show();
            $( "#tipoReporte" ).val("fechas");
        }
    });

    $("#fechasEmpresa").click(function(){
        if ($("#fechasEmpresa").prop("checked")) {
            //alert("seleccionado: FECHAS Y EMPRESA!");
            $( "#_empresa" ).show();
            $( "#_fechaInicial" ).show();
            $( "#_fechaFinal" ).show();
            $( "#tipoReporte" ).val("fechasEmpresa");
        }
    });

    $("#elemento" ).change(function() {
        var elem=$("#elemento").val();

        if(elem=="Complejo"){
            $( "#_resultadosComplejo" ).show();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
            $( "#_resultadosCobrePlata" ).hide();
        }

        if(elem=="Plomo Plata"){
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).show();
            $( "#_resultadosZincPlata" ).hide();
            $( "#_resultadosCobrePlata" ).hide();
        }

        if(elem=="Zinc Plata"){
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).show();
            $( "#_resultadosCobrePlata" ).hide();
        }

        if(elem=="Cobre Plata"){
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
            $( "#_resultadosCobrePlata" ).show();
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

