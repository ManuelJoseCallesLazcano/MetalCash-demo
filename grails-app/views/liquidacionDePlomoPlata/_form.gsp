<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo" %>

<g:hiddenField name="vista" value="" />

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'lote', 'error')} required"
     xmlns="http://www.w3.org/1999/html">
    <label for="lote">
        <g:message code="liquidacionDePlomoPlata.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${liquidacionDePlomoPlataInstance?.lote}"/>
    <g:hiddenField name="recepcionDeComplejo.id" value="${liquidacionDePlomoPlataInstance?.recepcionDeComplejo?.id}"/>
    <g:hiddenField name="empresa.id" value="${liquidacionDePlomoPlataInstance?.empresa?.id}"/>
    <g:hiddenField name="deposito.id" value="${liquidacionDePlomoPlataInstance?.deposito?.id}"/>
    <g:hiddenField name="conjuntoPlomoPlata" value="${liquidacionDePlomoPlataInstance?.conjuntoPlomoPlata}"/>
</div>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'nombreDeposito', 'error')} required">
    <label for="nombreDeposito">
        <g:message code="liquidacionDePlomoPlata.nombreDeposito.label" default="Nombre Deposito" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreDeposito" required="" value="${liquidacionDePlomoPlataInstance?.nombreDeposito}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="liquidacionDePlomoPlata.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${liquidacionDePlomoPlataInstance?.nombreCliente}"  class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="liquidacionDePlomoPlata.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="empresaId" value="${liquidacionDePlomoPlataInstance?.recepcionDeComplejo?.empresa?.id}"/>
    <g:textField name="nombreEmpresa" required="" value="${liquidacionDePlomoPlataInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="liquidacionDePlomoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${liquidacionDePlomoPlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'cantidadDeSacos', 'error')} required">
    <label for="cantidadDeSacos">
        <g:message code="liquidacionDePlomoPlata.cantidadDeSacos.label" default="Cantidad De Sacos" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${liquidacionDePlomoPlataInstance?.cantidadDeSacos}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="liquidacionDePlomoPlata.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'tipoDeMineral', 'error')} required">
    <label for="tipoDeMineral">
        <g:message code="liquidacionDePlomoPlata.tipoDeMineral.label" default="Tipo de Mineral" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="tipoDeMineral" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'tipoDeMineral')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'naturalezaMineral', 'error')} ">
    <label for="naturalezaMineral">
        <g:message code="liquidacionDePlomoPlata.naturalezaMineral.label" default="Naturaleza Mineral" />

    </label>
    <g:textField name="naturalezaMineral" value="${liquidacionDePlomoPlataInstance?.naturalezaMineral}" class="amarillo" readonly="true"/>
    <g:hiddenField name="estadoDelLote" value="${liquidacionDePlomoPlataInstance?.estadoDelLote}" />
</div>

%{--<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'estadoDelLote', 'error')} ">--}%
    %{--<label for="estadoDelLote">--}%
        %{--<g:message code="liquidacionDePlomoPlata.estadoDelLote.label" default="Estado Del Lote" />--}%

    %{--</label>--}%
    %{--<g:textField name="estadoDelLote" value="${liquidacionDePlomoPlataInstance?.estadoDelLote}" class="amarillo" readonly="true"/>--}%
%{--</div>--}%


