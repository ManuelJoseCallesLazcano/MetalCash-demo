
<%@ page import="org.socymet.liquidacion.LiquidacionDeOro" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'liquidacionDeOro.label', default: 'LiquidacionDeOro')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'styleScrolling.css')}" type="text/css" >
		<g:javascript src="jquery-1.10.1.min.js" />
		<g:javascript src="i18n/grid.locale-es.js" />
		<g:javascript src="jquery.jqGrid.min.js" />
		<g:javascript src="jquery-ui-1.10.3.custom.min.js" />
		<g:javascript src="jquery.jgrowl.min.js" />
		<g:javascript src="scrolling.js" />
		<script>
            $(document).ready(function() {
                // CREACION DE TABLA DE RETENCIONES UTILIZANDO COMPONENTE jqGrid
                jQuery("#tablaRetenciones").jqGrid({
                    datatype: "local",
                    height: 200,
                    colNames: ["CODIGO","DESCRIPCION","TIPO","CANTIDAD","UNIDAD","MONTO","ASIGNACION"],
                    colModel:[
                        {name:'CODIGO',index:'CODIGO', width:60},
                        {name:'DESCRIPCION',index:'DESCRIPCION', width:200},
                        {name:'TIPO',index:'TIPO', width:80},
                        {name:'CANTIDAD',index:'CANTIDAD', width:80},
                        {name:'UNIDAD',index:'UNIDAD', width:80},
                        {name:'MONTO',index:'MONTO', width:80},
                        {name:'ASIGNACION',index:'ASIGNACION', width:80} ],
                    multiselect: false,
                    caption: "RETENCIONES"
                });

                var mydata = $("#retenciones").val();
                if(mydata=="")
                    mydata = [];
                else
                    mydata = $.parseJSON(mydata);

                for(var i=0;i<=mydata.length;i++)
                    jQuery("#tablaRetenciones").jqGrid('addRowData',i+1,mydata[i]);

                var liquidoPagable = transFloat($("#liquidoPagable").val());
                if(liquidoPagable<0)
                    $.jGrowl("Debido a que el Liquido Pagable es negativo se ha creado un Anticipo Contra Futura Entrega. El enlace al formulario esta al final de la pagina.",
                        {sticky: true, header: 'ATENCION'});

                function transFloat(numeroString){
                    var numero = numeroString.replace(',','');
                    return parseFloat(numero);
                }
            });
		</script>
	</head>
	<body>
		<a href="#show-liquidacionDeOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-liquidacionDeOro" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list liquidacionDeOro">

				<g:if test="${liquidacionDeOroInstance?.numeroLiquidacionOro}">
					<li class="fieldcontain">
						<span id="numeroLiquidacionOro-label" class="property-label"><g:message code="liquidacionDeOro.numeroLiquidacionOro.label" default="No. Liquidacion" /></span>

						<span class="property-value" aria-labelledby="numeroLiquidacionOro-label"><g:link controller="liquidacionDeOro" action="show" id="${liquidacionDeOroInstance?.id}">${liquidacionDeOroInstance?.numeroLiquidacionOro?.encodeAsHTML()}</g:link></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.recepcionDeOro}">
					<li class="fieldcontain">
						<span id="recepcionDeOro-label" class="property-label"><g:message code="liquidacionDeOro.recepcionDeOro.label" default="Lote" /></span>

						<span class="property-value" aria-labelledby="recepcionDeOro-label"><g:link controller="recepcionDeOro" action="show" id="${liquidacionDeOroInstance?.recepcionDeOro?.id}">${liquidacionDeOroInstance?.recepcionDeOro?.encodeAsHTML()}</g:link></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.fechaDeLiquidacion}">
					<li class="fieldcontain">
						<span id="fechaDeLiquidacion-label" class="property-label"><g:message code="liquidacionDeOro.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

						<span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:formatDate date="${liquidacionDeOroInstance?.fechaDeLiquidacion}" format="dd/MM/yyyy"/></span>

					</li>
				</g:if>

				<h1 style="font-weight: bold">Datos de la Recepcion</h1>

				<g:if test="${liquidacionDeOroInstance?.nombreCliente}">
					<li class="fieldcontain">
						<span id="nombreCliente-label" class="property-label"><g:message code="liquidacionDeOro.nombreCliente.label" default="Nombre Cliente" /></span>

						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="nombreCliente"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.nombreEmpresa}">
					<li class="fieldcontain">
						<span id="nombreEmpresa-label" class="property-label"><g:message code="liquidacionDeOro.nombreEmpresa.label" default="Nombre Empresa" /></span>

						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="nombreEmpresa"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.fechaDeRecepcion}">
					<li class="fieldcontain">
						<span id="fechaDeRecepcion-label" class="property-label"><g:message code="liquidacionDeOro.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="fechaDeRecepcion"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.cantidadDeSacos}">
					<li class="fieldcontain">
						<span id="cantidadDeSacos-label" class="property-label"><g:message code="liquidacionDeOro.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

						<span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="cantidadDeSacos"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.pesoBruto}">
					<li class="fieldcontain">
						<span id="pesoBruto-label" class="property-label"><g:message code="liquidacionDeOro.pesoBruto.label" default="Peso Bruto" /></span>

						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="pesoBruto"/></span>

					</li>
				</g:if>

			%{--<g:if test="${liquidacionDeOroInstance?.tipoDeMineral}">--}%
			%{--<li class="fieldcontain">--}%
			%{--<span id="tipoDeMineral-label" class="property-label"><g:message code="liquidacionDeOro.tipoDeMineral.label" default="Tipo De Mineral" /></span>--}%

			%{--<span class="property-value" aria-labelledby="tipoDeMineral-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="tipoDeMineral"/></span>--}%

			%{--</li>--}%
			%{--</g:if>--}%

			%{--<g:if test="${liquidacionDeOroInstance?.naturalezaMineral}">--}%
			%{--<li class="fieldcontain">--}%
			%{--<span id="naturalezaMineral-label" class="property-label"><g:message code="liquidacionDeOro.naturalezaMineral.label" default="Naturaleza Mineral" /></span>--}%

			%{--<span class="property-value" aria-labelledby="naturalezaMineral-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="naturalezaMineral"/></span>--}%

			%{--</li>--}%
			%{--</g:if>--}%

			%{--<g:if test="${liquidacionDeOroInstance?.estadoDelLote}">--}%
			%{--<li class="fieldcontain">--}%
			%{--<span id="estadoDelLote-label" class="property-label"><g:message code="liquidacionDeOro.estadoDelLote.label" default="Estado Del Lote" /></span>--}%
			%{--<span class="property-value" aria-labelledby="estadoDelLote-label">${liquidacionDeOroInstance.recepcionDeOro.estadoDelLote}</span>--}%
			%{--</li>--}%
			%{--</g:if>--}%

				<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>
			
				<g:if test="${liquidacionDeOroInstance?.cotizacionDiariaDeOro}">
				<li class="fieldcontain">
					<span id="cotizacionDiariaDeOro-label" class="property-label"><g:message code="liquidacionDeOro.cotizacionDiariaDeOro.label" default="Cotizacion Diaria De Oro" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionDiariaDeOro-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="cotizacionDiariaDeOro"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeOroInstance?.cotizacionQuincenalDeOro}">
				<li class="fieldcontain">
					<span id="cotizacionQuincenalDeOro-label" class="property-label"><g:message code="liquidacionDeOro.cotizacionQuincenalDeOro.label" default="Cotizacion Quincenal De Oro" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionQuincenalDeOro-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="cotizacionQuincenalDeOro"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeOroInstance?.alicuotaDeOro}">
				<li class="fieldcontain">
					<span id="alicuotaDeOro-label" class="property-label"><g:message code="liquidacionDeOro.alicuotaDeOro.label" default="Alicuota De Oro" /></span>
					
						<span class="property-value" aria-labelledby="alicuotaDeOro-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="alicuotaDeOro"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeOroInstance?.tipoDeCambioOficial}">
				<li class="fieldcontain">
					<span id="tipoDeCambioOficial-label" class="property-label"><g:message code="liquidacionDeOro.tipoDeCambioOficial.label" default="Tipo De Cambio Oficial" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeCambioOficial-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="tipoDeCambioOficial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeOroInstance?.tipoDeCambioComercial}">
				<li class="fieldcontain">
					<span id="tipoDeCambioComercial-label" class="property-label"><g:message code="liquidacionDeOro.tipoDeCambioComercial.label" default="Tipo De Cambio Comercial" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeCambioComercial-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="tipoDeCambioComercial"/></span>
					
				</li>
				</g:if>
			
				%{--<g:if test="${liquidacionDeOroInstance?.fechaDeLiquidacion}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="fechaDeLiquidacion-label" class="property-label"><g:message code="liquidacionDeOro.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:formatDate date="${liquidacionDeOroInstance?.fechaDeLiquidacion}" /></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			
				%{--<g:if test="${liquidacionDeOroInstance?.kilosNetosHumedos}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="kilosNetosHumedos-label" class="property-label"><g:message code="liquidacionDeOro.kilosNetosHumedos.label" default="Kilos Netos Humedos" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="kilosNetosHumedos-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="kilosNetosHumedos"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%

				<h1 style="font-weight: bold">Detalle de Leyes</h1>

				<table class="center" style="width: 70%;">
					<thead>
					<tr>
						<th style="width: 40%">ELEMENTO</th>
						<th style="width: 20%">LEY EMPRESA</th>
						<th style="width: 20%">LEY CLIENTE</th>
						<th style="width: 20%">LEY FINAL</th>
					</tr>
					</thead>
					<tbody>
					%{--<tr>--}%
						%{--<td class="fieldcontain required">--}%
							%{--<label for="porcentajeMermaPromexbol">--}%
								%{--<g:message code="liquidacionDeOro.porcentajeMermaPromexbol.label" default="Merma" />--}%
							%{--</label>--}%
						%{--</td>--}%
						%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeMermaPromexbol', 'error')} required">--}%
							%{--<g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeMermaPromexbol"/>--}%
						%{--</td>--}%

						%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeMermaCliente', 'error')} required">--}%
							%{--<g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeMermaCliente"/>--}%
						%{--</td>--}%

						%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeMermaFinal', 'error')} required">--}%
							%{--<g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeMermaFinal"/>--}%
						%{--</td>--}%
					%{--</tr>--}%
					<tr>
						<td class="fieldcontain required">
							<label for="porcentajeOroPromexbol">
								<g:message code="liquidacionDeOro.porcentajeOroPromexbol.label" default="Oro" />
							</label>
						</td>
						<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeOroPromexbol', 'error')} required">
							<g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeOroPromexbol"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeOroCliente', 'error')} required">
							<g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeOroCliente"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeOroFinal', 'error')} required">
							<g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeOroFinal"/>
						</td>
					</tr>

					<tr>
						<td class="fieldcontain required">
							<label for="porcentajeHumedadPromexbol">
								<g:message code="liquidacionDeOro.porcentajeHumedadPromexbol.label" default="Humedad" />
							</label>
						</td>
						<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
							<g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeHumedadPromexbol"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeHumedadCliente', 'error')} required">
							<g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeHumedadCliente"/>
						</td>

						<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeHumedadFinal', 'error')} required">
							<g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeHumedadFinal"/>
						</td>
					</tr>

					</tbody>
				</table>
			
				%{--<g:if test="${liquidacionDeOroInstance?.porcentajeOroPromexbol}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="porcentajeOroPromexbol-label" class="property-label"><g:message code="liquidacionDeOro.porcentajeOroPromexbol.label" default="Porcentaje Oro Promexbol" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="porcentajeOroPromexbol-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeOroPromexbol"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			%{----}%
				%{--<g:if test="${liquidacionDeOroInstance?.porcentajeHumedadPromexbol}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="porcentajeHumedadPromexbol-label" class="property-label"><g:message code="liquidacionDeOro.porcentajeHumedadPromexbol.label" default="Porcentaje Humedad Promexbol" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="porcentajeHumedadPromexbol-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeHumedadPromexbol"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			%{----}%
				%{--<g:if test="${liquidacionDeOroInstance?.porcentajeMermaPromexbol}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="porcentajeMermaPromexbol-label" class="property-label"><g:message code="liquidacionDeOro.porcentajeMermaPromexbol.label" default="Porcentaje Merma Promexbol" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="porcentajeMermaPromexbol-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeMermaPromexbol"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			%{----}%
				%{--<g:if test="${liquidacionDeOroInstance?.porcentajeOroCliente}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="porcentajeOroCliente-label" class="property-label"><g:message code="liquidacionDeOro.porcentajeOroCliente.label" default="Porcentaje Oro Cliente" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="porcentajeOroCliente-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeOroCliente"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			%{----}%
				%{--<g:if test="${liquidacionDeOroInstance?.porcentajeHumedadCliente}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="porcentajeHumedadCliente-label" class="property-label"><g:message code="liquidacionDeOro.porcentajeHumedadCliente.label" default="Porcentaje Humedad Cliente" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="porcentajeHumedadCliente-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeHumedadCliente"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			%{----}%
				%{--<g:if test="${liquidacionDeOroInstance?.porcentajeMermaCliente}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="porcentajeMermaCliente-label" class="property-label"><g:message code="liquidacionDeOro.porcentajeMermaCliente.label" default="Porcentaje Merma Cliente" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="porcentajeMermaCliente-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeMermaCliente"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			%{----}%
				%{--<g:if test="${liquidacionDeOroInstance?.porcentajeOroFinal}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="porcentajeOroFinal-label" class="property-label"><g:message code="liquidacionDeOro.porcentajeOroFinal.label" default="Porcentaje Oro Final" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="porcentajeOroFinal-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeOroFinal"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			%{----}%
				%{--<g:if test="${liquidacionDeOroInstance?.porcentajeHumedadFinal}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="porcentajeHumedadFinal-label" class="property-label"><g:message code="liquidacionDeOro.porcentajeHumedadFinal.label" default="Porcentaje Humedad Final" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="porcentajeHumedadFinal-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeHumedadFinal"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			%{----}%
				%{--<g:if test="${liquidacionDeOroInstance?.porcentajeMermaFinal}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="porcentajeMermaFinal-label" class="property-label"><g:message code="liquidacionDeOro.porcentajeMermaFinal.label" default="Porcentaje Merma Final" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="porcentajeMermaFinal-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeMermaFinal"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%

				<h1 style="font-weight: bold">Valoraci&oacute;n del Lote</h1>

				<g:if test="${liquidacionDeOroInstance?.tablaPreciosOro}">
				<li class="fieldcontain">
					<span id="tablaPreciosOro-label" class="property-label"><g:message code="liquidacionDeOro.tablaPreciosOro.label" default="Tabla Precios Oro" /></span>
					
						<span class="property-value" aria-labelledby="tablaPreciosOro-label"><g:link controller="tablaPreciosOro" action="show" id="${liquidacionDeOroInstance?.tablaPreciosOro?.id}">${liquidacionDeOroInstance?.tablaPreciosOro?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.valorPorTonelada}">
					<li class="fieldcontain">
						<span id="valorPorTonelada-label" class="property-label"><g:message code="liquidacionDeOro.valorPorTonelada.label" default="Valor Por Tonelada" /></span>

						<span class="property-value" aria-labelledby="valorPorTonelada-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="valorPorTonelada"/></span>

					</li>
				</g:if>

			%{--<g:if test="${liquidacionDeOroInstance?.margen}">--}%
			%{--<li class="fieldcontain">--}%
			%{--<span id="margen-label" class="property-label"><g:message code="liquidacionDeOro.margen.label" default="Margen" /></span>--}%
			%{----}%
			%{--<span class="property-value" aria-labelledby="margen-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="margen"/></span>--}%
			%{----}%
			%{--</li>--}%
			%{--</g:if>--}%

			%{--<g:if test="${liquidacionDeOroInstance?.porcentajeRegalia}">--}%
			%{--<li class="fieldcontain">--}%
			%{--<span id="porcentajeRegalia-label" class="property-label"><g:message code="liquidacionDeOro.porcentajeRegalia.label" default="Porcentaje Regalia" /></span>--}%
			%{----}%
			%{--<span class="property-value" aria-labelledby="porcentajeRegalia-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeRegalia"/></span>--}%
			%{----}%
			%{--</li>--}%
			%{--</g:if>--}%

				<g:if test="${liquidacionDeOroInstance?.regaliaMinera}">
					<li class="fieldcontain">
						<span id="regaliaMinera-label" class="property-label"><g:message code="liquidacionDeOro.regaliaMinera.label" default="Regalia Minera" /></span>

						<span class="property-value" aria-labelledby="regaliaMinera-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="regaliaMinera"/></span>

					</li>
				</g:if>

				%{--<g:if test="${liquidacionDeOroInstance?.valorNetoMineral}">--}%
					%{--<li class="fieldcontain">--}%
						%{--<span id="valorNetoMineral-label" class="property-label"><g:message code="liquidacionDeOro.valorNetoMineral.label" default="Valor Neto Mineral" /></span>--}%

						%{--<span class="property-value" aria-labelledby="valorNetoMineral-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="valorNetoMineral"/></span>--}%

					%{--</li>--}%
				%{--</g:if>--}%

				<g:if test="${liquidacionDeOroInstance?.valorNetoMineralEnBolivianos}">
					<li class="fieldcontain">
						<span id="valorNetoMineralEnBolivianos-label" class="property-label"><g:message code="liquidacionDeOro.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" /></span>

						<span class="property-value" aria-labelledby="valorNetoMineralEnBolivianos-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="valorNetoMineralEnBolivianos"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.porcentajeBonificacion}">
					<li class="fieldcontain">
						<span id="porcentajeBonificacion-label" class="property-label"><g:message code="liquidacionDeOro.porcentajeBonificacion.label" default="Porcentaje Bonificacion" /></span>

						<span class="property-value" aria-labelledby="porcentajeBonificacion-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="porcentajeBonificacion"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.bonoCalidad}">
					<li class="fieldcontain">
						<span id="bonoCalidad-label" class="property-label"><g:message code="liquidacionDeOro.bonoCalidad.label" default="Bono Calidad" /></span>

						<span class="property-value" aria-labelledby="bonoCalidad-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="bonoCalidad"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.bonoIncentivo}">
					<li class="fieldcontain">
						<span id="bonoIncentivo-label" class="property-label"><g:message code="liquidacionDeOro.bonoIncentivo.label" default="Bono Incentivo" /></span>

						<span class="property-value" aria-labelledby="bonoIncentivo-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="bonoIncentivo"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.valorDeCompra}">
					<li class="fieldcontain">
						<span id="valorDeCompra-label" class="property-label"><g:message code="liquidacionDeOro.valorDeCompra.label" default="Valor De Compra" /></span>

						<span class="property-value" aria-labelledby="valorDeCompra-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="valorDeCompra"/></span>

					</li>
				</g:if>

				<h1 style="font-weight: bold">Pesos y Valores Brutos parciales</h1>

				<g:if test="${liquidacionDeOroInstance?.kilosNetosSecos}">
					<li class="fieldcontain">
						<span id="kilosNetosSecos-label" class="property-label"><g:message code="liquidacionDeOro.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>

						<span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="kilosNetosSecos"/></span>

					</li>
				</g:if>


				<g:if test="${liquidacionDeOroInstance?.kilosFinosOro}">
				<li class="fieldcontain">
					<span id="kilosFinosOro-label" class="property-label"><g:message code="liquidacionDeOro.kilosFinosOro.label" default="Kilos Finos Oro" /></span>
					
						<span class="property-value" aria-labelledby="kilosFinosOro-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="kilosFinosOro"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeOroInstance?.onzasTroyDeOro}">
				<li class="fieldcontain">
					<span id="onzasTroyDeOro-label" class="property-label"><g:message code="liquidacionDeOro.onzasTroyDeOro.label" default="Onzas Troy De Oro" /></span>
					
						<span class="property-value" aria-labelledby="onzasTroyDeOro-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="onzasTroyDeOro"/></span>
					
				</li>
				</g:if>
			
				%{--<g:if test="${liquidacionDeOroInstance?.valorOficialBrutoDeOro}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="valorOficialBrutoDeOro-label" class="property-label"><g:message code="liquidacionDeOro.valorOficialBrutoDeOro.label" default="Valor Oficial Bruto De Oro" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="valorOficialBrutoDeOro-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="valorOficialBrutoDeOro"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			%{----}%
				%{--<g:if test="${liquidacionDeOroInstance?.valorOficialBrutoDeOroEnBolivianos}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="valorOficialBrutoDeOroEnBolivianos-label" class="property-label"><g:message code="liquidacionDeOro.valorOficialBrutoDeOroEnBolivianos.label" default="Valor Oficial Bruto De Oro En Bolivianos" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="valorOficialBrutoDeOroEnBolivianos-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="valorOficialBrutoDeOroEnBolivianos"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			%{----}%
				%{--<g:if test="${liquidacionDeOroInstance?.valorOficialBruto}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="valorOficialBruto-label" class="property-label"><g:message code="liquidacionDeOro.valorOficialBruto.label" default="Valor Oficial Bruto" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="valorOficialBruto-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="valorOficialBruto"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%

				<h1 style="font-weight: bold; font-size: 12px">Retenciones</h1>
				<g:hiddenField name="retenciones" value="${liquidacionDeOroInstance?.retenciones}"/>
				<div style="width: 700px; margin-left: auto; margin-right: auto;"><table id="tablaRetenciones"></table></div>

				%{--<g:if test="${liquidacionDeOroInstance?.retenciones}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="retenciones-label" class="property-label"><g:message code="liquidacionDeOro.retenciones.label" default="Retenciones" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="retenciones-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="retenciones"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%

				<g:if test="${liquidacionDeOroInstance?.totalRetenciones}">
					<li class="fieldcontain">
						<span id="totalRetenciones-label" class="property-label"><g:message code="liquidacionDeOro.totalRetenciones.label" default="Total Retenciones" /></span>

						<span class="property-value" aria-labelledby="totalRetenciones-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="totalRetenciones"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.totalPagado}">
					<li class="fieldcontain">
						<span id="totalPagado-label" class="property-label"><g:message code="liquidacionDeOro.totalPagado.label" default="Total Pagado" /></span>

						<span class="property-value" aria-labelledby="totalPagado-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="totalPagado"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.totalAnticiposContraEntrega}">
					<li class="fieldcontain">
						<span id="totalAnticiposContraEntrega-label" class="property-label"><g:message code="liquidacionDeOro.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" /></span>

						<span class="property-value" aria-labelledby="totalAnticiposContraEntrega-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="totalAnticiposContraEntrega"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.totalAnticiposContraFuturaEntrega}">
					<li class="fieldcontain">
						<span id="totalAnticiposContraFuturaEntrega-label" class="property-label"><g:message code="liquidacionDeOro.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" /></span>

						<span class="property-value" aria-labelledby="totalAnticiposContraFuturaEntrega-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="totalAnticiposContraFuturaEntrega"/></span>

					</li>
				</g:if>

				<li class="fieldcontain">
					<span id="totalLiquidoPagable-label" class="property-label"><g:message code="liquidacionDeOro.totalLiquidoPagable.label" default="Total Liquido Pagable" /></span>

					<span class="property-value" aria-labelledby="totalLiquidoPagable-label" style="font-size: 16px;font-weight: bold;"><g:fieldValue bean="${liquidacionDeOroInstance}" field="totalLiquidoPagable"/></span>

				</li>

				<li class="fieldcontain">
					<span id="bonoTransporteKilosNetosSecosTotal-label" class="property-label"><g:message code="liquidacionDeOro.bonoTransporteKilosNetosSecosTotal.label" default="Bono Transporte Kilos Netos Secos Total" /></span>

					<span class="property-value" aria-labelledby="bonoTransporteKilosNetosSecosTotal-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="bonoTransporteKilosNetosSecosTotal"/></span>

				</li>

				<li class="fieldcontain">
					<span id="totalLiquidoPagableFinal-label" class="property-label"><g:message code="liquidacionDeOro.totalLiquidoPagableFinal.label" default="Total Liquido Pagable Final" /></span>

					<span class="property-value" aria-labelledby="totalLiquidoPagableFinal-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="totalLiquidoPagableFinal"/></span>

				</li>

				<g:if test="${liquidacionDeOroInstance?.nombreComposito}">
					<li class="fieldcontain">
						<span id="nombreComposito-label" class="property-label"><g:message code="liquidacionDeOro.nombreComposito.label" default="Nombre Composito" /></span>

						<span class="property-value" aria-labelledby="nombreComposito-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="nombreComposito"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.observaciones}">
					<li class="fieldcontain">
						<span id="observaciones-label" class="property-label"><g:message code="liquidacionDeOro.observaciones.label" default="Observaciones" /></span>

						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="observaciones"/></span>

					</li>
				</g:if>

				<g:if test="${liquidacionDeOroInstance?.motivoDeModificacion}">
					<li class="fieldcontain">
						<span id="motivoDeModificacion-label" class="property-label"><g:message code="liquidacionDeOro.motivoDeModificacion.label" default="Motivo De Modificacion" /></span>

						<span class="property-value" aria-labelledby="motivoDeModificacion-label"><g:fieldValue bean="${liquidacionDeOroInstance}" field="motivoDeModificacion"/></span>

					</li>
				</g:if>

			%{--<h1 style="font-weight: bold">Detalle de Analisis Realizados</h1>--}%

			%{--<table class="center" border="0" style="width: 70%;">--}%
			%{--<thead>--}%
			%{--<tr>--}%
			%{--<th style="text-align: center; width: 70%">DESCRIPCION DE ANALISIS</th>--}%
			%{--<th style="text-align: center; width: 30%">COSTO</th>--}%
			%{--</tr>--}%
			%{--</thead>--}%
			%{--<tbody>--}%
			%{--<tr>--}%
			%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'detalleLaboratorio1', 'error')}">--}%
			%{--${liquidacionDeOroInstance.detalleLaboratorio1}--}%
			%{--</td>--}%
			%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'costoLaboratorio1', 'error')}">--}%
			%{--${liquidacionDeOroInstance.costoLaboratorio1}--}%
			%{--</td>--}%
			%{--</tr>--}%
			%{--<tr>--}%
			%{--<g:if test="${liquidacionDeOroInstance?.detalleLaboratorio2}">--}%
			%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'detalleLaboratorio2', 'error')}">--}%
			%{--${liquidacionDeOroInstance.detalleLaboratorio2}--}%
			%{--</td>--}%
			%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'costoLaboratorio2', 'error')}">--}%
			%{--${liquidacionDeOroInstance.costoLaboratorio2}--}%
			%{--</td>--}%
			%{--</g:if>--}%
			%{--</tr>--}%
			%{--<tr>--}%
			%{--<g:if test="${liquidacionDeOroInstance?.detalleLaboratorio3}">--}%
			%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'detalleLaboratorio3', 'error')}">--}%
			%{--${liquidacionDeOroInstance.detalleLaboratorio3}--}%
			%{--</td>--}%
			%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'costoLaboratorio3', 'error')}">--}%
			%{--${liquidacionDeOroInstance.costoLaboratorio3}--}%
			%{--</td>--}%
			%{--</g:if>--}%
			%{--</tr>--}%
			%{--<tr>--}%
			%{--<g:if test="${liquidacionDeOroInstance?.detalleLaboratorio4}">--}%
			%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'detalleLaboratorio4', 'error')}">--}%
			%{--${liquidacionDeOroInstance.detalleLaboratorio4}--}%
			%{--</td>--}%
			%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'costoLaboratorio4', 'error')}">--}%
			%{--${liquidacionDeOroInstance.costoLaboratorio4}--}%
			%{--</td>--}%
			%{--</g:if>--}%
			%{--</tr>--}%
			%{--<tr>--}%
			%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'detalleLaboratorio4', 'error')}">--}%
			%{--<label for="totalCostoLaboratorio" style="width: 90%">--}%
			%{--<g:message code="liquidacionDeOro.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />--}%
			%{--</label>--}%
			%{--</td>--}%
			%{--<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'costoLaboratorio4', 'error')}">--}%
			%{--<g:field name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>--}%
			%{--</td>--}%
			%{--</tr>--}%
			%{--</tbody>--}%
			%{--</table>--}%

				<g:hiddenField name="liquidoPagable" value="${liquidacionDeOroInstance?.totalLiquidoPagable}" />
				<g:if test="${liquidacionDeOroInstance?.totalLiquidoPagable<0}">
					<h1 style="font-weight: bold">Enlace a Anticipo Contra Futura Entrega (Clic en enlace para desplegar formulario)</h1>
					<li class="fieldcontain">
						<label style="font-size: 16px; font-weight: bold; color: red; width: 70%">
							<g:link controller="anticipoContraFuturaEntrega" action="show" id="${org.socymet.anticipos.AnticipoContraFuturaEntrega.findByLiquidacionId(liquidacionDeOroInstance.id).id}" target="_blank" style="color: red"> ANTICIPO CONTRA FUTURA ENTREGA GENERADO!</g:link>
						</label>
					</li>
				</g:if>

			</ol>
			<fieldset class="buttons">
				<div style="float: left">
					<g:form>
						<g:hiddenField name="id" value="${liquidacionDeOroInstance?.id}" />
					%{--<g:if test="${liquidacionDeOroInstance.fechaDeCancelacion!=(new java.util.Date(84,5,14))&&liquidacionDeOroInstance.fechaDeCancelacion}">--}%
					%{--<span id="observaciones-label" class="property-label" style="font-weight: bold; color: green">CANCELADO</span>--}%
					%{--</g:if>--}%
					%{--<g:if test="${liquidacionDeOroInstance.fechaDeCancelacion==(new java.util.Date(84,5,14))&&liquidacionDeOroInstance.fechaDeCancelacion}">--}%
					%{--<span id="observaciones-label" class="property-label" style="font-weight: bold; color: red">SIN CANCELAR</span>--}%
					%{--</g:if>--}%
						<sec:ifAnyGranted roles="ROLE_ADMIN">
%{--							<g:link class="edit" action="edit" id="${liquidacionDeOroInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
							<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
						</sec:ifAnyGranted>
					</g:form>
				</div>
				<div style="float: right">
				%{--<h3 style="color: deepskyblue">CON HUMEDAD</h3>--}%
					<g:jasperReport controller="liquidacionDeOro" action="crearReporte" jasper="liquidacion_oro" format="PDF" name="ReporteLiquidacion${liquidacionDeOroInstance.lote}">
						<input type="hidden" name="LIQ_SN_ID" value="${liquidacionDeOroInstance.id}" />
					</g:jasperReport>
                    <g:jasperReport controller="liquidacionDeOro" action="crearReporte" jasper="liquidacion_oro_reducido" format="PDF" name="ReporteLiquidacion${liquidacionDeOroInstance.lote}_REDUCIDO">
                        <input type="hidden" name="LIQ_SN_ID" value="${liquidacionDeOroInstance.id}" />
                    </g:jasperReport>
				%{--<h3 style="color: darkorange">SIN HUMEDAD</h3>--}%
				%{--<g:jasperReport controller="liquidacionDeOro" action="crearReporte" jasper="liquidacion_complejo_sin_humedad" format="PDF,RTF" name="ReporteLiquidacion${liquidacionDeOroInstance.lote}">--}%
				%{--<input type="hidden" name="LIQ_SN_ID" value="${liquidacionDeOroInstance.id}" />--}%
				%{--</g:jasperReport>--}%
				</div>

			</fieldset>
			<div style="display:none;" class="nav_up" id="nav_up"></div>
			<div style="display:none;" class="nav_down" id="nav_down"></div>
		</div>
	</body>
</html>
