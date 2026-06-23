<%@ page import="org.socymet.caja.CierreCaja" %>



<div class="fieldcontain ${hasErrors(bean: cierreCajaInstance, field: 'numeroCierreCaja', 'error')} required" style="display: none;">
	<label for="numeroCierreCaja">
		<g:message code="cierreCaja.numeroCierreCaja.label" default="Numero Cierre Caja" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numeroCierreCaja" type="number" value="${cierreCajaInstance.numeroCierreCaja}" required="" inputmode="numeric"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaInstance, field: 'fechaCierreCaja', 'error')} required">
	<label for="fechaCierreCaja">
		<g:message code="cierreCaja.fechaCierreCaja.label" default="Fecha Cierre Caja" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaCierreCaja" precision="day"  value="${cierreCajaInstance?.fechaCierreCaja}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaInstance, field: 'usuario', 'error')} required" style="display:none;">
	<label for="usuario">
		<g:message code="cierreCaja.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${org.socymet.seguridad.SecUser.list()}" optionKey="id" required="" value="${cierreCajaInstance?.usuario?.id}" class="many-to-one"/>

</div>

<div style="text-align: center;">
	<br/>
	<input id="filtrar" type="button" value="FILTRAR TRANSACCIONES" style="background-color: #72BDA3; color: white; font-size: 16px;"/>

	%{--<button id="filtrar" class="ui-button ui-widget ui-corner-all">FILTRAR TRANSACCIONES</button>--}%
</div>

<h1 style="font-weight: bold">Movimientos de Caja a consolidarse</h1>

<div style="width: 750px; margin-left: auto; margin-right: auto;">
	<table id="detalleTabla"></table>
</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaInstance, field: 'detalle', 'error')} required" style="display: none;">
	<label for="detalle">
		<g:message code="cierreCaja.detalle.label" default="Detalle" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="detalle" required="" value="${cierreCajaInstance?.detalle}" readonly="readonly"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaInstance, field: 'debeTotal', 'error')} required">
	<label for="debeTotal">
		<g:message code="cierreCaja.debeTotal.label" default="Debe Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="debeTotal" value="${fieldValue(bean: cierreCajaInstance, field: 'debeTotal')}" required="" readonly="readonly" class="amarillo"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaInstance, field: 'haberTotal', 'error')} required">
	<label for="haberTotal">
		<g:message code="cierreCaja.haberTotal.label" default="Haber Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="haberTotal" value="${fieldValue(bean: cierreCajaInstance, field: 'haberTotal')}" required="" readonly="readonly" class="amarillo"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaInstance, field: 'saldoTotal', 'error')} required">
	<label for="saldoTotal">
		<g:message code="cierreCaja.saldoTotal.label" default="Saldo Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="saldoTotal" value="${fieldValue(bean: cierreCajaInstance, field: 'saldoTotal')}" required="" readonly="readonly" class="amarillo"/>

</div>

<div class="fieldcontain ${hasErrors(bean: cierreCajaInstance, field: 'observaciones', 'error')} ">
	<label for="observaciones">
		<g:message code="cierreCaja.observaciones.label" default="Observaciones" />
		
	</label>
	<g:textField name="observaciones" value="${cierreCajaInstance?.observaciones}" size="90"/>

</div>

