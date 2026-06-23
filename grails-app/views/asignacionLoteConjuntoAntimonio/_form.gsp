<%@ page import="org.socymet.liquidacion.AsignacionLoteConjuntoAntimonio" %>



<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoAntimonioInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="asignacionLoteConjuntoAntimonio.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${asignacionLoteConjuntoAntimonioInstance?.lote}"/>
</div>

<g:hiddenField name="liquidacionId" value="${asignacionLoteConjuntoAntimonioInstance?.liquidacionId}"/>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoAntimonioInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="asignacionLoteConjuntoAntimonio.nombreCliente.label" default="Nombre Cliente"  class="amarillo" readonly="true"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${asignacionLoteConjuntoAntimonioInstance?.nombreCliente}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoAntimonioInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="asignacionLoteConjuntoAntimonio.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${asignacionLoteConjuntoAntimonioInstance?.nombreEmpresa}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoAntimonioInstance, field: 'fechaDeRecepcion', 'error')} ">
    <label for="fechaDeRecepcion">
        <g:message code="asignacionLoteConjuntoAntimonio.fechaDeRecepcion.label" default="Fecha De Recepcion" />

    </label>
    <g:textField name="fechaDeRecepcion" value="${asignacionLoteConjuntoAntimonioInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoAntimonioInstance, field: 'fechaDeLiquidacion', 'error')} ">
    <label for="fechaDeLiquidacion">
        <g:message code="asignacionLoteConjuntoAntimonio.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />

    </label>
    <g:textField name="fechaDeLiquidacion" value="${asignacionLoteConjuntoAntimonioInstance?.fechaDeLiquidacion}"  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoAntimonioInstance, field: 'kilosNetosSecos', 'error')} required">
    <label for="kilosNetosSecos">
        <g:message code="asignacionLoteConjuntoAntimonio.kilosNetosSecos.label" default="Kilos Netos Secos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="kilosNetosSecos" value="${fieldValue(bean: asignacionLoteConjuntoAntimonioInstance, field: 'kilosNetosSecos')}" required=""  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoAntimonioInstance, field: 'porcentajeAntimonio', 'error')} required">
    <label for="porcentajeAntimonio">
        <g:message code="asignacionLoteConjuntoAntimonio.porcentajeAntimonio.label" default="Porcentaje Antimonio" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeAntimonio" value="${fieldValue(bean: asignacionLoteConjuntoAntimonioInstance, field: 'porcentajeAntimonio')}" required=""  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoAntimonioInstance, field: 'conjuntoDestino', 'error')} required">
    <label for="conjuntoDestino">
        <g:message code="asignacionLoteConjuntoAntimonio.conjuntoDestino.label" default="Conjunto Destino" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="conjuntoDestino" required="" value="${asignacionLoteConjuntoAntimonioInstance?.conjuntoDestino}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoAntimonioInstance, field: 'motivo', 'error')} required">
    <label for="motivo">
        <g:message code="asignacionLoteConjuntoAntimonio.motivo.label" default="Motivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="motivo" required="" value="${asignacionLoteConjuntoAntimonioInstance?.motivo}" size="100"/>
</div>

