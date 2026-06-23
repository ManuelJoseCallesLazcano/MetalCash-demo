
<%@ page import="org.socymet.anticipos.AnticipoContraTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'anticipoContraTransporte.label', default: 'AnticipoContraTransporte')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-anticipoContraTransporte" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-anticipoContraTransporte" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list anticipoContraTransporte">

        <g:if test="${anticipoContraTransporteInstance?.numeroAnticipo}">
            <li class="fieldcontain">
                <span id="numeroAnticipo-label" class="property-label"><g:message code="anticipoContraTransporte.numeroAnticipo.label" default="No. de Anticipo" /></span>

                <span class="property-value" aria-labelledby="numeroAnticipo-label"><g:fieldValue bean="${anticipoContraTransporteInstance}" field="numeroAnticipo"/></span>

            </li>
        </g:if>

        <g:if test="${anticipoContraTransporteInstance?.lote}">
            <li class="fieldcontain">
                <span id="lote-label" class="property-label"><g:message code="anticipoContraTransporte.lote.label" default="Lote" /></span>

                <span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${anticipoContraTransporteInstance}" field="lote"/></span>

            </li>
        </g:if>

        <g:if test="${anticipoContraTransporteInstance?.nombreCliente}">
            <li class="fieldcontain">
                <span id="nombreCliente-label" class="property-label"><g:message code="anticipoContraTransporte.nombreCliente.label" default="Nombre Cliente" /></span>

                <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${anticipoContraTransporteInstance}" field="nombreCliente"/></span>

            </li>
        </g:if>

        <g:if test="${anticipoContraTransporteInstance?.nombreEmpresa}">
            <li class="fieldcontain">
                <span id="nombreEmpresa-label" class="property-label"><g:message code="anticipoContraTransporte.nombreEmpresa.label" default="Nombre Empresa" /></span>

                <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${anticipoContraTransporteInstance}" field="nombreEmpresa"/></span>

            </li>
        </g:if>

        <g:if test="${anticipoContraTransporteInstance?.fechaDeRecepcion}">
            <li class="fieldcontain">
                <span id="fechaDeRecepcion-label" class="property-label"><g:message code="anticipoContraTransporte.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

                <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${anticipoContraTransporteInstance}" field="fechaDeRecepcion"/></span>

            </li>
        </g:if>

        <g:if test="${anticipoContraTransporteInstance?.pesoBruto}">
            <li class="fieldcontain">
                <span id="pesoBruto-label" class="property-label"><g:message code="anticipoContraTransporte.pesoBruto.label" default="Peso Bruto" /></span>

                <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${anticipoContraTransporteInstance}" field="pesoBruto"/></span>

            </li>
        </g:if>

        <g:if test="${anticipoContraTransporteInstance?.costoDeTransporte}">
            <li class="fieldcontain">
                <span id="costoDeTransporte-label" class="property-label"><g:message code="anticipoContraTransporte.costoDeTransporte.label" default="Costo de Transporte" /></span>

                <span class="property-value" aria-labelledby="costoDeTransporte-label"><g:fieldValue bean="${anticipoContraTransporteInstance}" field="costoDeTransporte"/></span>

            </li>
        </g:if>

        <g:if test="${anticipoContraTransporteInstance?.fechaDeAnticipo}">
            <li class="fieldcontain">
                <span id="fechaDeAnticipo-label" class="property-label"><g:message code="anticipoContraTransporte.fechaDeAnticipo.label" default="Fecha De Anticipo" /></span>

                <span class="property-value" aria-labelledby="fechaDeAnticipo-label"><g:formatDate date="${anticipoContraTransporteInstance?.fechaDeAnticipo}" format="dd/MM/yyyy"/></span>

            </li>
        </g:if>

        <g:if test="${anticipoContraTransporteInstance?.importe}">
            <li class="fieldcontain">
                <span id="importe-label" class="property-label"><g:message code="anticipoContraTransporte.importe.label" default="Importe" /></span>

                <span class="property-value" aria-labelledby="importe-label"><g:fieldValue bean="${anticipoContraTransporteInstance}" field="importe"/></span>

            </li>
        </g:if>

        <g:if test="${anticipoContraTransporteInstance?.importeLiteral}">
            <li class="fieldcontain">
                <span id="importeLiteral-label" class="property-label"><g:message code="anticipoContraTransporte.importeLiteral.label" default="importeLiteral" /></span>

                <span class="property-value" aria-labelledby="importeLiteral-label"><g:fieldValue bean="${anticipoContraTransporteInstance}" field="importeLiteral"/></span>

            </li>
        </g:if>

        <g:if test="${anticipoContraTransporteInstance?.observaciones}">
            <li class="fieldcontain">
                <span id="observaciones-label" class="property-label"><g:message code="anticipoContraTransporte.observaciones.label" default="Observaciones" /></span>

                <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${anticipoContraTransporteInstance}" field="observaciones"/></span>

            </li>
        </g:if>

    </ol>

    <fieldset class="buttons">
        <div style="float: left">
            <g:form>
                <g:hiddenField name="id" value="${anticipoContraTransporteInstance?.id}" />
                <g:link class="edit" action="edit" id="${anticipoContraTransporteInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            </g:form>
        </div>
        <div style="float: right">
            <g:jasperReport controller="anticipoContraTransporte" action="crearReporte" jasper="anticipo_contra_transporte" format="PDF" _format="PDF" name="ComprobanteAnticipoContraTransporte_${anticipoContraTransporteInstance.numeroAnticipo}">
                <input type="hidden" name="ACE_ID" value="${anticipoContraTransporteInstance.id}" />
            </g:jasperReport>
        </div>
    </fieldset>
</div>
</body>
</html>
