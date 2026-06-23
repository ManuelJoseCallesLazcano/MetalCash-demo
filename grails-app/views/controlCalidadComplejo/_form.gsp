<%@ page import="org.socymet.calidad.ControlCalidadComplejo" %>

<%-- ── Campos de sistema (ocultos) ─────────────────────────────────────────── --%>
<div id="_deposito" class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'deposito', 'has-error')}" style="display: none">
    <label class="col-sm-3 col-form-label">
        <g:message code="controlCalidadComplejo.deposito.label" default="Deposito" /> <span class="text-danger">*</span>
    </label>
    <div class="col-sm-4">
        <g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${controlCalidadComplejoInstance?.deposito?.id}" class="form-control many-to-one"/>
    </div>
</div>

<g:hiddenField name="empresa.id" value="${controlCalidadComplejoInstance?.empresa?.id}"/>
<g:hiddenField name="tablasIds" value="${controlCalidadComplejoInstance?.tablasIds}"/>
<g:hiddenField name="terminosIds" value="${controlCalidadComplejoInstance?.terminosIds}"/>

<%-- ══════════════════════════════════════════════════════════════════════
     LOTE Y DATOS DE RECEPCIÓN
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 class="form-section-title">Lote y Datos de Recepción</h5>

<div class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'recepcionDeComplejo', 'has-error')}">
    <label class="col-sm-3 col-form-label">
        <g:message code="controlCalidadComplejo.recepcionDeComplejo.label" default="Lote" /> <span class="text-danger">*</span>
    </label>
    <div class="col-sm-6">
        <g:if test="${controlCalidadComplejoInstance?.id}">
            <g:link controller="recepcionDeComplejo" action="show" id="${controlCalidadComplejoInstance?.recepcionDeComplejo?.id}" class="h5">
                ${controlCalidadComplejoInstance?.recepcionDeComplejo?.encodeAsHTML()}
            </g:link>
            <g:hiddenField id="recepcionDeComplejo" name="recepcionDeComplejo.id" value="${controlCalidadComplejoInstance?.recepcionDeComplejo?.id}"/>
        </g:if>
        <g:else>
            <g:select id="recepcionDeComplejo" name="recepcionDeComplejo.id"
                from="${org.socymet.recepcion.RecepcionDeComplejo.findAllByEstadoAnalisisAndEstadoDelLote('SIN ANALISIS', 'NO LIQUIDADO', [sort: 'id', order: 'desc'])}"
                optionKey="id" required="" value="${controlCalidadComplejoInstance?.recepcionDeComplejo?.id}"
                class="form-control many-to-one" style="width: 100%"/>
        </g:else>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'nombreCliente', 'has-error')}">
    <label class="col-sm-3 col-form-label"><g:message code="controlCalidadComplejo.nombreCliente.label" default="Cliente" /></label>
    <div class="col-sm-6">
        <g:textField name="nombreCliente" required="" value="${controlCalidadComplejoInstance?.recepcionDeComplejo?.cliente?.nombre}" class="form-control amarillo" readonly="true"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'nombreEmpresa', 'has-error')}">
    <label class="col-sm-3 col-form-label"><g:message code="controlCalidadComplejo.nombreEmpresa.label" default="Empresa" /></label>
    <div class="col-sm-6">
        <g:textField name="nombreEmpresa" required="" value="${controlCalidadComplejoInstance?.recepcionDeComplejo?.empresa?.toString()}" class="form-control amarillo" readonly="true"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'fechaDeRecepcion', 'has-error')}">
    <label class="col-sm-3 col-form-label"><g:message code="controlCalidadComplejo.fechaDeRecepcion.label" default="Fecha de Recepción" /></label>
    <div class="col-sm-4">
        <g:textField name="fechaDeRecepcion" required="" value="${controlCalidadComplejoInstance?.fechaDeRecepcion}" class="form-control amarillo" readonly="true"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'pesoBruto', 'has-error')}">
    <label class="col-sm-3 col-form-label"><g:message code="controlCalidadComplejo.pesoBruto.label" default="Peso Bruto" /></label>
    <div class="col-sm-4">
        <g:field name="pesoBruto" value="${controlCalidadComplejoInstance?.recepcionDeComplejo?.pesoBruto}" required="" class="form-control amarillo" readonly="true"/>
    </div>
</div>

<%-- Campos ocultos poblados por el autocompletado (necesarios para el binding/JS) --%>
<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Condición De Entrega</label>
    <div class="col-sm-4">
        <g:textField name="condicionDeEntrega" required="" value="${controlCalidadComplejoInstance?.condicionDeEntrega}" class="form-control amarillo" readonly="true"/>
    </div>
</div>
<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Cantidad De Sacos</label>
    <div class="col-sm-4">
        <g:field name="cantidadDeSacos" value="${controlCalidadComplejoInstance?.recepcionDeComplejo?.cantidadDeSacos}" required="" class="form-control amarillo" readonly="true"/>
    </div>
