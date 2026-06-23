<%@ page import="org.socymet.recepcion.RecepcionGrupalDeComplejo" %>

<div class="fieldcontain ${hasErrors(bean: recepcionGrupalDeComplejoInstance, field: 'cliente', 'error')} required">
    <label>
        CI de Cliente
    </label>
    <g:textField name="ciCliente" value="${recepcionGrupalDeComplejoInstance?.cliente?.ci}"/><g:link controller="cliente" action="create" target="_blank">Crear Cliente</g:link>
    <g:hiddenField name="cliente.id" value="${recepcionGrupalDeComplejoInstance?.cliente?.id}"/>
    <g:hiddenField name="empresa.id" value="${recepcionGrupalDeComplejoInstance?.empresa?.id}"/>
    <g:hiddenField name="costoTransporteComplejos" />
    <g:hiddenField name="unidadMonetariaComplejos" />
    <g:hiddenField name="unidadDeCobroComplejos" />
    <g:hiddenField name="costoTransporteConcentrados" />
    <g:hiddenField name="unidadMonetariaConcentrados" />
    <g:hiddenField name="unidadDeCobroConcentrados" />
    <g:hiddenField name="tipoDeCambioComercial" />
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionGrupalDeComplejoInstance, field: 'cliente', 'error')} required">
    <label>
        Nombre de Cliente
    </label>
    <g:textField name="nombreCliente" value="${recepcionGrupalDeComplejoInstance?.cliente?.nombre}" size="50" readonly="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionGrupalDeComplejoInstance, field: 'cliente', 'error')} required">
    <label>
        Empresa
    </label>
    <g:textField name="nombreEmpresa" value="${recepcionGrupalDeComplejoInstance?.empresa?.nombreDeEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionGrupalDeComplejoInstance, field: 'chofer', 'error')} required">
    <label>
        CI de Chofer
    </label>
    <g:textField name="ciChofer" value="${recepcionGrupalDeComplejoInstance?.chofer?.ci}"/><g:link controller="chofer" action="create" target="_blank">Crear Chofer</g:link>
    <g:hiddenField name="chofer.id" value="${recepcionGrupalDeComplejoInstance?.chofer?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionGrupalDeComplejoInstance, field: 'cliente', 'error')} required">
    <label>
        Nombre de Chofer
    </label>
    <g:textField name="nombreChofer" value="${recepcionGrupalDeComplejoInstance?.chofer?.nombre}" size="50" readonly="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionGrupalDeComplejoInstance, field: 'automovil', 'error')} required">
    <label>
        Placa de Automovil
    </label>
    <g:textField name="placa" value="${recepcionGrupalDeComplejoInstance?.automovil?.placa}"/><g:link controller="automovil" action="create" target="_blank">Crear Automovil</g:link>
    <g:hiddenField name="automovil.id" value="${recepcionGrupalDeComplejoInstance?.automovil?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionGrupalDeComplejoInstance, field: 'automovil', 'error')} required">
    <label>
        Modelo
    </label>
    <g:textField name="modelo" value="${recepcionGrupalDeComplejoInstance?.automovil?.modelo}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionGrupalDeComplejoInstance, field: 'automovil', 'error')} required">
    <label>
        Color
    </label>
    <g:textField name="color" value="${recepcionGrupalDeComplejoInstance?.automovil?.color}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionGrupalDeComplejoInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="recepcionGrupalDeComplejo.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeRecepcion" precision="day"  value="${recepcionGrupalDeComplejoInstance?.fechaDeRecepcion}"  />
</div>

<h1 style="font-weight: bold">Grupo de Lotes</h1>

<div class="fieldcontain ${hasErrors(bean: recepcionGrupalDeComplejoInstance, field: 'deposito', 'error')} required">
	<label for="deposito">
		<g:message code="recepcionGrupalDeComplejo.deposito.label" default="Deposito" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${recepcionGrupalDeComplejoInstance?.deposito?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recepcionGrupalDeComplejoInstance, field: 'tipoDeMineral', 'error')} ">
	<label for="tipoDeMineral">
		<g:message code="recepcionGrupalDeComplejo.tipoDeMineral.label" default="Tipo De Mineral" />
		
	</label>
	<g:select name="tipoDeMineral" from="${['COMPLEJO','PB-AG','ZN-AG','CU-AG']}" value="${recepcionGrupalDeComplejoInstance?.tipoDeMineral}" valueMessagePrefix="recepcionGrupalDeComplejo.tipoDeMineral" noSelection="['': '']"/>
</div>

<div class="fieldcontain">
    <label for="loteInicial">Lote Inicial</label>
    <input type="text" id="loteInicial"/>
</div>

<div class="fieldcontain">
    <label for="loteFinal">Lote Final</label>
    <input type="text" id="loteFinal"/>
</div>

<div class="fieldcontain">
    <label for="lotes">Lotes</label>
    <g:textArea name="lotes" value="${recepcionGrupalDeComplejoInstance?.lotes}"/>
</div>

<div id="_botones" style="text-align: center">
    <br>
    <button id="generar" type="button">GENERAR LOTES</button>
</div>

<div id="tabla" style="width: 600px; margin-left: auto; margin-right: auto;">
    <table id="lotesGenerados"></table>
</div>

<div id="_guardar" style="text-align: center">
    <br>
    <button id="validar" type="button">VALIDAR LOTES</button>
    <button id="guardar" type="button">GUARDAR LOTES</button>
</div>



