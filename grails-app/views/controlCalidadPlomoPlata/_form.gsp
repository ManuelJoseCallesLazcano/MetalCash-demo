<%@ page import="org.socymet.calidad.ControlCalidadPlomoPlata" %>


<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="controlCalidadPlomoPlata.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${controlCalidadPlomoPlataInstance?.lote}"/>
    <g:hiddenField name="recepcionDeComplejo.id" value="${controlCalidadPlomoPlataInstance?.recepcionDeComplejo?.id}"/>
    <g:hiddenField name="empresa.id" value="${controlCalidadPlomoPlataInstance?.empresa?.id}"/>
    <g:hiddenField name="tablasIds" value="${controlCalidadPlomoPlataInstance?.tablasIds}"/>
    <g:hiddenField name="terminosIds" value="${controlCalidadPlomoPlataInstance?.terminosIds}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="controlCalidadPlomoPlata.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${controlCalidadPlomoPlataInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="controlCalidadPlomoPlata.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${controlCalidadPlomoPlataInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="controlCalidadPlomoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${controlCalidadPlomoPlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'condicionDeEntrega', 'error')} required">
    <label for="condicionDeEntrega">
        <g:message code="controlCalidadPlomoPlata.condicionDeEntrega.label" default="Condicion De Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="condicionDeEntrega" required="" value="${controlCalidadPlomoPlataInstance?.condicionDeEntrega}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'cantidadDeSacos', 'error')} required">
    <label for="cantidadDeSacos">
        <g:message code="controlCalidadPlomoPlata.cantidadDeSacos.label" default="Cantidad De Sacos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="cantidadDeSacos" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'cantidadDeSacos')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="controlCalidadPlomoPlata.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'estadoDelLote', 'error')} required">
    <label for="estadoDelLote">
        <g:message code="controlCalidadPlomoPlata.estadoDelLote.label" default="Estado Del Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="estadoDelLote" required="" value="${controlCalidadPlomoPlataInstance?.estadoDelLote}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'nombreLaboratorio', 'error')} required">
    <label for="nombreLaboratorio">
        <g:message code="controlCalidadPlomoPlata.nombreLaboratorio.label" default="Nombre Laboratorio" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreLaboratorio" required="" value="${controlCalidadPlomoPlataInstance?.nombreLaboratorio}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'numeroAnalisis', 'error')} required">
    <label for="numeroAnalisis">
        <g:message code="controlCalidadPlomoPlata.numeroAnalisis.label" default="Numero Analisis" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="numeroAnalisis" inputmode="numeric" required="" value="${controlCalidadPlomoPlataInstance?.numeroAnalisis}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'fechaAnalisis', 'error')} required">
    <label for="fechaAnalisis">
        <g:message code="controlCalidadPlomoPlata.fechaAnalisis.label" default="Fecha Analisis" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaAnalisis" precision="day"  value="${controlCalidadPlomoPlataInstance?.fechaAnalisis}"  />
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
                <g:message code="controlCalidadPlomoPlata.porcentajeMermaPromexbol.label" default="Merma" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
            <g:field name="porcentajeMermaPromexbol" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeMermaPromexbol')}" required="" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeZincCliente">
                <g:message code="controlCalidadPlomoPlata.porcentajeZincCliente.label" default="Plomo" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajePlomoPromexbol', 'error')} required">
            <g:field name="porcentajePlomoPromexbol" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajePlomoPromexbol')}" required="" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeZincFinal">
                <g:message code="controlCalidadPlomoPlata.porcentajeZincFinal.label" default="Plata" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajePlataPromexbol', 'error')} required">
            <g:field name="porcentajePlataPromexbol" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajePlataPromexbol')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeHumedadPromexbol">
                <g:message code="controlCalidadPlomoPlata.porcentajeHumedadPromexbol.label" default="Humedad" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
            <g:field name="porcentajeHumedadPromexbol" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeHumedadPromexbol')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="porcentajeMermaCliente" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeMermaCliente')}"/>
<g:hiddenField name="porcentajeHumedadCliente" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeHumedadCliente')}"/>
<g:hiddenField name="porcentajePlomoCliente" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajePlomoCliente')}"/>
<g:hiddenField name="porcentajePlataCliente" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajePlataCliente')}"/>

<g:hiddenField name="porcentajeMermaFinal" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeMermaFinal')}"/>
<g:hiddenField name="porcentajeHumedadFinal" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeHumedadFinal')}"/>
<g:hiddenField name="porcentajePlomoFinal" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajePlomoFinal')}"/>
<g:hiddenField name="porcentajePlataFinal" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajePlataFinal')}"/>

<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'modoValoracion', 'error')} " style="display: none">
    <label for="modoValoracion">
        <g:message code="controlCalidadPlomoPlata.modoValoracion.label" default="Modo Valoracion" />

    </label>
    <g:select name="modoValoracion" from="${['TERMINOS DE CONTRATO','TABLA']}" value="${controlCalidadPlomoPlataInstance?.modoValoracion}" valueMessagePrefix="controlCalidadPlomoPlata.modoValoracion"/>
</div>

<div id="_porcentajeArsenico" class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeArsenico', 'error')} required" style="display:none">
    <h1 style="font-weight: bold">Porcentajes de Elementos Penalizables</h1>
    <label for="porcentajeArsenico">
        <g:message code="controlCalidadPlomoPlata.porcentajeArsenico.label" default="Porcentaje Arsenico" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeArsenico" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeArsenico')}" required=""  inputmode="decimal"/>
</div>

<div id="_porcentajeAntimonio" class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeAntimonio', 'error')} required" style="display:none">
    <label for="porcentajeAntimonio">
        <g:message code="controlCalidadPlomoPlata.porcentajeAntimonio.label" default="Porcentaje Antimonio" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeAntimonio" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeAntimonio')}" required=""  inputmode="decimal"/>
</div>

<div id="_porcentajeSilice" class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeSilice', 'error')} required" style="display:none">
    <label for="porcentajeSilice">
        <g:message code="controlCalidadPlomoPlata.porcentajeSilice.label" default="Porcentaje Silice" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeSilice" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeSilice')}" required=""  inputmode="decimal"/>
</div>

<div id="_porcentajeBismuto" class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeBismuto', 'error')} required" style="display:none">
    <label for="porcentajeBismuto">
        <g:message code="controlCalidadPlomoPlata.porcentajeBismuto.label" default="Porcentaje Bismuto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeBismuto" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeBismuto')}" required=""  inputmode="decimal"/>
</div>

<div id="_porcentajeEstano" class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeEstano', 'error')} required" style="display:none">
    <label for="porcentajeEstano">
        <g:message code="controlCalidadPlomoPlata.porcentajeEstano.label" default="Porcentaje Estano" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeEstano" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeEstano')}" required=""  inputmode="decimal"/>
</div>

<div id="_porcentajeZinc" class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeZinc', 'error')} required" style="display:none">
    <label for="porcentajeZinc">
        <g:message code="controlCalidadPlomoPlata.porcentajeZinc.label" default="Porcentaje Zinc" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeZinc" value="${fieldValue(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeZinc')}" required=""  inputmode="decimal"/>
</div>


<div class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="controlCalidadPlomoPlata.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${controlCalidadPlomoPlataInstance?.observaciones}" size="100"/>
</div>

