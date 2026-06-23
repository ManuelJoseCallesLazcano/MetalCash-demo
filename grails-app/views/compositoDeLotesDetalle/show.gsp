
<%@ page import="org.socymet.org.socymet.reportes.CompositoDeLotesDetalle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'compositoDeLotesDetalle.label', default: 'CompositoDeLotesDetalle')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-compositoDeLotesDetalle" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-compositoDeLotesDetalle" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list compositoDeLotesDetalle">
			
				<g:if test="${compositoDeLotesDetalleInstance?.reporteCompositoDeLotes}">
				<li class="fieldcontain">
					<span id="reporteCompositoDeLotes-label" class="property-label"><g:message code="compositoDeLotesDetalle.reporteCompositoDeLotes.label" default="Reporte Composito De Lotes" /></span>
					
						<span class="property-value" aria-labelledby="reporteCompositoDeLotes-label"><g:link controller="reporteCompositoDeLotes" action="show" id="${compositoDeLotesDetalleInstance?.reporteCompositoDeLotes?.id}">${compositoDeLotesDetalleInstance?.reporteCompositoDeLotes?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="compositoDeLotesDetalle.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.recepcionId}">
				<li class="fieldcontain">
					<span id="recepcionId-label" class="property-label"><g:message code="compositoDeLotesDetalle.recepcionId.label" default="Recepcion Id" /></span>
					
						<span class="property-value" aria-labelledby="recepcionId-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="recepcionId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.liquidacionId}">
				<li class="fieldcontain">
					<span id="liquidacionId-label" class="property-label"><g:message code="compositoDeLotesDetalle.liquidacionId.label" default="Liquidacion Id" /></span>
					
						<span class="property-value" aria-labelledby="liquidacionId-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="liquidacionId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="compositoDeLotesDetalle.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="compositoDeLotesDetalle.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:formatDate date="${compositoDeLotesDetalleInstance?.fechaDeRecepcion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="compositoDeLotesDetalle.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.kilosNetosSecos}">
				<li class="fieldcontain">
					<span id="kilosNetosSecos-label" class="property-label"><g:message code="compositoDeLotesDetalle.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>
					
						<span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="kilosNetosSecos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.porcentajeZincFinal}">
				<li class="fieldcontain">
					<span id="porcentajeZincFinal-label" class="property-label"><g:message code="compositoDeLotesDetalle.porcentajeZincFinal.label" default="Porcentaje Zinc Final" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeZincFinal-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="porcentajeZincFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.porcentajePlomoFinal}">
				<li class="fieldcontain">
					<span id="porcentajePlomoFinal-label" class="property-label"><g:message code="compositoDeLotesDetalle.porcentajePlomoFinal.label" default="Porcentaje Plomo Final" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePlomoFinal-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="porcentajePlomoFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.porcentajePlataFinal}">
				<li class="fieldcontain">
					<span id="porcentajePlataFinal-label" class="property-label"><g:message code="compositoDeLotesDetalle.porcentajePlataFinal.label" default="Porcentaje Plata Final" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePlataFinal-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="porcentajePlataFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.kilosFinosZinc}">
				<li class="fieldcontain">
					<span id="kilosFinosZinc-label" class="property-label"><g:message code="compositoDeLotesDetalle.kilosFinosZinc.label" default="Kilos Finos Zinc" /></span>
					
						<span class="property-value" aria-labelledby="kilosFinosZinc-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="kilosFinosZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.kilosFinosPlomo}">
				<li class="fieldcontain">
					<span id="kilosFinosPlomo-label" class="property-label"><g:message code="compositoDeLotesDetalle.kilosFinosPlomo.label" default="Kilos Finos Plomo" /></span>
					
						<span class="property-value" aria-labelledby="kilosFinosPlomo-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="kilosFinosPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.kilosFinosPlata}">
				<li class="fieldcontain">
					<span id="kilosFinosPlata-label" class="property-label"><g:message code="compositoDeLotesDetalle.kilosFinosPlata.label" default="Kilos Finos Plata" /></span>
					
						<span class="property-value" aria-labelledby="kilosFinosPlata-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="kilosFinosPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.valorNetoMineralEnBolivianos}">
				<li class="fieldcontain">
					<span id="valorNetoMineralEnBolivianos-label" class="property-label"><g:message code="compositoDeLotesDetalle.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" /></span>
					
						<span class="property-value" aria-labelledby="valorNetoMineralEnBolivianos-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="valorNetoMineralEnBolivianos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.costoDeTransporte}">
				<li class="fieldcontain">
					<span id="costoDeTransporte-label" class="property-label"><g:message code="compositoDeLotesDetalle.costoDeTransporte.label" default="Costo De Transporte" /></span>
					
						<span class="property-value" aria-labelledby="costoDeTransporte-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="costoDeTransporte"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.costoManipuleo}">
				<li class="fieldcontain">
					<span id="costoManipuleo-label" class="property-label"><g:message code="compositoDeLotesDetalle.costoManipuleo.label" default="Costo Manipuleo" /></span>
					
						<span class="property-value" aria-labelledby="costoManipuleo-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="costoManipuleo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.bonos}">
				<li class="fieldcontain">
					<span id="bonos-label" class="property-label"><g:message code="compositoDeLotesDetalle.bonos.label" default="Bonos" /></span>
					
						<span class="property-value" aria-labelledby="bonos-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="bonos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compositoDeLotesDetalleInstance?.valorDeCompra}">
				<li class="fieldcontain">
					<span id="valorDeCompra-label" class="property-label"><g:message code="compositoDeLotesDetalle.valorDeCompra.label" default="Valor De Compra" /></span>
					
						<span class="property-value" aria-labelledby="valorDeCompra-label"><g:fieldValue bean="${compositoDeLotesDetalleInstance}" field="valorDeCompra"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${compositoDeLotesDetalleInstance?.id}" />
					<g:link class="edit" action="edit" id="${compositoDeLotesDetalleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
