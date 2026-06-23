<%@ page import="org.socymet.cancelacion.PagoBonoCalidad" %>

<g:hiddenField name="vista" value="" />

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'numeroComprobante', 'error')} "  style="display: none">
	<label for="numeroComprobante">
		<g:message code="pagoBonoCalidad.numeroComprobante.label" default="Numero Comprobante" />
		
	</label>
	<g:field name="numeroComprobante" type="number" value="${pagoBonoCalidadInstance.numeroComprobante}" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'ci', 'error')} required">
	<label for="ci">
		<g:message code="pagoBonoCalidad.ci.label" default="Ci" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="ci" required="" value="${pagoBonoCalidadInstance?.ci}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'nombreCobrador', 'error')} required">
	<label for="nombreCobrador">
		<g:message code="pagoBonoCalidad.nombreCobrador.label" default="Nombre Cobrador" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCobrador" required="" value="${pagoBonoCalidadInstance?.nombreCobrador}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'fechaDePago', 'error')} required">
	<label for="fechaDePago">
		<g:message code="pagoBonoCalidad.fechaDePago.label" default="Fecha De Pago" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDePago" precision="day"  value="${pagoBonoCalidadInstance?.fechaDePago}"  />
</div>

<div>
    <h1 style="font-weight: bold">Parametros de Filtrado</h1>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'tipoSeleccion', 'error')} ">
	<label for="tipoSeleccion">
		<g:message code="pagoBonoCalidad.tipoSeleccion.label" default="Tipo Seleccion" />
		
	</label>
	<g:select name="tipoSeleccion" from="${['INDIVIDUAL','CUADRILLA']}" value="${pagoBonoCalidadInstance?.tipoSeleccion}" valueMessagePrefix="pagoBonoCalidad.tipoSeleccion" />
</div>

<div id="_cliente" class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'cliente', 'error')} " style="display: none">
	<label for="cliente">
		<g:message code="pagoBonoCalidad.cliente.label" default="Cliente" />
		
	</label>
	<g:select id="cliente" name="cliente.id" from="${org.socymet.proveedor.Cliente.list()}" optionKey="id" value="${pagoBonoCalidadInstance?.cliente?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div id="_nombreCliente" class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'nombreCliente', 'error')} required">
	<label for="nombreCliente">
		<g:message code="pagoBonoCalidad.nombreCliente.label" default="Nombre Cliente" />
	</label>
	<g:textField name="nombreCliente" required="" value="${pagoBonoCalidadInstance?.nombreCliente}" size="50" />
</div>

<div id="_empresa" class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'empresa', 'error')} " style="display: none">
	<label for="empresa">
		<g:message code="pagoBonoCalidad.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${pagoBonoCalidadInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div id="_nombreEmpresa" class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="pagoBonoCalidad.nombreEmpresa.label" default="Nombre Empresa" />
	</label>
	<g:textField name="nombreEmpresa" required="" value="${pagoBonoCalidadInstance?.nombreEmpresa}" size="50" class="amarillo" readonly="readonly"/>
</div>

<div id="_cuadrilla" class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'cuadrilla', 'error')} " style="display: none">
	<label for="cuadrilla">
		<g:message code="pagoBonoCalidad.cuadrilla.label" default="Cuadrilla" />
		
	</label>
	<g:select id="cuadrilla" name="cuadrilla.id" from="${org.socymet.proveedor.Cuadrilla.list()}" optionKey="id" value="${pagoBonoCalidadInstance?.cuadrilla?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'fechaInicial', 'error')} required">
	<label for="fechaInicial">
		<g:message code="pagoBonoCalidad.fechaInicial.label" default="Fecha Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaInicial" precision="month"  value="${pagoBonoCalidadInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'fechaFinal', 'error')} required">
	<label for="fechaFinal">
		<g:message code="pagoBonoCalidad.fechaFinal.label" default="Fecha Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaFinal" precision="month"  value="${pagoBonoCalidadInstance?.fechaFinal}"  />
</div>

%{--<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'acumulacionPorMes', 'error')} ">--}%
	%{--<label for="acumulacionPorMes">--}%
		%{--<g:message code="pagoBonoCalidad.acumulacionPorMes.label" default="Acumulacion Por Mes" />--}%
		%{----}%
	%{--</label>--}%
	%{--<g:textArea name="acumulacionPorMes" cols="40" rows="5" maxlength="500000" value="${pagoBonoCalidadInstance?.acumulacionPorMes}"/>--}%
