
<%@ page import="org.socymet.org.socymet.reportes.ReportePlanillaTransporteOro" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reportePlanillaTransporteOro.label', default: 'ReportePlanillaTransporteOro')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reportePlanillaTransporteOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reportePlanillaTransporteOro" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reportePlanillaTransporteOro">
			
				<g:if test="${reportePlanillaTransporteOroInstance?.deposito}">
				<li class="fieldcontain">
					<span id="deposito-label" class="property-label"><g:message code="reportePlanillaTransporteOro.deposito.label" default="Deposito" /></span>
					
						<span class="property-value" aria-labelledby="deposito-label"><g:link controller="deposito" action="show" id="${reportePlanillaTransporteOroInstance?.deposito?.id}">${reportePlanillaTransporteOroInstance?.deposito?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePlanillaTransporteOroInstance?.elemento}">
				<li class="fieldcontain">
					<span id="elemento-label" class="property-label"><g:message code="reportePlanillaTransporteOro.elemento.label" default="Elemento" /></span>
					
						<span class="property-value" aria-labelledby="elemento-label"><g:fieldValue bean="${reportePlanillaTransporteOroInstance}" field="elemento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePlanillaTransporteOroInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="reportePlanillaTransporteOro.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${reportePlanillaTransporteOroInstance?.empresa?.id}">${reportePlanillaTransporteOroInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePlanillaTransporteOroInstance?.fechaInicial}">
				<li class="fieldcontain">
					<span id="fechaInicial-label" class="property-label"><g:message code="reportePlanillaTransporteOro.fechaInicial.label" default="Fecha Inicial" /></span>
					
						<span class="property-value" aria-labelledby="fechaInicial-label"><g:formatDate date="${reportePlanillaTransporteOroInstance?.fechaInicial}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePlanillaTransporteOroInstance?.fechaFinal}">
				<li class="fieldcontain">
					<span id="fechaFinal-label" class="property-label"><g:message code="reportePlanillaTransporteOro.fechaFinal.label" default="Fecha Final" /></span>
					
						<span class="property-value" aria-labelledby="fechaFinal-label"><g:formatDate date="${reportePlanillaTransporteOroInstance?.fechaFinal}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePlanillaTransporteOroInstance?.loteInicial}">
				<li class="fieldcontain">
					<span id="loteInicial-label" class="property-label"><g:message code="reportePlanillaTransporteOro.loteInicial.label" default="Lote Inicial" /></span>
					
						<span class="property-value" aria-labelledby="loteInicial-label"><g:fieldValue bean="${reportePlanillaTransporteOroInstance}" field="loteInicial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePlanillaTransporteOroInstance?.loteFinal}">
				<li class="fieldcontain">
					<span id="loteFinal-label" class="property-label"><g:message code="reportePlanillaTransporteOro.loteFinal.label" default="Lote Final" /></span>
					
						<span class="property-value" aria-labelledby="loteFinal-label"><g:fieldValue bean="${reportePlanillaTransporteOroInstance}" field="loteFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePlanillaTransporteOroInstance?.estado}">
				<li class="fieldcontain">
					<span id="estado-label" class="property-label"><g:message code="reportePlanillaTransporteOro.estado.label" default="Estado" /></span>
					
						<span class="property-value" aria-labelledby="estado-label"><g:fieldValue bean="${reportePlanillaTransporteOroInstance}" field="estado"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:reportePlanillaTransporteOroInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${reportePlanillaTransporteOroInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
