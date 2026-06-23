
<%@ page import="org.socymet.liquidacion.LiquidacionDeZincPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'liquidacionDeZincPlata.label', default: 'LiquidacionDeZincPlata')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'styleScrolling.css')}" type="text/css" >
    <g:javascript src="jquery-1.10.1.min.js" />
    <g:javascript src="i18n/grid.locale-es.js" />
    <g:javascript src="jquery.jqGrid.min.js" />
    <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
    <g:javascript src="jquery.jgrowl.min.js" />
    <g:javascript src="scrolling.js" />
    <script>
        $(document).ready(function() {
            // CREACION DE TABLA DE RETENCIONES UTILIZANDO COMPONENTE jqGrid
            jQuery("#tablaRetenciones").jqGrid({
                datatype: "local",
                height: 200,
                colNames: ["CODIGO","DESCRIPCION","TIPO","CANTIDAD","UNIDAD","MONTO","ASIGNACION"],
                colModel:[
                    {name:'CODIGO',index:'CODIGO', width:60},
                    {name:'DESCRIPCION',index:'DESCRIPCION', width:200},
                    {name:'TIPO',index:'TIPO', width:80},
                    {name:'CANTIDAD',index:'CANTIDAD', width:80},
                    {name:'UNIDAD',index:'UNIDAD', width:80},
                    {name:'MONTO',index:'MONTO', width:80},
                    {name:'ASIGNACION',index:'ASIGNACION', width:80} ],
                multiselect: false,
                caption: "RETENCIONES"
            });

            var mydata = $("#retenciones").val();
            if(mydata=="")
                mydata = [];
            else
                mydata = $.parseJSON(mydata);

            for(var i=0;i<=mydata.length;i++)
                jQuery("#tablaRetenciones").jqGrid('addRowData',i+1,mydata[i]);

            var liquidoPagable = transFloat($("#liquidoPagable").val());
            if(liquidoPagable<0)
                $.jGrowl("Debido a que el Liquido Pagable es negativo se ha creado un Anticipo Contra Futura Entrega. El enlace al formulario esta al final de la pagina.",
                        {sticky: true, header: 'ATENCION'});

            function transFloat(numeroString){
                var numero = numeroString.replace(',','');
                return parseFloat(numero);
            }
        });
    </script>
</head>
<body>
<a href="#show-liquidacionDeZincPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-liquidacionDeZincPlata" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list liquidacionDeZincPlata">

<g:if test="${liquidacionDeZincPlataInstance?.numeroLiquidacionZincPlata}">
    <li class="fieldcontain">
        <span id="numeroLiquidacionZincPlata-label" class="property-label"><g:message code="liquidacionDeZincPlata.numeroLiquidacionZincPlata.label" default="No. Liquidacion" /></span>

        <span class="property-value" aria-labelledby="numeroLiquidacionZincPlata-label"><g:link controller="liquidacionDeZincPlata" action="show" id="${liquidacionDeZincPlataInstance?.id}">${liquidacionDeZincPlataInstance?.numeroLiquidacionZincPlata?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.recepcionDeComplejo}">
    <li class="fieldcontain">
        <span id="recepcionDeComplejo-label" class="property-label"><g:message code="liquidacionDeZincPlata.recepcionDeComplejo.label" default="Lote" /></span>

        <span class="property-value" aria-labelledby="recepcionDeComplejo-label"><g:link controller="recepcionDeComplejo" action="show" id="${liquidacionDeZincPlataInstance?.recepcionDeComplejo?.id}">${liquidacionDeZincPlataInstance?.recepcionDeComplejo?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<g:if test="${liquidacionDeZincPlataInstance?.nombreCliente}">
    <li class="fieldcontain">
        <span id="nombreCliente-label" class="property-label"><g:message code="liquidacionDeZincPlata.nombreCliente.label" default="Nombre Cliente" /></span>

        <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="nombreCliente"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.nombreEmpresa}">
    <li class="fieldcontain">
        <span id="nombreEmpresa-label" class="property-label"><g:message code="liquidacionDeZincPlata.nombreEmpresa.label" default="Nombre Empresa" /></span>

        <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="nombreEmpresa"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.fechaDeRecepcion}">
    <li class="fieldcontain">
        <span id="fechaDeRecepcion-label" class="property-label"><g:message code="liquidacionDeZincPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

        <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="fechaDeRecepcion"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.cantidadDeSacos}">
    <li class="fieldcontain">
        <span id="cantidadDeSacos-label" class="property-label"><g:message code="liquidacionDeZincPlata.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

        <span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="cantidadDeSacos"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.pesoBruto}">
    <li class="fieldcontain">
        <span id="pesoBruto-label" class="property-label"><g:message code="liquidacionDeZincPlata.pesoBruto.label" default="Peso Bruto" /></span>

        <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="pesoBruto"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.tipoDeMineral}">
    <li class="fieldcontain">
        <span id="tipoDeMineral-label" class="property-label"><g:message code="liquidacionDeZincPlata.tipoDeMineral.label" default="Peso Bruto" /></span>

        <span class="property-value" aria-labelledby="tipoDeMineral-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="tipoDeMineral"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.naturalezaMineral}">
    <li class="fieldcontain">
        <span id="naturalezaMineral-label" class="property-label"><g:message code="liquidacionDeZincPlata.naturalezaMineral.label" default="Naturaleza Mineral" /></span>

        <span class="property-value" aria-labelledby="naturalezaMineral-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="naturalezaMineral"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.estadoDelLote}">
    <li class="fieldcontain">
        <span id="estadoDelLote-label" class="property-label"><g:message code="liquidacionDeZincPlata.estadoDelLote.label" default="Estado Del Lote" /></span>
        <span class="property-value" aria-labelledby="estadoDelLote-label">${liquidacionDeZincPlataInstance.recepcionDeComplejo.estadoDelLote}</span>
    </li>
