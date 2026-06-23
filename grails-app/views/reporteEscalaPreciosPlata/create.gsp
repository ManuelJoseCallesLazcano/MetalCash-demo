<%@ page import="org.socymet.org.socymet.reportes.ReporteEscalaPreciosPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'reporteEscalaPreciosPlata.label', default: 'ReporteEscalaPreciosPlata')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
    <g:javascript src="jquery-1.10.1.min.js" />
    <g:javascript src="i18n/grid.locale-es.js" />
    <g:javascript src="jquery.jqGrid.min.js" />
    <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
    <g:javascript src="reportes/escalaPreciosPlata.js" />
</head>
<body>
<a href="#create-reporteEscalaPreciosPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
    </ul>
</div>
<div id="create-reporteEscalaPreciosPlata" class="content scaffold-create" role="main">
    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${reporteEscalaPreciosPlataInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${reporteEscalaPreciosPlataInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="save" >
        <fieldset class="form">
            <div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosPlataInstance, field: 'fechaCotizacion', 'error')} required">
                <label for="fechaCotizacion">
                    <g:message code="reporteEscalaPreciosPlata.fechaCotizacion.label" default="Fecha Cotizacion" />
                    <span class="required-indicator">*</span>
                </label>
                <g:datepickerUI name="fechaCotizacion" value="${reporteEscalaPreciosPlataInstance?.fechaCotizacion ?: new Date()}"/>
            </div>

            <div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosPlataInstance, field: 'cotizacionPlata', 'error')} ">
                <label for="cotizacionPlata">
                    <g:message code="reporteEscalaPreciosPlata.cotizacionPlata.label" default="Cotizacion Plata" />

                </label>
                <g:field name="cotizacionPlata" value="${fieldValue(bean: reporteEscalaPreciosPlataInstance, field: 'cotizacionPlata')}" inputmode="decimal"/>
            </div>

            <div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosPlataInstance, field: 'tablaCotizacionPlata', 'error')} ">
                <label for="tablaCotizacionPlata">
                    <g:message code="reporteEscalaPreciosPlata.tablaCotizacionPlata.label" default="Tabla Cotizacion Plata" />

                </label>
                <g:select id="tablaCotizacionPlata" name="tablaCotizacionPlata.id" from="${org.socymet.cotizaciones.TablaCotizacionPlata.list()}" optionKey="id" value="${reporteEscalaPreciosPlataInstance?.tablaCotizacionPlata?.id}" class="many-to-one" noSelection="['null': '']"/>
            </div>
            <br>
            <div style="text-align: center;">
                <g:actionSubmit class="reporte" controller="reporteEscalaPreciosPlata" action="crearReporteEscalaPreciosPlata" value="Generar Reporte" />
            </div>
        </fieldset>

    </g:form>
</div>
</body>
</html>
