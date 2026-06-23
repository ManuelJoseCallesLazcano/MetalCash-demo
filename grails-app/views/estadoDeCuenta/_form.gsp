<%@ page import="org.socymet.anticipos.EstadoDeCuenta" %>



<div class="fieldcontain ${hasErrors(bean: estadoDeCuentaInstance, field: 'cliente', 'error')} required">
	<label for="cliente">
		<g:message code="estadoDeCuenta.cliente.label" default="Cliente" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cliente" name="cliente.id" from="${org.socymet.proveedor.Cliente.list()}" optionKey="id" required="" value="${estadoDeCuentaInstance?.cliente?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoDeCuentaInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="estadoDeCuenta.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" required="" value="${estadoDeCuentaInstance?.empresa?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoDeCuentaInstance, field: 'ci', 'error')} required">
	<label for="ci">
		<g:message code="estadoDeCuenta.ci.label" default="Ci" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="ci" required="" value="${estadoDeCuentaInstance?.ci}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoDeCuentaInstance, field: 'nombre', 'error')} required">
	<label for="nombre">
		<g:message code="estadoDeCuenta.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombre" required="" value="${estadoDeCuentaInstance?.nombre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoDeCuentaInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="estadoDeCuenta.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreEmpresa" required="" value="${estadoDeCuentaInstance?.nombreEmpresa}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoDeCuentaInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="estadoDeCuenta.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${estadoDeCuentaInstance?.fecha}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: estadoDeCuentaInstance, field: 'numeroComprobante', 'error')} required">
	<label for="numeroComprobante">
		<g:message code="estadoDeCuenta.numeroComprobante.label" default="Numero Comprobante" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numeroComprobante" type="number" value="${estadoDeCuentaInstance.numeroComprobante}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoDeCuentaInstance, field: 'detalle', 'error')} required">
	<label for="detalle">
		<g:message code="estadoDeCuenta.detalle.label" default="Detalle" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="detalle" required="" value="${estadoDeCuentaInstance?.detalle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoDeCuentaInstance, field: 'debe', 'error')} required">
	<label for="debe">
		<g:message code="estadoDeCuenta.debe.label" default="Debe" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="debe" value="${fieldValue(bean: estadoDeCuentaInstance, field: 'debe')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoDeCuentaInstance, field: 'haber', 'error')} required">
	<label for="haber">
		<g:message code="estadoDeCuenta.haber.label" default="Haber" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="haber" value="${fieldValue(bean: estadoDeCuentaInstance, field: 'haber')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoDeCuentaInstance, field: 'saldo', 'error')} required">
	<label for="saldo">
		<g:message code="estadoDeCuenta.saldo.label" default="Saldo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="saldo" value="${fieldValue(bean: estadoDeCuentaInstance, field: 'saldo')}" required="" inputmode="decimal"/>
</div>

