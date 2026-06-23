<%@ page import="org.socymet.cotizaciones.TablaCotizacionEstano" %>



<div class="fieldcontain ${hasErrors(bean: tablaCotizacionEstanoInstance, field: 'nombreDeTabla', 'error')} required">
	<label for="nombreDeTabla">
		<g:message code="tablaCotizacionEstano.nombreDeTabla.label" default="Nombre De Tabla" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreDeTabla" required="" value="${tablaCotizacionEstanoInstance?.nombreDeTabla}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaCotizacionEstanoInstance, field: 'cotizacionInicial', 'error')} required">
	<label for="cotizacionInicial">
		<g:message code="tablaCotizacionEstano.cotizacionInicial.label" default="Cotizacion Inicial" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cotizacionInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'cotizacionInicial')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: tablaCotizacionEstanoInstance, field: 'cotizacionFinal', 'error')} required">
	<label for="cotizacionFinal">
		<g:message code="tablaCotizacionEstano.cotizacionFinal.label" default="Cotizacion Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cotizacionFinal" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'cotizacionFinal')}" required="" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Leyes y Costos por Tonelada [$us/Ton]</h1>

<table class="center" style="width: 700px;">
    <tbody>
    <tr>
        <td>
            &nbsp;</td>
        <td class="fieldcontain">
            <label style="width: 80%">
                <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 5%" />
            </label>
        </td>
        <td class="fieldcontain">
            <label style="width: 80%">
                <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 10%" />
            </label>
        </td>
        <td class="fieldcontain">
            <label style="width: 80%">
                <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 15%" />
            </label>
        </td>
        <td class="fieldcontain">
            <label style="width: 80%">
                <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 20%" />
            </label>
        </td>
        <td class="fieldcontain">
            <label style="width: 80%">
                <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 25%" />
            </label>
        </td>
        <td class="fieldcontain">
            <label style="width: 80%">
                <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 30%" />
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
            <g:field name="ley5valorIncrementable" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley5valorIncrementable')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley10valorIncrementable" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley10valorIncrementable')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley15valorIncrementable" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley15valorIncrementable')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley20valorIncrementable" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley20valorIncrementable')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley25valorIncrementable" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley25valorIncrementable')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley30valorIncrementable" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley30valorIncrementable')}" required="" size="8" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain">
            <label style="width: 95%">
                <g:message code="tablaCotizacionEstano.valorInicial.label" default="Valor Inicial" />
                <span class="required-indicator">*</span>
            </label>
        </td>
        <td>
            <g:field name="ley5valorInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley5valorInicial')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley10valorInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley10valorInicial')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley15valorInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley15valorInicial')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley20valorInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley20valorInicial')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley25valorInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley25valorInicial')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley30valorInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley30valorInicial')}" required="" size="8" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td>
            &nbsp;</td>
        <td class="fieldcontain">
            <label style="width: 80%">
                <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 35%" />
            </label>
        </td>
        <td class="fieldcontain">
            <label style="width: 80%">
                <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 40%" />
            </label>
        </td>
        <td class="fieldcontain">
            <label style="width: 80%">
                <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 50%" />
            </label>
        </td>
        <td class="fieldcontain">
            <label style="width: 80%">
                <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 60%" />
            </label>
        </td>
        <td class="fieldcontain">
            <label style="width: 80%">
                <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 70%" />
            </label>
        </td>
        <td class="fieldcontain">
            <label style="width: 80%">
                <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 75%" />
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
            <g:field name="ley35valorIncrementable" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley35valorIncrementable')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley40valorIncrementable" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley40valorIncrementable')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley50valorIncrementable" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley50valorIncrementable')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley60valorIncrementable" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley60valorIncrementable')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley70valorIncrementable" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley70valorIncrementable')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley75valorIncrementable" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley75valorIncrementable')}" required="" size="8" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain">
            <label style="width: 95%">
                <g:message code="tablaCotizacionEstano.valorInicial.label" default="Valor Inicial" />
                <span class="required-indicator">*</span>
            </label>
        </td>
        <td>
            <g:field name="ley35valorInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley35valorInicial')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley40valorInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley40valorInicial')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley50valorInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley50valorInicial')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley60valorInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley60valorInicial')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley70valorInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley70valorInicial')}" required="" size="8" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="ley75valorInicial" value="${fieldValue(bean: tablaCotizacionEstanoInstance, field: 'ley75valorInicial')}" required="" size="8" inputmode="decimal"/>
        </td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Tabla de Cotizaciones</h1>
<g:hiddenField name="tablaDeCotizaciones" value="${tablaCotizacionEstanoInstance?.tablaDeCotizaciones}"/>

<div style="text-align: center;">
<button type="button" id="generar" style="margin-left: auto; margin-right: auto;">GENERAR</button>
</div>
<div style="width: 900px; margin-left: auto; margin-right: auto;"><table id="list4"></table></div>


