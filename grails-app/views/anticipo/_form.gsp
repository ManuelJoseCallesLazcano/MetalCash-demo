<%@ page import="org.socymet.anticipos.Anticipo" %>

<%-- Depósito: listado oculto de los depósitos disponibles. Por ahora no se
     asocia un depósito específico; se envía el primero disponible para satisfacer
     la validación (deposito es nullable:false). --%>
<div style="display: none">
    <g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}"
        optionKey="id" required="" value="${anticipoInstance?.deposito?.id}"/>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     PROVEEDOR
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 class="form-section-title">Proveedor</h5>

<div class="form-group row ${hasErrors(bean: anticipoInstance, field: 'nombreCliente', 'has-error')}">
    <label class="col-sm-3 col-form-label">Cliente <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <select id="clienteSelect" class="form-control" style="width: 100%">
            <g:if test="${anticipoInstance?.cliente?.id}">
                <option value="${anticipoInstance.cliente.id}" selected="selected">
                    ${anticipoInstance.nombreCliente} [${anticipoInstance.nombreEmpresa}]
                </option>
            </g:if>
        </select>
        <g:hiddenField name="cliente.id" value="${anticipoInstance?.cliente?.id}"/>
        <g:hiddenField name="empresa.id" value="${anticipoInstance?.empresa?.id}"/>
        <g:hiddenField name="nombreCliente" value="${anticipoInstance?.nombreCliente}"/>
    </div>
</div>

<div class="form-group row">
    <label class="col-sm-3 col-form-label">Empresa</label>
    <div class="col-sm-7">
        <g:textField name="nombreEmpresa" value="${anticipoInstance?.nombreEmpresa}" class="form-control amarillo" readonly="true"/>
    </div>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     LOTES ASIGNADOS
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 class="form-section-title">Lotes Asignados</h5>

<%-- Campos ocultos requeridos por la búsqueda de lotes (anticipoUtilidades.js) --%>
<div style="display:none;">
    <g:select name="tipoDeMineral" from="${['COMPLEJO','ORO']}" required="" value="${anticipoInstance?.tipoDeMineral}" valueMessagePrefix="anticipo.tipoDeMineral"/>
    <input type="text" id="loteInicial" value="0" readonly="readonly"/>
    <input type="text" id="loteFinal" value="10000000" readonly="readonly"/>
    <g:select name="depositoId" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id"/>
</div>

<g:hiddenField name="_recepcionJSON" value=""/>
<g:hiddenField name="lotes" value="${anticipoInstance?.lotes}"/>

<g:if test="${!anticipoInstance?.id}">
    <div class="form-group row">
        <div class="col-sm-9 offset-sm-3">
            <button id="agregar" type="button" class="btn btn-info btn-sm">
                <i class="fas fa-search mr-1"></i>Buscar lotes
            </button>
        </div>
    </div>
</g:if>

<div class="form-group row">
    <div class="col-sm-12 table-responsive">
        <table id="lotesAsignados" class="table table-sm table-hover table-striped table-bordered mb-0"
               data-editable="${anticipoInstance?.id ? 'false' : 'true'}">
            <thead class="thead-light">
                <tr>
                    <th>Lote</th>
                    <th>Fecha Rec.</th>
                    <th>Tipo Material</th>
                    <th class="text-right">Peso Bruto [Kg]</th>
                    <th style="width:48px"></th>
                </tr>
            </thead>
            <tbody id="lotesAsignadosBody"></tbody>
        </table>
    </div>
</div>

<div class="form-group row">
    <label class="col-sm-3 col-form-label">Descripción <span class="text-danger">*</span></label>
    <div class="col-sm-9">
        <g:textField name="descripcion" required="" value="${anticipoInstance?.descripcion}" class="form-control amarillo" readonly="true"/>
    </div>
</div>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Observaciones</label>
    <div class="col-sm-9">
        <g:textField name="observaciones" value="${anticipoInstance?.observaciones}" class="form-control" readonly="readonly"/>
    </div>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     ANTICIPOS EMITIDOS
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 class="form-section-title">Anticipos Emitidos</h5>

<g:if test="${anticipoInstance?.id}">
    <%-- En edición los anticipos se gestionan desde la vista de detalle (emitir/anular) --%>
    <div class="alert alert-info py-2">
        Los anticipos emitidos se gestionan desde la vista de detalle del registro.
    </div>
    <div class="form-group row">
        <label class="col-sm-3 col-form-label">Total Anticipos [Bs]</label>
        <div class="col-sm-3">
            <input type="text" class="form-control amarillo" readonly="true"
                   value="<g:formatNumber number="${anticipoInstance?.totalAnticipos ?: 0}" type="number" maxFractionDigits="2"/>"/>
            <g:hiddenField name="totalAnticipos" value="${anticipoInstance?.totalAnticipos}"/>
        </div>
    </div>
    <g:hiddenField name="literalTotalAnticipos" value="${anticipoInstance?.literalTotalAnticipos}"/>
    <g:hiddenField name="totalPagado" value="${anticipoInstance?.totalPagado ?: 0}"/>
    <g:hiddenField name="totalPorPagar" value="${anticipoInstance?.totalPorPagar ?: 0}"/>
</g:if>
<g:else>
    <%-- En alta se capturan una o más cuotas iniciales (cada una con su comprobante) --%>
    <div class="form-group row">
        <div class="col-sm-12 table-responsive">
            <table class="table table-sm table-bordered mb-2">
                <thead class="thead-light">
                    <tr>
                        <th>Importe [Bs] <span class="text-danger">*</span></th>
                        <th style="width: 38%">Fecha <span class="text-danger">*</span></th>
                        <th style="width: 48px"></th>
                    </tr>
                </thead>
                <tbody id="cuotasBody">
                    <tr>
                        <td><input type="number" step="any" min="0" name="cuotaMonto" class="form-control form-control-sm cuota-monto" required=""/></td>
                        <td><input type="text" name="cuotaFecha" class="form-control form-control-sm cuota-fecha" autocomplete="off" value="${new java.text.SimpleDateFormat('dd/MM/yyyy').format(new Date())}"/></td>
                        <td class="text-center"><button type="button" class="btn btn-outline-danger btn-sm cuota-remove" title="Quitar"><i class="fas fa-times"></i></button></td>
                    </tr>
                </tbody>
            </table>
            <button type="button" id="agregarCuotaRow" class="btn btn-outline-primary btn-sm">
                <i class="fas fa-plus mr-1"></i>Agregar anticipo
            </button>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-3 col-form-label">Total Anticipos [Bs]</label>
        <div class="col-sm-3">
            <input type="text" id="totalAnticiposDisplay" class="form-control amarillo" readonly="readonly" value="0"/>
            <g:hiddenField name="totalAnticipos" value="0"/>
        </div>
    </div>

    <g:hiddenField name="literalTotalAnticipos" value="${anticipoInstance?.literalTotalAnticipos}"/>
    <g:hiddenField name="totalPagado" value="0"/>
    <g:hiddenField name="totalPorPagar" value="0"/>
</g:else>
