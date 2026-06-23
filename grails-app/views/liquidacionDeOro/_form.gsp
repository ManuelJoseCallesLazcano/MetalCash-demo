<%@ page import="org.socymet.liquidacion.LiquidacionDeOro" %>



<g:hiddenField name="vista" value="" />

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'lote', 'error')} required"
	 xmlns="http://www.w3.org/1999/html">
	<label for="lote">
		<g:message code="liquidacionDeOro.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${liquidacionDeOroInstance?.lote}"/>
	<g:hiddenField name="recepcionDeOro.id" value="${liquidacionDeOroInstance?.recepcionDeOro?.id}"/>
	<g:hiddenField name="empresa.id" value="${liquidacionDeOroInstance?.empresa?.id}"/>
	<g:hiddenField name="deposito.id" value="${liquidacionDeOroInstance?.deposito?.id}"/>
	<g:hiddenField name="conjuntoOro" value="${liquidacionDeOroInstance?.conjuntoOro}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'fechaDeLiquidacion', 'error')} required">
	<label for="fechaDeLiquidacion">
		<g:message code="liquidacionDeOro.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeLiquidacion" precision="day"  value="${liquidacionDeOroInstance?.fechaDeLiquidacion}"  />
</div>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<table>
	<tbody>
	<tr>
		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'nombreCliente', 'error')} required">
			<label for="nombreCliente" style="width: 40%">
				<g:message code="liquidacionDeOro.nombreCliente.label" default="Nombre Cliente" />
				<span class="required-indicator">*</span>
			</label>
			<g:textField name="nombreCliente" required="" value="${liquidacionDeOroInstance?.nombreCliente}"  class="amarillo" size="30" readonly="true"/>
		</td>

		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'nombreEmpresa', 'error')} required">
			<label for="nombreEmpresa" style="width: 40%">
				<g:message code="liquidacionDeOro.nombreEmpresa.label" default="Nombre Empresa" />
				<span class="required-indicator">*</span>
			</label>
			<g:hiddenField name="empresaId" value="${liquidacionDeOroInstance?.recepcionDeOro?.empresa?.id}"/>
			<g:textField name="nombreEmpresa" required="" value="${liquidacionDeOroInstance?.nombreEmpresa}" class="amarillo" size="30" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'nombreDeposito', 'error')} required">
			<label for="nombreDeposito" style="width: 40%">
				<g:message code="liquidacionDeOro.nombreDeposito.label" default="Nombre Deposito" />
				<span class="required-indicator">*</span>
			</label>
			<g:textField name="nombreDeposito" required="" value="${liquidacionDeOroInstance?.nombreDeposito}" class="amarillo" readonly="true"/>
		</td>

		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'fechaDeRecepcion', 'error')} required">
			<label for="fechaDeRecepcion" style="width: 40%">
				<g:message code="liquidacionDeOro.fechaDeRecepcion.label" default="Fecha De Recepcion" />
				<span class="required-indicator">*</span>
			</label>
			<g:textField name="fechaDeRecepcion" required="" value="${liquidacionDeOroInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'pesoBruto', 'error')} required">
			<label for="pesoBruto" style="width: 40%">
				<g:message code="liquidacionDeOro.pesoBruto.label" default="Peso Bruto" />
				<span class="required-indicator">*</span>
			</label>
			<g:field name="pesoBruto" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
		</td>
		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'cantidadDeSacos', 'error')} required">
			<label for="cantidadDeSacos" style="width: 40%">
				<g:message code="liquidacionDeOro.cantidadDeSacos.label" default="Cantidad De Sacos" />
				<span class="required-indicator">*</span>
			</label>
			<g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${liquidacionDeOroInstance?.cantidadDeSacos}"  class="amarillo" readonly="true"/>
		</td>
	</tr>
	</tbody>
