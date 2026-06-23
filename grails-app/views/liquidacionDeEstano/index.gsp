
<%@ page import="org.socymet.liquidacion.LiquidacionDeEstano" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-liquidacionDeEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-liquidacionDeEstano" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="numeroLiquidacionEstano" title="${message(code: 'liquidacionDeEstano.numeroLiquidacionEstano.label', default: 'Numero Liquidacion Estano')}" />
					
						<g:sortableColumn property="conjuntoEstano" title="${message(code: 'liquidacionDeEstano.conjuntoEstano.label', default: 'Conjunto Estano')}" />
					
						<th><g:message code="liquidacionDeEstano.recepcionDeEstano.label" default="Recepcion De Estano" /></th>
					
						<th><g:message code="liquidacionDeEstano.deposito.label" default="Deposito" /></th>
					
						<g:sortableColumn property="lote" title="${message(code: 'liquidacionDeEstano.lote.label', default: 'Lote')}" />
					
						<g:sortableColumn property="nombreCliente" title="${message(code: 'liquidacionDeEstano.nombreCliente.label', default: 'Nombre Cliente')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${liquidacionDeEstanoInstanceList}" status="i" var="liquidacionDeEstanoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${liquidacionDeEstanoInstance.id}">${fieldValue(bean: liquidacionDeEstanoInstance, field: "numeroLiquidacionEstano")}</g:link></td>
					
						<td>${fieldValue(bean: liquidacionDeEstanoInstance, field: "conjuntoEstano")}</td>
					
						<td>${fieldValue(bean: liquidacionDeEstanoInstance, field: "recepcionDeEstano")}</td>
					
						<td>${fieldValue(bean: liquidacionDeEstanoInstance, field: "deposito")}</td>
					
						<td>${fieldValue(bean: liquidacionDeEstanoInstance, field: "lote")}</td>
					
						<td>${fieldValue(bean: liquidacionDeEstanoInstance, field: "nombreCliente")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${liquidacionDeEstanoInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
