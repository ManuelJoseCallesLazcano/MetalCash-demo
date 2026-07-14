<%@ page import="org.smart.compositos.Ingenio" %>

<div class="form-group row">
    <label for="nombreIngenio" class="col-sm-3 col-form-label">Nombre <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <g:textField name="nombreIngenio" class="form-control ${hasErrors(bean: ingenioInstance, field: 'nombreIngenio', 'is-invalid')}"
            required="" value="${ingenioInstance?.nombreIngenio}"/>
    </div>
</div>

<div class="form-group row">
    <label for="telefono" class="col-sm-3 col-form-label">Datos de Contacto</label>
    <div class="col-sm-7">
        <g:textField name="telefono" class="form-control ${hasErrors(bean: ingenioInstance, field: 'telefono', 'is-invalid')}"
            value="${ingenioInstance?.telefono}"/>
    </div>
</div>
