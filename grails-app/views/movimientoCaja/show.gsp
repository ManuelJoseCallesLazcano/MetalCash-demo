
<%@ page import="org.socymet.caja.MovimientoCaja" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'movimientoCaja.label', default: 'MovimientoCaja')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-movimientoCaja" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-movimientoCaja" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list movimientoCaja">
			
				<g:if test="${movimientoCajaInstance?.numeroMovimiento}">
				<li class="fieldcontain">
					<span id="numeroMovimiento-label" class="property-label"><g:message code="movimientoCaja.numeroMovimiento.label" default="Numero Movimiento" /></span>
					
						<span class="property-value" aria-labelledby="numeroMovimiento-label"><g:fieldValue bean="${movimientoCajaInstance}" field="numeroMovimiento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.fechaMovimiento}">
				<li class="fieldcontain">
					<span id="fechaMovimiento-label" class="property-label"><g:message code="movimientoCaja.fechaMovimiento.label" default="Fecha Movimiento" /></span>
					
						<span class="property-value" aria-labelledby="fechaMovimiento-label"><g:formatDate date="${movimientoCajaInstance?.fechaMovimiento}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.ingreso}">
				<li class="fieldcontain">
					<span id="ingreso-label" class="property-label"><g:message code="movimientoCaja.ingreso.label" default="Ingreso" /></span>
					
						<span class="property-value" aria-labelledby="ingreso-label"><g:link controller="ingreso" action="show" id="${movimientoCajaInstance?.ingreso?.id}">${movimientoCajaInstance?.ingreso?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.egreso}">
				<li class="fieldcontain">
					<span id="egreso-label" class="property-label"><g:message code="movimientoCaja.egreso.label" default="Egreso" /></span>
					
						<span class="property-value" aria-labelledby="egreso-label"><g:link controller="egreso" action="show" id="${movimientoCajaInstance?.egreso?.id}">${movimientoCajaInstance?.egreso?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="movimientoCaja.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${movimientoCajaInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.nombre}">
				<li class="fieldcontain">
					<span id="nombre-label" class="property-label"><g:message code="movimientoCaja.nombre.label" default="Nombre" /></span>
					
						<span class="property-value" aria-labelledby="nombre-label"><g:fieldValue bean="${movimientoCajaInstance}" field="nombre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.concepto}">
				<li class="fieldcontain">
					<span id="concepto-label" class="property-label"><g:message code="movimientoCaja.concepto.label" default="Concepto" /></span>
					
						<span class="property-value" aria-labelledby="concepto-label"><g:fieldValue bean="${movimientoCajaInstance}" field="concepto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.debe}">
				<li class="fieldcontain">
					<span id="debe-label" class="property-label"><g:message code="movimientoCaja.debe.label" default="Debe" /></span>
					
						<span class="property-value" aria-labelledby="debe-label"><g:fieldValue bean="${movimientoCajaInstance}" field="debe"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.haber}">
				<li class="fieldcontain">
					<span id="haber-label" class="property-label"><g:message code="movimientoCaja.haber.label" default="Haber" /></span>
					
						<span class="property-value" aria-labelledby="haber-label"><g:fieldValue bean="${movimientoCajaInstance}" field="haber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.saldo}">
				<li class="fieldcontain">
					<span id="saldo-label" class="property-label"><g:message code="movimientoCaja.saldo.label" default="Saldo" /></span>
					
						<span class="property-value" aria-labelledby="saldo-label"><g:fieldValue bean="${movimientoCajaInstance}" field="saldo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.consolidado}">
				<li class="fieldcontain">
					<span id="consolidado-label" class="property-label"><g:message code="movimientoCaja.consolidado.label" default="Consolidado" /></span>
					
						<span class="property-value" aria-labelledby="consolidado-label"><g:fieldValue bean="${movimientoCajaInstance}" field="consolidado"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.usuario}">
				<li class="fieldcontain">
					<span id="usuario-label" class="property-label"><g:message code="movimientoCaja.usuario.label" default="Usuario" /></span>
					
						<span class="property-value" aria-labelledby="usuario-label"><g:link controller="secUser" action="show" id="${movimientoCajaInstance?.usuario?.id}">${movimientoCajaInstance?.usuario?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="movimientoCaja.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${movimientoCajaInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${movimientoCajaInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="movimientoCaja.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${movimientoCajaInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:movimientoCajaInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${movimientoCajaInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
