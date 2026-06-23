<%@ page import="org.socymet.org.socymet.reportes.ReporteAnticiposContraEntrega" %>



<div class="fieldcontain ${hasErrors(bean: reporteAnticiposContraEntregaInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reporteAnticiposContraEntrega.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Complejo','Plomo Plata','Zinc Plata']}" value="${reporteAnticiposContraEntregaInstance?.elemento}" valueMessagePrefix="reporteAnticiposContraEntrega.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteAnticiposContraEntregaInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reporteAnticiposContraEntrega.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteAnticiposContraEntregaInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteAnticiposContraEntregaInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reporteAnticiposContraEntrega.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteAnticiposContraEntregaInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteAnticiposContraEntregaInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reporteAnticiposContraEntrega.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteAnticiposContraEntregaInstance?.fechaFinal}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteAnticiposContraEntregaInstance, field: 'loteInicial', 'error')} ">
	<label for="loteInicial">
		<g:message code="reporteAnticiposContraEntrega.loteInicial.label" default="Lote Inicial" />
		
	</label>
	<g:textField name="loteInicial" inputmode="numeric" value="${reporteAnticiposContraEntregaInstance?.loteInicial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteAnticiposContraEntregaInstance, field: 'loteFinal', 'error')} ">
	<label for="loteFinal">
		<g:message code="reporteAnticiposContraEntrega.loteFinal.label" default="Lote Final" />
		
	</label>
	<g:textField name="loteFinal" inputmode="numeric" value="${reporteAnticiposContraEntregaInstance?.loteFinal}"/>
</div>

