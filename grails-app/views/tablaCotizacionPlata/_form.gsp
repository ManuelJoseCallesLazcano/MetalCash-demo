<%@ page import="org.socymet.cotizaciones.TablaCotizacionPlata" %>



<div class="fieldcontain ${hasErrors(bean: tablaCotizacionPlataInstance, field: 'nombreDeTabla', 'error')} required">
    <label for="nombreDeTabla">
        <g:message code="tablaCotizacionPlata.nombreDeTabla.label" default="Nombre De Tabla" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreDeTabla" required="" value="${tablaCotizacionPlataInstance?.nombreDeTabla}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaCotizacionPlataInstance, field: 'cotizacionInicial', 'error')} required">
    <label for="cotizacionInicial">
        <g:message code="tablaCotizacionPlata.cotizacionInicial.label" default="Cotizacion Inicial" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="cotizacionInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'cotizacionInicial')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaCotizacionPlataInstance, field: 'cotizacionFinal', 'error')} required">
    <label for="cotizacionFinal">
        <g:message code="tablaCotizacionPlata.cotizacionFinal.label" default="Cotizacion Final" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="cotizacionFinal" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'cotizacionFinal')}" required="" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Leyes y Costos por Tonelada [$us/Ton]</h1>

<table class="center" style="width: 700px;">
<tbody>
<tr>
    <td>
        &nbsp;</td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 10 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 20 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 30 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 40 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 50 DM" />
        </label>
    </td>
</tr>
<tr>
    <td class="fieldcontain">
        <label style="width: 95%">
            <g:message code="tablaCotizacionEstano.valorIncrementable.label" default="Valor Incrementable" />
            <span class="required-indicator">*</span>
        </label>
    </td>
    <td>
        <g:field name="ley10valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley10valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley20valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley20valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley30valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley30valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley40valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley40valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley50valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley50valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
</tr>
<tr>
    <td class="fieldcontain">
        <label style="width: 95%">
            <g:message code="tablaCotizacionPlata.valorInicial.label" default="Valor Inicial" />
            <span class="required-indicator">*</span>
        </label>
    </td>
    <td>
        <g:field name="ley10valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley10valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley20valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley20valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley30valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley30valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley40valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley40valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley50valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley50valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
</tr>
<tr>
    <td>
        &nbsp;</td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 60 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 70 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 80 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 90 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 100 DM" />
        </label>
    </td>
</tr>
<tr>
    <td class="fieldcontain">
        <label style="width: 95%">
            <g:message code="tablaCotizacionPlata.valorIncrementable.label" default="Valor Incrementable" />
            <span class="required-indicator">*</span>
        </label>
    </td>
    <td>
        <g:field name="ley60valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley60valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley70valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley70valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley80valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley80valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley90valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley90valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley100valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley100valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
</tr>
<tr>
    <td class="fieldcontain">
        <label style="width: 95%">
            <g:message code="tablaCotizacionPlata.valorInicial.label" default="Valor Inicial" />
            <span class="required-indicator">*</span>
        </label>
    </td>
    <td>
        <g:field name="ley60valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley60valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley70valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley70valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley80valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley80valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley90valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley90valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley100valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley100valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
</tr>
<tr>
    <td>
        &nbsp;</td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 150 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 200 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 300 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 400 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 500 DM" />
        </label>
    </td>
</tr>
<tr>
    <td class="fieldcontain">
        <label style="width: 95%">
            <g:message code="tablaCotizacionPlata.valorIncrementable.label" default="Valor Incrementable" />
            <span class="required-indicator">*</span>
        </label>
    </td>
    <td>
        <g:field name="ley150valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley150valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley200valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley200valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley300valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley300valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley400valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley400valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley500valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley500valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
</tr>
<tr>
    <td class="fieldcontain">
        <label style="width: 95%">
            <g:message code="tablaCotizacionPlata.valorInicial.label" default="Valor Inicial" />
            <span class="required-indicator">*</span>
        </label>
    </td>
    <td>
        <g:field name="ley150valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley150valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley200valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley200valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley300valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley300valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley400valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley400valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley500valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley500valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
</tr>
<tr>
    <td>
        &nbsp;</td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 600 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 700 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 800 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 900 DM" />
        </label>
    </td>
    <td class="fieldcontain">
        <label style="width: 80%">
            <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 1000 DM" />
        </label>
    </td>
</tr>
<tr>
    <td class="fieldcontain">
        <label style="width: 95%">
            <g:message code="tablaCotizacionPlata.valorIncrementable.label" default="Valor Incrementable" />
            <span class="required-indicator">*</span>
        </label>
    </td>
    <td>
        <g:field name="ley600valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley600valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley700valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley700valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley800valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley800valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley900valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley900valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley1000valorIncrementable" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley1000valorIncrementable')}" required="" size="8" inputmode="decimal"/>
    </td>
</tr>
<tr>
    <td class="fieldcontain">
        <label style="width: 95%">
            <g:message code="tablaCotizacionPlata.valorInicial.label" default="Valor Inicial" />
            <span class="required-indicator">*</span>
        </label>
    </td>
    <td>
        <g:field name="ley600valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley600valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley700valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley700valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley800valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley800valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley900valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley900valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
    <td>
        <g:field name="ley1000valorInicial" value="${fieldValue(bean: tablaCotizacionPlataInstance, field: 'ley1000valorInicial')}" required="" size="8" inputmode="decimal"/>
    </td>
</tr>
</tbody>
</table>

<h1 style="font-weight: bold">Tabla de Cotizaciones</h1>
<g:hiddenField name="tablaDeCotizaciones" value="${tablaCotizacionPlataInstance?.tablaDeCotizaciones}"/>

<div style="text-align: center;">
    <button type="button" id="generar" style="margin-left: auto; margin-right: auto;">GENERAR</button>
</div>
<div style="width: 900px; margin-left: auto; margin-right: auto;"><table id="list4"></table></div>


