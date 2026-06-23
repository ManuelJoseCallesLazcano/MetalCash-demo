<%@ page import="org.socymet.liquidacion.AsignacionLoteConjuntoComplejo" %>



<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoComplejoInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="asignacionLoteConjuntoComplejo.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${asignacionLoteConjuntoComplejoInstance?.lote}"/>
</div>

<g:hiddenField name="liquidacionId" value="${asignacionLoteConjuntoComplejoInstance?.liquidacionId}"/>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoComplejoInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="asignacionLoteConjuntoComplejo.nombreCliente.label" default="Nombre Cliente"  class="amarillo" readonly="true"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${asignacionLoteConjuntoComplejoInstance?.nombreCliente}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoComplejoInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="asignacionLoteConjuntoComplejo.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${asignacionLoteConjuntoComplejoInstance?.nombreEmpresa}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoComplejoInstance, field: 'fechaDeRecepcion', 'error')} ">
    <label for="fechaDeRecepcion">
        <g:message code="asignacionLoteConjuntoComplejo.fechaDeRecepcion.label" default="Fecha De Recepcion" />

    </label>
    <g:textField name="fechaDeRecepcion" value="${asignacionLoteConjuntoComplejoInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoComplejoInstance, field: 'fechaDeLiquidacion', 'error')} ">
    <label for="fechaDeLiquidacion">
        <g:message code="asignacionLoteConjuntoComplejo.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />

    </label>
    <g:textField name="fechaDeLiquidacion" value="${asignacionLoteConjuntoComplejoInstance?.fechaDeLiquidacion}"  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoComplejoInstance, field: 'kilosNetosSecos', 'error')} required">
    <label for="kilosNetosSecos">
        <g:message code="asignacionLoteConjuntoComplejo.kilosNetosSecos.label" default="Kilos Netos Secos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="kilosNetosSecos" value="${fieldValue(bean: asignacionLoteConjuntoComplejoInstance, field: 'kilosNetosSecos')}" required=""  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoComplejoInstance, field: 'porcentajeZinc', 'error')} required">
	<label for="porcentajeZinc">
		<g:message code="asignacionLoteConjuntoComplejo.porcentajeZinc.label" default="Porcentaje Zinc" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeZinc" value="${fieldValue(bean: asignacionLoteConjuntoComplejoInstance, field: 'porcentajeZinc')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoComplejoInstance, field: 'porcentajePlomo', 'error')} required">
	<label for="porcentajePlomo">
		<g:message code="asignacionLoteConjuntoComplejo.porcentajePlomo.label" default="Porcentaje Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajePlomo" value="${fieldValue(bean: asignacionLoteConjuntoComplejoInstance, field: 'porcentajePlomo')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoComplejoInstance, field: 'porcentajePlata', 'error')} required">
	<label for="porcentajePlata">
		<g:message code="asignacionLoteConjuntoComplejo.porcentajePlata.label" default="Porcentaje Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajePlata" value="${fieldValue(bean: asignacionLoteConjuntoComplejoInstance, field: 'porcentajePlata')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoComplejoInstance, field: 'conjuntoDestino', 'error')} required">
	<label for="conjuntoDestino">
		<g:message code="asignacionLoteConjuntoComplejo.conjuntoDestino.label" default="Conjunto Destino" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="conjuntoDestino" required="" value="${asignacionLoteConjuntoComplejoInstance?.conjuntoDestino}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoComplejoInstance, field: 'motivo', 'error')} required">
	<label for="motivo">
		<g:message code="asignacionLoteConjuntoComplejo.motivo.label" default="Motivo" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="motivo" required="" value="${asignacionLoteConjuntoComplejoInstance?.motivo}" size="100"/>
</div>

