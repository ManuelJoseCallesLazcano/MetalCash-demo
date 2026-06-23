<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo" %>



<g:hiddenField name="vista" value="" />

<g:if test="${liquidacionDeComplejoInstance?.id}">
    <div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'recepcionDeComplejo', 'error')} required">
        <label for="recepcionDeComplejo">
            <g:message code="liquidacionDeComplejo.recepcionDeComplejo.label" default="Recepcion De Complejo" />
            <span class="required-indicator">*</span>
        </label>
        <g:link controller="recepcionDeComplejo" action="show" id="${liquidacionDeComplejoInstance?.recepcionDeComplejo?.id}">${liquidacionDeComplejoInstance?.recepcionDeComplejo?.encodeAsHTML()}</g:link>
        <g:hiddenField id="recepcionDeComplejo" name="recepcionDeComplejo.id" value="${liquidacionDeComplejoInstance?.recepcionDeComplejo?.id}"/>
    </div>
</g:if>
<g:else>
    <div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'recepcionDeComplejo', 'error')} required">
        <label for="recepcionDeComplejo">
            <g:message code="liquidacionDeComplejo.recepcionDeComplejo.label" default="Recepcion De Complejo" />
            <span class="required-indicator">*</span>
        </label>
%{--        <g:select id="recepcionDeComplejo" name="recepcionDeComplejo.id" from="${org.socymet.recepcion.RecepcionDeComplejo.findAllByEstadoAnalisisAndEstadoDelLote("CON ANALISIS","NO LIQUIDADO")}" optionKey="id" required="" value="${liquidacionDeComplejoInstance?.recepcionDeComplejo?.id}" class="many-to-one, chosen-select"  style="width: 350px"/>--}%
        <g:select id="recepcionDeComplejo" name="recepcionDeComplejo.id" from="${org.socymet.recepcion.RecepcionDeComplejo.findAllByEstadoAnalisisAndEstadoDelLote("CON ANALISIS","NO LIQUIDADO", [sort: 'id', order: 'desc'])}" optionKey="id" required="" value="${liquidacionDeComplejoInstance?.recepcionDeComplejo?.id}" class="many-to-one, chosen-select"  style="width: 350px"/>

    </div>
</g:else>

<g:hiddenField name="lote" value="${liquidacionDeComplejoInstance?.lote}"/>
<g:hiddenField name="empresa.id" value="${liquidacionDeComplejoInstance?.empresa?.id}"/>
<g:hiddenField name="deposito.id" value="${liquidacionDeComplejoInstance?.deposito?.id}"/>
<g:hiddenField name="conjuntoComplejo" value="${liquidacionDeComplejoInstance?.conjuntoComplejo}"/>

%{--<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'lote', 'error')} required"--}%
%{--     xmlns="http://www.w3.org/1999/html">--}%
%{--    <label for="lote">--}%
%{--        <g:message code="liquidacionDeComplejo.lote.label" default="Lote" />--}%
%{--        <span class="required-indicator">*</span>--}%
%{--    </label>--}%
%{--    <g:textField name="lote" required="" value="${liquidacionDeComplejoInstance?.lote}"/>--}%
%{--    <g:hiddenField name="recepcionDeComplejo.id" value="${liquidacionDeComplejoInstance?.recepcionDeComplejo?.id}"/>--}%
%{--    --}%
%{--</div>--}%

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'fechaDeLiquidacion', 'error')} required">
    <label for="fechaDeLiquidacion">
        <g:message code="liquidacionDeComplejo.fechaDeLiquidacion.label" default="Fecha De Liquidacion" />
        <span class="required-indicator">*</span>
    </label>
    <g:datepickerUI name="fechaDeLiquidacion" value="${liquidacionDeComplejoInstance?.fechaDeLiquidacion ?: new Date()}"/>
</div>


