<%@ page import="org.socymet.cotizaciones.AjustePrecioEstano" %>


<div class="fieldcontain ${hasErrors(bean: ajustePrecioEstanoInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="ajustePrecioEstano.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${ajustePrecioEstanoInstance?.fecha}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: ajustePrecioEstanoInstance, field: 'cotizacionDiariaDeMinerales', 'error')} required">
	<label for="cotizacionDiariaDeMinerales">
		<g:message code="ajustePrecioEstano.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cotizacionDiariaDeMinerales" name="cotizacionDiariaDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionDiariaDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${ajustePrecioEstanoInstance?.cotizacionDiariaDeMinerales?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ajustePrecioEstanoInstance, field: 'tablaCotizacionEstano', 'error')} required">
	<label for="tablaCotizacionEstano">
		<g:message code="ajustePrecioEstano.tablaCotizacionEstano.label" default="Tabla Cotizacion Estano" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="tablaCotizacionEstano" name="tablaCotizacionEstano.id" from="${org.socymet.cotizaciones.TablaCotizacionEstano.list()}" optionKey="id" required="" value="${ajustePrecioEstanoInstance?.tablaCotizacionEstano?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ajustePrecioEstanoInstance, field: 'margen', 'error')} required">
	<label for="margen">
		<g:message code="ajustePrecioEstano.margen.label" default="Margen" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="margen" value="${fieldValue(bean: ajustePrecioEstanoInstance, field: 'margen')}" required="" inputmode="decimal"/>

</div>

<h1 style="font-weight: bold">Fila Original de Tabla de Estano</h1>
<div style="width: 900px; margin-left: auto; margin-right: auto;"><table id="tablaFilaOriginal"></table></div>

<div class="fieldcontain ${hasErrors(bean: ajustePrecioEstanoInstance, field: 'filaOriginal', 'error')} required" style="display: none">
	<label for="filaOriginal">
		<g:message code="ajustePrecioEstano.filaOriginal.label" default="Fila Original" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="filaOriginal" cols="40" rows="5" maxlength="2000" required="" value="${ajustePrecioEstanoInstance?.filaOriginal}" readonly="readonly"/>

</div>

<h1 style="font-weight: bold">Fila Ajustada de Tabla de Estano</h1>
<div style="width: 900px; margin-left: auto; margin-right: auto;"><table id="tablaFilaAjustada"></table></div>

<div class="fieldcontain ${hasErrors(bean: ajustePrecioEstanoInstance, field: 'filaAjustada', 'error')} required" style="display: none">
	<label for="filaAjustada">
		<g:message code="ajustePrecioEstano.filaAjustada.label" default="Fila Ajustada" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="filaAjustada" cols="40" rows="5" maxlength="2000" required="" value="${ajustePrecioEstanoInstance?.filaAjustada}" readonly="readonly"/>

</div>

<div class="fieldcontain ${hasErrors(bean: ajustePrecioEstanoInstance, field: 'usuario', 'error')} required" style="display: none">
	<label for="usuario">
		<g:message code="ajustePrecioEstano.usuario.label" default="Usuario&#" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${org.socymet.seguridad.SecUser.list()}" optionKey="id" required="" value="${ajustePrecioEstanoInstance?.usuario?.id}" class="many-to-one"/>

</div>

