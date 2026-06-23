$(document).ready(function() {
    $(".chosen-select").chosen({
        width: '350px'
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