</div>
<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Estado Del Lote</label>
    <div class="col-sm-4">
        <g:textField name="estadoDelLote" required="" value="${controlCalidadComplejoInstance?.recepcionDeComplejo?.estadoDelLote}" class="form-control amarillo" readonly="true"/>
    </div>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     DATOS DEL ANÁLISIS
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 class="form-section-title">Datos del Análisis</h5>

<div class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'nombreLaboratorio', 'has-error')}">
    <label class="col-sm-3 col-form-label">
        <g:message code="controlCalidadComplejo.nombreLaboratorio.label" default="Laboratorio" /> <span class="text-danger">*</span>
    </label>
    <div class="col-sm-6">
        <g:textField name="nombreLaboratorio" required="" value="${controlCalidadComplejoInstance?.nombreLaboratorio}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'numeroAnalisis', 'has-error')}">
    <label class="col-sm-3 col-form-label">
        <g:message code="controlCalidadComplejo.numeroAnalisis.label" default="Análisis N°" /> <span class="text-danger">*</span>
    </label>
    <div class="col-sm-4">
        <g:textField name="numeroAnalisis" inputmode="numeric" required="" value="${controlCalidadComplejoInstance?.numeroAnalisis}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'fechaAnalisis', 'has-error')}">
    <label class="col-sm-3 col-form-label">
        <g:message code="controlCalidadComplejo.fechaAnalisis.label" default="Fecha" /> <span class="text-danger">*</span>
    </label>
    <div class="col-sm-4">
        <g:datepickerUI name="fechaAnalisis" value="${controlCalidadComplejoInstance?.fechaAnalisis ?: new Date()}"/>
    </div>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     DETALLE DE LEYES (LEY EMPRESA)
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 class="form-section-title">Detalle de Leyes</h5>

<%-- Merma oculta (valor por defecto gestionado en backend) --%>
<g:hiddenField name="porcentajeMermaPromexbol" value="${fieldValue(bean: controlCalidadComplejoInstance, field: 'porcentajeMermaPromexbol')}"/>

<div class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'porcentajeZincPromexbol', 'has-error')}">
    <label class="col-sm-3 col-form-label">
        <g:message code="controlCalidadComplejo.porcentajeZincPromexbol.label" default="Zinc" /> <span class="text-danger">*</span>
    </label>
    <div class="col-sm-3">
        <div class="input-group">
            <g:field type="number" name="porcentajeZincPromexbol" step="any" min="0" inputmode="decimal" required="" class="form-control" value="${controlCalidadComplejoInstance?.porcentajeZincPromexbol}"/>
            <div class="input-group-append"><span class="input-group-text">%</span></div>
        </div>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'porcentajePlomoPromexbol', 'has-error')}">
    <label class="col-sm-3 col-form-label">
        <g:message code="controlCalidadComplejo.porcentajePlomoPromexbol.label" default="Plomo" /> <span class="text-danger">*</span>
    </label>
    <div class="col-sm-3">
        <div class="input-group">
            <g:field type="number" name="porcentajePlomoPromexbol" step="any" min="0" inputmode="decimal" required="" class="form-control" value="${controlCalidadComplejoInstance?.porcentajePlomoPromexbol}"/>
            <div class="input-group-append"><span class="input-group-text">%</span></div>
        </div>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'porcentajePlataPromexbol', 'has-error')}">
    <label class="col-sm-3 col-form-label">
        <g:message code="controlCalidadComplejo.porcentajePlataPromexbol.label" default="Plata" /> <span class="text-danger">*</span>
    </label>
    <div class="col-sm-3">
        <div class="input-group">
            <g:field type="number" name="porcentajePlataPromexbol" step="any" min="0" inputmode="decimal" required="" class="form-control" value="${controlCalidadComplejoInstance?.porcentajePlataPromexbol}"/>
            <div class="input-group-append"><span class="input-group-text">DM</span></div>
        </div>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'porcentajeHumedadPromexbol', 'has-error')}">
    <label class="col-sm-3 col-form-label">
        <g:message code="controlCalidadComplejo.porcentajeHumedadPromexbol.label" default="Humedad" /> <span class="text-danger">*</span>
    </label>
    <div class="col-sm-3">
        <div class="input-group">
            <g:field type="number" name="porcentajeHumedadPromexbol" step="any" min="0" inputmode="decimal" required="" class="form-control" value="${controlCalidadComplejoInstance?.porcentajeHumedadPromexbol}"/>
            <div class="input-group-append"><span class="input-group-text">%</span></div>
        </div>
    </div>
</div>

<%-- Leyes cliente y finales (calculadas en backend) --%>
<g:hiddenField name="porcentajeMermaCliente" value="${fieldValue(bean: controlCalidadComplejoInstance, field: 'porcentajeMermaCliente')}"/>
<g:hiddenField name="porcentajeHumedadCliente" value="${fieldValue(bean: controlCalidadComplejoInstance, field: 'porcentajeHumedadCliente')}"/>
<g:hiddenField name="porcentajeZincCliente" value="${fieldValue(bean: controlCalidadComplejoInstance, field: 'porcentajeZincCliente')}"/>
<g:hiddenField name="porcentajePlomoCliente" value="${fieldValue(bean: controlCalidadComplejoInstance, field: 'porcentajePlomoCliente')}"/>
<g:hiddenField name="porcentajePlataCliente" value="${fieldValue(bean: controlCalidadComplejoInstance, field: 'porcentajePlataCliente')}"/>

