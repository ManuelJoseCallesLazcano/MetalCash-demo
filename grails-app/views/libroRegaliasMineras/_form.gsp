<%@ page import="org.socymet.org.socymet.reportes.LibroRegaliasMineras" %>



<div class="fieldcontain ${hasErrors(bean: libroRegaliasMinerasInstance, field: 'elemento', 'error')} " style="display: none;">
    <label for="elemento">
        <g:message code="libroRegaliasMineras.elemento.label" default="Elemento" />

    </label>
    <g:select name="elemento" from="${['Complejo']}" value="${libroRegaliasMinerasInstance?.elemento}" valueMessagePrefix="libroRegaliasMineras.elemento" />
</div>

<div class="fieldcontain ${hasErrors(bean: libroRegaliasMinerasInstance, field: 'fechaInicial', 'error')} ">
    <label for="fechaInicial">
        <g:message code="libroRegaliasMineras.fechaInicial.label" default="Fecha Inicial" />

    </label>
    <g:datepickerUI name="fechaInicial" value="${libroRegaliasMinerasInstance?.fechaInicial ?: new Date()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: libroRegaliasMinerasInstance, field: 'fechaFinal', 'error')} ">
    <label for="fechaFinal">
        <g:message code="libroRegaliasMineras.fechaFinal.label" default="Fecha Final" />

    </label>
    <g:datepickerUI name="fechaFinal" value="${libroRegaliasMinerasInstance?.fechaFinal ?: new Date()}"/>
</div>

<br>

<div id="_resultadosEstano" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="libroRegaliasMineras" action="crearReporteEstano" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosPlata" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="libroRegaliasMineras" action="crearReportePlata" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosWolfran" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="libroRegaliasMineras" action="crearReporteWolfran" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosAntimonio" style="display: none">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="libroRegaliasMineras" action="crearReporteAntimonio" value="Generar Reporte" />
    </div>
</div>

<div id="_resultadosComplejo">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="libroRegaliasMineras" action="crearReporteComplejo" value="Generar Reporte" />
    </div>
</div>