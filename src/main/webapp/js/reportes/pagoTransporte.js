$(document).ready(function() {
    $(".chosen-select").chosen({
        width: '350px'
    });

    $("#fechas").click(function(){
        if ($("#fechas").prop("checked")) {
            $( "#tipoReporte" ).val("fechas");

            $( "#_empresa" ).hide();
            $( "#_automovil" ).hide();

            $( "#_ReportePorFechas" ).show();
            $( "#_ReportePorFechasEmpresa" ).hide();
            $( "#_ReportePorFechasAutomovil" ).hide();
        }
    });

    $("#fechasEmpresa").click(function(){
        if ($("#fechasEmpresa").prop("checked")) {
            $( "#tipoReporte" ).val("fechasEmpresa");

            $( "#_empresa" ).show();
            $( "#_automovil" ).hide();

            $( "#_ReportePorFechas" ).hide();
            $( "#_ReportePorFechasEmpresa" ).show();
            $( "#_ReportePorFechasAutomovil" ).hide();
        }
    });

    $("#fechasAutomovil").click(function(){
        if ($("#fechasAutomovil").prop("checked")) {
            $( "#tipoReporte" ).val("fechasAutomovil");

            $( "#_empresa" ).hide();
            $( "#_automovil" ).show();

            $( "#_ReportePorFechas" ).hide();
            $( "#_ReportePorFechasEmpresa" ).hide();
            $( "#_ReportePorFechasAutomovil" ).show();
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

