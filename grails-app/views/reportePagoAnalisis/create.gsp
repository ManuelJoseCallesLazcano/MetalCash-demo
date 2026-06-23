<%@ page import="org.socymet.org.socymet.reportes.ReportePagoAnalisis" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reportePagoAnalisis.label', default: 'ReportePagoAnalisis')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="reportes/pagoAnalisis.js" />
	</head>
	<body>
		<a href="#create-reportePagoAnalisis" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reportePagoAnalisis" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reportePagoAnalisisInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reportePagoAnalisisInstance}" var="error">
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

                    <div class="fieldcontain ${hasErrors(bean: reportePagoAnalisisInstance, field: 'nombreDeLaboratorio', 'error')} ">
                        <label for="nombreDeLaboratorio">
                            <g:message code="reportePagoAnalisis.nombreDeLaboratorio.label" default="Nombre De Laboratorio" />

                        </label>
                        <g:textField name="nombreDeLaboratorio" value="${reportePagoAnalisisInstance?.nombreDeLaboratorio}"/>
                        <span style="font-size: 10px; ">(Por ejemplo: CONDE. Si desea ver el resultado para todos los laboratorios deje este campo vacio.)</span>
                    </div>

                <div class="fieldcontain ${hasErrors(bean: reportePagoAnalisisInstance, field: 'fechaInicial', 'error')} required">
                    <label for="fechaInicial">
                        <g:message code="reportePagoAnalisis.fechaInicial.label" default="Fecha Inicial" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:datepickerUI name="fechaInicial" value="${reportePagoAnalisisInstance?.fechaInicial ?: new Date()}"/>
                </div>

                <div class="fieldcontain ${hasErrors(bean: reportePagoAnalisisInstance, field: 'fechaFinal', 'error')} required">
                    <label for="fechaFinal">
                        <g:message code="reportePagoAnalisis.fechaFinal.label" default="Fecha Final" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:datepickerUI name="fechaFinal" value="${reportePagoAnalisisInstance?.fechaFinal ?: new Date()}"/>
                </div>

                    <br>

                <div id="_resultados">
                    <div style="text-align: center;">
                        <g:actionSubmit class="reporte" controller="reportePagoAnalisis" action="crearReporte" value="Generar Reporte" />
                    </div>
                </div>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
