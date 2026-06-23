<%@ page import="org.socymet.org.socymet.reportes.ReporteDetalleLotesDadosDeBaja" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteDetalleLotesDadosDeBaja.label', default: 'ReporteDetalleLotesDadosDeBaja')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="reportes/detalleLotesDadosDeBaja.js" />
	</head>
	<body>
		<a href="#create-reporteDetalleLotesDadosDeBaja" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reporteDetalleLotesDadosDeBaja" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteDetalleLotesDadosDeBajaInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteDetalleLotesDadosDeBajaInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
            <fieldset class="form">
                <div class="fieldcontain ${hasErrors(bean: reporteDetalleLotesDadosDeBajaInstance, field: 'elemento', 'error')} ">
                    <label for="elemento">
                        <g:message code="reporteDetalleLotesDadosDeBaja.elemento.label" default="Elemento" />

                    </label>
                    <g:select name="elemento" from="${['Complejo','Plomo-Plata','Zinc-Plata']}" value="${reporteDetalleLotesDadosDeBajaInstance?.elemento}" valueMessagePrefix="reporteDetalleLotesDadosDeBaja.elemento" noSelection="['': '']"/>
                </div>

                <div class="fieldcontain ${hasErrors(bean: reporteDetalleLotesDadosDeBajaInstance, field: 'fechaInicial', 'error')} required">
                    <label for="fechaInicial">
                        <g:message code="reporteDetalleLotesDadosDeBaja.fechaInicial.label" default="Fecha Inicial" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:datepickerUI name="fechaInicial" value="${reporteDetalleLotesDadosDeBajaInstance?.fechaInicial ?: new Date()}"/>
                </div>

                <div class="fieldcontain ${hasErrors(bean: reporteDetalleLotesDadosDeBajaInstance, field: 'fechaFinal', 'error')} required">
                    <label for="fechaFinal">
                        <g:message code="reporteDetalleLotesDadosDeBaja.fechaFinal.label" default="Fecha Final" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:datepickerUI name="fechaFinal" value="${reporteDetalleLotesDadosDeBajaInstance?.fechaFinal ?: new Date()}"/>
                </div>

            </fieldset>

            <div id="_ReporteEstano" style="display: none">
                <fieldset class="buttons">
                    <g:jasperReport controller="reporteDetalleLotesDadosDeBaja" action="crearReporteEstano" jasper="reporte_detalle_lotes_baja_estano" format="PDF" name="DetalleLotesDadosDeBajaEstano">
                        <input type="hidden" id="FECHA_INICIAL_1" name="FECHA_INICIAL_1"/>
                        <input type="hidden" id="FECHA_FINAL_1" name="FECHA_FINAL_1"/>
                    </g:jasperReport>
                </fieldset>
            </div>

            <div id="_ReportePlata" style="display: none">
                <fieldset class="buttons">
                    <g:jasperReport controller="reporteDetalleLotesDadosDeBaja" action="crearReportePlata" jasper="reporte_detalle_lotes_baja_plata" format="PDF" name="DetalleLotesDadosDeBajaPlata">
                        <input type="hidden" id="FECHA_INICIAL_2" name="FECHA_INICIAL_2"/>
                        <input type="hidden" id="FECHA_FINAL_2" name="FECHA_FINAL_2"/>
                    </g:jasperReport>
                </fieldset>
            </div>

            <div id="_ReporteWolfran" style="display: none">
                <fieldset class="buttons">
                    <g:jasperReport controller="reporteDetalleLotesDadosDeBaja" action="crearReporteWolfran" jasper="reporte_detalle_lotes_baja_wolfran" format="PDF" name="DetalleLotesDadosDeBajaWolfran">
                        <input type="hidden" id="FECHA_INICIAL_3" name="FECHA_INICIAL_3"/>
                        <input type="hidden" id="FECHA_FINAL_3" name="FECHA_FINAL_3"/>
                    </g:jasperReport>
                </fieldset>
            </div>

            <div id="_ReporteAntimonio" style="display: none">
                <fieldset class="buttons">
                    <g:jasperReport controller="reporteDetalleLotesDadosDeBaja" action="crearReporteAntimonio" jasper="reporte_detalle_lotes_baja_antimonio" format="PDF" name="DetalleLotesDadosDeBajaAntimonio">
                        <input type="hidden" id="FECHA_INICIAL_4" name="FECHA_INICIAL_4"/>
                        <input type="hidden" id="FECHA_FINAL_4" name="FECHA_FINAL_4"/>
                    </g:jasperReport>
                </fieldset>
            </div>

            <div id="_ReporteComplejo" style="display: none">
                <fieldset class="buttons">
                    <g:jasperReport controller="reporteDetalleLotesDadosDeBaja" action="crearReporteComplejo" jasper="reporte_detalle_lotes_baja_complejo" format="PDF" name="DetalleLotesDadosDeBajaComplejo">
                        <input type="hidden" id="FECHA_INICIAL_5" name="FECHA_INICIAL_5"/>
                        <input type="hidden" id="FECHA_FINAL_5" name="FECHA_FINAL_5"/>
                    </g:jasperReport>
                </fieldset>
            </div>

		</div>
	</body>
</html>
