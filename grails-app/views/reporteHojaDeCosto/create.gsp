<%@ page import="org.socymet.org.socymet.reportes.ReporteHojaDeCosto" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'reporteHojaDeCosto.label', default: 'ReporteHojaDeCosto')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
    <g:javascript src="jquery-1.10.1.min.js" />
    <g:javascript src="i18n/grid.locale-es.js" />
    <g:javascript src="jquery.jqGrid.min.js" />
    <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
    <g:javascript src="reportes/hojaDeCosto.js" />
</head>
<body>
<a href="#create-reporteHojaDeCosto" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="create-reporteHojaDeCosto" class="content scaffold-create" role="main">
<h1><g:message code="default.create.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<g:if test="${flash.error}">
    <div class="message" role="status">${flash.error}</div>
</g:if>
<g:hasErrors bean="${reporteHojaDeCostoInstance}">
    <ul class="errors" role="alert">
        <g:eachError bean="${reporteHojaDeCostoInstance}" var="error">
            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
    </ul>
</g:hasErrors>
<g:form>
<fieldset class="form">
<h1 style="font-weight: bold">Listar por:</h1>

<table style="width: 500px;" class="center">
    <tbody>
    <tr>
        <td style="width: 10px"><input type="radio" id="fechas" name="myGroup" value="1" checked="checked" /></td>
        <td style="font-weight: bold">Fechas</td>
    </tr>
    <tr>
        <td style="width: 10px"><input type="radio" id="fechasEmpresa" name="myGroup" value="2" /></td>
        <td style="font-weight: bold">Fechas y Empresa</td>
    </tr>
    <tr>
        <td style="width: 10px"><input type="radio" id="lotes" name="myGroup" value="3" /></td>
        <td style="font-weight: bold">Lotes</td>
    </tr>
    <tr>
        <td style="width: 10px"><input type="radio" id="lotesEmpresa" name="myGroup" value="4" /></td>
        <td style="font-weight: bold">Lotes y Empresa</td>
    </tr>
    </tbody>
</table>
<h1 style="font-weight: bold">Parametros de busqueda:</h1>
<g:hiddenField name="tipoReporte" value="fechas" />
<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'elemento', 'error')} ">
    <label for="elemento">
        <g:message code="reporteHojaDeCosto.elemento.label" default="Elemento" />

    </label>
    <g:select name="elemento" from="${['Complejo','Plomo Plata','Zinc Plata']}" value="${reporteHojaDeCostoInstance?.elemento}" valueMessagePrefix="reporteHojaDeCosto.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'nombreDelConjunto', 'error')} ">
    <label for="nombreDelConjunto">
        <g:message code="reporteHojaDeCosto.nombreDelConjunto.label" default="Nombre Del Conjunto" />

    </label>
    <g:textField name="nombreDelConjunto" value="${reporteHojaDeCostoInstance?.nombreDelConjunto}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'destinoDelConjunto', 'error')} ">
    <label for="destinoDelConjunto">
        <g:message code="reporteHojaDeCosto.destinoDelConjunto.label" default="Destino Del Conjunto" />

    </label>
    <g:textField name="destinoDelConjunto" value="${reporteHojaDeCostoInstance?.destinoDelConjunto}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'asignarConjuntoALotes', 'error')} ">
    <label for="asignarConjuntoALotes">
        <g:message code="reporteHojaDeCosto.asignarConjuntoALotes.label" default="¿Asignar Conjunto a Lotes?" />

    </label>
    <g:select name="asignarConjuntoALotes" from="${['NO','SI']}" value="${reporteHojaDeCostoInstance?.asignarConjuntoALotes}" valueMessagePrefix="reporteHojaDeCosto.asignarConjuntoALotes" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'ignorarLotes', 'error')} ">
    <label for="ignorarLotes">
        <g:message code="reporteHojaDeCosto.ignorarLotes.label" default="Ignorar Lotes" />

    </label>
    <g:textField name="ignorarLotes" value="${reporteHojaDeCostoInstance?.ignorarLotes}" size="50"/>
    <span style="font-size: 12px; ">Separar por comas (Ejm: 1006,1014,1084)</span>
</div>

<div id="_empresa" class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'empresa', 'error')} " style="display: none">
    <label for="empresa">
        <g:message code="reporteHojaDeCosto.empresa.label" default="Empresa" />

    </label>
    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteHojaDeCostoInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'fechaInicial', 'error')} ">
    <label for="fechaInicial">
        <g:message code="reporteHojaDeCosto.fechaInicial.label" default="Fecha Inicial" />

    </label>
    <g:datepickerUI name="fechaInicial" value="${reporteHojaDeCostoInstance?.fechaInicial ?: new Date()}"/>
