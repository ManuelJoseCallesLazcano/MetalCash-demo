
<%@ page import="org.socymet.liquidacion.EliminacionLoteConjuntoWolfran" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'eliminacionLoteConjuntoWolfran.label', default: 'EliminacionLoteConjuntoWolfran')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-eliminacionLoteConjuntoWolfran" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-eliminacionLoteConjuntoWolfran" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list eliminacionLoteConjuntoWolfran">

        <g:if test="${eliminacionLoteConjuntoWolfranInstance?.lote}">
            <li class="fieldcontain">
                <span id="lote-label" class="property-label"><g:message code="eliminacionLoteConjuntoWolfran.lote.label" default="Lote" /></span>

                <span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${eliminacionLoteConjuntoWolfranInstance}" field="lote"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoWolfranInstance?.nombreCliente}">
            <li class="fieldcontain">
                <span id="nombreCliente-label" class="property-label"><g:message code="eliminacionLoteConjuntoWolfran.nombreCliente.label" default="Nombre Cliente" /></span>

                <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${eliminacionLoteConjuntoWolfranInstance}" field="nombreCliente"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoWolfranInstance?.nombreEmpresa}">
            <li class="fieldcontain">
                <span id="nombreEmpresa-label" class="property-label"><g:message code="eliminacionLoteConjuntoWolfran.nombreEmpresa.label" default="Nombre Empresa" /></span>

                <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${eliminacionLoteConjuntoWolfranInstance}" field="nombreEmpresa"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoWolfranInstance?.fechaDeRecepcion}">
            <li class="fieldcontain">
                <span id="fechaDeRecepcion-label" class="property-label"><g:message code="eliminacionLoteConjuntoWolfran.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

                <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${eliminacionLoteConjuntoWolfranInstance}" field="fechaDeRecepcion"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoWolfranInstance?.fechaDeLiquidacion}">
            <li class="fieldcontain">
                <span id="fechaDeLiquidacion-label" class="property-label"><g:message code="eliminacionLoteConjuntoWolfran.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

                <span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:fieldValue bean="${eliminacionLoteConjuntoWolfranInstance}" field="fechaDeLiquidacion"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoWolfranInstance?.kilosNetosSecos}">
            <li class="fieldcontain">
                <span id="kilosNetosSecos-label" class="property-label"><g:message code="eliminacionLoteConjuntoWolfran.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>

                <span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${eliminacionLoteConjuntoWolfranInstance}" field="kilosNetosSecos"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoWolfranInstance?.porcentajeWolfran}">
            <li class="fieldcontain">
                <span id="porcentajeWolfran-label" class="property-label"><g:message code="eliminacionLoteConjuntoWolfran.porcentajeWolfran.label" default="Porcentaje Wolfran" /></span>

                <span class="property-value" aria-labelledby="porcentajeWolfran-label"><g:fieldValue bean="${eliminacionLoteConjuntoWolfranInstance}" field="porcentajeWolfran"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoWolfranInstance?.fechaDeAsignacion}">
            <li class="fieldcontain">
                <span id="fechaDeAsignacion-label" class="property-label"><g:message code="eliminacionLoteConjuntoWolfran.fechaDeAsignacion.label" default="Fecha De Asignacion" /></span>

                <span class="property-value" aria-labelledby="fechaDeAsignacion-label"><g:formatDate date="${eliminacionLoteConjuntoWolfranInstance?.fechaDeAsignacion}" /></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoWolfranInstance?.conjuntoOriginal}">
            <li class="fieldcontain">
                <span id="conjuntoOriginal-label" class="property-label"><g:message code="eliminacionLoteConjuntoWolfran.conjuntoOriginal.label" default="Conjunto Original" /></span>

                <span class="property-value" aria-labelledby="conjuntoOriginal-label"><g:fieldValue bean="${eliminacionLoteConjuntoWolfranInstance}" field="conjuntoOriginal"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoWolfranInstance?.motivo}">
            <li class="fieldcontain">
                <span id="motivo-label" class="property-label"><g:message code="eliminacionLoteConjuntoWolfran.motivo.label" default="Motivo" /></span>

                <span class="property-value" aria-labelledby="motivo-label"><g:fieldValue bean="${eliminacionLoteConjuntoWolfranInstance}" field="motivo"/></span>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${eliminacionLoteConjuntoWolfranInstance?.id}" />
            <g:link class="edit" action="edit" id="${eliminacionLoteConjuntoWolfranInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
