
<%@ page import="org.socymet.recepcion.BajaDeRecepcionDeZincPlata" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bajaDeRecepcionDeZincPlata.label', default: 'BajaDeRecepcionDeZincPlata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bajaDeRecepcionDeZincPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bajaDeRecepcionDeZincPlata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bajaDeRecepcionDeZincPlata">
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.fechaDeBaja}">
				<li class="fieldcontain">
					<span id="fechaDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.fechaDeBaja.label" default="Fecha De Baja" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeBaja-label"><g:formatDate date="${bajaDeRecepcionDeZincPlataInstance?.fechaDeBaja}" format="dd/MM/yyyy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.motivoDeBaja}">
				<li class="fieldcontain">
					<span id="motivoDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.motivoDeBaja.label" default="Motivo De Baja" /></span>
					
						<span class="property-value" aria-labelledby="motivoDeBaja-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="motivoDeBaja"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.tipoDeBaja}">
				<li class="fieldcontain">
					<span id="tipoDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.tipoDeBaja.label" default="Tipo De Baja" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeBaja-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="tipoDeBaja"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.loteDestino}">
				<li class="fieldcontain">
					<span id="loteDestino-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.loteDestino.label" default="Lote Destino" /></span>
					
						<span class="property-value" aria-labelledby="loteDestino-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="loteDestino"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.recepcionDestinoId}">
				<li class="fieldcontain">
					<span id="recepcionDestinoId-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.recepcionDestinoId.label" default="Recepcion Destino Id" /></span>
					
						<span class="property-value" aria-labelledby="recepcionDestinoId-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="recepcionDestinoId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.gastoPorChanqueo}">
				<li class="fieldcontain">
					<span id="gastoPorChanqueo-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.gastoPorChanqueo.label" default="Gasto Por Chanqueo" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorChanqueo-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="gastoPorChanqueo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.gastoPorManipuleo}">
				<li class="fieldcontain">
					<span id="gastoPorManipuleo-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.gastoPorManipuleo.label" default="Gasto Por Manipuleo" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorManipuleo-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="gastoPorManipuleo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.gastoPorAnalisis}">
				<li class="fieldcontain">
					<span id="gastoPorAnalisis-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.gastoPorAnalisis.label" default="Gasto Por Analisis" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorAnalisis-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="gastoPorAnalisis"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.gastoPorAnticipo}">
				<li class="fieldcontain">
					<span id="gastoPorAnticipo-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.gastoPorAnticipo.label" default="Gasto Por Anticipo" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorAnticipo-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="gastoPorAnticipo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.gastoPorTransporte}">
				<li class="fieldcontain">
					<span id="gastoPorTransporte-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.gastoPorTransporte.label" default="Gasto Por Transporte" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorTransporte-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="gastoPorTransporte"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.otrosGastos}">
				<li class="fieldcontain">
					<span id="otrosGastos-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.otrosGastos.label" default="Otros Gastos" /></span>
					
						<span class="property-value" aria-labelledby="otrosGastos-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="otrosGastos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.totalDeGastos}">
				<li class="fieldcontain">
					<span id="totalDeGastos-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.totalDeGastos.label" default="Total De Gastos" /></span>
					
						<span class="property-value" aria-labelledby="totalDeGastos-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="totalDeGastos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDeZincPlataInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="bajaDeRecepcionDeZincPlata.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${bajaDeRecepcionDeZincPlataInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${bajaDeRecepcionDeZincPlataInstance?.id}" />
					<g:link class="edit" action="edit" id="${bajaDeRecepcionDeZincPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
