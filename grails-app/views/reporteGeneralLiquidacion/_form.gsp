<%@ page import="org.socymet.org.socymet.reportes.ReporteGeneralLiquidacion" %>



<div class="fieldcontain ${hasErrors(bean: reporteGeneralLiquidacionInstance, field: 'deposito', 'error')} ">
	<label for="deposito">
		<g:message code="reporteGeneralLiquidacion.deposito.label" default="Deposito" />
		
	</label>
	<g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" value="${reporteGeneralLiquidacionInstance?.deposito?.id}" class="many-to-one" noSelection="['null': '-TODOS-']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: reporteGeneralLiquidacionInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reporteGeneralLiquidacion.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteGeneralLiquidacionInstance?.fechaInicial}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: reporteGeneralLiquidacionInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reporteGeneralLiquidacion.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteGeneralLiquidacionInstance?.fechaFinal}"  />

</div>

<br>

<div id="_resultadosEstano">
	<div style="text-align: center;">
		<g:actionSubmit class="reporte" controller="reporteGeneralLiquidacion" action="crearReporte" value="Generar Reporte" />
	</div>
</div>
