<%@ page import="org.socymet.org.socymet.reportes.ReporteCompositoDeLotes" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
    <style type="text/css" media="screen">
    body{
        max-width: 1100px;
    }
    .ui-jqgrid tr.jqgrow td {
        font-size: 10px
    }
    th.ui-th-column div{
        white-space:normal !important;
        height:auto !important;
        padding:2px;
        font-size: 10px;
    }
    </style>
    <g:javascript src="jquery-1.10.1.min.js" />
    <g:javascript src="i18n/grid.locale-es.js" />
    <g:javascript src="jquery.jqGrid.min.js" />
    <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
    <g:javascript src="jquery.jgrowl.min.js" />
    <g:javascript src="notify.min.js" />
    <g:javascript src="NumerosALetras.js" />
    <g:javascript src="reportes/compositoLotesShow.js" />
    %{--<link rel="stylesheet" href="../css/style.css" type="text/css" media="screen"/>--}%
    %{--<script type='text/javascript' src='../js/menu_jquery.js'></script>--}%
    <script>
        $(document).ready(function() {
            $("#vista").val("create");
            $("#agregar").attr("disabled",false);
            $("#quitar").attr("disabled",false);
            $("#actualizar").attr("disabled",true);
        });
    </script>
</head>
<body>
<a href="#show-reporteCompositoDeLotes" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-reporteCompositoDeLotes" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list reporteCompositoDeLotes">

