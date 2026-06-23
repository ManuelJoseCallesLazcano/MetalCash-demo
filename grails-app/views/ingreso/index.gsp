
<%@ page import="org.socymet.caja.Ingreso" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ingreso.label', default: 'Ingreso')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-ingreso" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-ingreso" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="numeroIngreso" title="${message(code: 'ingreso.numeroIngreso.label', default: 'Numero Ingreso')}" />
					
						<g:sortableColumn property="fechaIngreso" title="${message(code: 'ingreso.fechaIngreso.label', default: 'Fecha Ingreso')}" />
					
						<g:sortableColumn property="ci" title="${message(code: 'ingreso.ci.label', default: 'Ci')}" />
					
						<g:sortableColumn property="nombre" title="${message(code: 'ingreso.nombre.label', default: 'Nombre')}" />
					
						<th><g:message code="ingreso.cuenta.label" default="Cuenta" /></th>
					
						<th><g:message code="ingreso.subcuenta.label" default="Subcuenta" /></th>

						<g:sortableColumn property="importe" title="${message(code: 'ingreso.importe.label', default: 'Importe')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${ingresoInstanceList}" status="i" var="ingresoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${ingresoInstance.id}"><g:formatNumber number="${ingresoInstance.numeroIngreso}" format="000000"/></g:link></td>
					
						<td><g:formatDate date="${ingresoInstance.fechaIngreso}" /></td>
					
						<td>${fieldValue(bean: ingresoInstance, field: "ci")}</td>
					
						<td>${fieldValue(bean: ingresoInstance, field: "nombre")}</td>
					
						<td>${fieldValue(bean: ingresoInstance, field: "cuenta")}</td>
					
						<td>${fieldValue(bean: ingresoInstance, field: "subcuenta")}</td>

						<td>${fieldValue(bean: ingresoInstance, field: "importe")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${ingresoInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
