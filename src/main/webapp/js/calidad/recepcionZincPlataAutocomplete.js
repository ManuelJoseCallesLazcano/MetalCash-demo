$(document).ready(function() {
    var opcionesValue = new Array();
    var opcionesText = new Array();
    var opcionesTerminosValue = new Array();
    var opcionesTerminosText = new Array();

    respaldarOpciones();
    desplegarCondiciones();

    if($('#tablasIds').val()!=""){ // vista edit
        var s = $('#tablaComplejo').val();
        var t = $('#terminosDeContrato').val();
        simplificarOpciones(""+$('#tablasIds').val(),""+$('#terminosIds').val());
        $('#tablaComplejo').val(s);
        $('#terminosDeContrato').val(t);
    }

    $("#lote").autocomplete({
        source: function(request, response){
            $.ajax({
                url: "/demo-liquidaciones/recepcionDeComplejo/recepcionesCalidadZincPlataJSON", // /demo-liquidaciones/recepcionDeComplejo/create
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
            //recepcionDeComplejo.id
            //$('#liquidacionId').val(ui.item.liquidacionId),
            $('#recepcionDeComplejo\\.id').val(ui.item.recepcionId),
            $('#nombreCliente').val(ui.item.nombreCliente),
            $('#empresa\\.id').val(ui.item.empresaId);
            $('#nombreEmpresa').val(ui.item.nombreEmpresa);
            $('#fechaDeRecepcion').val(ui.item.fechaDeRecepcion);
            $('#cantidadDeSacos').val(ui.item.cantidadDeSacos);
            $('#pesoBruto').val(ui.item.pesoBruto);
            $('#estadoDelLote').val(ui.item.estadoDelLote);
            $('#condicionDeEntrega').val(ui.item.condicionDeEntrega);
            $('#tablasIds').val(ui.item.tablasIds);
            $('#terminosIds').val(ui.item.terminosIds);
            simplificarOpciones(""+ui.item.tablasIds,""+ui.item.terminosIds);
        },
        response: function(event, ui) {
            if (ui.content.length === 0) {//verificar si existe alguna respuesta, sino desplegar un mensaje
                $.jGrowl("El Lote no existe o ya fue liquidado.");
            }
        }
    });

    //copiar el contenido de ley promexbol a cliente y final
    $("#porcentajeHumedadPromexbol").keyup(function(){
        $("#porcentajeHumedadCliente,#porcentajeHumedadFinal").val($("#porcentajeHumedadPromexbol").val());
    });
    
    $("#porcentajeMermaPromexbol").keyup(function(){
        $("#porcentajeMermaCliente,#porcentajeMermaFinal").val($("#porcentajeMermaPromexbol").val());
    });
    
    $("#porcentajeZincPromexbol").keyup(function(){
        $("#porcentajeZincCliente,#porcentajeZincFinal").val($("#porcentajeZincPromexbol").val());
    });

    $("#porcentajePlomoPromexbol").keyup(function(){
        $("#porcentajePlomoCliente,#porcentajePlomoFinal").val($("#porcentajePlomoPromexbol").val());
    });

    $("#porcentajePlataPromexbol").keyup(function(){
        $("#porcentajePlataCliente,#porcentajePlataFinal").val($("#porcentajePlataPromexbol").val());
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
    $("#porcentajeZincCliente").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajeZincPromexbol").val());
        var leyCliente = parseFloat($("#porcentajeZincCliente").val());
        var diferencia = leyCliente-leyPromexbol
        if(diferencia>3){//la ley del cliente no puede ser mayor en 3 puntos a la de promexbol
            $.jGrowl("La ley del cliente no puede ser mayor en 3 puntos a la de EMPRESA.", { sticky: true });
            $("#porcentajeZincFinal").val(0);
        }else{
            var leyFinal = (leyPromexbol+leyCliente)/2;
            $("#porcentajeZincFinal").val(leyFinal);
        }        
    });
    $("#porcentajePlomoCliente").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajePlomoPromexbol").val());
        var leyCliente = parseFloat($("#porcentajePlomoCliente").val());
        var diferencia = leyCliente-leyPromexbol
        if(diferencia>3){//la ley del cliente no puede ser mayor en 3 puntos a la de promexbol
            $.jGrowl("La ley del cliente no puede ser mayor en 3 puntos a la de EMPRESA.", { sticky: true });
            $("#porcentajePlomoFinal").val(0);
        }else{
            var leyFinal = (leyPromexbol+leyCliente)/2;
            $("#porcentajePlomoFinal").val(leyFinal);
        }
    });
    $("#porcentajePlataCliente").keyup(function(){
        var leyPromexbol = parseFloat($("#porcentajePlataPromexbol").val());
        var leyCliente = parseFloat($("#porcentajePlataCliente").val());
        var diferencia = leyCliente-leyPromexbol
        if(diferencia>3){//la ley del cliente no puede ser mayor en 3 puntos a la de promexbol
            $.jGrowl("La ley del cliente no puede ser mayor en 3 puntos a la de EMPRESA.", { sticky: true });
            $("#porcentajePlataFinal").val(0);
        }else{
            var leyFinal = (leyPromexbol+leyCliente)/2;
            $("#porcentajePlataFinal").val(leyFinal);
        }
    });

    $("#modoValoracion" ).change(function() {
        desplegarCondiciones();
    });

    function simplificarOpciones(tablasIds, terminosIds){
        var ids = tablasIds.split('-');
        copiarOpciones();

        $('#tablaComplejo option[value]').each(function() {
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
            $("#_terminosDeContrato").hide();
        }else{
            $("#_tablaComplejo").hide();
            $("#_terminosDeContrato").show();
        }
    }
});



