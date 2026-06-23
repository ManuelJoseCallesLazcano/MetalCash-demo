<%@ page import="org.socymet.org.socymet.reportes.ReporteAnticipos" %>



<div class="fieldcontain ${hasErrors(bean: reporteAnticiposInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reporteAnticipos.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${reporteAnticiposInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteAnticiposInstance, field: 'cliente', 'error')} ">
	<label for="cliente">
		<g:message code="reporteAnticipos.cliente.label" default="Cliente" />
		
	</label>
	<g:select id="cliente" name="cliente.id" from="${org.socymet.proveedor.Cliente.list([sort: 'nombre'])}" optionKey="id" value="${reporteAnticiposInstance?.cliente?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteAnticiposInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="reporteAnticipos.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteAnticiposInstance?.fechaInicial}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteAnticiposInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="reporteAnticipos.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteAnticiposInstance?.fechaFinal}" default="none" noSelection="['': '']" />
</div>

