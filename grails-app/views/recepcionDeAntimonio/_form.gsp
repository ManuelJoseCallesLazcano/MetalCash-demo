<%@ page import="org.socymet.recepcion.RecepcionDeAntimonio" %>


<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="recepcionDeAntimonio.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeRecepcion" precision="day"  value="${recepcionDeAntimonioInstance?.fechaDeRecepcion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'cliente', 'error')} required">
    <label>
        CI de Cliente
    </label>
    <g:textField name="ciCliente" value="${recepcionDeAntimonioInstance?.cliente?.ci}"/><g:link controller="cliente" action="create" target="_blank">Crear Cliente</g:link>
    <g:hiddenField name="cliente.id" value="${recepcionDeAntimonioInstance?.cliente?.id}"/>
    <g:hiddenField name="empresa.id" value="${recepcionDeAntimonioInstance?.empresa?.id}"/>
    <g:hiddenField name="costoTransporteAntimonio" />
    <g:hiddenField name="unidadMonetariaAntimonio" />
    <g:hiddenField name="unidadDeCobroAntimonio" />
    <g:hiddenField name="tipoDeCambioComercial" />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'cliente', 'error')} required">
    <label>
        Nombre de Cliente
    </label>
    <g:textField name="nombreCliente" value="${recepcionDeAntimonioInstance?.cliente?.nombre}" size="50" readonly="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'cliente', 'error')} required">
    <label>
        Empresa
    </label>
    <g:textField name="nombreEmpresa" value="${recepcionDeAntimonioInstance?.empresa?.nombreDeEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'chofer', 'error')} required">
    <label>
        CI de Chofer
    </label>
    <g:textField name="ciChofer" value="${recepcionDeAntimonioInstance?.chofer?.ci}"/><g:link controller="chofer" action="create" target="_blank">Crear Chofer</g:link>
    <g:hiddenField name="chofer.id" value="${recepcionDeAntimonioInstance?.chofer?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'cliente', 'error')} required">
    <label>
        Nombre de Chofer
    </label>
    <g:textField name="nombreChofer" value="${recepcionDeAntimonioInstance?.chofer?.nombre}" size="50" readonly="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'automovil', 'error')} required">
    <label>
        Placa de Automovil
    </label>
    <g:textField name="placa" value="${recepcionDeAntimonioInstance?.automovil?.placa}"/><g:link controller="automovil" action="create" target="_blank">Crear Automovil</g:link>
    <g:hiddenField name="automovil.id" value="${recepcionDeAntimonioInstance?.automovil?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'automovil', 'error')} required">
    <label>
        Modelo
    </label>
    <g:textField name="modelo" value="${recepcionDeAntimonioInstance?.automovil?.modelo}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'automovil', 'error')} required">
    <label>
        Color
    </label>
    <g:textField name="color" value="${recepcionDeAntimonioInstance?.automovil?.color}" class="amarillo" size="50" readonly="true"/>
</div>


<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'cantidadDeSacos', 'error')} required">
	<label for="cantidadDeSacos">
		<g:message code="recepcionDeAntimonio.cantidadDeSacos.label" default="Cantidad De Sacos" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${recepcionDeAntimonioInstance?.cantidadDeSacos}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="recepcionDeAntimonio.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: recepcionDeAntimonioInstance, field: 'pesoBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'pesoTara', 'error')} required">
	<label for="pesoTara">
		<g:message code="recepcionDeAntimonio.pesoTara.label" default="Peso Tara" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoTara" value="${fieldValue(bean: recepcionDeAntimonioInstance, field: 'pesoTara')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'costoDeTransporte', 'error')} required">
	<label for="costoDeTransporte">
		<g:message code="recepcionDeAntimonio.costoDeTransporte.label" default="Costo De Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoDeTransporte" value="${fieldValue(bean: recepcionDeAntimonioInstance, field: 'costoDeTransporte')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'estadoDelLote', 'error')} required">
	<label for="estadoDelLote">
		<g:message code="recepcionDeAntimonio.estadoDelLote.label" default="Estado Del Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="estadoDelLote" from="${['NO LIQUIDADO','LIQUIDADO','Quemado','Provisional','Baja']}" required="" value="${recepcionDeAntimonioInstance?.estadoDelLote}" valueMessagePrefix="recepcionDeAntimonio.estadoDelLote"/>
</div>

<div id="_cotizaciones">
    <h1 style="font-weight: bold">Cotizaciones</h1>

    <div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'cotizacionDiariaDeMinerales', 'error')} required">
        <label for="cotizacionDiariaDeMinerales">
            <g:message code="recepcionDeAntimonio.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="cotizacionDiariaDeMinerales" name="cotizacionDiariaDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionDiariaDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDeAntimonioInstance?.cotizacionDiariaDeMinerales?.id}" class="many-to-one"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'cotizacionQuincenalDeMinerales', 'error')} required">
        <label for="cotizacionQuincenalDeMinerales">
            <g:message code="recepcionDeAntimonio.cotizacionQuincenalDeMinerales.label" default="Cotizacion Quincenal De Minerales" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="cotizacionQuincenalDeMinerales" name="cotizacionQuincenalDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionQuincenalDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDeAntimonioInstance?.cotizacionQuincenalDeMinerales?.id}" class="many-to-one"/>
    </div>

    <div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'alicuota', 'error')} required">
        <label for="alicuota">
            <g:message code="recepcionDeAntimonio.alicuota.label" default="Alicuota" />
            <span class="required-indicator">*</span>
        </label>
        <g:select id="alicuota" name="alicuota.id" from="${org.socymet.cotizaciones.Alicuota.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDeAntimonioInstance?.alicuota?.id}" class="many-to-one"/>
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
        <td class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'detalleLaboratorio1', 'error')}">
            <g:field name="detalleLaboratorio1" value="${fieldValue(bean: recepcionDeAntimonioInstance, field: 'detalleLaboratorio1')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'costoLaboratorio1', 'error')}">
            <g:field name="costoLaboratorio1" value="${fieldValue(bean: recepcionDeAntimonioInstance, field: 'costoLaboratorio1')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'detalleLaboratorio2', 'error')}">
            <g:field name="detalleLaboratorio2" value="${fieldValue(bean: recepcionDeAntimonioInstance, field: 'detalleLaboratorio2')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'costoLaboratorio2', 'error')}">
            <g:field name="costoLaboratorio2" value="${fieldValue(bean: recepcionDeAntimonioInstance, field: 'costoLaboratorio2')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'detalleLaboratorio3', 'error')}">
            <g:field name="detalleLaboratorio3" value="${fieldValue(bean: recepcionDeAntimonioInstance, field: 'detalleLaboratorio3')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'costoLaboratorio3', 'error')}">
            <g:field name="costoLaboratorio3" value="${fieldValue(bean: recepcionDeAntimonioInstance, field: 'costoLaboratorio3')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'detalleLaboratorio4', 'error')}">
            <g:field name="detalleLaboratorio4" value="${fieldValue(bean: recepcionDeAntimonioInstance, field: 'detalleLaboratorio4')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="costoLaboratorio4" value="${fieldValue(bean: recepcionDeAntimonioInstance, field: 'costoLaboratorio4')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="recepcionDeAntimonio.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: recepcionDeAntimonioInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: recepcionDeAntimonioInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="recepcionDeAntimonio.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${recepcionDeAntimonioInstance?.observaciones}" size="90"/>
</div>

