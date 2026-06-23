<%@ page import="org.socymet.cotizacionParaCliente.CotizacionDeCobrePlata" %>

<g:hiddenField name="numeroCotizacionCobrePlata" value="${cotizacionDeCobrePlataInstance.numeroCotizacionCobrePlata}" required=""/>

<h1 style="font-weight: bold">Informacion General</h1>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'nombreSolicitante', 'error')} required">
	<label for="nombreSolicitante">
		<g:message code="cotizacionDeCobrePlata.nombreSolicitante.label" default="Nombre Solicitante" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreSolicitante" required="" value="${cotizacionDeCobrePlataInstance?.nombreSolicitante}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'empresaSolicitante', 'error')} required">
	<label for="empresaSolicitante">
		<g:message code="cotizacionDeCobrePlata.empresaSolicitante.label" default="Empresa Solicitante" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="empresaSolicitante" required="" value="${cotizacionDeCobrePlataInstance?.empresaSolicitante}" size="50"/>
</div>

<h1 style="font-weight: bold">Valoracion</h1>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'fechaDeCotizacion', 'error')} required">
	<label for="fechaDeCotizacion">
		<g:message code="cotizacionDeCobrePlata.fechaDeCotizacion.label" default="Fecha De Cotizacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeCotizacion" precision="day"  value="${cotizacionDeCobrePlataInstance?.fechaDeCotizacion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'cotizacionDiaria', 'error')} required">
	<label for="cotizacionDiaria">
		<g:message code="cotizacionDeCobrePlata.cotizacionDiaria.label" default="Cotizacion Diaria" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="cotizacionDiaria" name="cotizacionDiaria.id" from="${org.socymet.cotizaciones.CotizacionDiariaDeMinerales.list()}" optionKey="id" required="" value="${cotizacionDeCobrePlataInstance?.cotizacionDiaria?.id}" class="many-to-one" noSelection="['0': 'NO EXISTE COTIZACION']" disabled="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'leyCobre', 'error')} required">
	<label for="leyCobre">
		<g:message code="cotizacionDeCobrePlata.leyCobre.label" default="Ley Cobre" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyCobre" value="${fieldValue(bean: cotizacionDeCobrePlataInstance, field: 'leyCobre')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'leyPlata', 'error')} required">
	<label for="leyPlata">
		<g:message code="cotizacionDeCobrePlata.leyPlata.label" default="Ley Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyPlata" value="${fieldValue(bean: cotizacionDeCobrePlataInstance, field: 'leyPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'modoValoracion', 'error')} ">
	<label for="modoValoracion">
		<g:message code="cotizacionDeCobrePlata.modoValoracion.label" default="Modo Valoracion" />
		
	</label>
	<g:select name="modoValoracion" from="${['TABLA','TERMINOS DE CONTRATO']}" value="${cotizacionDeCobrePlataInstance?.modoValoracion}" valueMessagePrefix="cotizacionDeCobrePlata.modoValoracion" noSelection="['': '']"/>
</div>

<div id="_tablaCobrePlata" class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'tablaCobrePlata', 'error')} required" style="display: none">
	<label for="tablaCobrePlata">
		<g:message code="cotizacionDeCobrePlata.tablaCobrePlata.label" default="Tabla Cobre Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="tablaCobrePlata" name="tablaCobrePlata.id" from="${org.socymet.cotizaciones.TablaPreciosCobre.list()}" optionKey="id" required="" value="${cotizacionDeCobrePlataInstance?.tablaCobrePlata?.id}" class="many-to-one"/>
</div>

<div id="_terminosDeContrato" class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'terminosDeContrato', 'error')} required" style="display: none">
	<label for="terminosDeContrato">
		<g:message code="cotizacionDeCobrePlata.terminosDeContrato.label" default="Terminos De Contrato" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="terminosDeContrato" name="terminosDeContrato.id" from="${org.socymet.cotizaciones.TerminosDeContrato.list()}" optionKey="id" required="" value="${cotizacionDeCobrePlataInstance?.terminosDeContrato?.id}" class="many-to-one"/>
</div>

<br>

<div style="text-align: center;">
    <input id="valorar" type="button" value="VALORAR LOTE" style="background-color: #255b17; color: white; font-size: 16px;"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'valorTonelada', 'error')} required">
	<label for="valorTonelada">
		<g:message code="cotizacionDeCobrePlata.valorTonelada.label" default="Valor Tonelada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorTonelada" value="${fieldValue(bean: cotizacionDeCobrePlataInstance, field: 'valorTonelada')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="cotizacionDeCobrePlata.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: cotizacionDeCobrePlataInstance, field: 'pesoBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'valorEstimado', 'error')} required">
	<label for="valorEstimado">
		<g:message code="cotizacionDeCobrePlata.valorEstimado.label" default="Valor Estimado" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorEstimado" value="${fieldValue(bean: cotizacionDeCobrePlataInstance, field: 'valorEstimado')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeCobrePlataInstance, field: 'usuario', 'error')} required"  style="display: none">
	<label for="usuario">
		<g:message code="cotizacionDeCobrePlata.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${org.socymet.seguridad.SecUser.list()}" optionKey="id" required="" value="${cotizacionDeCobrePlataInstance?.usuario?.id}" class="many-to-one"/>
</div>

