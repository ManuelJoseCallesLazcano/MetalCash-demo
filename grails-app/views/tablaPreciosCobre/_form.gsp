<%@ page import="org.socymet.cotizaciones.TablaPreciosCobre" %>



<div class="fieldcontain ${hasErrors(bean: tablaPreciosCobreInstance, field: 'nombreTabla', 'error')} required">
	<label for="nombreTabla">
		<g:message code="tablaPreciosCobre.nombreTabla.label" default="Nombre Tabla" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreTabla" required="" value="${tablaPreciosCobreInstance?.nombreTabla}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaPreciosCobreInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="tablaPreciosCobre.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" required="" value="${tablaPreciosCobreInstance?.empresa?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaPreciosCobreInstance, field: 'leyInicial', 'error')} required">
	<label for="leyInicial">
		<g:message code="tablaPreciosCobre.leyInicial.label" default="Ley Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyInicial" value="${fieldValue(bean: tablaPreciosCobreInstance, field: 'leyInicial')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaPreciosCobreInstance, field: 'leyFinal', 'error')} required">
	<label for="leyFinal">
		<g:message code="tablaPreciosCobre.leyFinal.label" default="Ley Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyFinal" value="${fieldValue(bean: tablaPreciosCobreInstance, field: 'leyFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaPreciosCobreInstance, field: 'valorPorPunto', 'error')} required">
	<label for="valorPorPunto">
		<g:message code="tablaPreciosCobre.valorPorPunto.label" default="Valor Por Punto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorPorPunto" value="${fieldValue(bean: tablaPreciosCobreInstance, field: 'valorPorPunto')}" required="" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Tabla de Cotizaciones</h1>
<g:hiddenField name="tablaDePrecios" value="${tablaPreciosCobreInstance?.tablaDePrecios}"/>

<div style="text-align: center;">
    <button type="button" id="generar" style="margin-left: auto; margin-right: auto;">GENERAR</button>
</div>
<div style="width: 270px; margin-left: auto; margin-right: auto;"><table id="list4"></table></div>

