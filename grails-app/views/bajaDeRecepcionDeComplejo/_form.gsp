<%@ page import="org.socymet.recepcion.BajaDeRecepcionDeComplejo" %>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'lote', 'error')} required">
    <label for="lote">
        <g:message code="bajaDeRecepcionDeComplejo.lote.label" default="Lote" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="lote" required="" value="${bajaDeRecepcionDeComplejoInstance?.lote}"/>
    <g:hiddenField name="recepcionId" value="${bajaDeRecepcionDeComplejoInstance.recepcionId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'nombreCliente', 'error')} required">
    <label for="nombreCliente">
        <g:message code="bajaDeRecepcionDeComplejo.nombreCliente.label" default="Nombre Cliente" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreCliente" required="" value="${bajaDeRecepcionDeComplejoInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'nombreEmpresa', 'error')} required">
    <label for="nombreEmpresa">
        <g:message code="bajaDeRecepcionDeComplejo.nombreEmpresa.label" default="Nombre Empresa" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreEmpresa" required="" value="${bajaDeRecepcionDeComplejoInstance?.nombreEmpresa}"class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'fechaDeRecepcion', 'error')} required">
    <label for="fechaDeRecepcion">
        <g:message code="bajaDeRecepcionDeComplejo.fechaDeRecepcion.label" default="Fecha De Recepcion" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="fechaDeRecepcion" required="" value="${bajaDeRecepcionDeComplejoInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="bajaDeRecepcionDeComplejo.pesoBruto.label" default="Peso Bruto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'fechaDeBaja', 'error')} required">
    <label for="fechaDeBaja">
        <g:message code="bajaDeRecepcionDeComplejo.fechaDeBaja.label" default="Fecha De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeBaja" precision="day"  value="${bajaDeRecepcionDeComplejoInstance?.fechaDeBaja}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'motivoDeBaja', 'error')} required">
    <label for="motivoDeBaja">
        <g:message code="bajaDeRecepcionDeComplejo.motivoDeBaja.label" default="Motivo De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="motivoDeBaja" from="${['Ley baja','Proveedor desconforme por su ley','Proveedor desconforme con el precio del mineral']}" required="" value="${bajaDeRecepcionDeComplejoInstance?.motivoDeBaja}" valueMessagePrefix="bajaDeRecepcionDeComplejo.motivoDeBaja"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'tipoDeBaja', 'error')} required">
    <label for="tipoDeBaja">
        <g:message code="bajaDeRecepcionDeComplejo.tipoDeBaja.label" default="Tipo De Baja" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="tipoDeBaja" from="${['Baja por retiro', 'Baja por transferencia']}" required="" value="${bajaDeRecepcionDeComplejoInstance?.tipoDeBaja}" valueMessagePrefix="bajaDeRecepcionDeComplejo.tipoDeBaja"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'loteDestino', 'error')} ">
	<label for="loteDestino">
		<g:message code="bajaDeRecepcionDeComplejo.loteDestino.label" default="Lote Destino" />
		
	</label>
	<g:textField name="loteDestino" value="${bajaDeRecepcionDeComplejoInstance?.loteDestino}"/>
</div>

<g:hiddenField name="recepcionDestinoId" value="${bajaDeRecepcionDeComplejoInstance.recepcionDestinoId}"/>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'gastoPorChanqueo', 'error')} ">
    <label for="gastoPorChanqueo">
        <g:message code="bajaDeRecepcionDeComplejo.gastoPorChanqueo.label" default="Gasto Por Chanqueo" />

    </label>
    <g:field name="gastoPorChanqueo" value="${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: 'gastoPorChanqueo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'gastoPorManipuleo', 'error')} ">
    <label for="gastoPorManipuleo">
        <g:message code="bajaDeRecepcionDeComplejo.gastoPorManipuleo.label" default="Gasto Por Manipuleo" />

    </label>
    <g:field name="gastoPorManipuleo" value="${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: 'gastoPorManipuleo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'gastoPorAnalisis', 'error')} ">
    <label for="gastoPorAnalisis">
        <g:message code="bajaDeRecepcionDeComplejo.gastoPorAnalisis.label" default="Gasto Por Analisis" />

    </label>
    <g:field name="gastoPorAnalisis" value="${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: 'gastoPorAnalisis')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'gastoPorAnticipo', 'error')} ">
    <label for="gastoPorAnticipo">
        <g:message code="bajaDeRecepcionDeComplejo.gastoPorAnticipo.label" default="Gasto Por Anticipo" />

    </label>
    <g:field name="gastoPorAnticipo" value="${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: 'gastoPorAnticipo')}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'gastoPorTransporte', 'error')} ">
    <label for="gastoPorTransporte">
        <g:message code="bajaDeRecepcionDeComplejo.gastoPorTransporte.label" default="Gasto Por Transporte" />

    </label>
    <g:field name="gastoPorTransporte" value="${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: 'gastoPorTransporte')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'otrosGastos', 'error')} ">
    <label for="otrosGastos">
        <g:message code="bajaDeRecepcionDeComplejo.otrosGastos.label" default="Otros Gastos" />

    </label>
    <g:field name="otrosGastos" value="${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: 'otrosGastos')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'totalDeGastos', 'error')} required">
    <label for="totalDeGastos">
        <g:message code="bajaDeRecepcionDeComplejo.totalDeGastos.label" default="Total De Gastos" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="totalDeGastos" value="${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: 'totalDeGastos')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bajaDeRecepcionDeComplejoInstance, field: 'observaciones', 'error')} ">
    <label for="observaciones">
        <g:message code="bajaDeRecepcionDeComplejo.observaciones.label" default="Observaciones" />

    </label>
    <g:textField name="observaciones" value="${bajaDeRecepcionDeComplejoInstance?.observaciones}"/>
</div>
