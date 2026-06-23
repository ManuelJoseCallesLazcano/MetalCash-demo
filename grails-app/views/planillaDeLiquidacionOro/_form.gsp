<%@ page import="org.socymet.org.socymet.reportes.PlanillaDeLiquidacionOro" %>

<h1 style="font-weight: bold">Parametros de busqueda:</h1>
<g:hiddenField name="tipoReporte" value="fechasEmpresa" />
<div id="_empresa" class="fieldcontain ${hasErrors(bean: planillaDeLiquidacionOroInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="planillaDeLiquidacionOro.empresa.label" default="Empresa" />

	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${planillaDeLiquidacionOroInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: planillaDeLiquidacionOroInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="planillaDeLiquidacionOro.fechaInicial.label" default="Fecha Inicial" />

	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${planillaDeLiquidacionOroInstance?.fechaInicial}" noSelection="['': '']" />
</div>

<div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: planillaDeLiquidacionOroInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="planillaDeLiquidacionOro.fechaFinal.label" default="Fecha Final" />

	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${planillaDeLiquidacionOroInstance?.fechaFinal}" noSelection="['': '']" />
</div>

<br/>

<div id="_resultadosEstano">
	<div style="text-align: center;">
		<g:actionSubmit class="reporte" controller="planillaDeLiquidacionOro" action="crearReporte" value="Generar Reporte" />
	</div>
</div>    