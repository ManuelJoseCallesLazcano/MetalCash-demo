<%@ page import="org.socymet.anticipos.AnticipoPorTransporte" %>

%{-- Titular del ledger = Automovil. El anticipo es un adelanto puro al automovil (ya no atado a un lote). --}%
<g:hiddenField name="solicitante" value="${anticipoPorTransporteInstance?.solicitante ?: 'Particular'}"/>

<h5 class="form-section-title">Cobrador</h5>

<div class="form-group row ${hasErrors(bean: anticipoPorTransporteInstance, field: 'ci', 'has-error')}">
    <label class="col-sm-3 col-form-label">CI <span class="text-danger">*</span></label>
    <div class="col-sm-5">
        <select id="ciSelect" name="ci" class="form-control" style="width:100%">
            <g:if test="${anticipoPorTransporteInstance?.ci}">
                <option value="${anticipoPorTransporteInstance.ci}" selected="selected">${anticipoPorTransporteInstance.ci}</option>
            </g:if>
        </select>
        <small class="form-text text-muted">Busque un CI ya registrado o escriba uno nuevo.</small>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: anticipoPorTransporteInstance, field: 'nombreCobrador', 'has-error')}">
    <label class="col-sm-3 col-form-label">Nombre del Cobrador <span class="text-danger">*</span></label>
    <div class="col-sm-9">
        <g:textField name="nombreCobrador" required="" value="${anticipoPorTransporteInstance?.nombreCobrador}" class="form-control"/>
    </div>
</div>

<h5 class="form-section-title">Automóvil</h5>

<div class="form-group row ${hasErrors(bean: anticipoPorTransporteInstance, field: 'automovil', 'has-error')}">
    <label class="col-sm-3 col-form-label">Automóvil <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <g:select id="automovil" name="automovil.id"
                  from="${org.socymet.proveedor.Automovil.list([sort: 'placa'])}"
                  optionKey="id" optionValue="placa" required=""
                  value="${anticipoPorTransporteInstance?.automovil?.id}"
                  noSelection="['': '-SELECCIONE-']" class="form-control" style="width:100%"/>
        <small id="disponibleMsg" class="form-text text-info font-weight-bold"></small>
    </div>
</div>

<h5 class="form-section-title">Datos del Anticipo</h5>

<div class="form-group row ${hasErrors(bean: anticipoPorTransporteInstance, field: 'fecha', 'has-error')}">
    <label class="col-sm-3 col-form-label">Fecha <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:datepickerUI name="fecha" value="${anticipoPorTransporteInstance?.fecha ?: new Date()}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: anticipoPorTransporteInstance, field: 'descripcion', 'has-error')}">
    <label class="col-sm-3 col-form-label">Concepto <span class="text-danger">*</span></label>
    <div class="col-sm-9">
        <g:textField name="descripcion" required="" value="${anticipoPorTransporteInstance?.descripcion ?: 'ANTICIPO POR TRANSPORTE'}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: anticipoPorTransporteInstance, field: 'importe', 'has-error')}">
    <label class="col-sm-3 col-form-label">Importe [Bs] <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field type="number" name="importe" id="importe" step="any" min="0" inputmode="decimal" required=""
                 value="${fieldValue(bean: anticipoPorTransporteInstance, field: 'importe')}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: anticipoPorTransporteInstance, field: 'importeLiteral', 'has-error')}">
    <label class="col-sm-3 col-form-label">Literal</label>
    <div class="col-sm-9">
        <g:textField name="importeLiteral" id="importeLiteral" readonly="readonly"
                     value="${anticipoPorTransporteInstance?.importeLiteral}" class="form-control amarillo"/>
    </div>
</div>

%{-- Disponible antes del anticipo (informativo, lo carga el JS) --}%
<g:hiddenField name="ultimoSaldo" value="${anticipoPorTransporteInstance?.ultimoSaldo ?: 0}"/>

<div class="form-group row" style="display:none">
    <label class="col-sm-3 col-form-label">Observaciones</label>
    <div class="col-sm-9">
        <g:textField name="observaciones" value="${anticipoPorTransporteInstance?.observaciones}" class="form-control"/>
    </div>
</div>
