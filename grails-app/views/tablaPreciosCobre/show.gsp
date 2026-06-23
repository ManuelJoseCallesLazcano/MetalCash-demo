
<%@ page import="org.socymet.cotizaciones.TablaPreciosCobre" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tablaPreciosCobre.label', default: 'TablaPreciosCobre')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <g:javascript src="cotizaciones/tablaPreciosCobre.js" />
	</head>
	<body>
		<a href="#show-tablaPreciosCobre" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tablaPreciosCobre" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list tablaPreciosCobre">
			
				<g:if test="${tablaPreciosCobreInstance?.nombreTabla}">
				<li class="fieldcontain">
					<span id="nombreTabla-label" class="property-label"><g:message code="tablaPreciosCobre.nombreTabla.label" default="Nombre Tabla" /></span>
					
						<span class="property-value" aria-labelledby="nombreTabla-label"><g:fieldValue bean="${tablaPreciosCobreInstance}" field="nombreTabla"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tablaPreciosCobreInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="tablaPreciosCobre.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${tablaPreciosCobreInstance?.empresa?.id}">${tablaPreciosCobreInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${tablaPreciosCobreInstance?.leyInicial}">
				<li class="fieldcontain">
					<span id="leyInicial-label" class="property-label"><g:message code="tablaPreciosCobre.leyInicial.label" default="Ley Inicial" /></span>
					
						<span class="property-value" aria-labelledby="leyInicial-label"><g:fieldValue bean="${tablaPreciosCobreInstance}" field="leyInicial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tablaPreciosCobreInstance?.leyFinal}">
				<li class="fieldcontain">
					<span id="leyFinal-label" class="property-label"><g:message code="tablaPreciosCobre.leyFinal.label" default="Ley Final" /></span>
					
						<span class="property-value" aria-labelledby="leyFinal-label"><g:fieldValue bean="${tablaPreciosCobreInstance}" field="leyFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tablaPreciosCobreInstance?.valorPorPunto}">
				<li class="fieldcontain">
					<span id="valorPorPunto-label" class="property-label"><g:message code="tablaPreciosCobre.valorPorPunto.label" default="Valor Por Punto" /></span>
					
						<span class="property-value" aria-labelledby="valorPorPunto-label"><g:fieldValue bean="${tablaPreciosCobreInstance}" field="valorPorPunto"/></span>
					
				</li>
				</g:if>

                <h1 style="font-weight: bold">Tabla de Cotizaciones</h1>
                <g:hiddenField name="tablaDePrecios" value="${tablaPreciosCobreInstance?.tablaDePrecios}"/>

                <div style="width: 270px; margin-left: auto; margin-right: auto;"><table id="list4"></table></div>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${tablaPreciosCobreInstance?.id}" />
					<g:link class="edit" action="edit" id="${tablaPreciosCobreInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
