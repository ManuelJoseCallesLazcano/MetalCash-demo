
<%@ page import="org.socymet.liquidacion.LiquidacionDeOro" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'liquidacionDeOro.label', default: 'LiquidacionDeOro')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-liquidacionDeOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-liquidacionDeOro" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>

						<th><g:message code="liquidacionDeOro.numeroLiquidacionOro.label" default="No. Liq." /></th>

						<th><g:message code="liquidacionDeOro.recepcionDeOro.label" default="Lote" /></th>

						<g:sortableColumn property="nombreCliente" title="${message(code: 'liquidacionDeOro.nombreCliente.label', default: 'Nombre Cliente')}" />

						<g:sortableColumn property="nombreEmpresa" title="${message(code: 'liquidacionDeOro.nombreEmpresa.label', default: 'Nombre Empresa')}" />

						<g:sortableColumn property="fechaDeLiquidacion" title="${message(code: 'liquidacionDeOro.fechaDeLiquidacion.label', default: 'Fecha De Liquidacion')}" />

						<g:sortableColumn property="kilosNetosSecos" title="${message(code: 'liquidacionDeOro.kilosNetosSecos.label', default: 'K. N. S.')}" />

						<g:sortableColumn property="valorOficialBruto" title="${message(code: 'liquidacionDeOro.valorOficialBruto.label', default: 'Valor Bruto')}" />

						<g:sortableColumn property="totalLiquidoPagable" title="${message(code: 'liquidacionDeOro.totalLiquidoPagable.label', default: 'Liquido Pagable')}" />

					</tr>
				</thead>
				<tbody>
				<g:each in="${liquidacionDeOroInstanceList}" status="i" var="liquidacionDeOroInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link action="show" id="${liquidacionDeOroInstance.id}">${fieldValue(bean: liquidacionDeOroInstance, field: "numeroLiquidacionOro")}</g:link></td>

						<td><g:link action="show" id="${liquidacionDeOroInstance.id}">${fieldValue(bean: liquidacionDeOroInstance, field: "recepcionDeOro")}</g:link></td>

						<td>${fieldValue(bean: liquidacionDeOroInstance, field: "nombreCliente")}</td>

						<td>${fieldValue(bean: liquidacionDeOroInstance, field: "nombreEmpresa")}</td>

						<td><g:formatDate date="${liquidacionDeOroInstance.fechaDeLiquidacion}" format="dd/MM/yyyy" /></td>

						<td>${fieldValue(bean: liquidacionDeOroInstance, field: "kilosNetosSecos")}</td>

						<td>${fieldValue(bean: liquidacionDeOroInstance, field: "valorOficialBruto")}</td>

						<td>${fieldValue(bean: liquidacionDeOroInstance, field: "totalLiquidoPagable")}</td>					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${liquidacionDeOroInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
