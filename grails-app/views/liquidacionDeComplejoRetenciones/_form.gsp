<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejoRetenciones" %>



<div class="fieldcontain ${hasErrors(bean: liquidacionDeComplejoRetencionesInstance, field: 'liquidacionDeComplejo', 'error')} required">
	<label for="liquidacionDeComplejo">
		<g:message code="liquidacionDeComplejoRetenciones.liquidacionDeComplejo.label" default="Liquidacion De Complejo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="liquidacionDeComplejo" name="liquidacionDeComplejo.id" from="${org.socymet.liquidacion.LiquidacionDeComplejo.list()}" optionKey="id" required="" value="${liquidacionDeComplejoRetencionesInstance?.liquidacionDeComplejo?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeComplejoRetencionesInstance, field: 'codigo', 'error')} required">
	<label for="codigo">
		<g:message code="liquidacionDeComplejoRetenciones.codigo.label" default="Codigo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="codigo" type="number" value="${liquidacionDeComplejoRetencionesInstance.codigo}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeComplejoRetencionesInstance, field: 'cantidadDescuento', 'error')} required">
	<label for="cantidadDescuento">
		<g:message code="liquidacionDeComplejoRetenciones.cantidadDescuento.label" default="Cantidad Descuento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadDescuento" value="${fieldValue(bean: liquidacionDeComplejoRetencionesInstance, field: 'cantidadDescuento')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeComplejoRetencionesInstance, field: 'unidadDeDescuento', 'error')} ">
	<label for="unidadDeDescuento">
		<g:message code="liquidacionDeComplejoRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" />
		
	</label>
	<g:select name="unidadDeDescuento" from="${['%','Bs']}" value="${liquidacionDeComplejoRetencionesInstance?.unidadDeDescuento}" valueMessagePrefix="liquidacionDeComplejoRetenciones.unidadDeDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeComplejoRetencionesInstance, field: 'tipoDeRetencion', 'error')} ">
	<label for="tipoDeRetencion">
		<g:message code="liquidacionDeComplejoRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" />
		
	</label>
	<g:select name="tipoDeRetencion" from="${['DE LEY','OTRA']}" value="${liquidacionDeComplejoRetencionesInstance?.tipoDeRetencion}" valueMessagePrefix="liquidacionDeComplejoRetenciones.tipoDeRetencion" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeComplejoRetencionesInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="liquidacionDeComplejoRetenciones.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${liquidacionDeComplejoRetencionesInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeComplejoRetencionesInstance, field: 'asignacionDelDescuento', 'error')} ">
	<label for="asignacionDelDescuento">
		<g:message code="liquidacionDeComplejoRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" />
		
	</label>
	<g:select name="asignacionDelDescuento" from="${['VBV','VNV','TON. BRUTA','SACO','FIJO']}" value="${liquidacionDeComplejoRetencionesInstance?.asignacionDelDescuento}" valueMessagePrefix="liquidacionDeComplejoRetenciones.asignacionDelDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeComplejoRetencionesInstance, field: 'monto', 'error')} required">
	<label for="monto">
		<g:message code="liquidacionDeComplejoRetenciones.monto.label" default="Monto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="monto" value="${fieldValue(bean: liquidacionDeComplejoRetencionesInstance, field: 'monto')}" required="" inputmode="decimal"/>
</div>

