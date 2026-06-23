<%@ page import="org.socymet.liquidacion.LiquidacionDeEstano" %>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="liquidacionDeEstano.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${liquidacionDeEstanoInstance?.lote}"/>
    <g:hiddenField name="recepcionDeEstano.id" value="${liquidacionDeEstanoInstance?.recepcionDeEstano?.id}"/>
    <g:hiddenField name="empresa.id" value="${liquidacionDeEstanoInstance?.empresa?.id}" readonly="readonly"/>
    <g:hiddenField name="deposito.id" value="${liquidacionDeEstanoInstance?.deposito?.id}" readonly="readonly"/>
    <g:hiddenField name="conjuntoEstano" value="${liquidacionDeEstanoInstance?.conjuntoEstano}"/>
</div>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'nombreDeposito', 'error')} required">
    <label for="nombreDeposito">
        <g:message code="liquidacionDeEstano.nombreDeposito.label" default="Nombre Deposito" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreDeposito" required="" value="${liquidacionDeEstanoInstance?.nombreDeposito}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="liquidacionDeEstano.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${liquidacionDeEstanoInstance?.nombreCliente}"  class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="liquidacionDeEstano.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="empresaId" value="${liquidacionDeEstanoInstance?.recepcionDeEstano?.empresa?.id}"/>
    <g:textField name="nombreEmpresa" required="" value="${liquidacionDeEstanoInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="liquidacionDeEstano.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${liquidacionDeEstanoInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'cantidadDeSacos', 'error')} required">
    <label for="cantidadDeSacos">
        <g:message code="liquidacionDeEstano.cantidadDeSacos.label" default="Cantidad De Sacos" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${liquidacionDeEstanoInstance?.cantidadDeSacos}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'tara', 'error')} ">
    <label for="tara">
        <g:message code="liquidacionDeEstano.tara.label" default="Tara" />

    </label>
    <g:field name="tara" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'tara')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="liquidacionDeEstano.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'estadoDelLote', 'error')} ">
    <label for="estadoDelLote">
        <g:message code="liquidacionDeEstano.estadoDelLote.label" default="Estado Del Lote" />

    </label>
    <g:textField name="estadoDelLote" value="${liquidacionDeEstanoInstance?.estadoDelLote}" class="amarillo" readonly="true"/>
</div>

<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'cotizacionDiariaDeEstano', 'error')} required">
            <label for="cotizacionDiariaDeEstano" style="width: 120px">
                <g:message code="liquidacionDeEstano.cotizacionDiariaDeEstano.label" default="Cot. Dia Estano" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDeEstano" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'cotizacionDiariaDeEstano')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'cotizacionQuincenalDeEstano', 'error')} required">
            <label for="cotizacionQuincenalDeEstano" style="width: 120px">
                <g:message code="liquidacionDeEstano.cotizacionQuincenalDeEstano.label" default="Cot. Quinc. Estano" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDeEstano" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'cotizacionQuincenalDeEstano')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'alicuotaDeEstano', 'error')} required">
            <label for="alicuotaDeEstano" style="width: 120px">
                <g:message code="liquidacionDeEstano.alicuotaDeEstano.label" default="Alicuota Estano" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDeEstano" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'alicuotaDeEstano')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 120px">
                <g:message code="liquidacionDeEstano.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioOficial" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'tipoDeCambioOficial')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 120px">
                <g:message code="liquidacionDeEstano.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioComercial" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'tipoDeCambioComercial')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td>
            &nbsp;</td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Detalle de Leyes</h1>

<table class="center" style="width: 70%;">
    <thead>
    <tr>
        <th style="width: 40%">ELEMENTO</th>
        <th style="width: 20%">LEY COMERMIN</th>
        <th style="width: 20%">LEY CLIENTE</th>
        <th style="width: 20%">LEY FINAL</th>
    </tr>
    </thead>
    <tbody>
    <tr style="display: none">
        <td class="fieldcontain required">
            <label for="porcentajeMermaPromexbol">
                <g:message code="liquidacionDeEstano.porcentajeMermaPromexbol.label" default="Merma" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
            <g:field name="porcentajeMermaPromexbol" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaCliente', 'error')} required">
            <g:field name="porcentajeMermaCliente" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaFinal', 'error')} required">
            <g:field name="porcentajeMermaFinal" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeMermaFinal')}" required="" readonly="true"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeEstanoCliente">
                <g:message code="liquidacionDeEstano.porcentajeEstanoCliente.label" default="Estano" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoPromexbol', 'error')} required">
            <g:field name="porcentajeEstanoPromexbol" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoCliente', 'error')} required">
            <g:field name="porcentajeEstanoCliente" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoFinal', 'error')} required">
            <g:field name="porcentajeEstanoFinal" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeEstanoFinal')}" required=""  inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeHumedadPromexbol">
                <g:message code="liquidacionDeEstano.porcentajeHumedadPromexbol.label" default="Humedad" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
            <g:field name="porcentajeHumedadPromexbol" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadCliente', 'error')} required">
            <g:field name="porcentajeHumedadCliente" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadFinal', 'error')} required">
            <g:field name="porcentajeHumedadFinal" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'porcentajeHumedadFinal')}" required=""  inputmode="decimal"/>
        </td>
    </tr>

    </tbody>
