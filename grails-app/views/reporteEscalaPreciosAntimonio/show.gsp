
<%@ page import="org.socymet.org.socymet.reportes.ReporteEscalaPreciosAntimonio" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteEscalaPreciosAntimonio.label', default: 'ReporteEscalaPreciosAntimonio')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reporteEscalaPreciosAntimonio" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reporteEscalaPreciosAntimonio" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reporteEscalaPreciosAntimonio">
			
				<g:if test="${reporteEscalaPreciosAntimonioInstance?.fechaCotizacion}">
				<li class="fieldcontain">
					<span id="fechaCotizacion-label" class="property-label"><g:message code="reporteEscalaPreciosAntimonio.fechaCotizacion.label" default="Fecha Cotizacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaCotizacion-label"><g:formatDate date="${reporteEscalaPreciosAntimonioInstance?.fechaCotizacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteEscalaPreciosAntimonioInstance?.tablaCotizacionAntimonio}">
				<li class="fieldcontain">
					<span id="tablaCotizacionAntimonio-label" class="property-label"><g:message code="reporteEscalaPreciosAntimonio.tablaCotizacionAntimonio.label" default="Tabla Cotizacion Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="tablaCotizacionAntimonio-label"><g:link controller="tablaCotizacionAntimonio" action="show" id="${reporteEscalaPreciosAntimonioInstance?.tablaCotizacionAntimonio?.id}">${reporteEscalaPreciosAntimonioInstance?.tablaCotizacionAntimonio?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${reporteEscalaPreciosAntimonioInstance?.id}" />
					<g:link class="edit" action="edit" id="${reporteEscalaPreciosAntimonioInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
