
<%@ page import="org.socymet.org.socymet.reportes.PlanillaDeLiquidacionOro" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'planillaDeLiquidacionOro.label', default: 'PlanillaDeLiquidacionOro')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-planillaDeLiquidacionOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-planillaDeLiquidacionOro" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="planillaDeLiquidacionOro.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'planillaDeLiquidacionOro.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'planillaDeLiquidacionOro.fechaFinal.label', default: 'Fecha Final')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${planillaDeLiquidacionOroInstanceList}" status="i" var="planillaDeLiquidacionOroInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${planillaDeLiquidacionOroInstance.id}">${fieldValue(bean: planillaDeLiquidacionOroInstance, field: "empresa")}</g:link></td>
					
						<td><g:formatDate date="${planillaDeLiquidacionOroInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${planillaDeLiquidacionOroInstance.fechaFinal}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${planillaDeLiquidacionOroInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
