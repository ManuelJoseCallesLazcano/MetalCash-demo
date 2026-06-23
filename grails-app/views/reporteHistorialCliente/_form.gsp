<%@ page import="org.socymet.org.socymet.reportes.ReporteHistorialCliente" %>



<div class="fieldcontain ${hasErrors(bean: reporteHistorialClienteInstance, field: 'cliente', 'error')} ">
	<label for="cliente">
		<g:message code="reporteHistorialCliente.cliente.label" default="Cliente" />
		
	</label>
	<g:select id="cliente" name="cliente.id" from="${org.socymet.proveedor.Cliente.list()}" optionKey="id" value="${reporteHistorialClienteInstance?.cliente?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHistorialClienteInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="reporteHistorialCliente.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteHistorialClienteInstance?.fechaInicial}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHistorialClienteInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="reporteHistorialCliente.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteHistorialClienteInstance?.fechaFinal}" default="none" noSelection="['': '']" />
</div>

