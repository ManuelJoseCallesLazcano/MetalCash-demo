<%@ page import="org.socymet.caja.CierreCajaDetalle" %>



<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'cierreCaja', 'error')} required">
	<label for="cierreCaja">
		<g:message code="cierreCajaDetalle.cierreCaja.label" default="Cierre Caja" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cierreCaja" name="cierreCaja.id" from="${org.socymet.caja.CierreCaja.list()}" optionKey="id" required="" value="${cierreCajaDetalleInstance?.cierreCaja?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'movimientoCaja', 'error')} required">
	<label for="movimientoCaja">
		<g:message code="cierreCajaDetalle.movimientoCaja.label" default="Movimiento Caja" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="movimientoCaja" name="movimientoCaja.id" from="${org.socymet.caja.MovimientoCaja.list()}" optionKey="id" required="" value="${cierreCajaDetalleInstance?.movimientoCaja?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'numeroMovimiento', 'error')} required">
	<label for="numeroMovimiento">
		<g:message code="cierreCajaDetalle.numeroMovimiento.label" default="Numero Movimiento" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numeroMovimiento" type="number" value="${cierreCajaDetalleInstance.numeroMovimiento}" required="" inputmode="numeric"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'fechaMovimiento', 'error')} required">
	<label for="fechaMovimiento">
		<g:message code="cierreCajaDetalle.fechaMovimiento.label" default="Fecha Movimiento" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="fechaMovimiento" required="" value="${cierreCajaDetalleInstance?.fechaMovimiento}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'ingreso', 'error')} ">
	<label for="ingreso">
		<g:message code="cierreCajaDetalle.ingreso.label" default="Ingreso" />
		
	</label>
	<g:select id="ingreso" name="ingreso.id" from="${org.socymet.caja.Ingreso.list()}" optionKey="id" value="${cierreCajaDetalleInstance?.ingreso?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'egreso', 'error')} ">
	<label for="egreso">
		<g:message code="cierreCajaDetalle.egreso.label" default="Egreso" />
		
	</label>
	<g:select id="egreso" name="egreso.id" from="${org.socymet.caja.Egreso.list()}" optionKey="id" value="${cierreCajaDetalleInstance?.egreso?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'ci', 'error')} required">
	<label for="ci">
		<g:message code="cierreCajaDetalle.ci.label" default="Ci" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="ci" required="" value="${cierreCajaDetalleInstance?.ci}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="cierreCajaDetalle.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${cierreCajaDetalleInstance?.nombre}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'concepto', 'error')} required">
	<label for="concepto">
		<g:message code="cierreCajaDetalle.concepto.label" default="Concepto" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="concepto" required="" value="${cierreCajaDetalleInstance?.concepto}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'debe', 'error')} required">
	<label for="debe">
		<g:message code="cierreCajaDetalle.debe.label" default="Debe" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="debe" value="${fieldValue(bean: cierreCajaDetalleInstance, field: 'debe')}" required="" inputmode="decimal"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'haber', 'error')} required">
	<label for="haber">
		<g:message code="cierreCajaDetalle.haber.label" default="Haber" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="haber" value="${fieldValue(bean: cierreCajaDetalleInstance, field: 'haber')}" required="" inputmode="decimal"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'saldo', 'error')} required">
	<label for="saldo">
		<g:message code="cierreCajaDetalle.saldo.label" default="Saldo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="saldo" value="${fieldValue(bean: cierreCajaDetalleInstance, field: 'saldo')}" required="" inputmode="decimal"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaDetalleInstance, field: 'usuario', 'error')} required">
	<label for="usuario">
		<g:message code="cierreCajaDetalle.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${org.socymet.seguridad.SecUser.list()}" optionKey="id" required="" value="${cierreCajaDetalleInstance?.usuario?.id}" class="many-to-one"/>

</div>