<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'cotizacionDiariaDePlomo', 'error')} required">
            <label for="cotizacionDiariaDePlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.cotizacionDiariaDePlomo.label" default="Cot. Dia Plomo" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDePlomo" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'cotizacionDiariaDePlomo')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'cotizacionQuincenalDePlomo', 'error')} required">
            <label for="cotizacionQuincenalDePlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.cotizacionQuincenalDePlomo.label" default="Cot. Quinc. Plomo" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDePlomo" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'cotizacionQuincenalDePlomo')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'alicuotaDePlomo', 'error')} required">
            <label for="alicuotaDePlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.alicuotaDePlomo.label" default="Alicuota Plomo" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDePlomo" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'alicuotaDePlomo')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'cotizacionDiariaDePlata', 'error')} required">
            <label for="cotizacionDiariaDePlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.cotizacionDiariaDePlata.label" default="Cot. Dia Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDePlata" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'cotizacionDiariaDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'cotizacionQuincenalDePlata', 'error')} required">
            <label for="cotizacionQuincenalDePlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.cotizacionQuincenalDePlata.label" default="Cot. Quinc. Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDePlata" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'cotizacionQuincenalDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'alicuotaDePlata', 'error')} required">
            <label for="alicuotaDePlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.alicuotaDePlata.label" default="Alicuota Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDePlata" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'alicuotaDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioOficial" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'tipoDeCambioOficial')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioComercial" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'tipoDeCambioComercial')}" required="" size="10" class="amarillo" readonly="true"/>
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
                <g:message code="liquidacionDePlomoPlata.porcentajeMermaPromexbol.label" default="Merma" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
            <g:field name="porcentajeMermaPromexbol" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeMermaPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeMermaCliente', 'error')} required">
            <g:field name="porcentajeMermaCliente" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeMermaCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeMermaFinal', 'error')} required">
            <g:field name="porcentajeMermaFinal" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeMermaFinal')}" required="" readonly="true"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeZincCliente">
                <g:message code="liquidacionDePlomoPlata.porcentajeZincCliente.label" default="Plomo" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlomoPromexbol', 'error')} required">
            <g:field name="porcentajePlomoPromexbol" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlomoPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlomoCliente', 'error')} required">
            <g:field name="porcentajePlomoCliente" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlomoCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlomoFinal', 'error')} required">
            <g:field name="porcentajePlomoFinal" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlomoFinal')}" required=""  inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeZincFinal">
                <g:message code="liquidacionDePlomoPlata.porcentajeZincFinal.label" default="Plata" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlataPromexbol', 'error')} required">
            <g:field name="porcentajePlataPromexbol" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlataPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlataCliente', 'error')} required">
            <g:field name="porcentajePlataCliente" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlataCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlataFinal', 'error')} required">
            <g:field name="porcentajePlataFinal" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlataFinal')}" required=""  inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeHumedadPromexbol">
                <g:message code="liquidacionDePlomoPlata.porcentajeHumedadPromexbol.label" default="Humedad" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
            <g:field name="porcentajeHumedadPromexbol" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeHumedadPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeHumedadCliente', 'error')} required">
            <g:field name="porcentajeHumedadCliente" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeHumedadCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeHumedadFinal', 'error')} required">
            <g:field name="porcentajeHumedadFinal" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeHumedadFinal')}" required=""  inputmode="decimal"/>
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

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'modoValoracion', 'error')} ">
    <label for="modoValoracion">
        <g:message code="liquidacionDePlomoPlata.modoValoracion.label" default="Modo Valoracion" />

    </label>
    <g:hiddenField name="tablasIds" value="" />
    <g:hiddenField name="preciosPorLmeIds" value="" />
    <g:hiddenField name="terminosIds" value="" />
    <g:select name="modoValoracion" from="${['TABLA','PRECIO POR LME','TERMINOS DE CONTRATO']}" value="${liquidacionDePlomoPlataInstance?.modoValoracion}" valueMessagePrefix="liquidacionDePlomoPlata.modoValoracion"/>
</div>

<div id="_tablaComplejo" class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'tablaComplejo', 'error')} required">
    <label for="tablaComplejo">
        <g:message code="liquidacionDePlomoPlata.tablaComplejo.label" default="Tabla Complejo" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="tablaComplejo" name="tablaComplejo.id" from="${org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo.list()}" optionKey="id" required="" value="${liquidacionDePlomoPlataInstance?.tablaComplejo?.id}" class="many-to-one"/>
</div>

<div id="_tablaPrecioPorLme" class="fieldcontain ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'tablaPrecioPorLme', 'error')} required">
    <label for="tablaPrecioPorLme">
        <g:message code="liquidacionDeComplejo.tablaPrecioPorLme.label" default="Tabla Precio LME" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="tablaPrecioPorLme" name="tablaPrecioPorLme.id" from="${org.socymet.cotizaciones.TablaPrecioPorLme.list()}" optionKey="id" required="" value="${liquidacionDePlomoPlataInstance?.tablaPrecioPorLme?.id}" class="many-to-one"/>
</div>

<div id="_terminosDeContrato" class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'terminosDeContrato', 'error')} required">
    <label for="terminosDeContrato">
        <g:message code="liquidacionDePlomoPlata.terminosDeContrato.label" default="Terminos De Contrato" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="terminosDeContrato" name="terminosDeContrato.id" from="${org.socymet.cotizaciones.TerminosDeContrato.list()}" optionKey="id" required="" value="${liquidacionDePlomoPlataInstance?.terminosDeContrato?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'margen', 'error')} ">
    <label for="margen">
        <g:message code="liquidacionDePlomoPlata.margen.label" default="Margen" />

    </label>
    <g:textField name="margen" inputmode="decimal" value="${liquidacionDePlomoPlataInstance?.margen}" />
