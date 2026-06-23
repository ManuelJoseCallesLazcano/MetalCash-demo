
<%@ page import="org.socymet.calidad.ControlCalidadEstano" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName" value="${message(code: 'controlCalidadEstano.label', default: 'ControlCalidadEstano')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-controlCalidadEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
	<ul>
		<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
		<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
		<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
	</ul>
</div>
<div id="show-controlCalidadEstano" class="content scaffold-show" role="main">
	<h1><g:message code="default.show.label" args="[entityName]" /></h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">${flash.message}</div>
		%{--<div class="message" role="status">¿Ir al siguiente paso? <g:link class="create" controller="liquidacionDeEstano" action="create">LIQUIDACIÓN DEL LOTE</g:link></div>--}%
	</g:if>
	<ol class="property-list controlCalidadEstano">

		<g:if test="${controlCalidadEstanoInstance?.recepcionDeEstano}">
			<li class="fieldcontain">
				<span id="recepcionDeEstano-label" class="property-label"><g:message code="controlCalidadEstano.recepcionDeEstano.label" default="Recepcion De Estano" /></span>

				<span class="property-value" aria-labelledby="recepcionDeEstano-label"><g:link controller="recepcionDeEstano" action="show" id="${controlCalidadEstanoInstance?.recepcionDeEstano?.id}">${controlCalidadEstanoInstance?.recepcionDeEstano?.encodeAsHTML()}</g:link></span>

			</li>
		</g:if>

		<g:if test="${controlCalidadEstanoInstance?.nombreCliente}">
			<li class="fieldcontain">
				<span id="nombreCliente-label" class="property-label"><g:message code="controlCalidadEstano.nombreCliente.label" default="Nombre Cliente" /></span>

				%{--<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${controlCalidadEstanoInstance}" field="nombreCliente"/></span>--}%
				<span class="property-value" aria-labelledby="nombreEmpresa-label">${controlCalidadEstanoInstance?.recepcionDeEstano?.cliente.toString()}</span>
			</li>
		</g:if>

		<g:if test="${controlCalidadEstanoInstance?.nombreEmpresa}">
			<li class="fieldcontain">
				<span id="nombreEmpresa-label" class="property-label"><g:message code="controlCalidadEstano.nombreEmpresa.label" default="Nombre Empresa" /></span>

				%{--<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${controlCalidadEstanoInstance}" field="nombreEmpresa"/></span>--}%
				<span class="property-value" aria-labelledby="nombreEmpresa-label">${controlCalidadEstanoInstance?.recepcionDeEstano?.empresa.toString()}</span>
			</li>
		</g:if>

		<g:if test="${controlCalidadEstanoInstance?.fechaDeRecepcion}">
			<li class="fieldcontain">
				<span id="fechaDeRecepcion-label" class="property-label"><g:message code="controlCalidadEstano.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

				<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${controlCalidadEstanoInstance}" field="fechaDeRecepcion"/></span>

			</li>
		</g:if>

		<g:if test="${controlCalidadEstanoInstance?.cantidadDeSacos}">
			<li class="fieldcontain">
				<span id="cantidadDeSacos-label" class="property-label"><g:message code="controlCalidadEstano.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

				%{--<span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${controlCalidadEstanoInstance}" field="cantidadDeSacos"/></span>--}%
				<span class="property-value" aria-labelledby="cantidadDeSacos-label">${controlCalidadEstanoInstance?.recepcionDeEstano?.cantidadDeSacos}</span>
			</li>
		</g:if>

		<g:if test="${controlCalidadEstanoInstance?.pesoBruto}">
			<li class="fieldcontain">
				<span id="pesoBruto-label" class="property-label"><g:message code="controlCalidadEstano.pesoBruto.label" default="Peso Bruto" /></span>

				%{--<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${controlCalidadEstanoInstance}" field="pesoBruto"/></span>--}%
				<span class="property-value" aria-labelledby="pesoBruto-label">${controlCalidadEstanoInstance?.recepcionDeEstano?.pesoBruto}</span>
			</li>
		</g:if>

		<g:if test="${controlCalidadEstanoInstance?.estadoDelLote}">
			<li class="fieldcontain">
				<span id="estadoDelLote-label" class="property-label"><g:message code="controlCalidadEstano.estadoDelLote.label" default="Estado Del Lote" /></span>

				%{--<span class="property-value" aria-labelledby="estadoDelLote-label"><g:fieldValue bean="${controlCalidadEstanoInstance}" field="estadoDelLote"/></span>--}%
				<span class="property-value" aria-labelledby="estadoDelLote-label">${controlCalidadEstanoInstance?.recepcionDeEstano?.estadoDelLote}</span>
			</li>
		</g:if>

		<g:if test="${controlCalidadEstanoInstance?.nombreLaboratorio}">
			<li class="fieldcontain">
				<span id="nombreLaboratorio-label" class="property-label"><g:message code="controlCalidadEstano.nombreLaboratorio.label" default="Nombre Laboratorio" /></span>

				<span class="property-value" aria-labelledby="nombreLaboratorio-label"><g:fieldValue bean="${controlCalidadEstanoInstance}" field="nombreLaboratorio"/></span>

			</li>
		</g:if>

		<g:if test="${controlCalidadEstanoInstance?.numeroAnalisis}">
			<li class="fieldcontain">
				<span id="numeroAnalisis-label" class="property-label"><g:message code="controlCalidadEstano.numeroAnalisis.label" default="Numero Analisis" /></span>

				<span class="property-value" aria-labelledby="numeroAnalisis-label"><g:fieldValue bean="${controlCalidadEstanoInstance}" field="numeroAnalisis"/></span>

			</li>
		</g:if>

		<g:if test="${controlCalidadEstanoInstance?.fechaAnalisis}">
			<li class="fieldcontain">
				<span id="fechaAnalisis-label" class="property-label"><g:message code="controlCalidadEstano.fechaAnalisis.label" default="Fecha Analisis" /></span>

				<span class="property-value" aria-labelledby="fechaAnalisis-label"><g:formatDate date="${controlCalidadEstanoInstance?.fechaAnalisis}" /></span>

			</li>
		</g:if>

		<h1 style="font-weight: bold">Detalle de Leyes</h1>

		<table class="center" style="width: 70%;">
			<thead>
			<tr>
				<th style="width: 20%">ELEMENTO</th>
				<th style="width: 80%">LEY EMPRESA</th>
			</tr>
			</thead>
			<tbody>
			<tr style="display: none">
				<td class="fieldcontain required">
					<label for="porcentajeMermaPromexbol">
						<g:message code="controlCalidadEstano.porcentajeMermaPromexbol.label" default="Merma" />
					</label>
				</td>
				<td class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
					<g:fieldValue bean="${controlCalidadEstanoInstance}" field="porcentajeMermaPromexbol"/>
				</td>
			</tr>

			<tr>
				<td class="fieldcontain required">
					<label for="porcentajeEstanoFinal">
						<g:message code="controlCalidadEstano.porcentajeZincCliente.label" default="Estano" />
					</label>
				</td>

				<td class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'porcentajeEstanoPromexbol', 'error')} required">
					<g:fieldValue bean="${controlCalidadEstanoInstance}" field="porcentajeEstanoPromexbol"/>
				</td>
			</tr>

			<tr>
				<td class="fieldcontain required">
					<label for="porcentajeHumedadPromexbol">
						<g:message code="controlCalidadEstano.porcentajeHumedadPromexbol.label" default="Humedad" />
					</label>
				</td>
				<td class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
					<g:fieldValue bean="${controlCalidadEstanoInstance}" field="porcentajeHumedadPromexbol"/>
				</td>

			</tr>
			</tbody>
		</table>

		<g:if test="${controlCalidadEstanoInstance?.observaciones}">
			<li class="fieldcontain">
				<span id="observaciones-label" class="property-label"><g:message code="controlCalidadEstano.observaciones.label" default="Observaciones" /></span>

				<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${controlCalidadEstanoInstance}" field="observaciones"/></span>

			</li>
		</g:if>

	</ol>
	<g:form url="[resource:controlCalidadEstanoInstance, action:'delete']" method="DELETE">
		<fieldset class="buttons">
			<g:link class="edit" action="edit" resource="${controlCalidadEstanoInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
			<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
		</fieldset>
	</g:form>
</div>
</body>
</html>
