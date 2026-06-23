<%@ page import="org.socymet.org.socymet.reportes.ReporteRetenciones" %>



<div class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reporteRetenciones.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Complejo','Plomo Plata','Zinc Plata','Cobre Plata']}" value="${reporteRetencionesInstance?.elemento}" valueMessagePrefix="reporteRetenciones.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reporteRetenciones.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteRetencionesInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'tipoRetencion', 'error')} ">
	<label for="tipoRetencion">
		<g:message code="reporteRetenciones.tipoRetencion.label" default="Tipo Retencion" />
		
	</label>
	<g:select name="tipoRetencion" from="${['DE LEY','OTRA']}" value="${reporteRetencionesInstance?.tipoRetencion}" valueMessagePrefix="reporteRetenciones.tipoRetencion" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="reporteRetenciones.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteRetencionesInstance?.fechaInicial}" default="none" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="reporteRetenciones.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteRetencionesInstance?.fechaFinal}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'loteInicial', 'error')} ">
	<label for="loteInicial">
		<g:message code="reporteRetenciones.loteInicial.label" default="Lote Inicial" />
		
	</label>
	<g:textField name="loteInicial" inputmode="numeric" value="${reporteRetencionesInstance?.loteInicial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'loteFinal', 'error')} ">
	<label for="loteFinal">
		<g:message code="reporteRetenciones.loteFinal.label" default="Lote Final" />
		
	</label>
	<g:textField name="loteFinal" inputmode="numeric" value="${reporteRetencionesInstance?.loteFinal}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteRetencionesInstance, field: 'ignorarLotes', 'error')} ">
	<label for="ignorarLotes">
		<g:message code="reporteRetenciones.ignorarLotes.label" default="Ignorar Lotes" />
		
	</label>
	<g:textField name="ignorarLotes" value="${reporteRetencionesInstance?.ignorarLotes}"/>
</div>

