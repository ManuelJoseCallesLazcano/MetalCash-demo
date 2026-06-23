<%@ page import="org.socymet.liquidacion.EliminacionLoteConjuntoWolfran" %>



<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoWolfranInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="eliminacionLoteConjuntoWolfran.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${eliminacionLoteConjuntoWolfranInstance?.lote}"/>
</div>

<g:hiddenField name="liquidacionId" value="${eliminacionLoteConjuntoWolfranInstance?.liquidacionId}"/>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoWolfranInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="eliminacionLoteConjuntoWolfran.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${eliminacionLoteConjuntoWolfranInstance?.nombreCliente}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoWolfranInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="eliminacionLoteConjuntoWolfran.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${eliminacionLoteConjuntoWolfranInstance?.nombreEmpresa}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoWolfranInstance, field: 'fechaDeRecepcion', 'error')} ">
    <label for="fechaDeRecepcion">
        <g:message code="eliminacionLoteConjuntoWolfran.fechaDeRecepcion.label" default="Fecha De Recepcion"/>

    </label>
    <g:textField name="fechaDeRecepcion" value="${eliminacionLoteConjuntoWolfranInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoWolfranInstance, field: 'fechaDeLiquidacion', 'error')} ">
    <label for="fechaDeLiquidacion">
        <g:message code="eliminacionLoteConjuntoWolfran.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />

    </label>
    <g:textField name="fechaDeLiquidacion" value="${eliminacionLoteConjuntoWolfranInstance?.fechaDeLiquidacion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoWolfranInstance, field: 'kilosNetosSecos', 'error')} required">
    <label for="kilosNetosSecos">
        <g:message code="eliminacionLoteConjuntoWolfran.kilosNetosSecos.label" default="Kilos Netos Secos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="kilosNetosSecos" value="${fieldValue(bean: eliminacionLoteConjuntoWolfranInstance, field: 'kilosNetosSecos')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoWolfranInstance, field: 'porcentajeWolfran', 'error')} required">
    <label for="porcentajeWolfran">
        <g:message code="eliminacionLoteConjuntoWolfran.porcentajeWolfran.label" default="Porcentaje Wolfran" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeWolfran" value="${fieldValue(bean: eliminacionLoteConjuntoWolfranInstance, field: 'porcentajeWolfran')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoWolfranInstance, field: 'conjuntoOriginal', 'error')} required">
    <label for="conjuntoOriginal">
        <g:message code="eliminacionLoteConjuntoWolfran.conjuntoOriginal.label" default="Conjunto Original" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="conjuntoOriginal" required="" value="${eliminacionLoteConjuntoWolfranInstance?.conjuntoOriginal}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoWolfranInstance, field: 'motivo', 'error')} required">
    <label for="motivo">
        <g:message code="eliminacionLoteConjuntoWolfran.motivo.label" default="Motivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="motivo" required="" value="${eliminacionLoteConjuntoWolfranInstance?.motivo}" size="100"/>
</div>