<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<table>
    <tbody>
        <tr>
            <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'nombreCliente', 'error')} required">
                <label for="nombreCliente" style="width: 40%">
                    <g:message code="liquidacionDeComplejo.nombreCliente.label" default="Nombre Cliente" />
                    <span class="required-indicator">*</span>
                </label>
                <g:textField name="nombreCliente" required="" value="${liquidacionDeComplejoInstance?.nombreCliente}"  class="amarillo" size="30" readonly="true"/>
            </td>

            <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'nombreEmpresa', 'error')} required">
                <label for="nombreEmpresa" style="width: 40%">
                    <g:message code="liquidacionDeComplejo.nombreEmpresa.label" default="Nombre Empresa" />
                    <span class="required-indicator">*</span>
                </label>
                <g:hiddenField name="empresaId" value="${liquidacionDeComplejoInstance?.recepcionDeComplejo?.empresa?.id}"/>
                <g:textField name="nombreEmpresa" required="" value="${liquidacionDeComplejoInstance?.nombreEmpresa}" class="amarillo" size="30" readonly="true"/>
            </td>
        </tr>
        <tr style="display: none">
            <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'nombreDeposito', 'error')} required">
                <label for="nombreDeposito" style="width: 40%">
                    <g:message code="liquidacionDeComplejo.nombreDeposito.label" default="Nombre Deposito" />
                    <span class="required-indicator">*</span>
                </label>
                <g:textField name="nombreDeposito" required="" value="${liquidacionDeComplejoInstance?.nombreDeposito}" class="amarillo" readonly="true"/>
            </td>

            <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'cantidadDeSacos', 'error')} required">
                <label for="cantidadDeSacos" style="width: 40%">
                    <g:message code="liquidacionDeComplejo.cantidadDeSacos.label" default="Cantidad De Sacos" />
                    <span class="required-indicator">*</span>
                </label>
                <g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${liquidacionDeComplejoInstance?.cantidadDeSacos}"  class="amarillo" readonly="true"/>
            </td>
        </tr>
        <tr>
            <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'fechaDeRecepcion', 'error')} required">
                <label for="fechaDeRecepcion" style="width: 40%">
                    <g:message code="liquidacionDeComplejo.fechaDeRecepcion.label" default="Fecha De Recepcion" />
                    <span class="required-indicator">*</span>
                </label>
                <g:textField name="fechaDeRecepcion" required="" value="${liquidacionDeComplejoInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
            </td>

            <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'pesoBruto', 'error')} required">
                <label for="pesoBruto" style="width: 40%">
                    <g:message code="liquidacionDeComplejo.pesoBruto.label" default="Peso Bruto" />
                    <span class="required-indicator">*</span>
                </label>
                <g:field name="pesoBruto" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
            </td>
        </tr>
        <tr style="display: none">
            <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'tipoDeMineral', 'error')} required">
                <label for="tipoDeMineral" style="width: 40%">
                    <g:message code="liquidacionDeComplejo.tipoDeMineral.label" default="Tipo de Mineral" />
                    <span class="required-indicator">*</span>
                </label>
                <g:field name="tipoDeMineral" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'tipoDeMineral')}" required="" class="amarillo" readonly="true"/>
            </td>

            <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'naturalezaMineral', 'error')} ">
                <label for="naturalezaMineral" style="width: 40%">
                    <g:message code="liquidacionDeComplejo.naturalezaMineral.label" default="Naturaleza Mineral" />

                </label>
                <g:textField name="naturalezaMineral" value="${liquidacionDeComplejoInstance?.naturalezaMineral}" class="amarillo" readonly="true"/>
                <g:hiddenField name="estadoDelLote" value="${liquidacionDeComplejoInstance?.estadoDelLote}" />
            </td>

            %{--<td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'estadoDelLote', 'error')} ">--}%
                %{--<label for="estadoDelLote" style="width: 40%">--}%
                    %{--<g:message code="liquidacionDeComplejo.estadoDelLote.label" default="Estado Del Lote" />--}%

                %{--</label>--}%
                %{--<g:textField name="estadoDelLote" value="${liquidacionDeComplejoInstance?.estadoDelLote}" class="amarillo" readonly="true"/>--}%
            %{--</td>--}%
        </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'cotizacionDiariaDeZinc', 'error')} required">
            <label for="cotizacionDiariaDeZinc" style="width: 50%">
                <g:message code="liquidacionDeComplejo.cotizacionDiariaDeZinc.label" default="Cot. Dia Zinc" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDeZinc" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'cotizacionDiariaDeZinc')}" required="" size="10" class="amarillo" readonly="true"/></td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'cotizacionQuincenalDeZinc', 'error')} required">
            <label for="cotizacionQuincenalDeZinc" style="width: 50%">
                <g:message code="liquidacionDeComplejo.cotizacionQuincenalDeZinc.label" default="Cot. Quinc. Zinc" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDeZinc" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'cotizacionQuincenalDeZinc')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'alicuotaDeZinc', 'error')} required">
            <label for="alicuotaDeZinc" style="width: 50%">
                <g:message code="liquidacionDeComplejo.alicuotaDeZinc.label" default="Alicuota Zinc" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDeZinc" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'alicuotaDeZinc')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'cotizacionDiariaDePlomo', 'error')} required">
            <label for="cotizacionDiariaDePlomo" style="width: 50%">
                <g:message code="liquidacionDeComplejo.cotizacionDiariaDePlomo.label" default="Cot. Dia Plomo" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDePlomo" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'cotizacionDiariaDePlomo')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'cotizacionQuincenalDePlomo', 'error')} required">
            <label for="cotizacionQuincenalDePlomo" style="width: 50%">
                <g:message code="liquidacionDeComplejo.cotizacionQuincenalDePlomo.label" default="Cot. Quinc. Plomo" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDePlomo" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'cotizacionQuincenalDePlomo')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'alicuotaDePlomo', 'error')} required">
            <label for="alicuotaDePlomo" style="width: 50%">
                <g:message code="liquidacionDeComplejo.alicuotaDePlomo.label" default="Alicuota Plomo" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDePlomo" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'alicuotaDePlomo')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'cotizacionDiariaDePlata', 'error')} required">
            <label for="cotizacionDiariaDePlata" style="width: 50%">
                <g:message code="liquidacionDeComplejo.cotizacionDiariaDePlata.label" default="Cot. Dia Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionDiariaDePlata" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'cotizacionDiariaDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'cotizacionQuincenalDePlata', 'error')} required">
            <label for="cotizacionQuincenalDePlata" style="width: 50%">
                <g:message code="liquidacionDeComplejo.cotizacionQuincenalDePlata.label" default="Cot. Quinc. Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="cotizacionQuincenalDePlata" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'cotizacionQuincenalDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'alicuotaDePlata', 'error')} required">
            <label for="alicuotaDePlata" style="width: 50%">
                <g:message code="liquidacionDeComplejo.alicuotaDePlata.label" default="Alicuota Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="alicuotaDePlata" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'alicuotaDePlata')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="form-group ${hasErrors( bean: liquidacionDeComplejoInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDeComplejo.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioOficial" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'tipoDeCambioOficial')}" required="" size="10" class="amarillo" readonly="true"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 50%">
                <g:message code="liquidacionDeComplejo.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="tipoDeCambioComercial" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'tipoDeCambioComercial')}" required="" size="10" class="amarillo" readonly="true"/>
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
        <td class="form-group required">
            <label for="porcentajeMermaPromexbol" style="width: 80%">
                <g:message code="controlCalidadComplejo.porcentajeMermaPromexbol.label" default="Merma" />
            </label>
        </td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
            <g:field name="porcentajeMermaPromexbol" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajeMermaPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajeMermaCliente', 'error')} required">
            <g:field name="porcentajeMermaCliente" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajeMermaCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajeMermaFinal', 'error')} required">
            <g:field name="porcentajeMermaFinal" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajeMermaFinal')}" required="" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="form-group required">
            <label for="porcentajeZincPromexbol" style="width: 80%">
                <g:message code="controlCalidadComplejo.porcentajeZincPromexbol.label" default="Zinc" />
            </label>
        </td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajeZincPromexbol', 'error')} required">
            <g:field name="porcentajeZincPromexbol" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajeZincPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajeZincCliente', 'error')} required">
            <g:field name="porcentajeZincCliente" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajeZincCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajeZincFinal', 'error')} required">
            <g:field name="porcentajeZincFinal" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajeZincFinal')}" required=""  inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="form-group required">
            <label for="porcentajeZincCliente" style="width: 80%">
                <g:message code="controlCalidadComplejo.porcentajeZincCliente.label" default="Plomo" />
            </label>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajePlomoPromexbol', 'error')} required">
            <g:field name="porcentajePlomoPromexbol" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajePlomoPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajePlomoCliente', 'error')} required">
            <g:field name="porcentajePlomoCliente" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajePlomoCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajePlomoFinal', 'error')} required">
            <g:field name="porcentajePlomoFinal" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajePlomoFinal')}" required=""  inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="form-group required">
            <label for="porcentajeZincFinal" style="width: 80%">
                <g:message code="controlCalidadComplejo.porcentajeZincFinal.label" default="Plata" />
            </label>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajePlataPromexbol', 'error')} required">
            <g:field name="porcentajePlataPromexbol" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajePlataPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajePlataCliente', 'error')} required">
            <g:field name="porcentajePlataCliente" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajePlataCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajePlataFinal', 'error')} required">
            <g:field name="porcentajePlataFinal" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajePlataFinal')}" required=""  inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="form-group required">
            <label for="porcentajeHumedadPromexbol" style="width: 80%">
                <g:message code="controlCalidadComplejo.porcentajeHumedadPromexbol.label" default="Humedad" />
            </label>
        </td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
            <g:field name="porcentajeHumedadPromexbol" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajeHumedadPromexbol')}" required="" inputmode="decimal"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajeHumedadCliente', 'error')} required">
            <g:field name="porcentajeHumedadCliente" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajeHumedadCliente')}" required="" inputmode="decimal"/>
        </td>

        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'porcentajeHumedadFinal', 'error')} required">
            <g:field name="porcentajeHumedadFinal" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajeHumedadFinal')}" required=""  inputmode="decimal"/>
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

