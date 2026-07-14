<%@ page import="org.socymet.proveedor.Empresa" %>

<%-- ── Campos de sistema (nunca visibles) ──────────────────────────────── --%>
<g:hiddenField name="empresaId" value="${empresaInstance?.id}"/>
%{--<g:hiddenField name="deposito.id" value="${empresaInstance?.deposito?.id}"/>--}%
<g:hiddenField name="codigoMunicipio" id="codigoMunicipio" value="${empresaInstance?.codigoMunicipio}"/>
<g:hiddenField name="canton" value="${empresaInstance?.canton}"/>
<g:hiddenField name="concesion" value="${empresaInstance?.concesion}"/>
<g:hiddenField name="numeroCuentaCNS" value="${empresaInstance?.numeroCuentaCNS}"/>
<g:hiddenField name="numeroCuentaComibol" value="${empresaInstance?.numeroCuentaComibol}"/>
<g:hiddenField name="aplicarBonoTransporte" value="${empresaInstance?.aplicarBonoTransporte ?: 'NO'}"/>
<g:hiddenField name="bonoTransporteKilosNetosSecos" value="${empresaInstance?.bonoTransporteKilosNetosSecos ?: 0}"/>
<g:hiddenField name="bonoProduccionPorTonelada" value="${empresaInstance?.bonoProduccionPorTonelada ?: 0}"/>
<g:hiddenField name="bonoCalidadPorTonelada" value="${empresaInstance?.bonoCalidadPorTonelada ?: 0}"/>
<g:hiddenField name="bonoMinimoTransportePorTonelada" value="${empresaInstance?.bonoMinimoTransportePorTonelada ?: 0}"/>
<g:hiddenField name="bonoMaximoTransportePorTonelada" value="${empresaInstance?.bonoMaximoTransportePorTonelada ?: 0}"/>
<g:hiddenField name="costoTransporteOroTonelada" value="${empresaInstance?.costoTransporteOroTonelada ?: 0}"/>
<g:hiddenField name="bonoLiquidacionOro" value="${empresaInstance?.bonoLiquidacionOro ?: 0}"/>
<g:hiddenField name="bonoCantidadComplejo" value="${empresaInstance?.bonoCantidadComplejo}"/>
<g:hiddenField name="bonoCantidadConcentrado" value="${empresaInstance?.bonoCantidadConcentrado}"/>
<g:hiddenField name="bonoCantidadCobre" value="${empresaInstance?.bonoCantidadCobre}"/>
<g:hiddenField name="cuadrillas" value="${empresaInstance?.cuadrillas}"/>

<%-- ══════════════════════════════════════════════════════════════════════
     IDENTIFICACIÓN
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 class="form-section-title">Identificación</h5>

<div class="form-group row ${hasErrors(bean: empresaInstance, field: 'tipoDeEmpresa', 'has-error')}">
    <label class="col-sm-3 col-form-label">Tipo de Empresa <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:select name="tipoDeEmpresa" from="${Empresa.TIPOS_DE_EMPRESA}"
            required="" value="${empresaInstance?.tipoDeEmpresa}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: empresaInstance, field: 'nombreDeEmpresa', 'has-error')}">
    <label class="col-sm-3 col-form-label">Nombre de Empresa <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <g:textField name="nombreDeEmpresa" required="" value="${empresaInstance?.nombreDeEmpresa}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: empresaInstance, field: 'codigoEmpresa', 'has-error')}">
    <label class="col-sm-3 col-form-label">Código de Empresa</label>
    <div class="col-sm-4">
        <g:textField name="codigoEmpresa" value="${empresaInstance?.codigoEmpresa}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: empresaInstance, field: 'nim', 'has-error')}">
    <label class="col-sm-3 col-form-label">NIM</label>
    <div class="col-sm-4">
        <g:textField name="nim" value="${empresaInstance?.nim}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: empresaInstance, field: 'nit', 'has-error')}">
    <label class="col-sm-3 col-form-label">NIT</label>
    <div class="col-sm-4">
        <g:textField name="nit" value="${empresaInstance?.nit}" class="form-control"/>
    </div>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     UBICACIÓN
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 class="form-section-title">Ubicación</h5>

<div class="form-group row ${hasErrors(bean: empresaInstance, field: 'departamento', 'has-error')}">
    <label class="col-sm-3 col-form-label">Departamento <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:select id="departamento" name="departamento"
            from="${Empresa.DEPARTAMENTOS}"
            required="" value="${empresaInstance?.departamento}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: empresaInstance, field: 'provincia', 'has-error')}">
    <label class="col-sm-3 col-form-label">Provincia <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:textField name="provincia" required="" value="${empresaInstance?.provincia}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: empresaInstance, field: 'municipio', 'has-error')}">
    <label class="col-sm-3 col-form-label">Municipio <span class="text-danger">*</span></label>
    <div class="col-sm-5">
        <g:select id="municipio" name="municipio"
            from="${org.socymet.proveedor.Municipio.list()}"
            required="" value="${empresaInstance?.municipio}" class="form-control"/>
    </div>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     COSTOS DE TRANSPORTE
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 class="form-section-title">Costos de Transporte</h5>

