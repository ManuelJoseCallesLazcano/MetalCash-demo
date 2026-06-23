
<%@ page import="org.socymet.liquidacion.LiquidacionDeWolfran" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
    <g:javascript src="jquery-1.10.1.min.js" />
    <g:javascript src="i18n/grid.locale-es.js" />
    <g:javascript src="jquery.jqGrid.min.js" />
    <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
    <g:javascript src="liquidacionDeWolfran/calculosLiquidacion.js" />
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
<a href="#show-liquidacionDeWolfran" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-liquidacionDeWolfran" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list liquidacionDeWolfran">
<g:if test="${liquidacionDeWolfranInstance?.numeroLiquidacionWolfran}">
    <li class="fieldcontain">
        <span id="numeroLiquidacionWolfran-label" class="property-label"><g:message code="liquidacionDeWolfran.numeroLiquidacionWolfran.label" default="No. Liquidacion" /></span>

        <span class="property-value" aria-labelledby="numeroLiquidacionWolfran-label"><g:link controller="liquidacionDeWolfran" action="show" id="${liquidacionDeWolfranInstance?.id}">${liquidacionDeWolfranInstance?.numeroLiquidacionWolfran?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.recepcionDeWolfran}">
    <li class="fieldcontain">
        <span id="recepcionDeWolfran-label" class="property-label"><g:message code="liquidacionDeWolfran.recepcionDeWolfran.label" default="Lote" /></span>

        <span class="property-value" aria-labelledby="recepcionDeWolfran-label"><g:link controller="recepcionDeWolfran" action="show" id="${liquidacionDeWolfranInstance?.recepcionDeWolfran?.id}">${liquidacionDeWolfranInstance?.recepcionDeWolfran?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<g:if test="${liquidacionDeWolfranInstance?.nombreCliente}">
    <li class="fieldcontain">
        <span id="nombreCliente-label" class="property-label"><g:message code="liquidacionDeWolfran.nombreCliente.label" default="Nombre Cliente" /></span>

        <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="nombreCliente"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.nombreEmpresa}">
    <li class="fieldcontain">
        <span id="nombreEmpresa-label" class="property-label"><g:message code="liquidacionDeWolfran.nombreEmpresa.label" default="Nombre Empresa" /></span>

        <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="nombreEmpresa"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.fechaDeRecepcion}">
    <li class="fieldcontain">
        <span id="fechaDeRecepcion-label" class="property-label"><g:message code="liquidacionDeWolfran.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

        <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="fechaDeRecepcion"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.cantidadDeSacos}">
    <li class="fieldcontain">
        <span id="cantidadDeSacos-label" class="property-label"><g:message code="liquidacionDeWolfran.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

        <span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="cantidadDeSacos"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.tara}">
    <li class="fieldcontain">
        <span id="tara-label" class="property-label"><g:message code="liquidacionDeWolfran.tara.label" default="Tara" /></span>

        <span class="property-value" aria-labelledby="tara-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="tara"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.estadoDelLote}">
    <li class="fieldcontain">
        <span id="estadoDelLote-label" class="property-label"><g:message code="liquidacionDeWolfran.estadoDelLote.label" default="Estado Del Lote" /></span>
        <span class="property-value" aria-labelledby="estadoDelLote-label">${liquidacionDeWolfranInstance.recepcionDeWolfran.estadoDelLote}</span>
    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.pesoBruto}">
    <li class="fieldcontain">
        <span id="pesoBruto-label" class="property-label"><g:message code="liquidacionDeWolfran.pesoBruto.label" default="Peso Bruto" /></span>

        <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="pesoBruto"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'cotizacionDiariaDeWolfran', 'error')} required">
            <label for="cotizacionDiariaDeWolfran" style="width: 50%">
                <g:message code="liquidacionDeWolfran.cotizacionDiariaDeWolfran.label" default="Cot. Dia Wolfran" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeWolfranInstance.cotizacionDiariaDeWolfran}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'cotizacionQuincenalDeWolfran', 'error')} required">
            <label for="cotizacionQuincenalDeWolfran" style="width: 60%">
                <g:message code="liquidacionDeWolfran.cotizacionQuincenalDeWolfran.label" default="Cot. Quinc. Wolfran" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeWolfranInstance.cotizacionQuincenalDeWolfran}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'alicuotaDeWolfran', 'error')} required">
            <label for="alicuotaDeWolfran" style="width: 50%">
                <g:message code="liquidacionDeWolfran.alicuotaDeWolfran.label" default="Alicuota Wolfran" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeWolfranInstance.alicuotaDeWolfran}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDeWolfran.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeWolfranInstance.tipoDeCambioOficial}
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 60%">
                <g:message code="liquidacionDeWolfran.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeWolfranInstance.tipoDeCambioComercial}
        </td>
        <td>
            &nbsp;</td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<g:if test="${liquidacionDeWolfranInstance?.fechaDeLiquidacion}">
    <li class="fieldcontain">
        <span id="fechaDeLiquidacion-label" class="property-label"><g:message code="liquidacionDeWolfran.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

        <span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:formatDate date="${liquidacionDeWolfranInstance?.fechaDeLiquidacion}" format="dd/MM/yyyy"/></span>

    </li>
