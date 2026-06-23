
<%@ page import="org.socymet.liquidacion.LiquidacionDePlomoPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata')}" />
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
<a href="#show-liquidacionDePlomoPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-liquidacionDePlomoPlata" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list liquidacionDePlomoPlata">

<g:if test="${liquidacionDePlomoPlataInstance?.numeroLiquidacionPlomoPlata}">
    <li class="fieldcontain">
        <span id="numeroLiquidacionPlomoPlata-label" class="property-label"><g:message code="liquidacionDePlomoPlata.numeroLiquidacionPlomoPlata.label" default="No. Liquidacion" /></span>

        <span class="property-value" aria-labelledby="numeroLiquidacionPlomoPlata-label"><g:link controller="liquidacionDePlomoPlata" action="show" id="${liquidacionDePlomoPlataInstance?.id}">${liquidacionDePlomoPlataInstance?.numeroLiquidacionPlomoPlata?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.recepcionDeComplejo}">
    <li class="fieldcontain">
        <span id="recepcionDeComplejo-label" class="property-label"><g:message code="liquidacionDePlomoPlata.recepcionDeComplejo.label" default="Lote" /></span>

        <span class="property-value" aria-labelledby="recepcionDeComplejo-label"><g:link controller="recepcionDeComplejo" action="show" id="${liquidacionDePlomoPlataInstance?.recepcionDeComplejo?.id}">${liquidacionDePlomoPlataInstance?.recepcionDeComplejo?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<g:if test="${liquidacionDePlomoPlataInstance?.nombreCliente}">
    <li class="fieldcontain">
        <span id="nombreCliente-label" class="property-label"><g:message code="liquidacionDePlomoPlata.nombreCliente.label" default="Nombre Cliente" /></span>

        <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="nombreCliente"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.nombreEmpresa}">
    <li class="fieldcontain">
        <span id="nombreEmpresa-label" class="property-label"><g:message code="liquidacionDePlomoPlata.nombreEmpresa.label" default="Nombre Empresa" /></span>

        <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="nombreEmpresa"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.fechaDeRecepcion}">
    <li class="fieldcontain">
        <span id="fechaDeRecepcion-label" class="property-label"><g:message code="liquidacionDePlomoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

        <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="fechaDeRecepcion"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.cantidadDeSacos}">
    <li class="fieldcontain">
        <span id="cantidadDeSacos-label" class="property-label"><g:message code="liquidacionDePlomoPlata.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

        <span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="cantidadDeSacos"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.pesoBruto}">
    <li class="fieldcontain">
        <span id="pesoBruto-label" class="property-label"><g:message code="liquidacionDePlomoPlata.pesoBruto.label" default="Peso Bruto" /></span>

        <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="pesoBruto"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.tipoDeMineral}">
    <li class="fieldcontain">
        <span id="tipoDeMineral-label" class="property-label"><g:message code="liquidacionDePlomoPlata.tipoDeMineral.label" default="Tipo De Mineral" /></span>

        <span class="property-value" aria-labelledby="tipoDeMineral-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="tipoDeMineral"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.naturalezaMineral}">
    <li class="fieldcontain">
        <span id="naturalezaMineral-label" class="property-label"><g:message code="liquidacionDePlomoPlata.naturalezaMineral.label" default="Naturaleza Mineral" /></span>

        <span class="property-value" aria-labelledby="naturalezaMineral-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="naturalezaMineral"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.estadoDelLote}">
    <li class="fieldcontain">
        <span id="estadoDelLote-label" class="property-label"><g:message code="liquidacionDePlomoPlata.estadoDelLote.label" default="Estado Del Lote" /></span>
        <span class="property-value" aria-labelledby="estadoDelLote-label">${liquidacionDePlomoPlataInstance.recepcionDeComplejo.estadoDelLote}</span>
    </li>
</g:if>

