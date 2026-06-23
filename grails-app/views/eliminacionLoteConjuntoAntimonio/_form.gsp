<%@ page import="org.socymet.liquidacion.EliminacionLoteConjuntoAntimonio" %>



<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoAntimonioInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="eliminacionLoteConjuntoAntimonio.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${eliminacionLoteConjuntoAntimonioInstance?.lote}"/>
</div>

<g:hiddenField name="liquidacionId" value="${eliminacionLoteConjuntoAntimonioInstance?.liquidacionId}"/>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoAntimonioInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="eliminacionLoteConjuntoAntimonio.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${eliminacionLoteConjuntoAntimonioInstance?.nombreCliente}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoAntimonioInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="eliminacionLoteConjuntoAntimonio.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${eliminacionLoteConjuntoAntimonioInstance?.nombreEmpresa}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoAntimonioInstance, field: 'fechaDeRecepcion', 'error')} ">
    <label for="fechaDeRecepcion">
        <g:message code="eliminacionLoteConjuntoAntimonio.fechaDeRecepcion.label" default="Fecha De Recepcion"/>

    </label>
    <g:textField name="fechaDeRecepcion" value="${eliminacionLoteConjuntoAntimonioInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoAntimonioInstance, field: 'fechaDeLiquidacion', 'error')} ">
    <label for="fechaDeLiquidacion">
        <g:message code="eliminacionLoteConjuntoAntimonio.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />

    </label>
    <g:textField name="fechaDeLiquidacion" value="${eliminacionLoteConjuntoAntimonioInstance?.fechaDeLiquidacion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoAntimonioInstance, field: 'kilosNetosSecos', 'error')} required">
    <label for="kilosNetosSecos">
        <g:message code="eliminacionLoteConjuntoAntimonio.kilosNetosSecos.label" default="Kilos Netos Secos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="kilosNetosSecos" value="${fieldValue(bean: eliminacionLoteConjuntoAntimonioInstance, field: 'kilosNetosSecos')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoAntimonioInstance, field: 'porcentajeAntimonio', 'error')} required">
    <label for="porcentajeAntimonio">
        <g:message code="eliminacionLoteConjuntoAntimonio.porcentajeAntimonio.label" default="Porcentaje Antimonio" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeAntimonio" value="${fieldValue(bean: eliminacionLoteConjuntoAntimonioInstance, field: 'porcentajeAntimonio')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoAntimonioInstance, field: 'conjuntoOriginal', 'error')} required">
    <label for="conjuntoOriginal">
        <g:message code="eliminacionLoteConjuntoAntimonio.conjuntoOriginal.label" default="Conjunto Original" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="conjuntoOriginal" required="" value="${eliminacionLoteConjuntoAntimonioInstance?.conjuntoOriginal}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoAntimonioInstance, field: 'motivo', 'error')} required">
    <label for="motivo">
        <g:message code="eliminacionLoteConjuntoAntimonio.motivo.label" default="Motivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="motivo" required="" value="${eliminacionLoteConjuntoAntimonioInstance?.motivo}" size="100"/>
</div>

