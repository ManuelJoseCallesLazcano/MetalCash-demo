<%@ page import="org.socymet.proveedor.BonoEmpresa" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bono.label', default: 'BonoEmpresa')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>

        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="jquery.tabletojson.js" />
        <g:javascript src="creador_tabla_calidad.js" />
        <g:javascript src="creador_tabla_cantidad.js" />
        <g:javascript src="creador_tabla_incentivo.js" />
        <g:javascript src="jquery.collapsible.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <script type="text/javascript">
            $(document).ready(function() {
                //collapsible management
                $('.collapsible').collapsible({
                    //defaultOpen: 'nav-section1,nav-section3'
                });
            });
        </script>
	</head>
	<body>
		<a href="#create-bono" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="create-bono" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${bonoInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${bonoInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
