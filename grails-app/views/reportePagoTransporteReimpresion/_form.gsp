<%@ page import="org.socymet.org.socymet.reportes.ReportePagoTransporteReimpresion" %>



<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteReimpresionInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reportePagoTransporteReimpresion.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Estano','Plata','Wolfran','Antimonio','Complejo','Plomo Plata','Zinc Plata']}" value="${reportePagoTransporteReimpresionInstance?.elemento}" valueMessagePrefix="reportePagoTransporteReimpresion.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteReimpresionInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reportePagoTransporteReimpresion.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reportePagoTransporteReimpresionInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteReimpresionInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reportePagoTransporteReimpresion.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reportePagoTransporteReimpresionInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteReimpresionInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reportePagoTransporteReimpresion.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reportePagoTransporteReimpresionInstance?.fechaFinal}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteReimpresionInstance, field: 'loteInicial', 'error')} ">
	<label for="loteInicial">
		<g:message code="reportePagoTransporteReimpresion.loteInicial.label" default="Lote Inicial" />
		
	</label>
	<g:textField name="loteInicial" inputmode="numeric" value="${reportePagoTransporteReimpresionInstance?.loteInicial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteReimpresionInstance, field: 'loteFinal', 'error')} ">
	<label for="loteFinal">
		<g:message code="reportePagoTransporteReimpresion.loteFinal.label" default="Lote Final" />
		
	</label>
	<g:textField name="loteFinal" inputmode="numeric" value="${reportePagoTransporteReimpresionInstance?.loteFinal}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoTransporteReimpresionInstance, field: 'motivoReimpresion', 'error')} required">
	<label for="motivoReimpresion">
		<g:message code="reportePagoTransporteReimpresion.motivoReimpresion.label" default="Motivo Reimpresion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="motivoReimpresion" required="" value="${reportePagoTransporteReimpresionInstance?.motivoReimpresion}"/>
</div>

