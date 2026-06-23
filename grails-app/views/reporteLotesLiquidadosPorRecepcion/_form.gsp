<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesLiquidadosPorRecepcion" %>



<div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reporteLotesLiquidadosPorRecepcion.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Complejo','Plomo Plata','Zinc Plata']}" value="${reporteLotesLiquidadosPorRecepcionInstance?.elemento}" valueMessagePrefix="reporteLotesLiquidadosPorRecepcion.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reporteLotesLiquidadosPorRecepcion.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteLotesLiquidadosPorRecepcionInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reporteLotesLiquidadosPorRecepcion.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteLotesLiquidadosPorRecepcionInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reporteLotesLiquidadosPorRecepcion.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteLotesLiquidadosPorRecepcionInstance?.fechaFinal}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'loteInicial', 'error')} ">
	<label for="loteInicial">
		<g:message code="reporteLotesLiquidadosPorRecepcion.loteInicial.label" default="Lote Inicial" />
		
	</label>
	<g:textField name="loteInicial" inputmode="numeric" value="${reporteLotesLiquidadosPorRecepcionInstance?.loteInicial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'loteFinal', 'error')} ">
	<label for="loteFinal">
		<g:message code="reporteLotesLiquidadosPorRecepcion.loteFinal.label" default="Lote Final" />
		
	</label>
	<g:textField name="loteFinal" inputmode="numeric" value="${reporteLotesLiquidadosPorRecepcionInstance?.loteFinal}"/>
</div>

