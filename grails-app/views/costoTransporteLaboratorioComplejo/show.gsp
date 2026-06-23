
<%@ page import="org.socymet.liquidacion.CostoTransporteLaboratorioComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'costoTransporteLaboratorioComplejo.label', default: 'CostoTransporteLaboratorioComplejo')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-costoTransporteLaboratorioComplejo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-costoTransporteLaboratorioComplejo" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list costoTransporteLaboratorioComplejo">
    <g:if test="${costoTransporteLaboratorioComplejoInstance?.lote}">
        <li class="fieldcontain">
            <span id="lote-label" class="property-label"><g:message code="costoTransporteLaboratorioComplejo.lote.label" default="Lote" /></span>

            <span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="lote"/></span>

        </li>
    </g:if>

    <g:if test="${costoTransporteLaboratorioComplejoInstance?.nombreCliente}">
        <li class="fieldcontain">
            <span id="nombreCliente-label" class="property-label"><g:message code="costoTransporteLaboratorioComplejo.nombreCliente.label" default="Nombre Cliente" /></span>

            <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="nombreCliente"/></span>

        </li>
    </g:if>

    <g:if test="${costoTransporteLaboratorioComplejoInstance?.nombreEmpresa}">
        <li class="fieldcontain">
            <span id="nombreEmpresa-label" class="property-label"><g:message code="costoTransporteLaboratorioComplejo.nombreEmpresa.label" default="Nombre Empresa" /></span>

            <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="nombreEmpresa"/></span>

        </li>
    </g:if>

    <g:if test="${costoTransporteLaboratorioComplejoInstance?.fechaDeRecepcion}">
        <li class="fieldcontain">
            <span id="fechaDeRecepcion-label" class="property-label"><g:message code="costoTransporteLaboratorioComplejo.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

            <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="fechaDeRecepcion"/></span>

        </li>
    </g:if>

    <g:if test="${costoTransporteLaboratorioComplejoInstance?.pesoBruto}">
        <li class="fieldcontain">
            <span id="pesoBruto-label" class="property-label"><g:message code="costoTransporteLaboratorioComplejo.pesoBruto.label" default="Peso Bruto" /></span>

            <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="pesoBruto"/></span>

        </li>
    </g:if>

    <g:if test="${costoTransporteLaboratorioComplejoInstance?.costoDeTransporteAnterior}">
        <li class="fieldcontain">
            <span id="costoDeTransporteAnterior-label" class="property-label"><g:message code="costoTransporteLaboratorioComplejo.costoDeTransporteAnterior.label" default="Costo De Transporte Anterior" /></span>

            <span class="property-value" aria-labelledby="costoDeTransporteAnterior-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="costoDeTransporteAnterior"/></span>

        </li>
    </g:if>

    <g:if test="${costoTransporteLaboratorioComplejoInstance?.costoDeTransporteNuevo}">
        <li class="fieldcontain">
            <span id="costoDeTransporteNuevo-label" class="property-label"><g:message code="costoTransporteLaboratorioComplejo.costoDeTransporteNuevo.label" default="Costo De Transporte Nuevo" /></span>

            <span class="property-value" aria-labelledby="costoDeTransporteNuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="costoDeTransporteNuevo"/></span>

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
        <g:if test="${costoTransporteLaboratorioComplejoInstance?.detalleLaboratorio1Anterior}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio1Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="detalleLaboratorio1Anterior"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio1Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="costoLaboratorio1Anterior"/></span>
                </td>
            </tr>
        </g:if>
        <g:if test="${costoTransporteLaboratorioComplejoInstance?.detalleLaboratorio2Anterior}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio2Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="detalleLaboratorio2Anterior"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio2Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="costoLaboratorio2Anterior"/></span>
                </td>
            </tr>
        </g:if>
        <g:if test="${costoTransporteLaboratorioComplejoInstance?.detalleLaboratorio3Anterior}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio3Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="detalleLaboratorio3Anterior"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio3Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="costoLaboratorio3Anterior"/></span>
                </td>
            </tr>
        </g:if>
        <g:if test="${costoTransporteLaboratorioComplejoInstance?.detalleLaboratorio4Anterior}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio4Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="detalleLaboratorio4Anterior"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio4Anterior-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="costoLaboratorio4Anterior"/></span>
                </td>
            </tr>
        </g:if>
        <tr>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'detalleLaboratorio4Anterior', 'error')}">
                <label for="totalCostoLaboratorioAnterior" style="width: 90%">
                    <g:message code="liquidacionDeComplejo.totalCostoLaboratorioAnterior.label" default="Total Costo Laboratorio" />
                </label>
            </td>
            <td>
                <span class="property-value" aria-labelledby="totalCostoLaboratorioAnterior-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="totalCostoLaboratorioAnterior"/></span>
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
        <g:if test="${costoTransporteLaboratorioComplejoInstance?.detalleLaboratorio1Nuevo}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio1Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="detalleLaboratorio1Nuevo"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio1Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="costoLaboratorio1Nuevo"/></span>
                </td>
            </tr>
        </g:if>
        <g:if test="${costoTransporteLaboratorioComplejoInstance?.detalleLaboratorio2Nuevo}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio2Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="detalleLaboratorio2Nuevo"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio2Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="costoLaboratorio2Nuevo"/></span>
                </td>
            </tr>
        </g:if>
        <g:if test="${costoTransporteLaboratorioComplejoInstance?.detalleLaboratorio3Nuevo}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio3Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="detalleLaboratorio3Nuevo"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio3Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="costoLaboratorio3Nuevo"/></span>
                </td>
            </tr>
        </g:if>
        <g:if test="${costoTransporteLaboratorioComplejoInstance?.detalleLaboratorio4Nuevo}">
            <tr>
                <td>
                    <span class="property-value" aria-labelledby="detalleLaboratorio4Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="detalleLaboratorio4Nuevo"/></span>
                </td>
                <td>
                    <span class="property-value" aria-labelledby="costoLaboratorio4Nuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="costoLaboratorio4Nuevo"/></span>
                </td>
            </tr>
        </g:if>
        <tr>
            <td class="fieldcontain ${hasErrors(bean: liquidacionDeComplejoInstance, field: 'detalleLaboratorio4Nuevo', 'error')}">
                <label for="totalCostoLaboratorioNuevo" style="width: 90%">
                    <g:message code="liquidacionDeComplejo.totalCostoLaboratorioNuevo.label" default="Total Costo Laboratorio" />
                </label>
            </td>
            <td>
                <span class="property-value" aria-labelledby="totalCostoLaboratorioNuevo-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="totalCostoLaboratorioNuevo"/></span>
            </td>
        </tr>
        </tbody>
    </table>

    <g:if test="${costoTransporteLaboratorioComplejoInstance?.observaciones}">
        <li class="fieldcontain">
            <span id="observaciones-label" class="property-label"><g:message code="costoTransporteLaboratorioComplejo.observaciones.label" default="Observaciones" /></span>

            <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${costoTransporteLaboratorioComplejoInstance}" field="observaciones"/></span>

        </li>
    </g:if>


</ol>
<g:form>
    <fieldset class="buttons">
        <g:hiddenField name="id" value="${costoTransporteLaboratorioComplejoInstance?.id}" />
        <g:link class="edit" action="edit" id="${costoTransporteLaboratorioComplejoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </fieldset>
</g:form>
</div>
</body>
</html>
