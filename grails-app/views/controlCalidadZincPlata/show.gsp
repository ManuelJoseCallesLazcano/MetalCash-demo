<%@ page import="org.socymet.calidad.ControlCalidadZincPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'controlCalidadZincPlata.label', default: 'ControlCalidadZincPlata')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-controlCalidadZincPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="show-controlCalidadZincPlata" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list controlCalidadZincPlata">

<g:if test="${controlCalidadZincPlataInstance?.recepcionDeComplejo}">
    <li class="fieldcontain">
        <span id="recepcionDeComplejo-label" class="property-label"><g:message code="controlCalidadZincPlata.recepcionDeComplejo.label" default="Lote" /></span>

        <span class="property-value" aria-labelledby="recepcionDeComplejo-label"><g:link controller="recepcionDeComplejo" action="show" id="${controlCalidadZincPlataInstance?.recepcionDeComplejo?.id}">${controlCalidadZincPlataInstance?.recepcionDeComplejo?.encodeAsHTML()}</g:link></span>

    </li>
</g:if>

<g:if test="${controlCalidadZincPlataInstance?.nombreCliente}">
    <li class="fieldcontain">
        <span id="nombreCliente-label" class="property-label"><g:message code="controlCalidadZincPlata.nombreCliente.label" default="Nombre Cliente" /></span>

        <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${controlCalidadZincPlataInstance}" field="nombreCliente"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadZincPlataInstance?.nombreEmpresa}">
    <li class="fieldcontain">
        <span id="nombreEmpresa-label" class="property-label"><g:message code="controlCalidadZincPlata.nombreEmpresa.label" default="Nombre Empresa" /></span>

        <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${controlCalidadZincPlataInstance}" field="nombreEmpresa"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadZincPlataInstance?.fechaDeRecepcion}">
    <li class="fieldcontain">
        <span id="fechaDeRecepcion-label" class="property-label"><g:message code="controlCalidadZincPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

        <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${controlCalidadZincPlataInstance}" field="fechaDeRecepcion"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadZincPlataInstance?.cantidadDeSacos}">
    <li class="fieldcontain">
        <span id="cantidadDeSacos-label" class="property-label"><g:message code="controlCalidadZincPlata.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

        <span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${controlCalidadZincPlataInstance}" field="cantidadDeSacos"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadZincPlataInstance?.pesoBruto}">
    <li class="fieldcontain">
        <span id="pesoBruto-label" class="property-label"><g:message code="controlCalidadZincPlata.pesoBruto.label" default="Peso Bruto" /></span>

        <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${controlCalidadZincPlataInstance}" field="pesoBruto"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadZincPlataInstance?.estadoDelLote}">
    <li class="fieldcontain">
        <span id="estadoDelLote-label" class="property-label"><g:message code="controlCalidadZincPlata.estadoDelLote.label" default="Estado Del Lote" /></span>

        <span class="property-value" aria-labelledby="estadoDelLote-label"><g:fieldValue bean="${controlCalidadZincPlataInstance}" field="estadoDelLote"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadZincPlataInstance?.nombreLaboratorio}">
    <li class="fieldcontain">
        <span id="nombreLaboratorio-label" class="property-label"><g:message code="controlCalidadZincPlata.nombreLaboratorio.label" default="Nombre Laboratorio" /></span>

        <span class="property-value" aria-labelledby="nombreLaboratorio-label"><g:fieldValue bean="${controlCalidadZincPlataInstance}" field="nombreLaboratorio"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadZincPlataInstance?.numeroAnalisis}">
    <li class="fieldcontain">
        <span id="numeroAnalisis-label" class="property-label"><g:message code="controlCalidadZincPlata.numeroAnalisis.label" default="Numero Analisis" /></span>

        <span class="property-value" aria-labelledby="numeroAnalisis-label"><g:fieldValue bean="${controlCalidadZincPlataInstance}" field="numeroAnalisis"/></span>

    </li>
</g:if>

<g:if test="${controlCalidadZincPlataInstance?.fechaAnalisis}">
    <li class="fieldcontain">
        <span id="fechaAnalisis-label" class="property-label"><g:message code="controlCalidadZincPlata.fechaAnalisis.label" default="Fecha Analisis" /></span>

        <span class="property-value" aria-labelledby="fechaAnalisis-label"><g:formatDate date="${controlCalidadZincPlataInstance?.fechaAnalisis}" format="dd/MM/yyyy"/></span>

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
                <g:message code="controlCalidadZincPlata.porcentajeMermaPromexbol.label" default="Merma" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
            <g:fieldValue bean="${controlCalidadZincPlataInstance}" field="porcentajeMermaPromexbol"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeZincFinal">
                <g:message code="controlCalidadZincPlata.porcentajeZincCliente.label" default="Zinc" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'porcentajeZincPromexbol', 'error')} required">
            <g:fieldValue bean="${controlCalidadZincPlataInstance}" field="porcentajeZincPromexbol"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajePlataFinal">
                <g:message code="controlCalidadZincPlata.porcentajeZincFinal.label" default="Plata" />
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'porcentajePlataPromexbol', 'error')} required">
            <g:fieldValue bean="${controlCalidadZincPlataInstance}" field="porcentajePlataPromexbol"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain required">
            <label for="porcentajeHumedadPromexbol">
                <g:message code="controlCalidadZincPlata.porcentajeHumedadPromexbol.label" default="Humedad" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: controlCalidadZincPlataInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
            <g:fieldValue bean="${controlCalidadZincPlataInstance}" field="porcentajeHumedadPromexbol"/>
        </td>
    </tr>
    </tbody>
</table>

<g:if test="${controlCalidadZincPlataInstance?.observaciones}">
    <li class="fieldcontain">
        <span id="observaciones-label" class="property-label"><g:message code="controlCalidadZincPlata.observaciones.label" default="Observaciones" /></span>

        <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${controlCalidadZincPlataInstance}" field="observaciones"/></span>

    </li>
</g:if>

</ol>
<g:form>
    <fieldset class="buttons">
        <g:hiddenField name="id" value="${controlCalidadZincPlataInstance?.id}" />
        <g:link class="edit" action="edit" id="${controlCalidadZincPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </fieldset>
</g:form>
</div>
</body>
</html>
