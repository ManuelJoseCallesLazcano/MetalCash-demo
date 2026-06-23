<%@ page import="org.socymet.cotizaciones.TablaCotizacionAntimonio" %>



<div class="fieldcontain ${hasErrors(bean: tablaCotizacionAntimonioInstance, field: 'nombreDeTabla', 'error')} required">
    <label for="nombreDeTabla">
        <g:message code="tablaCotizacionAntimonio.nombreDeTabla.label" default="Nombre De Tabla" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreDeTabla" required="" value="${tablaCotizacionAntimonioInstance?.nombreDeTabla}"/>
    <g:hiddenField name="tablaDeCotizaciones" value="${tablaCotizacionAntimonioInstance?.tablaDeCotizaciones}"/>
</div>

<br />
<div style="width: 300px; margin-left: auto; margin-right: auto;"><table id="list4"></table></div>

