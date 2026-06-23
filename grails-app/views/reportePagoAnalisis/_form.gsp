<%@ page import="org.socymet.org.socymet.reportes.ReportePagoAnalisis" %>



<div class="fieldcontain ${hasErrors(bean: reportePagoAnalisisInstance, field: 'nombreDeLaboratorio', 'error')} ">
	<label for="nombreDeLaboratorio">
		<g:message code="reportePagoAnalisis.nombreDeLaboratorio.label" default="Nombre De Laboratorio" />
		
	</label>
	<g:textField name="nombreDeLaboratorio" value="${reportePagoAnalisisInstance?.nombreDeLaboratorio}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoAnalisisInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reportePagoAnalisis.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reportePagoAnalisisInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoAnalisisInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reportePagoAnalisis.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reportePagoAnalisisInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoAnalisisInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reportePagoAnalisis.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reportePagoAnalisisInstance?.fechaFinal}"  />
</div>

