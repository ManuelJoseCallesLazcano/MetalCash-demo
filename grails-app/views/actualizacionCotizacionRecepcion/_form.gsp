<%@ page import="org.socymet.recepcion.ActualizacionCotizacionRecepcion" %>

<div class="fieldcontain ${hasErrors(bean: actualizacionCotizacionRecepcionInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="actualizacionCotizacionRecepcion.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${actualizacionCotizacionRecepcionInstance?.fechaInicial}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: actualizacionCotizacionRecepcionInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="actualizacionCotizacionRecepcion.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${actualizacionCotizacionRecepcionInstance?.fechaFinal}"  />

</div>

<div id="_botones" style="text-align: center">
	<br>
	<button id="agregar" type="button">BUSCAR LOTES</button>
	<button id="quitar" type="button">QUITAR LOTE SELECCIONADO</button>
</div>

<div style="width: 750px; margin-left: auto; margin-right: auto;">
	<table id="detalleLotesTabla"></table>
</div>

<div class="fieldcontain ${hasErrors(bean: actualizacionCotizacionRecepcionInstance, field: 'detalleLotes', 'error')} required">
	<label for="detalleLotes">
		<g:message code="actualizacionCotizacionRecepcion.detalleLotes.label" default="Detalle Lotes" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="detalleLotes" required="" value="${actualizacionCotizacionRecepcionInstance?.detalleLotes}" readonly="readonly"/>

</div>

<h1 style="font-weight: bold">Cotizaci&oacute;n para la actualizaci&oacute;n</h1>

<div class="fieldcontain ${hasErrors(bean: actualizacionCotizacionRecepcionInstance, field: 'tipoCotizacion', 'error')} required">
	<label for="tipoCotizacion">
		<g:message code="actualizacionCotizacionRecepcion.tipoCotizacion.label" default="Tipo Cotizacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="tipoCotizacion" from="${['COTIZACION DIARIA','COTIZACION QUINCENAL']}" required="" value="${actualizacionCotizacionRecepcionInstance?.tipoCotizacion}" valueMessagePrefix="actualizacionCotizacionRecepcion.tipoCotizacion"/>

</div>

<div id="_cotizacionDiariaDeMinerales" class="fieldcontain ${hasErrors(bean: actualizacionCotizacionRecepcionInstance, field: 'cotizacionDiariaDeMinerales', 'error')} required">
	<label for="cotizacionDiariaDeMinerales">
		<g:message code="actualizacionCotizacionRecepcion.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cotizacionDiariaDeMinerales" name="cotizacionDiariaDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionDiariaDeMinerales.list()}" optionKey="id" required="" value="${actualizacionCotizacionRecepcionInstance?.cotizacionDiariaDeMinerales?.id}" class="many-to-one"/>

</div>

<div id="_cotizacionQuincenalDeMinerales" class="fieldcontain ${hasErrors(bean: actualizacionCotizacionRecepcionInstance, field: 'cotizacionQuincenalDeMinerales', 'error')} required" style="display: none">
	<label for="cotizacionQuincenalDeMinerales">
		<g:message code="actualizacionCotizacionRecepcion.cotizacionQuincenalDeMinerales.label" default="Cotizacion Quincenal De Minerales" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cotizacionQuincenalDeMinerales" name="cotizacionQuincenalDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionQuincenalDeMinerales.list()}" optionKey="id" required="" value="${actualizacionCotizacionRecepcionInstance?.cotizacionQuincenalDeMinerales?.id}" class="many-to-one"/>

</div>

<div id="_botones" style="text-align: center">
	<br>
	<button id="actualizar" type="button">ACTUALIZAR LOTES SELECCIONADOS</button>
</div>