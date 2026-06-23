<%@ page import="org.socymet.anticipos.AnticipoDetalle" %>



<div class="fieldcontain ${hasErrors(bean: anticipoDetalleInstance, field: 'anticipo', 'error')} required">
	<label for="anticipo">
		<g:message code="anticipoDetalle.anticipo.label" default="Anticipo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="anticipo" name="anticipo.id" from="${org.socymet.anticipos.Anticipo.list()}" optionKey="id" required="" value="${anticipoDetalleInstance?.anticipo?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoDetalleInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="anticipoDetalle.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${anticipoDetalleInstance?.lote}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoDetalleInstance, field: 'nombreCliente', 'error')} required">
	<label for="nombreCliente">
		<g:message code="anticipoDetalle.nombreCliente.label" default="Nombre Cliente" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCliente" required="" value="${anticipoDetalleInstance?.nombreCliente}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoDetalleInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="anticipoDetalle.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreEmpresa" required="" value="${anticipoDetalleInstance?.nombreEmpresa}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoDetalleInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="anticipoDetalle.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="fechaDeRecepcion" required="" value="${anticipoDetalleInstance?.fechaDeRecepcion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoDetalleInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="anticipoDetalle.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: anticipoDetalleInstance, field: 'pesoBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoDetalleInstance, field: 'estadoAnticipo', 'error')} ">
	<label for="estadoAnticipo">
		<g:message code="anticipoDetalle.estadoAnticipo.label" default="Estado Anticipo" />
		
	</label>
	<g:textField name="estadoAnticipo" value="${anticipoDetalleInstance?.estadoAnticipo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoDetalleInstance, field: 'anticipoPagable', 'error')} required">
	<label for="anticipoPagable">
		<g:message code="anticipoDetalle.anticipoPagable.label" default="Anticipo Pagable" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="anticipoPagable" value="${fieldValue(bean: anticipoDetalleInstance, field: 'anticipoPagable')}" required="" inputmode="decimal"/>
</div>

