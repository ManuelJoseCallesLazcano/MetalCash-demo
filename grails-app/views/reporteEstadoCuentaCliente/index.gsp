
<%@ page import="org.socymet.org.socymet.reportes.ReporteEstadoCuentaCliente" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteEstadoCuentaCliente.label', default: 'ReporteEstadoCuentaCliente')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-reporteEstadoCuentaCliente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-reporteEstadoCuentaCliente" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="reporteEstadoCuentaCliente.cliente.label" default="Cliente" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'reporteEstadoCuentaCliente.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'reporteEstadoCuentaCliente.fechaFinal.label', default: 'Fecha Final')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${reporteEstadoCuentaClienteInstanceList}" status="i" var="reporteEstadoCuentaClienteInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${reporteEstadoCuentaClienteInstance.id}">${fieldValue(bean: reporteEstadoCuentaClienteInstance, field: "cliente")}</g:link></td>
					
						<td><g:formatDate date="${reporteEstadoCuentaClienteInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${reporteEstadoCuentaClienteInstance.fechaFinal}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${reporteEstadoCuentaClienteInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
