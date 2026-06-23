<%@ page import="org.socymet.liquidacion.LiquidacionDeWolfran" %>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="liquidacionDeWolfran.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${liquidacionDeWolfranInstance?.lote}"/>
    <g:hiddenField name="recepcionDeWolfran.id" value="${liquidacionDeWolfranInstance?.recepcionDeWolfran?.id}"/>
    <g:hiddenField name="conjuntoWolfran" value="${liquidacionDeWolfranInstance?.conjuntoWolfran}"/>
</div>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="liquidacionDeWolfran.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${liquidacionDeWolfranInstance?.nombreCliente}"  class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="liquidacionDeWolfran.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="empresaId" value="${liquidacionDeWolfranInstance?.recepcionDeWolfran?.empresa?.id}"/>
    <g:textField name="nombreEmpresa" required="" value="${liquidacionDeWolfranInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="liquidacionDeWolfran.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${liquidacionDeWolfranInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'cantidadDeSacos', 'error')} required">
    <label for="cantidadDeSacos">
        <g:message code="liquidacionDeWolfran.cantidadDeSacos.label" default="Cantidad De Sacos" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${liquidacionDeWolfranInstance?.cantidadDeSacos}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'tara', 'error')} ">
    <label for="tara">
        <g:message code="liquidacionDeWolfran.tara.label" default="Tara" />

    </label>
    <g:field name="tara" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'tara')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="liquidacionDeWolfran.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'estadoDelLote', 'error')} ">
    <label for="estadoDelLote">
        <g:message code="liquidacionDeWolfran.estadoDelLote.label" default="Estado Del Lote" />

    </label>
    <g:textField name="estadoDelLote" value="${liquidacionDeWolfranInstance?.estadoDelLote}" class="amarillo" readonly="true"/>
</div>

<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'cotizacionDiariaDeWolfran', 'error')} required">
            <label for="cotizacionDiariaDeWolfran" style="width: 50%">
                <g:message code="liquidacionDeWolfran.cotizacionDiariaDeWolfran.label" default="Cot. Dia Wolfran" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDeWolfran" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'cotizacionDiariaDeWolfran')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'cotizacionQuincenalDeWolfran', 'error')} required">
            <label for="cotizacionQuincenalDeWolfran" style="width: 60%">
                <g:message code="liquidacionDeWolfran.cotizacionQuincenalDeWolfran.label" default="Cot. Quinc. Wolfran" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDeWolfran" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'cotizacionQuincenalDeWolfran')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'alicuotaDeWolfran', 'error')} required">
            <label for="alicuotaDeWolfran" style="width: 50%">
                <g:message code="liquidacionDeWolfran.alicuotaDeWolfran.label" default="Alicuota Wolfran" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDeWolfran" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'alicuotaDeWolfran')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDeWolfran.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioOficial" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'tipoDeCambioOficial')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 60%">
                <g:message code="liquidacionDeWolfran.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioComercial" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'tipoDeCambioComercial')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td>
            &nbsp;</td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'fechaDeLiquidacion', 'error')} required">
    <label for="fechaDeLiquidacion">
        <g:message code="liquidacionDeWolfran.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeLiquidacion" precision="day"  value="${liquidacionDeWolfranInstance?.fechaDeLiquidacion}"  />
