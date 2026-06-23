<%@ page import="org.socymet.org.socymet.reportes.ReporteGraficoTotalLiquidado" %>



<h1 style="font-weight: bold">Listar por:</h1>

<table style="width: 500px;" class="center">
    <tbody>
    <tr>
        <td style="width: 10px"><input type="radio" id="fechas" name="myGroup" value="1" checked="checked" /></td>
        <td style="font-weight: bold">Fechas</td>
    </tr>
    <tr>
        <td style="width: 10px"><input type="radio" id="fechasEmpresa" name="myGroup" value="2" /></td>
        <td style="font-weight: bold">Fechas y Empresa</td>
    </tr>
    </tbody>
</table>

<h1 style="font-weight: bold">Parametros de busqueda:</h1>

<div id="_empresa" class="fieldcontain ${hasErrors(bean: reporteGraficoTotalLiquidadoInstance, field: 'empresa', 'error')} " style="display: none">
    <label for="empresa">
        <g:message code="reporteGraficoCantidad.empresa.label" default="Empresa" />

    </label>
    <g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteGraficoTotalLiquidadoInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteGraficoTotalLiquidadoInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reporteGraficoTotalLiquidado.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Complejo','Plomo-Plata','Zinc-Plata']}" value="${reporteGraficoTotalLiquidadoInstance?.elemento}" valueMessagePrefix="reporteGraficoTotalLiquidado.elemento" noSelection="['': '']"/>
</div>

<div id="container" style="height: 400px; min-width: 310px"></div>