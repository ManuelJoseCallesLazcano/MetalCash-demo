<%@ page import="org.socymet.org.socymet.reportes.ReporteRetenciones" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'reporteRetenciones.label', default: 'ReporteRetenciones')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'chosen.css')}" type="text/css" >
    <g:javascript src="jquery-1.10.1.min.js" />
    <g:javascript src="i18n/grid.locale-es.js" />
    <g:javascript src="jquery.jqGrid.min.js" />
    <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
    <g:javascript src="chosen.jquery.js" />
    <g:javascript src="reportes/retenciones.js" />
    <g:javascript src="reportes/filtroEmpresas.js" />
</head>
<body>
<a href="#create-reporteRetenciones" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    </ul>
</div>
<div id="create-reporteRetenciones" class="content scaffold-create" role="main">
<h1><g:message code="default.report.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<g:if test="${flash.error}">
    <div class="message" role="status">${flash.error}</div>
</g:if>
<g:hasErrors bean="${reporteRetencionesInstance}">
    <ul class="errors" role="alert">
        <g:eachError bean="${reporteRetencionesInstance}" var="error">
            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
    </ul>
</g:hasErrors>
<g:form>
<fieldset class="form">

<div style="display: none">
<h1 style="font-weight: bold">Listar por:</h1>

<table style="width: 500px;" class="center">
    <tbody>
    <tr>
        <td style="width: 10px"><input type="radio" id="fechasEmpresa" name="myGroup" value="2" checked="true"/></td>
        <td style="font-weight: bold">Fechas y Empresa</td>
    </tr>
    <tr>
        <td style="width: 10px"><input type="radio" id="lotesEmpresa" name="myGroup" value="4" /></td>
        <td style="font-weight: bold">Lotes y Empresa</td>
    </tr>
    </tbody>
</table>
</div>
<h1 style="font-weight: bold">Parametros de busqueda:</h1>
<g:hiddenField name="tipoReporte" value="fechasEmpresa" />
<div class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'elemento', 'error')} " style="display: none">
    <label for="elemento">
        <g:message code="reporteRetenciones.elemento.label" default="Elemento" />

    </label>
    <g:select name="elemento" from="${['Complejo','Plomo Plata','Zinc Plata','Cobre Plata']}" value="${reporteRetencionesInstance?.elemento}" valueMessagePrefix="reporteRetenciones.elemento"/>
</div>

<div id="_empresa" class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'empresa', 'error')} ">
    <label for="empresa">
        <g:message code="reporteRetenciones.empresa.label" default="Empresa" />

    </label>
    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${reporteRetencionesInstance?.empresa?.id}" class="many-to-one, chosen-select" style="width: 350px"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'tipoRetencion', 'error')} ">
    <label for="tipoRetencion">
        <g:message code="reporteRetenciones.tipoRetencion.label" default="Tipo Retencion" />

    </label>
    <g:select name="tipoRetencion" from="${['DE LEY','OTRA']}" value="${reporteRetencionesInstance?.tipoRetencion}" valueMessagePrefix="reporteRetenciones.tipoRetencion"/>
</div>

    <div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'fechaInicial', 'error')} ">
    <label for="fechaInicial">
        <g:message code="reporteRetenciones.fechaInicial.label" default="Fecha Inicial" />

    </label>
    <g:datepickerUI name="fechaInicial" value="${reporteRetencionesInstance?.fechaInicial ?: new Date()}"/>
</div>

<div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'fechaFinal', 'error')} ">
    <label for="fechaFinal">
        <g:message code="reporteRetenciones.fechaFinal.label" default="Fecha Final" />

    </label>
    <g:datepickerUI name="fechaFinal" value="${reporteRetencionesInstance?.fechaFinal ?: new Date()}"/>
</div>

<div id="_loteInicial" class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'loteInicial', 'error')} " style="display: none">
    <label for="loteInicial">
        <g:message code="reporteRetenciones.loteInicial.label" default="Lote Inicial" />

    </label>
    <g:textField name="loteInicial" inputmode="numeric" value="${reporteRetencionesInstance?.loteInicial}"/>
</div>

<div id="_loteFinal" class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'loteFinal', 'error')} " style="display: none">
    <label for="loteFinal">
        <g:message code="reporteRetenciones.loteFinal.label" default="Lote Final" />

    </label>
    <g:textField name="loteFinal" inputmode="numeric" value="${reporteRetencionesInstance?.loteFinal}"/>
</div>

    <div class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'ignorarLotes', 'error')} " style="display: none">
        <label for="ignorarLotes">
            <g:message code="reporteRetenciones.ignorarLotes.label" default="Ignorar Lotes" />

        </label>
        <g:textField name="ignorarLotes" value="${reporteRetencionesInstance?.ignorarLotes}" size="50"/>
        <span style="font-size: 12px; ">Separar por comas (Ejm: 1006,1014,1084)</span>
    </div>

    <br>

<div id="_resultadosComplejo">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteRetenciones" action="crearReporteComplejo" value="Generar Reporte" />
    </div>
</div>

</fieldset>
</g:form>
</div>
</body>
</html>
