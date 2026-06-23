<%@ page import="org.socymet.org.socymet.reportes.ReporteProduccionPorCliente" %>

<h1 style="font-weight: bold">Parametros de busqueda:</h1>

<div class="fieldcontain ${hasErrors(bean: reporteProduccionPorClienteInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="reporteProduccionPorCliente.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list(sort: 'nombreDeEmpresa')}" optionKey="id" required="" value="${reporteProduccionPorClienteInstance?.empresa?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteProduccionPorClienteInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reporteProduccionPorCliente.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteProduccionPorClienteInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteProduccionPorClienteInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reporteProduccionPorCliente.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteProduccionPorClienteInstance?.fechaFinal}"  />
</div>

<br>

<div id="_resultadosEstano">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteProduccionPorCliente" action="crearReporte" value="Generar Reporte" />
    </div>
</div>

