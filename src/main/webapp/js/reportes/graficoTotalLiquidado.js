$(document).ready(function() {
    $("#elemento,#empresa" ).change(function() {
        graficarTotalLiquidado();
    });

    $("#fechas").click(function(){
        if ($("#fechas").prop("checked")) {
            $( "#_empresa" ).hide();

            graficarTotalLiquidado();
        }
    });

    $("#fechasEmpresa").click(function(){
        if ($("#fechasEmpresa").prop("checked")) {
            $( "#_empresa" ).show();

            graficarTotalLiquidado();
        }
    });

    function graficarTotalLiquidado(){
        var elem=$("#elemento").val();
        var empresaId=$("#empresa").val();
        var empresaIndex=$("#empresa")[0].selectedIndex;

        if(elem=="Estano" && $("#fechas").prop("checked")){
            $.getJSON('/demo-liquidaciones/reporteGraficoTotalLiquidado/listaTotalLiquidadoEstano', function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Total LIQUIDADO Estaño'
                    },
                    series : [{
                        name : 'KNS',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }
        if(elem=="Estano" && $("#fechasEmpresa").prop("checked") && empresaIndex>0){
            $.getJSON('/demo-liquidaciones/reporteGraficoTotalLiquidado/listaTotalLiquidadoEstanoEmpresa?empresaId='+empresaId, function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Total LIQUIDADO Estaño'
                    },
                    series : [{
                        name : 'KNS',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Plata" && $("#fechas").prop("checked")){
            $.getJSON('/demo-liquidaciones/reporteGraficoTotalLiquidado/listaTotalLiquidadoPlata', function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Total LIQUIDADO Plata'
                    },
                    series : [{
                        name : 'KNS',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Plata" && $("#fechasEmpresa").prop("checked") && empresaIndex>0){
            $.getJSON('/demo-liquidaciones/reporteGraficoTotalLiquidado/listaTotalLiquidadoPlataEmpresa?empresaId='+empresaId, function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Total LIQUIDADO Plata'
                    },
                    series : [{
                        name : 'KNS',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Wolfran" && $("#fechas").prop("checked")){
            $.getJSON('/demo-liquidaciones/reporteGraficoTotalLiquidado/listaTotalLiquidadoWolfran', function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Total LIQUIDADO Wolfran'
                    },
                    series : [{
                        name : 'KNS',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Wolfran" && $("#fechasEmpresa").prop("checked") && empresaIndex>0){
            $.getJSON('/demo-liquidaciones/reporteGraficoTotalLiquidado/listaTotalLiquidadoWolfranEmpresa?empresaId='+empresaId, function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Total LIQUIDADO Wolfran'
                    },
                    series : [{
                        name : 'KNS',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Antimonio" && $("#fechas").prop("checked")){
            $.getJSON('/demo-liquidaciones/reporteGraficoTotalLiquidado/listaTotalLiquidadoAntimonio', function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Total LIQUIDADO Antimonio'
                    },
                    series : [{
                        name : 'KNS',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Antimonio" && $("#fechasEmpresa").prop("checked") && empresaIndex>0){
            $.getJSON('/demo-liquidaciones/reporteGraficoTotalLiquidado/listaTotalLiquidadoAntimonioEmpresa?empresaId='+empresaId, function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Total LIQUIDADO Antimonio'
                    },
                    series : [{
                        name : 'KNS',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Complejo" && $("#fechas").prop("checked")){
            $.getJSON('/demo-liquidaciones/reporteGraficoTotalLiquidado/listaTotalLiquidadoComplejo', function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Total LIQUIDADO Complejo'
                    },
                    series : [{
                        name : 'KNS',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Complejo" && $("#fechasEmpresa").prop("checked") && empresaIndex>0){
            $.getJSON('/demo-liquidaciones/reporteGraficoTotalLiquidado/listaTotalLiquidadoComplejoEmpresa?empresaId='+empresaId, function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Total LIQUIDADO Complejo'
                    },
                    series : [{
                        name : 'KNS',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }
    }
});


