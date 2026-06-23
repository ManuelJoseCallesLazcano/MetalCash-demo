


<%@ page import="org.socymet.liquidacion.LiquidacionDeAntimonio" %>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="liquidacionDeAntimonio.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${liquidacionDeAntimonioInstance?.lote}"/>
    <g:hiddenField name="recepcionDeAntimonio.id" value="${liquidacionDeAntimonioInstance?.recepcionDeAntimonio?.id}"/>
    <g:hiddenField name="conjuntoAntimonio" value="${liquidacionDeAntimonioInstance?.conjuntoAntimonio}"/>
</div>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="liquidacionDeAntimonio.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${liquidacionDeAntimonioInstance?.nombreCliente}"  class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="liquidacionDeAntimonio.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="empresaId" value="${liquidacionDeAntimonioInstance?.recepcionDeAntimonio?.empresa?.id}"/>
    <g:textField name="nombreEmpresa" required="" value="${liquidacionDeAntimonioInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="liquidacionDeAntimonio.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${liquidacionDeAntimonioInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'cantidadDeSacos', 'error')} required">
    <label for="cantidadDeSacos">
        <g:message code="liquidacionDeAntimonio.cantidadDeSacos.label" default="Cantidad De Sacos" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${liquidacionDeAntimonioInstance?.cantidadDeSacos}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'tara', 'error')} ">
    <label for="tara">
        <g:message code="liquidacionDeAntimonio.tara.label" default="Tara" />

    </label>
    <g:field name="tara" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'tara')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="liquidacionDeAntimonio.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'estadoDelLote', 'error')} ">
    <label for="estadoDelLote">
        <g:message code="liquidacionDeAntimonio.estadoDelLote.label" default="Estado Del Lote" />

    </label>
    <g:textField name="estadoDelLote" value="${liquidacionDeAntimonioInstance?.estadoDelLote}" class="amarillo" readonly="true"/>
</div>

<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'cotizacionDiariaDeAntimonio', 'error')} required">
            <label for="cotizacionDiariaDeAntimonio" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.cotizacionDiariaDeAntimonio.label" default="Cot. Dia Antimonio" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDeAntimonio" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'cotizacionDiariaDeAntimonio')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'cotizacionQuincenalDeAntimonio', 'error')} required">
            <label for="cotizacionQuincenalDeAntimonio" style="width: 60%">
                <g:message code="liquidacionDeAntimonio.cotizacionQuincenalDeAntimonio.label" default="Cot. Quinc. Antimonio" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDeAntimonio" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'cotizacionQuincenalDeAntimonio')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'alicuotaDeAntimonio', 'error')} required">
            <label for="alicuotaDeAntimonio" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.alicuotaDeAntimonio.label" default="Alicuota Antimonio" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDeAntimonio" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'alicuotaDeAntimonio')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioOficial" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'tipoDeCambioOficial')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 60%">
                <g:message code="liquidacionDeAntimonio.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioComercial" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'tipoDeCambioComercial')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td>
            &nbsp;</td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'fechaDeLiquidacion', 'error')} required">
    <label for="fechaDeLiquidacion">
        <g:message code="liquidacionDeAntimonio.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeLiquidacion" precision="day"  value="${liquidacionDeAntimonioInstance?.fechaDeLiquidacion}"  />
