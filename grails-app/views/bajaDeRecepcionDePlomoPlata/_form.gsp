<%@ page import="org.socymet.recepcion.BajaDeRecepcionDePlomoPlata" %>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="bajaDeRecepcionDePlomoPlata.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${bajaDeRecepcionDePlomoPlataInstance?.lote}"/>
    <g:hiddenField name="recepcionId" value="${bajaDeRecepcionDePlomoPlataInstance.recepcionId}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="bajaDeRecepcionDePlomoPlata.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${bajaDeRecepcionDePlomoPlataInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="bajaDeRecepcionDePlomoPlata.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${bajaDeRecepcionDePlomoPlataInstance?.nombreEmpresa}"class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="bajaDeRecepcionDePlomoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${bajaDeRecepcionDePlomoPlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="bajaDeRecepcionDePlomoPlata.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'fechaDeBaja', 'error')} required">
    <label for="fechaDeBaja">
        <g:message code="bajaDeRecepcionDePlomoPlata.fechaDeBaja.label" default="Fecha De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeBaja" precision="day"  value="${bajaDeRecepcionDePlomoPlataInstance?.fechaDeBaja}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'motivoDeBaja', 'error')} required">
    <label for="motivoDeBaja">
        <g:message code="bajaDeRecepcionDePlomoPlata.motivoDeBaja.label" default="Motivo De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="motivoDeBaja" from="${['Ley baja','Proveedor desconforme por su ley','Proveedor desconforme con el precio del mineral']}" required="" value="${bajaDeRecepcionDePlomoPlataInstance?.motivoDeBaja}" valueMessagePrefix="bajaDeRecepcionDePlomoPlata.motivoDeBaja"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'tipoDeBaja', 'error')} required">
    <label for="tipoDeBaja">
        <g:message code="bajaDeRecepcionDePlomoPlata.tipoDeBaja.label" default="Tipo De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="tipoDeBaja" from="${['Baja por retiro', 'Baja por transferencia']}" required="" value="${bajaDeRecepcionDePlomoPlataInstance?.tipoDeBaja}" valueMessagePrefix="bajaDeRecepcionDePlomoPlata.tipoDeBaja"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'loteDestino', 'error')} ">
    <label for="loteDestino">
        <g:message code="bajaDeRecepcionDePlomoPlata.loteDestino.label" default="Lote Destino" />

    </label>
    <g:textField name="loteDestino" value="${bajaDeRecepcionDePlomoPlataInstance?.loteDestino}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'recepcionDestinoId', 'error')} ">
    <label for="recepcionDestinoId">
        <g:message code="bajaDeRecepcionDePlomoPlata.recepcionDestinoId.label" default="Recepcion Destino Id" />

    </label>
    <g:field name="recepcionDestinoId" type="number" min="0" value="${bajaDeRecepcionDePlomoPlataInstance.recepcionDestinoId}" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'gastoPorChanqueo', 'error')} ">
    <label for="gastoPorChanqueo">
        <g:message code="bajaDeRecepcionDePlomoPlata.gastoPorChanqueo.label" default="Gasto Por Chanqueo" />

    </label>
    <g:field name="gastoPorChanqueo" value="${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'gastoPorChanqueo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'gastoPorManipuleo', 'error')} ">
    <label for="gastoPorManipuleo">
        <g:message code="bajaDeRecepcionDePlomoPlata.gastoPorManipuleo.label" default="Gasto Por Manipuleo" />

    </label>
    <g:field name="gastoPorManipuleo" value="${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'gastoPorManipuleo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'gastoPorAnalisis', 'error')} ">
    <label for="gastoPorAnalisis">
        <g:message code="bajaDeRecepcionDePlomoPlata.gastoPorAnalisis.label" default="Gasto Por Analisis" />

    </label>
    <g:field name="gastoPorAnalisis" value="${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'gastoPorAnalisis')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'gastoPorAnticipo', 'error')} ">
    <label for="gastoPorAnticipo">
        <g:message code="bajaDeRecepcionDePlomoPlata.gastoPorAnticipo.label" default="Gasto Por Anticipo" />

    </label>
    <g:field name="gastoPorAnticipo" value="${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'gastoPorAnticipo')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'gastoPorTransporte', 'error')} ">
    <label for="gastoPorTransporte">
        <g:message code="bajaDeRecepcionDePlomoPlata.gastoPorTransporte.label" default="Gasto Por Transporte" />

    </label>
    <g:field name="gastoPorTransporte" value="${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'gastoPorTransporte')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'otrosGastos', 'error')} ">
    <label for="otrosGastos">
        <g:message code="bajaDeRecepcionDePlomoPlata.otrosGastos.label" default="Otros Gastos" />

    </label>
    <g:field name="otrosGastos" value="${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'otrosGastos')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'totalDeGastos', 'error')} required">
    <label for="totalDeGastos">
        <g:message code="bajaDeRecepcionDePlomoPlata.totalDeGastos.label" default="Total De Gastos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalDeGastos" value="${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'totalDeGastos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlomoPlataInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="bajaDeRecepcionDePlomoPlata.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${bajaDeRecepcionDePlomoPlataInstance?.observaciones}" size="90"/>
</div>
