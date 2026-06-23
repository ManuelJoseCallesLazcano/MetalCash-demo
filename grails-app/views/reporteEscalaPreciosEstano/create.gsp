<%@ page import="org.socymet.org.socymet.reportes.ReporteEscalaPreciosEstano" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteEscalaPreciosEstano.label', default: 'ReporteEscalaPreciosEstano')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="reportes/escalaPreciosEstano.js" />
	</head>
	<body>
		<a href="#create-reporteEscalaPreciosEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reporteEscalaPreciosEstano" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteEscalaPreciosEstanoInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteEscalaPreciosEstanoInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
                    <div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosEstanoInstance, field: 'fechaCotizacion', 'error')} required">
                        <label for="fechaCotizacion">
                            <g:message code="reporteEscalaPreciosEstano.fechaCotizacion.label" default="Fecha Cotizacion" />
                            <span class="required-indicator">*</span>
                        </label>
                        <g:datepickerUI name="fechaCotizacion" value="${reporteEscalaPreciosEstanoInstance?.fechaCotizacion ?: new Date()}"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosEstanoInstance, field: 'cotizacionEstano', 'error')} ">
                        <label for="cotizacionEstano">
                            <g:message code="reporteEscalaPreciosEstano.cotizacionEstano.label" default="Cotizacion Estano" />

                        </label>
                        <g:field name="cotizacionEstano" value="${fieldValue(bean: reporteEscalaPreciosEstanoInstance, field: 'cotizacionEstano')}" inputmode="decimal"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: reporteEscalaPreciosEstanoInstance, field: 'tablaCotizacionEstano', 'error')} ">
                        <label for="tablaCotizacionEstano">
                            <g:message code="reporteEscalaPreciosEstano.tablaCotizacionEstano.label" default="Tabla Cotizacion Estano" />

                        </label>
                        <g:select id="tablaCotizacionEstano" name="tablaCotizacionEstano.id" from="${org.socymet.cotizaciones.TablaCotizacionEstano.list()}" optionKey="id" value="${reporteEscalaPreciosEstanoInstance?.tablaCotizacionEstano?.id}" class="many-to-one" noSelection="['null': '']"/>
                    </div>
                    <br>
                    <div style="text-align: center;">
                        <g:actionSubmit class="reporte" controller="reporteEscalaPreciosEstano" action="crearReporteEscalaPreciosEstano" value="Generar Reporte" />
                    </div>
                </fieldset>

			</g:form>
		</div>
	</body>
</html>
