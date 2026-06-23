<%@ page import="org.smart.compositos.Ingenio" %>



<div class="fieldcontain ${hasErrors(bean: ingenioInstance, field: 'nombreIngenio', 'error')} required">
	<label for="nombreIngenio">
		<g:message code="ingenio.nombreIngenio.label" default="Nombre Ingenio" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreIngenio" required="" value="${ingenioInstance?.nombreIngenio}" size="50"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ingenioInstance, field: 'telefono', 'error')} ">
	<label for="telefono">
		<g:message code="ingenio.telefono.label" default="Telefono" />
		
	</label>
	<g:textField name="telefono" value="${ingenioInstance?.telefono}"/>

</div>

