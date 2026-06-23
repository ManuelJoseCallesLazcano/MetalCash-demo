
<%@ page import="org.socymet.recepcion.RecepcionDePlata" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'recepcionDePlata.label', default: 'RecepcionDePlata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-recepcionDePlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-recepcionDePlata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list recepcionDePlata">

                <g:if test="${recepcionDePlataInstance?.lotePlata}">
                    <li class="fieldcontain">
                        <span id="lotePlata-label" class="property-label"><g:message code="recepcionDePlata.lotePlata.label" default="Lote Plata" /></span>

                        <span class="property-value" aria-labelledby="lotePlata-label">${recepcionDePlataInstance.toString()}</span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlataInstance?.fechaDeRecepcion}">
                    <li class="fieldcontain">
                        <span id="fechaDeRecepcion-label" class="property-label"><g:message code="recepcionDePlata.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

                        <span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:formatDate date="${recepcionDePlataInstance?.fechaDeRecepcion}" format="dd/MM/yyyy"/></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlataInstance?.cliente}">
                    <li class="fieldcontain">
                        <span id="cliente-label" class="property-label"><g:message code="recepcionDePlata.cliente.label" default="Cliente" /></span>

                        <span class="property-value" aria-labelledby="cliente-label"><g:link controller="cliente" action="show" id="${recepcionDePlataInstance?.cliente?.id}">${recepcionDePlataInstance?.cliente?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlataInstance?.empresa}">
                    <li class="fieldcontain">
                        <span id="empresa-label" class="property-label"><g:message code="recepcionDePlata.empresa.label" default="Empresa" /></span>

                        <span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${recepcionDePlataInstance?.empresa?.id}">${recepcionDePlataInstance?.empresa?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlataInstance?.chofer}">
                    <li class="fieldcontain">
                        <span id="chofer-label" class="property-label"><g:message code="recepcionDePlata.chofer.label" default="Chofer" /></span>

                        <span class="property-value" aria-labelledby="chofer-label"><g:link controller="chofer" action="show" id="${recepcionDePlataInstance?.chofer?.id}">${recepcionDePlataInstance?.chofer?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlataInstance?.automovil}">
                    <li class="fieldcontain">
                        <span id="automovil-label" class="property-label"><g:message code="recepcionDePlata.automovil.label" default="Automovil" /></span>

                        <span class="property-value" aria-labelledby="automovil-label"><g:link controller="automovil" action="show" id="${recepcionDePlataInstance?.automovil?.id}">${recepcionDePlataInstance?.automovil?.encodeAsHTML()}</g:link></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlataInstance?.cantidadDeSacos}">
                    <li class="fieldcontain">
                        <span id="cantidadDeSacos-label" class="property-label"><g:message code="recepcionDePlata.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

                        <span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="cantidadDeSacos"/></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlataInstance?.pesoBruto}">
                    <li class="fieldcontain">
                        <span id="pesoBruto-label" class="property-label"><g:message code="recepcionDePlata.pesoBruto.label" default="Peso Bruto" /></span>

                        <span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="pesoBruto"/></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlataInstance?.pesoTara}">
                    <li class="fieldcontain">
                        <span id="pesoTara-label" class="property-label"><g:message code="recepcionDePlata.pesoTara.label" default="Peso Tara" /></span>

                        <span class="property-value" aria-labelledby="pesoTara-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="pesoTara"/></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlataInstance?.costoDeTransporte}">
                    <li class="fieldcontain">
                        <span id="costoDeTransporte-label" class="property-label"><g:message code="recepcionDePlata.costoDeTransporte.label" default="Costo De Transporte" /></span>

                        <span class="property-value" aria-labelledby="costoDeTransporte-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="costoDeTransporte"/></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlataInstance?.transportePagado}">
                    <li class="fieldcontain">
                        <span id="transportePagado-label" class="property-label"><g:message code="recepcionDePlata.transportePagado.label" default="Transporte Pagado?" /></span>

                        <span class="property-value" aria-labelledby="transportePagado-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="transportePagado"/></span>

                    </li>
                </g:if>

                <g:if test="${recepcionDePlataInstance?.estadoDelLote}">
                    <li class="fieldcontain">
                        <span id="estadoDelLote-label" class="property-label"><g:message code="recepcionDePlata.estadoDelLote.label" default="Estado Del Lote" /></span>

                        <span class="property-value" aria-labelledby="estadoDelLote-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="estadoDelLote"/></span>

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
                    <g:if test="${recepcionDePlataInstance?.detalleLaboratorio1}">
                        <tr>
                            <td>
                                <span class="property-value" aria-labelledby="detalleLaboratorio1-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="detalleLaboratorio1"/></span>
                            </td>
                            <td>
                                <span class="property-value" aria-labelledby="costoLaboratorio1-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="costoLaboratorio1"/></span>
                            </td>
                        </tr>
                    </g:if>
                    <g:if test="${recepcionDePlataInstance?.detalleLaboratorio2}">
                        <tr>
                            <td>
                                <span class="property-value" aria-labelledby="detalleLaboratorio2-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="detalleLaboratorio2"/></span>
                            </td>
                            <td>
                                <span class="property-value" aria-labelledby="costoLaboratorio2-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="costoLaboratorio2"/></span>
                            </td>
                        </tr>
                    </g:if>
                    <g:if test="${recepcionDePlataInstance?.detalleLaboratorio3}">
                        <tr>
                            <td>
                                <span class="property-value" aria-labelledby="detalleLaboratorio3-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="detalleLaboratorio3"/></span>
                            </td>
                            <td>
                                <span class="property-value" aria-labelledby="costoLaboratorio3-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="costoLaboratorio3"/></span>
                            </td>
                        </tr>
                    </g:if>
                    <g:if test="${recepcionDePlataInstance?.detalleLaboratorio4}">
                        <tr>
                            <td>
                                <span class="property-value" aria-labelledby="detalleLaboratorio4-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="detalleLaboratorio4"/></span>
                            </td>
                            <td>
                                <span class="property-value" aria-labelledby="costoLaboratorio4-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="costoLaboratorio4"/></span>
                            </td>
                        </tr>
                    </g:if>
                    <tr>
                        <td class="fieldcontain ${hasErrors(bean: liquidacionDePlataInstance, field: 'detalleLaboratorio4', 'error')}">
                            <label for="totalCostoLaboratorio" style="width: 90%">
                                <g:message code="liquidacionDePlata.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
                            </label>
                        </td>
                        <td>
                            <span class="property-value" aria-labelledby="totalCostoLaboratorio-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="totalCostoLaboratorio"/></span>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <g:if test="${recepcionDePlataInstance?.observaciones}">
                    <li class="fieldcontain">
                        <span id="observaciones-label" class="property-label"><g:message code="recepcionDePlata.observaciones.label" default="Observaciones" /></span>

                        <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${recepcionDePlataInstance}" field="observaciones"/></span>

                    </li>
                </g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${recepcionDePlataInstance?.id}" />
					<g:link class="edit" action="edit" id="${recepcionDePlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
