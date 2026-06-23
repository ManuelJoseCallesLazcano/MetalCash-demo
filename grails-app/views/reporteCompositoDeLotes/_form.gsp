<%@ page import="org.smart.compositos.Comprador; org.socymet.org.socymet.reportes.ReporteCompositoDeLotes" %>

<g:hiddenField name="vista" value="" readonly="readonly" />
<g:hiddenField name="roles" value="" readonly="readonly" />

<div id="_deposito" class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'deposito', 'error')} required" style="display: none">
    <label for="deposito">
        <g:message code="reporteCompositoDeLotes.deposito.label" default="Deposito" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${reporteCompositoDeLotesInstance?.deposito?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'sigla', 'error')} required">
    <label for="sigla">
        <g:message code="reporteCompositoDeLotes.sigla.label" default="Sigla" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="sigla" required="" value="${reporteCompositoDeLotesInstance?.sigla}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'destino', 'error')} required">
    <label for="destino">
        <g:message code="reporteCompositoDeLotes.destino.label" default="Destino" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="destino" from="${['VENTA','EXPORTACION','INGENIO']}" required="" value="${reporteCompositoDeLotesInstance?.destino}" valueMessagePrefix="reporteCompositoDeLotes.destino"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'nombreDestino', 'error')} required" hidden>
    <label for="nombreDestino">
        <g:message code="reporteCompositoDeLotes.nombreDestino.label" default="Nombre Destino" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreDestino" required="" value="${reporteCompositoDeLotesInstance?.nombreDestino}" size="50" readonly="readonly" class="amarillo"/>
</div>

<div id="_comprador" class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'comprador', 'error')} required">
    <label for="comprador">
        <g:message code="reporteCompositoDeLotes.comprador.label" default="Comprador"/>
        <span class="required-indicator">*</span>
    </label>

    <g:select id="comprador" name="comprador.id" from="${org.smart.compositos.Comprador.list([sort: 'nombreComprador'])}" optionKey="id" required="" value="${reporteCompositoDeLotesInstance?.comprador?.id}" class="many-to-one, chosen-select"/>
</div>

<div id="_ingenio" class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'ingenio', 'error')} required">
    <label for="ingenio">
        <g:message code="reporteCompositoDeLotes.ingenio.label" default="Ingenio"/>
        <span class="required-indicator">*</span>
    </label>

    <g:select id="ingenio" name="ingenio.id" from="${org.smart.compositos.Ingenio.list([sort: 'nombreIngenio'])}" optionKey="id" required="" value="${reporteCompositoDeLotesInstance?.ingenio?.id}" class="many-to-one, chosen-select"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'elaboradoPor', 'error')} required">
    <label for="elaboradoPor">
        <g:message code="reporteCompositoDeLotes.elaboradoPor.label" default="Elaborado Por" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="elaboradoPor" required="" value="${reporteCompositoDeLotesInstance?.elaboradoPor}" size="50" readonly="readonly" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'fechaDeElaboracion', 'error')} required">
    <label for="fechaDeElaboracion">
        <g:message code="reporteCompositoDeLotes.fechaDeElaboracion.label" default="Fecha De Elaboracion" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeElaboracion" precision="day"  value="${reporteCompositoDeLotesInstance?.fechaDeElaboracion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'estadoDelComposito', 'error')} required">
    <label for="estadoDelComposito">
        <g:message code="reporteCompositoDeLotes.estadoDelComposito.label" default="Estado Del Composito" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="estadoDelComposito" from="${['PROVISIONAL','DEFINITIVO']}" required="" value="${reporteCompositoDeLotesInstance?.estadoDelComposito}" valueMessagePrefix="reporteCompositoDeLotes.estadoDelComposito"/>
</div>