</div>

<div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'fechaFinal', 'error')} ">
    <label for="fechaFinal">
        <g:message code="reporteHojaDeCosto.fechaFinal.label" default="Fecha Final" />

    </label>
    <g:datepickerUI name="fechaFinal" value="${reporteHojaDeCostoInstance?.fechaFinal ?: new Date()}"/>
</div>

<div id="_loteInicial" class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'loteInicial', 'error')} " style="display: none">
    <label for="loteInicial">
        <g:message code="reporteHojaDeCosto.loteInicial.label" default="Lote Inicial" />

    </label>
    <g:textField name="loteInicial" inputmode="numeric" value="${reporteHojaDeCostoInstance?.loteInicial}"/>
</div>

<div id="_loteFinal" class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'loteFinal', 'error')} " style="display: none">
    <label for="loteFinal">
        <g:message code="reporteHojaDeCosto.loteFinal.label" default="Lote Final" />

    </label>
    <g:textField name="loteFinal" inputmode="numeric" value="${reporteHojaDeCostoInstance?.loteFinal}"/>
</div>

<div id="_leyesEstano" style="display: none">
    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaEstano', 'error')} ">
        <label for="leyMinimaEstano">
            <g:message code="reporteHojaDeCosto.leyMinimaEstano.label" default="Ley Minima Estano" />

        </label>
        <g:field name="leyMinimaEstano" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaEstano')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaEstano', 'error')} ">
        <label for="leyMaximaEstano">
            <g:message code="reporteHojaDeCosto.leyMaximaEstano.label" default="Ley Maxima Estano" />

        </label>
        <g:field name="leyMaximaEstano" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaEstano')}" inputmode="decimal"/>
    </div>
</div>

<div id="_leyesPlata" style="display: none">
    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlata', 'error')} ">
        <label for="leyMinimaPlata">
            <g:message code="reporteHojaDeCosto.leyMinimaPlata.label" default="Ley Minima Plata" />

        </label>
        <g:field name="leyMinimaPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlata')}" inputmode="decimal"/>
    </div>
    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlata', 'error')} ">
        <label for="leyMaximaPlata">
            <g:message code="reporteHojaDeCosto.leyMaximaPlata.label" default="Ley Maxima Plata" />

        </label>
        <g:field name="leyMaximaPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlata')}" inputmode="decimal"/>
    </div>
</div>

<div id="_leyesWolfran" style="display: none">
    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaWolfran', 'error')} ">
        <label for="leyMinimaWolfran">
            <g:message code="reporteHojaDeCosto.leyMinimaWolfran.label" default="Ley Minima Wolfran" />

        </label>
        <g:field name="leyMinimaWolfran" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaWolfran')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaWolfran', 'error')} ">
        <label for="leyMaximaWolfran">
            <g:message code="reporteHojaDeCosto.leyMaximaWolfran.label" default="Ley Maxima Wolfran" />

        </label>
        <g:field name="leyMaximaWolfran" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaWolfran')}" inputmode="decimal"/>
    </div>
</div>

<div id="_leyesAntimonio" style="display: none">
    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaAntimonio', 'error')} ">
        <label for="leyMinimaAntimonio">
            <g:message code="reporteHojaDeCosto.leyMinimaAntimonio.label" default="Ley Minima Antimonio" />

        </label>
        <g:field name="leyMinimaAntimonio" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaAntimonio')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaAntimonio', 'error')} ">
        <label for="leyMaximaAntimonio">
            <g:message code="reporteHojaDeCosto.leyMaximaAntimonio.label" default="Ley Maxima Antimonio" />

        </label>
        <g:field name="leyMaximaAntimonio" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaAntimonio')}" inputmode="decimal"/>
    </div>
</div>

