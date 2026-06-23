
<%@ page import="org.socymet.cotizaciones.TablaPreciosOro" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tablaPreciosOro.label', default: 'TablaPreciosOro')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
		<g:javascript src="jquery-1.10.1.min.js" />
		<g:javascript src="jquery-ui-1.10.3.custom.min.js" />
		<g:javascript src="i18n/grid.locale-es.js" />
		<g:javascript src="jquery.jqGrid.min.js" />
		<g:javascript src="notify.min.js" />
		<g:javascript src="jquery.alphanum.js" />
		<g:javascript src="cotizaciones/tablaPreciosOro.js" />
	</head>
	<body>
		<a href="#show-tablaPreciosOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tablaPreciosOro" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list tablaPreciosOro">
			
				<g:if test="${tablaPreciosOroInstance?.nombreTabla}">
				<li class="fieldcontain">
					<span id="nombreTabla-label" class="property-label"><g:message code="tablaPreciosOro.nombreTabla.label" default="Nombre Tabla" /></span>
					
						<span class="property-value" aria-labelledby="nombreTabla-label"><g:fieldValue bean="${tablaPreciosOroInstance}" field="nombreTabla"/></span>
					
				</li>
				</g:if>

				<h1 style="font-weight: bold">Tabla de Precios</h1>

				<div style="width: 360px; margin-left: auto; margin-right: auto;"><table id="tablaPreciosOro"></table></div>

				<div class="fieldcontain ${hasErrors(bean: tablaPreciosOroInstance, field: 'tablaPrecios', 'error')} required" style="display:none;">
					<label for="tablaPrecios">
						<g:message code="tablaPreciosOro.tablaPrecios.label" default="Tabla Precios" />
						<span class="required-indicator">*</span>
					</label>
					<g:textField name="tablaPrecios" required="" value="${tablaPreciosOroInstance?.tablaPrecios}" size="90" readonly="readonly"/>

				</div>

				%{--<g:if test="${tablaPreciosOroInstance?.tablaPrecios}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="tablaPrecios-label" class="property-label"><g:message code="tablaPreciosOro.tablaPrecios.label" default="Tabla Precios" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="tablaPrecios-label"><g:fieldValue bean="${tablaPreciosOroInstance}" field="tablaPrecios"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			
			</ol>
			<g:form url="[resource:tablaPreciosOroInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${tablaPreciosOroInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