</div>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'kilosNetosHumedos', 'error')} required">
            <label for="kilosNetosHumedos" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.kilosNetosHumedos.label" default="K. N. H." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosHumedos" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'kilosNetosHumedos')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'humedad', 'error')} required">
            <label for="humedad" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.humedad.label" default="% H2O" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="humedad" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'humedad')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosSecos" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'kilosNetosSecos')}" required="" size="10" readonly="true" class="amarillo"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'porcentajeAntimonio', 'error')} required">
            <label for="porcentajeAntimonio" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.porcentajeAntimonio.label" default="% de Sb" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="porcentajeAntimonio" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'porcentajeAntimonio')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'kilosFinosAntimonio', 'error')} required">
            <label for="kilosFinosAntimonio" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.kilosFinosAntimonio.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosAntimonio" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'kilosFinosAntimonio')}" required="" size="10" readonly="true" class="amarillo"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'librasFinasDeAntimonio', 'error')} required">
            <label for="librasFinasDeAntimonio" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.librasFinasDeAntimonio.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="librasFinasDeAntimonio" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'librasFinasDeAntimonio')}" required="" size="10" readonly="true" class="amarillo"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'porcentajePlomo', 'error')} required">
            <label for="porcentajePlomo" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.porcentajePlomo.label" default="% de Pb" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="porcentajePlomo" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'porcentajePlomo')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'porcentajeArsenico', 'error')} required">
            <label for="porcentajeArsenico" style="width: 50%">
                <g:message code="liquidacionDeAntimonio.porcentajeArsenico.label" default="% de As" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="porcentajeArsenico" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'porcentajeArsenico')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'valorOficialBruto', 'error')} required">
    <label for="valorOficialBruto">
        <g:message code="liquidacionDeAntimonio.valorOficialBruto.label" default="Valor Oficial Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorOficialBruto" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'valorOficialBruto')}" required="" size="10" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold; font-size: 12px">Parametros de Proteccion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'valorPorToneladaManual', 'error')} ">
    <label for="valorPorToneladaManual">
        <g:message code="liquidacionDeAntimonio.valorPorToneladaManual.label" default="Valor Por Tonelada Manual" />

    </label>
    <g:field name="valorPorToneladaManual" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'valorPorToneladaManual')}"  inputmode="decimal"/>
    <input type="radio" id="radio1" name="RadioGroup1" value="1" checked="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'puntoDeBajada', 'error')} ">
    <label for="puntoDeBajada">
        <g:message code="liquidacionDeAntimonio.puntoDeBajada.label" default="Punto De Bajada" />

    </label>
    <g:field name="puntoDeBajada" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'puntoDeBajada')}" inputmode="decimal"/>
    <input type="radio" id="radio2" name="RadioGroup1" value="2" />
</div>

<h1 style="font-weight: bold; font-size: 12px">Valoracion Final del Lote</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'tablaCotizacionAntimonio', 'error')} ">
    <label for="tablaCotizacionAntimonio">
        <g:message code="liquidacionDeAntimonio.tablaCotizacionAntimonio.label" default="Tabla Cotizacion Antimonio" />

    </label>
    <g:select id="tablaCotizacionAntimonio" name="tablaCotizacionAntimonio.id" from="${org.socymet.cotizaciones.TablaCotizacionAntimonio.list()}" optionKey="id" value="${liquidacionDeAntimonioInstance?.tablaCotizacionAntimonio?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'valorPorTonelada', 'error')} required">
    <label for="valorPorTonelada">
        <g:message code="liquidacionDeAntimonio.valorPorTonelada.label" default="Valor Por Tonelada" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorPorTonelada" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'valorPorTonelada')}" required="" class="verde" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'valorNetoMineral', 'error')} required">
    <label for="valorNetoMineral">
        <g:message code="liquidacionDeAntimonio.valorNetoMineral.label" default="Valor Neto Mineral" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineral" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'valorNetoMineral')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'valorNetoMineralEnBolivianos', 'error')} required">
    <label for="valorNetoMineralEnBolivianos">
        <g:message code="liquidacionDeAntimonio.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineralEnBolivianos" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'valorNetoMineralEnBolivianos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'bonoCalidad', 'error')} required">
    <label for="bonoCalidad">
        <g:message code="liquidacionDeAntimonio.bonoCalidad.label" default="Bono Calidad" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoCalidad" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'bonoCalidad')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'bonoIncentivo', 'error')} required">
    <label for="bonoIncentivo">
        <g:message code="liquidacionDeAntimonio.bonoIncentivo.label" default="Bono Incentivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoIncentivo" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'bonoIncentivo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'valorDeCompra', 'error')} required">
    <label for="valorDeCompra">
        <g:message code="liquidacionDeAntimonio.valorDeCompra.label" default="Valor De Compra" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorDeCompra" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'valorDeCompra')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'porcentajeRegalia', 'error')} ">
    <label for="porcentajeRegalia">
        <g:message code="liquidacionDeAntimonio.porcentajeRegalia.label" default="Porcentaje Regalia" />

    </label>
    <g:textField name="porcentajeRegalia" inputmode="decimal" value="${liquidacionDeAntimonioInstance?.porcentajeRegalia}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'regaliaMinera', 'error')} required">
    <label for="regaliaMinera">
        <g:message code="liquidacionDeAntimonio.regaliaMinera.label" default="Regalia Minera" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="regaliaMinera" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'regaliaMinera')}" required="" inputmode="decimal"/>
    <g:hiddenField name="retenciones" value="${liquidacionDeAntimonioInstance?.retenciones}"/>
