<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesRecepcionadosOro" %>

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
	%{--<tr>--}%
	%{--<td style="width: 10px"><input type="radio" id="lotes" name="myGroup" value="3" /></td>--}%
	%{--<td style="font-weight: bold">Lotes</td>--}%
	%{--</tr>--}%
	%{--<tr>--}%
	%{--<td style="width: 10px"><input type="radio" id="lotesEmpresa" name="myGroup" value="4" /></td>--}%
	%{--<td style="font-weight: bold">Lotes y Empresa</td>--}%
	%{--</tr>--}%
	</tbody>
</table>

<h1 style="font-weight: bold">Parametros de busqueda:</h1>

<div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosOroInstance, field: 'deposito', 'error')} required">
	<label for="deposito">
		<g:message code="reporteLotesRecepcionadosOro.deposito.label" default="Deposito" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${reporteLotesRecepcionadosOroInstance?.deposito?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosOroInstance, field: 'elemento', 'error')} " style="display: none">
	<label for="elemento">
		<g:message code="reporteLotesRecepcionadosOro.elemento.label" default="Elemento" />

	</label>
	<g:select name="elemento" from="${['Oro']}" value="${reporteLotesRecepcionadosOroInstance?.elemento}" valueMessagePrefix="reporteLotesRecepcionadosOro.elemento"/>
</div>

<div id="_empresa" class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosOroInstance, field: 'empresa', 'error')} " style="display: none">
	<label for="empresa">
		<g:message code="reporteLotesRecepcionadosOro.empresa.label" default="Empresa" />

	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteLotesRecepcionadosOroInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosOroInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reporteLotesRecepcionadosOro.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteLotesRecepcionadosOroInstance?.fechaInicial}"  />
</div>

<div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosOroInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reporteLotesRecepcionadosOro.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteLotesRecepcionadosOroInstance?.fechaFinal}"  />
</div>

<div id="_loteInicial" class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosOroInstance, field: 'loteInicial', 'error')} " style="display: none">
	<label for="loteInicial">
		<g:message code="reporteLotesRecepcionadosOro.loteInicial.label" default="Lote Inicial" />

	</label>
	<g:textField name="loteInicial" inputmode="numeric" value="${reporteLotesRecepcionadosOroInstance?.loteInicial}"/>
</div>

<div id="_loteFinal" class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosOroInstance, field: 'loteFinal', 'error')} " style="display: none">
	<label for="loteFinal">
		<g:message code="reporteLotesRecepcionadosOro.loteFinal.label" default="Lote Final" />

	</label>
	<g:textField name="loteFinal" inputmode="numeric" value="${reporteLotesRecepcionadosOroInstance?.loteFinal}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosOroInstance, field: 'estado', 'error')} ">
	<label for="estado">
		<g:message code="reporteLotesRecepcionadosOro.estado.label" default="Estado" />

	</label>
	<g:select name="estado" from="${['NO LIQUIDADO','LIQUIDADO','Todos']}" value="${reporteLotesRecepcionadosOroInstance?.estado}" valueMessagePrefix="reporteLotesRecepcionadosOro.estado"/>
</div>
<br>
<input type="hidden" id="tipoReporte" name="tipoReporte" value="fechas"/>

<div id="_complejo" style="text-align: center">
	<g:actionSubmit class="reporte" controller="reporteLotesRecepcionadosOro" action="crearReporteOro" value="Generar Reporte" />
</div>
<div id="_plomoPlata" style="display: none; text-align: center">
	<g:actionSubmit class="reporte" controller="reporteLotesRecepcionadosOro" action="crearReportePlomoPlata" value="Generar Reporte" />
</div>
<div id="_zincPlata" style="display: none; text-align: center">
	<g:actionSubmit class="reporte" controller="reporteLotesRecepcionadosOro" action="crearReporteZincPlata" value="Generar Reporte" />
</div>
<div id="_cobrePlata" style="display: none; text-align: center">
	<g:actionSubmit class="reporte" controller="reporteLotesRecepcionadosOro" action="crearReporteCobrePlata" value="Generar Reporte" />
</div>