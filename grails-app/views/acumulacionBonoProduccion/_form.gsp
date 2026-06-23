<%@ page import="org.socymet.cancelacion.AcumulacionBonoProduccion" %>



<div class="fieldcontain ${hasErrors(bean: acumulacionBonoProduccionInstance, field: 'pagoBonoProduccion', 'error')} required">
	<label for="pagoBonoProduccion">
		<g:message code="acumulacionBonoProduccion.pagoBonoProduccion.label" default="Pago Bono Produccion" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="pagoBonoProduccion" name="pagoBonoProduccion.id" from="${org.socymet.cancelacion.PagoBonoProduccion.list()}" optionKey="id" required="" value="${acumulacionBonoProduccionInstance?.pagoBonoProduccion?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoProduccionInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="acumulacionBonoProduccion.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${acumulacionBonoProduccionInstance?.fecha}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoProduccionInstance, field: 'tipoSeleccion', 'error')} required">
	<label for="tipoSeleccion">
		<g:message code="acumulacionBonoProduccion.tipoSeleccion.label" default="Tipo Seleccion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="tipoSeleccion" required="" value="${acumulacionBonoProduccionInstance?.tipoSeleccion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoProduccionInstance, field: 'cliente', 'error')} ">
	<label for="cliente">
		<g:message code="acumulacionBonoProduccion.cliente.label" default="Cliente" />
		
	</label>
	<g:select id="cliente" name="cliente.id" from="${org.socymet.proveedor.Cliente.list()}" optionKey="id" value="${acumulacionBonoProduccionInstance?.cliente?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoProduccionInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="acumulacionBonoProduccion.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${acumulacionBonoProduccionInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoProduccionInstance, field: 'cuadrilla', 'error')} ">
	<label for="cuadrilla">
		<g:message code="acumulacionBonoProduccion.cuadrilla.label" default="Cuadrilla" />
		
	</label>
	<g:select id="cuadrilla" name="cuadrilla.id" from="${org.socymet.proveedor.Cuadrilla.list()}" optionKey="id" value="${acumulacionBonoProduccionInstance?.cuadrilla?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoProduccionInstance, field: 'cantidadAcumulada', 'error')} required">
	<label for="cantidadAcumulada">
		<g:message code="acumulacionBonoProduccion.cantidadAcumulada.label" default="Cantidad Acumulada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadAcumulada" value="${fieldValue(bean: acumulacionBonoProduccionInstance, field: 'cantidadAcumulada')}" required="" inputmode="decimal"/>
</div>

