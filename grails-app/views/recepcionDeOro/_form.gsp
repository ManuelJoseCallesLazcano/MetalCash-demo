<%@ page import="org.socymet.recepcion.RecepcionDeOro" %>

<g:if test="${recepcionDeOroInstance?.loteOro}">
	<div class="fieldcontain">
		<span id="loteOro-label" class="property-label"><g:message code="recepcionDeOro.loteOro.label" default="Lote Oro" /></span>

		<span class="property-value" aria-labelledby="loteOro-label" style="font-weight: bold; color: #b81900; font-size: 18px">${recepcionDeOroInstance.toString()}</span>

	</div>
</g:if>

<h1 style="font-weight: bold">Información General</h1>

<div id="_deposito" class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'deposito', 'error')} required">
	<label for="deposito">
		<g:message code="recepcionDeOro.deposito.label" default="Deposito" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${recepcionDeOroInstance?.deposito?.id}" class="many-to-one"/>

	%{--<label for="fechaDeRecepcion" style="width: 22%">--}%
	%{--<g:message code="recepcionDeOro.fechaDeRecepcion.label" default="Fecha De Recepcion" />--}%
	%{--<span class="required-indicator">*</span>--}%
	%{--</label>--}%
	%{--<g:datePicker name="fechaDeRecepcion" precision="day"  value="${recepcionDeOroInstance?.fechaDeRecepcion}"  />--}%
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="recepcionDeOro.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeRecepcion" precision="day"  value="${recepcionDeOroInstance?.fechaDeRecepcion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'cliente', 'error')} required" style="background-color: #b0e0e6">
	<label>
		CI de Cliente
	</label>
	<g:textField name="ciCliente" value="${recepcionDeOroInstance?.cliente?.ci}" size="10"/>

	<label>
		Nombre de Cliente
	</label>
	<g:textField name="nombreCliente" value="${recepcionDeOroInstance?.cliente?.nombre}" size="30" readonly="false"/>
	<g:link controller="cliente" action="create" target="_blank">Crear Cliente</g:link>

	<g:hiddenField name="cliente.id" value="${recepcionDeOroInstance?.cliente?.id}"/>
	<g:hiddenField name="empresa.id" value="${recepcionDeOroInstance?.empresa?.id}"/>
	<g:hiddenField name="costoTransporteComplejos" />
	<g:hiddenField name="unidadMonetariaComplejos" />
	<g:hiddenField name="unidadDeCobroComplejos" />
	<g:hiddenField name="costoTransporteConcentrados" />
	<g:hiddenField name="unidadMonetariaConcentrados" />
	<g:hiddenField name="unidadDeCobroConcentrados" />
	<g:hiddenField name="tipoDeCambioComercial" />
</div>

%{--<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'cliente', 'error')} required">--}%
%{--<label>--}%
%{--Nombre de Cliente--}%
%{--</label>--}%
%{--<g:textField name="nombreCliente" value="${recepcionDeOroInstance?.cliente?.nombre}" size="30" readonly="false"/>--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'cliente', 'error')} required" style="background-color: #b0e0e6">
	<label>
		Empresa
	</label>
	<g:textField name="nombreEmpresa" value="${recepcionDeOroInstance?.empresa?.nombreDeEmpresa}" class="amarillo" size="30" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'chofer', 'error')} required" style="background-color: #fafad2">
	<label>
		CI de Chofer
	</label>
	<g:textField name="ciChofer" value="${recepcionDeOroInstance?.chofer?.ci}" size="10"/>

	<label>
		Nombre de Chofer
	</label>
	<g:textField name="nombreChofer" value="${recepcionDeOroInstance?.chofer?.nombre}" size="30" readonly="false"/>
	<g:link controller="chofer" action="create" target="_blank">Crear Chofer</g:link>

	<g:hiddenField name="chofer.id" value="${recepcionDeOroInstance?.chofer?.id}"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'cliente', 'error')} required">--}%
%{--<label>--}%
%{--Nombre de Chofer--}%
%{--</label>--}%
%{--<g:textField name="nombreChofer" value="${recepcionDeOroInstance?.chofer?.nombre}" size="50" readonly="false"/>--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'automovil', 'error')} required" style="background-color: #e6e6fa">
	<label>
		Placa de Automovil
	</label>
	<g:textField name="placa" value="${recepcionDeOroInstance?.automovil?.placa}"/><g:link controller="automovil" action="create" target="_blank">Crear Automovil</g:link>
	<g:hiddenField name="automovil.id" value="${recepcionDeOroInstance?.automovil?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'automovil', 'error')} required" style="background-color: #e6e6fa">
	<label>
		Modelo
	</label>
	<g:textField name="modelo" value="${recepcionDeOroInstance?.automovil?.modelo}" class="amarillo" size="30" readonly="true"/>

	<label style="width: 12%">
		Color
	</label>
	<g:textField name="color" value="${recepcionDeOroInstance?.automovil?.color}" class="amarillo" size="30" readonly="true"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'automovil', 'error')} required">--}%
