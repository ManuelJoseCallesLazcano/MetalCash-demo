
<%@ page import="org.socymet.liquidacion.LiquidacionDeAntimonio" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
    <g:javascript src="jquery-1.10.1.min.js" />
    <g:javascript src="i18n/grid.locale-es.js" />
    <g:javascript src="jquery.jqGrid.min.js" />
    <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
    <g:javascript src="liquidacionDeAntimonio/calculosLiquidacion.js" />
    <g:javascript src="jquery.jgrowl.min.js" />
    <script>
        $(document).ready(function() {
            var liquidoPagable = transFloat($("#liquidoPagable").val());
            if(liquidoPagable<0)
                $.jGrowl("Debido a que el Liquido Pagable es negativo se ha creado un Anticipo Contra Futura Entrega. El enlace al formulario esta al final de la pagina.",
                        {sticky: true, header: 'ATENCION'});
        });
    </script>
</head>
<body>
<a href="#show-liquidacionDeAntimonio" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-liquidacionDeAntimonio" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list liquidacionDeAntimonio">
<g:if test="${liquidacionDeAntimonioInstance?.numeroLiquidacionAntimonio}">
    <li class="fieldcontain">
        <span id="numeroLiquidacionAntimonio-label" class="property-label"><g:message code="liquidacionDeAntimonio.numeroLiquidacionAntimonio.label" default="No. Liquidacion" /></span>

        <span class="property-value" aria-labelledby="numeroLiquidacionAntimonio-label"><g:link controller="liquidacionDeAntimonio" action="show" id="${liquidacionDeAntimonioInstance?.id}">${liquidacionDeAntimonioInstance?.numeroLiquidacionAntimonio?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.recepcionDeAntimonio}">
    <li class="fieldcontain">
        <span id="recepcionDeAntimonio-label" class="property-label"><g:message code="liquidacionDeAntimonio.recepcionDeAntimonio.label" default="Lote" /></span>

        <span class="property-value" aria-labelledby="recepcionDeAntimonio-label"><g:link controller="recepcionDeAntimonio" action="show" id="${liquidacionDeAntimonioInstance?.recepcionDeAntimonio?.id}">${liquidacionDeAntimonioInstance?.recepcionDeAntimonio?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<g:if test="${liquidacionDeAntimonioInstance?.nombreCliente}">
    <li class="fieldcontain">
        <span id="nombreCliente-label" class="property-label"><g:message code="liquidacionDeAntimonio.nombreCliente.label" default="Nombre Cliente" /></span>

        <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="nombreCliente"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.nombreEmpresa}">
    <li class="fieldcontain">
        <span id="nombreEmpresa-label" class="property-label"><g:message code="liquidacionDeAntimonio.nombreEmpresa.label" default="Nombre Empresa" /></span>

        <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="nombreEmpresa"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.fechaDeRecepcion}">
    <li class="fieldcontain">
        <span id="fechaDeRecepcion-label" class="property-label"><g:message code="liquidacionDeAntimonio.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

        <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="fechaDeRecepcion"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.cantidadDeSacos}">
    <li class="fieldcontain">
        <span id="cantidadDeSacos-label" class="property-label"><g:message code="liquidacionDeAntimonio.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

        <span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="cantidadDeSacos"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.tara}">
    <li class="fieldcontain">
        <span id="tara-label" class="property-label"><g:message code="liquidacionDeAntimonio.tara.label" default="Tara" /></span>

        <span class="property-value" aria-labelledby="tara-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="tara"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.estadoDelLote}">
    <li class="fieldcontain">
        <span id="estadoDelLote-label" class="property-label"><g:message code="liquidacionDeAntimonio.estadoDelLote.label" default="Estado Del Lote" /></span>
        <span class="property-value" aria-labelledby="estadoDelLote-label">${liquidacionDeAntimonioInstance.recepcionDeAntimonio.estadoDelLote}</span>
    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.pesoBruto}">
    <li class="fieldcontain">
        <span id="pesoBruto-label" class="property-label"><g:message code="liquidacionDeAntimonio.pesoBruto.label" default="Peso Bruto" /></span>

        <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="pesoBruto"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'cotizacionDiariaDeAntimonio', 'error')} required">
            <label for="cotizacionDiariaDeAntimonio" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.cotizacionDiariaDeAntimonio.label" default="Cot. Dia Antimonio" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.cotizacionDiariaDeAntimonio}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'cotizacionQuincenalDeAntimonio', 'error')} required">
            <label for="cotizacionQuincenalDeAntimonio" style="width: 60%">
                <g:message code="liquidacionDeAntimonio.cotizacionQuincenalDeAntimonio.label" default="Cot. Quinc. Antimonio" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.cotizacionQuincenalDeAntimonio}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'alicuotaDeAntimonio', 'error')} required">
            <label for="alicuotaDeAntimonio" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.alicuotaDeAntimonio.label" default="Alicuota Antimonio" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.alicuotaDeAntimonio}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.tipoDeCambioOficial}
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 60%">
                <g:message code="liquidacionDeAntimonio.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.tipoDeCambioComercial}
        </td>
        <td>
            &nbsp;</td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<g:if test="${liquidacionDeAntimonioInstance?.fechaDeLiquidacion}">
    <li class="fieldcontain">
        <span id="fechaDeLiquidacion-label" class="property-label"><g:message code="liquidacionDeAntimonio.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

        <span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:formatDate date="${liquidacionDeAntimonioInstance?.fechaDeLiquidacion}" format="dd/MM/yyyy"/></span>

    </li>