</table>
<g:hiddenField name="estadoDelLote" value="${liquidacionDeOroInstance?.estadoDelLote}" />
<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
	<tbody>
	<tr>
		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'cotizacionDiariaDeOro', 'error')} required">
			<label for="cotizacionDiariaDeOro" style="width: 50%">
				<g:message code="liquidacionDeOro.cotizacionDiariaDeOro.label" default="Cot. Dia Oro" />
				<span class="required-indicator">*</span>
			</label>
			<g:field name="cotizacionDiariaDeOro" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'cotizacionDiariaDeOro')}" required="" size="10" class="amarillo" readonly="true"/></td>
		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'cotizacionQuincenalDeOro', 'error')} required">
			<label for="cotizacionQuincenalDeOro" style="width: 50%">
				<g:message code="liquidacionDeOro.cotizacionQuincenalDeOro.label" default="Cot. Quinc. Oro" />
				<span class="required-indicator">*</span>
			</label>
			<g:field name="cotizacionQuincenalDeOro" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'cotizacionQuincenalDeOro')}" required="" size="10" class="amarillo" readonly="true"/>
		</td>
		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'alicuotaDeOro', 'error')} required">
			<label for="alicuotaDeOro" style="width: 50%">
				<g:message code="liquidacionDeOro.alicuotaDeOro.label" default="Alicuota Oro" />
				<span class="required-indicator">*</span>
			</label>
			<g:field name="alicuotaDeOro" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'alicuotaDeOro')}" required="" size="10" class="amarillo" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td class="fieldcontain ${hasErrors( bean: liquidacionDeOroInstance, field: 'tipoDeCambioOficial', 'error')} required">
			<label for="tipoDeCambioOficial" style="width: 50%">
				<g:message code="liquidacionDeOro.tipoDeCambioOficial.label" default="T/C  Oficial" />
				<span class="required-indicator">*</span>
			</label>
			<g:field name="tipoDeCambioOficial" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'tipoDeCambioOficial')}" required="" size="10" class="amarillo" readonly="true"/>
		</td>

		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'tipoDeCambioComercial', 'error')} required">
			<label for="tipoDeCambioComercial" style="width: 50%">
				<g:message code="liquidacionDeOro.tipoDeCambioComercial.label" default="T/C  Comercial" />
				<span class="required-indicator">*</span>
			</label>
			<g:field name="tipoDeCambioComercial" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'tipoDeCambioComercial')}" required="" size="10" class="amarillo" readonly="true"/>
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
		<th style="width: 20%">LEY EMPRESA</th>
		<th style="width: 20%">LEY CLIENTE</th>
		<th style="width: 20%">LEY FINAL</th>
	</tr>
	</thead>
	<tbody>
	<tr style="display: none">
		<td class="fieldcontain required">
			<label for="porcentajeMermaPromexbol" style="width: 80%">
				<g:message code="controlCalidadOro.porcentajeMermaPromexbol.label" default="Merma" />
			</label>
		</td>
		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
			<g:field name="porcentajeMermaPromexbol" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'porcentajeMermaPromexbol')}" required="" inputmode="decimal"/>
		</td>

		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeMermaCliente', 'error')} required">
			<g:field name="porcentajeMermaCliente" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'porcentajeMermaCliente')}" required="" inputmode="decimal"/>
		</td>

		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeMermaFinal', 'error')} required">
			<g:field name="porcentajeMermaFinal" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'porcentajeMermaFinal')}" required="" inputmode="decimal"/>
		</td>
	</tr>

	<tr>
		<td class="fieldcontain required">
			<label for="porcentajeOroPromexbol" style="width: 80%">
				<g:message code="controlCalidadOro.porcentajeOroPromexbol.label" default="Oro" />
			</label>
		</td>
		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeOroPromexbol', 'error')} required">
			<g:field name="porcentajeOroPromexbol" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'porcentajeOroPromexbol')}" required="" inputmode="decimal"/>
		</td>

		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeOroCliente', 'error')} required">
			<g:field name="porcentajeOroCliente" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'porcentajeOroCliente')}" required="" inputmode="decimal"/>
		</td>

		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeOroFinal', 'error')} required">
			<g:field name="porcentajeOroFinal" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'porcentajeOroFinal')}" required=""  inputmode="decimal"/>
		</td>
	</tr>

	<tr>
		<td class="fieldcontain required">
			<label for="porcentajeHumedadPromexbol" style="width: 80%">
				<g:message code="controlCalidadOro.porcentajeHumedadPromexbol.label" default="Humedad" />
			</label>
		</td>
		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
			<g:field name="porcentajeHumedadPromexbol" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'porcentajeHumedadPromexbol')}" required="" inputmode="decimal"/>
		</td>

		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeHumedadCliente', 'error')} required">
			<g:field name="porcentajeHumedadCliente" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'porcentajeHumedadCliente')}" required="" inputmode="decimal"/>
		</td>

		<td class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeHumedadFinal', 'error')} required">
			<g:field name="porcentajeHumedadFinal" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'porcentajeHumedadFinal')}" required=""  inputmode="decimal"/>
		</td>
	</tr>
	</tbody>
