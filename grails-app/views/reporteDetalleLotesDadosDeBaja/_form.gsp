<%@ page import="org.socymet.org.socymet.reportes.ReporteDetalleLotesDadosDeBaja" %>



<div class="fieldcontain ${hasErrors(bean: reporteDetalleLotesDadosDeBajaInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reporteDetalleLotesDadosDeBaja.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Complejo','Plomo-Plata','Zinc-Plata']}" value="${reporteDetalleLotesDadosDeBajaInstance?.elemento}" valueMessagePrefix="reporteDetalleLotesDadosDeBaja.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteDetalleLotesDadosDeBajaInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reporteDetalleLotesDadosDeBaja.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteDetalleLotesDadosDeBajaInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteDetalleLotesDadosDeBajaInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reporteDetalleLotesDadosDeBaja.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteDetalleLotesDadosDeBajaInstance?.fechaFinal}"  />
</div>

