
<%@ page import="org.socymet.caja.MovimientoCaja" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'movimientoCaja.label', default: 'MovimientoCaja')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-movimientoCaja" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-movimientoCaja" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="numeroMovimiento" title="${message(code: 'movimientoCaja.numeroMovimiento.label', default: 'Numero Movimiento')}" />
					
						<g:sortableColumn property="fechaMovimiento" title="${message(code: 'movimientoCaja.fechaMovimiento.label', default: 'Fecha Movimiento')}" />
					
						<th><g:message code="movimientoCaja.ingreso.label" default="Ingreso" /></th>
					
						<th><g:message code="movimientoCaja.egreso.label" default="Egreso" /></th>
					
						<g:sortableColumn property="ci" title="${message(code: 'movimientoCaja.ci.label', default: 'Ci')}" />
					
						<g:sortableColumn property="nombre" title="${message(code: 'movimientoCaja.nombre.label', default: 'Nombre')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${movimientoCajaInstanceList}" status="i" var="movimientoCajaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${movimientoCajaInstance.id}">${fieldValue(bean: movimientoCajaInstance, field: "numeroMovimiento")}</g:link></td>
					
						<td><g:formatDate date="${movimientoCajaInstance.fechaMovimiento}" /></td>
					
						<td>${fieldValue(bean: movimientoCajaInstance, field: "ingreso")}</td>
					
						<td>${fieldValue(bean: movimientoCajaInstance, field: "egreso")}</td>
					
						<td>${fieldValue(bean: movimientoCajaInstance, field: "ci")}</td>
					
						<td>${fieldValue(bean: movimientoCajaInstance, field: "nombre")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${movimientoCajaInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
