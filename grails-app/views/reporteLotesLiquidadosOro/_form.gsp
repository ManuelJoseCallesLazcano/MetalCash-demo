<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesLiquidadosOro" %>

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
<g:hiddenField name="tipoReporte" value="fechas" />
<div id="_empresa" class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosOroInstance, field: 'empresa', 'error')} " style="display: none">
	<label for="empresa">
		<g:message code="reporteLotesLiquidadosOro.empresa.label" default="Empresa" />

	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteLotesLiquidadosOroInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosOroInstance, field: 'deposito', 'error')} required">
	<label for="deposito">
		<g:message code="reporteLotesLiquidadosOro.deposito.label" default="Deposito" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${reporteLotesLiquidadosOroInstance?.deposito?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosOroInstance, field: 'elemento', 'error')} " style="display: none">
	<label for="elemento">
		<g:message code="reporteLotesLiquidadosOro.elemento.label" default="Elemento" />

	</label>
	<g:select name="elemento" from="${['Oro']}" value="${reporteLotesLiquidadosOroInstance?.elemento}" valueMessagePrefix="reporteLotesLiquidadosOro.elemento"/>
</div>

<div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosOroInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="reporteLotesLiquidadosOro.fechaInicial.label" default="Fecha Inicial" />

	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteLotesLiquidadosOroInstance?.fechaInicial}" noSelection="['': '']" />
</div>

<div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosOroInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="reporteLotesLiquidadosOro.fechaFinal.label" default="Fecha Final" />

	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteLotesLiquidadosOroInstance?.fechaFinal}" noSelection="['': '']" />
</div>

<br/>

<div id="_resultadosComplejo">
	<div style="text-align: center;">
		<g:actionSubmit class="reporte" controller="reporteLotesLiquidadosOro" action="crearReporteOro" value="Generar Reporte" />
	</div>
</div>

<div id="_resultadosPlomoPlata" style="display: none">
	<div style="text-align: center;">
		<g:actionSubmit class="reporte" controller="reporteLotesLiquidadosOro" action="crearReportePlomoPlata" value="Generar Reporte" />
	</div>
</div>

<div id="_resultadosZincPlata" style="display: none">
	<div style="text-align: center;">
		<g:actionSubmit class="reporte" controller="reporteLotesLiquidadosOro" action="crearReporteZincPlata" value="Generar Reporte" />
	</div>
</div>

<div id="_resultadosCobrePlata" style="display: none">
	<div style="text-align: center;">
		<g:actionSubmit class="reporte" controller="reporteLotesLiquidadosOro" action="crearReporteCobrePlata" value="Generar Reporte" />
	</div>
</div>