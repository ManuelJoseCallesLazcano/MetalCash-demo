<%@ page import="org.socymet.org.socymet.reportes.PlanillaDeLiquidacion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'planillaDeLiquidacion.label', default: 'PlanillaDeLiquidacion')}" />
		<title><g:message code="default.report.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'chosen.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="chosen.jquery.js" />
        <g:javascript src="reportes/historialEmpresa.js" />
	</head>
	<body>
		<a href="#create-planillaDeLiquidacion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-planillaDeLiquidacion" class="content scaffold-create" role="main">
			<h1><g:message code="default.report.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${planillaDeLiquidacionInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${planillaDeLiquidacionInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
                    <h1 style="font-weight: bold">Parametros de busqueda:</h1>
                    <g:hiddenField name="tipoReporte" value="fechasEmpresa" />
                    <div id="_empresa" class="fieldcontain ${hasErrors(bean: planillaDeLiquidacionInstance, field: 'empresa', 'error')} ">
                        <label for="empresa">
                            <g:message code="planillaDeLiquidacion.empresa.label" default="Empresa" />

                        </label>
                        <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${planillaDeLiquidacionInstance?.empresa?.id}" class="many-to-one, chosen-select" style="width: 350px"/>
                    </div>

                    <div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: planillaDeLiquidacionInstance, field: 'fechaInicial', 'error')} ">
                        <label for="fechaInicial">
                            <g:message code="planillaDeLiquidacion.fechaInicial.label" default="Fecha Inicial" />

                        </label>
                        <g:datepickerUI name="fechaInicial" value="${planillaDeLiquidacionInstance?.fechaInicial ?: new Date()}"/>
                    </div>

                    <div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: planillaDeLiquidacionInstance, field: 'fechaFinal', 'error')} ">
                        <label for="fechaFinal">
                            <g:message code="planillaDeLiquidacion.fechaFinal.label" default="Fecha Final" />

                        </label>
                        <g:datepickerUI name="fechaFinal" value="${planillaDeLiquidacionInstance?.fechaFinal ?: new Date()}"/>
                    </div>

                    <br/>

                    <div id="_resultadosEstano">
                        <div style="text-align: center;">
                            <g:actionSubmit class="reporte" controller="planillaDeLiquidacion" action="crearReporte" value="Generar Reporte" />
                        </div>
                    </div>                
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
