<%@ page import="org.socymet.liquidacion.LiquidacionDePlata" %>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="liquidacionDePlata.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${liquidacionDePlataInstance?.lote}"/>
    <g:hiddenField name="recepcionDePlata.id" value="${liquidacionDePlataInstance?.recepcionDePlata?.id}"/>
    <g:hiddenField name="conjuntoPlata" value="${liquidacionDePlataInstance?.conjuntoPlata}"/>
</div>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="liquidacionDePlata.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${liquidacionDePlataInstance?.nombreCliente}"  class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="liquidacionDePlata.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="empresaId" value="${liquidacionDePlataInstance?.recepcionDePlata?.empresa?.id}"/>
    <g:textField name="nombreEmpresa" required="" value="${liquidacionDePlataInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="liquidacionDePlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${liquidacionDePlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'cantidadDeSacos', 'error')} required">
    <label for="cantidadDeSacos">
        <g:message code="liquidacionDePlata.cantidadDeSacos.label" default="Cantidad De Sacos" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${liquidacionDePlataInstance?.cantidadDeSacos}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'tara', 'error')} ">
    <label for="tara">
        <g:message code="liquidacionDePlata.tara.label" default="Tara" />

    </label>
    <g:field name="tara" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'tara')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="liquidacionDePlata.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'estadoDelLote', 'error')} ">
    <label for="estadoDelLote">
        <g:message code="liquidacionDePlata.estadoDelLote.label" default="Estado Del Lote" />

    </label>
    <g:textField name="estadoDelLote" value="${liquidacionDePlataInstance?.estadoDelLote}" class="amarillo" readonly="true"/>
</div>

<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'cotizacionDiariaDePlata', 'error')} required">
            <label for="cotizacionDiariaDePlata" style="width: 50%">
                <g:message code="liquidacionDePlata.cotizacionDiariaDePlata.label" default="Cot. Dia Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDePlata" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'cotizacionDiariaDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'cotizacionQuincenalDePlata', 'error')} required">
            <label for="cotizacionQuincenalDePlata" style="width: 60%">
                <g:message code="liquidacionDePlata.cotizacionQuincenalDePlata.label" default="Cot. Quinc. Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDePlata" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'cotizacionQuincenalDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'alicuotaDePlata', 'error')} required">
            <label for="alicuotaDePlata" style="width: 50%">
                <g:message code="liquidacionDePlata.alicuotaDePlata.label" default="Alicuota Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDePlata" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'alicuotaDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDePlata.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioOficial" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'tipoDeCambioOficial')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 60%">
                <g:message code="liquidacionDePlata.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioComercial" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'tipoDeCambioComercial')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td>
            &nbsp;</td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'fechaDeLiquidacion', 'error')} required">
    <label for="fechaDeLiquidacion">
        <g:message code="liquidacionDePlata.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeLiquidacion" precision="day"  value="${liquidacionDePlataInstance?.fechaDeLiquidacion}"  />
</div>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'kilosNetosHumedos', 'error')} required">
            <label for="kilosNetosHumedos" style="width: 50%">
                <g:message code="liquidacionDePlata.kilosNetosHumedos.label" default="K. N. H." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosHumedos" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'kilosNetosHumedos')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'humedad', 'error')} required">
            <label for="humedad" style="width: 50%">
                <g:message code="liquidacionDePlata.humedad.label" default="% H2O" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="humedad" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'humedad')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 50%">
                <g:message code="liquidacionDePlata.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosSecos" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'kilosNetosSecos')}" required="" size="10" readonly="true" class="amarillo"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'porcentajePlata', 'error')} required">
            <label for="porcentajePlata" style="width: 50%">
                <g:message code="liquidacionDePlata.porcentajePlata.label" default="DM de Ag" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="porcentajePlata" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'porcentajePlata')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'kilosFinosPlata', 'error')} required">
            <label for="kilosFinosPlata" style="width: 50%">
                <g:message code="liquidacionDePlata.kilosFinosPlata.label" default="Gramos/Ton." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosPlata" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'kilosFinosPlata')}" required="" size="10" readonly="true" class="amarillo"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'librasFinasDePlata', 'error')} required">
            <label for="librasFinasDePlata" style="width: 50%">
                <g:message code="liquidacionDePlata.librasFinasDePlata.label" default="Onzas Troy" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="librasFinasDePlata" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'librasFinasDePlata')}" required="" size="10" readonly="true" class="amarillo"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'valorOficialBruto', 'error')} required">
    <label for="valorOficialBruto">
        <g:message code="liquidacionDePlata.valorOficialBruto.label" default="Valor Oficial Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorOficialBruto" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'valorOficialBruto')}" required="" size="10" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold; font-size: 12px">Parametros de Proteccion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'valorPorToneladaManual', 'error')} ">
    <label for="valorPorToneladaManual">
        <g:message code="liquidacionDePlata.valorPorToneladaManual.label" default="Valor Por Tonelada Manual" />

    </label>
    <g:field name="valorPorToneladaManual" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'valorPorToneladaManual')}" inputmode="decimal"/>
    <input type="radio" id="radio1" name="RadioGroup1" value="1" checked="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'puntoDeBajada', 'error')} ">
    <label for="puntoDeBajada">
        <g:message code="liquidacionDePlata.puntoDeBajada.label" default="Punto De Bajada" />

    </label>
    <g:field name="puntoDeBajada" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'puntoDeBajada')}" inputmode="decimal"/>
    <input type="radio" id="radio2" name="RadioGroup1" value="2" />
</div>

