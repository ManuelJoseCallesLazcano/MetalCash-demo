<%@ page import="org.socymet.caja.Subcuenta" %>



<div class="fieldcontain ${hasErrors(bean: subcuentaInstance, field: 'cuenta', 'error')} required">
	<label for="cuenta">
		<g:message code="subcuenta.cuenta.label" default="Cuenta" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cuenta" name="cuenta.id" from="${org.socymet.caja.Cuenta.list()}" optionKey="id" required="" value="${subcuentaInstance?.cuenta?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: subcuentaInstance, field: 'codigoSubcuenta', 'error')} required">
	<label for="codigoSubcuenta">
		<g:message code="subcuenta.codigoSubcuenta.label" default="Codigo Subcuenta" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="codigoSubcuenta" required="" value="${subcuentaInstance?.codigoSubcuenta}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: subcuentaInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="subcuenta.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${subcuentaInstance?.descripcion}" size="90"/>

</div>

