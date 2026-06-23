$(document).ready(function() {
    var tablaId;
    var fechaInicial;
    var fechaFinal;
    var loteInicial;
    var loteFinal;
    var empresaId;

    $("#elemento" ).change(function() {
        var elem=$("#elemento").val();

        if(elem=="Estano"){
            $( "#_leyesEstano" ).show();
            $( "#_leyesPlata" ).hide();
            $( "#_leyesAntimonio" ).hide();
            $( "#_leyesWolfran" ).hide();
            $( "#_leyesComplejo" ).hide();
            $( "#_leyesPlomoPlata" ).hide();
            $( "#_leyesZincPlata" ).hide();
            
            $( "#_resultadosEstano" ).show();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
        }
        if(elem=="Plata"){
            $( "#_leyesEstano" ).hide();
            $( "#_leyesPlata" ).show();
            $( "#_leyesAntimonio" ).hide();
            $( "#_leyesWolfran" ).hide();
            $( "#_leyesComplejo" ).hide();
            $( "#_leyesPlomoPlata" ).hide();
            $( "#_leyesZincPlata" ).hide();

            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).show();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
        }
        if(elem=="Wolfran"){
            $( "#_leyesEstano" ).hide();
            $( "#_leyesPlata" ).hide();
            $( "#_leyesAntimonio" ).hide();
            $( "#_leyesWolfran" ).show();
            $( "#_leyesComplejo" ).hide();
            $( "#_leyesPlomoPlata" ).hide();
            $( "#_leyesZincPlata" ).hide();

            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).show();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
        }
        if(elem=="Antimonio"){
            $( "#_leyesEstano" ).hide();
            $( "#_leyesPlata" ).hide();
            $( "#_leyesAntimonio" ).show();
            $( "#_leyesWolfran" ).hide();
            $( "#_leyesComplejo" ).hide();
            $( "#_leyesPlomoPlata" ).hide();
            $( "#_leyesZincPlata" ).hide();

            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).show();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
        }
        if(elem=="Complejo"){
            $( "#_leyesEstano" ).hide();
            $( "#_leyesPlata" ).hide();
            $( "#_leyesAntimonio" ).hide();
            $( "#_leyesWolfran" ).hide();
            $( "#_leyesComplejo" ).show();
            $( "#_leyesPlomoPlata" ).hide();
            $( "#_leyesZincPlata" ).hide();

            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).show();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).hide();
        }

        if(elem=="Plomo Plata"){
            $( "#_leyesEstano" ).hide();
            $( "#_leyesPlata" ).hide();
            $( "#_leyesAntimonio" ).hide();
            $( "#_leyesWolfran" ).hide();
            $( "#_leyesComplejo" ).hide();
            $( "#_leyesPlomoPlata" ).show();
            $( "#_leyesZincPlata" ).hide();

            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).show();
            $( "#_resultadosZincPlata" ).hide();
        }

        if(elem=="Zinc Plata"){
            $( "#_leyesEstano" ).hide();
            $( "#_leyesPlata" ).hide();
            $( "#_leyesAntimonio" ).hide();
            $( "#_leyesWolfran" ).hide();
            $( "#_leyesComplejo" ).hide();
            $( "#_leyesPlomoPlata" ).hide();
            $( "#_leyesZincPlata" ).show();

            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
            $( "#_resultadosPlomoPlata" ).hide();
            $( "#_resultadosZincPlata" ).show();
        }
    });

    $("#tablaCotizacionEstano" ).change(function() {
        $("#tablaCotizacionEstanoId").val($("#tablaCotizacionEstano").val());
    });
    $("#tablaCotizacionPlata" ).change(function() {
        $("#tablaCotizacionPlataId").val($("#tablaCotizacionPlata").val());
    });
    $("#tablaCotizacionAntimonio" ).change(function() {
        $("#tablaCotizacionAntimonioId").val($("#tablaCotizacionAntimonio").val());
    });
    $("#tablaCotizacionWolfran" ).change(function() {
        $("#tablaCotizacionWolfranId").val($("#tablaCotizacionWolfran").val());
    });

    $("#asignarConjuntoALotes" ).change(function() {
        var elem=$("#asignarConjuntoALotes").val();
        if(elem=="SI")
            alert("ATENCION: Usted va a asignar el Nombre de Conjunto a las liquidaciones-humacayo a listarse.\n" +
                "Si algun Lote no debiera pertenecer al Conjunto listado usted lo deberá retornar a\n" +
                "Existencias manualmente en la ventana 'Mostrar Liquidacion' para la Liquidacion \n" +
                "asociada al Lote.");
    });
    

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

    $("#loteInicial" ).keyup(function() {
        var li=$("#loteInicial").val();
        $("#LOTE_INICIAL_3").val(li);
        $("#LOTE_INICIAL_4").val(li);
    });

    $("#loteFinal" ).keyup(function() {
        var lf=$("#loteFinal").val();
        $("#LOTE_FINAL_3").val(lf);
        $("#LOTE_FINAL_4").val(lf);
    });

    $("#fechas").click(function(){
        if ($("#fechas").prop("checked")) {
            //alert("seleccionado: FECHAS!");
            $( "#_empresa" ).hide();
            $( "#_fechaInicial" ).show();
            $( "#_fechaFinal" ).show();
            $( "#_loteInicial" ).hide();
            $( "#_loteFinal" ).hide();

            $( "#tipoReporte" ).val("fechas");
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

            $( "#tipoReporte" ).val("fechasEmpresa");
        }
    });

    $("#lotes").click(function(){
        if ($("#lotes").prop("checked")) {
            //alert("seleccionado: LOTES!");
            $( "#_empresa" ).hide();
            $( "#_fechaInicial" ).hide();
            $( "#_fechaFinal" ).hide();
            $( "#_loteInicial" ).show();
            $( "#_loteFinal" ).show();

            $( "#tipoReporte" ).val("lotes");
        }
    });

    $("#lotesEmpresa").click(function(){
        if ($("#lotesEmpresa").prop("checked")) {
            //alert("seleccionado: LOTES Y EMPRESA!");
            $( "#_empresa" ).show();
            $( "#_fechaInicial" ).hide();
            $( "#_fechaFinal" ).hide();
            $( "#_loteInicial" ).show();
            $( "#_loteFinal" ).show();

            $( "#tipoReporte" ).val("lotesEmpresa");
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

