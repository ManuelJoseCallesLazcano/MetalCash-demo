
<%@ page import="org.socymet.reportesAcopiadoras.LotesRecepcionados" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'lotesRecepcionados.label', default: 'LotesRecepcionados')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-lotesRecepcionados" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-lotesRecepcionados" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="lotesRecepcionados.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'lotesRecepcionados.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'lotesRecepcionados.fechaFinal.label', default: 'Fecha Final')}" />
					
						<g:sortableColumn property="estado" title="${message(code: 'lotesRecepcionados.estado.label', default: 'Estado')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${lotesRecepcionadosInstanceList}" status="i" var="lotesRecepcionadosInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${lotesRecepcionadosInstance.id}">${fieldValue(bean: lotesRecepcionadosInstance, field: "empresa")}</g:link></td>
					
						<td><g:formatDate date="${lotesRecepcionadosInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${lotesRecepcionadosInstance.fechaFinal}" /></td>
					
						<td>${fieldValue(bean: lotesRecepcionadosInstance, field: "estado")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${lotesRecepcionadosInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
