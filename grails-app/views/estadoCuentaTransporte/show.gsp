
<%@ page import="org.socymet.cancelacion.EstadoCuentaTransporte" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'estadoCuentaTransporte.label', default: 'EstadoCuentaTransporte')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-estadoCuentaTransporte" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-estadoCuentaTransporte" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list estadoCuentaTransporte">
			
				<g:if test="${estadoCuentaTransporteInstance?.solicitante}">
				<li class="fieldcontain">
					<span id="solicitante-label" class="property-label"><g:message code="estadoCuentaTransporte.solicitante.label" default="Solicitante" /></span>
					
						<span class="property-value" aria-labelledby="solicitante-label"><g:fieldValue bean="${estadoCuentaTransporteInstance}" field="solicitante"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoCuentaTransporteInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="estadoCuentaTransporte.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${estadoCuentaTransporteInstance?.empresa?.id}">${estadoCuentaTransporteInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoCuentaTransporteInstance?.automovil}">
				<li class="fieldcontain">
					<span id="automovil-label" class="property-label"><g:message code="estadoCuentaTransporte.automovil.label" default="Automovil" /></span>
					
						<span class="property-value" aria-labelledby="automovil-label"><g:link controller="automovil" action="show" id="${estadoCuentaTransporteInstance?.automovil?.id}">${estadoCuentaTransporteInstance?.automovil?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoCuentaTransporteInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="estadoCuentaTransporte.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${estadoCuentaTransporteInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoCuentaTransporteInstance?.nombreResponsable}">
				<li class="fieldcontain">
					<span id="nombreResponsable-label" class="property-label"><g:message code="estadoCuentaTransporte.nombreResponsable.label" default="Nombre Responsable" /></span>
					
						<span class="property-value" aria-labelledby="nombreResponsable-label"><g:fieldValue bean="${estadoCuentaTransporteInstance}" field="nombreResponsable"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoCuentaTransporteInstance?.fecha}">
				<li class="fieldcontain">
					<span id="fecha-label" class="property-label"><g:message code="estadoCuentaTransporte.fecha.label" default="Fecha" /></span>
					
						<span class="property-value" aria-labelledby="fecha-label"><g:formatDate date="${estadoCuentaTransporteInstance?.fecha}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoCuentaTransporteInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="estadoCuentaTransporte.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${estadoCuentaTransporteInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoCuentaTransporteInstance?.ingreso}">
				<li class="fieldcontain">
					<span id="ingreso-label" class="property-label"><g:message code="estadoCuentaTransporte.ingreso.label" default="Ingreso" /></span>
					
						<span class="property-value" aria-labelledby="ingreso-label"><g:fieldValue bean="${estadoCuentaTransporteInstance}" field="ingreso"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoCuentaTransporteInstance?.egreso}">
				<li class="fieldcontain">
					<span id="egreso-label" class="property-label"><g:message code="estadoCuentaTransporte.egreso.label" default="Egreso" /></span>
					
						<span class="property-value" aria-labelledby="egreso-label"><g:fieldValue bean="${estadoCuentaTransporteInstance}" field="egreso"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoCuentaTransporteInstance?.saldo}">
				<li class="fieldcontain">
					<span id="saldo-label" class="property-label"><g:message code="estadoCuentaTransporte.saldo.label" default="Saldo" /></span>
					
						<span class="property-value" aria-labelledby="saldo-label"><g:fieldValue bean="${estadoCuentaTransporteInstance}" field="saldo"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${estadoCuentaTransporteInstance?.id}" />
					<g:link class="edit" action="edit" id="${estadoCuentaTransporteInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
