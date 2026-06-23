<%@ page import="org.socymet.reportesAcopiadoras.LotesLiquidados" %>

<g:hiddenField name="tipoReporte" value="fechas" />

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

<h1 style="font-weight: bold">Parametros de busqueda:</h1>

<div id="_empresa" class="fieldcontain ${hasErrors(bean: lotesLiquidadosInstance, field: 'empresa', 'error')} " style="display: none">
	<label for="empresa">
		<g:message code="lotesLiquidados.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${lotesLiquidadosInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: lotesLiquidadosInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="lotesLiquidados.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${lotesLiquidadosInstance?.fechaInicial}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: lotesLiquidadosInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="lotesLiquidados.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${lotesLiquidadosInstance?.fechaFinal}"  />

</div>

<br/>

<div id="_resultadosComplejo">
	<div style="text-align: center;">
		<g:actionSubmit class="reporte" controller="lotesLiquidados" action="crearReporteComplejo" value="Generar Reporte" />
	</div>
</div>

