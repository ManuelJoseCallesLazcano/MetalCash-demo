<%@ page import="org.socymet.recepcion.RecepcionDeEstano" %>

<g:if test="${recepcionDeEstanoInstance?.loteEstano}">
    <div class="fieldcontain">
        <span id="loteEstano-label" class="property-label"><g:message code="recepcionDeEstano.loteEstano.label" default="Lote Estano" /></span>

        <span class="property-value" aria-labelledby="loteEstano-label" style="font-weight: bold; color: #b81900; font-size: 18px">${recepcionDeEstanoInstance.toString()}</span>

    </div>
</g:if>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'codigoDepositoEstano', 'error')} required" style="display: none;">
    <label for="codigoDepositoEstano">
        <g:message code="recepcionDeEstano.codigoDepositoEstano.label" default="Codigo Deposito Estano" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="codigoDepositoEstano" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'codigoDepositoEstano')}" required="" inputmode="numeric"/>
</div>

<h1 style="font-weight: bold">Información General</h1>

<div id="_deposito" class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'deposito', 'error')} required" style="display: none">
    <label for="deposito">
        <g:message code="recepcionDeEstano.deposito.label" default="Deposito" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${recepcionDeEstanoInstance?.deposito?.id}" class="many-to-one"/>

    %{--<label for="fechaDeRecepcion" style="width: 22%">--}%
    %{--<g:message code="recepcionDeEstano.fechaDeRecepcion.label" default="Fecha De Recepcion" />--}%
    %{--<span class="required-indicator">*</span>--}%
    %{--</label>--}%
    %{--<g:datePicker name="fechaDeRecepcion" precision="day"  value="${recepcionDeEstanoInstance?.fechaDeRecepcion}"  />--}%
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="recepcionDeEstano.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeRecepcion" precision="day"  value="${recepcionDeEstanoInstance?.fechaDeRecepcion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'cliente', 'error')} required" style="background-color: #b0e0e6">
    <label>
        CI de Cliente
    </label>
    <g:textField name="ciCliente" value="${recepcionDeEstanoInstance?.cliente?.ci}" size="10"/>

    <label>
        Nombre de Cliente
    </label>
    <g:textField name="nombreCliente" value="${recepcionDeEstanoInstance?.cliente?.nombre}" size="30" readonly="false"/>
    <g:link controller="cliente" action="create" target="_blank">Crear Cliente</g:link>

    <g:hiddenField name="cliente.id" value="${recepcionDeEstanoInstance?.cliente?.id}"/>
    <g:hiddenField name="empresa.id" value="${recepcionDeEstanoInstance?.empresa?.id}"/>
    <g:hiddenField name="costoTransporteComplejo" />
    <g:hiddenField name="unidadMonetariaComplejo" />
    <g:hiddenField name="unidadDeCobroComplejo" />
    <g:hiddenField name="costoTransporteConcentrados" />
    <g:hiddenField name="unidadMonetariaConcentrados" />
    <g:hiddenField name="unidadDeCobroConcentrados" />
    <g:hiddenField name="tipoDeCambioComercial" />
</div>

%{--<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'cliente', 'error')} required">--}%
%{--<label>--}%
%{--Nombre de Cliente--}%
%{--</label>--}%
%{--<g:textField name="nombreCliente" value="${recepcionDeEstanoInstance?.cliente?.nombre}" size="30" readonly="false"/>--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'cliente', 'error')} required" style="background-color: #b0e0e6">
    <label>
        Empresa
    </label>
    <g:textField name="nombreEmpresa" value="${recepcionDeEstanoInstance?.empresa?.nombreDeEmpresa}" class="amarillo" size="30" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'chofer', 'error')} required" style="background-color: #fafad2">
    <label>
        CI de Chofer
    </label>
    <g:textField name="ciChofer" value="${recepcionDeEstanoInstance?.chofer?.ci}" size="10"/>

    <label>
        Nombre de Chofer
    </label>
    <g:textField name="nombreChofer" value="${recepcionDeEstanoInstance?.chofer?.nombre}" size="30" readonly="false"/>
    <g:link controller="chofer" action="create" target="_blank">Crear Chofer</g:link>

    <g:hiddenField name="chofer.id" value="${recepcionDeEstanoInstance?.chofer?.id}"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'cliente', 'error')} required">--}%
%{--<label>--}%
%{--Nombre de Chofer--}%
%{--</label>--}%
%{--<g:textField name="nombreChofer" value="${recepcionDeEstanoInstance?.chofer?.nombre}" size="50" readonly="false"/>--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'automovil', 'error')} required" style="background-color: #e6e6fa">
    <label>
        Placa de Automovil
    </label>
    <g:textField name="placa" value="${recepcionDeEstanoInstance?.automovil?.placa}"/><g:link controller="automovil" action="create" target="_blank">Crear Automovil</g:link>
    <g:hiddenField name="automovil.id" value="${recepcionDeEstanoInstance?.automovil?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'automovil', 'error')} required" style="background-color: #e6e6fa">
    <label>
        Modelo
    </label>
    <g:textField name="modelo" value="${recepcionDeEstanoInstance?.automovil?.modelo}" class="amarillo" size="30" readonly="true"/>

    <label style="width: 12%">
        Color
    </label>
    <g:textField name="color" value="${recepcionDeEstanoInstance?.automovil?.color}" class="amarillo" size="30" readonly="true"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'automovil', 'error')} required">--}%
