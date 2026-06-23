
<%@ page import="org.socymet.cancelacion.PagoBonoCalidad" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pagoBonoCalidad.label', default: 'PagoBonoCalidad')}" />
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
        <g:javascript src="cancelacion/pagoBonoCalidadAutocomplete.js" />
	</head>
	<body>
		<a href="#show-pagoBonoCalidad" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-pagoBonoCalidad" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list pagoBonoCalidad">
			
				<g:if test="${pagoBonoCalidadInstance?.numeroComprobante}">
				<li class="fieldcontain">
					<span id="numeroComprobante-label" class="property-label"><g:message code="pagoBonoCalidad.numeroComprobante.label" default="Numero Comprobante" /></span>
					
						<span class="property-value" aria-labelledby="numeroComprobante-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="numeroComprobante"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="pagoBonoCalidad.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.nombreCobrador}">
				<li class="fieldcontain">
					<span id="nombreCobrador-label" class="property-label"><g:message code="pagoBonoCalidad.nombreCobrador.label" default="Nombre Cobrador" /></span>
					
						<span class="property-value" aria-labelledby="nombreCobrador-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="nombreCobrador"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.fechaDePago}">
				<li class="fieldcontain">
					<span id="fechaDePago-label" class="property-label"><g:message code="pagoBonoCalidad.fechaDePago.label" default="Fecha De Pago" /></span>
					
						<span class="property-value" aria-labelledby="fechaDePago-label"><g:formatDate date="${pagoBonoCalidadInstance?.fechaDePago}" /></span>
					
				</li>
				</g:if>

            <div>
                <h1 style="font-weight: bold">Parametros de Filtrado</h1>
            </div>

                <g:if test="${pagoBonoCalidadInstance?.tipoSeleccion}">
                    <li class="fieldcontain">
                        <span id="tipoSeleccion-label" class="property-label"><g:message code="pagoBonoCalidad.tipoSeleccion.label" default="Tipo Seleccion" /></span>

                        <span class="property-value" aria-labelledby="tipoSeleccion-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="tipoSeleccion"/></span>

                    </li>
                </g:if>

                <g:if test="${pagoBonoCalidadInstance?.tipoSeleccion.equals("INDIVIDUAL")}">
                    <li class="fieldcontain">
                        <span id="cliente-label" class="property-label"><g:message code="pagoBonoCalidad.cliente.label" default="Cliente" /></span>

                        <span class="property-value" aria-labelledby="cliente-label"><g:link controller="cliente" action="show" id="${pagoBonoCalidadInstance?.cliente?.id}">${pagoBonoCalidadInstance?.cliente?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${pagoBonoCalidadInstance?.empresa}">
                    <li class="fieldcontain">
                        <span id="empresa-label" class="property-label"><g:message code="pagoBonoCalidad.empresa.label" default="Empresa" /></span>

                        <span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${pagoBonoCalidadInstance?.empresa?.id}">${pagoBonoCalidadInstance?.empresa?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${pagoBonoCalidadInstance?.tipoSeleccion.equals("CUADRILLA")}">
                    <li class="fieldcontain">
                        <span id="cuadrilla-label" class="property-label"><g:message code="pagoBonoCalidad.cuadrilla.label" default="Cuadrilla" /></span>

                        <span class="property-value" aria-labelledby="cuadrilla-label"><g:link controller="cuadrilla" action="show" id="${pagoBonoCalidadInstance?.cuadrilla?.id}">${pagoBonoCalidadInstance?.cuadrilla?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.fechaInicial}">
				<li class="fieldcontain">
					<span id="fechaInicial-label" class="property-label"><g:message code="pagoBonoCalidad.fechaInicial.label" default="Fecha Inicial" /></span>
					
						<span class="property-value" aria-labelledby="fechaInicial-label"><g:formatDate date="${pagoBonoCalidadInstance?.fechaInicial}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.fechaFinal}">
				<li class="fieldcontain">
					<span id="fechaFinal-label" class="property-label"><g:message code="pagoBonoCalidad.fechaFinal.label" default="Fecha Final" /></span>
					
						<span class="property-value" aria-labelledby="fechaFinal-label"><g:formatDate date="${pagoBonoCalidadInstance?.fechaFinal}" /></span>
					
				</li>
				</g:if>
			
				%{--<g:if test="${pagoBonoCalidadInstance?.acumulacionPorMes}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="acumulacionPorMes-label" class="property-label"><g:message code="pagoBonoCalidad.acumulacionPorMes.label" default="Acumulacion Por Mes" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="acumulacionPorMes-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="acumulacionPorMes"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%

                <div style="width: 350px; margin-left: auto; margin-right: auto;">
                    <table id="acumulacionPorMesTabla"></table>
                </div>
                <g:hiddenField name="acumulacionPorMes" value="${pagoBonoCalidadInstance?.acumulacionPorMes}" />

            <div>
                <h1 style="font-weight: bold">Totales</h1>
            </div>
			
				<g:if test="${pagoBonoCalidadInstance?.numeroMesesPagables}">
				<li class="fieldcontain">
					<span id="numeroMesesPagables-label" class="property-label"><g:message code="pagoBonoCalidad.numeroMesesPagables.label" default="Numero Meses Pagables" /></span>
					
						<span class="property-value" aria-labelledby="numeroMesesPagables-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="numeroMesesPagables"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.numeroMesesAcumulados}">
				<li class="fieldcontain">
					<span id="numeroMesesAcumulados-label" class="property-label"><g:message code="pagoBonoCalidad.numeroMesesAcumulados.label" default="Numero Meses Acumulados" /></span>
					
						<span class="property-value" aria-labelledby="numeroMesesAcumulados-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="numeroMesesAcumulados"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.leyMinimaPlata}">
				<li class="fieldcontain">
					<span id="leyMinimaPlata-label" class="property-label"><g:message code="pagoBonoCalidad.leyMinimaPlata.label" default="Ley Minima Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaPlata-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="leyMinimaPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.totalKilosNetosSecos}">
				<li class="fieldcontain">
					<span id="totalKilosNetosSecos-label" class="property-label"><g:message code="pagoBonoCalidad.totalKilosNetosSecos.label" default="Total Kilos Netos Secos" /></span>
					
						<span class="property-value" aria-labelledby="totalKilosNetosSecos-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="totalKilosNetosSecos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.tipoDeCambio}">
				<li class="fieldcontain">
					<span id="tipoDeCambio-label" class="property-label"><g:message code="pagoBonoCalidad.tipoDeCambio.label" default="Tipo De Cambio" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeCambio-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="tipoDeCambio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.bonoPorTonelada}">
				<li class="fieldcontain">
					<span id="bonoPorTonelada-label" class="property-label"><g:message code="pagoBonoCalidad.bonoPorTonelada.label" default="Bono Por Tonelada" /></span>
					
						<span class="property-value" aria-labelledby="bonoPorTonelada-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="bonoPorTonelada"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.totalPagable}">
				<li class="fieldcontain">
					<span id="totalPagable-label" class="property-label"><g:message code="pagoBonoCalidad.totalPagable.label" default="Total Pagable" /></span>
					
						<span class="property-value" aria-labelledby="totalPagable-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="totalPagable"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.totalPagableLiteral}">
				<li class="fieldcontain">
					<span id="totalPagableLiteral-label" class="property-label"><g:message code="pagoBonoCalidad.totalPagableLiteral.label" default="Total Pagable Literal" /></span>
					
						<span class="property-value" aria-labelledby="totalPagableLiteral-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="totalPagableLiteral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoBonoCalidadInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="pagoBonoCalidad.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${pagoBonoCalidadInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>

			</ol>

        <fieldset class="buttons">
            <div style="float: left">
                <g:form>
                    <g:hiddenField name="id" value="${pagoBonoCalidadInstance?.id}" />
                    <g:link class="edit" action="edit" id="${pagoBonoCalidadInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </g:form>
            </div>
            <div style="float: right">
                <table>
                    <tr>
                        <td>
                            <g:jasperReport controller="pagoBonoCalidad" action="createReport" jasper="comprobante_pago_bono_calidad" format="PDF" _format="PDF" name="ORDEN_PAGO_BONO_PRODUCCION_${pagoBonoCalidadInstance.numeroComprobante}">
                                <input type="hidden" name="pagoBonoId" value="${pagoBonoCalidadInstance.id}" />
                            </g:jasperReport>
                        </td>
                        <td>
                            <g:jasperReport controller="pagoBonoCalidad" action="createReport" jasper="detalle_comprobante_pago_bono_calidad" format="PDF" _format="PDF" name="DETALLE_ORDEN_PAGO_BONO_PRODUCCION_${pagoBonoCalidadInstance.numeroComprobante}">
                                <input type="hidden" name="pagoBonoId" value="${pagoBonoCalidadInstance.id}" />
                            </g:jasperReport>
                        </td>
                    </tr>
                </table>
            </div>
        </fieldset>
		</div>
	</body>
</html>
