<%@ page import="org.socymet.liquidacion.AsignacionLoteConjuntoEstano" %>



<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoEstanoInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="asignacionLoteConjuntoEstano.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${asignacionLoteConjuntoEstanoInstance?.lote}"/>
</div>

<g:hiddenField name="liquidacionId" value="${asignacionLoteConjuntoEstanoInstance?.liquidacionId}"/>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoEstanoInstance, field: 'nombreCliente', 'error')} required">
	<label for="nombreCliente">
		<g:message code="asignacionLoteConjuntoEstano.nombreCliente.label" default="Nombre Cliente" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCliente" required="" value="${asignacionLoteConjuntoEstanoInstance?.nombreCliente}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoEstanoInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="asignacionLoteConjuntoEstano.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreEmpresa" required="" value="${asignacionLoteConjuntoEstanoInstance?.nombreEmpresa}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoEstanoInstance, field: 'fechaDeRecepcion', 'error')} ">
	<label for="fechaDeRecepcion">
		<g:message code="asignacionLoteConjuntoEstano.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		
	</label>
	<g:textField name="fechaDeRecepcion" value="${asignacionLoteConjuntoEstanoInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoEstanoInstance, field: 'fechaDeLiquidacion', 'error')} ">
	<label for="fechaDeLiquidacion">
		<g:message code="asignacionLoteConjuntoEstano.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
		
	</label>
	<g:textField name="fechaDeLiquidacion" value="${asignacionLoteConjuntoEstanoInstance?.fechaDeLiquidacion}"  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoEstanoInstance, field: 'kilosNetosSecos', 'error')} required">
	<label for="kilosNetosSecos">
		<g:message code="asignacionLoteConjuntoEstano.kilosNetosSecos.label" default="Kilos Netos Secos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosNetosSecos" value="${fieldValue(bean: asignacionLoteConjuntoEstanoInstance, field: 'kilosNetosSecos')}" required=""  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoEstanoInstance, field: 'porcentajeEstano', 'error')} required">
	<label for="porcentajeEstano">
		<g:message code="asignacionLoteConjuntoEstano.porcentajeEstano.label" default="Porcentaje Estano" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeEstano" value="${fieldValue(bean: asignacionLoteConjuntoEstanoInstance, field: 'porcentajeEstano')}" required=""  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoEstanoInstance, field: 'conjuntoDestino', 'error')} required">
	<label for="conjuntoDestino">
		<g:message code="asignacionLoteConjuntoEstano.conjuntoDestino.label" default="Conjunto Destino" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="conjuntoDestino" required="" value="${asignacionLoteConjuntoEstanoInstance?.conjuntoDestino}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoEstanoInstance, field: 'motivo', 'error')} required">
	<label for="motivo">
		<g:message code="asignacionLoteConjuntoEstano.motivo.label" default="Motivo" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="motivo" required="" value="${asignacionLoteConjuntoEstanoInstance?.motivo}" size="100"/>
</div>

