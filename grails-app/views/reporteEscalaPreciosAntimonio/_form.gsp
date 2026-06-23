<%@ page import="org.socymet.org.socymet.reportes.ReporteEscalaPreciosAntimonio" %>



<div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosAntimonioInstance, field: 'fechaCotizacion', 'error')} required">
	<label for="fechaCotizacion">
		<g:message code="reporteEscalaPreciosAntimonio.fechaCotizacion.label" default="Fecha Cotizacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaCotizacion" precision="day"  value="${reporteEscalaPreciosAntimonioInstance?.fechaCotizacion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosAntimonioInstance, field: 'tablaCotizacionAntimonio', 'error')} ">
	<label for="tablaCotizacionAntimonio">
		<g:message code="reporteEscalaPreciosAntimonio.tablaCotizacionAntimonio.label" default="Tabla Cotizacion Antimonio" />
		
	</label>
	<g:select id="tablaCotizacionAntimonio" name="tablaCotizacionAntimonio.id" from="${org.socymet.cotizaciones.TablaCotizacionAntimonio.list()}" optionKey="id" value="${reporteEscalaPreciosAntimonioInstance?.tablaCotizacionAntimonio?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<br>
<div style="text-align: center;">
    <g:actionSubmit class="reporte" controller="reporteEscalaPreciosAntimonio" action="crearReporteEscalaPreciosAntimonio" value="Generar Reporte" />
</div>

