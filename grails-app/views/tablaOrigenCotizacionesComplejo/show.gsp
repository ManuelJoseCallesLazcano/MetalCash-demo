
<%@ page import="org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tablaOrigenCotizacionesComplejo.label', default: 'TablaOrigenCotizacionesComplejo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-tablaOrigenCotizacionesComplejo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tablaOrigenCotizacionesComplejo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list tablaOrigenCotizacionesComplejo">
			
				<g:if test="${tablaOrigenCotizacionesComplejoInstance?.nombreTabla}">
				<li class="fieldcontain">
					<span id="nombreTabla-label" class="property-label"><g:message code="tablaOrigenCotizacionesComplejo.nombreTabla.label" default="Nombre Tabla" /></span>
					
						<span class="property-value" aria-labelledby="nombreTabla-label"><g:fieldValue bean="${tablaOrigenCotizacionesComplejoInstance}" field="nombreTabla"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tablaOrigenCotizacionesComplejoInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="tablaOrigenCotizacionesComplejo.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${tablaOrigenCotizacionesComplejoInstance?.empresa?.id}">${tablaOrigenCotizacionesComplejoInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>

                %{--<g:if test="${tablaOrigenCotizacionesComplejoInstance?.naturalezaMineral}">--}%
                    %{--<li class="fieldcontain">--}%
                        %{--<span id="naturalezaMineral-label" class="property-label"><g:message code="tablaOrigenCotizacionesComplejo.naturalezaMineral.label" default="Naturaleza Mineral" /></span>--}%

                        %{--<span class="property-value" aria-labelledby="naturalezaMineral-label"><g:fieldValue bean="${tablaOrigenCotizacionesComplejoInstance}" field="naturalezaMineral"/></span>--}%

                    %{--</li>--}%
                %{--</g:if>--}%
			
				<g:if test="${tablaOrigenCotizacionesComplejoInstance?.nombreArchivo}">
				<li class="fieldcontain">
					<span id="nombreArchivo-label" class="property-label"><g:message code="tablaOrigenCotizacionesComplejo.nombreArchivo.label" default="Nombre Archivo" /></span>
					
						<span class="property-value" aria-labelledby="nombreArchivo-label"><g:fieldValue bean="${tablaOrigenCotizacionesComplejoInstance}" field="nombreArchivo"/><g:link action="download" id="${tablaOrigenCotizacionesComplejoInstance.id}">Descargar archivo</g:link> </span>
					
				</li>
				</g:if>

				<g:if test="${tablaOrigenCotizacionesComplejoInstance?.fechaSubida}">
				<li class="fieldcontain">
					<span id="fechaSubida-label" class="property-label"><g:message code="tablaOrigenCotizacionesComplejo.fechaSubida.label" default="Fecha Subida" /></span>
					
						<span class="property-value" aria-labelledby="fechaSubida-label"><g:formatDate date="${tablaOrigenCotizacionesComplejoInstance?.fechaSubida}" /></span>
					
				</li>
				</g:if>
			

			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${tablaOrigenCotizacionesComplejoInstance?.id}" />
					<g:link class="edit" action="edit" id="${tablaOrigenCotizacionesComplejoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
