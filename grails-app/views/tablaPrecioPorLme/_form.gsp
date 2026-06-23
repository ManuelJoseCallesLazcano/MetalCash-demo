<%@ page import="org.socymet.cotizaciones.TablaPrecioPorLme" %>



<div class="fieldcontain ${hasErrors(bean: tablaPrecioPorLmeInstance, field: 'nombreTabla', 'error')} required">
	<label for="nombreTabla">
		<g:message code="tablaPrecioPorLme.nombreTabla.label" default="Nombre Tabla" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreTabla" required="" value="${tablaPrecioPorLmeInstance?.nombreTabla}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaPrecioPorLmeInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="tablaPrecioPorLme.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" required="" value="${tablaPrecioPorLmeInstance?.empresa?.id}" class="many-to-one"/>
</div>

<div id="radio2" style="height: 30px">
    <div class="fieldcontain">
        <label style="float: left; width: 25%; overflow: hidden">
            <g:message code="tablaPrecioPorLme.naturalezaMineral.label" default="Naturaleza Mineral" />

        </label>
    </div>

    <div class="ui-button" style="float: left; width: 50%; overflow: hidden; text-align: start">
        <input type="radio" id="radioSulfuro" value="sulfuro" name="radio2" checked="checked"><label for="radioSulfuro">SULFURO</label>
        <input type="radio" id="radioOxido" value="oxido" name="radio2"><label for="radioOxido">OXIDO</label>
    </div>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaPrecioPorLmeInstance, field: 'naturalezaMineral', 'error')} required" style="display: none">
	<label for="naturalezaMineral">
		<g:message code="tablaPrecioPorLme.naturalezaMineral.label" default="Naturaleza Mineral" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="naturalezaMineral" from="${['SULFURO','OXIDO']}" required="" value="${tablaPrecioPorLmeInstance?.naturalezaMineral}" valueMessagePrefix="tablaPrecioPorLme.naturalezaMineral"/>
</div>

<h1 style="font-weight: bold">Tabla de Precios</h1>

<div class="fieldcontain ${hasErrors(bean: tablaPrecioPorLmeInstance, field: 'cotizacionDiariaDeMinerales', 'error')} required">
	<label for="cotizacionDiariaDeMinerales">
		<g:message code="tablaPrecioPorLme.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cotizacionDiariaDeMinerales" name="cotizacionDiariaDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionDiariaDeMinerales.list([sort: 'fecha', order: 'desc'])}" optionKey="id" required="" value="${tablaPrecioPorLmeInstance?.cotizacionDiariaDeMinerales?.id}" class="many-to-one"/>
</div>
<g:hiddenField name="cotizacionDiariaPlata" readonly="readonly"/>

<div class="fieldcontain ${hasErrors(bean: tablaPrecioPorLmeInstance, field: 'leyPlata', 'error')} required">
	<label for="leyPlata">
		<g:message code="tablaPrecioPorLme.leyPlata.label" default="Ley Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyPlata" value="${fieldValue(bean: tablaPrecioPorLmeInstance, field: 'leyPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaPrecioPorLmeInstance, field: 'porcentajeLme', 'error')} required">
	<label for="porcentajeLme">
		<g:message code="tablaPrecioPorLme.porcentajeLme.label" default="Porcentaje Lme" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeLme" value="${fieldValue(bean: tablaPrecioPorLmeInstance, field: 'porcentajeLme')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaPrecioPorLmeInstance, field: 'valorPorTonelada', 'error')} required">
	<label for="valorPorTonelada">
		<g:message code="tablaPrecioPorLme.valorPorTonelada.label" default="Valor Por Tonelada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorPorTonelada" value="${fieldValue(bean: tablaPrecioPorLmeInstance, field: 'valorPorTonelada')}" required="" class="amarillo" readonly="readonly"/>
</div>

<g:hiddenField name="tablaDePrecios" value="${tablaPrecioPorLmeInstance?.tablaDePrecios}"/>

<div style="text-align: center;">
    <br>
    <button type="button" id="generar" style="margin-left: auto; margin-right: auto;">AGREGAR</button>
    <button type="button" id="actualizar" style="margin-left: auto; margin-right: auto;">ACTUALIZAR SELECCIONADO</button>
    <button type="button" id="eliminar" style="margin-left: auto; margin-right: auto;">ELIMINAR SELECCIONADO</button>
</div>
<div style="width: 360px; margin-left: auto; margin-right: auto;"><table id="list4"></table></div>