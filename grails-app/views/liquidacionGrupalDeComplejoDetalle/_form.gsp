<%@ page import="org.socymet.liquidacion.LiquidacionGrupalDeComplejoDetalle" %>



<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDeComplejoDetalleInstance, field: 'millis', 'error')} required">
	<label for="millis">
		<g:message code="liquidacionGrupalDeComplejoDetalle.millis.label" default="Millis" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="millis" value="${fieldValue(bean: liquidacionGrupalDeComplejoDetalleInstance, field: 'millis')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDeComplejoDetalleInstance, field: 'liquidacionDeComplejo', 'error')} required">
	<label for="liquidacionDeComplejo">
		<g:message code="liquidacionGrupalDeComplejoDetalle.liquidacionDeComplejo.label" default="Liquidacion De Complejo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="liquidacionDeComplejo" name="liquidacionDeComplejo.id" from="${org.socymet.liquidacion.LiquidacionDeComplejo.list()}" optionKey="id" required="" value="${liquidacionGrupalDeComplejoDetalleInstance?.liquidacionDeComplejo?.id}" class="many-to-one"/>
</div>

