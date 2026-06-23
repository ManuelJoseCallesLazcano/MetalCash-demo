
<%@ page import="org.socymet.org.socymet.reportes.ReporteGraficoCantidad" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteGraficoCantidad.label', default: 'ReporteGraficoCantidad')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reporteGraficoCantidad" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reporteGraficoCantidad" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reporteGraficoCantidad">
			
				<g:if test="${reporteGraficoCantidadInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="reporteGraficoCantidad.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${reporteGraficoCantidadInstance?.empresa?.id}">${reporteGraficoCantidadInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteGraficoCantidadInstance?.elemento}">
				<li class="fieldcontain">
					<span id="elemento-label" class="property-label"><g:message code="reporteGraficoCantidad.elemento.label" default="Elemento" /></span>
					
						<span class="property-value" aria-labelledby="elemento-label"><g:fieldValue bean="${reporteGraficoCantidadInstance}" field="elemento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteGraficoCantidadInstance?.fechaInicial}">
				<li class="fieldcontain">
					<span id="fechaInicial-label" class="property-label"><g:message code="reporteGraficoCantidad.fechaInicial.label" default="Fecha Inicial" /></span>
					
						<span class="property-value" aria-labelledby="fechaInicial-label"><g:formatDate date="${reporteGraficoCantidadInstance?.fechaInicial}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteGraficoCantidadInstance?.fechaFinal}">
				<li class="fieldcontain">
					<span id="fechaFinal-label" class="property-label"><g:message code="reporteGraficoCantidad.fechaFinal.label" default="Fecha Final" /></span>
					
						<span class="property-value" aria-labelledby="fechaFinal-label"><g:formatDate date="${reporteGraficoCantidadInstance?.fechaFinal}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${reporteGraficoCantidadInstance?.id}" />
					<g:link class="edit" action="edit" id="${reporteGraficoCantidadInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
