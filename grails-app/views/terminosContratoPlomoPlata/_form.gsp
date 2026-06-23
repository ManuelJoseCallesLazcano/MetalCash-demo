<%@ page import="org.socymet.cotizaciones.TerminosContratoPlomoPlata" %>



<div class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'nombreTerminosContrato', 'error')} required">
	<label for="nombreTerminosContrato">
		<g:message code="terminosContratoPlomoPlata.nombreTerminosContrato.label" default="Nombre Terminos Contrato" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreTerminosContrato" required="" value="${terminosContratoPlomoPlataInstance?.nombreTerminosContrato}" size="70"/>
</div>

<br/>

<table class="center" style="width: 700px;">
    <thead>
    <tr>
        <th scope="col">PLOMO</th>
        <th scope="col">PLATA</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'deduccionUnitariaPlomo', 'error')} required">
            <label for="deduccionUnitariaPlomo" style="width: 55%">
                <g:message code="terminosContratoPlomoPlata.deduccionUnitariaPlomo.label" default="Deduccion Unitaria Plomo" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="deduccionUnitariaPlomo" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'deduccionUnitariaPlomo')}" required="" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'deduccionUnitariaPlata', 'error')} required">
            <label for="deduccionUnitariaPlata" style="width: 55%">
                <g:message code="terminosContratoPlomoPlata.deduccionUnitariaPlata.label" default="Deduccion Unitaria Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="deduccionUnitariaPlata" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'deduccionUnitariaPlata')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'porcentajePagablePlomo', 'error')} required">
            <label for="porcentajePagablePlomo" style="width: 55%">
                <g:message code="terminosContratoPlomoPlata.porcentajePagablePlomo.label" default="Porcentaje Pagable Plomo" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="porcentajePagablePlomo" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'porcentajePagablePlomo')}" required="" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'porcentajePagablePlata', 'error')} required">
            <label for="porcentajePagablePlata" style="width: 55%">
                <g:message code="terminosContratoPlomoPlata.porcentajePagablePlata.label" default="Porcentaje Pagable Plata" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="porcentajePagablePlata" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'porcentajePagablePlata')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'maquila', 'error')} required">
            <label for="maquila" style="width: 55%">
                <g:message code="terminosContratoPlomoPlata.maquila.label" default="Maquila" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="maquila" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'maquila')}" required="" inputmode="decimal"/>
        </td>
        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'gastoRefinacion', 'error')} required">
            <label for="gastoRefinacion" style="width: 55%">
                <g:message code="terminosContratoPlomoPlata.gastoRefinacion.label" default="Gasto Refinacion" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="gastoRefinacion" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'gastoRefinacion')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'basePlomo', 'error')} required">
            <label for="basePlomo" style="width: 55%">
                <g:message code="terminosContratoPlomoPlata.basePlomo.label" default="Base Plomo" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="basePlomo" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'basePlomo')}" required="" inputmode="decimal"/>
        </td>
        <td>
            &nbsp;</td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'escaladorPlomo', 'error')} required">
            <label for="escaladorPlomo" style="width: 55%">
                <g:message code="terminosContratoPlomoPlata.escaladorPlomo.label" default="Escalador Plomo" />
                <span class="required-indicator">*</span>
            </label>
            <g:field name="escaladorPlomo" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'escaladorPlomo')}" required="" inputmode="decimal"/>
        </td>
        <td>
            &nbsp;</td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Penalidades</h1>

<table class="center" style="width: 700px;">
    <thead>
    <tr>
        <th scope="col">MINERAL</th>
        <th scope="col">CALIDAD LIBRE</th>
        <th scope="col">PENALIZACION</th>
        <th scope="col">PORCENTAJE PENALIZABLE</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'calidadLibreArsenico', 'error')} required">
            <label for="calidadLibreArsenico">
                <g:message code="terminosContratoPlomoPlata.calidadLibreArsenico.label" default="Arsenico" />
            </label>
        </td>
        <td>
            <g:field name="calidadLibreArsenico" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'calidadLibreArsenico')}" required="" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="penalizacionArsenico" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'penalizacionArsenico')}" required="" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="porcentajePenalizableArsenico" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'porcentajePenalizableArsenico')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'calidadLibreAntimonio', 'error')} required">
            <label for="calidadLibreAntimonio">
                <g:message code="terminosContratoPlomoPlata.calidadLibreAntimonio.label" default="Antimonio" />
            </label>
        </td>
        <td>
            <g:field name="calidadLibreAntimonio" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'calidadLibreAntimonio')}" required="" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="penalizacionAntimonio" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'penalizacionAntimonio')}" required="" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="porcentajePenalizableAntimonio" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'porcentajePenalizableAntimonio')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'calidadLibreBismuto', 'error')} required">
            <label for="calidadLibreBismuto">
                <g:message code="terminosContratoPlomoPlata.calidadLibreBismuto.label" default="Bismuto" />
            </label>
        </td>
        <td>
            <g:field name="calidadLibreBismuto" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'calidadLibreBismuto')}" required="" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="penalizacionBismuto" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'penalizacionBismuto')}" required="" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="porcentajePenalizableBismuto" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'porcentajePenalizableBismuto')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'calidadLibreEstano', 'error')} required">
            <label for="calidadLibreEstano">
                <g:message code="terminosContratoPlomoPlata.calidadLibreEstano.label" default="Estano" />
            </label>
        </td>
        <td>
            <g:field name="calidadLibreEstano" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'calidadLibreEstano')}" required="" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="penalizacionEstano" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'penalizacionEstano')}" required="" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="porcentajePenalizableEstano" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'porcentajePenalizableEstano')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosContratoPlomoPlataInstance, field: 'calidadLibreZinc', 'error')} required">
            <label for="calidadLibreZinc">
                <g:message code="terminosContratoPlomoPlata.calidadLibreZinc.label" default="Zinc" />
            </label>
        </td>
        <td>
            <g:field name="calidadLibreZinc" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'calidadLibreZinc')}" required="" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="penalizacionZinc" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'penalizacionZinc')}" required="" inputmode="decimal"/>
        </td>
        <td>
            <g:field name="porcentajePenalizableZinc" value="${fieldValue(bean: terminosContratoPlomoPlataInstance, field: 'porcentajePenalizableZinc')}" required="" inputmode="decimal"/>
        </td>
    </tr>
    </tbody>
</table>

