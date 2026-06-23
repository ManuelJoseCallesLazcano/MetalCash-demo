<%@ page import="org.socymet.org.socymet.reportes.GeneralLiquidacion" %>



<div class="fieldcontain ${hasErrors(bean: generalLiquidacionInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="generalLiquidacion.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${generalLiquidacionInstance?.fechaInicial}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: generalLiquidacionInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="generalLiquidacion.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${generalLiquidacionInstance?.fechaFinal}"  />

</div>

<br>

<div id="_resultadosEstano">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="generalLiquidacion" action="crearReporte" value="Generar Reporte" />
    </div>
</div>

