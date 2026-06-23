<%@ page import="org.socymet.recepcion.RecepcionDePlata" %>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="recepcionDePlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeRecepcion" precision="day"  value="${recepcionDePlataInstance?.fechaDeRecepcion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'cliente', 'error')} required">
    <label>
        CI de Cliente
    </label>
    <g:textField name="ciCliente" value="${recepcionDePlataInstance?.cliente?.ci}"/><g:link controller="cliente" action="create" target="_blank">Crear Cliente</g:link>
    <g:hiddenField name="cliente.id" value="${recepcionDePlataInstance?.cliente?.id}"/>
    <g:hiddenField name="empresa.id" value="${recepcionDePlataInstance?.empresa?.id}"/>
    <g:hiddenField name="costoTransportePlata" />
    <g:hiddenField name="unidadMonetariaPlata" />
    <g:hiddenField name="unidadDeCobroPlata" />
    <g:hiddenField name="tipoDeCambioComercial" />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'cliente', 'error')} required">
    <label>
        Nombre de Cliente
    </label>
    <g:textField name="nombreCliente" value="${recepcionDePlataInstance?.cliente?.nombre}" size="50" readonly="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'cliente', 'error')} required">
    <label>
        Empresa
    </label>
    <g:textField name="nombreEmpresa" value="${recepcionDePlataInstance?.empresa?.nombreDeEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'chofer', 'error')} required">
    <label>
        CI de Chofer
    </label>
    <g:textField name="ciChofer" value="${recepcionDePlataInstance?.chofer?.ci}"/><g:link controller="chofer" action="create" target="_blank">Crear Chofer</g:link>
    <g:hiddenField name="chofer.id" value="${recepcionDePlataInstance?.chofer?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'cliente', 'error')} required">
    <label>
        Nombre de Chofer
    </label>
    <g:textField name="nombreChofer" value="${recepcionDePlataInstance?.chofer?.nombre}" size="50" readonly="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'automovil', 'error')} required">
    <label>
        Placa de Automovil
    </label>
    <g:textField name="placa" value="${recepcionDePlataInstance?.automovil?.placa}"/><g:link controller="automovil" action="create" target="_blank">Crear Automovil</g:link>
    <g:hiddenField name="automovil.id" value="${recepcionDePlataInstance?.automovil?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'automovil', 'error')} required">
    <label>
        Modelo
    </label>
    <g:textField name="modelo" value="${recepcionDePlataInstance?.automovil?.modelo}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'automovil', 'error')} required">
    <label>
        Color
    </label>
    <g:textField name="color" value="${recepcionDePlataInstance?.automovil?.color}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'cantidadDeSacos', 'error')} required">
	<label for="cantidadDeSacos">
		<g:message code="recepcionDePlata.cantidadDeSacos.label" default="Cantidad De Sacos" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${recepcionDePlataInstance?.cantidadDeSacos}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="recepcionDePlata.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: recepcionDePlataInstance, field: 'pesoBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'pesoTara', 'error')} required">
	<label for="pesoTara">
		<g:message code="recepcionDePlata.pesoTara.label" default="Peso Tara" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoTara" value="${fieldValue(bean: recepcionDePlataInstance, field: 'pesoTara')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'costoDeTransporte', 'error')} required">
	<label for="costoDeTransporte">
		<g:message code="recepcionDePlata.costoDeTransporte.label" default="Costo De Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoDeTransporte" value="${fieldValue(bean: recepcionDePlataInstance, field: 'costoDeTransporte')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'estadoDelLote', 'error')} required">
	<label for="estadoDelLote">
		<g:message code="recepcionDePlata.estadoDelLote.label" default="Estado Del Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="estadoDelLote" from="${['NO LIQUIDADO','LIQUIDADO','Quemado','Provisional','Baja']}" required="" value="${recepcionDePlataInstance?.estadoDelLote}" valueMessagePrefix="recepcionDePlata.estadoDelLote"/>
</div>

<div id="_cotizaciones">
    <h1 style="font-weight: bold">Cotizaciones</h1>

    <div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'cotizacionDiariaDeMinerales', 'error')} required">
        <label for="cotizacionDiariaDeMinerales">
            <g:message code="recepcionDePlata.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="cotizacionDiariaDeMinerales" name="cotizacionDiariaDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionDiariaDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDePlataInstance?.cotizacionDiariaDeMinerales?.id}" class="many-to-one"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'cotizacionQuincenalDeMinerales', 'error')} required">
        <label for="cotizacionQuincenalDeMinerales">
            <g:message code="recepcionDePlata.cotizacionQuincenalDeMinerales.label" default="Cotizacion Quincenal De Minerales" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="cotizacionQuincenalDeMinerales" name="cotizacionQuincenalDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionQuincenalDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDePlataInstance?.cotizacionQuincenalDeMinerales?.id}" class="many-to-one"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'alicuota', 'error')} required">
        <label for="alicuota">
            <g:message code="recepcionDePlata.alicuota.label" default="Alicuota" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="alicuota" name="alicuota.id" from="${org.socymet.cotizaciones.Alicuota.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDePlataInstance?.alicuota?.id}" class="many-to-one"/>
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
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'detalleLaboratorio1', 'error')}">
            <g:field name="detalleLaboratorio1" value="${fieldValue(bean: recepcionDePlataInstance, field: 'detalleLaboratorio1')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'costoLaboratorio1', 'error')}">
            <g:field name="costoLaboratorio1" value="${fieldValue(bean: recepcionDePlataInstance, field: 'costoLaboratorio1')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'detalleLaboratorio2', 'error')}">
            <g:field name="detalleLaboratorio2" value="${fieldValue(bean: recepcionDePlataInstance, field: 'detalleLaboratorio2')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'costoLaboratorio2', 'error')}">
            <g:field name="costoLaboratorio2" value="${fieldValue(bean: recepcionDePlataInstance, field: 'costoLaboratorio2')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'detalleLaboratorio3', 'error')}">
            <g:field name="detalleLaboratorio3" value="${fieldValue(bean: recepcionDePlataInstance, field: 'detalleLaboratorio3')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'costoLaboratorio3', 'error')}">
            <g:field name="costoLaboratorio3" value="${fieldValue(bean: recepcionDePlataInstance, field: 'costoLaboratorio3')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'detalleLaboratorio4', 'error')}">
            <g:field name="detalleLaboratorio4" value="${fieldValue(bean: recepcionDePlataInstance, field: 'detalleLaboratorio4')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="costoLaboratorio4" value="${fieldValue(bean: recepcionDePlataInstance, field: 'costoLaboratorio4')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="recepcionDePlata.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: recepcionDePlataInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: recepcionDePlataInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="recepcionDePlata.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${recepcionDePlataInstance?.observaciones}" size="90"/>
</div>