%{--</div>--}%

%{--<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'lotesBono', 'error')} ">--}%
%{--<label for="lotesBono">--}%
%{--<g:message code="pagoBonoCalidad.lotesBono.label" default="Lotes Bono" />--}%

%{--</label>--}%
%{--<g:textArea name="lotesBono" cols="40" rows="5" maxlength="500000"/>--}%
%{--</div>--}%

<g:hiddenField name="lotesBono" value="${pagoBonoCalidadInstance?.lotesBono}" />

<div id="_botones" style="text-align: center">
    <br>
    <button id="agregar" type="button">GENERAR HISTOGRAMA</button>
    <button id="quitar" type="button">QUITAR MES SELECCIONADO</button>
</div>

<div style="width: 350px; margin-left: auto; margin-right: auto;">
    <table id="acumulacionPorMesTabla"></table>
</div>
<g:hiddenField name="acumulacionPorMes" value="${pagoBonoCalidadInstance?.acumulacionPorMes}" />

<div>
    <h1 style="font-weight: bold">Totales</h1>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'numeroMesesPagables', 'error')} required">
	<label for="numeroMesesPagables">
		<g:message code="pagoBonoCalidad.numeroMesesPagables.label" default="Numero Meses Pagables" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numeroMesesPagables" type="number" value="${pagoBonoCalidadInstance.numeroMesesPagables}" required="" class="rojo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'numeroMesesAcumulados', 'error')} required">
	<label for="numeroMesesAcumulados">
		<g:message code="pagoBonoCalidad.numeroMesesAcumulados.label" default="Numero Meses Acumulados" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numeroMesesAcumulados" type="number" value="${pagoBonoCalidadInstance.numeroMesesAcumulados}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'leyMinimaPlata', 'error')} required">
	<label for="leyMinimaPlata">
		<g:message code="pagoBonoCalidad.leyMinimaPlata.label" default="Ley Minima Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyMinimaPlata" value="${fieldValue(bean: pagoBonoCalidadInstance, field: 'leyMinimaPlata')}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'totalKilosNetosSecos', 'error')} required">
	<label for="totalKilosNetosSecos">
		<g:message code="pagoBonoCalidad.totalKilosNetosSecos.label" default="Total Kilos Netos Secos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalKilosNetosSecos" value="${fieldValue(bean: pagoBonoCalidadInstance, field: 'totalKilosNetosSecos')}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'tipoDeCambio', 'error')} required" style="display: none">
	<label for="tipoDeCambio">
		<g:message code="pagoBonoCalidad.tipoDeCambio.label" default="Tipo De Cambio" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="tipoDeCambio" value="${fieldValue(bean: pagoBonoCalidadInstance, field: 'tipoDeCambio')}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'bonoPorTonelada', 'error')} required">
	<label for="bonoPorTonelada">
		<g:message code="pagoBonoCalidad.bonoPorTonelada.label" default="Bono Por Tonelada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="bonoPorTonelada" value="${fieldValue(bean: pagoBonoCalidadInstance, field: 'bonoPorTonelada')}" required="" class="amarillo" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'totalPagable', 'error')} required">
	<label for="totalPagable">
		<g:message code="pagoBonoCalidad.totalPagable.label" default="Total Pagable" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalPagable" value="${fieldValue(bean: pagoBonoCalidadInstance, field: 'totalPagable')}" required="" class="verde" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'totalPagableLiteral', 'error')} required">
	<label for="totalPagableLiteral">
		<g:message code="pagoBonoCalidad.totalPagableLiteral.label" default="Total Pagable Literal" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="totalPagableLiteral" required="" value="${pagoBonoCalidadInstance?.totalPagableLiteral}" size="90" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pagoBonoCalidadInstance, field: 'observaciones', 'error')} ">
	<label for="observaciones">
		<g:message code="pagoBonoCalidad.observaciones.label" default="Observaciones" />
		
	</label>
	<g:textField name="observaciones" value="${pagoBonoCalidadInstance?.observaciones}" size="90"/>
</div>

