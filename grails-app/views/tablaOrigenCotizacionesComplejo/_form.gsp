<%@ page import="org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo" %>



<div class="fieldcontain ${hasErrors(bean: tablaOrigenCotizacionesComplejoInstance, field: 'nombreTabla', 'error')} required">
    <label for="nombreTabla">
        <g:message code="tablaOrigenCotizacionesComplejo.nombreTabla.label" default="Nombre Tabla" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreTabla" required="" value="${tablaOrigenCotizacionesComplejoInstance?.nombreTabla}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaOrigenCotizacionesComplejoInstance, field: 'empresa', 'error')} " style="display: none">
    <label for="empresa">
        <g:message code="tablaOrigenCotizacionesComplejo.empresa.label" default="Empresa" />

    </label>
    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${tablaOrigenCotizacionesComplejoInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div id="radio2" style="height: 30px; display: none;">
    <div class="fieldcontain">
        <label style="float: left; width: 25%; overflow: hidden">
            <g:message code="tablaOrigenCotizacionesComplejo.naturalezaMineral.label" default="Naturaleza Mineral" />

        </label>
    </div>

    <div class="ui-button" style="float: left; width: 50%; overflow: hidden; text-align: start">
        <input type="radio" id="radioSulfuro" value="sulfuro" name="radio2" checked="checked"><label for="radioSulfuro">SULFURO</label>
        <input type="radio" id="radioOxido" value="oxido" name="radio2"><label for="radioOxido">OXIDO</label>
    </div>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaOrigenCotizacionesComplejoInstance, field: 'naturalezaMineral', 'error')} required" style="display: none">
    <label for="naturalezaMineral">
        <g:message code="tablaOrigenCotizacionesComplejo.naturalezaMineral.label" default="Naturaleza Mineral" />
        <span class="required-indicator">*</span>
    </label>
    <g:select name="naturalezaMineral" from="${['SULFURO','OXIDO']}" required="" value="${tablaOrigenCotizacionesComplejoInstance?.naturalezaMineral}" valueMessagePrefix="tablaOrigenCotizacionesComplejo.naturalezaMineral"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaOrigenCotizacionesComplejoInstance, field: 'datosArchivo', 'error')} ">
    <label for="datosArchivo">
        <g:message code="tablaOrigenCotizacionesComplejo.datosArchivo.label" default="Datos Archivo" />

    </label>
    <input type="file" id="datosArchivo" name="datosArchivo" />
</div>

