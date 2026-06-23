
<%@ page import="org.socymet.recepcion.ActualizacionCotizacionRecepcion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'actualizacionCotizacionRecepcion.label', default: 'ActualizacionCotizacionRecepcion')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-actualizacionCotizacionRecepcion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-actualizacionCotizacionRecepcion" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="tipoCotizacion" title="${message(code: 'actualizacionCotizacionRecepcion.tipoCotizacion.label', default: 'Tipo Cotizacion')}" />
					
						<th><g:message code="actualizacionCotizacionRecepcion.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" /></th>
					
						<th><g:message code="actualizacionCotizacionRecepcion.cotizacionQuincenalDeMinerales.label" default="Cotizacion Quincenal De Minerales" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'actualizacionCotizacionRecepcion.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'actualizacionCotizacionRecepcion.fechaFinal.label', default: 'Fecha Final')}" />
					
						<g:sortableColumn property="detalleLotes" title="${message(code: 'actualizacionCotizacionRecepcion.detalleLotes.label', default: 'Detalle Lotes')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${actualizacionCotizacionRecepcionInstanceList}" status="i" var="actualizacionCotizacionRecepcionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${actualizacionCotizacionRecepcionInstance.id}">${fieldValue(bean: actualizacionCotizacionRecepcionInstance, field: "tipoCotizacion")}</g:link></td>
					
						<td>${fieldValue(bean: actualizacionCotizacionRecepcionInstance, field: "cotizacionDiariaDeMinerales")}</td>
					
						<td>${fieldValue(bean: actualizacionCotizacionRecepcionInstance, field: "cotizacionQuincenalDeMinerales")}</td>
					
						<td><g:formatDate date="${actualizacionCotizacionRecepcionInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${actualizacionCotizacionRecepcionInstance.fechaFinal}" /></td>
					
						<td>${fieldValue(bean: actualizacionCotizacionRecepcionInstance, field: "detalleLotes")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${actualizacionCotizacionRecepcionInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