%{--<g:if test="${reporteCompositoDeLotesInstance?.numeroComposito}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="numeroComposito-label" class="property-label"><g:message code="reporteCompositoDeLotes.numeroComposito.label" default="Numero Composito" /></span>--}%

%{--        <span class="property-value" aria-labelledby="numeroComposito-label"><g:formatNumber number="${reporteCompositoDeLotesInstance.numeroComposito}" format="000000"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

<g:if test="${reporteCompositoDeLotesInstance?.sigla}">
    <li class="fieldcontain">
        <span id="sigla-label" class="property-label"><g:message code="reporteCompositoDeLotes.sigla.label" default="Sigla" /></span>

        <span class="property-value" aria-labelledby="sigla-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="sigla"/></span>

    </li>
</g:if>

<g:if test="${reporteCompositoDeLotesInstance?.destino}">
    <li class="fieldcontain">
        <span id="destino-label" class="property-label"><g:message code="reporteCompositoDeLotes.destino.label" default="Destino" /></span>

        <span class="property-value" aria-labelledby="destino-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="destino"/></span>

    </li>
</g:if>

<g:if test="${reporteCompositoDeLotesInstance?.destino.equals("VENTA") || reporteCompositoDeLotesInstance?.destino.equals("EXPORTACION")}">
    <li class="fieldcontain">
        <span id="comprador-label" class="property-label"><g:message code="reporteCompositoDeLotes.comprador.label" default="Comprador" /></span>

        <span class="property-value" aria-labelledby="comprador-label"><g:link controller="comprador" action="show" id="${reporteCompositoDeLotesInstance?.comprador?.id}">${reporteCompositoDeLotesInstance?.comprador?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<g:if test="${reporteCompositoDeLotesInstance?.destino.equals("INGENIO")}">
    <li class="fieldcontain">
        <span id="ingenio-label" class="property-label"><g:message code="reporteCompositoDeLotes.ingenio.label" default="Ingenio" /></span>

        <span class="property-value" aria-labelledby="ingenio-label"><g:link controller="ingenio" action="show" id="${reporteCompositoDeLotesInstance?.ingenio?.id}">${reporteCompositoDeLotesInstance?.ingenio?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<g:if test="${reporteCompositoDeLotesInstance?.elaboradoPor}">
    <li class="fieldcontain">
        <span id="elaboradoPor-label" class="property-label"><g:message code="reporteCompositoDeLotes.elaboradoPor.label" default="Elaborado Por" /></span>

        <span class="property-value" aria-labelledby="elaboradoPor-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="elaboradoPor"/></span>

    </li>
</g:if>

<g:if test="${reporteCompositoDeLotesInstance?.fechaDeElaboracion}">
    <li class="fieldcontain">
        <span id="fechaDeElaboracion-label" class="property-label"><g:message code="reporteCompositoDeLotes.fechaDeElaboracion.label" default="Fecha De Elaboracion" /></span>

        <span class="property-value" aria-labelledby="fechaDeElaboracion-label"><g:formatDate date="${reporteCompositoDeLotesInstance?.fechaDeElaboracion}" /></span>

    </li>
</g:if>

<g:if test="${reporteCompositoDeLotesInstance?.estadoDelComposito}">
    <li class="fieldcontain">
        <span id="estadoDelComposito-label" class="property-label"><g:message code="reporteCompositoDeLotes.estadoDelComposito.label" default="Estado Del Composito" /></span>

        <span class="property-value" aria-labelledby="estadoDelComposito-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="estadoDelComposito"/></span>

    </li>
</g:if>

<g:if test="${reporteCompositoDeLotesInstance?.empresa}">
    <li class="fieldcontain">
        <span id="empresa-label" class="property-label"><g:message code="reporteCompositoDeLotes.empresa.label" default="Empresa" /></span>

        <span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${reporteCompositoDeLotesInstance?.empresa?.id}">${reporteCompositoDeLotesInstance?.empresa?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

%{--    <g:if test="${reporteCompositoDeLotesInstance?.fechaInicial}">--}%
%{--        <li class="fieldcontain">--}%
%{--            <span id="fechaInicial-label" class="property-label"><g:message code="reporteCompositoDeLotes.fechaInicial.label" default="Fecha Inicial" /></span>--}%

%{--            <span class="property-value" aria-labelledby="fechaInicial-label"><g:formatDate date="${reporteCompositoDeLotesInstance?.fechaInicial}" /></span>--}%

%{--        </li>--}%
%{--    </g:if>--}%

%{--    <g:if test="${reporteCompositoDeLotesInstance?.fechaFinal}">--}%
%{--        <li class="fieldcontain">--}%
%{--            <span id="fechaFinal-label" class="property-label"><g:message code="reporteCompositoDeLotes.fechaFinal.label" default="Fecha Final" /></span>--}%

%{--            <span class="property-value" aria-labelledby="fechaFinal-label"><g:formatDate date="${reporteCompositoDeLotesInstance?.fechaFinal}" /></span>--}%

%{--        </li>--}%
%{--    </g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.leyMinimaZinc}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="leyMinimaZinc-label" class="property-label"><g:message code="reporteCompositoDeLotes.leyMinimaZinc.label" default="Ley Minima Zinc" /></span>--}%

%{--        <span class="property-value" aria-labelledby="leyMinimaZinc-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="leyMinimaZinc"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.leyMaximaZinc}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="leyMaximaZinc-label" class="property-label"><g:message code="reporteCompositoDeLotes.leyMaximaZinc.label" default="Ley Maxima Zinc" /></span>--}%

%{--        <span class="property-value" aria-labelledby="leyMaximaZinc-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="leyMaximaZinc"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.leyMinimaPlomo}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="leyMinimaPlomo-label" class="property-label"><g:message code="reporteCompositoDeLotes.leyMinimaPlomo.label" default="Ley Minima Plomo" /></span>--}%

%{--        <span class="property-value" aria-labelledby="leyMinimaPlomo-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="leyMinimaPlomo"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.leyMaximaPlomo}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="leyMaximaPlomo-label" class="property-label"><g:message code="reporteCompositoDeLotes.leyMaximaPlomo.label" default="Ley Maxima Plomo" /></span>--}%

%{--        <span class="property-value" aria-labelledby="leyMaximaPlomo-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="leyMaximaPlomo"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.leyMinimaPlata}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="leyMinimaPlata-label" class="property-label"><g:message code="reporteCompositoDeLotes.leyMinimaPlata.label" default="Ley Minima Plata" /></span>--}%

%{--        <span class="property-value" aria-labelledby="leyMinimaPlata-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="leyMinimaPlata"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.leyMaximaPlata}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="leyMaximaPlata-label" class="property-label"><g:message code="reporteCompositoDeLotes.leyMaximaPlata.label" default="Ley Maxima Plata" /></span>--}%

%{--        <span class="property-value" aria-labelledby="leyMaximaPlata-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="leyMaximaPlata"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

    <h1 style="font-weight: bold">Lotes en Comp&oacute;sito</h1>

    <div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'lotesComposito', 'error')} " style="display: none">
        <label for="lotesComposito">
            <g:message code="reporteCompositoDeLotesInstance.lotesComposito.label" default="Lotes Composito" />

        </label>
        <g:textArea name="lotesComposito" cols="40" rows="5" value="${reporteCompositoDeLotesInstance?.lotesComposito}" readonly="readonly"/>
    </div>

    <div style="width: 1000px; margin-left: auto; margin-right: auto;">
        <table id="lotesCompositoTabla"></table>
    </div>

    <h1 style="font-weight: bold">Participaci&oacute;n</h1>

    <div class="fieldcontain ${hasErrors(bean: reporteCompositoDeLotesInstance, field: 'participacion', 'error')} " style="display: none">
        <label for="participacion">
            <g:message code="reporteCompositoDeLotes.participacion.label" default="Participacion" />

        </label>
        <g:textArea name="participacion" cols="40" rows="5" maxlength="2000000" value="${reporteCompositoDeLotesInstance?.participacion}" readonly="readonly"/>
    </div>

    <div style="width: 400px; margin-left: auto; margin-right: auto;">
        <table id="participacionTabla"></table>
    </div>

%{--<g:if test="${reporteCompositoDeLotesInstance?.lotes}">--}%
%{--<li class="fieldcontain">--}%
%{--<span id="lotes-label" class="property-label"><g:message code="reporteCompositoDeLotes.lotes.label" default="Lotes" /></span>--}%
%{----}%
%{--<span class="property-value" aria-labelledby="lotes-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="lotes"/></span>--}%
%{----}%
%{--</li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.totalKilosBrutos}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="totalKilosBrutos-label" class="property-label"><g:message code="reporteCompositoDeLotes.totalKilosBrutos.label" default="Total Kilos Brutos" /></span>--}%

%{--        <span class="property-value" aria-labelledby="totalKilosBrutos-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="totalKilosBrutos"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.totalKilosNetosSecos}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="totalKilosNetosSecos-label" class="property-label"><g:message code="reporteCompositoDeLotes.totalKilosNetosSecos.label" default="Total Kilos Netos Secos" /></span>--}%

%{--        <span class="property-value" aria-labelledby="totalKilosNetosSecos-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="totalKilosNetosSecos"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.leyPromedioZinc}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="leyPromedioZinc-label" class="property-label"><g:message code="reporteCompositoDeLotes.leyPromedioZinc.label" default="Ley Promedio Zinc" /></span>--}%

%{--        <span class="property-value" aria-labelledby="leyPromedioZinc-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="leyPromedioZinc"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.leyPromedioPlomo}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="leyPromedioPlomo-label" class="property-label"><g:message code="reporteCompositoDeLotes.leyPromedioPlomo.label" default="Ley Promedio Plomo" /></span>--}%

%{--        <span class="property-value" aria-labelledby="leyPromedioPlomo-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="leyPromedioPlomo"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.leyPromedioPlata}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="leyPromedioPlata-label" class="property-label"><g:message code="reporteCompositoDeLotes.leyPromedioPlata.label" default="Ley Promedio Plata" /></span>--}%

%{--        <span class="property-value" aria-labelledby="leyPromedioPlata-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="leyPromedioPlata"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.totalKilosFinosZinc}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="totalKilosFinosZinc-label" class="property-label"><g:message code="reporteCompositoDeLotes.totalKilosFinosZinc.label" default="Total Kilos Finos Zinc" /></span>--}%

%{--        <span class="property-value" aria-labelledby="totalKilosFinosZinc-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="totalKilosFinosZinc"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.totalKilosFinosPlomo}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="totalKilosFinosPlomo-label" class="property-label"><g:message code="reporteCompositoDeLotes.totalKilosFinosPlomo.label" default="Total Kilos Finos Plomo" /></span>--}%

%{--        <span class="property-value" aria-labelledby="totalKilosFinosPlomo-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="totalKilosFinosPlomo"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.totalKilosFinosPlata}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="totalKilosFinosPlata-label" class="property-label"><g:message code="reporteCompositoDeLotes.totalKilosFinosPlata.label" default="Total Kilos Finos Plata" /></span>--}%

%{--        <span class="property-value" aria-labelledby="totalKilosFinosPlata-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="totalKilosFinosPlata"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.totalValorNeto}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="totalValorNeto-label" class="property-label"><g:message code="reporteCompositoDeLotes.totalValorNeto.label" default="Total Valor Neto" /></span>--}%

%{--        <span class="property-value" aria-labelledby="totalValorNeto-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="totalValorNeto"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.totalValorDeCompra}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="totalValorDeCompra-label" class="property-label"><g:message code="reporteCompositoDeLotes.totalValorDeCompra.label" default="Total Valor De Compra" /></span>--}%

%{--        <span class="property-value" aria-labelledby="totalValorDeCompra-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="totalValorDeCompra"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.estadoDeAprobacion}">--}%
    %{--<li class="fieldcontain">--}%
        %{--<span id="estadoDeAprobacion-label" class="property-label"><g:message code="reporteCompositoDeLotes.estadoDeAprobacion.label" default="Estado De Aprobacion" /></span>--}%

        %{--<span class="property-value" aria-labelledby="estadoDeAprobacion-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="estadoDeAprobacion"/></span>--}%

    %{--</li>--}%
%{--</g:if>--}%

%{--<g:if test="${!reporteCompositoDeLotesInstance?.aprobadoPor?.equals("?")}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="aprobadoPor-label" class="property-label"><g:message code="reporteCompositoDeLotes.aprobadoPor.label" default="Aprobado Por" /></span>--}%

%{--        <span class="property-value" aria-labelledby="aprobadoPor-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="aprobadoPor"/></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

%{--<g:if test="${reporteCompositoDeLotesInstance?.aprobador}">--}%
%{--    <li class="fieldcontain">--}%
%{--        <span id="aprobador-label" class="property-label"><g:message code="reporteCompositoDeLotes.aprobador.label" default="Aprobador" /></span>--}%

%{--        <span class="property-value" aria-labelledby="aprobador-label"><g:link controller="secUser" action="show" id="${reporteCompositoDeLotesInstance?.aprobador?.id}">${reporteCompositoDeLotesInstance?.aprobador?.encodeAsHTML()}</g:link></span>--}%

%{--    </li>--}%
%{--</g:if>--}%

<g:if test="${reporteCompositoDeLotesInstance?.observaciones}">
    <li class="fieldcontain">
        <span id="observaciones-label" class="property-label"><g:message code="reporteCompositoDeLotes.observaciones.label" default="Observaciones" /></span>

        <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${reporteCompositoDeLotesInstance}" field="observaciones"/></span>

    </li>
</g:if>

%{--<g:if test="${reporteCompositoDeLotesInstance?.usuario}">--}%
%{--<li class="fieldcontain">--}%
%{--<span id="usuario-label" class="property-label"><g:message code="reporteCompositoDeLotes.usuario.label" default="Usuario" /></span>--}%
%{----}%
%{--<span class="property-value" aria-labelledby="usuario-label"><g:link controller="secUser" action="show" id="${reporteCompositoDeLotesInstance?.usuario?.id}">${reporteCompositoDeLotesInstance?.usuario?.encodeAsHTML()}</g:link></span>--}%
%{----}%
%{--</li>--}%
%{--</g:if>--}%

</ol>
%{--<g:form>--}%
    %{--<fieldset class="buttons">--}%
        %{--<g:hiddenField name="id" value="${reporteCompositoDeLotesInstance?.id}" />--}%
        %{--<g:link class="edit" action="edit" id="${reporteCompositoDeLotesInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
        %{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
    %{--</fieldset>--}%
%{--</g:form>--}%

<fieldset class="buttons">
    <div style="float: left">
        <g:form>
            <g:hiddenField name="id" value="${reporteCompositoDeLotesInstance?.id}" />
            <g:if test="${!reporteCompositoDeLotesInstance?.estadoDelComposito?.equals("DEFINITIVO")}">
                <g:link class="edit" action="edit" id="${reporteCompositoDeLotesInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
%{--                <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
            </g:if>
            <g:else>
                <span style="font-size: 16px; font-weight: bold; color: darkgreen">COMPOSITO DEFINITIVO</span>
            </g:else>
        </g:form>
    </div>
    <div style="float: right">
        <table>
            <tr>
%{--                <td>--}%
%{--                    <g:if test="${!reporteCompositoDeLotesInstance?.aprobadoPor?.equals("?")}">--}%
%{--                        <g:jasperReport controller="reporteCompositoDeLotes" action="createReport" jasper="comprobante_reporte_composito_lotes" format="PDF" name="ORDEN_ENTREGA_${reporteCompositoDeLotesInstance.numeroComposito}_${reporteCompositoDeLotesInstance.sigla}">--}%
%{--                            <input type="hidden" name="reporteCompositoDeLotesId" value="${reporteCompositoDeLotesInstance.id}" />--}%
%{--                        </g:jasperReport>--}%
%{--                    </g:if>--}%
%{--                </td>--}%
                <td style="background-color: #255b17">
                    <g:link controller="reporteCompositoDeLotes" action="crearReporteComposito" params="${[compositoId: reporteCompositoDeLotesInstance.id]}">
                        <img alt="XLS" src="/demo-liquidaciones/plugins/jasper-1.11.0/images/icons/XLS.gif" border="0">
                    </g:link>
                </td>
%{--                <td>--}%
%{--                    <g:jasperReport controller="reporteCompositoDeLotes" action="createReport" jasper="reporte_composito_lotes" format="PDF" name="LOTES_COMPOSITO_${reporteCompositoDeLotesInstance.numeroComposito}_${reporteCompositoDeLotesInstance.sigla}">--}%
%{--                        <input type="hidden" name="reporteCompositoDeLotesId" value="${reporteCompositoDeLotesInstance.id}" />--}%
%{--                    </g:jasperReport>--}%
%{--                </td>--}%

            </tr>
            <tr>
                <td style="background-color: #255b17">
                    <g:link controller="reporteCompositoDeLotes" action="crearReportePDF" params="${[compositoId: reporteCompositoDeLotesInstance.id]}">
                        <img alt="XLS" src="/demo-liquidaciones/images/pdf-download-icon.png" width="30" border="0">
                    </g:link>
                </td>
            </tr>
        </table>
    </div>
</fieldset>
</div>
</body>
</html>
