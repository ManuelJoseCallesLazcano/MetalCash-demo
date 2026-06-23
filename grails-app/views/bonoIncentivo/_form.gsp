<%@ page import="org.socymet.proveedor.bonos.BonoIncentivo" %>



<div class="fieldcontain ${hasErrors(bean: bonoIncentivoInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="bonoIncentivo.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" required="" value="${bonoIncentivoInstance?.empresa?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoIncentivoInstance, field: 'elemento', 'error')} required">
	<label for="elemento">
		<g:message code="bonoIncentivo.elemento.label" default="Elemento" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="elemento" required="" value="${bonoIncentivoInstance?.elemento}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoIncentivoInstance, field: 'simboloElemento', 'error')} required">
	<label for="simboloElemento">
		<g:message code="bonoIncentivo.simboloElemento.label" default="Simbolo Elemento" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="simboloElemento" required="" value="${bonoIncentivoInstance?.simboloElemento}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoIncentivoInstance, field: 'leyMinima', 'error')} required">
	<label for="leyMinima">
		<g:message code="bonoIncentivo.leyMinima.label" default="Ley Minima" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyMinima" value="${fieldValue(bean: bonoIncentivoInstance, field: 'leyMinima')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoIncentivoInstance, field: 'leyMaxima', 'error')} required">
	<label for="leyMaxima">
		<g:message code="bonoIncentivo.leyMaxima.label" default="Ley Maxima" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyMaxima" value="${fieldValue(bean: bonoIncentivoInstance, field: 'leyMaxima')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoIncentivoInstance, field: 'cantidadMinima', 'error')} required">
	<label for="cantidadMinima">
		<g:message code="bonoIncentivo.cantidadMinima.label" default="Cantidad Minima" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadMinima" value="${fieldValue(bean: bonoIncentivoInstance, field: 'cantidadMinima')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoIncentivoInstance, field: 'cantidadMaxima', 'error')} required">
	<label for="cantidadMaxima">
		<g:message code="bonoIncentivo.cantidadMaxima.label" default="Cantidad Maxima" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadMaxima" value="${fieldValue(bean: bonoIncentivoInstance, field: 'cantidadMaxima')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoIncentivoInstance, field: 'bono', 'error')} required">
	<label for="bono">
		<g:message code="bonoIncentivo.bono.label" default="Bono" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="bono" value="${fieldValue(bean: bonoIncentivoInstance, field: 'bono')}" required="" inputmode="decimal"/>
</div>

