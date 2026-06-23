<%@ page import="org.socymet.reportesAcopiadoras.PlanillaLiquidacion" %>



<div class="fieldcontain ${hasErrors(bean: planillaLiquidacionInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="planillaLiquidacion.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" required="" value="${planillaLiquidacionInstance?.empresa?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: planillaLiquidacionInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="planillaLiquidacion.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${planillaLiquidacionInstance?.fechaInicial}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: planillaLiquidacionInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="planillaLiquidacion.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${planillaLiquidacionInstance?.fechaFinal}"  />

</div>

<br>

<div id="_resultadosEstano">
	<div style="text-align: center;">
		<g:actionSubmit class="reporte" controller="planillaLiquidacion" action="crearReporte" value="Generar Reporte" />
	</div>
</div>
