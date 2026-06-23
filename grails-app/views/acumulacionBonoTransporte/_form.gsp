<%@ page import="org.socymet.cancelacion.AcumulacionBonoTransporte" %>



<div class="fieldcontain ${hasErrors(bean: acumulacionBonoTransporteInstance, field: 'pagoBonoTransporte', 'error')} required">
	<label for="pagoBonoTransporte">
		<g:message code="acumulacionBonoTransporte.pagoBonoTransporte.label" default="Pago Bono Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="pagoBonoTransporte" name="pagoBonoTransporte.id" from="${org.socymet.cancelacion.PagoBonoTransporte.list()}" optionKey="id" required="" value="${acumulacionBonoTransporteInstance?.pagoBonoTransporte?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoTransporteInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="acumulacionBonoTransporte.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${acumulacionBonoTransporteInstance?.fecha}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoTransporteInstance, field: 'automovil', 'error')} ">
	<label for="automovil">
		<g:message code="acumulacionBonoTransporte.automovil.label" default="Automovil" />
		
	</label>
	<g:select id="automovil" name="automovil.id" from="${org.socymet.proveedor.Automovil.list()}" optionKey="id" value="${acumulacionBonoTransporteInstance?.automovil?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoTransporteInstance, field: 'cantidadAcumulada', 'error')} required">
	<label for="cantidadAcumulada">
		<g:message code="acumulacionBonoTransporte.cantidadAcumulada.label" default="Cantidad Acumulada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadAcumulada" value="${fieldValue(bean: acumulacionBonoTransporteInstance, field: 'cantidadAcumulada')}" required="" inputmode="decimal"/>
</div>

