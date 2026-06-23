<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesLiquidadosPorRecepcion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteLotesLiquidadosPorRecepcion.label', default: 'ReporteLotesLiquidadosPorRecepcion')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="reportes/lotesRecepcionados.js" />
	</head>
	<body>
		<a href="#create-reporteLotesLiquidadosPorRecepcion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reporteLotesLiquidadosPorRecepcion" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteLotesLiquidadosPorRecepcionInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteLotesLiquidadosPorRecepcionInstance}" var="error">
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
                        <td style="font-weight: bold">Lotes</td>
                    </tr>
                    <tr>
                        <td style="width: 10px"><input type="radio" id="lotesEmpresa" name="myGroup" value="4" /></td>
                        <td style="font-weight: bold">Lotes y Empresa</td>
                    </tr>
                    </tbody>
                </table>

                <h1 style="font-weight: bold">Parametros de busqueda:</h1>

                <div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'elemento', 'error')} ">
                    <label for="elemento">
                        <g:message code="reporteLotesLiquidadosPorRecepcion.elemento.label" default="Elemento" />

                    </label>
                    <g:select name="elemento" from="${['Complejo','Plomo Plata','Zinc Plata']}" value="${reporteLotesLiquidadosPorRecepcionInstance?.elemento}" valueMessagePrefix="reporteLotesLiquidadosPorRecepcion.elemento" noSelection="['': '']"/>
                </div>

                <div id="_empresa" class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'empresa', 'error')} " style="display: none">
                    <label for="empresa">
                        <g:message code="reporteLotesLiquidadosPorRecepcion.empresa.label" default="Empresa" />

                    </label>
                    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteLotesLiquidadosPorRecepcionInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
                </div>

                <div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'fechaInicial', 'error')} required">
                    <label for="fechaInicial">
                        <g:message code="reporteLotesLiquidadosPorRecepcion.fechaInicial.label" default="Fecha Inicial" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:datepickerUI name="fechaInicial" value="${reporteLotesLiquidadosPorRecepcionInstance?.fechaInicial ?: new Date()}"/>
                </div>

                <div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'fechaFinal', 'error')} required">
                    <label for="fechaFinal">
                        <g:message code="reporteLotesLiquidadosPorRecepcion.fechaFinal.label" default="Fecha Final" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:datepickerUI name="fechaFinal" value="${reporteLotesLiquidadosPorRecepcionInstance?.fechaFinal ?: new Date()}"/>
                </div>

                <div id="_loteInicial" class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'loteInicial', 'error')} " style="display: none">
                    <label for="loteInicial">
                        <g:message code="reporteLotesLiquidadosPorRecepcion.loteInicial.label" default="Lote Inicial" />

                    </label>
                    <g:textField name="loteInicial" inputmode="numeric" value="${reporteLotesLiquidadosPorRecepcionInstance?.loteInicial}"/>
                </div>

                <div id="_loteFinal" class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'loteFinal', 'error')} " style="display: none">
                    <label for="loteFinal">
                        <g:message code="reporteLotesLiquidadosPorRecepcion.loteFinal.label" default="Lote Final" />

                    </label>
                    <g:textField name="loteFinal" inputmode="numeric" value="${reporteLotesLiquidadosPorRecepcionInstance?.loteFinal}"/>
                </div>

                <div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosPorRecepcionInstance, field: 'estado', 'error')} ">
                    <label for="estado">
                        <g:message code="reporteLotesLiquidadosPorRecepcion.estado.label" default="Estado" />

                    </label>
                    <g:select name="estado" from="${['ACTIVO','INACTIVO']}" value="${reporteLotesLiquidadosPorRecepcionInstance?.estado}" valueMessagePrefix="reporteLotesLiquidadosPorRecepcion.estado" noSelection="['': '']"/>
                </div>
                <br>
                <div id="_ReportePorFechas">
                    <fieldset class="buttons">
                        <g:jasperReport controller="reporteLotesLiquidadosPorRecepcion" action="crearReporte" jasper="reporte_recepcion_fechas" format="PDF" name="ReportePorFechas">
                            <input type="hidden" id="ELEMENTO_1" name="ELEMENTO_1" />
                            <input type="hidden" id="ELEMENTO_CLASS_1" name="ELEMENTO_CLASS_1" />
                            <input type="hidden" id="FECHA_INICIAL_1" name="FECHA_INICIAL_1"/>
                            <input type="hidden" id="FECHA_FINAL_1" name="FECHA_FINAL_1"/>
                            <input type="hidden" id="ESTADO_LOTE_1" name="ESTADO_LOTE_1"/>
                        </g:jasperReport>
                    </fieldset>
                </div>
                <div id="_ReportePorFechasEmpresa" style="display: none">
                    <fieldset class="buttons">
                        <g:jasperReport controller="reporteLotesLiquidadosPorRecepcion" action="crearReporte" jasper="reporte_recepcion_fechas_empresa" format="PDF" name="ReportePorFechasEmpresa">
                            <input type="hidden" id="ELEMENTO_2" name="ELEMENTO_2" />
                            <input type="hidden" id="ELEMENTO_CLASS_2" name="ELEMENTO_CLASS_2" />
                            <input type="hidden" id="EMPRESA_ID_2" name="EMPRESA_ID_2" />
                            <input type="hidden" id="FECHA_INICIAL_2" name="FECHA_INICIAL_2"/>
                            <input type="hidden" id="FECHA_FINAL_2" name="FECHA_FINAL_2"/>
                            <input type="hidden" id="ESTADO_LOTE_2" name="ESTADO_LOTE_2"/>
                        </g:jasperReport>
                    </fieldset>
                </div>
                <div id="_ReportePorLotes" style="display: none">
                    <fieldset class="buttons">
                        <g:jasperReport controller="reporteLotesLiquidadosPorRecepcion" action="crearReporte" jasper="reporte_recepcion_lotes" format="PDF" name="ReportePorLotes">
                            <input type="hidden" id="ELEMENTO_3" name="ELEMENTO_3" />
                            <input type="hidden" id="ELEMENTO_CLASS_3" name="ELEMENTO_CLASS_3" />
                            <input type="hidden" id="LOTE_INICIAL_3" name="LOTE_INICIAL_3"/>
                            <input type="hidden" id="LOTE_FINAL_3" name="LOTE_FINAL_3"/>
                            <input type="hidden" id="ESTADO_LOTE_3" name="ESTADO_LOTE_3"/>
                        </g:jasperReport>
                    </fieldset>
                </div>
                <div id="_ReportePorLotesEmpresa" style="display: none">
                    <fieldset class="buttons">
                        <g:jasperReport controller="reporteLotesLiquidadosPorRecepcion" action="crearReporte" jasper="reporte_recepcion_lotes_empresa" format="PDF" name="ReportePorLotesEmpresa">
                            <input type="hidden" id="ELEMENTO_4" name="ELEMENTO_4" />
                            <input type="hidden" id="ELEMENTO_CLASS_4" name="ELEMENTO_CLASS_4" />
                            <input type="hidden" id="EMPRESA_ID_4" name="EMPRESA_ID_2" />
                            <input type="hidden" id="LOTE_INICIAL_4" name="LOTE_INICIAL_4"/>
                            <input type="hidden" id="LOTE_FINAL_4" name="LOTE_FINAL_4"/>
                            <input type="hidden" id="ESTADO_LOTE_4" name="ESTADO_LOTE_4"/>
                        </g:jasperReport>
                    </fieldset>
                </div>
            </fieldset>
		</div>
	</body>
</html>