</div>

<h1 style="font-weight: bold; font-size: 12px">Retenciones</h1>
<div style="width: 700px; margin-left: auto; margin-right: auto;"><table id="tablaRetenciones"></table></div>
<br />
<div style="text-align: center">
    <a href="javascript:void(0)" id="g4" onclick="eliminarRetencionAntimonio();">Eliminar Fila Seleccionada</a>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'totalRetenciones', 'error')} required">
    <label for="totalRetenciones">
        <g:message code="liquidacionDeAntimonio.totalRetenciones.label" default="Total Retenciones" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalRetenciones" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'totalRetenciones')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'totalPagado', 'error')} required">
    <label for="totalPagado">
        <g:message code="liquidacionDeAntimonio.totalPagado.label" default="Total Pagado" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalPagado" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'totalPagado')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'totalAnticiposContraEntrega', 'error')} required">
    <label for="totalAnticiposContraEntrega">
        <g:message code="liquidacionDeAntimonio.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalAnticiposContraEntrega" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'totalAnticiposContraEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'totalAnticiposContraFuturaEntrega', 'error')} required">
    <label for="totalAnticiposContraFuturaEntrega">
        <g:message code="liquidacionDeAntimonio.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalAnticiposContraFuturaEntrega" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'totalAnticiposContraFuturaEntrega')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'totalLiquidoPagable', 'error')} required">
    <label for="totalLiquidoPagable">
        <g:message code="liquidacionDeAntimonio.totalLiquidoPagable.label" default="Total Liquido Pagable" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalLiquidoPagable" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'totalLiquidoPagable')}" required="" inputmode="decimal"/>
</div>


<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="liquidacionDeAntimonio.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${liquidacionDeAntimonioInstance?.observaciones}" size="90"/>
</div>

<h1 style="font-weight: bold">Detalle de Analisis Realizados</h1>

<table class="center" border="0" style="width: 70%;">
    <thead>
    <tr>
        <th style="text-align: center; width: 70%">DESCRIPCION DE ANALISIS</th>
        <th style="text-align: center; width: 30%">COSTO</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio1', 'error')}">
            <g:field name="detalleLaboratorio1" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio1')}" size="70" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio1', 'error')}">
            <g:field name="costoLaboratorio1" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio1')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio2', 'error')}">
            <g:field name="detalleLaboratorio2" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio2')}" size="70" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio2', 'error')}">
            <g:field name="costoLaboratorio2" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio2')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio3', 'error')}">
            <g:field name="detalleLaboratorio3" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio3')}" size="70"  class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio3', 'error')}">
            <g:field name="costoLaboratorio3" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio3')}"  class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio4', 'error')}">
            <g:field name="detalleLaboratorio4" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio4')}" size="70" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="costoLaboratorio4" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio4')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="liquidacionDeAntimonio.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDeAntimonioInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<div id="_modificacion" style="display: none">
<h1 style="font-weight: bold; font-size: 12px">Informacion de Modificacion del Registro</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'motivoDeModificacion', 'error')} ">
    <label for="motivoDeModificacion">
        <g:message code="liquidacionDeAntimonio.motivoDeModificacion.label" default="Motivo De Modificacion" />

    </label>
    <g:textField name="motivoDeModificacion" value="${liquidacionDeAntimonioInstance?.motivoDeModificacion}" size="90"/>
</div>
</div>