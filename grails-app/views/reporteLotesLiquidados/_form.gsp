<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesLiquidados" %>



<div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reporteLotesLiquidados.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteLotesLiquidadosInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reporteLotesLiquidados.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Complejo','Plomo Plata','Zinc Plata','Cobre Plata']}" value="${reporteLotesLiquidadosInstance?.elemento}" valueMessagePrefix="reporteLotesLiquidados.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="reporteLotesLiquidados.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datepickerUI name="fechaInicial" value="${reporteLotesLiquidadosInstance?.fechaInicial ?: new Date()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="reporteLotesLiquidados.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datepickerUI name="fechaFinal" value="${reporteLotesLiquidadosInstance?.fechaFinal ?: new Date()}"/>
</div>

