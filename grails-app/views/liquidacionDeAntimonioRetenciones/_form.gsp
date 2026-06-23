<%@ page import="org.socymet.liquidacion.LiquidacionDeAntimonioRetenciones" %>



<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioRetencionesInstance, field: 'liquidacionDeAntimonio', 'error')} required">
	<label for="liquidacionDeAntimonio">
		<g:message code="liquidacionDeAntimonioRetenciones.liquidacionDeAntimonio.label" default="Liquidacion De Antimonio" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="liquidacionDeAntimonio" name="liquidacionDeAntimonio.id" from="${org.socymet.liquidacion.LiquidacionDeAntimonio.list()}" optionKey="id" required="" value="${liquidacionDeAntimonioRetencionesInstance?.liquidacionDeAntimonio?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioRetencionesInstance, field: 'codigo', 'error')} required">
	<label for="codigo">
		<g:message code="liquidacionDeAntimonioRetenciones.codigo.label" default="Codigo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="codigo" type="number" value="${liquidacionDeAntimonioRetencionesInstance.codigo}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioRetencionesInstance, field: 'cantidadDescuento', 'error')} required">
	<label for="cantidadDescuento">
		<g:message code="liquidacionDeAntimonioRetenciones.cantidadDescuento.label" default="Cantidad Descuento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadDescuento" value="${fieldValue(bean: liquidacionDeAntimonioRetencionesInstance, field: 'cantidadDescuento')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioRetencionesInstance, field: 'unidadDeDescuento', 'error')} ">
	<label for="unidadDeDescuento">
		<g:message code="liquidacionDeAntimonioRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" />
		
	</label>
	<g:select name="unidadDeDescuento" from="${['%','Bs']}" value="${liquidacionDeAntimonioRetencionesInstance?.unidadDeDescuento}" valueMessagePrefix="liquidacionDeAntimonioRetenciones.unidadDeDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioRetencionesInstance, field: 'tipoDeRetencion', 'error')} ">
	<label for="tipoDeRetencion">
		<g:message code="liquidacionDeAntimonioRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" />
		
	</label>
	<g:select name="tipoDeRetencion" from="${['DE LEY','OTRA']}" value="${liquidacionDeAntimonioRetencionesInstance?.tipoDeRetencion}" valueMessagePrefix="liquidacionDeAntimonioRetenciones.tipoDeRetencion" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioRetencionesInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="liquidacionDeAntimonioRetenciones.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${liquidacionDeAntimonioRetencionesInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioRetencionesInstance, field: 'asignacionDelDescuento', 'error')} ">
	<label for="asignacionDelDescuento">
		<g:message code="liquidacionDeAntimonioRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" />
		
	</label>
	<g:select name="asignacionDelDescuento" from="${['VBV','VNV','SACO','FIJO']}" value="${liquidacionDeAntimonioRetencionesInstance?.asignacionDelDescuento}" valueMessagePrefix="liquidacionDeAntimonioRetenciones.asignacionDelDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioRetencionesInstance, field: 'monto', 'error')} required">
	<label for="monto">
		<g:message code="liquidacionDeAntimonioRetenciones.monto.label" default="Monto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="monto" value="${fieldValue(bean: liquidacionDeAntimonioRetencionesInstance, field: 'monto')}" required="" inputmode="decimal"/>
</div>

