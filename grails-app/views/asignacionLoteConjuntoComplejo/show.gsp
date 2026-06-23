
<%@ page import="org.socymet.liquidacion.AsignacionLoteConjuntoComplejo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'asignacionLoteConjuntoComplejo.label', default: 'AsignacionLoteConjuntoComplejo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-asignacionLoteConjuntoComplejo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-asignacionLoteConjuntoComplejo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list asignacionLoteConjuntoComplejo">
			
				<g:if test="${asignacionLoteConjuntoComplejoInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="asignacionLoteConjuntoComplejo.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${asignacionLoteConjuntoComplejoInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoComplejoInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="asignacionLoteConjuntoComplejo.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${asignacionLoteConjuntoComplejoInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoComplejoInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="asignacionLoteConjuntoComplejo.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${asignacionLoteConjuntoComplejoInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoComplejoInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="asignacionLoteConjuntoComplejo.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${asignacionLoteConjuntoComplejoInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoComplejoInstance?.fechaDeLiquidacion}">
				<li class="fieldcontain">
					<span id="fechaDeLiquidacion-label" class="property-label"><g:message code="asignacionLoteConjuntoComplejo.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:fieldValue bean="${asignacionLoteConjuntoComplejoInstance}" field="fechaDeLiquidacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoComplejoInstance?.kilosNetosSecos}">
				<li class="fieldcontain">
					<span id="kilosNetosSecos-label" class="property-label"><g:message code="asignacionLoteConjuntoComplejo.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>
					
						<span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${asignacionLoteConjuntoComplejoInstance}" field="kilosNetosSecos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoComplejoInstance?.porcentajeZinc}">
				<li class="fieldcontain">
					<span id="porcentajeZinc-label" class="property-label"><g:message code="asignacionLoteConjuntoComplejo.porcentajeZinc.label" default="Porcentaje Zinc" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeZinc-label"><g:fieldValue bean="${asignacionLoteConjuntoComplejoInstance}" field="porcentajeZinc"/></span>
					
				</li>
				</g:if>

                <g:if test="${asignacionLoteConjuntoComplejoInstance?.porcentajePlomo}">
                    <li class="fieldcontain">
                        <span id="porcentajePlomo-label" class="property-label"><g:message code="asignacionLoteConjuntoComplejo.porcentajePlomo.label" default="Porcentaje Plomo" /></span>

                        <span class="property-value" aria-labelledby="porcentajePlomo-label"><g:fieldValue bean="${asignacionLoteConjuntoComplejoInstance}" field="porcentajePlomo"/></span>

                    </li>
                </g:if>

                <g:if test="${asignacionLoteConjuntoComplejoInstance?.porcentajePlata}">
                    <li class="fieldcontain">
                        <span id="porcentajePlata-label" class="property-label"><g:message code="asignacionLoteConjuntoComplejo.porcentajePlata.label" default="Porcentaje Plata" /></span>

                        <span class="property-value" aria-labelledby="porcentajePlata-label"><g:fieldValue bean="${asignacionLoteConjuntoComplejoInstance}" field="porcentajePlata"/></span>

                    </li>
                </g:if>
			
				<g:if test="${asignacionLoteConjuntoComplejoInstance?.fechaDeAsignacion}">
				<li class="fieldcontain">
					<span id="fechaDeAsignacion-label" class="property-label"><g:message code="asignacionLoteConjuntoComplejo.fechaDeAsignacion.label" default="Fecha De Asignacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeAsignacion-label"><g:formatDate date="${asignacionLoteConjuntoComplejoInstance?.fechaDeAsignacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoComplejoInstance?.conjuntoDestino}">
				<li class="fieldcontain">
					<span id="conjuntoDestino-label" class="property-label"><g:message code="asignacionLoteConjuntoComplejo.conjuntoDestino.label" default="Conjunto Destino" /></span>
					
						<span class="property-value" aria-labelledby="conjuntoDestino-label"><g:fieldValue bean="${asignacionLoteConjuntoComplejoInstance}" field="conjuntoDestino"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoComplejoInstance?.motivo}">
				<li class="fieldcontain">
					<span id="motivo-label" class="property-label"><g:message code="asignacionLoteConjuntoComplejo.motivo.label" default="Motivo" /></span>
					
						<span class="property-value" aria-labelledby="motivo-label"><g:fieldValue bean="${asignacionLoteConjuntoComplejoInstance}" field="motivo"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${asignacionLoteConjuntoComplejoInstance?.id}" />
					<g:link class="edit" action="edit" id="${asignacionLoteConjuntoComplejoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
