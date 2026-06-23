<%@ page import="org.socymet.recepcion.BajaDeRecepcionDeEstano" %>


<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="bajaDeRecepcionDeEstano.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${bajaDeRecepcionDeEstanoInstance?.lote}"/>
    <g:hiddenField name="recepcionId" value="${bajaDeRecepcionDeEstanoInstance.recepcionId}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="bajaDeRecepcionDeEstano.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${bajaDeRecepcionDeEstanoInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="bajaDeRecepcionDeEstano.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${bajaDeRecepcionDeEstanoInstance?.nombreEmpresa}"class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="bajaDeRecepcionDeEstano.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${bajaDeRecepcionDeEstanoInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="bajaDeRecepcionDeEstano.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'fechaDeBaja', 'error')} required">
    <label for="fechaDeBaja">
        <g:message code="bajaDeRecepcionDeEstano.fechaDeBaja.label" default="Fecha De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeBaja" precision="day"  value="${bajaDeRecepcionDeEstanoInstance?.fechaDeBaja}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'motivoDeBaja', 'error')} required">
    <label for="motivoDeBaja">
        <g:message code="bajaDeRecepcionDeEstano.motivoDeBaja.label" default="Motivo De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="motivoDeBaja" from="${['Ley baja','Proveedor desconforme por su ley','Proveedor desconforme con el precio del mineral']}" required="" value="${bajaDeRecepcionDeEstanoInstance?.motivoDeBaja}" valueMessagePrefix="bajaDeRecepcionDeEstano.motivoDeBaja"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'tipoDeBaja', 'error')} required">
    <label for="tipoDeBaja">
        <g:message code="bajaDeRecepcionDeEstano.tipoDeBaja.label" default="Tipo De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="tipoDeBaja" from="${['Baja por retiro', 'Baja por transferencia']}" required="" value="${bajaDeRecepcionDeEstanoInstance?.tipoDeBaja}" valueMessagePrefix="bajaDeRecepcionDeEstano.tipoDeBaja"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'loteDestino', 'error')} ">
    <label for="loteDestino">
        <g:message code="bajaDeRecepcionDeEstano.loteDestino.label" default="Lote Destino" />

    </label>
    <g:textField name="loteDestino" value="${bajaDeRecepcionDeEstanoInstance?.loteDestino}"/>
</div>

<g:hiddenField name="recepcionDestinoId" value="${bajaDeRecepcionDeEstanoInstance.recepcionDestinoId}"/>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'gastoPorChanqueo', 'error')} ">
    <label for="gastoPorChanqueo">
        <g:message code="bajaDeRecepcionDeEstano.gastoPorChanqueo.label" default="Gasto Por Chanqueo" />

    </label>
    <g:field name="gastoPorChanqueo" value="${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: 'gastoPorChanqueo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'gastoPorManipuleo', 'error')} ">
    <label for="gastoPorManipuleo">
        <g:message code="bajaDeRecepcionDeEstano.gastoPorManipuleo.label" default="Gasto Por Manipuleo" />

    </label>
    <g:field name="gastoPorManipuleo" value="${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: 'gastoPorManipuleo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'gastoPorAnalisis', 'error')} ">
    <label for="gastoPorAnalisis">
        <g:message code="bajaDeRecepcionDeEstano.gastoPorAnalisis.label" default="Gasto Por Analisis" />

    </label>
    <g:field name="gastoPorAnalisis" value="${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: 'gastoPorAnalisis')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'gastoPorAnticipo', 'error')} ">
    <label for="gastoPorAnticipo">
        <g:message code="bajaDeRecepcionDeEstano.gastoPorAnticipo.label" default="Gasto Por Anticipo" />

    </label>
    <g:field name="gastoPorAnticipo" value="${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: 'gastoPorAnticipo')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'gastoPorTransporte', 'error')} ">
    <label for="gastoPorTransporte">
        <g:message code="bajaDeRecepcionDeEstano.gastoPorTransporte.label" default="Gasto Por Transporte" />

    </label>
    <g:field name="gastoPorTransporte" value="${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: 'gastoPorTransporte')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'otrosGastos', 'error')} ">
    <label for="otrosGastos">
        <g:message code="bajaDeRecepcionDeEstano.otrosGastos.label" default="Otros Gastos" />

    </label>
    <g:field name="otrosGastos" value="${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: 'otrosGastos')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'totalDeGastos', 'error')} required">
    <label for="totalDeGastos">
        <g:message code="bajaDeRecepcionDeEstano.totalDeGastos.label" default="Total De Gastos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalDeGastos" value="${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: 'totalDeGastos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeEstanoInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="bajaDeRecepcionDeEstano.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${bajaDeRecepcionDeEstanoInstance?.observaciones}" size="90"/>
</div>
