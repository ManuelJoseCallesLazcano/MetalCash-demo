
<%@ page import="org.socymet.caja.CierreCajaDetalle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cierreCajaDetalle.label', default: 'CierreCajaDetalle')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-cierreCajaDetalle" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-cierreCajaDetalle" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="cierreCajaDetalle.cierreCaja.label" default="Cierre Caja" /></th>
					
						<th><g:message code="cierreCajaDetalle.movimientoCaja.label" default="Movimiento Caja" /></th>
					
						<g:sortableColumn property="numeroMovimiento" title="${message(code: 'cierreCajaDetalle.numeroMovimiento.label', default: 'Numero Movimiento')}" />
					
						<g:sortableColumn property="fechaMovimiento" title="${message(code: 'cierreCajaDetalle.fechaMovimiento.label', default: 'Fecha Movimiento')}" />
					
						<th><g:message code="cierreCajaDetalle.ingreso.label" default="Ingreso" /></th>
					
						<th><g:message code="cierreCajaDetalle.egreso.label" default="Egreso" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${cierreCajaDetalleInstanceList}" status="i" var="cierreCajaDetalleInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${cierreCajaDetalleInstance.id}">${fieldValue(bean: cierreCajaDetalleInstance, field: "cierreCaja")}</g:link></td>
					
						<td>${fieldValue(bean: cierreCajaDetalleInstance, field: "movimientoCaja")}</td>
					
						<td>${fieldValue(bean: cierreCajaDetalleInstance, field: "numeroMovimiento")}</td>
					
						<td>${fieldValue(bean: cierreCajaDetalleInstance, field: "fechaMovimiento")}</td>
					
						<td>${fieldValue(bean: cierreCajaDetalleInstance, field: "ingreso")}</td>
					
						<td>${fieldValue(bean: cierreCajaDetalleInstance, field: "egreso")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${cierreCajaDetalleInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
