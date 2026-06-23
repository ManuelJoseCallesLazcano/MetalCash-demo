<%@ page import="org.socymet.org.socymet.reportes.ReportePagoTransporte" %>



<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reportePagoTransporte.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Estano','Plata','Wolfran','Antimonio','Complejo','Plomo Plata','Zinc Plata']}" value="${reportePagoTransporteInstance?.elemento}" valueMessagePrefix="reportePagoTransporte.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reportePagoTransporte.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reportePagoTransporteInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reportePagoTransporte.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reportePagoTransporteInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reportePagoTransporte.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reportePagoTransporteInstance?.fechaFinal}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteInstance, field: 'loteInicial', 'error')} ">
	<label for="loteInicial">
		<g:message code="reportePagoTransporte.loteInicial.label" default="Lote Inicial" />
		
	</label>
	<g:textField name="loteInicial" inputmode="numeric" value="${reportePagoTransporteInstance?.loteInicial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteInstance, field: 'loteFinal', 'error')} ">
	<label for="loteFinal">
		<g:message code="reportePagoTransporte.loteFinal.label" default="Lote Final" />
		
	</label>
	<g:textField name="loteFinal" inputmode="numeric" value="${reportePagoTransporteInstance?.loteFinal}"/>
</div>

