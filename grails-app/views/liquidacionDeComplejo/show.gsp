<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidación de Complejo</title>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'styleScrolling.css')}" type="text/css" >
    <g:javascript src="jquery-1.10.1.min.js" />
    <g:javascript src="i18n/grid.locale-es.js" />
    <g:javascript src="jquery.jqGrid.min.js" />
    <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
    <g:javascript src="jquery.jgrowl.min.js" />
    <g:javascript src="scrolling.js" />
    <script>
        $(document).ready(function() {
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
            if(mydata=="") mydata = [];
            else mydata = $.parseJSON(mydata);
            for(var i=0;i<=mydata.length;i++)
                jQuery("#tablaRetenciones").jqGrid('addRowData',i+1,mydata[i]);

            var liquidoPagable = transFloat($("#liquidoPagable").val());
            if(liquidoPagable<0)
                $.jGrowl("Debido a que el Liquido Pagable es negativo se ha creado un Anticipo Contra Futura Entrega. El enlace al formulario esta al final de la pagina.",
                        {sticky: true, header: 'ATENCION'});

            function transFloat(numeroString){
                var numero = numeroString.replace(',','');
                return parseFloat(numero);
            }
        });
    </script>
</head>
<body>
<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Liquidación de Complejo</h3>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1">
            <i class="fas fa-list mr-1"></i>Lista
        </g:link>
        <g:link action="create" class="btn btn-primary btn-sm">
            <i class="fas fa-plus mr-1"></i>Nueva
        </g:link>
    </div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div class="alert alert-info alert-dismissible">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                ${flash.message}
            </div>
        </g:if>

        <dl class="row">
            <g:if test="${liquidacionDeComplejoInstance?.numeroLiquidacionComplejo}">
                <dt class="col-sm-3">N° Liquidación</dt>
                <dd class="col-sm-9">${liquidacionDeComplejoInstance?.numeroLiquidacionComplejo?.encodeAsHTML()}</dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.recepcionDeComplejo}">
                <dt class="col-sm-3">Lote</dt>
                <dd class="col-sm-9">
                    <g:link controller="recepcionDeComplejo" action="show" id="${liquidacionDeComplejoInstance?.recepcionDeComplejo?.id}">
                        ${liquidacionDeComplejoInstance?.recepcionDeComplejo?.encodeAsHTML()}
                    </g:link>
                </dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.fechaDeLiquidacion}">
                <dt class="col-sm-3">Fecha de Liquidación</dt>
                <dd class="col-sm-9"><g:formatDate date="${liquidacionDeComplejoInstance?.fechaDeLiquidacion}" format="dd/MM/yyyy"/></dd>
            </g:if>
        </dl>

        <h5 class="mt-3 mb-2 font-weight-bold border-bottom pb-1">Datos de la Recepción</h5>
        <dl class="row">
            <g:if test="${liquidacionDeComplejoInstance?.nombreCliente}">
                <dt class="col-sm-3">Cliente</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="nombreCliente"/></dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.nombreEmpresa}">
                <dt class="col-sm-3">Empresa</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="nombreEmpresa"/></dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.fechaDeRecepcion}">
                <dt class="col-sm-3">Fecha de Recepción</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="fechaDeRecepcion"/></dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.cantidadDeSacos}">
                <dt class="col-sm-3">Cantidad de Sacos</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="cantidadDeSacos"/></dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.pesoBruto}">
                <dt class="col-sm-3">Peso Bruto</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="pesoBruto"/></dd>
            </g:if>
        </dl>

        <h5 class="mt-3 mb-2 font-weight-bold border-bottom pb-1">Cotizaciones durante la Recepción</h5>
        <div class="table-responsive"><table class="table table-bordered table-sm mb-3" style="max-width:700px;">
            <thead class="thead-dark">
            <tr>
                <th>Concepto</th>
                <th>Cot. Diaria</th>
                <th>Cot. Quincenal</th>
                <th>Alícuota</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><strong>Zinc</strong></td>
                <td>${liquidacionDeComplejoInstance.cotizacionDiariaDeZinc}</td>
                <td>${liquidacionDeComplejoInstance.cotizacionQuincenalDeZinc}</td>
                <td>${liquidacionDeComplejoInstance.alicuotaDeZinc}</td>
            </tr>
            <tr>
                <td><strong>Plomo</strong></td>
                <td>${liquidacionDeComplejoInstance.cotizacionDiariaDePlomo}</td>
                <td>${liquidacionDeComplejoInstance.cotizacionQuincenalDePlomo}</td>
                <td>${liquidacionDeComplejoInstance.alicuotaDePlomo}</td>
            </tr>
            <tr>
                <td><strong>Plata</strong></td>
                <td>${liquidacionDeComplejoInstance.cotizacionDiariaDePlata}</td>
                <td>${liquidacionDeComplejoInstance.cotizacionQuincenalDePlata}</td>
                <td>${liquidacionDeComplejoInstance.alicuotaDePlata}</td>
            </tr>
            <tr>
                <td><strong>T/C Oficial</strong></td>
                <td colspan="3">${liquidacionDeComplejoInstance.tipoDeCambioOficial}</td>
            </tr>
            <tr>
                <td><strong>T/C Comercial</strong></td>
                <td colspan="3">${liquidacionDeComplejoInstance.tipoDeCambioComercial}</td>
            </tr>
            </tbody>
        </table>

        </div>