<h1 style="font-weight: bold">Valoraci&oacute;n del Lote</h1>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'modoValoracion', 'error')} ">
    <label for="modoValoracion">
        <g:message code="liquidacionDeComplejo.modoValoracion.label" default="Modo Valoracion" />

    </label>
    <g:hiddenField name="tablasIds" value="" />
    <g:hiddenField name="preciosPorLmeIds" value="" />
    <g:hiddenField name="terminosIds" value="" />
    <g:select name="modoValoracion" from="${['TABLA','TERMINOS DE CONTRATO']}" value="${liquidacionDeComplejoInstance?.modoValoracion}" valueMessagePrefix="liquidacionDeComplejo.modoValoracion"/>
</div>

<div id="_tablaComplejo" class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'tablaComplejo', 'error')} required">
    <label for="tablaComplejo">
        <g:message code="liquidacionDeComplejo.tablaComplejo.label" default="Tabla Complejo" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="tablaComplejo" name="tablaComplejo.id" from="${org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo.list()}" optionKey="id" required="" value="${liquidacionDeComplejoInstance?.tablaComplejo?.id}" class="many-to-one"/>
</div>

<div id="_tablaPrecioPorLme" class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'tablaPrecioPorLme', 'error')} required">
    <label for="tablaPrecioPorLme">
        <g:message code="liquidacionDeComplejo.tablaPrecioPorLme.label" default="Tabla Precio LME" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="tablaPrecioPorLme" name="tablaPrecioPorLme.id" from="${org.socymet.cotizaciones.TablaPrecioPorLme.list()}" optionKey="id" required="" value="${liquidacionDeComplejoInstance?.tablaPrecioPorLme?.id}" class="many-to-one"/>
