<%@ page import="org.socymet.org.socymet.reportes.DetalleFormularioM02" %>



<div class="fieldcontain ${hasErrors(bean: detalleFormularioM02Instance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="detalleFormularioM02.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${detalleFormularioM02Instance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: detalleFormularioM02Instance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="detalleFormularioM02.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${detalleFormularioM02Instance?.fechaFinal}"  />
</div>

<div id="_resultadosComplejo">
    <br>
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="detalleFormularioM02" action="crearDetalle" value="Generar Reporte" />
    </div>
</div>

