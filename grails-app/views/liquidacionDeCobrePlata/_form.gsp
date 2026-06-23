<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo" %>

<g:hiddenField name="vista" value="" />

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'lote', 'error')} required"
     xmlns="http://www.w3.org/1999/html">
    <label for="lote">
        <g:message code="liquidacionDeCobrePlata.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${liquidacionDeCobrePlataInstance?.lote}"/>
    <g:hiddenField name="recepcionDeComplejo.id" value="${liquidacionDeCobrePlataInstance?.recepcionDeComplejo?.id}"/>
    <g:hiddenField name="empresa.id" value="${liquidacionDeCobrePlataInstance?.empresa?.id}"/>
    <g:hiddenField name="deposito.id" value="${liquidacionDeCobrePlataInstance?.deposito?.id}"/>
    <g:hiddenField name="conjuntoCobrePlata" value="${liquidacionDeCobrePlataInstance?.conjuntoCobrePlata}"/>
</div>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'nombreDeposito', 'error')} required">
    <label for="nombreDeposito">
        <g:message code="liquidacionDeCobrePlata.nombreDeposito.label" default="Nombre Deposito" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreDeposito" required="" value="${liquidacionDeCobrePlataInstance?.nombreDeposito}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="liquidacionDeCobrePlata.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${liquidacionDeCobrePlataInstance?.nombreCliente}"  class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="liquidacionDeCobrePlata.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="empresaId" value="${liquidacionDeCobrePlataInstance?.recepcionDeComplejo?.empresa?.id}"/>
    <g:textField name="nombreEmpresa" required="" value="${liquidacionDeCobrePlataInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="liquidacionDeCobrePlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${liquidacionDeCobrePlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'cantidadDeSacos', 'error')} required">
    <label for="cantidadDeSacos">
        <g:message code="liquidacionDeCobrePlata.cantidadDeSacos.label" default="Cantidad De Sacos" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${liquidacionDeCobrePlataInstance?.cantidadDeSacos}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="liquidacionDeCobrePlata.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'tipoDeMineral', 'error')} required">
    <label for="tipoDeMineral">
        <g:message code="liquidacionDeCobrePlata.tipoDeMineral.label" default="Tipo de Mineral" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="tipoDeMineral" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'tipoDeMineral')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'estadoDelLote', 'error')} ">
    <label for="estadoDelLote">
        <g:message code="liquidacionDeCobrePlata.estadoDelLote.label" default="Estado Del Lote" />

    </label>
    <g:textField name="estadoDelLote" value="${liquidacionDeCobrePlataInstance?.estadoDelLote}" class="amarillo" readonly="true"/>
</div>