</div>

<div id="_terminosDeContrato" class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'terminosDeContrato', 'error')} required">
    <label for="terminosDeContrato">
        <g:message code="liquidacionDeComplejo.terminosDeContrato.label" default="Terminos De Contrato" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="terminosDeContrato" name="terminosDeContrato.id" from="${org.socymet.cotizaciones.TerminosDeContrato.list()}" optionKey="id" required="" value="${liquidacionDeComplejoInstance?.terminosDeContrato?.id}" class="many-to-one"/>
</div>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'margen', 'error')} ">
    <label for="margen">
        <g:message code="liquidacionDeComplejo.margen.label" default="Margen" />

    </label>
    <g:textField name="margen" inputmode="decimal" value="${liquidacionDeComplejoInstance?.margen}" />
</div>

<br>

<div style="text-align: center;">
    <input id="valorar" type="button" value="VALORAR LOTE" style="background-color: #255b17; color: white; font-size: 16px;"/>
</div>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'valorOficialBruto', 'error')} required">
    <label for="valorOficialBruto">
        <g:message code="liquidacionDeComplejo.valorOficialBruto.label" default="Valor Oficial Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorOficialBruto" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'valorOficialBruto')}" required="" inputmode="decimal"/>

    <label for="regaliaMinera">
        <g:message code="liquidacionDeComplejo.regaliaMinera.label" default="Regalia Minera" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="regaliaMinera" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'regaliaMinera')}" required="" inputmode="decimal"/>
