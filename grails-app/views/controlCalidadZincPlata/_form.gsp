<%@ page import="org.socymet.calidad.ControlCalidadZincPlata" %>


<div class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="controlCalidadZincPlata.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${controlCalidadZincPlataInstance?.lote}"/>
    <g:hiddenField name="recepcionDeComplejo.id" value="${controlCalidadZincPlataInstance?.recepcionDeComplejo?.id}"/>
    <g:hiddenField name="empresa.id" value="${controlCalidadZincPlataInstance?.empresa?.id}"/>
    <g:hiddenField name="tablasIds" value="${controlCalidadZincPlataInstance?.tablasIds}"/>
    <g:hiddenField name="terminosIds" value="${controlCalidadZincPlataInstance?.terminosIds}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="controlCalidadZincPlata.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${controlCalidadZincPlataInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="controlCalidadZincPlata.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${controlCalidadZincPlataInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="controlCalidadZincPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${controlCalidadZincPlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'cantidadDeSacos', 'error')} required">
    <label for="cantidadDeSacos">
        <g:message code="controlCalidadZincPlata.cantidadDeSacos.label" default="Cantidad De Sacos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="cantidadDeSacos" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'cantidadDeSacos')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="controlCalidadZincPlata.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'estadoDelLote', 'error')} required">
    <label for="estadoDelLote">
        <g:message code="controlCalidadZincPlata.estadoDelLote.label" default="Estado Del Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="estadoDelLote" required="" value="${controlCalidadZincPlataInstance?.estadoDelLote}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'nombreLaboratorio', 'error')} required">
    <label for="nombreLaboratorio">
        <g:message code="controlCalidadZincPlata.nombreLaboratorio.label" default="Nombre Laboratorio" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreLaboratorio" required="" value="${controlCalidadZincPlataInstance?.nombreLaboratorio}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'numeroAnalisis', 'error')} required">
    <label for="numeroAnalisis">
        <g:message code="controlCalidadZincPlata.numeroAnalisis.label" default="Numero Analisis" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="numeroAnalisis" inputmode="numeric" required="" value="${controlCalidadZincPlataInstance?.numeroAnalisis}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'fechaAnalisis', 'error')} required">
    <label for="fechaAnalisis">
        <g:message code="controlCalidadZincPlata.fechaAnalisis.label" default="Fecha Analisis" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaAnalisis" precision="day"  value="${controlCalidadZincPlataInstance?.fechaAnalisis}"  />
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
                <g:message code="controlCalidadZincPlata.porcentajeMermaPromexbol.label" default="Merma" />
            </label>
        </td>
        
        <td class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
            <g:field name="porcentajeMermaPromexbol" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'porcentajeMermaPromexbol')}" required="" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeZincFinal">
                <g:message code="controlCalidadZincPlata.porcentajeZincCliente.label" default="Zinc" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'porcentajeZincPromexbol', 'error')} required">
            <g:field name="porcentajeZincPromexbol" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'porcentajeZincPromexbol')}" required="" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajePlataFinal">
                <g:message code="controlCalidadZincPlata.porcentajeZincFinal.label" default="Plata" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'porcentajePlataPromexbol', 'error')} required">
            <g:field name="porcentajePlataPromexbol" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'porcentajePlataPromexbol')}" required="" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeHumedadPromexbol">
                <g:message code="controlCalidadZincPlata.porcentajeHumedadPromexbol.label" default="Humedad" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
            <g:field name="porcentajeHumedadPromexbol" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'porcentajeHumedadPromexbol')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="porcentajeMermaCliente" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'porcentajeMermaCliente')}"/>
<g:hiddenField name="porcentajeHumedadCliente" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'porcentajeHumedadCliente')}"/>
<g:hiddenField name="porcentajeZincCliente" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'porcentajeZincCliente')}"/>
<g:hiddenField name="porcentajePlataCliente" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'porcentajePlataCliente')}"/>

<g:hiddenField name="porcentajeMermaFinal" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'porcentajeMermaFinal')}"/>
<g:hiddenField name="porcentajeHumedadFinal" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'porcentajeHumedadFinal')}"/>
<g:hiddenField name="porcentajeZincFinal" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'porcentajeZincFinal')}"/>
<g:hiddenField name="porcentajePlataFinal" value="${fieldValue(bean: controlCalidadZincPlataInstance, field: 'porcentajePlataFinal')}"/>

<div class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'modoValoracion', 'error')} " style="display: none">
    <label for="modoValoracion">
        <g:message code="controlCalidadZincPlata.modoValoracion.label" default="Modo Valoracion" />

    </label>
    <g:select name="modoValoracion" from="${['TERMINOS DE CONTRATO','TABLA']}" value="${controlCalidadZincPlataInstance?.modoValoracion}" valueMessagePrefix="controlCalidadZincPlata.modoValoracion"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="controlCalidadZincPlata.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${controlCalidadZincPlataInstance?.observaciones}" size="100"/>
</div>

