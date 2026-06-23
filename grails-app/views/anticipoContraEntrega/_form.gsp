<%@ page import="org.socymet.anticipos.AnticipoContraEntrega" %>



<div class="fieldcontain ${hasErrors(bean: anticipoContraEntregaInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="anticipoContraEntrega.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${anticipoContraEntregaInstance?.lote}"/>
    <g:hiddenField name="recepcionId" value="${anticipoContraEntregaInstance?.recepcionId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraEntregaInstance, field: 'nombreCliente', 'error')} required">
	<label for="nombreCliente">
		<g:message code="anticipoContraEntrega.nombreCliente.label" default="Nombre Cliente" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCliente" required="" value="${anticipoContraEntregaInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraEntregaInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="anticipoContraEntrega.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreEmpresa" required="" value="${anticipoContraEntregaInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraEntregaInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="anticipoContraEntrega.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="fechaDeRecepcion" required="" value="${anticipoContraEntregaInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraEntregaInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="anticipoContraEntrega.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: anticipoContraEntregaInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraEntregaInstance, field: 'fechaDeAnticipo', 'error')} required">
	<label for="fechaDeAnticipo">
		<g:message code="anticipoContraEntrega.fechaDeAnticipo.label" default="Fecha De Anticipo" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeAnticipo" precision="day"  value="${anticipoContraEntregaInstance?.fechaDeAnticipo}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraEntregaInstance, field: 'importe', 'error')} required">
	<label for="importe">
		<g:message code="anticipoContraEntrega.importe.label" default="Importe" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="importe" value="${fieldValue(bean: anticipoContraEntregaInstance, field: 'importe')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraEntregaInstance, field: 'importeLiteral', 'error')} required">
    <label for="importeLiteral">
        <g:message code="anticipoContraEntrega.importeLiteral.label" default="Importe Literal" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="importeLiteral" value="${fieldValue(bean: anticipoContraEntregaInstance, field: 'importeLiteral')}" required="" size="90" />
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoContraEntregaInstance, field: 'observaciones', 'error')} ">
	<label for="observaciones">
		<g:message code="anticipoContraEntrega.observaciones.label" default="Observaciones" />
		
	</label>
	<g:textField name="observaciones" value="${anticipoContraEntregaInstance?.observaciones}" size="90"/>
</div>