<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'cotizacionDiariaDeCobre', 'error')} required">
            <label for="cotizacionDiariaDeCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.cotizacionDiariaDeCobre.label" default="Cot. Dia Cobre" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDeCobre" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'cotizacionDiariaDeCobre')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'cotizacionQuincenalDeCobre', 'error')} required">
            <label for="cotizacionQuincenalDeCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.cotizacionQuincenalDeCobre.label" default="Cot. Quinc. Cobre" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDeCobre" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'cotizacionQuincenalDeCobre')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'alicuotaDeCobre', 'error')} required">
            <label for="alicuotaDeCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.alicuotaDeCobre.label" default="Alicuota Cobre" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDeCobre" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'alicuotaDeCobre')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'cotizacionDiariaDePlata', 'error')} required">
            <label for="cotizacionDiariaDePlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.cotizacionDiariaDePlata.label" default="Cot. Dia Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDePlata" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'cotizacionDiariaDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'cotizacionQuincenalDePlata', 'error')} required">
            <label for="cotizacionQuincenalDePlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.cotizacionQuincenalDePlata.label" default="Cot. Quinc. Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDePlata" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'cotizacionQuincenalDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'alicuotaDePlata', 'error')} required">
            <label for="alicuotaDePlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.alicuotaDePlata.label" default="Alicuota Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDePlata" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'alicuotaDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioOficial" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'tipoDeCambioOficial')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioComercial" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'tipoDeCambioComercial')}" required="" size="10" class="amarillo" readonly="true"/>
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
        <th style="width: 20%">LEY EMPRESA</th>
        <th style="width: 20%">LEY CLIENTE</th>
        <th style="width: 20%">LEY FINAL</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeMermaPromexbol">
                <g:message code="liquidacionDeCobrePlata.porcentajeMermaPromexbol.label" default="Merma" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
            <g:field name="porcentajeMermaPromexbol" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeMermaPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeMermaCliente', 'error')} required">
            <g:field name="porcentajeMermaCliente" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeMermaCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeMermaFinal', 'error')} required">
            <g:field name="porcentajeMermaFinal" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeMermaFinal')}" required="" readonly="true"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeZincCliente">
                <g:message code="liquidacionDeCobrePlata.porcentajeZincCliente.label" default="Cobre" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeCobrePromexbol', 'error')} required">
            <g:field name="porcentajeCobrePromexbol" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeCobrePromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeCobreCliente', 'error')} required">
            <g:field name="porcentajeCobreCliente" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeCobreCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeCobreFinal', 'error')} required">
            <g:field name="porcentajeCobreFinal" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeCobreFinal')}" required=""  inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeZincFinal">
                <g:message code="liquidacionDeCobrePlata.porcentajeZincFinal.label" default="Plata" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajePlataPromexbol', 'error')} required">
            <g:field name="porcentajePlataPromexbol" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajePlataPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajePlataCliente', 'error')} required">
            <g:field name="porcentajePlataCliente" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajePlataCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajePlataFinal', 'error')} required">
            <g:field name="porcentajePlataFinal" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajePlataFinal')}" required=""  inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeHumedadPromexbol">
                <g:message code="liquidacionDeCobrePlata.porcentajeHumedadPromexbol.label" default="Humedad" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
            <g:field name="porcentajeHumedadPromexbol" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeHumedadPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeHumedadCliente', 'error')} required">
            <g:field name="porcentajeHumedadCliente" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeHumedadCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeHumedadFinal', 'error')} required">
            <g:field name="porcentajeHumedadFinal" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeHumedadFinal')}" required=""  inputmode="decimal"/>
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

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'modoValoracion', 'error')} ">
    <label for="modoValoracion">
        <g:message code="liquidacionDeCobrePlata.modoValoracion.label" default="Modo Valoracion" />

    </label>
    <g:hiddenField name="tablasIds" value="" />
    <g:hiddenField name="terminosIds" value="" />
    <g:select name="modoValoracion" from="${['TABLA','TERMINOS DE CONTRATO']}" value="${liquidacionDeCobrePlataInstance?.modoValoracion}" valueMessagePrefix="liquidacionDeCobrePlata.modoValoracion"/>
</div>

<div id="_tablaCobre" class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'tablaCobre', 'error')} required">
    <label for="tablaCobre">
        <g:message code="liquidacionDeCobrePlata.tablaCobre.label" default="Tabla Cobre" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="tablaCobre" name="tablaCobre.id" from="${org.socymet.cotizaciones.TablaPreciosCobre.list()}" optionKey="id" required="" value="${liquidacionDeCobrePlataInstance?.tablaCobre?.id}" class="many-to-one"/>
</div>

<div id="_terminosDeContrato" class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'terminosDeContrato', 'error')} required">
    <label for="terminosDeContrato">
        <g:message code="liquidacionDeCobrePlata.terminosDeContrato.label" default="Terminos De Contrato" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="terminosDeContrato" name="terminosDeContrato.id" from="${org.socymet.cotizaciones.TerminosDeContrato.list()}" optionKey="id" required="" value="${liquidacionDeCobrePlataInstance?.terminosDeContrato?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'margen', 'error')} ">
    <label for="margen">
        <g:message code="liquidacionDeCobrePlata.margen.label" default="Margen" />

    </label>
    <g:textField name="margen" inputmode="decimal" value="${liquidacionDeCobrePlataInstance?.margen}" />
</div>

<br>

<div style="text-align: center;">
    <input id="valorar" type="button" value="VALORAR LOTE" style="background-color: #255b17; color: white; font-size: 16px;"/>
