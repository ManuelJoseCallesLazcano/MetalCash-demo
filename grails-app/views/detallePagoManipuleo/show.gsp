
<%@ page import="org.socymet.cancelacion.DetallePagoManipuleo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'detallePagoManipuleo.label', default: 'DetallePagoManipuleo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-detallePagoManipuleo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-detallePagoManipuleo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list detallePagoManipuleo">
			
				<g:if test="${detallePagoManipuleoInstance?.pagoManipuleo}">
				<li class="fieldcontain">
					<span id="pagoManipuleo-label" class="property-label"><g:message code="detallePagoManipuleo.pagoManipuleo.label" default="Pago Manipuleo" /></span>
					
						<span class="property-value" aria-labelledby="pagoManipuleo-label"><g:link controller="pagoManipuleo" action="show" id="${detallePagoManipuleoInstance?.pagoManipuleo?.id}">${detallePagoManipuleoInstance?.pagoManipuleo?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="detallePagoManipuleo.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${detallePagoManipuleoInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.recepcionId}">
				<li class="fieldcontain">
					<span id="recepcionId-label" class="property-label"><g:message code="detallePagoManipuleo.recepcionId.label" default="Recepcion Id" /></span>
					
						<span class="property-value" aria-labelledby="recepcionId-label"><g:fieldValue bean="${detallePagoManipuleoInstance}" field="recepcionId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="detallePagoManipuleo.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:formatDate date="${detallePagoManipuleoInstance?.fechaDeRecepcion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="detallePagoManipuleo.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${detallePagoManipuleoInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.tipoDeMaterial}">
				<li class="fieldcontain">
					<span id="tipoDeMaterial-label" class="property-label"><g:message code="detallePagoManipuleo.tipoDeMaterial.label" default="Tipo De Material" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeMaterial-label"><g:fieldValue bean="${detallePagoManipuleoInstance}" field="tipoDeMaterial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.pesadaVaciada}">
				<li class="fieldcontain">
					<span id="pesadaVaciada-label" class="property-label"><g:message code="detallePagoManipuleo.pesadaVaciada.label" default="Pesada Vaciada" /></span>
					
						<span class="property-value" aria-labelledby="pesadaVaciada-label"><g:fieldValue bean="${detallePagoManipuleoInstance}" field="pesadaVaciada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.carguioMaquina}">
				<li class="fieldcontain">
					<span id="carguioMaquina-label" class="property-label"><g:message code="detallePagoManipuleo.carguioMaquina.label" default="Carguio Maquina" /></span>
					
						<span class="property-value" aria-labelledby="carguioMaquina-label"><g:fieldValue bean="${detallePagoManipuleoInstance}" field="carguioMaquina"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.embolsadaArrumada}">
				<li class="fieldcontain">
					<span id="embolsadaArrumada-label" class="property-label"><g:message code="detallePagoManipuleo.embolsadaArrumada.label" default="Embolsada Arrumada" /></span>
					
						<span class="property-value" aria-labelledby="embolsadaArrumada-label"><g:fieldValue bean="${detallePagoManipuleoInstance}" field="embolsadaArrumada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.soloComuneada}">
				<li class="fieldcontain">
					<span id="soloComuneada-label" class="property-label"><g:message code="detallePagoManipuleo.soloComuneada.label" default="Solo Comuneada" /></span>
					
						<span class="property-value" aria-labelledby="soloComuneada-label"><g:fieldValue bean="${detallePagoManipuleoInstance}" field="soloComuneada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.soloVaciada}">
				<li class="fieldcontain">
					<span id="soloVaciada-label" class="property-label"><g:message code="detallePagoManipuleo.soloVaciada.label" default="Solo Vaciada" /></span>
					
						<span class="property-value" aria-labelledby="soloVaciada-label"><g:fieldValue bean="${detallePagoManipuleoInstance}" field="soloVaciada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.soloPesada}">
				<li class="fieldcontain">
					<span id="soloPesada-label" class="property-label"><g:message code="detallePagoManipuleo.soloPesada.label" default="Solo Pesada" /></span>
					
						<span class="property-value" aria-labelledby="soloPesada-label"><g:fieldValue bean="${detallePagoManipuleoInstance}" field="soloPesada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.soloEmbolsada}">
				<li class="fieldcontain">
					<span id="soloEmbolsada-label" class="property-label"><g:message code="detallePagoManipuleo.soloEmbolsada.label" default="Solo Embolsada" /></span>
					
						<span class="property-value" aria-labelledby="soloEmbolsada-label"><g:fieldValue bean="${detallePagoManipuleoInstance}" field="soloEmbolsada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoManipuleoInstance?.costoDeManipuleo}">
				<li class="fieldcontain">
					<span id="costoDeManipuleo-label" class="property-label"><g:message code="detallePagoManipuleo.costoDeManipuleo.label" default="Costo De Manipuleo" /></span>
					
						<span class="property-value" aria-labelledby="costoDeManipuleo-label"><g:fieldValue bean="${detallePagoManipuleoInstance}" field="costoDeManipuleo"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${detallePagoManipuleoInstance?.id}" />
					<g:link class="edit" action="edit" id="${detallePagoManipuleoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
