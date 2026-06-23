<%@ page import="org.socymet.liquidacion.AsignacionLoteConjuntoWolfran" %>



<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoWolfranInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="asignacionLoteConjuntoWolfran.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${asignacionLoteConjuntoWolfranInstance?.lote}"/>
</div>

<g:hiddenField name="liquidacionId" value="${asignacionLoteConjuntoWolfranInstance?.liquidacionId}"/>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoWolfranInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="asignacionLoteConjuntoWolfran.nombreCliente.label" default="Nombre Cliente"  class="amarillo" readonly="true"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${asignacionLoteConjuntoWolfranInstance?.nombreCliente}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoWolfranInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="asignacionLoteConjuntoWolfran.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${asignacionLoteConjuntoWolfranInstance?.nombreEmpresa}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoWolfranInstance, field: 'fechaDeRecepcion', 'error')} ">
    <label for="fechaDeRecepcion">
        <g:message code="asignacionLoteConjuntoWolfran.fechaDeRecepcion.label" default="Fecha De Recepcion" />

    </label>
    <g:textField name="fechaDeRecepcion" value="${asignacionLoteConjuntoWolfranInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoWolfranInstance, field: 'fechaDeLiquidacion', 'error')} ">
    <label for="fechaDeLiquidacion">
        <g:message code="asignacionLoteConjuntoWolfran.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />

    </label>
    <g:textField name="fechaDeLiquidacion" value="${asignacionLoteConjuntoWolfranInstance?.fechaDeLiquidacion}"  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoWolfranInstance, field: 'kilosNetosSecos', 'error')} required">
    <label for="kilosNetosSecos">
        <g:message code="asignacionLoteConjuntoWolfran.kilosNetosSecos.label" default="Kilos Netos Secos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="kilosNetosSecos" value="${fieldValue(bean: asignacionLoteConjuntoWolfranInstance, field: 'kilosNetosSecos')}" required=""  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoWolfranInstance, field: 'porcentajeWolfran', 'error')} required">
    <label for="porcentajeWolfran">
        <g:message code="asignacionLoteConjuntoWolfran.porcentajeWolfran.label" default="Porcentaje Wolfran" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeWolfran" value="${fieldValue(bean: asignacionLoteConjuntoWolfranInstance, field: 'porcentajeWolfran')}" required=""  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoWolfranInstance, field: 'conjuntoDestino', 'error')} required">
    <label for="conjuntoDestino">
        <g:message code="asignacionLoteConjuntoWolfran.conjuntoDestino.label" default="Conjunto Destino" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="conjuntoDestino" required="" value="${asignacionLoteConjuntoWolfranInstance?.conjuntoDestino}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoWolfranInstance, field: 'motivo', 'error')} required">
    <label for="motivo">
        <g:message code="asignacionLoteConjuntoWolfran.motivo.label" default="Motivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="motivo" required="" value="${asignacionLoteConjuntoWolfranInstance?.motivo}" size="100"/>
</div>