<h5 class="mt-3 mb-2 font-weight-bold border-bottom pb-1">Detalle de Leyes</h5>
        <div class="table-responsive"><table class="table table-bordered table-sm mb-3" style="max-width:600px;">
            <thead class="thead-dark">
            <tr>
                <th>Elemento</th>
                <th>Ley Empresa</th>
                <th>Ley Cliente</th>
                <th>Ley Final</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Merma</td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajeMermaPromexbol"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajeMermaCliente"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajeMermaFinal"/></td>
            </tr>
            <tr>
                <td>Zinc</td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajeZincPromexbol"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajeZincCliente"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajeZincFinal"/></td>
            </tr>
            <tr>
                <td>Plomo</td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajePlomoPromexbol"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajePlomoCliente"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajePlomoFinal"/></td>
            </tr>
            <tr>
                <td>Plata</td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajePlataPromexbol"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajePlataCliente"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajePlataFinal"/></td>
            </tr>
            <tr>
                <td>Humedad</td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajeHumedadPromexbol"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajeHumedadCliente"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="porcentajeHumedadFinal"/></td>
            </tr>
            </tbody>
        </table>

        </div>
<h5 class="mt-3 mb-2 font-weight-bold border-bottom pb-1">Valoración del Lote</h5>
        <dl class="row">
            <g:if test="${liquidacionDeComplejoInstance?.valorOficialBruto}">
                <dt class="col-sm-3">Valor Oficial Bruto</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="valorOficialBruto"/></dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.regaliaMinera}">
                <dt class="col-sm-3">Regalía Minera</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="regaliaMinera"/></dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.valorPorTonelada}">
                <dt class="col-sm-3">Valor por Tonelada</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="valorPorTonelada"/></dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.valorNetoMineral}">
                <dt class="col-sm-3">Valor Neto Mineral</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="valorNetoMineral"/></dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.valorNetoMineralEnBolivianos}">
                <dt class="col-sm-3">Valor Neto Mineral Bs</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="valorNetoMineralEnBolivianos"/></dd>
            </g:if>
        </dl>

        <h5 class="mt-3 mb-2 font-weight-bold border-bottom pb-1">Pesos y Valores Brutos Parciales</h5>
        <g:hiddenField name="kilosNetosHumedos" value="${liquidacionDeComplejoInstance?.pesoBruto}" />
        <div class="table-responsive"><table class="table table-bordered table-sm mb-3" style="max-width:700px;">
            <thead class="thead-dark">
            <tr>
                <th></th>
                <th class="text-center">Zinc</th>
                <th class="text-center">Plomo</th>
                <th class="text-center">Plata</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><strong>K.N.S.</strong></td>
                <td colspan="3"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="kilosNetosSecos"/></td>
            </tr>
            <tr>
                <td><strong>Kilos Finos</strong></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="kilosFinosZinc"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="kilosFinosPlomo"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="kilosFinosPlata"/></td>
            </tr>
            <tr>
                <td><strong>Libras/Onzas Finas</strong></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="librasFinasDeZinc"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="librasFinasDePlomo"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="onzasTroyDePlata"/></td>
            </tr>
            <tr>
                <td><strong>Val. Bruto $us</strong></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="valorOficialBrutoDeZinc"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="valorOficialBrutoDePlomo"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="valorOficialBrutoDePlata"/></td>
            </tr>
            <tr>
                <td><strong>Val. Bruto Bs</strong></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="valorOficialBrutoDeZincEnBolivianos"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="valorOficialBrutoDePlomoEnBolivianos"/></td>
                <td><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="valorOficialBrutoDePlataEnBolivianos"/></td>
            </tr>
            </tbody>
        </table>

        </div>
