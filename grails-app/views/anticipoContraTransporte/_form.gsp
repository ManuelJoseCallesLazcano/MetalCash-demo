<%@ page import="org.socymet.anticipos.AnticipoContraTransporte" %>



<div class="fieldcontain ${hasErrors(bean: anticipoContraTransporteInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="anticipoContraTransporte.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${anticipoContraTransporteInstance?.lote}"/>
    <g:hiddenField name="recepcionId" value="${anticipoContraTransporteInstance?.recepcionId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraTransporteInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="anticipoContraTransporte.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${anticipoContraTransporteInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraTransporteInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="anticipoContraTransporte.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${anticipoContraTransporteInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraTransporteInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="anticipoContraTransporte.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${anticipoContraTransporteInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraTransporteInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="anticipoContraTransporte.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: anticipoContraTransporteInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraTransporteInstance, field: 'costoDeTransporte', 'error')} required">
    <label for="costoDeTransporte">
        <g:message code="anticipoContraTransporte.costoDeTransporte.label" default="Costo de Transporte" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="costoDeTransporte" value="${fieldValue(bean: anticipoContraTransporteInstance, field: 'costoDeTransporte')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraTransporteInstance, field: 'fechaDeAnticipo', 'error')} required">
    <label for="fechaDeAnticipo">
        <g:message code="anticipoContraTransporte.fechaDeAnticipo.label" default="Fecha De Anticipo" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeAnticipo" precision="day"  value="${anticipoContraTransporteInstance?.fechaDeAnticipo}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraTransporteInstance, field: 'importe', 'error')} required">
    <label for="importe">
        <g:message code="anticipoContraTransporte.importe.label" default="Importe" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="importe" value="${fieldValue(bean: anticipoContraTransporteInstance, field: 'importe')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraTransporteInstance, field: 'importeLiteral', 'error')} required">
    <label for="importeLiteral">
        <g:message code="anticipoContraTransporte.importeLiteral.label" default="Importe Literal" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="importeLiteral" value="${fieldValue(bean: anticipoContraTransporteInstance, field: 'importeLiteral')}" required="" size="90" />
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraTransporteInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="anticipoContraTransporte.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${anticipoContraTransporteInstance?.observaciones}" size="90"/>
</div>

