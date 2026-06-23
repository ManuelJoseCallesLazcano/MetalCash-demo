
<%@ page import="org.socymet.liquidacion.EliminacionLoteConjuntoAntimonio" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'eliminacionLoteConjuntoAntimonio.label', default: 'EliminacionLoteConjuntoAntimonio')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-eliminacionLoteConjuntoAntimonio" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-eliminacionLoteConjuntoAntimonio" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list eliminacionLoteConjuntoAntimonio">

        <g:if test="${eliminacionLoteConjuntoAntimonioInstance?.lote}">
            <li class="fieldcontain">
                <span id="lote-label" class="property-label"><g:message code="eliminacionLoteConjuntoAntimonio.lote.label" default="Lote" /></span>

                <span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${eliminacionLoteConjuntoAntimonioInstance}" field="lote"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoAntimonioInstance?.nombreCliente}">
            <li class="fieldcontain">
                <span id="nombreCliente-label" class="property-label"><g:message code="eliminacionLoteConjuntoAntimonio.nombreCliente.label" default="Nombre Cliente" /></span>

                <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${eliminacionLoteConjuntoAntimonioInstance}" field="nombreCliente"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoAntimonioInstance?.nombreEmpresa}">
            <li class="fieldcontain">
                <span id="nombreEmpresa-label" class="property-label"><g:message code="eliminacionLoteConjuntoAntimonio.nombreEmpresa.label" default="Nombre Empresa" /></span>

                <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${eliminacionLoteConjuntoAntimonioInstance}" field="nombreEmpresa"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoAntimonioInstance?.fechaDeRecepcion}">
            <li class="fieldcontain">
                <span id="fechaDeRecepcion-label" class="property-label"><g:message code="eliminacionLoteConjuntoAntimonio.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

                <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${eliminacionLoteConjuntoAntimonioInstance}" field="fechaDeRecepcion"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoAntimonioInstance?.fechaDeLiquidacion}">
            <li class="fieldcontain">
                <span id="fechaDeLiquidacion-label" class="property-label"><g:message code="eliminacionLoteConjuntoAntimonio.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

                <span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:fieldValue bean="${eliminacionLoteConjuntoAntimonioInstance}" field="fechaDeLiquidacion"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoAntimonioInstance?.kilosNetosSecos}">
            <li class="fieldcontain">
                <span id="kilosNetosSecos-label" class="property-label"><g:message code="eliminacionLoteConjuntoAntimonio.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>

                <span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${eliminacionLoteConjuntoAntimonioInstance}" field="kilosNetosSecos"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoAntimonioInstance?.porcentajeAntimonio}">
            <li class="fieldcontain">
                <span id="porcentajeAntimonio-label" class="property-label"><g:message code="eliminacionLoteConjuntoAntimonio.porcentajeAntimonio.label" default="Porcentaje Antimonio" /></span>

                <span class="property-value" aria-labelledby="porcentajeAntimonio-label"><g:fieldValue bean="${eliminacionLoteConjuntoAntimonioInstance}" field="porcentajeAntimonio"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoAntimonioInstance?.fechaDeAsignacion}">
            <li class="fieldcontain">
                <span id="fechaDeAsignacion-label" class="property-label"><g:message code="eliminacionLoteConjuntoAntimonio.fechaDeAsignacion.label" default="Fecha De Asignacion" /></span>

                <span class="property-value" aria-labelledby="fechaDeAsignacion-label"><g:formatDate date="${eliminacionLoteConjuntoAntimonioInstance?.fechaDeAsignacion}" /></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoAntimonioInstance?.conjuntoOriginal}">
            <li class="fieldcontain">
                <span id="conjuntoOriginal-label" class="property-label"><g:message code="eliminacionLoteConjuntoAntimonio.conjuntoOriginal.label" default="Conjunto Original" /></span>

                <span class="property-value" aria-labelledby="conjuntoOriginal-label"><g:fieldValue bean="${eliminacionLoteConjuntoAntimonioInstance}" field="conjuntoOriginal"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoAntimonioInstance?.motivo}">
            <li class="fieldcontain">
                <span id="motivo-label" class="property-label"><g:message code="eliminacionLoteConjuntoAntimonio.motivo.label" default="Motivo" /></span>

                <span class="property-value" aria-labelledby="motivo-label"><g:fieldValue bean="${eliminacionLoteConjuntoAntimonioInstance}" field="motivo"/></span>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${eliminacionLoteConjuntoAntimonioInstance?.id}" />
            <g:link class="edit" action="edit" id="${eliminacionLoteConjuntoAntimonioInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
