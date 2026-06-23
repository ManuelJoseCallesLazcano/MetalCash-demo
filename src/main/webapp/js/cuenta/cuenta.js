$(document).ready(function() {
    var mydata;

    var grupo=$("#grupo");
    var rubro=$("#rubro");
    var titulo=$("#titulo");
    var compuesta=$("#compuesta");
    var subcuenta=$("#subcuenta");
    var parametrosCuenta=$("#grupo,#rubro,#titulo,#compuesta,#subcuenta");

    iniciarTablaPlanDeCuentas();
    insertarDatosTablaPlanDeCuentas();
    seleccionarGrupoCuenta();
    seleccionarParametrosCuenta();

    $('#grupoDeCuenta').bind("change", function(){
        seleccionarGrupoCuenta();
    });

    $('#nivelDeCuenta').bind("change", function(){
        seleccionarParametrosCuenta();
    });

    function iniciarTablaPlanDeCuentas(){
        $("#tablaPlanDeCuentas").jqGrid({
            datatype: "local",
            height: 200,
            colNames: ["CODIGO","NOMBRE DE CUENTA","NIVEL"],
            colModel:[
                {name:'codigoDeCuenta',index:'codigoDeCuenta', width:100},
                {name:'nombreDeCuenta',index:'nombreDeCuenta', width:400},
                {name:'nivelDeCuenta',index:'nivelDeCuenta', width:100} ],
            multiselect: false,
            caption: "PLAN DE CUENTAS",
            onSelectRow: function(id){
                cargarCuenta();
            }
        });
    }

    function insertarDatosTablaPlanDeCuentas(){
        var espacio="    ";
        $.ajax({
            url: "/demo-liquidaciones/cuenta/planDeCuentas",
            dataType: 'json',
            data: {
            },
            success: function(data) {
                $('#planDeCuentasJSON').val(data.cuentas);
                mydata = $("#planDeCuentasJSON").val();
                if(mydata=="")
                    mydata = [];
                else
                    mydata = $.parseJSON(mydata);
                $("#tablaPlanDeCuentas").jqGrid("clearGridData", true);

                for(var i=0;i<mydata.length;i++){
                    if(mydata[i].nivelDeCuenta=="RUBRO")
                        mydata[i].nombreDeCuenta=espacio+mydata[i].nombreDeCuenta;
                    if(mydata[i].nivelDeCuenta=="TITULO")
                        mydata[i].nombreDeCuenta=espacio+espacio+mydata[i].nombreDeCuenta;
                    if(mydata[i].nivelDeCuenta=="COMPUESTA")
                        mydata[i].nombreDeCuenta=espacio+espacio+espacio+mydata[i].nombreDeCuenta;
                    if(mydata[i].nivelDeCuenta=="SUBCUENTA")
                        mydata[i].nombreDeCuenta=espacio+espacio+espacio+espacio+mydata[i].nombreDeCuenta;
                    $("#tablaPlanDeCuentas").jqGrid('addRowData',i+1,mydata[i]);
                }
            },
            error: function(request, status, error) {
            }
        });
    }

    function cargarCuenta(){
        var filaId = parseInt($('#tablaPlanDeCuentas').jqGrid('getGridParam','selrow')) - 1; //restar uno porque la funcion considera el indice inicial como 1
        //
        var fila = mydata[filaId];
        $("#grupoDeCuenta").val(fila.grupoDeCuenta);
        $("#indiceGrupoDeCuenta").val(fila.indiceGrupoDeCuenta);
        $("#nivelDeCuenta").val(fila.nivelDeCuenta);
        $("#indiceNivelDeCuenta").val(fila.indiceNivelDeCuenta);
        $("#grupo").val(fila.grupo);
        $("#rubro").val(fila.rubro);
        $("#titulo").val(fila.titulo);
        $("#compuesta").val(fila.compuesta);
        $("#subcuenta").val(fila.subcuenta);

        seleccionarGrupoCuenta();
        seleccionarParametrosCuentaPorTabla();
    }

    function seleccionarGrupoCuenta(){
        var opIndex=$("select[name='grupoDeCuenta'] option:selected").index();
        $('#indiceGrupoDeCuenta').val(opIndex);
    }

    function seleccionarParametrosCuenta(){
//        alert("cambio detectado");
        var op=$('#nivelDeCuenta').val();
        var opIndex=$("select[name='nivelDeCuenta'] option:selected").index();
        $('#indiceNivelDeCuenta').val(opIndex);
        
        if(op=="GRUPO"){
            $('#_grupo').show();
            $('#_rubro').hide();
            $('#_titulo').hide();
            $('#_compuesta').hide();
            $('#_subcuenta').hide();

            $('#rubro').val(0);
            $('#titulo').val(0);
            $('#compuesta').val(0);
            $('#subcuenta').val(0);
        }
        if(op=="RUBRO"){
            $('#_grupo').show();
            $('#_rubro').show();
            $('#_titulo').hide();
            $('#_compuesta').hide();
            $('#_subcuenta').hide();

            $('#rubro').val('');
            $('#titulo').val(0);
            $('#compuesta').val(0);
            $('#subcuenta').val(0);
        }
        if(op=="TITULO"){
            $('#_grupo').show();
            $('#_rubro').show();
            $('#_titulo').show();
            $('#_compuesta').hide();
            $('#_subcuenta').hide();

            $('#rubro').val('');
            $('#titulo').val('');
            $('#compuesta').val(0);
            $('#subcuenta').val(0);
        }
        if(op=="COMPUESTA"){
            $('#_grupo').show();
            $('#_rubro').show();
            $('#_titulo').show();
            $('#_compuesta').show();
            $('#_subcuenta').hide();

            $('#rubro').val('');
            $('#titulo').val('');
            $('#compuesta').val('');
            $('#subcuenta').val(0);
        }
        if(op=="SUBCUENTA"){
            $('#_grupo').show();
            $('#_rubro').show();
            $('#_titulo').show();
            $('#_compuesta').show();
            $('#_subcuenta').show();

            $('#rubro').val('');
            $('#titulo').val('');
            $('#compuesta').val('');
            $('#subcuenta').val('');
        }
    }

    function seleccionarParametrosCuentaPorTabla(){
        var opIndex=$("select[name='nivelDeCuenta'] option:selected").index();
        opIndex++;
//        alert("opIndex:"+opIndex);
        $('#indiceNivelDeCuenta').val(opIndex);
        $("select[name='nivelDeCuenta']").prop('selectedIndex',opIndex);

        var op=$('#nivelDeCuenta').val();

        if(op=="GRUPO"){
            $('#_grupo').show();
            $('#_rubro').hide();
            $('#_titulo').hide();
            $('#_compuesta').hide();
            $('#_subcuenta').hide();

//            $('#rubro').val(0);
            $('#rubro').val('');
            $('#titulo').val(0);
            $('#compuesta').val(0);
            $('#subcuenta').val(0);
        }
        if(op=="RUBRO"){
            $('#_grupo').show();
            $('#_rubro').show();
            $('#_titulo').hide();
            $('#_compuesta').hide();
            $('#_subcuenta').hide();

            $('#rubro').val('');
//            $('#titulo').val(0);
            $('#titulo').val(0);
            $('#compuesta').val(0);
            $('#subcuenta').val(0);
        }
        if(op=="TITULO"){
            $('#_grupo').show();
            $('#_rubro').show();
            $('#_titulo').show();
            $('#_compuesta').hide();
            $('#_subcuenta').hide();

//            $('#rubro').val('');
            $('#titulo').val('');
//            $('#compuesta').val(0);
            $('#compuesta').val(0);
            $('#subcuenta').val(0);
        }
        if(op=="COMPUESTA"){
            $('#_grupo').show();
            $('#_rubro').show();
            $('#_titulo').show();
            $('#_compuesta').show();
            $('#_subcuenta').hide();

//            $('#rubro').val('');
//            $('#titulo').val('');
            $('#compuesta').val('');
//            $('#subcuenta').val(0);
            $('#subcuenta').val(0);
        }
        if(op=="SUBCUENTA"){
            $('#_grupo').show();
            $('#_rubro').show();
            $('#_titulo').show();
            $('#_compuesta').show();
            $('#_subcuenta').show();

//            $('#rubro').val('');
//            $('#titulo').val('');
//            $('#compuesta').val('');
            $('#subcuenta').val('');
        }
    }

    parametrosCuenta.bind("change", function(){
        generarCodigo();
    }).bind("keyup", function(){
        generarCodigo();
    });

    function generarCodigo(){
        var op=$('#nivelDeCuenta').val();

        if(op=="GRUPO"){
            $('#codigoDeCuenta').val(completarCeros(grupo,1));
        }
        if(op=="RUBRO"){
            $('#codigoDeCuenta').val(completarCeros(grupo,1)+completarCeros(rubro,1));
        }
        if(op=="TITULO"){
            $('#codigoDeCuenta').val(completarCeros(grupo,1)+completarCeros(rubro,1)+completarCeros(titulo,2));
        }
        if(op=="COMPUESTA"){
            $('#codigoDeCuenta').val(completarCeros(grupo,1)+completarCeros(rubro,1)+completarCeros(titulo,2)+completarCeros(compuesta,2));
        }
        if(op=="SUBCUENTA"){
            $('#codigoDeCuenta').val(completarCeros(grupo,1)+completarCeros(rubro,1)+completarCeros(titulo,2)+completarCeros(compuesta,2)+completarCeros(subcuenta,4));
        }

        $('#codigoDeCuentaControl').val(completarCeros(grupo,1)+completarCeros(rubro,1)+completarCeros(titulo,2)+completarCeros(compuesta,2)+completarCeros(subcuenta,4));
    }

    function completarCeros(numero,cantidadCeros){
        numero=parseInt(numero.val());
        if(isNaN(numero))
            numero=0;
        var numeroTexto=""+numero;
        var cantidadCerosFaltantes=cantidadCeros-numeroTexto.length;
        for(var i=0;i<cantidadCerosFaltantes;i++)
            numeroTexto="0"+numeroTexto;
        return numeroTexto;
    }
});
