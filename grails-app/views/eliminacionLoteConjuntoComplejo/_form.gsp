<%@ page import="org.socymet.liquidacion.EliminacionLoteConjuntoComplejo" %>



<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoComplejoInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="eliminacionLoteConjuntoComplejo.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${eliminacionLoteConjuntoComplejoInstance?.lote}"/>
</div>

<g:hiddenField name="liquidacionId" value="${eliminacionLoteConjuntoComplejoInstance?.liquidacionId}"/>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoComplejoInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="eliminacionLoteConjuntoComplejo.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${eliminacionLoteConjuntoComplejoInstance?.nombreCliente}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoComplejoInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="eliminacionLoteConjuntoComplejo.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${eliminacionLoteConjuntoComplejoInstance?.nombreEmpresa}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoComplejoInstance, field: 'fechaDeRecepcion', 'error')} ">
    <label for="fechaDeRecepcion">
        <g:message code="eliminacionLoteConjuntoComplejo.fechaDeRecepcion.label" default="Fecha De Recepcion"/>

    </label>
    <g:textField name="fechaDeRecepcion" value="${eliminacionLoteConjuntoComplejoInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoComplejoInstance, field: 'fechaDeLiquidacion', 'error')} ">
    <label for="fechaDeLiquidacion">
        <g:message code="eliminacionLoteConjuntoComplejo.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />

    </label>
    <g:textField name="fechaDeLiquidacion" value="${eliminacionLoteConjuntoComplejoInstance?.fechaDeLiquidacion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoComplejoInstance, field: 'kilosNetosSecos', 'error')} required">
    <label for="kilosNetosSecos">
        <g:message code="eliminacionLoteConjuntoComplejo.kilosNetosSecos.label" default="Kilos Netos Secos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="kilosNetosSecos" value="${fieldValue(bean: eliminacionLoteConjuntoComplejoInstance, field: 'kilosNetosSecos')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoComplejoInstance, field: 'porcentajeZinc', 'error')} required">
	<label for="porcentajeZinc">
		<g:message code="eliminacionLoteConjuntoComplejo.porcentajeZinc.label" default="Porcentaje Zinc" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeZinc" value="${fieldValue(bean: eliminacionLoteConjuntoComplejoInstance, field: 'porcentajeZinc')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoComplejoInstance, field: 'porcentajePlomo', 'error')} required">
	<label for="porcentajePlomo">
		<g:message code="eliminacionLoteConjuntoComplejo.porcentajePlomo.label" default="Porcentaje Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajePlomo" value="${fieldValue(bean: eliminacionLoteConjuntoComplejoInstance, field: 'porcentajePlomo')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoComplejoInstance, field: 'porcentajePlata', 'error')} required">
	<label for="porcentajePlata">
		<g:message code="eliminacionLoteConjuntoComplejo.porcentajePlata.label" default="Porcentaje Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajePlata" value="${fieldValue(bean: eliminacionLoteConjuntoComplejoInstance, field: 'porcentajePlata')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoComplejoInstance, field: 'conjuntoOriginal', 'error')} required">
    <label for="conjuntoOriginal">
        <g:message code="eliminacionLoteConjuntoComplejo.conjuntoOriginal.label" default="Conjunto Original" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="conjuntoOriginal" required="" value="${eliminacionLoteConjuntoComplejoInstance?.conjuntoOriginal}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoComplejoInstance, field: 'motivo', 'error')} required">
    <label for="motivo">
        <g:message code="eliminacionLoteConjuntoComplejo.motivo.label" default="Motivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="motivo" required="" value="${eliminacionLoteConjuntoComplejoInstance?.motivo}" size="100"/>
</div>

