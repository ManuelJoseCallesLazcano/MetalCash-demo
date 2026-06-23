<%@ page import="org.socymet.cancelacion.LoteBonoProduccion" %>



<div class="fieldcontain ${hasErrors(bean: loteBonoProduccionInstance, field: 'pagoBonoProduccion', 'error')} required">
	<label for="pagoBonoProduccion">
		<g:message code="loteBonoProduccion.pagoBonoProduccion.label" default="Pago Bono Produccion" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="pagoBonoProduccion" name="pagoBonoProduccion.id" from="${org.socymet.cancelacion.PagoBonoProduccion.list()}" optionKey="id" required="" value="${loteBonoProduccionInstance?.pagoBonoProduccion?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoProduccionInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="loteBonoProduccion.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${loteBonoProduccionInstance?.lote}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoProduccionInstance, field: 'fechaDeLiquidacion', 'error')} required">
	<label for="fechaDeLiquidacion">
		<g:message code="loteBonoProduccion.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeLiquidacion" precision="day"  value="${loteBonoProduccionInstance?.fechaDeLiquidacion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoProduccionInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="loteBonoProduccion.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreEmpresa" required="" value="${loteBonoProduccionInstance?.nombreEmpresa}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoProduccionInstance, field: 'nombreCliente', 'error')} required">
	<label for="nombreCliente">
		<g:message code="loteBonoProduccion.nombreCliente.label" default="Nombre Cliente" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCliente" required="" value="${loteBonoProduccionInstance?.nombreCliente}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoProduccionInstance, field: 'kilosNetosSecos', 'error')} required">
	<label for="kilosNetosSecos">
		<g:message code="loteBonoProduccion.kilosNetosSecos.label" default="Kilos Netos Secos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosNetosSecos" value="${fieldValue(bean: loteBonoProduccionInstance, field: 'kilosNetosSecos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoProduccionInstance, field: 'totalLiquidoPagable', 'error')} required">
	<label for="totalLiquidoPagable">
		<g:message code="loteBonoProduccion.totalLiquidoPagable.label" default="Total Liquido Pagable" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalLiquidoPagable" value="${fieldValue(bean: loteBonoProduccionInstance, field: 'totalLiquidoPagable')}" required="" inputmode="decimal"/>
</div>

