$(document).ready(function() {
    $(".chosen-select").chosen();

    var fechaInicial;
    var fechaFinal;
    var loteInicial;
    var loteFinal;
    var empresaId;

    $("#buscarEstano").click(function(){
        var tipoBusqueda = $("#tipoReporte").val();
        if(tipoBusqueda=="fechas"){
            tablaId = $("#tablaCotizacionEstano").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            alert("ESTANO FI: "+fechaInicial+" FF: "+fechaFinal+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="fechasEmpresa"){
            tablaId = $("#tablaCotizacionEstano").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            empresaId = $("#empresa").val();
            alert("ESTANO FI: "+fechaInicial+" FF: "+fechaFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="lotes"){
            tablaId = $("#tablaCotizacionEstano").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            alert("ESTANO LI: "+loteInicial+" LF: "+loteFinal+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="lotesEmpresa"){
            tablaId = $("#tablaCotizacionEstano").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            empresaId = $("#empresa").val();
            alert("ESTANO LI: "+loteInicial+" LF: "+loteFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
        }
    });

    $("#buscarPlata").click(function(){
        var tipoBusqueda = $("#tipoReporte").val();
        if(tipoBusqueda=="fechas"){
            tablaId = $("#tablaCotizacionPlata").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            alert("Plata FI: "+fechaInicial+" FF: "+fechaFinal+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="fechasEmpresa"){
            tablaId = $("#tablaCotizacionPlata").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            empresaId = $("#empresa").val();
            alert("Plata FI: "+fechaInicial+" FF: "+fechaFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="lotes"){
            tablaId = $("#tablaCotizacionPlata").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            alert("Plata LI: "+loteInicial+" LF: "+loteFinal+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="lotesEmpresa"){
            tablaId = $("#tablaCotizacionPlata").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            empresaId = $("#empresa").val();
            alert("Plata LI: "+loteInicial+" LF: "+loteFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
        }
    });

    $("#buscarAntimonio").click(function(){
        var tipoBusqueda = $("#tipoReporte").val();
        if(tipoBusqueda=="fechas"){
            tablaId = $("#tablaCotizacionAntimonio").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            //alert("Antimonio FI: "+fechaInicial+" FF: "+fechaFinal+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="fechasEmpresa"){
            tablaId = $("#tablaCotizacionAntimonio").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            empresaId = $("#empresa").val();
            //alert("Antimonio FI: "+fechaInicial+" FF: "+fechaFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="lotes"){
            tablaId = $("#tablaCotizacionAntimonio").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            //alert("Antimonio LI: "+loteInicial+" LF: "+loteFinal+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="lotesEmpresa"){
            tablaId = $("#tablaCotizacionAntimonio").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            empresaId = $("#empresa").val();
            //alert("Antimonio LI: "+loteInicial+" LF: "+loteFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
        }
    });

    $("#buscarWolfran").click(function(){
        var tipoBusqueda = $("#tipoReporte").val();
        if(tipoBusqueda=="fechas"){
            tablaId = $("#tablaCotizacionWolfran").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            //alert("Wolfran FI: "+fechaInicial+" FF: "+fechaFinal+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="fechasEmpresa"){
            tablaId = $("#tablaCotizacionWolfran").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            empresaId = $("#empresa").val();
            //alert("Wolfran FI: "+fechaInicial+" FF: "+fechaFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="lotes"){
            tablaId = $("#tablaCotizacionWolfran").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            //alert("Wolfran LI: "+loteInicial+" LF: "+loteFinal+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="lotesEmpresa"){
            tablaId = $("#tablaCotizacionWolfran").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            empresaId = $("#empresa").val();
            //alert("Wolfran LI: "+loteInicial+" LF: "+loteFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
        }
    });

    $("#buscarComplejo").click(function(){
        var tipoBusqueda = $("#tipoReporte").val();
        if(tipoBusqueda=="fechas"){
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            //alert("Complejo FI: "+fechaInicial+" FF: "+fechaFinal+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="fechasEmpresa"){
            tablaId = $("#tablaCotizacionComplejo").val();
            fechaInicial = getFechaInicial();
            fechaFinal = getFechaFinal();
            empresaId = $("#empresa").val();
            //alert("Complejo FI: "+fechaInicial+" FF: "+fechaFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="lotes"){
            tablaId = $("#tablaCotizacionComplejo").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            //alert("Complejo LI: "+loteInicial+" LF: "+loteFinal+" TABLAid: "+tablaId);
        }
        if(tipoBusqueda=="lotesEmpresa"){
            tablaId = $("#tablaCotizacionComplejo").val();
            loteInicial = $("#loteInicial").val();
            loteFinal = $("#loteFinal").val();
            empresaId = $("#empresa").val();
            //alert("Complejo LI: "+loteInicial+" LF: "+loteFinal+" Eid: "+empresaId+" TABLAid: "+tablaId);
        }
    });

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
            $( "#_leyesCobrePlata" ).hide();
            
            $( "#_resultadosEstano" ).show();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
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
            $( "#_resultadosCobrePlata" ).hide();
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
            $( "#_resultadosCobrePlata" ).hide();
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
            $( "#_resultadosCobrePlata" ).hide();
        }

        if(elem=="Cobre Plata"){
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
            $( "#_resultadosZincPlata" ).hide();
            $( "#_resultadosCobrePlata" ).show();
        }
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
});

