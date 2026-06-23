<%@ page import="org.socymet.liquidacion.LiquidacionGrupalDePlomoPlataDetalle" %>



<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDePlomoPlataDetalleInstance, field: 'millis', 'error')} required">
	<label for="millis">
		<g:message code="liquidacionGrupalDePlomoPlataDetalle.millis.label" default="Millis" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="millis" value="${fieldValue(bean: liquidacionGrupalDePlomoPlataDetalleInstance, field: 'millis')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDePlomoPlataDetalleInstance, field: 'liquidacionDePlomoPlata', 'error')} required">
	<label for="liquidacionDePlomoPlata">
		<g:message code="liquidacionGrupalDePlomoPlataDetalle.liquidacionDePlomoPlata.label" default="Liquidacion De Plomo Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="liquidacionDePlomoPlata" name="liquidacionDePlomoPlata.id" from="${org.socymet.liquidacion.LiquidacionDePlomoPlata.list()}" optionKey="id" required="" value="${liquidacionGrupalDePlomoPlataDetalleInstance?.liquidacionDePlomoPlata?.id}" class="many-to-one"/>
</div>

