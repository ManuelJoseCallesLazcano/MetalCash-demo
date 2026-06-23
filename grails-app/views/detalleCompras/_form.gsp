<%@ page import="org.socymet.org.socymet.reportes.DetalleCompras" %>



<div class="fieldcontain ${hasErrors(bean: detalleComprasInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="detalleCompras.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${detalleComprasInstance?.empresa?.id}"  class="many-to-one, chosen-select" style="width: 350px"/>

</div>

<div class="fieldcontain ${hasErrors(bean: detalleComprasInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="detalleCompras.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${detalleComprasInstance?.fechaInicial}" years="${2021..2025}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: detalleComprasInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="detalleCompras.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${detalleComprasInstance?.fechaFinal}" years="${2021..2025}" />

</div>

<br>

<div>
	<div style="text-align: center;">
		<g:actionSubmit class="reporte" controller="detalleCompras" action="crearReporte" value="Generar Reporte" />
	</div>
</div>