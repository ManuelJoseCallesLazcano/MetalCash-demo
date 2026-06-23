<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesLiquidados" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteLotesLiquidados.label', default: 'ReporteLotesLiquidados')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'chosen.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="chosen.jquery.js" />
        <g:javascript src="reportes/lotesLiquidados.js" />
	</head>
	<body>
		<a href="#create-reporteLotesLiquidados" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reporteLotesLiquidados" class="content scaffold-create" role="main">
			<h1><g:message code="default.report.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteLotesLiquidadosInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteLotesLiquidadosInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
            <g:form action="save" >
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
                        </tbody>
                    </table>
                    <h1 style="font-weight: bold">Parametros de busqueda:</h1>
                    <g:hiddenField name="tipoReporte" value="fechas" />
                    <div id="_empresa" class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosInstance, field: 'empresa', 'error')} " style="display: none">
                        <label for="empresa">
                            <g:message code="reporteLotesLiquidados.empresa.label" default="Empresa" />

                        </label>
                        <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${reporteLotesLiquidadosInstance?.empresa?.id}" class="many-to-one, chosen-select" style="width: 350px" noSelection="['null': '']"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosInstance, field: 'deposito', 'error')} required" style="display: none">
                        <label for="deposito">
                            <g:message code="reporteLotesLiquidados.deposito.label" default="Deposito" />
                            <span class="required-indicator">*</span>
                        </label>
                        <g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${reporteLotesLiquidadosInstance?.deposito?.id}" class="many-to-one"/>
                    </div>

                    <div class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosInstance, field: 'elemento', 'error')} " style="display: none">
                        <label for="elemento">
                            <g:message code="reporteLotesLiquidados.elemento.label" default="Elemento" />

                        </label>
                        <g:select name="elemento" from="${['Complejo','Plomo Plata','Zinc Plata','Cobre Plata']}" value="${reporteLotesLiquidadosInstance?.elemento}" valueMessagePrefix="reporteLotesLiquidados.elemento"/>
                    </div>

                    <div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosInstance, field: 'fechaInicial', 'error')} ">
                        <label for="fechaInicial">
                            <g:message code="reporteLotesLiquidados.fechaInicial.label" default="Fecha Inicial" />

                        </label>
                        <g:datepickerUI name="fechaInicial" value="${reporteLotesLiquidadosInstance?.fechaInicial ?: new Date()}"/>
                    </div>

                    <div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: reporteLotesLiquidadosInstance, field: 'fechaFinal', 'error')} ">
                        <label for="fechaFinal">
                            <g:message code="reporteLotesLiquidados.fechaFinal.label" default="Fecha Final" />

                        </label>
                        <g:datepickerUI name="fechaFinal" value="${reporteLotesLiquidadosInstance?.fechaFinal ?: new Date()}"/>
                    </div>

                    <br/>

                    <div id="_resultadosComplejo">
                        <div style="text-align: center;">
                            <g:actionSubmit class="reporte" controller="reporteLotesLiquidados" action="crearReporteComplejo" value="Generar Reporte" />
                        </div>
                    </div>
                </fieldset>
            </g:form>
		</div>
	</body>
</html>
