<%@ page import="org.socymet.proveedor.Deposito" %>

<h5 class="form-section-title">Datos del Depósito</h5>

<div class="form-group row ${hasErrors(bean: depositoInstance, field: 'nombreDeposito', 'has-error')}">
    <label class="col-sm-3 col-form-label">Nombre <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <g:textField name="nombreDeposito" class="form-control" required="" value="${depositoInstance?.nombreDeposito}"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: depositoInstance, field: 'codigoDeposito', 'has-error')}">
    <label class="col-sm-3 col-form-label">Código <span class="text-danger">*</span></label>
    <div class="col-sm-3">
        <g:textField name="codigoDeposito" maxlength="5" class="form-control" required="" value="${depositoInstance?.codigoDeposito}"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: depositoInstance, field: 'direccion', 'has-error')}">
    <label class="col-sm-3 col-form-label">Dirección <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <g:textField name="direccion" class="form-control" required="" value="${depositoInstance?.direccion}"/>
    </div>
</div>
