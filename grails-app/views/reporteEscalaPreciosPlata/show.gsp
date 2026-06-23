
<%@ page import="org.socymet.org.socymet.reportes.ReporteEscalaPreciosPlata" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteEscalaPreciosPlata.label', default: 'ReporteEscalaPreciosPlata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reporteEscalaPreciosPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reporteEscalaPreciosPlata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reporteEscalaPreciosPlata">
			
				<g:if test="${reporteEscalaPreciosPlataInstance?.fechaCotizacion}">
				<li class="fieldcontain">
					<span id="fechaCotizacion-label" class="property-label"><g:message code="reporteEscalaPreciosPlata.fechaCotizacion.label" default="Fecha Cotizacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaCotizacion-label"><g:formatDate date="${reporteEscalaPreciosPlataInstance?.fechaCotizacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteEscalaPreciosPlataInstance?.cotizacionPlata}">
				<li class="fieldcontain">
					<span id="cotizacionPlata-label" class="property-label"><g:message code="reporteEscalaPreciosPlata.cotizacionPlata.label" default="Cotizacion Plata" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionPlata-label"><g:fieldValue bean="${reporteEscalaPreciosPlataInstance}" field="cotizacionPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteEscalaPreciosPlataInstance?.tablaCotizacionPlata}">
				<li class="fieldcontain">
					<span id="tablaCotizacionPlata-label" class="property-label"><g:message code="reporteEscalaPreciosPlata.tablaCotizacionPlata.label" default="Tabla Cotizacion Plata" /></span>
					
						<span class="property-value" aria-labelledby="tablaCotizacionPlata-label"><g:link controller="tablaCotizacionPlata" action="show" id="${reporteEscalaPreciosPlataInstance?.tablaCotizacionPlata?.id}">${reporteEscalaPreciosPlataInstance?.tablaCotizacionPlata?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${reporteEscalaPreciosPlataInstance?.id}" />
					<g:link class="edit" action="edit" id="${reporteEscalaPreciosPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
