<%@ page import="org.socymet.cotizaciones.CotizacionQuincenalDeMinerales" %>

<h6 class="text-muted font-weight-bold mb-3 border-bottom pb-2">Cotización Quincenal</h6>

<div class="form-group row">
    <label class="col-sm-3 col-form-label">Fecha <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:datepickerUI name="fecha" value="${cotizacionQuincenalDeMineralesInstance?.fecha ?: new Date()}"
            class="form-control ${hasErrors(bean: cotizacionQuincenalDeMineralesInstance, field: 'fecha', 'is-invalid')}"/>
    </div>
</div>

<div class="form-group row">
    <label for="zinc" class="col-sm-3 col-form-label">Zinc [$us/lf] <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="zinc" class="form-control ${hasErrors(bean: cotizacionQuincenalDeMineralesInstance, field: 'zinc', 'is-invalid')}"
            value="${fieldValue(bean: cotizacionQuincenalDeMineralesInstance, field: 'zinc')}" required="" inputmode="decimal"/>
    </div>
</div>

<div class="form-group row">
    <label for="plomo" class="col-sm-3 col-form-label">Plomo [$us/lf] <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="plomo" class="form-control ${hasErrors(bean: cotizacionQuincenalDeMineralesInstance, field: 'plomo', 'is-invalid')}"
            value="${fieldValue(bean: cotizacionQuincenalDeMineralesInstance, field: 'plomo')}" required="" inputmode="decimal"/>
    </div>
</div>

<div class="form-group row">
    <label for="plata" class="col-sm-3 col-form-label">Plata [$us/ot] <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="plata" class="form-control ${hasErrors(bean: cotizacionQuincenalDeMineralesInstance, field: 'plata', 'is-invalid')}"
            value="${fieldValue(bean: cotizacionQuincenalDeMineralesInstance, field: 'plata')}" required="" inputmode="decimal"/>
    </div>
</div>

<h6 class="text-muted font-weight-bold mb-3 border-bottom pb-2 mt-4">Alícuota Quincenal</h6>

<div class="form-group row">
    <label for="alicuotaZinc" class="col-sm-3 col-form-label">Zinc % <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="alicuotaZinc" class="form-control ${hasErrors(bean: cotizacionQuincenalDeMineralesInstance, field: 'alicuotaZinc', 'is-invalid')}"
            value="${fieldValue(bean: cotizacionQuincenalDeMineralesInstance, field: 'alicuotaZinc')}" required="" inputmode="decimal"/>
    </div>
</div>

<div class="form-group row">
    <label for="alicuotaPlomo" class="col-sm-3 col-form-label">Plomo % <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="alicuotaPlomo" class="form-control ${hasErrors(bean: cotizacionQuincenalDeMineralesInstance, field: 'alicuotaPlomo', 'is-invalid')}"
            value="${fieldValue(bean: cotizacionQuincenalDeMineralesInstance, field: 'alicuotaPlomo')}" required="" inputmode="decimal"/>
    </div>
</div>

<div class="form-group row">
    <label for="alicuotaPlata" class="col-sm-3 col-form-label">Plata % <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="alicuotaPlata" class="form-control ${hasErrors(bean: cotizacionQuincenalDeMineralesInstance, field: 'alicuotaPlata', 'is-invalid')}"
            value="${fieldValue(bean: cotizacionQuincenalDeMineralesInstance, field: 'alicuotaPlata')}" required="" inputmode="decimal"/>
    </div>
</div>

<g:hiddenField name="estano"    value="${fieldValue(bean: cotizacionQuincenalDeMineralesInstance, field: 'estano')}"/>
<g:hiddenField name="antimonio" value="${fieldValue(bean: cotizacionQuincenalDeMineralesInstance, field: 'antimonio')}"/>
<g:hiddenField name="wolfran"   value="${fieldValue(bean: cotizacionQuincenalDeMineralesInstance, field: 'wolfran')}"/>
<g:hiddenField name="cobre"     value="${fieldValue(bean: cotizacionQuincenalDeMineralesInstance, field: 'cobre')}"/>
<g:hiddenField name="oro"       value="${fieldValue(bean: cotizacionQuincenalDeMineralesInstance, field: 'oro')}"/>
