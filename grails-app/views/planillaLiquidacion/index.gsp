
<%@ page import="org.socymet.reportesAcopiadoras.PlanillaLiquidacion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'planillaLiquidacion.label', default: 'PlanillaLiquidacion')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-planillaLiquidacion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-planillaLiquidacion" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="planillaLiquidacion.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'planillaLiquidacion.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'planillaLiquidacion.fechaFinal.label', default: 'Fecha Final')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${planillaLiquidacionInstanceList}" status="i" var="planillaLiquidacionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${planillaLiquidacionInstance.id}">${fieldValue(bean: planillaLiquidacionInstance, field: "empresa")}</g:link></td>
					
						<td><g:formatDate date="${planillaLiquidacionInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${planillaLiquidacionInstance.fechaFinal}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${planillaLiquidacionInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
