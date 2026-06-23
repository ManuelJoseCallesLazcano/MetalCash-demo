
<%@ page import="org.socymet.calidad.ControlCalidadOro" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'controlCalidadOro.label', default: 'ControlCalidadOro')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-controlCalidadOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-controlCalidadOro" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>

						<th><g:message code="controlCalidadOro.recepcionDeOro.label" default="Lote" /></th>

						<g:sortableColumn property="nombreCliente" title="${message(code: 'controlCalidadOro.nombreCliente.label', default: 'Nombre Cliente')}" />

						<g:sortableColumn property="fechaAnalisis" title="${message(code: 'controlCalidadOro.fechaAnalisis.label', default: 'Fecha De Analisis')}" />

						<g:sortableColumn property="porcentajeHumedadPromexbol" title="${message(code: 'controlCalidadOro.porcentajeHumedadPromexbol.label', default: '% Humedad')}" />

						%{--<g:sortableColumn property="porcentajeMermaPromexbol" title="${message(code: 'controlCalidadOro.porcentajeMermaPromexbol.label', default: '% Merma')}" />--}%

						<g:sortableColumn property="porcentajeOroPromexbol" title="${message(code: 'controlCalidadOro.porcentajeOroPromexbol.label', default: '% Oro')}" />

					</tr>
				</thead>
				<tbody>
				<g:each in="${controlCalidadOroInstanceList}" status="i" var="controlCalidadOroInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link action="show" id="${controlCalidadOroInstance.id}">${fieldValue(bean: controlCalidadOroInstance, field: "recepcionDeOro")}</g:link></td>

						<td>${fieldValue(bean: controlCalidadOroInstance, field: "nombreCliente")}</td>

						<td><g:formatDate date="${controlCalidadOroInstance.fechaAnalisis}" format="dd/MM/yyyy"/></td>

						<td>${fieldValue(bean: controlCalidadOroInstance, field: "porcentajeHumedadPromexbol")}</td>

						%{--<td>${fieldValue(bean: controlCalidadOroInstance, field: "porcentajeMermaPromexbol")}</td>--}%

						<td>${fieldValue(bean: controlCalidadOroInstance, field: "porcentajeOroPromexbol")}</td>

					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${controlCalidadOroInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
