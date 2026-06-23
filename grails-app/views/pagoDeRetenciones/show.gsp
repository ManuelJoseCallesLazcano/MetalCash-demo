
<%@ page import="org.socymet.cancelacion.PagoDeRetenciones" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pagoDeRetenciones.label', default: 'PagoDeRetenciones')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="jquery.jgrowl.min.js" />
        <g:javascript src="notify.min.js" />
        <g:javascript src="NumerosALetras.js" />
        <g:javascript src="cancelacion/pagoRetencionesAutocomplete.js" />
	</head>
	<body>
		<a href="#show-pagoDeRetenciones" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-pagoDeRetenciones" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list pagoDeRetenciones">
			
				<g:if test="${pagoDeRetencionesInstance?.numeroComprobante}">
				<li class="fieldcontain">
					<span id="numeroComprobante-label" class="property-label"><g:message code="pagoDeRetenciones.numeroComprobante.label" default="Numero Comprobante" /></span>
					
						<span class="property-value" aria-labelledby="numeroComprobante-label"><g:fieldValue bean="${pagoDeRetencionesInstance}" field="numeroComprobante"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoDeRetencionesInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="pagoDeRetenciones.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${pagoDeRetencionesInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoDeRetencionesInstance?.nombreCobrador}">
				<li class="fieldcontain">
					<span id="nombreCobrador-label" class="property-label"><g:message code="pagoDeRetenciones.nombreCobrador.label" default="Nombre Cobrador" /></span>
					
						<span class="property-value" aria-labelledby="nombreCobrador-label"><g:fieldValue bean="${pagoDeRetencionesInstance}" field="nombreCobrador"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoDeRetencionesInstance?.beneficiario}">
				<li class="fieldcontain">
					<span id="beneficiario-label" class="property-label"><g:message code="pagoDeRetenciones.beneficiario.label" default="Beneficiario" /></span>
					
						<span class="property-value" aria-labelledby="beneficiario-label"><g:fieldValue bean="${pagoDeRetencionesInstance}" field="beneficiario"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoDeRetencionesInstance?.fechaDePago}">
				<li class="fieldcontain">
					<span id="fechaDePago-label" class="property-label"><g:message code="pagoDeRetenciones.fechaDePago.label" default="Fecha De Pago" /></span>
					
						<span class="property-value" aria-labelledby="fechaDePago-label"><g:formatDate date="${pagoDeRetencionesInstance?.fechaDePago}" /></span>
					
				</li>
				</g:if>

                <div>
                    <h1 style="font-weight: bold">Retenciones seleccionadas</h1>
                </div>
			
				<g:if test="${pagoDeRetencionesInstance?.periodo}">
				<li class="fieldcontain">
					<span id="periodo-label" class="property-label"><g:message code="pagoDeRetenciones.periodo.label" default="Periodo" /></span>
					
						<span class="property-value" aria-labelledby="periodo-label"><g:formatDate date="${pagoDeRetencionesInstance?.periodo}" format="MM/yyyy" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoDeRetencionesInstance?.quincena}">
				<li class="fieldcontain">
					<span id="quincena-label" class="property-label"><g:message code="pagoDeRetenciones.quincena.label" default="Quincena" /></span>
					
						<span class="property-value" aria-labelledby="quincena-label"><g:fieldValue bean="${pagoDeRetencionesInstance}" field="quincena"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoDeRetencionesInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="pagoDeRetenciones.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${pagoDeRetencionesInstance?.empresa?.id}">${pagoDeRetencionesInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>

                <g:hiddenField name="retencionesSeleccionadas" value="${pagoDeRetencionesInstance?.retencionesSeleccionadas}"/>

                <div style="width: 300px; margin-left: auto; margin-right: auto;">
                    <table id="tablaRetencionesSeleccionadas"></table>
                </div>
			
				<g:if test="${pagoDeRetencionesInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="pagoDeRetenciones.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${pagoDeRetencionesInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoDeRetencionesInstance?.totalPagable}">
				<li class="fieldcontain">
					<span id="totalPagable-label" class="property-label"><g:message code="pagoDeRetenciones.totalPagable.label" default="Total Pagable" /></span>
					
						<span class="property-value" aria-labelledby="totalPagable-label"><g:fieldValue bean="${pagoDeRetencionesInstance}" field="totalPagable"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoDeRetencionesInstance?.totalPagableLiteral}">
				<li class="fieldcontain">
					<span id="totalPagableLiteral-label" class="property-label"><g:message code="pagoDeRetenciones.totalPagableLiteral.label" default="Total Pagable Literal" /></span>
					
						<span class="property-value" aria-labelledby="totalPagableLiteral-label"><g:fieldValue bean="${pagoDeRetencionesInstance}" field="totalPagableLiteral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoDeRetencionesInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="pagoDeRetenciones.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${pagoDeRetencionesInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoDeRetencionesInstance?.usuario}">
				<li class="fieldcontain">
					<span id="usuario-label" class="property-label"><g:message code="pagoDeRetenciones.usuario.label" default="Usuario" /></span>
					
						<span class="property-value" aria-labelledby="usuario-label"><g:link controller="secUser" action="show" id="${pagoDeRetencionesInstance?.usuario?.id}">${pagoDeRetencionesInstance?.usuario?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			%{--<g:form>--}%
				%{--<fieldset class="buttons">--}%
					%{--<g:hiddenField name="id" value="${pagoDeRetencionesInstance?.id}" />--}%
					%{--<g:link class="edit" action="edit" id="${pagoDeRetencionesInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
					%{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
				%{--</fieldset>--}%
			%{--</g:form>--}%

            <fieldset class="buttons">
                <div style="float: left">
                    %{--<g:form>--}%
                        %{--<g:hiddenField name="id" value="${pagoDeRetencionesInstance?.id}" />--}%
                        %{--<g:link class="edit" action="edit" id="${pagoDeRetencionesInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
                        %{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
                    %{--</g:form>--}%
                </div>
                <div style="float: right">
                    <table>
                        <tr>
                            <td>
                                <g:jasperReport controller="pagoDeRetenciones" action="createReport" jasper="orden_pago_retenciones" format="PDF" _format="PDF" name="ORDEN_PAGO_RETENCIONES_${pagoDeRetencionesInstance.numeroComprobante}">
                                    <input type="hidden" name="pagoRetencionesId" value="${pagoDeRetencionesInstance.id}" />
                                </g:jasperReport>
                            </td>
                            <td>
                                <g:jasperReport controller="pagoDeRetenciones" action="createReport" jasper="detalle_orden_pago_retenciones" format="PDF" _format="PDF" name="DETALLE_ORDEN_PAGO_RETENCIONES_${pagoDeRetencionesInstance.numeroComprobante}">
                                    <input type="hidden" name="pagoRetencionesId" value="${pagoDeRetencionesInstance.id}" />
                                </g:jasperReport>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
		</div>
	</body>
</html>
