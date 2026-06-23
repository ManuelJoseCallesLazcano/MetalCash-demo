
<%@ page import="org.socymet.liquidacion.AsignacionLoteConjuntoWolfran" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'asignacionLoteConjuntoWolfran.label', default: 'AsignacionLoteConjuntoWolfran')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-asignacionLoteConjuntoWolfran" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-asignacionLoteConjuntoWolfran" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list asignacionLoteConjuntoWolfran">

        <g:if test="${asignacionLoteConjuntoWolfranInstance?.lote}">
            <li class="fieldcontain">
                <span id="lote-label" class="property-label"><g:message code="asignacionLoteConjuntoWolfran.lote.label" default="Lote" /></span>

                <span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${asignacionLoteConjuntoWolfranInstance}" field="lote"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoWolfranInstance?.nombreCliente}">
            <li class="fieldcontain">
                <span id="nombreCliente-label" class="property-label"><g:message code="asignacionLoteConjuntoWolfran.nombreCliente.label" default="Nombre Cliente" /></span>

                <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${asignacionLoteConjuntoWolfranInstance}" field="nombreCliente"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoWolfranInstance?.nombreEmpresa}">
            <li class="fieldcontain">
                <span id="nombreEmpresa-label" class="property-label"><g:message code="asignacionLoteConjuntoWolfran.nombreEmpresa.label" default="Nombre Empresa" /></span>

                <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${asignacionLoteConjuntoWolfranInstance}" field="nombreEmpresa"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoWolfranInstance?.fechaDeRecepcion}">
            <li class="fieldcontain">
                <span id="fechaDeRecepcion-label" class="property-label"><g:message code="asignacionLoteConjuntoWolfran.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

                <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${asignacionLoteConjuntoWolfranInstance}" field="fechaDeRecepcion"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoWolfranInstance?.fechaDeLiquidacion}">
            <li class="fieldcontain">
                <span id="fechaDeLiquidacion-label" class="property-label"><g:message code="asignacionLoteConjuntoWolfran.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

                <span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:fieldValue bean="${asignacionLoteConjuntoWolfranInstance}" field="fechaDeLiquidacion"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoWolfranInstance?.kilosNetosSecos}">
            <li class="fieldcontain">
                <span id="kilosNetosSecos-label" class="property-label"><g:message code="asignacionLoteConjuntoWolfran.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>

                <span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${asignacionLoteConjuntoWolfranInstance}" field="kilosNetosSecos"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoWolfranInstance?.porcentajeWolfran}">
            <li class="fieldcontain">
                <span id="porcentajeWolfran-label" class="property-label"><g:message code="asignacionLoteConjuntoWolfran.porcentajeWolfran.label" default="Porcentaje Wolfran" /></span>

                <span class="property-value" aria-labelledby="porcentajeWolfran-label"><g:fieldValue bean="${asignacionLoteConjuntoWolfranInstance}" field="porcentajeWolfran"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoWolfranInstance?.fechaDeAsignacion}">
            <li class="fieldcontain">
                <span id="fechaDeAsignacion-label" class="property-label"><g:message code="asignacionLoteConjuntoWolfran.fechaDeAsignacion.label" default="Fecha De Asignacion" /></span>

                <span class="property-value" aria-labelledby="fechaDeAsignacion-label"><g:formatDate date="${asignacionLoteConjuntoWolfranInstance?.fechaDeAsignacion}" /></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoWolfranInstance?.conjuntoDestino}">
            <li class="fieldcontain">
                <span id="conjuntoDestino-label" class="property-label"><g:message code="asignacionLoteConjuntoWolfran.conjuntoDestino.label" default="Conjunto Destino" /></span>

                <span class="property-value" aria-labelledby="conjuntoDestino-label"><g:fieldValue bean="${asignacionLoteConjuntoWolfranInstance}" field="conjuntoDestino"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoWolfranInstance?.motivo}">
            <li class="fieldcontain">
                <span id="motivo-label" class="property-label"><g:message code="asignacionLoteConjuntoWolfran.motivo.label" default="Motivo" /></span>

                <span class="property-value" aria-labelledby="motivo-label"><g:fieldValue bean="${asignacionLoteConjuntoWolfranInstance}" field="motivo"/></span>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${asignacionLoteConjuntoWolfranInstance?.id}" />
            <g:link class="edit" action="edit" id="${asignacionLoteConjuntoWolfranInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
