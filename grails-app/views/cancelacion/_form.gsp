<%@ page import="org.socymet.cancelacion.Cancelacion" %>



<div class="fieldcontain ${hasErrors(bean: cancelacionInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="cancelacion.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${cancelacionInstance?.lote}"/>
    <g:hiddenField name="liquidacionId" value="${cancelacionInstance?.liquidacionId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cancelacionInstance, field: 'nombreCliente', 'error')} required">
	<label for="nombreCliente">
		<g:message code="cancelacion.nombreCliente.label" default="Nombre Cliente" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCliente" required="" value="${cancelacionInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cancelacionInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="cancelacion.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreEmpresa" required="" value="${cancelacionInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cancelacionInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="cancelacion.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="fechaDeRecepcion" required="" value="${cancelacionInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cancelacionInstance, field: 'fechaDeLiquidacion', 'error')} required">
	<label for="fechaDeLiquidacion">
		<g:message code="cancelacion.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="fechaDeLiquidacion" required="" value="${cancelacionInstance?.fechaDeLiquidacion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cancelacionInstance, field: 'totalLiquidoPagable', 'error')} required">
	<label for="totalLiquidoPagable">
		<g:message code="cancelacion.totalLiquidoPagable.label" default="Total Liquido Pagable" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalLiquidoPagable" value="${fieldValue(bean: cancelacionInstance, field: 'totalLiquidoPagable')}" required="" class="verde" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cancelacionInstance, field: 'fechaDeCancelacion', 'error')} required">
	<label for="fechaDeCancelacion">
		<g:message code="cancelacion.fechaDeCancelacion.label" default="Fecha De Cancelacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeCancelacion" precision="day"  value="${cancelacionInstance?.fechaDeCancelacion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: cancelacionInstance, field: 'observaciones', 'error')} ">
	<label for="observaciones">
		<g:message code="cancelacion.observaciones.label" default="Observaciones" />
		
	</label>
	<g:textField name="observaciones" value="${cancelacionInstance?.observaciones}" size="90"/>
</div>

