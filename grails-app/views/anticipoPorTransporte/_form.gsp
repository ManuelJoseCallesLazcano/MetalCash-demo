<%@ page import="org.socymet.anticipos.AnticipoPorTransporte" %>

<div class="fieldcontain ${hasErrors(bean: anticipoPorTransporteInstance, field: 'recepcionDeComplejo', 'error')} required">
	<label for="recepcionDeComplejo">
		<g:message code="anticipoPorTransporte.recepcionDeComplejo.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	%{--        <g:select id="recepcionDeComplejo" name="recepcionDeComplejo.id" from="${org.socymet.recepcion.RecepcionDeComplejo.findAllByEstadoAnalisisAndEstadoDelLote("SIN ANALISIS","NO LIQUIDADO")}" optionKey="id" required="" value="${anticipoPorTransporteInstance?.recepcionDeComplejo?.id}" class="many-to-one, chosen-select"  style="width: 350px"/>--}%
%{--	this.transportePagado = "NO"--}%
	<g:select id="recepcionDeComplejo" name="recepcionDeComplejo.id" from="${org.socymet.recepcion.RecepcionDeComplejo.findAllByTransportePagadoAndEstadoDelLote("NO","NO LIQUIDADO", [sort: 'id', order: 'desc'])}" optionKey="id" required="" value="${anticipoPorTransporteInstance?.recepcionDeComplejo?.id}" class="many-to-one, chosen-select"  style="width: 350px"/>

</div>

<div class="fieldcontain ${hasErrors(bean: anticipoPorTransporteInstance, field: 'solicitante', 'error')} " hidden>
	<label for="solicitante">
		<g:message code="anticipoPorTransporte.solicitante.label" default="Solicitante" />
		
	</label>
	<g:select name="solicitante" from="${['Empresa','Particular']}" value="${anticipoPorTransporteInstance?.solicitante}" valueMessagePrefix="anticipoPorTransporte.solicitante" class="chosen-select" />
</div>

<div id="_empresa" class="fieldcontain ${hasErrors(bean: anticipoPorTransporteInstance, field: 'empresa', 'error')} " hidden>
	<label for="empresa">
		<g:message code="anticipoPorTransporte.empresa.label" default="Empresa" />
		
	</label>
%{--	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${anticipoPorTransporteInstance?.empresa?.id}" class="many-to-one, chosen-select" noSelection="['null': '-SELECCIONE-']"/>--}%
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${anticipoPorTransporteInstance?.empresa?.id}" class="many-to-one, chosen-select" />
</div>

<div id="_automovil" class="fieldcontain ${hasErrors(bean: anticipoPorTransporteInstance, field: 'automovil', 'error')} " hidden>
	<label for="automovil">
		<g:message code="anticipoPorTransporte.automovil.label" default="Automovil" />
		
	</label>
%{--	<g:select id="automovil" name="automovil.id" from="${org.socymet.proveedor.Automovil.list([sort:"placa"])}" optionKey="id" value="${anticipoPorTransporteInstance?.automovil?.id}" class="many-to-one, chosen-select" noSelection="['null': '-SELECCIONE-']"/>--}%
	<g:select id="automovil" name="automovil.id" from="${org.socymet.proveedor.Automovil.list([sort:"placa"])}" optionKey="id" value="${anticipoPorTransporteInstance?.automovil?.id}" class="many-to-one, chosen-select" />
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoPorTransporteInstance, field: 'ci', 'error')} required">
	<label for="ci">
		<g:message code="anticipoPorTransporte.ci.label" default="Ci" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="ci" required="" value="${anticipoPorTransporteInstance?.ci}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoPorTransporteInstance, field: 'nombreCobrador', 'error')} required">
	<label for="nombreCobrador">
		<g:message code="anticipoPorTransporte.nombreCobrador.label" default="Nombre Cobrador" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCobrador" required="" value="${anticipoPorTransporteInstance?.nombreCobrador}" size="90"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoPorTransporteInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="anticipoPorTransporte.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${anticipoPorTransporteInstance?.fecha}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoPorTransporteInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="anticipoPorTransporte.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descripcion" required="" value="${anticipoPorTransporteInstance?.descripcion}" size="90"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoPorTransporteInstance, field: 'ultimoSaldo', 'error')} required" hidden>
    <label for="ultimoSaldo">
        <g:message code="anticipoPorTransporte.ultimoSaldo.label" default="Ultimo Saldo"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field name="ultimoSaldo" value="${fieldValue(bean: anticipoPorTransporteInstance, field: 'ultimoSaldo')}" class="amarillo" readonly="true"
             required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoPorTransporteInstance, field: 'importe', 'error')} required">
    <label for="importe">
        <g:message code="anticipoPorTransporte.importe.label" default="Importe"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field name="importe" value="${fieldValue(bean: anticipoPorTransporteInstance, field: 'importe')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoPorTransporteInstance, field: 'importeLiteral', 'error')} required">
    <label for="importeLiteral">
        <g:message code="anticipoPorTransporte.importeLiteral.label" default="Importe Literal"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="importeLiteral" required="" value="${anticipoPorTransporteInstance?.importeLiteral}" size="90" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: anticipoPorTransporteInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="anticipoPorTransporte.observaciones.label" default="Observaciones"/>

    </label>
    <g:textField name="observaciones" value="${anticipoPorTransporteInstance?.observaciones}" size="90"/>
</div>

