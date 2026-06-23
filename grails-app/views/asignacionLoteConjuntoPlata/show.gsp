
<%@ page import="org.socymet.liquidacion.AsignacionLoteConjuntoPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'asignacionLoteConjuntoPlata.label', default: 'AsignacionLoteConjuntoPlata')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-asignacionLoteConjuntoPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-asignacionLoteConjuntoPlata" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list asignacionLoteConjuntoPlata">

        <g:if test="${asignacionLoteConjuntoPlataInstance?.lote}">
            <li class="fieldcontain">
                <span id="lote-label" class="property-label"><g:message code="asignacionLoteConjuntoPlata.lote.label" default="Lote" /></span>

                <span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${asignacionLoteConjuntoPlataInstance}" field="lote"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoPlataInstance?.nombreCliente}">
            <li class="fieldcontain">
                <span id="nombreCliente-label" class="property-label"><g:message code="asignacionLoteConjuntoPlata.nombreCliente.label" default="Nombre Cliente" /></span>

                <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${asignacionLoteConjuntoPlataInstance}" field="nombreCliente"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoPlataInstance?.nombreEmpresa}">
            <li class="fieldcontain">
                <span id="nombreEmpresa-label" class="property-label"><g:message code="asignacionLoteConjuntoPlata.nombreEmpresa.label" default="Nombre Empresa" /></span>

                <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${asignacionLoteConjuntoPlataInstance}" field="nombreEmpresa"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoPlataInstance?.fechaDeRecepcion}">
            <li class="fieldcontain">
                <span id="fechaDeRecepcion-label" class="property-label"><g:message code="asignacionLoteConjuntoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

                <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${asignacionLoteConjuntoPlataInstance}" field="fechaDeRecepcion"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoPlataInstance?.fechaDeLiquidacion}">
            <li class="fieldcontain">
                <span id="fechaDeLiquidacion-label" class="property-label"><g:message code="asignacionLoteConjuntoPlata.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>

                <span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:fieldValue bean="${asignacionLoteConjuntoPlataInstance}" field="fechaDeLiquidacion"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoPlataInstance?.kilosNetosSecos}">
            <li class="fieldcontain">
                <span id="kilosNetosSecos-label" class="property-label"><g:message code="asignacionLoteConjuntoPlata.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>

                <span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${asignacionLoteConjuntoPlataInstance}" field="kilosNetosSecos"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoPlataInstance?.porcentajePlata}">
            <li class="fieldcontain">
                <span id="porcentajePlata-label" class="property-label"><g:message code="asignacionLoteConjuntoPlata.porcentajePlata.label" default="Porcentaje Plata" /></span>

                <span class="property-value" aria-labelledby="porcentajePlata-label"><g:fieldValue bean="${asignacionLoteConjuntoPlataInstance}" field="porcentajePlata"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoPlataInstance?.fechaDeAsignacion}">
            <li class="fieldcontain">
                <span id="fechaDeAsignacion-label" class="property-label"><g:message code="asignacionLoteConjuntoPlata.fechaDeAsignacion.label" default="Fecha De Asignacion" /></span>

                <span class="property-value" aria-labelledby="fechaDeAsignacion-label"><g:formatDate date="${asignacionLoteConjuntoPlataInstance?.fechaDeAsignacion}" /></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoPlataInstance?.conjuntoDestino}">
            <li class="fieldcontain">
                <span id="conjuntoDestino-label" class="property-label"><g:message code="asignacionLoteConjuntoPlata.conjuntoDestino.label" default="Conjunto Destino" /></span>

                <span class="property-value" aria-labelledby="conjuntoDestino-label"><g:fieldValue bean="${asignacionLoteConjuntoPlataInstance}" field="conjuntoDestino"/></span>

            </li>
        </g:if>

        <g:if test="${asignacionLoteConjuntoPlataInstance?.motivo}">
            <li class="fieldcontain">
                <span id="motivo-label" class="property-label"><g:message code="asignacionLoteConjuntoPlata.motivo.label" default="Motivo" /></span>

                <span class="property-value" aria-labelledby="motivo-label"><g:fieldValue bean="${asignacionLoteConjuntoPlataInstance}" field="motivo"/></span>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${asignacionLoteConjuntoPlataInstance?.id}" />
            <g:link class="edit" action="edit" id="${asignacionLoteConjuntoPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        </fieldset>
    </g:form>
</div>
</body>
</html>
