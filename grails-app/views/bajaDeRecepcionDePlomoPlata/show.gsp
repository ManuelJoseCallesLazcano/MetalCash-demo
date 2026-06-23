
<%@ page import="org.socymet.recepcion.BajaDeRecepcionDePlomoPlata" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bajaDeRecepcionDePlomoPlata.label', default: 'BajaDeRecepcionDePlomoPlata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bajaDeRecepcionDePlomoPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bajaDeRecepcionDePlomoPlata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bajaDeRecepcionDePlomoPlata">
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.fechaDeBaja}">
				<li class="fieldcontain">
					<span id="fechaDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.fechaDeBaja.label" default="Fecha De Baja" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeBaja-label"><g:formatDate date="${bajaDeRecepcionDePlomoPlataInstance?.fechaDeBaja}" format="dd/MM/yyyy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.motivoDeBaja}">
				<li class="fieldcontain">
					<span id="motivoDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.motivoDeBaja.label" default="Motivo De Baja" /></span>
					
						<span class="property-value" aria-labelledby="motivoDeBaja-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="motivoDeBaja"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.tipoDeBaja}">
				<li class="fieldcontain">
					<span id="tipoDeBaja-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.tipoDeBaja.label" default="Tipo De Baja" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeBaja-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="tipoDeBaja"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.loteDestino}">
				<li class="fieldcontain">
					<span id="loteDestino-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.loteDestino.label" default="Lote Destino" /></span>
					
						<span class="property-value" aria-labelledby="loteDestino-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="loteDestino"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.recepcionDestinoId}">
				<li class="fieldcontain">
					<span id="recepcionDestinoId-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.recepcionDestinoId.label" default="Recepcion Destino Id" /></span>
					
						<span class="property-value" aria-labelledby="recepcionDestinoId-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="recepcionDestinoId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.gastoPorChanqueo}">
				<li class="fieldcontain">
					<span id="gastoPorChanqueo-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.gastoPorChanqueo.label" default="Gasto Por Chanqueo" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorChanqueo-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="gastoPorChanqueo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.gastoPorManipuleo}">
				<li class="fieldcontain">
					<span id="gastoPorManipuleo-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.gastoPorManipuleo.label" default="Gasto Por Manipuleo" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorManipuleo-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="gastoPorManipuleo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.gastoPorAnalisis}">
				<li class="fieldcontain">
					<span id="gastoPorAnalisis-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.gastoPorAnalisis.label" default="Gasto Por Analisis" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorAnalisis-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="gastoPorAnalisis"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.gastoPorAnticipo}">
				<li class="fieldcontain">
					<span id="gastoPorAnticipo-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.gastoPorAnticipo.label" default="Gasto Por Anticipo" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorAnticipo-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="gastoPorAnticipo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.gastoPorTransporte}">
				<li class="fieldcontain">
					<span id="gastoPorTransporte-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.gastoPorTransporte.label" default="Gasto Por Transporte" /></span>
					
						<span class="property-value" aria-labelledby="gastoPorTransporte-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="gastoPorTransporte"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.otrosGastos}">
				<li class="fieldcontain">
					<span id="otrosGastos-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.otrosGastos.label" default="Otros Gastos" /></span>
					
						<span class="property-value" aria-labelledby="otrosGastos-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="otrosGastos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.totalDeGastos}">
				<li class="fieldcontain">
					<span id="totalDeGastos-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.totalDeGastos.label" default="Total De Gastos" /></span>
					
						<span class="property-value" aria-labelledby="totalDeGastos-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="totalDeGastos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bajaDeRecepcionDePlomoPlataInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="bajaDeRecepcionDePlomoPlata.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${bajaDeRecepcionDePlomoPlataInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${bajaDeRecepcionDePlomoPlataInstance?.id}" />
					<g:link class="edit" action="edit" id="${bajaDeRecepcionDePlomoPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
