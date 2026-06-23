<%@ page import="org.socymet.org.socymet.reportes.ReporteResumenRetenciones" %>

<g:hiddenField name="elemento" value="" />

<div class="fieldcontain ${hasErrors(bean: reporteResumenRetencionesInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reporteResumenRetenciones.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteResumenRetencionesInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteResumenRetencionesInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reporteResumenRetenciones.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteResumenRetencionesInstance?.fechaFinal}"  />
</div>

<br/>

<div id="_resultadosEstano">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteResumenRetenciones" action="crearReporte" value="Generar Reporte" />
    </div>
</div>

