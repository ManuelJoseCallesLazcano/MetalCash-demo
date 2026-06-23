<%@ page import="org.socymet.org.socymet.reportes.BuscadorLotes" %>



<div class="fieldcontain ${hasErrors(bean: buscadorLotesInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="buscadorLotes.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${buscadorLotesInstance?.lote}"/>

</div>

<div class="fieldcontain required" style="display: none">
	<label for="recepcionId">
		%{--<g:message default="Lote ID" />--}%
		Recepcion ID
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="recepcionId" readonly="readonly"/>

</div>

<div class="fieldcontain required" style="display: none">
	<label for="controlCalidadId">
		Control Calidad ID
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="controlCalidadId" readonly="readonly"/>

</div>

<div class="fieldcontain required" style="display: none">
	<label for="liquidacionId">
		Liquidacion ID
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="liquidacionId" readonly="readonly"/>

</div>

<div class="fieldcontain required" style="display: none">
	<label for="anticipoId">
		Anticipo ID
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="anticipoId" readonly="readonly"/>

</div>

<div class="fieldcontain required" style="display: none">
	<label for="pagoTransporteId">
		Pago Transporte ID
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="pagoTransporteId" readonly="readonly"/>

</div>

<h1 style="font-weight: bold">Resultados:</h1>

<table class="center" style="width: 90%;">
	<thead>
	<tr>
		<th style="width: 25%">REGISTRO</th>
		<th style="width: 75%">DIRECCION</th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td class="fieldcontain required">
			<label style="width: 80%">Recepcion</label>
		</td>
		<td class="fieldcontain required">
			<div id="_recepcionId"><span></span></div>
		</td>
	</tr>

	<tr>
		<td class="fieldcontain required">
			<label style="width: 80%">Control de Calidad</label>
		</td>
		<td class="fieldcontain required">
			<div id="_controlCalidadId"><span></span></div>
		</td>
	</tr>

	<tr>
		<td class="fieldcontain required">
			<label style="width: 80%">Liquidacion</label>
		</td>
		<td class="fieldcontain required">
			<div id="_liquidacionId"><span></span></div>
		</td>
	</tr>

	<tr>
		<td class="fieldcontain required">
			<label style="width: 80%">Anticipo Contra Entrega</label>
		</td>
		<td class="fieldcontain required">
			<div id="_anticipoId"><span></span></div>
		</td>
	</tr>

	<tr>
		<td class="fieldcontain required">
			<label style="width: 80%">Pago De Transporte</label>
		</td>
		<td class="fieldcontain required">
			<div id="_pagoTransporteId"><span></span></div>
		</td>
	</tr>

	</tbody>
</table>
