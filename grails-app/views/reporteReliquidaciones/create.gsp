<%@ page import="org.socymet.org.socymet.reportes.ReporteReliquidaciones" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteReliquidaciones.label', default: 'ReporteReliquidaciones')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="reportes/reliquidaciones.js" />
	</head>
	<body>
		<a href="#create-reporteReliquidaciones" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reporteReliquidaciones" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteReliquidacionesInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteReliquidacionesInstance}" var="error">
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
                <div class="fieldcontain ${hasErrors(bean: reporteReliquidacionesInstance, field: 'elemento', 'error')} ">
                    <label for="elemento">
                        <g:message code="reporteReliquidaciones.elemento.label" default="Elemento" />

                    </label>
                    <g:select name="elemento" from="${['Estano','Plata','Wolfran','Antimonio','Complejo']}" value="${reporteReliquidacionesInstance?.elemento}" valueMessagePrefix="reporteReliquidaciones.elemento" noSelection="['': '']"/>
                </div>

                <div id="_empresa" class="fieldcontain ${hasErrors(bean: reporteReliquidacionesInstance, field: 'empresa', 'error')} " style="display: none">
                    <label for="empresa">
                        <g:message code="reporteReliquidaciones.empresa.label" default="Empresa" />

                    </label>
                    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteReliquidacionesInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
                </div>

                <div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: reporteReliquidacionesInstance, field: 'fechaInicial', 'error')} ">
                    <label for="fechaInicial">
                        <g:message code="reporteReliquidaciones.fechaInicial.label" default="Fecha Inicial" />

                    </label>
                    <g:datepickerUI name="fechaInicial" value="${reporteReliquidacionesInstance?.fechaInicial ?: new Date()}"/>
                </div>

                <div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: reporteReliquidacionesInstance, field: 'fechaFinal', 'error')} ">
                    <label for="fechaFinal">
                        <g:message code="reporteReliquidaciones.fechaFinal.label" default="Fecha Final" />

                    </label>
                    <g:datepickerUI name="fechaFinal" value="${reporteReliquidacionesInstance?.fechaFinal ?: new Date()}"/>
                </div>

                <div id="_loteInicial" class="fieldcontain ${hasErrors(bean: reporteReliquidacionesInstance, field: 'loteInicial', 'error')} " style="display: none">
                    <label for="loteInicial">
                        <g:message code="reporteReliquidaciones.loteInicial.label" default="Lote Inicial" />

                    </label>
                    <g:textField name="loteInicial" inputmode="numeric" value="${reporteReliquidacionesInstance?.loteInicial}"/>
                </div>

                <div id="_loteFinal" class="fieldcontain ${hasErrors(bean: reporteReliquidacionesInstance, field: 'loteFinal', 'error')} " style="display: none">
                    <label for="loteFinal">
                        <g:message code="reporteReliquidaciones.loteFinal.label" default="Lote Final" />

                    </label>
                    <g:textField name="loteFinal" inputmode="numeric" value="${reporteReliquidacionesInstance?.loteFinal}"/>
                </div>

                <div id="_resultadosEstano" style="display: none">
                    <div style="text-align: center;">
                        <g:actionSubmit class="reporte" controller="reporteReliquidaciones" action="crearReporteEstano" value="Generar Reporte" />
                    </div>
                </div>

                <div id="_resultadosPlata" style="display: none">
                    <div style="text-align: center;">
                        <g:actionSubmit class="reporte" controller="reporteReliquidaciones" action="crearReportePlata" value="Generar Reporte" />
                    </div>
                </div>

                <div id="_resultadosWolfran" style="display: none">
                    <div style="text-align: center;">
                        <g:actionSubmit class="reporte" controller="reporteReliquidaciones" action="crearReporteWolfran" value="Generar Reporte" />
                    </div>
                </div>

                <div id="_resultadosAntimonio" style="display: none">
                    <div style="text-align: center;">
                        <g:actionSubmit class="reporte" controller="reporteReliquidaciones" action="crearReporteAntimonio" value="Generar Reporte" />
                    </div>
                </div>

                <div id="_resultadosComplejo" style="display: none">
                    <div style="text-align: center;">
                        <g:actionSubmit class="reporte" controller="reporteReliquidaciones" action="crearReporteComplejo" value="Generar Reporte" />
                    </div>
                </div>
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
