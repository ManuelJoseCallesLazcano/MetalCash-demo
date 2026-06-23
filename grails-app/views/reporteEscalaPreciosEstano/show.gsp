
<%@ page import="org.socymet.org.socymet.reportes.ReporteEscalaPreciosEstano" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteEscalaPreciosEstano.label', default: 'ReporteEscalaPreciosEstano')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reporteEscalaPreciosEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reporteEscalaPreciosEstano" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reporteEscalaPreciosEstano">
			
				<g:if test="${reporteEscalaPreciosEstanoInstance?.fechaCotizacion}">
				<li class="fieldcontain">
					<span id="fechaCotizacion-label" class="property-label"><g:message code="reporteEscalaPreciosEstano.fechaCotizacion.label" default="Fecha Cotizacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaCotizacion-label"><g:formatDate date="${reporteEscalaPreciosEstanoInstance?.fechaCotizacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteEscalaPreciosEstanoInstance?.cotizacionEstano}">
				<li class="fieldcontain">
					<span id="cotizacionEstano-label" class="property-label"><g:message code="reporteEscalaPreciosEstano.cotizacionEstano.label" default="Cotizacion Estano" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionEstano-label"><g:fieldValue bean="${reporteEscalaPreciosEstanoInstance}" field="cotizacionEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteEscalaPreciosEstanoInstance?.tablaCotizacionEstano}">
				<li class="fieldcontain">
					<span id="tablaCotizacionEstano-label" class="property-label"><g:message code="reporteEscalaPreciosEstano.tablaCotizacionEstano.label" default="Tabla Cotizacion Estano" /></span>
					
						<span class="property-value" aria-labelledby="tablaCotizacionEstano-label"><g:link controller="tablaCotizacionEstano" action="show" id="${reporteEscalaPreciosEstanoInstance?.tablaCotizacionEstano?.id}">${reporteEscalaPreciosEstanoInstance?.tablaCotizacionEstano?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${reporteEscalaPreciosEstanoInstance?.id}" />
					<g:link class="edit" action="edit" id="${reporteEscalaPreciosEstanoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
