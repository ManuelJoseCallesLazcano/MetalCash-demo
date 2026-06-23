$(document).ready(function () {
    jQuery("#tablaFilaOriginal").jqGrid({
        datatype: "local",
        height: 50,
        colNames: ["COT.","Ley 5%","Ley 10%","Ley 15%","Ley 20%","Ley 25%","Ley 30%","Ley 35%","Ley 40%","Ley 50%","Ley 60%","Ley 70%","Ley 75%"],
        colModel:[ {name:'COT',index:'COT', width:65},
            {name:'L5',index:'L5', width:65},
            {name:'L10',index:'L10', width:65},
            {name:'L15',index:'L15', width:65},
            {name:'L20',index:'L20', width:65},
            {name:'L25',index:'L25', width:65},
            {name:'L30',index:'L30', width:65},
            {name:'L35',index:'L35', width:65},
            {name:'L40',index:'L40', width:65},
            {name:'L50',index:'L50', width:65},
            {name:'L60',index:'L60', width:65},
            {name:'L70',index:'L70', width:65},
            {name:'L75',index:'L75', width:65} ],
        multiselect: false,
        caption: "FILA ORIGINAL"
    });

    jQuery("#tablaFilaAjustada").jqGrid({
        datatype: "local",
        height: 50,
        colNames: ["COT.","Ley 5%","Ley 10%","Ley 15%","Ley 20%","Ley 25%","Ley 30%","Ley 35%","Ley 40%","Ley 50%","Ley 60%","Ley 70%","Ley 75%"],
        colModel:[ {name:'COT',index:'COT', width:65},
            {name:'L5',index:'L5', width:65},
            {name:'L10',index:'L10', width:65},
            {name:'L15',index:'L15', width:65},
            {name:'L20',index:'L20', width:65},
            {name:'L25',index:'L25', width:65},
            {name:'L30',index:'L30', width:65},
            {name:'L35',index:'L35', width:65},
            {name:'L40',index:'L40', width:65},
            {name:'L50',index:'L50', width:65},
            {name:'L60',index:'L60', width:65},
            {name:'L70',index:'L70', width:65},
            {name:'L75',index:'L75', width:65} ],
        multiselect: false,
        caption: "FILA AJUSTADA"
    });

    crearTabla($("#filaOriginal"),$("#tablaFilaOriginal"));
    crearTabla($("#filaAjustada"),$("#tablaFilaAjustada"));
    
    function crearTabla(datos,tabla){
        var mydata = datos.val();
        
        if(mydata=="")
            mydata = [];
        else
            mydata = $.parseJSON(mydata);

        var fila = new Array();
        fila.push(mydata)
        tabla.jqGrid("clearGridData", true);
        for(var i=0;i<=fila.length;i++)
            tabla.jqGrid('addRowData',i+1,fila[i]);
    }
});
