<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesCompositos" %>



<div class="fieldcontain ${hasErrors(bean: reporteLotesCompositosInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reporteLotesCompositos.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteLotesCompositosInstance?.fechaInicial}" years="${2021..2026}" />

</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesCompositosInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reporteLotesCompositos.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteLotesCompositosInstance?.fechaFinal}" years="${2021..2026}" />

</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesCompositosInstance, field: 'estado', 'error')} required">
	<label for="estado">
		<g:message code="reporteLotesCompositos.estado.label" default="Estado" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="estado" from="${['EN COMPOSITO', 'SIN COMPOSITO']}" required="" value="${reporteLotesCompositosInstance?.estado}" valueMessagePrefix="reporteLotesCompositos.estado" class="chosen-select"/>

</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesCompositosInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="reporteLotesCompositos.empresa.label" default="Excluir Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list(sort: 'nombreDeEmpresa')}" optionKey="id" value="${reporteLotesCompositosInstance?.empresa?.id}" class="many-to-one, chosen-select" multiple=""/>

</div>

<br>

<div id="_complejo" style="text-align: center">
	<g:actionSubmit class="reporte" controller="reporteLotesCompositos" action="crearReporteComplejo" value="Generar Reporte" />
</div>
