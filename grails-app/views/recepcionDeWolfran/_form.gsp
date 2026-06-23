<%@ page import="org.socymet.recepcion.RecepcionDeWolfran" %>


<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="recepcionDeWolfran.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeRecepcion" precision="day"  value="${recepcionDeWolfranInstance?.fechaDeRecepcion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'cliente', 'error')} required">
    <label>
        CI de Cliente
    </label>
    <g:textField name="ciCliente" value="${recepcionDeWolfranInstance?.cliente?.ci}"/><g:link controller="cliente" action="create" target="_blank">Crear Cliente</g:link>
    <g:hiddenField name="cliente.id" value="${recepcionDeWolfranInstance?.cliente?.id}"/>
    <g:hiddenField name="empresa.id" value="${recepcionDeWolfranInstance?.empresa?.id}"/>
    <g:hiddenField name="costoTransporteWolfran" />
    <g:hiddenField name="unidadMonetariaWolfran" />
    <g:hiddenField name="unidadDeCobroWolfran" />
    <g:hiddenField name="tipoDeCambioComercial" />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'cliente', 'error')} required">
    <label>
        Nombre de Cliente
    </label>
    <g:textField name="nombreCliente" value="${recepcionDeWolfranInstance?.cliente?.nombre}" size="50" readonly="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'cliente', 'error')} required">
    <label>
        Empresa
    </label>
    <g:textField name="nombreEmpresa" value="${recepcionDeWolfranInstance?.empresa?.nombreDeEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'chofer', 'error')} required">
    <label>
        CI de Chofer
    </label>
    <g:textField name="ciChofer" value="${recepcionDeWolfranInstance?.chofer?.ci}"/><g:link controller="chofer" action="create" target="_blank">Crear Chofer</g:link>
    <g:hiddenField name="chofer.id" value="${recepcionDeWolfranInstance?.chofer?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'cliente', 'error')} required">
    <label>
        Nombre de Chofer
    </label>
    <g:textField name="nombreChofer" value="${recepcionDeWolfranInstance?.chofer?.nombre}" size="50" readonly="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'automovil', 'error')} required">
    <label>
        Placa de Automovil
    </label>
    <g:textField name="placa" value="${recepcionDeWolfranInstance?.automovil?.placa}"/><g:link controller="automovil" action="create" target="_blank">Crear Automovil</g:link>
    <g:hiddenField name="automovil.id" value="${recepcionDeWolfranInstance?.automovil?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'automovil', 'error')} required">
    <label>
        Modelo
    </label>
    <g:textField name="modelo" value="${recepcionDeWolfranInstance?.automovil?.modelo}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'automovil', 'error')} required">
    <label>
        Color
    </label>
    <g:textField name="color" value="${recepcionDeWolfranInstance?.automovil?.color}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'cantidadDeSacos', 'error')} required">
	<label for="cantidadDeSacos">
		<g:message code="recepcionDeWolfran.cantidadDeSacos.label" default="Cantidad De Sacos" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${recepcionDeWolfranInstance?.cantidadDeSacos}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="recepcionDeWolfran.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: recepcionDeWolfranInstance, field: 'pesoBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'pesoTara', 'error')} required">
	<label for="pesoTara">
		<g:message code="recepcionDeWolfran.pesoTara.label" default="Peso Tara" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoTara" value="${fieldValue(bean: recepcionDeWolfranInstance, field: 'pesoTara')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'costoDeTransporte', 'error')} required">
	<label for="costoDeTransporte">
		<g:message code="recepcionDeWolfran.costoDeTransporte.label" default="Costo De Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoDeTransporte" value="${fieldValue(bean: recepcionDeWolfranInstance, field: 'costoDeTransporte')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'estadoDelLote', 'error')} required">
	<label for="estadoDelLote">
		<g:message code="recepcionDeWolfran.estadoDelLote.label" default="Estado Del Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="estadoDelLote" from="${['NO LIQUIDADO','LIQUIDADO','Quemado','Provisional','Baja']}" required="" value="${recepcionDeWolfranInstance?.estadoDelLote}" valueMessagePrefix="recepcionDeWolfran.estadoDelLote"/>
</div>

<div id="_cotizaciones">
    <h1 style="font-weight: bold">Cotizaciones</h1>

    <div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'cotizacionDiariaDeMinerales', 'error')} required">
        <label for="cotizacionDiariaDeMinerales">
            <g:message code="recepcionDeWolfran.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="cotizacionDiariaDeMinerales" name="cotizacionDiariaDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionDiariaDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDeWolfranInstance?.cotizacionDiariaDeMinerales?.id}" class="many-to-one"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'cotizacionQuincenalDeMinerales', 'error')} required">
        <label for="cotizacionQuincenalDeMinerales">
            <g:message code="recepcionDeWolfran.cotizacionQuincenalDeMinerales.label" default="Cotizacion Quincenal De Minerales" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="cotizacionQuincenalDeMinerales" name="cotizacionQuincenalDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionQuincenalDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDeWolfranInstance?.cotizacionQuincenalDeMinerales?.id}" class="many-to-one"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'alicuota', 'error')} required">
        <label for="alicuota">
            <g:message code="recepcionDeWolfran.alicuota.label" default="Alicuota" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="alicuota" name="alicuota.id" from="${org.socymet.cotizaciones.Alicuota.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDeWolfranInstance?.alicuota?.id}" class="many-to-one"/>
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
        <td class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'detalleLaboratorio1', 'error')}">
            <g:field name="detalleLaboratorio1" value="${fieldValue(bean: recepcionDeWolfranInstance, field: 'detalleLaboratorio1')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'costoLaboratorio1', 'error')}">
            <g:field name="costoLaboratorio1" value="${fieldValue(bean: recepcionDeWolfranInstance, field: 'costoLaboratorio1')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'detalleLaboratorio2', 'error')}">
            <g:field name="detalleLaboratorio2" value="${fieldValue(bean: recepcionDeWolfranInstance, field: 'detalleLaboratorio2')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'costoLaboratorio2', 'error')}">
            <g:field name="costoLaboratorio2" value="${fieldValue(bean: recepcionDeWolfranInstance, field: 'costoLaboratorio2')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'detalleLaboratorio3', 'error')}">
            <g:field name="detalleLaboratorio3" value="${fieldValue(bean: recepcionDeWolfranInstance, field: 'detalleLaboratorio3')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'costoLaboratorio3', 'error')}">
            <g:field name="costoLaboratorio3" value="${fieldValue(bean: recepcionDeWolfranInstance, field: 'costoLaboratorio3')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'detalleLaboratorio4', 'error')}">
            <g:field name="detalleLaboratorio4" value="${fieldValue(bean: recepcionDeWolfranInstance, field: 'detalleLaboratorio4')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="costoLaboratorio4" value="${fieldValue(bean: recepcionDeWolfranInstance, field: 'costoLaboratorio4')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="recepcionDeWolfran.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: recepcionDeWolfranInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: recepcionDeWolfranInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="recepcionDeWolfran.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${recepcionDeWolfranInstance?.observaciones}" size="90"/>
</div>
