<%@ page import="org.socymet.recepcion.RecepcionDeOro" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName" value="${message(code: 'recepcionDeOro.label', default: 'RecepcionDeOro')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-recepcionDeOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
	<ul>
		<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
		<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
		<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
	</ul>
</div>
<div id="show-recepcionDeOro" class="content scaffold-show" role="main">
	<h1><g:message code="default.show.label" args="[entityName]" /></h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">${flash.message}</div>
	</g:if>
	<ol class="property-list recepcionDeOro">

		<g:if test="${recepcionDeOroInstance?.loteOro}">
			<li class="fieldcontain">
				<span id="loteOro-label" class="property-label"><g:message code="recepcionDeOro.loteOro.label" default="Lote Oro" /></span>

				<span class="property-value" aria-labelledby="loteOro-label" style="font-weight: bold; color: #b81900; font-size: 18px">${recepcionDeOroInstance.toString()}</span>

			</li>
		</g:if>

			<h1 style="font-weight: bold">Información General</h1>

		<g:if test="${recepcionDeOroInstance?.deposito}">
			<li class="fieldcontain">
				<span id="deposito-label" class="property-label"><g:message code="recepcionDeOro.deposito.label" default="Deposito" /></span>

				<span class="property-value" aria-labelledby="deposito-label">${recepcionDeOroInstance.deposito.toString()}</span>

			</li>
		</g:if>

	%{--<g:if test="${recepcionDeOroInstance?.numeroRecepcion}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="numeroRecepcion-label" class="property-label"><g:message code="recepcionDeOro.numeroRecepcion.label" default="Numero Recepcion" /></span>--}%

	%{--<span class="property-value" aria-labelledby="numeroRecepcion-label">${recepcionDeOroInstance.numeroRecepcion.toString()}</span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

	%{--<g:if test="${recepcionDeOroInstance?.codigoDepositoOro}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="codigoDepositoOro-label" class="property-label"><g:message code="recepcionDeOro.codigoDepositoOro.label" default="Codigo Deposito Oro" /></span>--}%

	%{--<span class="property-value" aria-labelledby="codigoDepositoOro-label" style="font-weight: bold; color: #255b17; font-size: 18px">${recepcionDeOroInstance.codigoDepositoOro}</span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

		<g:if test="${recepcionDeOroInstance?.fechaDeRecepcion}">
			<li class="fieldcontain">
				<span id="fechaDeRecepcion-label" class="property-label"><g:message code="recepcionDeOro.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

				<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:formatDate date="${recepcionDeOroInstance?.fechaDeRecepcion}" format="dd/MM/yyyy"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeOroInstance?.cliente}">
			<li class="fieldcontain">
				<span id="cliente-label" class="property-label"><g:message code="recepcionDeOro.cliente.label" default="Cliente" /></span>

				<span class="property-value" aria-labelledby="cliente-label"><g:link controller="cliente" action="show" id="${recepcionDeOroInstance?.cliente?.id}">${recepcionDeOroInstance?.cliente?.encodeAsHTML()}</g:link></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeOroInstance?.empresa}">
			<li class="fieldcontain">
				<span id="empresa-label" class="property-label"><g:message code="recepcionDeOro.empresa.label" default="Empresa" /></span>

				<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${recepcionDeOroInstance?.empresa?.id}">${recepcionDeOroInstance?.empresa?.encodeAsHTML()}</g:link></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeOroInstance?.chofer}">
			<li class="fieldcontain">
				<span id="chofer-label" class="property-label"><g:message code="recepcionDeOro.chofer.label" default="Chofer" /></span>

				<span class="property-value" aria-labelledby="chofer-label"><g:link controller="chofer" action="show" id="${recepcionDeOroInstance?.chofer?.id}">${recepcionDeOroInstance?.chofer?.encodeAsHTML()}</g:link></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeOroInstance?.automovil}">
			<li class="fieldcontain">
				<span id="automovil-label" class="property-label"><g:message code="recepcionDeOro.automovil.label" default="Automovil" /></span>

				<span class="property-value" aria-labelledby="automovil-label"><g:link controller="automovil" action="show" id="${recepcionDeOroInstance?.automovil?.id}">${recepcionDeOroInstance?.automovil?.encodeAsHTML()}</g:link></span>

			</li>
		</g:if>

		%{--<g:if test="${recepcionDeOroInstance?.numeroDeDocumento}">--}%
			%{--<li class="fieldcontain">--}%
				%{--<span id="numeroDeDocumento-label" class="property-label"><g:message code="recepcionDeOro.numeroDeDocumento.label" default="Numero De Documento" /></span>--}%

				%{--<span class="property-value" aria-labelledby="numeroDeDocumento-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="numeroDeDocumento"/></span>--}%

			%{--</li>--}%
		%{--</g:if>--}%

		<h1 style="font-weight: bold">Información del Producto</h1>

	%{--<g:if test="${recepcionDeOroInstance?.condicionDeEntrega}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="condicionDeEntrega-label" class="property-label"><g:message code="recepcionDeOro.condicionDeEntrega.label" default="Condicion De Entrega" /></span>--}%

	%{--<span class="property-value" aria-labelledby="condicionDeEntrega-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="condicionDeEntrega"/></span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

	%{--<g:if test="${recepcionDeOroInstance?.tipoDeMineral}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="tipoDeMineral-label" class="property-label"><g:message code="recepcionDeOro.tipoDeMineral.label" default="Tipo De Mineral" /></span>--}%

	%{--<span class="property-value" aria-labelledby="tipoDeMineral-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="tipoDeMineral"/></span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

	%{--<g:if test="${recepcionDeOroInstance?.naturalezaMineral}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="naturalezaMineral-label" class="property-label"><g:message code="recepcionDeOro.naturalezaMineral.label" default="Naturaleza Mineral" /></span>--}%

	%{--<span class="property-value" aria-labelledby="naturalezaMineral-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="naturalezaMineral"/></span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

		%{--<g:if test="${recepcionDeOroInstance?.tipoDeMaterial}">--}%
			%{--<li class="fieldcontain">--}%
				%{--<span id="tipoDeMaterial-label" class="property-label"><g:message code="recepcionDeOro.tipoDeMaterial.label" default="Tipo De Material" /></span>--}%

				%{--<span class="property-value" aria-labelledby="tipoDeMaterial-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="tipoDeMaterial"/></span>--}%

			%{--</li>--}%
		%{--</g:if>--}%

		<g:if test="${!recepcionDeOroInstance?.cantidadDeSacos.equals("0")}">
			<li class="fieldcontain">
				<span id="cantidadDeSacos-label" class="property-label"><g:message code="recepcionDeOro.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

				<span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="cantidadDeSacos"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeOroInstance?.pesoBruto}">
			<li class="fieldcontain">
				<span id="pesoBruto-label" class="property-label"><g:message code="recepcionDeOro.pesoBruto.label" default="Peso Bruto" /></span>

				<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="pesoBruto"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeOroInstance?.estadoAnticipo}">
			<li class="fieldcontain">
				<span id="estadoAnticipo-label" class="property-label"><g:message code="recepcionDeOro.estadoAnticipo.label" default="Estado Anticipo" /></span>

				<span class="property-value" aria-labelledby="estadoAnticipo-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="estadoAnticipo"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeOroInstance?.costoDeTransporte}">
			<li class="fieldcontain">
				<span id="costoDeTransporte-label" class="property-label"><g:message code="recepcionDeOro.costoDeTransporte.label" default="Costo De Transporte" /></span>

				<span class="property-value" aria-labelledby="costoDeTransporte-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="costoDeTransporte"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeOroInstance?.transportePagado}">
			<li class="fieldcontain">
				<span id="transportePagado-label" class="property-label"><g:message code="recepcionDeOro.transportePagado.label" default="Transporte Pagado?" /></span>

				<span class="property-value" aria-labelledby="transportePagado-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="transportePagado"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeOroInstance?.anticipoAutorizado}">
			<li class="fieldcontain">
				<span id="anticipoAutorizado-label" class="property-label"><g:message code="recepcionDeOro.anticipoAutorizado.label" default="Anticipo Autorizado" /></span>

				<span class="property-value" aria-labelledby="anticipoAutorizado-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="anticipoAutorizado"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeOroInstance?.estadoDelLote}">
			<li class="fieldcontain">
				<span id="estadoDelLote-label" class="property-label"><g:message code="recepcionDeOro.estadoDelLote.label" default="Estado Del Lote" /></span>

				<span class="property-value" aria-labelledby="estadoDelLote-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="estadoDelLote"/></span>

			</li>
		</g:if>

		<h1 style="font-weight: bold">Cotizaciones</h1>

		<g:if test="${recepcionDeOroInstance?.cotizacionDiariaDeMinerales}">
			<li class="fieldcontain">
				<span id="cotizacionDiariaDeMinerales-label" class="property-label"><g:message code="recepcionDeOro.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" /></span>

				%{--<span class="property-value" aria-labelledby="cotizacionDiariaDeMinerales-label"><g:link controller="cotizacionDiariaDeMinerales" action="show" id="${recepcionDeOroInstance?.cotizacionDiariaDeMinerales?.id}">${recepcionDeOroInstance?.cotizacionDiariaDeMinerales?.encodeAsHTML()}</g:link></span>--}%
				<span class="property-value" aria-labelledby="cotizacionDiariaDeMinerales-label">${recepcionDeOroInstance?.cotizacionDiariaDeMinerales?.encodeAsHTML()}</span>

			</li>
		</g:if>

		<g:if test="${recepcionDeOroInstance?.cotizacionQuincenalDeMinerales}">
			<li class="fieldcontain">
				<span id="cotizacionQuincenalDeMinerales-label" class="property-label"><g:message code="recepcionDeOro.cotizacionQuincenalDeMinerales.label" default="Cotizacion Quincenal De Minerales" /></span>

				<span class="property-value" aria-labelledby="cotizacionQuincenalDeMinerales-label">${recepcionDeOroInstance?.cotizacionQuincenalDeMinerales?.encodeAsHTML()}</span>

			</li>
		</g:if>

		<g:if test="${recepcionDeOroInstance?.alicuota}">
			<li class="fieldcontain">
				<span id="alicuota-label" class="property-label"><g:message code="recepcionDeOro.alicuota.label" default="Alicuota" /></span>

				<span class="property-value" aria-labelledby="alicuota-label">${recepcionDeOroInstance?.alicuota?.encodeAsHTML()}</span>

			</li>
		</g:if>

	%{--<g:if test="${recepcionDeOroInstance?.observaciones}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="observaciones-label" class="property-label"><g:message code="recepcionDeOro.observaciones.label" default="Observaciones" /></span>--}%

	%{--<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${recepcionDeOroInstance}" field="observaciones"/></span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

	</ol>

	<fieldset class="buttons">
		<div style="float: left">
			<g:form>
				<g:hiddenField name="id" value="${recepcionDeOroInstance?.id}" />
				<sec:ifAnyGranted roles="ROLE_ADMIN">
					<g:if test="${recepcionDeOroInstance?.estadoDelLote.equals("NO LIQUIDADO")}">
						<g:link class="edit" action="edit" id="${recepcionDeOroInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</g:if>
				</sec:ifAnyGranted>
			</g:form>
		</div>
		<div style="float: right">
			%{--<g:jasperReport controller="recepcionDeOro" action="crearReporte" jasper="recepcion_complejo" format="PDF" name="Imprimir Reporte">--}%
				%{--<input type="hidden" name="COMPLEJO_CONDICION" value="${recepcionDeOroInstance.id}" />--}%
			%{--</g:jasperReport>--}%
		</div>
	</fieldset>


</div>
</body>
</html>
