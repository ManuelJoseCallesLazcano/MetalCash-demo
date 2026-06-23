<%@ page import="org.socymet.liquidacion.LiquidacionDePlataRetenciones" %>



<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataRetencionesInstance, field: 'liquidacionDePlata', 'error')} required">
	<label for="liquidacionDePlata">
		<g:message code="liquidacionDePlataRetenciones.liquidacionDePlata.label" default="Liquidacion De Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="liquidacionDePlata" name="liquidacionDePlata.id" from="${org.socymet.liquidacion.LiquidacionDePlata.list()}" optionKey="id" required="" value="${liquidacionDePlataRetencionesInstance?.liquidacionDePlata?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataRetencionesInstance, field: 'codigo', 'error')} required">
	<label for="codigo">
		<g:message code="liquidacionDePlataRetenciones.codigo.label" default="Codigo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="codigo" type="number" value="${liquidacionDePlataRetencionesInstance.codigo}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataRetencionesInstance, field: 'cantidadDescuento', 'error')} required">
	<label for="cantidadDescuento">
		<g:message code="liquidacionDePlataRetenciones.cantidadDescuento.label" default="Cantidad Descuento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadDescuento" value="${fieldValue(bean: liquidacionDePlataRetencionesInstance, field: 'cantidadDescuento')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataRetencionesInstance, field: 'unidadDeDescuento', 'error')} ">
	<label for="unidadDeDescuento">
		<g:message code="liquidacionDePlataRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" />
		
	</label>
	<g:select name="unidadDeDescuento" from="${['%','Bs']}" value="${liquidacionDePlataRetencionesInstance?.unidadDeDescuento}" valueMessagePrefix="liquidacionDePlataRetenciones.unidadDeDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataRetencionesInstance, field: 'tipoDeRetencion', 'error')} ">
	<label for="tipoDeRetencion">
		<g:message code="liquidacionDePlataRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" />
		
	</label>
	<g:select name="tipoDeRetencion" from="${['DE LEY','OTRA']}" value="${liquidacionDePlataRetencionesInstance?.tipoDeRetencion}" valueMessagePrefix="liquidacionDePlataRetenciones.tipoDeRetencion" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataRetencionesInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="liquidacionDePlataRetenciones.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${liquidacionDePlataRetencionesInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataRetencionesInstance, field: 'asignacionDelDescuento', 'error')} ">
	<label for="asignacionDelDescuento">
		<g:message code="liquidacionDePlataRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" />
		
	</label>
	<g:select name="asignacionDelDescuento" from="${['VBV','VNV','SACO','FIJO']}" value="${liquidacionDePlataRetencionesInstance?.asignacionDelDescuento}" valueMessagePrefix="liquidacionDePlataRetenciones.asignacionDelDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataRetencionesInstance, field: 'monto', 'error')} required">
	<label for="monto">
		<g:message code="liquidacionDePlataRetenciones.monto.label" default="Monto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="monto" value="${fieldValue(bean: liquidacionDePlataRetencionesInstance, field: 'monto')}" required="" inputmode="decimal"/>
</div>

