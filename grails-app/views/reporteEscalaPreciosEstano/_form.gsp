<%@ page import="org.socymet.org.socymet.reportes.ReporteEscalaPreciosEstano" %>



<div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosEstanoInstance, field: 'fechaCotizacion', 'error')} required">
	<label for="fechaCotizacion">
		<g:message code="reporteEscalaPreciosEstano.fechaCotizacion.label" default="Fecha Cotizacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaCotizacion" precision="day"  value="${reporteEscalaPreciosEstanoInstance?.fechaCotizacion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosEstanoInstance, field: 'cotizacionEstano', 'error')} ">
	<label for="cotizacionEstano">
		<g:message code="reporteEscalaPreciosEstano.cotizacionEstano.label" default="Cotizacion Estano" />
		
	</label>
	<g:field name="cotizacionEstano" value="${fieldValue(bean: reporteEscalaPreciosEstanoInstance, field: 'cotizacionEstano')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosEstanoInstance, field: 'tablaCotizacionEstano', 'error')} ">
	<label for="tablaCotizacionEstano">
		<g:message code="reporteEscalaPreciosEstano.tablaCotizacionEstano.label" default="Tabla Cotizacion Estano" />
		
	</label>
	<g:select id="tablaCotizacionEstano" name="tablaCotizacionEstano.id" from="${org.socymet.cotizaciones.TablaCotizacionEstano.list()}" optionKey="id" value="${reporteEscalaPreciosEstanoInstance?.tablaCotizacionEstano?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

