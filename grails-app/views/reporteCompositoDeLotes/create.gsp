<%@ page import="org.socymet.org.socymet.reportes.ReporteCompositoDeLotes" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'chosen.css')}" type="text/css" >
        <style type="text/css" media="screen">
        body{
            max-width: 1100px;
        }
        .ui-jqgrid tr.jqgrow td {
            /*font-size: 10px*/
            font-size: 9px
        }
        th.ui-th-column div{
            white-space:normal !important;
            height:auto !important;
            padding:2px;
            /*font-size: 10px;*/
            font-size: 9px;
        }
        </style>
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="chosen.jquery.js" />
        <g:javascript src="notify.min.js" />
        <g:javascript src="NumerosALetras.js" />
        <script>
            $(document).ready(function() {
                $("#vista").val("create");
                $("#agregar").attr("disabled",false);
                $("#quitar").attr("disabled",false);
                $("#actualizar").attr("disabled",true);
            });
        </script>
		<g:javascript src="reportes/filtroEmpresas.js" />
		<g:javascript src="reportes/compositoLotesAutocomplete.js" />
	</head>
	<body>
		<a href="#create-reporteCompositoDeLotes" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="create-reporteCompositoDeLotes" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteCompositoDeLotesInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteCompositoDeLotesInstance}" var="error">
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
