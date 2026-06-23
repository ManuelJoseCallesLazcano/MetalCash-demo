<%@ page import="org.socymet.caja.Egreso" %>

<div id="importeDialogo" title="Importe a egresar de Caja">
	<p id="importeFormateado" style="font-size: 36px; font-weight: bold; color: red;">Bs 0,00</p>
</div>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'numeroEgreso', 'error')} required" style="display: none;">
	<label for="numeroEgreso">
		<g:message code="egreso.numeroEgreso.label" default="Numero Egreso" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numeroEgreso" type="number" value="${egresoInstance.numeroEgreso}" required="" inputmode="numeric"/>

</div>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'fechaEgreso', 'error')} required" style="display: none;">
	<label for="fechaEgreso">
		<g:message code="egreso.fechaEgreso.label" default="Fecha Egreso" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaEgreso" precision="day"  value="${egresoInstance?.fechaEgreso}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'ci', 'error')} required">
	<label for="ci">
		<g:message code="egreso.ci.label" default="Ci" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="ci" required="" value="${egresoInstance?.ci}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="egreso.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${egresoInstance?.nombre}" size="90"/>

</div>

<h1 style="font-weight: bold">Informaci&oacute;n de la Transacci&oacute;n</h1>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'cuenta', 'error')} required">
	<label for="cuenta">
		<g:message code="egreso.cuenta.label" default="Cuenta" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cuenta" name="cuenta.id" from="${org.socymet.caja.Cuenta.list()}" optionKey="id" required="" value="${egresoInstance?.cuenta?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'subcuenta', 'error')} required">
	<label for="subcuenta">
		<g:message code="egreso.subcuenta.label" default="Subcuenta" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="subcuenta" name="subcuenta.id" from="${org.socymet.caja.Subcuenta.list()}" optionKey="id" required="" value="${egresoInstance?.subcuenta?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'operacion', 'error')} required">
	<label for="operacion">
		<g:message code="egreso.operacion.label" default="Operacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="operacion" from="${['LIQUIDACION','ANTICIPO','PAGO DE TRANSPORTE','ANTICIPO POR TRANSPORTE','OTROS PAGOS']}" required="" value="${egresoInstance?.operacion}" valueMessagePrefix="egreso.operacion"/>

</div>

<div id="_identificador" class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'identificador', 'error')} required">
	<label for="identificador">
		<g:message code="egreso.identificador.label" default="Identificador" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="identificador" required="" value="${egresoInstance?.identificador}" size="90"/>

</div>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'concepto', 'error')} required">
	<label for="concepto">
		<g:message code="egreso.concepto.label" default="Concepto" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="concepto" required="" value="${egresoInstance?.concepto}" size="90"/>

</div>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'importe', 'error')} required">
	<label for="importe">
		<g:message code="egreso.importe.label" default="Importe" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="importe" value="${fieldValue(bean: egresoInstance, field: 'importe')}" required="" style="font-size: 20px; font-weight: bold;" class="verde" inputmode="decimal"/>

</div>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'importeLiteral', 'error')} required">
	<label for="importeLiteral">
		<g:message code="egreso.importeLiteral.label" default="Importe Literal" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="importeLiteral" required="" value="${egresoInstance?.importeLiteral}" size="90"/>

</div>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'observaciones', 'error')} ">
	<label for="observaciones">
		<g:message code="egreso.observaciones.label" default="Observaciones" />
		
	</label>
	<g:textField name="observaciones" value="${egresoInstance?.observaciones}" size="90"/>

</div>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'usuario', 'error')} required" style="display: none;">
	<label for="usuario">
		<g:message code="egreso.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${org.socymet.seguridad.SecUser.list()}" optionKey="id" required="" value="${egresoInstance?.usuario?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: egresoInstance, field: 'consolidado', 'error')} required" style="display: none;">
	<label for="consolidado">
		<g:message code="egreso.consolidado.label" default="Consolidado" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="consolidado" required="" value="${egresoInstance?.consolidado}"/>

</div>