</g:if>

<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'cotizacionDiariaDeZinc', 'error')} required">
            <label for="cotizacionDiariaDeZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.cotizacionDiariaDeZinc.label" default="Cot. Dia Zinc" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeZincPlataInstance.cotizacionDiariaDeZinc}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'cotizacionQuincenalDeZinc', 'error')} required">
            <label for="cotizacionQuincenalDeZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.cotizacionQuincenalDeZinc.label" default="Cot. Quinc. Zinc" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeZincPlataInstance.cotizacionQuincenalDeZinc}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'alicuotaDeZinc', 'error')} required">
            <label for="alicuotaDeZinc" style="width: 60%">
                <g:message code="liquidacionDeZincPlata.alicuotaDeZinc.label" default="Alicuota Zinc" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeZincPlataInstance.alicuotaDeZinc}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'cotizacionDiariaDePlata', 'error')} required">
            <label for="cotizacionDiariaDePlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.cotizacionDiariaDePlata.label" default="Cot. Dia Plata" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeZincPlataInstance.cotizacionDiariaDePlata}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'cotizacionQuincenalDePlata', 'error')} required">
            <label for="cotizacionQuincenalDePlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.cotizacionQuincenalDePlata.label" default="Cot. Quinc. Plata" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeZincPlataInstance.cotizacionQuincenalDePlata}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'alicuotaDePlata', 'error')} required">
            <label for="alicuotaDePlata" style="width: 60%">
                <g:message code="liquidacionDeZincPlata.alicuotaDePlata.label" default="Alicuota Plata" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeZincPlataInstance.alicuotaDePlata}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeZincPlataInstance.tipoDeCambioOficial}
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeZincPlataInstance.tipoDeCambioComercial}
        </td>
        <td>
            &nbsp;</td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<g:if test="${liquidacionDeZincPlataInstance?.fechaDeLiquidacion}">
    <li class="fieldcontain">
        <span id="fechaDeLiquidacion-label" class="property-label"><g:message code="liquidacionDeZincPlata.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

        <span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:formatDate date="${liquidacionDeZincPlataInstance?.fechaDeLiquidacion}" format="dd/MM/yyyy"/></span>

    </li>
</g:if>

<g:hiddenField name="kilosNetosHumedos" value="${liquidacionDeZincPlataInstance?.pesoBruto}" />

