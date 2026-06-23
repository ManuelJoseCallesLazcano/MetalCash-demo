
<%@ page import="org.socymet.recepcion.BajaDeRecepcionDePlata" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bajaDeRecepcionDePlata.label', default: 'BajaDeRecepcionDePlata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bajaDeRecepcionDePlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bajaDeRecepcionDePlata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bajaDeRecepcionDePlata">
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.recepcionId}">
				<li class="fieldcontain">
					<span id="recepcionId-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.recepcionId.label" default="Recepcion Id" /></span>
					
						<span class="property-value" aria-labelledby="recepcionId-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="recepcionId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.fechaDeBaja}">
				<li class="fieldcontain">
					<span id="fechaDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.fechaDeBaja.label" default="Fecha De Baja" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeBaja-label"><g:formatDate date="${bajaDeRecepcionDePlataInstance?.fechaDeBaja}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.motivoDeBaja}">
				<li class="fieldcontain">
					<span id="motivoDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.motivoDeBaja.label" default="Motivo De Baja" /></span>
					
						<span class="property-value" aria-labelledby="motivoDeBaja-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="motivoDeBaja"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.tipoDeBaja}">
				<li class="fieldcontain">
					<span id="tipoDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.tipoDeBaja.label" default="Tipo De Baja" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeBaja-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="tipoDeBaja"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.loteDestino}">
				<li class="fieldcontain">
					<span id="loteDestino-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.loteDestino.label" default="Lote Destino" /></span>
					
						<span class="property-value" aria-labelledby="loteDestino-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="loteDestino"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.recepcionDestinoId}">
				<li class="fieldcontain">
					<span id="recepcionDestinoId-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.recepcionDestinoId.label" default="Recepcion Destino Id" /></span>
					
						<span class="property-value" aria-labelledby="recepcionDestinoId-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="recepcionDestinoId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.gastoPorChanqueo}">
				<li class="fieldcontain">
					<span id="gastoPorChanqueo-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.gastoPorChanqueo.label" default="Gasto Por Chanqueo" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorChanqueo-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="gastoPorChanqueo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.gastoPorManipuleo}">
				<li class="fieldcontain">
					<span id="gastoPorManipuleo-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.gastoPorManipuleo.label" default="Gasto Por Manipuleo" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorManipuleo-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="gastoPorManipuleo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.gastoPorAnalisis}">
				<li class="fieldcontain">
					<span id="gastoPorAnalisis-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.gastoPorAnalisis.label" default="Gasto Por Analisis" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorAnalisis-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="gastoPorAnalisis"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.gastoPorAnticipo}">
				<li class="fieldcontain">
					<span id="gastoPorAnticipo-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.gastoPorAnticipo.label" default="Gasto Por Anticipo" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorAnticipo-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="gastoPorAnticipo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.gastoPorTransporte}">
				<li class="fieldcontain">
					<span id="gastoPorTransporte-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.gastoPorTransporte.label" default="Gasto Por Transporte" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorTransporte-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="gastoPorTransporte"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.otrosGastos}">
				<li class="fieldcontain">
					<span id="otrosGastos-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.otrosGastos.label" default="Otros Gastos" /></span>
					
						<span class="property-value" aria-labelledby="otrosGastos-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="otrosGastos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.totalDeGastos}">
				<li class="fieldcontain">
					<span id="totalDeGastos-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.totalDeGastos.label" default="Total De Gastos" /></span>
					
						<span class="property-value" aria-labelledby="totalDeGastos-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="totalDeGastos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlataInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="bajaDeRecepcionDePlata.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${bajaDeRecepcionDePlataInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
			</ol>
            <fieldset class="buttons">
                <div style="float: left">
                    <g:form>
                        <g:hiddenField name="id" value="${bajaDeRecepcionDePlataInstance?.id}" />
                        <g:link class="edit" action="edit" id="${bajaDeRecepcionDePlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </g:form>
                </div>
                <div style="float: right">
                    <g:jasperReport controller="bajaDeRecepcionDePlata" action="crearReporte" jasper="baja_lote_plata" format="PDF" _format="PDF" name="ComprobanteLoteDadoDeBaja_${bajaDeRecepcionDePlataInstance.id}">
                        <input type="hidden" name="ACE_ID" value="${bajaDeRecepcionDePlataInstance.id}" />
                    </g:jasperReport>
                </div>
            </fieldset>
		</div>
	</body>
</html>
