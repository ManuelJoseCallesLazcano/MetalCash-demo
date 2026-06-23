<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo" %>

<g:hiddenField name="vista" value="" />

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'lote', 'error')} required"
     xmlns="http://www.w3.org/1999/html">
    <label for="lote">
        <g:message code="liquidacionDeZincPlata.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${liquidacionDeZincPlataInstance?.lote}"/>
    <g:hiddenField name="recepcionDeComplejo.id" value="${liquidacionDeZincPlataInstance?.recepcionDeComplejo?.id}"/>
    <g:hiddenField name="empresa.id" value="${liquidacionDeZincPlataInstance?.empresa?.id}"/>
    <g:hiddenField name="deposito.id" value="${liquidacionDeZincPlataInstance?.deposito?.id}"/>
    <g:hiddenField name="conjuntoZincPlata" value="${liquidacionDeZincPlataInstance?.conjuntoZincPlata}"/>
</div>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'nombreDeposito', 'error')} required">
    <label for="nombreDeposito">
        <g:message code="liquidacionDeZincPlata.nombreDeposito.label" default="Nombre Deposito" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreDeposito" required="" value="${liquidacionDeZincPlataInstance?.nombreDeposito}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="liquidacionDeZincPlata.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${liquidacionDeZincPlataInstance?.nombreCliente}"  class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="liquidacionDeZincPlata.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="empresaId" value="${liquidacionDeZincPlataInstance?.recepcionDeComplejo?.empresa?.id}"/>
    <g:textField name="nombreEmpresa" required="" value="${liquidacionDeZincPlataInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="liquidacionDeZincPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${liquidacionDeZincPlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'cantidadDeSacos', 'error')} required">
    <label for="cantidadDeSacos">
        <g:message code="liquidacionDeZincPlata.cantidadDeSacos.label" default="Cantidad De Sacos" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${liquidacionDeZincPlataInstance?.cantidadDeSacos}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="liquidacionDeZincPlata.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'tipoDeMineral', 'error')} required">
    <label for="tipoDeMineral">
        <g:message code="liquidacionDeZincPlata.tipoDeMineral.label" default="Tipo de Mineral" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="tipoDeMineral" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'tipoDeMineral')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'naturalezaMineral', 'error')} ">
    <label for="naturalezaMineral">
        <g:message code="liquidacionDeZincPlata.naturalezaMineral.label" default="Naturaleza Mineral" />

    </label>
    <g:textField name="naturalezaMineral" value="${liquidacionDeZincPlataInstance?.naturalezaMineral}" class="amarillo" readonly="true"/>
    <g:hiddenField name="estadoDelLote" value="${liquidacionDeZincPlataInstance?.estadoDelLote}" />
</div>

%{--<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'estadoDelLote', 'error')} ">--}%
    %{--<label for="estadoDelLote">--}%
        %{--<g:message code="liquidacionDeZincPlata.estadoDelLote.label" default="Estado Del Lote" />--}%

    %{--</label>--}%
    %{--<g:textField name="estadoDelLote" value="${liquidacionDeZincPlataInstance?.estadoDelLote}" class="amarillo" readonly="true"/>--}%
%{--</div>--}%


