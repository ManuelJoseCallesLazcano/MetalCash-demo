<%@ page import="org.socymet.cancelacion.PagoDeRetenciones" %>

<div id="_deposito" class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'deposito', 'error')} required" style="display: none">
    <label for="deposito">
        <g:message code="pagoDeRetenciones.deposito.label" default="Deposito" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${pagoDeRetencionesInstance?.deposito?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'ci', 'error')} required">
    <label for="ci">
        <g:message code="pagoDeRetenciones.ci.label" default="Ci" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="ci" required="" value="${pagoDeRetencionesInstance?.ci}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'nombreCobrador', 'error')} required">
    <label for="nombreCobrador">
        <g:message code="pagoDeRetenciones.nombreCobrador.label" default="Nombre Cobrador" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCobrador" required="" value="${pagoDeRetencionesInstance?.nombreCobrador}" size="90"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'beneficiario', 'error')} required">
    <label for="beneficiario">
        <g:message code="pagoDeRetenciones.beneficiario.label" default="Beneficiario" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="beneficiario" required="" value="${pagoDeRetencionesInstance?.beneficiario}" size="90"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'fechaDePago', 'error')} required">
    <label for="fechaDePago">
        <g:message code="pagoDeRetenciones.fechaDePago.label" default="Fecha De Pago" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDePago" precision="day"  value="${pagoDeRetencionesInstance?.fechaDePago}"  />
</div>

<div>
    <h1 style="font-weight: bold">Selección de Retenciones a pagar</h1>
</div>

<div id="_periodo1" class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'periodo', 'error')} required">
    <label for="periodo">
        <g:message code="pagoDeRetenciones.periodo.label" default="Periodo" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="periodo" precision="month"  value="${pagoDeRetencionesInstance?.periodo}"  />
</div>

<div id="_periodo2" class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'periodo', 'error')} required" style="display: none">
    <label for="periodo2">
        <g:message code="pagoDeRetenciones.periodo.label" default="Periodo" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="periodo2" required="" readonly="true"/>
</div>

<div id="_quincena1" class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'quincena', 'error')} required">
    <label for="quincena">
        <g:message code="pagoDeRetenciones.quincena.label" default="Quincena" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="quincena" from="${['1ra. QUINCENA','2da. QUINCENA']}" required="" value="${pagoDeRetencionesInstance?.quincena}" valueMessagePrefix="pagoDeRetenciones.quincena"/>
</div>

<div id="_quincena2" class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'quincena', 'error')} required" style="display: none">
    <label for="quincena2">
        <g:message code="pagoDeRetenciones.quincena.label" default="Quincena" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="quincena2" required="" readonly="true"/>
</div>

<div id="_empresa1" class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'empresa', 'error')} ">
    <label for="empresa">
        <g:message code="pagoDeRetenciones.empresa.label" default="Empresa" />

    </label>
    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${pagoDeRetencionesInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '-TODAS LAS EMPRESAS-']"/>
</div>

<div id="_empresa2" class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'empresa', 'error')} " style="display: none">
    <label for="empresa2">
        <g:message code="pagoDeRetenciones.empresa.label" default="Empresa" />

    </label>
    <g:textField name="empresa2" required="" readonly="true"/>
</div>

<div id="_botones1" style="text-align: center">
    <br>
    <button id="buscarRetenciones" type="button">BUSCAR RETENCIONES A PAGAR</button>
    <button id="adicionarRetencion" type="button">ADICIONAR RETENCION SELECCIONADA</button>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'retenciones', 'error')} required">--}%
    %{--<label for="retenciones">--}%
        %{--<g:message code="pagoDeRetenciones.retenciones.label" default="Retenciones" />--}%
        %{--<span class="required-indicator">*</span>--}%
    %{--</label>--}%
    %{--<g:textArea name="retenciones" cols="40" rows="5" maxlength="50000" required="" value="${pagoDeRetencionesInstance?.retenciones}"/>--}%
%{--</div>--}%
<g:hiddenField name="retenciones" value="${pagoDeRetencionesInstance?.retenciones}"/>

<div style="width: 200px; margin-left: auto; margin-right: auto;">
    <table id="tablaRetenciones"></table>
</div>

<div>
    <h1 style="font-weight: bold">Detalle de Retenciones a pagar</h1>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'retencionesSeleccionadas', 'error')} required">--}%
    %{--<label for="retencionesSeleccionadas">--}%
        %{--<g:message code="pagoDeRetenciones.retencionesSeleccionadas.label" default="Retenciones Seleccionadas" />--}%
        %{--<span class="required-indicator">*</span>--}%
    %{--</label>--}%
    %{--<g:textArea name="retencionesSeleccionadas" cols="40" rows="5" maxlength="50000" required="" value="${pagoDeRetencionesInstance?.retencionesSeleccionadas}"/>--}%
%{--</div>--}%
<g:hiddenField name="retencionesSeleccionadas" value="${pagoDeRetencionesInstance?.retencionesSeleccionadas}"/>

<div style="width: 300px; margin-left: auto; margin-right: auto;">
    <table id="tablaRetencionesSeleccionadas"></table>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'descripcion', 'error')} required">
    <label for="descripcion">
        <g:message code="pagoDeRetenciones.descripcion.label" default="Descripcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="descripcion" required="" value="${pagoDeRetencionesInstance?.descripcion}" size="90" readonly="true" class="amarillo"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'lotes', 'error')} ">--}%
    %{--<label for="lotes">--}%
        %{--<g:message code="pagoDeRetenciones.lotes.label" default="Lotes" />--}%

    %{--</label>--}%
    %{--<g:textArea name="lotes" cols="40" rows="5" maxlength="1000000" value="${pagoDeRetencionesInstance?.lotes}"/>--}%
%{--</div>--}%
<g:hiddenField name="lotes" value="${pagoDeRetencionesInstance?.lotes}"/>

<div style="width: 840px; margin-left: auto; margin-right: auto;">
    <table id="tablaLotes"></table>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'totalPagable', 'error')} required">
    <label for="totalPagable">
        <g:message code="pagoDeRetenciones.totalPagable.label" default="Total Pagable" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalPagable" value="${fieldValue(bean: pagoDeRetencionesInstance, field: 'totalPagable')}" required="" readonly="true" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'totalPagableLiteral', 'error')} required">
    <label for="totalPagableLiteral">
        <g:message code="pagoDeRetenciones.totalPagableLiteral.label" default="Total Pagable Literal" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="totalPagableLiteral" required="" value="${pagoDeRetencionesInstance?.totalPagableLiteral}" size="90" readonly="true" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoDeRetencionesInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="pagoDeRetenciones.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${pagoDeRetencionesInstance?.observaciones}" size="90"/>
</div>