</div>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'fechaDeLiquidacion', 'error')} required">
    <label for="fechaDeLiquidacion">
        <g:message code="liquidacionDeCobrePlata.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeLiquidacion" precision="day"  value="${liquidacionDeCobrePlataInstance?.fechaDeLiquidacion}"  />
</div>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'kilosNetosHumedos', 'error')} required">
            <label for="kilosNetosHumedos" style="width: 30%">
                <g:message code="liquidacionDeCobrePlata.kilosNetosHumedos.label" default="K. N. H." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosHumedos" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'kilosNetosHumedos')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 30%">
                <g:message code="liquidacionDeCobrePlata.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosSecos" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'kilosNetosSecos')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajePlata', 'error')} required">
        </td>
    </tr>
    </tbody>
</table>

<br/>

<g:hiddenField name="dolarPuntoZinc" value="0" />
<g:hiddenField name="dolarPuntoCobre" value="0" />
<g:hiddenField name="dolarPuntoPlata" value="0" />

<g:hiddenField name="merma" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'merma')}" />
<g:hiddenField name="humedad" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'humedad')}" />
<g:hiddenField name="porcentajeCobre" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeCobre')}" />
<g:hiddenField name="porcentajePlata" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajePlata')}" />

<table class="center" border="0" style="width: 80%;">
    <thead>
    <tr>
        <th style="text-align: center">COBRE</th>
        <th style="text-align: center">PLATA</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'kilosFinosCobre', 'error')} required">
            <label for="kilosFinosCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.kilosFinosCobre.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosCobre" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'kilosFinosCobre')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'kilosFinosPlata', 'error')} required">
            <label for="kilosFinosPlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.kilosFinosPlata.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosPlata" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'kilosFinosPlata')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'librasFinasDeCobre', 'error')} required">
            <label for="librasFinasDeCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.librasFinasDeCobre.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="librasFinasDeCobre" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'librasFinasDeCobre')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'onzasTroyDePlata', 'error')} required">
            <label for="onzasTroyDePlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.onzasTroyDePlata.label" default="Onzas Troy" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="onzasTroyDePlata" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'onzasTroyDePlata')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBrutoDeCobre', 'error')} required">
            <label for="valorOficialBrutoDeCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.valorOficialBrutoDeCobre.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDeCobre" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBrutoDeCobre')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBrutoDePlata', 'error')} required">
            <label for="valorOficialBrutoDePlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.valorOficialBrutoDePlata.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDePlata" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBrutoDePlata')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBrutoDeCobreEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDeCobreEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.valorOficialBrutoDeCobreEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDeCobreEnBolivianos" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBrutoDeCobreEnBolivianos')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBrutoDePlataEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDePlataEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.valorOficialBrutoDePlataEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDePlataEnBolivianos" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBrutoDePlataEnBolivianos')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBruto', 'error')} required">
    <label for="valorOficialBruto">
        <g:message code="liquidacionDeCobrePlata.valorOficialBruto.label" default="Valor Oficial Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorOficialBruto" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorPorTonelada', 'error')} required">
    <label for="valorPorTonelada">
        <g:message code="liquidacionDeCobrePlata.valorPorTonelada.label" default="Valor Por Tonelada" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorPorTonelada" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'valorPorTonelada')}" required="" class="verde" inputmode="decimal"/>
</div>

<g:hiddenField name="porcentajeRegalia" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeRegalia')}"/>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'regaliaMinera', 'error')} required">
    <label for="regaliaMinera">
        <g:message code="liquidacionDeCobrePlata.regaliaMinera.label" default="Regalia Minera" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="regaliaMinera" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'regaliaMinera')}" required="" inputmode="decimal"/>
</div>

<g:hiddenField name="retenciones" value="${liquidacionDeCobrePlataInstance?.retenciones}" />

<h1 style="font-weight: bold">Retenciones</h1>

<div style="width: 700px; margin-left: auto; margin-right: auto;">
    <table id="tablaRetenciones">
    </table>
</div>

