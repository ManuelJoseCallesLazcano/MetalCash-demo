<%@ page import="org.socymet.cotizaciones.CotizacionDiariaDeMinerales" %>

<div class="form-group row">
    <label class="col-sm-3 col-form-label">Fecha <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:datepickerUI name="fecha" value="${cotizacionDiariaDeMineralesInstance?.fecha ?: new Date()}"
            class="form-control ${hasErrors(bean: cotizacionDiariaDeMineralesInstance, field: 'fecha', 'is-invalid')}"/>
    </div>
</div>

<div class="form-group row">
    <label for="zinc" class="col-sm-3 col-form-label">Zinc [$us/lf] <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="zinc" class="form-control ${hasErrors(bean: cotizacionDiariaDeMineralesInstance, field: 'zinc', 'is-invalid')}"
            value="${fieldValue(bean: cotizacionDiariaDeMineralesInstance, field: 'zinc')}" required="" inputmode="decimal"/>
    </div>
</div>

<div class="form-group row">
    <label for="plomo" class="col-sm-3 col-form-label">Plomo [$us/lf] <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="plomo" class="form-control ${hasErrors(bean: cotizacionDiariaDeMineralesInstance, field: 'plomo', 'is-invalid')}"
            value="${fieldValue(bean: cotizacionDiariaDeMineralesInstance, field: 'plomo')}" required="" inputmode="decimal"/>
    </div>
</div>

<div class="form-group row">
    <label for="plata" class="col-sm-3 col-form-label">Plata [$us/ot] <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="plata" class="form-control ${hasErrors(bean: cotizacionDiariaDeMineralesInstance, field: 'plata', 'is-invalid')}"
            value="${fieldValue(bean: cotizacionDiariaDeMineralesInstance, field: 'plata')}" required="" inputmode="decimal"/>
    </div>
</div>

<div class="form-group row" hidden>
    <label for="oro" class="col-sm-3 col-form-label">Oro</label>
    <div class="col-sm-4">
        <g:field name="oro" class="form-control ${hasErrors(bean: cotizacionDiariaDeMineralesInstance, field: 'oro', 'is-invalid')}"
            value="${fieldValue(bean: cotizacionDiariaDeMineralesInstance, field: 'oro')}" inputmode="decimal"/>
    </div>
</div>

<g:hiddenField name="estano"   value="${fieldValue(bean: cotizacionDiariaDeMineralesInstance, field: 'estano')}"/>
<g:hiddenField name="antimonio" value="${fieldValue(bean: cotizacionDiariaDeMineralesInstance, field: 'antimonio')}"/>
<g:hiddenField name="wolfran"  value="${fieldValue(bean: cotizacionDiariaDeMineralesInstance, field: 'wolfran')}"/>
<g:hiddenField name="cobre"    value="${fieldValue(bean: cotizacionDiariaDeMineralesInstance, field: 'cobre')}"/>
