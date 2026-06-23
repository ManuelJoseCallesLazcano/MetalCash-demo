<%@ page import="org.socymet.org.socymet.reportes.ReporteCompraDiaria" %>



<div class="fieldcontain ${hasErrors(bean: reporteCompraDiariaInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reporteCompraDiaria.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteCompraDiariaInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompraDiariaInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="reporteCompraDiaria.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteCompraDiariaInstance?.fechaInicial}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompraDiariaInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="reporteCompraDiaria.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteCompraDiariaInstance?.fechaFinal}" default="none" noSelection="['': '']" />
</div>

