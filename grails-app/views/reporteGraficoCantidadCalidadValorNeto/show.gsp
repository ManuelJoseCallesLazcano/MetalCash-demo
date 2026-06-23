
<%@ page import="org.socymet.org.socymet.reportes.ReporteGraficoCantidadCalidadValorNeto" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteGraficoCantidadCalidadValorNeto.label', default: 'ReporteGraficoCantidadCalidadValorNeto')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reporteGraficoCantidadCalidadValorNeto" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reporteGraficoCantidadCalidadValorNeto" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reporteGraficoCantidadCalidadValorNeto">
			
				<g:if test="${reporteGraficoCantidadCalidadValorNetoInstance?.elemento}">
				<li class="fieldcontain">
					<span id="elemento-label" class="property-label"><g:message code="reporteGraficoCantidadCalidadValorNeto.elemento.label" default="Elemento" /></span>
					
						<span class="property-value" aria-labelledby="elemento-label"><g:fieldValue bean="${reporteGraficoCantidadCalidadValorNetoInstance}" field="elemento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteGraficoCantidadCalidadValorNetoInstance?.fechaInicial}">
				<li class="fieldcontain">
					<span id="fechaInicial-label" class="property-label"><g:message code="reporteGraficoCantidadCalidadValorNeto.fechaInicial.label" default="Fecha Inicial" /></span>
					
						<span class="property-value" aria-labelledby="fechaInicial-label"><g:formatDate date="${reporteGraficoCantidadCalidadValorNetoInstance?.fechaInicial}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteGraficoCantidadCalidadValorNetoInstance?.fechaFinal}">
				<li class="fieldcontain">
					<span id="fechaFinal-label" class="property-label"><g:message code="reporteGraficoCantidadCalidadValorNeto.fechaFinal.label" default="Fecha Final" /></span>
					
						<span class="property-value" aria-labelledby="fechaFinal-label"><g:formatDate date="${reporteGraficoCantidadCalidadValorNetoInstance?.fechaFinal}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${reporteGraficoCantidadCalidadValorNetoInstance?.id}" />
					<g:link class="edit" action="edit" id="${reporteGraficoCantidadCalidadValorNetoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
