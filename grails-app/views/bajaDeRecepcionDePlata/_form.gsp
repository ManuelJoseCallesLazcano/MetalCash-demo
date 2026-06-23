<%@ page import="org.socymet.recepcion.BajaDeRecepcionDePlata" %>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="bajaDeRecepcionDePlata.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${bajaDeRecepcionDePlataInstance?.lote}"/>
    <g:hiddenField name="recepcionId" value="${bajaDeRecepcionDePlataInstance.recepcionId}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="bajaDeRecepcionDePlata.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${bajaDeRecepcionDePlataInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="bajaDeRecepcionDePlata.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${bajaDeRecepcionDePlataInstance?.nombreEmpresa}"class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="bajaDeRecepcionDePlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${bajaDeRecepcionDePlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="bajaDeRecepcionDePlata.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: bajaDeRecepcionDePlataInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'fechaDeBaja', 'error')} required">
    <label for="fechaDeBaja">
        <g:message code="bajaDeRecepcionDePlata.fechaDeBaja.label" default="Fecha De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeBaja" precision="day"  value="${bajaDeRecepcionDePlataInstance?.fechaDeBaja}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'motivoDeBaja', 'error')} required">
    <label for="motivoDeBaja">
        <g:message code="bajaDeRecepcionDePlata.motivoDeBaja.label" default="Motivo De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="motivoDeBaja" from="${['Ley baja','Proveedor desconforme por su ley','Proveedor desconforme con el precio del mineral']}" required="" value="${bajaDeRecepcionDePlataInstance?.motivoDeBaja}" valueMessagePrefix="bajaDeRecepcionDePlata.motivoDeBaja"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'tipoDeBaja', 'error')} required">
    <label for="tipoDeBaja">
        <g:message code="bajaDeRecepcionDePlata.tipoDeBaja.label" default="Tipo De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="tipoDeBaja" from="${['Baja por retiro', 'Baja por transferencia']}" required="" value="${bajaDeRecepcionDePlataInstance?.tipoDeBaja}" valueMessagePrefix="bajaDeRecepcionDePlata.tipoDeBaja"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'loteDestino', 'error')} ">
    <label for="loteDestino">
        <g:message code="bajaDeRecepcionDePlata.loteDestino.label" default="Lote Destino" />

    </label>
    <g:textField name="loteDestino" value="${bajaDeRecepcionDePlataInstance?.loteDestino}"/>
</div>

<g:hiddenField name="recepcionDestinoId" value="${bajaDeRecepcionDePlataInstance.recepcionDestinoId}"/>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'gastoPorChanqueo', 'error')} ">
    <label for="gastoPorChanqueo">
        <g:message code="bajaDeRecepcionDePlata.gastoPorChanqueo.label" default="Gasto Por Chanqueo" />

    </label>
    <g:field name="gastoPorChanqueo" value="${fieldValue(bean: bajaDeRecepcionDePlataInstance, field: 'gastoPorChanqueo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'gastoPorManipuleo', 'error')} ">
    <label for="gastoPorManipuleo">
        <g:message code="bajaDeRecepcionDePlata.gastoPorManipuleo.label" default="Gasto Por Manipuleo" />

    </label>
    <g:field name="gastoPorManipuleo" value="${fieldValue(bean: bajaDeRecepcionDePlataInstance, field: 'gastoPorManipuleo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'gastoPorAnalisis', 'error')} ">
    <label for="gastoPorAnalisis">
        <g:message code="bajaDeRecepcionDePlata.gastoPorAnalisis.label" default="Gasto Por Analisis" />

    </label>
    <g:field name="gastoPorAnalisis" value="${fieldValue(bean: bajaDeRecepcionDePlataInstance, field: 'gastoPorAnalisis')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'gastoPorAnticipo', 'error')} ">
    <label for="gastoPorAnticipo">
        <g:message code="bajaDeRecepcionDePlata.gastoPorAnticipo.label" default="Gasto Por Anticipo" />

    </label>
    <g:field name="gastoPorAnticipo" value="${fieldValue(bean: bajaDeRecepcionDePlataInstance, field: 'gastoPorAnticipo')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'gastoPorTransporte', 'error')} ">
    <label for="gastoPorTransporte">
        <g:message code="bajaDeRecepcionDePlata.gastoPorTransporte.label" default="Gasto Por Transporte" />

    </label>
    <g:field name="gastoPorTransporte" value="${fieldValue(bean: bajaDeRecepcionDePlataInstance, field: 'gastoPorTransporte')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'otrosGastos', 'error')} ">
    <label for="otrosGastos">
        <g:message code="bajaDeRecepcionDePlata.otrosGastos.label" default="Otros Gastos" />

    </label>
    <g:field name="otrosGastos" value="${fieldValue(bean: bajaDeRecepcionDePlataInstance, field: 'otrosGastos')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'totalDeGastos', 'error')} required">
    <label for="totalDeGastos">
        <g:message code="bajaDeRecepcionDePlata.totalDeGastos.label" default="Total De Gastos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalDeGastos" value="${fieldValue(bean: bajaDeRecepcionDePlataInstance, field: 'totalDeGastos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDePlataInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="bajaDeRecepcionDePlata.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${bajaDeRecepcionDePlataInstance?.observaciones}" size="90"/>
</div>
