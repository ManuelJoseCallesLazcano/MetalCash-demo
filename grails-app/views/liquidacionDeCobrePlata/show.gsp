
<%@ page import="org.socymet.liquidacion.LiquidacionDeCobrePlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'liquidacionDeCobrePlata.label', default: 'LiquidacionDeCobrePlata')}" />
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
<a href="#show-liquidacionDeCobrePlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-liquidacionDeCobrePlata" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list liquidacionDeCobrePlata">

<g:if test="${liquidacionDeCobrePlataInstance?.numeroLiquidacionCobrePlata}">
    <li class="fieldcontain">
        <span id="numeroLiquidacionCobrePlata-label" class="property-label"><g:message code="liquidacionDeCobrePlata.numeroLiquidacionCobrePlata.label" default="No. Liquidacion" /></span>

        <span class="property-value" aria-labelledby="numeroLiquidacionCobrePlata-label"><g:link controller="liquidacionDeCobrePlata" action="show" id="${liquidacionDeCobrePlataInstance?.id}">${liquidacionDeCobrePlataInstance?.numeroLiquidacionCobrePlata?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.recepcionDeComplejo}">
    <li class="fieldcontain">
        <span id="recepcionDeComplejo-label" class="property-label"><g:message code="liquidacionDeCobrePlata.recepcionDeComplejo.label" default="Lote" /></span>

        <span class="property-value" aria-labelledby="recepcionDeComplejo-label"><g:link controller="recepcionDeComplejo" action="show" id="${liquidacionDeCobrePlataInstance?.recepcionDeComplejo?.id}">${liquidacionDeCobrePlataInstance?.recepcionDeComplejo?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<h1 style="font-weight: bold">Datos de la Recepcion</h1>

<g:if test="${liquidacionDeCobrePlataInstance?.nombreCliente}">
    <li class="fieldcontain">
        <span id="nombreCliente-label" class="property-label"><g:message code="liquidacionDeCobrePlata.nombreCliente.label" default="Nombre Cliente" /></span>

        <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="nombreCliente"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.nombreEmpresa}">
    <li class="fieldcontain">
        <span id="nombreEmpresa-label" class="property-label"><g:message code="liquidacionDeCobrePlata.nombreEmpresa.label" default="Nombre Empresa" /></span>

        <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="nombreEmpresa"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.fechaDeRecepcion}">
    <li class="fieldcontain">
        <span id="fechaDeRecepcion-label" class="property-label"><g:message code="liquidacionDeCobrePlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

        <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="fechaDeRecepcion"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.cantidadDeSacos}">
    <li class="fieldcontain">
        <span id="cantidadDeSacos-label" class="property-label"><g:message code="liquidacionDeCobrePlata.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

        <span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="cantidadDeSacos"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.pesoBruto}">
    <li class="fieldcontain">
        <span id="pesoBruto-label" class="property-label"><g:message code="liquidacionDeCobrePlata.pesoBruto.label" default="Peso Bruto" /></span>

        <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="pesoBruto"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.tipoDeMineral}">
    <li class="fieldcontain">
        <span id="tipoDeMineral-label" class="property-label"><g:message code="liquidacionDeCobrePlata.tipoDeMineral.label" default="Tipo de Mineral" /></span>

        <span class="property-value" aria-labelledby="tipoDeMineral-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="tipoDeMineral"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.estadoDelLote}">
    <li class="fieldcontain">
        <span id="estadoDelLote-label" class="property-label"><g:message code="liquidacionDeCobrePlata.estadoDelLote.label" default="Estado Del Lote" /></span>
        <span class="property-value" aria-labelledby="estadoDelLote-label">${liquidacionDeCobrePlataInstance.recepcionDeComplejo.estadoDelLote}</span>
    </li>
</g:if>

<h1 style="font-weight: bold">Cotizaciones durante la Recepcion</h1>