</div>

%{--<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'regaliaMinera', 'error')} required">--}%
    %{--<label for="regaliaMinera">--}%
        %{--<g:message code="liquidacionDeComplejo.regaliaMinera.label" default="Regalia Minera" />--}%
        %{--<span class="required-indicator">*</span>--}%
    %{--</label>--}%
    %{--<g:field name="regaliaMinera" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'regaliaMinera')}" required="" inputmode="decimal"/>--}%
%{--</div>--}%

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'valorPorTonelada', 'error')} required">
    <label for="valorPorTonelada">
        <g:message code="liquidacionDeComplejo.valorPorTonelada.label" default="Valor Por Tonelada" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorPorTonelada" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'valorPorTonelada')}" required="" class="verde" style="font-size: 16px; font-weight: bold; width: 130px" inputmode="decimal"/>
</div>

<g:hiddenField name="porcentajeRegalia" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'porcentajeRegalia')}"/>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'valorNetoMineral', 'error')} required">
    <label for="valorNetoMineral">
        <g:message code="liquidacionDeComplejo.valorNetoMineral.label" default="Valor Neto Mineral" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineral" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'valorNetoMineral')}" required="" inputmode="decimal"/>

    <label for="valorNetoMineralEnBolivianos">
        <g:message code="liquidacionDeComplejo.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorNetoMineralEnBolivianos" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'valorNetoMineralEnBolivianos')}" required="" inputmode="decimal"/>
</div>

%{--<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'valorNetoMineralEnBolivianos', 'error')} required">--}%
    %{--<label for="valorNetoMineralEnBolivianos">--}%
        %{--<g:message code="liquidacionDeComplejo.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral En Bolivianos" />--}%
        %{--<span class="required-indicator">*</span>--}%
    %{--</label>--}%
    %{--<g:field name="valorNetoMineralEnBolivianos" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'valorNetoMineralEnBolivianos')}" required="" inputmode="decimal"/>--}%
%{--</div>--}%

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'bonoCalidad', 'error')} required" style="display: none">
    <label for="bonoCalidad">
        <g:message code="liquidacionDeComplejo.bonoCalidad.label" default="Bono Calidad" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoCalidad" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'bonoCalidad')}" required="" inputmode="decimal"/>
