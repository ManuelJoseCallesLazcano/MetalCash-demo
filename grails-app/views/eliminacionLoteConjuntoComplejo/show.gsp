
<%@ page import="org.socymet.liquidacion.EliminacionLoteConjuntoComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'eliminacionLoteConjuntoComplejo.label', default: 'EliminacionLoteConjuntoComplejo')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-eliminacionLoteConjuntoComplejo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-eliminacionLoteConjuntoComplejo" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list eliminacionLoteConjuntoComplejo">

        <g:if test="${eliminacionLoteConjuntoComplejoInstance?.lote}">
            <li class="fieldcontain">
                <span id="lote-label" class="property-label"><g:message code="eliminacionLoteConjuntoComplejo.lote.label" default="Lote" /></span>

                <span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${eliminacionLoteConjuntoComplejoInstance}" field="lote"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoComplejoInstance?.nombreCliente}">
            <li class="fieldcontain">
                <span id="nombreCliente-label" class="property-label"><g:message code="eliminacionLoteConjuntoComplejo.nombreCliente.label" default="Nombre Cliente" /></span>

                <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${eliminacionLoteConjuntoComplejoInstance}" field="nombreCliente"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoComplejoInstance?.nombreEmpresa}">
            <li class="fieldcontain">
                <span id="nombreEmpresa-label" class="property-label"><g:message code="eliminacionLoteConjuntoComplejo.nombreEmpresa.label" default="Nombre Empresa" /></span>

                <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${eliminacionLoteConjuntoComplejoInstance}" field="nombreEmpresa"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoComplejoInstance?.fechaDeRecepcion}">
            <li class="fieldcontain">
                <span id="fechaDeRecepcion-label" class="property-label"><g:message code="eliminacionLoteConjuntoComplejo.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

                <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${eliminacionLoteConjuntoComplejoInstance}" field="fechaDeRecepcion"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoComplejoInstance?.fechaDeLiquidacion}">
            <li class="fieldcontain">
                <span id="fechaDeLiquidacion-label" class="property-label"><g:message code="eliminacionLoteConjuntoComplejo.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

                <span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:fieldValue bean="${eliminacionLoteConjuntoComplejoInstance}" field="fechaDeLiquidacion"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoComplejoInstance?.kilosNetosSecos}">
            <li class="fieldcontain">
                <span id="kilosNetosSecos-label" class="property-label"><g:message code="eliminacionLoteConjuntoComplejo.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>

                <span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${eliminacionLoteConjuntoComplejoInstance}" field="kilosNetosSecos"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoComplejoInstance?.porcentajeZinc}">
            <li class="fieldcontain">
                <span id="porcentajeZinc-label" class="property-label"><g:message code="eliminacionLoteConjuntoComplejo.porcentajeZinc.label" default="Porcentaje Zinc" /></span>

                <span class="property-value" aria-labelledby="porcentajeZinc-label"><g:fieldValue bean="${eliminacionLoteConjuntoComplejoInstance}" field="porcentajeZinc"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoComplejoInstance?.porcentajePlomo}">
            <li class="fieldcontain">
                <span id="porcentajePlomo-label" class="property-label"><g:message code="eliminacionLoteConjuntoComplejo.porcentajePlomo.label" default="Porcentaje Plomo" /></span>

                <span class="property-value" aria-labelledby="porcentajePlomo-label"><g:fieldValue bean="${eliminacionLoteConjuntoComplejoInstance}" field="porcentajePlomo"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoComplejoInstance?.porcentajePlata}">
            <li class="fieldcontain">
                <span id="porcentajePlata-label" class="property-label"><g:message code="eliminacionLoteConjuntoComplejo.porcentajePlata.label" default="Porcentaje Plata" /></span>

                <span class="property-value" aria-labelledby="porcentajePlata-label"><g:fieldValue bean="${eliminacionLoteConjuntoComplejoInstance}" field="porcentajePlata"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoComplejoInstance?.fechaDeAsignacion}">
            <li class="fieldcontain">
                <span id="fechaDeAsignacion-label" class="property-label"><g:message code="eliminacionLoteConjuntoComplejo.fechaDeAsignacion.label" default="Fecha De Asignacion" /></span>

                <span class="property-value" aria-labelledby="fechaDeAsignacion-label"><g:formatDate date="${eliminacionLoteConjuntoComplejoInstance?.fechaDeAsignacion}" /></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoComplejoInstance?.conjuntoOriginal}">
            <li class="fieldcontain">
                <span id="conjuntoOriginal-label" class="property-label"><g:message code="eliminacionLoteConjuntoComplejo.conjuntoOriginal.label" default="Conjunto Original" /></span>

                <span class="property-value" aria-labelledby="conjuntoOriginal-label"><g:fieldValue bean="${eliminacionLoteConjuntoComplejoInstance}" field="conjuntoOriginal"/></span>

            </li>
        </g:if>

        <g:if test="${eliminacionLoteConjuntoComplejoInstance?.motivo}">
            <li class="fieldcontain">
                <span id="motivo-label" class="property-label"><g:message code="eliminacionLoteConjuntoComplejo.motivo.label" default="Motivo" /></span>

                <span class="property-value" aria-labelledby="motivo-label"><g:fieldValue bean="${eliminacionLoteConjuntoComplejoInstance}" field="motivo"/></span>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${eliminacionLoteConjuntoComplejoInstance?.id}" />
            <g:link class="edit" action="edit" id="${eliminacionLoteConjuntoComplejoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