<table class="center" border="0" style="width: 80%;">
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'cotizacionDiariaDeCobre', 'error')} required">
            <label for="cotizacionDiariaDeCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.cotizacionDiariaDeCobre.label" default="Cot. Dia Cobre" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeCobrePlataInstance.cotizacionDiariaDeCobre}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'cotizacionQuincenalDeCobre', 'error')} required">
            <label for="cotizacionQuincenalDeCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.cotizacionQuincenalDeCobre.label" default="Cot. Quinc. Cobre" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeCobrePlataInstance.cotizacionQuincenalDeCobre}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'alicuotaDeCobre', 'error')} required">
            <label for="alicuotaDeCobre" style="width: 60%">
                <g:message code="liquidacionDeCobrePlata.alicuotaDeCobre.label" default="Alicuota Cobre" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeCobrePlataInstance.alicuotaDeCobre}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'cotizacionDiariaDePlata', 'error')} required">
            <label for="cotizacionDiariaDePlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.cotizacionDiariaDePlata.label" default="Cot. Dia Plata" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeCobrePlataInstance.cotizacionDiariaDePlata}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'cotizacionQuincenalDePlata', 'error')} required">
            <label for="cotizacionQuincenalDePlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.cotizacionQuincenalDePlata.label" default="Cot. Quinc. Plata" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeCobrePlataInstance.cotizacionQuincenalDePlata}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'alicuotaDePlata', 'error')} required">
            <label for="alicuotaDePlata" style="width: 60%">
                <g:message code="liquidacionDeCobrePlata.alicuotaDePlata.label" default="Alicuota Plata" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeCobrePlataInstance.alicuotaDePlata}
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'tipoDeCambioOficial', 'error')} required">
            <label for="tipoDeCambioOficial" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.tipoDeCambioOficial.label" default="T/C  Oficial" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeCobrePlataInstance.tipoDeCambioOficial}
        </td>

        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'tipoDeCambioComercial', 'error')} required">
            <label for="tipoDeCambioComercial" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.tipoDeCambioComercial.label" default="T/C  Comercial" />
                <span class="required-indicator">*</span>
            </label>
            ${liquidacionDeCobrePlataInstance.tipoDeCambioComercial}
        </td>
        <td>
            &nbsp;</td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Informacion General de la Liquidacion</h1>

<g:if test="${liquidacionDeCobrePlataInstance?.fechaDeLiquidacion}">
    <li class="fieldcontain">
        <span id="fechaDeLiquidacion-label" class="property-label"><g:message code="liquidacionDeCobrePlata.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

        <span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:formatDate date="${liquidacionDeCobrePlataInstance?.fechaDeLiquidacion}" format="dd/MM/yyyy"/></span>

    </li>
</g:if>

<g:hiddenField name="kilosNetosHumedos" value="${liquidacionDeCobrePlataInstance?.pesoBruto}" />

<table class="center" border="0" style="width: 80%;">
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'merma', 'error')} required">
            <label for="merma" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.merma.label" default="Merma" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="merma"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'humedad', 'error')} required">
            <label for="humedad" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.humedad.label" default="Humedad" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="humedad"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'kilosNetosSecos', 'error')} required">
            <label for="kilosNetosSecos" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.kilosNetosSecos.label" default="K. N. S." />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="kilosNetosSecos"/>
        </td>
    </tr>
</table>

