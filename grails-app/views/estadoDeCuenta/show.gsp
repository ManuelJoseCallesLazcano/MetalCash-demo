
<%@ page import="org.socymet.anticipos.EstadoDeCuenta" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'estadoDeCuenta.label', default: 'EstadoDeCuenta')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-estadoDeCuenta" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-estadoDeCuenta" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list estadoDeCuenta">
			
				<g:if test="${estadoDeCuentaInstance?.cliente}">
				<li class="fieldcontain">
					<span id="cliente-label" class="property-label"><g:message code="estadoDeCuenta.cliente.label" default="Cliente" /></span>
					
						<span class="property-value" aria-labelledby="cliente-label"><g:link controller="cliente" action="show" id="${estadoDeCuentaInstance?.cliente?.id}">${estadoDeCuentaInstance?.cliente?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoDeCuentaInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="estadoDeCuenta.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${estadoDeCuentaInstance?.empresa?.id}">${estadoDeCuentaInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoDeCuentaInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="estadoDeCuenta.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${estadoDeCuentaInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoDeCuentaInstance?.nombre}">
				<li class="fieldcontain">
					<span id="nombre-label" class="property-label"><g:message code="estadoDeCuenta.nombre.label" default="Nombre" /></span>
					
						<span class="property-value" aria-labelledby="nombre-label"><g:fieldValue bean="${estadoDeCuentaInstance}" field="nombre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoDeCuentaInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="estadoDeCuenta.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${estadoDeCuentaInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoDeCuentaInstance?.fecha}">
				<li class="fieldcontain">
					<span id="fecha-label" class="property-label"><g:message code="estadoDeCuenta.fecha.label" default="Fecha" /></span>
					
						<span class="property-value" aria-labelledby="fecha-label"><g:formatDate date="${estadoDeCuentaInstance?.fecha}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoDeCuentaInstance?.numeroComprobante}">
				<li class="fieldcontain">
					<span id="numeroComprobante-label" class="property-label"><g:message code="estadoDeCuenta.numeroComprobante.label" default="Numero Comprobante" /></span>
					
						<span class="property-value" aria-labelledby="numeroComprobante-label"><g:fieldValue bean="${estadoDeCuentaInstance}" field="numeroComprobante"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoDeCuentaInstance?.detalle}">
				<li class="fieldcontain">
					<span id="detalle-label" class="property-label"><g:message code="estadoDeCuenta.detalle.label" default="Detalle" /></span>
					
						<span class="property-value" aria-labelledby="detalle-label"><g:fieldValue bean="${estadoDeCuentaInstance}" field="detalle"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoDeCuentaInstance?.debe}">
				<li class="fieldcontain">
					<span id="debe-label" class="property-label"><g:message code="estadoDeCuenta.debe.label" default="Debe" /></span>
					
						<span class="property-value" aria-labelledby="debe-label"><g:fieldValue bean="${estadoDeCuentaInstance}" field="debe"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoDeCuentaInstance?.haber}">
				<li class="fieldcontain">
					<span id="haber-label" class="property-label"><g:message code="estadoDeCuenta.haber.label" default="Haber" /></span>
					
						<span class="property-value" aria-labelledby="haber-label"><g:fieldValue bean="${estadoDeCuentaInstance}" field="haber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${estadoDeCuentaInstance?.saldo}">
				<li class="fieldcontain">
					<span id="saldo-label" class="property-label"><g:message code="estadoDeCuenta.saldo.label" default="Saldo" /></span>
					
						<span class="property-value" aria-labelledby="saldo-label"><g:fieldValue bean="${estadoDeCuentaInstance}" field="saldo"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${estadoDeCuentaInstance?.id}" />
					<g:link class="edit" action="edit" id="${estadoDeCuentaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
