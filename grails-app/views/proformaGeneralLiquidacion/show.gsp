
<%@ page import="org.socymet.proforma.ProformaGeneralLiquidacion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'proformaGeneralLiquidacion.label', default: 'ProformaGeneralLiquidacion')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
		<g:javascript src="jquery-1.10.1.min.js" />
		<g:javascript src="i18n/grid.locale-es.js" />
		<g:javascript src="jquery.jqGrid.min.js" />
		<g:javascript src="jquery-ui-1.10.3.custom.min.js" />
		<g:javascript src="notify.min.js" />
		<script>
            $(document).ready(function() {
                $(":input").prop('readonly',true);
            });
		</script>
	</head>
	<body>
		<a href="#show-proformaGeneralLiquidacion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-proformaGeneralLiquidacion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list proformaGeneralLiquidacion">
			
				<g:if test="${proformaGeneralLiquidacionInstance?.numeroProformaLiquidacion}">
				<li class="fieldcontain">
					<span id="numeroProformaLiquidacion-label" class="property-label"><g:message code="proformaGeneralLiquidacion.numeroProformaLiquidacion.label" default="Numero Proforma Liquidacion" /></span>
					
						<span class="property-value" aria-labelledby="numeroProformaLiquidacion-label"><g:fieldValue bean="${proformaGeneralLiquidacionInstance}" field="numeroProformaLiquidacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${proformaGeneralLiquidacionInstance?.fechaProformaLiquidacion}">
				<li class="fieldcontain">
					<span id="fechaProformaLiquidacion-label" class="property-label"><g:message code="proformaGeneralLiquidacion.fechaProformaLiquidacion.label" default="Fecha Proforma Liquidacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaProformaLiquidacion-label"><g:formatDate date="${proformaGeneralLiquidacionInstance?.fechaProformaLiquidacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${proformaGeneralLiquidacionInstance?.nombreProforma}">
				<li class="fieldcontain">
					<span id="nombreProforma-label" class="property-label"><g:message code="proformaGeneralLiquidacion.nombreProforma.label" default="Nombre Proforma" /></span>
					
						<span class="property-value" aria-labelledby="nombreProforma-label"><g:fieldValue bean="${proformaGeneralLiquidacionInstance}" field="nombreProforma"/></span>
					
				</li>
				</g:if>

				<h1 style="font-weight: bold">Pesos</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'toneladasMetricasHumedas', 'error')} required">
					<label for="toneladasMetricasHumedas">
						<g:message code="proformaGeneralLiquidacion.toneladasMetricasHumedas.label" default="Toneladas Metricas Humedas" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="toneladasMetricasHumedas" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'toneladasMetricasHumedas')}" required="" inputmode="decimal"/>

					<label for="humedadPromedio">
						<g:message code="proformaGeneralLiquidacion.humedadPromedio.label" default="Humedad Promedio" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="humedadPromedio" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'humedadPromedio')}" required="" inputmode="decimal"/>
				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'humedadPromedio', 'error')} required">--}%
				%{--<label for="humedadPromedio">--}%
				%{--<g:message code="proformaGeneralLiquidacion.humedadPromedio.label" default="Humedad Promedio" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="humedadPromedio" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'humedadPromedio')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'toneladasMetricasSecas', 'error')} required">
					<label for="toneladasMetricasSecas">
						<g:message code="proformaGeneralLiquidacion.toneladasMetricasSecas.label" default="Toneladas Metricas Secas" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="toneladasMetricasSecas" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'toneladasMetricasSecas')}" required="" class="amarillo" readonly="readonly"/>

					<label for="merma">
						<g:message code="proformaGeneralLiquidacion.merma.label" default="Merma" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="merma" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'merma')}" required="" inputmode="decimal"/>

				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'merma', 'error')} required">--}%
				%{--<label for="merma">--}%
				%{--<g:message code="proformaGeneralLiquidacion.merma.label" default="Merma" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="merma" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'merma')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'toneladasMetricasSecasFinales', 'error')} required">
					<label for="toneladasMetricasSecasFinales">
						<g:message code="proformaGeneralLiquidacion.toneladasMetricasSecasFinales.label" default="Toneladas Metricas Secas Finales" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="toneladasMetricasSecasFinales" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'toneladasMetricasSecasFinales')}" required="" class="amarillo" readonly="readonly"/>

				</div>

				<h1 style="font-weight: bold">Leyes De Minerales</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'leyPlomo', 'error')} required">
					<label for="leyPlomo">
						<g:message code="proformaGeneralLiquidacion.leyPlomo.label" default="Ley Plomo" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="leyPlomo" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'leyPlomo')}" required="" inputmode="decimal"/>

					<label for="leyPlata">
						<g:message code="proformaGeneralLiquidacion.leyPlata.label" default="Ley Plata" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="leyPlata" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'leyPlata')}" required="" inputmode="decimal"/>
				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'leyPlata', 'error')} required">--}%
				%{--<label for="leyPlata">--}%
				%{--<g:message code="proformaGeneralLiquidacion.leyPlata.label" default="Ley Plata" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="leyPlata" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'leyPlata')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<h1 style="font-weight: bold">Cotizaciones De Minerales</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'cotizacionPlomo', 'error')} required">
					<label for="cotizacionPlomo">
						<g:message code="proformaGeneralLiquidacion.cotizacionPlomo.label" default="Cotizacion Plomo" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="cotizacionPlomo" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'cotizacionPlomo')}" required="" inputmode="decimal"/>

					<label for="cotizacionPlata">
						<g:message code="proformaGeneralLiquidacion.cotizacionPlata.label" default="Cotizacion Plata" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="cotizacionPlata" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'cotizacionPlata')}" required="" inputmode="decimal"/>
				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'cotizacionPlata', 'error')} required">--}%
				%{--<label for="cotizacionPlata">--}%
				%{--<g:message code="proformaGeneralLiquidacion.cotizacionPlata.label" default="Cotizacion Plata" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="cotizacionPlata" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'cotizacionPlata')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<h1 style="font-weight: bold">Minerales Pagables</h1>

				<table>
					<thead>
					<tr>
						<th></th>
						<th>LEYES [%, oz]</th>
						<th>DEDUCCIONES UNITARIAS [%, oz]</th>
						<th>PORCENTAJES PAGABLES [%]</th>
						<th>COTIZACIONES</th>
						<th>VALOR PAGABLE [$]</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td>Plomo</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'leyPlomoMineralesPagables', 'error')} required">
							%{--<label for="leyPlomoMineralesPagables">--}%
							%{--<g:message code="proformaGeneralLiquidacion.leyPlomoMineralesPagables.label" default="Ley Plomo Minerales Pagables" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="leyPlomoMineralesPagables" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'leyPlomoMineralesPagables')}" required="" class="amarillo" readonly="readonly"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'deduccionUnitariaPlomo', 'error')} required">
							%{--<label for="deduccionUnitariaPlomo">--}%
							%{--<g:message code="proformaGeneralLiquidacion.deduccionUnitariaPlomo.label" default="Deduccion Unitaria Plomo" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="deduccionUnitariaPlomo" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'deduccionUnitariaPlomo')}" required="" inputmode="decimal"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajePagableLMEPlomo', 'error')} required">
							%{--<label for="porcentajePagableLMEPlomo">--}%
							%{--<g:message code="proformaGeneralLiquidacion.porcentajePagableLMEPlomo.label" default="Porcentaje Pagable LMEP lomo" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="porcentajePagableLMEPlomo" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajePagableLMEPlomo')}" required="" inputmode="decimal"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'cotizacionPlomoMineralesPagables', 'error')} required">
							%{--<label for="cotizacionPlomoMineralesPagables">--}%
							%{--<g:message code="proformaGeneralLiquidacion.cotizacionPlomoMineralesPagables.label" default="Cotizacion Plomo Minerales Pagables" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="cotizacionPlomoMineralesPagables" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'cotizacionPlomoMineralesPagables')}" required="" class="amarillo" readonly="readonly"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'valorPagablePlomo', 'error')} required">
							%{--<label for="valorPagablePlomo">--}%
							%{--<g:message code="proformaGeneralLiquidacion.valorPagablePlomo.label" default="Valor Pagable Plomo" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="valorPagablePlomo" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'valorPagablePlomo')}" required="" class="amarillo" readonly="readonly"/>

						</td>
					</tr>

					<tr>
						<td>Plata</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'leyPlataMineralesPagables', 'error')} required">
							%{--<label for="leyPlataMineralesPagables">--}%
							%{--<g:message code="proformaGeneralLiquidacion.leyPlataMineralesPagables.label" default="Ley Plata Minerales Pagables" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="leyPlataMineralesPagables" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'leyPlataMineralesPagables')}" required="" class="amarillo" readonly="readonly"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'deduccionUnitariaPlata', 'error')} required">
							%{--<label for="deduccionUnitariaPlata">--}%
							%{--<g:message code="proformaGeneralLiquidacion.deduccionUnitariaPlata.label" default="Deduccion Unitaria Plata" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="deduccionUnitariaPlata" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'deduccionUnitariaPlata')}" required="" inputmode="decimal"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajePagableLMEPlata', 'error')} required">
							%{--<label for="porcentajePagableLMEPlata">--}%
							%{--<g:message code="proformaGeneralLiquidacion.porcentajePagableLMEPlata.label" default="Porcentaje Pagable LMEP lomo" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="porcentajePagableLMEPlata" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajePagableLMEPlata')}" required="" inputmode="decimal"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'cotizacionPlataMineralesPagables', 'error')} required">
							%{--<label for="cotizacionPlataMineralesPagables">--}%
							%{--<g:message code="proformaGeneralLiquidacion.cotizacionPlataMineralesPagables.label" default="Cotizacion Plata Minerales Pagables" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="cotizacionPlataMineralesPagables" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'cotizacionPlataMineralesPagables')}" required="" class="amarillo" readonly="readonly"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'valorPagablePlata', 'error')} required">
							%{--<label for="valorPagablePlata">--}%
							%{--<g:message code="proformaGeneralLiquidacion.valorPagablePlata.label" default="Valor Pagable Plata" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="valorPagablePlata" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'valorPagablePlata')}" required="" class="amarillo" readonly="readonly"/>

						</td>
					</tr>

					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'valorPagableTotal', 'error')} required">
							<label for="valorPagableTotal" style="width: 80%">
								<g:message code="proformaGeneralLiquidacion.valorPagableTotal.label" default="Valor Pagable Total" />
								<span class="required-indicator">*</span>
							</label>
						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'valorPagableTotal', 'error')} required">
							<g:field name="valorPagableTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'valorPagableTotal')}" required="" class="amarillo" readonly="readonly"/>
						</td>
					</tr>
					</tbody>
				</table>

				<h1 style="font-weight: bold">DEDUCCIONES</h1>

				<h1 style="font-weight: bold">Maquila</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'maquila', 'error')} required">
					<label for="maquila">
						<g:message code="proformaGeneralLiquidacion.maquila.label" default="Maquila" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="maquila" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'maquila')}" required="" inputmode="decimal"/>

					<label for="maquilaFinal">
						<g:message code="proformaGeneralLiquidacion.maquilaFinal.label" default="Maquila Final" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="maquilaFinal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'maquilaFinal')}" required="" class="amarillo" readonly="readonly"/>

				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'maquilaFinal', 'error')} required">--}%
				%{--<label for="maquilaFinal">--}%
				%{--<g:message code="proformaGeneralLiquidacion.maquilaFinal.label" default="Maquila Final" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="maquilaFinal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'maquilaFinal')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<h1 style="font-weight: bold">Gastos De Realizaci&oacute;n</h1>

				<table>
					<thead>
					<tr>
						<th></th>
						<th></th>
						<th>COTIZACION ACTUAL</th>
						<th></th>
						<th>BASE</th>
						<th></th>
						<th>ESCALADOR</th>
						<th></th>
						<th>GASTO REALIZACION TOTAL</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td></td>
						<td>(</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'cotizacionPlomoActual', 'error')} required">
							%{--<label for="cotizacionPlomoActual">--}%
							%{--<g:message code="proformaGeneralLiquidacion.cotizacionPlomoActual.label" default="Cotizacion Plomo Actual" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="cotizacionPlomoActual" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'cotizacionPlomoActual')}" required="" class="amarillo" readonly="readonly"/>

						</td>
						<td>-</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'base', 'error')} required">
							%{--<label for="base">--}%
							%{--<g:message code="proformaGeneralLiquidacion.base.label" default="Base" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="base" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'base')}" required="" inputmode="decimal"/>

						</td>
						<td>) x</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'escaladorPlomoPlata', 'error')} required">
							%{--<label for="escaladorPlomoPlata">--}%
							%{--<g:message code="proformaGeneralLiquidacion.escaladorPlomoPlata.label" default="Escalador Plomo Plata" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="escaladorPlomoPlata" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'escaladorPlomoPlata')}" required="" inputmode="decimal"/>

						</td>
						<td>=</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'gastoRealizacionTotal', 'error')} required">
							%{--<label for="gastoRealizacionTotal">--}%
							%{--<g:message code="proformaGeneralLiquidacion.gastoRealizacionTotal.label" default="Gasto Realizacion Total" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="gastoRealizacionTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'gastoRealizacionTotal')}" required="" class="amarillo" readonly="readonly"/>

						</td>
					</tr>
					</tbody>
				</table>

				<h1 style="font-weight: bold">Refinamiento</h1>

				<table>
					<thead>
					<tr>
						<th>LEY PLATA O.T.</th>
						<th></th>
						<th>COSTO REFINACION</th>
						<th></th>
						<th>COSTO TOTAL REFINACION</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'leyPlataOnzaTroy', 'error')} required">
							%{--<label for="leyPlataOnzaTroy">--}%
							%{--<g:message code="proformaGeneralLiquidacion.leyPlataOnzaTroy.label" default="Ley Plata Onza Troy" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="leyPlataOnzaTroy" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'leyPlataOnzaTroy')}" required="" class="amarillo" readonly="readonly"/>

						</td>

						<td>X</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoRefinacion', 'error')} required">
							%{--<label for="costoRefinacion">--}%
							%{--<g:message code="proformaGeneralLiquidacion.costoRefinacion.label" default="Costo Refinacion" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="costoRefinacion" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoRefinacion')}" required="" inputmode="decimal"/>

						</td>

						<td>=</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoRefinacionTotal', 'error')} required">
							%{--<label for="costoRefinacionTotal">--}%
							%{--<g:message code="proformaGeneralLiquidacion.costoRefinacionTotal.label" default="Costo Refinacion Total" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="costoRefinacionTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoRefinacionTotal')}" required="" class="amarillo" readonly="readonly"/>

						</td>
					</tr>
					</tbody>
				</table>

				<h1 style="font-weight: bold">Penalizaciones</h1>

				<table>
					<thead>
					<tr>
						<th></th>
						<th>% CONTENIDO</th>
						<th>% LIBRE</th>
						<th>COSTO UNITARIO</th>
						<th>PORCENTAJE UNITARIO</th>
						<th>PENALIZACION</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td>Arsenico</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeArsenico', 'error')} required">
							<g:field name="porcentajeArsenico" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeArsenico')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'arsenicoLibre', 'error')} required">
							<g:field name="arsenicoLibre" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'arsenicoLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioArsenico', 'error')} required">
							<g:field name="costoUnitarioArsenico" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioArsenico')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioArsenico', 'error')} required">
							<g:field name="porcentajeUnitarioArsenico" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioArsenico')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionArsenico', 'error')} required">
							<g:field name="penalizacionArsenico" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionArsenico')}" required="" class="amarillo" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>Antimonio</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeAntimonio', 'error')} required">
							<g:field name="porcentajeAntimonio" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeAntimonio')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'antimonioLibre', 'error')} required">
							<g:field name="antimonioLibre" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'antimonioLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioAntimonio', 'error')} required">
							<g:field name="costoUnitarioAntimonio" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioAntimonio')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioAntimonio', 'error')} required">
							<g:field name="porcentajeUnitarioAntimonio" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioAntimonio')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionAntimonio', 'error')} required">
							<g:field name="penalizacionAntimonio" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionAntimonio')}" required="" class="amarillo" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>Bismuto</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeBismuto', 'error')} required">
							<g:field name="porcentajeBismuto" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeBismuto')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'bismutoLibre', 'error')} required">
							<g:field name="bismutoLibre" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'bismutoLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioBismuto', 'error')} required">
							<g:field name="costoUnitarioBismuto" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioBismuto')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioBismuto', 'error')} required">
							<g:field name="porcentajeUnitarioBismuto" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioBismuto')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionBismuto', 'error')} required">
							<g:field name="penalizacionBismuto" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionBismuto')}" required="" class="amarillo" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>Estano</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeEstano', 'error')} required">
							<g:field name="porcentajeEstano" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeEstano')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'estanoLibre', 'error')} required">
							<g:field name="estanoLibre" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'estanoLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioEstano', 'error')} required">
							<g:field name="costoUnitarioEstano" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioEstano')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioEstano', 'error')} required">
							<g:field name="porcentajeUnitarioEstano" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioEstano')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionEstano', 'error')} required">
							<g:field name="penalizacionEstano" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionEstano')}" required="" class="amarillo" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>Hierro</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeHierro', 'error')} required">
							<g:field name="porcentajeHierro" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeHierro')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'hierroLibre', 'error')} required">
							<g:field name="hierroLibre" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'hierroLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioHierro', 'error')} required">
							<g:field name="costoUnitarioHierro" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioHierro')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioHierro', 'error')} required">
							<g:field name="porcentajeUnitarioHierro" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioHierro')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionHierro', 'error')} required">
							<g:field name="penalizacionHierro" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionHierro')}" required="" class="amarillo" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>Silice</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeSilice', 'error')} required">
							<g:field name="porcentajeSilice" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeSilice')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'siliceLibre', 'error')} required">
							<g:field name="siliceLibre" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'siliceLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioSilice', 'error')} required">
							<g:field name="costoUnitarioSilice" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioSilice')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioSilice', 'error')} required">
							<g:field name="porcentajeUnitarioSilice" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioSilice')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionSilice', 'error')} required">
							<g:field name="penalizacionSilice" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionSilice')}" required="" class="amarillo" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td>Zinc</td>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeZinc', 'error')} required">
							<g:field name="porcentajeZinc" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeZinc')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'zincLibre', 'error')} required">
							<g:field name="zincLibre" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'zincLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioZinc', 'error')} required">
							<g:field name="costoUnitarioZinc" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoUnitarioZinc')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioZinc', 'error')} required">
							<g:field name="porcentajeUnitarioZinc" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'porcentajeUnitarioZinc')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionZinc', 'error')} required">
							<g:field name="penalizacionZinc" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'penalizacionZinc')}" required="" class="amarillo" readonly="readonly"/>
						</td>
					</tr>
					</tbody>
				</table>

				<h1 style="font-weight: bold">Precio Unitario y Valor Neto</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'precioUnitario', 'error')} required">
					<label for="precioUnitario">
						<g:message code="proformaGeneralLiquidacion.precioUnitario.label" default="Precio Unitario" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="precioUnitario" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'precioUnitario')}" required="" class="amarillo" readonly="readonly"/>

					<label for="valorNetoTotal">
						<g:message code="proformaGeneralLiquidacion.valorNetoTotal.label" default="Valor Neto Total" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="valorNetoTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'valorNetoTotal')}" required="" class="amarillo" readonly="readonly"/>

				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'valorNetoTotal', 'error')} required">--}%
				%{--<label for="valorNetoTotal">--}%
				%{--<g:message code="proformaGeneralLiquidacion.valorNetoTotal.label" default="Valor Neto Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="valorNetoTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'valorNetoTotal')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<h1 style="font-weight: bold">Costos Portuarios y Flete</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoFleteTonelada', 'error')} required">
					<label for="costoFleteTonelada">
						<g:message code="proformaGeneralLiquidacion.costoFleteTonelada.label" default="Costo Flete Tonelada" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="costoFleteTonelada" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoFleteTonelada')}" required="" inputmode="decimal"/>

					<label for="costoFleteToneladaTotal">
						<g:message code="proformaGeneralLiquidacion.costoFleteToneladaTotal.label" default="Costo Flete Tonelada Total" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="costoFleteToneladaTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoFleteToneladaTotal')}" required="" class="amarillo" readonly="readonly"/>

				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoFleteToneladaTotal', 'error')} required">--}%
				%{--<label for="costoFleteToneladaTotal">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoFleteToneladaTotal.label" default="Costo Flete Tonelada Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoFleteToneladaTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoFleteToneladaTotal')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoPortuarioTonelada', 'error')} required">
					<label for="costoPortuarioTonelada">
						<g:message code="proformaGeneralLiquidacion.costoPortuarioTonelada.label" default="Costo Portuario Tonelada" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="costoPortuarioTonelada" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoPortuarioTonelada')}" required="" inputmode="decimal"/>

					<label for="costoPortuarioToneladaTotal">
						<g:message code="proformaGeneralLiquidacion.costoPortuarioToneladaTotal.label" default="Costo Portuario Tonelada Total" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="costoPortuarioToneladaTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoPortuarioToneladaTotal')}" required="" class="amarillo" readonly="readonly"/>

				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoPortuarioToneladaTotal', 'error')} required">--}%
				%{--<label for="costoPortuarioToneladaTotal">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoPortuarioToneladaTotal.label" default="Costo Portuario Tonelada Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoPortuarioToneladaTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoPortuarioToneladaTotal')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<h1 style="font-weight: bold">Gastos de Operacion</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoOperacionTonelada', 'error')} required">
					<label for="costoOperacionTonelada">
						<g:message code="proformaGeneralLiquidacion.costoOperacionTonelada.label" default="Costo Operacion Tonelada" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="costoOperacionTonelada" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoOperacionTonelada')}" required="" inputmode="decimal"/>

					<label for="costoOperacionToneladaTotal">
						<g:message code="proformaGeneralLiquidacion.costoOperacionToneladaTotal.label" default="Costo Operacion Tonelada Total" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="costoOperacionToneladaTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoOperacionToneladaTotal')}" required="" class="amarillo" readonly="readonly"/>
				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoOperacionToneladaTotal', 'error')} required">--}%
				%{--<label for="costoOperacionToneladaTotal">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoOperacionToneladaTotal.label" default="Costo Operacion Tonelada Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoOperacionToneladaTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoOperacionToneladaTotal')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'regaliaDiferenciaPlomo', 'error')} required">
					<label for="regaliaDiferenciaPlomo">
						<g:message code="proformaGeneralLiquidacion.regaliaDiferenciaPlomo.label" default="Regalia Diferencia Plomo" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="regaliaDiferenciaPlomo" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'regaliaDiferenciaPlomo')}" required="" inputmode="decimal"/>

					<label for="regaliaDiferenciaPlomoTotal">
						<g:message code="proformaGeneralLiquidacion.regaliaDiferenciaPlomoTotal.label" default="Regalia Diferencia Plomo Total" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="regaliaDiferenciaPlomoTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'regaliaDiferenciaPlomoTotal')}" required="" class="amarillo" readonly="readonly"/>
				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'regaliaDiferenciaPlomoTotal', 'error')} required">--}%
				%{--<label for="regaliaDiferenciaPlomoTotal">--}%
				%{--<g:message code="proformaGeneralLiquidacion.regaliaDiferenciaPlomoTotal.label" default="Regalia Diferencia Plomo Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="regaliaDiferenciaPlomoTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'regaliaDiferenciaPlomoTotal')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'regaliaDiferenciaPlata', 'error')} required">
					<label for="regaliaDiferenciaPlata">
						<g:message code="proformaGeneralLiquidacion.regaliaDiferenciaPlata.label" default="Regalia Diferencia Plata" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="regaliaDiferenciaPlata" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'regaliaDiferenciaPlata')}" required="" inputmode="decimal"/>

					<label for="regaliaDiferenciaPlataTotal">
						<g:message code="proformaGeneralLiquidacion.regaliaDiferenciaPlataTotal.label" default="Regalia Diferencia Plata Total" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="regaliaDiferenciaPlataTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'regaliaDiferenciaPlataTotal')}" required="" class="amarillo" readonly="readonly"/>
				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'regaliaDiferenciaPlataTotal', 'error')} required">--}%
				%{--<label for="regaliaDiferenciaPlataTotal">--}%
				%{--<g:message code="proformaGeneralLiquidacion.regaliaDiferenciaPlataTotal.label" default="Regalia Diferencia Plata Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="regaliaDiferenciaPlataTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'regaliaDiferenciaPlataTotal')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'pagosProvisionales', 'error')} required">
					<label for="pagosProvisionales">
						<g:message code="proformaGeneralLiquidacion.pagosProvisionales.label" default="Pagos Provisionales" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="pagosProvisionales" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'pagosProvisionales')}" required="" inputmode="decimal"/>

					<label for="pagosProvisionalesTotal">
						<g:message code="proformaGeneralLiquidacion.pagosProvisionalesTotal.label" default="Pagos Provisionales Total" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="pagosProvisionalesTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'pagosProvisionalesTotal')}" required="" class="amarillo" readonly="readonly"/>
				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'pagosProvisionalesTotal', 'error')} required">--}%
				%{--<label for="pagosProvisionalesTotal">--}%
				%{--<g:message code="proformaGeneralLiquidacion.pagosProvisionalesTotal.label" default="Pagos Provisionales Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="pagosProvisionalesTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'pagosProvisionalesTotal')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<h1 style="font-weight: bold">Otros Gastos</h1>

				<table>
					<tbody>
					<tr>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'descuento1', 'error')} required">
							<label for="descuento1" style="width: 40%">
								<g:message code="proformaGeneralLiquidacion.descuento1.label" default="Descuento1" />
								<span class="required-indicator">*</span>
							</label>
							<g:textField name="descuento1" inputmode="decimal" required="" value="${proformaGeneralLiquidacionInstance?.descuento1}"/>

						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento1', 'error')} required">
							<label for="costoDescuento1" style="width: 50%">
								<g:message code="proformaGeneralLiquidacion.costoDescuento1.label" default="Costo Descuento1" />
								<span class="required-indicator">*</span>
							</label>
							<g:field name="costoDescuento1" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento1')}" required="" inputmode="decimal"/>

						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento1Total', 'error')} required">
							<label for="costoDescuento1Total" style="width: 50%">
								<g:message code="proformaGeneralLiquidacion.costoDescuento1Total.label" default="Costo Descuento1 Total" />
								<span class="required-indicator">*</span>
							</label>
							<g:field name="costoDescuento1Total" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento1Total')}" required="" class="amarillo" readonly="readonly"/>

						</td>
					</tr>

					<tr>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'descuento2', 'error')} required">
							<label for="descuento2" style="width: 40%">
								<g:message code="proformaGeneralLiquidacion.descuento2.label" default="Descuento2" />
								<span class="required-indicator">*</span>
							</label>
							<g:textField name="descuento2" inputmode="decimal" required="" value="${proformaGeneralLiquidacionInstance?.descuento2}"/>

						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento2', 'error')} required">
							<label for="costoDescuento2" style="width: 50%">
								<g:message code="proformaGeneralLiquidacion.costoDescuento2.label" default="Costo Descuento2" />
								<span class="required-indicator">*</span>
							</label>
							<g:field name="costoDescuento2" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento2')}" required="" inputmode="decimal"/>

						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento2Total', 'error')} required">
							<label for="costoDescuento2Total" style="width: 50%">
								<g:message code="proformaGeneralLiquidacion.costoDescuento2Total.label" default="Costo Descuento2 Total" />
								<span class="required-indicator">*</span>
							</label>
							<g:field name="costoDescuento2Total" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento2Total')}" required="" class="amarillo" readonly="readonly"/>

						</td>
					</tr>

					<tr>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'descuento3', 'error')} required">
							<label for="descuento3" style="width: 40%">
								<g:message code="proformaGeneralLiquidacion.descuento3.label" default="Descuento3" />
								<span class="required-indicator">*</span>
							</label>
							<g:textField name="descuento3" inputmode="decimal" required="" value="${proformaGeneralLiquidacionInstance?.descuento3}"/>

						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento3', 'error')} required">
							<label for="costoDescuento3" style="width: 50%">
								<g:message code="proformaGeneralLiquidacion.costoDescuento3.label" default="Costo Descuento3" />
								<span class="required-indicator">*</span>
							</label>
							<g:field name="costoDescuento3" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento3')}" required="" inputmode="decimal"/>

						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento3Total', 'error')} required">
							<label for="costoDescuento3Total" style="width: 50%">
								<g:message code="proformaGeneralLiquidacion.costoDescuento3Total.label" default="Costo Descuento3 Total" />
								<span class="required-indicator">*</span>
							</label>
							<g:field name="costoDescuento3Total" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento3Total')}" required="" class="amarillo" readonly="readonly"/>

						</td>
					</tr>

					<tr>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'descuento4', 'error')} required">
							<label for="descuento4" style="width: 40%">
								<g:message code="proformaGeneralLiquidacion.descuento4.label" default="Descuento4" />
								<span class="required-indicator">*</span>
							</label>
							<g:textField name="descuento4" inputmode="decimal" required="" value="${proformaGeneralLiquidacionInstance?.descuento4}"/>

						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento4', 'error')} required">
							<label for="costoDescuento4" style="width: 50%">
								<g:message code="proformaGeneralLiquidacion.costoDescuento4.label" default="Costo Descuento4" />
								<span class="required-indicator">*</span>
							</label>
							<g:field name="costoDescuento4" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento4')}" required="" inputmode="decimal"/>

						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento4Total', 'error')} required">
							<label for="costoDescuento4Total" style="width: 50%">
								<g:message code="proformaGeneralLiquidacion.costoDescuento4Total.label" default="Costo Descuento4 Total" />
								<span class="required-indicator">*</span>
							</label>
							<g:field name="costoDescuento4Total" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento4Total')}" required="" class="amarillo" readonly="readonly"/>

						</td>
					</tr>

					<tr>
						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'descuento5', 'error')} required">
							<label for="descuento5" style="width: 40%">
								<g:message code="proformaGeneralLiquidacion.descuento5.label" default="Descuento5" />
								<span class="required-indicator">*</span>
							</label>
							<g:textField name="descuento5" inputmode="decimal" required="" value="${proformaGeneralLiquidacionInstance?.descuento5}"/>

						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento5', 'error')} required">
							<label for="costoDescuento5" style="width: 50%">
								<g:message code="proformaGeneralLiquidacion.costoDescuento5.label" default="Costo Descuento5" />
								<span class="required-indicator">*</span>
							</label>
							<g:field name="costoDescuento5" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento5')}" required="" inputmode="decimal"/>

						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento5Total', 'error')} required">
							<label for="costoDescuento5Total" style="width: 50%">
								<g:message code="proformaGeneralLiquidacion.costoDescuento5Total.label" default="Costo Descuento5 Total" />
								<span class="required-indicator">*</span>
							</label>
							<g:field name="costoDescuento5Total" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento5Total')}" required="" class="amarillo" readonly="readonly"/>

						</td>
					</tr>
					</tbody>
				</table>
				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'descuento1', 'error')} required">--}%
				%{--<label for="descuento1">--}%
				%{--<g:message code="proformaGeneralLiquidacion.descuento1.label" default="Descuento1" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:textField name="descuento1" inputmode="decimal" required="" value="${proformaGeneralLiquidacionInstance?.descuento1}"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento1', 'error')} required">--}%
				%{--<label for="costoDescuento1">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoDescuento1.label" default="Costo Descuento1" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoDescuento1" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento1')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento1Total', 'error')} required">--}%
				%{--<label for="costoDescuento1Total">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoDescuento1Total.label" default="Costo Descuento1 Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoDescuento1Total" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento1Total')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'descuento2', 'error')} required">--}%
				%{--<label for="descuento2">--}%
				%{--<g:message code="proformaGeneralLiquidacion.descuento2.label" default="Descuento2" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:textField name="descuento2" inputmode="decimal" required="" value="${proformaGeneralLiquidacionInstance?.descuento2}"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento2', 'error')} required">--}%
				%{--<label for="costoDescuento2">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoDescuento2.label" default="Costo Descuento2" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoDescuento2" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento2')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento2Total', 'error')} required">--}%
				%{--<label for="costoDescuento2Total">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoDescuento2Total.label" default="Costo Descuento2 Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoDescuento2Total" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento2Total')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'descuento3', 'error')} required">--}%
				%{--<label for="descuento3">--}%
				%{--<g:message code="proformaGeneralLiquidacion.descuento3.label" default="Descuento3" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:textField name="descuento3" inputmode="decimal" required="" value="${proformaGeneralLiquidacionInstance?.descuento3}"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento3', 'error')} required">--}%
				%{--<label for="costoDescuento3">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoDescuento3.label" default="Costo Descuento3" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoDescuento3" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento3')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento3Total', 'error')} required">--}%
				%{--<label for="costoDescuento3Total">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoDescuento3Total.label" default="Costo Descuento3 Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoDescuento3Total" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento3Total')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'descuento4', 'error')} required">--}%
				%{--<label for="descuento4">--}%
				%{--<g:message code="proformaGeneralLiquidacion.descuento4.label" default="Descuento4" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:textField name="descuento4" inputmode="decimal" required="" value="${proformaGeneralLiquidacionInstance?.descuento4}"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento4', 'error')} required">--}%
				%{--<label for="costoDescuento4">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoDescuento4.label" default="Costo Descuento4" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoDescuento4" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento4')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento4Total', 'error')} required">--}%
				%{--<label for="costoDescuento4Total">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoDescuento4Total.label" default="Costo Descuento4 Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoDescuento4Total" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento4Total')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'descuento5', 'error')} required">--}%
				%{--<label for="descuento5">--}%
				%{--<g:message code="proformaGeneralLiquidacion.descuento5.label" default="Descuento5" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:textField name="descuento5" inputmode="decimal" required="" value="${proformaGeneralLiquidacionInstance?.descuento5}"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento5', 'error')} required">--}%
				%{--<label for="costoDescuento5">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoDescuento5.label" default="Costo Descuento5" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoDescuento5" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento5')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento5Total', 'error')} required">--}%
				%{--<label for="costoDescuento5Total">--}%
				%{--<g:message code="proformaGeneralLiquidacion.costoDescuento5Total.label" default="Costo Descuento5 Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoDescuento5Total" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'costoDescuento5Total')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'descuentoOperacionesTotal', 'error')} required" style="display:none;">
					<label for="descuentoOperacionesTotal">
						<g:message code="proformaGeneralLiquidacion.descuentoOperacionesTotal.label" default="Descuento Operaciones Total" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="descuentoOperacionesTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'descuentoOperacionesTotal')}" required="" inputmode="decimal"/>

				</div>

				<h1 style="font-weight: bold">Valor Neto Total Final</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'valorNetoTotalFinal', 'error')} required">
					<label for="valorNetoTotalFinal">
						<g:message code="proformaGeneralLiquidacion.valorNetoTotalFinal.label" default="Valor Neto Total Final" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="valorNetoTotalFinal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'valorNetoTotalFinal')}" required="" class="verde" readonly="readonly"/>

				</div>

				<h1 style="font-weight: bold">Costo de Compra del Mineral</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'valorCompraMineral', 'error')} required">
					<label for="valorCompraMineral">
						<g:message code="proformaGeneralLiquidacion.valorCompraMineral.label" default="Valor Compra Mineral" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="valorCompraMineral" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'valorCompraMineral')}" required="" inputmode="decimal"/>

					<label for="valorCompraMineralTotal">
						<g:message code="proformaGeneralLiquidacion.valorCompraMineralTotal.label" default="Valor Compra Mineral Total" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="valorCompraMineralTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'valorCompraMineralTotal')}" required="" class="amarillo" readonly="readonly"/>

				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'valorCompraMineralTotal', 'error')} required">--}%
				%{--<label for="valorCompraMineralTotal">--}%
				%{--<g:message code="proformaGeneralLiquidacion.valorCompraMineralTotal.label" default="Valor Compra Mineral Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="valorCompraMineralTotal" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'valorCompraMineralTotal')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<h1 style="font-weight: bold">Utililidad Estimada</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaGeneralLiquidacionInstance, field: 'utilidadEstimada', 'error')} required">
					<label for="utilidadEstimada">
						<g:message code="proformaGeneralLiquidacion.utilidadEstimada.label" default="Utilidad Estimada" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="utilidadEstimada" value="${fieldValue(bean: proformaGeneralLiquidacionInstance, field: 'utilidadEstimada')}" required="" class="verde" readonly="readonly"/>

				</div>

			</ol>
			%{--<g:form url="[resource:proformaGeneralLiquidacionInstance, action:'delete']" method="DELETE">--}%
				%{--<fieldset class="buttons">--}%
					%{--<g:link class="edit" action="edit" resource="${proformaGeneralLiquidacionInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
					%{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
				%{--</fieldset>--}%
			%{--</g:form>--}%

			<fieldset class="buttons">
				<div style="float: left">
					<g:form url="[resource:proformaGeneralLiquidacionInstance, action:'delete']" method="DELETE">
						<g:link class="edit" action="edit" resource="${proformaGeneralLiquidacionInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</g:form>
				</div>
				<div style="float: right">
					<table>
						<tr>
							<td>
								<g:jasperReport controller="pagoTransporte" action="createReport" jasper="proforma_general_liquidacion" format="PDF" _format="PDF" name="PROFORMA_LIQUIDACION_${proformaGeneralLiquidacionInstance.numeroProformaLiquidacion}">
									<input type="hidden" name="proformaLiquidacionId" value="${proformaGeneralLiquidacionInstance.id}" />
								</g:jasperReport>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
		</div>
	</body>
</html>
