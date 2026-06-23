<%@ page import="org.socymet.cancelacion.DetallePagoTransporte" %>



<div class="fieldcontain ${hasErrors(bean: detallePagoTransporteInstance, field: 'pagoTransporte', 'error')} required">
	<label for="pagoTransporte">
		<g:message code="detallePagoTransporte.pagoTransporte.label" default="Pago Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="pagoTransporte" name="pagoTransporte.id" from="${org.socymet.cancelacion.PagoTransporte.list()}" optionKey="id" required="" value="${detallePagoTransporteInstance?.pagoTransporte?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoTransporteInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="detallePagoTransporte.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${detallePagoTransporteInstance?.lote}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoTransporteInstance, field: 'nombreChofer', 'error')} required">
	<label for="nombreChofer">
		<g:message code="detallePagoTransporte.nombreChofer.label" default="Nombre Chofer" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreChofer" required="" value="${detallePagoTransporteInstance?.nombreChofer}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoTransporteInstance, field: 'placaAutomovil', 'error')} required">
	<label for="placaAutomovil">
		<g:message code="detallePagoTransporte.placaAutomovil.label" default="Placa Automovil" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="placaAutomovil" required="" value="${detallePagoTransporteInstance?.placaAutomovil}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoTransporteInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="detallePagoTransporte.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="fechaDeRecepcion" required="" value="${detallePagoTransporteInstance?.fechaDeRecepcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoTransporteInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="detallePagoTransporte.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: detallePagoTransporteInstance, field: 'pesoBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoTransporteInstance, field: 'tipoDeMaterial', 'error')} required">
	<label for="tipoDeMaterial">
		<g:message code="detallePagoTransporte.tipoDeMaterial.label" default="Tipo De Material" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="tipoDeMaterial" required="" value="${detallePagoTransporteInstance?.tipoDeMaterial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoTransporteInstance, field: 'costoDeTransporte', 'error')} required">
	<label for="costoDeTransporte">
		<g:message code="detallePagoTransporte.costoDeTransporte.label" default="Costo De Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoDeTransporte" value="${fieldValue(bean: detallePagoTransporteInstance, field: 'costoDeTransporte')}" required="" inputmode="decimal"/>
</div>

