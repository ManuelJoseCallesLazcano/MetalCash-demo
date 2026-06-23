<%@ page import="org.socymet.cotizaciones.TablaCotizacionWolfran" %>



<div class="fieldcontain ${hasErrors(bean: tablaCotizacionWolfranInstance, field: 'nombreDeTabla', 'error')} required">
	<label for="nombreDeTabla">
		<g:message code="tablaCotizacionWolfran.nombreDeTabla.label" default="Nombre De Tabla" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreDeTabla" required="" value="${tablaCotizacionWolfranInstance?.nombreDeTabla}"/>
    <g:hiddenField name="tablaDeCotizaciones" value="${tablaCotizacionWolfranInstance?.tablaDeCotizaciones}"/>
</div>

<br />
<div style="width: 300px; margin-left: auto; margin-right: auto;"><table id="list4"></table></div>

