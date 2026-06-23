<%@ page import="org.socymet.cotizaciones.TerminosDeContrato" %>

<g:hiddenField name="vista" />

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'nombreContrato', 'error')} required">
    <label for="nombreContrato">
        <g:message code="terminosDeContrato.nombreContrato.label" default="Nombre Contrato" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="nombreContrato" required="" value="${terminosDeContratoInstance?.nombreContrato}" size="70"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'empresa', 'error')} " style="display: none">
    <label for="empresa">
        <g:message code="terminosDeContrato.empresa.label" default="Empresa" />

    </label>
    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${terminosDeContratoInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'tipoDeMineral', 'error')} ">
    <label for="tipoDeMineral">
        <g:message code="terminosDeContrato.tipoDeMineral.label" default="Tipo De Mineral" />

    </label>
    <g:select name="tipoDeMineral" from="${['PB-AG','ZN-AG']}" value="${terminosDeContratoInstance?.tipoDeMineral}" valueMessagePrefix="terminosDeContrato.tipoDeMineral" noSelection="['': '']"/>
</div>

<h1 style="font-weight: bold">Impurezas</h1>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeArsenico', 'error')} required">
    <label for="porcentajeArsenico">
        <g:message code="terminosDeContrato.porcentajeArsenico.label" default="Porcentaje Arsenico" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeArsenico" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeArsenico')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeAntimonio', 'error')} required">
    <label for="porcentajeAntimonio">
        <g:message code="terminosDeContrato.porcentajeAntimonio.label" default="Porcentaje Antimonio" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeAntimonio" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeAntimonio')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeBismuto', 'error')} required">
    <label for="porcentajeBismuto">
        <g:message code="terminosDeContrato.porcentajeBismuto.label" default="Porcentaje Bismuto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeBismuto" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeBismuto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeEstano', 'error')} required">
    <label for="porcentajeEstano">
        <g:message code="terminosDeContrato.porcentajeEstano.label" default="Porcentaje Estano" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeEstano" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeEstano')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeHierro', 'error')} required">
    <label for="porcentajeHierro">
        <g:message code="terminosDeContrato.porcentajeHierro.label" default="Porcentaje Hierro" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeHierro" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeHierro')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeSilice', 'error')} required">
    <label for="porcentajeSilice">
        <g:message code="terminosDeContrato.porcentajeSilice.label" default="Porcentaje Silice" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeSilice" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeSilice')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeZinc', 'error')} required">
    <label for="porcentajeZinc">
        <g:message code="terminosDeContrato.porcentajeZinc.label" default="Porcentaje Zinc" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajeZinc" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeZinc')}" required="" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Deducciones Unitarias</h1>

<div id="_deduccionUnitariaZinc" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'deduccionUnitariaZinc', 'error')} required">
    <label for="deduccionUnitariaZinc">
        <g:message code="terminosDeContrato.deduccionUnitariaZinc.label" default="Deduccion Unitaria Zinc" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="deduccionUnitariaZinc" value="${fieldValue(bean: terminosDeContratoInstance, field: 'deduccionUnitariaZinc')}" required="" inputmode="decimal"/>
</div>

<div id="_deduccionUnitariaPlomo" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'deduccionUnitariaPlomo', 'error')} required">
    <label for="deduccionUnitariaPlomo">
        <g:message code="terminosDeContrato.deduccionUnitariaPlomo.label" default="Deduccion Unitaria Plomo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="deduccionUnitariaPlomo" value="${fieldValue(bean: terminosDeContratoInstance, field: 'deduccionUnitariaPlomo')}" required="" inputmode="decimal"/>
</div>

<div id="_deduccionUnitariaPlata" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'deduccionUnitariaPlata', 'error')} required">
    <label for="deduccionUnitariaPlata">
        <g:message code="terminosDeContrato.deduccionUnitariaPlata.label" default="Deduccion Unitaria Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="deduccionUnitariaPlata" value="${fieldValue(bean: terminosDeContratoInstance, field: 'deduccionUnitariaPlata')}" required="" inputmode="decimal"/>
