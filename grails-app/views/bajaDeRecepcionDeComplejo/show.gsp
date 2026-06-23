
<%@ page import="org.socymet.recepcion.BajaDeRecepcionDeComplejo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bajaDeRecepcionDeComplejo.label', default: 'BajaDeRecepcionDeComplejo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bajaDeRecepcionDeComplejo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bajaDeRecepcionDeComplejo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bajaDeRecepcionDeComplejo">
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.recepcionId}">
				<li class="fieldcontain">
					<span id="recepcionId-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.recepcionId.label" default="Recepcion Id" /></span>
					
						<span class="property-value" aria-labelledby="recepcionId-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="recepcionId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.fechaDeBaja}">
				<li class="fieldcontain">
					<span id="fechaDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.fechaDeBaja.label" default="Fecha De Baja" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeBaja-label"><g:formatDate date="${bajaDeRecepcionDeComplejoInstance?.fechaDeBaja}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.motivoDeBaja}">
				<li class="fieldcontain">
					<span id="motivoDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.motivoDeBaja.label" default="Motivo De Baja" /></span>
					
						<span class="property-value" aria-labelledby="motivoDeBaja-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="motivoDeBaja"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.tipoDeBaja}">
				<li class="fieldcontain">
					<span id="tipoDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.tipoDeBaja.label" default="Tipo De Baja" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeBaja-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="tipoDeBaja"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.loteDestino}">
				<li class="fieldcontain">
					<span id="loteDestino-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.loteDestino.label" default="Lote Destino" /></span>
					
						<span class="property-value" aria-labelledby="loteDestino-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="loteDestino"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.recepcionDestinoId}">
				<li class="fieldcontain">
					<span id="recepcionDestinoId-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.recepcionDestinoId.label" default="Recepcion Destino Id" /></span>
					
						<span class="property-value" aria-labelledby="recepcionDestinoId-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="recepcionDestinoId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.gastoPorChanqueo}">
				<li class="fieldcontain">
					<span id="gastoPorChanqueo-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.gastoPorChanqueo.label" default="Gasto Por Chanqueo" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorChanqueo-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="gastoPorChanqueo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.gastoPorManipuleo}">
				<li class="fieldcontain">
					<span id="gastoPorManipuleo-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.gastoPorManipuleo.label" default="Gasto Por Manipuleo" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorManipuleo-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="gastoPorManipuleo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.gastoPorAnalisis}">
				<li class="fieldcontain">
					<span id="gastoPorAnalisis-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.gastoPorAnalisis.label" default="Gasto Por Analisis" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorAnalisis-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="gastoPorAnalisis"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.gastoPorAnticipo}">
				<li class="fieldcontain">
					<span id="gastoPorAnticipo-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.gastoPorAnticipo.label" default="Gasto Por Anticipo" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorAnticipo-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="gastoPorAnticipo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.gastoPorTransporte}">
				<li class="fieldcontain">
					<span id="gastoPorTransporte-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.gastoPorTransporte.label" default="Gasto Por Transporte" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorTransporte-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="gastoPorTransporte"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.otrosGastos}">
				<li class="fieldcontain">
					<span id="otrosGastos-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.otrosGastos.label" default="Otros Gastos" /></span>
					
						<span class="property-value" aria-labelledby="otrosGastos-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="otrosGastos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.totalDeGastos}">
				<li class="fieldcontain">
					<span id="totalDeGastos-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.totalDeGastos.label" default="Total De Gastos" /></span>
					
						<span class="property-value" aria-labelledby="totalDeGastos-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="totalDeGastos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeComplejoInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="bajaDeRecepcionDeComplejo.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${bajaDeRecepcionDeComplejoInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
			</ol>
            <fieldset class="buttons">
                <div style="float: left">
                    <g:form>
                        <g:hiddenField name="id" value="${bajaDeRecepcionDeComplejoInstance?.id}" />
                        <g:link class="edit" action="edit" id="${bajaDeRecepcionDeComplejoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </g:form>
                </div>
                <div style="float: right">
                    <g:jasperReport controller="bajaDeRecepcionDeComplejo" action="crearReporte" jasper="baja_lote_complejo" format="PDF" _format="PDF" name="ComprobanteLoteDadoDeBaja_${bajaDeRecepcionDeComplejoInstance.id}">
                        <input type="hidden" name="ACE_ID" value="${bajaDeRecepcionDeComplejoInstance.id}" />
                    </g:jasperReport>
                </div>
            </fieldset>
		</div>
	</body>
</html>
