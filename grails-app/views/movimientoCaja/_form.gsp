<%@ page import="org.socymet.caja.MovimientoCaja" %>



<div class="fieldcontain ${hasErrors(bean: movimientoCajaInstance, field: 'numeroMovimiento', 'error')} required">
	<label for="numeroMovimiento">
		<g:message code="movimientoCaja.numeroMovimiento.label" default="Numero Movimiento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numeroMovimiento" type="number" value="${movimientoCajaInstance.numeroMovimiento}" required="" inputmode="numeric"/>

</div>

<div class="fieldcontain ${hasErrors(bean: movimientoCajaInstance, field: 'fechaMovimiento', 'error')} required">
	<label for="fechaMovimiento">
		<g:message code="movimientoCaja.fechaMovimiento.label" default="Fecha Movimiento" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaMovimiento" precision="day"  value="${movimientoCajaInstance?.fechaMovimiento}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: movimientoCajaInstance, field: 'ingreso', 'error')} ">
	<label for="ingreso">
		<g:message code="movimientoCaja.ingreso.label" default="Ingreso" />
		
	</label>
	<g:select id="ingreso" name="ingreso.id" from="${org.socymet.caja.Ingreso.list()}" optionKey="id" value="${movimientoCajaInstance?.ingreso?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: movimientoCajaInstance, field: 'egreso', 'error')} ">
	<label for="egreso">
		<g:message code="movimientoCaja.egreso.label" default="Egreso" />
		
	</label>
	<g:select id="egreso" name="egreso.id" from="${org.socymet.caja.Egreso.list()}" optionKey="id" value="${movimientoCajaInstance?.egreso?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: movimientoCajaInstance, field: 'ci', 'error')} required">
	<label for="ci">
		<g:message code="movimientoCaja.ci.label" default="Ci" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="ci" required="" value="${movimientoCajaInstance?.ci}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: movimientoCajaInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="movimientoCaja.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${movimientoCajaInstance?.nombre}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: movimientoCajaInstance, field: 'concepto', 'error')} required">
	<label for="concepto">
		<g:message code="movimientoCaja.concepto.label" default="Concepto" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="concepto" required="" value="${movimientoCajaInstance?.concepto}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: movimientoCajaInstance, field: 'debe', 'error')} required">
	<label for="debe">
		<g:message code="movimientoCaja.debe.label" default="Debe" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="debe" value="${fieldValue(bean: movimientoCajaInstance, field: 'debe')}" required="" inputmode="decimal"/>

</div>

<div class="fieldcontain ${hasErrors(bean: movimientoCajaInstance, field: 'haber', 'error')} required">
	<label for="haber">
		<g:message code="movimientoCaja.haber.label" default="Haber" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="haber" value="${fieldValue(bean: movimientoCajaInstance, field: 'haber')}" required="" inputmode="decimal"/>

</div>

<div class="fieldcontain ${hasErrors(bean: movimientoCajaInstance, field: 'saldo', 'error')} required">
	<label for="saldo">
		<g:message code="movimientoCaja.saldo.label" default="Saldo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="saldo" value="${fieldValue(bean: movimientoCajaInstance, field: 'saldo')}" required="" inputmode="decimal"/>

</div>

<div class="fieldcontain ${hasErrors(bean: movimientoCajaInstance, field: 'consolidado', 'error')} required">
	<label for="consolidado">
		<g:message code="movimientoCaja.consolidado.label" default="Consolidado" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="consolidado" required="" value="${movimientoCajaInstance?.consolidado}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: movimientoCajaInstance, field: 'usuario', 'error')} required">
	<label for="usuario">
		<g:message code="movimientoCaja.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${org.socymet.seguridad.SecUser.list()}" optionKey="id" required="" value="${movimientoCajaInstance?.usuario?.id}" class="many-to-one"/>

</div>

