
<%@ page import="org.socymet.proforma.ProformaLiquidacion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'proformaLiquidacion.label', default: 'ProformaLiquidacion')}" />
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
		<a href="#show-proformaLiquidacion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-proformaLiquidacion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list proformaLiquidacion">
			
				<g:if test="${proformaLiquidacionInstance?.numeroProformaLiquidacion}">
				<li class="fieldcontain">
					<span id="numeroProformaLiquidacion-label" class="property-label"><g:message code="proformaLiquidacion.numeroProformaLiquidacion.label" default="Numero Proforma Liquidacion" /></span>
					
						<span class="property-value" aria-labelledby="numeroProformaLiquidacion-label"><g:fieldValue bean="${proformaLiquidacionInstance}" field="numeroProformaLiquidacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${proformaLiquidacionInstance?.fechaProformaLiquidacion}">
				<li class="fieldcontain">
					<span id="fechaProformaLiquidacion-label" class="property-label"><g:message code="proformaLiquidacion.fechaProformaLiquidacion.label" default="Fecha Proforma Liquidacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaProformaLiquidacion-label"><g:formatDate date="${proformaLiquidacionInstance?.fechaProformaLiquidacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${proformaLiquidacionInstance?.nombreProforma}">
				<li class="fieldcontain">
					<span id="nombreProforma-label" class="property-label"><g:message code="proformaLiquidacion.nombreProforma.label" default="Nombre Proforma" /></span>
					
						<span class="property-value" aria-labelledby="nombreProforma-label"><g:fieldValue bean="${proformaLiquidacionInstance}" field="nombreProforma"/></span>
					
				</li>
				</g:if>

				<h1 style="font-weight: bold">Pesos</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'toneladasMetricasHumedas', 'error')} required">
					<label for="toneladasMetricasHumedas">
						<g:message code="proformaLiquidacion.toneladasMetricasHumedas.label" default="Toneladas Metricas Humedas" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="toneladasMetricasHumedas" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'toneladasMetricasHumedas')}" required="" inputmode="decimal"/>

					<label for="humedadPromedio">
						<g:message code="proformaLiquidacion.humedadPromedio.label" default="Humedad Promedio" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="humedadPromedio" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'humedadPromedio')}" required="" inputmode="decimal"/>
				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'humedadPromedio', 'error')} required">--}%
				%{--<label for="humedadPromedio">--}%
				%{--<g:message code="proformaLiquidacion.humedadPromedio.label" default="Humedad Promedio" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="humedadPromedio" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'humedadPromedio')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'toneladasMetricasSecas', 'error')} required">
					<label for="toneladasMetricasSecas">
						<g:message code="proformaLiquidacion.toneladasMetricasSecas.label" default="Toneladas Metricas Secas" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="toneladasMetricasSecas" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'toneladasMetricasSecas')}" required="" inputmode="decimal"/>

					<label for="merma">
						<g:message code="proformaLiquidacion.merma.label" default="Merma" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="merma" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'merma')}" required="" inputmode="decimal"/>

				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'merma', 'error')} required">--}%
				%{--<label for="merma">--}%
				%{--<g:message code="proformaLiquidacion.merma.label" default="Merma" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="merma" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'merma')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'toneladasMetricasSecasFinales', 'error')} required">
					<label for="toneladasMetricasSecasFinales">
						<g:message code="proformaLiquidacion.toneladasMetricasSecasFinales.label" default="Toneladas Metricas Secas Finales" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="toneladasMetricasSecasFinales" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'toneladasMetricasSecasFinales')}" required="" inputmode="decimal"/>

				</div>

				<h1 style="font-weight: bold">Leyes De Minerales</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'leyPlomo', 'error')} required">
					<label for="leyPlomo">
						<g:message code="proformaLiquidacion.leyPlomo.label" default="Ley Plomo" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="leyPlomo" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'leyPlomo')}" required="" inputmode="decimal"/>

					<label for="leyPlata">
						<g:message code="proformaLiquidacion.leyPlata.label" default="Ley Plata" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="leyPlata" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'leyPlata')}" required="" inputmode="decimal"/>
				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'leyPlata', 'error')} required">--}%
				%{--<label for="leyPlata">--}%
				%{--<g:message code="proformaLiquidacion.leyPlata.label" default="Ley Plata" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="leyPlata" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'leyPlata')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<h1 style="font-weight: bold">Cotizaciones De Minerales</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'cotizacionPlomo', 'error')} required">
					<label for="cotizacionPlomo">
						<g:message code="proformaLiquidacion.cotizacionPlomo.label" default="Cotizacion Plomo" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="cotizacionPlomo" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'cotizacionPlomo')}" required="" inputmode="decimal"/>

					<label for="cotizacionPlata">
						<g:message code="proformaLiquidacion.cotizacionPlata.label" default="Cotizacion Plata" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="cotizacionPlata" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'cotizacionPlata')}" required="" inputmode="decimal"/>
				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'cotizacionPlata', 'error')} required">--}%
				%{--<label for="cotizacionPlata">--}%
				%{--<g:message code="proformaLiquidacion.cotizacionPlata.label" default="Cotizacion Plata" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="cotizacionPlata" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'cotizacionPlata')}" required="" inputmode="decimal"/>--}%

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
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'leyPlomoMineralesPagables', 'error')} required">
							%{--<label for="leyPlomoMineralesPagables">--}%
							%{--<g:message code="proformaLiquidacion.leyPlomoMineralesPagables.label" default="Ley Plomo Minerales Pagables" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="leyPlomoMineralesPagables" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'leyPlomoMineralesPagables')}" required="" inputmode="decimal"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'deduccionUnitariaPlomo', 'error')} required">
							%{--<label for="deduccionUnitariaPlomo">--}%
							%{--<g:message code="proformaLiquidacion.deduccionUnitariaPlomo.label" default="Deduccion Unitaria Plomo" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="deduccionUnitariaPlomo" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'deduccionUnitariaPlomo')}" required="" inputmode="decimal"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajePagableLMEPlomo', 'error')} required">
							%{--<label for="porcentajePagableLMEPlomo">--}%
							%{--<g:message code="proformaLiquidacion.porcentajePagableLMEPlomo.label" default="Porcentaje Pagable LMEP lomo" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="porcentajePagableLMEPlomo" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajePagableLMEPlomo')}" required="" inputmode="decimal"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'cotizacionPlomoMineralesPagables', 'error')} required">
							%{--<label for="cotizacionPlomoMineralesPagables">--}%
							%{--<g:message code="proformaLiquidacion.cotizacionPlomoMineralesPagables.label" default="Cotizacion Plomo Minerales Pagables" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="cotizacionPlomoMineralesPagables" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'cotizacionPlomoMineralesPagables')}" required="" inputmode="decimal"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'valorPagablePlomo', 'error')} required">
							%{--<label for="valorPagablePlomo">--}%
							%{--<g:message code="proformaLiquidacion.valorPagablePlomo.label" default="Valor Pagable Plomo" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="valorPagablePlomo" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'valorPagablePlomo')}" required="" inputmode="decimal"/>

						</td>
					</tr>

					<tr>
						<td>Plata</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'leyPlataMineralesPagables', 'error')} required">
							%{--<label for="leyPlataMineralesPagables">--}%
							%{--<g:message code="proformaLiquidacion.leyPlataMineralesPagables.label" default="Ley Plata Minerales Pagables" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="leyPlataMineralesPagables" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'leyPlataMineralesPagables')}" required="" inputmode="decimal"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'deduccionUnitariaPlata', 'error')} required">
							%{--<label for="deduccionUnitariaPlata">--}%
							%{--<g:message code="proformaLiquidacion.deduccionUnitariaPlata.label" default="Deduccion Unitaria Plata" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="deduccionUnitariaPlata" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'deduccionUnitariaPlata')}" required="" inputmode="decimal"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajePagableLMEPlata', 'error')} required">
							%{--<label for="porcentajePagableLMEPlata">--}%
							%{--<g:message code="proformaLiquidacion.porcentajePagableLMEPlata.label" default="Porcentaje Pagable LMEP lomo" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="porcentajePagableLMEPlata" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajePagableLMEPlata')}" required="" inputmode="decimal"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'cotizacionPlataMineralesPagables', 'error')} required">
							%{--<label for="cotizacionPlataMineralesPagables">--}%
							%{--<g:message code="proformaLiquidacion.cotizacionPlataMineralesPagables.label" default="Cotizacion Plata Minerales Pagables" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="cotizacionPlataMineralesPagables" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'cotizacionPlataMineralesPagables')}" required="" inputmode="decimal"/>

						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'valorPagablePlata', 'error')} required">
							%{--<label for="valorPagablePlata">--}%
							%{--<g:message code="proformaLiquidacion.valorPagablePlata.label" default="Valor Pagable Plata" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="valorPagablePlata" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'valorPagablePlata')}" required="" inputmode="decimal"/>

						</td>
					</tr>

					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'valorPagableTotal', 'error')} required">
							<label for="valorPagableTotal" style="width: 80%">
								<g:message code="proformaLiquidacion.valorPagableTotal.label" default="Valor Pagable Total" />
								<span class="required-indicator">*</span>
							</label>
						</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'valorPagableTotal', 'error')} required">
							<g:field name="valorPagableTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'valorPagableTotal')}" required="" inputmode="decimal"/>
						</td>
					</tr>
					</tbody>
				</table>

				<h1 style="font-weight: bold">DEDUCCIONES</h1>

				<h1 style="font-weight: bold">Maquila</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'maquila', 'error')} required">
					<label for="maquila">
						<g:message code="proformaLiquidacion.maquila.label" default="Maquila" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="maquila" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'maquila')}" required="" inputmode="decimal"/>

					<label for="maquilaFinal">
						<g:message code="proformaLiquidacion.maquilaFinal.label" default="Maquila Final" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="maquilaFinal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'maquilaFinal')}" required="" inputmode="decimal"/>

				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'maquilaFinal', 'error')} required">--}%
				%{--<label for="maquilaFinal">--}%
				%{--<g:message code="proformaLiquidacion.maquilaFinal.label" default="Maquila Final" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="maquilaFinal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'maquilaFinal')}" required="" inputmode="decimal"/>--}%

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
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'cotizacionPlomoActual', 'error')} required">
							%{--<label for="cotizacionPlomoActual">--}%
							%{--<g:message code="proformaLiquidacion.cotizacionPlomoActual.label" default="Cotizacion Plomo Actual" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="cotizacionPlomoActual" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'cotizacionPlomoActual')}" required="" inputmode="decimal"/>

						</td>
						<td>-</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'base', 'error')} required">
							%{--<label for="base">--}%
							%{--<g:message code="proformaLiquidacion.base.label" default="Base" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="base" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'base')}" required="" inputmode="decimal"/>

						</td>
						<td>) x</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'escaladorPlomoPlata', 'error')} required">
							%{--<label for="escaladorPlomoPlata">--}%
							%{--<g:message code="proformaLiquidacion.escaladorPlomoPlata.label" default="Escalador Plomo Plata" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="escaladorPlomoPlata" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'escaladorPlomoPlata')}" required="" inputmode="decimal"/>

						</td>
						<td>=</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'gastoRealizacionTotal', 'error')} required">
							%{--<label for="gastoRealizacionTotal">--}%
							%{--<g:message code="proformaLiquidacion.gastoRealizacionTotal.label" default="Gasto Realizacion Total" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="gastoRealizacionTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'gastoRealizacionTotal')}" required="" inputmode="decimal"/>

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
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'leyPlataOnzaTroy', 'error')} required">
							%{--<label for="leyPlataOnzaTroy">--}%
							%{--<g:message code="proformaLiquidacion.leyPlataOnzaTroy.label" default="Ley Plata Onza Troy" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="leyPlataOnzaTroy" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'leyPlataOnzaTroy')}" required="" inputmode="decimal"/>

						</td>

						<td>X</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoRefinacion', 'error')} required">
							%{--<label for="costoRefinacion">--}%
							%{--<g:message code="proformaLiquidacion.costoRefinacion.label" default="Costo Refinacion" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="costoRefinacion" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoRefinacion')}" required="" inputmode="decimal"/>

						</td>

						<td>=</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoRefinacionTotal', 'error')} required">
							%{--<label for="costoRefinacionTotal">--}%
							%{--<g:message code="proformaLiquidacion.costoRefinacionTotal.label" default="Costo Refinacion Total" />--}%
							%{--<span class="required-indicator">*</span>--}%
							%{--</label>--}%
							<g:field name="costoRefinacionTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoRefinacionTotal')}" required="" inputmode="decimal"/>

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
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeArsenico', 'error')} required">
							<g:field name="porcentajeArsenico" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeArsenico')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'arsenicoLibre', 'error')} required">
							<g:field name="arsenicoLibre" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'arsenicoLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoUnitarioArsenico', 'error')} required">
							<g:field name="costoUnitarioArsenico" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoUnitarioArsenico')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioArsenico', 'error')} required">
							<g:field name="porcentajeUnitarioArsenico" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioArsenico')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'penalizacionArsenico', 'error')} required">
							<g:field name="penalizacionArsenico" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'penalizacionArsenico')}" required="" inputmode="decimal"/>
						</td>
					</tr>
					<tr>
						<td>Antimonio</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeAntimonio', 'error')} required">
							<g:field name="porcentajeAntimonio" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeAntimonio')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'antimonioLibre', 'error')} required">
							<g:field name="antimonioLibre" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'antimonioLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoUnitarioAntimonio', 'error')} required">
							<g:field name="costoUnitarioAntimonio" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoUnitarioAntimonio')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioAntimonio', 'error')} required">
							<g:field name="porcentajeUnitarioAntimonio" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioAntimonio')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'penalizacionAntimonio', 'error')} required">
							<g:field name="penalizacionAntimonio" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'penalizacionAntimonio')}" required="" inputmode="decimal"/>
						</td>
					</tr>
					<tr>
						<td>Bismuto</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeBismuto', 'error')} required">
							<g:field name="porcentajeBismuto" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeBismuto')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'bismutoLibre', 'error')} required">
							<g:field name="bismutoLibre" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'bismutoLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoUnitarioBismuto', 'error')} required">
							<g:field name="costoUnitarioBismuto" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoUnitarioBismuto')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioBismuto', 'error')} required">
							<g:field name="porcentajeUnitarioBismuto" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioBismuto')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'penalizacionBismuto', 'error')} required">
							<g:field name="penalizacionBismuto" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'penalizacionBismuto')}" required="" inputmode="decimal"/>
						</td>
					</tr>
					<tr>
						<td>Estano</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeEstano', 'error')} required">
							<g:field name="porcentajeEstano" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeEstano')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'estanoLibre', 'error')} required">
							<g:field name="estanoLibre" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'estanoLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoUnitarioEstano', 'error')} required">
							<g:field name="costoUnitarioEstano" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoUnitarioEstano')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioEstano', 'error')} required">
							<g:field name="porcentajeUnitarioEstano" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioEstano')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'penalizacionEstano', 'error')} required">
							<g:field name="penalizacionEstano" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'penalizacionEstano')}" required="" inputmode="decimal"/>
						</td>
					</tr>
					<tr>
						<td>Hierro</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeHierro', 'error')} required">
							<g:field name="porcentajeHierro" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeHierro')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'hierroLibre', 'error')} required">
							<g:field name="hierroLibre" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'hierroLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoUnitarioHierro', 'error')} required">
							<g:field name="costoUnitarioHierro" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoUnitarioHierro')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioHierro', 'error')} required">
							<g:field name="porcentajeUnitarioHierro" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioHierro')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'penalizacionHierro', 'error')} required">
							<g:field name="penalizacionHierro" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'penalizacionHierro')}" required="" inputmode="decimal"/>
						</td>
					</tr>
					<tr>
						<td>Silice</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeSilice', 'error')} required">
							<g:field name="porcentajeSilice" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeSilice')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'siliceLibre', 'error')} required">
							<g:field name="siliceLibre" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'siliceLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoUnitarioSilice', 'error')} required">
							<g:field name="costoUnitarioSilice" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoUnitarioSilice')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioSilice', 'error')} required">
							<g:field name="porcentajeUnitarioSilice" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioSilice')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'penalizacionSilice', 'error')} required">
							<g:field name="penalizacionSilice" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'penalizacionSilice')}" required="" inputmode="decimal"/>
						</td>
					</tr>
					<tr>
						<td>Zinc</td>
						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeZinc', 'error')} required">
							<g:field name="porcentajeZinc" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeZinc')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'zincLibre', 'error')} required">
							<g:field name="zincLibre" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'zincLibre')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoUnitarioZinc', 'error')} required">
							<g:field name="costoUnitarioZinc" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoUnitarioZinc')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioZinc', 'error')} required">
							<g:field name="porcentajeUnitarioZinc" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'porcentajeUnitarioZinc')}" required="" inputmode="decimal"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'penalizacionZinc', 'error')} required">
							<g:field name="penalizacionZinc" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'penalizacionZinc')}" required="" inputmode="decimal"/>
						</td>
					</tr>
					</tbody>
				</table>

				<h1 style="font-weight: bold">Precio Unitario y Valor Neto</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'precioUnitario', 'error')} required">
					<label for="precioUnitario">
						<g:message code="proformaLiquidacion.precioUnitario.label" default="Precio Unitario" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="precioUnitario" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'precioUnitario')}" required="" inputmode="decimal"/>

					<label for="valorNetoTotal">
						<g:message code="proformaLiquidacion.valorNetoTotal.label" default="Valor Neto Total" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="valorNetoTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'valorNetoTotal')}" required="" inputmode="decimal"/>

				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'valorNetoTotal', 'error')} required">--}%
				%{--<label for="valorNetoTotal">--}%
				%{--<g:message code="proformaLiquidacion.valorNetoTotal.label" default="Valor Neto Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="valorNetoTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'valorNetoTotal')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<h1 style="font-weight: bold">Costos Portuarios y Flete</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoFleteTonelada', 'error')} required">
					<label for="costoFleteTonelada">
						<g:message code="proformaLiquidacion.costoFleteTonelada.label" default="Costo Flete Tonelada" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="costoFleteTonelada" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoFleteTonelada')}" required="" inputmode="decimal"/>

					<label for="costoFleteToneladaTotal">
						<g:message code="proformaLiquidacion.costoFleteToneladaTotal.label" default="Costo Flete Tonelada Total" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="costoFleteToneladaTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoFleteToneladaTotal')}" required="" inputmode="decimal"/>

				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoFleteToneladaTotal', 'error')} required">--}%
				%{--<label for="costoFleteToneladaTotal">--}%
				%{--<g:message code="proformaLiquidacion.costoFleteToneladaTotal.label" default="Costo Flete Tonelada Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoFleteToneladaTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoFleteToneladaTotal')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%

				<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoPortuarioTonelada', 'error')} required">
					<label for="costoPortuarioTonelada">
						<g:message code="proformaLiquidacion.costoPortuarioTonelada.label" default="Costo Portuario Tonelada" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="costoPortuarioTonelada" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoPortuarioTonelada')}" required="" inputmode="decimal"/>

					<label for="costoPortuarioToneladaTotal">
						<g:message code="proformaLiquidacion.costoPortuarioToneladaTotal.label" default="Costo Portuario Tonelada Total" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="costoPortuarioToneladaTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoPortuarioToneladaTotal')}" required="" inputmode="decimal"/>

				</div>

				%{--<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoPortuarioToneladaTotal', 'error')} required">--}%
				%{--<label for="costoPortuarioToneladaTotal">--}%
				%{--<g:message code="proformaLiquidacion.costoPortuarioToneladaTotal.label" default="Costo Portuario Tonelada Total" />--}%
				%{--<span class="required-indicator">*</span>--}%
				%{--</label>--}%
				%{--<g:field name="costoPortuarioToneladaTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoPortuarioToneladaTotal')}" required="" inputmode="decimal"/>--}%

				%{--</div>--}%


					<h1 style="font-weight: bold">Gastos de Operacion</h1>

					<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoOperacionTonelada', 'error')} required">
						<label for="costoOperacionTonelada">
							<g:message code="proformaLiquidacion.costoOperacionTonelada.label" default="Costo Operacion Tonelada" />
							<span class="required-indicator">*</span>
						</label>
						<g:field name="costoOperacionTonelada" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoOperacionTonelada')}" required="" inputmode="decimal"/>

						<label for="costoOperacionToneladaTotal">
							<g:message code="proformaLiquidacion.costoOperacionToneladaTotal.label" default="Costo Operacion Tonelada Total" />
							<span class="required-indicator">*</span>
						</label>
						<g:field name="costoOperacionToneladaTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoOperacionToneladaTotal')}" required="" inputmode="decimal"/>
					</div>

					%{--<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'costoOperacionToneladaTotal', 'error')} required">--}%
					%{--<label for="costoOperacionToneladaTotal">--}%
					%{--<g:message code="proformaLiquidacion.costoOperacionToneladaTotal.label" default="Costo Operacion Tonelada Total" />--}%
					%{--<span class="required-indicator">*</span>--}%
					%{--</label>--}%
					%{--<g:field name="costoOperacionToneladaTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'costoOperacionToneladaTotal')}" required="" inputmode="decimal"/>--}%

					%{--</div>--}%

				<div style="display: none;">

					<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'regaliaDiferenciaPlomo', 'error')} required">
						<label for="regaliaDiferenciaPlomo">
							<g:message code="proformaLiquidacion.regaliaDiferenciaPlomo.label" default="Regalia Diferencia Plomo" />
							<span class="required-indicator">*</span>
						</label>
						<g:field name="regaliaDiferenciaPlomo" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'regaliaDiferenciaPlomo')}" required="" inputmode="decimal"/>

						<label for="regaliaDiferenciaPlomoTotal">
							<g:message code="proformaLiquidacion.regaliaDiferenciaPlomoTotal.label" default="Regalia Diferencia Plomo Total" />
							<span class="required-indicator">*</span>
						</label>
						<g:field name="regaliaDiferenciaPlomoTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'regaliaDiferenciaPlomoTotal')}" required="" inputmode="decimal"/>
					</div>

					%{--<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'regaliaDiferenciaPlomoTotal', 'error')} required">--}%
					%{--<label for="regaliaDiferenciaPlomoTotal">--}%
					%{--<g:message code="proformaLiquidacion.regaliaDiferenciaPlomoTotal.label" default="Regalia Diferencia Plomo Total" />--}%
					%{--<span class="required-indicator">*</span>--}%
					%{--</label>--}%
					%{--<g:field name="regaliaDiferenciaPlomoTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'regaliaDiferenciaPlomoTotal')}" required="" inputmode="decimal"/>--}%

					%{--</div>--}%

					<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'regaliaDiferenciaPlata', 'error')} required">
						<label for="regaliaDiferenciaPlata">
							<g:message code="proformaLiquidacion.regaliaDiferenciaPlata.label" default="Regalia Diferencia Plata" />
							<span class="required-indicator">*</span>
						</label>
						<g:field name="regaliaDiferenciaPlata" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'regaliaDiferenciaPlata')}" required="" inputmode="decimal"/>

						<label for="regaliaDiferenciaPlataTotal">
							<g:message code="proformaLiquidacion.regaliaDiferenciaPlataTotal.label" default="Regalia Diferencia Plata Total" />
							<span class="required-indicator">*</span>
						</label>
						<g:field name="regaliaDiferenciaPlataTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'regaliaDiferenciaPlataTotal')}" required="" inputmode="decimal"/>
					</div>

					%{--<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'regaliaDiferenciaPlataTotal', 'error')} required">--}%
					%{--<label for="regaliaDiferenciaPlataTotal">--}%
					%{--<g:message code="proformaLiquidacion.regaliaDiferenciaPlataTotal.label" default="Regalia Diferencia Plata Total" />--}%
					%{--<span class="required-indicator">*</span>--}%
					%{--</label>--}%
					%{--<g:field name="regaliaDiferenciaPlataTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'regaliaDiferenciaPlataTotal')}" required="" inputmode="decimal"/>--}%

					%{--</div>--}%

					<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'descuentoOperacionesTotal', 'error')} required">
						<label for="descuentoOperacionesTotal">
							<g:message code="proformaLiquidacion.descuentoOperacionesTotal.label" default="Descuento Operaciones Total" />
							<span class="required-indicator">*</span>
						</label>
						<g:field name="descuentoOperacionesTotal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'descuentoOperacionesTotal')}" required="" inputmode="decimal"/>

					</div>
				</div>

				<h1 style="font-weight: bold">Valor Neto Total Final</h1>

				<div class="fieldcontain ${hasErrors(bean: proformaLiquidacionInstance, field: 'valorNetoTotalFinal', 'error')} required">
					<label for="valorNetoTotalFinal">
						<g:message code="proformaLiquidacion.valorNetoTotalFinal.label" default="Valor Neto Total Final" />
						<span class="required-indicator">*</span>
					</label>
					<g:field name="valorNetoTotalFinal" value="${fieldValue(bean: proformaLiquidacionInstance, field: 'valorNetoTotalFinal')}" required="" inputmode="decimal"/>

				</div>
			
			</ol>
			%{--<g:form url="[resource:proformaLiquidacionInstance, action:'delete']" method="DELETE">--}%
				%{--<fieldset class="buttons">--}%
					%{--<g:link class="edit" action="edit" resource="${proformaLiquidacionInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
					%{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
				%{--</fieldset>--}%
			%{--</g:form>--}%

			<fieldset class="buttons">
				<div style="float: left">
					<g:form url="[resource:proformaLiquidacionInstance, action:'delete']" method="DELETE">
						<g:link class="edit" action="edit" resource="${proformaLiquidacionInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</g:form>
				</div>
				<div style="float: right">
					<table>
						<tr>
							<td>
								<g:jasperReport controller="pagoTransporte" action="createReport" jasper="proforma_liquidacion" format="PDF" _format="PDF" name="PROFORMA_LIQUIDACION_${proformaLiquidacionInstance.numeroProformaLiquidacion}">
									<input type="hidden" name="proformaLiquidacionId" value="${proformaLiquidacionInstance.id}" />
								</g:jasperReport>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
		</div>
	</body>
</html>
