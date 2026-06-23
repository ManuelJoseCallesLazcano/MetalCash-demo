
<%@ page import="org.socymet.recepcion.BajaDeRecepcionDeEstano" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bajaDeRecepcionDeEstano.label', default: 'BajaDeRecepcionDeEstano')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bajaDeRecepcionDeEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bajaDeRecepcionDeEstano" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bajaDeRecepcionDeEstano">

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.lote}">
                    <li class="fieldcontain">
                        <span id="lote-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.lote.label" default="Lote" /></span>

                        <span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="lote"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.nombreCliente}">
                    <li class="fieldcontain">
                        <span id="nombreCliente-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.nombreCliente.label" default="Nombre Cliente" /></span>

                        <span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="nombreCliente"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.nombreEmpresa}">
                    <li class="fieldcontain">
                        <span id="nombreEmpresa-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.nombreEmpresa.label" default="Nombre Empresa" /></span>

                        <span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="nombreEmpresa"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.fechaDeRecepcion}">
                    <li class="fieldcontain">
                        <span id="fechaDeRecepcion-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

                        <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="fechaDeRecepcion"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.pesoBruto}">
                    <li class="fieldcontain">
                        <span id="pesoBruto-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.pesoBruto.label" default="Peso Bruto" /></span>

                        <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="pesoBruto"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.fechaDeBaja}">
                    <li class="fieldcontain">
                        <span id="fechaDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.fechaDeBaja.label" default="Fecha De Baja" /></span>

                        <span class="property-value" aria-labelledby="fechaDeBaja-label"><g:formatDate date="${bajaDeRecepcionDeEstanoInstance?.fechaDeBaja}" format="dd/MM/yyyy"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.motivoDeBaja}">
                    <li class="fieldcontain">
                        <span id="motivoDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.motivoDeBaja.label" default="Motivo De Baja" /></span>

                        <span class="property-value" aria-labelledby="motivoDeBaja-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="motivoDeBaja"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.tipoDeBaja}">
                    <li class="fieldcontain">
                        <span id="tipoDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.tipoDeBaja.label" default="Tipo De Baja" /></span>

                        <span class="property-value" aria-labelledby="tipoDeBaja-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="tipoDeBaja"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.loteDestino}">
                    <li class="fieldcontain">
                        <span id="loteDestino-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.loteDestino.label" default="Lote Destino" /></span>

                        <span class="property-value" aria-labelledby="loteDestino-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="loteDestino"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.recepcionDestinoId}">
                    <li class="fieldcontain">
                        <span id="recepcionDestinoId-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.recepcionDestinoId.label" default="Recepcion Destino Id" /></span>

                        <span class="property-value" aria-labelledby="recepcionDestinoId-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="recepcionDestinoId"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.gastoPorChanqueo}">
                    <li class="fieldcontain">
                        <span id="gastoPorChanqueo-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.gastoPorChanqueo.label" default="Gasto Por Chanqueo" /></span>

                        <span class="property-value" aria-labelledby="gastoPorChanqueo-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="gastoPorChanqueo"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.gastoPorManipuleo}">
                    <li class="fieldcontain">
                        <span id="gastoPorManipuleo-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.gastoPorManipuleo.label" default="Gasto Por Manipuleo" /></span>

                        <span class="property-value" aria-labelledby="gastoPorManipuleo-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="gastoPorManipuleo"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.gastoPorAnalisis}">
                    <li class="fieldcontain">
                        <span id="gastoPorAnalisis-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.gastoPorAnalisis.label" default="Gasto Por Analisis" /></span>

                        <span class="property-value" aria-labelledby="gastoPorAnalisis-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="gastoPorAnalisis"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.gastoPorAnticipo}">
                    <li class="fieldcontain">
                        <span id="gastoPorAnticipo-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.gastoPorAnticipo.label" default="Gasto Por Anticipo" /></span>

                        <span class="property-value" aria-labelledby="gastoPorAnticipo-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="gastoPorAnticipo"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.gastoPorTransporte}">
                    <li class="fieldcontain">
                        <span id="gastoPorTransporte-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.gastoPorTransporte.label" default="Gasto Por Transporte" /></span>

                        <span class="property-value" aria-labelledby="gastoPorTransporte-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="gastoPorTransporte"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.otrosGastos}">
                    <li class="fieldcontain">
                        <span id="otrosGastos-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.otrosGastos.label" default="Otros Gastos" /></span>

                        <span class="property-value" aria-labelledby="otrosGastos-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="otrosGastos"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.totalDeGastos}">
                    <li class="fieldcontain">
                        <span id="totalDeGastos-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.totalDeGastos.label" default="Total De Gastos" /></span>

                        <span class="property-value" aria-labelledby="totalDeGastos-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="totalDeGastos"/></span>

                    </li>
                </g:if>

                <g:if test="${bajaDeRecepcionDeEstanoInstance?.observaciones}">
                    <li class="fieldcontain">
                        <span id="observaciones-label" class="property-label"><g:message code="bajaDeRecepcionDeEstano.observaciones.label" default="Observaciones" /></span>

                        <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${bajaDeRecepcionDeEstanoInstance}" field="observaciones"/></span>

                    </li>
                </g:if>
			
			</ol>

            <fieldset class="buttons">
                <div style="float: left">
                    <g:form>
                        <g:hiddenField name="id" value="${bajaDeRecepcionDeEstanoInstance?.id}" />
                        <g:link class="edit" action="edit" id="${bajaDeRecepcionDeEstanoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </g:form>
                </div>
                <div style="float: right">
                    <g:jasperReport controller="bajaDeRecepcionDeEstano" action="crearReporte" jasper="baja_lote_estano" format="PDF" _format="PDF" name="ComprobanteLoteDadoDeBaja_${bajaDeRecepcionDeEstanoInstance.id}">
                        <input type="hidden" name="ACE_ID" value="${bajaDeRecepcionDeEstanoInstance.id}" />
                    </g:jasperReport>
                </div>
            </fieldset>
		</div>
	</body>
</html>
