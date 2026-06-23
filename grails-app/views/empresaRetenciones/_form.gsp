<%@ page import="org.socymet.proveedor.EmpresaRetenciones" %>



<div class="fieldcontain ${hasErrors(bean: empresaRetencionesInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="empresaRetenciones.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" required="" value="${empresaRetencionesInstance?.empresa?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: empresaRetencionesInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="empresaRetenciones.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${empresaRetencionesInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: empresaRetencionesInstance, field: 'tipoDeRetencion', 'error')} ">
	<label for="tipoDeRetencion">
		<g:message code="empresaRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" />
		
	</label>
	<g:select name="tipoDeRetencion" from="${['DE LEY','OTRA']}" value="${empresaRetencionesInstance?.tipoDeRetencion}" valueMessagePrefix="empresaRetenciones.tipoDeRetencion" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: empresaRetencionesInstance, field: 'cantidadDescuento', 'error')} required">
	<label for="cantidadDescuento">
		<g:message code="empresaRetenciones.cantidadDescuento.label" default="Cantidad Descuento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadDescuento" value="${fieldValue(bean: empresaRetencionesInstance, field: 'cantidadDescuento')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: empresaRetencionesInstance, field: 'unidadDeDescuento', 'error')} ">
	<label for="unidadDeDescuento">
		<g:message code="empresaRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" />
		
	</label>
	<g:select name="unidadDeDescuento" from="${['%','Bs']}" value="${empresaRetencionesInstance?.unidadDeDescuento}" valueMessagePrefix="empresaRetenciones.unidadDeDescuento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: empresaRetencionesInstance, field: 'asignacionDelDescuento', 'error')} ">
	<label for="asignacionDelDescuento">
		<g:message code="empresaRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" />
		
	</label>
	<g:select name="asignacionDelDescuento" from="${['VBV','VNV','TON. BRUTA','SACO','FIJO']}" value="${empresaRetencionesInstance?.asignacionDelDescuento}" valueMessagePrefix="empresaRetenciones.asignacionDelDescuento" noSelection="['': '']"/>
</div>

