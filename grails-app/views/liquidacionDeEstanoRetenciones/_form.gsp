<%@ page import="org.socymet.liquidacion.LiquidacionDeEstanoRetenciones" %>



<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoRetencionesInstance, field: 'liquidacionDeEstano', 'error')} required">
	<label for="liquidacionDeEstano">
		<g:message code="liquidacionDeEstanoRetenciones.liquidacionDeEstano.label" default="Liquidacion De Estano" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="liquidacionDeEstano" name="liquidacionDeEstano.id" from="${org.socymet.liquidacion.LiquidacionDeEstano.list()}" optionKey="id" required="" value="${liquidacionDeEstanoRetencionesInstance?.liquidacionDeEstano?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoRetencionesInstance, field: 'codigo', 'error')} required">
	<label for="codigo">
		<g:message code="liquidacionDeEstanoRetenciones.codigo.label" default="Codigo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="codigo" type="number" value="${liquidacionDeEstanoRetencionesInstance.codigo}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoRetencionesInstance, field: 'cantidadDescuento', 'error')} required">
	<label for="cantidadDescuento">
		<g:message code="liquidacionDeEstanoRetenciones.cantidadDescuento.label" default="Cantidad Descuento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadDescuento" value="${fieldValue(bean: liquidacionDeEstanoRetencionesInstance, field: 'cantidadDescuento')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoRetencionesInstance, field: 'unidadDeDescuento', 'error')} ">
	<label for="unidadDeDescuento">
		<g:message code="liquidacionDeEstanoRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" />
		
	</label>
	<g:select name="unidadDeDescuento" from="${['%','Bs']}" value="${liquidacionDeEstanoRetencionesInstance?.unidadDeDescuento}" valueMessagePrefix="liquidacionDeEstanoRetenciones.unidadDeDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoRetencionesInstance, field: 'tipoDeRetencion', 'error')} ">
	<label for="tipoDeRetencion">
		<g:message code="liquidacionDeEstanoRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" />
		
	</label>
	<g:select name="tipoDeRetencion" from="${['DE LEY','OTRA']}" value="${liquidacionDeEstanoRetencionesInstance?.tipoDeRetencion}" valueMessagePrefix="liquidacionDeEstanoRetenciones.tipoDeRetencion" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoRetencionesInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="liquidacionDeEstanoRetenciones.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${liquidacionDeEstanoRetencionesInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoRetencionesInstance, field: 'asignacionDelDescuento', 'error')} ">
	<label for="asignacionDelDescuento">
		<g:message code="liquidacionDeEstanoRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" />
		
	</label>
	<g:select name="asignacionDelDescuento" from="${['VBV','VNV','SACO','FIJO']}" value="${liquidacionDeEstanoRetencionesInstance?.asignacionDelDescuento}" valueMessagePrefix="liquidacionDeEstanoRetenciones.asignacionDelDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoRetencionesInstance, field: 'monto', 'error')} required">
	<label for="monto">
		<g:message code="liquidacionDeEstanoRetenciones.monto.label" default="Monto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="monto" value="${fieldValue(bean: liquidacionDeEstanoRetencionesInstance, field: 'monto')}" required="" inputmode="decimal"/>
</div>

