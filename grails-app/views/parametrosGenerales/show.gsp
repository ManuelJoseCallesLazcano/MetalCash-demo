
<%@ page import="org.smart.parametros.ParametrosGenerales" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'parametrosGenerales.label', default: 'ParametrosGenerales')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-parametrosGenerales" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="show-parametrosGenerales" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list parametrosGenerales">
			
				<g:if test="${parametrosGeneralesInstance?.mesesPagablesBonoCantidad}">
				<li class="fieldcontain">
					<span id="mesesPagablesBonoCantidad-label" class="property-label"><g:message code="parametrosGenerales.mesesPagablesBonoCantidad.label" default="Meses Pagables Bono Cantidad" /></span>
					
						<span class="property-value" aria-labelledby="mesesPagablesBonoCantidad-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="mesesPagablesBonoCantidad"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.mesesPagablesBonoCalidad}">
				<li class="fieldcontain">
					<span id="mesesPagablesBonoCalidad-label" class="property-label"><g:message code="parametrosGenerales.mesesPagablesBonoCalidad.label" default="Meses Pagables Bono Calidad" /></span>
					
						<span class="property-value" aria-labelledby="mesesPagablesBonoCalidad-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="mesesPagablesBonoCalidad"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.mesesPagablesBonoTransporte}">
				<li class="fieldcontain">
					<span id="mesesPagablesBonoTransporte-label" class="property-label"><g:message code="parametrosGenerales.mesesPagablesBonoTransporte.label" default="Meses Pagables Bono Transporte" /></span>
					
						<span class="property-value" aria-labelledby="mesesPagablesBonoTransporte-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="mesesPagablesBonoTransporte"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.leyMinimaPlataBonoCalidad}">
				<li class="fieldcontain">
					<span id="leyMinimaPlataBonoCalidad-label" class="property-label"><g:message code="parametrosGenerales.leyMinimaPlataBonoCalidad.label" default="Ley Minima Plata Bono Calidad" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaPlataBonoCalidad-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="leyMinimaPlataBonoCalidad"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.leyBajaZincBonoTransporte}">
				<li class="fieldcontain">
					<span id="leyBajaZincBonoTransporte-label" class="property-label"><g:message code="parametrosGenerales.leyBajaZincBonoTransporte.label" default="Ley Baja Zinc Bono Transporte" /></span>
					
						<span class="property-value" aria-labelledby="leyBajaZincBonoTransporte-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="leyBajaZincBonoTransporte"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.leyBajaPlomoBonoTransporte}">
				<li class="fieldcontain">
					<span id="leyBajaPlomoBonoTransporte-label" class="property-label"><g:message code="parametrosGenerales.leyBajaPlomoBonoTransporte.label" default="Ley Baja Plomo Bono Transporte" /></span>
					
						<span class="property-value" aria-labelledby="leyBajaPlomoBonoTransporte-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="leyBajaPlomoBonoTransporte"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.leyAltaZincBonoTransporte}">
				<li class="fieldcontain">
					<span id="leyAltaZincBonoTransporte-label" class="property-label"><g:message code="parametrosGenerales.leyAltaZincBonoTransporte.label" default="Ley Alta Zinc Bono Transporte" /></span>
					
						<span class="property-value" aria-labelledby="leyAltaZincBonoTransporte-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="leyAltaZincBonoTransporte"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.leyAltaPlomoBonoTransporte}">
				<li class="fieldcontain">
					<span id="leyAltaPlomoBonoTransporte-label" class="property-label"><g:message code="parametrosGenerales.leyAltaPlomoBonoTransporte.label" default="Ley Alta Plomo Bono Transporte" /></span>
					
						<span class="property-value" aria-labelledby="leyAltaPlomoBonoTransporte-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="leyAltaPlomoBonoTransporte"/></span>
					
				</li>
				</g:if>

                <div>
                    <h1 style="font-weight: bold">Costos de Manipuleo</h1>
                </div>
			
				<g:if test="${parametrosGeneralesInstance?.pesadaVaciada}">
				<li class="fieldcontain">
					<span id="pesadaVaciada-label" class="property-label"><g:message code="parametrosGenerales.pesadaVaciada.label" default="Pesada Vaciada" /></span>
					
						<span class="property-value" aria-labelledby="pesadaVaciada-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="pesadaVaciada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.carguioMaquina}">
				<li class="fieldcontain">
					<span id="carguioMaquina-label" class="property-label"><g:message code="parametrosGenerales.carguioMaquina.label" default="Carguio Maquina" /></span>
					
						<span class="property-value" aria-labelledby="carguioMaquina-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="carguioMaquina"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.embolsadaArrumada}">
				<li class="fieldcontain">
					<span id="embolsadaArrumada-label" class="property-label"><g:message code="parametrosGenerales.embolsadaArrumada.label" default="Embolsada Arrumada" /></span>
					
						<span class="property-value" aria-labelledby="embolsadaArrumada-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="embolsadaArrumada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.soloComuneada}">
				<li class="fieldcontain">
					<span id="soloComuneada-label" class="property-label"><g:message code="parametrosGenerales.soloComuneada.label" default="Solo Comuneada" /></span>
					
						<span class="property-value" aria-labelledby="soloComuneada-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="soloComuneada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.soloVaciada}">
				<li class="fieldcontain">
					<span id="soloVaciada-label" class="property-label"><g:message code="parametrosGenerales.soloVaciada.label" default="Solo Vaciada" /></span>
					
						<span class="property-value" aria-labelledby="soloVaciada-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="soloVaciada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.soloPesada}">
				<li class="fieldcontain">
					<span id="soloPesada-label" class="property-label"><g:message code="parametrosGenerales.soloPesada.label" default="Solo Pesada" /></span>
					
						<span class="property-value" aria-labelledby="soloPesada-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="soloPesada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${parametrosGeneralesInstance?.soloEmbolsada}">
				<li class="fieldcontain">
					<span id="soloEmbolsada-label" class="property-label"><g:message code="parametrosGenerales.soloEmbolsada.label" default="Solo Embolsada" /></span>
					
						<span class="property-value" aria-labelledby="soloEmbolsada-label"><g:fieldValue bean="${parametrosGeneralesInstance}" field="soloEmbolsada"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${parametrosGeneralesInstance?.id}" />
					<g:link class="edit" action="edit" id="${parametrosGeneralesInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
