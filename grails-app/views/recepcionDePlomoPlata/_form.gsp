<%@ page import="org.socymet.recepcion.RecepcionDePlomoPlata" %>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'deposito', 'error')} required">
    <label for="deposito">
        <g:message code="recepcionDePlomoPlata.deposito.label" default="Deposito" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${recepcionDePlomoPlataInstance?.deposito?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="recepcionDePlomoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeRecepcion" precision="day"  value="${recepcionDePlomoPlataInstance?.fechaDeRecepcion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'cliente', 'error')} required">
    <label>
        CI de Cliente
    </label>
    <g:textField name="ciCliente" value="${recepcionDePlomoPlataInstance?.cliente?.ci}"/><g:link controller="cliente" action="create" target="_blank">Crear Cliente</g:link>
    <g:hiddenField name="cliente.id" value="${recepcionDePlomoPlataInstance?.cliente?.id}"/>
    <g:hiddenField name="empresa.id" value="${recepcionDePlomoPlataInstance?.empresa?.id}"/>
    <g:hiddenField name="costoTransportePlata" />
    <g:hiddenField name="unidadMonetariaPlata" />
    <g:hiddenField name="unidadDeCobroPlata" />
    <g:hiddenField name="tipoDeCambioComercial" />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'cliente', 'error')} required">
    <label>
        Nombre de Cliente
    </label>
    <g:textField name="nombreCliente" value="${recepcionDePlomoPlataInstance?.cliente?.nombre}" size="50" readonly="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'cliente', 'error')} required">
    <label>
        Empresa
    </label>
    <g:textField name="nombreEmpresa" value="${recepcionDePlomoPlataInstance?.empresa?.nombreDeEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'chofer', 'error')} required">
    <label>
        CI de Chofer
    </label>
    <g:textField name="ciChofer" value="${recepcionDePlomoPlataInstance?.chofer?.ci}"/><g:link controller="chofer" action="create" target="_blank">Crear Chofer</g:link>
    <g:hiddenField name="chofer.id" value="${recepcionDePlomoPlataInstance?.chofer?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'cliente', 'error')} required">
    <label>
        Nombre de Chofer
    </label>
    <g:textField name="nombreChofer" value="${recepcionDePlomoPlataInstance?.chofer?.nombre}" size="50" readonly="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'automovil', 'error')} required">
    <label>
        Placa de Automovil
    </label>
    <g:textField name="placa" value="${recepcionDePlomoPlataInstance?.automovil?.placa}"/><g:link controller="automovil" action="create" target="_blank">Crear Automovil</g:link>
    <g:hiddenField name="automovil.id" value="${recepcionDePlomoPlataInstance?.automovil?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'automovil', 'error')} required">
    <label>
        Modelo
    </label>
    <g:textField name="modelo" value="${recepcionDePlomoPlataInstance?.automovil?.modelo}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'automovil', 'error')} required">
    <label>
        Color
    </label>
    <g:textField name="color" value="${recepcionDePlomoPlataInstance?.automovil?.color}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'cantidadDeSacos', 'error')} required">
	<label for="cantidadDeSacos">
		<g:message code="recepcionDePlomoPlata.cantidadDeSacos.label" default="Cantidad De Sacos" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${recepcionDePlomoPlataInstance?.cantidadDeSacos}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="recepcionDePlomoPlata.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: recepcionDePlomoPlataInstance, field: 'pesoBruto')}" required="" inputmode="decimal"/>
    <g:hiddenField name="pesoTara" value="0" />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'costoDeTransporte', 'error')} required">
	<label for="costoDeTransporte">
		<g:message code="recepcionDePlomoPlata.costoDeTransporte.label" default="Costo De Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoDeTransporte" value="${fieldValue(bean: recepcionDePlomoPlataInstance, field: 'costoDeTransporte')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'estadoDelLote', 'error')} required">
	<label for="estadoDelLote">
		<g:message code="recepcionDePlomoPlata.estadoDelLote.label" default="Estado Del Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="estadoDelLote" from="${['NO LIQUIDADO','LIQUIDADO','Quemado','Provisional','Baja']}" required="" value="${recepcionDePlomoPlataInstance?.estadoDelLote}" valueMessagePrefix="recepcionDePlomoPlata.estadoDelLote"/>
</div>

<div id="_cotizaciones">
    <h1 style="font-weight: bold">Cotizaciones</h1>

    <div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'cotizacionDiariaDeMinerales', 'error')} required">
        <label for="cotizacionDiariaDeMinerales">
            <g:message code="recepcionDePlomoPlata.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="cotizacionDiariaDeMinerales" name="cotizacionDiariaDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionDiariaDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDePlomoPlataInstance?.cotizacionDiariaDeMinerales?.id}" class="many-to-one"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'cotizacionQuincenalDeMinerales', 'error')} required">
        <label for="cotizacionQuincenalDeMinerales">
            <g:message code="recepcionDePlomoPlata.cotizacionQuincenalDeMinerales.label" default="Cotizacion Quincenal De Minerales" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="cotizacionQuincenalDeMinerales" name="cotizacionQuincenalDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionQuincenalDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDePlomoPlataInstance?.cotizacionQuincenalDeMinerales?.id}" class="many-to-one"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'alicuota', 'error')} required">
        <label for="alicuota">
            <g:message code="recepcionDePlomoPlata.alicuota.label" default="Alicuota" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="alicuota" name="alicuota.id" from="${org.socymet.cotizaciones.Alicuota.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDePlomoPlataInstance?.alicuota?.id}" class="many-to-one"/>
    </div>
</div>

<h1 style="font-weight: bold">Detalle de Analisis Realizados</h1>

<table class="center" border="0" style="width: 70%;">
    <thead>
    <tr>
        <th style="text-align: center; width: 70%">DESCRIPCION DE ANALISIS</th>
        <th style="text-align: center; width: 30%">COSTO</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'detalleLaboratorio1', 'error')}">
            <g:field name="detalleLaboratorio1" value="${fieldValue(bean: recepcionDePlomoPlataInstance, field: 'detalleLaboratorio1')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'costoLaboratorio1', 'error')}">
            <g:field name="costoLaboratorio1" value="${fieldValue(bean: recepcionDePlomoPlataInstance, field: 'costoLaboratorio1')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'detalleLaboratorio2', 'error')}">
            <g:field name="detalleLaboratorio2" value="${fieldValue(bean: recepcionDePlomoPlataInstance, field: 'detalleLaboratorio2')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'costoLaboratorio2', 'error')}">
            <g:field name="costoLaboratorio2" value="${fieldValue(bean: recepcionDePlomoPlataInstance, field: 'costoLaboratorio2')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'detalleLaboratorio3', 'error')}">
            <g:field name="detalleLaboratorio3" value="${fieldValue(bean: recepcionDePlomoPlataInstance, field: 'detalleLaboratorio3')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'costoLaboratorio3', 'error')}">
            <g:field name="costoLaboratorio3" value="${fieldValue(bean: recepcionDePlomoPlataInstance, field: 'costoLaboratorio3')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'detalleLaboratorio4', 'error')}">
            <g:field name="detalleLaboratorio4" value="${fieldValue(bean: recepcionDePlomoPlataInstance, field: 'detalleLaboratorio4')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="costoLaboratorio4" value="${fieldValue(bean: recepcionDePlomoPlataInstance, field: 'costoLaboratorio4')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="recepcionDePlomoPlata.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: recepcionDePlomoPlataInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlomoPlataInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="recepcionDePlomoPlata.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${recepcionDePlomoPlataInstance?.observaciones}" size="90"/>
</div>
