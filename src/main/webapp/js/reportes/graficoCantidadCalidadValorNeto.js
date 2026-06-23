$(document).ready(function() {
    $("#graficarEstano").bind("click",graficarEstano);
    $("#graficarPlata").bind("click",graficarPlata);
    $("#graficarWolfran").bind("click",graficarWolfran);
    $("#graficarAntimonio").bind("click",graficarAntimonio);
    $("#graficarComplejo").bind("click",graficarComplejo);

    function graficarEstano(){
        var fechaInicial = "fechaInicial_year="+$("#fechaInicial_year").val()+
            "&fechaInicial_month="+$("#fechaInicial_month").val()+
            "&fechaInicial_day="+$("#fechaInicial_day").val();
        var fechaFinal = "fechaFinal_year="+$("#fechaFinal_year").val()+
            "&fechaFinal_month="+$("#fechaFinal_month").val()+
            "&fechaFinal_day="+$("#fechaFinal_day").val();
        $.getJSON("/demo-liquidaciones/reporteGraficoCantidadCalidadValorNeto/crearReporteEstano?"+fechaInicial+"&"+fechaFinal, function(data) {
            //inicio - grafico barras
            $('#container').highcharts({
                chart: {
                    zoomType: 'xy'
                },
                title: {
                    text: 'Grafico Cantidad/Calidad/Valor Neto'
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    categories: data[0],
                    labels: {
                        rotation: -90,
                        align: 'right',
                        style: {
                            fontSize: '12px',
                            fontFamily: 'Verdana, sans-serif'
                        }
                    }
                },

                yAxis: [{ // Primary yAxis
                    labels: {
                        format: 'Bs {value}',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                    title: {
                        text: 'Valor Neto',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                    opposite: true

                }, { // Secondary yAxis
                    gridLineWidth: 0,
                    title: {
                        text: 'Peso Bruto',
                        style: {
                            color: Highcharts.getOptions().colors[1]
                        }
                    },
                    labels: {
                        format: '{value} Kgs.',
                        style: {
                            color: Highcharts.getOptions().colors[1]
                        }
                    }

                }, { // Tertiary yAxis
                    gridLineWidth: 0,
                    title: {
                        text: 'Ley',
                        style: {
                            color: Highcharts.getOptions().colors[2]
                        }
                    },
                    labels: {
                        format: '{value} %',
                        style: {
                            color: Highcharts.getOptions().colors[2]
                        }
                    },
                    opposite: true
                }],
                tooltip: {
                    shared: true
                },//aqui puede entrar legend
                series: [{
                    name: 'Valor Neto',
                    type: 'column',
                    data: data[3],
                    tooltip: {
                        valuePrefix: ' Bs'
                    }
                },{
                    name: 'Peso Bruto',
                    type: 'column',
                    yAxis: 1,
                    data: data[1],
                    tooltip: {
                        valueSuffix: ' Kgs.'
                    }

                }, {
                    name: 'Ley',
                    type: 'line',
                    yAxis: 2,
                    data: data[2],
                    marker: {
                        enabled: false
                    },
                    tooltip: {
                        valueSuffix: ' %'
                    }

                } ]
            });
            //fin - grafico barras
        });
    }

    function graficarPlata(){
        var fechaInicial = "fechaInicial_year="+$("#fechaInicial_year").val()+
            "&fechaInicial_month="+$("#fechaInicial_month").val()+
            "&fechaInicial_day="+$("#fechaInicial_day").val();
        var fechaFinal = "fechaFinal_year="+$("#fechaFinal_year").val()+
            "&fechaFinal_month="+$("#fechaFinal_month").val()+
            "&fechaFinal_day="+$("#fechaFinal_day").val();
        $.getJSON("/demo-liquidaciones/reporteGraficoCantidadCalidadValorNeto/crearReportePlata?"+fechaInicial+"&"+fechaFinal, function(data) {
            //inicio - grafico barras
            $('#container').highcharts({
                chart: {
                    zoomType: 'xy'
                },
                title: {
                    text: 'Grafico Cantidad/Calidad/Valor Neto'
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    categories: data[0],
                    labels: {
                        rotation: -90,
                        align: 'right',
                        style: {
                            fontSize: '12px',
                            fontFamily: 'Verdana, sans-serif'
                        }
                    }
                },

                yAxis: [{ // Primary yAxis
                    labels: {
                        format: 'Bs {value}',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                    title: {
                        text: 'Valor Neto',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                    opposite: true

                }, { // Secondary yAxis
                    gridLineWidth: 0,
                    title: {
                        text: 'Peso Bruto',
                        style: {
                            color: Highcharts.getOptions().colors[1]
                        }
                    },
                    labels: {
                        format: '{value} Kgs.',
                        style: {
                            color: Highcharts.getOptions().colors[1]
                        }
                    }

                }, { // Tertiary yAxis
                    gridLineWidth: 0,
                    title: {
                        text: 'Ley',
                        style: {
                            color: Highcharts.getOptions().colors[2]
                        }
                    },
                    labels: {
                        format: '{value} DM',
                        style: {
                            color: Highcharts.getOptions().colors[2]
                        }
                    },
                    opposite: true
                }],
                tooltip: {
                    shared: true
                },//aqui puede entrar legend
                series: [{
                    name: 'Valor Neto',
                    type: 'column',
                    data: data[3],
                    tooltip: {
                        valuePrefix: ' Bs'
                    }
                },{
                    name: 'Peso Bruto',
                    type: 'column',
                    yAxis: 1,
                    data: data[1],
                    tooltip: {
                        valueSuffix: ' Kgs.'
                    }

                }, {
                    name: 'Ley',
                    type: 'line',
                    yAxis: 2,
                    data: data[2],
                    marker: {
                        enabled: false
                    },
                    tooltip: {
                        valueSuffix: ' DM'
                    }

                } ]
            });
            //fin - grafico barras
        });
    }

    function graficarWolfran(){
        var fechaInicial = "fechaInicial_year="+$("#fechaInicial_year").val()+
            "&fechaInicial_month="+$("#fechaInicial_month").val()+
            "&fechaInicial_day="+$("#fechaInicial_day").val();
        var fechaFinal = "fechaFinal_year="+$("#fechaFinal_year").val()+
            "&fechaFinal_month="+$("#fechaFinal_month").val()+
            "&fechaFinal_day="+$("#fechaFinal_day").val();
        $.getJSON("/demo-liquidaciones/reporteGraficoCantidadCalidadValorNeto/crearReporteWolfran?"+fechaInicial+"&"+fechaFinal, function(data) {
            //inicio - grafico barras
            $('#container').highcharts({
                chart: {
                    zoomType: 'xy'
                },
                title: {
                    text: 'Grafico Cantidad/Calidad/Valor Neto'
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    categories: data[0],
                    labels: {
                        rotation: -90,
                        align: 'right',
                        style: {
                            fontSize: '12px',
                            fontFamily: 'Verdana, sans-serif'
                        }
                    }
                },

                yAxis: [{ // Primary yAxis
                    labels: {
                        format: 'Bs {value}',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                    title: {
                        text: 'Valor Neto',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                    opposite: true

                }, { // Secondary yAxis
                    gridLineWidth: 0,
                    title: {
                        text: 'Peso Bruto',
                        style: {
                            color: Highcharts.getOptions().colors[1]
                        }
                    },
                    labels: {
                        format: '{value} Kgs.',
                        style: {
                            color: Highcharts.getOptions().colors[1]
                        }
                    }

                }, { // Tertiary yAxis
                    gridLineWidth: 0,
                    title: {
                        text: 'Ley',
                        style: {
                            color: Highcharts.getOptions().colors[2]
                        }
                    },
                    labels: {
                        format: '{value} %',
                        style: {
                            color: Highcharts.getOptions().colors[2]
                        }
                    },
                    opposite: true
                }],
                tooltip: {
                    shared: true
                },//aqui puede entrar legend
                series: [{
                    name: 'Valor Neto',
                    type: 'column',
                    data: data[3],
                    tooltip: {
                        valuePrefix: ' Bs'
                    }
                },{
                    name: 'Peso Bruto',
                    type: 'column',
                    yAxis: 1,
                    data: data[1],
                    tooltip: {
                        valueSuffix: ' Kgs.'
                    }

                }, {
                    name: 'Ley',
                    type: 'line',
                    yAxis: 2,
                    data: data[2],
                    marker: {
                        enabled: false
                    },
                    tooltip: {
                        valueSuffix: ' %'
                    }

                } ]
            });
            //fin - grafico barras
        });
    }

    function graficarAntimonio(){
        var fechaInicial = "fechaInicial_year="+$("#fechaInicial_year").val()+
            "&fechaInicial_month="+$("#fechaInicial_month").val()+
            "&fechaInicial_day="+$("#fechaInicial_day").val();
        var fechaFinal = "fechaFinal_year="+$("#fechaFinal_year").val()+
            "&fechaFinal_month="+$("#fechaFinal_month").val()+
            "&fechaFinal_day="+$("#fechaFinal_day").val();
        $.getJSON("/demo-liquidaciones/reporteGraficoCantidadCalidadValorNeto/crearReporteAntimonio?"+fechaInicial+"&"+fechaFinal, function(data) {
            //inicio - grafico barras
            $('#container').highcharts({
                chart: {
                    zoomType: 'xy'
                },
                title: {
                    text: 'Grafico Cantidad/Calidad/Valor Neto'
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    categories: data[0],
                    labels: {
                        rotation: -90,
                        align: 'right',
                        style: {
                            fontSize: '12px',
                            fontFamily: 'Verdana, sans-serif'
                        }
                    }
                },

                yAxis: [{ // Primary yAxis
                    labels: {
                        format: 'Bs {value}',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                    title: {
                        text: 'Valor Neto',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                    opposite: true

                }, { // Secondary yAxis
                    gridLineWidth: 0,
                    title: {
                        text: 'Peso Bruto',
                        style: {
                            color: Highcharts.getOptions().colors[1]
                        }
                    },
                    labels: {
                        format: '{value} Kgs.',
                        style: {
                            color: Highcharts.getOptions().colors[1]
                        }
                    }

                }, { // Tertiary yAxis
                    gridLineWidth: 0,
                    title: {
                        text: 'Ley',
                        style: {
                            color: Highcharts.getOptions().colors[2]
                        }
                    },
                    labels: {
                        format: '{value} %',
                        style: {
                            color: Highcharts.getOptions().colors[2]
                        }
                    },
                    opposite: true
                }],
                tooltip: {
                    shared: true
                },//aqui puede entrar legend
                series: [{
                    name: 'Valor Neto',
                    type: 'column',
                    data: data[3],
                    tooltip: {
                        valuePrefix: ' Bs'
                    }
                },{
                    name: 'Peso Bruto',
                    type: 'column',
                    yAxis: 1,
                    data: data[1],
                    tooltip: {
                        valueSuffix: ' Kgs.'
                    }

                }, {
                    name: 'Ley',
                    type: 'line',
                    yAxis: 2,
                    data: data[2],
                    marker: {
                        enabled: false
                    },
                    tooltip: {
                        valueSuffix: ' %'
                    }

                } ]
            });
            //fin - grafico barras
        });
    }

    function graficarComplejo(){
        var fechaInicial = "fechaInicial_year="+$("#fechaInicial_year").val()+
            "&fechaInicial_month="+$("#fechaInicial_month").val()+
            "&fechaInicial_day="+$("#fechaInicial_day").val();
        var fechaFinal = "fechaFinal_year="+$("#fechaFinal_year").val()+
            "&fechaFinal_month="+$("#fechaFinal_month").val()+
            "&fechaFinal_day="+$("#fechaFinal_day").val();
        $.getJSON("/demo-liquidaciones/reporteGraficoCantidadCalidadValorNeto/crearReporteComplejo?"+fechaInicial+"&"+fechaFinal, function(data) {
            //inicio - grafico barras
            $('#container').highcharts({
                chart: {
                    zoomType: 'xy'
                },
                title: {
                    text: 'Grafico Cantidad/Calidad/Valor Neto'
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    categories: data[0],
                    labels: {
                        rotation: -90,
                        align: 'right',
                        style: {
                            fontSize: '12px',
                            fontFamily: 'Verdana, sans-serif'
                        }
                    }
                },

                yAxis: [{ // Primary yAxis
                    labels: {
                        format: 'Bs {value}',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                    title: {
                        text: 'Valor Neto',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                    opposite: true

                }, { // Secondary yAxis
                    gridLineWidth: 0,
                    title: {
                        text: 'Peso Bruto',
                        style: {
                            color: Highcharts.getOptions().colors[1]
                        }
                    },
                    labels: {
                        format: '{value} Kgs.',
                        style: {
                            color: Highcharts.getOptions().colors[1]
                        }
                    }

                }, { // Tertiary yAxis
                    gridLineWidth: 0,
                    title: {
                        text: 'Ley Zn',
                        style: {
                            color: Highcharts.getOptions().colors[2]
                        }
                    },
                    labels: {
                        format: '{value} %',
                        style: {
                            color: Highcharts.getOptions().colors[2]
                        }
                    },
                    opposite: true
                }, { // Tertiary yAxis
                    gridLineWidth: 0,
                    title: {
                        text: 'Ley Pb',
                        style: {
                            color: Highcharts.getOptions().colors[3]
                        }
                    },
                    labels: {
                        format: '{value} %',
                        style: {
                            color: Highcharts.getOptions().colors[3]
                        }
                    },
                    opposite: true
                }, { // Tertiary yAxis
                    gridLineWidth: 0,
                    title: {
                        text: 'Ley Ag',
                        style: {
                            color: Highcharts.getOptions().colors[4]
                        }
                    },
                    labels: {
                        format: '{value} DM',
                        style: {
                            color: Highcharts.getOptions().colors[4]
                        }
                    },
                    opposite: true
                }],
                tooltip: {
                    shared: true
                },//aqui puede entrar legend
                series: [{
                    name: 'Valor Neto',
                    type: 'column',
                    data: data[5],
                    tooltip: {
                        valuePrefix: ' Bs'
                    }
                },{
                    name: 'Peso Bruto',
                    type: 'column',
                    yAxis: 1,
                    data: data[1],
                    tooltip: {
                        valueSuffix: ' Kgs.'
                    }

                }, {
                    name: 'Ley Zn',
                    type: 'line',
                    yAxis: 2,
                    data: data[2],
                    marker: {
                        enabled: false
                    },
                    tooltip: {
                        valueSuffix: ' %'
                    }

                }, {
                    name: 'Ley Pb',
                    type: 'line',
                    yAxis: 2,
                    data: data[3],
                    marker: {
                        enabled: false
                    },
                    tooltip: {
                        valueSuffix: ' %'
                    }

                }, {
                    name: 'Ley Ag',
                    type: 'line',
                    yAxis: 2,
                    data: data[4],
                    marker: {
                        enabled: false
                    },
                    tooltip: {
                        valueSuffix: ' DM'
                    }

                } ]
            });
            //fin - grafico barras
        });
    }

    $("#elemento" ).change(function() {
        var elem=$("#elemento").val();

        if(elem=="Estano"){
            $( "#_resultadosEstano" ).show();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
        }
        if(elem=="Plata"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).show();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
        }
        if(elem=="Wolfran"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).show();
            $( "#_resultadosComplejo" ).hide();
        }
        if(elem=="Antimonio"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).show();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).hide();
        }
        if(elem=="Complejo"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).show();
        }

        if(elem=="Plomo Plata"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).show();
        }

        if(elem=="Zinc Plata"){
            $( "#_resultadosEstano" ).hide();
            $( "#_resultadosPlata" ).hide();
            $( "#_resultadosAntimonio" ).hide();
            $( "#_resultadosWolfran" ).hide();
            $( "#_resultadosComplejo" ).show();
        }
    });
});
