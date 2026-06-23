<%@ page import="org.socymet.calidad.ControlCalidadEstano" %>


<div class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="controlCalidadEstano.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${controlCalidadEstanoInstance?.lote}"/>
	<g:hiddenField name="recepcionDeEstano.id" value="${controlCalidadEstanoInstance?.recepcionDeEstano?.id}"/>
	<g:hiddenField name="empresa.id" value="${controlCalidadEstanoInstance?.empresa?.id}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'nombreCliente', 'error')} required">
	<label for="nombreCliente">
		<g:message code="controlCalidadEstano.nombreCliente.label" default="Nombre Cliente" />
		<span class="required-indicator">*</span>
	</label>
	%{--<g:textField name="nombreCliente" required="" value="${controlCalidadEstanoInstance?.nombreCliente}" class="amarillo" size="50" readonly="true"/>--}%
	<g:textField name="nombreCliente" required="" value="${controlCalidadEstanoInstance?.recepcionDeEstano?.cliente?.toString()}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'nombreEmpresa', 'error')} required">
	<label for="nombreEmpresa">
		<g:message code="controlCalidadEstano.nombreEmpresa.label" default="Nombre Empresa" />
		<span class="required-indicator">*</span>
	</label>
	%{--<g:textField name="nombreEmpresa" required="" value="${controlCalidadEstanoInstance?.nombreEmpresa}" class="amarillo" size="50" readonly="true"/>--}%
	<g:textField name="nombreEmpresa" required="" value="${controlCalidadEstanoInstance?.recepcionDeEstano?.empresa?.toString()}" class="amarillo" size="50" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="controlCalidadEstano.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="fechaDeRecepcion" required="" value="${controlCalidadEstanoInstance?.fechaDeRecepcion}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'cantidadDeSacos', 'error')} required">
	<label for="cantidadDeSacos">
		<g:message code="controlCalidadEstano.cantidadDeSacos.label" default="Cantidad De Sacos" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadDeSacos" value="${fieldValue(bean: controlCalidadEstanoInstance, field: 'cantidadDeSacos')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="controlCalidadEstano.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: controlCalidadEstanoInstance, field: 'pesoBruto')}" required="" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'estadoDelLote', 'error')} required">
	<label for="estadoDelLote">
		<g:message code="controlCalidadEstano.estadoDelLote.label" default="Estado Del Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="estadoDelLote" required="" value="${controlCalidadEstanoInstance?.estadoDelLote}" class="amarillo" readonly="true"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'nombreLaboratorio', 'error')} required">
	<label for="nombreLaboratorio">
		<g:message code="controlCalidadEstano.nombreLaboratorio.label" default="Nombre Laboratorio" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreLaboratorio" required="" value="${controlCalidadEstanoInstance?.nombreLaboratorio}" size="50"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'numeroAnalisis', 'error')} required">
	<label for="numeroAnalisis">
		<g:message code="controlCalidadEstano.numeroAnalisis.label" default="Numero Analisis" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="numeroAnalisis" inputmode="numeric" required="" value="${controlCalidadEstanoInstance?.numeroAnalisis}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'fechaAnalisis', 'error')} required">
	<label for="fechaAnalisis">
		<g:message code="controlCalidadEstano.fechaAnalisis.label" default="Fecha Analisis" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaAnalisis" precision="day"  value="${controlCalidadEstanoInstance?.fechaAnalisis}"  />
</div>

<h1 style="font-weight: bold">Detalle de Leyes</h1>

<table class="center" style="width: 70%;">
	<thead>
	<tr>
		<th style="width: 30%">ELEMENTO</th>
		<th style="width: 70%">LEY EMPRESA</th>
	</tr>
	</thead>
	<tbody>
	<tr>
		<td class="fieldcontain required">
			<label for="porcentajeMermaPromexbol">
				<g:message code="controlCalidadEstano.porcentajeMermaPromexbol.label" default="Merma" />
			</label>
		</td>
		<td class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'porcentajeMermaPromexbol', 'error')} required">
			<g:field name="porcentajeMermaPromexbol" value="${fieldValue(bean: controlCalidadEstanoInstance, field: 'porcentajeMermaPromexbol')}" required="" readonly="readonly"/>
		</td>
	</tr>

	<tr>
		<td class="fieldcontain required">
			<label for="porcentajeEstanoPromexbol">
				<g:message code="controlCalidadEstano.porcentajeEstanoPromexbol.label" default="Estano" />
			</label>
		</td>

		<td class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'porcentajeEstanoPromexbol', 'error')} required">
			<g:field name="porcentajeEstanoPromexbol" value="${fieldValue(bean: controlCalidadEstanoInstance, field: 'porcentajeEstanoPromexbol')}" required="" inputmode="decimal"/>
		</td>
	</tr>

	<tr>
		<td class="fieldcontain required">
			<label for="porcentajeHumedadPromexbol">
				<g:message code="controlCalidadEstano.porcentajeHumedadPromexbol.label" default="Humedad" />
			</label>
		</td>
		<td class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'porcentajeHumedadPromexbol', 'error')} required">
			<g:field name="porcentajeHumedadPromexbol" value="${fieldValue(bean: controlCalidadEstanoInstance, field: 'porcentajeHumedadPromexbol')}" required="" inputmode="decimal"/>
		</td>
	</tr>
	</tbody>
</table>

<div class="fieldcontain ${hasErrors(bean: controlCalidadEstanoInstance, field: 'observaciones', 'error')} ">
	<label for="observaciones">
		<g:message code="controlCalidadEstano.observaciones.label" default="Observaciones" />

	</label>
	<g:textField name="observaciones" value="${controlCalidadEstanoInstance?.observaciones}" size="90"/>

</div>

