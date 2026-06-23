<%@ page import="org.socymet.org.socymet.reportes.ReporteGraficoCantidad" %>

<div id="_empresa" class="fieldcontain ${hasErrors(bean: reporteGraficoCantidadInstance, field: 'empresa', 'error')} " style="display: none">
	<label for="empresa">
		<g:message code="reporteGraficoCantidad.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteGraficoCantidadInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteGraficoCantidadInstance, field: 'elemento', 'error')} " style="display: none">
	<label for="elemento">
		<g:message code="reporteGraficoCantidad.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Complejo']}" value="${reporteGraficoCantidadInstance?.elemento}" valueMessagePrefix="reporteGraficoCantidad.elemento" />
</div>

<g:hiddenField name="empresas" size="70"/>
<g:hiddenField name="valoresNetos" size="70"/>
<g:hiddenField name="pesosNetos" size="70"/>
<g:hiddenField name="leyesZinc" size="70"/>
<g:hiddenField name="leyesPlomo" size="70"/>
<g:hiddenField name="leyesPlata" size="70"/>

<div id="container" style="height: 700px; min-width: 310px"></div>