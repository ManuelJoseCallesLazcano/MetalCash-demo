<%@ page import="org.socymet.liquidacion.LiquidacionGrupalDePlomoPlata" %>

<h1 style="font-weight: bold">Seleccion de Lotes</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDePlomoPlataInstance, field: 'deposito', 'error')} required">
    <label for="deposito">
        <g:message code="liquidacionGrupalDePlomoPlata.deposito.label" default="Deposito" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${liquidacionGrupalDePlomoPlataInstance?.deposito?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDePlomoPlataInstance, field: 'loteInicial', 'error')} required">
    <label for="loteInicial">
        <g:message code="liquidacionGrupalDePlomoPlata.loteInicial.label" default="Lote Inicial" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="loteInicial" type="number" value="${liquidacionGrupalDePlomoPlataInstance.loteInicial}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDePlomoPlataInstance, field: 'loteFinal', 'error')} required">
    <label for="loteFinal">
        <g:message code="liquidacionGrupalDePlomoPlata.loteFinal.label" default="Lote Final" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="loteFinal" type="number" value="${liquidacionGrupalDePlomoPlataInstance.loteFinal}" required="" inputmode="numeric"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDePlomoPlataInstance, field: 'millis', 'error')} ">--}%
%{--<label for="millis">--}%
%{--<g:message code="liquidacionGrupalDePlomoPlata.millis.label" default="Millis" />--}%
%{----}%
%{--</label>--}%
%{--<g:field name="millis" value="${fieldValue(bean: liquidacionGrupalDePlomoPlataInstance, field: 'millis')}" inputmode="decimal"/>--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDePlomoPlataInstance, field: 'lotes', 'error')} required">--}%
%{--<label for="lotes">--}%
%{--<g:message code="liquidacionGrupalDePlomoPlata.lotes.label" default="Lotes" />--}%
%{--<span class="required-indicator">*</span>--}%
%{--</label>--}%
%{--<g:textArea name="lotes" cols="40" rows="5" maxlength="500000" required="" value="${liquidacionGrupalDePlomoPlataInstance?.lotes}"/>--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDePlomoPlataInstance, field: 'lotesLiquidados', 'error')} required">--}%
%{--<label for="lotesLiquidados">--}%
%{--<g:message code="liquidacionGrupalDePlomoPlata.lotesLiquidados.label" default="Lotes Liquidados" />--}%
%{--<span class="required-indicator">*</span>--}%
%{--</label>--}%
%{--<g:textArea name="lotesLiquidados" cols="40" rows="5" maxlength="500000" required="" value="${liquidacionGrupalDePlomoPlataInstance?.lotesLiquidados}"/>--}%
%{--</div>--}%

<g:hiddenField name="millis" value="${fieldValue(bean: liquidacionGrupalDePlomoPlataInstance, field: 'millis')}"/>
<g:hiddenField name="lotes" value="${liquidacionGrupalDePlomoPlataInstance?.lotes}"/>
<g:hiddenField name="lotesLiquidados" value="${liquidacionGrupalDePlomoPlataInstance?.lotesLiquidados}"/>

<div style="text-align: center;">
    <button id="agregar" type="button">BUSCAR LOTES</button>
    <button id="quitar" type="button">QUITAR LOTES SELECCIONADOS</button>
</div>

<h1 style="font-weight: bold">Valoracion de Lotes a Liquidar</h1>

<table id="nuevaTabla" style="font-size: 10px">
    <thead>
    <tr>
        <th></th>
        <th>ID</th>
        <th>LOTE</th>
        <th>% PB<br>CLIENTE</th>
        <th>DM AG<br>CLIENTE</th>
        <th>% PB<br>FINAL</th>
        <th>DM AG<br>FINAL</th>
        <th>MODO VAL.</th>
        <th>TABLA/TERMINO</th>
        <th>MARGEN</th>
        <th>V.P.T.</th>
        <th>TOTAL ANT.<br>C/ENTREGA</th>
        <th>LIQ. PAGABLE</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody></tbody>
</table>

<div style="text-align: center;">
    <button id="copiarLeyes" type="button">COPIAR LEYES DE EMPRESA</button>
</div>

<h1 style="font-weight: bold">Lotes Liquidados</h1>

<div style="width: 700px; margin-left: auto; margin-right: auto;">
    <table id="tablaLotesLiquidados">
    </table>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionGrupalDePlomoPlataInstance, field: 'total', 'error')} required">
    <label for="total">
        <g:message code="liquidacionGrupalDePlomoPlata.total.label" default="Total" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="total" value="${fieldValue(bean: liquidacionGrupalDePlomoPlataInstance, field: 'total')}" required="" style="font-size: 16px; font-weight: bold; color: #b81900" inputmode="decimal"/>
</div>

<div style="text-align: center;">
    <button id="imprimir" type="button">IMPRIMIR LOTES LIQUIDADOS</button>
    %{--<input id="valorar" type="button" value="VALORAR LOTE" style="background-color: #255b17; color: white; font-size: 16px;"/>--}%
</div>
