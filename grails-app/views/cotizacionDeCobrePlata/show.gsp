
<%@ page import="org.socymet.cotizacionParaCliente.CotizacionDeCobrePlata" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cotizacionDeCobrePlata.label', default: 'CotizacionDeCobrePlata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-cotizacionDeCobrePlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-cotizacionDeCobrePlata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list cotizacionDeCobrePlata">
			
				<g:if test="${cotizacionDeCobrePlataInstance?.numeroCotizacionCobrePlata}">
				<li class="fieldcontain">
					<span id="numeroCotizacionCobrePlata-label" class="property-label"><g:message code="cotizacionDeCobrePlata.numeroCotizacionCobrePlata.label" default="Numero Cotizacion Cobre Plata" /></span>
					
						<span class="property-value" aria-labelledby="numeroCotizacionCobrePlata-label"><g:fieldValue bean="${cotizacionDeCobrePlataInstance}" field="numeroCotizacionCobrePlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.nombreSolicitante}">
				<li class="fieldcontain">
					<span id="nombreSolicitante-label" class="property-label"><g:message code="cotizacionDeCobrePlata.nombreSolicitante.label" default="Nombre Solicitante" /></span>
					
						<span class="property-value" aria-labelledby="nombreSolicitante-label"><g:fieldValue bean="${cotizacionDeCobrePlataInstance}" field="nombreSolicitante"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.empresaSolicitante}">
				<li class="fieldcontain">
					<span id="empresaSolicitante-label" class="property-label"><g:message code="cotizacionDeCobrePlata.empresaSolicitante.label" default="Empresa Solicitante" /></span>
					
						<span class="property-value" aria-labelledby="empresaSolicitante-label"><g:fieldValue bean="${cotizacionDeCobrePlataInstance}" field="empresaSolicitante"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.fechaDeCotizacion}">
				<li class="fieldcontain">
					<span id="fechaDeCotizacion-label" class="property-label"><g:message code="cotizacionDeCobrePlata.fechaDeCotizacion.label" default="Fecha De Cotizacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeCotizacion-label"><g:formatDate date="${cotizacionDeCobrePlataInstance?.fechaDeCotizacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.cotizacionDiaria}">
				<li class="fieldcontain">
					<span id="cotizacionDiaria-label" class="property-label"><g:message code="cotizacionDeCobrePlata.cotizacionDiaria.label" default="Cotizacion Diaria" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionDiaria-label"><g:link controller="cotizacionDiariaDeMinerales" action="show" id="${cotizacionDeCobrePlataInstance?.cotizacionDiaria?.id}">${cotizacionDeCobrePlataInstance?.cotizacionDiaria?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.leyCobre}">
				<li class="fieldcontain">
					<span id="leyCobre-label" class="property-label"><g:message code="cotizacionDeCobrePlata.leyCobre.label" default="Ley Cobre" /></span>
					
						<span class="property-value" aria-labelledby="leyCobre-label"><g:fieldValue bean="${cotizacionDeCobrePlataInstance}" field="leyCobre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.leyPlata}">
				<li class="fieldcontain">
					<span id="leyPlata-label" class="property-label"><g:message code="cotizacionDeCobrePlata.leyPlata.label" default="Ley Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyPlata-label"><g:fieldValue bean="${cotizacionDeCobrePlataInstance}" field="leyPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.modoValoracion}">
				<li class="fieldcontain">
					<span id="modoValoracion-label" class="property-label"><g:message code="cotizacionDeCobrePlata.modoValoracion.label" default="Modo Valoracion" /></span>
					
						<span class="property-value" aria-labelledby="modoValoracion-label"><g:fieldValue bean="${cotizacionDeCobrePlataInstance}" field="modoValoracion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.tablaCobrePlata}">
				<li class="fieldcontain">
					<span id="tablaCobrePlata-label" class="property-label"><g:message code="cotizacionDeCobrePlata.tablaCobrePlata.label" default="Tabla Cobre Plata" /></span>
					
						<span class="property-value" aria-labelledby="tablaCobrePlata-label"><g:link controller="tablaPreciosCobre" action="show" id="${cotizacionDeCobrePlataInstance?.tablaCobrePlata?.id}">${cotizacionDeCobrePlataInstance?.tablaCobrePlata?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.terminosDeContrato}">
				<li class="fieldcontain">
					<span id="terminosDeContrato-label" class="property-label"><g:message code="cotizacionDeCobrePlata.terminosDeContrato.label" default="Terminos De Contrato" /></span>
					
						<span class="property-value" aria-labelledby="terminosDeContrato-label"><g:link controller="terminosDeContrato" action="show" id="${cotizacionDeCobrePlataInstance?.terminosDeContrato?.id}">${cotizacionDeCobrePlataInstance?.terminosDeContrato?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.valorTonelada}">
				<li class="fieldcontain">
					<span id="valorTonelada-label" class="property-label"><g:message code="cotizacionDeCobrePlata.valorTonelada.label" default="Valor Tonelada" /></span>
					
						<span class="property-value" aria-labelledby="valorTonelada-label"><g:fieldValue bean="${cotizacionDeCobrePlataInstance}" field="valorTonelada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="cotizacionDeCobrePlata.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${cotizacionDeCobrePlataInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.valorEstimado}">
				<li class="fieldcontain">
					<span id="valorEstimado-label" class="property-label"><g:message code="cotizacionDeCobrePlata.valorEstimado.label" default="Valor Estimado" /></span>
					
						<span class="property-value" aria-labelledby="valorEstimado-label"><g:fieldValue bean="${cotizacionDeCobrePlataInstance}" field="valorEstimado"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeCobrePlataInstance?.usuario}">
				<li class="fieldcontain">
					<span id="usuario-label" class="property-label"><g:message code="cotizacionDeCobrePlata.usuario.label" default="Usuario" /></span>
					
						<span class="property-value" aria-labelledby="usuario-label"><g:link controller="secUser" action="show" id="${cotizacionDeCobrePlataInstance?.usuario?.id}">${cotizacionDeCobrePlataInstance?.usuario?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${cotizacionDeCobrePlataInstance?.id}" />
					<g:link class="edit" action="edit" id="${cotizacionDeCobrePlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
