<%@ page import="org.smart.parametros.ParametrosGenerales" %>



<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'mesesPagablesBonoCantidad', 'error')} required">
	<label for="mesesPagablesBonoCantidad">
		<g:message code="parametrosGenerales.mesesPagablesBonoCantidad.label" default="Meses Pagables Bono Cantidad" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="mesesPagablesBonoCantidad" type="number" min="0" value="${parametrosGeneralesInstance.mesesPagablesBonoCantidad}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'mesesPagablesBonoCalidad', 'error')} required">
	<label for="mesesPagablesBonoCalidad">
		<g:message code="parametrosGenerales.mesesPagablesBonoCalidad.label" default="Meses Pagables Bono Calidad" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="mesesPagablesBonoCalidad" type="number" min="0" value="${parametrosGeneralesInstance.mesesPagablesBonoCalidad}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'mesesPagablesBonoTransporte', 'error')} required">
	<label for="mesesPagablesBonoTransporte">
		<g:message code="parametrosGenerales.mesesPagablesBonoTransporte.label" default="Meses Pagables Bono Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="mesesPagablesBonoTransporte" type="number" min="0" value="${parametrosGeneralesInstance.mesesPagablesBonoTransporte}" required="" inputmode="numeric"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'leyMinimaPlataBonoCalidad', 'error')} required">
	<label for="leyMinimaPlataBonoCalidad">
		<g:message code="parametrosGenerales.leyMinimaPlataBonoCalidad.label" default="Ley Minima Plata Bono Calidad" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyMinimaPlataBonoCalidad" value="${fieldValue(bean: parametrosGeneralesInstance, field: 'leyMinimaPlataBonoCalidad')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'leyBajaZincBonoTransporte', 'error')} required">
	<label for="leyBajaZincBonoTransporte">
		<g:message code="parametrosGenerales.leyBajaZincBonoTransporte.label" default="Ley Baja Zinc Bono Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyBajaZincBonoTransporte" value="${fieldValue(bean: parametrosGeneralesInstance, field: 'leyBajaZincBonoTransporte')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'leyBajaPlomoBonoTransporte', 'error')} required">
	<label for="leyBajaPlomoBonoTransporte">
		<g:message code="parametrosGenerales.leyBajaPlomoBonoTransporte.label" default="Ley Baja Plomo Bono Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyBajaPlomoBonoTransporte" value="${fieldValue(bean: parametrosGeneralesInstance, field: 'leyBajaPlomoBonoTransporte')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'leyAltaZincBonoTransporte', 'error')} required">
	<label for="leyAltaZincBonoTransporte">
		<g:message code="parametrosGenerales.leyAltaZincBonoTransporte.label" default="Ley Alta Zinc Bono Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyAltaZincBonoTransporte" value="${fieldValue(bean: parametrosGeneralesInstance, field: 'leyAltaZincBonoTransporte')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'leyAltaPlomoBonoTransporte', 'error')} required">
	<label for="leyAltaPlomoBonoTransporte">
		<g:message code="parametrosGenerales.leyAltaPlomoBonoTransporte.label" default="Ley Alta Plomo Bono Transporte" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="leyAltaPlomoBonoTransporte" value="${fieldValue(bean: parametrosGeneralesInstance, field: 'leyAltaPlomoBonoTransporte')}" required="" inputmode="decimal"/>
</div>

<div>
    <h1 style="font-weight: bold">Costos de Manipuleo</h1>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'pesadaVaciada', 'error')} required">
	<label for="pesadaVaciada">
		<g:message code="parametrosGenerales.pesadaVaciada.label" default="Pesada Vaciada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pesadaVaciada" value="${fieldValue(bean: parametrosGeneralesInstance, field: 'pesadaVaciada')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'carguioMaquina', 'error')} required">
	<label for="carguioMaquina">
		<g:message code="parametrosGenerales.carguioMaquina.label" default="Carguio Maquina" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="carguioMaquina" value="${fieldValue(bean: parametrosGeneralesInstance, field: 'carguioMaquina')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'embolsadaArrumada', 'error')} required">
	<label for="embolsadaArrumada">
		<g:message code="parametrosGenerales.embolsadaArrumada.label" default="Embolsada Arrumada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="embolsadaArrumada" value="${fieldValue(bean: parametrosGeneralesInstance, field: 'embolsadaArrumada')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'soloComuneada', 'error')} required">
	<label for="soloComuneada">
		<g:message code="parametrosGenerales.soloComuneada.label" default="Solo Comuneada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="soloComuneada" value="${fieldValue(bean: parametrosGeneralesInstance, field: 'soloComuneada')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'soloVaciada', 'error')} required">
	<label for="soloVaciada">
		<g:message code="parametrosGenerales.soloVaciada.label" default="Solo Vaciada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="soloVaciada" value="${fieldValue(bean: parametrosGeneralesInstance, field: 'soloVaciada')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'soloPesada', 'error')} required">
	<label for="soloPesada">
		<g:message code="parametrosGenerales.soloPesada.label" default="Solo Pesada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="soloPesada" value="${fieldValue(bean: parametrosGeneralesInstance, field: 'soloPesada')}" required="" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: parametrosGeneralesInstance, field: 'soloEmbolsada', 'error')} required">
	<label for="soloEmbolsada">
		<g:message code="parametrosGenerales.soloEmbolsada.label" default="Solo Embolsada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="soloEmbolsada" value="${fieldValue(bean: parametrosGeneralesInstance, field: 'soloEmbolsada')}" required="" inputmode="decimal"/>
</div>

