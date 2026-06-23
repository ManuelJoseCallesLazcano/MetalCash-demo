<%@ page import="org.socymet.cancelacion.PagoBonoTransporte" %>



<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'numeroComprobante', 'error')} " style="display: none">
	<label for="numeroComprobante">
		<g:message code="pagoBonoTransporte.numeroComprobante.label" default="Numero Comprobante" />
		
	</label>
	<g:field name="numeroComprobante" type="number" value="${pagoBonoTransporteInstance.numeroComprobante}" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'ci', 'error')} required">
	<label for="ci">
		<g:message code="pagoBonoTransporte.ci.label" default="Ci" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="ci" required="" value="${pagoBonoTransporteInstance?.ci}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'nombreCobrador', 'error')} required">
	<label for="nombreCobrador">
		<g:message code="pagoBonoTransporte.nombreCobrador.label" default="Nombre Cobrador" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCobrador" required="" value="${pagoBonoTransporteInstance?.nombreCobrador}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'fechaDePago', 'error')} required">
    <label for="fechaDePago">
        <g:message code="pagoBonoTransporte.fechaDePago.label" default="Fecha De Pago" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDePago" precision="day"  value="${pagoBonoTransporteInstance?.fechaDePago}"  />
</div>

<div>
    <h1 style="font-weight: bold">Parametros de Filtrado</h1>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'automovil', 'error')} ">
	<label for="automovil">
		<g:message code="pagoBonoTransporte.automovil.label" default="Automovil" />
		
	</label>
	%{--<g:select id="automovil" name="automovil.id" from="${org.socymet.proveedor.Automovil.list()}" optionKey="id" value="${pagoBonoTransporteInstance?.automovil?.id}" class="many-to-one" noSelection="['null': '']"/>--}%
    <g:select id="automovil" name="automovil.id" from="${org.socymet.proveedor.Automovil.list([sort: 'placa'])}" optionKey="id" value="${pagoBonoTransporteInstance?.automovil?.id}" class="many-to-one" />
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="pagoBonoTransporte.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="month"  value="${pagoBonoTransporteInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="pagoBonoTransporte.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="month"  value="${pagoBonoTransporteInstance?.fechaFinal}"  />
</div>

%{--<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'acumulacionPorMes', 'error')} ">--}%
	%{--<label for="acumulacionPorMes">--}%
		%{--<g:message code="pagoBonoTransporte.acumulacionPorMes.label" default="Acumulacion Por Mes" />--}%
		%{----}%
	%{--</label>--}%
	%{--<g:textArea name="acumulacionPorMes" cols="40" rows="5" maxlength="500000" value="${pagoBonoTransporteInstance?.acumulacionPorMes}"/>--}%
%{--</div>--}%

<g:hiddenField name="acumulacionPorMes" value="${pagoBonoTransporteInstance?.acumulacionPorMes}" />

%{--<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'lotesBono', 'error')} ">--}%
	%{--<label for="lotesBono">--}%
		%{--<g:message code="pagoBonoTransporte.lotesBono.label" default="Lotes Bono" />--}%
		%{----}%
	%{--</label>--}%
	%{--<g:textArea name="lotesBono" cols="40" rows="5" maxlength="500000" value="${pagoBonoTransporteInstance?.lotesBono}"/>--}%
%{--</div>--}%

<g:hiddenField name="lotesBono" value="${pagoBonoTransporteInstance?.lotesBono}" />

<div id="_botones" style="text-align: center">
    <br>
    <button id="agregar" type="button">GENERAR HISTOGRAMA</button>
    %{--<button id="quitar" type="button">QUITAR MES SELECCIONADO</button>--}%
</div>

<div style="width: 350px; margin-left: auto; margin-right: auto;">
    <table id="acumulacionPorMesTabla"></table>
</div>
%{--<g:hiddenField name="acumulacionPorMes" value="${pagoBonoProduccionInstance?.acumulacionPorMes}" />--}%


<div>
    <h1 style="font-weight: bold">Totales</h1>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'numeroMesesPagables', 'error')} required">
    <label for="numeroMesesPagables">
        <g:message code="pagoBonoTransporte.numeroMesesPagables.label" default="Numero Meses Pagables" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="numeroMesesPagables" type="number" value="${pagoBonoTransporteInstance.numeroMesesPagables}" required="" readonly="readonly" class="rojo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'numeroMesesAcumulados', 'error')} required">
	<label for="numeroMesesAcumulados">
		<g:message code="pagoBonoTransporte.numeroMesesAcumulados.label" default="Numero Meses Acumulados" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numeroMesesAcumulados" type="number" value="${pagoBonoTransporteInstance.numeroMesesAcumulados}" required="" readonly="readonly" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'totalKilosBrutos', 'error')} required">
	<label for="totalKilosBrutos">
		<g:message code="pagoBonoTransporte.totalKilosBrutos.label" default="Total Kilos Brutos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalKilosBrutos" value="${fieldValue(bean: pagoBonoTransporteInstance, field: 'totalKilosBrutos')}" required="" readonly="readonly" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'tipoDeCambio', 'error')} required" style="display: none">
	<label for="tipoDeCambio">
		<g:message code="pagoBonoTransporte.tipoDeCambio.label" default="Tipo De Cambio" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="tipoDeCambio" value="${fieldValue(bean: pagoBonoTransporteInstance, field: 'tipoDeCambio')}" required="" readonly="readonly" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'totalPagable', 'error')} required">
	<label for="totalPagable">
		<g:message code="pagoBonoTransporte.totalPagable.label" default="Total Pagable" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalPagable" value="${fieldValue(bean: pagoBonoTransporteInstance, field: 'totalPagable')}" required="" readonly="readonly" class="verde"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'totalPagableLiteral', 'error')} required">
	<label for="totalPagableLiteral">
		<g:message code="pagoBonoTransporte.totalPagableLiteral.label" default="Total Pagable Literal" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="totalPagableLiteral" required="" value="${pagoBonoTransporteInstance?.totalPagableLiteral}" size="90" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoTransporteInstance, field: 'observaciones', 'error')} ">
	<label for="observaciones">
		<g:message code="pagoBonoTransporte.observaciones.label" default="Observaciones" />
		
	</label>
	<g:textField name="observaciones" value="${pagoBonoTransporteInstance?.observaciones}" size="90"/>
</div>