</div>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'bonoIncentivo', 'error')} required" style="display: none">
    <label for="bonoIncentivo">
        <g:message code="liquidacionDeComplejo.bonoIncentivo.label" default="Bono Incentivo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoIncentivo" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'bonoIncentivo')}" required="" inputmode="decimal"/>
</div>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'valorDeCompra', 'error')} required" style="display: none">
    <label for="valorDeCompra">
        <g:message code="liquidacionDeComplejo.valorDeCompra.label" default="Valor De Compra" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorDeCompra" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'valorDeCompra')}" required="" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Pesos y Valores Brutos parciales</h1>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'kilosNetosHumedos', 'error')} required" style="display: none;">
    <label for="kilosNetosHumedos">
        <g:message code="liquidacionDeComplejo.kilosNetosHumedos.label" default="K. N. H." />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="kilosNetosHumedos" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'kilosNetosHumedos')}" required="" size="10" inputmode="decimal"/>
</div>
<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'kilosNetosSecos', 'error')} required">
    <label for="kilosNetosSecos">
        <g:message code="liquidacionDeComplejo.kilosNetosSecos.label" default="K. N. S." />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="kilosNetosSecos" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'kilosNetosSecos')}" required="" size="10" inputmode="decimal"/>
</div>

<g:hiddenField name="dolarPuntoZinc" value="0" />
<g:hiddenField name="dolarPuntoPlomo" value="0" />
<g:hiddenField name="dolarPuntoPlata" value="0" />

<table class="center" border="0" style="width: 80%;">
    <thead>
    <tr>
        <th style="text-align: center">ZINC</th>
        <th style="text-align: center">PLOMO</th>
        <th style="text-align: center">PLATA</th>
    </tr>
    </thead>
    <tbody>

    <tr>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'kilosFinosZinc', 'error')} required">
            <label for="kilosFinosZinc" style="width: 50%">
                <g:message code="liquidacionDeComplejo.kilosFinosZinc.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosZinc" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'kilosFinosZinc')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'kilosFinosPlomo', 'error')} required">
            <label for="kilosFinosPlomo" style="width: 50%">
                <g:message code="liquidacionDeComplejo.kilosFinosPlomo.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosPlomo" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'kilosFinosPlomo')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'kilosFinosPlata', 'error')} required">
            <label for="kilosFinosPlata" style="width: 50%">
                <g:message code="liquidacionDeComplejo.kilosFinosPlata.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="kilosFinosPlata" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'kilosFinosPlata')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'librasFinasDeZinc', 'error')} required">
            <label for="librasFinasDeZinc" style="width: 50%">
                <g:message code="liquidacionDeComplejo.librasFinasDeZinc.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="librasFinasDeZinc" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'librasFinasDeZinc')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'librasFinasDePlomo', 'error')} required">
            <label for="librasFinasDePlomo" style="width: 50%">
                <g:message code="liquidacionDeComplejo.librasFinasDePlomo.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="librasFinasDePlomo" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'librasFinasDePlomo')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'onzasTroyDePlata', 'error')} required">
            <label for="onzasTroyDePlata" style="width: 50%">
                <g:message code="liquidacionDeComplejo.onzasTroyDePlata.label" default="Onzas Troy" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="onzasTroyDePlata" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'onzasTroyDePlata')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'valorOficialBrutoDeZinc', 'error')} required">
            <label for="valorOficialBrutoDeZinc" style="width: 50%">
                <g:message code="liquidacionDeComplejo.valorOficialBrutoDeZinc.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDeZinc" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'valorOficialBrutoDeZinc')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'valorOficialBrutoDePlomo', 'error')} required">
            <label for="valorOficialBrutoDePlomo" style="width: 50%">
                <g:message code="liquidacionDeComplejo.valorOficialBrutoDePlomo.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDePlomo" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'valorOficialBrutoDePlomo')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'valorOficialBrutoDePlata', 'error')} required">
            <label for="valorOficialBrutoDePlata" style="width: 50%">
                <g:message code="liquidacionDeComplejo.valorOficialBrutoDePlata.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDePlata" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'valorOficialBrutoDePlata')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'valorOficialBrutoDeZincEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDeZincEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDeComplejo.valorOficialBrutoDeZincEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDeZincEnBolivianos" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'valorOficialBrutoDeZincEnBolivianos')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'valorOficialBrutoDePlomoEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDePlomoEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDeComplejo.valorOficialBrutoDePlomoEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDePlomoEnBolivianos" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'valorOficialBrutoDePlomoEnBolivianos')}" required="" size="10" inputmode="decimal"/>
        </td>
        <td class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'valorOficialBrutoDePlataEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDePlataEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDeComplejo.valorOficialBrutoDePlataEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="valorOficialBrutoDePlataEnBolivianos" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'valorOficialBrutoDePlataEnBolivianos')}" required="" size="10" inputmode="decimal"/>
        </td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="retenciones" value="${liquidacionDeComplejoInstance?.retenciones}" />

