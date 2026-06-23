
<%@ page import="org.socymet.liquidacion.EliminacionLoteConjuntoEstano" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'eliminacionLoteConjuntoEstano.label', default: 'EliminacionLoteConjuntoEstano')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-eliminacionLoteConjuntoEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-eliminacionLoteConjuntoEstano" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list eliminacionLoteConjuntoEstano">
			
				<g:if test="${eliminacionLoteConjuntoEstanoInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="eliminacionLoteConjuntoEstano.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${eliminacionLoteConjuntoEstanoInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${eliminacionLoteConjuntoEstanoInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="eliminacionLoteConjuntoEstano.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${eliminacionLoteConjuntoEstanoInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${eliminacionLoteConjuntoEstanoInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="eliminacionLoteConjuntoEstano.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${eliminacionLoteConjuntoEstanoInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${eliminacionLoteConjuntoEstanoInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="eliminacionLoteConjuntoEstano.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${eliminacionLoteConjuntoEstanoInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${eliminacionLoteConjuntoEstanoInstance?.fechaDeLiquidacion}">
				<li class="fieldcontain">
					<span id="fechaDeLiquidacion-label" class="property-label"><g:message code="eliminacionLoteConjuntoEstano.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:fieldValue bean="${eliminacionLoteConjuntoEstanoInstance}" field="fechaDeLiquidacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${eliminacionLoteConjuntoEstanoInstance?.kilosNetosSecos}">
				<li class="fieldcontain">
					<span id="kilosNetosSecos-label" class="property-label"><g:message code="eliminacionLoteConjuntoEstano.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>
					
						<span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${eliminacionLoteConjuntoEstanoInstance}" field="kilosNetosSecos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${eliminacionLoteConjuntoEstanoInstance?.porcentajeEstano}">
				<li class="fieldcontain">
					<span id="porcentajeEstano-label" class="property-label"><g:message code="eliminacionLoteConjuntoEstano.porcentajeEstano.label" default="Porcentaje Estano" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeEstano-label"><g:fieldValue bean="${eliminacionLoteConjuntoEstanoInstance}" field="porcentajeEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${eliminacionLoteConjuntoEstanoInstance?.fechaDeAsignacion}">
				<li class="fieldcontain">
					<span id="fechaDeAsignacion-label" class="property-label"><g:message code="eliminacionLoteConjuntoEstano.fechaDeAsignacion.label" default="Fecha De Asignacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeAsignacion-label"><g:formatDate date="${eliminacionLoteConjuntoEstanoInstance?.fechaDeAsignacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${eliminacionLoteConjuntoEstanoInstance?.conjuntoOriginal}">
				<li class="fieldcontain">
					<span id="conjuntoOriginal-label" class="property-label"><g:message code="eliminacionLoteConjuntoEstano.conjuntoOriginal.label" default="Conjunto Original" /></span>
					
						<span class="property-value" aria-labelledby="conjuntoOriginal-label"><g:fieldValue bean="${eliminacionLoteConjuntoEstanoInstance}" field="conjuntoOriginal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${eliminacionLoteConjuntoEstanoInstance?.motivo}">
				<li class="fieldcontain">
					<span id="motivo-label" class="property-label"><g:message code="eliminacionLoteConjuntoEstano.motivo.label" default="Motivo" /></span>
					
						<span class="property-value" aria-labelledby="motivo-label"><g:fieldValue bean="${eliminacionLoteConjuntoEstanoInstance}" field="motivo"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${eliminacionLoteConjuntoEstanoInstance?.id}" />
					<g:link class="edit" action="edit" id="${eliminacionLoteConjuntoEstanoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