</div>

<div id="_deduccionUnitariaCobre" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'deduccionUnitariaCobre', 'error')} required" style="display: none">
    <label for="deduccionUnitariaCobre">
        <g:message code="terminosDeContrato.deduccionUnitariaCobre.label" default="Deduccion Unitaria Cobre" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="deduccionUnitariaCobre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'deduccionUnitariaCobre')}" required="" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Minerales Pagables</h1>

<div id="_porcentajePagableLMEZinc" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajePagableLMEZinc', 'error')} required">
    <label for="porcentajePagableLMEZinc">
        <g:message code="terminosDeContrato.porcentajePagableLMEZinc.label" default="Porcentaje Pagable LME Zinc" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajePagableLMEZinc" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajePagableLMEZinc')}" required="" inputmode="decimal"/>
</div>

<div id="_porcentajePagableLMEPlomo" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajePagableLMEPlomo', 'error')} required">
    <label for="porcentajePagableLMEPlomo">
        <g:message code="terminosDeContrato.porcentajePagableLMEPlomo.label" default="Porcentaje Pagable LME Plomo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajePagableLMEPlomo" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajePagableLMEPlomo')}" required="" inputmode="decimal"/>
</div>

<div id="_porcentajePagableLMEPlata" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajePagableLMEPlata', 'error')} required">
    <label for="porcentajePagableLMEPlata">
        <g:message code="terminosDeContrato.porcentajePagableLMEPlata.label" default="Porcentaje Pagable LME Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajePagableLMEPlata" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajePagableLMEPlata')}" required="" inputmode="decimal"/>
</div>

<div id="_porcentajePagableLMECobre" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajePagableLMECobre', 'error')} required" style="display: none">
    <label for="porcentajePagableLMECobre">
        <g:message code="terminosDeContrato.porcentajePagableLMECobre.label" default="Porcentaje Pagable LME Cobre" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="porcentajePagableLMECobre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajePagableLMECobre')}" required="" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Maquila + Escalador</h1>

<div id="_maquilaZincPlata" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'maquilaZincPlata', 'error')} required">
    <label for="maquilaZincPlata">
        <g:message code="terminosDeContrato.maquilaZincPlata.label" default="Maquila Zinc Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="maquilaZincPlata" value="${fieldValue(bean: terminosDeContratoInstance, field: 'maquilaZincPlata')}" required="" inputmode="decimal"/>
</div>

<div id="_baseZincPlata" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'baseZincPlata', 'error')} required">
    <label for="baseZincPlata">
        <g:message code="terminosDeContrato.baseZincPlata.label" default="Base Zinc Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="baseZincPlata" value="${fieldValue(bean: terminosDeContratoInstance, field: 'baseZincPlata')}" required="" inputmode="decimal"/>
</div>

<div id="_escaladorZincPlata" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'escaladorZincPlata', 'error')} required">
    <label for="escaladorZincPlata">
        <g:message code="terminosDeContrato.escaladorZincPlata.label" default="Escalador Zinc Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="escaladorZincPlata" value="${fieldValue(bean: terminosDeContratoInstance, field: 'escaladorZincPlata')}" required="" inputmode="decimal"/>
</div>

<div id="_maquilaPlomoPlata" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'maquilaPlomoPlata', 'error')} required">
    <label for="maquilaPlomoPlata">
        <g:message code="terminosDeContrato.maquilaPlomoPlata.label" default="Maquila Plomo Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="maquilaPlomoPlata" value="${fieldValue(bean: terminosDeContratoInstance, field: 'maquilaPlomoPlata')}" required="" inputmode="decimal"/>
</div>

<div id="_basePlomoPlata" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'basePlomoPlata', 'error')} required">
    <label for="basePlomoPlata">
        <g:message code="terminosDeContrato.basePlomoPlata.label" default="Base Plomo Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="basePlomoPlata" value="${fieldValue(bean: terminosDeContratoInstance, field: 'basePlomoPlata')}" required="" inputmode="decimal"/>