</g:if>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'kilosNetosHumedos', 'error')} required">
            <label for="kilosNetosHumedos" style="width: 50%">
                <g:message code="liquidacionDeWolfran.kilosNetosHumedos.label" default="K. N. H." />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeWolfranInstance.kilosNetosHumedos}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'humedad', 'error')} required">
            <label for="humedad" style="width: 50%">
                <g:message code="liquidacionDeWolfran.humedad.label" default="% H2O" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeWolfranInstance.humedad}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 50%">
                <g:message code="liquidacionDeWolfran.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeWolfranInstance.kilosNetosSecos}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'porcentajeWolfran', 'error')} required">
            <label for="porcentajeWolfran" style="width: 50%">
                <g:message code="liquidacionDeWolfran.porcentajeWolfran.label" default="% de Sn" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeWolfranInstance.porcentajeWolfran}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'kilosFinosWolfran', 'error')} required">
            <label for="kilosFinosWolfran" style="width: 50%">
                <g:message code="liquidacionDeWolfran.kilosFinosWolfran.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeWolfranInstance.kilosFinosWolfran}
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'librasFinasDeWolfran', 'error')} required">
            <label for="librasFinasDeWolfran" style="width: 50%">
                <g:message code="liquidacionDeWolfran.librasFinasDeWolfran.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeWolfranInstance.librasFinasDeWolfran}
        </td>
    </tr>
    </tbody>
</table>

<g:if test="${liquidacionDeWolfranInstance?.valorOficialBruto}">
    <li class="fieldcontain">
        <span id="valorOficialBruto-label" class="property-label"><g:message code="liquidacionDeWolfran.valorOficialBruto.label" default="Valor Oficial Bruto" /></span>

        <span class="property-value" aria-labelledby="valorOficialBruto-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="valorOficialBruto"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold; font-size: 12px">Parametros de Proteccion</h1>

<g:if test="${liquidacionDeWolfranInstance?.valorPorToneladaManual}">
    <li class="fieldcontain">
        <span id="valorPorToneladaManual-label" class="property-label"><g:message code="liquidacionDeWolfran.valorPorToneladaManual.label" default="Valor Por Tonelada Manual" /></span>

        <span class="property-value" aria-labelledby="valorPorToneladaManual-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="valorPorToneladaManual"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.puntoDeBajada}">
    <li class="fieldcontain">
        <span id="puntoDeBajada-label" class="property-label"><g:message code="liquidacionDeWolfran.puntoDeBajada.label" default="Punto De Bajada" /></span>

        <span class="property-value" aria-labelledby="puntoDeBajada-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="puntoDeBajada"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold; font-size: 12px">Valoracion Final del Lote</h1>

