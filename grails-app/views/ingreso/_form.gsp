<%@ page import="org.socymet.caja.Ingreso" %>

<div id="importeDialogo" title="Importe a ingresar a Caja">
	<p id="importeFormateado" style="font-size: 36px; font-weight: bold; color: red;">Bs 0,00</p>
</div>

<div class="fieldcontain ${hasErrors(bean: ingresoInstance, field: 'numeroIngreso', 'error')} required" style="display: none">
	<label for="numeroIngreso">
		<g:message code="ingreso.numeroIngreso.label" default="Numero Ingreso" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numeroIngreso" type="number" value="${ingresoInstance.numeroIngreso}" required="" readonly="readonly"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ingresoInstance, field: 'fechaIngreso', 'error')} required" style="display: none">
	<label for="fechaIngreso">
		<g:message code="ingreso.fechaIngreso.label" default="Fecha Ingreso" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaIngreso" precision="day"  value="${ingresoInstance?.fechaIngreso}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: ingresoInstance, field: 'ci', 'error')} required">
	<label for="ci">
		<g:message code="ingreso.ci.label" default="Ci" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="ci" required="" value="${ingresoInstance?.ci}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ingresoInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="ingreso.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${ingresoInstance?.nombre}" size="90"/>

</div>

<h1 style="font-weight: bold">Informaci&oacute;n de la Transacci&oacute;n</h1>

<div class="fieldcontain ${hasErrors(bean: ingresoInstance, field: 'cuenta', 'error')} required">
	<label for="cuenta">
		<g:message code="ingreso.cuenta.label" default="Cuenta" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cuenta" name="cuenta.id" from="${org.socymet.caja.Cuenta.list()}" optionKey="id" required="" value="${ingresoInstance?.cuenta?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ingresoInstance, field: 'subcuenta', 'error')} required">
	<label for="subcuenta">
		<g:message code="ingreso.subcuenta.label" default="Subcuenta" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="subcuenta" name="subcuenta.id" from="${org.socymet.caja.Subcuenta.list()}" optionKey="id" required="" value="${ingresoInstance?.subcuenta?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ingresoInstance, field: 'concepto', 'error')} required">
	<label for="concepto">
		<g:message code="ingreso.concepto.label" default="Concepto" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="concepto" required="" value="${ingresoInstance?.concepto}" size="90"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ingresoInstance, field: 'importe', 'error')} required">
	<label for="importe">
		<g:message code="ingreso.importe.label" default="Importe" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="importe" value="${fieldValue(bean: ingresoInstance, field: 'importe')}" required="" style="font-size: 20px; font-weight: bold;" class="verde" inputmode="decimal"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ingresoInstance, field: 'importeLiteral', 'error')} required">
	<label for="importeLiteral">
		<g:message code="ingreso.importeLiteral.label" default="Importe Literal" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="importeLiteral" required="" value="${ingresoInstance?.importeLiteral}" readonly="readonly" size="90" class="amarillo"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ingresoInstance, field: 'observaciones', 'error')} ">
	<label for="observaciones">
		<g:message code="ingreso.observaciones.label" default="Observaciones" />
		
	</label>
	<g:textField name="observaciones" value="${ingresoInstance?.observaciones}" size="90"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ingresoInstance, field: 'usuario', 'error')} required" style="display: none">
	<label for="usuario">
		<g:message code="ingreso.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${org.socymet.seguridad.SecUser.list()}" optionKey="id" required="" value="${ingresoInstance?.usuario?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ingresoInstance, field: 'consolidado', 'error')} required" style="display: none">
	<label for="consolidado">
		<g:message code="ingreso.consolidado.label" default="Consolidado" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="consolidado" required="" value="${ingresoInstance?.consolidado}" readonly="readonly"/>

</div>

