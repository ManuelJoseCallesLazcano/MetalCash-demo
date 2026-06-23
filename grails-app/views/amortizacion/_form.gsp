<%@ page import="org.socymet.anticipos.Amortizacion" %>

<%-- Campos calculados/ocultos: numeroAmortizacion (beforeValidate) --%>
<g:hiddenField name="numeroAmortizacion" value="${amortizacionInstance?.numeroAmortizacion}"/>

<h5 class="form-section-title">Cliente</h5>

<div class="form-group row ${hasErrors(bean: amortizacionInstance, field: 'cliente', 'has-error')}">
    <label class="col-sm-3 col-form-label">Cliente <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <select id="clienteSelect" class="form-control" style="width: 100%">
            <g:if test="${amortizacionInstance?.cliente?.id}">
                <option value="${amortizacionInstance.cliente.id}" selected="selected">
                    ${amortizacionInstance.cliente.ci} — ${amortizacionInstance.cliente.nombre} [${amortizacionInstance.empresa?.nombreDeEmpresa}]
                </option>
            </g:if>
        </select>
        <g:hiddenField name="cliente.id" value="${amortizacionInstance?.cliente?.id}"/>
        <g:hiddenField name="empresa.id" value="${amortizacionInstance?.empresa?.id}"/>
        <small id="estadoCuentaMsg" class="form-text text-danger font-weight-bold"></small>
    </div>
</div>

<h5 class="form-section-title">Datos de la Amortización</h5>

<div class="form-group row ${hasErrors(bean: amortizacionInstance, field: 'fecha', 'has-error')}">
    <label class="col-sm-3 col-form-label">Fecha <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:datepickerUI name="fecha" value="${amortizacionInstance?.fecha ?: new Date()}"
            class="form-control ${hasErrors(bean: amortizacionInstance, field: 'fecha', 'is-invalid')}"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: amortizacionInstance, field: 'concepto', 'has-error')}">
    <label class="col-sm-3 col-form-label">Concepto <span class="text-danger">*</span></label>
    <div class="col-sm-9">
        <g:textField name="concepto" required="" value="${amortizacionInstance?.concepto}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: amortizacionInstance, field: 'importe', 'has-error')}">
    <label class="col-sm-3 col-form-label">Importe [Bs] <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field type="number" name="importe" id="importe" step="any" min="0" inputmode="decimal" required=""
                 value="${fieldValue(bean: amortizacionInstance, field: 'importe')}" class="form-control"/>
    </div>
</div>

<div class="form-group row">
    <label class="col-sm-3 col-form-label">Literal</label>
    <div class="col-sm-9">
        <g:textField name="importeLiteral" id="importeLiteral" readonly="readonly"
                     value="${amortizacionInstance?.importeLiteral}" class="form-control amarillo"/>
    </div>
</div>

<div class="form-group row">
    <label class="col-sm-3 col-form-label">Saldo actual [Bs]</label>
    <div class="col-sm-3">
        <g:field type="text" name="saldoActual" id="saldoActual" readonly="readonly"
                 value="${fieldValue(bean: amortizacionInstance, field: 'saldoActual')}" class="form-control amarillo"/>
    </div>
    <label class="col-sm-2 col-form-label">Saldo por pagar [Bs]</label>
    <div class="col-sm-3">
        <g:field type="text" name="saldoPorPagar" id="saldoPorPagar" readonly="readonly"
                 value="${fieldValue(bean: amortizacionInstance, field: 'saldoPorPagar')}" class="form-control amarillo"/>
    </div>
</div>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Observaciones</label>
    <div class="col-sm-9">
        <g:textField name="observaciones" value="${amortizacionInstance?.observaciones}" class="form-control"/>
    </div>
</div>
