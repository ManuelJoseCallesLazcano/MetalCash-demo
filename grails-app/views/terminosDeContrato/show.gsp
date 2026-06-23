
<%@ page import="org.socymet.cotizaciones.TerminosDeContrato" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-terminosDeContrato" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-terminosDeContrato" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list terminosDeContrato">
			
				<g:if test="${terminosDeContratoInstance?.nombreContrato}">
				<li class="fieldcontain">
					<span id="nombreContrato-label" class="property-label"><g:message code="terminosDeContrato.nombreContrato.label" default="Nombre Contrato" /></span>
					
						<span class="property-value" aria-labelledby="nombreContrato-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="nombreContrato"/></span>
					
				</li>
				</g:if>
			
				%{--<g:if test="${terminosDeContratoInstance?.empresa}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="empresa-label" class="property-label"><g:message code="terminosDeContrato.empresa.label" default="Empresa" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${terminosDeContratoInstance?.empresa?.id}">${terminosDeContratoInstance?.empresa?.encodeAsHTML()}</g:link></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%

            <h1 style="font-weight: bold">Impurezas</h1>
			
				<g:if test="${terminosDeContratoInstance?.porcentajeArsenico}">
				<li class="fieldcontain">
					<span id="porcentajeArsenico-label" class="property-label"><g:message code="terminosDeContrato.porcentajeArsenico.label" default="Porcentaje Arsenico" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeArsenico-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeArsenico"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.porcentajeAntimonio}">
				<li class="fieldcontain">
					<span id="porcentajeAntimonio-label" class="property-label"><g:message code="terminosDeContrato.porcentajeAntimonio.label" default="Porcentaje Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeAntimonio-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.porcentajeBismuto}">
				<li class="fieldcontain">
					<span id="porcentajeBismuto-label" class="property-label"><g:message code="terminosDeContrato.porcentajeBismuto.label" default="Porcentaje Bismuto" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeBismuto-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeBismuto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.porcentajeEstano}">
				<li class="fieldcontain">
					<span id="porcentajeEstano-label" class="property-label"><g:message code="terminosDeContrato.porcentajeEstano.label" default="Porcentaje Estano" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeEstano-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.porcentajeHierro}">
				<li class="fieldcontain">
					<span id="porcentajeHierro-label" class="property-label"><g:message code="terminosDeContrato.porcentajeHierro.label" default="Porcentaje Hierro" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeHierro-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeHierro"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.porcentajeSilice}">
				<li class="fieldcontain">
					<span id="porcentajeSilice-label" class="property-label"><g:message code="terminosDeContrato.porcentajeSilice.label" default="Porcentaje Silice" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeSilice-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeSilice"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.porcentajeZinc}">
				<li class="fieldcontain">
					<span id="porcentajeZinc-label" class="property-label"><g:message code="terminosDeContrato.porcentajeZinc.label" default="Porcentaje Zinc" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeZinc-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeZinc"/></span>
					
				</li>
				</g:if>

            <h1 style="font-weight: bold">Deducciones Unitarias</h1>
			
				<g:if test="${terminosDeContratoInstance?.deduccionUnitariaZinc}">
				<li class="fieldcontain">
					<span id="deduccionUnitariaZinc-label" class="property-label"><g:message code="terminosDeContrato.deduccionUnitariaZinc.label" default="Deduccion Unitaria Zinc" /></span>
					
						<span class="property-value" aria-labelledby="deduccionUnitariaZinc-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="deduccionUnitariaZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.deduccionUnitariaPlomo}">
				<li class="fieldcontain">
					<span id="deduccionUnitariaPlomo-label" class="property-label"><g:message code="terminosDeContrato.deduccionUnitariaPlomo.label" default="Deduccion Unitaria Plomo" /></span>
					
						<span class="property-value" aria-labelledby="deduccionUnitariaPlomo-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="deduccionUnitariaPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.deduccionUnitariaPlata}">
				<li class="fieldcontain">
					<span id="deduccionUnitariaPlata-label" class="property-label"><g:message code="terminosDeContrato.deduccionUnitariaPlata.label" default="Deduccion Unitaria Plata" /></span>
					
						<span class="property-value" aria-labelledby="deduccionUnitariaPlata-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="deduccionUnitariaPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.deduccionUnitariaCobre}">
				<li class="fieldcontain">
					<span id="deduccionUnitariaCobre-label" class="property-label"><g:message code="terminosDeContrato.deduccionUnitariaCobre.label" default="Deduccion Unitaria Cobre" /></span>
					
						<span class="property-value" aria-labelledby="deduccionUnitariaCobre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="deduccionUnitariaCobre"/></span>
					
				</li>
				</g:if>

            <h1 style="font-weight: bold">Minerales Pagables</h1>
			
				<g:if test="${terminosDeContratoInstance?.porcentajePagableLMEZinc}">
				<li class="fieldcontain">
					<span id="porcentajePagableLMEZinc-label" class="property-label"><g:message code="terminosDeContrato.porcentajePagableLMEZinc.label" default="Porcentaje Pagable LME Zinc" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePagableLMEZinc-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajePagableLMEZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.porcentajePagableLMEPlomo}">
				<li class="fieldcontain">
					<span id="porcentajePagableLMEPlomo-label" class="property-label"><g:message code="terminosDeContrato.porcentajePagableLMEPlomo.label" default="Porcentaje Pagable LME Plomo" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePagableLMEPlomo-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajePagableLMEPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.porcentajePagableLMEPlata}">
				<li class="fieldcontain">
					<span id="porcentajePagableLMEPlata-label" class="property-label"><g:message code="terminosDeContrato.porcentajePagableLMEPlata.label" default="Porcentaje Pagable LME Plata" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePagableLMEPlata-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajePagableLMEPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.porcentajePagableLMECobre}">
				<li class="fieldcontain">
					<span id="porcentajePagableLMECobre-label" class="property-label"><g:message code="terminosDeContrato.porcentajePagableLMECobre.label" default="Porcentaje Pagable LME Cobre" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePagableLMECobre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajePagableLMECobre"/></span>
					
				</li>
				</g:if>

            <h1 style="font-weight: bold">Maquila + Escalador</h1>
			
				<g:if test="${terminosDeContratoInstance?.maquilaZincPlata}">
				<li class="fieldcontain">
					<span id="maquilaZincPlata-label" class="property-label"><g:message code="terminosDeContrato.maquilaZincPlata.label" default="Maquila Zinc Plata" /></span>
					
						<span class="property-value" aria-labelledby="maquilaZincPlata-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="maquilaZincPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.baseZincPlata}">
				<li class="fieldcontain">
					<span id="baseZincPlata-label" class="property-label"><g:message code="terminosDeContrato.baseZincPlata.label" default="Base Zinc Plata" /></span>
					
						<span class="property-value" aria-labelledby="baseZincPlata-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="baseZincPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.escaladorZincPlata}">
				<li class="fieldcontain">
					<span id="escaladorZincPlata-label" class="property-label"><g:message code="terminosDeContrato.escaladorZincPlata.label" default="Escalador Zinc Plata" /></span>
					
						<span class="property-value" aria-labelledby="escaladorZincPlata-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="escaladorZincPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.maquilaPlomoPlata}">
				<li class="fieldcontain">
					<span id="maquilaPlomoPlata-label" class="property-label"><g:message code="terminosDeContrato.maquilaPlomoPlata.label" default="Maquila Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="maquilaPlomoPlata-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="maquilaPlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.basePlomoPlata}">
				<li class="fieldcontain">
					<span id="basePlomoPlata-label" class="property-label"><g:message code="terminosDeContrato.basePlomoPlata.label" default="Base Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="basePlomoPlata-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="basePlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.escaladorPlomoPlata}">
				<li class="fieldcontain">
					<span id="escaladorPlomoPlata-label" class="property-label"><g:message code="terminosDeContrato.escaladorPlomoPlata.label" default="Escalador Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="escaladorPlomoPlata-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="escaladorPlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.maquilaCobre}">
				<li class="fieldcontain">
					<span id="maquilaCobre-label" class="property-label"><g:message code="terminosDeContrato.maquilaCobre.label" default="Maquila Cobre" /></span>
					
						<span class="property-value" aria-labelledby="maquilaCobre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="maquilaCobre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.baseCobre}">
				<li class="fieldcontain">
					<span id="baseCobre-label" class="property-label"><g:message code="terminosDeContrato.baseCobre.label" default="Base Cobre" /></span>
					
						<span class="property-value" aria-labelledby="baseCobre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="baseCobre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.escaladorCobre}">
				<li class="fieldcontain">
					<span id="escaladorCobre-label" class="property-label"><g:message code="terminosDeContrato.escaladorCobre.label" default="Escalador Cobre" /></span>
					
						<span class="property-value" aria-labelledby="escaladorCobre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="escaladorCobre"/></span>
					
				</li>
				</g:if>

            <h1 style="font-weight: bold">Gastos de Refinacion por Onza</h1>
			
				<g:if test="${terminosDeContratoInstance?.deduccionRefinacionOnzaZincPlata}">
				<li class="fieldcontain">
					<span id="deduccionRefinacionOnzaZincPlata-label" class="property-label"><g:message code="terminosDeContrato.deduccionRefinacionOnzaZincPlata.label" default="Deduccion Refinacion Onza Zinc Plata" /></span>
					
						<span class="property-value" aria-labelledby="deduccionRefinacionOnzaZincPlata-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="deduccionRefinacionOnzaZincPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.deduccionRefinacionOnzaPlomoPlata}">
				<li class="fieldcontain">
					<span id="deduccionRefinacionOnzaPlomoPlata-label" class="property-label"><g:message code="terminosDeContrato.deduccionRefinacionOnzaPlomoPlata.label" default="Deduccion Refinacion Onza Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="deduccionRefinacionOnzaPlomoPlata-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="deduccionRefinacionOnzaPlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.deduccionRefinacionOnzaCobrePlata}">
				<li class="fieldcontain">
					<span id="deduccionRefinacionOnzaCobrePlata-label" class="property-label"><g:message code="terminosDeContrato.deduccionRefinacionOnzaCobrePlata.label" default="Deduccion Refinacion Onza Cobre Plata" /></span>
					
						<span class="property-value" aria-labelledby="deduccionRefinacionOnzaCobrePlata-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="deduccionRefinacionOnzaCobrePlata"/></span>
					
				</li>
				</g:if>

            <h1 style="font-weight: bold">Gasto de Refinacion por Libra</h1>
			
				<g:if test="${terminosDeContratoInstance?.deduccionRefinacionLibraZinc}">
				<li class="fieldcontain">
					<span id="deduccionRefinacionLibraZinc-label" class="property-label"><g:message code="terminosDeContrato.deduccionRefinacionLibraZinc.label" default="Deduccion Refinacion Libra Zinc" /></span>
					
						<span class="property-value" aria-labelledby="deduccionRefinacionLibraZinc-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="deduccionRefinacionLibraZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.deduccionRefinacionLibraPlomo}">
				<li class="fieldcontain">
					<span id="deduccionRefinacionLibraPlomo-label" class="property-label"><g:message code="terminosDeContrato.deduccionRefinacionLibraPlomo.label" default="Deduccion Refinacion Libra Plomo" /></span>
					
						<span class="property-value" aria-labelledby="deduccionRefinacionLibraPlomo-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="deduccionRefinacionLibraPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.deduccionRefinacionLibraCobre}">
				<li class="fieldcontain">
					<span id="deduccionRefinacionLibraCobre-label" class="property-label"><g:message code="terminosDeContrato.deduccionRefinacionLibraCobre.label" default="Deduccion Refinacion Libra Cobre" /></span>
					
						<span class="property-value" aria-labelledby="deduccionRefinacionLibraCobre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="deduccionRefinacionLibraCobre"/></span>
					
				</li>
				</g:if>

            <h1 style="font-weight: bold">Penalidades</h1>

            <table>
                <thead>
                <tr>
                    <th>ELEMENTO</th>
                    <th>LIBRE</th>
                    <th>COSTO UNITARIO</th>
                    <th>PORCENTAJE UNITARIO</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'arsenicoLibre', 'error')} required">
                        <label for="arsenicoLibre" style="width: 50%">
                            <g:message code="terminosDeContrato.arsenicoLibre.label" default="Arsenico" />
                            <span class="required-indicator">*</span>
                        </label>
                    </td>
                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'arsenicoLibre', 'error')} required">
                        <span class="property-value" aria-labelledby="arsenicoLibre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="arsenicoLibre"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioArsenico', 'error')} required">
                    </label>
                        <span class="property-value" aria-labelledby="costoUnitarioArsenico-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="costoUnitarioArsenico"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioArsenico', 'error')} required">
                        <span class="property-value" aria-labelledby="porcentajeUnitarioArsenico-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeUnitarioArsenico"/></span>
                    </td>
                </tr>

                <tr>
                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'antimonioLibre', 'error')} required">
                        <label for="antimonioLibre" style="width: 60%">
                            <g:message code="terminosDeContrato.antimonioLibre.label" default="Antimonio" />
                            <span class="required-indicator">*</span>
                        </label>
                    </td>
                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'antimonioLibre', 'error')} required">
                        <span class="property-value" aria-labelledby="antimonioLibre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="antimonioLibre"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioAntimonio', 'error')} required">
                        <span class="property-value" aria-labelledby="costoUnitarioAntimonio-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="costoUnitarioAntimonio"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioAntimonio', 'error')} required">
                        <span class="property-value" aria-labelledby="porcentajeUnitarioAntimonio-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeUnitarioAntimonio"/></span>
                    </td>
                </tr>

                <tr>
                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'bismutoLibre', 'error')} required">
                        <label for="bismutoLibre" style="width: 50%">
                            <g:message code="terminosDeContrato.bismutoLibre.label" default="Bismuto" />
                            <span class="required-indicator">*</span>
                        </label>
                    </td>
                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'bismutoLibre', 'error')} required">
                        <span class="property-value" aria-labelledby="bismutoLibre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="bismutoLibre"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioBismuto', 'error')} required">
                        <span class="property-value" aria-labelledby="costoUnitarioBismuto-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="costoUnitarioBismuto"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioBismuto', 'error')} required">
                        <span class="property-value" aria-labelledby="porcentajeUnitarioBismuto-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeUnitarioBismuto"/></span>
                    </td>
                </tr>

                <tr>
                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'estanoLibre', 'error')} required">
                        <label for="estanoLibre" style="width: 50%">
                            <g:message code="terminosDeContrato.estanoLibre.label" default="Estano" />
                            <span class="required-indicator">*</span>
                        </label>
                    </td>
                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'estanoLibre', 'error')} required">
                        <span class="property-value" aria-labelledby="estanoLibre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="estanoLibre"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioEstano', 'error')} required">
                        <span class="property-value" aria-labelledby="costoUnitarioEstano-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="costoUnitarioEstano"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioEstano', 'error')} required">
                        <span class="property-value" aria-labelledby="porcentajeUnitarioEstano-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeUnitarioEstano"/></span>
                    </td>
                </tr>

                <tr>
                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'hierroLibre', 'error')} required">
                        <label for="hierroLibre" style="width: 50%">
                            <g:message code="terminosDeContrato.hierroLibre.label" default="Hierro" />
                            <span class="required-indicator">*</span>
                        </label>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'hierroLibre', 'error')} required">
                        <span class="property-value" aria-labelledby="hierroLibre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="hierroLibre"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioHierro', 'error')} required">
                        <span class="property-value" aria-labelledby="costoUnitarioHierro-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="costoUnitarioHierro"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioHierro', 'error')} required">
                        <span class="property-value" aria-labelledby="porcentajeUnitarioHierro-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeUnitarioHierro"/></span>
                    </td>
                </tr>

                <tr>
                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'siliceLibre', 'error')} required">
                        <label for="siliceLibre" style="width: 50%">
                            <g:message code="terminosDeContrato.siliceLibre.label" default="Silice" />
                            <span class="required-indicator">*</span>
                        </label>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'siliceLibre', 'error')} required">
                        <span class="property-value" aria-labelledby="siliceLibre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="siliceLibre"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioSilice', 'error')} required">
                        <span class="property-value" aria-labelledby="costoUnitarioSilice-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="costoUnitarioSilice"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioSilice', 'error')} required">
                        <span class="property-value" aria-labelledby="porcentajeUnitarioSilice-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeUnitarioSilice"/></span>
                    </td>
                </tr>

                <tr>
                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'zincLibre', 'error')} required">
                        <label for="zincLibre" style="width: 50%">
                            <g:message code="terminosDeContrato.zincLibre.label" default="Zinc" />
                            <span class="required-indicator">*</span>
                        </label>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'zincLibre', 'error')} required">
                        <span class="property-value" aria-labelledby="zincLibre-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="zincLibre"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioZinc', 'error')} required">
                        <span class="property-value" aria-labelledby="costoUnitarioZinc-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="costoUnitarioZinc"/></span>
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioZinc', 'error')} required">
                        <span class="property-value" aria-labelledby="porcentajeUnitarioZinc-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="porcentajeUnitarioZinc"/></span>
                    </td>
                </tr>
                </tbody>
            </table>

            <h1 style="font-weight: bold">Otros Gastos</h1>
			
				<g:if test="${terminosDeContratoInstance?.transporteInterno}">
				<li class="fieldcontain">
					<span id="transporteInterno-label" class="property-label"><g:message code="terminosDeContrato.transporteInterno.label" default="Transporte Interno" /></span>
					
						<span class="property-value" aria-labelledby="transporteInterno-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="transporteInterno"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.laboratorio}">
				<li class="fieldcontain">
					<span id="laboratorio-label" class="property-label"><g:message code="terminosDeContrato.laboratorio.label" default="Laboratorio" /></span>
					
						<span class="property-value" aria-labelledby="laboratorio-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="laboratorio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.molienda}">
				<li class="fieldcontain">
					<span id="molienda-label" class="property-label"><g:message code="terminosDeContrato.molienda.label" default="Molienda" /></span>
					
						<span class="property-value" aria-labelledby="molienda-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="molienda"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.manipuleo}">
				<li class="fieldcontain">
					<span id="manipuleo-label" class="property-label"><g:message code="terminosDeContrato.manipuleo.label" default="Manipuleo" /></span>
					
						<span class="property-value" aria-labelledby="manipuleo-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="manipuleo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.margenAdministrativo}">
				<li class="fieldcontain">
					<span id="margenAdministrativo-label" class="property-label"><g:message code="terminosDeContrato.margenAdministrativo.label" default="Margen Administrativo" /></span>
					
						<span class="property-value" aria-labelledby="margenAdministrativo-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="margenAdministrativo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.transporteAPuerto}">
				<li class="fieldcontain">
					<span id="transporteAPuerto-label" class="property-label"><g:message code="terminosDeContrato.transporteAPuerto.label" default="Transporte AP uerto" /></span>
					
						<span class="property-value" aria-labelledby="transporteAPuerto-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="transporteAPuerto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${terminosDeContratoInstance?.rollBack}">
				<li class="fieldcontain">
					<span id="rollBack-label" class="property-label"><g:message code="terminosDeContrato.rollBack.label" default="Roll Back" /></span>
					
						<span class="property-value" aria-labelledby="rollBack-label"><g:fieldValue bean="${terminosDeContratoInstance}" field="rollBack"/></span>
					
				</li>
				</g:if>

			</ol>

        <fieldset class="buttons">
            <div style="float: left">
                <g:hiddenField name="id" value="${terminosDeContratoInstance?.id}" />
                <g:link class="edit" action="edit" id="${terminosDeContratoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            </div>
            %{--<div style="float: right">--}%
                %{--<g:jasperReport controller="terminosDeContrato" action="crearReporte" jasper="terminos_contrato" format="PDF" name="TERMINOS_${terminosDeContratoInstance.empresa.nombreDeEmpresa.replace(' ','_')}">--}%
                    %{--<input type="hidden" name="terminos_id" value="${terminosDeContratoInstance.id}" />--}%
                %{--</g:jasperReport>--}%
            %{--</div>--}%
        </fieldset>
		</div>
	</body>
</html>
