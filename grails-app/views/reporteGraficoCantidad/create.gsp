<%@ page import="org.socymet.org.socymet.reportes.ReporteGraficoCantidad" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteGraficoCantidad.label', default: 'ReporteGraficoCantidad')}" />
		<title><g:message code="default.report.label" args="[entityName]" /></title>
        <g:javascript src="jquery-1.10.1.min.js" />
%{--        <g:javascript src="highstock.js" />--}%
%{--        <g:javascript src="exporting.js" />--}%
		<script src="${assetPath(src: 'vendor/highcharts.js')}"></script>
		<script src="${assetPath(src: 'vendor/highcharts-exporting.js')}"></script>
		<script src="${assetPath(src: 'vendor/highcharts-export-data.js')}"></script>
		<script src="${assetPath(src: 'vendor/highcharts-accessibility.js')}"></script>
        <g:javascript src="reportes/graficoCantidad.js" />
	</head>
	<body>
		<a href="#create-reporteGraficoCantidad" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reporteGraficoCantidad" class="content scaffold-create" role="main">
			<h1><g:message code="default.report.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteGraficoCantidadInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteGraficoCantidadInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
