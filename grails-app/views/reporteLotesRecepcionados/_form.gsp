<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesRecepcionados" %>



<div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'deposito', 'error')} required">
	<label for="deposito">
		<g:message code="reporteLotesRecepcionados.deposito.label" default="Deposito" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${reporteLotesRecepcionadosInstance?.deposito?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reporteLotesRecepcionados.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Complejo','Plomo Plata','Zinc Plata','Cobre Plata']}" value="${reporteLotesRecepcionadosInstance?.elemento}" valueMessagePrefix="reporteLotesRecepcionados.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reporteLotesRecepcionados.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteLotesRecepcionadosInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reporteLotesRecepcionados.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datepickerUI name="fechaInicial" value="${reporteLotesRecepcionadosInstance?.fechaInicial ?: new Date()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reporteLotesRecepcionados.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datepickerUI name="fechaFinal" value="${reporteLotesRecepcionadosInstance?.fechaFinal ?: new Date()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'loteInicial', 'error')} ">
	<label for="loteInicial">
		<g:message code="reporteLotesRecepcionados.loteInicial.label" default="Lote Inicial" />
		
	</label>
	<g:textField name="loteInicial" inputmode="numeric" value="${reporteLotesRecepcionadosInstance?.loteInicial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'loteFinal', 'error')} ">
	<label for="loteFinal">
		<g:message code="reporteLotesRecepcionados.loteFinal.label" default="Lote Final" />
		
	</label>
	<g:textField name="loteFinal" inputmode="numeric" value="${reporteLotesRecepcionadosInstance?.loteFinal}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'estado', 'error')} ">
	<label for="estado">
		<g:message code="reporteLotesRecepcionados.estado.label" default="Estado" />
		
	</label>
	<g:select name="estado" from="${['NO LIQUIDADO','LIQUIDADO','Todos']}" value="${reporteLotesRecepcionadosInstance?.estado}" valueMessagePrefix="reporteLotesRecepcionados.estado" noSelection="['': '']"/>
</div>

