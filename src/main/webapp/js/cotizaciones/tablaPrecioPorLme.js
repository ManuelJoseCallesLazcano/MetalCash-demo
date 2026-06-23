$(document).ready(function () {
    $(function() {
        $( "#radio2" ).buttonset();
    });

    $('input:radio[name="radio2"]').change(
        function(){
            if (this.checked && this.value == 'sulfuro') {
                $("#naturalezaMineral").val("SULFURO");
            }
            if (this.checked && this.value == 'oxido') {
                $("#naturalezaMineral").val("OXIDO");
            }
        }
    );

    establecerRadios();
    cotizacionDiariaPlata();

    $("#generar").bind("click",actualizarContenido);
    $("#actualizar").bind("click",actualizarFila);
    $("#eliminar").bind("click",eliminarFila);
    $("#cotizacionDiariaDeMinerales").change(function(){ cotizacionDiariaPlata(); });
    $("#leyPlata, #porcentajeLme").bind("keyup",calcularValorTonelada);

    jQuery("#list4").jqGrid({
        datatype: "local",
        height: 300,
        colNames: ["LEY Ag DM","% LME","VAL. TONELADA"],
        colModel:[ {name:'leyPlata',index:'leyPlata', width:120},
            {name:'porcentajeLme',index:'porcentajeLme', width:120},
            {name:'valorPorTonelada',index:'valorPorTonelada', width:120} ],
        multiselect: false,
        caption: "TABLA DE PRECIOS",
        onSelectRow: function(id){
            cargarFila();
        }
    });

    var mydata = $("#tablaDePrecios").val();

    if(mydata=="")
        mydata = [];
    else
        mydata = $.parseJSON(mydata);

    for(var i=0;i<=mydata.length;i++)
        jQuery("#list4").jqGrid('addRowData',i+1,mydata[i]);

    function calcularValorTonelada(){
        var leyPlata = parseFloat($("#leyPlata").val());
        var porcentajeLme = parseFloat($("#porcentajeLme").val());
        var cotizacionDiariaPlata = parseFloat($("#cotizacionDiariaPlata").val());
        var valorPorTonelada = leyPlata*cotizacionDiariaPlata*porcentajeLme/31.1035;
        $("#valorPorTonelada").val((isNaN(valorPorTonelada)) ?-1:toFixed(valorPorTonelada,2).toString());
    }

    function actualizarContenido(){
        /*leyPlata
         porcentajeLme
         valorPorTonelada*/
        var leyPlata = parseFloat($("#leyPlata").val());
        var porcentajeLme = parseFloat($("#porcentajeLme").val());
        var valorPorTonelada = parseFloat($("#valorPorTonelada").val());

        var fila = new Object();
        fila.leyPlata=leyPlata;
        fila.porcentajeLme=porcentajeLme;
        fila.valorPorTonelada=valorPorTonelada;
        mydata.push(fila);
        $("#tablaDePrecios").val(JSON.stringify(mydata));

        $("#list4").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            $("#list4").jqGrid('addRowData',i+1,mydata[i]);
        }

        limpiarCampos();
    }

    function cargarFila(){
        var filaId = parseInt($('#list4').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //
        var fila = mydata[filaId];
        $("#leyPlata").val(fila.leyPlata);
        $("#porcentajeLme").val(fila.porcentajeLme);
        $("#valorPorTonelada").val(fila.valorPorTonelada);
    }

    function actualizarFila(){
        var filaId = parseInt($('#list4').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //
        var fila = mydata[filaId];
        fila.leyPlata=$("#leyPlata").val();
        fila.porcentajeLme=$("#porcentajeLme").val();
        fila.valorPorTonelada=$("#valorPorTonelada").val();

        $("#tablaDePrecios").val(JSON.stringify(mydata));

        $("#list4").jqGrid("clearGridData", true);

        for(var i=0;i<mydata.length;i++){
            $("#list4").jqGrid('addRowData',i+1,mydata[i]);
        }

        limpiarCampos();
    }

    function eliminarFila(){
        var filaId = parseInt($('#list4').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //alert("fila: "+filaId);
        var newmydata=[];
        var pos1=0;
        var pos2=0;
        while(pos1<mydata.length){
            if(pos1!=filaId){
                newmydata[pos2]=mydata[pos1];
                pos1++;
                pos2++;
            }else
                pos1++;
        }

        mydata=[];
        for(var i=0;i<newmydata.length;i++){
            mydata[i]=newmydata[i];
        }
        $("#tablaDePrecios").val(JSON.stringify(mydata));

        $("#list4").jqGrid("clearGridData", true);

        for(i=0;i<mydata.length;i++){
            $("#list4").jqGrid('addRowData',i+1,mydata[i]);
        }
    }

    function limpiarCampos(){
//        $("#leyPlata").val("");
//        $("#porcentajeLme").val("");
//        $("#valorPorTonelada").val("");
    }

    function cotizacionDiariaPlata(){
        $.ajax({
            url:"/demo-liquidaciones/cotizacionDiariaDeMinerales/cotizacionDiariaPlataPrecioLme",
            dataType: 'json',
            data: {
                cotizacionId:$("#cotizacionDiariaDeMinerales").val()
            },
            success: function(data) {
                $("#cotizacionDiariaPlata").val(data.cotDiaPlata);
            },
            error: function(request, status, error) {
            }
        });
    }

    function establecerRadios(){
        var n = $("#naturalezaMineral").val();
        if(n=="SULFURO"){
            $('#radioSulfuro').prop('checked',true);
        }
        if(n=="OXIDO"){
            $('#radioOxido').prop('checked',true);
        }
    }

    function toFixed( number, precision ) {
        var multiplier = Math.pow( 10, precision );
        return Math.round( number * multiplier ) / multiplier;
    }

});
