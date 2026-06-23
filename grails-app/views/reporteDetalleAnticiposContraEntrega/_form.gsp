<%@ page import="org.socymet.org.socymet.reportes.ReporteDetalleAnticiposContraEntrega" %>



<div class="fieldcontain ${hasErrors(bean: reporteDetalleAnticiposContraEntregaInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reporteDetalleAnticiposContraEntrega.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteDetalleAnticiposContraEntregaInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteDetalleAnticiposContraEntregaInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="reporteDetalleAnticiposContraEntrega.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteDetalleAnticiposContraEntregaInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteDetalleAnticiposContraEntregaInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="reporteDetalleAnticiposContraEntrega.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteDetalleAnticiposContraEntregaInstance?.fechaFinal}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteDetalleAnticiposContraEntregaInstance, field: 'numeroAnticipoInicial', 'error')} ">
	<label for="numeroAnticipoInicial">
		<g:message code="reporteDetalleAnticiposContraEntrega.numeroAnticipoInicial.label" default="Numero Anticipo Inicial" />
		
	</label>
	<g:textField name="numeroAnticipoInicial" inputmode="numeric" value="${reporteDetalleAnticiposContraEntregaInstance?.numeroAnticipoInicial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteDetalleAnticiposContraEntregaInstance, field: 'numeroAnticipoFinal', 'error')} ">
	<label for="numeroAnticipoFinal">
		<g:message code="reporteDetalleAnticiposContraEntrega.numeroAnticipoFinal.label" default="Numero Anticipo Final" />
		
	</label>
	<g:textField name="numeroAnticipoFinal" inputmode="numeric" value="${reporteDetalleAnticiposContraEntregaInstance?.numeroAnticipoFinal}"/>
</div>

