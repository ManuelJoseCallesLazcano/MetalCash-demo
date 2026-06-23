<%@ page import="org.socymet.org.socymet.reportes.DetalleCanjeTornaguias" %>



<div class="fieldcontain ${hasErrors(bean: detalleCanjeTornaguiasInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="detalleCanjeTornaguias.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${detalleCanjeTornaguiasInstance?.empresa?.id}" class="many-to-one, chosen-select" style="width: 350px"/>

</div>

<div class="fieldcontain ${hasErrors(bean: detalleCanjeTornaguiasInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="detalleCanjeTornaguias.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${detalleCanjeTornaguiasInstance?.fechaInicial}"  years="${2021..2025}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: detalleCanjeTornaguiasInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="detalleCanjeTornaguias.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${detalleCanjeTornaguiasInstance?.fechaFinal}"  years="${2021..2025}"/>

</div>


<br>

<div>
	<div style="text-align: center;">
		<g:actionSubmit class="reporte" controller="detalleCanjeTornaguias" action="crearReporte" value="Generar Reporte" />
	</div>
</div>