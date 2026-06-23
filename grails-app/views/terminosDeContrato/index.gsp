<%@ page import="org.socymet.cotizaciones.TerminosDeContrato" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'terminosDeContrato.label', default: 'TerminosDeContrato')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-terminosDeContrato" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                         default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-terminosDeContrato" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="nombreContrato"
                              title="${message(code: 'terminosDeContrato.nombreContrato.label', default: 'Nombre Contrato')}"/>

            <th><g:message code="terminosDeContrato.empresa.label" default="Empresa"/></th>

            <g:sortableColumn property="tipoDeMineral"
                              title="${message(code: 'terminosDeContrato.tipoDeMineral.label', default: 'Tipo De Mineral')}"/>

            <g:sortableColumn property="porcentajeArsenico"
                              title="${message(code: 'terminosDeContrato.porcentajeArsenico.label', default: 'Porcentaje Arsenico')}"/>

            <g:sortableColumn property="porcentajeAntimonio"
                              title="${message(code: 'terminosDeContrato.porcentajeAntimonio.label', default: 'Porcentaje Antimonio')}"/>

            <g:sortableColumn property="porcentajeBismuto"
                              title="${message(code: 'terminosDeContrato.porcentajeBismuto.label', default: 'Porcentaje Bismuto')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${terminosDeContratoInstanceList}" status="i" var="terminosDeContratoInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${terminosDeContratoInstance.id}">${fieldValue(bean: terminosDeContratoInstance, field: "nombreContrato")}</g:link></td>

                <td>${fieldValue(bean: terminosDeContratoInstance, field: "empresa")}</td>

                <td>${fieldValue(bean: terminosDeContratoInstance, field: "tipoDeMineral")}</td>

                <td>${fieldValue(bean: terminosDeContratoInstance, field: "porcentajeArsenico")}</td>

                <td>${fieldValue(bean: terminosDeContratoInstance, field: "porcentajeAntimonio")}</td>

                <td>${fieldValue(bean: terminosDeContratoInstance, field: "porcentajeBismuto")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${terminosDeContratoInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