<h1 style="font-weight: bold">Retenciones</h1>

<div style="width: 700px; margin-left: auto; margin-right: auto;">
    <table id="tablaRetenciones">
    </table>
</div>

<div id="_botones" style="text-align: center">
    <br>
    <button id="eliminarRetencion" type="button">ELIMINAR RETENCION SELECCIONADA</button>
</div>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'totalRetenciones', 'error')} required">
    <label for="totalRetenciones">
        <g:message code="liquidacionDeComplejo.totalRetenciones.label" default="Total Retenciones" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalRetenciones" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'totalRetenciones')}" required="" inputmode="decimal"/>
</div>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'totalPagado', 'error')} required">
    <label for="totalPagado">
        <g:message code="liquidacionDeComplejo.totalPagado.label" default="Total Pagado" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalPagado" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'totalPagado')}" required="" inputmode="decimal"/>
</div>

<g:hiddenField name="cantidadAnticiposPorPagar" />

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'anticipoPorPagar', 'error')} required">
    <label for="anticipoPorPagar">
        <g:message code="liquidacionDeComplejo.anticipoPorPagar.label" default="Anticipo Por Pagar" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="anticipoPorPagar" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'anticipoPorPagar')}" required="" class="rojo" readonly="true"/>
</div>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'totalAnticiposContraEntrega', 'error')} required">
    <label for="totalAnticiposContraEntrega">
        <g:message code="liquidacionDeComplejo.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalAnticiposContraEntrega" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'totalAnticiposContraEntrega')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'totalAnticiposContraFuturaEntrega', 'error')} required">
    <label for="totalAnticiposContraFuturaEntrega">
        <g:message code="liquidacionDeComplejo.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" />
        <span class="required-indicator">*</span>
    </label>
    <g:hiddenField name="adelantoPorLiquidacionProvisional" value="0"/>
    <g:field name="totalAnticiposContraFuturaEntrega" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'totalAnticiposContraFuturaEntrega')}" required=""  inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Costo De Tratamiento</h1>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'aplicarCostoTratamiento', 'error')} ">
    <label for="aplicarCostoTratamiento">
        <g:message code="liquidacionDeComplejo.aplicarCostoTratamiento.label" default="Aplicar Costo Tratamiento" />

    </label>
    <g:select name="aplicarCostoTratamiento" from="${['NO', 'SI']}" value="${liquidacionDeComplejoInstance?.aplicarCostoTratamiento}" valueMessagePrefix="liquidacionDeComplejo.aplicarCostoTratamiento"/>
</div>

