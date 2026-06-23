<%@ page import="org.socymet.org.socymet.reportes.PlanillaDeLiquidacionCobre" %>



<div class="fieldcontain ${hasErrors(bean: planillaDeLiquidacionCobreInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="planillaDeLiquidacionCobre.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${planillaDeLiquidacionCobreInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: planillaDeLiquidacionCobreInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="planillaDeLiquidacionCobre.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${planillaDeLiquidacionCobreInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: planillaDeLiquidacionCobreInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="planillaDeLiquidacionCobre.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${planillaDeLiquidacionCobreInstance?.fechaFinal}"  />
</div>

<br>
<div id="_resultados">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="planillaDeLiquidacionCobre" action="crearReporte" value="Generar Reporte" />
    </div>
</div>