</table>

<table style="width: 50%; margin-left: auto; margin-right: auto">
	<tbody>
	<tr>
		<td id="controlCalidadLink">

		</td>
		<td style="text-align: center;">
			<input id="copiarLeyes" type="button" value="COPIAR LEYES >>>" style="background-color: #033257; color: white; font-size: 12px;"/>
		</td>
	</tr>
	</tbody>
</table>

<h1 style="font-weight: bold">Valoraci&oacute;n del Lote</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'tablaPreciosOro', 'error')} required">
	<label for="tablaPreciosOro">
		<g:message code="liquidacionDeOro.tablaPreciosOro.label" default="Tabla Precios Oro" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="tablaPreciosOro" name="tablaPreciosOro.id" from="${org.socymet.cotizaciones.TablaPreciosOro.list()}" optionKey="id" required="" value="${liquidacionDeOroInstance?.tablaPreciosOro?.id}" class="many-to-one"/>

</div>

<br>

<div style="text-align: center;">
	<input id="valorar" type="button" value="VALORAR LOTE" style="background-color: #255b17; color: white; font-size: 16px;"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'margen', 'error')} required" style="display:none;">
    <label for="margen">
        <g:message code="liquidacionDeOro.margen.label" default="Valor Por Tonelada" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="margen" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'margen')}" required="" class="verde" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'valorOficialBruto', 'error')} required">
	<label for="valorOficialBruto">
		<g:message code="liquidacionDeOro.valorOficialBruto.label" default="Valor Oficial Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorOficialBruto" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'valorOficialBruto')}" required="" inputmode="decimal"/>

	<label for="regaliaMinera">
		<g:message code="liquidacionDeOro.regaliaMinera.label" default="Regalia Minera" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="regaliaMinera" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'regaliaMinera')}" required="" inputmode="decimal"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'regaliaMinera', 'error')} required">--}%
%{--<label for="regaliaMinera">--}%
%{--<g:message code="liquidacionDeOro.regaliaMinera.label" default="Regalia Minera" />--}%
%{--<span class="required-indicator">*</span>--}%
%{--</label>--}%
%{--<g:field name="regaliaMinera" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'regaliaMinera')}" required="" inputmode="decimal"/>--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'valorPorTonelada', 'error')} required">
	<label for="valorPorTonelada">
		<g:message code="liquidacionDeOro.valorPorTonelada.label" default="Valor Por Tonelada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorPorTonelada" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'valorPorTonelada')}" required="" class="verde" inputmode="decimal"/>

	<label for="valorNetoMineralEnBolivianos">
		<g:message code="liquidacionDeOro.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorNetoMineralEnBolivianos" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'valorNetoMineralEnBolivianos')}" required="" inputmode="decimal"/>
</div>

<g:hiddenField name="porcentajeRegalia" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'porcentajeRegalia')}"/>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'valorNetoMineral', 'error')} required" style="display:none;">
	<label for="valorNetoMineral">
		<g:message code="liquidacionDeOro.valorNetoMineral.label" default="Valor Neto Mineral" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorNetoMineral" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'valorNetoMineral')}" required="" inputmode="decimal"/>

</div>