<div id="_costoTratamiento">
    <div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'costoTratamiento', 'error')} required">
        <label for="costoTratamiento">
            <g:message code="liquidacionDeComplejo.costoTratamiento.label" default="Costo Tratamiento" />
            <span class="required-indicator">*</span>
        </label>
        <g:field name="costoTratamiento" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'costoTratamiento')}" required="" class="verde" style="font-weight: bold;" inputmode="decimal"/>
    </div>

    <div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'pesoBrozaInicial', 'error')} required">
        <label for="pesoBrozaInicial">
            <g:message code="liquidacionDeComplejo.pesoBrozaInicial.label" default="Peso Broza Inicial" />
            <span class="required-indicator">*</span>
        </label>
        <g:field name="pesoBrozaInicial" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'pesoBrozaInicial')}" required=""  inputmode="decimal"/>
    </div>

    <div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'costoTratamientoTotal', 'error')} required">
        <label for="costoTratamientoTotal">
            <g:message code="liquidacionDeComplejo.costoTratamientoTotal.label" default="Costo Tratamiento Total" />
            <span class="required-indicator">*</span>
        </label>
        <g:field name="costoTratamientoTotal" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'costoTratamientoTotal')}" required="" readonly="" class="amarillo" style="font-weight: bold; font-size: 16px;" inputmode="decimal"/>
    </div>
</div>

<h1 style="font-weight: bold">Líquido Pagable</h1>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'totalLiquidoPagable', 'error')} required">
    <label for="totalLiquidoPagable">
        <g:message code="liquidacionDeComplejo.totalLiquidoPagable.label" default="Total Liquido Pagable" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalLiquidoPagable" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'totalLiquidoPagable')}" required="" class="verde" style="font-weight: bold; font-size: 16px;" inputmode="decimal"/>
    <g:hiddenField name="totalLiquidoPagableOriginal" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'totalLiquidoPagableOriginal')}"/>
    <g:hiddenField name="diferenciaLiquidoPagable" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'diferenciaLiquidoPagable')}"/>
</div>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'bonoTransporteKilosNetosSecosTotal', 'error')} required" style="display: none">
    <label for="bonoTransporteKilosNetosSecosTotal">
        <g:message code="liquidacionDeComplejo.bonoTransporteKilosNetosSecosTotal.label" default="Bono Transporte Kilos Netos Secos Total" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="bonoTransporteKilosNetosSecosTotal" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'bonoTransporteKilosNetosSecosTotal')}" required="" class="amarillo" inputmode="decimal"/>
</div>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'totalLiquidoPagableFinal', 'error')} required" style="display: none">
    <label for="totalLiquidoPagableFinal">
        <g:message code="liquidacionDeComplejo.totalLiquidoPagableFinal.label" default="Total Liquido Pagable Final" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalLiquidoPagableFinal" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'totalLiquidoPagableFinal')}" required="" class="verde" inputmode="decimal"/>
</div>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="liquidacionDeComplejo.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${liquidacionDeComplejoInstance?.observaciones}" size="90"/>
</div>

<g:hiddenField name="detalleLaboratorio1" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'detalleLaboratorio1')}"/>
<g:hiddenField name="costoLaboratorio1" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'costoLaboratorio1')}"/>
<g:hiddenField name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDeComplejoInstance, field: 'totalCostoLaboratorio')}"/>

<div id="_modificacion" style="display: none">
    <h1 style="font-weight: bold; font-size: 12px">Informacion de Modificacion del Registro</h1>

<div class="form-group ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'motivoDeModificacion', 'error')} ">
    <label for="motivoDeModificacion">
        <g:message code="liquidacionDeComplejo.motivoDeModificacion.label" default="Motivo De Modificacion" />

    </label>
    <g:textField name="motivoDeModificacion" value="${liquidacionDeComplejoInstance?.motivoDeModificacion}" size="90"/>
</div>
</div>

<div style="display:none;" class="nav_up" id="nav_up"></div>
<div style="display:none;" class="nav_down" id="nav_down"></div>

%{--<button type="button" onclick="printJS({ printable: 'create-liquidacionDeComplejo', type: 'html', header: 'PrintJS - Form Element Selection' })">--}%
%{--    Print Form with Header--}%
%{--</button>--}%