</g:if>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'kilosNetosHumedos', 'error')} required">
            <label for="kilosNetosHumedos" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.kilosNetosHumedos.label" default="K. N. H." />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.kilosNetosHumedos}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'humedad', 'error')} required">
            <label for="humedad" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.humedad.label" default="% H2O" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.humedad}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.kilosNetosSecos}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'porcentajeAntimonio', 'error')} required">
            <label for="porcentajeAntimonio" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.porcentajeAntimonio.label" default="% de Sn" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.porcentajeAntimonio}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'kilosFinosAntimonio', 'error')} required">
            <label for="kilosFinosAntimonio" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.kilosFinosAntimonio.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.kilosFinosAntimonio}
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'librasFinasDeAntimonio', 'error')} required">
            <label for="librasFinasDeAntimonio" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.librasFinasDeAntimonio.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.librasFinasDeAntimonio}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'porcentajePlomo', 'error')} required">
            <label for="porcentajePlomo" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.porcentajePlomo.label" default="% de Pb" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.porcentajePlomo}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'porcentajeArsenico', 'error')} required">
            <label for="porcentajeArsenico" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.porcentajeArsenico.label" default="% de As" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeAntimonioInstance.porcentajeArsenico}
        </td>
    </tr>
    </tbody>
</table>

<g:if test="${liquidacionDeAntimonioInstance?.valorOficialBruto}">
    <li class="fieldcontain">
        <span id="valorOficialBruto-label" class="property-label"><g:message code="liquidacionDeAntimonio.valorOficialBruto.label" default="Valor Oficial Bruto" /></span>

        <span class="property-value" aria-labelledby="valorOficialBruto-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="valorOficialBruto"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold; font-size: 12px">Parametros de Proteccion</h1>

<g:if test="${liquidacionDeAntimonioInstance?.valorPorToneladaManual}">
    <li class="fieldcontain">
        <span id="valorPorToneladaManual-label" class="property-label"><g:message code="liquidacionDeAntimonio.valorPorToneladaManual.label" default="Valor Por Tonelada Manual" /></span>

        <span class="property-value" aria-labelledby="valorPorToneladaManual-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="valorPorToneladaManual"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.puntoDeBajada}">
    <li class="fieldcontain">
        <span id="puntoDeBajada-label" class="property-label"><g:message code="liquidacionDeAntimonio.puntoDeBajada.label" default="Punto De Bajada" /></span>

        <span class="property-value" aria-labelledby="puntoDeBajada-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="puntoDeBajada"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold; font-size: 12px">Valoracion Final del Lote</h1>