<g:if test="${liquidacionDeWolfranInstance?.tablaCotizacionWolfran}">
    <li class="fieldcontain">
        <span id="tablaCotizacionWolfran-label" class="property-label"><g:message code="liquidacionDeWolfran.tablaCotizacionWolfran.label" default="Tabla Cotizacion Wolfran" /></span>

        <span class="property-value" aria-labelledby="tablaCotizacionWolfran-label"><g:link controller="tablaCotizacionWolfran" action="show" id="${liquidacionDeWolfranInstance?.tablaCotizacionWolfran?.id}">${liquidacionDeWolfranInstance?.tablaCotizacionWolfran?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.valorPorTonelada}">
    <li class="fieldcontain">
        <span id="valorPorTonelada-label" class="property-label"><g:message code="liquidacionDeWolfran.valorPorTonelada.label" default="Valor Por Tonelada" /></span>

        <span class="property-value" aria-labelledby="valorPorTonelada-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="valorPorTonelada"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.valorNetoMineral}">
    <li class="fieldcontain">
        <span id="valorNetoMineral-label" class="property-label"><g:message code="liquidacionDeWolfran.valorNetoMineral.label" default="Valor Neto Mineral" /></span>

        <span class="property-value" aria-labelledby="valorNetoMineral-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="valorNetoMineral"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.valorNetoMineralEnBolivianos}">
    <li class="fieldcontain">
        <span id="valorNetoMineralEnBolivianos-label" class="property-label"><g:message code="liquidacionDeWolfran.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" /></span>

        <span class="property-value" aria-labelledby="valorNetoMineralEnBolivianos-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="valorNetoMineralEnBolivianos"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.bonoCalidad}">
    <li class="fieldcontain">
        <span id="bonoCalidad-label" class="property-label"><g:message code="liquidacionDeWolfran.bonoCalidad.label" default="Bono Calidad" /></span>

        <span class="property-value" aria-labelledby="bonoCalidad-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="bonoCalidad"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.bonoIncentivo}">
    <li class="fieldcontain">
        <span id="bonoIncentivo-label" class="property-label"><g:message code="liquidacionDeWolfran.bonoIncentivo.label" default="Bono Incentivo" /></span>

        <span class="property-value" aria-labelledby="bonoIncentivo-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="bonoIncentivo"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.valorDeCompra}">
    <li class="fieldcontain">
        <span id="valorDeCompra-label" class="property-label"><g:message code="liquidacionDeWolfran.valorDeCompra.label" default="Valor De Compra" /></span>

        <span class="property-value" aria-labelledby="valorDeCompra-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="valorDeCompra"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.porcentajeRegalia}">
    <li class="fieldcontain">
        <span id="porcentajeRegalia-label" class="property-label"><g:message code="liquidacionDeWolfran.porcentajeRegalia.label" default="Porcentaje Regalia" /></span>

        <span class="property-value" aria-labelledby="porcentajeRegalia-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="porcentajeRegalia"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.regaliaMinera}">
    <li class="fieldcontain">
        <span id="regaliaMinera-label" class="property-label"><g:message code="liquidacionDeWolfran.regaliaMinera.label" default="Regalia Minera" /></span>

        <span class="property-value" aria-labelledby="regaliaMinera-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="regaliaMinera"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold; font-size: 12px">Retenciones</h1>
<g:hiddenField name="retenciones" value="${liquidacionDeWolfranInstance?.retenciones}"/>
<div style="width: 700px; margin-left: auto; margin-right: auto;"><table id="tablaRetenciones"></table></div>