<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'cotizacionDiariaDePlomo', 'error')} required">
            <label for="cotizacionDiariaDePlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.cotizacionDiariaDePlomo.label" default="Cot. Dia Plomo" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlomoPlataInstance.cotizacionDiariaDePlomo}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'cotizacionQuincenalDePlomo', 'error')} required">
            <label for="cotizacionQuincenalDePlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.cotizacionQuincenalDePlomo.label" default="Cot. Quinc. Plomo" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlomoPlataInstance.cotizacionQuincenalDePlomo}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'alicuotaDePlomo', 'error')} required">
            <label for="alicuotaDePlomo" style="width: 60%">
                <g:message code="liquidacionDePlomoPlata.alicuotaDePlomo.label" default="Alicuota Plomo" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlomoPlataInstance.alicuotaDePlomo}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'cotizacionDiariaDePlata', 'error')} required">
            <label for="cotizacionDiariaDePlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.cotizacionDiariaDePlata.label" default="Cot. Dia Plata" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlomoPlataInstance.cotizacionDiariaDePlata}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'cotizacionQuincenalDePlata', 'error')} required">
            <label for="cotizacionQuincenalDePlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.cotizacionQuincenalDePlata.label" default="Cot. Quinc. Plata" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlomoPlataInstance.cotizacionQuincenalDePlata}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'alicuotaDePlata', 'error')} required">
            <label for="alicuotaDePlata" style="width: 60%">
                <g:message code="liquidacionDePlomoPlata.alicuotaDePlata.label" default="Alicuota Plata" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlomoPlataInstance.alicuotaDePlata}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlomoPlataInstance.tipoDeCambioOficial}
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDePlomoPlataInstance.tipoDeCambioComercial}
        </td>
        <td>
            &nbsp;</td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<g:if test="${liquidacionDePlomoPlataInstance?.fechaDeLiquidacion}">
    <li class="fieldcontain">
        <span id="fechaDeLiquidacion-label" class="property-label"><g:message code="liquidacionDePlomoPlata.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

        <span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:formatDate date="${liquidacionDePlomoPlataInstance?.fechaDeLiquidacion}" format="dd/MM/yyyy"/></span>

    </li>
</g:if>

<g:hiddenField name="kilosNetosHumedos" value="${liquidacionDePlomoPlataInstance?.pesoBruto}" />

<table class="center" border="0" style="width: 80%;">
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'merma', 'error')} required">
            <label for="merma" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.merma.label" default="Merma" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="merma"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'humedad', 'error')} required">
            <label for="humedad" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.humedad.label" default="Humedad" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="humedad"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="kilosNetosSecos"/>
        </td>
    </tr>
</table>

<table class="center" border="0" style="width: 80%;">
    <thead>
    <tr>
        <th style="text-align: center">PLOMO</th>
        <th style="text-align: center">PLATA</th>
    </tr>
    </thead>
    <tbody>

    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlomo', 'error')} required">
            <label for="porcentajePlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.porcentajePlomo.label" default="Ley %" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="porcentajePlomo"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'porcentajePlata', 'error')} required">
            <label for="porcentajePlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.porcentajePlata.label" default="Ley %" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="porcentajePlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'dolarPuntoPlomo', 'error')} required">
            <label for="dolarPuntoPlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.dolarPuntoPlomo.label" default="\$us/Punto" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="dolarPuntoPlomo"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'dolarPuntoPlata', 'error')} required">
            <label for="dolarPuntoPlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.dolarPuntoPlata.label" default="\$us/Punto" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="dolarPuntoPlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'kilosFinosPlomo', 'error')} required">
            <label for="kilosFinosPlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.kilosFinosPlomo.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="kilosFinosPlomo"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'kilosFinosPlata', 'error')} required">
            <label for="kilosFinosPlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.kilosFinosPlata.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="kilosFinosPlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'librasFinasDePlomo', 'error')} required">
            <label for="librasFinasDePlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.librasFinasDePlomo.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="librasFinasDePlomo"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'onzasTroyDePlata', 'error')} required">
            <label for="onzasTroyDePlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.onzasTroyDePlata.label" default="Onzas Troy" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="onzasTroyDePlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBrutoDePlomo', 'error')} required">
            <label for="valorOficialBrutoDePlomo" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.valorOficialBrutoDePlomo.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="valorOficialBrutoDePlomo"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBrutoDePlata', 'error')} required">
            <label for="valorOficialBrutoDePlata" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.valorOficialBrutoDePlata.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="valorOficialBrutoDePlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBrutoDePlomoEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDePlomoEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.valorOficialBrutoDePlomoEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="valorOficialBrutoDePlomoEnBolivianos"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'valorOficialBrutoDePlataEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDePlataEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDePlomoPlata.valorOficialBrutoDePlataEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="valorOficialBrutoDePlataEnBolivianos"/>
        </td>
    </tr>
    </tbody>
</table>

