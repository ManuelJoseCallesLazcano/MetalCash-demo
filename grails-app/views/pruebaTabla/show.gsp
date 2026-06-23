
<%@ page import="org.socymet.proveedor.PruebaTabla" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pruebaTabla.label', default: 'PruebaTabla')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="conversor_json_tabla.js" />
        <script>
        $(document).ready(function() {
            buildHtmlTableToShow();
        });
        </script>
	</head>
	<body>
		<a href="#show-pruebaTabla" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-pruebaTabla" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list pruebaTabla">
			
				<g:if test="${pruebaTablaInstance?.nombreDeTabla}">
				<li class="fieldcontain">
					<span id="nombreDeTabla-label" class="property-label"><g:message code="pruebaTabla.nombreDeTabla.label" default="Nombre De Tabla" /></span>
					
						<span class="property-value" aria-labelledby="nombreDeTabla-label"><g:fieldValue bean="${pruebaTablaInstance}" field="nombreDeTabla"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pruebaTablaInstance?.contenido}">
				<li class="fieldcontain">
					<span id="contenido-label" class="property-label"><g:message code="pruebaTabla.contenido.label" default="Contenido" /></span>
					
						<span class="property-value" aria-labelledby="contenido-label"><g:fieldValue bean="${pruebaTablaInstance}" field="contenido"/></span>
                    <g:hiddenField name="jsondata" value="${pruebaTablaInstance.contenido}" />

				</li>

				</g:if>

			</ol>
            <table id="retenciones">
            </table>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${pruebaTablaInstance?.id}" />
					<g:link class="edit" action="edit" id="${pruebaTablaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
