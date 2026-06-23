<%@ page import="org.socymet.recepcion.BajaDeRecepcionDeAntimonio" %>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="bajaDeRecepcionDeAntimonio.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${bajaDeRecepcionDeAntimonioInstance?.lote}"/>
    <g:hiddenField name="recepcionId" value="${bajaDeRecepcionDeAntimonioInstance.recepcionId}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="bajaDeRecepcionDeAntimonio.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${bajaDeRecepcionDeAntimonioInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="bajaDeRecepcionDeAntimonio.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${bajaDeRecepcionDeAntimonioInstance?.nombreEmpresa}"class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="bajaDeRecepcionDeAntimonio.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${bajaDeRecepcionDeAntimonioInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="bajaDeRecepcionDeAntimonio.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: bajaDeRecepcionDeAntimonioInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'fechaDeBaja', 'error')} required">
    <label for="fechaDeBaja">
        <g:message code="bajaDeRecepcionDeAntimonio.fechaDeBaja.label" default="Fecha De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeBaja" precision="day"  value="${bajaDeRecepcionDeAntimonioInstance?.fechaDeBaja}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'motivoDeBaja', 'error')} required">
    <label for="motivoDeBaja">
        <g:message code="bajaDeRecepcionDeAntimonio.motivoDeBaja.label" default="Motivo De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="motivoDeBaja" from="${['Ley baja','Proveedor desconforme por su ley','Proveedor desconforme con el precio del mineral']}" required="" value="${bajaDeRecepcionDeAntimonioInstance?.motivoDeBaja}" valueMessagePrefix="bajaDeRecepcionDeAntimonio.motivoDeBaja"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'tipoDeBaja', 'error')} required">
    <label for="tipoDeBaja">
        <g:message code="bajaDeRecepcionDeAntimonio.tipoDeBaja.label" default="Tipo De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="tipoDeBaja" from="${['Baja por retiro', 'Baja por transferencia']}" required="" value="${bajaDeRecepcionDeAntimonioInstance?.tipoDeBaja}" valueMessagePrefix="bajaDeRecepcionDeAntimonio.tipoDeBaja"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'loteDestino', 'error')} ">
    <label for="loteDestino">
        <g:message code="bajaDeRecepcionDeAntimonio.loteDestino.label" default="Lote Destino" />

    </label>
    <g:textField name="loteDestino" value="${bajaDeRecepcionDeAntimonioInstance?.loteDestino}"/>
</div>

<g:hiddenField name="recepcionDestinoId" value="${bajaDeRecepcionDeAntimonioInstance.recepcionDestinoId}"/>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'gastoPorChanqueo', 'error')} ">
    <label for="gastoPorChanqueo">
        <g:message code="bajaDeRecepcionDeAntimonio.gastoPorChanqueo.label" default="Gasto Por Chanqueo" />

    </label>
    <g:field name="gastoPorChanqueo" value="${fieldValue(bean: bajaDeRecepcionDeAntimonioInstance, field: 'gastoPorChanqueo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'gastoPorManipuleo', 'error')} ">
    <label for="gastoPorManipuleo">
        <g:message code="bajaDeRecepcionDeAntimonio.gastoPorManipuleo.label" default="Gasto Por Manipuleo" />

    </label>
    <g:field name="gastoPorManipuleo" value="${fieldValue(bean: bajaDeRecepcionDeAntimonioInstance, field: 'gastoPorManipuleo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'gastoPorAnalisis', 'error')} ">
    <label for="gastoPorAnalisis">
        <g:message code="bajaDeRecepcionDeAntimonio.gastoPorAnalisis.label" default="Gasto Por Analisis" />

    </label>
    <g:field name="gastoPorAnalisis" value="${fieldValue(bean: bajaDeRecepcionDeAntimonioInstance, field: 'gastoPorAnalisis')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'gastoPorAnticipo', 'error')} ">
    <label for="gastoPorAnticipo">
        <g:message code="bajaDeRecepcionDeAntimonio.gastoPorAnticipo.label" default="Gasto Por Anticipo" />

    </label>
    <g:field name="gastoPorAnticipo" value="${fieldValue(bean: bajaDeRecepcionDeAntimonioInstance, field: 'gastoPorAnticipo')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'gastoPorTransporte', 'error')} ">
    <label for="gastoPorTransporte">
        <g:message code="bajaDeRecepcionDeAntimonio.gastoPorTransporte.label" default="Gasto Por Transporte" />

    </label>
    <g:field name="gastoPorTransporte" value="${fieldValue(bean: bajaDeRecepcionDeAntimonioInstance, field: 'gastoPorTransporte')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'otrosGastos', 'error')} ">
    <label for="otrosGastos">
        <g:message code="bajaDeRecepcionDeAntimonio.otrosGastos.label" default="Otros Gastos" />

    </label>
    <g:field name="otrosGastos" value="${fieldValue(bean: bajaDeRecepcionDeAntimonioInstance, field: 'otrosGastos')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'totalDeGastos', 'error')} required">
    <label for="totalDeGastos">
        <g:message code="bajaDeRecepcionDeAntimonio.totalDeGastos.label" default="Total De Gastos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalDeGastos" value="${fieldValue(bean: bajaDeRecepcionDeAntimonioInstance, field: 'totalDeGastos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeAntimonioInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="bajaDeRecepcionDeAntimonio.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${bajaDeRecepcionDeAntimonioInstance?.observaciones}" size="90"/>
</div>