%{--<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'valorNetoMineralEnBolivianos', 'error')} required">--}%
%{--<label for="valorNetoMineralEnBolivianos">--}%
%{--<g:message code="liquidacionDeOro.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" />--}%
%{--<span class="required-indicator">*</span>--}%
%{--</label>--}%
%{--<g:field name="valorNetoMineralEnBolivianos" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'valorNetoMineralEnBolivianos')}" required="" inputmode="decimal"/>--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'porcentajeBonificacion', 'error')} required" style="display: block">
	<label for="porcentajeBonificacion">
		<g:message code="liquidacionDeOro.porcentajeBonificacion.label" default="Porcentaje Bonificacion %" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeBonificacion" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'porcentajeBonificacion')}" required="" inputmode="decimal"/>

	<label for="bonoCalidad">
		<g:message code="liquidacionDeOro.bonoCalidad.label" default="Bono Calidad" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="bonoCalidad" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'bonoCalidad')}" required="" inputmode="decimal"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'bonoCalidad', 'error')} required" style="display: block">--}%
	%{--<label for="bonoCalidad">--}%
		%{--<g:message code="liquidacionDeOro.bonoCalidad.label" default="Bono Calidad" />--}%
		%{--<span class="required-indicator">*</span>--}%
	%{--</label>--}%
	%{--<g:field name="bonoCalidad" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'bonoCalidad')}" required="" inputmode="decimal"/>--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'bonoIncentivo', 'error')} required" style="display: none">
	<label for="bonoIncentivo">
		<g:message code="liquidacionDeOro.bonoIncentivo.label" default="Bono Incentivo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="bonoIncentivo" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'bonoIncentivo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'valorDeCompra', 'error')} required" style="display: block">
	<label for="valorDeCompra">
		<g:message code="liquidacionDeOro.valorDeCompra.label" default="Valor De Compra" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorDeCompra" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'valorDeCompra')}" required="" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Pesos y Valores Brutos parciales</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'kilosNetosHumedos', 'error')} required" style="display: none;">
	<label for="kilosNetosHumedos">
		<g:message code="liquidacionDeOro.kilosNetosHumedos.label" default="K. N. H." />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosNetosHumedos" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'kilosNetosHumedos')}" required="" size="10" inputmode="decimal"/>
</div>
<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'kilosNetosSecos', 'error')} required">
	<label for="kilosNetosSecos">
		<g:message code="liquidacionDeOro.kilosNetosSecos.label" default="K. N. S." />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosNetosSecos" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'kilosNetosSecos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'kilosFinosOro', 'error')} required">
	<label for="kilosFinosOro">
		<g:message code="liquidacionDeOro.kilosFinosOro.label" default="Kilos Finos Oro" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosFinosOro" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'kilosFinosOro')}" required="" inputmode="decimal"/>

</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'onzasTroyDeOro', 'error')} required">
	<label for="onzasTroyDeOro">
		<g:message code="liquidacionDeOro.onzasTroyDeOro.label" default="Onzas Troy De Oro" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="onzasTroyDeOro" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'onzasTroyDeOro')}" required="" inputmode="decimal"/>

</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'valorOficialBrutoDeOro', 'error')} required" style="display: none">
	<label for="valorOficialBrutoDeOro">
		<g:message code="liquidacionDeOro.valorOficialBrutoDeOro.label" default="Valor Oficial Bruto De Oro" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorOficialBrutoDeOro" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'valorOficialBrutoDeOro')}" required="" inputmode="decimal"/>

</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'valorOficialBrutoDeOroEnBolivianos', 'error')} required" style="display: none">
	<label for="valorOficialBrutoDeOroEnBolivianos">
		<g:message code="liquidacionDeOro.valorOficialBrutoDeOroEnBolivianos.label" default="Valor Oficial Bruto De Oro En Bolivianos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorOficialBrutoDeOroEnBolivianos" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'valorOficialBrutoDeOroEnBolivianos')}" required="" inputmode="decimal"/>

</div>

<g:hiddenField name="retenciones" value="${liquidacionDeOroInstance?.retenciones}" />

<h1 style="font-weight: bold">Retenciones</h1>

<div style="width: 700px; margin-left: auto; margin-right: auto;">
	<table id="tablaRetenciones">
	</table>
</div>

