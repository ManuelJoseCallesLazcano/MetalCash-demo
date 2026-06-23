<%@ page import="org.socymet.calidad.ControlCalidadPlomoPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'controlCalidadPlomoPlata.label', default: 'ControlCalidadPlomoPlata')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-controlCalidadPlomoPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-controlCalidadPlomoPlata" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list controlCalidadPlomoPlata">

    <g:if test="${controlCalidadPlomoPlataInstance?.recepcionDeComplejo}">
        <li class="fieldcontain">
            <span id="recepcionDeComplejo-label" class="property-label"><g:message code="controlCalidadPlomoPlata.recepcionDeComplejo.label" default="Lote" /></span>

            <span class="property-value" aria-labelledby="recepcionDeComplejo-label"><g:link controller="recepcionDeComplejo" action="show" id="${controlCalidadPlomoPlataInstance?.recepcionDeComplejo?.id}">${controlCalidadPlomoPlataInstance?.recepcionDeComplejo?.encodeAsHTML()}</g:link></span>

        </li>
    </g:if>

    <g:if test="${controlCalidadPlomoPlataInstance?.nombreCliente}">
        <li class="fieldcontain">
            <span id="nombreCliente-label" class="property-label"><g:message code="controlCalidadPlomoPlata.nombreCliente.label" default="Nombre Cliente" /></span>

            <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="nombreCliente"/></span>

        </li>
    </g:if>

    <g:if test="${controlCalidadPlomoPlataInstance?.nombreEmpresa}">
        <li class="fieldcontain">
            <span id="nombreEmpresa-label" class="property-label"><g:message code="controlCalidadPlomoPlata.nombreEmpresa.label" default="Nombre Empresa" /></span>

            <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="nombreEmpresa"/></span>

        </li>
    </g:if>

    <g:if test="${controlCalidadPlomoPlataInstance?.fechaDeRecepcion}">
        <li class="fieldcontain">
            <span id="fechaDeRecepcion-label" class="property-label"><g:message code="controlCalidadPlomoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

            <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="fechaDeRecepcion"/></span>

        </li>
    </g:if>

    <g:if test="${controlCalidadPlomoPlataInstance?.cantidadDeSacos}">
        <li class="fieldcontain">
            <span id="cantidadDeSacos-label" class="property-label"><g:message code="controlCalidadPlomoPlata.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

            <span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="cantidadDeSacos"/></span>

        </li>
    </g:if>

    <g:if test="${controlCalidadPlomoPlataInstance?.pesoBruto}">
        <li class="fieldcontain">
            <span id="pesoBruto-label" class="property-label"><g:message code="controlCalidadPlomoPlata.pesoBruto.label" default="Peso Bruto" /></span>

            <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="pesoBruto"/></span>

        </li>
    </g:if>

    <g:if test="${controlCalidadPlomoPlataInstance?.estadoDelLote}">
        <li class="fieldcontain">
            <span id="estadoDelLote-label" class="property-label"><g:message code="controlCalidadPlomoPlata.estadoDelLote.label" default="Estado Del Lote" /></span>

            <span class="property-value" aria-labelledby="estadoDelLote-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="estadoDelLote"/></span>

        </li>
    </g:if>

    <g:if test="${controlCalidadPlomoPlataInstance?.nombreLaboratorio}">
        <li class="fieldcontain">
            <span id="nombreLaboratorio-label" class="property-label"><g:message code="controlCalidadPlomoPlata.nombreLaboratorio.label" default="Nombre Laboratorio" /></span>

            <span class="property-value" aria-labelledby="nombreLaboratorio-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="nombreLaboratorio"/></span>

        </li>
    </g:if>

    <g:if test="${controlCalidadPlomoPlataInstance?.numeroAnalisis}">
        <li class="fieldcontain">
            <span id="numeroAnalisis-label" class="property-label"><g:message code="controlCalidadPlomoPlata.numeroAnalisis.label" default="Numero Analisis" /></span>

            <span class="property-value" aria-labelledby="numeroAnalisis-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="numeroAnalisis"/></span>

        </li>
    </g:if>

    <g:if test="${controlCalidadPlomoPlataInstance?.fechaAnalisis}">
        <li class="fieldcontain">
            <span id="fechaAnalisis-label" class="property-label"><g:message code="controlCalidadPlomoPlata.fechaAnalisis.label" default="Fecha Analisis" /></span>

            <span class="property-value" aria-labelledby="fechaAnalisis-label"><g:formatDate date="${controlCalidadPlomoPlataInstance?.fechaAnalisis}" format="dd/MM/yyyy"/></span>

        </li>
    </g:if>

    <h1 style="font-weight: bold">Detalle de Leyes</h1>

    <table class="center" style="width: 70%;">
        <thead>
        <tr>
            <th style="width: 40%">ELEMENTO</th>
            <th style="width: 20%">LEY EMPRESA</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td class="fieldcontain required">
                <label for="porcentajeMermaPromexbol">
                    <g:message code="controlCalidadPlomoPlata.porcentajeMermaPromexbol.label" default="Merma" />
                </label>
            </td>
            <td class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
                <g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="porcentajeMermaPromexbol"/>
            </td>
        </tr>

        <tr>
            <td class="fieldcontain required">
                <label for="porcentajePlomoFinal">
                    <g:message code="controlCalidadPlomoPlata.porcentajeZincCliente.label" default="Plomo" />
                </label>
            </td>

            <td class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajePlomoPromexbol', 'error')} required">
                <g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="porcentajePlomoPromexbol"/>
            </td>
        </tr>

        <tr>
            <td class="fieldcontain required">
                <label for="porcentajePlataFinal">
                    <g:message code="controlCalidadPlomoPlata.porcentajeZincFinal.label" default="Plata" />
                </label>
            </td>

            <td class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajePlataPromexbol', 'error')} required">
                <g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="porcentajePlataPromexbol"/>
            </td>
        </tr>
        <tr>
            <td class="fieldcontain required">
                <label for="porcentajeHumedadPromexbol">
                    <g:message code="controlCalidadPlomoPlata.porcentajeHumedadPromexbol.label" default="Humedad" />
                </label>
            </td>
            <td class="fieldcontain ${hasErrors(bean: controlCalidadPlomoPlataInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
                <g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="porcentajeHumedadPromexbol"/>
            </td>

        </tr>
        </tbody>
    </table>

