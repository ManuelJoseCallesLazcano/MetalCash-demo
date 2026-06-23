<%@ page import="org.socymet.cotizaciones.CotizacionDeDolar" %>

<div class="form-group row">
    <label class="col-sm-3 col-form-label">Fecha <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:datepickerUI name="fecha" value="${cotizacionDeDolarInstance?.fecha ?: new Date()}"
            class="form-control ${hasErrors(bean: cotizacionDeDolarInstance, field: 'fecha', 'is-invalid')}"/>
    </div>
</div>

<div class="form-group row">
    <label for="tipoDeCambioOficial" class="col-sm-3 col-form-label">T.C. Oficial <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="tipoDeCambioOficial" class="form-control ${hasErrors(bean: cotizacionDeDolarInstance, field: 'tipoDeCambioOficial', 'is-invalid')}"
            value="${fieldValue(bean: cotizacionDeDolarInstance, field: 'tipoDeCambioOficial')}" required="" inputmode="decimal"/>
    </div>
</div>

<div class="form-group row">
    <label for="tipoDeCambioComercial" class="col-sm-3 col-form-label">T.C. Comercial <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="tipoDeCambioComercial" class="form-control ${hasErrors(bean: cotizacionDeDolarInstance, field: 'tipoDeCambioComercial', 'is-invalid')}"
            value="${fieldValue(bean: cotizacionDeDolarInstance, field: 'tipoDeCambioComercial')}" required="" inputmode="decimal"/>
    </div>
</div>