</div>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'kilosNetosHumedos', 'error')} required">
            <label for="kilosNetosHumedos" style="width: 50%">
                <g:message code="liquidacionDeWolfran.kilosNetosHumedos.label" default="K. N. H." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosHumedos" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'kilosNetosHumedos')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'humedad', 'error')} required">
            <label for="humedad" style="width: 50%">
                <g:message code="liquidacionDeWolfran.humedad.label" default="% H2O" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="humedad" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'humedad')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 50%">
                <g:message code="liquidacionDeWolfran.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosSecos" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'kilosNetosSecos')}" required="" size="10" readonly="true" class="amarillo"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'porcentajeWolfran', 'error')} required">
            <label for="porcentajeWolfran" style="width: 50%">
                <g:message code="liquidacionDeWolfran.porcentajeWolfran.label" default="% de WO3" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="porcentajeWolfran" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'porcentajeWolfran')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'kilosFinosWolfran', 'error')} required">
            <label for="kilosFinosWolfran" style="width: 50%">
                <g:message code="liquidacionDeWolfran.kilosFinosWolfran.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosWolfran" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'kilosFinosWolfran')}" required="" size="10" readonly="true" class="amarillo"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'librasFinasDeWolfran', 'error')} required">
            <label for="librasFinasDeWolfran" style="width: 50%">
                <g:message code="liquidacionDeWolfran.librasFinasDeWolfran.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="librasFinasDeWolfran" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'librasFinasDeWolfran')}" required="" size="10" readonly="true" class="amarillo"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'valorOficialBruto', 'error')} required">
    <label for="valorOficialBruto">
        <g:message code="liquidacionDeWolfran.valorOficialBruto.label" default="Valor Oficial Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorOficialBruto" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'valorOficialBruto')}" required="" size="10" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold; font-size: 12px">Parametros de Proteccion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'valorPorToneladaManual', 'error')} ">
    <label for="valorPorToneladaManual">
        <g:message code="liquidacionDeWolfran.valorPorToneladaManual.label" default="Valor Por Tonelada Manual" />

    </label>
    <g:field name="valorPorToneladaManual" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'valorPorToneladaManual')}" inputmode="decimal"/>
    <input type="radio" id="radio1" name="RadioGroup1" value="1" checked="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'puntoDeBajada', 'error')} ">
    <label for="puntoDeBajada">
        <g:message code="liquidacionDeWolfran.puntoDeBajada.label" default="Punto De Bajada" />

    </label>
    <g:field name="puntoDeBajada" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'puntoDeBajada')}" inputmode="decimal"/>
    <input type="radio" id="radio2" name="RadioGroup1" value="2" />
</div>

<h1 style="font-weight: bold; font-size: 12px">Valoracion Final del Lote</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'tablaCotizacionWolfran', 'error')} ">
    <label for="tablaCotizacionWolfran">
        <g:message code="liquidacionDeWolfran.tablaCotizacionWolfran.label" default="Tabla Cotizacion Wolfran" />

    </label>
    <g:select id="tablaCotizacionWolfran" name="tablaCotizacionWolfran.id" from="${org.socymet.cotizaciones.TablaCotizacionWolfran.list()}" optionKey="id" value="${liquidacionDeWolfranInstance?.tablaCotizacionWolfran?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'valorPorTonelada', 'error')} required">
    <label for="valorPorTonelada">
        <g:message code="liquidacionDeWolfran.valorPorTonelada.label" default="Valor Por Tonelada" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorPorTonelada" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'valorPorTonelada')}" required="" class="verde" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'valorNetoMineral', 'error')} required">
    <label for="valorNetoMineral">
        <g:message code="liquidacionDeWolfran.valorNetoMineral.label" default="Valor Neto Mineral" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineral" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'valorNetoMineral')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'valorNetoMineralEnBolivianos', 'error')} required">
    <label for="valorNetoMineralEnBolivianos">
        <g:message code="liquidacionDeWolfran.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineralEnBolivianos" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'valorNetoMineralEnBolivianos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'bonoCalidad', 'error')} required">
    <label for="bonoCalidad">
        <g:message code="liquidacionDeWolfran.bonoCalidad.label" default="Bono Calidad" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoCalidad" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'bonoCalidad')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'bonoIncentivo', 'error')} required">
    <label for="bonoIncentivo">
        <g:message code="liquidacionDeWolfran.bonoIncentivo.label" default="Bono Incentivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoIncentivo" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'bonoIncentivo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'valorDeCompra', 'error')} required">
    <label for="valorDeCompra">
        <g:message code="liquidacionDeWolfran.valorDeCompra.label" default="Valor De Compra" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorDeCompra" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'valorDeCompra')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'porcentajeRegalia', 'error')} ">
    <label for="porcentajeRegalia">
        <g:message code="liquidacionDeWolfran.porcentajeRegalia.label" default="Porcentaje Regalia" />

    </label>
    <g:textField name="porcentajeRegalia" inputmode="decimal" value="${liquidacionDeWolfranInstance?.porcentajeRegalia}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'regaliaMinera', 'error')} required">
    <label for="regaliaMinera">
        <g:message code="liquidacionDeWolfran.regaliaMinera.label" default="Regalia Minera" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="regaliaMinera" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'regaliaMinera')}" required="" inputmode="decimal"/>
    <g:hiddenField name="retenciones" value="${liquidacionDeWolfranInstance?.retenciones}"/>