<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'cotizacionDiariaDeZinc', 'error')} required">
            <label for="cotizacionDiariaDeZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.cotizacionDiariaDeZinc.label" default="Cot. Dia Zinc" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDeZinc" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'cotizacionDiariaDeZinc')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'cotizacionQuincenalDeZinc', 'error')} required">
            <label for="cotizacionQuincenalDeZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.cotizacionQuincenalDeZinc.label" default="Cot. Quinc. Zinc" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDeZinc" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'cotizacionQuincenalDeZinc')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'alicuotaDeZinc', 'error')} required">
            <label for="alicuotaDeZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.alicuotaDeZinc.label" default="Alicuota Zinc" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDeZinc" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'alicuotaDeZinc')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'cotizacionDiariaDePlata', 'error')} required">
            <label for="cotizacionDiariaDePlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.cotizacionDiariaDePlata.label" default="Cot. Dia Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDePlata" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'cotizacionDiariaDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'cotizacionQuincenalDePlata', 'error')} required">
            <label for="cotizacionQuincenalDePlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.cotizacionQuincenalDePlata.label" default="Cot. Quinc. Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDePlata" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'cotizacionQuincenalDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'alicuotaDePlata', 'error')} required">
            <label for="alicuotaDePlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.alicuotaDePlata.label" default="Alicuota Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDePlata" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'alicuotaDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioOficial" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'tipoDeCambioOficial')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioComercial" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'tipoDeCambioComercial')}" required="" size="10" class="amarillo" readonly="true"/>
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
                <g:message code="liquidacionDeZincPlata.porcentajeMermaPromexbol.label" default="Merma" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
            <g:field name="porcentajeMermaPromexbol" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajeMermaPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajeMermaCliente', 'error')} required">
            <g:field name="porcentajeMermaCliente" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajeMermaCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajeMermaFinal', 'error')} required">
            <g:field name="porcentajeMermaFinal" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajeMermaFinal')}" required="" readonly="true"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeZincCliente">
                <g:message code="liquidacionDeZincPlata.porcentajeZincCliente.label" default="Zinc" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajeZincPromexbol', 'error')} required">
            <g:field name="porcentajeZincPromexbol" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajeZincPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajeZincCliente', 'error')} required">
            <g:field name="porcentajeZincCliente" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajeZincCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajeZincFinal', 'error')} required">
            <g:field name="porcentajeZincFinal" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajeZincFinal')}" required=""  inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeZincFinal">
                <g:message code="liquidacionDeZincPlata.porcentajeZincFinal.label" default="Plata" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajePlataPromexbol', 'error')} required">
            <g:field name="porcentajePlataPromexbol" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajePlataPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajePlataCliente', 'error')} required">
            <g:field name="porcentajePlataCliente" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajePlataCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajePlataFinal', 'error')} required">
            <g:field name="porcentajePlataFinal" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajePlataFinal')}" required=""  inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeHumedadPromexbol">
                <g:message code="liquidacionDeZincPlata.porcentajeHumedadPromexbol.label" default="Humedad" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
            <g:field name="porcentajeHumedadPromexbol" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajeHumedadPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajeHumedadCliente', 'error')} required">
            <g:field name="porcentajeHumedadCliente" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajeHumedadCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajeHumedadFinal', 'error')} required">
            <g:field name="porcentajeHumedadFinal" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajeHumedadFinal')}" required=""  inputmode="decimal"/>
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

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'modoValoracion', 'error')} ">
    <label for="modoValoracion">
        <g:message code="liquidacionDeZincPlata.modoValoracion.label" default="Modo Valoracion" />

    </label>
    <g:hiddenField name="tablasIds" value="" />
    <g:hiddenField name="preciosPorLmeIds" value="" />
    <g:hiddenField name="terminosIds" value="" />
    <g:select name="modoValoracion" from="${['TABLA','PRECIO POR LME','TERMINOS DE CONTRATO']}" value="${liquidacionDeZincPlataInstance?.modoValoracion}" valueMessagePrefix="liquidacionDeZincPlata.modoValoracion"/>
</div>

<div id="_tablaComplejo" class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'tablaComplejo', 'error')} required">
    <label for="tablaComplejo">
        <g:message code="liquidacionDeZincPlata.tablaComplejo.label" default="Tabla Complejo" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="tablaComplejo" name="tablaComplejo.id" from="${org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo.list()}" optionKey="id" required="" value="${liquidacionDeZincPlataInstance?.tablaComplejo?.id}" class="many-to-one"/>
</div>

<div id="_tablaPrecioPorLme" class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'tablaPrecioPorLme', 'error')} required">
    <label for="tablaPrecioPorLme">
        <g:message code="liquidacionDeZincPlata.tablaPrecioPorLme.label" default="Tabla Precio LME" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="tablaPrecioPorLme" name="tablaPrecioPorLme.id" from="${org.socymet.cotizaciones.TablaPrecioPorLme.list()}" optionKey="id" required="" value="${liquidacionDeZincPlataInstance?.tablaPrecioPorLme?.id}" class="many-to-one"/>
</div>

<div id="_terminosDeContrato" class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'terminosDeContrato', 'error')} required">
    <label for="terminosDeContrato">
        <g:message code="liquidacionDeZincPlata.terminosDeContrato.label" default="Terminos De Contrato" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="terminosDeContrato" name="terminosDeContrato.id" from="${org.socymet.cotizaciones.TerminosDeContrato.list()}" optionKey="id" required="" value="${liquidacionDeZincPlataInstance?.terminosDeContrato?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'margen', 'error')} ">
    <label for="margen">
        <g:message code="liquidacionDeZincPlata.margen.label" default="Margen" />

    </label>
    <g:textField name="margen" inputmode="decimal" value="${liquidacionDeZincPlataInstance?.margen}" />
