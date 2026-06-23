
<%@ page import="org.socymet.org.socymet.reportes.ReportePlanillaTransporteOro" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reportePlanillaTransporteOro.label', default: 'ReportePlanillaTransporteOro')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-reportePlanillaTransporteOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-reportePlanillaTransporteOro" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="reportePlanillaTransporteOro.deposito.label" default="Deposito" /></th>
					
						<g:sortableColumn property="elemento" title="${message(code: 'reportePlanillaTransporteOro.elemento.label', default: 'Elemento')}" />
					
						<th><g:message code="reportePlanillaTransporteOro.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'reportePlanillaTransporteOro.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'reportePlanillaTransporteOro.fechaFinal.label', default: 'Fecha Final')}" />
					
						<g:sortableColumn property="loteInicial" title="${message(code: 'reportePlanillaTransporteOro.loteInicial.label', default: 'Lote Inicial')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${reportePlanillaTransporteOroInstanceList}" status="i" var="reportePlanillaTransporteOroInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${reportePlanillaTransporteOroInstance.id}">${fieldValue(bean: reportePlanillaTransporteOroInstance, field: "deposito")}</g:link></td>
					
						<td>${fieldValue(bean: reportePlanillaTransporteOroInstance, field: "elemento")}</td>
					
						<td>${fieldValue(bean: reportePlanillaTransporteOroInstance, field: "empresa")}</td>
					
						<td><g:formatDate date="${reportePlanillaTransporteOroInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${reportePlanillaTransporteOroInstance.fechaFinal}" /></td>
					
						<td>${fieldValue(bean: reportePlanillaTransporteOroInstance, field: "loteInicial")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${reportePlanillaTransporteOroInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
