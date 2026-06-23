
<%@ page import="org.socymet.caja.CierreCaja" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cierreCaja.label', default: 'CierreCaja')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-cierreCaja" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-cierreCaja" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>

						<g:sortableColumn property="numeroCierreCaja" title="${message(code: 'cierreCaja.numeroCierreCaja.label', default: 'Numero Cierre Caja')}" />

						<g:sortableColumn property="fechaCierreCaja" title="${message(code: 'cierreCaja.fechaCierreCaja.label', default: 'Fecha Cierre Caja')}" />

						<g:sortableColumn property="debeTotal" title="${message(code: 'cierreCaja.debeTotal.label', default: 'Debe Total')}" />

						<g:sortableColumn property="haberTotal" title="${message(code: 'cierreCaja.haberTotal.label', default: 'Haber Total')}" />

						<g:sortableColumn property="saldoTotal" title="${message(code: 'cierreCaja.saldoTotal.label', default: 'Saldo Final')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${cierreCajaInstanceList}" status="i" var="cierreCajaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						%{--<td><g:link action="show" id="${cierreCajaInstance.id}">${fieldValue(bean: cierreCajaInstance, field: "numeroCierreCaja")}</g:link></td>--}%
						<td><g:link action="show" id="${cierreCajaInstance.id}"><g:formatNumber number="${cierreCajaInstance.numeroCierreCaja}" format="000000"/></g:link></td>

						<td><g:formatDate date="${cierreCajaInstance.fechaCierreCaja}" /></td>

						<td>${fieldValue(bean: cierreCajaInstance, field: "debeTotal")}</td>

						<td>${fieldValue(bean: cierreCajaInstance, field: "haberTotal")}</td>

						<td>${fieldValue(bean: cierreCajaInstance, field: "saldoTotal")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${cierreCajaInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
