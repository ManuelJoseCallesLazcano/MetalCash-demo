<%@ page import="org.socymet.liquidacion.LiquidacionProvisionalDePlomoPlata" %>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'numeroLiquidacionProvisionalPlomoPlata', 'error')} required" style="display: none">
	<label for="numeroLiquidacionProvisionalPlomoPlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.numeroLiquidacionProvisionalPlomoPlata.label" default="Numero Liquidacion Provisional Plomo Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numeroLiquidacionProvisionalPlomoPlata" type="number" value="${liquidacionProvisionalDePlomoPlataInstance.numeroLiquidacionProvisionalPlomoPlata}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="liquidacionProvisionalDePlomoPlata.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.lote}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'fechaDeLiquidacionProvisional', 'error')} required">
    <label for="fechaDeLiquidacionProvisional">
        <g:message code="liquidacionProvisionalDePlomoPlata.fechaDeLiquidacionProvisional.label" default="Fecha De Liquidacion Provisional" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="fechaDeLiquidacionProvisional" precision="day"  value="${liquidacionProvisionalDePlomoPlataInstance?.fechaDeLiquidacionProvisional}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'recepcionDeComplejo', 'error')} required" style="display: none">
	<label for="recepcionDeComplejo">
		<g:message code="liquidacionProvisionalDePlomoPlata.recepcionDeComplejo.label" default="Recepcion De Complejo" />
		<span class="required-indicator">*</span>
	</label>
	%{--<g:select id="recepcionDeComplejo" name="recepcionDeComplejo.id" from="${org.socymet.recepcion.RecepcionDeComplejo.list()}" optionKey="id" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.recepcionDeComplejo?.id}" class="many-to-one"/>--}%
    <g:textField name="recepcionDeComplejo.id" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.recepcionDeComplejo?.id}" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'deposito', 'error')} required" style="display: none">
	<label for="deposito">
		<g:message code="liquidacionProvisionalDePlomoPlata.deposito.label" default="Deposito" />
		<span class="required-indicator">*</span>
	</label>
	%{--<g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.deposito?.id}" class="many-to-one"/>--}%
    <g:textField name="deposito.id" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.deposito?.id}" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionDiariaDeZinc', 'error')} required" style="display: none">
	<label for="cotizacionDiariaDeZinc">
		<g:message code="liquidacionProvisionalDePlomoPlata.cotizacionDiariaDeZinc.label" default="Cotizacion Diaria De Zinc" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cotizacionDiariaDeZinc" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionDiariaDeZinc')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionQuincenalDeZinc', 'error')} required" style="display: none">
	<label for="cotizacionQuincenalDeZinc">
		<g:message code="liquidacionProvisionalDePlomoPlata.cotizacionQuincenalDeZinc.label" default="Cotizacion Quincenal De Zinc" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cotizacionQuincenalDeZinc" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionQuincenalDeZinc')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'alicuotaDeZinc', 'error')} required" style="display: none">
	<label for="alicuotaDeZinc">
		<g:message code="liquidacionProvisionalDePlomoPlata.alicuotaDeZinc.label" default="Alicuota De Zinc" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="alicuotaDeZinc" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'alicuotaDeZinc')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'alicuotaDeZincParaExportacion', 'error')} required" style="display: none">
	<label for="alicuotaDeZincParaExportacion">
		<g:message code="liquidacionProvisionalDePlomoPlata.alicuotaDeZincParaExportacion.label" default="Alicuota De Zinc Para Exportacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="alicuotaDeZincParaExportacion" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'alicuotaDeZincParaExportacion')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionDiariaDePlomo', 'error')} required" style="display: none">
	<label for="cotizacionDiariaDePlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.cotizacionDiariaDePlomo.label" default="Cotizacion Diaria De Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cotizacionDiariaDePlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionDiariaDePlomo')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionQuincenalDePlomo', 'error')} required" style="display: none">
	<label for="cotizacionQuincenalDePlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.cotizacionQuincenalDePlomo.label" default="Cotizacion Quincenal De Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cotizacionQuincenalDePlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionQuincenalDePlomo')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'alicuotaDePlomo', 'error')} required" style="display: none">
	<label for="alicuotaDePlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.alicuotaDePlomo.label" default="Alicuota De Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="alicuotaDePlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'alicuotaDePlomo')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'alicuotaDePlomoParaExportacion', 'error')} required" style="display: none">
	<label for="alicuotaDePlomoParaExportacion">
		<g:message code="liquidacionProvisionalDePlomoPlata.alicuotaDePlomoParaExportacion.label" default="Alicuota De Plomo Para Exportacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="alicuotaDePlomoParaExportacion" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'alicuotaDePlomoParaExportacion')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionDiariaDePlata', 'error')} required" style="display: none">
	<label for="cotizacionDiariaDePlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.cotizacionDiariaDePlata.label" default="Cotizacion Diaria De Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cotizacionDiariaDePlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionDiariaDePlata')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionQuincenalDePlata', 'error')} required" style="display: none">
	<label for="cotizacionQuincenalDePlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.cotizacionQuincenalDePlata.label" default="Cotizacion Quincenal De Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cotizacionQuincenalDePlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionQuincenalDePlata')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'alicuotaDePlata', 'error')} required" style="display: none">
	<label for="alicuotaDePlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.alicuotaDePlata.label" default="Alicuota De Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="alicuotaDePlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'alicuotaDePlata')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'alicuotaDePlataParaExportacion', 'error')} required" style="display: none">
	<label for="alicuotaDePlataParaExportacion">
		<g:message code="liquidacionProvisionalDePlomoPlata.alicuotaDePlataParaExportacion.label" default="Alicuota De Plata Para Exportacion" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="alicuotaDePlataParaExportacion" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'alicuotaDePlataParaExportacion')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'tipoDeCambioOficial', 'error')} required" style="display: none">
	<label for="tipoDeCambioOficial">
		<g:message code="liquidacionProvisionalDePlomoPlata.tipoDeCambioOficial.label" default="Tipo De Cambio Oficial" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="tipoDeCambioOficial" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'tipoDeCambioOficial')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'tipoDeCambioComercial', 'error')} required" style="display: none">
	<label for="tipoDeCambioComercial">
		<g:message code="liquidacionProvisionalDePlomoPlata.tipoDeCambioComercial.label" default="Tipo De Cambio Comercial" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="tipoDeCambioComercial" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'tipoDeCambioComercial')}" required="" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cliente', 'error')} required" style="display: none">
	<label for="cliente">
		<g:message code="liquidacionProvisionalDePlomoPlata.cliente.label" default="Cliente" />
		<span class="required-indicator">*</span>
	</label>
	%{--<g:select id="cliente" name="cliente.id" from="${org.socymet.proveedor.Cliente.list()}" optionKey="id" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.cliente?.id}" class="many-to-one"/>--}%
    <g:textField name="cliente.id" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.cliente?.id}" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'empresa', 'error')} required" style="display: none">
	<label for="empresa">
		<g:message code="liquidacionProvisionalDePlomoPlata.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	%{--<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.empresa?.id}" class="many-to-one"/>--}%
    <g:textField name="empresa.id" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.empresa?.id}" readonly="readonly"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'nombreCliente', 'error')} required">
	<label for="nombreCliente">
		<g:message code="liquidacionProvisionalDePlomoPlata.nombreCliente.label" default="Nombre Cliente" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCliente" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="liquidacionProvisionalDePlomoPlata.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreEmpresa" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'direccion', 'error')} required">
	<label for="direccion">
		<g:message code="liquidacionProvisionalDePlomoPlata.direccion.label" default="Direccion" />
		<span class="required-indicator">*</span>
	</label>
	%{--<g:textField name="direccion" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.direccion}"/>--}%
    <g:textArea name="direccion" required="" maxlength="200" value="${liquidacionProvisionalDePlomoPlataInstance?.direccion}" readonly="readonly" class="amarillo"/>
