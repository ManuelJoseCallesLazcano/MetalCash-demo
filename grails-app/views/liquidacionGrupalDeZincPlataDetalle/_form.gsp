<%@ page import="org.socymet.liquidacion.LiquidacionGrupalDeZincPlataDetalle" %>



<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDeZincPlataDetalleInstance, field: 'millis', 'error')} required">
	<label for="millis">
		<g:message code="liquidacionGrupalDeZincPlataDetalle.millis.label" default="Millis" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="millis" value="${fieldValue(bean: liquidacionGrupalDeZincPlataDetalleInstance, field: 'millis')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDeZincPlataDetalleInstance, field: 'liquidacionDeZincPlata', 'error')} required">
	<label for="liquidacionDeZincPlata">
		<g:message code="liquidacionGrupalDeZincPlataDetalle.liquidacionDeZincPlata.label" default="Liquidacion De Zinc Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="liquidacionDeZincPlata" name="liquidacionDeZincPlata.id" from="${org.socymet.liquidacion.LiquidacionDeZincPlata.list()}" optionKey="id" required="" value="${liquidacionGrupalDeZincPlataDetalleInstance?.liquidacionDeZincPlata?.id}" class="many-to-one"/>
</div>