<h5 class="mt-3 mb-2 font-weight-bold border-bottom pb-1">Retenciones</h5>
        <g:hiddenField name="retenciones" value="${liquidacionDeComplejoInstance?.retenciones}"/>
        <div class="mb-3"><table id="tablaRetenciones"></table></div>

        <dl class="row">
            <g:if test="${liquidacionDeComplejoInstance?.totalRetenciones}">
                <dt class="col-sm-3">Total Retenciones</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="totalRetenciones"/></dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.totalPagado}">
                <dt class="col-sm-3">Total Pagado</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="totalPagado"/></dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.totalAnticiposContraEntrega}">
                <dt class="col-sm-3">Anticipos Contra Entrega</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="totalAnticiposContraEntrega"/></dd>
            </g:if>
            <g:if test="${liquidacionDeComplejoInstance?.totalAnticiposContraFuturaEntrega}">
                <dt class="col-sm-3">Anticipos Contra Futura Entrega</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="totalAnticiposContraFuturaEntrega"/></dd>
            </g:if>
        </dl>

        <g:if test="${liquidacionDeComplejoInstance?.aplicarCostoTratamiento || liquidacionDeComplejoInstance?.costoTratamiento}">
            <h5 class="mt-3 mb-2 font-weight-bold border-bottom pb-1">Costo de Tratamiento</h5>
            <dl class="row">
                <g:if test="${liquidacionDeComplejoInstance?.aplicarCostoTratamiento}">
                    <dt class="col-sm-3">Aplicar Costo Tratamiento</dt>
                    <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="aplicarCostoTratamiento"/></dd>
                </g:if>
                <g:if test="${liquidacionDeComplejoInstance?.costoTratamiento}">
                    <dt class="col-sm-3">Costo Tratamiento</dt>
                    <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="costoTratamiento"/></dd>
                </g:if>
                <g:if test="${liquidacionDeComplejoInstance?.pesoBrozaInicial}">
                    <dt class="col-sm-3">Peso Broza Inicial</dt>
                    <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="pesoBrozaInicial"/></dd>
                </g:if>
                <g:if test="${liquidacionDeComplejoInstance?.costoTratamientoTotal}">
                    <dt class="col-sm-3">Costo Tratamiento Total</dt>
                    <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="costoTratamientoTotal"/></dd>
                </g:if>
            </dl>
        </g:if>

        <h5 class="mt-3 mb-2 font-weight-bold border-bottom pb-1">Líquido Pagable</h5>
        <div class="alert alert-success py-2">
            <strong><g:message code="liquidacionDeComplejo.totalLiquidoPagable.label" default="Total Líquido Pagable"/>:</strong>
            <span class="h5 ml-2"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="totalLiquidoPagable"/></span>
        </div>

        <g:if test="${liquidacionDeComplejoInstance?.nombreComposito}">
            <dl class="row">
                <dt class="col-sm-3">Nombre Compósito</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="nombreComposito"/></dd>
            </dl>
        </g:if>

        <g:if test="${liquidacionDeComplejoInstance?.observaciones}">
            <dl class="row">
                <dt class="col-sm-3">Observaciones</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="observaciones"/></dd>
            </dl>
        </g:if>

        <g:if test="${liquidacionDeComplejoInstance?.motivoDeModificacion}">
            <dl class="row">
                <dt class="col-sm-3">Motivo de Modificación</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${liquidacionDeComplejoInstance}" field="motivoDeModificacion"/></dd>
            </dl>
        </g:if>

        <g:hiddenField name="liquidoPagable" value="${liquidacionDeComplejoInstance?.totalLiquidoPagable}" />
        <g:if test="${liquidacionDeComplejoInstance?.totalLiquidoPagable < 0}">
            <div class="alert alert-danger mt-2">
                <strong>Anticipo Contra Futura Entrega Generado:</strong>
                <g:link controller="anticipoContraFuturaEntrega" action="show"
                    id="${org.socymet.anticipos.AnticipoContraFuturaEntrega.findByLiquidacionId(liquidacionDeComplejoInstance.id).id}"
                    target="_blank" class="alert-link ml-2">Ver Anticipo Contra Futura Entrega</g:link>
            </div>
        </g:if>

        <div style="display:none;" class="nav_up" id="nav_up"></div>
        <div style="display:none;" class="nav_down" id="nav_down"></div>
    </div>
    <div class="card-footer">
        <g:form>
            <g:hiddenField name="id" value="${liquidacionDeComplejoInstance?.id}" />
            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <g:link action="edit" id="${liquidacionDeComplejoInstance?.id}" class="btn btn-warning btn-sm">
                    <i class="fas fa-edit mr-1"></i>Editar
                </g:link>
                <g:actionSubmit action="delete" class="btn btn-danger btn-sm"
                    value="Eliminar"
                    onclick="return confirm('¿Está seguro de eliminar este registro?');"/>
            </sec:ifAnyGranted>
        </g:form>
        <g:jasperReport controller="liquidacionDeComplejo" action="crearReporte" jasper="liquidacion_complejo" format="PDF,RTF" name="ReporteLiquidacion${liquidacionDeComplejoInstance.lote}">
            <input type="hidden" name="LIQ_SN_ID" value="${liquidacionDeComplejoInstance.id}" />
        </g:jasperReport>
    </div>
</div>
</body>
</html>