<table class="center" border="0" style="width: 80%;">
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'merma', 'error')} required">
            <label for="merma" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.merma.label" default="Merma" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="merma"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'humedad', 'error')} required">
            <label for="humedad" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.humedad.label" default="Humedad" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="humedad"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="kilosNetosSecos"/>
        </td>
    </tr>
</table>

<table class="center" border="0" style="width: 80%;">
    <thead>
    <tr>
        <th style="text-align: center">ZINC</th>
        <th style="text-align: center">PLATA</th>
    </tr>
    </thead>
    <tbody>

    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajeZinc', 'error')} required">
            <label for="porcentajeZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.porcentajeZinc.label" default="Ley %" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="porcentajeZinc"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'porcentajePlata', 'error')} required">
            <label for="porcentajePlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.porcentajePlata.label" default="Ley %" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="porcentajePlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'dolarPuntoZinc', 'error')} required">
            <label for="dolarPuntoZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.dolarPuntoZinc.label" default="\$us/Punto" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="dolarPuntoZinc"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'dolarPuntoPlata', 'error')} required">
            <label for="dolarPuntoPlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.dolarPuntoPlata.label" default="\$us/Punto" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="dolarPuntoPlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'kilosFinosZinc', 'error')} required">
            <label for="kilosFinosZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.kilosFinosZinc.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="kilosFinosZinc"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'kilosFinosPlata', 'error')} required">
            <label for="kilosFinosPlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.kilosFinosPlata.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="kilosFinosPlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'librasFinasDeZinc', 'error')} required">
            <label for="librasFinasDeZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.librasFinasDeZinc.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="librasFinasDeZinc"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'onzasTroyDePlata', 'error')} required">
            <label for="onzasTroyDePlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.onzasTroyDePlata.label" default="Onzas Troy" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="onzasTroyDePlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBrutoDeZinc', 'error')} required">
            <label for="valorOficialBrutoDeZinc" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.valorOficialBrutoDeZinc.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="valorOficialBrutoDeZinc"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBrutoDePlata', 'error')} required">
            <label for="valorOficialBrutoDePlata" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.valorOficialBrutoDePlata.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="valorOficialBrutoDePlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBrutoDeZincEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDeZincEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.valorOficialBrutoDeZincEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="valorOficialBrutoDeZincEnBolivianos"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'valorOficialBrutoDePlataEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDePlataEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDeZincPlata.valorOficialBrutoDePlataEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="valorOficialBrutoDePlataEnBolivianos"/>
        </td>
    </tr>
    </tbody>
</table>

<g:if test="${liquidacionDeZincPlataInstance?.valorOficialBruto}">
    <li class="fieldcontain">
        <span id="valorOficialBruto-label" class="property-label"><g:message code="liquidacionDeZincPlata.valorOficialBruto.label" default="Valor Oficial Bruto" /></span>

        <span class="property-value" aria-labelledby="valorOficialBruto-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="valorOficialBruto"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.valorPorTonelada}">
    <li class="fieldcontain">
        <span id="valorPorTonelada-label" class="property-label"><g:message code="liquidacionDeZincPlata.valorPorTonelada.label" default="Valor Por Tonelada" /></span>

        <span class="property-value" aria-labelledby="valorPorTonelada-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="valorPorTonelada"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.valorNetoMineral}">
    <li class="fieldcontain">
        <span id="valorNetoMineral-label" class="property-label"><g:message code="liquidacionDeZincPlata.valorNetoMineral.label" default="Valor Neto Mineral" /></span>

        <span class="property-value" aria-labelledby="valorNetoMineral-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="valorNetoMineral"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.valorNetoMineralEnBolivianos}">
    <li class="fieldcontain">
        <span id="valorNetoMineralEnBolivianos-label" class="property-label"><g:message code="liquidacionDeZincPlata.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral Bs" /></span>

        <span class="property-value" aria-labelledby="valorNetoMineralEnBolivianos-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="valorNetoMineralEnBolivianos"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.bonoCalidad}">
    <li class="fieldcontain">
        <span id="bonoCalidad-label" class="property-label"><g:message code="liquidacionDeZincPlata.bonoCalidad.label" default="Bono Calidad" /></span>

        <span class="property-value" aria-labelledby="bonoCalidad-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="bonoCalidad"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.bonoIncentivo}">
    <li class="fieldcontain">
        <span id="bonoIncentivo-label" class="property-label"><g:message code="liquidacionDeZincPlata.bonoIncentivo.label" default="Bono Incentivo" /></span>

        <span class="property-value" aria-labelledby="bonoIncentivo-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="bonoIncentivo"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.valorDeCompra}">
    <li class="fieldcontain">
        <span id="valorDeCompra-label" class="property-label"><g:message code="liquidacionDeZincPlata.valorDeCompra.label" default="Valor De Compra" /></span>

        <span class="property-value" aria-labelledby="valorDeCompra-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="valorDeCompra"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.porcentajeRegalia}">
    <li class="fieldcontain">
        <span id="porcentajeRegalia-label" class="property-label"><g:message code="liquidacionDeZincPlata.porcentajeRegalia.label" default="Porcentaje Regalia" /></span>

        <span class="property-value" aria-labelledby="porcentajeRegalia-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="porcentajeRegalia"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.regaliaMinera}">
    <li class="fieldcontain">
        <span id="regaliaMinera-label" class="property-label"><g:message code="liquidacionDeZincPlata.regaliaMinera.label" default="Regalia Minera" /></span>

        <span class="property-value" aria-labelledby="regaliaMinera-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="regaliaMinera"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold; font-size: 12px">Retenciones</h1>
