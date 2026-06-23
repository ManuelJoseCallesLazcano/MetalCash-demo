
<%@ page import="org.socymet.caja.Ingreso" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ingreso.label', default: 'Ingreso')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-ingreso" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-ingreso" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list ingreso">
			
				<g:if test="${ingresoInstance?.numeroIngreso}">
				<li class="fieldcontain">
					<span id="numeroIngreso-label" class="property-label"><g:message code="ingreso.numeroIngreso.label" default="Numero Ingreso" /></span>
					
						<span class="property-value" aria-labelledby="numeroIngreso-label"><g:fieldValue bean="${ingresoInstance}" field="numeroIngreso"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingresoInstance?.fechaIngreso}">
				<li class="fieldcontain">
					<span id="fechaIngreso-label" class="property-label"><g:message code="ingreso.fechaIngreso.label" default="Fecha Ingreso" /></span>
					
						<span class="property-value" aria-labelledby="fechaIngreso-label"><g:formatDate date="${ingresoInstance?.fechaIngreso}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingresoInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="ingreso.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${ingresoInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingresoInstance?.nombre}">
				<li class="fieldcontain">
					<span id="nombre-label" class="property-label"><g:message code="ingreso.nombre.label" default="Nombre" /></span>
					
						<span class="property-value" aria-labelledby="nombre-label"><g:fieldValue bean="${ingresoInstance}" field="nombre"/></span>
					
				</li>
				</g:if>

				<h1 style="font-weight: bold">Informaci&oacute;n de la Transacci&oacute;n</h1>

				<g:if test="${ingresoInstance?.cuenta}">
				<li class="fieldcontain">
					<span id="cuenta-label" class="property-label"><g:message code="ingreso.cuenta.label" default="Cuenta" /></span>
					
						<span class="property-value" aria-labelledby="cuenta-label"><g:link controller="cuenta" action="show" id="${ingresoInstance?.cuenta?.id}">${ingresoInstance?.cuenta?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingresoInstance?.subcuenta}">
				<li class="fieldcontain">
					<span id="subcuenta-label" class="property-label"><g:message code="ingreso.subcuenta.label" default="Subcuenta" /></span>
					
						<span class="property-value" aria-labelledby="subcuenta-label"><g:link controller="subcuenta" action="show" id="${ingresoInstance?.subcuenta?.id}">${ingresoInstance?.subcuenta?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingresoInstance?.concepto}">
				<li class="fieldcontain">
					<span id="concepto-label" class="property-label"><g:message code="ingreso.concepto.label" default="Concepto" /></span>
					
						<span class="property-value" aria-labelledby="concepto-label"><g:fieldValue bean="${ingresoInstance}" field="concepto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingresoInstance?.importe}">
				<li class="fieldcontain">
					<span id="importe-label" class="property-label"><g:message code="ingreso.importe.label" default="Importe" /></span>
					
						<span class="property-value" aria-labelledby="importe-label"><g:fieldValue bean="${ingresoInstance}" field="importe"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingresoInstance?.importeLiteral}">
				<li class="fieldcontain">
					<span id="importeLiteral-label" class="property-label"><g:message code="ingreso.importeLiteral.label" default="Importe Literal" /></span>
					
						<span class="property-value" aria-labelledby="importeLiteral-label"><g:fieldValue bean="${ingresoInstance}" field="importeLiteral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${ingresoInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="ingreso.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${ingresoInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
				%{--<g:if test="${ingresoInstance?.usuario}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="usuario-label" class="property-label"><g:message code="ingreso.usuario.label" default="Usuario" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="usuario-label"><g:link controller="secUser" action="show" id="${ingresoInstance?.usuario?.id}">${ingresoInstance?.usuario?.encodeAsHTML()}</g:link></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			
				<g:if test="${ingresoInstance?.consolidado}">
				<li class="fieldcontain">
					<span id="consolidado-label" class="property-label"><g:message code="ingreso.consolidado.label" default="Consolidado" /></span>
					
						<span class="property-value" aria-labelledby="consolidado-label"><g:fieldValue bean="${ingresoInstance}" field="consolidado"/></span>
					
				</li>
				</g:if>
			
				%{--<g:if test="${ingresoInstance?.dateCreated}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="dateCreated-label" class="property-label"><g:message code="ingreso.dateCreated.label" default="Date Created" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${ingresoInstance?.dateCreated}" /></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			
				%{--<g:if test="${ingresoInstance?.lastUpdated}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="lastUpdated-label" class="property-label"><g:message code="ingreso.lastUpdated.label" default="Last Updated" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${ingresoInstance?.lastUpdated}" /></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			
			</ol>
			%{--<g:form url="[resource:ingresoInstance, action:'delete']" method="DELETE">--}%
				%{--<fieldset class="buttons">--}%
					%{--<sec:ifAnyGranted roles="ROLE_ADMIN">--}%
						%{--<g:link class="edit" action="edit" resource="${ingresoInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
						%{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
					%{--</sec:ifAnyGranted>--}%
				%{--</fieldset>--}%
			%{--</g:form>--}%

			<fieldset class="buttons">
				<div style="float: left">
					<g:form>
						<g:hiddenField name="id" value="${ingresoInstance?.id}" />

						<sec:ifAnyGranted roles="ROLE_ADMIN">
							<g:link class="edit" action="edit" id="${ingresoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
							<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
						</sec:ifAnyGranted>
					</g:form>
				</div>
				<div style="float: right">
					<table>
						<tr>
							<td>
								<g:jasperReport controller="ingreso" action="createReport" jasper="comprobante_ingreso" format="PDF" _format="PDF" name="COMPROBANTE_INGRESO_${ingresoInstance.toString()}">
									<input type="hidden" name="ingresoId" value="${ingresoInstance.id}" />
								</g:jasperReport>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
		</div>
	</body>
</html>
