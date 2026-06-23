<%@ page import="org.socymet.liquidacion.CostoTransporteLaboratorioPlata" %>



<div class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'lote', 'error')} ">
    <label for="lote">
        <g:message code="costoTransporteLaboratorioPlata.lote.label" default="Lote" />

    </label>
    <g:textField name="lote" value="${costoTransporteLaboratorioPlataInstance?.lote}"/>
    <g:hiddenField name="recepcionId" value="${costoTransporteLaboratorioPlataInstance?.recepcionId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="costoTransporteLaboratorioPlata.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${costoTransporteLaboratorioPlataInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="costoTransporteLaboratorioPlata.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${costoTransporteLaboratorioPlataInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="costoTransporteLaboratorioPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${costoTransporteLaboratorioPlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="costoTransporteLaboratorioPlata.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'costoDeTransporteAnterior', 'error')} required">
    <label for="costoDeTransporteAnterior">
        <g:message code="costoTransporteLaboratorioPlata.costoDeTransporteAnterior.label" default="Costo De Transporte Anterior" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="costoDeTransporteAnterior" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'costoDeTransporteAnterior')}" required="" class="rojo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'costoDeTransporteNuevo', 'error')} ">
    <label for="costoDeTransporteNuevo">
        <g:message code="costoTransporteLaboratorioPlata.costoDeTransporteNuevo.label" default="Costo De Transporte Nuevo" />

    </label>
    <g:field name="costoDeTransporteNuevo" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'costoDeTransporteNuevo')}" class="verde" readonly="false" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Detalle de Analisis Realizados ANTERIOR</h1>

<table class="center" border="0" style="width: 70%;">
    <thead>
    <tr>
        <th style="text-align: center; width: 70%">DESCRIPCION DE ANALISIS</th>
        <th style="text-align: center; width: 30%">COSTO</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio1Anterior', 'error')}">
            <g:field name="detalleLaboratorio1Anterior" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio1Anterior')}" size="70" class="rojo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio1Anterior', 'error')}">
            <g:field name="costoLaboratorio1Anterior" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio1Anterior')}" class="rojo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio2Anterior', 'error')}">
            <g:field name="detalleLaboratorio2Anterior" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio2Anterior')}" size="70" class="rojo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio2Anterior', 'error')}">
            <g:field name="costoLaboratorio2Anterior" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio2Anterior')}" class="rojo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio3Anterior', 'error')}">
            <g:field name="detalleLaboratorio3Anterior" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio3Anterior')}" size="70" class="rojo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio3Anterior', 'error')}">
            <g:field name="costoLaboratorio3Anterior" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio3Anterior')}" class="rojo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio4Anterior', 'error')}">
            <g:field name="detalleLaboratorio4Anterior" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio4Anterior')}" size="70" class="rojo" readonly="true"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio4Anterior', 'error')}">
            <g:field name="costoLaboratorio4Anterior" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio4Anterior')}" class="rojo" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio4Anterior', 'error')}">
            <label for="totalCostoLaboratorioAnterior" style="width: 90%">
                <g:message code="costoTransporteLaboratorioPlata.totalCostoLaboratorioAnterior.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio4Anterior', 'error')}">
            <g:field name="totalCostoLaboratorioAnterior" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'totalCostoLaboratorioAnterior')}" class="rojo" readonly="true" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Detalle de Analisis Realizados NUEVO</h1>

<table class="center" border="0" style="width: 70%;">
    <thead>
    <tr>
        <th style="text-align: center; width: 70%">DESCRIPCION DE ANALISIS</th>
        <th style="text-align: center; width: 30%">COSTO</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio1Nuevo', 'error')}">
            <g:field name="detalleLaboratorio1Nuevo" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio1Nuevo')}" size="70" class="verde" readonly="false"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio1Nuevo', 'error')}">
            <g:field name="costoLaboratorio1Nuevo" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio1Nuevo')}" class="verde" readonly="false" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio2Nuevo', 'error')}">
            <g:field name="detalleLaboratorio2Nuevo" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio2Nuevo')}" size="70" class="verde" readonly="false"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio2Nuevo', 'error')}">
            <g:field name="costoLaboratorio2Nuevo" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio2Nuevo')}" class="verde" readonly="false" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio3Nuevo', 'error')}">
            <g:field name="detalleLaboratorio3Nuevo" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio3Nuevo')}" size="70" class="verde" readonly="false"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio3Nuevo', 'error')}">
            <g:field name="costoLaboratorio3Nuevo" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio3Nuevo')}" class="verde" readonly="false" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio4Nuevo', 'error')}">
            <g:field name="detalleLaboratorio4Nuevo" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio4Nuevo')}" size="70" class="verde" readonly="false"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio4Nuevo', 'error')}">
            <g:field name="costoLaboratorio4Nuevo" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio4Nuevo')}" class="verde" readonly="false" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'detalleLaboratorio4Nuevo', 'error')}">
            <label for="totalCostoLaboratorioNuevo" style="width: 90%">
                <g:message code="costoTransporteLaboratorioPlata.totalCostoLaboratorioNuevo.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'costoLaboratorio4Nuevo', 'error')}">
            <g:field name="totalCostoLaboratorioNuevo" value="${fieldValue(bean: costoTransporteLaboratorioPlataInstance, field: 'totalCostoLaboratorioNuevo')}" class="verde" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: costoTransporteLaboratorioPlataInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="costoTransporteLaboratorioPlata.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${costoTransporteLaboratorioPlataInstance?.observaciones}" size="90"/>
</div>