<g:hiddenField name="retenciones" value="${liquidacionDeZincPlataInstance?.retenciones}"/>
<div style="width: 700px; margin-left: auto; margin-right: auto;"><table id="tablaRetenciones"></table></div>

<g:if test="${liquidacionDeZincPlataInstance?.totalRetenciones}">
    <li class="fieldcontain">
        <span id="totalRetenciones-label" class="property-label"><g:message code="liquidacionDeZincPlata.totalRetenciones.label" default="Total Retenciones" /></span>

        <span class="property-value" aria-labelledby="totalRetenciones-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="totalRetenciones"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.totalPagado}">
    <li class="fieldcontain">
        <span id="totalPagado-label" class="property-label"><g:message code="liquidacionDeZincPlata.totalPagado.label" default="Total Pagado" /></span>

        <span class="property-value" aria-labelledby="totalPagado-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="totalPagado"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.totalAnticiposContraEntrega}">
    <li class="fieldcontain">
        <span id="totalAnticiposContraEntrega-label" class="property-label"><g:message code="liquidacionDeZincPlata.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" /></span>

        <span class="property-value" aria-labelledby="totalAnticiposContraEntrega-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="totalAnticiposContraEntrega"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.totalAnticiposContraFuturaEntrega}">
    <li class="fieldcontain">
        <span id="totalAnticiposContraFuturaEntrega-label" class="property-label"><g:message code="liquidacionDeZincPlata.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" /></span>

        <span class="property-value" aria-labelledby="totalAnticiposContraFuturaEntrega-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="totalAnticiposContraFuturaEntrega"/></span>

    </li>
</g:if>

<li class="fieldcontain">
    <span id="totalLiquidoPagable-label" class="property-label"><g:message code="liquidacionDeZincPlata.totalLiquidoPagable.label" default="Total Liquido Pagable" /></span>

    <span class="property-value" aria-labelledby="totalLiquidoPagable-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="totalLiquidoPagable"/></span>

</li>

<g:if test="${liquidacionDeZincPlataInstance?.conjuntoZincPlata}">
    <li class="fieldcontain">
        <span id="conjuntoZincPlata-label" class="property-label"><g:message code="liquidacionDeZincPlata.conjuntoZincPlata.label" default="Conjunto de ZincPlata" /></span>

        <span class="property-value" aria-labelledby="conjuntoZincPlata-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="conjuntoZincPlata"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.observaciones}">
    <li class="fieldcontain">
        <span id="observaciones-label" class="property-label"><g:message code="liquidacionDeZincPlata.observaciones.label" default="Observaciones" /></span>

        <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="observaciones"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeZincPlataInstance?.motivoDeModificacion}">
    <li class="fieldcontain">
        <span id="motivoDeModificacion-label" class="property-label"><g:message code="liquidacionDeZincPlata.motivoDeModificacion.label" default="Motivo De Modificacion" /></span>

        <span class="property-value" aria-labelledby="motivoDeModificacion-label"><g:fieldValue bean="${liquidacionDeZincPlataInstance}" field="motivoDeModificacion"/></span>

    </li>