</div>

<div id="_escaladorPlomoPlata" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'escaladorPlomoPlata', 'error')} required">
    <label for="escaladorPlomoPlata">
        <g:message code="terminosDeContrato.escaladorPlomoPlata.label" default="Escalador Plomo Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="escaladorPlomoPlata" value="${fieldValue(bean: terminosDeContratoInstance, field: 'escaladorPlomoPlata')}" required="" inputmode="decimal"/>
</div>

<div id="_maquilaCobre" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'maquilaCobre', 'error')} required" style="display: none">
    <label for="maquilaCobre">
        <g:message code="terminosDeContrato.maquilaCobre.label" default="Maquila Cobre" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="maquilaCobre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'maquilaCobre')}" required="" inputmode="decimal"/>
</div>

<div id="_baseCobre" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'baseCobre', 'error')} required" style="display: none">
    <label for="baseCobre">
        <g:message code="terminosDeContrato.baseCobre.label" default="Base Cobre" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="baseCobre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'baseCobre')}" required="" inputmode="decimal"/>
</div>

<div id="_escaladorCobre" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'escaladorCobre', 'error')} required" style="display: none">
    <label for="escaladorCobre">
        <g:message code="terminosDeContrato.escaladorCobre.label" default="Escalador Cobre" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="escaladorCobre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'escaladorCobre')}" required="" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Gastos de Refinacion por Onza</h1>

<div id="_deduccionRefinacionOnzaZincPlata" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'deduccionRefinacionOnzaZincPlata', 'error')} required">
    <label for="deduccionRefinacionOnzaZincPlata">
        <g:message code="terminosDeContrato.deduccionRefinacionOnzaZincPlata.label" default="Deduccion Refinacion Onza Zinc Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="deduccionRefinacionOnzaZincPlata" value="${fieldValue(bean: terminosDeContratoInstance, field: 'deduccionRefinacionOnzaZincPlata')}" required="" inputmode="decimal"/>
</div>

<div id="_deduccionRefinacionOnzaPlomoPlata" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'deduccionRefinacionOnzaPlomoPlata', 'error')} required">
    <label for="deduccionRefinacionOnzaPlomoPlata">
        <g:message code="terminosDeContrato.deduccionRefinacionOnzaPlomoPlata.label" default="Deduccion Refinacion Onza Plomo Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="deduccionRefinacionOnzaPlomoPlata" value="${fieldValue(bean: terminosDeContratoInstance, field: 'deduccionRefinacionOnzaPlomoPlata')}" required="" inputmode="decimal"/>
</div>

<div id="_deduccionRefinacionOnzaCobrePlata" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'deduccionRefinacionOnzaCobrePlata', 'error')} required" style="display: none">
    <label for="deduccionRefinacionOnzaCobrePlata">
        <g:message code="terminosDeContrato.deduccionRefinacionOnzaCobrePlata.label" default="Deduccion Refinacion Onza Cobre Plata" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="deduccionRefinacionOnzaCobrePlata" value="${fieldValue(bean: terminosDeContratoInstance, field: 'deduccionRefinacionOnzaCobrePlata')}" required="" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Gastos de Refinacion por Libra</h1>

<div id="_deduccionRefinacionLibraZinc" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'deduccionRefinacionLibraZinc', 'error')} required">
    <label for="deduccionRefinacionLibraZinc">
        <g:message code="terminosDeContrato.deduccionRefinacionLibraZinc.label" default="Deduccion Refinacion Libra Zinc" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="deduccionRefinacionLibraZinc" value="${fieldValue(bean: terminosDeContratoInstance, field: 'deduccionRefinacionLibraZinc')}" required="" inputmode="decimal"/>
</div>

<div id="_deduccionRefinacionLibraPlomo" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'deduccionRefinacionLibraPlomo', 'error')} required">
    <label for="deduccionRefinacionLibraPlomo">
        <g:message code="terminosDeContrato.deduccionRefinacionLibraPlomo.label" default="Deduccion Refinacion Libra Plomo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="deduccionRefinacionLibraPlomo" value="${fieldValue(bean: terminosDeContratoInstance, field: 'deduccionRefinacionLibraPlomo')}" required="" inputmode="decimal"/>