<div id="_botones" style="text-align: center">
	<br>
	<button id="eliminarRetencion" type="button">ELIMINAR RETENCION SELECCIONADA</button>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'totalRetenciones', 'error')} required">
	<label for="totalRetenciones">
		<g:message code="liquidacionDeOro.totalRetenciones.label" default="Total Retenciones" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalRetenciones" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'totalRetenciones')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'totalPagado', 'error')} required">
	<label for="totalPagado">
		<g:message code="liquidacionDeOro.totalPagado.label" default="Total Pagado" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalPagado" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'totalPagado')}" required="" inputmode="decimal"/>
</div>

<g:hiddenField name="cantidadAnticiposPorPagar" />

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'anticipoPorPagar', 'error')} required">
	<label for="anticipoPorPagar">
		<g:message code="liquidacionDeOro.anticipoPorPagar.label" default="Anticipo Por Pagar" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="anticipoPorPagar" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'anticipoPorPagar')}" required="" class="rojo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'totalAnticiposContraEntrega', 'error')} required">
	<label for="totalAnticiposContraEntrega">
		<g:message code="liquidacionDeOro.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalAnticiposContraEntrega" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'totalAnticiposContraEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'totalAnticiposContraFuturaEntrega', 'error')} required">
	<label for="totalAnticiposContraFuturaEntrega">
		<g:message code="liquidacionDeOro.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" />
		<span class="required-indicator">*</span>
	</label>
	<g:hiddenField name="adelantoPorLiquidacionProvisional" value="0"/>
	<g:field name="totalAnticiposContraFuturaEntrega" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'totalAnticiposContraFuturaEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'totalLiquidoPagable', 'error')} required">
	<label for="totalLiquidoPagable">
		<g:message code="liquidacionDeOro.totalLiquidoPagable.label" default="Total Liquido Pagable" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalLiquidoPagable" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'totalLiquidoPagable')}" required="" class="verde" style="font-weight: bold; font-size: 16px;" inputmode="decimal"/>
	<g:hiddenField name="totalLiquidoPagableOriginal" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'totalLiquidoPagableOriginal')}"/>
	<g:hiddenField name="diferenciaLiquidoPagable" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'diferenciaLiquidoPagable')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'bonoTransporteKilosNetosSecosTotal', 'error')} required">
	<label for="bonoTransporteKilosNetosSecosTotal">
		<g:message code="liquidacionDeOro.bonoTransporteKilosNetosSecosTotal.label" default="Bono Transporte Kilos Netos Secos Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="bonoTransporteKilosNetosSecosTotal" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'bonoTransporteKilosNetosSecosTotal')}" required="" class="amarillo" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'totalLiquidoPagableFinal', 'error')} required">
	<label for="totalLiquidoPagableFinal">
		<g:message code="liquidacionDeOro.totalLiquidoPagableFinal.label" default="Total Liquido Pagable Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalLiquidoPagableFinal" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'totalLiquidoPagableFinal')}" required="" class="verde" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'observaciones', 'error')} ">
	<label for="observaciones">
		<g:message code="liquidacionDeOro.observaciones.label" default="Observaciones" />

	</label>
	<g:textField name="observaciones" value="${liquidacionDeOroInstance?.observaciones}" size="90"/>
</div>

<g:hiddenField name="detalleLaboratorio1" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'detalleLaboratorio1')}"/>
<g:hiddenField name="costoLaboratorio1" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'costoLaboratorio1')}"/>
<g:hiddenField name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDeOroInstance, field: 'totalCostoLaboratorio')}"/>

<div id="_modificacion" style="display: none">
	<h1 style="font-weight: bold; font-size: 12px">Informacion de Modificacion del Registro</h1>

	<div class="fieldcontain ${hasErrors(bean: liquidacionDeOroInstance, field: 'motivoDeModificacion', 'error')} ">
		<label for="motivoDeModificacion">
			<g:message code="liquidacionDeOro.motivoDeModificacion.label" default="Motivo De Modificacion" />

		</label>
		<g:textField name="motivoDeModificacion" value="${liquidacionDeOroInstance?.motivoDeModificacion}" size="90"/>
	</div>
</div>

<div style="display:none;" class="nav_up" id="nav_up"></div>
<div style="display:none;" class="nav_down" id="nav_down"></div>