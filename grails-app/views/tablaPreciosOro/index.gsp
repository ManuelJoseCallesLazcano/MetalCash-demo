
<%@ page import="org.socymet.cotizaciones.TablaPreciosOro" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tablaPreciosOro.label', default: 'TablaPreciosOro')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-tablaPreciosOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-tablaPreciosOro" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="nombreTabla" title="${message(code: 'tablaPreciosOro.nombreTabla.label', default: 'Nombre Tabla')}" />
					
						%{--<g:sortableColumn property="tablaPrecios" title="${message(code: 'tablaPreciosOro.tablaPrecios.label', default: 'Tabla Precios')}" />--}%
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${tablaPreciosOroInstanceList}" status="i" var="tablaPreciosOroInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${tablaPreciosOroInstance.id}">${fieldValue(bean: tablaPreciosOroInstance, field: "nombreTabla")}</g:link></td>
					
						%{--<td>${fieldValue(bean: tablaPreciosOroInstance, field: "tablaPrecios")}</td>--}%
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${tablaPreciosOroInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
