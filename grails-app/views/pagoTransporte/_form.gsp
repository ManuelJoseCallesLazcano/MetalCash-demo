<%@ page import="org.socymet.cancelacion.PagoTransporte" %>

<g:hiddenField name="solicitante" value="Particular"/>
%{-- empresa/automovil/recepcionId se derivan en el backend (beforeValidate) desde los lotes --}%
<g:hiddenField name="deposito.id" value="${org.socymet.proveedor.Deposito.list()?.getAt(0)?.id}"/>
<g:hiddenField name="descripcion" value="${pagoTransporteInstance?.descripcion}"/>
<g:hiddenField name="lotes" value="${pagoTransporteInstance?.lotes}"/>

<h5 class="form-section-title">Cobrador</h5>

<div class="form-group row ${hasErrors(bean: pagoTransporteInstance, field: 'ci', 'has-error')}">
    <label class="col-sm-3 col-form-label">CI <span class="text-danger">*</span></label>
    <div class="col-sm-5">
        <select id="ciSelect" name="ci" class="form-control" style="width:100%">
            <g:if test="${pagoTransporteInstance?.ci}">
                <option value="${pagoTransporteInstance.ci}" selected="selected">${pagoTransporteInstance.ci}</option>
            </g:if>
        </select>
        <small class="form-text text-muted">Busque un CI ya registrado o escriba uno nuevo.</small>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: pagoTransporteInstance, field: 'nombreCobrador', 'has-error')}">
    <label class="col-sm-3 col-form-label">Nombre del Cobrador <span class="text-danger">*</span></label>
    <div class="col-sm-9">
        <g:textField name="nombreCobrador" required="" value="${pagoTransporteInstance?.nombreCobrador}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: pagoTransporteInstance, field: 'fechaDePago', 'has-error')}">
    <label class="col-sm-3 col-form-label">Fecha de Pago <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:datepickerUI name="fechaDePago" value="${pagoTransporteInstance?.fechaDePago ?: new Date()}" class="form-control"/>
    </div>
</div>

<h5 class="form-section-title">Automóvil</h5>

<div class="form-group row ${hasErrors(bean: pagoTransporteInstance, field: 'automovil', 'has-error')}">
    <label class="col-sm-3 col-form-label">Automóvil <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <g:select id="automovil" name="automovilFiltro"
                  from="${org.socymet.proveedor.Automovil.list([sort: 'placa'])}"
                  optionKey="id" optionValue="placa"
                  noSelection="['': '-SELECCIONE-']" class="form-control" style="width:100%"/>
        <small class="form-text text-muted">Un pago cubre lotes de un mismo automóvil.</small>
    </div>
</div>

<h5 class="form-section-title">Lotes con Transporte por Pagar</h5>

<div class="form-group">
    <button id="agregar" type="button" class="btn btn-info btn-sm">
        <i class="fas fa-search mr-1"></i>BUSCAR LOTES DEL AUTOMÓVIL
    </button>
</div>

<div class="table-responsive">
    <table id="lotesAsignados" class="table table-sm table-bordered">
        <thead class="thead-light">
            <tr>
                <th>LOTE</th>
                <th>FECHA REC.</th>
                <th>TIPO MAT.</th>
                <th class="text-right">P. BRUTO KG</th>
                <th class="text-right">COSTO TRANS.</th>
                <th style="width:40px"></th>
            </tr>
        </thead>
        <tbody id="lotesAsignadosBody"></tbody>
        <tfoot class="thead-light font-weight-bold">
            <tr>
                <td colspan="3" class="text-right">TOTALES</td>
                <td class="text-right" id="totalPesoBruto">0.00</td>
                <td class="text-right" id="totalCostoTransporte">0.00</td>
                <td></td>
            </tr>
        </tfoot>
    </table>
</div>

<h5 class="form-section-title">Liquidación del Pago</h5>

<div class="form-group row ${hasErrors(bean: pagoTransporteInstance, field: 'total', 'has-error')}">
    <label class="col-sm-3 col-form-label font-weight-bold text-info">Total [Bs] <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field type="number" name="total" id="total" step="any" min="0" inputmode="decimal" required=""
                 value="${fieldValue(bean: pagoTransporteInstance, field: 'total')}"
                 class="form-control text-right font-weight-bold"
                 style="background:#e3f2fd; border:2px solid #17a2b8; color:#0c5460; font-size:1.1rem;"/>
        <small class="form-text text-muted">Precargado con la suma del costo de transporte; editable.</small>
    </div>
</div>

<div class="form-group row">
    <label class="col-sm-3 col-form-label">Anticipo Disponible [Bs]</label>
    <div class="col-sm-4">
        <g:field name="disponibleDisplay" id="disponibleDisplay" value="0.00" readonly="true" class="form-control amarillo text-right"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: pagoTransporteInstance, field: 'totalAnticipos', 'has-error')}">
    <label class="col-sm-3 col-form-label">Anticipo Aplicado [Bs]</label>
    <div class="col-sm-4">
        <g:field type="number" name="totalAnticipos" id="totalAnticipos" step="any" min="0" inputmode="decimal"
                 value="${fieldValue(bean: pagoTransporteInstance, field: 'totalAnticipos')}" class="form-control text-right"/>
        <small class="form-text text-muted">Editable: cobre una fracción aplicando menos anticipo (máx. el menor entre Total y Disponible).</small>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: pagoTransporteInstance, field: 'totalPagable', 'has-error')}">
    <label class="col-sm-3 col-form-label font-weight-bold text-info">Total Pagable [Bs] <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="totalPagable" id="totalPagable" value="${fieldValue(bean: pagoTransporteInstance, field: 'totalPagable')}" readonly="true"
                 class="form-control text-right font-weight-bold"
                 style="background:#e3f2fd; border:2px solid #17a2b8; color:#0c5460; font-size:1.1rem;"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: pagoTransporteInstance, field: 'totalPagableLiteral', 'has-error')}">
    <label class="col-sm-3 col-form-label">Literal</label>
    <div class="col-sm-9">
        <g:textField name="totalPagableLiteral" id="totalPagableLiteral" readonly="true"
                     value="${pagoTransporteInstance?.totalPagableLiteral}" class="form-control amarillo"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: pagoTransporteInstance, field: 'observaciones', 'has-error')}" hidden>
    <label class="col-sm-3 col-form-label">Observaciones</label>
    <div class="col-sm-9">
        <g:textField name="observaciones" value="${pagoTransporteInstance?.observaciones}" class="form-control"/>
    </div>
</div>
