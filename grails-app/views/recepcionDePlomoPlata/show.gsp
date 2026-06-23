
<%@ page import="org.socymet.recepcion.RecepcionDePlomoPlata" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'recepcionDePlomoPlata.label', default: 'RecepcionDePlomoPlata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-recepcionDePlomoPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-recepcionDePlomoPlata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list recepcionDePlomoPlata">

                <g:if test="${recepcionDePlomoPlataInstance?.deposito}">
                    <li class="fieldcontain">
                        <span id="deposito-label" class="property-label"><g:message code="recepcionDePlomoPlata.deposito.label" default="Deposito" /></span>

                        <span class="property-value" aria-labelledby="deposito-label">${recepcionDePlomoPlataInstance.deposito.toString()}</span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlomoPlataInstance?.lotePlomoPlata}">
                    <li class="fieldcontain">
                        <span id="lotePlomoPlata-label" class="property-label"><g:message code="recepcionDePlomoPlata.lotePlomoPlata.label" default="Lote PlomoPlata" /></span>

                        <span class="property-value" aria-labelledby="lotePlomoPlata-label" style="font-weight: bold; color: #b81900; font-size: 18px">${recepcionDePlomoPlataInstance.toString()}</span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlomoPlataInstance?.fechaDeRecepcion}">
                    <li class="fieldcontain">
                        <span id="fechaDeRecepcion-label" class="property-label"><g:message code="recepcionDePlomoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

                        <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:formatDate date="${recepcionDePlomoPlataInstance?.fechaDeRecepcion}" format="dd/MM/yyyy"/></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlomoPlataInstance?.cliente}">
                    <li class="fieldcontain">
                        <span id="cliente-label" class="property-label"><g:message code="recepcionDePlomoPlata.cliente.label" default="Cliente" /></span>

                        <span class="property-value" aria-labelledby="cliente-label"><g:link controller="cliente" action="show" id="${recepcionDePlomoPlataInstance?.cliente?.id}">${recepcionDePlomoPlataInstance?.cliente?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlomoPlataInstance?.empresa}">
                    <li class="fieldcontain">
                        <span id="empresa-label" class="property-label"><g:message code="recepcionDePlomoPlata.empresa.label" default="Empresa" /></span>

                        <span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${recepcionDePlomoPlataInstance?.empresa?.id}">${recepcionDePlomoPlataInstance?.empresa?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlomoPlataInstance?.chofer}">
                    <li class="fieldcontain">
                        <span id="chofer-label" class="property-label"><g:message code="recepcionDePlomoPlata.chofer.label" default="Chofer" /></span>

                        <span class="property-value" aria-labelledby="chofer-label"><g:link controller="chofer" action="show" id="${recepcionDePlomoPlataInstance?.chofer?.id}">${recepcionDePlomoPlataInstance?.chofer?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlomoPlataInstance?.automovil}">
                    <li class="fieldcontain">
                        <span id="automovil-label" class="property-label"><g:message code="recepcionDePlomoPlata.automovil.label" default="Automovil" /></span>

                        <span class="property-value" aria-labelledby="automovil-label"><g:link controller="automovil" action="show" id="${recepcionDePlomoPlataInstance?.automovil?.id}">${recepcionDePlomoPlataInstance?.automovil?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlomoPlataInstance?.cantidadDeSacos}">
                    <li class="fieldcontain">
                        <span id="cantidadDeSacos-label" class="property-label"><g:message code="recepcionDePlomoPlata.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

                        <span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="cantidadDeSacos"/></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlomoPlataInstance?.pesoBruto}">
                    <li class="fieldcontain">
                        <span id="pesoBruto-label" class="property-label"><g:message code="recepcionDePlomoPlata.pesoBruto.label" default="Peso Bruto" /></span>

                        <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="pesoBruto"/></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlomoPlataInstance?.costoDeTransporte}">
                    <li class="fieldcontain">
                        <span id="costoDeTransporte-label" class="property-label"><g:message code="recepcionDePlomoPlata.costoDeTransporte.label" default="Costo De Transporte" /></span>

                        <span class="property-value" aria-labelledby="costoDeTransporte-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="costoDeTransporte"/></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlomoPlataInstance?.transportePagado}">
                    <li class="fieldcontain">
                        <span id="transportePagado-label" class="property-label"><g:message code="recepcionDePlomoPlata.transportePagado.label" default="Transporte Pagado?" /></span>

                        <span class="property-value" aria-labelledby="transportePagado-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="transportePagado"/></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlomoPlataInstance?.estadoDelLote}">
                    <li class="fieldcontain">
                        <span id="estadoDelLote-label" class="property-label"><g:message code="recepcionDePlomoPlata.estadoDelLote.label" default="Estado Del Lote" /></span>

                        <span class="property-value" aria-labelledby="estadoDelLote-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="estadoDelLote"/></span>

                    </li>
                </g:if>

                <h1 style="font-weight: bold">Detalle de Analisis Realizados</h1>

                <table class="center" border="0" style="width: 70%;">
                    <thead>
                    <tr>
                        <th style="text-align: center; width: 70%">DESCRIPCION DE ANALISIS</th>
                        <th style="text-align: center; width: 30%">COSTO</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:if test="${recepcionDePlomoPlataInstance?.detalleLaboratorio1}">
                        <tr>
                            <td>
                                <span class="property-value" aria-labelledby="detalleLaboratorio1-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="detalleLaboratorio1"/></span>
                            </td>
                            <td>
                                <span class="property-value" aria-labelledby="costoLaboratorio1-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="costoLaboratorio1"/></span>
                            </td>
                        </tr>
                    </g:if>
                    <g:if test="${recepcionDePlomoPlataInstance?.detalleLaboratorio2}">
                        <tr>
                            <td>
                                <span class="property-value" aria-labelledby="detalleLaboratorio2-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="detalleLaboratorio2"/></span>
                            </td>
                            <td>
                                <span class="property-value" aria-labelledby="costoLaboratorio2-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="costoLaboratorio2"/></span>
                            </td>
                        </tr>
                    </g:if>
                    <g:if test="${recepcionDePlomoPlataInstance?.detalleLaboratorio3}">
                        <tr>
                            <td>
                                <span class="property-value" aria-labelledby="detalleLaboratorio3-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="detalleLaboratorio3"/></span>
                            </td>
                            <td>
                                <span class="property-value" aria-labelledby="costoLaboratorio3-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="costoLaboratorio3"/></span>
                            </td>
                        </tr>
                    </g:if>
                    <g:if test="${recepcionDePlomoPlataInstance?.detalleLaboratorio4}">
                        <tr>
                            <td>
                                <span class="property-value" aria-labelledby="detalleLaboratorio4-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="detalleLaboratorio4"/></span>
                            </td>
                            <td>
                                <span class="property-value" aria-labelledby="costoLaboratorio4-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="costoLaboratorio4"/></span>
                            </td>
                        </tr>
                    </g:if>
                    <tr>
                        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlomoPlataInstance, field: 'detalleLaboratorio4', 'error')}">
                            <label for="totalCostoLaboratorio" style="width: 90%">
                                <g:message code="liquidacionDePlomoPlata.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
                            </label>
                        </td>
                        <td>
                            <span class="property-value" aria-labelledby="totalCostoLaboratorio-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="totalCostoLaboratorio"/></span>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <g:if test="${recepcionDePlomoPlataInstance?.observaciones}">
                    <li class="fieldcontain">
                        <span id="observaciones-label" class="property-label"><g:message code="recepcionDePlomoPlata.observaciones.label" default="Observaciones" /></span>

                        <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${recepcionDePlomoPlataInstance}" field="observaciones"/></span>

                    </li>
                </g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${recepcionDePlomoPlataInstance?.id}" />
					<g:link class="edit" action="edit" id="${recepcionDePlomoPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
