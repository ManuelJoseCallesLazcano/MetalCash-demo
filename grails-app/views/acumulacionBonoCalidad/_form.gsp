<%@ page import="org.socymet.cancelacion.AcumulacionBonoCalidad" %>



<div class="fieldcontain ${hasErrors(bean: acumulacionBonoCalidadInstance, field: 'pagoBonoCalidad', 'error')} required">
	<label for="pagoBonoCalidad">
		<g:message code="acumulacionBonoCalidad.pagoBonoCalidad.label" default="Pago Bono Calidad" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="pagoBonoCalidad" name="pagoBonoCalidad.id" from="${org.socymet.cancelacion.PagoBonoCalidad.list()}" optionKey="id" required="" value="${acumulacionBonoCalidadInstance?.pagoBonoCalidad?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoCalidadInstance, field: 'fecha', 'error')} required">
	<label for="fecha">
		<g:message code="acumulacionBonoCalidad.fecha.label" default="Fecha" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="fecha" precision="day"  value="${acumulacionBonoCalidadInstance?.fecha}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoCalidadInstance, field: 'tipoSeleccion', 'error')} required">
	<label for="tipoSeleccion">
		<g:message code="acumulacionBonoCalidad.tipoSeleccion.label" default="Tipo Seleccion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="tipoSeleccion" required="" value="${acumulacionBonoCalidadInstance?.tipoSeleccion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoCalidadInstance, field: 'cliente', 'error')} ">
	<label for="cliente">
		<g:message code="acumulacionBonoCalidad.cliente.label" default="Cliente" />
		
	</label>
	<g:select id="cliente" name="cliente.id" from="${org.socymet.proveedor.Cliente.list()}" optionKey="id" value="${acumulacionBonoCalidadInstance?.cliente?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoCalidadInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="acumulacionBonoCalidad.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${acumulacionBonoCalidadInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoCalidadInstance, field: 'cuadrilla', 'error')} ">
	<label for="cuadrilla">
		<g:message code="acumulacionBonoCalidad.cuadrilla.label" default="Cuadrilla" />
		
	</label>
	<g:select id="cuadrilla" name="cuadrilla.id" from="${org.socymet.proveedor.Cuadrilla.list()}" optionKey="id" value="${acumulacionBonoCalidadInstance?.cuadrilla?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: acumulacionBonoCalidadInstance, field: 'cantidadAcumulada', 'error')} required">
	<label for="cantidadAcumulada">
		<g:message code="acumulacionBonoCalidad.cantidadAcumulada.label" default="Cantidad Acumulada" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cantidadAcumulada" value="${fieldValue(bean: acumulacionBonoCalidadInstance, field: 'cantidadAcumulada')}" required="" inputmode="decimal"/>
</div>