</div>

<table>
    <tbody>
        <tr>
            <td class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'fechaDeRecepcion', 'error')} required">
                <label for="fechaDeRecepcion" style="width: 207px">
                    <g:message code="liquidacionProvisionalDePlomoPlata.fechaDeRecepcion.label" default="Fecha De Recepcion" />
                    <span class="required-indicator">*</span>
                </label>
                <g:textField name="fechaDeRecepcion" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
            </td>

            <td class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'tipoDeMineral', 'error')} required">
                <label for="tipoDeMineral" style="width: 207px">
                    <g:message code="liquidacionProvisionalDePlomoPlata.tipoDeMineral.label" default="Tipo De Mineral" />
                    <span class="required-indicator">*</span>
                </label>
                <g:textField name="tipoDeMineral" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.tipoDeMineral}" class="amarillo" readonly="true"/>
            </td>
        </tr>
        <tr>
            <td class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'naturalezaMineral', 'error')} required">
                <label for="naturalezaMineral" style="width: 207px">
                    <g:message code="liquidacionProvisionalDePlomoPlata.naturalezaMineral.label" default="Naturaleza Mineral" />
                    <span class="required-indicator">*</span>
                </label>
                <g:textField name="naturalezaMineral" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.naturalezaMineral}" class="amarillo" readonly="true"/>
            </td>

            <td class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cantidadDeSacos', 'error')} required">
                <label for="cantidadDeSacos" style="width: 207px">
                    <g:message code="liquidacionProvisionalDePlomoPlata.cantidadDeSacos.label" default="Cantidad De Sacos" />
                    <span class="required-indicator">*</span>
                </label>
                <g:textField name="cantidadDeSacos" inputmode="numeric" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.cantidadDeSacos}" class="amarillo" readonly="true"/>
            </td>
        </tr>
        <tr>
            <td class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'toneladasMetricasHumedas', 'error')} required">
                <label for="toneladasMetricasHumedas" style="width: 207px">
                    <g:message code="liquidacionProvisionalDePlomoPlata.toneladasMetricasHumedas.label" default="Toneladas Metricas Humedas" />
                    <span class="required-indicator">*</span>
                </label>
                <g:field name="toneladasMetricasHumedas" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'toneladasMetricasHumedas')}" required="" class="amarillo" readonly="true"/>
            </td>

            <td class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'humedadPromedio', 'error')} required">
                <label for="humedadPromedio" style="width: 207px">
                    <g:message code="liquidacionProvisionalDePlomoPlata.humedadPromedio.label" default="Humedad Promedio" />
                    <span class="required-indicator">*</span>
                </label>
                <g:field name="humedadPromedio" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'humedadPromedio')}" required="" inputmode="decimal"/>
            </td>
        </tr>
    
        <tr>
            <td class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'toneladasMetricasSecas', 'error')} required">
                <label for="toneladasMetricasSecas" style="width: 207px">
                    <g:message code="liquidacionProvisionalDePlomoPlata.toneladasMetricasSecas.label" default="Toneladas Metricas Secas" />
                    <span class="required-indicator">*</span>
                </label>
                <g:field name="toneladasMetricasSecas" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'toneladasMetricasSecas')}" required="" inputmode="decimal"/>
            </td>
    
            <td class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'merma', 'error')} required">
                <label for="merma" style="width: 207px">
                    <g:message code="liquidacionProvisionalDePlomoPlata.merma.label" default="Merma" />
                    <span class="required-indicator">*</span>
                </label>
                <g:field name="merma" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'merma')}" required="" inputmode="decimal"/>
            </td>
        </tr>
        
        <tr>
            <td class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'toneladasMetricasSecasFinales', 'error')} required">
                <label for="toneladasMetricasSecasFinales" style="width: 207px">
                    <g:message code="liquidacionProvisionalDePlomoPlata.toneladasMetricasSecasFinales.label" default="Toneladas Metricas Secas Finales" />
                    <span class="required-indicator">*</span>
                </label>
                <g:field name="toneladasMetricasSecasFinales" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'toneladasMetricasSecasFinales')}" required="" inputmode="decimal"/>
            </td>
        </tr>
        
        <tr>
            <td class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'partidaArancelaria', 'error')} required">
                <label for="partidaArancelaria" style="width: 207px">
                    <g:message code="liquidacionProvisionalDePlomoPlata.partidaArancelaria.label" default="Partida Arancelaria" />
                    <span class="required-indicator">*</span>
                </label>
                <g:textField name="partidaArancelaria" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.partidaArancelaria}"/>
            </td>
    
            <td class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'condicionesDeEntrega', 'error')} required">
                <label for="condicionesDeEntrega" style="width: 207px">
                    <g:message code="liquidacionProvisionalDePlomoPlata.condicionesDeEntrega.label" default="Condiciones De Entrega" />
                    <span class="required-indicator">*</span>
                </label>
                <g:textField name="condicionesDeEntrega" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.condicionesDeEntrega}"/>
            </td>
        </tr>
        
        <tr>
            <td class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'origen', 'error')} required">
                <label for="origen" style="width: 207px">
                    <g:message code="liquidacionProvisionalDePlomoPlata.origen.label" default="Origen" />
                    <span class="required-indicator">*</span>
                </label>
                <g:textField name="origen" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.origen}"/>
            </td>
        </tr>
    </tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajePlomo', 'error')} required">
	<label for="porcentajePlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajePlomo.label" default="Porcentaje Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajePlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajePlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'deduccionUnitariaPlomo', 'error')} required">
	<label for="deduccionUnitariaPlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.deduccionUnitariaPlomo.label" default="Deduccion Unitaria Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="deduccionUnitariaPlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'deduccionUnitariaPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'leyPagablePlomo', 'error')} required">
	<label for="leyPagablePlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.leyPagablePlomo.label" default="Ley Pagable Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyPagablePlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'leyPagablePlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajePagableLMEPlomo', 'error')} required">
	<label for="porcentajePagableLMEPlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajePagableLMEPlomo.label" default="Porcentaje Pagable LMEP lomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajePagableLMEPlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajePagableLMEPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'leyFinalPagablePlomo', 'error')} required">
	<label for="leyFinalPagablePlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.leyFinalPagablePlomo.label" default="Ley Final Pagable Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyFinalPagablePlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'leyFinalPagablePlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionPlomo', 'error')} required">
	<label for="cotizacionPlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.cotizacionPlomo.label" default="Cotizacion Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cotizacionPlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'valorBrutoPlomo', 'error')} required">
	<label for="valorBrutoPlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.valorBrutoPlomo.label" default="Valor Bruto Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorBrutoPlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'valorBrutoPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajePlata', 'error')} required">
	<label for="porcentajePlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajePlata.label" default="Porcentaje Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajePlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajePlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'deduccionUnitariaPlata', 'error')} required">
	<label for="deduccionUnitariaPlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.deduccionUnitariaPlata.label" default="Deduccion Unitaria Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="deduccionUnitariaPlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'deduccionUnitariaPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'leyPagablePlata', 'error')} required">
	<label for="leyPagablePlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.leyPagablePlata.label" default="Ley Pagable Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyPagablePlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'leyPagablePlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajePagableLMEPlata', 'error')} required">
	<label for="porcentajePagableLMEPlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajePagableLMEPlata.label" default="Porcentaje Pagable LMEP lata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajePagableLMEPlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajePagableLMEPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'leyFinalPagablePlata', 'error')} required">
	<label for="leyFinalPagablePlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.leyFinalPagablePlata.label" default="Ley Final Pagable Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyFinalPagablePlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'leyFinalPagablePlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionPlata', 'error')} required">
	<label for="cotizacionPlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.cotizacionPlata.label" default="Cotizacion Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cotizacionPlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'valorBrutoPlata', 'error')} required">
	<label for="valorBrutoPlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.valorBrutoPlata.label" default="Valor Bruto Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorBrutoPlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'valorBrutoPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'valorBruto', 'error')} required">
	<label for="valorBruto">
		<g:message code="liquidacionProvisionalDePlomoPlata.valorBruto.label" default="Valor Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorBruto" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'valorBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'maquilaPlomoPlata', 'error')} required">
	<label for="maquilaPlomoPlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.maquilaPlomoPlata.label" default="Maquila Plomo Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="maquilaPlomoPlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'maquilaPlomoPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'basePlomoPlata', 'error')} required">
	<label for="basePlomoPlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.basePlomoPlata.label" default="Base Plomo Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="basePlomoPlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'basePlomoPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionBasadaPlomo', 'error')} required">
	<label for="cotizacionBasadaPlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.cotizacionBasadaPlomo.label" default="Cotizacion Basada Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cotizacionBasadaPlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionBasadaPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'escaladorPlomoPlata', 'error')} required">
	<label for="escaladorPlomoPlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.escaladorPlomoPlata.label" default="Escalador Plomo Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="escaladorPlomoPlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'escaladorPlomoPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionEscaladaPlomo', 'error')} required">
	<label for="cotizacionEscaladaPlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.cotizacionEscaladaPlomo.label" default="Cotizacion Escalada Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cotizacionEscaladaPlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'cotizacionEscaladaPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'deduccionMaquilaFinalPlomo', 'error')} required">
	<label for="deduccionMaquilaFinalPlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.deduccionMaquilaFinalPlomo.label" default="Deduccion Maquila Final Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="deduccionMaquilaFinalPlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'deduccionMaquilaFinalPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'deduccionRefinacionOnzaPlomoPlata', 'error')} required">
	<label for="deduccionRefinacionOnzaPlomoPlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.deduccionRefinacionOnzaPlomoPlata.label" default="Deduccion Refinacion Onza Plomo Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="deduccionRefinacionOnzaPlomoPlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'deduccionRefinacionOnzaPlomoPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'deduccionRefinacionOnzaPlomoPlataFinal', 'error')} required">
	<label for="deduccionRefinacionOnzaPlomoPlataFinal">
		<g:message code="liquidacionProvisionalDePlomoPlata.deduccionRefinacionOnzaPlomoPlataFinal.label" default="Deduccion Refinacion Onza Plomo Plata Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="deduccionRefinacionOnzaPlomoPlataFinal" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'deduccionRefinacionOnzaPlomoPlataFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeArsenico', 'error')} required">
	<label for="porcentajeArsenico">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeArsenico.label" default="Porcentaje Arsenico" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeArsenico" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeArsenico')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'arsenicoLibre', 'error')} required">
	<label for="arsenicoLibre">
		<g:message code="liquidacionProvisionalDePlomoPlata.arsenicoLibre.label" default="Arsenico Libre" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="arsenicoLibre" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'arsenicoLibre')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioArsenico', 'error')} required">
	<label for="porcentajeUnitarioArsenico">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioArsenico.label" default="Porcentaje Unitario Arsenico" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeUnitarioArsenico" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioArsenico')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioArsenico', 'error')} required">
	<label for="costoUnitarioArsenico">
		<g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioArsenico.label" default="Costo Unitario Arsenico" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoUnitarioArsenico" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioArsenico')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableArsenicoFinal', 'error')} required">
	<label for="penalidadCastigableArsenicoFinal">
		<g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableArsenicoFinal.label" default="Penalidad Castigable Arsenico Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="penalidadCastigableArsenicoFinal" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableArsenicoFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeAntimonio', 'error')} required">
	<label for="porcentajeAntimonio">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeAntimonio.label" default="Porcentaje Antimonio" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeAntimonio" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeAntimonio')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'antimonioLibre', 'error')} required">
	<label for="antimonioLibre">
		<g:message code="liquidacionProvisionalDePlomoPlata.antimonioLibre.label" default="Antimonio Libre" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="antimonioLibre" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'antimonioLibre')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioAntimonio', 'error')} required">
	<label for="porcentajeUnitarioAntimonio">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioAntimonio.label" default="Porcentaje Unitario Antimonio" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeUnitarioAntimonio" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioAntimonio')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioAntimonio', 'error')} required">
	<label for="costoUnitarioAntimonio">
		<g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioAntimonio.label" default="Costo Unitario Antimonio" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoUnitarioAntimonio" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioAntimonio')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableAntimonioFinal', 'error')} required">
	<label for="penalidadCastigableAntimonioFinal">
		<g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableAntimonioFinal.label" default="Penalidad Castigable Antimonio Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="penalidadCastigableAntimonioFinal" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableAntimonioFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeBismuto', 'error')} required">
	<label for="porcentajeBismuto">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeBismuto.label" default="Porcentaje Bismuto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeBismuto" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeBismuto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'bismutoLibre', 'error')} required">
	<label for="bismutoLibre">
		<g:message code="liquidacionProvisionalDePlomoPlata.bismutoLibre.label" default="Bismuto Libre" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="bismutoLibre" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'bismutoLibre')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioBismuto', 'error')} required">
	<label for="porcentajeUnitarioBismuto">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioBismuto.label" default="Porcentaje Unitario Bismuto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeUnitarioBismuto" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioBismuto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioBismuto', 'error')} required">
	<label for="costoUnitarioBismuto">
		<g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioBismuto.label" default="Costo Unitario Bismuto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoUnitarioBismuto" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioBismuto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableBismutoFinal', 'error')} required">
	<label for="penalidadCastigableBismutoFinal">
		<g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableBismutoFinal.label" default="Penalidad Castigable Bismuto Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="penalidadCastigableBismutoFinal" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableBismutoFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeEstano', 'error')} required">
	<label for="porcentajeEstano">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeEstano.label" default="Porcentaje Estano" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeEstano" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeEstano')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'estanoLibre', 'error')} required">
	<label for="estanoLibre">
		<g:message code="liquidacionProvisionalDePlomoPlata.estanoLibre.label" default="Estano Libre" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="estanoLibre" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'estanoLibre')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioEstano', 'error')} required">
	<label for="porcentajeUnitarioEstano">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioEstano.label" default="Porcentaje Unitario Estano" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeUnitarioEstano" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioEstano')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioEstano', 'error')} required">
	<label for="costoUnitarioEstano">
		<g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioEstano.label" default="Costo Unitario Estano" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoUnitarioEstano" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioEstano')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableEstanoFinal', 'error')} required">
	<label for="penalidadCastigableEstanoFinal">
		<g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableEstanoFinal.label" default="Penalidad Castigable Estano Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="penalidadCastigableEstanoFinal" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableEstanoFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeHierro', 'error')} required">
	<label for="porcentajeHierro">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeHierro.label" default="Porcentaje Hierro" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeHierro" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeHierro')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'hierroLibre', 'error')} required">
	<label for="hierroLibre">
		<g:message code="liquidacionProvisionalDePlomoPlata.hierroLibre.label" default="Hierro Libre" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="hierroLibre" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'hierroLibre')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioHierro', 'error')} required">
	<label for="porcentajeUnitarioHierro">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioHierro.label" default="Porcentaje Unitario Hierro" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeUnitarioHierro" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioHierro')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioHierro', 'error')} required">
	<label for="costoUnitarioHierro">
		<g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioHierro.label" default="Costo Unitario Hierro" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoUnitarioHierro" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioHierro')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableHierroFinal', 'error')} required">
	<label for="penalidadCastigableHierroFinal">
		<g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableHierroFinal.label" default="Penalidad Castigable Hierro Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="penalidadCastigableHierroFinal" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableHierroFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeSilice', 'error')} required">
	<label for="porcentajeSilice">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeSilice.label" default="Porcentaje Silice" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeSilice" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeSilice')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'siliceLibre', 'error')} required">
	<label for="siliceLibre">
		<g:message code="liquidacionProvisionalDePlomoPlata.siliceLibre.label" default="Silice Libre" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="siliceLibre" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'siliceLibre')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioSilice', 'error')} required">
	<label for="porcentajeUnitarioSilice">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioSilice.label" default="Porcentaje Unitario Silice" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeUnitarioSilice" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioSilice')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioSilice', 'error')} required">
	<label for="costoUnitarioSilice">
		<g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioSilice.label" default="Costo Unitario Silice" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoUnitarioSilice" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioSilice')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableSiliceFinal', 'error')} required">
	<label for="penalidadCastigableSiliceFinal">
		<g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableSiliceFinal.label" default="Penalidad Castigable Silice Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="penalidadCastigableSiliceFinal" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableSiliceFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeZinc', 'error')} required">
	<label for="porcentajeZinc">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeZinc.label" default="Porcentaje Zinc" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeZinc" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeZinc')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'zincLibre', 'error')} required">
	<label for="zincLibre">
		<g:message code="liquidacionProvisionalDePlomoPlata.zincLibre.label" default="Zinc Libre" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="zincLibre" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'zincLibre')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioZinc', 'error')} required">
	<label for="porcentajeUnitarioZinc">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeUnitarioZinc.label" default="Porcentaje Unitario Zinc" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeUnitarioZinc" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeUnitarioZinc')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioZinc', 'error')} required">
	<label for="costoUnitarioZinc">
		<g:message code="liquidacionProvisionalDePlomoPlata.costoUnitarioZinc.label" default="Costo Unitario Zinc" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoUnitarioZinc" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoUnitarioZinc')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableZincFinal', 'error')} required">
	<label for="penalidadCastigableZincFinal">
		<g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableZincFinal.label" default="Penalidad Castigable Zinc Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="penalidadCastigableZincFinal" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableZincFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableFinal', 'error')} required">
	<label for="penalidadCastigableFinal">
		<g:message code="liquidacionProvisionalDePlomoPlata.penalidadCastigableFinal.label" default="Penalidad Castigable Final" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="penalidadCastigableFinal" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'penalidadCastigableFinal')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'valorNeto', 'error')} required">
	<label for="valorNeto">
		<g:message code="liquidacionProvisionalDePlomoPlata.valorNeto.label" default="Valor Neto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorNeto" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'valorNeto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeDePagoProvisional', 'error')} required">
	<label for="porcentajeDePagoProvisional">
		<g:message code="liquidacionProvisionalDePlomoPlata.porcentajeDePagoProvisional.label" default="Porcentaje De Pago Provisional" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="porcentajeDePagoProvisional" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'porcentajeDePagoProvisional')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'pagoProvisional', 'error')} required">
	<label for="pagoProvisional">
		<g:message code="liquidacionProvisionalDePlomoPlata.pagoProvisional.label" default="Pago Provisional" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pagoProvisional" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'pagoProvisional')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'librasFinasPlomo', 'error')} required">
	<label for="librasFinasPlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.librasFinasPlomo.label" default="Libras Finas Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="librasFinasPlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'librasFinasPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'valorOficialBrutoPlomo', 'error')} required">
	<label for="valorOficialBrutoPlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.valorOficialBrutoPlomo.label" default="Valor Oficial Bruto Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorOficialBrutoPlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'valorOficialBrutoPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'regaliaPlomo', 'error')} required">
	<label for="regaliaPlomo">
		<g:message code="liquidacionProvisionalDePlomoPlata.regaliaPlomo.label" default="Regalia Plomo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="regaliaPlomo" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'regaliaPlomo')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'onzasTroyPlata', 'error')} required">
	<label for="onzasTroyPlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.onzasTroyPlata.label" default="Onzas Troy Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="onzasTroyPlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'onzasTroyPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'valorOficialBrutoPlata', 'error')} required">
	<label for="valorOficialBrutoPlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.valorOficialBrutoPlata.label" default="Valor Oficial Bruto Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="valorOficialBrutoPlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'valorOficialBrutoPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'regaliaPlata', 'error')} required">
	<label for="regaliaPlata">
		<g:message code="liquidacionProvisionalDePlomoPlata.regaliaPlata.label" default="Regalia Plata" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="regaliaPlata" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'regaliaPlata')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'retenciones', 'error')} required">
	<label for="retenciones">
		<g:message code="liquidacionProvisionalDePlomoPlata.retenciones.label" default="Retenciones" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="retenciones" cols="40" rows="5" maxlength="100000" required="" value="${liquidacionProvisionalDePlomoPlataInstance?.retenciones}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'analisisDeLaboratorio', 'error')} required">
	<label for="analisisDeLaboratorio">
		<g:message code="liquidacionProvisionalDePlomoPlata.analisisDeLaboratorio.label" default="Analisis De Laboratorio" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="analisisDeLaboratorio" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'analisisDeLaboratorio')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'inspeccionDeProducto', 'error')} required">
	<label for="inspeccionDeProducto">
		<g:message code="liquidacionProvisionalDePlomoPlata.inspeccionDeProducto.label" default="Inspeccion De Producto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="inspeccionDeProducto" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'inspeccionDeProducto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoDeMolienda', 'error')} required">
	<label for="costoDeMolienda">
		<g:message code="liquidacionProvisionalDePlomoPlata.costoDeMolienda.label" default="Costo De Molienda" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoDeMolienda" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'costoDeMolienda')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'anticipoContraContrato', 'error')} required">
	<label for="anticipoContraContrato">
		<g:message code="liquidacionProvisionalDePlomoPlata.anticipoContraContrato.label" default="Anticipo Contra Contrato" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="anticipoContraContrato" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'anticipoContraContrato')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'transporteAPuerto', 'error')} required">
	<label for="transporteAPuerto">
		<g:message code="liquidacionProvisionalDePlomoPlata.transporteAPuerto.label" default="Transporte AP uerto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="transporteAPuerto" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'transporteAPuerto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'totalTransporteAPuerto', 'error')} required">
	<label for="totalTransporteAPuerto">
		<g:message code="liquidacionProvisionalDePlomoPlata.totalTransporteAPuerto.label" default="Total Transporte AP uerto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalTransporteAPuerto" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'totalTransporteAPuerto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'rollBack', 'error')} required">
	<label for="rollBack">
		<g:message code="liquidacionProvisionalDePlomoPlata.rollBack.label" default="Roll Back" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="rollBack" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'rollBack')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'totalRollBack', 'error')} required">
	<label for="totalRollBack">
		<g:message code="liquidacionProvisionalDePlomoPlata.totalRollBack.label" default="Total Roll Back" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalRollBack" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'totalRollBack')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'totalRetenciones', 'error')} required">
	<label for="totalRetenciones">
		<g:message code="liquidacionProvisionalDePlomoPlata.totalRetenciones.label" default="Total Retenciones" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="totalRetenciones" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'totalRetenciones')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'balanceProvisionalPagable', 'error')} required">
	<label for="balanceProvisionalPagable">
		<g:message code="liquidacionProvisionalDePlomoPlata.balanceProvisionalPagable.label" default="Balance Provisional Pagable" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="balanceProvisionalPagable" value="${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: 'balanceProvisionalPagable')}" required="" inputmode="decimal"/>
</div>

