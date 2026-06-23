
<%@ page import="org.socymet.liquidacion.CostoTransporteLaboratorioWolfran" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'costoTransporteLaboratorioWolfran.label', default: 'CostoTransporteLaboratorioWolfran')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-costoTransporteLaboratorioWolfran" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-costoTransporteLaboratorioWolfran" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list costoTransporteLaboratorioWolfran">
    <g:if test="${costoTransporteLaboratorioWolfranInstance?.lote}">
        <li class="fieldcontain">
            <span id="lote-label" class="property-label"><g:message code="costoTransporteLaboratorioWolfran.lote.label" default="Lote" /></span>

            <span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="lote"/></span>

        </li>
    </g:if>

    <g:if test="${costoTransporteLaboratorioWolfranInstance?.nombreCliente}">
        <li class="fieldcontain">
            <span id="nombreCliente-label" class="property-label"><g:message code="costoTransporteLaboratorioWolfran.nombreCliente.label" default="Nombre Cliente" /></span>

            <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="nombreCliente"/></span>

        </li>
    </g:if>

    <g:if test="${costoTransporteLaboratorioWolfranInstance?.nombreEmpresa}">
        <li class="fieldcontain">
            <span id="nombreEmpresa-label" class="property-label"><g:message code="costoTransporteLaboratorioWolfran.nombreEmpresa.label" default="Nombre Empresa" /></span>

            <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="nombreEmpresa"/></span>

        </li>
    </g:if>

    <g:if test="${costoTransporteLaboratorioWolfranInstance?.fechaDeRecepcion}">
        <li class="fieldcontain">
            <span id="fechaDeRecepcion-label" class="property-label"><g:message code="costoTransporteLaboratorioWolfran.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

            <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="fechaDeRecepcion"/></span>

        </li>
    </g:if>

    <g:if test="${costoTransporteLaboratorioWolfranInstance?.pesoBruto}">
        <li class="fieldcontain">
            <span id="pesoBruto-label" class="property-label"><g:message code="costoTransporteLaboratorioWolfran.pesoBruto.label" default="Peso Bruto" /></span>

            <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="pesoBruto"/></span>

        </li>
    </g:if>

    <g:if test="${costoTransporteLaboratorioWolfranInstance?.costoDeTransporteAnterior}">
        <li class="fieldcontain">
            <span id="costoDeTransporteAnterior-label" class="property-label"><g:message code="costoTransporteLaboratorioWolfran.costoDeTransporteAnterior.label" default="Costo De Transporte Anterior" /></span>

            <span class="property-value" aria-labelledby="costoDeTransporteAnterior-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="costoDeTransporteAnterior"/></span>

        </li>
    </g:if>

    <g:if test="${costoTransporteLaboratorioWolfranInstance?.costoDeTransporteNuevo}">
        <li class="fieldcontain">
            <span id="costoDeTransporteNuevo-label" class="property-label"><g:message code="costoTransporteLaboratorioWolfran.costoDeTransporteNuevo.label" default="Costo De Transporte Nuevo" /></span>

            <span class="property-value" aria-labelledby="costoDeTransporteNuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="costoDeTransporteNuevo"/></span>

        </li>
    </g:if>

    <h1 style="font-weight: bold">Detalle de Analisis Realizados ANTERIOR</h1>

    <table class="center" border="0" style="width: 70%;">
        <thead>
        <tr>
            <th style="text-align: center; width: 70%">DESCRIPCION DE ANALISIS</th>
            <th style="text-align: center; width: 30%">COSTO</th>
        </tr>
        </thead>
        <tbody>
        <g:if test="${costoTransporteLaboratorioWolfranInstance?.detalleLaboratorio1Anterior}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio1Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="detalleLaboratorio1Anterior"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio1Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="costoLaboratorio1Anterior"/></span>
                </td>
            </tr>
        </g:if>
        <g:if test="${costoTransporteLaboratorioWolfranInstance?.detalleLaboratorio2Anterior}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio2Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="detalleLaboratorio2Anterior"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio2Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="costoLaboratorio2Anterior"/></span>
                </td>
            </tr>
        </g:if>
        <g:if test="${costoTransporteLaboratorioWolfranInstance?.detalleLaboratorio3Anterior}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio3Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="detalleLaboratorio3Anterior"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio3Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="costoLaboratorio3Anterior"/></span>
                </td>
            </tr>
        </g:if>
        <g:if test="${costoTransporteLaboratorioWolfranInstance?.detalleLaboratorio4Anterior}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio4Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="detalleLaboratorio4Anterior"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio4Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="costoLaboratorio4Anterior"/></span>
                </td>
            </tr>
        </g:if>
        <tr>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio4Anterior', 'error')}">
                <label for="totalCostoLaboratorioAnterior" style="width: 90%">
                    <g:message code="liquidacionDeWolfran.totalCostoLaboratorioAnterior.label" default="Total Costo Laboratorio" />
                </label>
            </td>
            <td>
                <span class="property-value" aria-labelledby="totalCostoLaboratorioAnterior-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="totalCostoLaboratorioAnterior"/></span>
            </td>
        </tr>
        </tbody>
    </table>

    <h1 style="font-weight: bold">Detalle de Analisis Realizados ANTERIOR</h1>

    <table class="center" border="0" style="width: 70%;">
        <thead>
        <tr>
            <th style="text-align: center; width: 70%">DESCRIPCION DE ANALISIS</th>
            <th style="text-align: center; width: 30%">COSTO</th>
        </tr>
        </thead>
        <tbody>
        <g:if test="${costoTransporteLaboratorioWolfranInstance?.detalleLaboratorio1Nuevo}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio1Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="detalleLaboratorio1Nuevo"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio1Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="costoLaboratorio1Nuevo"/></span>
                </td>
            </tr>
        </g:if>
        <g:if test="${costoTransporteLaboratorioWolfranInstance?.detalleLaboratorio2Nuevo}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio2Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="detalleLaboratorio2Nuevo"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio2Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="costoLaboratorio2Nuevo"/></span>
                </td>
            </tr>
        </g:if>
        <g:if test="${costoTransporteLaboratorioWolfranInstance?.detalleLaboratorio3Nuevo}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio3Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="detalleLaboratorio3Nuevo"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio3Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="costoLaboratorio3Nuevo"/></span>
                </td>
            </tr>
        </g:if>
        <g:if test="${costoTransporteLaboratorioWolfranInstance?.detalleLaboratorio4Nuevo}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio4Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="detalleLaboratorio4Nuevo"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio4Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="costoLaboratorio4Nuevo"/></span>
                </td>
            </tr>
        </g:if>
        <tr>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeWolfranInstance, field: 'detalleLaboratorio4Nuevo', 'error')}">
                <label for="totalCostoLaboratorioNuevo" style="width: 90%">
                    <g:message code="liquidacionDeWolfran.totalCostoLaboratorioNuevo.label" default="Total Costo Laboratorio" />
                </label>
            </td>
            <td>
                <span class="property-value" aria-labelledby="totalCostoLaboratorioNuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="totalCostoLaboratorioNuevo"/></span>
            </td>
        </tr>
        </tbody>
    </table>

    <g:if test="${costoTransporteLaboratorioWolfranInstance?.observaciones}">
        <li class="fieldcontain">
            <span id="observaciones-label" class="property-label"><g:message code="costoTransporteLaboratorioWolfran.observaciones.label" default="Observaciones" /></span>

            <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${costoTransporteLaboratorioWolfranInstance}" field="observaciones"/></span>

        </li>
    </g:if>


</ol>
<g:form>
    <fieldset class="buttons">
        <g:hiddenField name="id" value="${costoTransporteLaboratorioWolfranInstance?.id}" />
        <g:link class="edit" action="edit" id="${costoTransporteLaboratorioWolfranInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </fieldset>
</g:form>
</div>
</body>
</html>
