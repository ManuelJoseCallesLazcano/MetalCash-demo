<%@ page import="org.socymet.cancelacion.LoteBonoTransporte" %>



<div class="fieldcontain ${hasErrors(bean: loteBonoTransporteInstance, field: 'pagoBonoTransporte', 'error')} required">
	<label for="pagoBonoTransporte">
		<g:message code="loteBonoTransporte.pagoBonoTransporte.label" default="Pago Bono Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="pagoBonoTransporte" name="pagoBonoTransporte.id" from="${org.socymet.cancelacion.PagoBonoTransporte.list()}" optionKey="id" required="" value="${loteBonoTransporteInstance?.pagoBonoTransporte?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoTransporteInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="loteBonoTransporte.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${loteBonoTransporteInstance?.lote}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoTransporteInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="loteBonoTransporte.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeRecepcion" precision="day"  value="${loteBonoTransporteInstance?.fechaDeRecepcion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoTransporteInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="loteBonoTransporte.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreEmpresa" required="" value="${loteBonoTransporteInstance?.nombreEmpresa}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoTransporteInstance, field: 'nombreCliente', 'error')} required">
	<label for="nombreCliente">
		<g:message code="loteBonoTransporte.nombreCliente.label" default="Nombre Cliente" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCliente" required="" value="${loteBonoTransporteInstance?.nombreCliente}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoTransporteInstance, field: 'kilosBrutos', 'error')} required">
	<label for="kilosBrutos">
		<g:message code="loteBonoTransporte.kilosBrutos.label" default="Kilos Brutos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosBrutos" value="${fieldValue(bean: loteBonoTransporteInstance, field: 'kilosBrutos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoTransporteInstance, field: 'leyPlomo', 'error')} required">
	<label for="leyPlomo">
		<g:message code="loteBonoTransporte.leyPlomo.label" default="Ley Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyPlomo" value="${fieldValue(bean: loteBonoTransporteInstance, field: 'leyPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoTransporteInstance, field: 'leyZinc', 'error')} required">
	<label for="leyZinc">
		<g:message code="loteBonoTransporte.leyZinc.label" default="Ley Zinc" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyZinc" value="${fieldValue(bean: loteBonoTransporteInstance, field: 'leyZinc')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: loteBonoTransporteInstance, field: 'bono', 'error')} required">
	<label for="bono">
		<g:message code="loteBonoTransporte.bono.label" default="Bono" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="bono" value="${fieldValue(bean: loteBonoTransporteInstance, field: 'bono')}" required="" inputmode="decimal"/>
</div>