%{--<label>--}%
%{--Color--}%
%{--</label>--}%
%{--<g:textField name="color" value="${recepcionDeOroInstance?.automovil?.color}" class="amarillo" size="30" readonly="true"/>--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'numeroDeDocumento', 'error')} required" style="display: none;">
	<label for="numeroDeDocumento">
		<g:message code="recepcionDeOro.numeroDeDocumento.label" default="Numero De Documento" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="numeroDeDocumento" inputmode="numeric" value="${recepcionDeOroInstance?.numeroDeDocumento}" required=""/>
	<g:checkBox name="tieneDocumentos" value="1" checked="true"/><span style="padding-left: 5px">Desmarque si no existe documentación.</span>
</div>

<h1 style="font-weight: bold">Información del Producto</h1>

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'tipoDeMaterial', 'error')} " style="display: none;">
	<label for="tipoDeMaterial">
		<g:message code="recepcionDeOro.tipoDeMaterial.label" default="Tipo De Material" />

	</label>
	<g:select name="tipoDeMaterial" from="${['CONCENTRADO','BROZA']}" value="${recepcionDeOroInstance?.tipoDeMaterial}" valueMessagePrefix="recepcionDeOro.tipoDeMaterial" />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'cantidadDeSacos', 'error')} required" >
	<label for="cantidadDeSacos">
		<g:message code="recepcionDeOro.cantidadDeSacos.label" default="Cantidad De Sacos" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="cantidadDeSacos" inputmode="numeric" value="${recepcionDeOroInstance?.cantidadDeSacos}" required="" />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="recepcionDeOro.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: recepcionDeOroInstance, field: 'pesoBruto')}" required="" inputmode="decimal"/>
	<g:hiddenField name="pesoTara" value="0" />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'costoDeTransporte', 'error')} required" >
	<label for="costoDeTransporte">
		<g:message code="recepcionDeOro.costoDeTransporte.label" default="Costo De Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoDeTransporte" value="${fieldValue(bean: recepcionDeOroInstance, field: 'costoDeTransporte')}" required=""  inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'anticipoAutorizado', 'error')} required" style="display: none">
	<label for="anticipoAutorizado">
		<g:message code="recepcionDeOro.anticipoAutorizado.label" default="Anticipo Autorizado" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="anticipoAutorizado" value="${fieldValue(bean: recepcionDeOroInstance, field: 'anticipoAutorizado')}" required=""  inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'estadoDelLote', 'error')} required" style="display: none">
	<label for="estadoDelLote">
		<g:message code="recepcionDeOro.estadoDelLote.label" default="Estado Del Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="estadoDelLote" from="${['NO LIQUIDADO','LIQUIDADO','Quemado','Provisional','Baja']}" required="" value="${recepcionDeOroInstance?.estadoDelLote}" valueMessagePrefix="recepcionDeOro.estadoDelLote"/>
</div>
<div id="_cotizaciones">
	<h1 style="font-weight: bold">Cotizaciones</h1>

	<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'cotizacionDiariaDeMinerales', 'error')} required">
		<label for="cotizacionDiariaDeMinerales">
			<g:message code="recepcionDeOro.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" />
			<span class="required-indicator">*</span>
		</label>
		<g:select id="cotizacionDiariaDeMinerales" name="cotizacionDiariaDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionDiariaDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDeOroInstance?.cotizacionDiariaDeMinerales?.id}" class="many-to-one"/>
	</div>

	<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'cotizacionQuincenalDeMinerales', 'error')} required">
		<label for="cotizacionQuincenalDeMinerales">
			<g:message code="recepcionDeOro.cotizacionQuincenalDeMinerales.label" default="Cotizacion Quincenal De Minerales" />
			<span class="required-indicator">*</span>
		</label>
		<g:select id="cotizacionQuincenalDeMinerales" name="cotizacionQuincenalDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionQuincenalDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDeOroInstance?.cotizacionQuincenalDeMinerales?.id}" class="many-to-one"/>
	</div>

	<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'alicuota', 'error')} required">
		<label for="alicuota">
			<g:message code="recepcionDeOro.alicuota.label" default="Alicuota" />
			<span class="required-indicator">*</span>
		</label>
		<g:select id="alicuota" name="alicuota.id" from="${org.socymet.cotizaciones.Alicuota.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDeOroInstance?.alicuota?.id}" class="many-to-one"/>
	</div>
</div>

<g:hiddenField name="detalleLaboratorio1" value="${fieldValue(bean: recepcionDeOroInstance, field: 'detalleLaboratorio1')}"/>
<g:hiddenField name="costoLaboratorio1" value="${fieldValue(bean: recepcionDeOroInstance, field: 'costoLaboratorio1')}"/>
<g:hiddenField name="totalCostoLaboratorio" value="${fieldValue(bean: recepcionDeOroInstance, field: 'totalCostoLaboratorio')}"/>

<div class="fieldcontain ${hasErrors(bean: recepcionDeOroInstance, field: 'observaciones', 'error')} " style="display: none">
	<label for="observaciones">
		<g:message code="recepcionDeOro.observaciones.label" default="Observaciones" />

	</label>
	<g:textField name="observaciones" value="${recepcionDeOroInstance?.observaciones}" size="90"/>
</div>