<g:if test="${liquidacionDePlomoPlataInstance?.valorOficialBruto}">
    <li class="fieldcontain">
        <span id="valorOficialBruto-label" class="property-label"><g:message code="liquidacionDePlomoPlata.valorOficialBruto.label" default="Valor Oficial Bruto" /></span>

        <span class="property-value" aria-labelledby="valorOficialBruto-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="valorOficialBruto"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.valorPorTonelada}">
    <li class="fieldcontain">
        <span id="valorPorTonelada-label" class="property-label"><g:message code="liquidacionDePlomoPlata.valorPorTonelada.label" default="Valor Por Tonelada" /></span>

        <span class="property-value" aria-labelledby="valorPorTonelada-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="valorPorTonelada"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.valorNetoMineral}">
    <li class="fieldcontain">
        <span id="valorNetoMineral-label" class="property-label"><g:message code="liquidacionDePlomoPlata.valorNetoMineral.label" default="Valor Neto Mineral" /></span>

        <span class="property-value" aria-labelledby="valorNetoMineral-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="valorNetoMineral"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.valorNetoMineralEnBolivianos}">
    <li class="fieldcontain">
        <span id="valorNetoMineralEnBolivianos-label" class="property-label"><g:message code="liquidacionDePlomoPlata.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral Bs" /></span>

        <span class="property-value" aria-labelledby="valorNetoMineralEnBolivianos-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="valorNetoMineralEnBolivianos"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.bonoCalidad}">
    <li class="fieldcontain">
        <span id="bonoCalidad-label" class="property-label"><g:message code="liquidacionDePlomoPlata.bonoCalidad.label" default="Bono Calidad" /></span>

        <span class="property-value" aria-labelledby="bonoCalidad-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="bonoCalidad"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.bonoIncentivo}">
    <li class="fieldcontain">
        <span id="bonoIncentivo-label" class="property-label"><g:message code="liquidacionDePlomoPlata.bonoIncentivo.label" default="Bono Incentivo" /></span>

        <span class="property-value" aria-labelledby="bonoIncentivo-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="bonoIncentivo"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.valorDeCompra}">
    <li class="fieldcontain">
        <span id="valorDeCompra-label" class="property-label"><g:message code="liquidacionDePlomoPlata.valorDeCompra.label" default="Valor De Compra" /></span>

        <span class="property-value" aria-labelledby="valorDeCompra-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="valorDeCompra"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.porcentajeRegalia}">
    <li class="fieldcontain">
        <span id="porcentajeRegalia-label" class="property-label"><g:message code="liquidacionDePlomoPlata.porcentajeRegalia.label" default="Porcentaje Regalia" /></span>

        <span class="property-value" aria-labelledby="porcentajeRegalia-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="porcentajeRegalia"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.regaliaMinera}">
    <li class="fieldcontain">
        <span id="regaliaMinera-label" class="property-label"><g:message code="liquidacionDePlomoPlata.regaliaMinera.label" default="Regalia Minera" /></span>

        <span class="property-value" aria-labelledby="regaliaMinera-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="regaliaMinera"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold; font-size: 12px">Retenciones</h1>
<g:hiddenField name="retenciones" value="${liquidacionDePlomoPlataInstance?.retenciones}"/>
<div style="width: 700px; margin-left: auto; margin-right: auto;"><table id="tablaRetenciones"></table></div>

<g:if test="${liquidacionDePlomoPlataInstance?.totalRetenciones}">
    <li class="fieldcontain">
        <span id="totalRetenciones-label" class="property-label"><g:message code="liquidacionDePlomoPlata.totalRetenciones.label" default="Total Retenciones" /></span>

        <span class="property-value" aria-labelledby="totalRetenciones-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="totalRetenciones"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.totalPagado}">
    <li class="fieldcontain">
        <span id="totalPagado-label" class="property-label"><g:message code="liquidacionDePlomoPlata.totalPagado.label" default="Total Pagado" /></span>

        <span class="property-value" aria-labelledby="totalPagado-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="totalPagado"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.totalAnticiposContraEntrega}">
    <li class="fieldcontain">
        <span id="totalAnticiposContraEntrega-label" class="property-label"><g:message code="liquidacionDePlomoPlata.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" /></span>

        <span class="property-value" aria-labelledby="totalAnticiposContraEntrega-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="totalAnticiposContraEntrega"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.totalAnticiposContraFuturaEntrega}">
    <li class="fieldcontain">
        <span id="totalAnticiposContraFuturaEntrega-label" class="property-label"><g:message code="liquidacionDePlomoPlata.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" /></span>

        <span class="property-value" aria-labelledby="totalAnticiposContraFuturaEntrega-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="totalAnticiposContraFuturaEntrega"/></span>

    </li>
</g:if>

<li class="fieldcontain">
    <span id="totalLiquidoPagable-label" class="property-label"><g:message code="liquidacionDePlomoPlata.totalLiquidoPagable.label" default="Total Liquido Pagable" /></span>

    <span class="property-value" aria-labelledby="totalLiquidoPagable-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="totalLiquidoPagable"/></span>

</li>

