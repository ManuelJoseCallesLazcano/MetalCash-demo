$(document).ready(function() {
    $(".chosen-select").chosen({search_contains: true});

    var opcionesValue = new Array();
    var opcionesText = new Array();
    var opcionesPreciosValue = new Array();
    var opcionesPreciosText = new Array();
    var opcionesTerminosValue = new Array();
    var opcionesTerminosText = new Array();

    respaldarOpciones();
    desplegarCondiciones();
    //setInterval(actualizarLeyes, 2000);
    $("#porcentajeMermaCliente," +
        "#porcentajeZincCliente," +
        "#porcentajePlomoCliente," +
        "#porcentajePlataCliente," +
        "#porcentajeHumedadCliente," +
        "#valorPorTonelada," +
        "#costoTratamiento," +
        "#pesoBrozaInicial," +
        "#totalAnticiposContraFuturaEntrega").numeric({
        allowMinus   : false,
        allowThouSep : false,
        maxDecimalPlaces:2
    });

    if($('#empresa\\.id').val()!=""){ // vista edit
        var s = $('#tablaComplejo').val();
        var p = $('#tablaPrecioPorLme').val();
        var t = $('#terminosDeContrato').val();
        $.ajax({
            url:"/demo-liquidaciones/recepcionDeComplejo/preciosIds",
            dataType: 'json',
            data: {
                empresaId: $('#empresa\\.id').val(),
                naturalezaMineral: $('#naturalezaMineral').val()
            },
            success: function(data) {
                $('#tablasIds').val(data.tablasIds);
                $('#preciosPorLmeIds').val(data.preciosPorLmeIds);
                $('#terminosIds').val(data.terminosIds);
                //preciosPorLmeIds
                simplificarOpciones(""+data.tablasIds,""+data.preciosPorLmeIds,""+data.terminosIds);
                $('#tablaComplejo').val(s);
                $('#tablaPrecioPorLme').val(p);
                $('#terminosDeContrato').val(t);
            },
            error: function(request, status, error) {
            }
        });
    }

    // CREACION DE TABLA DE RETENCIONES UTILIZANDO COMPONENTE jqGrid
    jQuery("#tablaRetenciones").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["CODIGO","DESCRIPCION","TIPO","CANTIDAD","UNIDAD","MONTO","ASIGNACION"],
        colModel:[
            {name:'CODIGO',index:'CODIGO', width:60},
            {name:'DESCRIPCION',index:'DESCRIPCION', width:200},
            {name:'TIPO',index:'TIPO', width:80},
            {name:'CANTIDAD',index:'CANTIDAD', width:80, align: 'right'},
            {name:'UNIDAD',index:'UNIDAD', width:80},
            {name:'MONTO',index:'MONTO', width:80, align: 'right'},
            {name:'ASIGNACION',index:'ASIGNACION', width:80} ],
        multiselect: false,
        caption: "RETENCIONES"
    });

    var mydata = $("#retenciones").val();
    if(mydata=="")
        mydata = [];
    else
        mydata = $.parseJSON(mydata);

    for(var i=0;i<=mydata.length;i++)
        jQuery("#tablaRetenciones").jqGrid('addRowData',i+1,mydata[i]);

    //$("#porcentajePlomo, #porcentajePlata, #porcentajeZinc, #dolarPuntoPlomo, #dolarPuntoPlata, #dolarPuntoZinc, #porcentajeRegalia, #totalAnticiposContraFuturaEntrega").bind("keyup",calcularTotalLiquidoPagable.bind("keyup",generarTablaRetencionesComplejo));
    //$("#costoLaboratorio1, #costoLaboratorio2, #costoLaboratorio3, #costoLaboratorio4").bind("keyup",calcularTotalCostoLaboratorio);
    $("#valorPorTonelada").bind("keyup",recalcularValorToneladaLibre);
    $("#totalAnticiposContraFuturaEntrega").bind("keyup",calcularTotalLiquidoPagable);

    //generarTablaRetencionesComplejo();

    function recuperar(){
        if($("#vista").val() === "edit") {
            console.log("VISTA EDIT. Sin recuperar")
            return;
        }

        $.ajax({
            url: "/demo-liquidaciones/recepcionDeComplejo/recepcionesComplejoJSON", // /Liquidaciones/recepcionDeComplejo/create
            dataType: 'json',
            data: {
                recepcionId: $('#recepcionDeComplejo').val()
            },
            success: function(data) {
                $('#lote').val(data.lote),
                $('#recepcionDeComplejo\\.id').val(data.recepcionId),
                    $('#nombreCliente').val(data.nombreCliente),
                    $('#empresa\\.id').val(data.empresaId);
                $('#deposito\\.id').val(data.depositoId);
                $('#nombreDeposito').val(data.nombreDeposito);
                $('#nombreEmpresa').val(data.nombreEmpresa);
                //    retenciones
                $('#retenciones').val(data.retenciones);
                $('#fechaDeRecepcion').val(data.fechaDeRecepcion);
                $('#cantidadDeSacos').val(data.cantidadDeSacos);
                $('#pesoBruto').val(data.pesoBruto);
                $('#tipoDeMineral').val(data.tipoDeMineral);
                $('#estadoDelLote').val(data.estadoDelLote);
                $('#naturalezaMineral').val(data.naturalezaMineral);
                //kilosNetosHumedos
                $('#cotizacionDiariaDeZinc').val(data.cotizacionDiariaDeZinc);
                $('#cotizacionQuincenalDeZinc').val(data.cotizacionQuincenalDeZinc);
                $('#alicuotaDeZinc').val(data.alicuotaDeZinc);
                $('#cotizacionDiariaDePlomo').val(data.cotizacionDiariaDePlomo);
                $('#cotizacionQuincenalDePlomo').val(data.cotizacionQuincenalDePlomo);
                $('#alicuotaDePlomo').val(data.alicuotaDePlomo);
                $('#cotizacionDiariaDePlata').val(data.cotizacionDiariaDePlata);
                $('#cotizacionQuincenalDePlata').val(data.cotizacionQuincenalDePlata);
                $('#alicuotaDePlata').val(data.alicuotaDePlata);
                $('#tipoDeCambioOficial').val(data.tipoDeCambioOficial);
                $('#tipoDeCambioComercial').val(data.tipoDeCambioComercial);

                $('#porcentajeMermaPromexbol').val(data.porcentajeMermaPromexbol);
                $('#porcentajeHumedadPromexbol').val(data.porcentajeHumedadPromexbol);
                $('#porcentajeZincPromexbol').val(data.porcentajeZincPromexbol);
                $('#porcentajePlomoPromexbol').val(data.porcentajePlomoPromexbol);
                $('#porcentajePlataPromexbol').val(data.porcentajePlataPromexbol);

                if(data.controlCalidadId!=0){
                    $('#controlCalidadLink').empty();
                    $('<a>',{
                        text: 'Ver Control de Calidad',
                        title: 'Ver Control de Calidad',
                        href: '/demo-liquidaciones/controlCalidadComplejo/show/'+data.controlCalidadId,
                        target: '_blank'
                    }).appendTo('#controlCalidadLink');
                }else{
                    $('#controlCalidadLink').empty();
                    $('<h3>',{
                        text: 'No tiene Control de Calidad'
                    }).appendTo('#controlCalidadLink');
                }

                $('#anticipoPorPagar').val(data.totalAnticipoPorPagar);

                $('#costoTratamiento').val(data.costoTratamiento);

                $('#detalleLaboratorio1').val(data.detalleLaboratorio1);
                $('#costoLaboratorio1').val(data.costoLaboratorio1);
                $('#detalleLaboratorio2').val(data.detalleLaboratorio2);
                $('#costoLaboratorio2').val(data.costoLaboratorio2);
                $('#detalleLaboratorio3').val(data.detalleLaboratorio3);
                $('#costoLaboratorio3').val(data.costoLaboratorio3);
                $('#detalleLaboratorio4').val(data.detalleLaboratorio4);
                $('#costoLaboratorio4').val(data.costoLaboratorio4);
                $('#totalCostoLaboratorio').val(data.totalCostoLaboratorio);
                $('#totalAnticiposContraFuturaEntrega').val(data.totalAnticiposContraFuturaEntrega);

                $.ajax({
                    url:"/demo-liquidaciones/recepcionDeComplejo/preciosIds",
                    dataType: 'json',
                    data: {
                        empresaId: $('#empresa\\.id').val(),
                        naturalezaMineral: $('#naturalezaMineral').val()
                    },
                    success: function(data) {
                        $('#tablasIds').val(data.tablasIds);
                        $('#preciosPorLmeIds').val(data.preciosPorLmeIds);
                        $('#terminosIds').val(data.terminosIds);
                        simplificarOpciones(""+data.tablasIds,""+data.preciosPorLmeIds,""+data.terminosIds);
                    },
                    error: function(request, status, error) {
                    }
                });

                if(data.merma=="1"&&data.humedad=="1"&&data.porcentajeZinc=="1"&&data.porcentajePlomo=="1"&&data.porcentajePlata=="1"){
                    $.jGrowl("Aun no se han registrado las LEYES para el lote ingresado.", {sticky: true, header: 'ATENCION'});
                    $("#create").prop("disabled",true);
                }else
                    $("#create").prop("disabled",false);

                if(data.notificacionAnticipo!=""){
                    //$.jGrowl(data.notificacionAnticipo, {sticky: true, header: 'ATENCION'});
                    $.notify(data._notificacionAnticipo,{autoHide: false,clickToHide: true,className: "warn"});
                }

                if(data.totalAnticiposContraFuturaEntrega!="0"){
                    // $.jGrowl("El Cliente tiene una deuda pendiente por Anticipo Contra Futura Entrega de: Bs "+data.totalAnticiposContraFuturaEntrega, {sticky: false, header: 'ATENCION'});
                    $.notify("El Cliente tiene una deuda pendiente por Anticipo Contra Futura Entrega de: Bs "+data.totalAnticiposContraFuturaEntrega,{autoHide: false,clickToHide: true,className: "warn"});
                }

                if(data.documentacionCompleta=="?"){
                    $.notify("No se recomienda liquidar el Lote "+data.value+" debido a que no tiene documentación completa.",{autoHide: false,clickToHide: true,className: "warn"});
                }

                generarTablaRetencionesComplejo();

                //poner en "solo lectura" todos los inputs del formulario para que no se modifique ningun dato y para que tampoco haya
                //que configurar uno por uno
                $("#formulario input").prop("readonly", true);
                //habilitar campos para poder escribir otro lote
                $(".chosen-search-input").prop("readonly", false);
                $("#lote").prop("readonly", false);
                $('#porcentajeMermaCliente').prop("readonly", false);
                $('#porcentajeHumedadCliente').prop("readonly", false);
                $('#porcentajeZincCliente').prop("readonly", false);
                $('#porcentajePlomoCliente').prop("readonly", false);
                $('#porcentajePlataCliente').prop("readonly", false);

                $('#porcentajeMermaFinal').prop("readonly", false);
                $('#porcentajeHumedadFinal').prop("readonly", false);
                $('#porcentajeZincFinal').prop("readonly", false);
                $('#porcentajePlomoFinal').prop("readonly", false);
                $('#porcentajePlataFinal').prop("readonly", false);

                $('#margen').prop("readonly", false);
                $('#valorPorTonelada').prop("readonly", false);
                $('#totalAnticiposContraFuturaEntrega').prop("readonly", false);
                $('#costoTratamiento, #pesoBrozaInicial, #costoTratamientoTotal').prop("readonly", false);
            },
            error: function(request, status, error) {
            }
        });
    }


    recuperar();
    $("#recepcionDeComplejo").bind("change",recuperar);

    $('#valorar').click(function() {
        $.ajax({
            //url:"/demo-liquidaciones/retencion/mostrar",
            url:"/demo-liquidaciones/recepcionDeComplejo/recepcionesComplejoValoracionJSON",
            dataType: 'json',
            data: {
                vista: $('#vista').val(),
                recepcionDeComplejoId: $('#recepcionDeComplejo').val(),
                // recepcionDeComplejoId: $('#recepcionDeComplejo\\.id').val(),
                porcentajeMermaFinal: $('#porcentajeMermaFinal').val(),
                porcentajeHumedadFinal: $('#porcentajeHumedadFinal').val(),
                porcentajeZincFinal: $('#porcentajeZincFinal').val(),
                porcentajePlomoFinal: $('#porcentajePlomoFinal').val(),
                porcentajePlataFinal: $('#porcentajePlataFinal').val(),
                modoValoracion: $('#modoValoracion').val(),
                tablaComplejoId: $('#tablaComplejo').val(),
                preciosPorLmeId: $('#tablaPrecioPorLme').val(),
                margen: $('#margen').val(),
                terminosDeContratoId: $('#terminosDeContrato').val()
            },
            success: function(data) {
                $('#recepcionDeComplejo\\.id').val(data.recepcionId),
                $('#nombreCliente').val(data.nombreCliente),
                $('#empresa\\.id').val(data.empresaId);
                $('#deposito\\.id').val(data.depositoId);
                $('#nombreDeposito').val(data.nombreDeposito);
                $('#nombreEmpresa').val(data.nombreEmpresa);
                //    retenciones
                $('#retenciones').val(data.retenciones);
                $('#fechaDeRecepcion').val(data.fechaDeRecepcion);
                $('#cantidadDeSacos').val(data.cantidadDeSacos);
                $('#pesoBruto').val(data.pesoBruto);
                $('#tipoDeMineral').val(data.tipoDeMineral);
                $('#estadoDelLote').val(data.estadoDelLote);//naturalezaMineral
                $('#naturalezaMineral').val(data.naturalezaMineral);
                //kilosNetosHumedos
                $('#cotizacionDiariaDeZinc').val(data.cotizacionDiariaDeZinc);
                $('#cotizacionQuincenalDeZinc').val(data.cotizacionQuincenalDeZinc);
                $('#alicuotaDeZinc').val(data.alicuotaDeZinc);
                $('#cotizacionDiariaDePlomo').val(data.cotizacionDiariaDePlomo);
                $('#cotizacionQuincenalDePlomo').val(data.cotizacionQuincenalDePlomo);
                $('#alicuotaDePlomo').val(data.alicuotaDePlomo);
                $('#cotizacionDiariaDePlata').val(data.cotizacionDiariaDePlata);
                $('#cotizacionQuincenalDePlata').val(data.cotizacionQuincenalDePlata);
                $('#alicuotaDePlata').val(data.alicuotaDePlata);
                $('#tipoDeCambioOficial').val(data.tipoDeCambioOficial);
                $('#tipoDeCambioComercial').val(data.tipoDeCambioComercial);

                $('#kilosNetosHumedos').val(redondear2(data.kilosNetosHumedos))
                $('#kilosNetosSecos').val(redondear2(data.kilosNetosSecos))
                $('#kilosFinosZinc').val(redondear2(data.kilosFinosZinc))
                $('#kilosFinosPlomo').val(redondear2(data.kilosFinosPlomo))
                $('#kilosFinosPlata').val(redondear2(data.kilosFinosPlata))
                $('#librasFinasDeZinc').val(data.librasFinasDeZinc);
                $('#librasFinasDePlomo').val(data.librasFinasDePlomo);
                $('#onzasTroyDePlata').val(data.onzasTroyDePlata);
                $('#valorOficialBrutoDeZinc').val(data.valorOficialBrutoDeZinc);
                $('#valorOficialBrutoDePlomo').val(data.valorOficialBrutoDePlomo);
                $('#valorOficialBrutoDePlata').val(data.valorOficialBrutoDePlata);
                $('#valorOficialBrutoDeZincEnBolivianos').val(data.valorOficialBrutoDeZincEnBolivianos);
                $('#valorOficialBrutoDePlomoEnBolivianos').val(data.valorOficialBrutoDePlomoEnBolivianos);
                $('#valorOficialBrutoDePlataEnBolivianos').val(data.valorOficialBrutoDePlataEnBolivianos);
                $('#valorOficialBruto').val(data.valorOficialBruto);
                $('#valorPorTonelada').val(data.valorPorTonelada);

                if(parseFloat(data.regaliaMinera)<parseFloat(data.valorNetoMineralEnBolivianos)){
                    $('#regaliaMinera').val(data.regaliaMinera);
                    $('#valorNetoMineral').val(data.valorNetoMineral);
                    $('#valorNetoMineralEnBolivianos').val(data.valorNetoMineralEnBolivianos);
                    $('#bonoCalidad').val(data.bonoCalidad);
                    $('#bonoIncentivo').val(data.bonoIncentivo);
                    $('#valorDeCompra').val(data.valorDeCompra);
                }else{
                    $.notify("El Valor Neto es menor a la Regalía Minera.\nSe procesará al Lote como QUEMADO.",{autoHide: false,clickToHide: true,className: "error"});
                    $('#regaliaMinera').val(0);
                    $('#valorNetoMineral').val(0);
                    $('#valorNetoMineralEnBolivianos').val(0);
                    $('#bonoCalidad').val(0);
                    $('#bonoIncentivo').val(0);
                    $('#valorDeCompra').val(0);
                }

                $('#anticipoPorPagar').val(data.anticipoPorPagar);
                $('#totalAnticiposContraEntrega').val(data.totalAnticiposContraEntrega);
                $('#cantidadAnticiposPorPagar').val(data.cantidadAnticiposPorPagar);
                $('#bonoTransporteKilosNetosSecosTotal').val(data.bonoTransporteKilosNetosSecosTotal);

                $('#detalleLaboratorio1').val(data.detalleLaboratorio1);
                $('#costoLaboratorio1').val(data.costoLaboratorio1);
                $('#detalleLaboratorio2').val(data.detalleLaboratorio2);
                $('#costoLaboratorio2').val(data.costoLaboratorio2);
                $('#detalleLaboratorio3').val(data.detalleLaboratorio3);
                $('#costoLaboratorio3').val(data.costoLaboratorio3);
                $('#detalleLaboratorio4').val(data.detalleLaboratorio4);
                $('#costoLaboratorio4').val(data.costoLaboratorio4);
                $('#totalCostoLaboratorio').val(data.totalCostoLaboratorio);
                $('#totalAnticiposContraFuturaEntrega').val(data.totalAnticiposContraFuturaEntrega);

                if(data.merma=="1"&&data.humedad=="1"&&data.porcentajeZinc=="1"&&data.porcentajePlomo=="1"&&data.porcentajePlata=="1"){
                    $.jGrowl("Aun no se han registrado las LEYES para el lote ingresado.", {sticky: true, header: 'ATENCION'});
                    $("#create").prop("disabled",true);
                }else
                    $("#create").prop("disabled",false);

                if(data.notificacionAnticipo!=""){
                    //$.jGrowl(data.notificacionAnticipo, {sticky: true, header: 'ATENCION'});
                    $.notify(data.notificacionAnticipo,{autoHide: false,clickToHide: true,className: "warn"});
                }

                if(data.totalAnticiposContraFuturaEntrega!="0"){
                    // $.jGrowl("El Cliente tiene una deuda pendiente por Anticipo Contra Futura Entrega de: Bs "+data.totalAnticiposContraFuturaEntrega, {sticky: true, header: 'ATENCION'});
                    $.notify("El Cliente tiene una deuda pendiente por Anticipo Contra Futura Entrega de: Bs "+data.totalAnticiposContraFuturaEntrega,{autoHide: false,clickToHide: true,className: "warn"});
                }
                //$.jGrowl("Seleccionado: "+$("#empresa\\.id").val());

                generarTablaRetencionesComplejo();

                $.notify(data.notificacionValoresTonelada,{autoHide: false,clickToHide: true,className: "info"});
            },
            error: function(request, status, error) {

            }
        });
        //alert("A BAILAR MORENO!!!");
    });

    $('#eliminarRetencion').click(eliminarRetencionComplejo);

    $("#margen").keyup(function(){
        var margen = parseFloat($("#margen").val());
        if(margen>100){
            //$.jGrowl("El Margen no puede ser mayor a $us10.", { sticky: true });
            $.notify("El Margen no puede ser mayor a $us100.\nVuelva a valorar el lote!",{autoHide: true,clickToHide: true,className: "error"});
            $("#margen").val(-10);
        }
    });

    $('#copiarLeyes').click(function() {
        $("#porcentajeMermaFinal, #porcentajeMermaCliente").val($("#porcentajeMermaPromexbol").val());
        $("#porcentajeZincFinal, #porcentajeZincCliente").val($("#porcentajeZincPromexbol").val());
        $("#porcentajePlomoFinal, #porcentajePlomoCliente").val($("#porcentajePlomoPromexbol").val());
        $("#porcentajePlataFinal, #porcentajePlataCliente").val($("#porcentajePlataPromexbol").val());
        $("#porcentajeHumedadFinal, #porcentajeHumedadCliente").val($("#porcentajeHumedadPromexbol").val());
    });

    //calcular ley final por promedio entre promexbol y cliente
    $("#porcentajeHumedadCliente").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajeHumedadPromexbol").val());
        var leyCliente = parseFloat($("#porcentajeHumedadCliente").val());
        var diferencia = leyCliente-leyPromexbol
        if(leyCliente===0)
            $("#porcentajeHumedadFinal").val(leyPromexbol);
        else
            if(diferencia>3){//la ley del cliente no puede ser mayor en 3 puntos a la de promexbol
                $.jGrowl("La ley del cliente no puede ser mayor en 3 puntos a la de EMPRESA.", { sticky: true });
                $("#porcentajeHumedadFinal").val(0);
            }else{
                var leyFinal = (leyPromexbol+leyCliente)/2;
                $("#porcentajeHumedadFinal").val(isNaN(leyFinal)?"?":redondear2(leyFinal));
            }
    });
    $("#porcentajeMermaCliente").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajeMermaPromexbol").val());
        var leyCliente = parseFloat($("#porcentajeMermaCliente").val());
        var diferencia = leyCliente-leyPromexbol
        if(leyCliente===0)
            $("#porcentajeMermaFinal").val(leyPromexbol);
        else
            if(diferencia>3){//la ley del cliente no puede ser mayor en 3 puntos a la de promexbol
                $.jGrowl("La ley del cliente no puede ser mayor en 3 puntos a la de EMPRESA.", { sticky: true });
                $("#porcentajeMermaFinal").val(0);
            }else{
                var leyFinal = (leyPromexbol+leyCliente)/2;
                $("#porcentajeMermaFinal").val(isNaN(leyFinal)?"?":redondear2(leyFinal));
            }
    });
    $("#porcentajeZincCliente").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajeZincPromexbol").val());
        var leyCliente = parseFloat($("#porcentajeZincCliente").val());
        var diferencia = leyCliente-leyPromexbol;
        if(leyCliente===0)
            $("#porcentajeZincFinal").val(leyPromexbol);
        else
            if(diferencia>3){//la ley del cliente no puede ser mayor en 3 puntos a la de promexbol
                $.jGrowl("La ley del cliente no puede ser mayor en 3 puntos a la de EMPRESA.", { sticky: true });
                $("#porcentajeZincFinal").val(0);
            }else{
                var leyFinal = (leyPromexbol+leyCliente)/2;
                $("#porcentajeZincFinal").val(isNaN(leyFinal)?"?":redondear2(leyFinal));
            }
    });
    $("#porcentajePlomoCliente").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajePlomoPromexbol").val());
        var leyCliente = parseFloat($("#porcentajePlomoCliente").val());
        var diferencia = leyCliente-leyPromexbol;
        if(leyCliente===0)
            $("#porcentajePlomoFinal").val(leyPromexbol);
        else
            if(diferencia>3){//la ley del cliente no puede ser mayor en 3 puntos a la de promexbol
                $.jGrowl("La ley del cliente no puede ser mayor en 3 puntos a la de EMPRESA.", { sticky: true });
                $("#porcentajePlomoFinal").val(0);
            }else{
                var leyFinal = (leyPromexbol+leyCliente)/2;
                $("#porcentajePlomoFinal").val(isNaN(leyFinal)?"?":redondear2(leyFinal));
            }
    });
    $("#porcentajePlataCliente").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajePlataPromexbol").val());
        var leyCliente = parseFloat($("#porcentajePlataCliente").val());
        var diferencia = leyCliente-leyPromexbol;
        var diferenciaMaxima = 0;

        if(leyPromexbol>=0&&leyPromexbol<=59.99) diferenciaMaxima = 3;
        if(leyPromexbol>=60&&leyPromexbol<=99.99) diferenciaMaxima = 5;
        if(leyPromexbol>=100&&leyPromexbol<=199.99) diferenciaMaxima = 10;
        if(leyPromexbol>=200) diferenciaMaxima = 20;

        if(leyCliente===0)
            $("#porcentajePlataFinal").val(leyPromexbol);
        else
            if(diferencia>diferenciaMaxima){//la ley del cliente no puede ser mayor en 3 puntos a la de promexbol
                $.jGrowl("La ley del cliente no puede ser mayor en "+diferenciaMaxima+" puntos a la de EMPRESA. [Ag DM: "+leyPromexbol+"]", { sticky: true });
                $("#porcentajePlataFinal").val(0);
            }else{
                var leyFinal = (leyPromexbol+leyCliente)/2;
                $("#porcentajePlataFinal").val(isNaN(leyFinal)?"?":redondear2(leyFinal));
            }
    });
    $("#porcentajeHumedadFinal").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajeHumedadPromexbol").val());
        var leyFinal = parseFloat($("#porcentajeHumedadFinal").val());
        var leyCliente = 2*leyFinal-leyPromexbol;
        $("#porcentajeHumedadCliente").val(isNaN(leyCliente)?"?":leyCliente);
    });
    $("#porcentajeZincFinal").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajeZincPromexbol").val());
        var leyFinal = parseFloat($("#porcentajeZincFinal").val());
        var leyCliente = 2*leyFinal-leyPromexbol;
        $("#porcentajeZincCliente").val(isNaN(leyCliente)?"?":leyCliente);
    });
    $("#porcentajePlomoFinal").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajePlomoPromexbol").val());
        var leyFinal = parseFloat($("#porcentajePlomoFinal").val());
        var leyCliente = 2*leyFinal-leyPromexbol;
        $("#porcentajePlomoCliente").val(isNaN(leyCliente)?"?":leyCliente);
    });
    $("#porcentajePlataFinal").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajePlataPromexbol").val());
        var leyFinal = parseFloat($("#porcentajePlataFinal").val());
        var leyCliente = 2*leyFinal-leyPromexbol;
        $("#porcentajePlataCliente").val(isNaN(leyCliente)?"?":leyCliente);
    });

    controlarAplicarCostoTratamiento();
    $("#aplicarCostoTratamiento").change(controlarAplicarCostoTratamiento);

    $("#costoTratamiento, #pesoBrozaInicial").keyup(calcularCostoTratamiento);

    function controlarAplicarCostoTratamiento() {
        let aplicarCostoTratamiento = $("#aplicarCostoTratamiento").val();
        if (aplicarCostoTratamiento === 'SI') {
            $('#pesoBrozaInicial, #costoTratamientoTotal').val('');
            // calcularCostoTratamiento();
            $("#_costoTratamiento").show();
        } else {
            $('#pesoBrozaInicial, #costoTratamientoTotal').val(0);
            $("#_costoTratamiento").hide();
        }
        calcularTotalLiquidoPagable();
    }

    function calcularCostoTratamiento() {
        let tipoDeCambioOficial = transFloat($('#tipoDeCambioOficial').val());
        let costoTratamiento = transFloat($('#costoTratamiento').val());
        let pesoBrozaInicial = transFloat($('#pesoBrozaInicial').val());
        let costoTratamientoTotal = tipoDeCambioOficial*costoTratamiento*pesoBrozaInicial/1000;
        $("#costoTratamientoTotal").val((isNaN(costoTratamientoTotal)) ?"?":toFixed(costoTratamientoTotal,2).toString());

        calcularTotalLiquidoPagable();
    }

    function calcularTotalLiquidoPagable(){
        let totalPagado = transFloat($("#totalPagado").val());
        let anticipoPorPagar = transFloat($("#anticipoPorPagar").val());
        let cantidadAnticiposPorPagar = transFloat($("#cantidadAnticiposPorPagar").val());
        let bonoTransporteKilosNetosSecosTotal = transFloat($("#bonoTransporteKilosNetosSecosTotal").val());
        let costoTratamientoTotal = transFloat($("#costoTratamientoTotal").val());
        let totalAnticiposContraEntrega = 0;
        if(cantidadAnticiposPorPagar>1&&anticipoPorPagar>totalPagado){
            if(totalPagado<0)
                totalAnticiposContraEntrega = 0;
            else
                totalAnticiposContraEntrega = totalPagado;
        }else{
            totalAnticiposContraEntrega = anticipoPorPagar
        }
        let totalAnticiposContraFuturaEntrega = transFloat($("#totalAnticiposContraFuturaEntrega").val());
        //calculos
        let totalLiquidoPagable = totalPagado-totalAnticiposContraEntrega-totalAnticiposContraFuturaEntrega-costoTratamientoTotal;
        let totalLiquidoPagableFinal = totalLiquidoPagable+bonoTransporteKilosNetosSecosTotal;
        //alert("totalPagado="+totalPagado+" anticipoPorPagar="+anticipoPorPagar);

        $("#totalAnticiposContraEntrega").val((isNaN(totalAnticiposContraEntrega)) ?"?":toFixed(totalAnticiposContraEntrega,2).toString());
        $("#totalLiquidoPagable").val((isNaN(totalLiquidoPagable)) ?"?":toFixed(totalLiquidoPagable,2).toString());
        $("#totalLiquidoPagableFinal").val((isNaN(totalLiquidoPagableFinal)) ?"?":toFixed(totalLiquidoPagableFinal,2).toString());

        if($("#vista").val()=="create"){
            $("#totalLiquidoPagableOriginal").val((isNaN(totalLiquidoPagable)) ?"?":toFixed(totalLiquidoPagable,2).toString());
            $("#diferenciaLiquidoPagable").val(0);
        }else{
            var totalLiquidoPagableOriginal = transFloat($("#totalLiquidoPagableOriginal").val());
            var diferencia = totalLiquidoPagable - totalLiquidoPagableOriginal;
            $("#diferenciaLiquidoPagable").val(diferencia);
        }
    }

    function recalcularValorToneladaLibre(){
        let valorPorTonelada = transFloat($("#valorPorTonelada").val());
        let kilosNetosSecos = transFloat($("#kilosNetosSecos").val());
        let tipoDeCambioComercial = transFloat($("#tipoDeCambioComercial").val());
        let valorNetoMineral = valorPorTonelada*kilosNetosSecos/1000
        let valorNetoMineralEnBolivianos = valorNetoMineral*tipoDeCambioComercial
        let valorDeCompra = valorNetoMineralEnBolivianos
        $("#valorNetoMineral").val((isNaN(valorNetoMineral)) ?"?":redondear2(valorNetoMineral));
        $("#valorNetoMineralEnBolivianos").val((isNaN(valorNetoMineralEnBolivianos)) ?"?":redondear2(valorNetoMineralEnBolivianos));
        $("#valorDeCompra").val((isNaN(valorDeCompra)) ?"?":redondear2(valorDeCompra));
        generarTablaRetencionesComplejo()
    }

    function generarTablaRetencionesComplejo(){
        //alert("JSON: "+$("#retenciones").val());
        var retencionesJSON = jQuery.parseJSON($("#retenciones").val());
        var valorOficialBruto = transFloat($("#valorOficialBruto").val());
        var valorOficialNeto = transFloat($("#valorNetoMineralEnBolivianos").val());
        var valorDeCompra = transFloat($("#valorDeCompra").val());
        var cantidadDeSacos = transFloat($("#cantidadDeSacos").val());
        var pesoBruto = transFloat($("#pesoBruto").val());
        var regaliaMinera = transFloat($("#regaliaMinera").val());
        var totalRetenciones = regaliaMinera;

        //alert("dentro de generador de tabla de retenciones!");

        regaliaMinera = (isNaN(regaliaMinera))?0:regaliaMinera;

        var tabla = new Array();
        $.each(retencionesJSON,function() {
            var monto = 0;
            if(this.DESCRIPCION=="REGALIA MINERA")
                monto = regaliaMinera;
            else{
                var descuento = transFloat(this.CANTIDAD);
                var unidad = this.UNIDAD;
                var asignacion = this.ASIGNACION;
                if(unidad=="%"&&asignacion=="VBV")
                    monto = valorOficialBruto*descuento/100;
                if(unidad=="%"&&asignacion=="VNV")
                    monto = valorOficialNeto*descuento/100;
                if(unidad=="Bs"&&asignacion=="TON. BRUTA")
                    monto = pesoBruto*descuento/1000;
                if(unidad=="Bs"&&asignacion=="SACO")
                    monto = cantidadDeSacos*descuento;
                if(unidad=="Bs"&&asignacion=="FIJO")
                    monto = descuento;
                monto=isNaN(monto)?0:toFixed(monto,2)
                //alert("descuento = "+this.CANTIDAD+" por "+this.DESCRIPCION+" monto = "+this.MONTO);
                totalRetenciones+=monto;
            }
            var fila = new Object();
            fila.CODIGO = this.CODIGO;
            fila.DESCRIPCION = this.DESCRIPCION;
            fila.TIPO = this.TIPO;
            fila.CANTIDAD = this.CANTIDAD;
            fila.UNIDAD = this.UNIDAD;
            fila.MONTO = monto;
            fila.ASIGNACION = this.ASIGNACION;

            tabla.push(fila);
            $("#retenciones").val(JSON.stringify(tabla));
        });

        $("#tablaRetenciones").jqGrid("clearGridData", true);
        for(var i=0;i<=tabla.length;i++)
            jQuery("#tablaRetenciones").jqGrid('addRowData',i+1,tabla[i]);

        var totalPagado = valorDeCompra - totalRetenciones;
        //$("#retenciones").val(JSON.stringify(retencionesJSON));
        $("#totalRetenciones").val((isNaN(totalRetenciones)) ?"?":toFixed(totalRetenciones,2).toString());
        $("#totalPagado").val((isNaN(totalPagado)) ?"?":toFixed(totalPagado,2).toString());

        calcularTotalLiquidoPagable();
    }


    function eliminarRetencionComplejo(){
        var filaId = parseInt(jQuery('#tablaRetenciones').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //alert("fila: "+filaId);
        var mydata = $.parseJSON($("#retenciones").val());
        var nuevoMydata = new Array()

        for(var i=0;i<mydata.length;i++){
            if(filaId!=i) // copiar todas las filas menos la seleccionada
                nuevoMydata.push(mydata[i]);
        }
        //actualizar la cadena JSON en el campo de texto
        $("#retenciones").val(JSON.stringify(nuevoMydata));
        generarTablaRetencionesComplejo();
    }

    $("#modoValoracion" ).change(function() {
        desplegarCondiciones();
    });

    function actualizarLeyes(){
        $.ajax({
            url:"/demo-liquidaciones/controlCalidadComplejo/leyes",
            dataType: 'json',
            data: {
                recepcionDeComplejoId: $("#recepcionDeComplejo\\.id").val()
            },
            success: function(data) {
                if($("#recepcionDeComplejo\\.id").val()=="") return;

                var mermaAnterior = transFloat($("#porcentajeMermaPromexbol").val());
                var humedadAnterior = transFloat($("#porcentajeHumedadPromexbol").val());
                var zincAnterior = transFloat($("#porcentajeZincPromexbol").val());
                var plomoAnterior = transFloat($("#porcentajePlomoPromexbol").val());
                var plataAnterior = transFloat($("#porcentajePlataPromexbol").val());

                var mermaNueva = data.porcentajeMermaPromexbol;
                var humedadNueva = data.porcentajeHumedadPromexbol;
                var zincNueva = data.porcentajeZincPromexbol;
                var plomoNueva = data.porcentajePlomoPromexbol;
                var plataNueva = data.porcentajePlataPromexbol;

                if(mermaNueva==0 && humedadNueva==0 && zincNueva==0 && plomoNueva==0 && plataNueva==0) return;

                if(mermaAnterior!=mermaNueva || humedadAnterior!=humedadNueva || zincAnterior!=zincNueva || plomoAnterior!=plomoNueva || plataAnterior!=plataNueva){
                    $('#porcentajeMermaPromexbol').val(data.porcentajeMermaPromexbol);
                    $('#porcentajeHumedadPromexbol').val(data.porcentajeHumedadPromexbol);
                    $('#porcentajeZincPromexbol').val(data.porcentajeZincPromexbol);
                    $('#porcentajePlomoPromexbol').val(data.porcentajePlomoPromexbol);
                    $('#porcentajePlataPromexbol').val(data.porcentajePlataPromexbol);

                    $.notify("Se han detectado cambios en el Control de Calidad del Lote.",{autoHide: false,clickToHide: true,className: "info"});
                }
            },
            error: function(request, status, error) {

            }
        });
    }

    function simplificarOpciones(tablasIds, preciosIds, terminosIds){

        var ids = tablasIds.split('-');
        copiarOpciones();

        $('#tablaComplejo option[value]').each(function() {
            if( !existe(ids,parseInt($(this).attr("value"))) ) {
                $(this).remove();
            }
        });

        ids = preciosIds.split('-');
        $('#tablaPrecioPorLme option[value]').each(function() {
            if( !existe(ids,parseInt($(this).attr("value"))) ) {
                $(this).remove();
            }
        });

        ids = terminosIds.split('-');
        $('#terminosDeContrato option[value]').each(function() {
            if( !existe(ids,parseInt($(this).attr("value"))) ) {
                $(this).remove();
            }
        });
    }

    function existe(lista,valor){
        var pos=0;
        var hay=false;
        while(pos<lista.length&&!hay){
            if(lista[pos]==valor)
                hay=true;
            else
                pos++;
        }
        return hay;
    }

    function respaldarOpciones(){
        var pos=0;
        $('#tablaComplejo option').each(function() {
            opcionesValue[pos]=$(this).val();
            opcionesText[pos]=$(this).text();
            pos++;
        });

        pos=0;
        $('#tablaPrecioPorLme option').each(function() {
            opcionesPreciosValue[pos]=$(this).val();
            opcionesPreciosText[pos]=$(this).text();
            pos++;
        });

        pos=0;
        $('#terminosDeContrato option').each(function() {
            opcionesTerminosValue[pos]=$(this).val();
            opcionesTerminosText[pos]=$(this).text();
            pos++;
        });
    }

    function copiarOpciones(){
        //TABLA COMPLEJOS
        //eliminar todas las opciones
        $('#tablaComplejo option[value]').each(function() {
            $(this).remove();
        });
        //copiar los items respaldados
        for(var i=0;i<opcionesValue.length;i++){
            $("#tablaComplejo").append("<option value=\""+opcionesValue[i]+"\">"+opcionesText[i]+"</option>");
        }

        //PRECIOS LME
        //eliminar todas las opciones
        $('#tablaPrecioPorLme option[value]').each(function() {
            $(this).remove();
        });
        //copiar los items respaldados
        for(var i=0;i<opcionesValue.length;i++){
            $("#tablaPrecioPorLme").append("<option value=\""+opcionesPreciosValue[i]+"\">"+opcionesPreciosText[i]+"</option>");
        }

        //TERMINOS DE CONTRATO
        //eliminar todas las opciones
        $('#terminosDeContrato option[value]').each(function() {
            $(this).remove();
        });
        //copiar los items respaldados
        for(var i=0;i<opcionesTerminosValue.length;i++){
            $("#terminosDeContrato").append("<option value=\""+opcionesTerminosValue[i]+"\">"+opcionesTerminosText[i]+"</option>");
        }
    }

    function desplegarCondiciones(){
        var modo = $("#modoValoracion").val();
        if(modo=="TABLA"){
            $("#_tablaComplejo").show();
            $("#_tablaPrecioPorLme").hide();
            $("#_terminosDeContrato").hide();
        }
        if(modo=="PRECIO POR LME"){
            $("#_tablaComplejo").hide();
            $("#_tablaPrecioPorLme").show();
            $("#_terminosDeContrato").hide();
        }
        if(modo=="TERMINOS DE CONTRATO"){
            $("#_tablaComplejo").hide();
            $("#_tablaPrecioPorLme").hide();
            $("#_terminosDeContrato").show();
        }
    }

    function redondear2(number){
        return toFixed(number,2)
    }

    function toFixed( number, precision ) {
        var multiplier = Math.pow( 10, precision );
        return Math.round( number * multiplier ) / multiplier;
    }

    function transFloat(numeroString){
        var numero = numeroString.replace(',','');
        return parseFloat(numero);
    }
});
