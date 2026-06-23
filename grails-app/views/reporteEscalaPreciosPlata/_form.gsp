<%@ page import="org.socymet.org.socymet.reportes.ReporteEscalaPreciosPlata" %>



<div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosPlataInstance, field: 'fechaCotizacion', 'error')} required">
	<label for="fechaCotizacion">
		<g:message code="reporteEscalaPreciosPlata.fechaCotizacion.label" default="Fecha Cotizacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaCotizacion" precision="day"  value="${reporteEscalaPreciosPlataInstance?.fechaCotizacion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosPlataInstance, field: 'cotizacionPlata', 'error')} ">
	<label for="cotizacionPlata">
		<g:message code="reporteEscalaPreciosPlata.cotizacionPlata.label" default="Cotizacion Plata" />
		
	</label>
	<g:field name="cotizacionPlata" value="${fieldValue(bean: reporteEscalaPreciosPlataInstance, field: 'cotizacionPlata')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosPlataInstance, field: 'tablaCotizacionPlata', 'error')} ">
	<label for="tablaCotizacionPlata">
		<g:message code="reporteEscalaPreciosPlata.tablaCotizacionPlata.label" default="Tabla Cotizacion Plata" />
		
	</label>
	<g:select id="tablaCotizacionPlata" name="tablaCotizacionPlata.id" from="${org.socymet.cotizaciones.TablaCotizacionPlata.list()}" optionKey="id" value="${reporteEscalaPreciosPlataInstance?.tablaCotizacionPlata?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

