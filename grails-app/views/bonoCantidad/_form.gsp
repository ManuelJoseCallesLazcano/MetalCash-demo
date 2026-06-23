<%@ page import="org.socymet.proveedor.bonos.BonoCantidad" %>



<div class="fieldcontain ${hasErrors(bean: bonoCantidadInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="bonoCantidad.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" required="" value="${bonoCantidadInstance?.empresa?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoCantidadInstance, field: 'elemento', 'error')} required">
	<label for="elemento">
		<g:message code="bonoCantidad.elemento.label" default="Elemento" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="elemento" required="" value="${bonoCantidadInstance?.elemento}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoCantidadInstance, field: 'simboloElemento', 'error')} required">
	<label for="simboloElemento">
		<g:message code="bonoCantidad.simboloElemento.label" default="Simbolo Elemento" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="simboloElemento" required="" value="${bonoCantidadInstance?.simboloElemento}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoCantidadInstance, field: 'cantidadMinima', 'error')} required">
	<label for="cantidadMinima">
		<g:message code="bonoCantidad.cantidadMinima.label" default="Cantidad Minima" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadMinima" value="${fieldValue(bean: bonoCantidadInstance, field: 'cantidadMinima')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoCantidadInstance, field: 'cantidadMaxima', 'error')} required">
	<label for="cantidadMaxima">
		<g:message code="bonoCantidad.cantidadMaxima.label" default="Cantidad Maxima" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadMaxima" value="${fieldValue(bean: bonoCantidadInstance, field: 'cantidadMaxima')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoCantidadInstance, field: 'bono', 'error')} required">
	<label for="bono">
		<g:message code="bonoCantidad.bono.label" default="Bono" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="bono" value="${fieldValue(bean: bonoCantidadInstance, field: 'bono')}" required="" inputmode="decimal"/>
</div>