</div>

<div id="_deduccionRefinacionLibraCobre" class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'deduccionRefinacionLibraCobre', 'error')} required" style="display: none">
    <label for="deduccionRefinacionLibraCobre">
        <g:message code="terminosDeContrato.deduccionRefinacionLibraCobre.label" default="Deduccion Refinacion Libra Cobre" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="deduccionRefinacionLibraCobre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'deduccionRefinacionLibraCobre')}" required="" inputmode="decimal"/>
</div>

<h1 style="font-weight: bold">Penalidades</h1>

<table>
    <thead>
    <tr>
        <th>ELEMENTO</th>
        <th>LIBRE</th>
        <th>COSTO UNITARIO</th>
        <th>PORCENTAJE UNITARIO</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'arsenicoLibre', 'error')} required">
            <label for="arsenicoLibre" style="width: 50%">
                <g:message code="terminosDeContrato.arsenicoLibre.label" default="Arsenico" />
                <span class="required-indicator">*</span>
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'arsenicoLibre', 'error')} required">
            <g:field name="arsenicoLibre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'arsenicoLibre')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioArsenico', 'error')} required">
        </label>
            <g:field name="costoUnitarioArsenico" value="${fieldValue(bean: terminosDeContratoInstance, field: 'costoUnitarioArsenico')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioArsenico', 'error')} required">
            <g:field name="porcentajeUnitarioArsenico" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioArsenico')}" required="" size="15" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'antimonioLibre', 'error')} required">
            <label for="antimonioLibre" style="width: 60%">
                <g:message code="terminosDeContrato.antimonioLibre.label" default="Antimonio" />
                <span class="required-indicator">*</span>
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'antimonioLibre', 'error')} required">
            <g:field name="antimonioLibre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'antimonioLibre')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioAntimonio', 'error')} required">
            <g:field name="costoUnitarioAntimonio" value="${fieldValue(bean: terminosDeContratoInstance, field: 'costoUnitarioAntimonio')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioAntimonio', 'error')} required">
            <g:field name="porcentajeUnitarioAntimonio" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioAntimonio')}" required="" size="15" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'bismutoLibre', 'error')} required">
            <label for="bismutoLibre" style="width: 50%">
                <g:message code="terminosDeContrato.bismutoLibre.label" default="Bismuto" />
                <span class="required-indicator">*</span>
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'bismutoLibre', 'error')} required">
            <g:field name="bismutoLibre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'bismutoLibre')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioBismuto', 'error')} required">
            <g:field name="costoUnitarioBismuto" value="${fieldValue(bean: terminosDeContratoInstance, field: 'costoUnitarioBismuto')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioBismuto', 'error')} required">
            <g:field name="porcentajeUnitarioBismuto" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioBismuto')}" required="" size="15" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'estanoLibre', 'error')} required">
            <label for="estanoLibre" style="width: 50%">
                <g:message code="terminosDeContrato.estanoLibre.label" default="Estano" />
                <span class="required-indicator">*</span>
            </label>
        </td>
        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'estanoLibre', 'error')} required">
            <g:field name="estanoLibre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'estanoLibre')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioEstano', 'error')} required">
            <g:field name="costoUnitarioEstano" value="${fieldValue(bean: terminosDeContratoInstance, field: 'costoUnitarioEstano')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioEstano', 'error')} required">
            <g:field name="porcentajeUnitarioEstano" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioEstano')}" required="" size="15" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'hierroLibre', 'error')} required">
            <label for="hierroLibre" style="width: 50%">
                <g:message code="terminosDeContrato.hierroLibre.label" default="Hierro" />
                <span class="required-indicator">*</span>
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'hierroLibre', 'error')} required">
            <g:field name="hierroLibre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'hierroLibre')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioHierro', 'error')} required">
            <g:field name="costoUnitarioHierro" value="${fieldValue(bean: terminosDeContratoInstance, field: 'costoUnitarioHierro')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioHierro', 'error')} required">
            <g:field name="porcentajeUnitarioHierro" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioHierro')}" required="" size="15" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'siliceLibre', 'error')} required">
            <label for="siliceLibre" style="width: 50%">
                <g:message code="terminosDeContrato.siliceLibre.label" default="Silice" />
                <span class="required-indicator">*</span>
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'siliceLibre', 'error')} required">
            <g:field name="siliceLibre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'siliceLibre')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioSilice', 'error')} required">
            <g:field name="costoUnitarioSilice" value="${fieldValue(bean: terminosDeContratoInstance, field: 'costoUnitarioSilice')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioSilice', 'error')} required">
            <g:field name="porcentajeUnitarioSilice" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioSilice')}" required="" size="15" inputmode="decimal"/>
        </td>
    </tr>

    <tr>
        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'zincLibre', 'error')} required">
            <label for="zincLibre" style="width: 50%">
                <g:message code="terminosDeContrato.zincLibre.label" default="Zinc" />
                <span class="required-indicator">*</span>
            </label>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'zincLibre', 'error')} required">
            <g:field name="zincLibre" value="${fieldValue(bean: terminosDeContratoInstance, field: 'zincLibre')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'costoUnitarioZinc', 'error')} required">
            <g:field name="costoUnitarioZinc" value="${fieldValue(bean: terminosDeContratoInstance, field: 'costoUnitarioZinc')}" required="" size="15" inputmode="decimal"/>
        </td>

        <td class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioZinc', 'error')} required">
            <g:field name="porcentajeUnitarioZinc" value="${fieldValue(bean: terminosDeContratoInstance, field: 'porcentajeUnitarioZinc')}" required="" size="15" inputmode="decimal"/>
        </td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Otros Gastos</h1>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'transporteInterno', 'error')} required">
    <label for="transporteInterno">
        <g:message code="terminosDeContrato.transporteInterno.label" default="Transporte Interno" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="transporteInterno" value="${fieldValue(bean: terminosDeContratoInstance, field: 'transporteInterno')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'laboratorio', 'error')} required">
    <label for="laboratorio">
        <g:message code="terminosDeContrato.laboratorio.label" default="Laboratorio" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="laboratorio" value="${fieldValue(bean: terminosDeContratoInstance, field: 'laboratorio')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'molienda', 'error')} required">
    <label for="molienda">
        <g:message code="terminosDeContrato.molienda.label" default="Molienda" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="molienda" value="${fieldValue(bean: terminosDeContratoInstance, field: 'molienda')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'manipuleo', 'error')} required">
    <label for="manipuleo">
        <g:message code="terminosDeContrato.manipuleo.label" default="Manipuleo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="manipuleo" value="${fieldValue(bean: terminosDeContratoInstance, field: 'manipuleo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'margenAdministrativo', 'error')} required">
    <label for="margenAdministrativo">
        <g:message code="terminosDeContrato.margenAdministrativo.label" default="Margen Administrativo" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="margenAdministrativo" value="${fieldValue(bean: terminosDeContratoInstance, field: 'margenAdministrativo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'transporteAPuerto', 'error')} required">
    <label for="transporteAPuerto">
        <g:message code="terminosDeContrato.transporteAPuerto.label" default="Transporte AP uerto" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="transporteAPuerto" value="${fieldValue(bean: terminosDeContratoInstance, field: 'transporteAPuerto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: terminosDeContratoInstance, field: 'rollBack', 'error')} required">
    <label for="rollBack">
        <g:message code="terminosDeContrato.rollBack.label" default="Roll Back" />
        <span class="required-indicator">*</span>
    </label>
    <g:field name="rollBack" value="${fieldValue(bean: terminosDeContratoInstance, field: 'rollBack')}" required="" inputmode="decimal"/>
</div>