$(document).ready(function() {
    $('#empresa').bind("change", graficarCantidad)

    graficarCantidad()

    function graficarCantidad(){
        $.ajax({
            url:"/demo-liquidaciones/reporteGraficoAcumuladoEmpresaCliente/datosGrafico",
            dataType: 'json',
            data: {
                empresaId: $('#empresa').val(),
            },
            success: function(data) {
                $("#empresas").val(data.empresas)
                $("#valoresNetos").val(data.valoresNetos)
                $("#pesosNetos").val(data.pesosNetos)
                $("#leyesZinc").val(data.leyesZinc)
                $("#leyesPlomo").val(data.leyesPlomo)
                $("#leyesPlata").val(data.leyesPlata)

                let vn = data.valoresNetos.toString().split(',').map(Number)
                console.log(vn)

                Highcharts.chart('container', {
                    chart: {
                        type: 'bar'
                    },
                    title: {
                        text: 'Gráfico Acumulado por Empresa y Cliente'
                    },
                    subtitle: {
                        text: 'según Valor Neto y Peso Neto'
                    },
                    xAxis: {
                        // categories: ['Africa', 'America', 'Asia', 'Europe', 'Oceania'],
                        categories: data.empresas.split(','),
                        // categories: ['10 DE FEBRERO R.L.','NUEVA SAN JOSE R.L.','particular','MARAGUA','26 DE FEBRERO R.L.','MINERA FLOR - LURIBAY S.R.L.','MARIA LUISA  R.L.','COLQUECHACA R.L.','NEVADO R.L.','LACOPE SRL'],
                        title: {
                            text: null
                        },
                        subtitle: {
                            text: "asd"
                        }
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            // text: 'Population (millions)',
                            text: null,
                            align: 'high'
                        },
                        labels: {
                            overflow: 'justify'
                        }
                    },
                    tooltip: {
                        // valueSuffix: ' millions'
                    },
                    plotOptions: {
                        bar: {
                            dataLabels: {
                                enabled: true
                            }
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'top',
                        x: -40,
                        y: 80,
                        floating: true,
                        borderWidth: 1,
                        backgroundColor:
                            Highcharts.defaultOptions.legend.backgroundColor || '#FFFFFF',
                        shadow: true
                    },
                    credits: {
                        enabled: false
                    },
                    series: [{
                        name: 'Valor neto',
                        // data: [107, 31, 635, 203, 2]
                        data: data.valoresNetos.split(',').map(Number)
                        // data: [5584247.01,1309955.98,789818.16,549120.82,157033.91,45782.88,7292.32,5346.48,3954.10,1177.38]
                    }, {
                        name: 'Peso neto',
                        // data: [133, 156, 947, 408, 6]
                        data: data.pesosNetos.split(',').map(Number)
                        // data: [575156.33,169061.69,56944.67,79798.02,66204.78,3097.14,1199.54,459.03,480.00,481.67]
                    }
                        // , {
                        //     name: 'Ley Zn promedio',
                        //     // data: [814, 841, 3714, 727, 31]
                        //     data: data.leyesZinc.split(',').map(Number)
                        //     // data: [0.000000,0.028902,0.000000,0.000000,0.000000,0.000000,0.000000,0.000000,10.000000,0.000000]
                        // }, {
                        //     name: 'Ley Pb promedio',
                        //     // data: [814, 841, 3714, 727, 31]
                        //     data: data.leyesPlomo.split(',').map(Number)
                        //     // data: [0.915976,7.920954,10.307194,0.183575,8.511081,47.500000,25.000000,42.500000,20.000000,30.000000]
                        // },{
                        //     name: 'Ley Ag promedio',
                        //     // data: [1216, 1001, 4436, 738, 40]
                        //     data: data.leyesPlata.split(',').map(Number)
                        //     // data: [50.415368,36.478786,99.734604,39.044106,18.699189,47.000000,20.000000,18.000000,30.000000,10.000000]
                        // }
                    ]
                });
            },
            error: function(request, status, error) {
            }
        });
    }
});