<div id="_leyesComplejo" style="display: none">
    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaZincComplejo', 'error')} ">
        <label for="leyMinimaZincComplejo">
            <g:message code="reporteHojaDeCosto.leyMinimaZincComplejo.label" default="Ley Minima Zinc" />

        </label>
        <g:field name="leyMinimaZincComplejo" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaZincComplejo')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaZincComplejo', 'error')} ">
        <label for="leyMaximaZincComplejo">
            <g:message code="reporteHojaDeCosto.leyMaximaZincComplejo.label" default="Ley Maxima Zinc" />

        </label>
        <g:field name="leyMaximaZincComplejo" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaZincComplejo')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlomoComplejo', 'error')} ">
        <label for="leyMinimaPlomoComplejo">
            <g:message code="reporteHojaDeCosto.leyMinimaPlomoComplejo.label" default="Ley Minima Plomo" />

        </label>
        <g:field name="leyMinimaPlomoComplejo" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlomoComplejo')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlomoComplejo', 'error')} ">
        <label for="leyMaximaPlomoComplejo">
            <g:message code="reporteHojaDeCosto.leyMaximaPlomoComplejo.label" default="Ley Maxima Plomo" />

        </label>
        <g:field name="leyMaximaPlomoComplejo" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlomoComplejo')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlataComplejo', 'error')} ">
        <label for="leyMinimaPlataComplejo">
            <g:message code="reporteHojaDeCosto.leyMinimaPlataComplejo.label" default="Ley Minima Plata" />

        </label>
        <g:field name="leyMinimaPlataComplejo" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlataComplejo')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlataComplejo', 'error')} ">
        <label for="leyMaximaPlataComplejo">
            <g:message code="reporteHojaDeCosto.leyMaximaPlataComplejo.label" default="Ley Maxima Plata" />

        </label>
        <g:field name="leyMaximaPlataComplejo" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlataComplejo')}" inputmode="decimal"/>
    </div>
</div>

<div id="_leyesPlomoPlata" style="display: none">
    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlomoPlomoPlata', 'error')} ">
        <label for="leyMinimaPlomoPlomoPlata">
            <g:message code="reporteHojaDeCosto.leyMinimaPlomoPlomoPlata.label" default="Ley Minima Plomo" />

        </label>
        <g:field name="leyMinimaPlomoPlomoPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlomoPlomoPlata')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlomoPlomoPlata', 'error')} ">
        <label for="leyMaximaPlomoPlomoPlata">
            <g:message code="reporteHojaDeCosto.leyMaximaPlomoPlomoPlata.label" default="Ley Maxima Plomo" />

        </label>
        <g:field name="leyMaximaPlomoPlomoPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlomoPlomoPlata')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlataPlomoPlata', 'error')} ">
        <label for="leyMinimaPlataPlomoPlata">
            <g:message code="reporteHojaDeCosto.leyMinimaPlataPlomoPlata.label" default="Ley Minima Plata" />

        </label>
        <g:field name="leyMinimaPlataPlomoPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlataPlomoPlata')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlataPlomoPlata', 'error')} ">
        <label for="leyMaximaPlataPlomoPlata">
            <g:message code="reporteHojaDeCosto.leyMaximaPlataPlomoPlata.label" default="Ley Maxima Plata" />

        </label>
        <g:field name="leyMaximaPlataPlomoPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlataPlomoPlata')}" inputmode="decimal"/>
    </div>
</div>

<div id="_leyesZincPlata" style="display: none">
    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaZincZincPlata', 'error')} ">
        <label for="leyMinimaZincZincPlata">
            <g:message code="reporteHojaDeCosto.leyMinimaZincZincPlata.label" default="Ley Minima Zinc" />

        </label>
        <g:field name="leyMinimaZincZincPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaZincZincPlata')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaZincZincPlata', 'error')} ">
        <label for="leyMaximaZincZincPlata">
            <g:message code="reporteHojaDeCosto.leyMaximaZincZincPlata.label" default="Ley Maxima Zinc" />

        </label>
        <g:field name="leyMaximaZincZincPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaZincZincPlata')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlataZincPlata', 'error')} ">
        <label for="leyMinimaPlataZincPlata">
            <g:message code="reporteHojaDeCosto.leyMinimaPlataZincPlata.label" default="Ley Minima Plata" />

        </label>
        <g:field name="leyMinimaPlataZincPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlataZincPlata')}" inputmode="decimal"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlataZincPlata', 'error')} ">
        <label for="leyMaximaPlataZincPlata">
            <g:message code="reporteHojaDeCosto.leyMaximaPlataZincPlata.label" default="Ley Maxima Plata" />

        </label>
        <g:field name="leyMaximaPlataZincPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlataZincPlata')}" inputmode="decimal"/>
    </div>
</div>

<div id="_resultadosEstano" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteHojaDeCosto" action="crearReporteEstano" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosPlata" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteHojaDeCosto" action="crearReportePlata" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosWolfran" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteHojaDeCosto" action="crearReporteWolfran" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosAntimonio" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteHojaDeCosto" action="crearReporteAntimonio" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosComplejo" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteHojaDeCosto" action="crearReporteComplejo" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosPlomoPlata" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteHojaDeCosto" action="crearReportePlomoPlata" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosZincPlata" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteHojaDeCosto" action="crearReporteZincPlata" value="Generar Reporte" />
    </div>
</div>
</fieldset>
</g:form>
</div>
</body>
</html>
