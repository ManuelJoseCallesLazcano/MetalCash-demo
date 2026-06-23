<%@ page import="org.socymet.org.socymet.reportes.PresupuestoLotesPorPagar" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'presupuestoLotesPorPagar.label', default: 'PresupuestoLotesPorPagar')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="reportes/presupuestoLotesPorPagar.js" />
	</head>
	<body>
		<a href="#create-presupuestoLotesPorPagar" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-presupuestoLotesPorPagar" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${presupuestoLotesPorPagarInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${presupuestoLotesPorPagarInstance}" var="error">
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

                <g:form>
                <h1 style="font-weight: bold">Parametros de busqueda:</h1>
                <g:hiddenField name="tipoReporte" value="fechas" />

                <div class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'elemento', 'error')} ">
                    <label for="elemento">
                        <g:message code="reporteAnticiposContraEntrega.elemento.label" default="Elemento" />

                    </label>
                    <g:select name="elemento" from="${['Complejo']}" value="${presupuestoLotesPorPagarInstance?.elemento}" valueMessagePrefix="reporteAnticiposContraEntrega.elemento" noSelection="['': '']"/>
                </div>

                <div id="_tablaCotizacionEstano" class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'tablaCotizacionEstano', 'error')} " style="display: none">
                    <label for="tablaCotizacionEstano">
                        <g:message code="presupuestoLotesPorPagar.tablaCotizacionEstano.label" default="Tabla Cotizacion Estano" />

                    </label>
                    <g:select id="tablaCotizacionEstano" name="tablaCotizacionEstano.id" from="${org.socymet.cotizaciones.TablaCotizacionEstano.list()}" optionKey="id" value="${presupuestoLotesPorPagarInstance?.tablaCotizacionEstano?.id}" class="many-to-one" noSelection="['null': '']"/>
                </div>

                <div id="_tablaCotizacionPlata" class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'tablaCotizacionPlata', 'error')} " style="display: none">
                    <label for="tablaCotizacionPlata">
                        <g:message code="presupuestoLotesPorPagar.tablaCotizacionPlata.label" default="Tabla Cotizacion Plata" />

                    </label>
                    <g:select id="tablaCotizacionPlata" name="tablaCotizacionPlata.id" from="${org.socymet.cotizaciones.TablaCotizacionPlata.list()}" optionKey="id" value="${presupuestoLotesPorPagarInstance?.tablaCotizacionPlata?.id}" class="many-to-one" noSelection="['null': '']"/>
                </div>

                <div id="_tablaCotizacionAntimonio" class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'tablaCotizacionAntimonio', 'error')} " style="display: none">
                    <label for="tablaCotizacionAntimonio">
                        <g:message code="presupuestoLotesPorPagar.tablaCotizacionAntimonio.label" default="Tabla Cotizacion Antimonio" />

                    </label>
                    <g:select id="tablaCotizacionAntimonio" name="tablaCotizacionAntimonio.id" from="${org.socymet.cotizaciones.TablaCotizacionAntimonio.list()}" optionKey="id" value="${presupuestoLotesPorPagarInstance?.tablaCotizacionAntimonio?.id}" class="many-to-one" noSelection="['null': '']"/>
                </div>

                <div id="_tablaCotizacionWolfran" class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'tablaCotizacionWolfran', 'error')} " style="display: none">
                    <label for="tablaCotizacionWolfran">
                        <g:message code="presupuestoLotesPorPagar.tablaCotizacionWolfran.label" default="Tabla Cotizacion Wolfran" />

                    </label>
                    <g:select id="tablaCotizacionWolfran" name="tablaCotizacionWolfran.id" from="${org.socymet.cotizaciones.TablaCotizacionWolfran.list()}" optionKey="id" value="${presupuestoLotesPorPagarInstance?.tablaCotizacionWolfran?.id}" class="many-to-one" noSelection="['null': '']"/>
                </div>

                <div id="_empresa" class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'empresa', 'error')} " style="display: none">
                    <label for="empresa">
                        <g:message code="reporteAnticiposContraEntrega.empresa.label" default="Empresa" />

                    </label>
                    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${presupuestoLotesPorPagarInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
                </div>

                <div id="_fechaInicial" class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'fechaInicial', 'error')} required">
                    <label for="fechaInicial">
                        <g:message code="reporteAnticiposContraEntrega.fechaInicial.label" default="Fecha Inicial" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:datepickerUI name="fechaInicial" value="${presupuestoLotesPorPagarInstance?.fechaInicial ?: new Date()}"/>
                </div>

                <div id="_fechaFinal" class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'fechaFinal', 'error')} required">
                    <label for="fechaFinal">
                        <g:message code="reporteAnticiposContraEntrega.fechaFinal.label" default="Fecha Final" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:datepickerUI name="fechaFinal" value="${presupuestoLotesPorPagarInstance?.fechaFinal ?: new Date()}"/>
                </div>

                <div id="_loteInicial" class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'loteInicial', 'error')} " style="display: none">
                    <label for="loteInicial">
                        <g:message code="reporteAnticiposContraEntrega.loteInicial.label" default="Lote Inicial" />

                    </label>
                    <g:textField name="loteInicial" inputmode="numeric" value="${presupuestoLotesPorPagarInstance?.loteInicial}"/>
                </div>

                <div id="_loteFinal" class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'loteFinal', 'error')} " style="display: none">
                    <label for="loteFinal">
                        <g:message code="reporteAnticiposContraEntrega.loteFinal.label" default="Lote Final" />

                    </label>
                    <g:textField name="loteFinal" inputmode="numeric" value="${presupuestoLotesPorPagarInstance?.loteFinal}"/>
                </div>

                <div id="_resultadosEstano" style="display: none">
                    <h1 style="font-weight: bold;">Resultados para Estano:</h1>
                    <div style="width: 900px; ">
                        <table id="tablaEstano"></table>
                    </div>
                    <div style="text-align: center;">
                        <button type="button" id="buscarEstano">Buscar</button>
                        <g:form>
                            <g:hiddenField name="resultadoEstano" value=""/>
                            <g:hiddenField name="tablaCotizacionEstanoId" value=""/>
                            <g:actionSubmit class="reporte" controller="presupuestoLotesPorPagar" action="crearReporteEstano" value="Generar Reporte" />
                        </g:form>
                    </div>
                </div>

                <div id="_resultadosPlata" style="display: none">
                    <h1 style="font-weight: bold;">Resultados para Plata:</h1>
                    <div style="width: 900px; ">
                        <table id="tablaPlata"></table>
                    </div>
                    <div style="text-align: center;">
                        <button type="button" id="buscarPlata">Buscar</button>
                        <g:form>
                            <g:hiddenField name="resultadoPlata" value=""/>
                            <g:hiddenField name="tablaCotizacionPlataId" value=""/>
                            <g:actionSubmit class="reporte" controller="presupuestoLotesPorPagar" action="crearReportePlata" value="Generar Reporte" />
                        </g:form>
                    </div>
                </div>

                <div id="_resultadosWolfran" style="display: none">
                    <h1 style="font-weight: bold;">Resultados para Wolfran:</h1>
                    <div style="width: 900px; ">
                        <table id="tablaWolfran"></table>
                    </div>
                    <div style="text-align: center;">
                        <button type="button" id="buscarWolfran">Buscar</button>
                        <g:form>
                            <g:hiddenField name="resultadoWolfran" value=""/>
                            <g:hiddenField name="tablaCotizacionWolfranId" value=""/>
                            <g:actionSubmit class="reporte" controller="presupuestoLotesPorPagar" action="crearReporteWolfran" value="Generar Reporte" />
                        </g:form>
                    </div>
                </div>

                <div id="_resultadosAntimonio" style="display: none">
                    <h1 style="font-weight: bold;">Resultados para Antimonio:</h1>
                    <div style="width: 900px; ">
                        <table id="tablaAntimonio"></table>
                    </div>
                    <div style="text-align: center;">
                        <button type="button" id="buscarAntimonio">Buscar</button>
                        <g:form>
                            <g:hiddenField name="resultadoAntimonio" value=""/>
                            <g:hiddenField name="tablaCotizacionAntimonioId" value=""/>
                            <g:actionSubmit class="reporte" controller="presupuestoLotesPorPagar" action="crearReporteAntimonio" value="Generar Reporte" />
                        </g:form>
                    </div>
                </div>

                <div id="_resultadosComplejo" style="display: none">
                    <h1 style="font-weight: bold;">Resultados para Complejo:</h1>
                    <div style="width: 900px; ">
                        <table id="tablaComplejo"></table>
                    </div>
                    <div style="text-align: center;">
                        <button type="button" id="buscarComplejo">Buscar</button>
                        <g:form>
                            <g:hiddenField name="resultadoComplejo" value=""/>
                            <g:hiddenField name="tablaCotizacionComplejoId" value=""/>
                            <g:actionSubmit class="reporte" controller="presupuestoLotesPorPagar" action="crearReporteComplejo" value="Generar Reporte" />
                        </g:form>
                    </div>
                </div>

                <br>
                </g:form>
            </fieldset>
		</div>
	</body>
</html>
