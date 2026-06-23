<%@ page import="org.socymet.proveedor.bonos.Bono" %>



<div class="fieldcontain ${hasErrors(bean: bonoInstance, field: 'bono', 'error')} required">
	<label for="bono">
		<g:message code="bono.bono.label" default="Bono" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="bono" value="${fieldValue(bean: bonoInstance, field: 'bono')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bonoInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="bono.elemento.label" default="Elemento" />
		
	</label>
	<g:textField name="elemento" value="${bonoInstance?.elemento}"/>
</div>

