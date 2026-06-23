<%@ page import="org.socymet.org.socymet.reportes.PresupuestoLotesPorPagar" %>



<div class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="presupuestoLotesPorPagar.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Complejo']}" value="${presupuestoLotesPorPagarInstance?.elemento}" valueMessagePrefix="presupuestoLotesPorPagar.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'tablaCotizacionEstano', 'error')} ">
	<label for="tablaCotizacionEstano">
		<g:message code="presupuestoLotesPorPagar.tablaCotizacionEstano.label" default="Tabla Cotizacion Estano" />
		
	</label>
	<g:select id="tablaCotizacionEstano" name="tablaCotizacionEstano.id" from="${org.socymet.cotizaciones.TablaCotizacionEstano.list()}" optionKey="id" value="${presupuestoLotesPorPagarInstance?.tablaCotizacionEstano?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'tablaCotizacionPlata', 'error')} ">
	<label for="tablaCotizacionPlata">
		<g:message code="presupuestoLotesPorPagar.tablaCotizacionPlata.label" default="Tabla Cotizacion Plata" />
		
	</label>
	<g:select id="tablaCotizacionPlata" name="tablaCotizacionPlata.id" from="${org.socymet.cotizaciones.TablaCotizacionPlata.list()}" optionKey="id" value="${presupuestoLotesPorPagarInstance?.tablaCotizacionPlata?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'tablaCotizacionAntimonio', 'error')} ">
	<label for="tablaCotizacionAntimonio">
		<g:message code="presupuestoLotesPorPagar.tablaCotizacionAntimonio.label" default="Tabla Cotizacion Antimonio" />
		
	</label>
	<g:select id="tablaCotizacionAntimonio" name="tablaCotizacionAntimonio.id" from="${org.socymet.cotizaciones.TablaCotizacionAntimonio.list()}" optionKey="id" value="${presupuestoLotesPorPagarInstance?.tablaCotizacionAntimonio?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'tablaCotizacionWolfran', 'error')} ">
	<label for="tablaCotizacionWolfran">
		<g:message code="presupuestoLotesPorPagar.tablaCotizacionWolfran.label" default="Tabla Cotizacion Wolfran" />
		
	</label>
	<g:select id="tablaCotizacionWolfran" name="tablaCotizacionWolfran.id" from="${org.socymet.cotizaciones.TablaCotizacionWolfran.list()}" optionKey="id" value="${presupuestoLotesPorPagarInstance?.tablaCotizacionWolfran?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="presupuestoLotesPorPagar.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${presupuestoLotesPorPagarInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="presupuestoLotesPorPagar.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${presupuestoLotesPorPagarInstance?.fechaInicial}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="presupuestoLotesPorPagar.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${presupuestoLotesPorPagarInstance?.fechaFinal}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'loteInicial', 'error')} ">
	<label for="loteInicial">
		<g:message code="presupuestoLotesPorPagar.loteInicial.label" default="Lote Inicial" />
		
	</label>
	<g:textField name="loteInicial" inputmode="numeric" value="${presupuestoLotesPorPagarInstance?.loteInicial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: presupuestoLotesPorPagarInstance, field: 'loteFinal', 'error')} ">
	<label for="loteFinal">
		<g:message code="presupuestoLotesPorPagar.loteFinal.label" default="Lote Final" />
		
	</label>
	<g:textField name="loteFinal" inputmode="numeric" value="${presupuestoLotesPorPagarInstance?.loteFinal}"/>
</div>