</div>

<br>

<div style="text-align: center;">
    <input id="valorar" type="button" value="VALORAR LOTE" style="background-color: #255b17; color: white; font-size: 16px;"/>
</div>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'fechaDeLiquidacion', 'error')} required">
    <label for="fechaDeLiquidacion">
        <g:message code="liquidacionDeZincPlata.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeLiquidacion" precision="day"  value="${liquidacionDeZincPlataInstance?.fechaDeLiquidacion}"  />
</div>

<br/>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'kilosNetosHumedos', 'error')} required">
            <label for="kilosNetosHumedos" style="width: 30%">
                <g:message code="liquidacionDeZincPlata.kilosNetosHumedos.label" default="K. N. H." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosHumedos" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'kilosNetosHumedos')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 30%">
                <g:message code="liquidacionDeZincPlata.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosSecos" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'kilosNetosSecos')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajePlata', 'error')} required">
        </td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="dolarPuntoZinc" value="0" />
<g:hiddenField name="dolarPuntoPlomo" value="0" />
<g:hiddenField name="dolarPuntoPlata" value="0" />

<g:hiddenField name="merma" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'merma')}" />
<g:hiddenField name="humedad" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'humedad')}" />
<g:hiddenField name="porcentajeZinc" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajeZinc')}" />
<g:hiddenField name="porcentajePlata" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajePlata')}" />

<table class="center" border="0" style="width: 80%;">
    <thead>
    <tr>
        <th style="text-align: center">ZINC</th>
        <th style="text-align: center">PLATA</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'kilosFinosZinc', 'error')} required">
            <label for="kilosFinosZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.kilosFinosZinc.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosZinc" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'kilosFinosZinc')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'kilosFinosPlata', 'error')} required">
            <label for="kilosFinosPlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.kilosFinosPlata.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosPlata" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'kilosFinosPlata')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'librasFinasDeZinc', 'error')} required">
            <label for="librasFinasDeZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.librasFinasDeZinc.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="librasFinasDeZinc" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'librasFinasDeZinc')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'onzasTroyDePlata', 'error')} required">
            <label for="onzasTroyDePlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.onzasTroyDePlata.label" default="Onzas Troy" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="onzasTroyDePlata" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'onzasTroyDePlata')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBrutoDeZinc', 'error')} required">
            <label for="valorOficialBrutoDeZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.valorOficialBrutoDeZinc.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDeZinc" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBrutoDeZinc')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBrutoDePlata', 'error')} required">
            <label for="valorOficialBrutoDePlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.valorOficialBrutoDePlata.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDePlata" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBrutoDePlata')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBrutoDeZincEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDeZincEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.valorOficialBrutoDeZincEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDeZincEnBolivianos" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBrutoDeZincEnBolivianos')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBrutoDePlataEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDePlataEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.valorOficialBrutoDePlataEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDePlataEnBolivianos" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBrutoDePlataEnBolivianos')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBruto', 'error')} required">
    <label for="valorOficialBruto">
        <g:message code="liquidacionDeZincPlata.valorOficialBruto.label" default="Valor Oficial Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorOficialBruto" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorPorTonelada', 'error')} required">
    <label for="valorPorTonelada">
        <g:message code="liquidacionDeZincPlata.valorPorTonelada.label" default="Valor Por Tonelada" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorPorTonelada" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'valorPorTonelada')}" required="" class="verde" inputmode="decimal"/>
</div>

<g:hiddenField name="porcentajeRegalia" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'porcentajeRegalia')}"/>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'regaliaMinera', 'error')} required">
    <label for="regaliaMinera">
        <g:message code="liquidacionDeZincPlata.regaliaMinera.label" default="Regalia Minera" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="regaliaMinera" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'regaliaMinera')}" required="" inputmode="decimal"/>
</div>

<g:hiddenField name="retenciones" value="${liquidacionDeZincPlataInstance?.retenciones}" />

<h1 style="font-weight: bold">Retenciones</h1>

<div style="width: 700px; margin-left: auto; margin-right: auto;">
    <table id="tablaRetenciones">
    </table>
