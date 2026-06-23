
<%@ page import="org.socymet.caja.Egreso" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'egreso.label', default: 'Egreso')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-egreso" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-egreso" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list egreso">
			
				<g:if test="${egresoInstance?.numeroEgreso}">
				<li class="fieldcontain">
					<span id="numeroEgreso-label" class="property-label"><g:message code="egreso.numeroEgreso.label" default="Numero Egreso" /></span>
					
						<span class="property-value" aria-labelledby="numeroEgreso-label"><g:fieldValue bean="${egresoInstance}" field="numeroEgreso"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${egresoInstance?.fechaEgreso}">
				<li class="fieldcontain">
					<span id="fechaEgreso-label" class="property-label"><g:message code="egreso.fechaEgreso.label" default="Fecha Egreso" /></span>
					
						<span class="property-value" aria-labelledby="fechaEgreso-label"><g:formatDate date="${egresoInstance?.fechaEgreso}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${egresoInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="egreso.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${egresoInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${egresoInstance?.nombre}">
				<li class="fieldcontain">
					<span id="nombre-label" class="property-label"><g:message code="egreso.nombre.label" default="Nombre" /></span>
					
						<span class="property-value" aria-labelledby="nombre-label"><g:fieldValue bean="${egresoInstance}" field="nombre"/></span>
					
				</li>
				</g:if>

				<h1 style="font-weight: bold">Informaci&oacute;n de la Transacci&oacute;n</h1>
			
				<g:if test="${egresoInstance?.cuenta}">
				<li class="fieldcontain">
					<span id="cuenta-label" class="property-label"><g:message code="egreso.cuenta.label" default="Cuenta" /></span>
					
						<span class="property-value" aria-labelledby="cuenta-label"><g:link controller="cuenta" action="show" id="${egresoInstance?.cuenta?.id}">${egresoInstance?.cuenta?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${egresoInstance?.subcuenta}">
				<li class="fieldcontain">
					<span id="subcuenta-label" class="property-label"><g:message code="egreso.subcuenta.label" default="Subcuenta" /></span>
					
						<span class="property-value" aria-labelledby="subcuenta-label"><g:link controller="subcuenta" action="show" id="${egresoInstance?.subcuenta?.id}">${egresoInstance?.subcuenta?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${egresoInstance?.operacion}">
				<li class="fieldcontain">
					<span id="operacion-label" class="property-label"><g:message code="egreso.operacion.label" default="Operacion" /></span>
					
						<span class="property-value" aria-labelledby="operacion-label"><g:fieldValue bean="${egresoInstance}" field="operacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${egresoInstance?.concepto}">
				<li class="fieldcontain">
					<span id="concepto-label" class="property-label"><g:message code="egreso.concepto.label" default="Concepto" /></span>
					
						<span class="property-value" aria-labelledby="concepto-label"><g:fieldValue bean="${egresoInstance}" field="concepto"/></span>
					
				</li>
				</g:if>

				<g:if test="${egresoInstance?.identificador}">
					<li class="fieldcontain">
						<span id="identificador-label" class="property-label"><g:message code="egreso.identificador.label" default="Identificador" /></span>

						<span class="property-value" aria-labelledby="identificador-label"><g:fieldValue bean="${egresoInstance}" field="identificador"/></span>

					</li>
				</g:if>
				
				<g:if test="${egresoInstance?.importe}">
				<li class="fieldcontain">
					<span id="importe-label" class="property-label"><g:message code="egreso.importe.label" default="Importe" /></span>
					
						<span class="property-value" aria-labelledby="importe-label"><g:fieldValue bean="${egresoInstance}" field="importe"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${egresoInstance?.importeLiteral}">
				<li class="fieldcontain">
					<span id="importeLiteral-label" class="property-label"><g:message code="egreso.importeLiteral.label" default="Importe Literal" /></span>
					
						<span class="property-value" aria-labelledby="importeLiteral-label"><g:fieldValue bean="${egresoInstance}" field="importeLiteral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${egresoInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="egreso.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${egresoInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
				%{--<g:if test="${egresoInstance?.usuario}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="usuario-label" class="property-label"><g:message code="egreso.usuario.label" default="Usuario" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="usuario-label"><g:link controller="secUser" action="show" id="${egresoInstance?.usuario?.id}">${egresoInstance?.usuario?.encodeAsHTML()}</g:link></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			
				<g:if test="${egresoInstance?.consolidado}">
				<li class="fieldcontain">
					<span id="consolidado-label" class="property-label"><g:message code="egreso.consolidado.label" default="Consolidado" /></span>
					
						<span class="property-value" aria-labelledby="consolidado-label"><g:fieldValue bean="${egresoInstance}" field="consolidado"/></span>
					
				</li>
				</g:if>
			
				%{--<g:if test="${egresoInstance?.dateCreated}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="dateCreated-label" class="property-label"><g:message code="egreso.dateCreated.label" default="Date Created" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${egresoInstance?.dateCreated}" /></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			%{----}%
				%{--<g:if test="${egresoInstance?.lastUpdated}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="lastUpdated-label" class="property-label"><g:message code="egreso.lastUpdated.label" default="Last Updated" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${egresoInstance?.lastUpdated}" /></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			
			</ol>
			%{--<g:form url="[resource:egresoInstance, action:'delete']" method="DELETE">--}%
				%{--<fieldset class="buttons">--}%
					%{--<sec:ifAnyGranted roles="ROLE_ADMIN">--}%
						%{--<g:link class="edit" action="edit" resource="${egresoInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
						%{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
					%{--</sec:ifAnyGranted>--}%
				%{--</fieldset>--}%
			%{--</g:form>--}%

			<fieldset class="buttons">
				<div style="float: left">
					<g:form>
						<g:hiddenField name="id" value="${egresoInstance?.id}" />

						<sec:ifAnyGranted roles="ROLE_ADMIN">
							<g:link class="edit" action="edit" id="${egresoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
							<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
						</sec:ifAnyGranted>
					</g:form>
				</div>
				<div style="float: right">
					<table>
						<tr>
							<td>
								<g:jasperReport controller="egreso" action="createReport" jasper="comprobante_egreso" format="PDF" _format="PDF" name="COMPROBANTE_EGRESO_${egresoInstance.toString()}">
									<input type="hidden" name="egresoId" value="${egresoInstance.id}" />
								</g:jasperReport>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
		</div>
	</body>
</html>
