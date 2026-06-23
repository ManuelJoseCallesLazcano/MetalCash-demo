<%@ page import="org.socymet.proveedor.Municipio" %>

<div class="form-group row">
    <label for="departamento" class="col-sm-3 col-form-label">Departamento <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:select name="departamento" class="form-control ${hasErrors(bean: municipioInstance, field: 'departamento', 'is-invalid')}"
            from="${['ORURO','LA PAZ','POTOSI','COCHABAMBA','CHUQUISACA','TARIJA','PANDO','BENI','SANTA CRUZ']}"
            required=""
            value="${municipioInstance?.departamento}"
            valueMessagePrefix="municipio.departamento"
            noSelection="['': '-- Seleccione --']"/>
    </div>
</div>

<div class="form-group row">
    <label for="municipio" class="col-sm-3 col-form-label">Municipio <span class="text-danger">*</span></label>
    <div class="col-sm-5">
        <g:textField name="municipio" class="form-control ${hasErrors(bean: municipioInstance, field: 'municipio', 'is-invalid')}"
            required="" value="${municipioInstance?.municipio}"/>
    </div>
</div>
