<%@ page import="org.socymet.cancelacion.PagoManipuleo" %>



<div class="fieldcontain ${hasErrors(bean: pagoManipuleoInstance, field: 'ci', 'error')} required">
	<label for="ci">
		<g:message code="pagoManipuleo.ci.label" default="Ci" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="ci" required="" value="${pagoManipuleoInstance?.ci}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoManipuleoInstance, field: 'nombreCobrador', 'error')} required">
	<label for="nombreCobrador">
		<g:message code="pagoManipuleo.nombreCobrador.label" default="Nombre Cobrador" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCobrador" required="" value="${pagoManipuleoInstance?.nombreCobrador}" size="90"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoManipuleoInstance, field: 'fechaDePago', 'error')} required">
	<label for="fechaDePago">
		<g:message code="pagoManipuleo.fechaDePago.label" default="Fecha De Pago" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDePago" precision="day"  value="${pagoManipuleoInstance?.fechaDePago}"  />
</div>

<div>
    <h1 style="font-weight: bold">Lotes Asignados</h1>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoManipuleoInstance, field: 'deposito', 'error')} required">
	<label for="deposito">
		<g:message code="pagoManipuleo.deposito.label" default="Deposito" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${pagoManipuleoInstance?.deposito?.id}" class="many-to-one"/>
</div>

<g:hiddenField name="pesadaVaciada" value="" readonly="readonly"/>
<g:hiddenField name="carguioMaquina" value="" readonly="readonly"/>
<g:hiddenField name="embolsadaArrumada" value="" readonly="readonly"/>
<g:hiddenField name="soloComuneada" value="" readonly="readonly"/>
<g:hiddenField name="soloVaciada" value="" readonly="readonly"/>
<g:hiddenField name="soloPesada" value="" readonly="readonly"/>
<g:hiddenField name="soloEmbolsada" value="" readonly="readonly"/>

<div id="_botones" style="text-align: center">
    <br>
    <button id="agregar" type="button">BUSCAR LOTES</button>
    <button id="quitar" type="button">QUITAR LOTE SELECCIONADO</button>
</div>

<div class="fieldcontain">
    <label for="todosPesadaVaciada">
        <g:message code="pagoManipuleo.todosPesadaVaciada.label" default="Todos por Pesada Vaciada" />

    </label>
    <g:checkBox name="todosPesadaVaciada" value="" />
</div>

<br>

<div style="width: 840px; margin-left: auto; margin-right: auto;">
    <table id="lotesAsignados"></table>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: pagoManipuleoInstance, field: 'lotes', 'error')} ">--}%
	%{--<label for="lotes">--}%
		%{--<g:message code="pagoManipuleo.lotes.label" default="Lotes" />--}%
		%{----}%
	%{--</label>--}%
	%{--<g:textArea name="lotes" cols="40" rows="5" maxlength="500000" value="${pagoManipuleoInstance?.lotes}"/>--}%
%{--</div>--}%
<g:hiddenField name="lotes" value="${pagoManipuleoInstance?.lotes}" readonly="true" />

<div class="fieldcontain ${hasErrors(bean: pagoManipuleoInstance, field: 'descripcion', 'error')} required">
	<label for="descripcion">
		<g:message code="pagoManipuleo.descripcion.label" default="Descripcion" />
		<span class="required-indicator">*</span>
	</label>
	%{--<g:textArea name="descripcion" cols="40" rows="5" maxlength="50000" required="" value="${pagoManipuleoInstance?.descripcion}"/>--}%
    <g:textField name="descripcion" required="" value="${pagoManipuleoInstance?.descripcion}" size="90" readonly="true" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoManipuleoInstance, field: 'totalPagable', 'error')} required">
	<label for="totalPagable">
		<g:message code="pagoManipuleo.totalPagable.label" default="Total Pagable" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalPagable" value="${fieldValue(bean: pagoManipuleoInstance, field: 'totalPagable')}" required="" readonly="readonly" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoManipuleoInstance, field: 'totalPagableLiteral', 'error')} required">
	<label for="totalPagableLiteral">
		<g:message code="pagoManipuleo.totalPagableLiteral.label" default="Total Pagable Literal" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="totalPagableLiteral" required="" value="${pagoManipuleoInstance?.totalPagableLiteral}" size="90" readonly="readonly" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoManipuleoInstance, field: 'observaciones', 'error')} ">
	<label for="observaciones">
		<g:message code="pagoManipuleo.observaciones.label" default="Observaciones" />
		
	</label>
	<g:textField name="observaciones" value="${pagoManipuleoInstance?.observaciones}" size="90"/>
</div>

