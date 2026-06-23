<%@ page import="org.socymet.org.socymet.reportes.Reimpresion" %>



<div class="fieldcontain ${hasErrors(bean: reimpresionInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="reimpresion.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${reimpresionInstance?.fecha}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reimpresionInstance, field: 'nombreReporte', 'error')} required">
	<label for="nombreReporte">
		<g:message code="reimpresion.nombreReporte.label" default="Nombre Reporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreReporte" required="" value="${reimpresionInstance?.nombreReporte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reimpresionInstance, field: 'identificadorDocumento', 'error')} required">
	<label for="identificadorDocumento">
		<g:message code="reimpresion.identificadorDocumento.label" default="Identificador Documento" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="identificadorDocumento" required="" value="${reimpresionInstance?.identificadorDocumento}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reimpresionInstance, field: 'lote', 'error')} ">
	<label for="lote">
		<g:message code="reimpresion.lote.label" default="Lote" />
		
	</label>
	<g:textField name="lote" value="${reimpresionInstance?.lote}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reimpresionInstance, field: 'motivo', 'error')} ">
	<label for="motivo">
		<g:message code="reimpresion.motivo.label" default="Motivo" />
		
	</label>
	<g:textField name="motivo" value="${reimpresionInstance?.motivo}"/>
</div>

