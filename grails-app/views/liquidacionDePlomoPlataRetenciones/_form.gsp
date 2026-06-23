<%@ page import="org.socymet.liquidacion.LiquidacionDePlomoPlataRetenciones" %>



<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataRetencionesInstance, field: 'liquidacionDePlomoPlata', 'error')} required">
	<label for="liquidacionDePlomoPlata">
		<g:message code="liquidacionDePlomoPlataRetenciones.liquidacionDePlomoPlata.label" default="Liquidacion De Plomo Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="liquidacionDePlomoPlata" name="liquidacionDePlomoPlata.id" from="${org.socymet.liquidacion.LiquidacionDePlomoPlata.list()}" optionKey="id" required="" value="${liquidacionDePlomoPlataRetencionesInstance?.liquidacionDePlomoPlata?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataRetencionesInstance, field: 'codigo', 'error')} required">
	<label for="codigo">
		<g:message code="liquidacionDePlomoPlataRetenciones.codigo.label" default="Codigo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="codigo" type="number" value="${liquidacionDePlomoPlataRetencionesInstance.codigo}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataRetencionesInstance, field: 'cantidadDescuento', 'error')} required">
	<label for="cantidadDescuento">
		<g:message code="liquidacionDePlomoPlataRetenciones.cantidadDescuento.label" default="Cantidad Descuento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadDescuento" value="${fieldValue(bean: liquidacionDePlomoPlataRetencionesInstance, field: 'cantidadDescuento')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataRetencionesInstance, field: 'unidadDeDescuento', 'error')} ">
	<label for="unidadDeDescuento">
		<g:message code="liquidacionDePlomoPlataRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" />
		
	</label>
	<g:select name="unidadDeDescuento" from="${['%','Bs']}" value="${liquidacionDePlomoPlataRetencionesInstance?.unidadDeDescuento}" valueMessagePrefix="liquidacionDePlomoPlataRetenciones.unidadDeDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataRetencionesInstance, field: 'tipoDeRetencion', 'error')} ">
	<label for="tipoDeRetencion">
		<g:message code="liquidacionDePlomoPlataRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" />
		
	</label>
	<g:select name="tipoDeRetencion" from="${['DE LEY','OTRA']}" value="${liquidacionDePlomoPlataRetencionesInstance?.tipoDeRetencion}" valueMessagePrefix="liquidacionDePlomoPlataRetenciones.tipoDeRetencion" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataRetencionesInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="liquidacionDePlomoPlataRetenciones.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${liquidacionDePlomoPlataRetencionesInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataRetencionesInstance, field: 'asignacionDelDescuento', 'error')} ">
	<label for="asignacionDelDescuento">
		<g:message code="liquidacionDePlomoPlataRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" />
		
	</label>
	<g:select name="asignacionDelDescuento" from="${['VBV','VNV','TON. BRUTA','SACO','FIJO']}" value="${liquidacionDePlomoPlataRetencionesInstance?.asignacionDelDescuento}" valueMessagePrefix="liquidacionDePlomoPlataRetenciones.asignacionDelDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataRetencionesInstance, field: 'monto', 'error')} required">
	<label for="monto">
		<g:message code="liquidacionDePlomoPlataRetenciones.monto.label" default="Monto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="monto" value="${fieldValue(bean: liquidacionDePlomoPlataRetencionesInstance, field: 'monto')}" required="" inputmode="decimal"/>
</div>

