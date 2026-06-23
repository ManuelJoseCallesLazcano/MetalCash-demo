
<%@ page import="org.socymet.anticipos.AnticipoContraEntrega" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'anticipoContraEntrega.label', default: 'AnticipoContraEntrega')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-anticipoContraEntrega" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-anticipoContraEntrega" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list anticipoContraEntrega">

                <g:if test="${anticipoContraEntregaInstance?.numeroAnticipo}">
                    <li class="fieldcontain">
                        <span id="numeroAnticipo-label" class="property-label"><g:message code="anticipoContraEntrega.numeroAnticipo.label" default="No. de Anticipo" /></span>

                        <span class="property-value" aria-labelledby="numeroAnticipo-label"><g:fieldValue bean="${anticipoContraEntregaInstance}" field="numeroAnticipo"/></span>

                    </li>
                </g:if>
			
				<g:if test="${anticipoContraEntregaInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="anticipoContraEntrega.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${anticipoContraEntregaInstance}" field="lote"/></span>
					
				</li>
				</g:if>

				<g:if test="${anticipoContraEntregaInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="anticipoContraEntrega.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${anticipoContraEntregaInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoContraEntregaInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="anticipoContraEntrega.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${anticipoContraEntregaInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoContraEntregaInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="anticipoContraEntrega.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${anticipoContraEntregaInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoContraEntregaInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="anticipoContraEntrega.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${anticipoContraEntregaInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoContraEntregaInstance?.fechaDeAnticipo}">
				<li class="fieldcontain">
					<span id="fechaDeAnticipo-label" class="property-label"><g:message code="anticipoContraEntrega.fechaDeAnticipo.label" default="Fecha De Anticipo" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeAnticipo-label"><g:formatDate date="${anticipoContraEntregaInstance?.fechaDeAnticipo}" format="dd/MM/yyyy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoContraEntregaInstance?.importe}">
				<li class="fieldcontain">
					<span id="importe-label" class="property-label"><g:message code="anticipoContraEntrega.importe.label" default="Importe" /></span>
					
						<span class="property-value" aria-labelledby="importe-label"><g:fieldValue bean="${anticipoContraEntregaInstance}" field="importe"/></span>
					
				</li>
				</g:if>

                <g:if test="${anticipoContraEntregaInstance?.importeLiteral}">
                    <li class="fieldcontain">
                        <span id="importeLiteral-label" class="property-label"><g:message code="anticipoContraEntrega.importeLiteral.label" default="importeLiteral" /></span>

                        <span class="property-value" aria-labelledby="importeLiteral-label"><g:fieldValue bean="${anticipoContraEntregaInstance}" field="importeLiteral"/></span>

                    </li>
                </g:if>

                <g:if test="${anticipoContraEntregaInstance?.anticipoPagado}">
                    <li class="fieldcontain">
                        <span id="anticipoPagado-label" class="property-label"><g:message code="anticipoContraEntrega.anticipoPagado.label" default="Anticipo Pagado?" /></span>

                        <span class="property-value" aria-labelledby="anticipoPagado-label"><g:fieldValue bean="${anticipoContraEntregaInstance}" field="anticipoPagado"/></span>

                    </li>
                </g:if>
			
				<g:if test="${anticipoContraEntregaInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="anticipoContraEntrega.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${anticipoContraEntregaInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>

			</ol>

            <fieldset class="buttons">
                <div style="float: left">
                    <g:form>
                        <g:hiddenField name="id" value="${anticipoContraEntregaInstance?.id}" />
                        <g:link class="edit" action="edit" id="${anticipoContraEntregaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </g:form>
                </div>
                <div style="float: right">
                    <g:jasperReport controller="anticipoContraEntrega" action="crearReporte" jasper="anticipo_contra_entrega3" format="PDF" _format="PDF" name="ComprobanteAnticipoContraEntrega_${anticipoContraEntregaInstance.numeroAnticipo}">
                        <input type="hidden" name="ACE_ID" value="${anticipoContraEntregaInstance.id}" />
                    </g:jasperReport>
                </div>
            </fieldset>
		</div>
	</body>
</html>
