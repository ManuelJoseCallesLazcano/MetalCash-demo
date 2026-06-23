<%@ page import="org.socymet.cancelacion.EstadoCuentaTransporte" %>



<div class="fieldcontain ${hasErrors(bean: estadoCuentaTransporteInstance, field: 'solicitante', 'error')} required">
	<label for="solicitante">
		<g:message code="estadoCuentaTransporte.solicitante.label" default="Solicitante" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="solicitante" required="" value="${estadoCuentaTransporteInstance?.solicitante}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoCuentaTransporteInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="estadoCuentaTransporte.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${estadoCuentaTransporteInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoCuentaTransporteInstance, field: 'automovil', 'error')} ">
	<label for="automovil">
		<g:message code="estadoCuentaTransporte.automovil.label" default="Automovil" />
		
	</label>
	<g:select id="automovil" name="automovil.id" from="${org.socymet.proveedor.Automovil.list()}" optionKey="id" value="${estadoCuentaTransporteInstance?.automovil?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoCuentaTransporteInstance, field: 'ci', 'error')} required">
	<label for="ci">
		<g:message code="estadoCuentaTransporte.ci.label" default="Ci" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="ci" required="" value="${estadoCuentaTransporteInstance?.ci}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoCuentaTransporteInstance, field: 'nombreResponsable', 'error')} required">
	<label for="nombreResponsable">
		<g:message code="estadoCuentaTransporte.nombreResponsable.label" default="Nombre Responsable" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreResponsable" required="" value="${estadoCuentaTransporteInstance?.nombreResponsable}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoCuentaTransporteInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="estadoCuentaTransporte.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${estadoCuentaTransporteInstance?.fecha}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: estadoCuentaTransporteInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="estadoCuentaTransporte.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${estadoCuentaTransporteInstance?.descripcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoCuentaTransporteInstance, field: 'ingreso', 'error')} required">
	<label for="ingreso">
		<g:message code="estadoCuentaTransporte.ingreso.label" default="Ingreso" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="ingreso" value="${fieldValue(bean: estadoCuentaTransporteInstance, field: 'ingreso')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoCuentaTransporteInstance, field: 'egreso', 'error')} required">
	<label for="egreso">
		<g:message code="estadoCuentaTransporte.egreso.label" default="Egreso" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="egreso" value="${fieldValue(bean: estadoCuentaTransporteInstance, field: 'egreso')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: estadoCuentaTransporteInstance, field: 'saldo', 'error')} required">
	<label for="saldo">
		<g:message code="estadoCuentaTransporte.saldo.label" default="Saldo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="saldo" value="${fieldValue(bean: estadoCuentaTransporteInstance, field: 'saldo')}" required="" inputmode="decimal"/>
</div>

