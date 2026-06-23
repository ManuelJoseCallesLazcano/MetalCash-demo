
<%@ page import="org.socymet.liquidacion.LiquidacionDeEstano" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="liquidacionDeEstano/calculosLiquidacion.js" />
        <g:javascript src="jquery.jgrowl.min.js" />
        <script>
            $(document).ready(function() {
                var liquidoPagable = transFloat($("#liquidoPagable").val());
                if(liquidoPagable<0)
                    $.jGrowl("Debido a que el Liquido Pagable es negativo se ha creado un Anticipo Contra Futura Entrega. El enlace al formulario esta al final de la pagina.",
                        {sticky: true, header: 'ATENCION'});
            });
        </script>
	</head>
	<body>
		<a href="#show-liquidacionDeEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-liquidacionDeEstano" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list liquidacionDeEstano">

            <g:if test="${liquidacionDeEstanoInstance?.numeroLiquidacionEstano}">
                <li class="fieldcontain">
                    <span id="numeroLiquidacionEstano-label" class="property-label"><g:message code="liquidacionDeEstano.numeroLiquidacionEstano.label" default="No. Liquidacion" /></span>

                    <span class="property-value" aria-labelledby="numeroLiquidacionEstano-label"><g:link controller="liquidacionDeEstano" action="show" id="${liquidacionDeEstanoInstance?.id}">${liquidacionDeEstanoInstance?.numeroLiquidacionEstano?.encodeAsHTML()}</g:link></span>

                </li>
            </g:if>

            <g:if test="${liquidacionDeEstanoInstance?.recepcionDeEstano}">
				<li class="fieldcontain">
					<span id="recepcionDeEstano-label" class="property-label"><g:message code="liquidacionDeEstano.recepcionDeEstano.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="recepcionDeEstano-label"><g:link controller="recepcionDeEstano" action="show" id="${liquidacionDeEstanoInstance?.recepcionDeEstano?.id}">${liquidacionDeEstanoInstance?.recepcionDeEstano?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>

                <h1 style="font-weight: bold">Datos de la Recepcion</h1>

                <g:if test="${liquidacionDeEstanoInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="liquidacionDeEstano.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="liquidacionDeEstano.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="liquidacionDeEstano.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.cantidadDeSacos}">
				<li class="fieldcontain">
					<span id="cantidadDeSacos-label" class="property-label"><g:message code="liquidacionDeEstano.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>
					
						<span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="cantidadDeSacos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.tara}">
				<li class="fieldcontain">
					<span id="tara-label" class="property-label"><g:message code="liquidacionDeEstano.tara.label" default="Tara" /></span>
					
						<span class="property-value" aria-labelledby="tara-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="tara"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.estadoDelLote}">
				<li class="fieldcontain">
					<span id="estadoDelLote-label" class="property-label"><g:message code="liquidacionDeEstano.estadoDelLote.label" default="Estado Del Lote" /></span>
						<span class="property-value" aria-labelledby="estadoDelLote-label">${liquidacionDeEstanoInstance.recepcionDeEstano.estadoDelLote}</span>
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="liquidacionDeEstano.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>

            <h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

            <table class="center" border="0" style="width: 80%;">
                <tbody>
                <tr>
                    <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'cotizacionDiariaDeEstano', 'error')} required">
                        <label for="cotizacionDiariaDeEstano" style="width: 50%">
                            <g:message code="liquidacionDeEstano.cotizacionDiariaDeEstano.label" default="Cot. Dia Estano" />
                            <span class="required-indicator">*</span>
                        </label>
                        ${liquidacionDeEstanoInstance.cotizacionDiariaDeEstano}
                    </td>
                    <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'cotizacionQuincenalDeEstano', 'error')} required">
                        <label for="cotizacionQuincenalDeEstano" style="width: 60%">
                            <g:message code="liquidacionDeEstano.cotizacionQuincenalDeEstano.label" default="Cot. Quinc. Estano" />
                            <span class="required-indicator">*</span>
                        </label>
                        ${liquidacionDeEstanoInstance.cotizacionQuincenalDeEstano}
                    </td>
                    <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'alicuotaDeEstano', 'error')} required">
                        <label for="alicuotaDeEstano" style="width: 50%">
                            <g:message code="liquidacionDeEstano.alicuotaDeEstano.label" default="Alicuota Estano" />
                            <span class="required-indicator">*</span>
                        </label>
                        ${liquidacionDeEstanoInstance.alicuotaDeEstano}
                    </td>
                </tr>
                <tr>
                    <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'tipoDeCambioOficial', 'error')} required">
                        <label for="tipoDeCambioOficial" style="width: 50%">
                            <g:message code="liquidacionDeEstano.tipoDeCambioOficial.label" default="T/C  Oficial" />
                            <span class="required-indicator">*</span>
                        </label>
                        ${liquidacionDeEstanoInstance.tipoDeCambioOficial}
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'tipoDeCambioComercial', 'error')} required">
                        <label for="tipoDeCambioComercial" style="width: 60%">
                            <g:message code="liquidacionDeEstano.tipoDeCambioComercial.label" default="T/C  Comercial" />
                            <span class="required-indicator">*</span>
                        </label>
                       ${liquidacionDeEstanoInstance.tipoDeCambioComercial}
                    </td>
                    <td>
                        &nbsp;</td>
                </tr>
                </tbody>
            </table>

                <h1 style="font-weight: bold">Detalle de Leyes</h1>

                <table class="center" style="width: 70%;">
                    <thead>
                    <tr>
                        <th style="width: 40%">ELEMENTO</th>
                        <th style="width: 20%">LEY COMERMIN</th>
                        <th style="width: 20%">LEY CLIENTE</th>
                        <th style="width: 20%">LEY FINAL</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr style="display: none">
                        <td class="fieldcontain required">
                            <label for="porcentajeMermaPromexbol">
                                <g:message code="liquidacionDeEstano.porcentajeMermaPromexbol.label" default="Merma" />
                            </label>
                        </td>
                        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
                            %{--<g:field name="porcentajeMermaPromexbol" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaPromexbol')}" required="" inputmode="decimal"/>--}%
                            ${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaPromexbol')}
                        </td>

                        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaCliente', 'error')} required">
                            %{--<g:field name="porcentajeMermaCliente" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaCliente')}" required="" inputmode="decimal"/>--}%
                            ${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaCliente')}
                        </td>

                        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaFinal', 'error')} required">
                            %{--<g:field name="porcentajeMermaFinal" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaFinal')}" required="" readonly="true"/>--}%
                            ${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaFinal')}
                        </td>
                    </tr>

                    <tr>
                        <td class="fieldcontain required">
                            <label for="porcentajeEstanoCliente">
                                <g:message code="liquidacionDeEstano.porcentajeEstanoCliente.label" default="Estano" />
                            </label>
                        </td>

                        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoPromexbol', 'error')} required">
                            %{--<g:field name="porcentajeEstanoPromexbol" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoPromexbol')}" required="" inputmode="decimal"/>--}%
                            ${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoPromexbol')}
                        </td>

                        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoCliente', 'error')} required">
                            %{--<g:field name="porcentajeEstanoCliente" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoCliente')}" required="" inputmode="decimal"/>--}%
                            ${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoCliente')}
                        </td>

                        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoFinal', 'error')} required">
                            %{--<g:field name="porcentajeEstanoFinal" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoFinal')}" required=""  inputmode="decimal"/>--}%
                            ${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoFinal')}
                        </td>
                    </tr>

                    <tr>
                        <td class="fieldcontain required">
                            <label for="porcentajeHumedadPromexbol">
                                <g:message code="liquidacionDeEstano.porcentajeHumedadPromexbol.label" default="Humedad" />
                            </label>
                        </td>
                        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
                            %{--<g:field name="porcentajeHumedadPromexbol" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadPromexbol')}" required="" inputmode="decimal"/>--}%
                            ${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadPromexbol')}
                        </td>

                        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadCliente', 'error')} required">
                            %{--<g:field name="porcentajeHumedadCliente" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadCliente')}" required="" inputmode="decimal"/>--}%
                            ${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadCliente')}
                        </td>

                        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadFinal', 'error')} required">
                            %{--<g:field name="porcentajeHumedadFinal" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadFinal')}" required=""  inputmode="decimal"/>--}%
                            ${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadFinal')}
                        </td>
                    </tr>

                    </tbody>
                </table>

            <h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

				<g:if test="${liquidacionDeEstanoInstance?.fechaDeLiquidacion}">
				<li class="fieldcontain">
					<span id="fechaDeLiquidacion-label" class="property-label"><g:message code="liquidacionDeEstano.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:formatDate date="${liquidacionDeEstanoInstance?.fechaDeLiquidacion}" format="dd/MM/yyyy"/></span>
					
				</li>
				</g:if>

            <table class="center" border="0" style="width: 80%;">
                <tbody>
                <tr>
                    <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'kilosNetosHumedos', 'error')} required">
                        <label for="kilosNetosHumedos" style="width: 50%">
                            <g:message code="liquidacionDeEstano.kilosNetosHumedos.label" default="K. N. H." />
                            <span class="required-indicator">*</span>
                        </label>
                        ${liquidacionDeEstanoInstance.kilosNetosHumedos}
                    </td>
                    <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'kilosNetosSecos', 'error')} required">
                        <label for="kilosNetosSecos" style="width: 50%">
                            <g:message code="liquidacionDeEstano.kilosNetosSecos.label" default="K. N. S." />
                            <span class="required-indicator">*</span>
                        </label>
                        ${liquidacionDeEstanoInstance.kilosNetosSecos}
                    </td>
                </tr>
                <tr>
                    <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'kilosFinosEstano', 'error')} required">
                        <label for="kilosFinosEstano" style="width: 50%">
                            <g:message code="liquidacionDeEstano.kilosFinosEstano.label" default="Kilos Finos" />
                            <span class="required-indicator">*</span>
                        </label>
                        ${liquidacionDeEstanoInstance.kilosFinosEstano}
                    </td>

                    <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'librasFinasDeEstano', 'error')} required">
                        <label for="librasFinasDeEstano" style="width: 50%">
                            <g:message code="liquidacionDeEstano.librasFinasDeEstano.label" default="Libras Finas" />
                            <span class="required-indicator">*</span>
                        </label>
                        ${liquidacionDeEstanoInstance.librasFinasDeEstano}
                    </td>
                </tr>
                </tbody>
            </table>
			
				<g:if test="${liquidacionDeEstanoInstance?.valorOficialBruto}">
				<li class="fieldcontain">
					<span id="valorOficialBruto-label" class="property-label"><g:message code="liquidacionDeEstano.valorOficialBruto.label" default="Valor Oficial Bruto" /></span>
					
						<span class="property-value" aria-labelledby="valorOficialBruto-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="valorOficialBruto"/></span>
					
				</li>
				</g:if>

				<g:if test="${liquidacionDeEstanoInstance?.ajustePrecioEstano}">
				<li class="fieldcontain">
					<span id="ajustePrecioEstano-label" class="property-label"><g:message code="liquidacionDeEstano.ajustePrecioEstano.label" default="Ajuste Precio Estano" /></span>

						<span class="property-value" aria-labelledby="ajustePrecioEstano-label"><g:link controller="ajustePrecioEstano" action="show" id="${liquidacionDeEstanoInstance?.ajustePrecioEstano?.id}">${liquidacionDeEstanoInstance?.ajustePrecioEstano?.encodeAsHTML()}</g:link></span>

				</li>
				</g:if>

            <g:if test="${liquidacionDeEstanoInstance?.valorPorTonelada}">
                <li class="fieldcontain">
                    <span id="valorPorTonelada-label" class="property-label"><g:message code="liquidacionDeEstano.valorPorTonelada.label" default="Valor Por Tonelada" /></span>

                    <span class="property-value" aria-labelledby="valorPorTonelada-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="valorPorTonelada"/></span>

                </li>
            </g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.regaliaMinera}">
				<li class="fieldcontain">
					<span id="regaliaMinera-label" class="property-label"><g:message code="liquidacionDeEstano.regaliaMinera.label" default="Regalia Minera" /></span>
					
						<span class="property-value" aria-labelledby="regaliaMinera-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="regaliaMinera"/></span>
					
				</li>
				</g:if>

            <h1 style="font-weight: bold">Retenciones</h1>
            <g:hiddenField name="retenciones" value="${liquidacionDeEstanoInstance?.retenciones}"/>
            <div style="width: 700px; margin-left: auto; margin-right: auto;"><table id="tablaRetenciones"></table></div>

                <g:if test="${liquidacionDeEstanoInstance?.valorNetoMineral}">
                    <li class="fieldcontain">
                        <span id="valorNetoMineral-label" class="property-label"><g:message code="liquidacionDeEstano.valorNetoMineral.label" default="Valor Neto Mineral" /></span>

                        <span class="property-value" aria-labelledby="valorNetoMineral-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="valorNetoMineral"/></span>

                    </li>
                </g:if>

                <g:if test="${liquidacionDeEstanoInstance?.valorNetoMineralEnBolivianos}">
                    <li class="fieldcontain">
                        <span id="valorNetoMineralEnBolivianos-label" class="property-label"><g:message code="liquidacionDeEstano.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" /></span>

                        <span class="property-value" aria-labelledby="valorNetoMineralEnBolivianos-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="valorNetoMineralEnBolivianos"/></span>

                    </li>
                </g:if>

                <g:if test="${liquidacionDeEstanoInstance?.bonoCalidad}">
                    <li class="fieldcontain">
                        <span id="bonoCalidad-label" class="property-label"><g:message code="liquidacionDeEstano.bonoCalidad.label" default="Bono Calidad" /></span>

                        <span class="property-value" aria-labelledby="bonoCalidad-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="bonoCalidad"/></span>

                    </li>
                </g:if>

                <g:if test="${liquidacionDeEstanoInstance?.bonoIncentivo}">
                    <li class="fieldcontain">
                        <span id="bonoIncentivo-label" class="property-label"><g:message code="liquidacionDeEstano.bonoIncentivo.label" default="Bono Incentivo" /></span>

                        <span class="property-value" aria-labelledby="bonoIncentivo-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="bonoIncentivo"/></span>

                    </li>
                </g:if>

                <g:if test="${liquidacionDeEstanoInstance?.valorDeCompra}">
                    <li class="fieldcontain">
                        <span id="valorDeCompra-label" class="property-label"><g:message code="liquidacionDeEstano.valorDeCompra.label" default="Valor De Compra" /></span>

                        <span class="property-value" aria-labelledby="valorDeCompra-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="valorDeCompra"/></span>

                    </li>
                </g:if>

                <g:if test="${liquidacionDeEstanoInstance?.totalRetenciones}">
				<li class="fieldcontain">
					<span id="totalRetenciones-label" class="property-label"><g:message code="liquidacionDeEstano.totalRetenciones.label" default="Total Retenciones" /></span>
					
						<span class="property-value" aria-labelledby="totalRetenciones-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="totalRetenciones"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.totalPagado}">
				<li class="fieldcontain">
					<span id="totalPagado-label" class="property-label"><g:message code="liquidacionDeEstano.totalPagado.label" default="Total Pagado" /></span>
					
						<span class="property-value" aria-labelledby="totalPagado-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="totalPagado"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.totalAnticiposContraEntrega}">
				<li class="fieldcontain">
					<span id="totalAnticiposContraEntrega-label" class="property-label"><g:message code="liquidacionDeEstano.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" /></span>
					
						<span class="property-value" aria-labelledby="totalAnticiposContraEntrega-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="totalAnticiposContraEntrega"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.totalAnticiposContraFuturaEntrega}">
				<li class="fieldcontain">
					<span id="totalAnticiposContraFuturaEntrega-label" class="property-label"><g:message code="liquidacionDeEstano.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" /></span>
					
						<span class="property-value" aria-labelledby="totalAnticiposContraFuturaEntrega-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="totalAnticiposContraFuturaEntrega"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.totalLiquidoPagable}">
				<li class="fieldcontain">
					<span id="totalLiquidoPagable-label" class="property-label"><g:message code="liquidacionDeEstano.totalLiquidoPagable.label" default="Total Liquido Pagable" /></span>
					
						<span class="property-value" aria-labelledby="totalLiquidoPagable-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="totalLiquidoPagable"/></span>
					
				</li>
				</g:if>

                <g:if test="${liquidacionDeEstanoInstance?.conjuntoEstano}">
                    <li class="fieldcontain">
                        <span id="conjuntoEstano-label" class="property-label"><g:message code="liquidacionDeEstano.conjuntoEstano.label" default="Conjunto de Estano" /></span>
    
                        <span class="property-value" aria-labelledby="conjuntoEstano-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="conjuntoEstano"/></span>
    
                    </li>
                </g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="liquidacionDeEstano.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeEstanoInstance?.motivoDeModificacion}">
				<li class="fieldcontain">
					<span id="motivoDeModificacion-label" class="property-label"><g:message code="liquidacionDeEstano.motivoDeModificacion.label" default="Motivo De Modificacion" /></span>
					
						<span class="property-value" aria-labelledby="motivoDeModificacion-label"><g:fieldValue bean="${liquidacionDeEstanoInstance}" field="motivoDeModificacion"/></span>
					
				</li>
				</g:if>

            <g:hiddenField name="liquidoPagable" value="${liquidacionDeEstanoInstance?.totalLiquidoPagable}" />
            <g:if test="${liquidacionDeEstanoInstance?.totalLiquidoPagable<0}">
                <h1 style="font-weight: bold">Enlace a Anticipo Contra Futura Entrega (Clic en enlace para desplegar formulario)</h1>
                <li class="fieldcontain">
                    <label style="font-size: 16px; font-weight: bold; color: red; width: 70%">
                        <g:link controller="anticipoContraFuturaEntrega" action="show" id="${org.socymet.anticipos.AnticipoContraFuturaEntrega.findByLiquidacionId(liquidacionDeEstanoInstance.id).id}" target="_blank" style="color: red"> ANTICIPO CONTRA FUTURA ENTREGA GENERADO!</g:link>
                    </label>
                </li>
            </g:if>
			
			</ol>

        <fieldset class="buttons">
            <div style="float: left">
                <g:form>
                    <g:hiddenField name="id" value="${liquidacionDeEstanoInstance?.id}" />
                    <g:if test="${liquidacionDeEstanoInstance.fechaDeCancelacion!=(new java.util.Date(84,5,14))&&liquidacionDeEstanoInstance.fechaDeCancelacion}">
                        <span id="observaciones-label" class="property-label" style="font-weight: bold; color: green">CANCELADO</span>
                    </g:if>
                    <g:if test="${liquidacionDeEstanoInstance.fechaDeCancelacion==(new java.util.Date(84,5,14))&&liquidacionDeEstanoInstance.fechaDeCancelacion}">
                        <span id="observaciones-label" class="property-label" style="font-weight: bold; color: red">SIN CANCELAR</span>
                    </g:if>
                    <g:link class="edit" action="edit" id="${liquidacionDeEstanoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </g:form>
            </div>
            <div style="float: right">
                <g:jasperReport controller="liquidacionDeEstano" action="crearReporte" jasper="liquidacion_estano" format="PDF" _format="PDF" name="LIQUIDACION-${liquidacionDeEstanoInstance.lote}">
                    <input type="hidden" name="LIQ_SN_ID" value="${liquidacionDeEstanoInstance.id}" />
                </g:jasperReport>
            </div>
        </fieldset>
		</div>
	</body>
</html>
