
<%@ page import="org.socymet.caja.CierreCajaDetalle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cierreCajaDetalle.label', default: 'CierreCajaDetalle')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-cierreCajaDetalle" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-cierreCajaDetalle" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list cierreCajaDetalle">
			
				<g:if test="${cierreCajaDetalleInstance?.cierreCaja}">
				<li class="fieldcontain">
					<span id="cierreCaja-label" class="property-label"><g:message code="cierreCajaDetalle.cierreCaja.label" default="Cierre Caja" /></span>
					
						<span class="property-value" aria-labelledby="cierreCaja-label"><g:link controller="cierreCaja" action="show" id="${cierreCajaDetalleInstance?.cierreCaja?.id}">${cierreCajaDetalleInstance?.cierreCaja?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaDetalleInstance?.movimientoCaja}">
				<li class="fieldcontain">
					<span id="movimientoCaja-label" class="property-label"><g:message code="cierreCajaDetalle.movimientoCaja.label" default="Movimiento Caja" /></span>
					
						<span class="property-value" aria-labelledby="movimientoCaja-label"><g:link controller="movimientoCaja" action="show" id="${cierreCajaDetalleInstance?.movimientoCaja?.id}">${cierreCajaDetalleInstance?.movimientoCaja?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaDetalleInstance?.numeroMovimiento}">
				<li class="fieldcontain">
					<span id="numeroMovimiento-label" class="property-label"><g:message code="cierreCajaDetalle.numeroMovimiento.label" default="Numero Movimiento" /></span>
					
						<span class="property-value" aria-labelledby="numeroMovimiento-label"><g:fieldValue bean="${cierreCajaDetalleInstance}" field="numeroMovimiento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaDetalleInstance?.fechaMovimiento}">
				<li class="fieldcontain">
					<span id="fechaMovimiento-label" class="property-label"><g:message code="cierreCajaDetalle.fechaMovimiento.label" default="Fecha Movimiento" /></span>
					
						<span class="property-value" aria-labelledby="fechaMovimiento-label"><g:fieldValue bean="${cierreCajaDetalleInstance}" field="fechaMovimiento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaDetalleInstance?.ingreso}">
				<li class="fieldcontain">
					<span id="ingreso-label" class="property-label"><g:message code="cierreCajaDetalle.ingreso.label" default="Ingreso" /></span>
					
						<span class="property-value" aria-labelledby="ingreso-label"><g:link controller="ingreso" action="show" id="${cierreCajaDetalleInstance?.ingreso?.id}">${cierreCajaDetalleInstance?.ingreso?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaDetalleInstance?.egreso}">
				<li class="fieldcontain">
					<span id="egreso-label" class="property-label"><g:message code="cierreCajaDetalle.egreso.label" default="Egreso" /></span>
					
						<span class="property-value" aria-labelledby="egreso-label"><g:link controller="egreso" action="show" id="${cierreCajaDetalleInstance?.egreso?.id}">${cierreCajaDetalleInstance?.egreso?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaDetalleInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="cierreCajaDetalle.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${cierreCajaDetalleInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaDetalleInstance?.nombre}">
				<li class="fieldcontain">
					<span id="nombre-label" class="property-label"><g:message code="cierreCajaDetalle.nombre.label" default="Nombre" /></span>
					
						<span class="property-value" aria-labelledby="nombre-label"><g:fieldValue bean="${cierreCajaDetalleInstance}" field="nombre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaDetalleInstance?.concepto}">
				<li class="fieldcontain">
					<span id="concepto-label" class="property-label"><g:message code="cierreCajaDetalle.concepto.label" default="Concepto" /></span>
					
						<span class="property-value" aria-labelledby="concepto-label"><g:fieldValue bean="${cierreCajaDetalleInstance}" field="concepto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaDetalleInstance?.debe}">
				<li class="fieldcontain">
					<span id="debe-label" class="property-label"><g:message code="cierreCajaDetalle.debe.label" default="Debe" /></span>
					
						<span class="property-value" aria-labelledby="debe-label"><g:fieldValue bean="${cierreCajaDetalleInstance}" field="debe"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaDetalleInstance?.haber}">
				<li class="fieldcontain">
					<span id="haber-label" class="property-label"><g:message code="cierreCajaDetalle.haber.label" default="Haber" /></span>
					
						<span class="property-value" aria-labelledby="haber-label"><g:fieldValue bean="${cierreCajaDetalleInstance}" field="haber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaDetalleInstance?.saldo}">
				<li class="fieldcontain">
					<span id="saldo-label" class="property-label"><g:message code="cierreCajaDetalle.saldo.label" default="Saldo" /></span>
					
						<span class="property-value" aria-labelledby="saldo-label"><g:fieldValue bean="${cierreCajaDetalleInstance}" field="saldo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaDetalleInstance?.usuario}">
				<li class="fieldcontain">
					<span id="usuario-label" class="property-label"><g:message code="cierreCajaDetalle.usuario.label" default="Usuario" /></span>
					
						<span class="property-value" aria-labelledby="usuario-label"><g:link controller="secUser" action="show" id="${cierreCajaDetalleInstance?.usuario?.id}">${cierreCajaDetalleInstance?.usuario?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:cierreCajaDetalleInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${cierreCajaDetalleInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
