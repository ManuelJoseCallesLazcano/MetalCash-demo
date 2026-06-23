<%@ page import="org.socymet.recepcion.RecepcionDeEstano" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName" value="${message(code: 'recepcionDeEstano.label', default: 'RecepcionDeEstano')}" />
	<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<a href="#show-recepcionDeEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
	<ul>
		<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
		<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
		<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
	</ul>
</div>
<div id="show-recepcionDeEstano" class="content scaffold-show" role="main">
	<h1><g:message code="default.show.label" args="[entityName]" /></h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">${flash.message}</div>
	</g:if>
	<ol class="property-list recepcionDeEstano">

		<g:if test="${recepcionDeEstanoInstance?.loteEstano}">
			<li class="fieldcontain">
				<span id="loteEstano-label" class="property-label"><g:message code="recepcionDeEstano.loteEstano.label" default="Lote Estano" /></span>

				<span class="property-value" aria-labelledby="loteEstano-label" style="font-weight: bold; color: #b81900; font-size: 18px">${recepcionDeEstanoInstance.toString()}</span>

			</li>
		</g:if>

		<h1 style="font-weight: bold">Información General</h1>

		<g:if test="${recepcionDeEstanoInstance?.deposito}">
			<li class="fieldcontain">
				<span id="deposito-label" class="property-label"><g:message code="recepcionDeEstano.deposito.label" default="Deposito" /></span>

				<span class="property-value" aria-labelledby="deposito-label">${recepcionDeEstanoInstance.deposito.toString()}</span>

			</li>
		</g:if>

	%{--<g:if test="${recepcionDeEstanoInstance?.numeroRecepcion}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="numeroRecepcion-label" class="property-label"><g:message code="recepcionDeEstano.numeroRecepcion.label" default="Numero Recepcion" /></span>--}%

	%{--<span class="property-value" aria-labelledby="numeroRecepcion-label">${recepcionDeEstanoInstance.numeroRecepcion.toString()}</span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

	%{--<g:if test="${recepcionDeEstanoInstance?.codigoDepositoEstano}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="codigoDepositoEstano-label" class="property-label"><g:message code="recepcionDeEstano.codigoDepositoEstano.label" default="Codigo Deposito Estano" /></span>--}%

	%{--<span class="property-value" aria-labelledby="codigoDepositoEstano-label" style="font-weight: bold; color: #255b17; font-size: 18px">${recepcionDeEstanoInstance.codigoDepositoEstano}</span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

		<g:if test="${recepcionDeEstanoInstance?.fechaDeRecepcion}">
			<li class="fieldcontain">
				<span id="fechaDeRecepcion-label" class="property-label"><g:message code="recepcionDeEstano.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>

				<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:formatDate date="${recepcionDeEstanoInstance?.fechaDeRecepcion}" format="dd/MM/yyyy"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeEstanoInstance?.cliente}">
			<li class="fieldcontain">
				<span id="cliente-label" class="property-label"><g:message code="recepcionDeEstano.cliente.label" default="Cliente" /></span>

				<span class="property-value" aria-labelledby="cliente-label"><g:link controller="cliente" action="show" id="${recepcionDeEstanoInstance?.cliente?.id}">${recepcionDeEstanoInstance?.cliente?.encodeAsHTML()}</g:link></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeEstanoInstance?.empresa}">
			<li class="fieldcontain">
				<span id="empresa-label" class="property-label"><g:message code="recepcionDeEstano.empresa.label" default="Empresa" /></span>

				<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${recepcionDeEstanoInstance?.empresa?.id}">${recepcionDeEstanoInstance?.empresa?.encodeAsHTML()}</g:link></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeEstanoInstance?.chofer}">
			<li class="fieldcontain">
				<span id="chofer-label" class="property-label"><g:message code="recepcionDeEstano.chofer.label" default="Chofer" /></span>

				<span class="property-value" aria-labelledby="chofer-label"><g:link controller="chofer" action="show" id="${recepcionDeEstanoInstance?.chofer?.id}">${recepcionDeEstanoInstance?.chofer?.encodeAsHTML()}</g:link></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeEstanoInstance?.automovil}">
			<li class="fieldcontain">
				<span id="automovil-label" class="property-label"><g:message code="recepcionDeEstano.automovil.label" default="Automovil" /></span>

				<span class="property-value" aria-labelledby="automovil-label"><g:link controller="automovil" action="show" id="${recepcionDeEstanoInstance?.automovil?.id}">${recepcionDeEstanoInstance?.automovil?.encodeAsHTML()}</g:link></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeEstanoInstance?.numeroDeDocumento}">
			<li class="fieldcontain">
				<span id="numeroDeDocumento-label" class="property-label"><g:message code="recepcionDeEstano.numeroDeDocumento.label" default="Numero De Documento" /></span>

				<span class="property-value" aria-labelledby="numeroDeDocumento-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="numeroDeDocumento"/></span>

			</li>
		</g:if>

		<h1 style="font-weight: bold">Información del Producto</h1>

	%{--<g:if test="${recepcionDeEstanoInstance?.condicionDeEntrega}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="condicionDeEntrega-label" class="property-label"><g:message code="recepcionDeEstano.condicionDeEntrega.label" default="Condicion De Entrega" /></span>--}%

	%{--<span class="property-value" aria-labelledby="condicionDeEntrega-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="condicionDeEntrega"/></span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

	%{--<g:if test="${recepcionDeEstanoInstance?.tipoDeMineral}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="tipoDeMineral-label" class="property-label"><g:message code="recepcionDeEstano.tipoDeMineral.label" default="Tipo De Mineral" /></span>--}%

	%{--<span class="property-value" aria-labelledby="tipoDeMineral-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="tipoDeMineral"/></span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

	%{--<g:if test="${recepcionDeEstanoInstance?.naturalezaMineral}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="naturalezaMineral-label" class="property-label"><g:message code="recepcionDeEstano.naturalezaMineral.label" default="Naturaleza Mineral" /></span>--}%

	%{--<span class="property-value" aria-labelledby="naturalezaMineral-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="naturalezaMineral"/></span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

		<g:if test="${recepcionDeEstanoInstance?.tipoDeMaterial}">
			<li class="fieldcontain">
				<span id="tipoDeMaterial-label" class="property-label"><g:message code="recepcionDeEstano.tipoDeMaterial.label" default="Tipo De Material" /></span>

				<span class="property-value" aria-labelledby="tipoDeMaterial-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="tipoDeMaterial"/></span>

			</li>
		</g:if>

		<g:if test="${!recepcionDeEstanoInstance?.cantidadDeSacos.equals("0")}">
			<li class="fieldcontain">
				<span id="cantidadDeSacos-label" class="property-label"><g:message code="recepcionDeEstano.cantidadDeSacos.label" default="Cantidad De Sacos" /></span>

				<span class="property-value" aria-labelledby="cantidadDeSacos-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="cantidadDeSacos"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeEstanoInstance?.pesoBruto}">
			<li class="fieldcontain">
				<span id="pesoBruto-label" class="property-label"><g:message code="recepcionDeEstano.pesoBruto.label" default="Peso Bruto" /></span>

				<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="pesoBruto"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeEstanoInstance?.estadoAnticipo}">
			<li class="fieldcontain">
				<span id="estadoAnticipo-label" class="property-label"><g:message code="recepcionDeEstano.estadoAnticipo.label" default="Estado Anticipo" /></span>

				<span class="property-value" aria-labelledby="estadoAnticipo-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="estadoAnticipo"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeEstanoInstance?.costoDeTransporte}">
			<li class="fieldcontain">
				<span id="costoDeTransporte-label" class="property-label"><g:message code="recepcionDeEstano.costoDeTransporte.label" default="Costo De Transporte" /></span>

				<span class="property-value" aria-labelledby="costoDeTransporte-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="costoDeTransporte"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeEstanoInstance?.transportePagado}">
			<li class="fieldcontain">
				<span id="transportePagado-label" class="property-label"><g:message code="recepcionDeEstano.transportePagado.label" default="Transporte Pagado?" /></span>

				<span class="property-value" aria-labelledby="transportePagado-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="transportePagado"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeEstanoInstance?.anticipoAutorizado}">
			<li class="fieldcontain">
				<span id="anticipoAutorizado-label" class="property-label"><g:message code="recepcionDeEstano.anticipoAutorizado.label" default="Anticipo Autorizado" /></span>

				<span class="property-value" aria-labelledby="anticipoAutorizado-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="anticipoAutorizado"/></span>

			</li>
		</g:if>

		<g:if test="${recepcionDeEstanoInstance?.estadoDelLote}">
			<li class="fieldcontain">
				<span id="estadoDelLote-label" class="property-label"><g:message code="recepcionDeEstano.estadoDelLote.label" default="Estado Del Lote" /></span>

				<span class="property-value" aria-labelledby="estadoDelLote-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="estadoDelLote"/></span>

			</li>
		</g:if>

	%{--<g:if test="${recepcionDeEstanoInstance?.observaciones}">--}%
	%{--<li class="fieldcontain">--}%
	%{--<span id="observaciones-label" class="property-label"><g:message code="recepcionDeEstano.observaciones.label" default="Observaciones" /></span>--}%

	%{--<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${recepcionDeEstanoInstance}" field="observaciones"/></span>--}%

	%{--</li>--}%
	%{--</g:if>--}%

	</ol>

	<fieldset class="buttons">
		<div style="float: left">
			<g:form>
				<g:hiddenField name="id" value="${recepcionDeEstanoInstance?.id}" />
				<g:if test="${recepcionDeEstanoInstance?.estadoDelLote.equals("NO LIQUIDADO")}">
					<g:link class="edit" action="edit" id="${recepcionDeEstanoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</g:if>
			</g:form>
		</div>
		<div style="float: right">
			<g:jasperReport controller="recepcionDeEstano" action="crearReporte" jasper="recepcion_complejo" format="PDF" name="Imprimir Reporte">
				<input type="hidden" name="COMPLEJO_CONDICION" value="${recepcionDeEstanoInstance.id}" />
			</g:jasperReport>
		</div>
	</fieldset>


</div>
</body>
</html>
