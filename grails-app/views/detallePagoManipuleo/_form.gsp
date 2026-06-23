<%@ page import="org.socymet.cancelacion.DetallePagoManipuleo" %>



<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'pagoManipuleo', 'error')} required">
	<label for="pagoManipuleo">
		<g:message code="detallePagoManipuleo.pagoManipuleo.label" default="Pago Manipuleo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="pagoManipuleo" name="pagoManipuleo.id" from="${org.socymet.cancelacion.PagoManipuleo.list()}" optionKey="id" required="" value="${detallePagoManipuleoInstance?.pagoManipuleo?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'lote', 'error')} required">
	<label for="lote">
		<g:message code="detallePagoManipuleo.lote.label" default="Lote" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lote" required="" value="${detallePagoManipuleoInstance?.lote}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'fechaDeRecepcion', 'error')} required">
	<label for="fechaDeRecepcion">
		<g:message code="detallePagoManipuleo.fechaDeRecepcion.label" default="Fecha De Recepcion" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fechaDeRecepcion" precision="day"  value="${detallePagoManipuleoInstance?.fechaDeRecepcion}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'pesoBruto', 'error')} required">
	<label for="pesoBruto">
		<g:message code="detallePagoManipuleo.pesoBruto.label" default="Peso Bruto" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesoBruto" value="${fieldValue(bean: detallePagoManipuleoInstance, field: 'pesoBruto')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'tipoDeMaterial', 'error')} required">
	<label for="tipoDeMaterial">
		<g:message code="detallePagoManipuleo.tipoDeMaterial.label" default="Tipo De Material" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="tipoDeMaterial" required="" value="${detallePagoManipuleoInstance?.tipoDeMaterial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'pesadaVaciada', 'error')} required">
	<label for="pesadaVaciada">
		<g:message code="detallePagoManipuleo.pesadaVaciada.label" default="Pesada Vaciada" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="pesadaVaciada" required="" value="${detallePagoManipuleoInstance?.pesadaVaciada}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'carguioMaquina', 'error')} required">
	<label for="carguioMaquina">
		<g:message code="detallePagoManipuleo.carguioMaquina.label" default="Carguio Maquina" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="carguioMaquina" required="" value="${detallePagoManipuleoInstance?.carguioMaquina}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'embolsadaArrumada', 'error')} required">
	<label for="embolsadaArrumada">
		<g:message code="detallePagoManipuleo.embolsadaArrumada.label" default="Embolsada Arrumada" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="embolsadaArrumada" required="" value="${detallePagoManipuleoInstance?.embolsadaArrumada}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'soloComuneada', 'error')} required">
	<label for="soloComuneada">
		<g:message code="detallePagoManipuleo.soloComuneada.label" default="Solo Comuneada" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="soloComuneada" required="" value="${detallePagoManipuleoInstance?.soloComuneada}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'soloVaciada', 'error')} required">
	<label for="soloVaciada">
		<g:message code="detallePagoManipuleo.soloVaciada.label" default="Solo Vaciada" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="soloVaciada" required="" value="${detallePagoManipuleoInstance?.soloVaciada}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'soloPesada', 'error')} required">
	<label for="soloPesada">
		<g:message code="detallePagoManipuleo.soloPesada.label" default="Solo Pesada" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="soloPesada" required="" value="${detallePagoManipuleoInstance?.soloPesada}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'soloEmbolsada', 'error')} required">
	<label for="soloEmbolsada">
		<g:message code="detallePagoManipuleo.soloEmbolsada.label" default="Solo Embolsada" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="soloEmbolsada" required="" value="${detallePagoManipuleoInstance?.soloEmbolsada}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: detallePagoManipuleoInstance, field: 'costoDeManipuleo', 'error')} required">
	<label for="costoDeManipuleo">
		<g:message code="detallePagoManipuleo.costoDeManipuleo.label" default="Costo De Manipuleo" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="costoDeManipuleo" value="${fieldValue(bean: detallePagoManipuleoInstance, field: 'costoDeManipuleo')}" required="" inputmode="decimal"/>
</div>