<h1 style="font-weight: bold; font-size: 12px">Valoracion Final del Lote</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'tablaCotizacionPlata', 'error')} ">
    <label for="tablaCotizacionPlata">
        <g:message code="liquidacionDePlata.tablaCotizacionPlata.label" default="Tabla Cotizacion Plata" />

    </label>
    <g:select id="tablaCotizacionPlata" name="tablaCotizacionPlata.id" from="${org.socymet.cotizaciones.TablaCotizacionPlata.list()}" optionKey="id" value="${liquidacionDePlataInstance?.tablaCotizacionPlata?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'valorPorTonelada', 'error')} required">
    <label for="valorPorTonelada">
        <g:message code="liquidacionDePlata.valorPorTonelada.label" default="Valor Por Tonelada" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorPorTonelada" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'valorPorTonelada')}" required="" class="verde" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'valorNetoMineral', 'error')} required">
    <label for="valorNetoMineral">
        <g:message code="liquidacionDePlata.valorNetoMineral.label" default="Valor Neto Mineral" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineral" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'valorNetoMineral')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'valorNetoMineralEnBolivianos', 'error')} required">
    <label for="valorNetoMineralEnBolivianos">
        <g:message code="liquidacionDePlata.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineralEnBolivianos" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'valorNetoMineralEnBolivianos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'bonoCalidad', 'error')} required">
    <label for="bonoCalidad">
        <g:message code="liquidacionDePlata.bonoCalidad.label" default="Bono Calidad" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoCalidad" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'bonoCalidad')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'bonoIncentivo', 'error')} required">
    <label for="bonoIncentivo">
        <g:message code="liquidacionDePlata.bonoIncentivo.label" default="Bono Incentivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoIncentivo" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'bonoIncentivo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'valorDeCompra', 'error')} required">
    <label for="valorDeCompra">
        <g:message code="liquidacionDePlata.valorDeCompra.label" default="Valor De Compra" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorDeCompra" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'valorDeCompra')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'porcentajeRegalia', 'error')} ">
    <label for="porcentajeRegalia">
        <g:message code="liquidacionDePlata.porcentajeRegalia.label" default="Porcentaje Regalia" />

    </label>
    <g:textField name="porcentajeRegalia" inputmode="decimal" value="${liquidacionDePlataInstance?.porcentajeRegalia}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'regaliaMinera', 'error')} required">
    <label for="regaliaMinera">
        <g:message code="liquidacionDePlata.regaliaMinera.label" default="Regalia Minera" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="regaliaMinera" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'regaliaMinera')}" required="" inputmode="decimal"/>
    <g:hiddenField name="retenciones" value="${liquidacionDePlataInstance?.retenciones}"/>
</div>

<h1 style="font-weight: bold; font-size: 12px">Retenciones</h1>
<div style="width: 700px; margin-left: auto; margin-right: auto;"><table id="tablaRetenciones"></table></div>
<br />
<div style="text-align: center">
    <a href="javascript:void(0)" id="g4" onclick="eliminarRetencionPlata();">Eliminar Fila Seleccionada</a>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'totalRetenciones', 'error')} required">
    <label for="totalRetenciones">
        <g:message code="liquidacionDePlata.totalRetenciones.label" default="Total Retenciones" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalRetenciones" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'totalRetenciones')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'totalPagado', 'error')} required">
    <label for="totalPagado">
        <g:message code="liquidacionDePlata.totalPagado.label" default="Total Pagado" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalPagado" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'totalPagado')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'totalAnticiposContraEntrega', 'error')} required">
    <label for="totalAnticiposContraEntrega">
        <g:message code="liquidacionDePlata.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalAnticiposContraEntrega" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'totalAnticiposContraEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'totalAnticiposContraFuturaEntrega', 'error')} required">
    <label for="totalAnticiposContraFuturaEntrega">
        <g:message code="liquidacionDePlata.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalAnticiposContraFuturaEntrega" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'totalAnticiposContraFuturaEntrega')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'totalLiquidoPagable', 'error')} required">
    <label for="totalLiquidoPagable">
        <g:message code="liquidacionDePlata.totalLiquidoPagable.label" default="Total Liquido Pagable" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalLiquidoPagable" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'totalLiquidoPagable')}" required="" inputmode="decimal"/>
</div>


<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="liquidacionDePlata.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${liquidacionDePlataInstance?.observaciones}" size="90"/>
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
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio1', 'error')}">
            <g:field name="detalleLaboratorio1" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio1')}" size="70" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'costoLaboratorio1', 'error')}">
            <g:field name="costoLaboratorio1" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'costoLaboratorio1')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio2', 'error')}">
            <g:field name="detalleLaboratorio2" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio2')}" size="70" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'costoLaboratorio2', 'error')}">
            <g:field name="costoLaboratorio2" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'costoLaboratorio2')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio3', 'error')}">
            <g:field name="detalleLaboratorio3" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio3')}" size="70"  class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'costoLaboratorio3', 'error')}">
            <g:field name="costoLaboratorio3" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'costoLaboratorio3')}"  class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio4', 'error')}">
            <g:field name="detalleLaboratorio4" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio4')}" size="70" class="amarillo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="costoLaboratorio4" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'costoLaboratorio4')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="liquidacionDePlata.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDePlataInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<div id="_modificacion" style="display: none">
<h1 style="font-weight: bold; font-size: 12px">Informacion de Modificacion del Registro</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'motivoDeModificacion', 'error')} ">
    <label for="motivoDeModificacion">
        <g:message code="liquidacionDePlata.motivoDeModificacion.label" default="Motivo De Modificacion" />

    </label>
    <g:textField name="motivoDeModificacion" value="${liquidacionDePlataInstance?.motivoDeModificacion}" size="90"/>
</div>
</div>