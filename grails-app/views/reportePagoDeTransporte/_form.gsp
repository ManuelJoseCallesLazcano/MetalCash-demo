<%@ page import="org.socymet.org.socymet.reportes.ReportePagoDeTransporte" %>

<h1 style="font-weight: bold">Listar por:</h1>

<table style="width: 500px;" class="center">
    <tbody>
    <tr>
        <td style="width: 10px"><input type="radio" id="fechas" name="myGroup" value="1" checked="checked" /></td>
        <td style="font-weight: bold">Fechas</td>
    </tr>
    <tr>
        <td style="width: 10px"><input type="radio" id="fechasEmpresa" name="myGroup" value="2" /></td>
        <td style="font-weight: bold">Fechas y Empresa</td>
    </tr>
    <tr>
        <td style="width: 10px"><input type="radio" id="fechasAutomovil" name="myGroup" value="3" /></td>
        <td style="font-weight: bold">Fechas y Automovil</td>
    </tr>
    </tbody>
</table>

<g:hiddenField name="tipoReporte" value="fechas" />

<h1 style="font-weight: bold">Parametros de busqueda:</h1>

<div class="fieldcontain ${hasErrors(bean: reportePagoDeTransporteInstance, field: 'deposito', 'error')} " style="display: none">
    <label for="deposito">
        <g:message code="reportePagoDeTransporte.deposito.label" default="Deposito" />

    </label>
    <g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" value="${reportePagoDeTransporteInstance?.deposito?.id}" class="many-to-one" noSelection="['null': '-TODOS-']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoDeTransporteInstance, field: 'elemento', 'error')} " style="display: none">
    <label for="elemento">
        <g:message code="reportePagoDeTransporte.elemento.label" default="Elemento" />

    </label>
    <g:select name="elemento" from="${['COMPLEJO','PB-AG','ZN-AG','CU-AG']}" value="${reportePagoDeTransporteInstance?.elemento}" valueMessagePrefix="reportePagoDeTransporte.elemento" noSelection="['': '-TODOS-']"/>
</div>

<div id="_empresa" class="fieldcontain ${hasErrors(bean: reportePagoDeTransporteInstance, field: 'empresa', 'error')} required" style="display: none">
    <label for="empresa">
        <g:message code="reportePagoDeTransporte.empresa.label" default="Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" required="" value="${reportePagoDeTransporteInstance?.empresa?.id}" class="many-to-one, chosen-select" style="width: 350px"/>
</div>

<div id="_automovil" class="fieldcontain ${hasErrors(bean: reportePagoDeTransporteInstance, field: 'automovil', 'error')} required" style="display: none">
    <label for="automovil">
        <g:message code="reportePagoDeTransporte.automovil.label" default="Automovil" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="automovil" name="automovil.id" from="${org.socymet.proveedor.Automovil.list([sort: 'placa'])}" optionKey="id" required="" value="${reportePagoDeTransporteInstance?.automovil?.id}" class="many-to-one, chosen-select" style="width: 350px"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoDeTransporteInstance, field: 'fechaInicial', 'error')} required">
    <label for="fechaInicial">
        <g:message code="reportePagoDeTransporte.fechaInicial.label" default="Fecha Inicial" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaInicial" precision="day"  value="${reportePagoDeTransporteInstance?.fechaInicial}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoDeTransporteInstance, field: 'fechaFinal', 'error')} required">
    <label for="fechaFinal">
        <g:message code="reportePagoDeTransporte.fechaFinal.label" default="Fecha Final" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaFinal" precision="day"  value="${reportePagoDeTransporteInstance?.fechaFinal}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: reportePagoDeTransporteInstance, field: 'estado', 'error')} " hidden>
    <label for="estado">
        <g:message code="reportePagoDeTransporte.estado.label" default="Estado" />

    </label>
    <g:select name="estado" from="${['PAGADO','SIN PAGAR']}" value="${reportePagoDeTransporteInstance?.estado}" valueMessagePrefix="reportePagoDeTransporte.estado"/>
</div>

<br/>

<div id="_resultadosComplejo">
    <div style="text-align: center;">
        <g:actionSubmit class="reporte" controller="reportePagoDeTransporte" action="crearReporte" value="Generar Reporte" />
    </div>
</div>