<div id="_botones" style="text-align: center">
    <br>
    <button id="eliminarRetencion" type="button">ELIMINAR RETENCION SELECCIONADA</button>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorNetoMineral', 'error')} required">
    <label for="valorNetoMineral">
        <g:message code="liquidacionDeCobrePlata.valorNetoMineral.label" default="Valor Neto Mineral" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineral" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'valorNetoMineral')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorNetoMineralEnBolivianos', 'error')} required">
    <label for="valorNetoMineralEnBolivianos">
        <g:message code="liquidacionDeCobrePlata.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineralEnBolivianos" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'valorNetoMineralEnBolivianos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'bonoCalidad', 'error')} required">
    <label for="bonoCalidad">
        <g:message code="liquidacionDeCobrePlata.bonoCalidad.label" default="Bono Calidad" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoCalidad" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'bonoCalidad')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'bonoIncentivo', 'error')} required">
    <label for="bonoIncentivo">
        <g:message code="liquidacionDeCobrePlata.bonoIncentivo.label" default="Bono Incentivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoIncentivo" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'bonoIncentivo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorDeCompra', 'error')} required">
    <label for="valorDeCompra">
        <g:message code="liquidacionDeCobrePlata.valorDeCompra.label" default="Valor De Compra" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorDeCompra" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'valorDeCompra')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'totalRetenciones', 'error')} required">
    <label for="totalRetenciones">
        <g:message code="liquidacionDeCobrePlata.totalRetenciones.label" default="Total Retenciones" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalRetenciones" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'totalRetenciones')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'totalPagado', 'error')} required">
    <label for="totalPagado">
        <g:message code="liquidacionDeCobrePlata.totalPagado.label" default="Total Pagado" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalPagado" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'totalPagado')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'anticipoPorPagar', 'error')} required">
    <label for="anticipoPorPagar">
        <g:message code="liquidacionDeCobrePlata.anticipoPorPagar.label" default="Anticipo Por Pagar" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="anticipoPorPagar" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'anticipoPorPagar')}" required="" class="rojo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'totalAnticiposContraEntrega', 'error')} required">
    <label for="totalAnticiposContraEntrega">
        <g:message code="liquidacionDeCobrePlata.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalAnticiposContraEntrega" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'totalAnticiposContraEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'totalAnticiposContraFuturaEntrega', 'error')} required">
    <label for="totalAnticiposContraFuturaEntrega">
        <g:message code="liquidacionDeCobrePlata.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="adelantoPorLiquidacionProvisional" value="0"/>
    <g:field name="totalAnticiposContraFuturaEntrega" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'totalAnticiposContraFuturaEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'totalLiquidoPagable', 'error')} required">
    <label for="totalLiquidoPagable">
        <g:message code="liquidacionDeCobrePlata.totalLiquidoPagable.label" default="Total Liquido Pagable" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalLiquidoPagable" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'totalLiquidoPagable')}" required="" class="verde" inputmode="decimal"/>
    <g:hiddenField name="totalLiquidoPagableOriginal" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'totalLiquidoPagableOriginal')}"/>
    <g:hiddenField name="diferenciaLiquidoPagable" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'diferenciaLiquidoPagable')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="liquidacionDeCobrePlata.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${liquidacionDeCobrePlataInstance?.observaciones}" size="90"/>
</div>

<g:hiddenField name="detalleLaboratorio1" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'detalleLaboratorio1')}"/>
<g:hiddenField name="costoLaboratorio1" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'costoLaboratorio1')}"/>
<g:hiddenField name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'totalCostoLaboratorio')}"/>

<div id="_modificacion" style="display: none">
    <h1 style="font-weight: bold; font-size: 12px">Informacion de Modificacion del Registro</h1>

    <div class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'motivoDeModificacion', 'error')} ">
        <label for="motivoDeModificacion">
            <g:message code="liquidacionDeCobrePlata.motivoDeModificacion.label" default="Motivo De Modificacion" />

        </label>
        <g:textField name="motivoDeModificacion" value="${liquidacionDeCobrePlataInstance?.motivoDeModificacion}" size="90"/>
    </div>
</div>

<div style="display:none;" class="nav_up" id="nav_up"></div>
<div style="display:none;" class="nav_down" id="nav_down"></div>