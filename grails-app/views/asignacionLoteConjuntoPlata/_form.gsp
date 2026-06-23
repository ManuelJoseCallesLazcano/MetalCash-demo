<%@ page import="org.socymet.liquidacion.AsignacionLoteConjuntoPlata" %>



<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoPlataInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="asignacionLoteConjuntoPlata.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${asignacionLoteConjuntoPlataInstance?.lote}"/>
</div>

<g:hiddenField name="liquidacionId" value="${asignacionLoteConjuntoPlataInstance?.liquidacionId}"/>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoPlataInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="asignacionLoteConjuntoPlata.nombreCliente.label" default="Nombre Cliente"  class="amarillo" readonly="true"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${asignacionLoteConjuntoPlataInstance?.nombreCliente}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoPlataInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="asignacionLoteConjuntoPlata.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${asignacionLoteConjuntoPlataInstance?.nombreEmpresa}" size="50" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoPlataInstance, field: 'fechaDeRecepcion', 'error')} ">
    <label for="fechaDeRecepcion">
        <g:message code="asignacionLoteConjuntoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />

    </label>
    <g:textField name="fechaDeRecepcion" value="${asignacionLoteConjuntoPlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoPlataInstance, field: 'fechaDeLiquidacion', 'error')} ">
    <label for="fechaDeLiquidacion">
        <g:message code="asignacionLoteConjuntoPlata.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />

    </label>
    <g:textField name="fechaDeLiquidacion" value="${asignacionLoteConjuntoPlataInstance?.fechaDeLiquidacion}"  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoPlataInstance, field: 'kilosNetosSecos', 'error')} required">
    <label for="kilosNetosSecos">
        <g:message code="asignacionLoteConjuntoPlata.kilosNetosSecos.label" default="Kilos Netos Secos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="kilosNetosSecos" value="${fieldValue(bean: asignacionLoteConjuntoPlataInstance, field: 'kilosNetosSecos')}" required=""  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoPlataInstance, field: 'porcentajePlata', 'error')} required">
    <label for="porcentajePlata">
        <g:message code="asignacionLoteConjuntoPlata.porcentajePlata.label" default="Porcentaje Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajePlata" value="${fieldValue(bean: asignacionLoteConjuntoPlataInstance, field: 'porcentajePlata')}" required=""  class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoPlataInstance, field: 'conjuntoDestino', 'error')} required">
    <label for="conjuntoDestino">
        <g:message code="asignacionLoteConjuntoPlata.conjuntoDestino.label" default="Conjunto Destino" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="conjuntoDestino" required="" value="${asignacionLoteConjuntoPlataInstance?.conjuntoDestino}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: asignacionLoteConjuntoPlataInstance, field: 'motivo', 'error')} required">
    <label for="motivo">
        <g:message code="asignacionLoteConjuntoPlata.motivo.label" default="Motivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="motivo" required="" value="${asignacionLoteConjuntoPlataInstance?.motivo}" size="100"/>
</div>

