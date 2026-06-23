<%@ page import="org.socymet.liquidacion.LiquidacionDeCobrePlataRetenciones" %>



<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataRetencionesInstance, field: 'liquidacionDeCobrePlata', 'error')} required">
	<label for="liquidacionDeCobrePlata">
		<g:message code="liquidacionDeCobrePlataRetenciones.liquidacionDeCobrePlata.label" default="Liquidacion De Cobre Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="liquidacionDeCobrePlata" name="liquidacionDeCobrePlata.id" from="${org.socymet.liquidacion.LiquidacionDeCobrePlata.list()}" optionKey="id" required="" value="${liquidacionDeCobrePlataRetencionesInstance?.liquidacionDeCobrePlata?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataRetencionesInstance, field: 'codigo', 'error')} required">
	<label for="codigo">
		<g:message code="liquidacionDeCobrePlataRetenciones.codigo.label" default="Codigo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="codigo" type="number" value="${liquidacionDeCobrePlataRetencionesInstance.codigo}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataRetencionesInstance, field: 'cantidadDescuento', 'error')} required">
	<label for="cantidadDescuento">
		<g:message code="liquidacionDeCobrePlataRetenciones.cantidadDescuento.label" default="Cantidad Descuento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadDescuento" value="${fieldValue(bean: liquidacionDeCobrePlataRetencionesInstance, field: 'cantidadDescuento')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataRetencionesInstance, field: 'unidadDeDescuento', 'error')} ">
	<label for="unidadDeDescuento">
		<g:message code="liquidacionDeCobrePlataRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" />
		
	</label>
	<g:select name="unidadDeDescuento" from="${['%','Bs']}" value="${liquidacionDeCobrePlataRetencionesInstance?.unidadDeDescuento}" valueMessagePrefix="liquidacionDeCobrePlataRetenciones.unidadDeDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataRetencionesInstance, field: 'tipoDeRetencion', 'error')} ">
	<label for="tipoDeRetencion">
		<g:message code="liquidacionDeCobrePlataRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" />
		
	</label>
	<g:select name="tipoDeRetencion" from="${['DE LEY','OTRA']}" value="${liquidacionDeCobrePlataRetencionesInstance?.tipoDeRetencion}" valueMessagePrefix="liquidacionDeCobrePlataRetenciones.tipoDeRetencion" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataRetencionesInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="liquidacionDeCobrePlataRetenciones.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${liquidacionDeCobrePlataRetencionesInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataRetencionesInstance, field: 'asignacionDelDescuento', 'error')} ">
	<label for="asignacionDelDescuento">
		<g:message code="liquidacionDeCobrePlataRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" />
		
	</label>
	<g:select name="asignacionDelDescuento" from="${['VBV','VNV','TON. BRUTA','SACO','FIJO']}" value="${liquidacionDeCobrePlataRetencionesInstance?.asignacionDelDescuento}" valueMessagePrefix="liquidacionDeCobrePlataRetenciones.asignacionDelDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataRetencionesInstance, field: 'monto', 'error')} required">
	<label for="monto">
		<g:message code="liquidacionDeCobrePlataRetenciones.monto.label" default="Monto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="monto" value="${fieldValue(bean: liquidacionDeCobrePlataRetencionesInstance, field: 'monto')}" required="" inputmode="decimal"/>
</div>

