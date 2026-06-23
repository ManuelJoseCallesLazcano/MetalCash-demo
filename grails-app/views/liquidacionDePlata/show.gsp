
<%@ page import="org.socymet.liquidacion.LiquidacionDePlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
    <g:javascript src="jquery-1.10.1.min.js" />
    <g:javascript src="i18n/grid.locale-es.js" />
    <g:javascript src="jquery.jqGrid.min.js" />
    <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
    <g:javascript src="liquidacionDePlata/calculosLiquidacion.js" />
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
<a href="#show-liquidacionDePlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-liquidacionDePlata" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list liquidacionDePlata">
<g:if test="${liquidacionDePlataInstance?.numeroLiquidacionPlata}">
    <li class="fieldcontain">
        <span id="numeroLiquidacionPlata-label" class="property-label"><g:message code="liquidacionDePlata.numeroLiquidacionPlata.label" default="No. Liquidacion" /></span>

        <span class="property-value" aria-labelledby="numeroLiquidacionPlata-label"><g:link controller="liquidacionDePlata" action="show" id="${liquidacionDePlataInstance?.id}">${liquidacionDePlataInstance?.numeroLiquidacionPlata?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.recepcionDePlata}">
    <li class="fieldcontain">
        <span id="recepcionDePlata-label" class="property-label"><g:message code="liquidacionDePlata.recepcionDePlata.label" default="Lote" /></span>

        <span class="property-value" aria-labelledby="recepcionDePlata-label"><g:link controller="recepcionDePlata" action="show" id="${liquidacionDePlataInstance?.recepcionDePlata?.id}">${liquidacionDePlataInstance?.recepcionDePlata?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<g:if test="${liquidacionDePlataInstance?.nombreCliente}">
    <li class="fieldcontain">
        <span id="nombreCliente-label" class="property-label"><g:message code="liquidacionDePlata.nombreCliente.label" default="Nombre Cliente" /></span>

        <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="nombreCliente"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.nombreEmpresa}">
    <li class="fieldcontain">
        <span id="nombreEmpresa-label" class="property-label"><g:message code="liquidacionDePlata.nombreEmpresa.label" default="Nombre Empresa" /></span>

        <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="nombreEmpresa"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.fechaDeRecepcion}">
    <li class="fieldcontain">
        <span id="fechaDeRecepcion-label" class="property-label"><g:message code="liquidacionDePlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

        <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="fechaDeRecepcion"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.cantidadDeSacos}">
    <li class="fieldcontain">
        <span id="cantidadDeSacos-label" class="property-label"><g:message code="liquidacionDePlata.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

        <span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="cantidadDeSacos"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.tara}">
    <li class="fieldcontain">
        <span id="tara-label" class="property-label"><g:message code="liquidacionDePlata.tara.label" default="Tara" /></span>

        <span class="property-value" aria-labelledby="tara-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="tara"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.estadoDelLote}">
    <li class="fieldcontain">
        <span id="estadoDelLote-label" class="property-label"><g:message code="liquidacionDePlata.estadoDelLote.label" default="Estado Del Lote" /></span>
        <span class="property-value" aria-labelledby="estadoDelLote-label">${liquidacionDePlataInstance.recepcionDePlata.estadoDelLote}</span>
    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.pesoBruto}">
    <li class="fieldcontain">
        <span id="pesoBruto-label" class="property-label"><g:message code="liquidacionDePlata.pesoBruto.label" default="Peso Bruto" /></span>

        <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="pesoBruto"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'cotizacionDiariaDePlata', 'error')} required">
            <label for="cotizacionDiariaDePlata" style="width: 50%">
                <g:message code="liquidacionDePlata.cotizacionDiariaDePlata.label" default="Cot. Dia Plata" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlataInstance.cotizacionDiariaDePlata}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'cotizacionQuincenalDePlata', 'error')} required">
            <label for="cotizacionQuincenalDePlata" style="width: 60%">
                <g:message code="liquidacionDePlata.cotizacionQuincenalDePlata.label" default="Cot. Quinc. Plata" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlataInstance.cotizacionQuincenalDePlata}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'alicuotaDePlata', 'error')} required">
            <label for="alicuotaDePlata" style="width: 50%">
                <g:message code="liquidacionDePlata.alicuotaDePlata.label" default="Alicuota Plata" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlataInstance.alicuotaDePlata}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDePlata.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlataInstance.tipoDeCambioOficial}
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 60%">
                <g:message code="liquidacionDePlata.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlataInstance.tipoDeCambioComercial}
        </td>
        <td>
            &nbsp;</td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<g:if test="${liquidacionDePlataInstance?.fechaDeLiquidacion}">
    <li class="fieldcontain">
        <span id="fechaDeLiquidacion-label" class="property-label"><g:message code="liquidacionDePlata.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

        <span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:formatDate date="${liquidacionDePlataInstance?.fechaDeLiquidacion}" format="dd/MM/yyyy"/></span>

    </li>
