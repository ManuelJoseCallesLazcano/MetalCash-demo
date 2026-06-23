<%@ page import="org.socymet.org.socymet.reportes.PlanillaDeLiquidacion" %>



<div class="fieldcontain ${hasErrors(bean: planillaDeLiquidacionInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="planillaDeLiquidacion.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${planillaDeLiquidacionInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: planillaDeLiquidacionInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="planillaDeLiquidacion.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datepickerUI name="fechaInicial" value="${planillaDeLiquidacionInstance?.fechaInicial ?: new Date()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: planillaDeLiquidacionInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="planillaDeLiquidacion.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datepickerUI name="fechaFinal" value="${planillaDeLiquidacionInstance?.fechaFinal ?: new Date()}"/>
</div>

