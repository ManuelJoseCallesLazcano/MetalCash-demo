
<%@ page import="org.socymet.cotizaciones.AjustePrecioEstano" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName" value="${message(code: 'ajustePrecioEstano.label', default: 'AjustePrecioEstano')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
	<link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
	<g:javascript src="jquery-1.10.1.min.js" />
	<g:javascript src="i18n/grid.locale-es.js" />
	<g:javascript src="jquery.jqGrid.min.js" />
	<g:javascript src="notify.min.js" />
	<g:javascript src="cotizaciones/ajustePrecioEstanoShow.js" />
</head>
<body>
<a href="#show-ajustePrecioEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
	<ul>
		<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
		<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
		<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
	</ul>
</div>
<div id="show-ajustePrecioEstano" class="content scaffold-show" role="main">
	<h1><g:message code="default.show.label" args="[entityName]" /></h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">${flash.message}</div>
	</g:if>
	<ol class="property-list ajustePrecioEstano">

		<g:if test="${ajustePrecioEstanoInstance?.fecha}">
			<li class="fieldcontain">
				<span id="fecha-label" class="property-label"><g:message code="ajustePrecioEstano.fecha.label" default="Fecha" /></span>

				<span class="property-value" aria-labelledby="fecha-label"><g:formatDate date="${ajustePrecioEstanoInstance?.fecha}" format="dd/MM/yyyy"/></span>

			</li>
		</g:if>

		<g:if test="${ajustePrecioEstanoInstance?.cotizacionDiariaDeMinerales}">
			<li class="fieldcontain">
				<span id="cotizacionDiariaDeMinerales-label" class="property-label"><g:message code="ajustePrecioEstano.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" /></span>

				<span class="property-value" aria-labelledby="cotizacionDiariaDeMinerales-label"><g:link controller="cotizacionDiariaDeMinerales" action="show" id="${ajustePrecioEstanoInstance?.cotizacionDiariaDeMinerales?.id}">${ajustePrecioEstanoInstance?.cotizacionDiariaDeMinerales?.encodeAsHTML()}</g:link></span>

			</li>
		</g:if>

		<g:if test="${ajustePrecioEstanoInstance?.tablaCotizacionEstano}">
			<li class="fieldcontain">
				<span id="tablaCotizacionEstano-label" class="property-label"><g:message code="ajustePrecioEstano.tablaCotizacionEstano.label" default="Tabla Cotizacion Estano" /></span>

				<span class="property-value" aria-labelledby="tablaCotizacionEstano-label"><g:link controller="tablaCotizacionEstano" action="show" id="${ajustePrecioEstanoInstance?.tablaCotizacionEstano?.id}">${ajustePrecioEstanoInstance?.tablaCotizacionEstano?.encodeAsHTML()}</g:link></span>

			</li>
		</g:if>

		<g:if test="${ajustePrecioEstanoInstance?.margen}">
			<li class="fieldcontain">
				<span id="margen-label" class="property-label"><g:message code="ajustePrecioEstano.margen.label" default="Margen" /></span>

				<span class="property-value" aria-labelledby="margen-label"><g:fieldValue bean="${ajustePrecioEstanoInstance}" field="margen"/></span>

			</li>
		</g:if>

	%{--<g:if test="${ajustePrecioEstanoInstance?.filaOriginal}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="filaOriginal-label" class="property-label"><g:message code="ajustePrecioEstano.filaOriginal.label" default="Fila Original" /></span>--}%
	%{----}%
	%{--<span class="property-value" aria-labelledby="filaOriginal-label"><g:fieldValue bean="${ajustePrecioEstanoInstance}" field="filaOriginal"/></span>--}%
	%{----}%
	%{--</li>--}%
	%{--</g:if>--}%
	%{----}%
	%{--<g:if test="${ajustePrecioEstanoInstance?.filaAjustada}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="filaAjustada-label" class="property-label"><g:message code="ajustePrecioEstano.filaAjustada.label" default="Fila Ajustada" /></span>--}%
	%{----}%
	%{--<span class="property-value" aria-labelledby="filaAjustada-label"><g:fieldValue bean="${ajustePrecioEstanoInstance}" field="filaAjustada"/></span>--}%
	%{----}%
	%{--</li>--}%
	%{--</g:if>--}%

		<h1 style="font-weight: bold">Fila Original de Tabla de Estano</h1>
		<div style="width: 900px; margin-left: auto; margin-right: auto;"><table id="tablaFilaOriginal"></table></div>

		<div class="fieldcontain ${hasErrors(bean: ajustePrecioEstanoInstance, field: 'filaOriginal', 'error')} required" style="display: none">
			<label for="filaOriginal">
				<g:message code="ajustePrecioEstano.filaOriginal.label" default="Fila Original" />
				<span class="required-indicator">*</span>
			</label>
			<g:textArea name="filaOriginal" cols="40" rows="5" maxlength="2000" required="" value="${ajustePrecioEstanoInstance?.filaOriginal}" readonly="readonly"/>

		</div>

		<h1 style="font-weight: bold">Fila Ajustada de Tabla de Estano</h1>
		<div style="width: 900px; margin-left: auto; margin-right: auto;"><table id="tablaFilaAjustada"></table></div>

		<div class="fieldcontain ${hasErrors(bean: ajustePrecioEstanoInstance, field: 'filaAjustada', 'error')} required" style="display: none">
			<label for="filaAjustada">
				<g:message code="ajustePrecioEstano.filaAjustada.label" default="Fila Ajustada" />
				<span class="required-indicator">*</span>
			</label>
			<g:textArea name="filaAjustada" cols="40" rows="5" maxlength="2000" required="" value="${ajustePrecioEstanoInstance?.filaAjustada}" readonly="readonly"/>

		</div>

		%{--<g:if test="${ajustePrecioEstanoInstance?.usuario}">--}%
		%{--<li class="fieldcontain">--}%
		%{--<span id="usuario-label" class="property-label"><g:message code="ajustePrecioEstano.usuario.label" default="Usuario" /></span>--}%
		%{----}%
		%{--<span class="property-value" aria-labelledby="usuario-label"><g:link controller="secUser" action="show" id="${ajustePrecioEstanoInstance?.usuario?.id}">${ajustePrecioEstanoInstance?.usuario?.encodeAsHTML()}</g:link></span>--}%
		%{----}%
		%{--</li>--}%
		%{--</g:if>--}%

	</ol>
	<g:form url="[resource:ajustePrecioEstanoInstance, action:'delete']" method="DELETE">
		<fieldset class="buttons">
			<g:link class="edit" action="edit" resource="${ajustePrecioEstanoInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
			%{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
		</fieldset>
	</g:form>
</div>
</body>
</html>
