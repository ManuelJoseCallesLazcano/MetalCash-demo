
<%@ page import="org.socymet.liquidacion.EliminacionLoteConjuntoPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'eliminacionLoteConjuntoPlata.label', default: 'EliminacionLoteConjuntoPlata')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-eliminacionLoteConjuntoPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-eliminacionLoteConjuntoPlata" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list eliminacionLoteConjuntoPlata">

        <g:if test="${eliminacionLoteConjuntoPlataInstance?.lote}">
            <li class="fieldcontain">
                <span id="lote-label" class="property-label"><g:message code="eliminacionLoteConjuntoPlata.lote.label" default="Lote" /></span>

                <span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${eliminacionLoteConjuntoPlataInstance}" field="lote"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoPlataInstance?.nombreCliente}">
            <li class="fieldcontain">
                <span id="nombreCliente-label" class="property-label"><g:message code="eliminacionLoteConjuntoPlata.nombreCliente.label" default="Nombre Cliente" /></span>

                <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${eliminacionLoteConjuntoPlataInstance}" field="nombreCliente"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoPlataInstance?.nombreEmpresa}">
            <li class="fieldcontain">
                <span id="nombreEmpresa-label" class="property-label"><g:message code="eliminacionLoteConjuntoPlata.nombreEmpresa.label" default="Nombre Empresa" /></span>

                <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${eliminacionLoteConjuntoPlataInstance}" field="nombreEmpresa"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoPlataInstance?.fechaDeRecepcion}">
            <li class="fieldcontain">
                <span id="fechaDeRecepcion-label" class="property-label"><g:message code="eliminacionLoteConjuntoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

                <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${eliminacionLoteConjuntoPlataInstance}" field="fechaDeRecepcion"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoPlataInstance?.fechaDeLiquidacion}">
            <li class="fieldcontain">
                <span id="fechaDeLiquidacion-label" class="property-label"><g:message code="eliminacionLoteConjuntoPlata.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

                <span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:fieldValue bean="${eliminacionLoteConjuntoPlataInstance}" field="fechaDeLiquidacion"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoPlataInstance?.kilosNetosSecos}">
            <li class="fieldcontain">
                <span id="kilosNetosSecos-label" class="property-label"><g:message code="eliminacionLoteConjuntoPlata.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>

                <span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${eliminacionLoteConjuntoPlataInstance}" field="kilosNetosSecos"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoPlataInstance?.porcentajePlata}">
            <li class="fieldcontain">
                <span id="porcentajePlata-label" class="property-label"><g:message code="eliminacionLoteConjuntoPlata.porcentajePlata.label" default="Porcentaje Plata" /></span>

                <span class="property-value" aria-labelledby="porcentajePlata-label"><g:fieldValue bean="${eliminacionLoteConjuntoPlataInstance}" field="porcentajePlata"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoPlataInstance?.fechaDeAsignacion}">
            <li class="fieldcontain">
                <span id="fechaDeAsignacion-label" class="property-label"><g:message code="eliminacionLoteConjuntoPlata.fechaDeAsignacion.label" default="Fecha De Asignacion" /></span>

                <span class="property-value" aria-labelledby="fechaDeAsignacion-label"><g:formatDate date="${eliminacionLoteConjuntoPlataInstance?.fechaDeAsignacion}" /></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoPlataInstance?.conjuntoOriginal}">
            <li class="fieldcontain">
                <span id="conjuntoOriginal-label" class="property-label"><g:message code="eliminacionLoteConjuntoPlata.conjuntoOriginal.label" default="Conjunto Original" /></span>

                <span class="property-value" aria-labelledby="conjuntoOriginal-label"><g:fieldValue bean="${eliminacionLoteConjuntoPlataInstance}" field="conjuntoOriginal"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoPlataInstance?.motivo}">
            <li class="fieldcontain">
                <span id="motivo-label" class="property-label"><g:message code="eliminacionLoteConjuntoPlata.motivo.label" default="Motivo" /></span>

                <span class="property-value" aria-labelledby="motivo-label"><g:fieldValue bean="${eliminacionLoteConjuntoPlataInstance}" field="motivo"/></span>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${eliminacionLoteConjuntoPlataInstance?.id}" />
            <g:link class="edit" action="edit" id="${eliminacionLoteConjuntoPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
