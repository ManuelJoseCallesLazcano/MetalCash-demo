$(document).tooltip({
    content: function () {
        return $(this).prop('title');
    }
});

$(document).ready(function() {
    var lotesJSON = "";
    var lotesLiquidadosJSON = new Array();
    var tiempo = new Date();

    $('#millis').val(tiempo.getTime());
    $("#agregar").bind("click",buscarFilas);
    $("#quitar").bind("click",quitarFilas);
    $("#copiarLeyes").bind("click",copiarLeyes);
    $("#valorar").bind("click",valorar);
    $("#imprimir").bind("click",imprimir);

    mostrarTablaLotesLiquidados();

    function buscarFilas(){
        $.ajax({
            url: "/demo-liquidaciones/recepcionDeComplejo/recepcionGrupalPlomoPlataJSON",
            dataType: 'json',
            data: {
                depositoId: $('#deposito').val(),
                loteInicial: $('#loteInicial').val(),
                loteFinal: $('#loteFinal').val()
            },
            success: function(data) {
                $('#lotes').val(data.lotes);
                lotesJSON = $.parseJSON(data.lotes);
                insertarFilas();
            },
            error: function(request, status, error) {

            }
        });
    }

    function insertarFilas(){
        //var data = $.parseJSON(dataJSON);
        var data = lotesJSON;
        $("#nuevaTabla").find('tbody').empty();
        for(var i=0;i<data.length;i++){
            console.log(data[i]);
            $("#nuevaTabla").find('tbody')
                .append($('<tr>')
                    .attr('title', mensajeTooltip(data[i]))
                .append($('<td>')
                    .append($('<input>')
                        .attr('id', 'eliminar_'+i)
                        .attr('type', 'checkbox')
                        .attr('size', '3')
                        .attr('readonly', 'true')
                        .val(data[i].recepcionId)
                    )
                )
                .append($('<td>')
                    .append($('<input>')
                        .attr('id', 'recepcionId_'+i)
                        .attr('size', '3')
                        .attr('readonly', 'true')
                        .val(data[i].recepcionId)
                    )
                )
                .append($('<td>')
                    .append($('<input>')
                        .attr('id', 'lote_'+i)
                        .attr('size', '15')
                        .attr('readonly', 'true')
                        .val(data[i].lote)
                    )
                )
                .append($('<td>')
                    .append($('<input>')
                        .attr('id', 'porcentajePlomoCliente_'+i)
                        .attr('size', '5')
                        .val(data[i].porcentajePlomoCliente)
                        .bind("keyup",function(){
                            promediarLey("Plomo",this);
                        })
                    )
                )
                .append($('<td>')
                    .append($('<input>')
                        .attr('id', 'porcentajePlataCliente_'+i)
                        .attr('size', '5')
                        .val(data[i].porcentajePlataCliente)
                        .bind("keyup",function(){
                            promediarLey("Plata",this);
                        })
                    )
                )
                .append($('<td>')
                    .append($('<input>')
                        .attr('id', 'porcentajePlomoFinal_'+i)
                        .attr('size', '5')
                        .attr('readonly', 'true')
                        .val(data[i].porcentajePlomoFinal)
                    )
                )
                .append($('<td>')
                    .append($('<input>')
                        .attr('id', 'porcentajePlataFinal_'+i)
                        .attr('size', '5')
                        .attr('readonly', 'true')
                        .val(data[i].porcentajePlataFinal)
                    )
                )
                .append($('<td>')
                    .append($('<select>')
                        .attr('id', 'modoValoracion_'+i)
                        .append($('<option>', {value: 0, text: "-SELECCIONE-"}))
                        .append($('<option>', {value: 1, text: "TABLA"}))
                        .append($('<option>', {value: 2, text: "TERMINOS"}))
                        .bind("change",function(){
                            cargarTablaTermino(this);
                        })
                    )
                )
                .append($('<td>')
                    .append($('<select>')
                        .attr('id', 'tablaTermino_'+i)
                        .append($('<option>', {value: 0, text: "-SELECCIONE-"}))
                    )
                )
                .append($('<td>')
                    .append($('<input>')
                        .attr('id', 'margen_'+i)
                        .attr('size', '5')
                        .val(data[i].margen)
                    )
                )
                .append($('<td>')
                    .append($('<input>')
                        .attr('id', 'valorPorTonelada_'+i)
                        .attr('size', '5')
                        .attr('readonly', 'true')
                        .val(data[i].valorPorTonelada)
                    )
                )
                .append($('<td>')
                    .append($('<input>')
                        .attr('id', 'totalAnticiposContraEntrega_'+i)
                        .attr('size', '5')
                        .attr('readonly', 'true')
                        .val(data[i].totalAnticiposContraEntrega)
                    )
                )
                .append($('<td>')
                    .append($('<input>')
                        .attr('id', 'totalLiquidoPagable_'+i)
                        .attr('size', '10')
                        .attr('readonly', 'true')
                        .val(data[i].totalLiquidoPagable)
                    )
                )
                .append($('<td>')
                    .append($('<button>')
                        .attr('id', 'valorar_'+i)
                        .attr('type', 'button')
                        .attr('class', 'valorar')
                        .append('V')
                        .bind("click",function(){
                            valorar(this);
                        })
                    )
                )
                .append($('<td>')
                    .append($('<button>')
                        .attr('id', 'liquidar_'+i)
                        .attr('type', 'button')
                        .attr('class', 'liquidar')
                        .append('L')
                        .bind("click",function(){
                            liquidar(this);
                        })
                    )
                )
            );

            if(data[i].notificacionAnticipo!="")
                $.notify(data[i].notificacionAnticipo,{autoHide: false,clickToHide: true,className: "warn"});

            if(data[i].documentacionCompleta!="?"){
                $.notify("No se recomienda liquidar el Lote "+data[i].lote+" debido a que no tiene documentación completa.",{autoHide: false,clickToHide: true,className: "warn"});
            }
        }
    }

    function quitarFilas(){
        //var data = $.parseJSON(lotesJSON);
        var data = lotesJSON;
        var nuevoData = new Array();
        var s="seleccionados: ";
        for(var i=0;i<data.length;i++){
            //s=s+$("#eliminar_"+i).prop("checked")+" - ";
            if(!$("#eliminar_"+i).prop("checked")){
                nuevoData.push(data[i]);
            }
        }
        //alert(s);
        //lotesJSON = $.parseJSON(nuevoData);
        lotesJSON = nuevoData;
        $("#lotes").val(JSON.stringify(nuevoData));

        insertarFilas();
    }

    function cargarTablaTermino(modoValoracionSelect){
        var i = posicion($(modoValoracionSelect).attr('id'));
        var recepcionId = $("#recepcionId_"+i).val();
        $.ajax({
            url: "/demo-liquidaciones/recepcionDeComplejo/listarTablaTermino",
            dataType: 'json',
            data: {
                modo: $(modoValoracionSelect).val(),
                recepcionId: recepcionId
            },
            success: function(data) {
                //console.log("data.opciones="+data.opciones);
                $("#tablaTermino_"+i).empty().append(data.opciones);
            },
            error: function(request, status, error) {

            }
        });
    }

    function promediarLey(elemento,entrada){
        var i = posicion($(entrada).attr('id'));
        //porcentajeZincCliente_0
        //porcentajeZincFinal_0
        var porcentajePromexbol = 0;
        var porcentajeCliente = transFloat($(entrada).val());
        var porcentajeFinal;
        if(elemento=="Plomo"){
            porcentajePromexbol = transFloat(lotesJSON[i].porcentajePlomoPromexbol);
            porcentajeFinal = (porcentajePromexbol+porcentajeCliente)/2;
        }
        if(elemento=="Plata"){
            porcentajePromexbol = transFloat(lotesJSON[i].porcentajePlataPromexbol);

            var diferencia = porcentajeCliente-porcentajePromexbol
            var diferenciaMaxima = 0;

            if(porcentajePromexbol>=0&&porcentajePromexbol<=59.99) diferenciaMaxima = 3;
            if(porcentajePromexbol>=60&&porcentajePromexbol<=99.99) diferenciaMaxima = 5;
            if(porcentajePromexbol>=100&&porcentajePromexbol<=199.99) diferenciaMaxima = 10;
            if(porcentajePromexbol>=200) diferenciaMaxima = 20;

            if(diferencia>diferenciaMaxima){//la ley del cliente no puede ser mayor en 3 puntos a la de promexbol
                $.jGrowl("La ley del cliente no puede ser mayor en "+diferenciaMaxima+" puntos a la de EMPRESA. [Ag DM: "+porcentajePromexbol+"]", { sticky: true });
                porcentajeFinal = 0;
            }else{
                porcentajeFinal = (porcentajePromexbol+porcentajeCliente)/2;
            }
        }
        $("#porcentaje"+elemento+"Final_"+i).val((isNaN(porcentajeFinal)) ?"?":toFixed(porcentajeFinal,2).toString());
    }

    function copiarLeyes(){
        for(var i=0;i<lotesJSON.length;i++){
            $("#porcentajePlomoCliente_"+i).val(lotesJSON[i].porcentajePlomoPromexbol);
            $("#porcentajePlomoFinal_"+i).val(lotesJSON[i].porcentajePlomoPromexbol);
            lotesJSON[i].porcentajePlomoCliente=lotesJSON[i].porcentajePlomoPromexbol;
            lotesJSON[i].porcentajePlomoFinal=lotesJSON[i].porcentajePlomoPromexbol;
            
            $("#porcentajePlataCliente_"+i).val(lotesJSON[i].porcentajePlataPromexbol);
            $("#porcentajePlataFinal_"+i).val(lotesJSON[i].porcentajePlataPromexbol);
            lotesJSON[i].porcentajePlataCliente=lotesJSON[i].porcentajePlataPromexbol;
            lotesJSON[i].porcentajePlataFinal=lotesJSON[i].porcentajePlataPromexbol;

            lotesJSON[i].porcentajeMermaCliente=lotesJSON[i].porcentajeMermaPromexbol;
            lotesJSON[i].porcentajeMermaFinal=lotesJSON[i].porcentajeMermaPromexbol;

            lotesJSON[i].porcentajeHumedadCliente=lotesJSON[i].porcentajeHumedadPromexbol;
            lotesJSON[i].porcentajeHumedadFinal=lotesJSON[i].porcentajeHumedadPromexbol;
        }

        $("#lotes").val(JSON.stringify(lotesJSON));
    }
    
    function valorar(boton){
        var i = posicion($(boton).attr('id'));

        if(!validar(i)){
            //$.notify("Existe información incorrecta en la tabla de Lotes! Revise por favor.",{autoHide: true,clickToHide: true,className: "error"});
            $.notify("Existe información incorrecta para valorar el Lote "+lotesJSON[i].lote+"! Revise por favor.",{autoHide: true,clickToHide: true,className: "error"});
            return;
        }

        //copiar informacion de la tabla a la cadena JSON
        lotesJSON[i].porcentajePlomoCliente=$("#porcentajePlomoCliente_"+i).val();
        lotesJSON[i].porcentajePlomoFinal=$("#porcentajePlomoFinal_"+i).val();
        lotesJSON[i].porcentajePlataCliente=$("#porcentajePlataCliente_"+i).val();
        lotesJSON[i].porcentajePlataFinal=$("#porcentajePlataFinal_"+i).val();
        lotesJSON[i].porcentajeMermaCliente=lotesJSON[i].porcentajeMermaPromexbol;
        lotesJSON[i].porcentajeMermaFinal=lotesJSON[i].porcentajeMermaPromexbol;
        lotesJSON[i].porcentajeHumedadCliente=lotesJSON[i].porcentajeHumedadPromexbol;
        lotesJSON[i].porcentajeHumedadFinal=lotesJSON[i].porcentajeHumedadPromexbol;
        lotesJSON[i].margen=transFloat($("#margen_"+i).val());

        var modo=$("#modoValoracion_"+i+" option:selected").text();
        var preciosIds;
        if(modo=="TABLA"){
            lotesJSON[i].modoValoracion="TABLA";
            lotesJSON[i].tablaComplejoId=$("#tablaTermino_"+i).val();
            //primer id por defecto para terminos
            preciosIds=$.parseJSON(lotesJSON[i].preciosIds);
            lotesJSON[i].terminosDeContratoId=(preciosIds.terminosIds.toString().split("-"))[0];
        }

        if(modo=="TERMINOS"){
            lotesJSON[i].modoValoracion="TERMINOS DE CONTRATO";
            lotesJSON[i].terminosDeContratoId=$("#tablaTermino_"+i).val();
            //primer id por defecto para terminos
            preciosIds=$.parseJSON(lotesJSON[i].preciosIds);
            lotesJSON[i].tablaComplejoId=(preciosIds.tablasIds.toString().split("-"))[0];
        }

        //actualizando la cadena JSON del campo lotes
        $("#lotes").val(JSON.stringify(lotesJSON));

        //VALORACION
        var cotizacionQuincenalZinc=0;
        var cotizacionQuincenalPlomo=0;
        var cotizacionQuincenalPlata=0;
        var alicuotaZinc=0;
        var alicuotaPlomo=0;
        var alicuotaPlata=0;
        var tipoDeCambioComercial=0;
        var tipoDeCambioOficial=0;
        var cantidadDeSacos=0;
        var pesoBruto=0;
        var merma=1;
        var humedad=1;
        var porcentajeZinc=1;
        var porcentajePlomo=1;
        var porcentajePlata=1;
        var tipoDeMineral="COMPLEJO";
        var pesoBrutoSinMerma=0;
        var kilosNetosHumedos=0;
        var kilosNetosSecos=0;
        var kilosFinosZinc=0;
        var kilosFinosPlomo=0;
        var kilosFinosPlata=0;
        var librasFinasZinc=0;
        var librasFinasPlomo=0;
        var onzasTroyPlata=0;
        var valorOficialBrutoZinc=0;
        var valorOficialBrutoPlomo=0;
        var valorOficialBrutoPlata=0;
        var valorOficialBrutoZincBs=0;
        var valorOficialBrutoPlomoBs=0;
        var valorOficialBrutoPlataBs=0;
        var valorOficialBruto=0;
        var valorToneladaZinc=0;
        var valorToneladaPlomo=0;
        var valorToneladaPlata=0;
        var valorTonelada=0;

        var retenciones="";
        var totalRetenciones=0;

        var valorToneladaTabla=0;
        var valorToneladaTerminos=0;
        var pLMEtabla=0;
        var pLMEterminos=0;
        var vonTabla=0;
        var vonTerminos=0;
        var margen=0;

        var regaliaMinera=0;
        var valorNetoMineral=0;
        var valorNetoMineralEnBolivianos=0;
        var bonoCalidad=0;
        var bonoIncentivo=0;
        var valorDeCompra=0;
        var totalPagado = 0;
        var anticipoPorPagar = 0;
        var totalAnticiposContraEntrega = 0;
        var totalAnticiposContraFuturaEntrega = 0;
        var totalLiquidoPagable = 0;

        cotizacionQuincenalPlomo=lotesJSON[i].cotizacionQuincenalDePlomo;
        cotizacionQuincenalPlata=lotesJSON[i].cotizacionQuincenalDePlata;
        alicuotaPlomo=lotesJSON[i].alicuotaDePlomo;
        alicuotaPlata=lotesJSON[i].alicuotaDePlata;
        tipoDeCambioComercial=lotesJSON[i].tipoDeCambioComercial;
        tipoDeCambioOficial=lotesJSON[i].tipoDeCambioOficial;
        pesoBruto=lotesJSON[i].pesoBruto;
        cantidadDeSacos=transFloat(lotesJSON[i].cantidadDeSacos);

        merma = lotesJSON[i].porcentajeMermaFinal;
        humedad = lotesJSON[i].porcentajeHumedadFinal;
        porcentajePlomo = lotesJSON[i].porcentajePlomoFinal;
        porcentajePlata = lotesJSON[i].porcentajePlataFinal;

        margen = lotesJSON[i].margen;

        kilosNetosHumedos=pesoBruto-pesoBruto*merma/100;
        kilosNetosSecos=kilosNetosHumedos-kilosNetosHumedos*humedad/100;
        kilosFinosPlomo=kilosNetosSecos*porcentajePlomo/100;
        kilosFinosPlata=kilosNetosSecos*porcentajePlata/10000;
        librasFinasPlomo = kilosFinosPlomo*2.2046223;
        onzasTroyPlata = kilosFinosPlata*32.15073;
        valorOficialBrutoPlomo = librasFinasPlomo*cotizacionQuincenalPlomo;
        valorOficialBrutoPlata = onzasTroyPlata*cotizacionQuincenalPlata;
        valorOficialBrutoPlomoBs = valorOficialBrutoPlomo*tipoDeCambioOficial;
        valorOficialBrutoPlataBs = valorOficialBrutoPlata*tipoDeCambioOficial;
        valorOficialBruto = valorOficialBrutoPlomoBs+valorOficialBrutoPlataBs;

        //determinacion del valor por tonelada
        if(lotesJSON[i].modoValoracion=="TABLA"){
            valorToneladaPlomo = valorToneladaPlomoPorTabla(lotesJSON[i].recepcionId,lotesJSON[i].tablaComplejoId,lotesJSON[i].porcentajePlomoFinal);
            valorToneladaPlata = valorToneladaPlataPorTabla(lotesJSON[i].recepcionId,lotesJSON[i].tablaComplejoId,lotesJSON[i].porcentajePlataFinal);
            valorToneladaTabla = valorToneladaPlomo+valorToneladaPlata;
            valorTonelada = valorToneladaTabla+margen;
            //alert("TABLA: margen i="+i+" -> "+margen+" -> vpt="+valorToneladaTabla);
        }

        if(lotesJSON[i].modoValoracion=="TERMINOS DE CONTRATO"){
            valorToneladaTerminos = valorToneladaPorTerminos(lotesJSON[i].recepcionId,lotesJSON[i].terminosDeContratoId,0,lotesJSON[i].porcentajePlomoFinal,lotesJSON[i].porcentajePlataFinal,0);
            valorTonelada = valorToneladaTerminos+margen;
            //alert("TERMINOS: margen i="+i+" -> "+margen+" -> vpt="+valorToneladaTerminos);
        }

        regaliaMinera = (valorOficialBrutoPlomoBs*alicuotaPlomo/100 + valorOficialBrutoPlataBs*alicuotaPlata/100);
        valorNetoMineral = valorTonelada*kilosNetosSecos/1000;
        valorNetoMineralEnBolivianos = valorNetoMineral*tipoDeCambioComercial;
        bonoCalidad = 0;
        bonoIncentivo = 0;
        valorDeCompra = valorNetoMineralEnBolivianos+bonoCalidad+bonoIncentivo;

        //function calcularRetenciones(valorOficialBruto,valorOficialNeto,cantidadDeSacos,regaliaMinera,retencionesString){
        retenciones = calcularRetenciones(valorOficialBruto,valorNetoMineralEnBolivianos,cantidadDeSacos,regaliaMinera,lotesJSON[i].retenciones);
        totalRetenciones=calcularTotalRetenciones(retenciones);
        totalPagado = toFixed(valorDeCompra - totalRetenciones,2);
        anticipoPorPagar = lotesJSON[i].anticipoPorPagar;
        totalAnticiposContraEntrega = 0;
        if(anticipoPorPagar>totalPagado){
            totalAnticiposContraEntrega = totalPagado
        }else{
            totalAnticiposContraEntrega = anticipoPorPagar
        }
        totalAnticiposContraFuturaEntrega = lotesJSON[i].totalAnticiposContraFuturaEntrega;
        totalLiquidoPagable = toFixed(totalPagado-totalAnticiposContraEntrega-totalAnticiposContraFuturaEntrega,2);

        //actualizando variables de la cadena JSON
        lotesJSON[i].kilosNetosHumedos=kilosNetosHumedos;
        lotesJSON[i].kilosNetosSecos=kilosNetosSecos;
        lotesJSON[i].kilosFinosPlomo=kilosFinosPlomo;
        lotesJSON[i].kilosFinosPlata=kilosFinosPlata;
        lotesJSON[i].librasFinasDePlomo=librasFinasPlomo;
        lotesJSON[i].onzasTroyDePlata=onzasTroyPlata;
        lotesJSON[i].valorOficialBrutoDePlomo=valorOficialBrutoPlomo;
        lotesJSON[i].valorOficialBrutoDePlata=valorOficialBrutoPlata;
        lotesJSON[i].valorOficialBrutoDePlomoEnBolivianos=valorOficialBrutoPlomoBs;
        lotesJSON[i].valorOficialBrutoDePlataEnBolivianos=valorOficialBrutoPlataBs;
        lotesJSON[i].valorOficialBrutoPlomo=valorOficialBrutoPlomo;
        lotesJSON[i].valorOficialBrutoPlata=valorOficialBrutoPlata;
        lotesJSON[i].valorOficialBrutoPlomoBs=valorOficialBrutoPlomoBs;
        lotesJSON[i].valorOficialBrutoPlataBs=valorOficialBrutoPlataBs;
        lotesJSON[i].valorOficialBruto=valorOficialBruto;
        lotesJSON[i].valorToneladaPlomo=valorToneladaPlomo;
        lotesJSON[i].valorToneladaPlata=valorToneladaPlata;
        lotesJSON[i].valorPorTonelada=toFixed(valorTonelada,2);
        lotesJSON[i].valorToneladaTabla=valorToneladaTabla;
        lotesJSON[i].valorToneladaTerminos=valorToneladaTerminos;
        lotesJSON[i].pLMEtabla=pLMEtabla;
        lotesJSON[i].pLMEterminos=pLMEterminos;
        lotesJSON[i].vonTabla=vonTabla;
        lotesJSON[i].vonTerminos=vonTerminos;
        lotesJSON[i].margen=margen;
        lotesJSON[i].regaliaMinera=regaliaMinera;
        lotesJSON[i].valorNetoMineral=valorNetoMineral;
        lotesJSON[i].valorNetoMineralEnBolivianos=valorNetoMineralEnBolivianos;
        lotesJSON[i].bonoCalidad=bonoCalidad;
        lotesJSON[i].bonoIncentivo=bonoIncentivo;
        lotesJSON[i].valorDeCompra=valorDeCompra;
        lotesJSON[i].retenciones=retenciones;
        lotesJSON[i].totalPagado=totalPagado;
        lotesJSON[i].totalRetenciones=totalRetenciones;
        lotesJSON[i].anticipoPorPagar=anticipoPorPagar;
        lotesJSON[i].totalAnticiposContraEntrega=totalAnticiposContraEntrega;
        lotesJSON[i].totalAnticiposContraFuturaEntrega=totalAnticiposContraFuturaEntrega;
        lotesJSON[i].totalLiquidoPagable=totalLiquidoPagable;

        //valorPorTonelada_0
        $("#valorPorTonelada_"+i).val(valorTonelada);
        $("#totalAnticiposContraEntrega_"+i).val(totalAnticiposContraEntrega);
        $("#totalLiquidoPagable_"+i).val(totalLiquidoPagable);

        //actualizando la cadena JSON del campo lotes
        $("#lotes").val(JSON.stringify(lotesJSON));
    }

    function liquidar(boton){
        var i = posicion($(boton).attr('id'));

        if(confirm("Está seguro que desea liquidar el Lote "+lotesJSON[i].lote+"?")){
            //deshabilitar controler para evitar incoherencias en la info
            deshabilitarControles();

            lotesLiquidadosJSON.push(lotesJSON[i]);
            $.ajax({
                url: "/demo-liquidaciones/liquidacionDePlomoPlata/liquidar",
                async: false,
                dataType: 'json',
                data: {
                    lote: JSON.stringify(lotesJSON[i]),
                    millis:$("#millis").val()
                },
                success: function(data) {
//                vpt=transFloat(data.vptZn);
                    $("#lotesLiquidados").val(JSON.stringify(lotesLiquidadosJSON));
                    mostrarTablaLotesLiquidados();
                    buscarFilas();
//                    copiarLeyes();
                },
                error: function(request, status, error) {
                }
            });
        }
    }

    function imprimir(){
//        $.ajax({
//            url: "/demo-liquidaciones/liquidacionDeComplejo/crearReporteGrupal2",
//            dataType: 'json',
//            data: {
//                millis:$("#millis").val()
//            },
//            success: function(data) {
//            },
//            error: function(request, status, error) {
//            }
//        });

        $('<form>', {
            "id": "imprimir",
            "html": '<input type="hidden" id="millis" name="millis" value="' + $("#millis").val() + '" />',
            "action": '/demo-liquidaciones/liquidacionDePlomoPlata/crearReporteGrupal2'
        }).appendTo(document.body).submit();
    }

    function deshabilitarControles(){
        $("#loteInicial").prop("disabled",true);
        $("#loteFinal").prop("disabled",true);
        $("#agregar").prop("disabled",true);
        $("#quitar").prop("disabled",true);
    }

    function mostrarTablaLotesLiquidados(){
        $("#tablaLotesLiquidados").jqGrid({
            datatype: "local",
            height: 150,
            colNames: ["LOTE","%PB FINAL","DM AG FINAL","MODO VAL.","MARGEN","V.P.T.","ANTICIPOS C/E","LIQ. PAGABLE"],
            colModel:[
                {name:'lote',index:'lote', width:80},
                {name:'porcentajePlomoFinal',index:'porcentajePlomoFinal', width:80},
                {name:'porcentajePlataFinal',index:'porcentajePlataFinal', width:80},
                {name:'modoValoracion',index:'modoValoracion', width:80},
                {name:'margen',index:'margen', width:80},
                {name:'valorPorTonelada',index:'valorPorTonelada', width:80},
                {name:'totalAnticiposContraEntrega',index:'totalAnticiposContraEntrega', width:80},
                {name:'totalLiquidoPagable',index:'totalLiquidoPagable', width:80} ],
            multiselect: false,
            caption: "RETENCIONES"
        });
        $("#tablaLotesLiquidados").jqGrid("clearGridData", true);

        var sumaTotalLiquidoPagable = 0;

        for(var i=0;i<lotesLiquidadosJSON.length;i++){
            jQuery("#tablaLotesLiquidados").jqGrid('addRowData',i+1,lotesLiquidadosJSON[i]);
            sumaTotalLiquidoPagable+=lotesLiquidadosJSON[i].totalLiquidoPagable;
            console.log("lote: "+lotesLiquidadosJSON[i].lote+" totalLiquidoPagable"+lotesLiquidadosJSON[i].totalLiquidoPagable);
        }

        $("#total").val(toFixed(sumaTotalLiquidoPagable,2));
    }

    function valorToneladaPlomoPorTabla(recepcionId,tablaComplejoId,porcentajePlomo){
        var vpt=0;
        $.ajax({
            url: "/demo-liquidaciones/tablaOrigenCotizacionesComplejo/getVPTPlomo2",
            async: false,
            dataType: 'json',
            data: {
                recepcionId: recepcionId,
                porcentajePlomo: porcentajePlomo,
                tablaId: tablaComplejoId
            },
            success: function(data) {
                vpt=transFloat(data.vptPb);
            },
            error: function(request, status, error) {
            }
        });
        return vpt;
    }

    function valorToneladaPlataPorTabla(recepcionId,tablaComplejoId,porcentajePlata){
        var vpt=0;
        $.ajax({
            url: "/demo-liquidaciones/tablaOrigenCotizacionesComplejo/getVPTPlata2",
            async: false,
            dataType: 'json',
            data: {
                recepcionId: recepcionId,
                porcentajePlata: porcentajePlata,
                tablaId: tablaComplejoId
            },
            success: function(data) {
                vpt=transFloat(data.vptAg);
            },
            error: function(request, status, error) {
            }
        });
        return vpt;
    }

    function valorToneladaPorTerminos(recepcionId,terminosId,porcentajeZinc,porcentajePlomo,porcentajePlata,porcentajeCobre){
        var vpt=0;
        $.ajax({
            url: "/demo-liquidaciones/terminosDeContrato/getValorTonelada",
            async: false,
            dataType: 'json',
            data: {
                recepcionId: recepcionId,
                porcentajeZinc: porcentajeZinc,
                porcentajePlomo: porcentajePlomo,
                porcentajePlata: porcentajePlata,
                porcentajeCobre: porcentajeCobre,
                terminosId: terminosId
            },
            success: function(data) {
                vpt=transFloat(data.vpt);
            },
            error: function(request, status, error) {
            }
        });
        return vpt;
    }

    function calcularRetenciones(valorOficialBruto,valorOficialNeto,cantidadDeSacos,regaliaMinera,retencionesString){
        var retencionesJSON = jQuery.parseJSON(retencionesString);
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
        });

        return JSON.stringify(tabla);
    }

    function validar(i){
//        var data = lotesJSON;
        var valido = true;
//        for(var i=0;i<data.length;i++){
//            valido=valido&&($("#modoValoracion_"+i).val()!=0&&$("#tablaTermino_"+i).val()!=0);
//        }
        valido=valido&&($("#modoValoracion_"+i).val()!=0&&$("#tablaTermino_"+i).val()!=0);
        return valido;
    }

    function mensajeTooltip(obj){
        return "<html><label style='font-weight: bold;'>LOTE:</label>"+obj.lote+"<br>" +
            "<label style='font-weight: bold;'>CLIENTE:</label>"+obj.nombreCliente+"<br>" +
            "<label style='font-weight: bold;'>EMPRESA:</label>"+obj.nombreEmpresa+"<br>" +
            "<label style='font-weight: bold;'>PESO BRUTO:</label>"+obj.pesoBruto+"<br>" +
            "<label style='font-weight: bold;'>FECHA RECEPCION:</label>"+obj.fechaDeRecepcion+"<br></html>";
    }

    function calcularTotalRetenciones(retencionesString){
        var retencionesJSON = jQuery.parseJSON(retencionesString);
        var totalRetenciones = 0;
        $.each(retencionesJSON,function() {
            totalRetenciones+=this.MONTO;
        });
        return totalRetenciones;
    }

    function posicion(nombre){
        //formato: modoValoracion_0
        return parseInt(nombre.substring(nombre.indexOf("_")+1));
    }

    function transFloat(numeroString){
        numeroString=""+numeroString;
        var numero = numeroString.replace(',','');
        return parseFloat(numero);
    }

    function toFixed( number, precision ) {
        var multiplier = Math.pow( 10, precision );
        return Math.round( number * multiplier ) / multiplier;
    }
});
