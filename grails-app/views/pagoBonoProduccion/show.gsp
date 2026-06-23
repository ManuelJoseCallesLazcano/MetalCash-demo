
<%@ page import="org.socymet.cancelacion.PagoBonoProduccion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pagoBonoProduccion.label', default: 'PagoBonoProduccion')}" />
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
        <g:javascript src="cancelacion/pagoBonoProduccionAutocomplete.js" />
	</head>
	<body>
		<a href="#show-pagoBonoProduccion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-pagoBonoProduccion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list pagoBonoProduccion">
			
				<g:if test="${pagoBonoProduccionInstance?.numeroComprobante}">
				<li class="fieldcontain">
					<span id="numeroComprobante-label" class="property-label"><g:message code="pagoBonoProduccion.numeroComprobante.label" default="Numero Comprobante" /></span>
					
						<span class="property-value" aria-labelledby="numeroComprobante-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="numeroComprobante"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoProduccionInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="pagoBonoProduccion.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoProduccionInstance?.nombreCobrador}">
				<li class="fieldcontain">
					<span id="nombreCobrador-label" class="property-label"><g:message code="pagoBonoProduccion.nombreCobrador.label" default="Nombre Cobrador" /></span>
					
						<span class="property-value" aria-labelledby="nombreCobrador-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="nombreCobrador"/></span>
					
				</li>
				</g:if>

                <g:if test="${pagoBonoProduccionInstance?.fechaDePago}">
                    <li class="fieldcontain">
                        <span id="fechaDePago-label" class="property-label"><g:message code="pagoBonoProduccion.fechaDePago.label" default="Fecha De Pago" /></span>

                        <span class="property-value" aria-labelledby="fechaDePago-label"><g:formatDate date="${pagoBonoProduccionInstance?.fechaDePago}" /></span>

                    </li>
                </g:if>

                <div>
                    <h1 style="font-weight: bold">Parametros de Filtrado</h1>
                </div>
			
				<g:if test="${pagoBonoProduccionInstance?.tipoSeleccion}">
				<li class="fieldcontain">
					<span id="tipoSeleccion-label" class="property-label"><g:message code="pagoBonoProduccion.tipoSeleccion.label" default="Tipo Seleccion" /></span>
					
						<span class="property-value" aria-labelledby="tipoSeleccion-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="tipoSeleccion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoProduccionInstance?.tipoSeleccion.equals("INDIVIDUAL")}">
				<li class="fieldcontain">
					<span id="cliente-label" class="property-label"><g:message code="pagoBonoProduccion.cliente.label" default="Cliente" /></span>
					
						<span class="property-value" aria-labelledby="cliente-label"><g:link controller="cliente" action="show" id="${pagoBonoProduccionInstance?.cliente?.id}">${pagoBonoProduccionInstance?.cliente?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoProduccionInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="pagoBonoProduccion.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${pagoBonoProduccionInstance?.empresa?.id}">${pagoBonoProduccionInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>

                <g:if test="${pagoBonoProduccionInstance?.tipoSeleccion.equals("CUADRILLA")}">
				<li class="fieldcontain">
					<span id="cuadrilla-label" class="property-label"><g:message code="pagoBonoProduccion.cuadrilla.label" default="Cuadrilla" /></span>
					
						<span class="property-value" aria-labelledby="cuadrilla-label"><g:link controller="cuadrilla" action="show" id="${pagoBonoProduccionInstance?.cuadrilla?.id}">${pagoBonoProduccionInstance?.cuadrilla?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoProduccionInstance?.fechaInicial}">
				<li class="fieldcontain">
					<span id="fechaInicial-label" class="property-label"><g:message code="pagoBonoProduccion.fechaInicial.label" default="Fecha Inicial" /></span>
					
						<span class="property-value" aria-labelledby="fechaInicial-label"><g:formatDate date="${pagoBonoProduccionInstance?.fechaInicial}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoProduccionInstance?.fechaFinal}">
				<li class="fieldcontain">
					<span id="fechaFinal-label" class="property-label"><g:message code="pagoBonoProduccion.fechaFinal.label" default="Fecha Final" /></span>
					
						<span class="property-value" aria-labelledby="fechaFinal-label"><g:formatDate date="${pagoBonoProduccionInstance?.fechaFinal}" /></span>
					
				</li>
				</g:if>
			
				%{--<g:if test="${pagoBonoProduccionInstance?.acumulacionPorMes}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="acumulacionPorMes-label" class="property-label"><g:message code="pagoBonoProduccion.acumulacionPorMes.label" default="Acumulacion Por Mes" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="acumulacionPorMes-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="acumulacionPorMes"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
                <div style="width: 350px; margin-left: auto; margin-right: auto;">
                    <table id="acumulacionPorMesTabla"></table>
                </div>
                <g:hiddenField name="acumulacionPorMes" value="${pagoBonoProduccionInstance?.acumulacionPorMes}" />

                <div>
                    <h1 style="font-weight: bold">Totales</h1>
                </div>

                <g:if test="${pagoBonoProduccionInstance?.numeroMesesPagables}">
                    <li class="fieldcontain">
                        <span id="numeroMesesPagables-label" class="property-label"><g:message code="pagoBonoProduccion.numeroMesesPagables.label" default="Numero Meses Pagables" /></span>

                        <span class="property-value" aria-labelledby="numeroMesesPagables-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="numeroMesesPagables"/></span>

                    </li>
                </g:if>

                <g:if test="${pagoBonoProduccionInstance?.numeroMesesAcumulados}">
				<li class="fieldcontain">
					<span id="numeroMesesAcumulados-label" class="property-label"><g:message code="pagoBonoProduccion.numeroMesesAcumulados.label" default="Numero Meses Acumulados" /></span>
					
						<span class="property-value" aria-labelledby="numeroMesesAcumulados-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="numeroMesesAcumulados"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoProduccionInstance?.totalKilosNetosSecos}">
				<li class="fieldcontain">
					<span id="totalKilosNetosSecos-label" class="property-label"><g:message code="pagoBonoProduccion.totalKilosNetosSecos.label" default="Total Kilos Netos Secos" /></span>
					
						<span class="property-value" aria-labelledby="totalKilosNetosSecos-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="totalKilosNetosSecos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoProduccionInstance?.tipoDeCambio}">
				<li class="fieldcontain">
					<span id="tipoDeCambio-label" class="property-label"><g:message code="pagoBonoProduccion.tipoDeCambio.label" default="Tipo De Cambio" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeCambio-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="tipoDeCambio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoProduccionInstance?.bonoPorTonelada}">
				<li class="fieldcontain">
					<span id="bonoPorTonelada-label" class="property-label"><g:message code="pagoBonoProduccion.bonoPorTonelada.label" default="Bono Por Tonelada" /></span>
					
						<span class="property-value" aria-labelledby="bonoPorTonelada-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="bonoPorTonelada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoProduccionInstance?.totalPagable}">
				<li class="fieldcontain">
					<span id="totalPagable-label" class="property-label"><g:message code="pagoBonoProduccion.totalPagable.label" default="Total Pagable" /></span>
					
						<span class="property-value" aria-labelledby="totalPagable-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="totalPagable"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoProduccionInstance?.totalPagableLiteral}">
				<li class="fieldcontain">
					<span id="totalPagableLiteral-label" class="property-label"><g:message code="pagoBonoProduccion.totalPagableLiteral.label" default="Total Pagable Literal" /></span>
					
						<span class="property-value" aria-labelledby="totalPagableLiteral-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="totalPagableLiteral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoProduccionInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="pagoBonoProduccion.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${pagoBonoProduccionInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
			</ol>

        <fieldset class="buttons">
            <div style="float: left">
                <g:form>
                    <g:hiddenField name="id" value="${pagoBonoProduccionInstance?.id}" />
                    <g:link class="edit" action="edit" id="${pagoBonoProduccionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </g:form>
            </div>
            <div style="float: right">
                <table>
                    <tr>
                        <td>
                            <g:jasperReport controller="pagoBonoProduccion" action="createReport" jasper="comprobante_pago_bono_produccion" format="PDF" _format="PDF" name="ORDEN_PAGO_BONO_PRODUCCION_${pagoBonoProduccionInstance.numeroComprobante}">
                                <input type="hidden" name="pagoBonoId" value="${pagoBonoProduccionInstance.id}" />
                            </g:jasperReport>
                        </td>
                        <td>
                            <g:jasperReport controller="pagoBonoProduccion" action="createReport" jasper="detalle_comprobante_pago_bono_produccion" format="PDF" _format="PDF" name="DETALLE_ORDEN_PAGO_BONO_PRODUCCION_${pagoBonoProduccionInstance.numeroComprobante}">
                                <input type="hidden" name="pagoBonoId" value="${pagoBonoProduccionInstance.id}" />
                            </g:jasperReport>
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
		</div>
	</body>
</html>
