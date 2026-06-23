<%@ page import="org.socymet.liquidacion.LiquidacionDeWolfranRetenciones" %>



<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranRetencionesInstance, field: 'liquidacionDeWolfran', 'error')} required">
	<label for="liquidacionDeWolfran">
		<g:message code="liquidacionDeWolfranRetenciones.liquidacionDeWolfran.label" default="Liquidacion De Wolfran" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="liquidacionDeWolfran" name="liquidacionDeWolfran.id" from="${org.socymet.liquidacion.LiquidacionDeWolfran.list()}" optionKey="id" required="" value="${liquidacionDeWolfranRetencionesInstance?.liquidacionDeWolfran?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranRetencionesInstance, field: 'codigo', 'error')} required">
	<label for="codigo">
		<g:message code="liquidacionDeWolfranRetenciones.codigo.label" default="Codigo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="codigo" type="number" value="${liquidacionDeWolfranRetencionesInstance.codigo}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranRetencionesInstance, field: 'cantidadDescuento', 'error')} required">
	<label for="cantidadDescuento">
		<g:message code="liquidacionDeWolfranRetenciones.cantidadDescuento.label" default="Cantidad Descuento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadDescuento" value="${fieldValue(bean: liquidacionDeWolfranRetencionesInstance, field: 'cantidadDescuento')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranRetencionesInstance, field: 'unidadDeDescuento', 'error')} ">
	<label for="unidadDeDescuento">
		<g:message code="liquidacionDeWolfranRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" />
		
	</label>
	<g:select name="unidadDeDescuento" from="${['%','Bs']}" value="${liquidacionDeWolfranRetencionesInstance?.unidadDeDescuento}" valueMessagePrefix="liquidacionDeWolfranRetenciones.unidadDeDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranRetencionesInstance, field: 'tipoDeRetencion', 'error')} ">
	<label for="tipoDeRetencion">
		<g:message code="liquidacionDeWolfranRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" />
		
	</label>
	<g:select name="tipoDeRetencion" from="${['DE LEY','OTRA']}" value="${liquidacionDeWolfranRetencionesInstance?.tipoDeRetencion}" valueMessagePrefix="liquidacionDeWolfranRetenciones.tipoDeRetencion" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranRetencionesInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="liquidacionDeWolfranRetenciones.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${liquidacionDeWolfranRetencionesInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranRetencionesInstance, field: 'asignacionDelDescuento', 'error')} ">
	<label for="asignacionDelDescuento">
		<g:message code="liquidacionDeWolfranRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" />
		
	</label>
	<g:select name="asignacionDelDescuento" from="${['VBV','VNV','SACO','FIJO']}" value="${liquidacionDeWolfranRetencionesInstance?.asignacionDelDescuento}" valueMessagePrefix="liquidacionDeWolfranRetenciones.asignacionDelDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranRetencionesInstance, field: 'monto', 'error')} required">
	<label for="monto">
		<g:message code="liquidacionDeWolfranRetenciones.monto.label" default="Monto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="monto" value="${fieldValue(bean: liquidacionDeWolfranRetencionesInstance, field: 'monto')}" required="" inputmode="decimal"/>
</div>

