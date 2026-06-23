<%@ page import="org.socymet.cancelacion.LoteBonoCalidad" %>



<div class="fieldcontain ${hasErrors(bean: loteBonoCalidadInstance, field: 'pagoBonoProduccion', 'error')} required">
	<label for="pagoBonoProduccion">
		<g:message code="loteBonoCalidad.pagoBonoProduccion.label" default="Pago Bono Produccion" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="pagoBonoProduccion" name="pagoBonoProduccion.id" from="${org.socymet.cancelacion.PagoBonoProduccion.list()}" optionKey="id" required="" value="${loteBonoCalidadInstance?.pagoBonoProduccion?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoCalidadInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="loteBonoCalidad.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${loteBonoCalidadInstance?.lote}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoCalidadInstance, field: 'fechaDeLiquidacion', 'error')} required">
	<label for="fechaDeLiquidacion">
		<g:message code="loteBonoCalidad.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeLiquidacion" precision="day"  value="${loteBonoCalidadInstance?.fechaDeLiquidacion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoCalidadInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="loteBonoCalidad.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreEmpresa" required="" value="${loteBonoCalidadInstance?.nombreEmpresa}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoCalidadInstance, field: 'nombreCliente', 'error')} required">
	<label for="nombreCliente">
		<g:message code="loteBonoCalidad.nombreCliente.label" default="Nombre Cliente" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCliente" required="" value="${loteBonoCalidadInstance?.nombreCliente}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoCalidadInstance, field: 'kilosNetosSecos', 'error')} required">
	<label for="kilosNetosSecos">
		<g:message code="loteBonoCalidad.kilosNetosSecos.label" default="Kilos Netos Secos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosNetosSecos" value="${fieldValue(bean: loteBonoCalidadInstance, field: 'kilosNetosSecos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoCalidadInstance, field: 'porcentajePlataFinal', 'error')} required">
	<label for="porcentajePlataFinal">
		<g:message code="loteBonoCalidad.porcentajePlataFinal.label" default="Porcentaje Plata Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajePlataFinal" value="${fieldValue(bean: loteBonoCalidadInstance, field: 'porcentajePlataFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoCalidadInstance, field: 'totalLiquidoPagable', 'error')} required">
	<label for="totalLiquidoPagable">
		<g:message code="loteBonoCalidad.totalLiquidoPagable.label" default="Total Liquido Pagable" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalLiquidoPagable" value="${fieldValue(bean: loteBonoCalidadInstance, field: 'totalLiquidoPagable')}" required="" inputmode="decimal"/>
</div>

