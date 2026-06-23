<%@ page import="org.socymet.proveedor.Cliente" %>

<h5 class="form-section-title">Datos del Cliente</h5>

<%-- Depósito (asignado por backend) --%>
<div id="_deposito" class="form-group row ${hasErrors(bean: clienteInstance, field: 'deposito', 'has-error')}" style="display: none">
    <label class="col-sm-3 col-form-label">Depósito <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}"
            optionKey="id" required="" value="${clienteInstance?.deposito?.id}" class="form-control many-to-one"/>
    </div>
</div>

<%-- Empresa (Select2 async) --%>
<div class="form-group row ${hasErrors(bean: clienteInstance, field: 'empresa', 'has-error')}">
    <label class="col-sm-3 col-form-label">Empresa <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <select id="empresaSelect" name="empresa.id" class="form-control" style="width: 100%" required="">
            <g:if test="${clienteInstance?.empresa?.id}">
                <option value="${clienteInstance.empresa.id}" selected="selected">
                    ${clienteInstance.empresa.toString()}
                </option>
            </g:if>
        </select>
    </div>
</div>

<%-- Cuadrilla (oculto) --%>
<div class="form-group row ${hasErrors(bean: clienteInstance, field: 'cuadrilla', 'has-error')}" style="display: none">
    <label class="col-sm-3 col-form-label">Cuadrilla</label>
    <div class="col-sm-4">
        <g:select name="cuadrilla" from="${org.socymet.proveedor.Cuadrilla.list()}"
            noSelection="['': 'NO PERTENECE']" value="${clienteInstance?.cuadrilla}" class="form-control"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: clienteInstance, field: 'ci', 'has-error')}">
    <label class="col-sm-3 col-form-label">C.I. <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:textField name="ci" class="form-control" required="" value="${clienteInstance?.ci}"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: clienteInstance, field: 'nombre', 'has-error')}">
    <label class="col-sm-3 col-form-label">Nombre <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <g:textField name="nombre" class="form-control" required="" value="${clienteInstance?.nombre}"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: clienteInstance, field: 'telefono', 'has-error')}">
    <label class="col-sm-3 col-form-label">Teléfono</label>
    <div class="col-sm-4">
        <g:textField name="telefono" class="form-control" value="${clienteInstance?.telefono}"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: clienteInstance, field: 'celular', 'has-error')}">
    <label class="col-sm-3 col-form-label">Celular</label>
    <div class="col-sm-4">
        <g:textField name="celular" class="form-control" value="${clienteInstance?.celular}"/>
    </div>
</div>
