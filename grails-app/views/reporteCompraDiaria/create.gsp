<%@ page import="org.socymet.org.socymet.reportes.ReporteCompraDiaria" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteCompraDiaria.label', default: 'ReporteCompraDiaria')}" />
		<title><g:message code="default.report.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="reportes/compraDiaria.js" />
	</head>
	<body>
		<a href="#create-reporteCompraDiaria" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reporteCompraDiaria" class="content scaffold-create" role="main">
			<h1><g:message code="default.report.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteCompraDiariaInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteCompraDiariaInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
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
                        </tbody>
                    </table>
                    <h1 style="font-weight: bold">Parametros de busqueda:</h1>
                    <g:hiddenField name="tipoReporte" value="fechas" />
                    <div id="_empresa" class="fieldcontain ${hasErrors(bean: reporteCompraDiariaInstance, field: 'empresa', 'error')} " style="display: none">
                        <label for="empresa">
                            <g:message code="reporteCompraDiaria.empresa.label" default="Empresa" />

                        </label>
                        <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteCompraDiariaInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
                    </div>

                    <div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: reporteCompraDiariaInstance, field: 'fechaInicial', 'error')} ">
                        <label for="fechaInicial">
                            <g:message code="reporteCompraDiaria.fechaInicial.label" default="Fecha Inicial" />

                        </label>
                        <g:datepickerUI name="fechaInicial" value="${reporteCompraDiariaInstance?.fechaInicial ?: new Date()}"/>
                    </div>

                    <div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: reporteCompraDiariaInstance, field: 'fechaFinal', 'error')} ">
                        <label for="fechaFinal">
                            <g:message code="reporteCompraDiaria.fechaFinal.label" default="Fecha Final" />

                        </label>
                        <g:datepickerUI name="fechaFinal" value="${reporteCompraDiariaInstance?.fechaFinal ?: new Date()}"/>
                    </div>

                    <br/>

                    <div id="_resultadosEstano">
                        <div style="text-align: center;">
                            <g:actionSubmit class="reporte" controller="reporteCompraDiaria" action="crearReporte" value="Generar Reporte" />
                        </div>
                    </div>
                </fieldset>
			</g:form>
		</div>
	</body>
</html>