</g:if>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'kilosNetosHumedos', 'error')} required">
            <label for="kilosNetosHumedos" style="width: 50%">
                <g:message code="liquidacionDePlata.kilosNetosHumedos.label" default="K. N. H." />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlataInstance.kilosNetosHumedos}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'humedad', 'error')} required">
            <label for="humedad" style="width: 50%">
                <g:message code="liquidacionDePlata.humedad.label" default="% H2O" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlataInstance.humedad}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 50%">
                <g:message code="liquidacionDePlata.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlataInstance.kilosNetosSecos}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'porcentajePlata', 'error')} required">
            <label for="porcentajePlata" style="width: 50%">
                <g:message code="liquidacionDePlata.porcentajePlata.label" default="DM de Ag" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlataInstance.porcentajePlata}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'kilosFinosPlata', 'error')} required">
            <label for="kilosFinosPlata" style="width: 50%">
                <g:message code="liquidacionDePlata.kilosFinosPlata.label" default="Gramos/Ton." />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlataInstance.kilosFinosPlata}
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'librasFinasDePlata', 'error')} required">
            <label for="librasFinasDePlata" style="width: 50%">
                <g:message code="liquidacionDePlata.librasFinasDePlata.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlataInstance.librasFinasDePlata}
        </td>
    </tr>
    </tbody>
</table>

<g:if test="${liquidacionDePlataInstance?.valorOficialBruto}">
    <li class="fieldcontain">
        <span id="valorOficialBruto-label" class="property-label"><g:message code="liquidacionDePlata.valorOficialBruto.label" default="Valor Oficial Bruto" /></span>

        <span class="property-value" aria-labelledby="valorOficialBruto-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="valorOficialBruto"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold; font-size: 12px">Parametros de Proteccion</h1>

<g:if test="${liquidacionDePlataInstance?.valorPorToneladaManual}">
    <li class="fieldcontain">
        <span id="valorPorToneladaManual-label" class="property-label"><g:message code="liquidacionDePlata.valorPorToneladaManual.label" default="Valor Por Tonelada Manual" /></span>

        <span class="property-value" aria-labelledby="valorPorToneladaManual-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="valorPorToneladaManual"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.puntoDeBajada}">
    <li class="fieldcontain">
        <span id="puntoDeBajada-label" class="property-label"><g:message code="liquidacionDePlata.puntoDeBajada.label" default="Punto De Bajada" /></span>

        <span class="property-value" aria-labelledby="puntoDeBajada-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="puntoDeBajada"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold; font-size: 12px">Valoracion Final del Lote</h1>

