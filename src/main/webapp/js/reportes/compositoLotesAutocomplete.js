$(document).ready(function() {
    $(".chosen-select").chosen({
        width: '350px'
    });

    usuarioActual();
    //definir tabla de lotes
    $("#lotesDisponibles").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["recepcionId","FECHA REC.","LOTE","EMPRESA","PROVEEDOR","KILOS BRUTOS","H2O %","KILOS SECOS","% Zn","% Pb","DM Ag","K. F. Zn","K. F. Pb","K. F. Ag","PRECIO TN $us","VALOR BRUTO Bs","VALOR NETO Bs","COSTO TRANS. TNMH","TOTAL TRANS.","COSTO MANIP.","BONOS","LIQUIDO PAGABLE","DISPONIBLE"],
        colModel:[
            {name:'recepcionId',index:'recepcionId', width:5, hidden: true},
            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:55},
            {name:'lote',index:'lote', width:65},
            {name:'nombreEmpresa',index:'nombreEmpresa', width:150},
            {name:'proveedor',index:'proveedor', width:150, hidden: true},
            {name:'pesoBruto',index:'pesoBruto', width:50, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'porcentajeHumedad',index:'porcentajeHumedad', width:35, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'kilosNetosSecos',index:'kilosNetosSecos', width:50, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'porcentajeZincFinal',index:'porcentajeZincFinal', width:35, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'porcentajePlomoFinal',index:'porcentajePlomoFinal', width:35, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'porcentajePlataFinal',index:'porcentajePlataFinal', width:35, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'kilosFinosZinc',index:'kilosFinosZinc', width:50, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'kilosFinosPlomo',index:'kilosFinosPlomo', width:50, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'kilosFinosPlata',index:'kilosFinosPlata', width:50, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'precioTonelada',index:'precioTonelada', width:50, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'valorOficialBruto',index:'valorOficialBruto', width:55, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'valorNetoMineralEnBolivianos',index:'valorNetoMineralEnBolivianos', width:55, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'costoUnitarioTransporte',index:'costoUnitarioTransporte', hidden: true, width:45, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'costoDeTransporte',index:'costoDeTransporte', width:45, hidden: true, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'costoManipuleo',index:'costoManipuleo', width:50, hidden: true, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'bonos',index:'bonos', width:50, hidden: true, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'totalLiquidoPagable',index:'totalLiquidoPagable', width:60, hidden: true, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'disponible',index:'disponible', width:50}
            ],
        multiselect: true,
        footerrow: true,
        rownumbers: true,
        caption: "LOTES EN INVENTARIO"
    });
    //crear tabla de lotes
    crearTabla();

    $("#lotesCompositoTabla").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["recepcionId","FECHA REC.","LOTE","EMPRESA","PROVEEDOR","KILOS BRUTOS","H2O %","KILOS SECOS","% Zn","% Pb","DM Ag","K. F. Zn","K. F. Pb","K. F. Ag","PRECIO TN $us","VALOR BRUTO Bs","VALOR NETO Bs","COSTO TRANS. TNMH","TOTAL TRANS.","COSTO MANIP.","BONOS","LIQUIDO PAGABLE","DISPONIBLE"],
        colModel:[
            {name:'recepcionId',index:'recepcionId', width:5, hidden: true},
            {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:55},
            {name:'lote',index:'lote', width:65},
            {name:'nombreEmpresa',index:'nombreEmpresa', width:150},
            {name:'proveedor',index:'proveedor', width:150, hidden: true},
            {name:'pesoBruto',index:'pesoBruto', width:50, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'porcentajeHumedad',index:'porcentajeHumedad', width:35, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'kilosNetosSecos',index:'kilosNetosSecos', width:50, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'porcentajeZincFinal',index:'porcentajeZincFinal', width:35, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'porcentajePlomoFinal',index:'porcentajePlomoFinal', width:35, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'porcentajePlataFinal',index:'porcentajePlataFinal', width:35, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'kilosFinosZinc',index:'kilosFinosZinc', width:50, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'kilosFinosPlomo',index:'kilosFinosPlomo', width:50, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'kilosFinosPlata',index:'kilosFinosPlata', width:50, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'precioTonelada',index:'precioTonelada', width:50, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'valorOficialBruto',index:'valorOficialBruto', width:55, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'valorNetoMineralEnBolivianos',index:'valorNetoMineralEnBolivianos', width:55, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'costoUnitarioTransporte',index:'costoUnitarioTransporte', hidden: true, width:45, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'costoDeTransporte',index:'costoDeTransporte', width:45, hidden: true, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'costoManipuleo',index:'costoManipuleo', width:50, hidden: true, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'bonos',index:'bonos', width:50, hidden: true, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'totalLiquidoPagable',index:'totalLiquidoPagable', width:60, hidden: true, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'disponible',index:'disponible', width:50, hidden: true}
        ],
        multiselect: true,
        footerrow: true,
        rownumbers: true,
        caption: "LOTES PARA COMPOSITO"
    });

    $("#participacionTabla").jqGrid({
        datatype: "local",
        height: 200,
        // colNames: ["EMPRESA","PARTICIPACION %"],
        // colModel:[
        //     {name:'nombreEmpresa',index:'nombreEmpresa', width:300},
        //     {name:'porcentajeParticipacion',index:'porcentajeParticipacion', width:100, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}}
        // ],
        colNames: ["DEPARTAMENTO","MUNICIPIO","KILOS NETOS","APORTE %"],
        colModel:[
            {name:'departamento',index:'departamento', width:150},
            {name:'municipio',index:'municipio', width:150},
            {name:'kilosNetosSecos',index:'kilosNetosSecos', width:100, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}},
            {name:'porcentajeParticipacion',index:'porcentajeParticipacion', width:100, align: "right",formatter:'number', formatoptions:{decimalSeparator: ".", thousandsSeparator: " ", decimalPlaces: 2, defaultValue: '0.00'}}
        ],
        multiselect: true,
        footerrow: true,
        rownumbers: true,
        caption: "PARTICIPACION"
    });

    crearTablaComposito()

    $("#agregar").bind("click",agregarLote);
    $("#asignar").bind("click",asignarLotesAComposito);
    $("#retornar").bind("click",retornarALotesDisponibles);

    desplegarDestino();
    $('#destino').change(function() {
        desplegarDestino();
    });

    $('#estadoDelComposito').change(function() {
        estadoDelComposito();
    });
    $('#estadoDeAprobacion').change(function() {
        estadoDeAprobacion();
    });

    function simplificarJSON(expresionJSON){
        console.log(`expresionJSON: ${expresionJSON}`)
        let expresion = $.parseJSON(expresionJSON);
        let lista = [];
        let elemento = {};
        for(var i=0;i<expresion.length;i++){
            elemento = {};
            elemento.recepcionId=expresion[i].recepcionId;
            lista.push(elemento);
        }
        return JSON.stringify(lista);
    }

    function agregarLote(){
        // console.log(simplificarJSON($('#lotesComposito').val()));
        $.ajax({
            //url: "/demo-liquidaciones/reporteCompositoDeLotes/usuarioActual",
            url: "/demo-liquidaciones/reporteCompositoDeLotes/lotesParaCompositoJSON",
            dataType: 'json',
            data: {
                empresaId: $('#empresa').val(),
                ordenarElemento: $('#ordenarElemento').val(),
                // lotesComposito: $('#lotesComposito').val()
                lotesComposito: simplificarJSON($('#lotesComposito').val()==="" ? "[]" : $('#lotesComposito').val())
            },
            success: function(data) {
                if(data.numberFormatException==0&&data.nullPointerException==0){
                    $('#lotes').val(data.lotes);
                    crearTabla();
                }
                if(data.numberFormatException==1)
                    $.notify("Se han ingresado valores incorrectos!",{autoHide: true,clickToHide: true,className: "error"});
                if(data.nullPointerException==1)
                    $.notify("Se ha producido un error interno!",{autoHide: true,clickToHide: true,className: "error"});
            },
            error: function(request, status, error) {

            }
        });
    }

    function crearTabla(){
        var mydata = $("#lotes").val();
        var totalKilosBrutos=0;
        var totalKilosNetosSecos=0;
        var leyPromedioZinc=0;
        var leyPromedioPlomo=0;
        var leyPromedioPlata=0;
        var totalKilosFinosZinc=0;
        var totalKilosFinosPlomo=0;
        var totalKilosFinosPlata=0;
        var totalValorBruto=0;
        var totalValorNeto=0;
        var totalValorDeCompra=0;
        let totalCostoTransporte=0
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);
        $("#lotesDisponibles").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            if(mydata[i].disponible==="SI"){
                totalKilosBrutos+=mydata[i].pesoBruto;
                totalKilosNetosSecos+=mydata[i].kilosNetosSecos;
                totalKilosFinosZinc+=mydata[i].kilosFinosZinc;
                totalKilosFinosPlomo+=mydata[i].kilosFinosPlomo;
                totalKilosFinosPlata+=mydata[i].kilosFinosPlata;
                totalValorBruto+=mydata[i].valorOficialBruto;
                totalValorNeto+=mydata[i].valorNetoMineralEnBolivianos;
                totalValorDeCompra+=mydata[i].totalLiquidoPagable;
                totalCostoTransporte+=mydata[i].costoDeTransporte;
            }
            $("#lotesDisponibles").jqGrid('addRowData',i+1,mydata[i]);
        }

        leyPromedioZinc=(isNaN(totalKilosFinosZinc/totalKilosNetosSecos*100)) ?0:toFixed(totalKilosFinosZinc/totalKilosNetosSecos*100,2)
        leyPromedioPlomo=(isNaN(totalKilosFinosPlomo/totalKilosNetosSecos*100)) ?0:toFixed(totalKilosFinosPlomo/totalKilosNetosSecos*100,2)
        leyPromedioPlata=(isNaN(10000*totalKilosFinosPlata/totalKilosNetosSecos)) ?0:toFixed(10000*totalKilosFinosPlata/totalKilosNetosSecos,2)
        //totales de tabla
        $("#lotesDisponibles").jqGrid('footerData','set',{nombreEmpresa: 'TOTALES/PROMEDIOS'});
        $("#lotesDisponibles").jqGrid('footerData','set',{pesoBruto: (isNaN(totalKilosBrutos)) ?"?":toFixed(totalKilosBrutos,2).toString()});
        $("#lotesDisponibles").jqGrid('footerData','set',{kilosNetosSecos: (isNaN(totalKilosNetosSecos)) ?"?":toFixed(totalKilosNetosSecos,2).toString()});
        $("#lotesDisponibles").jqGrid('footerData','set',{porcentajeZincFinal: leyPromedioZinc});
        $("#lotesDisponibles").jqGrid('footerData','set',{porcentajePlomoFinal: leyPromedioPlomo});
        $("#lotesDisponibles").jqGrid('footerData','set',{porcentajePlataFinal: leyPromedioPlata});
        $("#lotesDisponibles").jqGrid('footerData','set',{kilosFinosPlata: toFixed(totalKilosFinosPlata,2)});
        $("#lotesDisponibles").jqGrid('footerData','set',{kilosFinosPlomo: toFixed(totalKilosFinosPlomo,2)});
        $("#lotesDisponibles").jqGrid('footerData','set',{kilosFinosZinc: toFixed(totalKilosFinosZinc,2)});
        $("#lotesDisponibles").jqGrid('footerData','set',{valorOficialBruto: toFixed(totalValorBruto,2)});
        $("#lotesDisponibles").jqGrid('footerData','set',{valorNetoMineralEnBolivianos: toFixed(totalValorNeto,2)});
        $("#lotesDisponibles").jqGrid('footerData','set',{totalLiquidoPagable: toFixed(totalValorDeCompra,2)});
        $("#lotesDisponibles").jqGrid('footerData','set',{costoDeTransporte: toFixed(totalCostoTransporte,2)});
    }

    function crearTablaComposito(){
        let mydata = $("#lotesComposito").val();
        let totalKilosBrutos=0;
        let totalKilosNetosSecos=0;
        let leyPromedioZinc=0;
        let leyPromedioPlomo=0;
        let leyPromedioPlata=0;
        let totalKilosFinosZinc=0;
        let totalKilosFinosPlomo=0;
        let totalKilosFinosPlata=0;
        let totalValorBruto=0;
        let totalValorNeto=0;
        let totalValorDeCompra=0;
        let totalCostoTransporte=0
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);
        $("#lotesCompositoTabla").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            totalKilosBrutos+=mydata[i].pesoBruto;
            totalKilosNetosSecos+=mydata[i].kilosNetosSecos;
            totalKilosFinosZinc+=mydata[i].kilosFinosZinc;
            totalKilosFinosPlomo+=mydata[i].kilosFinosPlomo;
            totalKilosFinosPlata+=mydata[i].kilosFinosPlata;
            totalValorBruto+=mydata[i].valorOficialBruto;
            totalValorNeto+=mydata[i].valorNetoMineralEnBolivianos;
            totalValorDeCompra+=mydata[i].totalLiquidoPagable;
            totalCostoTransporte+=mydata[i].costoDeTransporte;
            $("#lotesCompositoTabla").jqGrid('addRowData',i+1,mydata[i]);
        }

        leyPromedioZinc=(isNaN(totalKilosFinosZinc/totalKilosNetosSecos*100)) ?0:toFixed(totalKilosFinosZinc/totalKilosNetosSecos*100,2)
        leyPromedioPlomo=(isNaN(totalKilosFinosPlomo/totalKilosNetosSecos*100)) ?0:toFixed(totalKilosFinosPlomo/totalKilosNetosSecos*100,2)
        leyPromedioPlata=(isNaN(10000*totalKilosFinosPlata/totalKilosNetosSecos)) ?0:toFixed(10000*totalKilosFinosPlata/totalKilosNetosSecos,2)
        //totales de tabla
        $("#lotesCompositoTabla").jqGrid('footerData','set',{nombreEmpresa: 'TOTALES/PROMEDIOS'});
        $("#lotesCompositoTabla").jqGrid('footerData','set',{pesoBruto: (isNaN(totalKilosBrutos)) ?"?":toFixed(totalKilosBrutos,2).toString()});
        $("#lotesCompositoTabla").jqGrid('footerData','set',{kilosNetosSecos: (isNaN(totalKilosNetosSecos)) ?"?":toFixed(totalKilosNetosSecos,2).toString()});
        $("#lotesCompositoTabla").jqGrid('footerData','set',{porcentajeZincFinal: leyPromedioZinc});
        $("#lotesCompositoTabla").jqGrid('footerData','set',{porcentajePlomoFinal: leyPromedioPlomo});
        $("#lotesCompositoTabla").jqGrid('footerData','set',{porcentajePlataFinal: leyPromedioPlata});
        $("#lotesCompositoTabla").jqGrid('footerData','set',{kilosFinosPlata: toFixed(totalKilosFinosPlata,2)});
        $("#lotesCompositoTabla").jqGrid('footerData','set',{kilosFinosPlomo: toFixed(totalKilosFinosPlomo,2)});
        $("#lotesCompositoTabla").jqGrid('footerData','set',{kilosFinosZinc: toFixed(totalKilosFinosZinc,2)});
        $("#lotesCompositoTabla").jqGrid('footerData','set',{valorOficialBruto: toFixed(totalValorBruto,2)});
        $("#lotesCompositoTabla").jqGrid('footerData','set',{valorNetoMineralEnBolivianos: toFixed(totalValorNeto,2)});
        $("#lotesCompositoTabla").jqGrid('footerData','set',{totalLiquidoPagable: toFixed(totalValorDeCompra,2)});
        $("#lotesCompositoTabla").jqGrid('footerData','set',{costoDeTransporte: toFixed(totalCostoTransporte,2)});

        $("#totalKilosBrutos").val((isNaN(totalKilosBrutos)) ?"?":toFixed(totalKilosBrutos,2).toString());
        $("#totalKilosNetosSecos").val((isNaN(totalKilosNetosSecos)) ?"?":toFixed(totalKilosNetosSecos,2).toString());
        $("#leyPromedioZinc").val(leyPromedioZinc);
        $("#leyPromedioPlomo").val(leyPromedioPlomo);
        $("#leyPromedioPlata").val(leyPromedioPlata);
        $("#totalKilosFinosZinc").val(toFixed(totalKilosFinosZinc,2));
        $("#totalKilosFinosPlomo").val(toFixed(totalKilosFinosPlomo,2));
        $("#totalKilosFinosPlata").val(toFixed(totalKilosFinosPlata,2));
        $("#totalValorNeto").val(toFixed(totalValorNeto,2));
        $("#totalValorDeCompra").val(toFixed(totalValorDeCompra,2));

        crearTablaParticipacion()
    }

    // function crearTablaParticipacion(){
    //     let lotesComposito = $("#lotesComposito").val()===""?[]:$.parseJSON($("#lotesComposito").val())
    //     let totalValorNeto=0
    //     let participacion = []
    //     let mapaParticipacion = {}
    //     let posicion = 0
    //
    //     console.log(`Lotes composito: ${lotesComposito}`)
    //
    //     for(let i=0;i<lotesComposito.length;i++){
    //         totalValorNeto+=lotesComposito[i].valorNetoMineralEnBolivianos
    //         mapaParticipacion = {}
    //         posicion = existeEmpresa(lotesComposito[i].nombreEmpresa, participacion)
    //         if(posicion === -1){
    //             mapaParticipacion.nombreEmpresa = lotesComposito[i].nombreEmpresa
    //             mapaParticipacion.porcentajeParticipacion = lotesComposito[i].valorNetoMineralEnBolivianos
    //             participacion.push(mapaParticipacion)
    //         }else{
    //             participacion[posicion].porcentajeParticipacion = participacion[posicion].porcentajeParticipacion+lotesComposito[i].valorNetoMineralEnBolivianos
    //         }
    //     }
    //
    //     for(let i=0;i<participacion.length;i++){
    //         participacion[i].porcentajeParticipacion = 100*participacion[i].porcentajeParticipacion/totalValorNeto
    //     }
    //
    //     $("#participacion").val(JSON.stringify(participacion));
    //     console.log(`${$("#participacion").val()}`)
    //
    //     $("#participacionTabla").jqGrid("clearGridData", true);
    //     for(var i=0;i<participacion.length;i++){
    //         console.log(`${participacion[i].nombreEmpresa} -> ${participacion[i].porcentajeParticipacion}`)
    //         $("#participacionTabla").jqGrid('addRowData',i+1,participacion[i]);
    //     }
    //     $("#participacionTabla").jqGrid('footerData','set',{nombreEmpresa: "TOTAL"});
    //     $("#participacionTabla").jqGrid('footerData','set',{porcentajeParticipacion: 100});
    // }

    function crearTablaParticipacion(){
        let lotesComposito = $("#lotesComposito").val()===""?[]:$.parseJSON($("#lotesComposito").val())
        let totalKilosNetosSecos=0
        let participacion = []
        let mapaParticipacion = {}
        let posicion = 0

        console.log(`Lotes composito: ${lotesComposito}`)

        for(let i=0;i<lotesComposito.length;i++){
            totalKilosNetosSecos+=lotesComposito[i].kilosNetosSecos
            mapaParticipacion = {}
            posicion = existeEmpresa(lotesComposito[i].municipio, participacion)
            if(posicion === -1){
                mapaParticipacion.departamento = lotesComposito[i].departamento
                mapaParticipacion.municipio = lotesComposito[i].municipio
                mapaParticipacion.kilosNetosSecos = lotesComposito[i].kilosNetosSecos
                mapaParticipacion.porcentajeParticipacion = lotesComposito[i].kilosNetosSecos
                participacion.push(mapaParticipacion)
            }else{
                participacion[posicion].kilosNetosSecos = participacion[posicion].kilosNetosSecos+lotesComposito[i].kilosNetosSecos
                participacion[posicion].porcentajeParticipacion = participacion[posicion].porcentajeParticipacion+lotesComposito[i].kilosNetosSecos
            }
        }

        for(let i=0;i<participacion.length;i++){
            participacion[i].porcentajeParticipacion = 100*participacion[i].porcentajeParticipacion/totalKilosNetosSecos
        }

        $("#participacion").val(JSON.stringify(participacion));
        console.log(`${$("#participacion").val()}`)

        $("#participacionTabla").jqGrid("clearGridData", true);
        for(var i=0;i<participacion.length;i++){
            console.log(`${participacion[i].municipio} -> ${participacion[i].porcentajeParticipacion}`)
            $("#participacionTabla").jqGrid('addRowData',i+1,participacion[i]);
        }
        $("#participacionTabla").jqGrid('footerData','set',{nombreEmpresa: "TOTAL"});
        $("#participacionTabla").jqGrid('footerData','set',{porcentajeParticipacion: 100});
    }

    function existeEmpresa(nombreEmpresa, listaParticipacion){
        let pos = 0
        while(pos<listaParticipacion.length){
            // if(listaParticipacion[pos].nombreEmpresa===nombreEmpresa)
            if(listaParticipacion[pos].municipio===nombreEmpresa)
                return pos
            else
                pos++
        }
        return -1
    }

    function asignarLotesAComposito(){
        let filas = $('#lotesDisponibles').jqGrid('getGridParam','selarrrow'); //restar uno porque la funcion considera el indice inicial como 1
        let mydata = $.parseJSON($("#lotes").val());
        let nuevoMydata = [];
        let lotesComposito = $("#lotesComposito").val()===""?[]:$.parseJSON($("#lotesComposito").val());

        for(let i=0;i<mydata.length;i++){
            if(!existeValor(i+1,filas)) // copiar todas las filas menos la seleccionada
                nuevoMydata.push(mydata[i]);
            else{
                if(mydata[i].disponible==="SI"){
                    mydata[i].disponible="NO"
                    lotesComposito.push(mydata[i])
                }else
                    $.notify("El lote "+mydata[i].lote+" ya fue seleccionado",{autoHide: true,clickToHide: true,className: "warn"});
                nuevoMydata.push(mydata[i])
            }
        }
        //actualizar la cadena JSON en el campo de texto
        $("#lotes").val(JSON.stringify(nuevoMydata));
        $("#lotesComposito").val(JSON.stringify(lotesComposito));
        crearTabla();
        crearTablaComposito()
    }

    function retornarALotesDisponibles(){
        let filas = $('#lotesCompositoTabla').jqGrid('getGridParam','selarrrow'); //restar uno porque la funcion considera el indice inicial como 1
        let liquidacionIds = []
        let lotesDisponibles = $.parseJSON($("#lotes").val());
        let lotesComposito = $("#lotesComposito").val()===""?[]:$.parseJSON($("#lotesComposito").val());
        let lotesCompositoNuevo = [];

        for(let i=0;i<filas.length;i++){
            liquidacionIds.push(lotesComposito[filas[i]-1].liquidacionId)
        }
        //restaurar los lotes eliminados del composito
        let encontrado=false
        let pos=0
        let cantidadLotesDisponibles = lotesDisponibles.length
        for(let i=0;i<liquidacionIds.length;i++){
            encontrado=false
            pos=0
            while (!encontrado && pos<cantidadLotesDisponibles){
                console.log(`Comparando ${lotesDisponibles[pos].liquidacionId} con ${liquidacionIds[i]}`)
                if(lotesDisponibles[pos].liquidacionId===liquidacionIds[i]){
                    encontrado=true
                    lotesDisponibles[pos].disponible="SI"
                }else
                    pos++
            }
        }

        for(var i=0;i<lotesComposito.length;i++){
            if(!existeValor(i+1,filas)) // copiar todas las filas menos la seleccionada
                lotesCompositoNuevo.push(lotesComposito[i]);
        }
        //actualizar la cadena JSON en el campo de texto
        $("#lotes").val(JSON.stringify(lotesDisponibles));
        $("#lotesComposito").val(JSON.stringify(lotesCompositoNuevo));
        crearTabla();
        crearTablaComposito()
    }

    function usuarioActual(){
        $.ajax({
            url: "/demo-liquidaciones/reporteCompositoDeLotes/usuarioActual",
            dataType: 'json',
            data: {
            },
            success: function(data) {
                if($('#vista').val()=="create"){
                    $('#elaboradoPor').val(data.elaboradoPor);
                }
                $('#roles').val(data.roles);
            },
            error: function(request, status, error) {
            }
        });
    }

    function desplegarDestino(){
        let destino = $("#destino").val();
        if(destino=="INGENIO"){
            $("#_ingenio").show();
            $("#_comprador").hide();
        }else{
            $("#_ingenio").hide();
            $("#_comprador").show();
        }
    }

    function estadoDelComposito(){
        var e = $("#estadoDelComposito").val();
        if(e=="DEFINITIVO"){
            $.notify("ATENCIÓN:\n" +
                "Usted ha seleccionado la opción de Estado De Composito DEFINITIVO.\n" +
                "Los Lotes seleccionados de retirarán definitivamente del inventario\n" +
                "segun el Número y Nombre de Compósito asignados.",{autoHide: true,autoHideDelay: 30000,clickToHide: true,className: "warn"});
//            $.notify("DEPOSITO: "+$("#deposito option:selected").text()+"\nLOTE DE TURNO: "+data.lote,{autoHide: false,clickToHide: true,className: "info",position: "top left"});
        }
    }

    function estadoDeAprobacion(){
        var roles = $("#roles").val().toString();

        //alert("roles: "+roles);

        if(roles.indexOf("ROLE_ADMIN")==0){
            $.notify("ATENCIÓN:\n" +
                "Usted no tiene los privilegios necesarios para\n" +
                "realizar la operación de aprobar Compósitos.",{autoHide: true,autoHideDelay: 10000,clickToHide: true,className: "error"});
            $("#estadoDeAprobacion").val("PENDIENTE");
            return;
        }

        var e = $("#estadoDeAprobacion").val();
        if(e=="APROBADO"){
            $("#_aprobadoPor").show();
            $.ajax({
                url: "/demo-liquidaciones/reporteCompositoDeLotes/usuarioActual",
                dataType: 'json',
                data: {
                },
                success: function(data) {
                    $('#aprobadoPor').val(data.elaboradoPor);
                    $('#roles').val(data.roles);
                },
                error: function(request, status, error) {
                }
            });
        }else{
            $("#_aprobadoPor").hide();
            $('#aprobadoPor').val("?");
        }
    }

    function existeValor(valor,array){
        var pos=0;
        var existe=false;
        while(pos<array.length && !existe){
            //alert("comparando array: "+array[pos]+" y valor: "+valor);
            if(array[pos]==valor)
                existe=true;
            else
                pos++;
        }
        return existe;
    }

    function transFloat(numeroString){
        var numero = numeroString.replace(',','');
        return parseFloat(numero);
    }

    function toFixed( number, precision ) {
        var multiplier = Math.pow( 10, precision );
        return Math.round( number * multiplier ) / multiplier;
    }

    function getDecimal(numero){
        var n=Math.round((numero*100)%100);
        var ns=""+n;
        return (ns.length<2)?"0"+ns:ns;
    }

    function redondear2( number ) {
        //var multiplier = Math.pow( 10, precision );
        var multiplier = Math.pow( 10, 2 );
        return Math.round( number * multiplier ) / multiplier;
    }
});