<table class="center" border="0" style="width: 80%;">
    <thead>
    <tr>
        <th style="text-align: center">COBRE</th>
        <th style="text-align: center">PLATA</th>
    </tr>
    </thead>
    <tbody>

    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajeCobre', 'error')} required">
            <label for="porcentajeCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.porcentajeCobre.label" default="Ley %" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="porcentajeCobre"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'porcentajePlata', 'error')} required">
            <label for="porcentajePlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.porcentajePlata.label" default="Ley %" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="porcentajePlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'dolarPuntoCobre', 'error')} required">
            <label for="dolarPuntoCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.dolarPuntoCobre.label" default="\$us/Punto" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="dolarPuntoCobre"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'dolarPuntoPlata', 'error')} required">
            <label for="dolarPuntoPlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.dolarPuntoPlata.label" default="\$us/Punto" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="dolarPuntoPlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'kilosFinosCobre', 'error')} required">
            <label for="kilosFinosCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.kilosFinosCobre.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="kilosFinosCobre"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'kilosFinosPlata', 'error')} required">
            <label for="kilosFinosPlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.kilosFinosPlata.label" default="Kilos Finos" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="kilosFinosPlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'librasFinasDeCobre', 'error')} required">
            <label for="librasFinasDeCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.librasFinasDeCobre.label" default="Libras Finas" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="librasFinasDeCobre"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'onzasTroyDePlata', 'error')} required">
            <label for="onzasTroyDePlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.onzasTroyDePlata.label" default="Onzas Troy" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="onzasTroyDePlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBrutoDeCobre', 'error')} required">
            <label for="valorOficialBrutoDeCobre" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.valorOficialBrutoDeCobre.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="valorOficialBrutoDeCobre"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBrutoDePlata', 'error')} required">
            <label for="valorOficialBrutoDePlata" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.valorOficialBrutoDePlata.label" default="Val. Bruto \$us" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="valorOficialBrutoDePlata"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBrutoDeCobreEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDeCobreEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.valorOficialBrutoDeCobreEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="valorOficialBrutoDeCobreEnBolivianos"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'valorOficialBrutoDePlataEnBolivianos', 'error')} required">
            <label for="valorOficialBrutoDePlataEnBolivianos" style="width: 50%">
                <g:message code="liquidacionDeCobrePlata.valorOficialBrutoDePlataEnBolivianos.label" default="Val. Bruto Bs" />
                <span class="required-indicator">*</span>
            </label>
            <g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="valorOficialBrutoDePlataEnBolivianos"/>
        </td>
    </tr>
    </tbody>
</table>

<g:if test="${liquidacionDeCobrePlataInstance?.valorOficialBruto}">
    <li class="fieldcontain">
        <span id="valorOficialBruto-label" class="property-label"><g:message code="liquidacionDeCobrePlata.valorOficialBruto.label" default="Valor Oficial Bruto" /></span>

        <span class="property-value" aria-labelledby="valorOficialBruto-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="valorOficialBruto"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.valorPorTonelada}">
    <li class="fieldcontain">
        <span id="valorPorTonelada-label" class="property-label"><g:message code="liquidacionDeCobrePlata.valorPorTonelada.label" default="Valor Por Tonelada" /></span>

        <span class="property-value" aria-labelledby="valorPorTonelada-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="valorPorTonelada"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.valorNetoMineral}">
    <li class="fieldcontain">
        <span id="valorNetoMineral-label" class="property-label"><g:message code="liquidacionDeCobrePlata.valorNetoMineral.label" default="Valor Neto Mineral" /></span>

        <span class="property-value" aria-labelledby="valorNetoMineral-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="valorNetoMineral"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.valorNetoMineralEnBolivianos}">
    <li class="fieldcontain">
        <span id="valorNetoMineralEnBolivianos-label" class="property-label"><g:message code="liquidacionDeCobrePlata.valorNetoMineralEnBolivianos.label" default="Valor Neto Mineral Bs" /></span>

        <span class="property-value" aria-labelledby="valorNetoMineralEnBolivianos-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="valorNetoMineralEnBolivianos"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.bonoCalidad}">
    <li class="fieldcontain">
        <span id="bonoCalidad-label" class="property-label"><g:message code="liquidacionDeCobrePlata.bonoCalidad.label" default="Bono Calidad" /></span>

        <span class="property-value" aria-labelledby="bonoCalidad-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="bonoCalidad"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.bonoIncentivo}">
    <li class="fieldcontain">
        <span id="bonoIncentivo-label" class="property-label"><g:message code="liquidacionDeCobrePlata.bonoIncentivo.label" default="Bono Incentivo" /></span>

        <span class="property-value" aria-labelledby="bonoIncentivo-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="bonoIncentivo"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.valorDeCompra}">
    <li class="fieldcontain">
        <span id="valorDeCompra-label" class="property-label"><g:message code="liquidacionDeCobrePlata.valorDeCompra.label" default="Valor De Compra" /></span>

        <span class="property-value" aria-labelledby="valorDeCompra-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="valorDeCompra"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.porcentajeRegalia}">
    <li class="fieldcontain">
        <span id="porcentajeRegalia-label" class="property-label"><g:message code="liquidacionDeCobrePlata.porcentajeRegalia.label" default="Porcentaje Regalia" /></span>

        <span class="property-value" aria-labelledby="porcentajeRegalia-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="porcentajeRegalia"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.regaliaMinera}">
    <li class="fieldcontain">
        <span id="regaliaMinera-label" class="property-label"><g:message code="liquidacionDeCobrePlata.regaliaMinera.label" default="Regalia Minera" /></span>

        <span class="property-value" aria-labelledby="regaliaMinera-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="regaliaMinera"/></span>

    </li>
