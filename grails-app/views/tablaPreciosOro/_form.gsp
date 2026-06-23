<%@ page import="org.socymet.cotizaciones.TablaPreciosOro" %>



<div class="fieldcontain ${hasErrors(bean: tablaPreciosOroInstance, field: 'nombreTabla', 'error')} required">
	<label for="nombreTabla">
		<g:message code="tablaPreciosOro.nombreTabla.label" default="Nombre Tabla" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreTabla" required="" value="${tablaPreciosOroInstance?.nombreTabla}" size="90"/>

</div>

<h1 style="font-weight: bold">Tabla de Precios</h1>

<div class="fieldcontain">
	<label for="leyOro">LEY LAMAS gr/TMS</label>
	<input type="text" id="leyOro"/>
</div>

<div class="fieldcontain">
	<label for="valorTonelada">PRECIO TMS Bs</label>
	<input type="text" id="valorTonelada"/>
</div>

<div id="_botones" style="text-align: center">
	<br>
	<button id="agregar" type="button">AGREGAR</button>
	<button id="actualizar" type="button">ACTUALIZAR SELECCIONADO</button>
	<button id="eliminar" type="button">ELIMINAR SELECCIONADO</button>
</div>

<div style="width: 360px; margin-left: auto; margin-right: auto;"><table id="tablaPreciosOro"></table></div>

<div class="fieldcontain ${hasErrors(bean: tablaPreciosOroInstance, field: 'tablaPrecios', 'error')} required" style="display:none;">
	<label for="tablaPrecios">
		<g:message code="tablaPreciosOro.tablaPrecios.label" default="Tabla Precios" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="tablaPrecios" required="" value="${tablaPreciosOroInstance?.tablaPrecios}" size="90" readonly="readonly"/>

</div>