<g:if test="${liquidacionDePlataInstance?.tablaCotizacionPlata}">
    <li class="fieldcontain">
        <span id="tablaCotizacionPlata-label" class="property-label"><g:message code="liquidacionDePlata.tablaCotizacionPlata.label" default="Tabla Cotizacion Plata" /></span>

        <span class="property-value" aria-labelledby="tablaCotizacionPlata-label"><g:link controller="tablaCotizacionPlata" action="show" id="${liquidacionDePlataInstance?.tablaCotizacionPlata?.id}">${liquidacionDePlataInstance?.tablaCotizacionPlata?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.valorPorTonelada}">
    <li class="fieldcontain">
        <span id="valorPorTonelada-label" class="property-label"><g:message code="liquidacionDePlata.valorPorTonelada.label" default="Valor Por Tonelada" /></span>

        <span class="property-value" aria-labelledby="valorPorTonelada-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="valorPorTonelada"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.valorNetoMineral}">
    <li class="fieldcontain">
        <span id="valorNetoMineral-label" class="property-label"><g:message code="liquidacionDePlata.valorNetoMineral.label" default="Valor Neto Mineral" /></span>

        <span class="property-value" aria-labelledby="valorNetoMineral-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="valorNetoMineral"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.valorNetoMineralEnBolivianos}">
    <li class="fieldcontain">
        <span id="valorNetoMineralEnBolivianos-label" class="property-label"><g:message code="liquidacionDePlata.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" /></span>

        <span class="property-value" aria-labelledby="valorNetoMineralEnBolivianos-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="valorNetoMineralEnBolivianos"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.bonoCalidad}">
    <li class="fieldcontain">
        <span id="bonoCalidad-label" class="property-label"><g:message code="liquidacionDePlata.bonoCalidad.label" default="Bono Calidad" /></span>

        <span class="property-value" aria-labelledby="bonoCalidad-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="bonoCalidad"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.bonoIncentivo}">
    <li class="fieldcontain">
        <span id="bonoIncentivo-label" class="property-label"><g:message code="liquidacionDePlata.bonoIncentivo.label" default="Bono Incentivo" /></span>

        <span class="property-value" aria-labelledby="bonoIncentivo-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="bonoIncentivo"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.valorDeCompra}">
    <li class="fieldcontain">
        <span id="valorDeCompra-label" class="property-label"><g:message code="liquidacionDePlata.valorDeCompra.label" default="Valor De Compra" /></span>

        <span class="property-value" aria-labelledby="valorDeCompra-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="valorDeCompra"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.porcentajeRegalia}">
    <li class="fieldcontain">
        <span id="porcentajeRegalia-label" class="property-label"><g:message code="liquidacionDePlata.porcentajeRegalia.label" default="Porcentaje Regalia" /></span>

        <span class="property-value" aria-labelledby="porcentajeRegalia-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="porcentajeRegalia"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.regaliaMinera}">
    <li class="fieldcontain">
        <span id="regaliaMinera-label" class="property-label"><g:message code="liquidacionDePlata.regaliaMinera.label" default="Regalia Minera" /></span>

        <span class="property-value" aria-labelledby="regaliaMinera-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="regaliaMinera"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold; font-size: 12px">Retenciones</h1>
<g:hiddenField name="retenciones" value="${liquidacionDePlataInstance?.retenciones}"/>
<div style="width: 700px; margin-left: auto; margin-right: auto;"><table id="tablaRetenciones"></table></div>