<g:if test="${liquidacionDeWolfranInstance?.totalRetenciones}">
    <li class="fieldcontain">
        <span id="totalRetenciones-label" class="property-label"><g:message code="liquidacionDeWolfran.totalRetenciones.label" default="Total Retenciones" /></span>

        <span class="property-value" aria-labelledby="totalRetenciones-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="totalRetenciones"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.totalPagado}">
    <li class="fieldcontain">
        <span id="totalPagado-label" class="property-label"><g:message code="liquidacionDeWolfran.totalPagado.label" default="Total Pagado" /></span>

        <span class="property-value" aria-labelledby="totalPagado-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="totalPagado"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.totalAnticiposContraEntrega}">
    <li class="fieldcontain">
        <span id="totalAnticiposContraEntrega-label" class="property-label"><g:message code="liquidacionDeWolfran.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" /></span>

        <span class="property-value" aria-labelledby="totalAnticiposContraEntrega-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="totalAnticiposContraEntrega"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.totalAnticiposContraFuturaEntrega}">
    <li class="fieldcontain">
        <span id="totalAnticiposContraFuturaEntrega-label" class="property-label"><g:message code="liquidacionDeWolfran.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" /></span>

        <span class="property-value" aria-labelledby="totalAnticiposContraFuturaEntrega-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="totalAnticiposContraFuturaEntrega"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.totalLiquidoPagable}">
    <li class="fieldcontain">
        <span id="totalLiquidoPagable-label" class="property-label"><g:message code="liquidacionDeWolfran.totalLiquidoPagable.label" default="Total Liquido Pagable" /></span>

        <span class="property-value" aria-labelledby="totalLiquidoPagable-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="totalLiquidoPagable"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.conjuntoWolfran}">
    <li class="fieldcontain">
        <span id="conjuntoWolfran-label" class="property-label"><g:message code="liquidacionDeWolfran.conjuntoWolfran.label" default="Conjunto de Wolfran" /></span>

        <span class="property-value" aria-labelledby="conjuntoWolfran-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="conjuntoWolfran"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.observaciones}">
    <li class="fieldcontain">
        <span id="observaciones-label" class="property-label"><g:message code="liquidacionDeWolfran.observaciones.label" default="Observaciones" /></span>

        <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="observaciones"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeWolfranInstance?.motivoDeModificacion}">
    <li class="fieldcontain">
        <span id="motivoDeModificacion-label" class="property-label"><g:message code="liquidacionDeWolfran.motivoDeModificacion.label" default="Motivo De Modificacion" /></span>

        <span class="property-value" aria-labelledby="motivoDeModificacion-label"><g:fieldValue bean="${liquidacionDeWolfranInstance}" field="motivoDeModificacion"/></span>

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
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio1', 'error')}">
            ${liquidacionDeWolfranInstance.detalleLaboratorio1}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio1', 'error')}">
            ${liquidacionDeWolfranInstance.costoLaboratorio1}
        </td>
    </tr>
    <tr>
        <g:if test="${liquidacionDeWolfranInstance?.detalleLaboratorio2}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio2', 'error')}">
                ${liquidacionDeWolfranInstance.detalleLaboratorio2}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio2', 'error')}">
                ${liquidacionDeWolfranInstance.costoLaboratorio2}
            </td>
        </g:if>
    </tr>
    <tr>
        <g:if test="${liquidacionDeWolfranInstance?.detalleLaboratorio3}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio3', 'error')}">
                ${liquidacionDeWolfranInstance.detalleLaboratorio3}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio3', 'error')}">
                ${liquidacionDeWolfranInstance.costoLaboratorio3}
            </td>
        </g:if>
    </tr>
    <tr>
        <g:if test="${liquidacionDeWolfranInstance?.detalleLaboratorio4}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio4', 'error')}">
                ${liquidacionDeWolfranInstance.detalleLaboratorio4}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio4', 'error')}">
                ${liquidacionDeWolfranInstance.costoLaboratorio4}
            </td>
        </g:if>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="liquidacionDeWolfran.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="liquidoPagable" value="${liquidacionDeWolfranInstance?.totalLiquidoPagable}" />
<g:if test="${liquidacionDeWolfranInstance?.totalLiquidoPagable<0}">
    <h1 style="font-weight: bold">Enlace a Anticipo Contra Futura Entrega (Clic en enlace para desplegar formulario)</h1>
    <li class="fieldcontain">
        <label style="font-size: 16px; font-weight: bold; color: red; width: 70%">
            <g:link controller="anticipoContraFuturaEntrega" action="show" id="${org.socymet.anticipos.AnticipoContraFuturaEntrega.findByLiquidacionId(liquidacionDeWolfranInstance.id).id}" target="_blank" style="color: red"> ANTICIPO CONTRA FUTURA ENTREGA GENERADO!</g:link>
        </label>
    </li>
</g:if>

</ol>

<fieldset class="buttons">
    <div style="float: left">
        <g:form>
            <g:hiddenField name="id" value="${liquidacionDeWolfranInstance?.id}" />
            <g:if test="${liquidacionDeWolfranInstance.fechaDeCancelacion!=(new java.util.Date(84,5,14))&&liquidacionDeWolfranInstance.fechaDeCancelacion}">
                <span id="observaciones-label" class="property-label" style="font-weight: bold; color: green">CANCELADO</span>
            </g:if>
            <g:if test="${liquidacionDeWolfranInstance.fechaDeCancelacion==(new java.util.Date(84,5,14))&&liquidacionDeWolfranInstance.fechaDeCancelacion}">
                <span id="observaciones-label" class="property-label" style="font-weight: bold; color: red">SIN CANCELAR</span>
            </g:if>
            <g:link class="edit" action="edit" id="${liquidacionDeWolfranInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </g:form>
    </div>
    <div style="float: right">
        <g:jasperReport controller="liquidacionDeWolfran" action="crearReporte" jasper="liquidacion_wolfran" format="PDF" name="ReporteLiquidacion${liquidacionDeWolfranInstance.lote}">
            <input type="hidden" name="LIQ_SN_ID" value="${liquidacionDeWolfranInstance.id}" />
        </g:jasperReport>
    </div>
</fieldset>
</div>
</body>
</html>