<div class="table-responsive mb-3">
    <table class="table table-sm table-bordered">
        <thead class="thead-light">
            <tr>
                <th style="width:32%">Descripción</th>
                <th style="width:23%">Costo</th>
                <th style="width:22%">Unidad Monetaria</th>
                <th style="width:23%">Unidad de Cobro</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td class="align-middle pl-3">Broza</td>
                <td>
                    <g:field name="costoTransporteComplejos"
                        value="${fieldValue(bean: empresaInstance, field: 'costoTransporteComplejos')}"
                        class="form-control form-control-sm" inputmode="decimal"/>
                </td>
                <td>
                    <g:select name="unidadMonetariaComplejos"
                        from="${Empresa.UNIDADES_MONETARIAS}"
                        value="${empresaInstance?.unidadMonetariaComplejos}"
                        noSelection="['': '—']" class="form-control form-control-sm"/>
                </td>
                <td>
                    <g:select name="unidadDeCobroComplejos"
                        from="${Empresa.UNIDADES_DE_COBRO}"
                        value="${empresaInstance?.unidadDeCobroComplejos}"
                        noSelection="['': '—']" class="form-control form-control-sm"/>
                </td>
            </tr>
            <tr>
                <td class="align-middle pl-3">Concentrados</td>
                <td>
                    <g:field name="costoTransporteConcentrados"
                        value="${fieldValue(bean: empresaInstance, field: 'costoTransporteConcentrados')}"
                        class="form-control form-control-sm" inputmode="decimal"/>
                </td>
                <td>
                    <g:select name="unidadMonetariaConcentrados"
                        from="${Empresa.UNIDADES_MONETARIAS}"
                        value="${empresaInstance?.unidadMonetariaConcentrados}"
                        noSelection="['': '—']" class="form-control form-control-sm"/>
                </td>
                <td>
                    <g:select name="unidadDeCobroConcentrados"
                        from="${Empresa.UNIDADES_DE_COBRO}"
                        value="${empresaInstance?.unidadDeCobroConcentrados}"
                        noSelection="['': '—']" class="form-control form-control-sm"/>
                </td>
            </tr>
        </tbody>
    </table>
</div>

<div class="form-group row ${hasErrors(bean: empresaInstance, field: 'costoTratamiento', 'has-error')}" hidden>
    <label class="col-sm-3 col-form-label">Costo de Tratamiento</label>
    <div class="col-sm-3">
        <g:field name="costoTratamiento" value="${empresaInstance?.costoTratamiento}" class="form-control" inputmode="decimal"/>
    </div>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     RETENCIONES
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 class="form-section-title">Retenciones</h5>

<div class="row mb-2">
    <div class="col-sm-6">
        <label class="col-form-label pt-0">Retención</label>
        <g:select name="lista_retencion" id="lista_retencion"
            from="${org.socymet.proveedor.Retencion.list([sort:'descripcion'])}"
            optionKey="id" class="form-control"/>
    </div>
    <div class="col-sm-6" hidden>
        <label class="col-form-label pt-0">Descripción</label>
        <input type="text" id="descripcion" class="form-control amarillo" readonly="true"/>
    </div>
</div>
<div class="row mb-2">
    <div class="col-sm-3">
        <label class="col-form-label pt-0">Tipo</label>
        <input type="text" id="tipoDeRetencion" class="form-control amarillo" readonly="true"/>
    </div>
    <div class="col-sm-3">
        <label class="col-form-label pt-0">Cantidad</label>
        <input type="text" id="cantidad" class="form-control" inputmode="decimal" pattern="[0-9]*[.,]?[0-9]*"/>
    </div>
    <div class="col-sm-3">
        <label class="col-form-label pt-0">Unidad</label>
        <input type="text" id="unidadDeDescuento" class="form-control amarillo" readonly="true"/>
    </div>
    <div class="col-sm-3">
        <label class="col-form-label pt-0">Asignación</label>
        <input type="text" id="asignacion" class="form-control amarillo" readonly="true"/>
    </div>
</div>
<div class="row mb-3">
    <div class="col-sm-12 d-flex">
        <button id="agregar" type="button" class="btn btn-success btn-sm mr-1">
            <i class="fas fa-plus"></i> Adicionar
        </button>
        <button id="actualizar" type="button" class="btn btn-warning btn-sm mr-1">
            <i class="fas fa-sync-alt"></i> Actualizar
        </button>
        <button id="quitar" type="button" class="btn btn-danger btn-sm">
            <i class="fas fa-trash"></i> Eliminar
        </button>
    </div>
</div>

<div class="card card-outline card-secondary mb-3">
    <div class="card-header py-2">
        <h3 class="card-title mb-0" style="font-size:0.85rem; font-weight:600;">Detalle de Retenciones</h3>
    </div>
    <div class="card-body p-0">
        <div class="table-responsive">
            <table id="tablaRetenciones" class="table table-sm table-hover table-striped mb-0">
                <thead class="thead-light">
                    <tr>
                        <th>Descripción</th>
                        <th style="width:90px">Tipo</th>
                        <th class="text-right" style="width:90px">Cantidad</th>
                        <th style="width:80px">Unidad</th>
                        <th style="width:110px">Asignación</th>
                        <th style="width:50px"></th>
                    </tr>
                </thead>
                <tbody id="tablaRetencionesBody"></tbody>
            </table>
        </div>
    </div>
</div>

<g:hiddenField name="retenciones" value="${empresaInstance?.retenciones}"/>
