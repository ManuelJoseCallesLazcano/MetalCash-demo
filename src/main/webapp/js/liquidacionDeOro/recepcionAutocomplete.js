$(document).ready(function() {
    // CREACION DE TABLA DE RETENCIONES UTILIZANDO COMPONENTE jqGrid
    jQuery("#tablaRetenciones").jqGrid({
        datatype: "local",
        height: 200,
        colNames: ["CODIGO","DESCRIPCION","TIPO","CANTIDAD","UNIDAD","MONTO","ASIGNACION"],
        colModel:[
            {name:'CODIGO',index:'CODIGO', width:60},
            {name:'DESCRIPCION',index:'DESCRIPCION', width:200},
            {name:'TIPO',index:'TIPO', width:80},
            {name:'CANTIDAD',index:'CANTIDAD', width:80},
            {name:'UNIDAD',index:'UNIDAD', width:80},
            {name:'MONTO',index:'MONTO', width:80},
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

    //$("#porcentajePlomo, #porcentajePlata, #porcentajeOro, #dolarPuntoPlomo, #dolarPuntoPlata, #dolarPuntoOro, #porcentajeRegalia, #totalAnticiposContraFuturaEntrega").bind("keyup",calcularTotalLiquidoPagable.bind("keyup",generarTablaRetencionesOro));
    //$("#costoLaboratorio1, #costoLaboratorio2, #costoLaboratorio3, #costoLaboratorio4").bind("keyup",calcularTotalCostoLaboratorio);

    //generarTablaRetencionesOro();

    $("#lote").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/recepcionDeOro/recepcionesOroJSON", // /demo-liquidaciones/recepcionDeOro/create
                data: request,
                success: function(data){
                    response(data); // set the response
                },
                error: function(){
                }
            });
        },
        minLength: 1, // triggered only after minimum 1 characters have been entered.
        select: function(event, ui) { // event handler when user selects a company from the list.
            //recepcionDeOro.id
            //$('#liquidacionId').val(ui.item.liquidacionId),
            $('#recepcionDeOro\\.id').val(ui.item.recepcionId),
            $('#nombreCliente').val(ui.item.nombreCliente),
            $('#empresa\\.id').val(ui.item.empresaId);
            $('#deposito\\.id').val(ui.item.depositoId);
            $('#nombreDeposito').val(ui.item.nombreDeposito);
            $('#nombreEmpresa').val(ui.item.nombreEmpresa);
            //    retenciones
            $('#retenciones').val(ui.item.retenciones);
            $('#fechaDeRecepcion').val(ui.item.fechaDeRecepcion);
            $('#cantidadDeSacos').val(ui.item.cantidadDeSacos);
            $('#pesoBruto').val(ui.item.pesoBruto);
            $('#tipoDeMineral').val(ui.item.tipoDeMineral);
            $('#estadoDelLote').val(ui.item.estadoDelLote);
            $('#naturalezaMineral').val(ui.item.naturalezaMineral);
            //kilosNetosHumedos
            $('#cotizacionDiariaDeOro').val(ui.item.cotizacionDiariaDeOro);
            $('#cotizacionQuincenalDeOro').val(ui.item.cotizacionQuincenalDeOro);
            $('#alicuotaDeOro').val(ui.item.alicuotaDeOro);
            $('#tipoDeCambioOficial').val(ui.item.tipoDeCambioOficial);
            $('#tipoDeCambioComercial').val(ui.item.tipoDeCambioComercial);

            $('#porcentajeMermaPromexbol').val(ui.item.porcentajeMermaPromexbol);
            $('#porcentajeHumedadPromexbol').val(ui.item.porcentajeHumedadPromexbol);
            $('#porcentajeOroPromexbol').val(ui.item.porcentajeOroPromexbol);

            if(ui.item.controlCalidadId!=0){
                $('#controlCalidadLink').empty();
                $('<a>',{
                    text: 'Ver Control de Calidad',
                    title: 'Ver Control de Calidad',
                    href: '/demo-liquidaciones/controlCalidadOro/show/'+ui.item.controlCalidadId,
                    target: '_blank'
                }).appendTo('#controlCalidadLink');
            }else{
                $('#controlCalidadLink').empty();
                $('<h3>',{
                    text: 'No tiene Control de Calidad'
                }).appendTo('#controlCalidadLink');
            }

            $('#anticipoPorPagar').val(ui.item.anticipoPorPagar);

            $('#detalleLaboratorio1').val(ui.item.detalleLaboratorio1);
            $('#costoLaboratorio1').val(ui.item.costoLaboratorio1);
            $('#detalleLaboratorio2').val(ui.item.detalleLaboratorio2);
            $('#costoLaboratorio2').val(ui.item.costoLaboratorio2);
            $('#detalleLaboratorio3').val(ui.item.detalleLaboratorio3);
            $('#costoLaboratorio3').val(ui.item.costoLaboratorio3);
            $('#detalleLaboratorio4').val(ui.item.detalleLaboratorio4);
            $('#costoLaboratorio4').val(ui.item.costoLaboratorio4);
            $('#totalCostoLaboratorio').val(ui.item.totalCostoLaboratorio);
            $('#totalAnticiposContraFuturaEntrega').val(ui.item.totalAnticiposContraFuturaEntrega);

            if(ui.item.merma=="1"&&ui.item.humedad=="1"&&ui.item.porcentajeOro=="1"&&ui.item.porcentajePlomo=="1"&&ui.item.porcentajePlata=="1"){
                $.jGrowl("Aun no se han registrado las LEYES para el lote ingresado.", {sticky: true, header: 'ATENCION'});
                $("#create").prop("disabled",true);
            }else
                $("#create").prop("disabled",false);

            if(ui.item.notificacionAnticipo!=""){
                //$.jGrowl(ui.item.notificacionAnticipo, {sticky: true, header: 'ATENCION'});
                $.notify(ui.item.notificacionAnticipo,{autoHide: false,clickToHide: true,className: "warn"});
            }

            if(ui.item.totalAnticiposContraFuturaEntrega!="0"){
                $.jGrowl("El Cliente tiene una deuda pendiente por Anticipo Contra Futura Entrega de: Bs "+ui.item.totalAnticiposContraFuturaEntrega, {sticky: false, header: 'ATENCION'});
            }

            if(ui.item.documentacionCompleta=="?"){
                $.notify("No se recomienda liquidar el Lote "+ui.item.value+" debido a que no tiene documentación completa.",{autoHide: false,clickToHide: true,className: "warn"});
            }

            generarTablaRetencionesOro();

            //poner en "solo lectura" todos los inputs del formulario para que no se modifique ningun dato y para que tampoco haya
            //que configurar uno por uno
            $("#formulario input").prop("readonly", true);
            //habilitar campos para poder escribir otro lote
            $("#lote").prop("readonly", false);
            $('#porcentajeMermaCliente').prop("readonly", false);
            $('#porcentajeHumedadCliente').prop("readonly", false);
            $('#porcentajeOroCliente').prop("readonly", false);

            $('#porcentajeMermaFinal').prop("readonly", false);
            $('#porcentajeHumedadFinal').prop("readonly", false);
            $('#porcentajeOroFinal').prop("readonly", false);
            
            $('#margen').prop("readonly", false);
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("El Lote no existe o ya fue liquidado.");
            }
        }
    });

    $('#valorar').click(function() {
        $.ajax({
            //url:"/demo-liquidaciones/retencion/mostrar",
            url:"/demo-liquidaciones/recepcionDeOro/recepcionesOroValoracionJSON",
            dataType: 'json',
            data: {
                vista: $('#vista').val(),
                recepcionDeOroId: $('#recepcionDeOro\\.id').val(),
                porcentajeMermaFinal: $('#porcentajeMermaFinal').val(),
                porcentajeHumedadFinal: $('#porcentajeHumedadFinal').val(),
                porcentajeOroFinal: $('#porcentajeOroFinal').val(),
                tablaOroId: $('#tablaPreciosOro').val(),
                margen: $('#margen').val()
            },
            success: function(data) {
                $('#recepcionDeOro\\.id').val(data.recepcionId),
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
                $('#cotizacionDiariaDeOro').val(data.cotizacionDiariaDeOro);
                $('#cotizacionQuincenalDeOro').val(data.cotizacionQuincenalDeOro);
                $('#alicuotaDeOro').val(data.alicuotaDeOro);
                $('#tipoDeCambioOficial').val(data.tipoDeCambioOficial);
                $('#tipoDeCambioComercial').val(data.tipoDeCambioComercial);

                $('#kilosNetosHumedos').val(data.kilosNetosHumedos);
                $('#kilosNetosSecos').val(data.kilosNetosSecos);
                $('#kilosFinosOro').val(data.kilosFinosOro);
                $('#onzasTroyDeOro').val(data.onzasTroyDeOro);
                $('#librasFinasDeOro').val(data.librasFinasDeOro);
                $('#valorOficialBrutoDeOro').val(data.valorOficialBrutoDeOro);
                $('#valorOficialBrutoDeOroEnBolivianos').val(data.valorOficialBrutoDeOroEnBolivianos);
                $('#valorOficialBruto').val(data.valorOficialBruto);
                $('#valorPorTonelada').val(data.valorPorTonelada);
                $('#porcentajeBonificacion').val(data.porcentajeBonificacion);

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

                if(data.merma=="1"&&data.humedad=="1"&&data.porcentajeOro=="1"&&data.porcentajePlomo=="1"&&data.porcentajePlata=="1"){
                    $.jGrowl("Aun no se han registrado las LEYES para el lote ingresado.", {sticky: true, header: 'ATENCION'});
                    $("#create").prop("disabled",true);
                }else
                    $("#create").prop("disabled",false);

                if(data.notificacionAnticipo!=""){
                    //$.jGrowl(data.notificacionAnticipo, {sticky: true, header: 'ATENCION'});
                    $.notify(data.notificacionAnticipo,{autoHide: false,clickToHide: true,className: "warn"});
                }

                if(data.totalAnticiposContraFuturaEntrega!="0"){
                    $.jGrowl("El Cliente tiene una deuda pendiente por Anticipo Contra Futura Entrega de: Bs "+data.totalAnticiposContraFuturaEntrega, {sticky: true, header: 'ATENCION'});
                }
                //$.jGrowl("Seleccionado: "+$("#empresa\\.id").val());

                generarTablaRetencionesOro();

                $.notify(data.notificacionValoresTonelada,{autoHide: false,clickToHide: true,className: "info"});
            },
            error: function(request, status, error) {

            }
        });
        //alert("A BAILAR MORENO!!!");
    });

    $('#eliminarRetencion').click(eliminarRetencionOro);

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
        $("#porcentajeOroFinal, #porcentajeOroCliente").val($("#porcentajeOroPromexbol").val());
        $("#porcentajeHumedadFinal, #porcentajeHumedadCliente").val($("#porcentajeHumedadPromexbol").val());
    });

    //calcular ley final por promedio entre promexbol y cliente
    $("#porcentajeHumedadCliente").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajeHumedadPromexbol").val());
        var leyCliente = parseFloat($("#porcentajeHumedadCliente").val());
        var diferencia = leyCliente-leyPromexbol
        if(diferencia>3){//la ley del cliente no puede ser mayor en 3 puntos a la de promexbol
            $.jGrowl("La ley del cliente no puede ser mayor en 3 puntos a la de EMPRESA.", { sticky: true });
            $("#porcentajeHumedadFinal").val(0);
        }else{
            var leyFinal = (leyPromexbol+leyCliente)/2;
            $("#porcentajeHumedadFinal").val(leyFinal);
        }
    });
    $("#porcentajeMermaCliente").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajeMermaPromexbol").val());
        var leyCliente = parseFloat($("#porcentajeMermaCliente").val());
        var diferencia = leyCliente-leyPromexbol
        if(diferencia>3){//la ley del cliente no puede ser mayor en 3 puntos a la de promexbol
            $.jGrowl("La ley del cliente no puede ser mayor en 3 puntos a la de EMPRESA.", { sticky: true });
            $("#porcentajeMermaFinal").val(0);
        }else{
            var leyFinal = (leyPromexbol+leyCliente)/2;
            $("#porcentajeMermaFinal").val(leyFinal);
        }
    });
    $("#porcentajeOroCliente").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajeOroPromexbol").val());
        var leyCliente = parseFloat($("#porcentajeOroCliente").val());
        var diferencia = leyCliente-leyPromexbol;
        if(diferencia>3){//la ley del cliente no puede ser mayor en 3 puntos a la de promexbol
            $.jGrowl("La ley del cliente no puede ser mayor en 3 puntos a la de EMPRESA.", { sticky: true });
            $("#porcentajeOroFinal").val(0);
        }else{
            var leyFinal = (leyPromexbol+leyCliente)/2;
            $("#porcentajeOroFinal").val(leyFinal);
        }
    });
    $("#porcentajeHumedadFinal").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajeHumedadPromexbol").val());
        var leyFinal = parseFloat($("#porcentajeHumedadFinal").val());
        var leyCliente = 2*leyFinal-leyPromexbol;
        $("#porcentajeHumedadCliente").val(isNaN(leyCliente)?"?":leyCliente);
    });
    $("#porcentajeOroFinal").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajeOroPromexbol").val());
        var leyFinal = parseFloat($("#porcentajeOroFinal").val());
        var leyCliente = 2*leyFinal-leyPromexbol;
        $("#porcentajeOroCliente").val(isNaN(leyCliente)?"?":leyCliente);
    });
    
    function calcularTotalLiquidoPagable(){
        var totalPagado = transFloat($("#totalPagado").val());
        var anticipoPorPagar = transFloat($("#anticipoPorPagar").val());
        var cantidadAnticiposPorPagar = transFloat($("#cantidadAnticiposPorPagar").val());
        var bonoTransporteKilosNetosSecosTotal = transFloat($("#bonoTransporteKilosNetosSecosTotal").val());
        var totalAnticiposContraEntrega = 0;
        if(cantidadAnticiposPorPagar>1&&anticipoPorPagar>totalPagado){
            if(totalPagado<0)
                totalAnticiposContraEntrega = 0;
            else
                totalAnticiposContraEntrega = totalPagado;
        }else{
            totalAnticiposContraEntrega = anticipoPorPagar
        }
        var totalAnticiposContraFuturaEntrega = parseFloat($("#totalAnticiposContraFuturaEntrega").val());
        //calculos
        var totalLiquidoPagable = totalPagado-totalAnticiposContraEntrega-totalAnticiposContraFuturaEntrega;
        var totalLiquidoPagableFinal = totalLiquidoPagable+bonoTransporteKilosNetosSecosTotal;
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

    function generarTablaRetencionesOro(){
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


    function eliminarRetencionOro(){
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
        generarTablaRetencionesOro();
    }

    function actualizarLeyes(){
        $.ajax({
            url:"/demo-liquidaciones/controlCalidadOro/leyes",
            dataType: 'json',
            data: {
                recepcionDeOroId: $("#recepcionDeOro\\.id").val()
            },
            success: function(data) {
                if($("#recepcionDeOro\\.id").val()=="") return;

                var mermaAnterior = transFloat($("#porcentajeMermaPromexbol").val());
                var humedadAnterior = transFloat($("#porcentajeHumedadPromexbol").val());
                var zincAnterior = transFloat($("#porcentajeOroPromexbol").val());
                var plomoAnterior = transFloat($("#porcentajePlomoPromexbol").val());
                var plataAnterior = transFloat($("#porcentajePlataPromexbol").val());

                var mermaNueva = data.porcentajeMermaPromexbol;
                var humedadNueva = data.porcentajeHumedadPromexbol;
                var zincNueva = data.porcentajeOroPromexbol;
                var plomoNueva = data.porcentajePlomoPromexbol;
                var plataNueva = data.porcentajePlataPromexbol;

                if(mermaNueva==0 && humedadNueva==0 && zincNueva==0 && plomoNueva==0 && plataNueva==0) return;

                if(mermaAnterior!=mermaNueva || humedadAnterior!=humedadNueva || zincAnterior!=zincNueva || plomoAnterior!=plomoNueva || plataAnterior!=plataNueva){
                    $('#porcentajeMermaPromexbol').val(data.porcentajeMermaPromexbol);
                    $('#porcentajeHumedadPromexbol').val(data.porcentajeHumedadPromexbol);
                    $('#porcentajeOroPromexbol').val(data.porcentajeOroPromexbol);
                    $('#porcentajePlomoPromexbol').val(data.porcentajePlomoPromexbol);
                    $('#porcentajePlataPromexbol').val(data.porcentajePlataPromexbol);

                    $.notify("Se han detectado cambios en el Control de Calidad del Lote.",{autoHide: false,clickToHide: true,className: "info"});
                }
            },
            error: function(request, status, error) {

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

    function toFixed( number, precision ) {
        var multiplier = Math.pow( 10, precision );
        return Math.round( number * multiplier ) / multiplier;
    }

    function transFloat(numeroString){
        var numero = numeroString.replace(',','');
        return parseFloat(numero);
    }
});