</table>

<table style="width: 50%; margin-left: auto; margin-right: auto">
    <tbody>
    <tr>
        <td id="controlCalidadLink">

        </td>
        <td style="text-align: center;">
            <input id="copiarLeyes" type="button" value="COPIAR LEYES >>>" style="background-color: #033257; color: white; font-size: 12px;"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'ajustePrecioEstano', 'error')} required">
    <label for="ajustePrecioEstano">
        <g:message code="liquidacionDeEstano.ajustePrecioEstano.label" default="Ajuste Precio Estano" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="ajustePrecioEstano" name="ajustePrecioEstano.id" from="${org.socymet.cotizaciones.AjustePrecioEstano.list([sort: 'fecha',order: 'desc'])}" optionKey="id" required="" value="${liquidacionDeEstanoInstance?.ajustePrecioEstano?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'margen', 'error')} " style="display:none;">
    <label for="margen">
        <g:message code="liquidacionDeEstano.margen.label" default="Margen" />

    </label>
    <g:textField name="margen" inputmode="decimal" value="${liquidacionDeEstanoInstance?.margen}" readonly="readonly"/>
</div>

<br>

<div style="text-align: center;">
    <input id="valorar" type="button" value="VALORAR LOTE" style="background-color: #255b17; color: white; font-size: 16px;"/>
</div>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'fechaDeLiquidacion', 'error')} required">
    <label for="fechaDeLiquidacion">
        <g:message code="liquidacionDeEstano.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeLiquidacion" precision="day"  value="${liquidacionDeEstanoInstance?.fechaDeLiquidacion}" />
</div>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'kilosNetosHumedos', 'error')} required">
            <label for="kilosNetosHumedos" style="width: 50%">
                <g:message code="liquidacionDeEstano.kilosNetosHumedos.label" default="K. N. H." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosHumedos" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'kilosNetosHumedos')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 50%">
                <g:message code="liquidacionDeEstano.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosSecos" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'kilosNetosSecos')}" required="" size="10" readonly="true" class="amarillo"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'kilosFinosEstano', 'error')} required">
            <label for="kilosFinosEstano" style="width: 50%">
                <g:message code="liquidacionDeEstano.kilosFinosEstano.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosEstano" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'kilosFinosEstano')}" required="" size="10" readonly="true" class="amarillo"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'librasFinasDeEstano', 'error')} required">
            <label for="librasFinasDeEstano" style="width: 50%">
                <g:message code="liquidacionDeEstano.librasFinasDeEstano.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="librasFinasDeEstano" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'librasFinasDeEstano')}" required="" size="10" readonly="true" class="amarillo"/>
        </td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="valorPorToneladaManual" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'valorPorToneladaManual')}"/>
<g:hiddenField name="puntoDeBajada" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'puntoDeBajada')}"/>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'valorOficialBruto', 'error')} required">
    <label for="valorOficialBruto">
        <g:message code="liquidacionDeEstano.valorOficialBruto.label" default="Valor Oficial Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorOficialBruto" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'valorOficialBruto')}" required="" size="10" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'valorPorTonelada', 'error')} required">
    <label for="valorPorTonelada">
        <g:message code="liquidacionDeEstano.valorPorTonelada.label" default="Valor Por Tonelada" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorPorTonelada" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'valorPorTonelada')}" required="" class="verde" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'regaliaMinera', 'error')} required">
    <label for="regaliaMinera">
        <g:message code="liquidacionDeEstano.regaliaMinera.label" default="Regalia Minera" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="regaliaMinera" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'regaliaMinera')}" required="" inputmode="decimal"/>

</div>

<g:hiddenField name="retenciones" value="${liquidacionDeZincPlataInstance?.retenciones}" />

<h1 style="font-weight: bold">Retenciones</h1>

<div style="width: 980px; margin-left: auto; margin-right: auto;">
    <table id="tablaRetenciones">
    </table>
</div>

