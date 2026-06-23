<%@ page import="org.socymet.liquidacion.RetencionPorPagarComplejo" %>



<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'codigo', 'error')} required">
	<label for="codigo">
		<g:message code="retencionPorPagarComplejo.codigo.label" default="Codigo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="codigo" type="number" value="${retencionPorPagarComplejoInstance.codigo}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'cantidadDescuento', 'error')} required">
	<label for="cantidadDescuento">
		<g:message code="retencionPorPagarComplejo.cantidadDescuento.label" default="Cantidad Descuento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadDescuento" value="${fieldValue(bean: retencionPorPagarComplejoInstance, field: 'cantidadDescuento')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'unidadDeDescuento', 'error')} required">
	<label for="unidadDeDescuento">
		<g:message code="retencionPorPagarComplejo.unidadDeDescuento.label" default="Unidad De Descuento" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="unidadDeDescuento" required="" value="${retencionPorPagarComplejoInstance?.unidadDeDescuento}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'tipoDeRetencion', 'error')} required">
	<label for="tipoDeRetencion">
		<g:message code="retencionPorPagarComplejo.tipoDeRetencion.label" default="Tipo De Retencion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="tipoDeRetencion" required="" value="${retencionPorPagarComplejoInstance?.tipoDeRetencion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="retencionPorPagarComplejo.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${retencionPorPagarComplejoInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'asignacionDelDescuento', 'error')} required">
	<label for="asignacionDelDescuento">
		<g:message code="retencionPorPagarComplejo.asignacionDelDescuento.label" default="Asignacion Del Descuento" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="asignacionDelDescuento" required="" value="${retencionPorPagarComplejoInstance?.asignacionDelDescuento}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'monto', 'error')} required">
	<label for="monto">
		<g:message code="retencionPorPagarComplejo.monto.label" default="Monto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="monto" value="${fieldValue(bean: retencionPorPagarComplejoInstance, field: 'monto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="retencionPorPagarComplejo.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${retencionPorPagarComplejoInstance?.lote}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'recepcionDeComplejo', 'error')} required">
	<label for="recepcionDeComplejo">
		<g:message code="retencionPorPagarComplejo.recepcionDeComplejo.label" default="Recepcion De Complejo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="recepcionDeComplejo" name="recepcionDeComplejo.id" from="${org.socymet.recepcion.RecepcionDeComplejo.list()}" optionKey="id" required="" value="${retencionPorPagarComplejoInstance?.recepcionDeComplejo?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'tipoDeMineral', 'error')} required">
	<label for="tipoDeMineral">
		<g:message code="retencionPorPagarComplejo.tipoDeMineral.label" default="Tipo De Mineral" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="tipoDeMineral" required="" value="${retencionPorPagarComplejoInstance?.tipoDeMineral}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="retencionPorPagarComplejo.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" required="" value="${retencionPorPagarComplejoInstance?.empresa?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'fechaDeRegistro', 'error')} required">
	<label for="fechaDeRegistro">
		<g:message code="retencionPorPagarComplejo.fechaDeRegistro.label" default="Fecha De Registro" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeRegistro" precision="day"  value="${retencionPorPagarComplejoInstance?.fechaDeRegistro}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'pagado', 'error')} required">
	<label for="pagado">
		<g:message code="retencionPorPagarComplejo.pagado.label" default="Pagado" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="pagado" required="" value="${retencionPorPagarComplejoInstance?.pagado}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'kilosNetosSecos', 'error')} required">
	<label for="kilosNetosSecos">
		<g:message code="retencionPorPagarComplejo.kilosNetosSecos.label" default="Kilos Netos Secos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosNetosSecos" value="${fieldValue(bean: retencionPorPagarComplejoInstance, field: 'kilosNetosSecos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: retencionPorPagarComplejoInstance, field: 'valorOficialNeto', 'error')} required">
	<label for="valorOficialNeto">
		<g:message code="retencionPorPagarComplejo.valorOficialNeto.label" default="Valor Oficial Neto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorOficialNeto" value="${fieldValue(bean: retencionPorPagarComplejoInstance, field: 'valorOficialNeto')}" required="" inputmode="decimal"/>
</div>