</div>

<br>

<div style="text-align: center;">
    <input id="valorar" type="button" value="VALORAR LOTE" style="background-color: #255b17; color: white; font-size: 16px;"/>
</div>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'fechaDeLiquidacion', 'error')} required">
    <label for="fechaDeLiquidacion">
        <g:message code="liquidacionDePlomoPlata.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeLiquidacion" precision="day"  value="${liquidacionDePlomoPlataInstance?.fechaDeLiquidacion}"  />
</div>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'kilosNetosHumedos', 'error')} required">
            <label for="kilosNetosHumedos" style="width: 30%">
                <g:message code="liquidacionDePlomoPlata.kilosNetosHumedos.label" default="K. N. H." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosHumedos" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'kilosNetosHumedos')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 30%">
                <g:message code="liquidacionDePlomoPlata.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosNetosSecos" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'kilosNetosSecos')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlata', 'error')} required">
        </td>
    </tr>
    </tbody>
</table>

<br/>

<g:hiddenField name="dolarPuntoZinc" value="0" />
<g:hiddenField name="dolarPuntoPlomo" value="0" />
<g:hiddenField name="dolarPuntoPlata" value="0" />

<g:hiddenField name="merma" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'merma')}" />
<g:hiddenField name="humedad" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'humedad')}" />
<g:hiddenField name="porcentajePlomo" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlomo')}" />
<g:hiddenField name="porcentajePlata" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlata')}" />

<table class="center" border="0" style="width: 80%;">
    <thead>
    <tr>
        <th style="text-align: center">PLOMO</th>
        <th style="text-align: center">PLATA</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'kilosFinosPlomo', 'error')} required">
            <label for="kilosFinosPlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.kilosFinosPlomo.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosPlomo" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'kilosFinosPlomo')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'kilosFinosPlata', 'error')} required">
            <label for="kilosFinosPlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.kilosFinosPlata.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosPlata" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'kilosFinosPlata')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'librasFinasDePlomo', 'error')} required">
            <label for="librasFinasDePlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.librasFinasDePlomo.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="librasFinasDePlomo" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'librasFinasDePlomo')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'onzasTroyDePlata', 'error')} required">
            <label for="onzasTroyDePlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.onzasTroyDePlata.label" default="Onzas Troy" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="onzasTroyDePlata" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'onzasTroyDePlata')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBrutoDePlomo', 'error')} required">
            <label for="valorOficialBrutoDePlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.valorOficialBrutoDePlomo.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDePlomo" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBrutoDePlomo')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBrutoDePlata', 'error')} required">
            <label for="valorOficialBrutoDePlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.valorOficialBrutoDePlata.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDePlata" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBrutoDePlata')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBrutoDePlomoEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDePlomoEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.valorOficialBrutoDePlomoEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDePlomoEnBolivianos" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBrutoDePlomoEnBolivianos')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBrutoDePlataEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDePlataEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.valorOficialBrutoDePlataEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDePlataEnBolivianos" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBrutoDePlataEnBolivianos')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBruto', 'error')} required">
    <label for="valorOficialBruto">
        <g:message code="liquidacionDePlomoPlata.valorOficialBruto.label" default="Valor Oficial Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorOficialBruto" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorPorTonelada', 'error')} required">
    <label for="valorPorTonelada">
        <g:message code="liquidacionDePlomoPlata.valorPorTonelada.label" default="Valor Por Tonelada" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorPorTonelada" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'valorPorTonelada')}" required="" class="verde" inputmode="decimal"/>
</div>

<g:hiddenField name="porcentajeRegalia" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'porcentajeRegalia')}"/>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'regaliaMinera', 'error')} required">
    <label for="regaliaMinera">
        <g:message code="liquidacionDePlomoPlata.regaliaMinera.label" default="Regalia Minera" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="regaliaMinera" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'regaliaMinera')}" required="" inputmode="decimal"/>
</div>

<g:hiddenField name="retenciones" value="${liquidacionDePlomoPlataInstance?.retenciones}" />

<h1 style="font-weight: bold">Retenciones</h1>

<div style="width: 700px; margin-left: auto; margin-right: auto;">
    <table id="tablaRetenciones">
    </table>
