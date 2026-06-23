$(document).ready(function() {
    $("#elemento" ).change(function() {
        graficarCotizacionDiaria();
    });

    function graficarCotizacionDiaria(){
        var elem=$("#elemento").val();

        if(elem=="Estano"){
            $.getJSON('/demo-liquidaciones/reporteGraficoCotizacionDiaria/listaCotizacionDiariaEstano', function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Cotizacion Diaria Estaño'
                    },
                    series : [{
                        name : 'COT. DIARIA',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Plata"){
            $.getJSON('/demo-liquidaciones/reporteGraficoCotizacionDiaria/listaCotizacionDiariaPlata', function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Cotizacion Diaria Plata'
                    },
                    series : [{
                        name : 'COT. DIARIA',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Plomo"){
            $.getJSON('/demo-liquidaciones/reporteGraficoCotizacionDiaria/listaCotizacionDiariaPlomo', function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Cotizacion Diaria Plomo'
                    },
                    series : [{
                        name : 'COT. DIARIA',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Wolfran"){
            $.getJSON('/demo-liquidaciones/reporteGraficoCotizacionDiaria/listaCotizacionDiariaWolfran', function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Cotizacion Diaria Wolfran'
                    },
                    series : [{
                        name : 'COT. DIARIA',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Antimonio"){
            $.getJSON('/demo-liquidaciones/reporteGraficoCotizacionDiaria/listaCotizacionDiariaAntimonio', function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Cotizacion Diaria Antimonio'
                    },
                    series : [{
                        name : 'COT. DIARIA',
                        data : data,
                        tooltip: {
                            valueDecimals: 2
                        }
                    }]
                });
            });
        }

        if(elem=="Zinc"){
            $.getJSON('/demo-liquidaciones/reporteGraficoCotizacionDiaria/listaCotizacionDiariaZinc', function(data) {
                // Create the chart
                $('#container').highcharts('StockChart', {
                    rangeSelector : {
                        selected : 1,
                        inputEnabled: $('#container').width() > 480
                    },
                    title : {
                        text : 'Grafico de Cotizacion Diaria Zinc'
                    },
                    series : [{
                        name : 'COT. DIARIA',
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


