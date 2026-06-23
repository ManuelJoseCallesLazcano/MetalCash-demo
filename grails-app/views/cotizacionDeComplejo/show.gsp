
<%@ page import="org.socymet.cotizacionParaCliente.CotizacionDeComplejo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cotizacionDeComplejo.label', default: 'CotizacionDeComplejo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-cotizacionDeComplejo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-cotizacionDeComplejo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list cotizacionDeComplejo">

                <h1 style="font-weight: bold">Informacion General</h1>
			
				<g:if test="${cotizacionDeComplejoInstance?.numeroCotizacionComplejo}">
				<li class="fieldcontain">
					<span id="numeroCotizacionComplejo-label" class="property-label"><g:message code="cotizacionDeComplejo.numeroCotizacionComplejo.label" default="Numero Cotizacion Complejo" /></span>
					
						<span class="property-value" aria-labelledby="numeroCotizacionComplejo-label"><g:formatNumber number="${cotizacionDeComplejoInstance.numeroCotizacionComplejo}" format="000000"/> </span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeComplejoInstance?.nombreSolicitante}">
				<li class="fieldcontain">
					<span id="nombreSolicitante-label" class="property-label"><g:message code="cotizacionDeComplejo.nombreSolicitante.label" default="Nombre Solicitante" /></span>
					
						<span class="property-value" aria-labelledby="nombreSolicitante-label"><g:fieldValue bean="${cotizacionDeComplejoInstance}" field="nombreSolicitante"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeComplejoInstance?.empresaSolicitante}">
				<li class="fieldcontain">
					<span id="empresaSolicitante-label" class="property-label"><g:message code="cotizacionDeComplejo.empresaSolicitante.label" default="Empresa Solicitante" /></span>
					
						<span class="property-value" aria-labelledby="empresaSolicitante-label"><g:fieldValue bean="${cotizacionDeComplejoInstance}" field="empresaSolicitante"/></span>
					
				</li>
				</g:if>

                <h1 style="font-weight: bold">Valoracion</h1>
			
				<g:if test="${cotizacionDeComplejoInstance?.fechaDeCotizacion}">
				<li class="fieldcontain">
					<span id="fechaDeCotizacion-label" class="property-label"><g:message code="cotizacionDeComplejo.fechaDeCotizacion.label" default="Fecha De Cotizacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeCotizacion-label"><g:formatDate date="${cotizacionDeComplejoInstance?.fechaDeCotizacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeComplejoInstance?.cotizacionDiaria}">
				<li class="fieldcontain">
					<span id="cotizacionDiaria-label" class="property-label"><g:message code="cotizacionDeComplejo.cotizacionDiaria.label" default="Cotizacion Diaria" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionDiaria-label"><g:link controller="cotizacionDiariaDeMinerales" action="show" id="${cotizacionDeComplejoInstance?.cotizacionDiaria?.id}">${cotizacionDeComplejoInstance?.cotizacionDiaria?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeComplejoInstance?.leyZinc}">
				<li class="fieldcontain">
					<span id="leyZinc-label" class="property-label"><g:message code="cotizacionDeComplejo.leyZinc.label" default="Ley Zinc" /></span>
					
						<span class="property-value" aria-labelledby="leyZinc-label"><g:fieldValue bean="${cotizacionDeComplejoInstance}" field="leyZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeComplejoInstance?.leyPlomo}">
				<li class="fieldcontain">
					<span id="leyPlomo-label" class="property-label"><g:message code="cotizacionDeComplejo.leyPlomo.label" default="Ley Plomo" /></span>
					
						<span class="property-value" aria-labelledby="leyPlomo-label"><g:fieldValue bean="${cotizacionDeComplejoInstance}" field="leyPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeComplejoInstance?.leyPlata}">
				<li class="fieldcontain">
					<span id="leyPlata-label" class="property-label"><g:message code="cotizacionDeComplejo.leyPlata.label" default="Ley Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyPlata-label"><g:fieldValue bean="${cotizacionDeComplejoInstance}" field="leyPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeComplejoInstance?.modoValoracion}">
				<li class="fieldcontain">
					<span id="modoValoracion-label" class="property-label"><g:message code="cotizacionDeComplejo.modoValoracion.label" default="Modo Valoracion" /></span>
					
						<span class="property-value" aria-labelledby="modoValoracion-label"><g:fieldValue bean="${cotizacionDeComplejoInstance}" field="modoValoracion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeComplejoInstance?.modoValoracion.equals("TABLA")}">
				<li class="fieldcontain">
					<span id="tablaComplejo-label" class="property-label"><g:message code="cotizacionDeComplejo.tablaComplejo.label" default="Tabla Complejo" /></span>
					
						<span class="property-value" aria-labelledby="tablaComplejo-label"><g:link controller="tablaOrigenCotizacionesComplejo" action="show" id="${cotizacionDeComplejoInstance?.tablaComplejo?.id}">${cotizacionDeComplejoInstance?.tablaComplejo?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>

                <g:if test="${!cotizacionDeComplejoInstance?.modoValoracion.equals("TABLA")}">
				<li class="fieldcontain">
					<span id="terminosDeContrato-label" class="property-label"><g:message code="cotizacionDeComplejo.terminosDeContrato.label" default="Terminos De Contrato" /></span>
					
						<span class="property-value" aria-labelledby="terminosDeContrato-label"><g:link controller="terminosDeContrato" action="show" id="${cotizacionDeComplejoInstance?.terminosDeContrato?.id}">${cotizacionDeComplejoInstance?.terminosDeContrato?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeComplejoInstance?.valorTonelada}">
				<li class="fieldcontain">
					<span id="valorTonelada-label" class="property-label"><g:message code="cotizacionDeComplejo.valorTonelada.label" default="Valor Tonelada" /></span>
					
						<span class="property-value" aria-labelledby="valorTonelada-label"><g:fieldValue bean="${cotizacionDeComplejoInstance}" field="valorTonelada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeComplejoInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="cotizacionDeComplejo.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${cotizacionDeComplejoInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeComplejoInstance?.valorEstimado}">
				<li class="fieldcontain">
					<span id="valorEstimado-label" class="property-label"><g:message code="cotizacionDeComplejo.valorEstimado.label" default="Valor Estimado" /></span>
					
						<span class="property-value" aria-labelledby="valorEstimado-label"><g:fieldValue bean="${cotizacionDeComplejoInstance}" field="valorEstimado"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cotizacionDeComplejoInstance?.usuario}">
				<li class="fieldcontain">
					<span id="usuario-label" class="property-label"><g:message code="cotizacionDeComplejo.usuario.label" default="Usuario" /></span>
					
						<span class="property-value" aria-labelledby="usuario-label"><g:link controller="secUser" action="show" id="${cotizacionDeComplejoInstance?.usuario?.id}">${cotizacionDeComplejoInstance?.usuario?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<sec:ifAnyGranted roles="ROLE_ADMIN">
						<g:hiddenField name="id" value="${cotizacionDeComplejoInstance?.id}" />
						<g:link class="edit" action="edit" id="${cotizacionDeComplejoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</sec:ifAnyGranted>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
