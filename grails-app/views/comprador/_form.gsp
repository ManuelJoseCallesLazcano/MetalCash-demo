<%@ page import="org.smart.compositos.Comprador" %>



<div class="fieldcontain ${hasErrors(bean: compradorInstance, field: 'nombreComprador', 'error')} required">
	<label for="nombreComprador">
		<g:message code="comprador.nombreComprador.label" default="Nombre Comprador" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreComprador" required="" value="${compradorInstance?.nombreComprador}" size="70"/>

</div>

<div class="fieldcontain ${hasErrors(bean: compradorInstance, field: 'nombreContacto', 'error')} required">
	<label for="nombreContacto">
		<g:message code="comprador.nombreContacto.label" default="Nombre Contacto" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreContacto" required="" value="${compradorInstance?.nombreContacto}" size="70"/>

</div>

<div class="fieldcontain ${hasErrors(bean: compradorInstance, field: 'telefono', 'error')} ">
	<label for="telefono">
		<g:message code="comprador.telefono.label" default="Telefono" />
		
	</label>
	<g:textField name="telefono" value="${compradorInstance?.telefono}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: compradorInstance, field: 'email', 'error')} ">
	<label for="email">
		<g:message code="comprador.email.label" default="Email" />
		
	</label>
	<g:textField name="email" value="${compradorInstance?.email}"/>

</div>

