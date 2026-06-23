<%@ page import="org.socymet.liquidacion.EliminacionLoteConjuntoEstano" %>



<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoEstanoInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="eliminacionLoteConjuntoEstano.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${eliminacionLoteConjuntoEstanoInstance?.lote}"/>
</div>

<g:hiddenField name="liquidacionId" value="${eliminacionLoteConjuntoEstanoInstance?.liquidacionId}"/>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoEstanoInstance, field: 'nombreCliente', 'error')} required">
	<label for="nombreCliente">
		<g:message code="eliminacionLoteConjuntoEstano.nombreCliente.label" default="Nombre Cliente" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCliente" required="" value="${eliminacionLoteConjuntoEstanoInstance?.nombreCliente}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoEstanoInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="eliminacionLoteConjuntoEstano.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreEmpresa" required="" value="${eliminacionLoteConjuntoEstanoInstance?.nombreEmpresa}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoEstanoInstance, field: 'fechaDeRecepcion', 'error')} ">
	<label for="fechaDeRecepcion">
		<g:message code="eliminacionLoteConjuntoEstano.fechaDeRecepcion.label" default="Fecha De Recepcion"/>
		
	</label>
	<g:textField name="fechaDeRecepcion" value="${eliminacionLoteConjuntoEstanoInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoEstanoInstance, field: 'fechaDeLiquidacion', 'error')} ">
	<label for="fechaDeLiquidacion">
		<g:message code="eliminacionLoteConjuntoEstano.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
		
	</label>
	<g:textField name="fechaDeLiquidacion" value="${eliminacionLoteConjuntoEstanoInstance?.fechaDeLiquidacion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoEstanoInstance, field: 'kilosNetosSecos', 'error')} required">
	<label for="kilosNetosSecos">
		<g:message code="eliminacionLoteConjuntoEstano.kilosNetosSecos.label" default="Kilos Netos Secos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="kilosNetosSecos" value="${fieldValue(bean: eliminacionLoteConjuntoEstanoInstance, field: 'kilosNetosSecos')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoEstanoInstance, field: 'porcentajeEstano', 'error')} required">
	<label for="porcentajeEstano">
		<g:message code="eliminacionLoteConjuntoEstano.porcentajeEstano.label" default="Porcentaje Estano" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeEstano" value="${fieldValue(bean: eliminacionLoteConjuntoEstanoInstance, field: 'porcentajeEstano')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoEstanoInstance, field: 'conjuntoOriginal', 'error')} required">
	<label for="conjuntoOriginal">
		<g:message code="eliminacionLoteConjuntoEstano.conjuntoOriginal.label" default="Conjunto Original" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="conjuntoOriginal" required="" value="${eliminacionLoteConjuntoEstanoInstance?.conjuntoOriginal}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoEstanoInstance, field: 'motivo', 'error')} required">
	<label for="motivo">
		<g:message code="eliminacionLoteConjuntoEstano.motivo.label" default="Motivo" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="motivo" required="" value="${eliminacionLoteConjuntoEstanoInstance?.motivo}" size="100"/>
</div>

