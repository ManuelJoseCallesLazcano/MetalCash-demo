
<%@ page import="org.socymet.recepcion.RecepcionDeOro" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'recepcionDeOro.label', default: 'RecepcionDeOro')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-recepcionDeOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-recepcionDeOro" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>

                        %{--<g:sortableColumn property="numeroRecepcion" title="${message(code: 'recepcionDeOro.numeroRecepcion.label', default: 'No. Rec.')}" />--}%

                        <g:sortableColumn property="loteOro" title="${message(code: 'recepcionDeOro.loteOro.label', default: 'Lote')}" />

                        <g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'recepcionDeOro.fechaDeRecepcion.label', default: 'Fecha Rec.')}" />

                        %{--<th><g:message code="recepcionDeOro.deposito.label" default="Deposito" /></th>--}%

                        <th><g:message code="recepcionDeOro.cliente.label" default="Cliente" /></th>

                        <th><g:message code="recepcionDeOro.empresa.label" default="Empresa" /></th>

                        %{--<g:sortableColumn property="condicionDeEntrega" title="${message(code: 'recepcionDeOro.condicionDeEntrega.label', default: 'Cond. Entrega')}" />--}%

                        <th><g:message code="recepcionDeOro.pesoBruto.label" default="Peso Bruto" /></th>

                        <th><g:message code="recepcionDeOro.estadoDelLote.label" default="Estado del Lote" /></th>

                        <th><g:message code="recepcionDeOro.estadoAnticipo.label" default="Estado Anticipo" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${recepcionDeOroInstanceList}" status="i" var="recepcionDeOroInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                        %{--<td><g:link action="show" id="${recepcionDeOroInstance.id}">${recepcionDeOroInstance.numeroRecepcion}</g:link></td>--}%

                        <td><g:link action="show" id="${recepcionDeOroInstance.id}">${recepcionDeOroInstance.toString()}</g:link></td>

                        <td><g:formatDate date="${recepcionDeOroInstance.fechaDeRecepcion}" format="dd/MM/yyyy"/></td>

                        %{--<td>${recepcionDeOroInstance.deposito.toString()}</td>--}%

                        <td>${recepcionDeOroInstance.cliente.nombre}</td>

                        <td>${recepcionDeOroInstance.empresa.nombreDeEmpresa}</td>

                        %{--<td>${recepcionDeOroInstance.condicionDeEntrega}</td>--}%

                        <td>${recepcionDeOroInstance.pesoBruto}</td>

                        <td>${recepcionDeOroInstance.estadoDelLote}</td>

                        <td>${recepcionDeOroInstance.estadoAnticipo}</td>

                    </tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${recepcionDeOroInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
