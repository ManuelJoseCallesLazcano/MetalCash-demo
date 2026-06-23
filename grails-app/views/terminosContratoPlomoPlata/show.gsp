
<%@ page import="org.socymet.cotizaciones.TerminosContratoPlomoPlata" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'terminosContratoPlomoPlata.label', default: 'TerminosContratoPlomoPlata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-terminosContratoPlomoPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-terminosContratoPlomoPlata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list terminosContratoPlomoPlata">
			
				<g:if test="${terminosContratoPlomoPlataInstance?.nombreTerminosContrato}">
				<li class="fieldcontain">
					<span id="nombreTerminosContrato-label" class="property-label"><g:message code="terminosContratoPlomoPlata.nombreTerminosContrato.label" default="Nombre Terminos Contrato" /></span>
					
						<span class="property-value" aria-labelledby="nombreTerminosContrato-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="nombreTerminosContrato"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.deduccionUnitariaPlomo}">
				<li class="fieldcontain">
					<span id="deduccionUnitariaPlomo-label" class="property-label"><g:message code="terminosContratoPlomoPlata.deduccionUnitariaPlomo.label" default="Deduccion Unitaria Plomo" /></span>
					
						<span class="property-value" aria-labelledby="deduccionUnitariaPlomo-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="deduccionUnitariaPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.deduccionUnitariaPlata}">
				<li class="fieldcontain">
					<span id="deduccionUnitariaPlata-label" class="property-label"><g:message code="terminosContratoPlomoPlata.deduccionUnitariaPlata.label" default="Deduccion Unitaria Plata" /></span>
					
						<span class="property-value" aria-labelledby="deduccionUnitariaPlata-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="deduccionUnitariaPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.porcentajePagablePlomo}">
				<li class="fieldcontain">
					<span id="porcentajePagablePlomo-label" class="property-label"><g:message code="terminosContratoPlomoPlata.porcentajePagablePlomo.label" default="Porcentaje Pagable Plomo" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePagablePlomo-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="porcentajePagablePlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.porcentajePagablePlata}">
				<li class="fieldcontain">
					<span id="porcentajePagablePlata-label" class="property-label"><g:message code="terminosContratoPlomoPlata.porcentajePagablePlata.label" default="Porcentaje Pagable Plata" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePagablePlata-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="porcentajePagablePlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.maquila}">
				<li class="fieldcontain">
					<span id="maquila-label" class="property-label"><g:message code="terminosContratoPlomoPlata.maquila.label" default="Maquila" /></span>
					
						<span class="property-value" aria-labelledby="maquila-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="maquila"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.basePlomo}">
				<li class="fieldcontain">
					<span id="basePlomo-label" class="property-label"><g:message code="terminosContratoPlomoPlata.basePlomo.label" default="Base Plomo" /></span>
					
						<span class="property-value" aria-labelledby="basePlomo-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="basePlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.escaladorPlomo}">
				<li class="fieldcontain">
					<span id="escaladorPlomo-label" class="property-label"><g:message code="terminosContratoPlomoPlata.escaladorPlomo.label" default="Escalador Plomo" /></span>
					
						<span class="property-value" aria-labelledby="escaladorPlomo-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="escaladorPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.gastoRefinacion}">
				<li class="fieldcontain">
					<span id="gastoRefinacion-label" class="property-label"><g:message code="terminosContratoPlomoPlata.gastoRefinacion.label" default="Gasto Refinacion" /></span>
					
						<span class="property-value" aria-labelledby="gastoRefinacion-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="gastoRefinacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.calidadLibreArsenico}">
				<li class="fieldcontain">
					<span id="calidadLibreArsenico-label" class="property-label"><g:message code="terminosContratoPlomoPlata.calidadLibreArsenico.label" default="Calidad Libre Arsenico" /></span>
					
						<span class="property-value" aria-labelledby="calidadLibreArsenico-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="calidadLibreArsenico"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.penalizacionArsenico}">
				<li class="fieldcontain">
					<span id="penalizacionArsenico-label" class="property-label"><g:message code="terminosContratoPlomoPlata.penalizacionArsenico.label" default="Penalizacion Arsenico" /></span>
					
						<span class="property-value" aria-labelledby="penalizacionArsenico-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="penalizacionArsenico"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.porcentajePenalizableArsenico}">
				<li class="fieldcontain">
					<span id="porcentajePenalizableArsenico-label" class="property-label"><g:message code="terminosContratoPlomoPlata.porcentajePenalizableArsenico.label" default="Porcentaje Penalizable Arsenico" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePenalizableArsenico-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="porcentajePenalizableArsenico"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.calidadLibreAntimonio}">
				<li class="fieldcontain">
					<span id="calidadLibreAntimonio-label" class="property-label"><g:message code="terminosContratoPlomoPlata.calidadLibreAntimonio.label" default="Calidad Libre Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="calidadLibreAntimonio-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="calidadLibreAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.penalizacionAntimonio}">
				<li class="fieldcontain">
					<span id="penalizacionAntimonio-label" class="property-label"><g:message code="terminosContratoPlomoPlata.penalizacionAntimonio.label" default="Penalizacion Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="penalizacionAntimonio-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="penalizacionAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.porcentajePenalizableAntimonio}">
				<li class="fieldcontain">
					<span id="porcentajePenalizableAntimonio-label" class="property-label"><g:message code="terminosContratoPlomoPlata.porcentajePenalizableAntimonio.label" default="Porcentaje Penalizable Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePenalizableAntimonio-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="porcentajePenalizableAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.calidadLibreBismuto}">
				<li class="fieldcontain">
					<span id="calidadLibreBismuto-label" class="property-label"><g:message code="terminosContratoPlomoPlata.calidadLibreBismuto.label" default="Calidad Libre Bismuto" /></span>
					
						<span class="property-value" aria-labelledby="calidadLibreBismuto-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="calidadLibreBismuto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.penalizacionBismuto}">
				<li class="fieldcontain">
					<span id="penalizacionBismuto-label" class="property-label"><g:message code="terminosContratoPlomoPlata.penalizacionBismuto.label" default="Penalizacion Bismuto" /></span>
					
						<span class="property-value" aria-labelledby="penalizacionBismuto-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="penalizacionBismuto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.porcentajePenalizableBismuto}">
				<li class="fieldcontain">
					<span id="porcentajePenalizableBismuto-label" class="property-label"><g:message code="terminosContratoPlomoPlata.porcentajePenalizableBismuto.label" default="Porcentaje Penalizable Bismuto" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePenalizableBismuto-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="porcentajePenalizableBismuto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.calidadLibreEstano}">
				<li class="fieldcontain">
					<span id="calidadLibreEstano-label" class="property-label"><g:message code="terminosContratoPlomoPlata.calidadLibreEstano.label" default="Calidad Libre Estano" /></span>
					
						<span class="property-value" aria-labelledby="calidadLibreEstano-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="calidadLibreEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.penalizacionEstano}">
				<li class="fieldcontain">
					<span id="penalizacionEstano-label" class="property-label"><g:message code="terminosContratoPlomoPlata.penalizacionEstano.label" default="Penalizacion Estano" /></span>
					
						<span class="property-value" aria-labelledby="penalizacionEstano-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="penalizacionEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.porcentajePenalizableEstano}">
				<li class="fieldcontain">
					<span id="porcentajePenalizableEstano-label" class="property-label"><g:message code="terminosContratoPlomoPlata.porcentajePenalizableEstano.label" default="Porcentaje Penalizable Estano" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePenalizableEstano-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="porcentajePenalizableEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.calidadLibreZinc}">
				<li class="fieldcontain">
					<span id="calidadLibreZinc-label" class="property-label"><g:message code="terminosContratoPlomoPlata.calidadLibreZinc.label" default="Calidad Libre Zinc" /></span>
					
						<span class="property-value" aria-labelledby="calidadLibreZinc-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="calidadLibreZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.penalizacionZinc}">
				<li class="fieldcontain">
					<span id="penalizacionZinc-label" class="property-label"><g:message code="terminosContratoPlomoPlata.penalizacionZinc.label" default="Penalizacion Zinc" /></span>
					
						<span class="property-value" aria-labelledby="penalizacionZinc-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="penalizacionZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosContratoPlomoPlataInstance?.porcentajePenalizableZinc}">
				<li class="fieldcontain">
					<span id="porcentajePenalizableZinc-label" class="property-label"><g:message code="terminosContratoPlomoPlata.porcentajePenalizableZinc.label" default="Porcentaje Penalizable Zinc" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePenalizableZinc-label"><g:fieldValue bean="${terminosContratoPlomoPlataInstance}" field="porcentajePenalizableZinc"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${terminosContratoPlomoPlataInstance?.id}" />
					<g:link class="edit" action="edit" id="${terminosContratoPlomoPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