</div>

<h1 style="font-weight: bold; font-size: 12px">Retenciones</h1>
<div style="width: 700px; margin-left: auto; margin-right: auto;"><table id="tablaRetenciones"></table></div>
<br />
<div style="text-align: center">
    <a href="javascript:void(0)" id="g4" onclick="eliminarRetencionWolfran();">Eliminar Fila Seleccionada</a>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'totalRetenciones', 'error')} required">
    <label for="totalRetenciones">
        <g:message code="liquidacionDeWolfran.totalRetenciones.label" default="Total Retenciones" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalRetenciones" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'totalRetenciones')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'totalPagado', 'error')} required">
    <label for="totalPagado">
        <g:message code="liquidacionDeWolfran.totalPagado.label" default="Total Pagado" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalPagado" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'totalPagado')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'totalAnticiposContraEntrega', 'error')} required">
    <label for="totalAnticiposContraEntrega">
        <g:message code="liquidacionDeWolfran.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalAnticiposContraEntrega" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'totalAnticiposContraEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'totalAnticiposContraFuturaEntrega', 'error')} required">
    <label for="totalAnticiposContraFuturaEntrega">
        <g:message code="liquidacionDeWolfran.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalAnticiposContraFuturaEntrega" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'totalAnticiposContraFuturaEntrega')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'totalLiquidoPagable', 'error')} required">
    <label for="totalLiquidoPagable">
        <g:message code="liquidacionDeWolfran.totalLiquidoPagable.label" default="Total Liquido Pagable" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalLiquidoPagable" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'totalLiquidoPagable')}" required="" inputmode="decimal"/>
</div>


<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="liquidacionDeWolfran.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${liquidacionDeWolfranInstance?.observaciones}" size="90"/>
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
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio1', 'error')}">
            <g:field name="detalleLaboratorio1" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio1')}" size="70" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio1', 'error')}">
            <g:field name="costoLaboratorio1" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio1')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio2', 'error')}">
            <g:field name="detalleLaboratorio2" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio2')}" size="70" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio2', 'error')}">
            <g:field name="costoLaboratorio2" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio2')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio3', 'error')}">
            <g:field name="detalleLaboratorio3" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio3')}" size="70"  class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio3', 'error')}">
            <g:field name="costoLaboratorio3" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio3')}"  class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio4', 'error')}">
            <g:field name="detalleLaboratorio4" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio4')}" size="70" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="costoLaboratorio4" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio4')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="liquidacionDeWolfran.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDeWolfranInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<div id="_modificacion" style="display: none">
<h1 style="font-weight: bold; font-size: 12px">Informacion de Modificacion del Registro</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'motivoDeModificacion', 'error')} ">
    <label for="motivoDeModificacion">
        <g:message code="liquidacionDeWolfran.motivoDeModificacion.label" default="Motivo De Modificacion" />

    </label>
    <g:textField name="motivoDeModificacion" value="${liquidacionDeWolfranInstance?.motivoDeModificacion}" size="90"/>
</div>
</div>