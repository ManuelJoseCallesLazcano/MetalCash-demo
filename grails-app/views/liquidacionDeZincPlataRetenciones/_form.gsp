<%@ page import="org.socymet.liquidacion.LiquidacionDeZincPlataRetenciones" %>



<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataRetencionesInstance, field: 'liquidacionDeZincPlata', 'error')} required">
	<label for="liquidacionDeZincPlata">
		<g:message code="liquidacionDeZincPlataRetenciones.liquidacionDeZincPlata.label" default="Liquidacion De Zinc Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="liquidacionDeZincPlata" name="liquidacionDeZincPlata.id" from="${org.socymet.liquidacion.LiquidacionDeZincPlata.list()}" optionKey="id" required="" value="${liquidacionDeZincPlataRetencionesInstance?.liquidacionDeZincPlata?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataRetencionesInstance, field: 'codigo', 'error')} required">
	<label for="codigo">
		<g:message code="liquidacionDeZincPlataRetenciones.codigo.label" default="Codigo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="codigo" type="number" value="${liquidacionDeZincPlataRetencionesInstance.codigo}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataRetencionesInstance, field: 'cantidadDescuento', 'error')} required">
	<label for="cantidadDescuento">
		<g:message code="liquidacionDeZincPlataRetenciones.cantidadDescuento.label" default="Cantidad Descuento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadDescuento" value="${fieldValue(bean: liquidacionDeZincPlataRetencionesInstance, field: 'cantidadDescuento')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataRetencionesInstance, field: 'unidadDeDescuento', 'error')} ">
	<label for="unidadDeDescuento">
		<g:message code="liquidacionDeZincPlataRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" />
		
	</label>
	<g:select name="unidadDeDescuento" from="${['%','Bs']}" value="${liquidacionDeZincPlataRetencionesInstance?.unidadDeDescuento}" valueMessagePrefix="liquidacionDeZincPlataRetenciones.unidadDeDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataRetencionesInstance, field: 'tipoDeRetencion', 'error')} ">
	<label for="tipoDeRetencion">
		<g:message code="liquidacionDeZincPlataRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" />
		
	</label>
	<g:select name="tipoDeRetencion" from="${['DE LEY','OTRA']}" value="${liquidacionDeZincPlataRetencionesInstance?.tipoDeRetencion}" valueMessagePrefix="liquidacionDeZincPlataRetenciones.tipoDeRetencion" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataRetencionesInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="liquidacionDeZincPlataRetenciones.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${liquidacionDeZincPlataRetencionesInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataRetencionesInstance, field: 'asignacionDelDescuento', 'error')} ">
	<label for="asignacionDelDescuento">
		<g:message code="liquidacionDeZincPlataRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" />
		
	</label>
	<g:select name="asignacionDelDescuento" from="${['VBV','VNV','TON. BRUTA','SACO','FIJO']}" value="${liquidacionDeZincPlataRetencionesInstance?.asignacionDelDescuento}" valueMessagePrefix="liquidacionDeZincPlataRetenciones.asignacionDelDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataRetencionesInstance, field: 'monto', 'error')} required">
	<label for="monto">
		<g:message code="liquidacionDeZincPlataRetenciones.monto.label" default="Monto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="monto" value="${fieldValue(bean: liquidacionDeZincPlataRetencionesInstance, field: 'monto')}" required="" inputmode="decimal"/>
</div>

