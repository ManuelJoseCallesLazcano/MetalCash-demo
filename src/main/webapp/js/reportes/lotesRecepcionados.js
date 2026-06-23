$(document).ready(function() {
    $(".chosen-select").chosen({
        width: '350px'
    });

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

    $("#elemento" ).change(function() {
        var elem=$("#elemento").val();
        $("#ELEMENTO_1").val(elem);
        $("#ELEMENTO_CLASS_1").val(getElementoClass(elem));
        $("#ELEMENTO_2").val(elem);
        $("#ELEMENTO_CLASS_2").val(getElementoClass(elem));
        $("#ELEMENTO_3").val(elem);
        $("#ELEMENTO_CLASS_3").val(getElementoClass(elem));
        $("#ELEMENTO_4").val(elem);
        $("#ELEMENTO_CLASS_4").val(getElementoClass(elem));

        if(elem=="Complejo"){
            $( "#_complejo" ).show();
            $( "#_plomoPlata" ).hide();
            $( "#_zincPlata" ).hide();
            $( "#_cobrePlata" ).hide();
        }
        if(elem=="Plomo Plata"){
            $( "#_complejo" ).hide();
            $( "#_plomoPlata" ).show();
            $( "#_zincPlata" ).hide();
            $( "#_cobrePlata" ).hide();
        }
        if(elem=="Zinc Plata"){
            $( "#_complejo" ).hide();
            $( "#_plomoPlata" ).hide();
            $( "#_zincPlata" ).show();
            $( "#_cobrePlata" ).hide();
        }
        if(elem=="Cobre Plata"){
            $( "#_complejo" ).hide();
            $( "#_plomoPlata" ).hide();
            $( "#_zincPlata" ).hide();
            $( "#_cobrePlata" ).show();
        }
        //Cobre Plata
    });

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

    $("#empresa" ).change(function() {
        var empr=$("#empresa").val();
        $("#EMPRESA_ID_2").val(empr);
        $("#EMPRESA_ID_4").val(empr);
        $("#EMPRESA_ID_5").val(empr);
    });

    $("#loteInicial" ).keyup(function() {
        var li=$("#loteInicial").val();
        $("#LOTE_INICIAL_3").val(li);
        $("#LOTE_INICIAL_4").val(li);
        $("#LOTE_INICIAL_5").val(li);
    });

    $("#loteFinal" ).keyup(function() {
        var lf=$("#loteFinal").val();
        $("#LOTE_FINAL_3").val(lf);
        $("#LOTE_FINAL_4").val(lf);
        $("#LOTE_FINAL_5").val(lf);
    });

    // $("#estado" ).change(function() {
    //     var elem=$("#estado").val();
    //     $("#ESTADO_LOTE_1").val(elem);
    //     $("#ESTADO_LOTE_2").val(elem);
    //     $("#ESTADO_LOTE_3").val(elem);
    //     $("#ESTADO_LOTE_4").val(elem);
    //
    //     if(elem=="Todos"){
    //         $( "#_ReportePorTodos" ).show();
    //
    //         $( "#_ReportePorFechas" ).hide();
    //         $( "#_ReportePorFechasEmpresa" ).hide();
    //         $( "#_ReportePorLotes" ).hide();
    //         $( "#_ReportePorLotesEmpresa" ).hide();
    //     }else{
    //         $( "#_ReportePorTodos" ).hide();
    //
    //         $('#fechas').prop('checked', true);
    //         $( "#_empresa" ).hide();
    //         $( "#_fechaInicial" ).show();
    //         $( "#_fechaFinal" ).show();
    //         $( "#_loteInicial" ).hide();
    //         $( "#_loteFinal" ).hide();
    //
    //         $( "#_ReportePorFechas" ).show();
    //         $( "#_ReportePorFechasEmpresa" ).hide();
    //         $( "#_ReportePorLotes" ).hide();
    //         $( "#_ReportePorLotesEmpresa" ).hide();
    //
    //         $("#tipoReporte").val("fechas");
    //     }
    // });

    $("#fechas").click(function(){
        if ($("#fechas").prop("checked")) {
            //alert("seleccionado: FECHAS!");
            $( "#_empresa" ).hide();
            $( "#_fechaInicial" ).show();
            $( "#_fechaFinal" ).show();
            $( "#_loteInicial" ).hide();
            $( "#_loteFinal" ).hide();

            if($("#estado").val()!="Todos"){
                $( "#_ReportePorFechas" ).show();
                $( "#_ReportePorFechasEmpresa" ).hide();
                $( "#_ReportePorLotes" ).hide();
                $( "#_ReportePorLotesEmpresa" ).hide();
            }

            $("#tipoReporte").val("fechas");
        }
    });

    $("#fechasEmpresa").click(function(){
        if ($("#fechasEmpresa").prop("checked")) {
            //alert("seleccionado: FECHAS Y EMPRESA!");
            $( "#_empresa" ).show();
            $( "#_fechaInicial" ).show();
            $( "#_fechaFinal" ).show();
            $( "#_loteInicial" ).hide();
            $( "#_loteFinal" ).hide();

            if($("#estado").val()!="Todos"){
                $( "#_ReportePorFechas" ).hide();
                $( "#_ReportePorFechasEmpresa" ).show();
                $( "#_ReportePorLotes" ).hide();
                $( "#_ReportePorLotesEmpresa" ).hide();
            }

            $("#tipoReporte").val("fechasEmpresa");
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

    function getElementoClass(elem){
        //"Estano","Plata","Wolfran","Antimonio","Complejo","Plomo Plata","Zinc Plata"
        /*if(elem=="Estano") return "'org.socymet.recepcion.RecepcionDeEstano'";
        if(elem=="Plata") return "'org.socymet.recepcion.RecepcionDePlata'";
        if(elem=="Wolfran") return "'org.socymet.recepcion.RecepcionDeWolfran'";
        if(elem=="Antimonio") return "'org.socymet.recepcion.RecepcionDeAntimonio'";
        if(elem=="Complejo") return "'org.socymet.recepcion.RecepcionDeComplejo'";
        if(elem=="Plomo Plata") return "'org.socymet.recepcion.RecepcionDePlomoPlata'";
        if(elem=="Zinc Plata") return "'org.socymet.recepcion.RecepcionDeZincPlata'";*/
        if(elem=="Estano") return "org.socymet.recepcion.RecepcionDeEstano";
        if(elem=="Plata") return "org.socymet.recepcion.RecepcionDePlata";
        if(elem=="Wolfran") return "org.socymet.recepcion.RecepcionDeWolfran";
        if(elem=="Antimonio") return "org.socymet.recepcion.RecepcionDeAntimonio";
        if(elem=="Complejo") return "org.socymet.recepcion.RecepcionDeComplejo";
        if(elem=="Plomo Plata") return "org.socymet.recepcion.RecepcionDePlomoPlata";
        if(elem=="Zinc Plata") return "org.socymet.recepcion.RecepcionDeZincPlata";
        return "wtf?";
    }
});

