<%@ page import="org.socymet.reportesAcopiadoras.LotesRecepcionados" %>

<h1 style="font-weight: bold">Listar por:</h1>

<table style="width: 500px;" class="center">
	<tbody>
	<tr>
		<td style="width: 10px"><input type="radio" id="fechas" name="myGroup" value="1" checked="checked" /></td>
		<td style="font-weight: bold">Fechas</td>
	</tr>
	<tr>
		<td style="width: 10px"><input type="radio" id="fechasEmpresa" name="myGroup" value="2" /></td>
		<td style="font-weight: bold">Fechas y Empresa</td>
	</tr>
	</tbody>
</table>

<input type="hidden" id="tipoReporte" name="tipoReporte" value="fechas"/>

<h1 style="font-weight: bold">Parametros de busqueda:</h1>

<div id="_empresa" class="fieldcontain ${hasErrors(bean: lotesRecepcionadosInstance, field: 'empresa', 'error')} " style="display: none">
	<label for="empresa">
		<g:message code="lotesRecepcionados.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${lotesRecepcionadosInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: lotesRecepcionadosInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="lotesRecepcionados.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${lotesRecepcionadosInstance?.fechaInicial}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: lotesRecepcionadosInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="lotesRecepcionados.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${lotesRecepcionadosInstance?.fechaFinal}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: lotesRecepcionadosInstance, field: 'estado', 'error')} required">
	<label for="estado">
		<g:message code="lotesRecepcionados.estado.label" default="Estado" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="estado" from="${['NO LIQUIDADO','LIQUIDADO','Todos']}" required="" value="${lotesRecepcionadosInstance?.estado}" valueMessagePrefix="lotesRecepcionados.estado"/>

</div>

<br>

<div id="_complejo" style="text-align: center">
	<g:actionSubmit class="reporte" controller="lotesRecepcionados" action="crearReporteComplejo" value="Generar Reporte" />
</div>
