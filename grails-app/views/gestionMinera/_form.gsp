<%@ page import="org.smart.parametros.GestionMinera" %>

<h5 class="form-section-title">Datos de la Gestión</h5>

<div class="form-group row ${hasErrors(bean: gestionMineraInstance, field: 'gestion', 'has-error')}">
    <label class="col-sm-3 col-form-label">Gestión <span class="text-danger">*</span></label>
    <div class="col-sm-3">
        <g:datePicker name="gestion" precision="year" value="${gestionMineraInstance?.gestion}" years="${2020..2035}"/>
    </div>
</div>

<%-- Estado: lo determina el backend (la nueva gestión queda ACTIVA) --%>
<div class="form-group row ${hasErrors(bean: gestionMineraInstance, field: 'estado', 'has-error')}" style="display: none">
    <label class="col-sm-3 col-form-label">Estado</label>
    <div class="col-sm-3">
        <g:select name="estado" from="${['ACTIVO','INACTIVO']}" value="${gestionMineraInstance?.estado}" class="form-control"/>
    </div>
</div>
