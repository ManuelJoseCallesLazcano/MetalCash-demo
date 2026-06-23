<%@ page import="org.socymet.org.socymet.reportes.ReporteDetalleAnticiposContraEntrega" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteDetalleAnticiposContraEntrega.label', default: 'ReporteDetalleAnticiposContraEntrega')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="reportes/detalleAnticiposContraEntrega.js" />
	</head>
	<body>
		<a href="#create-reporteDetalleAnticiposContraEntrega" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reporteDetalleAnticiposContraEntrega" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteDetalleAnticiposContraEntregaInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteDetalleAnticiposContraEntregaInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
            <fieldset class="form">
                <h1 style="font-weight: bold">Listar por:</h1>

                <table style="width: 500px;" class="center">
                    <tbody>
                    <tr>
                        <td style="width: 10px"><input type="radio" id="fechas" name="myGroup" value="1" checked="checked" /></td>
                        <td style="font-weight: bold">Fechas</td>
                    </tr>
                    <tr>
                        <td style="width: 10px"><input type="radio" id="fechasEmpresa" name="myGroup" value="2" /></td>
                        <td style="font-weight: bold">Fechas y Empresa</td>
                    </tr>
                    <tr>
                        <td style="width: 10px"><input type="radio" id="lotes" name="myGroup" value="3" /></td>
                        <td style="font-weight: bold">Numero de Anticipo</td>
                    </tr>
                    <tr>
                        <td style="width: 10px"><input type="radio" id="lotesEmpresa" name="myGroup" value="4" /></td>
                        <td style="font-weight: bold">Numero de Anticipo y Empresa</td>
                    </tr>
                    </tbody>
                </table>

                <h1 style="font-weight: bold">Parametros de busqueda:</h1>
                <div id="_empresa" class="fieldcontain ${hasErrors(bean: reporteDetalleAnticiposContraEntregaInstance, field: 'empresa', 'error')} " style="display: none">
                    <label for="empresa">
                        <g:message code="reporteDetalleAnticiposContraEntrega.empresa.label" default="Empresa" />

                    </label>
                    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteDetalleAnticiposContraEntregaInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
                </div>

                <div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: reporteDetalleAnticiposContraEntregaInstance, field: 'fechaInicial', 'error')} required">
                    <label for="fechaInicial">
                        <g:message code="reporteDetalleAnticiposContraEntrega.fechaInicial.label" default="Fecha Inicial" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:datepickerUI name="fechaInicial" value="${reporteDetalleAnticiposContraEntregaInstance?.fechaInicial ?: new Date()}"/>
                </div>

                <div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: reporteDetalleAnticiposContraEntregaInstance, field: 'fechaFinal', 'error')} required">
                    <label for="fechaFinal">
                        <g:message code="reporteDetalleAnticiposContraEntrega.fechaFinal.label" default="Fecha Final" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:datepickerUI name="fechaFinal" value="${reporteDetalleAnticiposContraEntregaInstance?.fechaFinal ?: new Date()}"/>
                </div>

                <div id="_numeroAnticipoInicial" class="fieldcontain ${hasErrors(bean: reporteDetalleAnticiposContraEntregaInstance, field: 'numeroAnticipoInicial', 'error')} " style="display: none">
                    <label for="numeroAnticipoInicial">
                        <g:message code="reporteDetalleAnticiposContraEntrega.numeroAnticipoInicial.label" default="Numero Anticipo Inicial" />

                    </label>
                    <g:textField name="numeroAnticipoInicial" inputmode="numeric" value="${reporteDetalleAnticiposContraEntregaInstance?.numeroAnticipoInicial}"/>
                </div>

                <div id="_numeroAnticipoFinal" class="fieldcontain ${hasErrors(bean: reporteDetalleAnticiposContraEntregaInstance, field: 'numeroAnticipoFinal', 'error')} " style="display: none">
                    <label for="numeroAnticipoFinal">
                        <g:message code="reporteDetalleAnticiposContraEntrega.numeroAnticipoFinal.label" default="Numero Anticipo Final" />

                    </label>
                    <g:textField name="numeroAnticipoFinal" inputmode="numeric" value="${reporteDetalleAnticiposContraEntregaInstance?.numeroAnticipoFinal}"/>
                </div>

                <br>
                <div id="_ReportePorFechas">
                    <fieldset class="buttons">
                        <g:jasperReport controller="reporteDetalleAnticiposContraEntrega" action="crearReporte" jasper="reporte_anticipos_fechas" format="PDF" name="ReporteAnticiposPorFechas">
                            <input type="hidden" id="FECHA_INICIAL_1" name="FECHA_INICIAL_1"/>
                            <input type="hidden" id="FECHA_FINAL_1" name="FECHA_FINAL_1"/>
                        </g:jasperReport>
                    </fieldset>
                </div>
                <div id="_ReportePorFechasEmpresa" style="display: none">
                    <fieldset class="buttons">
                        <g:jasperReport controller="reporteDetalleAnticiposContraEntrega" action="crearReporte" jasper="reporte_anticipos_fechas_empresa" format="PDF" name="ReporteAnticiposPorFechasEmpresa">
                            <input type="hidden" id="EMPRESA_ID_2" name="EMPRESA_ID_2" />
                            <input type="hidden" id="FECHA_INICIAL_2" name="FECHA_INICIAL_2"/>
                            <input type="hidden" id="FECHA_FINAL_2" name="FECHA_FINAL_2"/>
                        </g:jasperReport>
                    </fieldset>
                </div>
                <div id="_ReportePorNumeroAnticipo" style="display: none">
                    <fieldset class="buttons">
                        <g:jasperReport controller="reporteDetalleAnticiposContraEntrega" action="crearReporte" jasper="reporte_anticipos_numero_anticipo" format="PDF" name="ReporteAnticiposPorNumeroAnticipo">
                            <input type="hidden" id="NUMERO_ANTICIPO_INICIAL_3" name="NUMERO_ANTICIPO_INICIAL_3"/>
                            <input type="hidden" id="NUMERO_ANTICIPO_FINAL_3" name="NUMERO_ANTICIPO_FINAL_3"/>
                        </g:jasperReport>
                    </fieldset>
                </div>
                <div id="_ReportePorNumeroAnticipoEmpresa" style="display: none">
                    <fieldset class="buttons">
                        <g:jasperReport controller="reporteDetalleAnticiposContraEntrega" action="crearReporte" jasper="reporte_anticipos_numero_anticipo_empresa" format="PDF" name="ReporteAnticiposPorNumeroAnticipoEmpresa">
                            <input type="hidden" id="EMPRESA_ID_4" name="EMPRESA_ID_4" />
                            <input type="hidden" id="NUMERO_ANTICIPO_INICIAL_4" name="NUMERO_ANTICIPO_INICIAL_4"/>
                            <input type="hidden" id="NUMERO_ANTICIPO_FINAL_4" name="NUMERO_ANTICIPO_FINAL_4"/>
                        </g:jasperReport>
                    </fieldset>
                </div>
            </fieldset>
		</div>
	</body>
</html>