<g:if test="${liquidacionDePlataInstance?.totalRetenciones}">
    <li class="fieldcontain">
        <span id="totalRetenciones-label" class="property-label"><g:message code="liquidacionDePlata.totalRetenciones.label" default="Total Retenciones" /></span>

        <span class="property-value" aria-labelledby="totalRetenciones-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="totalRetenciones"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.totalPagado}">
    <li class="fieldcontain">
        <span id="totalPagado-label" class="property-label"><g:message code="liquidacionDePlata.totalPagado.label" default="Total Pagado" /></span>

        <span class="property-value" aria-labelledby="totalPagado-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="totalPagado"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.totalAnticiposContraEntrega}">
    <li class="fieldcontain">
        <span id="totalAnticiposContraEntrega-label" class="property-label"><g:message code="liquidacionDePlata.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" /></span>

        <span class="property-value" aria-labelledby="totalAnticiposContraEntrega-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="totalAnticiposContraEntrega"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.totalAnticiposContraFuturaEntrega}">
    <li class="fieldcontain">
        <span id="totalAnticiposContraFuturaEntrega-label" class="property-label"><g:message code="liquidacionDePlata.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" /></span>

        <span class="property-value" aria-labelledby="totalAnticiposContraFuturaEntrega-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="totalAnticiposContraFuturaEntrega"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.totalLiquidoPagable}">
    <li class="fieldcontain">
        <span id="totalLiquidoPagable-label" class="property-label"><g:message code="liquidacionDePlata.totalLiquidoPagable.label" default="Total Liquido Pagable" /></span>

        <span class="property-value" aria-labelledby="totalLiquidoPagable-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="totalLiquidoPagable"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.conjuntoPlata}">
    <li class="fieldcontain">
        <span id="conjuntoPlata-label" class="property-label"><g:message code="liquidacionDePlata.conjuntoPlata.label" default="Conjunto de Plata" /></span>

        <span class="property-value" aria-labelledby="conjuntoPlata-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="conjuntoPlata"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.observaciones}">
    <li class="fieldcontain">
        <span id="observaciones-label" class="property-label"><g:message code="liquidacionDePlata.observaciones.label" default="Observaciones" /></span>

        <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="observaciones"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlataInstance?.motivoDeModificacion}">
    <li class="fieldcontain">
        <span id="motivoDeModificacion-label" class="property-label"><g:message code="liquidacionDePlata.motivoDeModificacion.label" default="Motivo De Modificacion" /></span>

        <span class="property-value" aria-labelledby="motivoDeModificacion-label"><g:fieldValue bean="${liquidacionDePlataInstance}" field="motivoDeModificacion"/></span>

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
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio1', 'error')}">
            ${liquidacionDePlataInstance.detalleLaboratorio1}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'costoLaboratorio1', 'error')}">
            ${liquidacionDePlataInstance.costoLaboratorio1}
        </td>
    </tr>
    <tr>
        <g:if test="${liquidacionDePlataInstance?.detalleLaboratorio2}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio2', 'error')}">
                ${liquidacionDePlataInstance.detalleLaboratorio2}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'costoLaboratorio2', 'error')}">
                ${liquidacionDePlataInstance.costoLaboratorio2}
            </td>
        </g:if>
    </tr>
    <tr>
        <g:if test="${liquidacionDePlataInstance?.detalleLaboratorio3}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio3', 'error')}">
                ${liquidacionDePlataInstance.detalleLaboratorio3}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'costoLaboratorio3', 'error')}">
                ${liquidacionDePlataInstance.costoLaboratorio3}
            </td>
        </g:if>
    </tr>
    <tr>
        <g:if test="${liquidacionDePlataInstance?.detalleLaboratorio4}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio4', 'error')}">
                ${liquidacionDePlataInstance.detalleLaboratorio4}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'costoLaboratorio4', 'error')}">
                ${liquidacionDePlataInstance.costoLaboratorio4}
            </td>
        </g:if>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="liquidacionDePlata.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="liquidoPagable" value="${liquidacionDePlataInstance?.totalLiquidoPagable}" />
<g:if test="${liquidacionDePlataInstance?.totalLiquidoPagable<0}">
    <h1 style="font-weight: bold">Enlace a Anticipo Contra Futura Entrega (Clic en enlace para desplegar formulario)</h1>
    <li class="fieldcontain">
        <label style="font-size: 16px; font-weight: bold; color: red; width: 70%">
            <g:link controller="anticipoContraFuturaEntrega" action="show" id="${org.socymet.anticipos.AnticipoContraFuturaEntrega.findByLiquidacionId(liquidacionDePlataInstance.id).id}" target="_blank" style="color: red"> ANTICIPO CONTRA FUTURA ENTREGA GENERADO!</g:link>
        </label>
    </li>
</g:if>

</ol>

<fieldset class="buttons">
    <div style="float: left">
        <g:form>
            <g:hiddenField name="id" value="${liquidacionDePlataInstance?.id}" />
            <g:if test="${liquidacionDePlataInstance.fechaDeCancelacion!=(new java.util.Date(84,5,14))&&liquidacionDePlataInstance.fechaDeCancelacion}">
                <span id="observaciones-label" class="property-label" style="font-weight: bold; color: green">CANCELADO</span>
            </g:if>
            <g:if test="${liquidacionDePlataInstance.fechaDeCancelacion==(new java.util.Date(84,5,14))&&liquidacionDePlataInstance.fechaDeCancelacion}">
                <span id="observaciones-label" class="property-label" style="font-weight: bold; color: red">SIN CANCELAR</span>
            </g:if>
            <g:link class="edit" action="edit" id="${liquidacionDePlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </g:form>
    </div>
    <div style="float: right">
        <g:jasperReport controller="liquidacionDePlata" action="crearReporte" jasper="liquidacion_plata" format="PDF" name="ReporteLiquidacion${liquidacionDePlataInstance.lote}">
            <input type="hidden" name="LIQ_SN_ID" value="${liquidacionDePlataInstance.id}" />
        </g:jasperReport>
    </div>
</fieldset>

</div>
</body>
</html>
