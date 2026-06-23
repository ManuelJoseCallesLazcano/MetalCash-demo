
<%@ page import="org.socymet.proveedor.EmpresaSeccion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'empresaSeccion.label', default: 'EmpresaSeccion')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-empresaSeccion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-empresaSeccion" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="empresaSeccion.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="nombreSeccion" title="${message(code: 'empresaSeccion.nombreSeccion.label', default: 'Nombre Seccion')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${empresaSeccionInstanceList}" status="i" var="empresaSeccionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${empresaSeccionInstance.id}">${fieldValue(bean: empresaSeccionInstance, field: "empresa")}</g:link></td>
					
						<td>${fieldValue(bean: empresaSeccionInstance, field: "nombreSeccion")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${empresaSeccionInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
