
<%@ page import="org.socymet.caja.Egreso" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'egreso.label', default: 'Egreso')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-egreso" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-egreso" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="numeroEgreso" title="${message(code: 'egreso.numeroEgreso.label', default: 'Numero Egreso')}" />
					
						<g:sortableColumn property="fechaEgreso" title="${message(code: 'egreso.fechaEgreso.label', default: 'Fecha Egreso')}" />
					
						<g:sortableColumn property="ci" title="${message(code: 'egreso.ci.label', default: 'Ci')}" />
					
						<g:sortableColumn property="nombre" title="${message(code: 'egreso.nombre.label', default: 'Nombre')}" />
					
						<th><g:message code="egreso.cuenta.label" default="Cuenta" /></th>
					
						<th><g:message code="egreso.subcuenta.label" default="Subcuenta" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${egresoInstanceList}" status="i" var="egresoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${egresoInstance.id}">${fieldValue(bean: egresoInstance, field: "numeroEgreso")}</g:link></td>
					
						<td><g:formatDate date="${egresoInstance.fechaEgreso}" /></td>
					
						<td>${fieldValue(bean: egresoInstance, field: "ci")}</td>
					
						<td>${fieldValue(bean: egresoInstance, field: "nombre")}</td>
					
						<td>${fieldValue(bean: egresoInstance, field: "cuenta")}</td>
					
						<td>${fieldValue(bean: egresoInstance, field: "subcuenta")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${egresoInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
