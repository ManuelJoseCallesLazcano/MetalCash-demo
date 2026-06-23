<%@ page import="org.socymet.calidad.ControlCalidadCobrePlata" %>


<div class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="controlCalidadCobrePlata.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${controlCalidadCobrePlataInstance?.lote}"/>
    <g:hiddenField name="recepcionDeComplejo.id" value="${controlCalidadCobrePlataInstance?.recepcionDeComplejo?.id}"/>
    <g:hiddenField name="empresa.id" value="${controlCalidadCobrePlataInstance?.empresa?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="controlCalidadCobrePlata.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${controlCalidadCobrePlataInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="controlCalidadCobrePlata.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${controlCalidadCobrePlataInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="controlCalidadCobrePlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${controlCalidadCobrePlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'cantidadDeSacos', 'error')} required">
    <label for="cantidadDeSacos">
        <g:message code="controlCalidadCobrePlata.cantidadDeSacos.label" default="Cantidad De Sacos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="cantidadDeSacos" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'cantidadDeSacos')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="controlCalidadCobrePlata.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'estadoDelLote', 'error')} required">
    <label for="estadoDelLote">
        <g:message code="controlCalidadCobrePlata.estadoDelLote.label" default="Estado Del Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="estadoDelLote" required="" value="${controlCalidadCobrePlataInstance?.estadoDelLote}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'nombreLaboratorio', 'error')} required">
    <label for="nombreLaboratorio">
        <g:message code="controlCalidadCobrePlata.nombreLaboratorio.label" default="Nombre Laboratorio" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreLaboratorio" required="" value="${controlCalidadCobrePlataInstance?.nombreLaboratorio}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'numeroAnalisis', 'error')} required">
    <label for="numeroAnalisis">
        <g:message code="controlCalidadCobrePlata.numeroAnalisis.label" default="Numero Analisis" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="numeroAnalisis" inputmode="numeric" required="" value="${controlCalidadCobrePlataInstance?.numeroAnalisis}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'fechaAnalisis', 'error')} required">
    <label for="fechaAnalisis">
        <g:message code="controlCalidadCobrePlata.fechaAnalisis.label" default="Fecha Analisis" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaAnalisis" precision="day"  value="${controlCalidadCobrePlataInstance?.fechaAnalisis}"  />
</div>

<h1 style="font-weight: bold">Detalle de Leyes</h1>

<table class="center" style="width: 70%;">
    <thead>
    <tr>
        <th style="width: 40%">ELEMENTO</th>
        <th style="width: 20%">LEY EMPRESA</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeMermaPromexbol">
                <g:message code="controlCalidadCobrePlata.porcentajeMermaPromexbol.label" default="Merma" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
            <g:field name="porcentajeMermaPromexbol" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'porcentajeMermaPromexbol')}" required="" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeZincCliente">
                <g:message code="controlCalidadCobrePlata.porcentajeZincCliente.label" default="Cobre" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'porcentajeCobrePromexbol', 'error')} required">
            <g:field name="porcentajeCobrePromexbol" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'porcentajeCobrePromexbol')}" required="" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeZincFinal">
                <g:message code="controlCalidadCobrePlata.porcentajeZincFinal.label" default="Plata" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'porcentajePlataPromexbol', 'error')} required">
            <g:field name="porcentajePlataPromexbol" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'porcentajePlataPromexbol')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeHumedadPromexbol">
                <g:message code="controlCalidadCobrePlata.porcentajeHumedadPromexbol.label" default="Humedad" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
            <g:field name="porcentajeHumedadPromexbol" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'porcentajeHumedadPromexbol')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="porcentajeMermaCliente" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'porcentajeMermaCliente')}"/>
<g:hiddenField name="porcentajeHumedadCliente" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'porcentajeHumedadCliente')}"/>
<g:hiddenField name="porcentajeCobreCliente" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'porcentajeCobreCliente')}"/>
<g:hiddenField name="porcentajePlataCliente" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'porcentajePlataCliente')}"/>

<g:hiddenField name="porcentajeMermaFinal" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'porcentajeMermaFinal')}"/>
<g:hiddenField name="porcentajeHumedadFinal" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'porcentajeHumedadFinal')}"/>
<g:hiddenField name="porcentajeCobreFinal" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'porcentajeCobreFinal')}"/>
<g:hiddenField name="porcentajePlataFinal" value="${fieldValue(bean: controlCalidadCobrePlataInstance, field: 'porcentajePlataFinal')}"/>

<div class="fieldcontain ${hasErrors(bean: controlCalidadCobrePlataInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="controlCalidadCobrePlata.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${controlCalidadCobrePlataInstance?.observaciones}" size="100"/>
</div>