<g:if test="${liquidacionDeAntimonioInstance?.tablaCotizacionAntimonio}">
    <li class="fieldcontain">
        <span id="tablaCotizacionAntimonio-label" class="property-label"><g:message code="liquidacionDeAntimonio.tablaCotizacionAntimonio.label" default="Tabla Cotizacion Antimonio" /></span>

        <span class="property-value" aria-labelledby="tablaCotizacionAntimonio-label"><g:link controller="tablaCotizacionAntimonio" action="show" id="${liquidacionDeAntimonioInstance?.tablaCotizacionAntimonio?.id}">${liquidacionDeAntimonioInstance?.tablaCotizacionAntimonio?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.valorPorTonelada}">
    <li class="fieldcontain">
        <span id="valorPorTonelada-label" class="property-label"><g:message code="liquidacionDeAntimonio.valorPorTonelada.label" default="Valor Por Tonelada" /></span>

        <span class="property-value" aria-labelledby="valorPorTonelada-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="valorPorTonelada"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.valorNetoMineral}">
    <li class="fieldcontain">
        <span id="valorNetoMineral-label" class="property-label"><g:message code="liquidacionDeAntimonio.valorNetoMineral.label" default="Valor Neto Mineral" /></span>

        <span class="property-value" aria-labelledby="valorNetoMineral-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="valorNetoMineral"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.valorNetoMineralEnBolivianos}">
    <li class="fieldcontain">
        <span id="valorNetoMineralEnBolivianos-label" class="property-label"><g:message code="liquidacionDeAntimonio.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" /></span>

        <span class="property-value" aria-labelledby="valorNetoMineralEnBolivianos-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="valorNetoMineralEnBolivianos"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.bonoCalidad}">
    <li class="fieldcontain">
        <span id="bonoCalidad-label" class="property-label"><g:message code="liquidacionDeAntimonio.bonoCalidad.label" default="Bono Calidad" /></span>

        <span class="property-value" aria-labelledby="bonoCalidad-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="bonoCalidad"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.bonoIncentivo}">
    <li class="fieldcontain">
        <span id="bonoIncentivo-label" class="property-label"><g:message code="liquidacionDeAntimonio.bonoIncentivo.label" default="Bono Incentivo" /></span>

        <span class="property-value" aria-labelledby="bonoIncentivo-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="bonoIncentivo"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.valorDeCompra}">
    <li class="fieldcontain">
        <span id="valorDeCompra-label" class="property-label"><g:message code="liquidacionDeAntimonio.valorDeCompra.label" default="Valor De Compra" /></span>

        <span class="property-value" aria-labelledby="valorDeCompra-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="valorDeCompra"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.porcentajeRegalia}">
    <li class="fieldcontain">
        <span id="porcentajeRegalia-label" class="property-label"><g:message code="liquidacionDeAntimonio.porcentajeRegalia.label" default="Porcentaje Regalia" /></span>

        <span class="property-value" aria-labelledby="porcentajeRegalia-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="porcentajeRegalia"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.regaliaMinera}">
    <li class="fieldcontain">
        <span id="regaliaMinera-label" class="property-label"><g:message code="liquidacionDeAntimonio.regaliaMinera.label" default="Regalia Minera" /></span>

        <span class="property-value" aria-labelledby="regaliaMinera-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="regaliaMinera"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold; font-size: 12px">Retenciones</h1>
<g:hiddenField name="retenciones" value="${liquidacionDeAntimonioInstance?.retenciones}"/>
<div style="width: 700px; margin-left: auto; margin-right: auto;"><table id="tablaRetenciones"></table></div>

<g:if test="${liquidacionDeAntimonioInstance?.totalRetenciones}">
    <li class="fieldcontain">
        <span id="totalRetenciones-label" class="property-label"><g:message code="liquidacionDeAntimonio.totalRetenciones.label" default="Total Retenciones" /></span>

        <span class="property-value" aria-labelledby="totalRetenciones-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="totalRetenciones"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.totalPagado}">
    <li class="fieldcontain">
        <span id="totalPagado-label" class="property-label"><g:message code="liquidacionDeAntimonio.totalPagado.label" default="Total Pagado" /></span>

        <span class="property-value" aria-labelledby="totalPagado-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="totalPagado"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.totalAnticiposContraEntrega}">
    <li class="fieldcontain">
        <span id="totalAnticiposContraEntrega-label" class="property-label"><g:message code="liquidacionDeAntimonio.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" /></span>

        <span class="property-value" aria-labelledby="totalAnticiposContraEntrega-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="totalAnticiposContraEntrega"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.totalAnticiposContraFuturaEntrega}">
    <li class="fieldcontain">
        <span id="totalAnticiposContraFuturaEntrega-label" class="property-label"><g:message code="liquidacionDeAntimonio.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" /></span>

        <span class="property-value" aria-labelledby="totalAnticiposContraFuturaEntrega-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="totalAnticiposContraFuturaEntrega"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.totalLiquidoPagable}">
    <li class="fieldcontain">
        <span id="totalLiquidoPagable-label" class="property-label"><g:message code="liquidacionDeAntimonio.totalLiquidoPagable.label" default="Total Liquido Pagable" /></span>

        <span class="property-value" aria-labelledby="totalLiquidoPagable-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="totalLiquidoPagable"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.conjuntoAntimonio}">
    <li class="fieldcontain">
        <span id="conjuntoAntimonio-label" class="property-label"><g:message code="liquidacionDeAntimonio.conjuntoAntimonio.label" default="Conjunto de Antimonio" /></span>

        <span class="property-value" aria-labelledby="conjuntoAntimonio-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="conjuntoAntimonio"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.observaciones}">
    <li class="fieldcontain">
        <span id="observaciones-label" class="property-label"><g:message code="liquidacionDeAntimonio.observaciones.label" default="Observaciones" /></span>

        <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="observaciones"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeAntimonioInstance?.motivoDeModificacion}">
    <li class="fieldcontain">
        <span id="motivoDeModificacion-label" class="property-label"><g:message code="liquidacionDeAntimonio.motivoDeModificacion.label" default="Motivo De Modificacion" /></span>

        <span class="property-value" aria-labelledby="motivoDeModificacion-label"><g:fieldValue bean="${liquidacionDeAntimonioInstance}" field="motivoDeModificacion"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold">Detalle de Analisis Realizados</h1>

