<%@ page import="org.socymet.proveedor.bonos.BonoCalidad" %>



<div class="fieldcontain ${hasErrors(bean: bonoCalidadInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="bonoCalidad.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" required="" value="${bonoCalidadInstance?.empresa?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoCalidadInstance, field: 'elemento', 'error')} required">
	<label for="elemento">
		<g:message code="bonoCalidad.elemento.label" default="Elemento" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="elemento" required="" value="${bonoCalidadInstance?.elemento}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoCalidadInstance, field: 'simboloElemento', 'error')} required">
	<label for="simboloElemento">
		<g:message code="bonoCalidad.simboloElemento.label" default="Simbolo Elemento" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="simboloElemento" required="" value="${bonoCalidadInstance?.simboloElemento}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoCalidadInstance, field: 'leyMinima', 'error')} required">
	<label for="leyMinima">
		<g:message code="bonoCalidad.leyMinima.label" default="Ley Minima" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyMinima" value="${fieldValue(bean: bonoCalidadInstance, field: 'leyMinima')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoCalidadInstance, field: 'leyMaxima', 'error')} required">
	<label for="leyMaxima">
		<g:message code="bonoCalidad.leyMaxima.label" default="Ley Maxima" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyMaxima" value="${fieldValue(bean: bonoCalidadInstance, field: 'leyMaxima')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoCalidadInstance, field: 'bono', 'error')} required">
	<label for="bono">
		<g:message code="bonoCalidad.bono.label" default="Bono" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="bono" value="${fieldValue(bean: bonoCalidadInstance, field: 'bono')}" required="" inputmode="decimal"/>
</div>

