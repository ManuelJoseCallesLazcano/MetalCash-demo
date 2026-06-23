
<%@ page import="org.socymet.cotizaciones.TablaPrecioPorLme" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tablaPrecioPorLme.label', default: 'TablaPrecioPorLme')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <g:javascript src="notify.min.js" />
        <g:javascript src="cotizaciones/tablaPrecioPorLme.js" />
	</head>
	<body>
		<a href="#show-tablaPrecioPorLme" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tablaPrecioPorLme" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list tablaPrecioPorLme">
			
				<g:if test="${tablaPrecioPorLmeInstance?.nombreTabla}">
				<li class="fieldcontain">
					<span id="nombreTabla-label" class="property-label"><g:message code="tablaPrecioPorLme.nombreTabla.label" default="Nombre Tabla" /></span>
					
						<span class="property-value" aria-labelledby="nombreTabla-label"><g:fieldValue bean="${tablaPrecioPorLmeInstance}" field="nombreTabla"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tablaPrecioPorLmeInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="tablaPrecioPorLme.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${tablaPrecioPorLmeInstance?.empresa?.id}">${tablaPrecioPorLmeInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${tablaPrecioPorLmeInstance?.naturalezaMineral}">
				<li class="fieldcontain">
					<span id="naturalezaMineral-label" class="property-label"><g:message code="tablaPrecioPorLme.naturalezaMineral.label" default="Naturaleza Mineral" /></span>
					
						<span class="property-value" aria-labelledby="naturalezaMineral-label"><g:fieldValue bean="${tablaPrecioPorLmeInstance}" field="naturalezaMineral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tablaPrecioPorLmeInstance?.cotizacionDiariaDeMinerales}">
				<li class="fieldcontain">
					<span id="cotizacionDiariaDeMinerales-label" class="property-label"><g:message code="tablaPrecioPorLme.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionDiariaDeMinerales-label"><g:link controller="cotizacionDiariaDeMinerales" action="show" id="${tablaPrecioPorLmeInstance?.cotizacionDiariaDeMinerales?.id}">${tablaPrecioPorLmeInstance?.cotizacionDiariaDeMinerales?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:hiddenField name="tablaDePrecios" value="${tablaPrecioPorLmeInstance?.tablaDePrecios}"/>

<h1 style="font-weight: bold">Tabla de Precios</h1>
                <div style="width: 360px; margin-left: auto; margin-right: auto;"><table id="list4"></table></div>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${tablaPrecioPorLmeInstance?.id}" />
					<g:link class="edit" action="edit" id="${tablaPrecioPorLmeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