<h1 style="font-weight: bold">Lotes Disponibles</h1>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'empresa', 'error')} ">
    <label for="empresa">
        <g:message code="reporteCompositoDeLotes.empresa.label" default="Empresa" />

    </label>
    %{--<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteCompositoDeLotesInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '-TODOS-']"/>--}%
    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${reporteCompositoDeLotesInstance?.empresa?.id}" class="many-to-one, chosen-select" noSelection="['null': '-TODOS-']"/>
    %{--<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${reporteCompositoDeLotesInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '-TODOS-']"/>--}%
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'ordenarElemento', 'error')} required">
    <label for="ordenarElemento">
        <g:message code="reporteCompositoDeLotes.ordenarElemento.label" default="Ordenar Por Elemento" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="ordenarElemento" from="${['ZINC','PLOMO','PLATA']}" required="" value="${reporteCompositoDeLotesInstance?.ordenarElemento}" valueMessagePrefix="reporteCompositoDeLotes.ordenarElemento"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'fechaInicial', 'error')} required" style="display: none">
    <label for="fechaInicial">
        <g:message code="reporteCompositoDeLotes.fechaInicial.label" default="Fecha Inicial" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaInicial" precision="day"  value="${reporteCompositoDeLotesInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'fechaFinal', 'error')} required" style="display: none">
    <label for="fechaFinal">
        <g:message code="reporteCompositoDeLotes.fechaFinal.label" default="Fecha Final" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaFinal" precision="day"  value="${reporteCompositoDeLotesInstance?.fechaFinal}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'leyMinimaZinc', 'error')} required" style="display: none">
    <label for="leyMinimaZinc">
        <g:message code="reporteCompositoDeLotes.leyMinimaZinc.label" default="Ley Minima Zinc" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="leyMinimaZinc" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'leyMinimaZinc')}" required="" inputmode="decimal"/>

    <label for="leyMaximaZinc">
        <g:message code="reporteCompositoDeLotes.leyMaximaZinc.label" default="Ley Maxima Zinc" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="leyMaximaZinc" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'leyMaximaZinc')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'leyMaximaZinc', 'error')} required">
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'leyMinimaPlomo', 'error')} required" style="display: none">
    <label for="leyMinimaPlomo">
        <g:message code="reporteCompositoDeLotes.leyMinimaPlomo.label" default="Ley Minima Plomo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="leyMinimaPlomo" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'leyMinimaPlomo')}" required="" inputmode="decimal"/>

    <label for="leyMaximaPlomo">
        <g:message code="reporteCompositoDeLotes.leyMaximaPlomo.label" default="Ley Maxima Plomo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="leyMaximaPlomo" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'leyMaximaPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'leyMaximaPlomo', 'error')} required">
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'leyMinimaPlata', 'error')} required" style="display: none">
    <label for="leyMinimaPlata">
        <g:message code="reporteCompositoDeLotes.leyMinimaPlata.label" default="Ley Minima Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="leyMinimaPlata" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'leyMinimaPlata')}" required="" inputmode="decimal"/>

    <label for="leyMaximaPlata">
        <g:message code="reporteCompositoDeLotes.leyMaximaPlata.label" default="Ley Maxima Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="leyMaximaPlata" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'leyMaximaPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'leyMaximaPlata', 'error')} required">
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'lotes', 'error')} " style="display: none">
    <label for="lotes">
        <g:message code="reporteCompositoDeLotes.lotes.label" default="Lotes" />

    </label>
    <g:textArea name="lotes" cols="40" rows="5" maxlength="2000000" value="${reporteCompositoDeLotesInstance?.lotes}" readonly="readonly"/>
</div>

<div id="_botones" style="text-align: center">
    <br>
    <button id="agregar" type="button">BUSCAR LOTES</button>
</div>

%{--<div style="width: 1010px; margin-left: auto; margin-right: auto;">--}%
    %{--<table id="lotesAsignados"></table>--}%
%{--</div>--}%

<div style="width: 1000px; margin-left: auto; margin-right: auto;">
    <table id="lotesDisponibles"></table>
</div>

<h1 style="font-weight: bold">Lotes en Comp&oacute;sito</h1>

<div id="_botones" style="text-align: center">
    <br>
    <button id="asignar" type="button">ASIGNAR A COMPOSITO</button>
    <button id="retornar" type="button">RETORNAR LOTES</button>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'lotesComposito', 'error')} " style="display: none">
    <label for="lotesComposito">
        <g:message code="reporteCompositoDeLotesInstance.lotesComposito.label" default="Lotes Composito" />

    </label>
    <g:textArea name="lotesComposito" cols="40" rows="5" value="${reporteCompositoDeLotesInstance?.lotesComposito}" readonly="readonly"/>
</div>

<div style="width: 1000px; margin-left: auto; margin-right: auto;">
    <table id="lotesCompositoTabla"></table>
</div>

<h1 style="font-weight: bold">Participaci&oacute;n</h1>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'participacion', 'error')} " style="display: none">
    <label for="participacion">
        <g:message code="reporteCompositoDeLotes.participacion.label" default="Participacion" />

    </label>
    <g:textArea name="participacion" cols="40" rows="5" maxlength="2000000" value="${reporteCompositoDeLotesInstance?.participacion}" readonly="readonly"/>