<g:hiddenField name="porcentajeMermaFinal" value="${fieldValue(bean: controlCalidadComplejoInstance, field: 'porcentajeMermaFinal')}"/>
<g:hiddenField name="porcentajeHumedadFinal" value="${fieldValue(bean: controlCalidadComplejoInstance, field: 'porcentajeHumedadFinal')}"/>
<g:hiddenField name="porcentajeZincFinal" value="${fieldValue(bean: controlCalidadComplejoInstance, field: 'porcentajeZincFinal')}"/>
<g:hiddenField name="porcentajePlomoFinal" value="${fieldValue(bean: controlCalidadComplejoInstance, field: 'porcentajePlomoFinal')}"/>
<g:hiddenField name="porcentajePlataFinal" value="${fieldValue(bean: controlCalidadComplejoInstance, field: 'porcentajePlataFinal')}"/>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Modo Valoración</label>
    <div class="col-sm-4">
        <g:select name="modoValoracion" from="${['TABLA','TERMINOS DE CONTRATO']}" value="${controlCalidadComplejoInstance?.modoValoracion}" valueMessagePrefix="controlCalidadComplejo.modoValoracion" class="form-control"/>
    </div>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     ELEMENTOS PENALIZABLES (visibles solo en TERM-CON, controlado por JS)
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 id="_tituloPenalizables" class="form-section-title" style="display:none">Elementos Penalizables</h5>

<div id="_porcentajeArsenico" class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'porcentajeArsenico', 'has-error')}" style="display:none">
    <label class="col-sm-3 col-form-label"><g:message code="controlCalidadComplejo.porcentajeArsenico.label" default="Arsénico" /> <span class="text-danger">*</span></label>
    <div class="col-sm-3">
        <g:field type="number" name="porcentajeArsenico" step="any" min="0" inputmode="decimal" required="" class="form-control" value="${controlCalidadComplejoInstance?.porcentajeArsenico}"/>
    </div>
</div>

<div id="_porcentajeAntimonio" class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'porcentajeAntimonio', 'has-error')}" style="display:none">
    <label class="col-sm-3 col-form-label"><g:message code="controlCalidadComplejo.porcentajeAntimonio.label" default="Antimonio" /> <span class="text-danger">*</span></label>
    <div class="col-sm-3">
        <g:field type="number" name="porcentajeAntimonio" step="any" min="0" inputmode="decimal" required="" class="form-control" value="${controlCalidadComplejoInstance?.porcentajeAntimonio}"/>
    </div>
</div>

<div id="_porcentajeSilice" class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'porcentajeSilice', 'has-error')}" style="display:none">
    <label class="col-sm-3 col-form-label"><g:message code="controlCalidadComplejo.porcentajeSilice.label" default="Sílice" /> <span class="text-danger">*</span></label>
    <div class="col-sm-3">
        <g:field type="number" name="porcentajeSilice" step="any" min="0" inputmode="decimal" required="" class="form-control" value="${controlCalidadComplejoInstance?.porcentajeSilice}"/>
    </div>
</div>

<div id="_porcentajeBismuto" class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'porcentajeBismuto', 'has-error')}" style="display:none">
    <label class="col-sm-3 col-form-label"><g:message code="controlCalidadComplejo.porcentajeBismuto.label" default="Bismuto" /> <span class="text-danger">*</span></label>
    <div class="col-sm-3">
        <g:field type="number" name="porcentajeBismuto" step="any" min="0" inputmode="decimal" required="" class="form-control" value="${controlCalidadComplejoInstance?.porcentajeBismuto}"/>
    </div>
</div>

<div id="_porcentajeEstano" class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'porcentajeEstano', 'has-error')}" style="display:none">
    <label class="col-sm-3 col-form-label"><g:message code="controlCalidadComplejo.porcentajeEstano.label" default="Estaño" /> <span class="text-danger">*</span></label>
    <div class="col-sm-3">
        <g:field type="number" name="porcentajeEstano" step="any" min="0" inputmode="decimal" required="" class="form-control" value="${controlCalidadComplejoInstance?.porcentajeEstano}"/>
    </div>
</div>

<div id="_porcentajeZinc" class="form-group row ${hasErrors(bean: controlCalidadComplejoInstance, field: 'porcentajeZinc', 'has-error')}" style="display:none">
    <label class="col-sm-3 col-form-label"><g:message code="controlCalidadComplejo.porcentajeZinc.label" default="Zinc" /> <span class="text-danger">*</span></label>
    <div class="col-sm-3">
        <g:field type="number" name="porcentajeZinc" step="any" min="0" inputmode="decimal" required="" class="form-control" value="${controlCalidadComplejoInstance?.porcentajeZinc}"/>
    </div>
</div>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Observaciones</label>
    <div class="col-sm-8">
        <g:textField name="observaciones" value="${controlCalidadComplejoInstance?.observaciones}" class="form-control"/>
    </div>
</div>
