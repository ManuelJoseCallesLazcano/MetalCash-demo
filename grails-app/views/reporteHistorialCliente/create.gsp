<%@ page import="org.socymet.org.socymet.reportes.ReporteHistorialCliente" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteHistorialCliente.label', default: 'ReporteHistorialCliente')}" />
		<title><g:message code="default.report.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="jquery.jgrowl.min.js" />
        <g:javascript src="reportes/clienteAutocomplete.js" />
	</head>
	<body>
		<a href="#create-reporteHistorialCliente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reporteHistorialCliente" class="content scaffold-create" role="main">
			<h1><g:message code="default.report.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteHistorialClienteInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteHistorialClienteInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">

                    <div class="fieldcontain required">
                        <label>
                            CI de Cliente
                        </label>
                        <g:textField name="ciCliente" value=""/>
                        <g:hiddenField name="cliente.id" value="${reporteHistorialClienteInstance?.cliente?.id}"/>
                    </div>

                    <div class="fieldcontain required">
                        <label>
                            Nombre de Cliente
                        </label>
                        <g:textField name="nombreCliente" value="" size="50" />
                    </div>

                    <div class="fieldcontain required">
                        <label>
                            Empresa
                        </label>
                        <g:textField name="nombreEmpresa" value="" class="amarillo" size="50" readonly="true"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: reporteHistorialClienteInstance, field: 'fechaInicial', 'error')} ">
                        <label for="fechaInicial">
                            <g:message code="reporteHistorialCliente.fechaInicial.label" default="Fecha Inicial" />

                        </label>
                        <g:datepickerUI name="fechaInicial" value="${reporteHistorialClienteInstance?.fechaInicial ?: new Date()}"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: reporteHistorialClienteInstance, field: 'fechaFinal', 'error')} ">
                        <label for="fechaFinal">
                            <g:message code="reporteHistorialCliente.fechaFinal.label" default="Fecha Final" />

                        </label>
                        <g:datepickerUI name="fechaFinal" value="${reporteHistorialClienteInstance?.fechaFinal ?: new Date()}"/>
                    </div>

                    <br>

                    <div id="_resultados">
                        <div style="text-align: center;">
                            <g:actionSubmit class="reporte" controller="reporteHistorialCliente" action="crearReporte" value="Generar Reporte" />
                        </div>
                    </div>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
