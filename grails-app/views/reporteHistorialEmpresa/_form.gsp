<%@ page import="org.socymet.org.socymet.reportes.ReporteHistorialEmpresa" %>



<div class="fieldcontain ${hasErrors(bean: reporteHistorialEmpresaInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reporteHistorialEmpresa.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteHistorialEmpresaInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHistorialEmpresaInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="reporteHistorialEmpresa.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteHistorialEmpresaInstance?.fechaInicial}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHistorialEmpresaInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="reporteHistorialEmpresa.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteHistorialEmpresaInstance?.fechaFinal}" default="none" noSelection="['': '']" />
</div>

