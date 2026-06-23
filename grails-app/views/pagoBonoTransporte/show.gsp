
<%@ page import="org.socymet.cancelacion.PagoBonoTransporte" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pagoBonoTransporte.label', default: 'PagoBonoTransporte')}" />
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
        <script>
            $(document).ready(function() {
                $("#vista").val("create");
            });
        </script>
        <g:javascript src="cancelacion/pagoBonoTransporteAutocomplete.js" />
	</head>
	<body>
		<a href="#show-pagoBonoTransporte" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-pagoBonoTransporte" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list pagoBonoTransporte">
			
				<g:if test="${pagoBonoTransporteInstance?.numeroComprobante}">
				<li class="fieldcontain">
					<span id="numeroComprobante-label" class="property-label"><g:message code="pagoBonoTransporte.numeroComprobante.label" default="Numero Comprobante" /></span>
					
						<span class="property-value" aria-labelledby="numeroComprobante-label"><g:fieldValue bean="${pagoBonoTransporteInstance}" field="numeroComprobante"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoTransporteInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="pagoBonoTransporte.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${pagoBonoTransporteInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoTransporteInstance?.nombreCobrador}">
				<li class="fieldcontain">
					<span id="nombreCobrador-label" class="property-label"><g:message code="pagoBonoTransporte.nombreCobrador.label" default="Nombre Cobrador" /></span>
					
						<span class="property-value" aria-labelledby="nombreCobrador-label"><g:fieldValue bean="${pagoBonoTransporteInstance}" field="nombreCobrador"/></span>
					
				</li>
				</g:if>

                <g:if test="${pagoBonoTransporteInstance?.fechaDePago}">
                    <li class="fieldcontain">
                        <span id="fechaDePago-label" class="property-label"><g:message code="pagoBonoTransporte.fechaDePago.label" default="Fecha De Pago" /></span>

                        <span class="property-value" aria-labelledby="fechaDePago-label"><g:formatDate date="${pagoBonoTransporteInstance?.fechaDePago}" /></span>

                    </li>
                </g:if>

                <div>
                    <h1 style="font-weight: bold">Parametros de Filtrado</h1>
                </div>

                <g:if test="${pagoBonoTransporteInstance?.automovil}">
				<li class="fieldcontain">
					<span id="automovil-label" class="property-label"><g:message code="pagoBonoTransporte.automovil.label" default="Automovil" /></span>
					
						<span class="property-value" aria-labelledby="automovil-label"><g:link controller="automovil" action="show" id="${pagoBonoTransporteInstance?.automovil?.id}">${pagoBonoTransporteInstance?.automovil?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>

				<g:if test="${pagoBonoTransporteInstance?.fechaInicial}">
				<li class="fieldcontain">
					<span id="fechaInicial-label" class="property-label"><g:message code="pagoBonoTransporte.fechaInicial.label" default="Fecha Inicial" /></span>
					
						<span class="property-value" aria-labelledby="fechaInicial-label"><g:formatDate date="${pagoBonoTransporteInstance?.fechaInicial}" format="MM/yyyy" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoTransporteInstance?.fechaFinal}">
				<li class="fieldcontain">
					<span id="fechaFinal-label" class="property-label"><g:message code="pagoBonoTransporte.fechaFinal.label" default="Fecha Final" /></span>
					
						<span class="property-value" aria-labelledby="fechaFinal-label"><g:formatDate date="${pagoBonoTransporteInstance?.fechaFinal}" format="MM/yyyy" /></span>
					
				</li>
				</g:if>

                <g:hiddenField name="acumulacionPorMes" value="${pagoBonoTransporteInstance?.acumulacionPorMes}" />
                <div style="width: 350px; margin-left: auto; margin-right: auto;">
                    <table id="acumulacionPorMesTabla"></table>
                </div>

                <div>
                    <h1 style="font-weight: bold">Totales</h1>
                </div>

                <g:if test="${pagoBonoTransporteInstance?.numeroMesesPagables}">
                    <li class="fieldcontain">
                        <span id="numeroMesesPagables-label" class="property-label"><g:message code="pagoBonoTransporte.numeroMesesPagables.label" default="Numero Meses Pagables" /></span>

                        <span class="property-value" aria-labelledby="numeroMesesPagables-label"><g:fieldValue bean="${pagoBonoTransporteInstance}" field="numeroMesesPagables"/></span>

                    </li>
                </g:if>

                <g:if test="${pagoBonoTransporteInstance?.numeroMesesAcumulados}">
				<li class="fieldcontain">
					<span id="numeroMesesAcumulados-label" class="property-label"><g:message code="pagoBonoTransporte.numeroMesesAcumulados.label" default="Numero Meses Acumulados" /></span>
					
						<span class="property-value" aria-labelledby="numeroMesesAcumulados-label"><g:fieldValue bean="${pagoBonoTransporteInstance}" field="numeroMesesAcumulados"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoTransporteInstance?.totalKilosBrutos}">
				<li class="fieldcontain">
					<span id="totalKilosBrutos-label" class="property-label"><g:message code="pagoBonoTransporte.totalKilosBrutos.label" default="Total Kilos Brutos" /></span>
					
						<span class="property-value" aria-labelledby="totalKilosBrutos-label"><g:fieldValue bean="${pagoBonoTransporteInstance}" field="totalKilosBrutos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoTransporteInstance?.totalPagable}">
				<li class="fieldcontain">
					<span id="totalPagable-label" class="property-label"><g:message code="pagoBonoTransporte.totalPagable.label" default="Total Pagable" /></span>
					
						<span class="property-value" aria-labelledby="totalPagable-label"><g:fieldValue bean="${pagoBonoTransporteInstance}" field="totalPagable"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoTransporteInstance?.totalPagableLiteral}">
				<li class="fieldcontain">
					<span id="totalPagableLiteral-label" class="property-label"><g:message code="pagoBonoTransporte.totalPagableLiteral.label" default="Total Pagable Literal" /></span>
					
						<span class="property-value" aria-labelledby="totalPagableLiteral-label"><g:fieldValue bean="${pagoBonoTransporteInstance}" field="totalPagableLiteral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoTransporteInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="pagoBonoTransporte.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${pagoBonoTransporteInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>

			</ol>

            <fieldset class="buttons">
                <div style="float: left">
                    <g:form>
                        <g:hiddenField name="id" value="${pagoBonoTransporteInstance?.id}" />
                        <g:link class="edit" action="edit" id="${pagoBonoTransporteInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </g:form>
                </div>
                <div style="float: right">
                    <table>
                        <tr>
                            <td>
                                <g:jasperReport controller="pagoBonoTransporte" action="createReport" jasper="comprobante_pago_bono_transporte" format="PDF" _format="PDF" name="ORDEN_PAGO_BONO_TRANSPORTE_${pagoBonoTransporteInstance.numeroComprobante}">
                                    <input type="hidden" name="pagoBonoId" value="${pagoBonoTransporteInstance.id}" />
                                </g:jasperReport>
                            </td>
                            <td>
                                <g:jasperReport controller="pagoBonoTransporte" action="createReport" jasper="detalle_comprobante_pago_bono_transporte" format="PDF" _format="PDF" name="DETALLE_ORDEN_PAGO_BONO_TRANSPORTE_${pagoBonoTransporteInstance.numeroComprobante}">
                                    <input type="hidden" name="pagoBonoId" value="${pagoBonoTransporteInstance.id}" />
                                </g:jasperReport>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
		</div>
	</body>
</html>
