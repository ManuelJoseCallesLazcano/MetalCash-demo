<%@ page import="org.socymet.cancelacion.PagoBonoProduccion" %>

<g:hiddenField name="vista" value="" />

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'numeroComprobante', 'error')} " style="display: none">
    <label for="numeroComprobante">
        <g:message code="pagoBonoProduccion.numeroComprobante.label" default="Numero Comprobante" />

    </label>
    <g:field name="numeroComprobante" type="number" value="${pagoBonoProduccionInstance.numeroComprobante}" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'ci', 'error')} required">
    <label for="ci">
        <g:message code="pagoBonoProduccion.ci.label" default="Ci" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="ci" required="" value="${pagoBonoProduccionInstance?.ci}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'nombreCobrador', 'error')} required">
    <label for="nombreCobrador">
        <g:message code="pagoBonoProduccion.nombreCobrador.label" default="Nombre Cobrador" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCobrador" required="" value="${pagoBonoProduccionInstance?.nombreCobrador}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'fechaDePago', 'error')} required">
    <label for="fechaDePago">
        <g:message code="pagoBonoProduccion.fechaDePago.label" default="Fecha De Pago" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDePago" precision="day"  value="${pagoBonoProduccionInstance?.fechaDePago}"  />
</div>

<div>
    <h1 style="font-weight: bold">Parametros de Filtrado</h1>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'tipoSeleccion', 'error')} ">
    <label for="tipoSeleccion">
        <g:message code="pagoBonoProduccion.tipoSeleccion.label" default="Tipo Seleccion" />

    </label>
    <g:select name="tipoSeleccion" from="${['INDIVIDUAL','CUADRILLA']}" value="${pagoBonoProduccionInstance?.tipoSeleccion}" valueMessagePrefix="pagoBonoProduccion.tipoSeleccion" />
</div>

<div id="_nombreCliente" class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'nombreCliente', 'error')} required">
    <label>
        Nombre Cliente
    </label>
    <g:textField name="nombreCliente" value="${pagoBonoProduccionInstance?.nombreCliente}" size="50" />
</div>

<div id="_cliente" class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'cliente', 'error')} " style="display: none">
    <label for="cliente">
        <g:message code="pagoBonoProduccion.cliente.label" default="Cliente" />

    </label>
    <g:select id="cliente" name="cliente.id" from="${org.socymet.proveedor.Cliente.list()}" optionKey="id" value="${pagoBonoProduccionInstance?.cliente?.id}" class="many-to-one"/>
</div>

<div id="_empresa" class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'empresa', 'error')} " style="display: none">
    <label for="empresa">
        <g:message code="pagoBonoProduccion.empresa.label" default="Empresa" />

    </label>
    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${pagoBonoProduccionInstance?.empresa?.id}" class="many-to-one"/>
</div>

<div id="_nombreEmpresa" class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'nombreEmpresa', 'error')} required">
    <label>
        Nombre Empresa
    </label>
    <g:textField name="nombreEmpresa" value="${pagoBonoProduccionInstance?.nombreEmpresa}" size="50" class="amarillo" readonly="readonly"/>
</div>

<div id="_cuadrilla" class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'cuadrilla', 'error')} " style="display: none">
    <label for="cuadrilla">
        <g:message code="pagoBonoProduccion.cuadrilla.label" default="Cuadrilla" />

    </label>
    <g:select id="cuadrilla" name="cuadrilla.id" from="${org.socymet.proveedor.Cuadrilla.list()}" optionKey="id" value="${pagoBonoProduccionInstance?.cuadrilla?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'fechaInicial', 'error')} required">
    <label for="fechaInicial">
        <g:message code="pagoBonoProduccion.fechaInicial.label" default="Fecha Inicial" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaInicial" precision="month"  value="${pagoBonoProduccionInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'fechaFinal', 'error')} required">
    <label for="fechaFinal">
        <g:message code="pagoBonoProduccion.fechaFinal.label" default="Fecha Final" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaFinal" precision="month"  value="${pagoBonoProduccionInstance?.fechaFinal}"  />
