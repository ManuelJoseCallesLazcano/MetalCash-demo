
<%@ page import="org.socymet.org.socymet.reportes.DetalleCanjeTornaguias" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'detalleCanjeTornaguias.label', default: 'DetalleCanjeTornaguias')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-detalleCanjeTornaguias" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-detalleCanjeTornaguias" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="detalleCanjeTornaguias.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'detalleCanjeTornaguias.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'detalleCanjeTornaguias.fechaFinal.label', default: 'Fecha Final')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${detalleCanjeTornaguiasInstanceList}" status="i" var="detalleCanjeTornaguiasInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${detalleCanjeTornaguiasInstance.id}">${fieldValue(bean: detalleCanjeTornaguiasInstance, field: "empresa")}</g:link></td>
					
						<td><g:formatDate date="${detalleCanjeTornaguiasInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${detalleCanjeTornaguiasInstance.fechaFinal}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${detalleCanjeTornaguiasInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