%{--<label>--}%
%{--Color--}%
%{--</label>--}%
%{--<g:textField name="color" value="${recepcionDeEstanoInstance?.automovil?.color}" class="amarillo" size="30" readonly="true"/>--}%
%{--</div>--}%

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'numeroDeDocumento', 'error')} required">
    <label for="numeroDeDocumento">
        <g:message code="recepcionDeEstano.numeroDeDocumento.label" default="Numero De Documento" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="numeroDeDocumento" inputmode="numeric" value="${recepcionDeEstanoInstance?.numeroDeDocumento}" required=""/>
    <g:checkBox name="tieneDocumentos" value="1" checked="true"/><span style="padding-left: 5px">Desmarque si no existe documentación.</span>
</div>

<g:hiddenField name="documentacionCompleta" value="${recepcionDeEstanoInstance?.documentacionCompleta}" />

<h1 style="font-weight: bold">Información del Producto</h1>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'tipoDeMaterial', 'error')} ">
    <label for="tipoDeMaterial">
        <g:message code="recepcionDeEstano.tipoDeMaterial.label" default="Tipo De Material" />

    </label>
    <g:select name="tipoDeMaterial" from="${['CONCENTRADO','BROZA']}" value="${recepcionDeEstanoInstance?.tipoDeMaterial}" valueMessagePrefix="recepcionDeEstano.tipoDeMaterial" />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'cantidadDeSacos', 'error')} required">
    <label for="cantidadDeSacos">
        <g:message code="recepcionDeEstano.cantidadDeSacos.label" default="Cantidad De Sacos" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${recepcionDeEstanoInstance?.cantidadDeSacos}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="recepcionDeEstano.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'pesoBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'pesoTara', 'error')} required" style="display: none">
    <label for="pesoTara">
        <g:message code="recepcionDeEstano.pesoTara.label" default="Peso Tara" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoTara" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'pesoTara')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'costoDeTransporte', 'error')} required">
    <label for="costoDeTransporte">
        <g:message code="recepcionDeEstano.costoDeTransporte.label" default="Costo De Transporte" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="costoDeTransporte" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'costoDeTransporte')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'estadoDelLote', 'error')} required" style="display:none;">
    <label for="estadoDelLote">
        <g:message code="recepcionDeEstano.estadoDelLote.label" default="Estado Del Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="estadoDelLote" from="${['NO LIQUIDADO','LIQUIDADO','Quemado','Provisional','Baja']}" required="" value="${recepcionDeEstanoInstance?.estadoDelLote}" valueMessagePrefix="recepcionDeEstano.estadoDelLote"/>
</div>

<div id="_cotizaciones">
<h1 style="font-weight: bold">Cotizaciones</h1>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'cotizacionDiariaDeMinerales', 'error')} required">
    <label for="cotizacionDiariaDeMinerales">
        <g:message code="recepcionDeEstano.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="cotizacionDiariaDeMinerales" name="cotizacionDiariaDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionDiariaDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDeEstanoInstance?.cotizacionDiariaDeMinerales?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'cotizacionQuincenalDeMinerales', 'error')} required">
    <label for="cotizacionQuincenalDeMinerales">
        <g:message code="recepcionDeEstano.cotizacionQuincenalDeMinerales.label" default="Cotizacion Quincenal De Minerales" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="cotizacionQuincenalDeMinerales" name="cotizacionQuincenalDeMinerales.id" from="${org.socymet.cotizaciones.CotizacionQuincenalDeMinerales.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDeEstanoInstance?.cotizacionQuincenalDeMinerales?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'alicuota', 'error')} required">
    <label for="alicuota">
        <g:message code="recepcionDeEstano.alicuota.label" default="Alicuota" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="alicuota" name="alicuota.id" from="${org.socymet.cotizaciones.Alicuota.list(sort: 'fecha', order: 'desc')}" optionKey="id" required="" value="${recepcionDeEstanoInstance?.alicuota?.id}" class="many-to-one"/>
</div>
</div>

<div style="display: none;">
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
        <td class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'detalleLaboratorio1', 'error')}">
            <g:field name="detalleLaboratorio1" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'detalleLaboratorio1')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'costoLaboratorio1', 'error')}">
            <g:field name="costoLaboratorio1" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'costoLaboratorio1')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'detalleLaboratorio2', 'error')}">
            <g:field name="detalleLaboratorio2" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'detalleLaboratorio2')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'costoLaboratorio2', 'error')}">
            <g:field name="costoLaboratorio2" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'costoLaboratorio2')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'detalleLaboratorio3', 'error')}">
            <g:field name="detalleLaboratorio3" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'detalleLaboratorio3')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'costoLaboratorio3', 'error')}">
            <g:field name="costoLaboratorio3" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'costoLaboratorio3')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'detalleLaboratorio4', 'error')}">
            <g:field name="detalleLaboratorio4" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'detalleLaboratorio4')}" size="70"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="costoLaboratorio4" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'costoLaboratorio4')}" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'detalleLaboratorio4', 'error')}">
            <label for="totalCostoLaboratorio" style="width: 90%">
                <g:message code="recepcionDeEstano.totalCostoLaboratorio.label" default="Total Costo Laboratorio" />
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'costoLaboratorio4', 'error')}">
            <g:field name="totalCostoLaboratorio" value="${fieldValue(bean: recepcionDeEstanoInstance, field: 'totalCostoLaboratorio')}" class="amarillo" readonly="true"/>
        </td>
    </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: recepcionDeEstanoInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="recepcionDeEstano.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${recepcionDeEstanoInstance?.observaciones}" size="90"/>
</div>
</div>
