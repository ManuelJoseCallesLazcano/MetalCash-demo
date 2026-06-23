
<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesCompositos" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteLotesCompositos.label', default: 'ReporteLotesCompositos')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-reporteLotesCompositos" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-reporteLotesCompositos" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'reporteLotesCompositos.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'reporteLotesCompositos.fechaFinal.label', default: 'Fecha Final')}" />
					
						<g:sortableColumn property="estado" title="${message(code: 'reporteLotesCompositos.estado.label', default: 'Estado')}" />
					
						<th><g:message code="reporteLotesCompositos.empresa.label" default="Empresa" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${reporteLotesCompositosInstanceList}" status="i" var="reporteLotesCompositosInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${reporteLotesCompositosInstance.id}">${fieldValue(bean: reporteLotesCompositosInstance, field: "fechaInicial")}</g:link></td>
					
						<td><g:formatDate date="${reporteLotesCompositosInstance.fechaFinal}" /></td>
					
						<td>${fieldValue(bean: reporteLotesCompositosInstance, field: "estado")}</td>
					
						<td>${fieldValue(bean: reporteLotesCompositosInstance, field: "empresa")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${reporteLotesCompositosInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
