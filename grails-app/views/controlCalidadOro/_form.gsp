<%@ page import="org.socymet.calidad.ControlCalidadOro" %>

<div id="_deposito" class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'deposito', 'error')} required" style="display: none">
	<label for="deposito">
		<g:message code="controlCalidadOro.deposito.label" default="Deposito" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}" optionKey="id" required="" value="${controlCalidadOroInstance?.deposito?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="controlCalidadOro.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${controlCalidadOroInstance?.lote}"/>
	<g:hiddenField name="recepcionDeOro.id" value="${controlCalidadOroInstance?.recepcionDeOro?.id}"/>
	<g:hiddenField name="empresa.id" value="${controlCalidadOroInstance?.empresa?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'nombreCliente', 'error')} required">
	<label for="nombreCliente">
		<g:message code="controlCalidadOro.nombreCliente.label" default="Nombre Cliente" />
		<span class="required-indicator">*</span>
	</label>
	%{--<g:textField name="nombreCliente" required="" value="${controlCalidadOroInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>--}%
	<g:textField name="nombreCliente" required="" value="${controlCalidadOroInstance?.recepcionDeOro?.cliente?.nombre}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="controlCalidadOro.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreEmpresa" required="" value="${controlCalidadOroInstance?.recepcionDeOro?.empresa?.toString()}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="controlCalidadOro.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="fechaDeRecepcion" required="" value="${controlCalidadOroInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'cantidadDeSacos', 'error')} required" style="display: none">
	<label for="cantidadDeSacos">
		<g:message code="controlCalidadOro.cantidadDeSacos.label" default="Cantidad De Sacos" />
		<span class="required-indicator">*</span>
	</label>
	%{--<g:field name="cantidadDeSacos" value="${fieldValue(bean: controlCalidadOroInstance, field: 'cantidadDeSacos')}" required="" class="amarillo" readonly="true"/>--}%
	<g:field name="cantidadDeSacos" value="${controlCalidadOroInstance?.recepcionDeOro?.cantidadDeSacos}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="controlCalidadOro.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	%{--<g:field name="pesoBruto" value="${fieldValue(bean: controlCalidadOroInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>--}%
	<g:field name="pesoBruto" value="${controlCalidadOroInstance?.recepcionDeOro?.pesoBruto}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'estadoDelLote', 'error')} required" style="display: none">
	<label for="estadoDelLote">
		<g:message code="controlCalidadOro.estadoDelLote.label" default="Estado Del Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="estadoDelLote" required="" value="${controlCalidadOroInstance?.recepcionDeOro?.estadoDelLote}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'nombreLaboratorio', 'error')} required">
	<label for="nombreLaboratorio">
		<g:message code="controlCalidadOro.nombreLaboratorio.label" default="Nombre Laboratorio" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreLaboratorio" required="" value="${controlCalidadOroInstance?.nombreLaboratorio}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'numeroAnalisis', 'error')} required">
	<label for="numeroAnalisis">
		<g:message code="controlCalidadOro.numeroAnalisis.label" default="Numero Analisis" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="numeroAnalisis" inputmode="numeric" required="" value="${controlCalidadOroInstance?.numeroAnalisis}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'fechaAnalisis', 'error')} required">
	<label for="fechaAnalisis">
		<g:message code="controlCalidadOro.fechaAnalisis.label" default="Fecha Analisis" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaAnalisis" precision="day"  value="${controlCalidadOroInstance?.fechaAnalisis}"  />
</div>

<h1 style="font-weight: bold">Detalle de Leyes</h1>

<table class="center" style="width: 70%;">
	<thead>
	<tr>
		<th style="width: 40%">ELEMENTO</th>
		<th style="width: 20%">LEY EMPRESA</th>
	</tr>
	</thead>
	<tbody>
	<tr style="display: none">
		<td class="fieldcontain required">
			<label for="porcentajeMermaPromexbol">
				<g:message code="controlCalidadOro.porcentajeMermaPromexbol.label" default="Merma" />
			</label>
		</td>
		<td class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
			<g:field name="porcentajeMermaPromexbol" value="${fieldValue(bean: controlCalidadOroInstance, field: 'porcentajeMermaPromexbol')}" required="" inputmode="decimal"/>
		</td>
	</tr>

	<tr>
		<td class="fieldcontain required">
			<label for="porcentajeOroPromexbol">
				<g:message code="controlCalidadOro.porcentajeOroPromexbol.label" default="Oro" />
			</label>
		</td>
		<td class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'porcentajeOroPromexbol', 'error')} required">
			<g:field name="porcentajeOroPromexbol" value="${fieldValue(bean: controlCalidadOroInstance, field: 'porcentajeOroPromexbol')}" required="" inputmode="decimal"/>
		</td>
	</tr>

	<tr>
		<td class="fieldcontain required">
			<label for="porcentajeHumedadPromexbol">
				<g:message code="controlCalidadOro.porcentajeHumedadPromexbol.label" default="Humedad" />
			</label>
		</td>
		<td class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
			<g:field name="porcentajeHumedadPromexbol" value="${fieldValue(bean: controlCalidadOroInstance, field: 'porcentajeHumedadPromexbol')}" required="" inputmode="decimal"/>
		</td>
	</tr>
	</tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: controlCalidadOroInstance, field: 'observaciones', 'error')} " style="display: none">
	<label for="observaciones">
		<g:message code="controlCalidadOro.observaciones.label" default="Observaciones" />

	</label>
	<g:textField name="observaciones" value="${controlCalidadOroInstance?.observaciones}" size="100"/>
</div>

