<%@ page import="org.socymet.seguridad.MiCuenta" %>



<div class="fieldcontain ${hasErrors(bean: miCuentaInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="miCuenta.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${miCuentaInstance?.nombre}" size="50" readonly="readonly" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: miCuentaInstance, field: 'cuenta', 'error')} required">
	<label for="cuenta">
		<g:message code="miCuenta.cuenta.label" default="Cuenta" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="cuenta" required="" value="${miCuentaInstance?.cuenta}" size="50"/>
</div>

<h1 style="font-weight: bold">Cambio de Contraseña</h1>

<div class="fieldcontain ${hasErrors(bean: miCuentaInstance, field: 'contrasena', 'error')} required">
	<label for="contrasena">
		<g:message code="miCuenta.contrasena.label" default="Contrasena" />
		<span class="required-indicator">*</span>
	</label>
	<g:passwordField name="contrasena" required="" value="${miCuentaInstance?.contrasena}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: miCuentaInstance, field: 'confirmarContrasena', 'error')} required">
	<label for="confirmarContrasena">
		<g:message code="miCuenta.confirmarContrasena.label" default="Confirmar Contrasena" />
		<span class="required-indicator">*</span>
	</label>
	<g:passwordField name="confirmarContrasena" required="" value="${miCuentaInstance?.confirmarContrasena}"/>
</div>

<div id="_botones" style="text-align: center">
    <br>
    <button id="actualizar" type="button">ACTUALIZAR INFORMACION</button>
</div>