<div id="_botones" style="text-align: center">
    <br>
    <button id="eliminarRetencion" type="button">ELIMINAR RETENCION SELECCIONADA</button>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'valorNetoMineral', 'error')} required">
    <label for="valorNetoMineral">
        <g:message code="liquidacionDeEstano.valorNetoMineral.label" default="Valor Neto Mineral" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineral" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'valorNetoMineral')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'valorNetoMineralEnBolivianos', 'error')} required">
    <label for="valorNetoMineralEnBolivianos">
        <g:message code="liquidacionDeEstano.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineralEnBolivianos" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'valorNetoMineralEnBolivianos')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'bonoCalidad', 'error')} required" style="display: none">
    <label for="bonoCalidad">
        <g:message code="liquidacionDeEstano.bonoCalidad.label" default="Bono Calidad" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoCalidad" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'bonoCalidad')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'bonoIncentivo', 'error')} required" style="display: none">
    <label for="bonoIncentivo">
        <g:message code="liquidacionDeEstano.bonoIncentivo.label" default="Bono Incentivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoIncentivo" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'bonoIncentivo')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'valorDeCompra', 'error')} required">
    <label for="valorDeCompra">
        <g:message code="liquidacionDeEstano.valorDeCompra.label" default="Valor De Compra" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorDeCompra" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'valorDeCompra')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'porcentajeRegalia', 'error')} " style="display: none">
    <label for="porcentajeRegalia">
        <g:message code="liquidacionDeEstano.porcentajeRegalia.label" default="Porcentaje Regalia" />

    </label>
    <g:textField name="porcentajeRegalia" inputmode="decimal" value="${liquidacionDeEstanoInstance?.porcentajeRegalia}" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'totalRetenciones', 'error')} required">
    <label for="totalRetenciones">
        <g:message code="liquidacionDeEstano.totalRetenciones.label" default="Total Retenciones" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalRetenciones" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'totalRetenciones')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'totalPagado', 'error')} required">
    <label for="totalPagado">
        <g:message code="liquidacionDeEstano.totalPagado.label" default="Total Pagado" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalPagado" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'totalPagado')}" required="" readonly="readonly"/>
</div>

<g:hiddenField name="cantidadAnticiposPorPagar" />

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'anticipoPorPagar', 'error')} required">
    <label for="anticipoPorPagar">
        <g:message code="liquidacionDeEstano.anticipoPorPagar.label" default="Anticipo Por Pagar" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="anticipoPorPagar" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'anticipoPorPagar')}" required="" class="rojo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'totalAnticiposContraEntrega', 'error')} required">
    <label for="totalAnticiposContraEntrega">
        <g:message code="liquidacionDeEstano.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalAnticiposContraEntrega" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'totalAnticiposContraEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'totalAnticiposContraFuturaEntrega', 'error')} required">
    <label for="totalAnticiposContraFuturaEntrega">
        <g:message code="liquidacionDeEstano.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="adelantoPorLiquidacionProvisional" value="0"/>
    <g:field name="totalAnticiposContraFuturaEntrega" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'totalAnticiposContraFuturaEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'totalLiquidoPagable', 'error')} required">
    <label for="totalLiquidoPagable">
        <g:message code="liquidacionDeEstano.totalLiquidoPagable.label" default="Total Liquido Pagable" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalLiquidoPagable" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'totalLiquidoPagable')}" required="" class="verde" inputmode="decimal"/>
    <g:hiddenField name="totalLiquidoPagableOriginal" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'totalLiquidoPagableOriginal')}"/>
    <g:hiddenField name="diferenciaLiquidoPagable" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'diferenciaLiquidoPagable')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="liquidacionDeEstano.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${liquidacionDeEstanoInstance?.observaciones}" size="90"/>
</div>

<table class="center" border="0" style="width: 70%; display: none">
    <thead>
    <tr>
        <th style="text-align: center; width: 70%">DESCRIPCION DE ANALISIS</th>
        <th style="text-align: center; width: 30%">COSTO</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'detalleLaboratorio1', 'error')}">
            <g:field name="detalleLaboratorio1" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'detalleLaboratorio1')}" size="70" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'costoLaboratorio1', 'error')}">
            <g:field name="costoLaboratorio1" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'costoLaboratorio1')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'detalleLaboratorio2', 'error')}">
            <g:field name="detalleLaboratorio2" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'detalleLaboratorio2')}" size="70" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'costoLaboratorio2', 'error')}">
            <g:field name="costoLaboratorio2" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'costoLaboratorio2')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'detalleLaboratorio3', 'error')}">
            <g:field name="detalleLaboratorio3" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'detalleLaboratorio3')}" size="70"  class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'costoLaboratorio3', 'error')}">
            <g:field name="costoLaboratorio3" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'costoLaboratorio3')}"  class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'detalleLaboratorio4', 'error')}">
            <g:field name="detalleLaboratorio4" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'detalleLaboratorio4')}" size="70" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="costoLaboratorio4" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'costoLaboratorio4')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="liquidacionDeEstano.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDeEstanoInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<div id="_modificacion" style="display: none">
    <h1 style="font-weight: bold; font-size: 12px">Informacion de Modificacion del Registro</h1>

    <div class="fieldcontain ${hasErrors(bean: liquidacionDeEstanoInstance, field: 'motivoDeModificacion', 'error')} ">
        <label for="motivoDeModificacion">
            <g:message code="liquidacionDeEstano.motivoDeModificacion.label" default="Motivo De Modificacion" />

        </label>
        <g:textField name="motivoDeModificacion" value="${liquidacionDeEstanoInstance?.motivoDeModificacion}" size="90"/>
    </div>
</div>