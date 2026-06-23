
<%@ page import="org.smart.compositos.Comprador" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'comprador.label', default: 'Comprador')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-comprador" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-comprador" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="nombreComprador" title="${message(code: 'comprador.nombreComprador.label', default: 'Nombre Comprador')}" />
					
						<g:sortableColumn property="nombreContacto" title="${message(code: 'comprador.nombreContacto.label', default: 'Nombre Contacto')}" />
					
						<g:sortableColumn property="telefono" title="${message(code: 'comprador.telefono.label', default: 'Telefono')}" />
					
						<g:sortableColumn property="email" title="${message(code: 'comprador.email.label', default: 'Email')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${compradorInstanceList}" status="i" var="compradorInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${compradorInstance.id}">${fieldValue(bean: compradorInstance, field: "nombreComprador")}</g:link></td>
					
						<td>${fieldValue(bean: compradorInstance, field: "nombreContacto")}</td>
					
						<td>${fieldValue(bean: compradorInstance, field: "telefono")}</td>
					
						<td>${fieldValue(bean: compradorInstance, field: "email")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${compradorInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
