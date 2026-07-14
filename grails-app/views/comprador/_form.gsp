<%@ page import="org.smart.compositos.Comprador" %>

<div class="form-group row">
    <label for="nombreComprador" class="col-sm-3 col-form-label">Nombre <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <g:textField name="nombreComprador" class="form-control ${hasErrors(bean: compradorInstance, field: 'nombreComprador', 'is-invalid')}"
            required="" value="${compradorInstance?.nombreComprador}"/>
    </div>
</div>

<div class="form-group row">
    <label for="nombreContacto" class="col-sm-3 col-form-label">Nombre de Contacto <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <g:textField name="nombreContacto" class="form-control ${hasErrors(bean: compradorInstance, field: 'nombreContacto', 'is-invalid')}"
            required="" value="${compradorInstance?.nombreContacto}"/>
    </div>
</div>

<div class="form-group row">
    <label for="telefono" class="col-sm-3 col-form-label">Teléfono</label>
    <div class="col-sm-4">
        <g:textField name="telefono" class="form-control ${hasErrors(bean: compradorInstance, field: 'telefono', 'is-invalid')}"
            value="${compradorInstance?.telefono}"/>
    </div>
</div>

<div class="form-group row">
    <label for="email" class="col-sm-3 col-form-label">Email</label>
    <div class="col-sm-5">
        <g:field type="email" name="email" class="form-control ${hasErrors(bean: compradorInstance, field: 'email', 'is-invalid')}"
            value="${compradorInstance?.email}"/>
    </div>
</div>
