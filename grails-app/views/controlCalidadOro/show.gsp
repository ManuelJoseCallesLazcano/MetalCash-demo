//show
<%@ page import="org.socymet.calidad.ControlCalidadOro" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName" value="${message(code: 'controlCalidadOro.label', default: 'ControlCalidadOro')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-controlCalidadOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
	<ul>
		<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
		<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
		<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
	</ul>
</div>
<div id="show-controlCalidadOro" class="content scaffold-show" role="main">
	<h1><g:message code="default.show.label" args="[entityName]" /></h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">${flash.message}</div>
	</g:if>
	<ol class="property-list controlCalidadOro">

		<g:if test="${controlCalidadOroInstance?.recepcionDeOro}">
			<li class="fieldcontain">
				<span id="recepcionDeOro-label" class="property-label"><g:message code="controlCalidadOro.recepcionDeOro.label" default="Lote" /></span>

				<span class="property-value" aria-labelledby="recepcionDeOro-label"><g:link controller="recepcionDeOro" action="show" id="${controlCalidadOroInstance?.recepcionDeOro?.id}">${controlCalidadOroInstance?.recepcionDeOro?.encodeAsHTML()}</g:link></span>

			</li>
		</g:if>

		<g:if test="${controlCalidadOroInstance?.nombreCliente}">
			<li class="fieldcontain">
				<span id="nombreCliente-label" class="property-label"><g:message code="controlCalidadOro.nombreCliente.label" default="Nombre Cliente" /></span>

				<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${controlCalidadOroInstance}" field="nombreCliente"/></span>

			</li>
		</g:if>

		<g:if test="${controlCalidadOroInstance?.nombreEmpresa}">
			<li class="fieldcontain">
				<span id="nombreEmpresa-label" class="property-label"><g:message code="controlCalidadOro.nombreEmpresa.label" default="Nombre Empresa" /></span>

				<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${controlCalidadOroInstance}" field="nombreEmpresa"/></span>

			</li>
		</g:if>

		<g:if test="${controlCalidadOroInstance?.fechaDeRecepcion}">
			<li class="fieldcontain">
				<span id="fechaDeRecepcion-label" class="property-label"><g:message code="controlCalidadOro.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

				<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${controlCalidadOroInstance}" field="fechaDeRecepcion"/></span>

			</li>
		</g:if>

	%{--<g:if test="${controlCalidadOroInstance?.condicionDeEntrega}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="condicionDeEntrega-label" class="property-label"><g:message code="controlCalidadOro.condicionDeEntrega.label" default="Condicion De Entrega" /></span>--}%

	%{--<span class="property-value" aria-labelledby="condicionDeEntrega-label"><g:fieldValue bean="${controlCalidadOroInstance}" field="condicionDeEntrega"/></span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

		<g:if test="${controlCalidadOroInstance?.cantidadDeSacos}">
			<li class="fieldcontain">
				<span id="cantidadDeSacos-label" class="property-label"><g:message code="controlCalidadOro.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

				<span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${controlCalidadOroInstance}" field="cantidadDeSacos"/></span>

			</li>
		</g:if>

		<g:if test="${controlCalidadOroInstance?.pesoBruto}">
			<li class="fieldcontain">
				<span id="pesoBruto-label" class="property-label"><g:message code="controlCalidadOro.pesoBruto.label" default="Peso Bruto" /></span>

				<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${controlCalidadOroInstance}" field="pesoBruto"/></span>

			</li>
		</g:if>

		<g:if test="${controlCalidadOroInstance?.estadoDelLote}">
			<li class="fieldcontain">
				<span id="estadoDelLote-label" class="property-label"><g:message code="controlCalidadOro.estadoDelLote.label" default="Estado Del Lote" /></span>

				<span class="property-value" aria-labelledby="estadoDelLote-label"><g:fieldValue bean="${controlCalidadOroInstance}" field="estadoDelLote"/></span>

			</li>
		</g:if>

		<g:if test="${controlCalidadOroInstance?.nombreLaboratorio}">
			<li class="fieldcontain">
				<span id="nombreLaboratorio-label" class="property-label"><g:message code="controlCalidadOro.nombreLaboratorio.label" default="Nombre Laboratorio" /></span>

				<span class="property-value" aria-labelledby="nombreLaboratorio-label"><g:fieldValue bean="${controlCalidadOroInstance}" field="nombreLaboratorio"/></span>

			</li>
		</g:if>

		<g:if test="${controlCalidadOroInstance?.numeroAnalisis}">
			<li class="fieldcontain">
				<span id="numeroAnalisis-label" class="property-label"><g:message code="controlCalidadOro.numeroAnalisis.label" default="Numero Analisis" /></span>

				<span class="property-value" aria-labelledby="numeroAnalisis-label"><g:fieldValue bean="${controlCalidadOroInstance}" field="numeroAnalisis"/></span>

			</li>
		</g:if>

		<g:if test="${controlCalidadOroInstance?.fechaAnalisis}">
			<li class="fieldcontain">
				<span id="fechaAnalisis-label" class="property-label"><g:message code="controlCalidadOro.fechaAnalisis.label" default="Fecha Analisis" /></span>

				<span class="property-value" aria-labelledby="fechaAnalisis-label"><g:formatDate date="${controlCalidadOroInstance?.fechaAnalisis}" format="dd/MM/yyyy"/></span>

			</li>
		</g:if>

		<h1 style="font-weight: bold">Detalle de Leyes</h1>

		<table class="center" style="width: 70%;">
			<thead>
			<tr>
				<th style="width: 40%">ELEMENTO</th>
				<th style="width: 20%">LEY EMPRESA</th>
			</tr>
			</thead>
			<tbody>
			<tr style="display: none">
				<td class="fieldcontain required">
					<label for="porcentajeMermaPromexbol">
						<g:message code="controlCalidadOro.porcentajeMermaPromexbol.label" default="Merma" />
					</label>
				</td>
				<td class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
					<g:fieldValue bean="${controlCalidadOroInstance}" field="porcentajeMermaPromexbol"/>
				</td>
			</tr>
			<tr>
				<td class="fieldcontain required">
					<label for="porcentajeOroPromexbol">
						<g:message code="controlCalidadOro.porcentajeOroPromexbol.label" default="Oro" />
					</label>
				</td>
				<td class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'porcentajeOroPromexbol', 'error')} required">
					<g:fieldValue bean="${controlCalidadOroInstance}" field="porcentajeOroPromexbol"/>
				</td>
			</tr>
			<tr>
				<td class="fieldcontain required">
					<label for="porcentajeHumedadPromexbol">
						<g:message code="controlCalidadOro.porcentajeHumedadPromexbol.label" default="Humedad" />
					</label>
				</td>
				<td class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
					<g:fieldValue bean="${controlCalidadOroInstance}" field="porcentajeHumedadPromexbol"/>
				</td>
			</tr>

			</tbody>
		</table>

	%{--<g:if test="${controlCalidadOroInstance?.observaciones}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="observaciones-label" class="property-label"><g:message code="controlCalidadOro.observaciones.label" default="Observaciones" /></span>--}%

	%{--<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${controlCalidadOroInstance}" field="observaciones"/></span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

	</ol>
	<g:form>
		<fieldset class="buttons">
			<g:hiddenField name="id" value="${controlCalidadOroInstance?.id}" />
			<sec:ifAnyGranted roles="ROLE_ADMIN">
				<g:if test="${controlCalidadOroInstance?.recepcionDeOro?.estadoDelLote.equals("NO LIQUIDADO")}">
					<g:link class="edit" action="edit" id="${controlCalidadOroInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</g:if>
			</sec:ifAnyGranted>
		</fieldset>
	</g:form>
</div>
</body>
</html>