<table class="center" border="0" style="width: 70%;">
    <thead>
    <tr>
        <th style="text-align: center; width: 70%">DESCRIPCION DE ANALISIS</th>
        <th style="text-align: center; width: 30%">COSTO</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio1', 'error')}">
            ${liquidacionDeAntimonioInstance.detalleLaboratorio1}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio1', 'error')}">
            ${liquidacionDeAntimonioInstance.costoLaboratorio1}
        </td>
    </tr>
    <tr>
        <g:if test="${liquidacionDeAntimonioInstance?.detalleLaboratorio2}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio2', 'error')}">
                ${liquidacionDeAntimonioInstance.detalleLaboratorio2}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio2', 'error')}">
                ${liquidacionDeAntimonioInstance.costoLaboratorio2}
            </td>
        </g:if>
    </tr>
    <tr>
        <g:if test="${liquidacionDeAntimonioInstance?.detalleLaboratorio3}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio3', 'error')}">
                ${liquidacionDeAntimonioInstance.detalleLaboratorio3}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio3', 'error')}">
                ${liquidacionDeAntimonioInstance.costoLaboratorio3}
            </td>
        </g:if>
    </tr>
    <tr>
        <g:if test="${liquidacionDeAntimonioInstance?.detalleLaboratorio4}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio4', 'error')}">
                ${liquidacionDeAntimonioInstance.detalleLaboratorio4}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio4', 'error')}">
                ${liquidacionDeAntimonioInstance.costoLaboratorio4}
            </td>
        </g:if>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="liquidacionDeAntimonio.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="liquidoPagable" value="${liquidacionDeAntimonioInstance?.totalLiquidoPagable}" />
<g:if test="${liquidacionDeAntimonioInstance?.totalLiquidoPagable<0}">
    <h1 style="font-weight: bold">Enlace a Anticipo Contra Futura Entrega (Clic en enlace para desplegar formulario)</h1>
    <li class="fieldcontain">
        <label style="font-size: 16px; font-weight: bold; color: red; width: 70%">
            <g:link controller="anticipoContraFuturaEntrega" action="show" id="${org.socymet.anticipos.AnticipoContraFuturaEntrega.findByLiquidacionId(liquidacionDeAntimonioInstance.id).id}" target="_blank" style="color: red"> ANTICIPO CONTRA FUTURA ENTREGA GENERADO!</g:link>
        </label>
    </li>
</g:if>

</ol>

<fieldset class="buttons">
    <div style="float: left">
        <g:form>
            <g:hiddenField name="id" value="${liquidacionDeAntimonioInstance?.id}" />
            <g:if test="${liquidacionDeAntimonioInstance.fechaDeCancelacion!=(new java.util.Date(84,5,14))&&liquidacionDeAntimonioInstance.fechaDeCancelacion}">
                <span id="observaciones-label" class="property-label" style="font-weight: bold; color: green">CANCELADO</span>
            </g:if>
            <g:if test="${liquidacionDeAntimonioInstance.fechaDeCancelacion==(new java.util.Date(84,5,14))&&liquidacionDeAntimonioInstance.fechaDeCancelacion}">
                <span id="observaciones-label" class="property-label" style="font-weight: bold; color: red">SIN CANCELAR</span>
            </g:if>
            <g:link class="edit" action="edit" id="${liquidacionDeAntimonioInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </g:form>
    </div>
    <div style="float: right">
        <g:jasperReport controller="liquidacionDeAntimonio" action="crearReporte" jasper="liquidacion_antimonio" format="PDF" name="ReporteLiquidacion${liquidacionDeAntimonioInstance.lote}">
            <input type="hidden" name="LIQ_SN_ID" value="${liquidacionDeAntimonioInstance.id}" />
        </g:jasperReport>
    </div>
</fieldset>

</div>
</body>
</html>
