
<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesLiquidadosOro" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteLotesLiquidadosOro.label', default: 'ReporteLotesLiquidadosOro')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-reporteLotesLiquidadosOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-reporteLotesLiquidadosOro" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="reporteLotesLiquidadosOro.empresa.label" default="Empresa" /></th>
					
						<th><g:message code="reporteLotesLiquidadosOro.deposito.label" default="Deposito" /></th>
					
						<g:sortableColumn property="elemento" title="${message(code: 'reporteLotesLiquidadosOro.elemento.label', default: 'Elemento')}" />
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'reporteLotesLiquidadosOro.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'reporteLotesLiquidadosOro.fechaFinal.label', default: 'Fecha Final')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${reporteLotesLiquidadosOroInstanceList}" status="i" var="reporteLotesLiquidadosOroInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${reporteLotesLiquidadosOroInstance.id}">${fieldValue(bean: reporteLotesLiquidadosOroInstance, field: "empresa")}</g:link></td>
					
						<td>${fieldValue(bean: reporteLotesLiquidadosOroInstance, field: "deposito")}</td>
					
						<td>${fieldValue(bean: reporteLotesLiquidadosOroInstance, field: "elemento")}</td>
					
						<td><g:formatDate date="${reporteLotesLiquidadosOroInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${reporteLotesLiquidadosOroInstance.fechaFinal}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${reporteLotesLiquidadosOroInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