<g:if test="${controlCalidadPlomoPlataInstance?.porcentajeArsenico}">
    <h1 style="font-weight: bold">Porcentajes de Elementos Penalizables</h1>
    <li class="fieldcontain">
        <span id="porcentajeArsenico-label" class="property-label"><g:message code="controlCalidadPlomoPlata.porcentajeArsenico.label" default="Porcentaje Arsenico" /></span>

        <span class="property-value" aria-labelledby="porcentajeArsenico-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="porcentajeArsenico"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadPlomoPlataInstance?.porcentajeAntimonio}">
    <li class="fieldcontain">
        <span id="porcentajeAntimonio-label" class="property-label"><g:message code="controlCalidadPlomoPlata.porcentajeAntimonio.label" default="Porcentaje Antimonio" /></span>

        <span class="property-value" aria-labelledby="porcentajeAntimonio-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="porcentajeAntimonio"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadPlomoPlataInstance?.porcentajeSilice}">
    <li class="fieldcontain">
        <span id="porcentajeSilice-label" class="property-label"><g:message code="controlCalidadPlomoPlata.porcentajeSilice.label" default="Porcentaje Silice" /></span>

        <span class="property-value" aria-labelledby="porcentajeSilice-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="porcentajeSilice"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadPlomoPlataInstance?.porcentajeBismuto}">
    <li class="fieldcontain">
        <span id="porcentajeBismuto-label" class="property-label"><g:message code="controlCalidadPlomoPlata.porcentajeBismuto.label" default="Porcentaje Bismuto" /></span>

        <span class="property-value" aria-labelledby="porcentajeBismuto-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="porcentajeBismuto"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadPlomoPlataInstance?.porcentajeEstano}">
    <li class="fieldcontain">
        <span id="porcentajeEstano-label" class="property-label"><g:message code="controlCalidadPlomoPlata.porcentajeEstano.label" default="Porcentaje Estano" /></span>

        <span class="property-value" aria-labelledby="porcentajeEstano-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="porcentajeEstano"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadPlomoPlataInstance?.porcentajeZinc}">
    <li class="fieldcontain">
        <span id="porcentajeZinc-label" class="property-label"><g:message code="controlCalidadPlomoPlata.porcentajeZinc.label" default="Porcentaje Zinc" /></span>

        <span class="property-value" aria-labelledby="porcentajeZinc-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="porcentajeZinc"/></span>

    </li>
</g:if>

    <g:if test="${controlCalidadPlomoPlataInstance?.observaciones}">
        <li class="fieldcontain">
            <span id="observaciones-label" class="property-label"><g:message code="controlCalidadPlomoPlata.observaciones.label" default="Observaciones" /></span>

            <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${controlCalidadPlomoPlataInstance}" field="observaciones"/></span>

        </li>
    </g:if>

</ol>
<g:form>
    <fieldset class="buttons">
        <g:hiddenField name="id" value="${controlCalidadPlomoPlataInstance?.id}" />
        <g:link class="edit" action="edit" id="${controlCalidadPlomoPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </fieldset>
</g:form>
</div>
</body>
</html>
