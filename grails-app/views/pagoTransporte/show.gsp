
<%@ page import="org.socymet.cancelacion.PagoTransporte" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pagoTransporte.label', default: 'PagoTransporte')}" />
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
%{--        <g:javascript src="cancelacion/pagoTransporteAutocomplete.js" />--}%
	</head>
	<body>
		<a href="#show-pagoTransporte" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-pagoTransporte" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list pagoTransporte">
                <g:if test="${pagoTransporteInstance?.numeroComprobante}">
                    <li class="fieldcontain">
                        <span id="numeroComprobante-label" class="property-label"><g:message code="pagoTransporte.numeroComprobante.label" default="Numero Comprobante" /></span>

                        <span class="property-value" aria-labelledby="numeroComprobante-label"><g:formatNumber number="${pagoTransporteInstance.numeroComprobante}" format="000000"/> </span>

                    </li>
                </g:if>
			
				<g:if test="${pagoTransporteInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="pagoTransporte.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${pagoTransporteInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoTransporteInstance?.nombreCobrador}">
				<li class="fieldcontain">
					<span id="nombreCobrador-label" class="property-label"><g:message code="pagoTransporte.nombreCobrador.label" default="Nombre Cobrador" /></span>
					
						<span class="property-value" aria-labelledby="nombreCobrador-label"><g:fieldValue bean="${pagoTransporteInstance}" field="nombreCobrador"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoTransporteInstance?.fechaDePago}">
				<li class="fieldcontain">
					<span id="fechaDePago-label" class="property-label"><g:message code="pagoTransporte.fechaDePago.label" default="Fecha De Pago" /></span>
					
						<span class="property-value" aria-labelledby="fechaDePago-label"><g:formatDate date="${pagoTransporteInstance?.fechaDePago}" /></span>
					
				</li>
				</g:if>

                <div>
                    <h1 style="font-weight: bold">Lotes Asignados</h1>
                </div>

                <g:if test="${pagoTransporteInstance?.solicitante}">
                    <li class="fieldcontain">
                        <span id="solicitante-label" class="property-label"><g:message code="pagoTransporte.solicitante.label" default="Solicitante" /></span>

                        <span class="property-value" aria-labelledby="solicitante-label"><g:fieldValue bean="${pagoTransporteInstance}" field="solicitante"/></span>

                    </li>
                </g:if>

                <g:if test="${pagoTransporteInstance?.solicitante.equals("Empresa")}">
                    <li class="fieldcontain">
                        <span id="empresa-label" class="property-label"><g:message code="pagoTransporte.empresa.label" default="Empresa" /></span>

                        <span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${pagoTransporteInstance?.empresa?.id}">${pagoTransporteInstance?.empresa?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${pagoTransporteInstance?.solicitante.equals("Particular")}">
                    <li class="fieldcontain">
                        <span id="automovil-label" class="property-label"><g:message code="pagoTransporte.automovil.label" default="Automovil" /></span>

                        <span class="property-value" aria-labelledby="automovil-label"><g:link controller="automovil" action="show" id="${pagoTransporteInstance?.automovil?.id}">${pagoTransporteInstance?.automovil?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:hiddenField name="lotes" value="${pagoTransporteInstance?.lotes}" />

                <div style="width: 840px; margin-left: auto; margin-right: auto;">
                    <table id="lotesAsignados"></table>
                </div>

                <g:if test="${pagoTransporteInstance?.pesoBruto}">
                    <li class="fieldcontain">
                        <span id="pesoBruto-label" class="property-label"><g:message code="pagoTransporte.pesoBruto.label" default="pesoBruto" /></span>

                        <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${pagoTransporteInstance}" field="pesoBruto"/></span>

                    </li>
                </g:if>

                <g:if test="${pagoTransporteInstance?.precioTonelada}">
                    <li class="fieldcontain">
                        <span id="precioTonelada-label" class="property-label"><g:message code="pagoTransporte.precioTonelada.label" default="precioTonelada" /></span>

                        <span class="property-value" aria-labelledby="precioTonelada-label"><g:fieldValue bean="${pagoTransporteInstance}" field="precioTonelada"/></span>

                    </li>
                </g:if>
                
                <g:if test="${pagoTransporteInstance?.descripcion}">
                    <li class="fieldcontain">
                        <span id="descripcion-label" class="property-label"><g:message code="pagoTransporte.descripcion.label" default="Descripcion" /></span>

                        <span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${pagoTransporteInstance}" field="descripcion"/></span>

                    </li>
                </g:if>
                
                <g:if test="${pagoTransporteInstance?.total}">
				<li class="fieldcontain">
					<span id="total-label" class="property-label"><g:message code="pagoTransporte.total.label" default="Total" /></span>
					
						<span class="property-value" aria-labelledby="total-label"><g:fieldValue bean="${pagoTransporteInstance}" field="total"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoTransporteInstance?.totalAnticipos}">
				<li class="fieldcontain">
					<span id="totalAnticipos-label" class="property-label"><g:message code="pagoTransporte.totalAnticipos.label" default="Total Anticipos" /></span>
					
						<span class="property-value" aria-labelledby="totalAnticipos-label"><g:fieldValue bean="${pagoTransporteInstance}" field="totalAnticipos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoTransporteInstance?.totalPagable}">
				<li class="fieldcontain">
					<span id="totalPagable-label" class="property-label"><g:message code="pagoTransporte.totalPagable.label" default="Total Pagable" /></span>
					
						<span class="property-value" aria-labelledby="totalPagable-label"><g:fieldValue bean="${pagoTransporteInstance}" field="totalPagable"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoTransporteInstance?.totalPagableLiteral}">
				<li class="fieldcontain">
					<span id="totalPagableLiteral-label" class="property-label"><g:message code="pagoTransporte.totalPagableLiteral.label" default="Total Pagable Literal" /></span>
					
						<span class="property-value" aria-labelledby="totalPagableLiteral-label"><g:fieldValue bean="${pagoTransporteInstance}" field="totalPagableLiteral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoTransporteInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="pagoTransporte.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${pagoTransporteInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
				%{--<g:if test="${pagoTransporteInstance?.usuario}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="usuario-label" class="property-label"><g:message code="pagoTransporte.usuario.label" default="Usuario" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="usuario-label"><g:link controller="secUser" action="show" id="${pagoTransporteInstance?.usuario?.id}">${pagoTransporteInstance?.usuario?.encodeAsHTML()}</g:link></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			
			</ol>

            <fieldset class="buttons">
                <div style="float: left">
                    <g:form>
                        <sec:ifAnyGranted roles="ROLE_ADMIN">
                            <g:hiddenField name="id" value="${pagoTransporteInstance?.id}" />
%{--                            <g:link class="edit" action="edit" id="${pagoTransporteInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
                        %{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
                        </sec:ifAnyGranted>
                    </g:form>
                </div>
                <div style="float: right">
                    <table>
                        <tr>
                            <td>
                                <g:jasperReport controller="pagoTransporte" action="createReport" jasper="comprobante_pago_transporte" format="PDF" _format="PDF" name="ORDEN_PAGO_TRANSPORTE_${pagoTransporteInstance.numeroComprobante}">
                                    <input type="hidden" name="pagoTransporteId" value="${pagoTransporteInstance.id}" />
                                </g:jasperReport>
                            </td>
%{--                            <td>--}%
%{--                                <g:jasperReport controller="pagoTransporte" action="createReport" jasper="detalle_comprobante_pago_transporte" format="PDF" _format="PDF" name="DETALLE_ORDEN_PAGO_TRANSPORTE_${pagoTransporteInstance.numeroComprobante}">--}%
%{--                                    <input type="hidden" name="pagoTransporteId" value="${pagoTransporteInstance.id}" />--}%
%{--                                </g:jasperReport>--}%
%{--                            </td>--}%
                        </tr>
                    </table>
                </div>
            </fieldset>
		</div>
	</body>
</html>
