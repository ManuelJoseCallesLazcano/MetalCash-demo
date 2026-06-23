// $(document).ready(function() {
//         $('#tieneDocumentos').prop("checked",$("#documentacionCompleta").val()=="1");
//
//         desplegarLote();
//         siguienteLote();
//         tieneDocumentacion();
//
//         $("#tipoDeMineral").bind("change",desplegarLote);
//
//         $('#tieneDocumentos').change(function() {
//             tieneDocumentacion();
//         });
//
//         $("#deposito,#tipoDeMineral").change(function() {
//             siguienteLote();
//         });
//
//         $("#costoLaboratorio1, #costoLaboratorio2, #costoLaboratorio3, #costoLaboratorio4").bind("keyup",calcularTotalCostoLaboratorio);
//
//         function tieneDocumentacion(){
//             if ($('#tieneDocumentos').is(":checked")){
//                 $("#documentacionCompleta").val(1);
//                 $("#numeroDeDocumento").val("0").prop("readonly",false);
//             }else{
//                 $("#documentacionCompleta").val(0);
//                 $("#numeroDeDocumento").val("?").prop("readonly",true);
//             }
//         }
//     }
// );
//
// function siguienteLote(){
//     var t = $("#tipoDeMineral").val();
//     var d = $("#deposito").val();
//
//     $.ajax({
//         url:"/demo-liquidaciones/recepcionDeOro/numeroDeLote",
//         dataType: 'json',
//         data: {
//             tipoDeMineral:t,
//             depositoId:d
//         },
//         success: function(data) {
//             $.notify("DEPOSITO: "+$("#deposito option:selected").text()+"\nLOTE DE TURNO: "+data.lote,{autoHide: false,clickToHide: true,className: "info",position: "top left"});
//         },
//         error: function(request, status, error) {
//
//         }
//     });
// }
//
// function desplegarLote(){
//     var e = $("#tipoDeMineral").val();
//     if(e=="COMPLEJO"){
//         $("#_loteOro").show();
//         $("#_lotePlomoPlata").hide();
//         $("#_loteZincPlata").hide();
//         $("#_loteCobrePlata").hide();
//     }
//     if(e=="PB-AG"){
//         $("#_loteOro").hide();
//         $("#_lotePlomoPlata").show();
//         $("#_loteZincPlata").hide();
//         $("#_loteCobrePlata").hide();
//     }
//     if(e=="ZN-AG"){
//         $("#_loteOro").hide();
//         $("#_lotePlomoPlata").hide();
//         $("#_loteZincPlata").show();
//         $("#_loteCobrePlata").hide();
//     }
//     if(e=="CU-AG"){
//         $("#_loteOro").hide();
//         $("#_lotePlomoPlata").hide();
//         $("#_loteZincPlata").hide();
//         $("#_loteCobrePlata").show();
//     }
// }
//
// function calcularTotalCostoLaboratorio(){
//     var costoLaboratorio1 = parseFloat($("#costoLaboratorio1").val());
//     costoLaboratorio1 = isNaN(costoLaboratorio1)?0:costoLaboratorio1;
//     var costoLaboratorio2 = parseFloat($("#costoLaboratorio2").val());
//     costoLaboratorio2 = isNaN(costoLaboratorio2)?0:costoLaboratorio2;
//     var costoLaboratorio3 = parseFloat($("#costoLaboratorio3").val());
//     costoLaboratorio3 = isNaN(costoLaboratorio3)?0:costoLaboratorio3;
//     var costoLaboratorio4 = parseFloat($("#costoLaboratorio4").val());
//     costoLaboratorio4 = isNaN(costoLaboratorio4)?0:costoLaboratorio4;
//     //calculos
//     var totalCostoLaboratorio = costoLaboratorio1+costoLaboratorio2+costoLaboratorio3+costoLaboratorio4;
//     $("#totalCostoLaboratorio").val((isNaN(totalCostoLaboratorio)) ?"?":toFixed(totalCostoLaboratorio,2).toString());
// }
//
// function toFixed( number, precision ) {
//     var multiplier = Math.pow( 10, precision );
//     return Math.round( number * multiplier ) / multiplier;
// }