</g:if>

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
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'detalleLaboratorio1', 'error')}">
            ${liquidacionDeZincPlataInstance.detalleLaboratorio1}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'costoLaboratorio1', 'error')}">
            ${liquidacionDeZincPlataInstance.costoLaboratorio1}
        </td>
    </tr>
    <tr>
        <g:if test="${liquidacionDeZincPlataInstance?.detalleLaboratorio2}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'detalleLaboratorio2', 'error')}">
                ${liquidacionDeZincPlataInstance.detalleLaboratorio2}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'costoLaboratorio2', 'error')}">
                ${liquidacionDeZincPlataInstance.costoLaboratorio2}
            </td>
        </g:if>
    </tr>
    <tr>
        <g:if test="${liquidacionDeZincPlataInstance?.detalleLaboratorio3}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'detalleLaboratorio3', 'error')}">
                ${liquidacionDeZincPlataInstance.detalleLaboratorio3}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'costoLaboratorio3', 'error')}">
                ${liquidacionDeZincPlataInstance.costoLaboratorio3}
            </td>
        </g:if>
    </tr>
    <tr>
        <g:if test="${liquidacionDeZincPlataInstance?.detalleLaboratorio4}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'detalleLaboratorio4', 'error')}">
                ${liquidacionDeZincPlataInstance.detalleLaboratorio4}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'costoLaboratorio4', 'error')}">
                ${liquidacionDeZincPlataInstance.costoLaboratorio4}
            </td>
        </g:if>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="liquidacionDeZincPlata.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeZincPlataInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDeZincPlataInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="liquidoPagable" value="${liquidacionDeZincPlataInstance?.totalLiquidoPagable}" />
<g:if test="${liquidacionDeZincPlataInstance?.totalLiquidoPagable<0}">
    <h1 style="font-weight: bold">Enlace a Anticipo Contra Futura Entrega (Clic en enlace para desplegar formulario)</h1>
    <li class="fieldcontain">
        <label style="font-size: 16px; font-weight: bold; color: red; width: 70%">
            <g:link controller="anticipoContraFuturaEntrega" action="show" id="${org.socymet.anticipos.AnticipoContraFuturaEntrega.findByLiquidacionId(liquidacionDeZincPlataInstance.id).id}" target="_blank" style="color: red"> ANTICIPO CONTRA FUTURA ENTREGA GENERADO!</g:link>
        </label>
    </li>
</g:if>

</ol>
<fieldset class="buttons">
    <div style="float: left">
        <g:form>
            <g:hiddenField name="id" value="${liquidacionDeZincPlataInstance?.id}" />
            <g:if test="${liquidacionDeZincPlataInstance.fechaDeCancelacion!=(new java.util.Date(84,5,14))&&liquidacionDeZincPlataInstance.fechaDeCancelacion}">
                <span id="observaciones-label" class="property-label" style="font-weight: bold; color: green">CANCELADO</span>
            </g:if>
            <g:if test="${liquidacionDeZincPlataInstance.fechaDeCancelacion==(new java.util.Date(84,5,14))&&liquidacionDeZincPlataInstance.fechaDeCancelacion}">
                <span id="observaciones-label" class="property-label" style="font-weight: bold; color: red">SIN CANCELAR</span>
            </g:if>
            <g:link class="edit" action="edit" id="${liquidacionDeZincPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </g:form>
    </div>
    <div style="float: right">
        <g:jasperReport controller="liquidacionDeZincPlata" action="crearReporte" jasper="liquidacion_zinc_plata" format="PDF,RTF" name="ReporteLiquidacion${liquidacionDeZincPlataInstance.lote}">
            <input type="hidden" name="LIQ_SN_ID" value="${liquidacionDeZincPlataInstance.id}" />
        </g:jasperReport>
    </div>
</fieldset>
<div style="display:none;" class="nav_up" id="nav_up"></div>
<div style="display:none;" class="nav_down" id="nav_down"></div>
</div>
</body>
</html>
