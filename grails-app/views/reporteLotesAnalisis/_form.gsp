<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesAnalisis" %>



<div class="fieldcontain ${hasErrors(bean: reporteLotesAnalisisInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reporteLotesAnalisis.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${reporteLotesAnalisisInstance?.empresa?.id}" class="many-to-one, chosen-select" noSelection="['null': '-TODOS-']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesAnalisisInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reporteLotesAnalisis.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datepickerUI name="fechaInicial" value="${reporteLotesAnalisisInstance?.fechaInicial ?: new Date()}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesAnalisisInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reporteLotesAnalisis.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datepickerUI name="fechaFinal" value="${reporteLotesAnalisisInstance?.fechaFinal ?: new Date()}"/>

</div>

<br>

<div id="_complejo" style="text-align: center">
	<g:actionSubmit class="reporte" controller="reporteLotesAnalisis" action="crearReporte" value="Generar Reporte" />
</div>
