<%@ page import="org.socymet.cotizaciones.Alicuota" %>

<g:hiddenField name="zincAnterior"  value="${org.socymet.cotizaciones.Alicuota.list([max: 1, sort: 'id', order: 'desc']).get(0).zinc}"/>
<g:hiddenField name="plomoAnterior" value="${org.socymet.cotizaciones.Alicuota.list([max: 1, sort: 'id', order: 'desc']).get(0).plomo}"/>
<g:hiddenField name="plataAnterior" value="${org.socymet.cotizaciones.Alicuota.list([max: 1, sort: 'id', order: 'desc']).get(0).plata}"/>

<div class="form-group row">
    <label class="col-sm-3 col-form-label">Fecha <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:datepickerUI name="fecha" value="${alicuotaInstance?.fecha ?: new Date()}"
            class="form-control ${hasErrors(bean: alicuotaInstance, field: 'fecha', 'is-invalid')}"/>
    </div>
</div>

<div class="form-group row">
    <label for="zinc" class="col-sm-3 col-form-label">Zinc <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="zinc" class="form-control ${hasErrors(bean: alicuotaInstance, field: 'zinc', 'is-invalid')}"
            value="${fieldValue(bean: alicuotaInstance, field: 'zinc')}" required="" inputmode="decimal"/>
    </div>
</div>

<div class="form-group row">
    <label for="plomo" class="col-sm-3 col-form-label">Plomo <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="plomo" class="form-control ${hasErrors(bean: alicuotaInstance, field: 'plomo', 'is-invalid')}"
            value="${fieldValue(bean: alicuotaInstance, field: 'plomo')}" required="" inputmode="decimal"/>
    </div>
</div>

<div class="form-group row">
    <label for="plata" class="col-sm-3 col-form-label">Plata <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="plata" class="form-control ${hasErrors(bean: alicuotaInstance, field: 'plata', 'is-invalid')}"
            value="${fieldValue(bean: alicuotaInstance, field: 'plata')}" required="" inputmode="decimal"/>
    </div>
</div>

<g:hiddenField name="estano"    value="${fieldValue(bean: alicuotaInstance, field: 'estano')}"/>
<g:hiddenField name="antimonio" value="${fieldValue(bean: alicuotaInstance, field: 'antimonio')}"/>
<g:hiddenField name="wolfran"   value="${fieldValue(bean: alicuotaInstance, field: 'wolfran')}"/>
<g:hiddenField name="cobre"     value="${fieldValue(bean: alicuotaInstance, field: 'cobre')}"/>
<g:hiddenField name="oro"       value="${fieldValue(bean: alicuotaInstance, field: 'oro')}"/>
