<%@ page import="org.socymet.caja.Cuenta" %>



<div class="fieldcontain ${hasErrors(bean: cuentaInstance, field: 'codigoCuenta', 'error')} required">
	<label for="codigoCuenta">
		<g:message code="cuenta.codigoCuenta.label" default="Codigo Cuenta" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="codigoCuenta" required="" value="${cuentaInstance?.codigoCuenta}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cuentaInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="cuenta.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${cuentaInstance?.descripcion}" size="90"/>

</div>