</div>

<div style="width: 700px; margin-left: auto; margin-right: auto;">
    <table id="participacionTabla"></table>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'totalKilosBrutos', 'error')} required" style="display: none">
    <label for="totalKilosBrutos">
        <g:message code="reporteCompositoDeLotes.totalKilosBrutos.label" default="Total Kilos Brutos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalKilosBrutos" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'totalKilosBrutos')}" required="" class="amarillo" readonly="readonly"/>

    <label for="totalKilosNetosSecos">
        <g:message code="reporteCompositoDeLotes.totalKilosNetosSecos.label" default="Total Kilos Netos Secos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalKilosNetosSecos" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'totalKilosNetosSecos')}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'totalKilosNetosSecos', 'error')} required">
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'leyPromedioZinc', 'error')} required" style="display: none">
    <label for="leyPromedioZinc">
        <g:message code="reporteCompositoDeLotes.leyPromedioZinc.label" default="Ley Promedio Zinc" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="leyPromedioZinc" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'leyPromedioZinc')}" required="" class="amarillo" readonly="readonly"/>

    <label for="totalKilosFinosZinc">
        <g:message code="reporteCompositoDeLotes.totalKilosFinosZinc.label" default="Total Kilos Finos Zinc" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalKilosFinosZinc" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'totalKilosFinosZinc')}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'leyPromedioPlomo', 'error')} required" style="display: none">
    <label for="leyPromedioPlomo">
        <g:message code="reporteCompositoDeLotes.leyPromedioPlomo.label" default="Ley Promedio Plomo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="leyPromedioPlomo" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'leyPromedioPlomo')}" required="" class="amarillo" readonly="readonly"/>

    <label for="totalKilosFinosPlomo">
        <g:message code="reporteCompositoDeLotes.totalKilosFinosPlomo.label" default="Total Kilos Finos Plomo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalKilosFinosPlomo" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'totalKilosFinosPlomo')}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'leyPromedioPlata', 'error')} required" style="display: none">
    <label for="leyPromedioPlata">
        <g:message code="reporteCompositoDeLotes.leyPromedioPlata.label" default="Ley Promedio Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="leyPromedioPlata" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'leyPromedioPlata')}" required="" class="amarillo" readonly="readonly"/>

    <label for="totalKilosFinosPlata">
        <g:message code="reporteCompositoDeLotes.totalKilosFinosPlata.label" default="Total Kilos Finos Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalKilosFinosPlata" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'totalKilosFinosPlata')}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'totalKilosFinosZinc', 'error')} required">
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'totalKilosFinosPlomo', 'error')} required">
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'totalKilosFinosPlata', 'error')} required">
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'totalValorNeto', 'error')} required" style="display: none">
    <label for="totalValorNeto">
        <g:message code="reporteCompositoDeLotes.totalValorNeto.label" default="Total Valor Neto"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalValorNeto" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'totalValorNeto')}" required="" class="amarillo" readonly="readonly"/>

    <label for="totalValorDeCompra">
        <g:message code="reporteCompositoDeLotes.totalValorDeCompra.label" default="Total Valor De Compra" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalValorDeCompra" value="${fieldValue(bean: reporteCompositoDeLotesInstance, field: 'totalValorDeCompra')}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'totalValorDeCompra', 'error')} required">
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'estadoDeAprobacion', 'error')} required" style="display:none;">
    <label for="estadoDeAprobacion">
        <g:message code="reporteCompositoDeLotes.estadoDeAprobacion.label" default="Estado De Aprobacion" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="estadoDeAprobacion" from="${['PENDIENTE','APROBADO']}" required="" value="${reporteCompositoDeLotesInstance?.estadoDeAprobacion}" valueMessagePrefix="reporteCompositoDeLotes.estadoDeAprobacion"/>
</div>

<div id="_aprobadoPor" class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'aprobadoPor', 'error')} required" style="display: none">
    <label for="aprobadoPor">
        <g:message code="reporteCompositoDeLotes.aprobadoPor.label" default="Aprobado Por" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="aprobadoPor" required="" value="${reporteCompositoDeLotesInstance?.aprobadoPor}" size="50" readonly="readonly" class="amarillo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="reporteCompositoDeLotes.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${reporteCompositoDeLotesInstance?.observaciones}" size="90"/>
</div>

