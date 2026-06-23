
<%@ page import="org.socymet.liquidacion.AsignacionLoteConjuntoAntimonio" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'asignacionLoteConjuntoAntimonio.label', default: 'AsignacionLoteConjuntoAntimonio')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-asignacionLoteConjuntoAntimonio" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-asignacionLoteConjuntoAntimonio" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list asignacionLoteConjuntoAntimonio">
			
				<g:if test="${asignacionLoteConjuntoAntimonioInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="asignacionLoteConjuntoAntimonio.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${asignacionLoteConjuntoAntimonioInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoAntimonioInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="asignacionLoteConjuntoAntimonio.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${asignacionLoteConjuntoAntimonioInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoAntimonioInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="asignacionLoteConjuntoAntimonio.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${asignacionLoteConjuntoAntimonioInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoAntimonioInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="asignacionLoteConjuntoAntimonio.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${asignacionLoteConjuntoAntimonioInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoAntimonioInstance?.fechaDeLiquidacion}">
				<li class="fieldcontain">
					<span id="fechaDeLiquidacion-label" class="property-label"><g:message code="asignacionLoteConjuntoAntimonio.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:fieldValue bean="${asignacionLoteConjuntoAntimonioInstance}" field="fechaDeLiquidacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoAntimonioInstance?.kilosNetosSecos}">
				<li class="fieldcontain">
					<span id="kilosNetosSecos-label" class="property-label"><g:message code="asignacionLoteConjuntoAntimonio.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>
					
						<span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${asignacionLoteConjuntoAntimonioInstance}" field="kilosNetosSecos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoAntimonioInstance?.porcentajeAntimonio}">
				<li class="fieldcontain">
					<span id="porcentajeAntimonio-label" class="property-label"><g:message code="asignacionLoteConjuntoAntimonio.porcentajeAntimonio.label" default="Porcentaje Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeAntimonio-label"><g:fieldValue bean="${asignacionLoteConjuntoAntimonioInstance}" field="porcentajeAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoAntimonioInstance?.fechaDeAsignacion}">
				<li class="fieldcontain">
					<span id="fechaDeAsignacion-label" class="property-label"><g:message code="asignacionLoteConjuntoAntimonio.fechaDeAsignacion.label" default="Fecha De Asignacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeAsignacion-label"><g:formatDate date="${asignacionLoteConjuntoAntimonioInstance?.fechaDeAsignacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoAntimonioInstance?.conjuntoDestino}">
				<li class="fieldcontain">
					<span id="conjuntoDestino-label" class="property-label"><g:message code="asignacionLoteConjuntoAntimonio.conjuntoDestino.label" default="Conjunto Destino" /></span>
					
						<span class="property-value" aria-labelledby="conjuntoDestino-label"><g:fieldValue bean="${asignacionLoteConjuntoAntimonioInstance}" field="conjuntoDestino"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoAntimonioInstance?.motivo}">
				<li class="fieldcontain">
					<span id="motivo-label" class="property-label"><g:message code="asignacionLoteConjuntoAntimonio.motivo.label" default="Motivo" /></span>
					
						<span class="property-value" aria-labelledby="motivo-label"><g:fieldValue bean="${asignacionLoteConjuntoAntimonioInstance}" field="motivo"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${asignacionLoteConjuntoAntimonioInstance?.id}" />
					<g:link class="edit" action="edit" id="${asignacionLoteConjuntoAntimonioInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
