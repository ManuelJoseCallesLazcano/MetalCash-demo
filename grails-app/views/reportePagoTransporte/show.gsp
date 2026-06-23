
<%@ page import="org.socymet.org.socymet.reportes.ReportePagoTransporte" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reportePagoTransporte.label', default: 'ReportePagoTransporte')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reportePagoTransporte" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reportePagoTransporte" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reportePagoTransporte">
			
				<g:if test="${reportePagoTransporteInstance?.elemento}">
				<li class="fieldcontain">
					<span id="elemento-label" class="property-label"><g:message code="reportePagoTransporte.elemento.label" default="Elemento" /></span>
					
						<span class="property-value" aria-labelledby="elemento-label"><g:fieldValue bean="${reportePagoTransporteInstance}" field="elemento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePagoTransporteInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="reportePagoTransporte.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${reportePagoTransporteInstance?.empresa?.id}">${reportePagoTransporteInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePagoTransporteInstance?.fechaInicial}">
				<li class="fieldcontain">
					<span id="fechaInicial-label" class="property-label"><g:message code="reportePagoTransporte.fechaInicial.label" default="Fecha Inicial" /></span>
					
						<span class="property-value" aria-labelledby="fechaInicial-label"><g:formatDate date="${reportePagoTransporteInstance?.fechaInicial}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePagoTransporteInstance?.fechaFinal}">
				<li class="fieldcontain">
					<span id="fechaFinal-label" class="property-label"><g:message code="reportePagoTransporte.fechaFinal.label" default="Fecha Final" /></span>
					
						<span class="property-value" aria-labelledby="fechaFinal-label"><g:formatDate date="${reportePagoTransporteInstance?.fechaFinal}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePagoTransporteInstance?.loteInicial}">
				<li class="fieldcontain">
					<span id="loteInicial-label" class="property-label"><g:message code="reportePagoTransporte.loteInicial.label" default="Lote Inicial" /></span>
					
						<span class="property-value" aria-labelledby="loteInicial-label"><g:fieldValue bean="${reportePagoTransporteInstance}" field="loteInicial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePagoTransporteInstance?.loteFinal}">
				<li class="fieldcontain">
					<span id="loteFinal-label" class="property-label"><g:message code="reportePagoTransporte.loteFinal.label" default="Lote Final" /></span>
					
						<span class="property-value" aria-labelledby="loteFinal-label"><g:fieldValue bean="${reportePagoTransporteInstance}" field="loteFinal"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${reportePagoTransporteInstance?.id}" />
					<g:link class="edit" action="edit" id="${reportePagoTransporteInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
