<%@ page import="org.socymet.org.socymet.reportes.ReporteReliquidaciones" %>



<div class="fieldcontain ${hasErrors(bean: reporteReliquidacionesInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reporteReliquidaciones.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Estano','Plata','Wolfran','Antimonio','Complejo']}" value="${reporteReliquidacionesInstance?.elemento}" valueMessagePrefix="reporteReliquidaciones.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteReliquidacionesInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reporteReliquidaciones.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteReliquidacionesInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteReliquidacionesInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="reporteReliquidaciones.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteReliquidacionesInstance?.fechaInicial}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteReliquidacionesInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="reporteReliquidaciones.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteReliquidacionesInstance?.fechaFinal}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteReliquidacionesInstance, field: 'loteInicial', 'error')} ">
	<label for="loteInicial">
		<g:message code="reporteReliquidaciones.loteInicial.label" default="Lote Inicial" />
		
	</label>
	<g:textField name="loteInicial" inputmode="numeric" value="${reporteReliquidacionesInstance?.loteInicial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteReliquidacionesInstance, field: 'loteFinal', 'error')} ">
	<label for="loteFinal">
		<g:message code="reporteReliquidaciones.loteFinal.label" default="Lote Final" />
		
	</label>
	<g:textField name="loteFinal" inputmode="numeric" value="${reporteReliquidacionesInstance?.loteFinal}"/>
</div>

