$(document).ready(function() {
    $("#toneladasMetricasHumedas,\n" +
        "#humedadPromedio,\n" +
        "#toneladasMetricasSecas,\n" +
        "#merma,\n" +
        "#toneladasMetricasSecasFinales,\n" +
        "#leyPlomo,\n" +
        "#leyPlata,\n" +
        "#cotizacionPlomo,\n" +
        "#cotizacionPlata,\n" +
        "#leyPlomoMineralesPagables,\n" +
        "#leyPlataMineralesPagables,\n" +
        "#deduccionUnitariaPlomo,\n" +
        "#deduccionUnitariaPlata,\n" +
        "#porcentajePagableLMEPlomo,\n" +
        "#porcentajePagableLMEPlata,\n" +
        "#cotizacionPlomoMineralesPagables,\n" +
        "#cotizacionPlataMineralesPagables,\n" +
        "#valorPagablePlomo,\n" +
        "#valorPagablePlata,\n" +
        "#valorPagableTotal,\n" +
        "#maquila,\n" +
        "#maquilaFinal,\n" +
        "#base,\n" +
        "#cotizacionPlomoActual,\n" +
        "#escaladorPlomoPlata,\n" +
        "#gastoRealizacionTotal,\n" +
        "#leyPlataOnzaTroy,\n" +
        "#costoRefinacion,\n" +
        "#costoRefinacionTotal,\n" +
        "#porcentajeArsenico,\n" +
        "#arsenicoLibre,\n" +
        "#costoUnitarioArsenico,\n" +
        "#porcentajeUnitarioArsenico,\n" +
        "#porcentajeAntimonio,\n" +
        "#antimonioLibre,\n" +
        "#costoUnitarioAntimonio,\n" +
        "#porcentajeUnitarioAntimonio,\n" +
        "#porcentajeBismuto,\n" +
        "#bismutoLibre,\n" +
        "#costoUnitarioBismuto,\n" +
        "#porcentajeUnitarioBismuto,\n" +
        "#porcentajeEstano,\n" +
        "#estanoLibre,\n" +
        "#costoUnitarioEstano,\n" +
        "#porcentajeUnitarioEstano,\n" +
        "#porcentajeHierro,\n" +
        "#hierroLibre,\n" +
        "#costoUnitarioHierro,\n" +
        "#porcentajeUnitarioHierro,\n" +
        "#porcentajeSilice,\n" +
        "#siliceLibre,\n" +
        "#costoUnitarioSilice,\n" +
        "#porcentajeUnitarioSilice,\n" +
        "#porcentajeZinc,\n" +
        "#zincLibre,\n" +
        "#costoUnitarioZinc,\n" +
        "#porcentajeUnitarioZinc,\n" +
        "#precioUnitario,\n" +
        "#valorNetoTotal,\n" +
        "#costoFleteTonelada,\n" +
        "#costoFleteToneladaTotal,\n" +
        "#costoPortuarioTonelada,\n" +
        "#costoPortuarioToneladaTotal,\n" +
        "#costoOperacionTonelada,\n" +
        "#costoOperacionToneladaTotal,\n" +
        "#regaliaDiferenciaPlomo,\n" +
        "#regaliaDiferenciaPlomoTotal,\n" +
        "#regaliaDiferenciaPlata,\n" +
        "#regaliaDiferenciaPlataTotal,\n" +
        "#descuentoOperacionesTotal,\n" +
        "#valorNetoTotalFinal").keyup(function(){
        calcularProforma();
    });

    function calcularProforma(){
        var toneladasMetricasHumedas=transFloat($("#toneladasMetricasHumedas").val());
        var humedadPromedio=transFloat($("#humedadPromedio").val());
        var toneladasMetricasSecas=toneladasMetricasHumedas-toneladasMetricasHumedas*humedadPromedio/100.0;
        $("#toneladasMetricasSecas").val(isNaN(toneladasMetricasSecas)?"?":redondear2(toneladasMetricasSecas));

        var merma=transFloat($("#merma").val());
        var toneladasMetricasSecasFinales=toneladasMetricasSecas-toneladasMetricasSecas*merma/100.0;
        $("#toneladasMetricasSecasFinales").val(isNaN(toneladasMetricasSecasFinales)?"?":redondear2(toneladasMetricasSecasFinales));
        
        var leyPlomo=transFloat($("#leyPlomo").val());
        var leyPlomoMineralesPagables=leyPlomo;
        $("#leyPlomoMineralesPagables").val(isNaN(leyPlomoMineralesPagables)?"?":redondear2(leyPlomoMineralesPagables));
        
        var leyPlata=transFloat($("#leyPlata").val());
        var leyPlataMineralesPagables=leyPlata/31.1035;
        $("#leyPlataMineralesPagables,#leyPlataOnzaTroy").val(isNaN(leyPlataMineralesPagables)?"?":redondear2(leyPlataMineralesPagables));
        
        var cotizacionPlomo=transFloat($("#cotizacionPlomo").val());
        var cotizacionPlomoMineralesPagables=cotizacionPlomo;
        $("#cotizacionPlomoMineralesPagables,#cotizacionPlomoActual").val(isNaN(cotizacionPlomoMineralesPagables)?"?":redondear2(cotizacionPlomoMineralesPagables));

        var cotizacionPlata=transFloat($("#cotizacionPlata").val());
        var cotizacionPlataMineralesPagables=cotizacionPlata;
        $("#cotizacionPlataMineralesPagables").val(isNaN(cotizacionPlataMineralesPagables)?"?":redondear2(cotizacionPlataMineralesPagables));
        
        var deduccionUnitariaPlomo=transFloat($("#deduccionUnitariaPlomo").val());
        var porcentajePagableLMEPlomo=transFloat($("#porcentajePagableLMEPlomo").val());
        var valorPagablePlomo=(leyPlomoMineralesPagables-deduccionUnitariaPlomo)/100.0*porcentajePagableLMEPlomo/100.0*cotizacionPlomoMineralesPagables;
        $("#valorPagablePlomo").val(isNaN(valorPagablePlomo)?"?":redondear2(valorPagablePlomo));

        var deduccionUnitariaPlata=transFloat($("#deduccionUnitariaPlata").val());
        var porcentajePagableLMEPlata=transFloat($("#porcentajePagableLMEPlata").val());
        var valorPagablePlata=(leyPlataMineralesPagables-deduccionUnitariaPlata)*porcentajePagableLMEPlata/100.0*cotizacionPlataMineralesPagables;
        $("#valorPagablePlata").val(isNaN(valorPagablePlata)?"?":redondear2(valorPagablePlata));

        var valorPagableTotal=valorPagablePlomo+valorPagablePlata;
        $("#valorPagableTotal").val(isNaN(valorPagableTotal)?"?":redondear2(valorPagableTotal));
        
        var maquila=transFloat($("#maquila").val());
        var maquilaFinal=maquila;
        $("#maquilaFinal").val(isNaN(maquilaFinal)?"?":redondear2(maquilaFinal));

        var base=transFloat($("#base").val());
        var cotizacionPlomoActual=transFloat($("#cotizacionPlomoActual").val());
        var escaladorPlomoPlata=transFloat($("#escaladorPlomoPlata").val());
        var gastoRealizacionTotal=cotizacionPlomoActual>base?(cotizacionPlomoActual-base)*escaladorPlomoPlata:0;
        $("#gastoRealizacionTotal").val(isNaN(gastoRealizacionTotal)?"?":redondear2(gastoRealizacionTotal));

        var leyPlataOnzaTroy=transFloat($("#leyPlataOnzaTroy").val());
        var costoRefinacion=transFloat($("#costoRefinacion").val());
        var costoRefinacionTotal=leyPlataOnzaTroy*costoRefinacion;
        $("#costoRefinacionTotal").val(isNaN(costoRefinacionTotal)?"?":redondear2(costoRefinacionTotal));

        var porcentajeArsenico=transFloat($("#porcentajeArsenico").val());
        var arsenicoLibre=transFloat($("#arsenicoLibre").val());
        var costoUnitarioArsenico=transFloat($("#costoUnitarioArsenico").val());
        var porcentajeUnitarioArsenico=transFloat($("#porcentajeUnitarioArsenico").val());
        var penalizacionArsenico=porcentajeArsenico>arsenicoLibre?(porcentajeArsenico-arsenicoLibre)*costoUnitarioArsenico/porcentajeUnitarioArsenico:0;
        $("#penalizacionArsenico").val(isNaN(penalizacionArsenico)?"?":redondear2(penalizacionArsenico));

        var porcentajeAntimonio=transFloat($("#porcentajeAntimonio").val());
        var antimonioLibre=transFloat($("#antimonioLibre").val());
        var costoUnitarioAntimonio=transFloat($("#costoUnitarioAntimonio").val());
        var porcentajeUnitarioAntimonio=transFloat($("#porcentajeUnitarioAntimonio").val());
        var penalizacionAntimonio=porcentajeAntimonio>antimonioLibre?(porcentajeAntimonio-antimonioLibre)*costoUnitarioAntimonio/porcentajeUnitarioAntimonio:0;
        $("#penalizacionAntimonio").val(isNaN(penalizacionAntimonio)?"?":redondear2(penalizacionAntimonio));

        var porcentajeBismuto=transFloat($("#porcentajeBismuto").val());
        var bismutoLibre=transFloat($("#bismutoLibre").val());
        var costoUnitarioBismuto=transFloat($("#costoUnitarioBismuto").val());
        var porcentajeUnitarioBismuto=transFloat($("#porcentajeUnitarioBismuto").val());
        var penalizacionBismuto=porcentajeBismuto>bismutoLibre?(porcentajeBismuto-bismutoLibre)*costoUnitarioBismuto/porcentajeUnitarioBismuto:0;
        $("#penalizacionBismuto").val(isNaN(penalizacionBismuto)?"?":redondear2(penalizacionBismuto));

        var porcentajeEstano=transFloat($("#porcentajeEstano").val());
        var estanoLibre=transFloat($("#estanoLibre").val());
        var costoUnitarioEstano=transFloat($("#costoUnitarioEstano").val());
        var porcentajeUnitarioEstano=transFloat($("#porcentajeUnitarioEstano").val());
        var penalizacionEstano=porcentajeEstano>estanoLibre?(porcentajeEstano-estanoLibre)*costoUnitarioEstano/porcentajeUnitarioEstano:0;
        $("#penalizacionEstano").val(isNaN(penalizacionEstano)?"?":redondear2(penalizacionEstano));

        var porcentajeHierro=transFloat($("#porcentajeHierro").val());
        var hierroLibre=transFloat($("#hierroLibre").val());
        var costoUnitarioHierro=transFloat($("#costoUnitarioHierro").val());
        var porcentajeUnitarioHierro=transFloat($("#porcentajeUnitarioHierro").val());
        var penalizacionHierro=porcentajeHierro>hierroLibre?(porcentajeHierro-hierroLibre)*costoUnitarioHierro/porcentajeUnitarioHierro:0;
        $("#penalizacionHierro").val(isNaN(penalizacionHierro)?"?":redondear2(penalizacionHierro));

        var porcentajeSilice=transFloat($("#porcentajeSilice").val());
        var siliceLibre=transFloat($("#siliceLibre").val());
        var costoUnitarioSilice=transFloat($("#costoUnitarioSilice").val());
        var porcentajeUnitarioSilice=transFloat($("#porcentajeUnitarioSilice").val());
        var penalizacionSilice=porcentajeSilice>siliceLibre?(porcentajeSilice-siliceLibre)*costoUnitarioSilice/porcentajeUnitarioSilice:0;
        $("#penalizacionSilice").val(isNaN(penalizacionSilice)?"?":redondear2(penalizacionSilice));

        var porcentajeZinc=transFloat($("#porcentajeZinc").val());
        var zincLibre=transFloat($("#zincLibre").val());
        var costoUnitarioZinc=transFloat($("#costoUnitarioZinc").val());
        var porcentajeUnitarioZinc=transFloat($("#porcentajeUnitarioZinc").val());
        var penalizacionZinc=porcentajeZinc>zincLibre?(porcentajeZinc-zincLibre)*costoUnitarioZinc/porcentajeUnitarioZinc:0;
        $("#penalizacionZinc").val(isNaN(penalizacionZinc)?"?":redondear2(penalizacionZinc));

        var penalizacionTotal=penalizacionArsenico+penalizacionAntimonio+penalizacionBismuto+penalizacionEstano+penalizacionHierro+penalizacionSilice+penalizacionZinc;
        var precioUnitario=valorPagableTotal-(maquilaFinal+gastoRealizacionTotal+costoRefinacionTotal+penalizacionTotal);
        var valorNetoTotal=precioUnitario*toneladasMetricasSecasFinales;
        $("#precioUnitario").val(isNaN(precioUnitario)?"?":redondear2(precioUnitario));
        $("#valorNetoTotal").val(isNaN(valorNetoTotal)?"?":redondear2(valorNetoTotal));

        var costoFleteTonelada=transFloat($("#costoFleteTonelada").val());
        var costoFleteToneladaTotal=costoFleteTonelada*toneladasMetricasHumedas;
        $("#costoFleteToneladaTotal").val(isNaN(costoFleteToneladaTotal)?"?":redondear2(costoFleteToneladaTotal));

        var costoPortuarioTonelada=transFloat($("#costoPortuarioTonelada").val());
        var costoPortuarioToneladaTotal=costoPortuarioTonelada*toneladasMetricasHumedas;
        $("#costoPortuarioToneladaTotal").val(isNaN(costoPortuarioToneladaTotal)?"?":redondear2(costoPortuarioToneladaTotal));

        var costoOperacionTonelada=transFloat($("#costoOperacionTonelada").val());
        var costoOperacionToneladaTotal=costoOperacionTonelada*toneladasMetricasHumedas;
        $("#costoOperacionToneladaTotal").val(isNaN(costoOperacionToneladaTotal)?"?":redondear2(costoOperacionToneladaTotal));

        var valorNetoTotalFinal=valorNetoTotal-(costoFleteToneladaTotal+costoPortuarioToneladaTotal+costoOperacionToneladaTotal);
        $("#valorNetoTotalFinal").val(isNaN(valorNetoTotalFinal)?"?":redondear2(valorNetoTotalFinal));
    }

    function transFloat(numeroString){
        var numero = numeroString.replace(',','');
        return parseFloat(numero);
    }

    function redondear2( number ) {
        //var multiplier = Math.pow( 10, precision );
        var multiplier = Math.pow( 10, 2 );
        return Math.round( number * multiplier ) / multiplier;
    }
});
