<%@ page import="org.socymet.liquidacion.LiquidacionGrupalDeCobrePlataDetalle" %>



<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDeCobrePlataDetalleInstance, field: 'millis', 'error')} required">
	<label for="millis">
		<g:message code="liquidacionGrupalDeCobrePlataDetalle.millis.label" default="Millis" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="millis" value="${fieldValue(bean: liquidacionGrupalDeCobrePlataDetalleInstance, field: 'millis')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDeCobrePlataDetalleInstance, field: 'liquidacionDeCobrePlata', 'error')} required">
	<label for="liquidacionDeCobrePlata">
		<g:message code="liquidacionGrupalDeCobrePlataDetalle.liquidacionDeCobrePlata.label" default="Liquidacion De Cobre Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="liquidacionDeCobrePlata" name="liquidacionDeCobrePlata.id" from="${org.socymet.liquidacion.LiquidacionDeCobrePlata.list()}" optionKey="id" required="" value="${liquidacionGrupalDeCobrePlataDetalleInstance?.liquidacionDeCobrePlata?.id}" class="many-to-one"/>
</div>