</g:if>

<h1 style="font-weight: bold; font-size: 12px">Retenciones</h1>
<g:hiddenField name="retenciones" value="${liquidacionDeCobrePlataInstance?.retenciones}"/>
<div style="width: 700px; margin-left: auto; margin-right: auto;"><table id="tablaRetenciones"></table></div>

<g:if test="${liquidacionDeCobrePlataInstance?.totalRetenciones}">
    <li class="fieldcontain">
        <span id="totalRetenciones-label" class="property-label"><g:message code="liquidacionDeCobrePlata.totalRetenciones.label" default="Total Retenciones" /></span>

        <span class="property-value" aria-labelledby="totalRetenciones-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="totalRetenciones"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.totalPagado}">
    <li class="fieldcontain">
        <span id="totalPagado-label" class="property-label"><g:message code="liquidacionDeCobrePlata.totalPagado.label" default="Total Pagado" /></span>

        <span class="property-value" aria-labelledby="totalPagado-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="totalPagado"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.totalAnticiposContraEntrega}">
    <li class="fieldcontain">
        <span id="totalAnticiposContraEntrega-label" class="property-label"><g:message code="liquidacionDeCobrePlata.totalAnticiposContraEntrega.label" default="Total Anticipos Contra Entrega" /></span>

        <span class="property-value" aria-labelledby="totalAnticiposContraEntrega-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="totalAnticiposContraEntrega"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.totalAnticiposContraFuturaEntrega}">
    <li class="fieldcontain">
        <span id="totalAnticiposContraFuturaEntrega-label" class="property-label"><g:message code="liquidacionDeCobrePlata.totalAnticiposContraFuturaEntrega.label" default="Total Anticipos Contra Futura Entrega" /></span>

        <span class="property-value" aria-labelledby="totalAnticiposContraFuturaEntrega-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="totalAnticiposContraFuturaEntrega"/></span>

    </li>
</g:if>

<li class="fieldcontain">
    <span id="totalLiquidoPagable-label" class="property-label"><g:message code="liquidacionDeCobrePlata.totalLiquidoPagable.label" default="Total Liquido Pagable" /></span>

    <span class="property-value" aria-labelledby="totalLiquidoPagable-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="totalLiquidoPagable"/></span>

</li>

<g:if test="${liquidacionDeCobrePlataInstance?.conjuntoCobrePlata}">
    <li class="fieldcontain">
        <span id="conjuntoCobrePlata-label" class="property-label"><g:message code="liquidacionDeCobrePlata.conjuntoCobrePlata.label" default="Conjunto de CobrePlata" /></span>

        <span class="property-value" aria-labelledby="conjuntoCobrePlata-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="conjuntoCobrePlata"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.observaciones}">
    <li class="fieldcontain">
        <span id="observaciones-label" class="property-label"><g:message code="liquidacionDeCobrePlata.observaciones.label" default="Observaciones" /></span>

        <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="observaciones"/></span>

    </li>
</g:if>

