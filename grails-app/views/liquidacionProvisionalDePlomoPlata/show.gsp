
<%@ page import="org.socymet.liquidacion.LiquidacionProvisionalDePlomoPlata" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'liquidacionProvisionalDePlomoPlata.label', default: 'LiquidacionProvisionalDePlomoPlata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-liquidacionProvisionalDePlomoPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-liquidacionProvisionalDePlomoPlata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list liquidacionProvisionalDePlomoPlata">
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.fechaDeLiquidacionProvisional}">
				<li class="fieldcontain">
					<span id="fechaDeLiquidacionProvisional-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.fechaDeLiquidacionProvisional.label" default="Fecha De Liquidacion Provisional" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeLiquidacionProvisional-label"><g:formatDate date="${liquidacionProvisionalDePlomoPlataInstance?.fechaDeLiquidacionProvisional}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.numeroLiquidacionProvisionalPlomoPlata}">
				<li class="fieldcontain">
					<span id="numeroLiquidacionProvisionalPlomoPlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.numeroLiquidacionProvisionalPlomoPlata.label" default="Numero Liquidacion Provisional Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="numeroLiquidacionProvisionalPlomoPlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="numeroLiquidacionProvisionalPlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.recepcionDeComplejo}">
				<li class="fieldcontain">
					<span id="recepcionDeComplejo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.recepcionDeComplejo.label" default="Recepcion De Complejo" /></span>
					
						<span class="property-value" aria-labelledby="recepcionDeComplejo-label"><g:link controller="recepcionDeComplejo" action="show" id="${liquidacionProvisionalDePlomoPlataInstance?.recepcionDeComplejo?.id}">${liquidacionProvisionalDePlomoPlataInstance?.recepcionDeComplejo?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.deposito}">
				<li class="fieldcontain">
					<span id="deposito-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.deposito.label" default="Deposito" /></span>
					
						<span class="property-value" aria-labelledby="deposito-label"><g:link controller="deposito" action="show" id="${liquidacionProvisionalDePlomoPlataInstance?.deposito?.id}">${liquidacionProvisionalDePlomoPlataInstance?.deposito?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.cotizacionDiariaDeZinc}">
				<li class="fieldcontain">
					<span id="cotizacionDiariaDeZinc-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.cotizacionDiariaDeZinc.label" default="Cotizacion Diaria De Zinc" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionDiariaDeZinc-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="cotizacionDiariaDeZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.cotizacionQuincenalDeZinc}">
				<li class="fieldcontain">
					<span id="cotizacionQuincenalDeZinc-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.cotizacionQuincenalDeZinc.label" default="Cotizacion Quincenal De Zinc" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionQuincenalDeZinc-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="cotizacionQuincenalDeZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.alicuotaDeZinc}">
				<li class="fieldcontain">
					<span id="alicuotaDeZinc-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.alicuotaDeZinc.label" default="Alicuota De Zinc" /></span>
					
						<span class="property-value" aria-labelledby="alicuotaDeZinc-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="alicuotaDeZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.alicuotaDeZincParaExportacion}">
				<li class="fieldcontain">
					<span id="alicuotaDeZincParaExportacion-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.alicuotaDeZincParaExportacion.label" default="Alicuota De Zinc Para Exportacion" /></span>
					
						<span class="property-value" aria-labelledby="alicuotaDeZincParaExportacion-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="alicuotaDeZincParaExportacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.cotizacionDiariaDePlomo}">
				<li class="fieldcontain">
					<span id="cotizacionDiariaDePlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.cotizacionDiariaDePlomo.label" default="Cotizacion Diaria De Plomo" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionDiariaDePlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="cotizacionDiariaDePlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.cotizacionQuincenalDePlomo}">
				<li class="fieldcontain">
					<span id="cotizacionQuincenalDePlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.cotizacionQuincenalDePlomo.label" default="Cotizacion Quincenal De Plomo" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionQuincenalDePlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="cotizacionQuincenalDePlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.alicuotaDePlomo}">
				<li class="fieldcontain">
					<span id="alicuotaDePlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.alicuotaDePlomo.label" default="Alicuota De Plomo" /></span>
					
						<span class="property-value" aria-labelledby="alicuotaDePlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="alicuotaDePlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.alicuotaDePlomoParaExportacion}">
				<li class="fieldcontain">
					<span id="alicuotaDePlomoParaExportacion-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.alicuotaDePlomoParaExportacion.label" default="Alicuota De Plomo Para Exportacion" /></span>
					
						<span class="property-value" aria-labelledby="alicuotaDePlomoParaExportacion-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="alicuotaDePlomoParaExportacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.cotizacionDiariaDePlata}">
				<li class="fieldcontain">
					<span id="cotizacionDiariaDePlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.cotizacionDiariaDePlata.label" default="Cotizacion Diaria De Plata" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionDiariaDePlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="cotizacionDiariaDePlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.cotizacionQuincenalDePlata}">
				<li class="fieldcontain">
					<span id="cotizacionQuincenalDePlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.cotizacionQuincenalDePlata.label" default="Cotizacion Quincenal De Plata" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionQuincenalDePlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="cotizacionQuincenalDePlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.alicuotaDePlata}">
				<li class="fieldcontain">
					<span id="alicuotaDePlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.alicuotaDePlata.label" default="Alicuota De Plata" /></span>
					
						<span class="property-value" aria-labelledby="alicuotaDePlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="alicuotaDePlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.alicuotaDePlataParaExportacion}">
				<li class="fieldcontain">
					<span id="alicuotaDePlataParaExportacion-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.alicuotaDePlataParaExportacion.label" default="Alicuota De Plata Para Exportacion" /></span>
					
						<span class="property-value" aria-labelledby="alicuotaDePlataParaExportacion-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="alicuotaDePlataParaExportacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.tipoDeCambioOficial}">
				<li class="fieldcontain">
					<span id="tipoDeCambioOficial-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.tipoDeCambioOficial.label" default="Tipo De Cambio Oficial" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeCambioOficial-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="tipoDeCambioOficial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.tipoDeCambioComercial}">
				<li class="fieldcontain">
					<span id="tipoDeCambioComercial-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.tipoDeCambioComercial.label" default="Tipo De Cambio Comercial" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeCambioComercial-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="tipoDeCambioComercial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.cliente}">
				<li class="fieldcontain">
					<span id="cliente-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.cliente.label" default="Cliente" /></span>
					
						<span class="property-value" aria-labelledby="cliente-label"><g:link controller="cliente" action="show" id="${liquidacionProvisionalDePlomoPlataInstance?.cliente?.id}">${liquidacionProvisionalDePlomoPlataInstance?.cliente?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${liquidacionProvisionalDePlomoPlataInstance?.empresa?.id}">${liquidacionProvisionalDePlomoPlataInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.direccion}">
				<li class="fieldcontain">
					<span id="direccion-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.direccion.label" default="Direccion" /></span>
					
						<span class="property-value" aria-labelledby="direccion-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="direccion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.tipoDeMineral}">
				<li class="fieldcontain">
					<span id="tipoDeMineral-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.tipoDeMineral.label" default="Tipo De Mineral" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeMineral-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="tipoDeMineral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.naturalezaMineral}">
				<li class="fieldcontain">
					<span id="naturalezaMineral-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.naturalezaMineral.label" default="Naturaleza Mineral" /></span>
					
						<span class="property-value" aria-labelledby="naturalezaMineral-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="naturalezaMineral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.cantidadDeSacos}">
				<li class="fieldcontain">
					<span id="cantidadDeSacos-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>
					
						<span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="cantidadDeSacos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.toneladasMetricasHumedas}">
				<li class="fieldcontain">
					<span id="toneladasMetricasHumedas-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.toneladasMetricasHumedas.label" default="Toneladas Metricas Humedas" /></span>
					
						<span class="property-value" aria-labelledby="toneladasMetricasHumedas-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="toneladasMetricasHumedas"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.humedadPromedio}">
				<li class="fieldcontain">
					<span id="humedadPromedio-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.humedadPromedio.label" default="Humedad Promedio" /></span>
					
						<span class="property-value" aria-labelledby="humedadPromedio-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="humedadPromedio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.toneladasMetricasSecas}">
				<li class="fieldcontain">
					<span id="toneladasMetricasSecas-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.toneladasMetricasSecas.label" default="Toneladas Metricas Secas" /></span>
					
						<span class="property-value" aria-labelledby="toneladasMetricasSecas-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="toneladasMetricasSecas"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.merma}">
				<li class="fieldcontain">
					<span id="merma-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.merma.label" default="Merma" /></span>
					
						<span class="property-value" aria-labelledby="merma-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="merma"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.toneladasMetricasSecasFinales}">
				<li class="fieldcontain">
					<span id="toneladasMetricasSecasFinales-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.toneladasMetricasSecasFinales.label" default="Toneladas Metricas Secas Finales" /></span>
					
						<span class="property-value" aria-labelledby="toneladasMetricasSecasFinales-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="toneladasMetricasSecasFinales"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.partidaArancelaria}">
				<li class="fieldcontain">
					<span id="partidaArancelaria-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.partidaArancelaria.label" default="Partida Arancelaria" /></span>
					
						<span class="property-value" aria-labelledby="partidaArancelaria-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="partidaArancelaria"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.condicionesDeEntrega}">
				<li class="fieldcontain">
					<span id="condicionesDeEntrega-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.condicionesDeEntrega.label" default="Condiciones De Entrega" /></span>
					
						<span class="property-value" aria-labelledby="condicionesDeEntrega-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="condicionesDeEntrega"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.origen}">
				<li class="fieldcontain">
					<span id="origen-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.origen.label" default="Origen" /></span>
					
						<span class="property-value" aria-labelledby="origen-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="origen"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajePlomo}">
				<li class="fieldcontain">
					<span id="porcentajePlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajePlomo.label" default="Porcentaje Plomo" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajePlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.deduccionUnitariaPlomo}">
				<li class="fieldcontain">
					<span id="deduccionUnitariaPlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.deduccionUnitariaPlomo.label" default="Deduccion Unitaria Plomo" /></span>
					
						<span class="property-value" aria-labelledby="deduccionUnitariaPlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="deduccionUnitariaPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.leyPagablePlomo}">
				<li class="fieldcontain">
					<span id="leyPagablePlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.leyPagablePlomo.label" default="Ley Pagable Plomo" /></span>
					
						<span class="property-value" aria-labelledby="leyPagablePlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="leyPagablePlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajePagableLMEPlomo}">
				<li class="fieldcontain">
					<span id="porcentajePagableLMEPlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajePagableLMEPlomo.label" default="Porcentaje Pagable LMEP lomo" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePagableLMEPlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajePagableLMEPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.leyFinalPagablePlomo}">
				<li class="fieldcontain">
					<span id="leyFinalPagablePlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.leyFinalPagablePlomo.label" default="Ley Final Pagable Plomo" /></span>
					
						<span class="property-value" aria-labelledby="leyFinalPagablePlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="leyFinalPagablePlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.cotizacionPlomo}">
				<li class="fieldcontain">
					<span id="cotizacionPlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.cotizacionPlomo.label" default="Cotizacion Plomo" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionPlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="cotizacionPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.valorBrutoPlomo}">
				<li class="fieldcontain">
					<span id="valorBrutoPlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.valorBrutoPlomo.label" default="Valor Bruto Plomo" /></span>
					
						<span class="property-value" aria-labelledby="valorBrutoPlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="valorBrutoPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajePlata}">
				<li class="fieldcontain">
					<span id="porcentajePlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajePlata.label" default="Porcentaje Plata" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajePlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.deduccionUnitariaPlata}">
				<li class="fieldcontain">
					<span id="deduccionUnitariaPlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.deduccionUnitariaPlata.label" default="Deduccion Unitaria Plata" /></span>
					
						<span class="property-value" aria-labelledby="deduccionUnitariaPlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="deduccionUnitariaPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.leyPagablePlata}">
				<li class="fieldcontain">
					<span id="leyPagablePlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.leyPagablePlata.label" default="Ley Pagable Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyPagablePlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="leyPagablePlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajePagableLMEPlata}">
				<li class="fieldcontain">
					<span id="porcentajePagableLMEPlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajePagableLMEPlata.label" default="Porcentaje Pagable LMEP lata" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePagableLMEPlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajePagableLMEPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.leyFinalPagablePlata}">
				<li class="fieldcontain">
					<span id="leyFinalPagablePlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.leyFinalPagablePlata.label" default="Ley Final Pagable Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyFinalPagablePlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="leyFinalPagablePlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.cotizacionPlata}">
				<li class="fieldcontain">
					<span id="cotizacionPlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.cotizacionPlata.label" default="Cotizacion Plata" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionPlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="cotizacionPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.valorBrutoPlata}">
				<li class="fieldcontain">
					<span id="valorBrutoPlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.valorBrutoPlata.label" default="Valor Bruto Plata" /></span>
					
						<span class="property-value" aria-labelledby="valorBrutoPlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="valorBrutoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.valorBruto}">
				<li class="fieldcontain">
					<span id="valorBruto-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.valorBruto.label" default="Valor Bruto" /></span>
					
						<span class="property-value" aria-labelledby="valorBruto-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="valorBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.maquilaPlomoPlata}">
				<li class="fieldcontain">
					<span id="maquilaPlomoPlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.maquilaPlomoPlata.label" default="Maquila Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="maquilaPlomoPlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="maquilaPlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.basePlomoPlata}">
				<li class="fieldcontain">
					<span id="basePlomoPlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.basePlomoPlata.label" default="Base Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="basePlomoPlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="basePlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.cotizacionBasadaPlomo}">
				<li class="fieldcontain">
					<span id="cotizacionBasadaPlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.cotizacionBasadaPlomo.label" default="Cotizacion Basada Plomo" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionBasadaPlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="cotizacionBasadaPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.escaladorPlomoPlata}">
				<li class="fieldcontain">
					<span id="escaladorPlomoPlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.escaladorPlomoPlata.label" default="Escalador Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="escaladorPlomoPlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="escaladorPlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.cotizacionEscaladaPlomo}">
				<li class="fieldcontain">
					<span id="cotizacionEscaladaPlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.cotizacionEscaladaPlomo.label" default="Cotizacion Escalada Plomo" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionEscaladaPlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="cotizacionEscaladaPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.deduccionMaquilaFinalPlomo}">
				<li class="fieldcontain">
					<span id="deduccionMaquilaFinalPlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.deduccionMaquilaFinalPlomo.label" default="Deduccion Maquila Final Plomo" /></span>
					
						<span class="property-value" aria-labelledby="deduccionMaquilaFinalPlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="deduccionMaquilaFinalPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.deduccionRefinacionOnzaPlomoPlata}">
				<li class="fieldcontain">
					<span id="deduccionRefinacionOnzaPlomoPlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.deduccionRefinacionOnzaPlomoPlata.label" default="Deduccion Refinacion Onza Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="deduccionRefinacionOnzaPlomoPlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="deduccionRefinacionOnzaPlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.deduccionRefinacionOnzaPlomoPlataFinal}">
				<li class="fieldcontain">
					<span id="deduccionRefinacionOnzaPlomoPlataFinal-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.deduccionRefinacionOnzaPlomoPlataFinal.label" default="Deduccion Refinacion Onza Plomo Plata Final" /></span>
					
						<span class="property-value" aria-labelledby="deduccionRefinacionOnzaPlomoPlataFinal-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="deduccionRefinacionOnzaPlomoPlataFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeArsenico}">
				<li class="fieldcontain">
					<span id="porcentajeArsenico-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeArsenico.label" default="Porcentaje Arsenico" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeArsenico-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeArsenico"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.arsenicoLibre}">
				<li class="fieldcontain">
					<span id="arsenicoLibre-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.arsenicoLibre.label" default="Arsenico Libre" /></span>
					
						<span class="property-value" aria-labelledby="arsenicoLibre-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="arsenicoLibre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeUnitarioArsenico}">
				<li class="fieldcontain">
					<span id="porcentajeUnitarioArsenico-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioArsenico.label" default="Porcentaje Unitario Arsenico" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeUnitarioArsenico-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeUnitarioArsenico"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.costoUnitarioArsenico}">
				<li class="fieldcontain">
					<span id="costoUnitarioArsenico-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioArsenico.label" default="Costo Unitario Arsenico" /></span>
					
						<span class="property-value" aria-labelledby="costoUnitarioArsenico-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="costoUnitarioArsenico"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.penalidadCastigableArsenicoFinal}">
				<li class="fieldcontain">
					<span id="penalidadCastigableArsenicoFinal-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableArsenicoFinal.label" default="Penalidad Castigable Arsenico Final" /></span>
					
						<span class="property-value" aria-labelledby="penalidadCastigableArsenicoFinal-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="penalidadCastigableArsenicoFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeAntimonio}">
				<li class="fieldcontain">
					<span id="porcentajeAntimonio-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeAntimonio.label" default="Porcentaje Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeAntimonio-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.antimonioLibre}">
				<li class="fieldcontain">
					<span id="antimonioLibre-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.antimonioLibre.label" default="Antimonio Libre" /></span>
					
						<span class="property-value" aria-labelledby="antimonioLibre-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="antimonioLibre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeUnitarioAntimonio}">
				<li class="fieldcontain">
					<span id="porcentajeUnitarioAntimonio-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioAntimonio.label" default="Porcentaje Unitario Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeUnitarioAntimonio-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeUnitarioAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.costoUnitarioAntimonio}">
				<li class="fieldcontain">
					<span id="costoUnitarioAntimonio-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioAntimonio.label" default="Costo Unitario Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="costoUnitarioAntimonio-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="costoUnitarioAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.penalidadCastigableAntimonioFinal}">
				<li class="fieldcontain">
					<span id="penalidadCastigableAntimonioFinal-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableAntimonioFinal.label" default="Penalidad Castigable Antimonio Final" /></span>
					
						<span class="property-value" aria-labelledby="penalidadCastigableAntimonioFinal-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="penalidadCastigableAntimonioFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeBismuto}">
				<li class="fieldcontain">
					<span id="porcentajeBismuto-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeBismuto.label" default="Porcentaje Bismuto" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeBismuto-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeBismuto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.bismutoLibre}">
				<li class="fieldcontain">
					<span id="bismutoLibre-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.bismutoLibre.label" default="Bismuto Libre" /></span>
					
						<span class="property-value" aria-labelledby="bismutoLibre-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="bismutoLibre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeUnitarioBismuto}">
				<li class="fieldcontain">
					<span id="porcentajeUnitarioBismuto-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioBismuto.label" default="Porcentaje Unitario Bismuto" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeUnitarioBismuto-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeUnitarioBismuto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.costoUnitarioBismuto}">
				<li class="fieldcontain">
					<span id="costoUnitarioBismuto-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioBismuto.label" default="Costo Unitario Bismuto" /></span>
					
						<span class="property-value" aria-labelledby="costoUnitarioBismuto-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="costoUnitarioBismuto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.penalidadCastigableBismutoFinal}">
				<li class="fieldcontain">
					<span id="penalidadCastigableBismutoFinal-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableBismutoFinal.label" default="Penalidad Castigable Bismuto Final" /></span>
					
						<span class="property-value" aria-labelledby="penalidadCastigableBismutoFinal-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="penalidadCastigableBismutoFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeEstano}">
				<li class="fieldcontain">
					<span id="porcentajeEstano-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeEstano.label" default="Porcentaje Estano" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeEstano-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.estanoLibre}">
				<li class="fieldcontain">
					<span id="estanoLibre-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.estanoLibre.label" default="Estano Libre" /></span>
					
						<span class="property-value" aria-labelledby="estanoLibre-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="estanoLibre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeUnitarioEstano}">
				<li class="fieldcontain">
					<span id="porcentajeUnitarioEstano-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioEstano.label" default="Porcentaje Unitario Estano" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeUnitarioEstano-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeUnitarioEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.costoUnitarioEstano}">
				<li class="fieldcontain">
					<span id="costoUnitarioEstano-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioEstano.label" default="Costo Unitario Estano" /></span>
					
						<span class="property-value" aria-labelledby="costoUnitarioEstano-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="costoUnitarioEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.penalidadCastigableEstanoFinal}">
				<li class="fieldcontain">
					<span id="penalidadCastigableEstanoFinal-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableEstanoFinal.label" default="Penalidad Castigable Estano Final" /></span>
					
						<span class="property-value" aria-labelledby="penalidadCastigableEstanoFinal-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="penalidadCastigableEstanoFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeHierro}">
				<li class="fieldcontain">
					<span id="porcentajeHierro-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeHierro.label" default="Porcentaje Hierro" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeHierro-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeHierro"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.hierroLibre}">
				<li class="fieldcontain">
					<span id="hierroLibre-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.hierroLibre.label" default="Hierro Libre" /></span>
					
						<span class="property-value" aria-labelledby="hierroLibre-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="hierroLibre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeUnitarioHierro}">
				<li class="fieldcontain">
					<span id="porcentajeUnitarioHierro-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioHierro.label" default="Porcentaje Unitario Hierro" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeUnitarioHierro-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeUnitarioHierro"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.costoUnitarioHierro}">
				<li class="fieldcontain">
					<span id="costoUnitarioHierro-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioHierro.label" default="Costo Unitario Hierro" /></span>
					
						<span class="property-value" aria-labelledby="costoUnitarioHierro-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="costoUnitarioHierro"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.penalidadCastigableHierroFinal}">
				<li class="fieldcontain">
					<span id="penalidadCastigableHierroFinal-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableHierroFinal.label" default="Penalidad Castigable Hierro Final" /></span>
					
						<span class="property-value" aria-labelledby="penalidadCastigableHierroFinal-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="penalidadCastigableHierroFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeSilice}">
				<li class="fieldcontain">
					<span id="porcentajeSilice-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeSilice.label" default="Porcentaje Silice" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeSilice-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeSilice"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.siliceLibre}">
				<li class="fieldcontain">
					<span id="siliceLibre-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.siliceLibre.label" default="Silice Libre" /></span>
					
						<span class="property-value" aria-labelledby="siliceLibre-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="siliceLibre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeUnitarioSilice}">
				<li class="fieldcontain">
					<span id="porcentajeUnitarioSilice-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioSilice.label" default="Porcentaje Unitario Silice" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeUnitarioSilice-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeUnitarioSilice"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.costoUnitarioSilice}">
				<li class="fieldcontain">
					<span id="costoUnitarioSilice-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioSilice.label" default="Costo Unitario Silice" /></span>
					
						<span class="property-value" aria-labelledby="costoUnitarioSilice-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="costoUnitarioSilice"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.penalidadCastigableSiliceFinal}">
				<li class="fieldcontain">
					<span id="penalidadCastigableSiliceFinal-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableSiliceFinal.label" default="Penalidad Castigable Silice Final" /></span>
					
						<span class="property-value" aria-labelledby="penalidadCastigableSiliceFinal-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="penalidadCastigableSiliceFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeZinc}">
				<li class="fieldcontain">
					<span id="porcentajeZinc-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeZinc.label" default="Porcentaje Zinc" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeZinc-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.zincLibre}">
				<li class="fieldcontain">
					<span id="zincLibre-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.zincLibre.label" default="Zinc Libre" /></span>
					
						<span class="property-value" aria-labelledby="zincLibre-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="zincLibre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeUnitarioZinc}">
				<li class="fieldcontain">
					<span id="porcentajeUnitarioZinc-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioZinc.label" default="Porcentaje Unitario Zinc" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeUnitarioZinc-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeUnitarioZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.costoUnitarioZinc}">
				<li class="fieldcontain">
					<span id="costoUnitarioZinc-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioZinc.label" default="Costo Unitario Zinc" /></span>
					
						<span class="property-value" aria-labelledby="costoUnitarioZinc-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="costoUnitarioZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.penalidadCastigableZincFinal}">
				<li class="fieldcontain">
					<span id="penalidadCastigableZincFinal-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableZincFinal.label" default="Penalidad Castigable Zinc Final" /></span>
					
						<span class="property-value" aria-labelledby="penalidadCastigableZincFinal-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="penalidadCastigableZincFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.penalidadCastigableFinal}">
				<li class="fieldcontain">
					<span id="penalidadCastigableFinal-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableFinal.label" default="Penalidad Castigable Final" /></span>
					
						<span class="property-value" aria-labelledby="penalidadCastigableFinal-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="penalidadCastigableFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.valorNeto}">
				<li class="fieldcontain">
					<span id="valorNeto-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.valorNeto.label" default="Valor Neto" /></span>
					
						<span class="property-value" aria-labelledby="valorNeto-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="valorNeto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.porcentajeDePagoProvisional}">
				<li class="fieldcontain">
					<span id="porcentajeDePagoProvisional-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.porcentajeDePagoProvisional.label" default="Porcentaje De Pago Provisional" /></span>
					
						<span class="property-value" aria-labelledby="porcentajeDePagoProvisional-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="porcentajeDePagoProvisional"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.pagoProvisional}">
				<li class="fieldcontain">
					<span id="pagoProvisional-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.pagoProvisional.label" default="Pago Provisional" /></span>
					
						<span class="property-value" aria-labelledby="pagoProvisional-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="pagoProvisional"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.librasFinasPlomo}">
				<li class="fieldcontain">
					<span id="librasFinasPlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.librasFinasPlomo.label" default="Libras Finas Plomo" /></span>
					
						<span class="property-value" aria-labelledby="librasFinasPlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="librasFinasPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.valorOficialBrutoPlomo}">
				<li class="fieldcontain">
					<span id="valorOficialBrutoPlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.valorOficialBrutoPlomo.label" default="Valor Oficial Bruto Plomo" /></span>
					
						<span class="property-value" aria-labelledby="valorOficialBrutoPlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="valorOficialBrutoPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.regaliaPlomo}">
				<li class="fieldcontain">
					<span id="regaliaPlomo-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.regaliaPlomo.label" default="Regalia Plomo" /></span>
					
						<span class="property-value" aria-labelledby="regaliaPlomo-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="regaliaPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.onzasTroyPlata}">
				<li class="fieldcontain">
					<span id="onzasTroyPlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.onzasTroyPlata.label" default="Onzas Troy Plata" /></span>
					
						<span class="property-value" aria-labelledby="onzasTroyPlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="onzasTroyPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.valorOficialBrutoPlata}">
				<li class="fieldcontain">
					<span id="valorOficialBrutoPlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.valorOficialBrutoPlata.label" default="Valor Oficial Bruto Plata" /></span>
					
						<span class="property-value" aria-labelledby="valorOficialBrutoPlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="valorOficialBrutoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.regaliaPlata}">
				<li class="fieldcontain">
					<span id="regaliaPlata-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.regaliaPlata.label" default="Regalia Plata" /></span>
					
						<span class="property-value" aria-labelledby="regaliaPlata-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="regaliaPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.retenciones}">
				<li class="fieldcontain">
					<span id="retenciones-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.retenciones.label" default="Retenciones" /></span>
					
						<span class="property-value" aria-labelledby="retenciones-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="retenciones"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.analisisDeLaboratorio}">
				<li class="fieldcontain">
					<span id="analisisDeLaboratorio-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.analisisDeLaboratorio.label" default="Analisis De Laboratorio" /></span>
					
						<span class="property-value" aria-labelledby="analisisDeLaboratorio-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="analisisDeLaboratorio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.inspeccionDeProducto}">
				<li class="fieldcontain">
					<span id="inspeccionDeProducto-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.inspeccionDeProducto.label" default="Inspeccion De Producto" /></span>
					
						<span class="property-value" aria-labelledby="inspeccionDeProducto-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="inspeccionDeProducto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.costoDeMolienda}">
				<li class="fieldcontain">
					<span id="costoDeMolienda-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.costoDeMolienda.label" default="Costo De Molienda" /></span>
					
						<span class="property-value" aria-labelledby="costoDeMolienda-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="costoDeMolienda"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.anticipoContraContrato}">
				<li class="fieldcontain">
					<span id="anticipoContraContrato-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.anticipoContraContrato.label" default="Anticipo Contra Contrato" /></span>
					
						<span class="property-value" aria-labelledby="anticipoContraContrato-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="anticipoContraContrato"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.transporteAPuerto}">
				<li class="fieldcontain">
					<span id="transporteAPuerto-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.transporteAPuerto.label" default="Transporte AP uerto" /></span>
					
						<span class="property-value" aria-labelledby="transporteAPuerto-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="transporteAPuerto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.totalTransporteAPuerto}">
				<li class="fieldcontain">
					<span id="totalTransporteAPuerto-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.totalTransporteAPuerto.label" default="Total Transporte AP uerto" /></span>
					
						<span class="property-value" aria-labelledby="totalTransporteAPuerto-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="totalTransporteAPuerto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.rollBack}">
				<li class="fieldcontain">
					<span id="rollBack-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.rollBack.label" default="Roll Back" /></span>
					
						<span class="property-value" aria-labelledby="rollBack-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="rollBack"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.totalRollBack}">
				<li class="fieldcontain">
					<span id="totalRollBack-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.totalRollBack.label" default="Total Roll Back" /></span>
					
						<span class="property-value" aria-labelledby="totalRollBack-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="totalRollBack"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.totalRetenciones}">
				<li class="fieldcontain">
					<span id="totalRetenciones-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.totalRetenciones.label" default="Total Retenciones" /></span>
					
						<span class="property-value" aria-labelledby="totalRetenciones-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="totalRetenciones"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionProvisionalDePlomoPlataInstance?.balanceProvisionalPagable}">
				<li class="fieldcontain">
					<span id="balanceProvisionalPagable-label" class="property-label"><g:message code="liquidacionProvisionalDePlomoPlata.balanceProvisionalPagable.label" default="Balance Provisional Pagable" /></span>
					
						<span class="property-value" aria-labelledby="balanceProvisionalPagable-label"><g:fieldValue bean="${liquidacionProvisionalDePlomoPlataInstance}" field="balanceProvisionalPagable"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${liquidacionProvisionalDePlomoPlataInstance?.id}" />
					<g:link class="edit" action="edit" id="${liquidacionProvisionalDePlomoPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
