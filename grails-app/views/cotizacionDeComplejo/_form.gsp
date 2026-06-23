<%@ page import="org.socymet.cotizacionParaCliente.CotizacionDeComplejo" %>

<g:hiddenField name="numeroCotizacionComplejo" value="${cotizacionDeComplejoInstance.numeroCotizacionComplejo}" required=""/>

<h1 style="font-weight: bold">Informacion General</h1>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'nombreSolicitante', 'error')} required">
    <label for="nombreSolicitante">
        <g:message code="cotizacionDeComplejo.nombreSolicitante.label" default="Nombre Solicitante" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreSolicitante" required="" value="${cotizacionDeComplejoInstance?.nombreSolicitante}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'empresaSolicitante', 'error')} required">
    <label for="empresaSolicitante">
        <g:message code="cotizacionDeComplejo.empresaSolicitante.label" default="Empresa Solicitante" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="empresaSolicitante" required="" value="${cotizacionDeComplejoInstance?.empresaSolicitante}" size="50"/>
</div>

<h1 style="font-weight: bold">Valoracion</h1>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'fechaDeCotizacion', 'error')} required">
    <label for="fechaDeCotizacion">
        <g:message code="cotizacionDeComplejo.fechaDeCotizacion.label" default="Fecha De Cotizacion" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeCotizacion" precision="day"  value="${cotizacionDeComplejoInstance?.fechaDeCotizacion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'cotizacionDiaria', 'error')} required">
    <label for="cotizacionDiaria">
        <g:message code="cotizacionDeComplejo.cotizacionDiaria.label" default="Cotizacion Diaria" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="cotizacionDiaria" name="cotizacionDiaria.id" from="${org.socymet.cotizaciones.CotizacionDiariaDeMinerales.list()}" optionKey="id" required="" value="${cotizacionDeComplejoInstance?.cotizacionDiaria?.id}" class="many-to-one" noSelection="['0': 'NO EXISTE COTIZACION']" disabled="false"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'leyZinc', 'error')} required">
    <label for="leyZinc">
        <g:message code="cotizacionDeComplejo.leyZinc.label" default="Ley Zinc" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="leyZinc" value="${fieldValue(bean: cotizacionDeComplejoInstance, field: 'leyZinc')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'leyPlomo', 'error')} required">
    <label for="leyPlomo">
        <g:message code="cotizacionDeComplejo.leyPlomo.label" default="Ley Plomo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="leyPlomo" value="${fieldValue(bean: cotizacionDeComplejoInstance, field: 'leyPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'leyPlata', 'error')} required">
    <label for="leyPlata">
        <g:message code="cotizacionDeComplejo.leyPlata.label" default="Ley Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="leyPlata" value="${fieldValue(bean: cotizacionDeComplejoInstance, field: 'leyPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'modoValoracion', 'error')} ">
    <label for="modoValoracion">
        <g:message code="cotizacionDeComplejo.modoValoracion.label" default="Modo Valoracion" />

    </label>
    <g:select name="modoValoracion" from="${['TABLA','TERMINOS DE CONTRATO']}" value="${cotizacionDeComplejoInstance?.modoValoracion}" valueMessagePrefix="cotizacionDeComplejo.modoValoracion" noSelection="['': '']"/>
</div>

<div id="_tablaComplejo" class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'tablaComplejo', 'error')} required" style="display: none">
    <label for="tablaComplejo">
        <g:message code="cotizacionDeComplejo.tablaComplejo.label" default="Tabla Complejo" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="tablaComplejo" name="tablaComplejo.id" from="${org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo.list()}" optionKey="id" required="" value="${cotizacionDeComplejoInstance?.tablaComplejo?.id}" class="many-to-one"/>
</div>

<div id="_terminosDeContrato" class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'terminosDeContrato', 'error')} required" style="display: none">
    <label for="terminosDeContrato">
        <g:message code="cotizacionDeComplejo.terminosDeContrato.label" default="Terminos De Contrato" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="terminosDeContrato" name="terminosDeContrato.id" from="${org.socymet.cotizaciones.TerminosDeContrato.list()}" optionKey="id" required="" value="${cotizacionDeComplejoInstance?.terminosDeContrato?.id}" class="many-to-one"/>
</div>

<br>

<div style="text-align: center;">
    <input id="valorar" type="button" value="VALORAR LOTE" style="background-color: #255b17; color: white; font-size: 16px;"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'valorTonelada', 'error')} required">
    <label for="valorTonelada">
        <g:message code="cotizacionDeComplejo.valorTonelada.label" default="Valor Tonelada" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorTonelada" value="${fieldValue(bean: cotizacionDeComplejoInstance, field: 'valorTonelada')}" required="" class="verde" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'pesoBruto', 'error')} required">
    <label for="pesoBruto">
        <g:message code="cotizacionDeComplejo.pesoBruto.label" default="Peso Bruto Kgs" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="pesoBruto" value="${fieldValue(bean: cotizacionDeComplejoInstance, field: 'pesoBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'valorEstimado', 'error')} required">
    <label for="valorEstimado">
        <g:message code="cotizacionDeComplejo.valorEstimado.label" default="Valor Estimado" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="valorEstimado" value="${fieldValue(bean: cotizacionDeComplejoInstance, field: 'valorEstimado')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cotizacionDeComplejoInstance, field: 'usuario', 'error')} required" style="display: none">
    <label for="usuario">
        <g:message code="cotizacionDeComplejo.usuario.label" default="Usuario" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="usuario" name="usuario.id" from="${org.socymet.seguridad.SecUser.list()}" optionKey="id" required="" value="${cotizacionDeComplejoInstance?.usuario?.id}" class="many-to-one"/>
</div>