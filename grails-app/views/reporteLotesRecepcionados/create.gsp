<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesRecepcionados" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteLotesRecepcionados.label', default: 'ReporteLotesRecepcionados')}" />
		<title><g:message code="default.report.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'chosen.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="chosen.jquery.js" />
        <g:javascript src="reportes/lotesRecepcionados.js" />
	</head>
	<body>
		<a href="#create-reporteLotesRecepcionados" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reporteLotesRecepcionados" class="content scaffold-create" role="main">
			<h1><g:message code="default.report.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteLotesRecepcionadosInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteLotesRecepcionadosInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
            <fieldset class="form">
                <form>
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
                    %{--<tr>--}%
                        %{--<td style="width: 10px"><input type="radio" id="lotes" name="myGroup" value="3" /></td>--}%
                        %{--<td style="font-weight: bold">Lotes</td>--}%
                    %{--</tr>--}%
                    %{--<tr>--}%
                        %{--<td style="width: 10px"><input type="radio" id="lotesEmpresa" name="myGroup" value="4" /></td>--}%
                        %{--<td style="font-weight: bold">Lotes y Empresa</td>--}%
                    %{--</tr>--}%
                    </tbody>
                </table>

                <h1 style="font-weight: bold">Parametros de busqueda:</h1>

                <div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'deposito', 'error')} required" style="display: none">
                    <label for="deposito">
                        <g:message code="reporteLotesRecepcionados.deposito.label" default="Deposito" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${reporteLotesRecepcionadosInstance?.deposito?.id}" class="many-to-one"/>
                </div>

                <div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'elemento', 'error')} " style="display: none">
                    <label for="elemento">
                        <g:message code="reporteLotesRecepcionados.elemento.label" default="Elemento" />

                    </label>
                    <g:select name="elemento" from="${['Complejo','Plomo Plata','Zinc Plata','Cobre Plata']}" value="${reporteLotesRecepcionadosInstance?.elemento}" valueMessagePrefix="reporteLotesRecepcionados.elemento"/>
                </div>

                <div id="_empresa" class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'empresa', 'error')} " style="display: none">
                    <label for="empresa">
                        <g:message code="reporteLotesRecepcionados.empresa.label" default="Empresa" />

                    </label>
                    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${reporteLotesRecepcionadosInstance?.empresa?.id}" class="many-to-one, chosen-select" style="width: 350px" noSelection="['null': '']"/>
                </div>

                <div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'fechaInicial', 'error')} required">
                    <label for="fechaInicial">
                        <g:message code="reporteLotesRecepcionados.fechaInicial.label" default="Fecha Inicial" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:datepickerUI name="fechaInicial" value="${reporteLotesRecepcionadosInstance?.fechaInicial ?: new Date()}"/>
                </div>

                <div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'fechaFinal', 'error')} required">
                    <label for="fechaFinal">
                        <g:message code="reporteLotesRecepcionados.fechaFinal.label" default="Fecha Final" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:datepickerUI name="fechaFinal" value="${reporteLotesRecepcionadosInstance?.fechaFinal ?: new Date()}"/>
                </div>

                <div id="_loteInicial" class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'loteInicial', 'error')} " style="display: none">
                    <label for="loteInicial">
                        <g:message code="reporteLotesRecepcionados.loteInicial.label" default="Lote Inicial" />

                    </label>
                    <g:textField name="loteInicial" inputmode="numeric" value="${reporteLotesRecepcionadosInstance?.loteInicial}"/>
                </div>

                <div id="_loteFinal" class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'loteFinal', 'error')} " style="display: none">
                    <label for="loteFinal">
                        <g:message code="reporteLotesRecepcionados.loteFinal.label" default="Lote Final" />

                    </label>
                    <g:textField name="loteFinal" inputmode="numeric" value="${reporteLotesRecepcionadosInstance?.loteFinal}"/>
                </div>

                <div class="fieldcontain ${hasErrors(bean: reporteLotesRecepcionadosInstance, field: 'estado', 'error')} ">
                    <label for="estado">
                        <g:message code="reporteLotesRecepcionados.estado.label" default="Estado" />

                    </label>
                    <g:select name="estado" from="${['NO LIQUIDADO','LIQUIDADO','Todos']}" value="${reporteLotesRecepcionadosInstance?.estado}" valueMessagePrefix="reporteLotesRecepcionados.estado"/>
                </div>
                <br>
                <input type="hidden" id="tipoReporte" name="tipoReporte" value="fechas"/>

                <div id="_complejo" style="text-align: center">
                    <g:actionSubmit class="reporte" controller="reporteLotesRecepcionados" action="crearReporteComplejo" value="Generar Reporte" />
                </div>
                <div id="_plomoPlata" style="display: none; text-align: center">
                    <g:actionSubmit class="reporte" controller="reporteLotesRecepcionados" action="crearReportePlomoPlata" value="Generar Reporte" />
                </div>
                <div id="_zincPlata" style="display: none; text-align: center">
                    <g:actionSubmit class="reporte" controller="reporteLotesRecepcionados" action="crearReporteZincPlata" value="Generar Reporte" />
                </div>
                <div id="_cobrePlata" style="display: none; text-align: center">
                    <g:actionSubmit class="reporte" controller="reporteLotesRecepcionados" action="crearReporteCobrePlata" value="Generar Reporte" />
                </div>
                </form>
            </fieldset>
        </div>
	</body>
</html>
