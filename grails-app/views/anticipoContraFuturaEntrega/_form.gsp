<%@ page import="org.socymet.anticipos.AnticipoContraFuturaEntrega" %>

<%-- Campos calculados/ocultos: numeroAnticipo (beforeInsert), liquidacionId --%>
<g:hiddenField name="numeroAnticipo" value="${anticipoContraFuturaEntregaInstance?.numeroAnticipo}"/>
<g:hiddenField name="liquidacionId" value="${anticipoContraFuturaEntregaInstance?.liquidacionId}"/>

<h5 class="form-section-title">Cliente</h5>

<div class="form-group row ${hasErrors(bean: anticipoContraFuturaEntregaInstance, field: 'cliente', 'has-error')}">
    <label class="col-sm-3 col-form-label">Cliente <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <select id="clienteSelect" class="form-control" style="width: 100%">
            <g:if test="${anticipoContraFuturaEntregaInstance?.cliente?.id}">
                <option value="${anticipoContraFuturaEntregaInstance.cliente.id}" selected="selected">
                    ${anticipoContraFuturaEntregaInstance.cliente.ci} — ${anticipoContraFuturaEntregaInstance.cliente.nombre} [${anticipoContraFuturaEntregaInstance.empresa?.nombreDeEmpresa}]
                </option>
            </g:if>
        </select>
        <g:hiddenField name="cliente.id" value="${anticipoContraFuturaEntregaInstance?.cliente?.id}"/>
        <g:hiddenField name="empresa.id" value="${anticipoContraFuturaEntregaInstance?.empresa?.id}"/>
        <small id="estadoCuentaMsg" class="form-text text-danger font-weight-bold"></small>
    </div>
</div>

<h5 class="form-section-title">Datos del Anticipo</h5>

<div class="form-group row ${hasErrors(bean: anticipoContraFuturaEntregaInstance, field: 'fechaDeAnticipo', 'has-error')}">
    <label class="col-sm-3 col-form-label">Fecha de Anticipo <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:datepickerUI name="fechaDeAnticipo" value="${anticipoContraFuturaEntregaInstance?.fechaDeAnticipo ?: new Date()}"
            class="form-control ${hasErrors(bean: anticipoContraFuturaEntregaInstance, field: 'fechaDeAnticipo', 'is-invalid')}"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: anticipoContraFuturaEntregaInstance, field: 'compromiso', 'has-error')}">
    <label class="col-sm-3 col-form-label">Concepto <span class="text-danger">*</span></label>
    <div class="col-sm-9">
        <g:textField name="compromiso" required="" value="${anticipoContraFuturaEntregaInstance?.compromiso}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: anticipoContraFuturaEntregaInstance, field: 'importe', 'has-error')}">
    <label class="col-sm-3 col-form-label">Monto [Bs] <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field type="number" name="importe" id="importe" step="any" min="0" inputmode="decimal" required=""
                 value="${fieldValue(bean: anticipoContraFuturaEntregaInstance, field: 'importe')}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: anticipoContraFuturaEntregaInstance, field: 'importeLiteral', 'has-error')}">
    <label class="col-sm-3 col-form-label">Literal</label>
    <div class="col-sm-9">
        <g:textField name="importeLiteral" id="importeLiteral" readonly="readonly"
                     value="${anticipoContraFuturaEntregaInstance?.importeLiteral}" class="form-control amarillo"/>
    </div>
</div>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Observaciones</label>
    <div class="col-sm-9">
        <g:textField name="observaciones" value="${anticipoContraFuturaEntregaInstance?.observaciones}" class="form-control"/>
    </div>
</div>
