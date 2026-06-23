
<%@ page import="org.socymet.recepcion.RecepcionGrupalDeComplejo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'recepcionGrupalDeComplejo.label', default: 'RecepcionGrupalDeComplejo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-recepcionGrupalDeComplejo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-recepcionGrupalDeComplejo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list recepcionGrupalDeComplejo">
			
				<g:if test="${recepcionGrupalDeComplejoInstance?.cliente}">
				<li class="fieldcontain">
					<span id="cliente-label" class="property-label"><g:message code="recepcionGrupalDeComplejo.cliente.label" default="Cliente" /></span>
					
						<span class="property-value" aria-labelledby="cliente-label"><g:link controller="cliente" action="show" id="${recepcionGrupalDeComplejoInstance?.cliente?.id}">${recepcionGrupalDeComplejoInstance?.cliente?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionGrupalDeComplejoInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="recepcionGrupalDeComplejo.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${recepcionGrupalDeComplejoInstance?.empresa?.id}">${recepcionGrupalDeComplejoInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionGrupalDeComplejoInstance?.chofer}">
				<li class="fieldcontain">
					<span id="chofer-label" class="property-label"><g:message code="recepcionGrupalDeComplejo.chofer.label" default="Chofer" /></span>
					
						<span class="property-value" aria-labelledby="chofer-label"><g:link controller="chofer" action="show" id="${recepcionGrupalDeComplejoInstance?.chofer?.id}">${recepcionGrupalDeComplejoInstance?.chofer?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionGrupalDeComplejoInstance?.automovil}">
				<li class="fieldcontain">
					<span id="automovil-label" class="property-label"><g:message code="recepcionGrupalDeComplejo.automovil.label" default="Automovil" /></span>
					
						<span class="property-value" aria-labelledby="automovil-label"><g:link controller="automovil" action="show" id="${recepcionGrupalDeComplejoInstance?.automovil?.id}">${recepcionGrupalDeComplejoInstance?.automovil?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionGrupalDeComplejoInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="recepcionGrupalDeComplejo.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:formatDate date="${recepcionGrupalDeComplejoInstance?.fechaDeRecepcion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionGrupalDeComplejoInstance?.deposito}">
				<li class="fieldcontain">
					<span id="deposito-label" class="property-label"><g:message code="recepcionGrupalDeComplejo.deposito.label" default="Deposito" /></span>
					
						<span class="property-value" aria-labelledby="deposito-label"><g:link controller="deposito" action="show" id="${recepcionGrupalDeComplejoInstance?.deposito?.id}">${recepcionGrupalDeComplejoInstance?.deposito?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionGrupalDeComplejoInstance?.tipoDeMineral}">
				<li class="fieldcontain">
					<span id="tipoDeMineral-label" class="property-label"><g:message code="recepcionGrupalDeComplejo.tipoDeMineral.label" default="Tipo De Mineral" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeMineral-label"><g:fieldValue bean="${recepcionGrupalDeComplejoInstance}" field="tipoDeMineral"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${recepcionGrupalDeComplejoInstance?.id}" />
					<g:link class="edit" action="edit" id="${recepcionGrupalDeComplejoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
