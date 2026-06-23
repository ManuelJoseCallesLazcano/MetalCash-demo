<%@ page import="org.socymet.liquidacion.EliminacionLoteConjuntoPlata" %>



<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoPlataInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="eliminacionLoteConjuntoPlata.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${eliminacionLoteConjuntoPlataInstance?.lote}"/>
</div>

<g:hiddenField name="liquidacionId" value="${eliminacionLoteConjuntoPlataInstance?.liquidacionId}"/>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoPlataInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="eliminacionLoteConjuntoPlata.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${eliminacionLoteConjuntoPlataInstance?.nombreCliente}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoPlataInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="eliminacionLoteConjuntoPlata.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${eliminacionLoteConjuntoPlataInstance?.nombreEmpresa}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoPlataInstance, field: 'fechaDeRecepcion', 'error')} ">
    <label for="fechaDeRecepcion">
        <g:message code="eliminacionLoteConjuntoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion"/>

    </label>
    <g:textField name="fechaDeRecepcion" value="${eliminacionLoteConjuntoPlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoPlataInstance, field: 'fechaDeLiquidacion', 'error')} ">
    <label for="fechaDeLiquidacion">
        <g:message code="eliminacionLoteConjuntoPlata.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />

    </label>
    <g:textField name="fechaDeLiquidacion" value="${eliminacionLoteConjuntoPlataInstance?.fechaDeLiquidacion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoPlataInstance, field: 'kilosNetosSecos', 'error')} required">
    <label for="kilosNetosSecos">
        <g:message code="eliminacionLoteConjuntoPlata.kilosNetosSecos.label" default="Kilos Netos Secos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="kilosNetosSecos" value="${fieldValue(bean: eliminacionLoteConjuntoPlataInstance, field: 'kilosNetosSecos')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoPlataInstance, field: 'porcentajePlata', 'error')} required">
    <label for="porcentajePlata">
        <g:message code="eliminacionLoteConjuntoPlata.porcentajePlata.label" default="Porcentaje Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajePlata" value="${fieldValue(bean: eliminacionLoteConjuntoPlataInstance, field: 'porcentajePlata')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoPlataInstance, field: 'conjuntoOriginal', 'error')} required">
    <label for="conjuntoOriginal">
        <g:message code="eliminacionLoteConjuntoPlata.conjuntoOriginal.label" default="Conjunto Original" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="conjuntoOriginal" required="" value="${eliminacionLoteConjuntoPlataInstance?.conjuntoOriginal}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eliminacionLoteConjuntoPlataInstance, field: 'motivo', 'error')} required">
    <label for="motivo">
        <g:message code="eliminacionLoteConjuntoPlata.motivo.label" default="Motivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="motivo" required="" value="${eliminacionLoteConjuntoPlataInstance?.motivo}" size="100"/>
</div>