</div>

<div id="_botones" style="text-align: center">
    <br>
    <button id="eliminarRetencion" type="button">ELIMINAR RETENCION SELECCIONADA</button>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorNetoMineral', 'error')} required">
    <label for="valorNetoMineral">
        <g:message code="liquidacionDePlomoPlata.valorNetoMineral.label" default="Valor Neto Mineral" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineral" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'valorNetoMineral')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorNetoMineralEnBolivianos', 'error')} required">
    <label for="valorNetoMineralEnBolivianos">
        <g:message code="liquidacionDePlomoPlata.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineralEnBolivianos" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'valorNetoMineralEnBolivianos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'bonoCalidad', 'error')} required">
    <label for="bonoCalidad">
        <g:message code="liquidacionDePlomoPlata.bonoCalidad.label" default="Bono Calidad" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoCalidad" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'bonoCalidad')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'bonoIncentivo', 'error')} required">
    <label for="bonoIncentivo">
        <g:message code="liquidacionDePlomoPlata.bonoIncentivo.label" default="Bono Incentivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoIncentivo" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'bonoIncentivo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorDeCompra', 'error')} required">
    <label for="valorDeCompra">
        <g:message code="liquidacionDePlomoPlata.valorDeCompra.label" default="Valor De Compra" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorDeCompra" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'valorDeCompra')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'totalRetenciones', 'error')} required">
    <label for="totalRetenciones">
        <g:message code="liquidacionDePlomoPlata.totalRetenciones.label" default="Total Retenciones" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalRetenciones" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'totalRetenciones')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'totalPagado', 'error')} required">
    <label for="totalPagado">
        <g:message code="liquidacionDePlomoPlata.totalPagado.label" default="Total Pagado" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalPagado" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'totalPagado')}" required="" inputmode="decimal"/>
</div>

<g:hiddenField name="cantidadAnticiposPorPagar" />

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'anticipoPorPagar', 'error')} required">
    <label for="anticipoPorPagar">
        <g:message code="liquidacionDePlomoPlata.anticipoPorPagar.label" default="Anticipo Por Pagar" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="anticipoPorPagar" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'anticipoPorPagar')}" required="" class="rojo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'totalAnticiposContraEntrega', 'error')} required">
    <label for="totalAnticiposContraEntrega">
        <g:message code="liquidacionDePlomoPlata.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalAnticiposContraEntrega" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'totalAnticiposContraEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'totalAnticiposContraFuturaEntrega', 'error')} required">
    <label for="totalAnticiposContraFuturaEntrega">
        <g:message code="liquidacionDePlomoPlata.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="adelantoPorLiquidacionProvisional" value="0"/>
    <g:field name="totalAnticiposContraFuturaEntrega" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'totalAnticiposContraFuturaEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'totalLiquidoPagable', 'error')} required">
    <label for="totalLiquidoPagable">
        <g:message code="liquidacionDePlomoPlata.totalLiquidoPagable.label" default="Total Liquido Pagable" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalLiquidoPagable" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'totalLiquidoPagable')}" required="" class="verde" inputmode="decimal"/>
    <g:hiddenField name="totalLiquidoPagableOriginal" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'totalLiquidoPagableOriginal')}"/>
    <g:hiddenField name="diferenciaLiquidoPagable" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'diferenciaLiquidoPagable')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="liquidacionDePlomoPlata.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${liquidacionDePlomoPlataInstance?.observaciones}" size="90"/>
</div>

<g:hiddenField name="detalleLaboratorio1" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'detalleLaboratorio1')}"/>
<g:hiddenField name="costoLaboratorio1" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'costoLaboratorio1')}"/>
<g:hiddenField name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'totalCostoLaboratorio')}"/>

<div id="_modificacion" style="display: none">
    <h1 style="font-weight: bold; font-size: 12px">Informacion de Modificacion del Registro</h1>

    <div class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'motivoDeModificacion', 'error')} ">
        <label for="motivoDeModificacion">
            <g:message code="liquidacionDePlomoPlata.motivoDeModificacion.label" default="Motivo De Modificacion" />

        </label>
        <g:textField name="motivoDeModificacion" value="${liquidacionDePlomoPlataInstance?.motivoDeModificacion}" size="90"/>
    </div>
</div>

<div style="display:none;" class="nav_up" id="nav_up"></div>
<div style="display:none;" class="nav_down" id="nav_down"></div>