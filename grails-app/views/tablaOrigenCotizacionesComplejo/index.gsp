<%@ page import="org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName"
           value="${message(code: 'tablaOrigenCotizacionesComplejo.label', default: 'TablaOrigenCotizacionesComplejo')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-tablaOrigenCotizacionesComplejo" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                                      default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-tablaOrigenCotizacionesComplejo" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="nombreTabla"
                              title="${message(code: 'tablaOrigenCotizacionesComplejo.nombreTabla.label', default: 'Nombre Tabla')}"/>

            <th><g:message code="tablaOrigenCotizacionesComplejo.empresa.label" default="Empresa"/></th>

            <g:sortableColumn property="naturalezaMineral"
                              title="${message(code: 'tablaOrigenCotizacionesComplejo.naturalezaMineral.label', default: 'Naturaleza Mineral')}"/>

            <g:sortableColumn property="datosArchivo"
                              title="${message(code: 'tablaOrigenCotizacionesComplejo.datosArchivo.label', default: 'Datos Archivo')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${tablaOrigenCotizacionesComplejoInstanceList}" status="i"
                var="tablaOrigenCotizacionesComplejoInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${tablaOrigenCotizacionesComplejoInstance.id}">${fieldValue(bean: tablaOrigenCotizacionesComplejoInstance, field: "nombreTabla")}</g:link></td>

                <td>${fieldValue(bean: tablaOrigenCotizacionesComplejoInstance, field: "empresa")}</td>

                <td>${fieldValue(bean: tablaOrigenCotizacionesComplejoInstance, field: "naturalezaMineral")}</td>

                <td>${fieldValue(bean: tablaOrigenCotizacionesComplejoInstance, field: "datosArchivo")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${tablaOrigenCotizacionesComplejoInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