<g:if test="${liquidacionDeCobrePlataInstance?.motivoDeModificacion}">
    <li class="fieldcontain">
        <span id="motivoDeModificacion-label" class="property-label"><g:message code="liquidacionDeCobrePlata.motivoDeModificacion.label" default="Motivo De Modificacion" /></span>

        <span class="property-value" aria-labelledby="motivoDeModificacion-label"><g:fieldValue bean="${liquidacionDeCobrePlataInstance}" field="motivoDeModificacion"/></span>

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
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'detalleLaboratorio1', 'error')}">
            ${liquidacionDeCobrePlataInstance.detalleLaboratorio1}
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'costoLaboratorio1', 'error')}">
            ${liquidacionDeCobrePlataInstance.costoLaboratorio1}
        </td>
    </tr>
    <tr>
        <g:if test="${liquidacionDeCobrePlataInstance?.detalleLaboratorio2}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'detalleLaboratorio2', 'error')}">
                ${liquidacionDeCobrePlataInstance.detalleLaboratorio2}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'costoLaboratorio2', 'error')}">
                ${liquidacionDeCobrePlataInstance.costoLaboratorio2}
            </td>
        </g:if>
    </tr>
    <tr>
        <g:if test="${liquidacionDeCobrePlataInstance?.detalleLaboratorio3}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'detalleLaboratorio3', 'error')}">
                ${liquidacionDeCobrePlataInstance.detalleLaboratorio3}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'costoLaboratorio3', 'error')}">
                ${liquidacionDeCobrePlataInstance.costoLaboratorio3}
            </td>
        </g:if>
    </tr>
    <tr>
        <g:if test="${liquidacionDeCobrePlataInstance?.detalleLaboratorio4}">
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'detalleLaboratorio4', 'error')}">
                ${liquidacionDeCobrePlataInstance.detalleLaboratorio4}
            </td>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'costoLaboratorio4', 'error')}">
                ${liquidacionDeCobrePlataInstance.costoLaboratorio4}
            </td>
        </g:if>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="liquidacionDeCobrePlata.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: liquidacionDeCobrePlataInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: liquidacionDeCobrePlataInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="liquidoPagable" value="${liquidacionDeCobrePlataInstance?.totalLiquidoPagable}" />
<g:if test="${liquidacionDeCobrePlataInstance?.totalLiquidoPagable<0}">
    <h1 style="font-weight: bold">Enlace a Anticipo Contra Futura Entrega (Clic en enlace para desplegar formulario)</h1>
    <li class="fieldcontain">
        <label style="font-size: 16px; font-weight: bold; color: red; width: 70%">
            <g:link controller="anticipoContraFuturaEntrega" action="show" id="${org.socymet.anticipos.AnticipoContraFuturaEntrega.findByLiquidacionId(liquidacionDeCobrePlataInstance.id).id}" target="_blank" style="color: red"> ANTICIPO CONTRA FUTURA ENTREGA GENERADO!</g:link>
        </label>
    </li>
</g:if>

</ol>
<fieldset class="buttons">
    <div style="float: left">
        <g:form>
            <g:hiddenField name="id" value="${liquidacionDeCobrePlataInstance?.id}" />
            <g:if test="${liquidacionDeCobrePlataInstance.fechaDeCancelacion!=(new java.util.Date(84,5,14))&&liquidacionDeCobrePlataInstance.fechaDeCancelacion}">
                <span id="observaciones-label" class="property-label" style="font-weight: bold; color: green">CANCELADO</span>
            </g:if>
            <g:if test="${liquidacionDeCobrePlataInstance.fechaDeCancelacion==(new java.util.Date(84,5,14))&&liquidacionDeCobrePlataInstance.fechaDeCancelacion}">
                <span id="observaciones-label" class="property-label" style="font-weight: bold; color: red">SIN CANCELAR</span>
            </g:if>
            <g:link class="edit" action="edit" id="${liquidacionDeCobrePlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </g:form>
    </div>
    <div style="float: right">
        <g:jasperReport controller="liquidacionDeCobrePlata" action="crearReporte" jasper="liquidacion_cobre_plata" format="PDF,RTF" name="ReporteLiquidacion${liquidacionDeCobrePlataInstance.lote}">
            <input type="hidden" name="LIQ_SN_ID" value="${liquidacionDeCobrePlataInstance.id}" />
        </g:jasperReport>
    </div>
</fieldset>
<div style="display:none;" class="nav_up" id="nav_up"></div>
<div style="display:none;" class="nav_down" id="nav_down"></div>
</div>
</body>
</html>
