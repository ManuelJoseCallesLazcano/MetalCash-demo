
<%@ page import="org.socymet.anticipos.AnticipoPorTransporte" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-anticipoPorTransporte" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-anticipoPorTransporte" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list anticipoPorTransporte">

				<g:if test="${anticipoPorTransporteInstance?.numeroComprobante}">
					<li class="fieldcontain">
						<span id="numeroComprobante-label" class="property-label"><g:message code="anticipoPorTransporte.numeroComprobante.label" default="No. Comprobante" /></span>

						<span class="property-value" aria-labelledby="numeroComprobante-label"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="numeroComprobante"/></span>

					</li>
				</g:if>

				<g:if test="${anticipoPorTransporteInstance?.recepcionDeComplejo}">
					<li class="fieldcontain">
						<span id="recepcionDeComplejo-label" class="property-label"><g:message code="anticipoPorTransporte.recepcionDeComplejo.label" default="Lote" /></span>

						<span class="property-value" aria-labelledby="recepcionDeComplejo-label"><g:link controller="recepcionDeComplejo" action="show" id="${anticipoPorTransporteInstance?.recepcionDeComplejo?.id}">${anticipoPorTransporteInstance?.recepcionDeComplejo?.encodeAsHTML()}</g:link></span>

					</li>
				</g:if>
			
%{--				<g:if test="${anticipoPorTransporteInstance?.solicitante}">--}%
%{--				<li class="fieldcontain">--}%
%{--					<span id="solicitante-label" class="property-label"><g:message code="anticipoPorTransporte.solicitante.label" default="Solicitante" /></span>--}%
%{--					--}%
%{--						<span class="property-value" aria-labelledby="solicitante-label"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="solicitante"/></span>--}%
%{--					--}%
%{--				</li>--}%
%{--				</g:if>--}%
			
%{--				<g:if test="${anticipoPorTransporteInstance?.solicitante.equals("Empresa")}">--}%
				<g:if test="${anticipoPorTransporteInstance?.solicitante}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="anticipoPorTransporte.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${anticipoPorTransporteInstance?.empresa?.id}">${anticipoPorTransporteInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>

%{--                <g:if test="${anticipoPorTransporteInstance?.solicitante.equals("Particular")}">--}%
                <g:if test="${anticipoPorTransporteInstance?.solicitante}">
				<li class="fieldcontain">
					<span id="automovil-label" class="property-label"><g:message code="anticipoPorTransporte.automovil.label" default="Automovil" /></span>
					
						<span class="property-value" aria-labelledby="automovil-label"><g:link controller="automovil" action="show" id="${anticipoPorTransporteInstance?.automovil?.id}">${anticipoPorTransporteInstance?.automovil?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoPorTransporteInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="anticipoPorTransporte.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoPorTransporteInstance?.nombreCobrador}">
				<li class="fieldcontain">
					<span id="nombreCobrador-label" class="property-label"><g:message code="anticipoPorTransporte.nombreCobrador.label" default="Nombre Cobrador" /></span>
					
						<span class="property-value" aria-labelledby="nombreCobrador-label"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="nombreCobrador"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoPorTransporteInstance?.fecha}">
				<li class="fieldcontain">
					<span id="fecha-label" class="property-label"><g:message code="anticipoPorTransporte.fecha.label" default="Fecha" /></span>
					
						<span class="property-value" aria-labelledby="fecha-label"><g:formatDate date="${anticipoPorTransporteInstance?.fecha}" format="dd/MM/yyyy" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoPorTransporteInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="anticipoPorTransporte.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoPorTransporteInstance?.ultimoSaldo}">
				<li class="fieldcontain">
					<span id="ultimoSaldo-label" class="property-label"><g:message code="anticipoPorTransporte.ultimoSaldo.label" default="Ultimo Saldo" /></span>
					
						<span class="property-value" aria-labelledby="ultimoSaldo-label"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="ultimoSaldo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoPorTransporteInstance?.importe}">
				<li class="fieldcontain">
					<span id="importe-label" class="property-label"><g:message code="anticipoPorTransporte.importe.label" default="Importe" /></span>
					
						<span class="property-value" aria-labelledby="importe-label"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="importe"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoPorTransporteInstance?.importeLiteral}">
				<li class="fieldcontain">
					<span id="importeLiteral-label" class="property-label"><g:message code="anticipoPorTransporte.importeLiteral.label" default="Importe Literal" /></span>
					
						<span class="property-value" aria-labelledby="importeLiteral-label"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="importeLiteral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoPorTransporteInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="anticipoPorTransporte.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>

			
			</ol>

            <fieldset class="buttons">
                <div style="float: left">
                    <g:form>
						<sec:ifAnyGranted roles="ROLE_ADMIN">
							<g:hiddenField name="id" value="${anticipoPorTransporteInstance?.id}" />
%{--							<g:link class="edit" action="edit" id="${anticipoPorTransporteInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
%{--							<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
						</sec:ifAnyGranted>
                    </g:form>
                </div>
                <div style="float: right">
                    <g:jasperReport controller="anticipoPorTransporte" action="createReport" jasper="orden_anticipo_contra_transporte" format="PDF" name="OrdenAnticipoTransporte_${anticipoPorTransporteInstance.numeroComprobante}">
                        <input type="hidden" name="anticipoId" value="${anticipoPorTransporteInstance.id}" />
                    </g:jasperReport>
                </div>
            </fieldset>
		</div>
	</body>
</html>