<g:if test="${liquidacionDePlomoPlataInstance?.conjuntoPlomoPlata}">
    <li class="fieldcontain">
        <span id="conjuntoPlomoPlata-label" class="property-label"><g:message code="liquidacionDePlomoPlata.conjuntoPlomoPlata.label" default="Conjunto de PlomoPlata" /></span>

        <span class="property-value" aria-labelledby="conjuntoPlomoPlata-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="conjuntoPlomoPlata"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.observaciones}">
    <li class="fieldcontain">
        <span id="observaciones-label" class="property-label"><g:message code="liquidacionDePlomoPlata.observaciones.label" default="Observaciones" /></span>

        <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="observaciones"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDePlomoPlataInstance?.motivoDeModificacion}">
    <li class="fieldcontain">
        <span id="motivoDeModificacion-label" class="property-label"><g:message code="liquidacionDePlomoPlata.motivoDeModificacion.label" default="Motivo De Modificacion" /></span>

        <span class="property-value" aria-labelledby="motivoDeModificacion-label"><g:fieldValue bean="${liquidacionDePlomoPlataInstance}" field="motivoDeModificacion"/></span>

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
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'detalleLaboratorio1', 'error')}">
            ${liquidacionDePlomoPlataInstance.detalleLaboratorio1}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'costoLaboratorio1', 'error')}">
            ${liquidacionDePlomoPlataInstance.costoLaboratorio1}
        </td>
    </tr>
    <tr>
        <g:if test="${liquidacionDePlomoPlataInstance?.detalleLaboratorio2}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'detalleLaboratorio2', 'error')}">
                ${liquidacionDePlomoPlataInstance.detalleLaboratorio2}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'costoLaboratorio2', 'error')}">
                ${liquidacionDePlomoPlataInstance.costoLaboratorio2}
            </td>
        </g:if>
    </tr>
    <tr>
        <g:if test="${liquidacionDePlomoPlataInstance?.detalleLaboratorio3}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'detalleLaboratorio3', 'error')}">
                ${liquidacionDePlomoPlataInstance.detalleLaboratorio3}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'costoLaboratorio3', 'error')}">
                ${liquidacionDePlomoPlataInstance.costoLaboratorio3}
            </td>
        </g:if>
    </tr>
    <tr>
        <g:if test="${liquidacionDePlomoPlataInstance?.detalleLaboratorio4}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'detalleLaboratorio4', 'error')}">
                ${liquidacionDePlomoPlataInstance.detalleLaboratorio4}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'costoLaboratorio4', 'error')}">
                ${liquidacionDePlomoPlataInstance.costoLaboratorio4}
            </td>
        </g:if>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="liquidacionDePlomoPlata.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDePlomoPlataInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="liquidoPagable" value="${liquidacionDePlomoPlataInstance?.totalLiquidoPagable}" />
<g:if test="${liquidacionDePlomoPlataInstance?.totalLiquidoPagable<0}">
    <h1 style="font-weight: bold">Enlace a Anticipo Contra Futura Entrega (Clic en enlace para desplegar formulario)</h1>
    <li class="fieldcontain">
        <label style="font-size: 16px; font-weight: bold; color: red; width: 70%">
            <g:link controller="anticipoContraFuturaEntrega" action="show" id="${org.socymet.anticipos.AnticipoContraFuturaEntrega.findByLiquidacionId(liquidacionDePlomoPlataInstance.id).id}" target="_blank" style="color: red"> ANTICIPO CONTRA FUTURA ENTREGA GENERADO!</g:link>
        </label>
    </li>
</g:if>

</ol>
<fieldset class="buttons">
    <div style="float: left">
        <g:form>
            <g:hiddenField name="id" value="${liquidacionDePlomoPlataInstance?.id}" />
            <g:if test="${liquidacionDePlomoPlataInstance.fechaDeCancelacion!=(new java.util.Date(84,5,14))&&liquidacionDePlomoPlataInstance.fechaDeCancelacion}">
                <span id="observaciones-label" class="property-label" style="font-weight: bold; color: green">CANCELADO</span>
            </g:if>
            <g:if test="${liquidacionDePlomoPlataInstance.fechaDeCancelacion==(new java.util.Date(84,5,14))&&liquidacionDePlomoPlataInstance.fechaDeCancelacion}">
                <span id="observaciones-label" class="property-label" style="font-weight: bold; color: red">SIN CANCELAR</span>
            </g:if>
            <g:link class="edit" action="edit" id="${liquidacionDePlomoPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </g:form>
    </div>
    <div style="float: right">
        <g:jasperReport controller="liquidacionDePlomoPlata" action="crearReporte" jasper="liquidacion_plomo_plata" format="PDF,RTF" name="ReporteLiquidacion${liquidacionDePlomoPlataInstance.lote}">
            <input type="hidden" name="LIQ_SN_ID" value="${liquidacionDePlomoPlataInstance.id}" />
        </g:jasperReport>
    </div>
</fieldset>
<div style="display:none;" class="nav_up" id="nav_up"></div>
<div style="display:none;" class="nav_down" id="nav_down"></div>
</div>
</body>
</html>
