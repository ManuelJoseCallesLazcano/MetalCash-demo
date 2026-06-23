
<%@ page import="org.socymet.cotizaciones.AjustePrecioEstano" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName" value="${message(code: 'ajustePrecioEstano.label', default: 'AjustePrecioEstano')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-ajustePrecioEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
	<ul>
		<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
		<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
	</ul>
</div>
<div id="list-ajustePrecioEstano" class="content scaffold-list" role="main">
	<h1><g:message code="default.list.label" args="[entityName]" /></h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">${flash.message}</div>
	</g:if>
	<table>
		<thead>
		<tr>

			<th><g:message code="ajustePrecioEstano.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" /></th>

			<g:sortableColumn property="fecha" title="${message(code: 'ajustePrecioEstano.fecha.label', default: 'Fecha')}" />

			<th><g:message code="ajustePrecioEstano.tablaCotizacionEstano.label" default="Tabla Cotizacion Estano" /></th>

			<g:sortableColumn property="margen" title="${message(code: 'ajustePrecioEstano.margen.label', default: 'Margen')}" />

		</tr>
		</thead>
		<tbody>
		<g:each in="${ajustePrecioEstanoInstanceList}" status="i" var="ajustePrecioEstanoInstance">
			<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

				<td><g:link action="show" id="${ajustePrecioEstanoInstance.id}">${fieldValue(bean: ajustePrecioEstanoInstance, field: "cotizacionDiariaDeMinerales")}</g:link></td>

				<td><g:link action="show" id="${ajustePrecioEstanoInstance.id}"><g:formatDate date="${ajustePrecioEstanoInstance.fecha}" format="dd/MM/yyyy"/></g:link></td>

				<td>${fieldValue(bean: ajustePrecioEstanoInstance, field: "tablaCotizacionEstano")}</td>

				<td>${fieldValue(bean: ajustePrecioEstanoInstance, field: "margen")}</td>

			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${ajustePrecioEstanoInstanceCount ?: 0}" />
	</div>
</div>
</body>
</html>
