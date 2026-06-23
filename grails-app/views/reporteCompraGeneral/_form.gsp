<%@ page import="org.socymet.org.socymet.reportes.ReporteCompraGeneral" %>



<div class="fieldcontain ${hasErrors(bean: reporteCompraGeneralInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reporteCompraGeneral.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Complejo','Plomo-Plata','Zinc-Plata','Cobre-Plata']}" value="${reporteCompraGeneralInstance?.elemento}" valueMessagePrefix="reporteCompraGeneral.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompraGeneralInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="reporteCompraGeneral.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteCompraGeneralInstance?.fechaInicial}" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompraGeneralInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="reporteCompraGeneral.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteCompraGeneralInstance?.fechaFinal}" noSelection="['': '']" />
</div>

<br>

<div id="_resultadosEstano" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteCompraGeneral" action="crearReporteEstano" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosPlata" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteCompraGeneral" action="crearReportePlata" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosWolfran" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteCompraGeneral" action="crearReporteWolfran" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosAntimonio" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteCompraGeneral" action="crearReporteAntimonio" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosComplejo" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteCompraGeneral" action="crearReporteComplejo" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosPlomoPlata" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteCompraGeneral" action="crearReportePlomoPlata" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosZincPlata" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteCompraGeneral" action="crearReporteZincPlata" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosCobrePlata" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reporteCompraGeneral" action="crearReporteCobrePlata" value="Generar Reporte" />
    </div>
</div>