<%@ page import="org.socymet.org.socymet.reportes.CompositoDeLotesDetalle" %>



<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'reporteCompositoDeLotes', 'error')} required">
	<label for="reporteCompositoDeLotes">
		<g:message code="compositoDeLotesDetalle.reporteCompositoDeLotes.label" default="Reporte Composito De Lotes" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="reporteCompositoDeLotes" name="reporteCompositoDeLotes.id" from="${org.socymet.org.socymet.reportes.ReporteCompositoDeLotes.list()}" optionKey="id" required="" value="${compositoDeLotesDetalleInstance?.reporteCompositoDeLotes?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="compositoDeLotesDetalle.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${compositoDeLotesDetalleInstance?.lote}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'recepcionId', 'error')} required">
	<label for="recepcionId">
		<g:message code="compositoDeLotesDetalle.recepcionId.label" default="Recepcion Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="recepcionId" type="number" value="${compositoDeLotesDetalleInstance.recepcionId}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'liquidacionId', 'error')} required">
	<label for="liquidacionId">
		<g:message code="compositoDeLotesDetalle.liquidacionId.label" default="Liquidacion Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="liquidacionId" type="number" value="${compositoDeLotesDetalleInstance.liquidacionId}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="compositoDeLotesDetalle.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreEmpresa" required="" value="${compositoDeLotesDetalleInstance?.nombreEmpresa}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="compositoDeLotesDetalle.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeRecepcion" precision="day"  value="${compositoDeLotesDetalleInstance?.fechaDeRecepcion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="compositoDeLotesDetalle.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'pesoBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'kilosNetosSecos', 'error')} required">
	<label for="kilosNetosSecos">
		<g:message code="compositoDeLotesDetalle.kilosNetosSecos.label" default="Kilos Netos Secos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosNetosSecos" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'kilosNetosSecos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'porcentajeZincFinal', 'error')} required">
	<label for="porcentajeZincFinal">
		<g:message code="compositoDeLotesDetalle.porcentajeZincFinal.label" default="Porcentaje Zinc Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeZincFinal" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'porcentajeZincFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'porcentajePlomoFinal', 'error')} required">
	<label for="porcentajePlomoFinal">
		<g:message code="compositoDeLotesDetalle.porcentajePlomoFinal.label" default="Porcentaje Plomo Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajePlomoFinal" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'porcentajePlomoFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'porcentajePlataFinal', 'error')} required">
	<label for="porcentajePlataFinal">
		<g:message code="compositoDeLotesDetalle.porcentajePlataFinal.label" default="Porcentaje Plata Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajePlataFinal" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'porcentajePlataFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'kilosFinosZinc', 'error')} required">
	<label for="kilosFinosZinc">
		<g:message code="compositoDeLotesDetalle.kilosFinosZinc.label" default="Kilos Finos Zinc" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosFinosZinc" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'kilosFinosZinc')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'kilosFinosPlomo', 'error')} required">
	<label for="kilosFinosPlomo">
		<g:message code="compositoDeLotesDetalle.kilosFinosPlomo.label" default="Kilos Finos Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosFinosPlomo" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'kilosFinosPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'kilosFinosPlata', 'error')} required">
	<label for="kilosFinosPlata">
		<g:message code="compositoDeLotesDetalle.kilosFinosPlata.label" default="Kilos Finos Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosFinosPlata" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'kilosFinosPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'valorNetoMineralEnBolivianos', 'error')} required">
	<label for="valorNetoMineralEnBolivianos">
		<g:message code="compositoDeLotesDetalle.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorNetoMineralEnBolivianos" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'valorNetoMineralEnBolivianos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'costoDeTransporte', 'error')} required">
	<label for="costoDeTransporte">
		<g:message code="compositoDeLotesDetalle.costoDeTransporte.label" default="Costo De Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoDeTransporte" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'costoDeTransporte')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'costoManipuleo', 'error')} required">
	<label for="costoManipuleo">
		<g:message code="compositoDeLotesDetalle.costoManipuleo.label" default="Costo Manipuleo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoManipuleo" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'costoManipuleo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'bonos', 'error')} required">
	<label for="bonos">
		<g:message code="compositoDeLotesDetalle.bonos.label" default="Bonos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="bonos" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'bonos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: compositoDeLotesDetalleInstance, field: 'valorDeCompra', 'error')} required">
	<label for="valorDeCompra">
		<g:message code="compositoDeLotesDetalle.valorDeCompra.label" default="Valor De Compra" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorDeCompra" value="${fieldValue(bean: compositoDeLotesDetalleInstance, field: 'valorDeCompra')}" required="" inputmode="decimal"/>
</div>

