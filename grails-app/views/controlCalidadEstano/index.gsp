
<%@ page import="org.socymet.calidad.ControlCalidadEstano" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName" value="${message(code: 'controlCalidadEstano.label', default: 'ControlCalidadEstano')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-controlCalidadEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
	<ul>
		<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
		<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
	</ul>
</div>
<div id="list-controlCalidadEstano" class="content scaffold-list" role="main">
	<h1><g:message code="default.list.label" args="[entityName]" /></h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">${flash.message}</div>
	</g:if>
	<table>
		<thead>
		<tr>

			<th><g:message code="controlCalidadEstano.recepcionDeEstano.label" default="Recepcion De Estano" /></th>

			%{--<th><g:message code="controlCalidadEstano.empresa.label" default="Empresa" /></th>--}%

			%{--<g:sortableColumn property="lote" title="${message(code: 'controlCalidadEstano.lote.label', default: 'Lote')}" />--}%

			<g:sortableColumn property="nombreCliente" title="${message(code: 'controlCalidadEstano.nombreCliente.label', default: 'Nombre Cliente')}" />

			<g:sortableColumn property="nombreEmpresa" title="${message(code: 'controlCalidadEstano.nombreEmpresa.label', default: 'Nombre Empresa')}" />

			<g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'controlCalidadEstano.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />

			<g:sortableColumn property="porcentajeEstanoPromexbol" title="${message(code: 'controlCalidadEstano.porcentajeEstanoPromexbol.label', default: 'porcentajeEstanoPromexbol')}" />

		</tr>
		</thead>
		<tbody>
		<g:each in="${controlCalidadEstanoInstanceList}" status="i" var="controlCalidadEstanoInstance">
			<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

				<td><g:link action="show" id="${controlCalidadEstanoInstance.id}">${fieldValue(bean: controlCalidadEstanoInstance, field: "recepcionDeEstano")}</g:link></td>

				%{--<td>${fieldValue(bean: controlCalidadEstanoInstance, field: "empresa")}</td>--}%

				%{--<td>${fieldValue(bean: controlCalidadEstanoInstance, field: "lote")}</td>--}%

				<td>${fieldValue(bean: controlCalidadEstanoInstance, field: "nombreCliente")}</td>

				<td>${fieldValue(bean: controlCalidadEstanoInstance, field: "nombreEmpresa")}</td>

				<td>${fieldValue(bean: controlCalidadEstanoInstance, field: "fechaDeRecepcion")}</td>

				<td>${fieldValue(bean: controlCalidadEstanoInstance, field: "porcentajeEstanoPromexbol")}</td>

			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${controlCalidadEstanoInstanceCount ?: 0}" />
	</div>
</div>
</body>
</html>
