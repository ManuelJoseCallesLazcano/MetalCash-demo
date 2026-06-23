
<%@ page import="org.socymet.recepcion.RecepcionDeAntimonio" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'recepcionDeAntimonio.label', default: 'RecepcionDeAntimonio')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-recepcionDeAntimonio" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-recepcionDeAntimonio" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list recepcionDeAntimonio">
			
				<g:if test="${recepcionDeAntimonioInstance?.loteAntimonio}">
				<li class="fieldcontain">
					<span id="loteAntimonio-label" class="property-label"><g:message code="recepcionDeAntimonio.loteAntimonio.label" default="Lote Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="loteAntimonio-label">${recepcionDeAntimonioInstance.toString()}</span>
				</li>
				</g:if>
			
				<g:if test="${recepcionDeAntimonioInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="recepcionDeAntimonio.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:formatDate date="${recepcionDeAntimonioInstance?.fechaDeRecepcion}" format="dd/MM/yyyy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionDeAntimonioInstance?.cliente}">
				<li class="fieldcontain">
					<span id="cliente-label" class="property-label"><g:message code="recepcionDeAntimonio.cliente.label" default="Cliente" /></span>
					
						<span class="property-value" aria-labelledby="cliente-label"><g:link controller="cliente" action="show" id="${recepcionDeAntimonioInstance?.cliente?.id}">${recepcionDeAntimonioInstance?.cliente?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionDeAntimonioInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="recepcionDeAntimonio.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${recepcionDeAntimonioInstance?.empresa?.id}">${recepcionDeAntimonioInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionDeAntimonioInstance?.chofer}">
				<li class="fieldcontain">
					<span id="chofer-label" class="property-label"><g:message code="recepcionDeAntimonio.chofer.label" default="Chofer" /></span>
					
						<span class="property-value" aria-labelledby="chofer-label"><g:link controller="chofer" action="show" id="${recepcionDeAntimonioInstance?.chofer?.id}">${recepcionDeAntimonioInstance?.chofer?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionDeAntimonioInstance?.automovil}">
				<li class="fieldcontain">
					<span id="automovil-label" class="property-label"><g:message code="recepcionDeAntimonio.automovil.label" default="Automovil" /></span>
					
						<span class="property-value" aria-labelledby="automovil-label"><g:link controller="automovil" action="show" id="${recepcionDeAntimonioInstance?.automovil?.id}">${recepcionDeAntimonioInstance?.automovil?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionDeAntimonioInstance?.cantidadDeSacos}">
				<li class="fieldcontain">
					<span id="cantidadDeSacos-label" class="property-label"><g:message code="recepcionDeAntimonio.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>
					
						<span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="cantidadDeSacos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionDeAntimonioInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="recepcionDeAntimonio.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionDeAntimonioInstance?.pesoTara}">
				<li class="fieldcontain">
					<span id="pesoTara-label" class="property-label"><g:message code="recepcionDeAntimonio.pesoTara.label" default="Peso Tara" /></span>
					
						<span class="property-value" aria-labelledby="pesoTara-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="pesoTara"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${recepcionDeAntimonioInstance?.costoDeTransporte}">
				<li class="fieldcontain">
					<span id="costoDeTransporte-label" class="property-label"><g:message code="recepcionDeAntimonio.costoDeTransporte.label" default="Costo De Transporte" /></span>
					
						<span class="property-value" aria-labelledby="costoDeTransporte-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="costoDeTransporte"/></span>
					
				</li>
				</g:if>

                <g:if test="${recepcionDeAntimonioInstance?.transportePagado}">
                    <li class="fieldcontain">
                        <span id="transportePagado-label" class="property-label"><g:message code="recepcionDeAntimonio.transportePagado.label" default="Transporte Pagado?" /></span>

                        <span class="property-value" aria-labelledby="transportePagado-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="transportePagado"/></span>

                    </li>
                </g:if>
			
				<g:if test="${recepcionDeAntimonioInstance?.estadoDelLote}">
				<li class="fieldcontain">
					<span id="estadoDelLote-label" class="property-label"><g:message code="recepcionDeAntimonio.estadoDelLote.label" default="Estado Del Lote" /></span>
					
						<span class="property-value" aria-labelledby="estadoDelLote-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="estadoDelLote"/></span>
					
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
                    <g:if test="${recepcionDeAntimonioInstance?.detalleLaboratorio1}">
                        <tr>
                            <td>
                                <span class="property-value" aria-labelledby="detalleLaboratorio1-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="detalleLaboratorio1"/></span>
                            </td>
                            <td>
                                <span class="property-value" aria-labelledby="costoLaboratorio1-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="costoLaboratorio1"/></span>
                            </td>
                        </tr>
                    </g:if>
                    <g:if test="${recepcionDeAntimonioInstance?.detalleLaboratorio2}">
                        <tr>
                            <td>
                                <span class="property-value" aria-labelledby="detalleLaboratorio2-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="detalleLaboratorio2"/></span>
                            </td>
                            <td>
                                <span class="property-value" aria-labelledby="costoLaboratorio2-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="costoLaboratorio2"/></span>
                            </td>
                        </tr>
                    </g:if>
                    <g:if test="${recepcionDeAntimonioInstance?.detalleLaboratorio3}">
                        <tr>
                            <td>
                                <span class="property-value" aria-labelledby="detalleLaboratorio3-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="detalleLaboratorio3"/></span>
                            </td>
                            <td>
                                <span class="property-value" aria-labelledby="costoLaboratorio3-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="costoLaboratorio3"/></span>
                            </td>
                        </tr>
                    </g:if>
                    <g:if test="${recepcionDeAntimonioInstance?.detalleLaboratorio4}">
                        <tr>
                            <td>
                                <span class="property-value" aria-labelledby="detalleLaboratorio4-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="detalleLaboratorio4"/></span>
                            </td>
                            <td>
                                <span class="property-value" aria-labelledby="costoLaboratorio4-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="costoLaboratorio4"/></span>
                            </td>
                        </tr>
                    </g:if>
                    <tr>
                        <td class="fieldcontain ${hasErrors(bean: liquidacionDeAntimonioInstance, field: 'detalleLaboratorio4', 'error')}">
                            <label for="totalCostoLaboratorio" style="width: 90%">
                                <g:message code="liquidacionDeAntimonio.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
                            </label>
                        </td>
                        <td>
                            <span class="property-value" aria-labelledby="totalCostoLaboratorio-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="totalCostoLaboratorio"/></span>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <g:if test="${recepcionDeAntimonioInstance?.observaciones}">
                    <li class="fieldcontain">
                        <span id="observaciones-label" class="property-label"><g:message code="recepcionDeAntimonio.observaciones.label" default="Observaciones" /></span>

                        <span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${recepcionDeAntimonioInstance}" field="observaciones"/></span>

                    </li>
                </g:if>

            </ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${recepcionDeAntimonioInstance?.id}" />
					<g:link class="edit" action="edit" id="${recepcionDeAntimonioInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
