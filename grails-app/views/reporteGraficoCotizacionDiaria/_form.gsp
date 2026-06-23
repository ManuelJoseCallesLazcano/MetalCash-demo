<%@ page import="org.socymet.org.socymet.reportes.ReporteGraficoCotizacionDiaria" %>



<div class="fieldcontain ${hasErrors(bean: reporteGraficoCotizacionDiariaInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reporteGraficoCotizacionDiaria.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Plata','Plomo','Zinc']}" value="${reporteGraficoCotizacionDiariaInstance?.elemento}" valueMessagePrefix="reporteGraficoCotizacionDiaria.elemento" noSelection="['': '']"/>
</div>

<div id="container" style="height: 400px; min-width: 310px"></div>

