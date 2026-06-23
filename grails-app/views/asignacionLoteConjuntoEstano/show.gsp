
<%@ page import="org.socymet.liquidacion.AsignacionLoteConjuntoEstano" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'asignacionLoteConjuntoEstano.label', default: 'AsignacionLoteConjuntoEstano')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-asignacionLoteConjuntoEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-asignacionLoteConjuntoEstano" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list asignacionLoteConjuntoEstano">
			
				<g:if test="${asignacionLoteConjuntoEstanoInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="asignacionLoteConjuntoEstano.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${asignacionLoteConjuntoEstanoInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoEstanoInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="asignacionLoteConjuntoEstano.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${asignacionLoteConjuntoEstanoInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoEstanoInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="asignacionLoteConjuntoEstano.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${asignacionLoteConjuntoEstanoInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoEstanoInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="asignacionLoteConjuntoEstano.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${asignacionLoteConjuntoEstanoInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoEstanoInstance?.fechaDeLiquidacion}">
				<li class="fieldcontain">
					<span id="fechaDeLiquidacion-label" class="property-label"><g:message code="asignacionLoteConjuntoEstano.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:fieldValue bean="${asignacionLoteConjuntoEstanoInstance}" field="fechaDeLiquidacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoEstanoInstance?.kilosNetosSecos}">
				<li class="fieldcontain">
					<span id="kilosNetosSecos-label" class="property-label"><g:message code="asignacionLoteConjuntoEstano.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>
					
						<span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${asignacionLoteConjuntoEstanoInstance}" field="kilosNetosSecos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoEstanoInstance?.porcentajeEstano}">
				<li class="fieldcontain">
					<span id="porcentajeEstano-label" class="property-label"><g:message code="asignacionLoteConjuntoEstano.porcentajeEstano.label" default="Porcentaje Estano" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeEstano-label"><g:fieldValue bean="${asignacionLoteConjuntoEstanoInstance}" field="porcentajeEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoEstanoInstance?.fechaDeAsignacion}">
				<li class="fieldcontain">
					<span id="fechaDeAsignacion-label" class="property-label"><g:message code="asignacionLoteConjuntoEstano.fechaDeAsignacion.label" default="Fecha De Asignacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeAsignacion-label"><g:formatDate date="${asignacionLoteConjuntoEstanoInstance?.fechaDeAsignacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoEstanoInstance?.conjuntoDestino}">
				<li class="fieldcontain">
					<span id="conjuntoDestino-label" class="property-label"><g:message code="asignacionLoteConjuntoEstano.conjuntoDestino.label" default="Conjunto Destino" /></span>
					
						<span class="property-value" aria-labelledby="conjuntoDestino-label"><g:fieldValue bean="${asignacionLoteConjuntoEstanoInstance}" field="conjuntoDestino"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${asignacionLoteConjuntoEstanoInstance?.motivo}">
				<li class="fieldcontain">
					<span id="motivo-label" class="property-label"><g:message code="asignacionLoteConjuntoEstano.motivo.label" default="Motivo" /></span>
					
						<span class="property-value" aria-labelledby="motivo-label"><g:fieldValue bean="${asignacionLoteConjuntoEstanoInstance}" field="motivo"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${asignacionLoteConjuntoEstanoInstance?.id}" />
					<g:link class="edit" action="edit" id="${asignacionLoteConjuntoEstanoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
