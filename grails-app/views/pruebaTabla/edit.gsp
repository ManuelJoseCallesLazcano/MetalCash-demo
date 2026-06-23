<%@ page import="org.socymet.proveedor.PruebaTabla" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pruebaTabla.label', default: 'PruebaTabla')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="jquery.tabletojson.js" />
        <g:javascript src="conversor_json_tabla.js" />
        <g:javascript src="creador_tabla_retencion.js" />
        <script language="javascript">
            $(document).ready(function() {
                buildHtmlTableToEdit();
            });
        </script>
	</head>
	<body>
		<a href="#edit-pruebaTabla" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="edit-pruebaTabla" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${pruebaTablaInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${pruebaTablaInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${pruebaTablaInstance?.id}" />
				<g:hiddenField name="version" value="${pruebaTablaInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
