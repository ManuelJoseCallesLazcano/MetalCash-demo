
<%@ page import="org.socymet.org.socymet.reportes.ReporteGraficoAcumuladoEmpresaCliente" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteGraficoAcumuladoEmpresaCliente.label', default: 'ReporteGraficoAcumuladoEmpresaCliente')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-reporteGraficoAcumuladoEmpresaCliente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-reporteGraficoAcumuladoEmpresaCliente" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="reporteGraficoAcumuladoEmpresaCliente.empresa.label" default="Empresa" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${reporteGraficoAcumuladoEmpresaClienteInstanceList}" status="i" var="reporteGraficoAcumuladoEmpresaClienteInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${reporteGraficoAcumuladoEmpresaClienteInstance.id}">${fieldValue(bean: reporteGraficoAcumuladoEmpresaClienteInstance, field: "empresa")}</g:link></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${reporteGraficoAcumuladoEmpresaClienteInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
