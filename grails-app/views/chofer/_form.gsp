<%@ page import="org.socymet.proveedor.Chofer" %>

<div class="form-group row">
    <label for="ci" class="col-sm-3 col-form-label">C.I. <span class="text-danger">*</span></label>
    <div class="col-sm-5">
        <g:textField name="ci" class="form-control ${hasErrors(bean: choferInstance, field: 'ci', 'is-invalid')}"
            required="" value="${choferInstance?.ci}"/>
    </div>
</div>

<div class="form-group row">
    <label for="nombre" class="col-sm-3 col-form-label">Nombre <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <g:textField name="nombre" class="form-control ${hasErrors(bean: choferInstance, field: 'nombre', 'is-invalid')}"
            required="" value="${choferInstance?.nombre}"/>
    </div>
</div>

<div class="form-group row">
    <label for="telefono" class="col-sm-3 col-form-label">Teléfono</label>
    <div class="col-sm-4">
        <g:textField name="telefono" class="form-control ${hasErrors(bean: choferInstance, field: 'telefono', 'is-invalid')}"
            value="${choferInstance?.telefono}"/>
    </div>
</div>

<div class="form-group row">
    <label for="celular" class="col-sm-3 col-form-label">Celular</label>
    <div class="col-sm-4">
        <g:textField name="celular" class="form-control ${hasErrors(bean: choferInstance, field: 'celular', 'is-invalid')}"
            value="${choferInstance?.celular}"/>
    </div>
</div>