</div>

<div id="_botones" style="text-align: center">
    <br>
    <button id="eliminarRetencion" type="button">ELIMINAR RETENCION SELECCIONADA</button>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorNetoMineral', 'error')} required">
    <label for="valorNetoMineral">
        <g:message code="liquidacionDeZincPlata.valorNetoMineral.label" default="Valor Neto Mineral" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineral" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'valorNetoMineral')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorNetoMineralEnBolivianos', 'error')} required">
    <label for="valorNetoMineralEnBolivianos">
        <g:message code="liquidacionDeZincPlata.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineralEnBolivianos" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'valorNetoMineralEnBolivianos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'bonoCalidad', 'error')} required">
    <label for="bonoCalidad">
        <g:message code="liquidacionDeZincPlata.bonoCalidad.label" default="Bono Calidad" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoCalidad" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'bonoCalidad')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'bonoIncentivo', 'error')} required">
    <label for="bonoIncentivo">
        <g:message code="liquidacionDeZincPlata.bonoIncentivo.label" default="Bono Incentivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoIncentivo" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'bonoIncentivo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorDeCompra', 'error')} required">
    <label for="valorDeCompra">
        <g:message code="liquidacionDeZincPlata.valorDeCompra.label" default="Valor De Compra" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorDeCompra" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'valorDeCompra')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'totalRetenciones', 'error')} required">
    <label for="totalRetenciones">
        <g:message code="liquidacionDeZincPlata.totalRetenciones.label" default="Total Retenciones" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalRetenciones" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'totalRetenciones')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'totalPagado', 'error')} required">
    <label for="totalPagado">
        <g:message code="liquidacionDeZincPlata.totalPagado.label" default="Total Pagado" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalPagado" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'totalPagado')}" required="" inputmode="decimal"/>
</div>

<g:hiddenField name="cantidadAnticiposPorPagar" />

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'anticipoPorPagar', 'error')} required">
    <label for="anticipoPorPagar">
        <g:message code="liquidacionDeZincPlata.anticipoPorPagar.label" default="Anticipo Por Pagar" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="anticipoPorPagar" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'anticipoPorPagar')}" required="" class="rojo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'totalAnticiposContraEntrega', 'error')} required">
    <label for="totalAnticiposContraEntrega">
        <g:message code="liquidacionDeZincPlata.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalAnticiposContraEntrega" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'totalAnticiposContraEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'totalAnticiposContraFuturaEntrega', 'error')} required">
    <label for="totalAnticiposContraFuturaEntrega">
        <g:message code="liquidacionDeZincPlata.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="adelantoPorLiquidacionProvisional" value="0"/>
    <g:field name="totalAnticiposContraFuturaEntrega" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'totalAnticiposContraFuturaEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'totalLiquidoPagable', 'error')} required">
    <label for="totalLiquidoPagable">
        <g:message code="liquidacionDeZincPlata.totalLiquidoPagable.label" default="Total Liquido Pagable" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalLiquidoPagable" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'totalLiquidoPagable')}" required="" class="verde" inputmode="decimal"/>
    <g:hiddenField name="totalLiquidoPagableOriginal" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'totalLiquidoPagableOriginal')}"/>
    <g:hiddenField name="diferenciaLiquidoPagable" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'diferenciaLiquidoPagable')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="liquidacionDeZincPlata.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${liquidacionDeZincPlataInstance?.observaciones}" size="90"/>
</div>

<g:hiddenField name="detalleLaboratorio1" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'detalleLaboratorio1')}"/>
<g:hiddenField name="costoLaboratorio1" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'costoLaboratorio1')}"/>
<g:hiddenField name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'totalCostoLaboratorio')}"/>

<div id="_modificacion" style="display: none">
    <h1 style="font-weight: bold; font-size: 12px">Informacion de Modificacion del Registro</h1>

    <div class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'motivoDeModificacion', 'error')} ">
        <label for="motivoDeModificacion">
            <g:message code="liquidacionDeZincPlata.motivoDeModificacion.label" default="Motivo De Modificacion" />

        </label>
        <g:textField name="motivoDeModificacion" value="${liquidacionDeZincPlataInstance?.motivoDeModificacion}" size="90"/>
    </div>
</div>

<div style="display:none;" class="nav_up" id="nav_up"></div>
<div style="display:none;" class="nav_down" id="nav_down"></div>