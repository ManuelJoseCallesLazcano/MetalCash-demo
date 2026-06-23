<%@ page import="org.socymet.org.socymet.reportes.ReporteGraficoAcumuladoEmpresaCliente" %>



<div class="fieldcontain ${hasErrors(bean: reporteGraficoAcumuladoEmpresaClienteInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="reporteGraficoAcumuladoEmpresaCliente.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" required="" value="${reporteGraficoAcumuladoEmpresaClienteInstance?.empresa?.id}" class="many-to-one"/>

</div>

<g:hiddenField name="empresas" size="70"/>
<g:hiddenField name="valoresNetos" size="70"/>
<g:hiddenField name="pesosNetos" size="70"/>
<g:hiddenField name="leyesZinc" size="70"/>
<g:hiddenField name="leyesPlomo" size="70"/>
<g:hiddenField name="leyesPlata" size="70"/>

%{--<div id="container" style="height: 700px; min-width: 310px"></div>--}%
<div id="container" style="height: 12000px; min-width: 310px"></div>