</div>

%{--<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'acumulacionPorMes', 'error')} ">--}%
    %{--<label for="acumulacionPorMes">--}%
        %{--<g:message code="pagoBonoProduccion.acumulacionPorMes.label" default="Acumulacion Por Mes" />--}%

    %{--</label>--}%
    %{--<g:textArea name="acumulacionPorMes" cols="40" rows="5" maxlength="500000" value="${pagoBonoProduccionInstance?.acumulacionPorMes}"/>--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'lotesBono', 'error')} ">--}%
%{--<label for="lotesBono">--}%
%{--<g:message code="pagoBonoProduccion.lotesBono.label" default="Lotes Bono" />--}%

%{--</label>--}%
%{--<g:textArea name="lotesBono" cols="40" rows="5" maxlength="500000"/>--}%
%{--</div>--}%

<g:hiddenField name="lotesBono" value="${pagoBonoProduccionInstance?.lotesBono}" />

<div id="_botones" style="text-align: center">
    <br>
    <button id="agregar" type="button">GENERAR HISTOGRAMA</button>
    <button id="quitar" type="button">QUITAR MES SELECCIONADO</button>
</div>

<div style="width: 350px; margin-left: auto; margin-right: auto;">
    <table id="acumulacionPorMesTabla"></table>
</div>
<g:hiddenField name="acumulacionPorMes" value="${pagoBonoProduccionInstance?.acumulacionPorMes}" />


<div>
    <h1 style="font-weight: bold">Totales</h1>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'numeroMesesPagables', 'error')} required">
    <label for="numeroMesesPagables">
        <g:message code="pagoBonoProduccion.numeroMesesPagables.label" default="Numero Meses Pagables" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="numeroMesesPagables" type="number" value="${pagoBonoProduccionInstance.numeroMesesPagables}" required="" class="rojo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'numeroMesesAcumulados', 'error')} required">
    <label for="numeroMesesAcumulados">
        <g:message code="pagoBonoProduccion.numeroMesesAcumulados.label" default="Numero Meses Acumulados" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="numeroMesesAcumulados" type="number" value="${pagoBonoProduccionInstance.numeroMesesAcumulados}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'totalKilosNetosSecos', 'error')} required">
    <label for="totalKilosNetosSecos">
        <g:message code="pagoBonoProduccion.totalKilosNetosSecos.label" default="Total Kilos Netos Secos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalKilosNetosSecos" value="${fieldValue(bean: pagoBonoProduccionInstance, field: 'totalKilosNetosSecos')}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'tipoDeCambio', 'error')} required" style="display: none">
    <label for="tipoDeCambio">
        <g:message code="pagoBonoProduccion.tipoDeCambio.label" default="Tipo De Cambio" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="tipoDeCambio" value="${fieldValue(bean: pagoBonoProduccionInstance, field: 'tipoDeCambio')}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'bonoPorTonelada', 'error')} required">
    <label for="bonoPorTonelada">
        <g:message code="pagoBonoProduccion.bonoPorTonelada.label" default="Bono Por Tonelada" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoPorTonelada" value="${fieldValue(bean: pagoBonoProduccionInstance, field: 'bonoPorTonelada')}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'totalPagable', 'error')} required">
    <label for="totalPagable">
        <g:message code="pagoBonoProduccion.totalPagable.label" default="Total Pagable" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalPagable" value="${fieldValue(bean: pagoBonoProduccionInstance, field: 'totalPagable')}" required="" class="verde" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'totalPagableLiteral', 'error')} required">
    <label for="totalPagableLiteral">
        <g:message code="pagoBonoProduccion.totalPagableLiteral.label" default="Total Pagable Literal" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="totalPagableLiteral" required="" value="${pagoBonoProduccionInstance?.totalPagableLiteral}" size="90" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoProduccionInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="pagoBonoProduccion.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${pagoBonoProduccionInstance?.observaciones}" size="90"/>
</div>

