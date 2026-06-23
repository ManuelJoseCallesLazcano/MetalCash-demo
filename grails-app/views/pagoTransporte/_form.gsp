<%@ page import="org.socymet.cancelacion.PagoTransporte" %>

<g:hiddenField name="vista" value="" />

<div id="_deposito" class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'deposito', 'error')} required" style="display: none">
	<label for="deposito">
		<g:message code="pagoTransporte.deposito.label" default="Deposito" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${pagoTransporteInstance?.deposito?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'ci', 'error')} required">
	<label for="ci">
		<g:message code="pagoTransporte.ci.label" default="Ci" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="ci" required="" value="${pagoTransporteInstance?.ci}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'nombreCobrador', 'error')} required">
	<label for="nombreCobrador">
		<g:message code="pagoTransporte.nombreCobrador.label" default="Nombre Cobrador" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCobrador" required="" value="${pagoTransporteInstance?.nombreCobrador}" size="90"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'fechaDePago', 'error')} required" style="display: none">
	<label for="fechaDePago">
		<g:message code="pagoTransporte.fechaDePago.label" default="Fecha De Pago" />
		<span class="required-indicator">*</span>
	</label>
	<g:datepickerUI name="fechaDePago" value="${pagoTransporteInstance?.fechaDePago ?: new Date()}"/>
</div>


<div>
    <h1 style="font-weight: bold">Lotes Asignados</h1>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'solicitante', 'error')} " hidden>
    <label for="solicitante">
        <g:message code="pagoTransporte.solicitante.label" default="Solicitante" />

    </label>
    <g:select name="solicitante" from="${['Empresa','Particular']}" value="${pagoTransporteInstance?.solicitante}" valueMessagePrefix="pagoTransporte.solicitante"/>
</div>

<div id="_empresa" class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'empresa', 'error')} ">
    <label for="empresa">
        <g:message code="pagoTransporte.empresa.label" default="Empresa" />

    </label>
    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${pagoTransporteInstance?.empresa?.id}" class="many-to-one, chosen-select"/>
</div>

<div id="_automovil" class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'automovil', 'error')} ">
    <label for="automovil">
        <g:message code="pagoTransporte.automovil.label" default="Automovil" />

    </label>
    <g:select id="automovil" name="automovil.id" from="${org.socymet.proveedor.Automovil.list([sort: "placa"])}" optionKey="id" value="${pagoTransporteInstance?.automovil?.id}" class="many-to-one, chosen-select"/>
</div>

<div class="fieldcontain" style="display:none;">
    <label for="depositoId">Deposito</label>
    <g:select name="depositoId" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id"/>
</div>

<div id="_botones" style="text-align: center">
    <br>
    <button id="agregar" type="button">BUSCAR LOTES</button>
%{--    <button id="quitar" type="button">QUITAR LOTE SELECCIONADO</button>--}%
%{--    <button id="actualizar" type="button">ACTUALIZAR LOTES SELECCIONADOS</button>--}%
</div>

<g:hiddenField name="lotes" value="${pagoTransporteInstance?.lotes}" />

<div style="width: 840px; margin-left: auto; margin-right: auto;">
    <table id="lotesAsignados"></table>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'recepcionId', 'error')} required" style="display: none">
	<label for="recepcionId">
		<g:message code="pagoTransporte.recepcionId.label" default="Recepcion Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="recepcionId" value="${fieldValue(bean: pagoTransporteInstance, field: 'recepcionId')}" required="" readonly="true" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="pagoTransporte.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: pagoTransporteInstance, field: 'pesoBruto')}" required="" readonly="true" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'precioTonelada', 'error')} required">
	<label for="precioTonelada">
		<g:message code="pagoTransporte.precioTonelada.label" default="Precio Tonelada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="precioTonelada" value="${fieldValue(bean: pagoTransporteInstance, field: 'precioTonelada')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'descripcion', 'error')} required">
    <label for="descripcion">
        <g:message code="pagoTransporte.descripcion.label" default="Descripcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="descripcion" required="" value="${pagoTransporteInstance?.descripcion}" size="90" readonly="true" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'total', 'error')} required">
	<label for="total">
		<g:message code="pagoTransporte.total.label" default="Total" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="total" value="${fieldValue(bean: pagoTransporteInstance, field: 'total')}" required="" readonly="true" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'totalAnticipos', 'error')} required">
	<label for="totalAnticipos">
		<g:message code="pagoTransporte.totalAnticipos.label" default="Total Anticipos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalAnticipos" value="${fieldValue(bean: pagoTransporteInstance, field: 'totalAnticipos')}" required="" readonly="true" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'totalPagable', 'error')} required">
	<label for="totalPagable">
		<g:message code="pagoTransporte.totalPagable.label" default="Total Pagable" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalPagable" value="${fieldValue(bean: pagoTransporteInstance, field: 'totalPagable')}" required="" readonly="true" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'totalPagableLiteral', 'error')} required">
	<label for="totalPagableLiteral">
		<g:message code="pagoTransporte.totalPagableLiteral.label" default="Total Pagable Literal" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="totalPagableLiteral" required="" value="${pagoTransporteInstance?.totalPagableLiteral}" size="90" readonly="true" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoTransporteInstance, field: 'observaciones', 'error')} ">
	<label for="observaciones">
		<g:message code="pagoTransporte.observaciones.label" default="Observaciones" />
		
	</label>
	<g:textField name="observaciones" value="${pagoTransporteInstance?.observaciones}" size="90"/>
</div>

