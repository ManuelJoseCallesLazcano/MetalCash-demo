<%@ page import="org.socymet.proveedor.Automovil" %>

<div class="form-group row">
    <label for="placa" class="col-sm-3 col-form-label">Placa <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:textField name="placa" class="form-control ${hasErrors(bean: automovilInstance, field: 'placa', 'is-invalid')}"
            required="" value="${automovilInstance?.placa}"/>
    </div>
</div>

<div class="form-group row">
    <label for="modelo" class="col-sm-3 col-form-label">Modelo</label>
    <div class="col-sm-5">
        <g:textField name="modelo" class="form-control ${hasErrors(bean: automovilInstance, field: 'modelo', 'is-invalid')}"
            value="${automovilInstance?.modelo}"/>
    </div>
</div>

<div class="form-group row">
    <label for="color" class="col-sm-3 col-form-label">Color</label>
    <div class="col-sm-4">
        <g:textField name="color" class="form-control ${hasErrors(bean: automovilInstance, field: 'color', 'is-invalid')}"
            value="${automovilInstance?.color}"/>
    </div>
</div>
