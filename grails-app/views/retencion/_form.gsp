<%@ page import="org.socymet.proveedor.Retencion" %>

<h5 class="form-section-title">Datos de la Retención</h5>

<div class="form-group row ${hasErrors(bean: retencionInstance, field: 'descripcion', 'has-error')}">
    <label class="col-sm-3 col-form-label">Descripción <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <g:textField name="descripcion" class="form-control" required=""
            value="${retencionInstance?.descripcion}"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: retencionInstance, field: 'tipoDeRetencion', 'has-error')}">
    <label class="col-sm-3 col-form-label">Tipo de Retención <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:select name="tipoDeRetencion" class="form-control"
            from="${['DE LEY', 'OTRA']}"
            value="${retencionInstance?.tipoDeRetencion}"
            noSelection="['': '— Seleccione —']"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: retencionInstance, field: 'cantidadDescuento', 'has-error')}">
    <label class="col-sm-3 col-form-label">Cantidad de Descuento <span class="text-danger">*</span></label>
    <div class="col-sm-3">
        <g:field name="cantidadDescuento" class="form-control" required=""
            value="${fieldValue(bean: retencionInstance, field: 'cantidadDescuento')}" inputmode="decimal"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: retencionInstance, field: 'unidadDeDescuento', 'has-error')}">
    <label class="col-sm-3 col-form-label">Unidad de Descuento <span class="text-danger">*</span></label>
    <div class="col-sm-3">
        <g:select id="unidadDeDescuento" name="unidadDeDescuento" class="form-control"
            from="${['%', 'Bs']}"
            value="${retencionInstance?.unidadDeDescuento}"
            noSelection="['': '— Seleccione —']"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: retencionInstance, field: 'asignacionDelDescuento', 'has-error')}">
    <label class="col-sm-3 col-form-label">Asignación del Descuento <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:select id="asignacionDelDescuento" name="asignacionDelDescuento" class="form-control"
            from="${['VNV', 'SACO', 'FIJO', 'VBV']}"
            value="${retencionInstance?.asignacionDelDescuento}"
            noSelection="['': '— Seleccione —']"/>
    </div>
</div>
