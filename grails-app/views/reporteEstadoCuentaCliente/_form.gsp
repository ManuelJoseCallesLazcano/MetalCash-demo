	<%@ page import="org.socymet.org.socymet.reportes.ReporteEstadoCuentaCliente" %>



<div class="fieldcontain ${hasErrors(bean: reporteEstadoCuentaClienteInstance, field: 'cliente', 'error')} ">
	<label for="cliente">
		<g:message code="reporteEstadoCuentaCliente.cliente.label" default="Cliente" />
		
	</label>
	<g:select id="cliente" name="cliente.id" from="${org.socymet.proveedor.Cliente.list([sort: 'nombre'])}" optionKey="id" value="${reporteEstadoCuentaClienteInstance?.cliente?.id}" class="many-to-one, chosen-select"  style="width: 450px"/>

</div>

<div class="fieldcontain ${hasErrors(bean: reporteEstadoCuentaClienteInstance, field: 'fechaInicial', 'error')} " style="display: none">
	<label for="fechaInicial">
		<g:message code="reporteEstadoCuentaCliente.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteEstadoCuentaClienteInstance?.fechaInicial}" />

</div>

<div class="fieldcontain ${hasErrors(bean: reporteEstadoCuentaClienteInstance, field: 'fechaFinal', 'error')} " style="display: none">
	<label for="fechaFinal">
		<g:message code="reporteEstadoCuentaCliente.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteEstadoCuentaClienteInstance?.fechaFinal}" />

</div>

<br>

<div style="text-align: center">
	<g:actionSubmit class="reporte" controller="reporteEstadoCuentaCliente" action="crearReporte" value="Generar Reporte" />
</div>