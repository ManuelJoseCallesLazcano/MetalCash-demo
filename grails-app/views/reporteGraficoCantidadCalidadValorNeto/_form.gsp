<%@ page import="org.socymet.org.socymet.reportes.ReporteGraficoCantidadCalidadValorNeto" %>



<div class="fieldcontain ${hasErrors(bean: reporteGraficoCantidadCalidadValorNetoInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reporteGraficoCantidadCalidadValorNeto.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Complejo','Plomo-Plata','Zinc-Plata']}" value="${reporteGraficoCantidadCalidadValorNetoInstance?.elemento}" valueMessagePrefix="reporteGraficoCantidadCalidadValorNeto.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteGraficoCantidadCalidadValorNetoInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reporteGraficoCantidadCalidadValorNeto.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteGraficoCantidadCalidadValorNetoInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteGraficoCantidadCalidadValorNetoInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reporteGraficoCantidadCalidadValorNeto.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteGraficoCantidadCalidadValorNetoInstance?.fechaFinal}"  />
</div>

<br>

<div id="_resultadosEstano" style="display: none">
    <div style="text-align: center;">
        <button id="graficarEstano" name="graficarEstano" type="button">GRAFICAR</button>
    </div>
</div>

<div id="_resultadosPlata" style="display: none">
    <div style="text-align: center;">
        <button id="graficarPlata" name="graficarPlata" type="button">GRAFICAR</button>
    </div>
</div>

<div id="_resultadosWolfran" style="display: none">
    <div style="text-align: center;">
        <button id="graficarWolfran" name="graficarWolfran" type="button">GRAFICAR</button>
    </div>
</div>

<div id="_resultadosAntimonio" style="display: none">
    <div style="text-align: center;">
        <button id="graficarAntimonio" name="graficarAntimonio" type="button">GRAFICAR</button>
    </div>
</div>

<div id="_resultadosComplejo" style="display: none">
    <div style="text-align: center;">
        <button id="graficarComplejo" name="graficarComplejo" type="button">GRAFICAR</button>
    </div>
</div>

<div id="container" style="height: 800px; min-width: 110px"></div>