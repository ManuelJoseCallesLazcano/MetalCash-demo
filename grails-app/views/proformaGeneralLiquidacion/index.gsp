
<%@ page import="org.socymet.proforma.ProformaGeneralLiquidacion" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<g:set var="entityName" value="${message(code: 'proformaGeneralLiquidacion.label', default: 'ProformaLiquidacion')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-proformaGeneralLiquidacion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
	<ul>
		<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
		<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
	</ul>
</div>
<div id="list-proformaGeneralLiquidacion" class="content scaffold-list" role="main">
	<h1><g:message code="default.list.label" args="[entityName]" /></h1>
	<g:if test="${flash.message}">
		<div class="message" role="status">${flash.message}</div>
	</g:if>
	<table>
		<thead>
		<tr>

			<g:sortableColumn property="numeroProformaLiquidacion" title="${message(code: 'proformaGeneralLiquidacion.numeroProformaLiquidacion.label', default: 'Numero Proforma Liquidacion')}" />

			<g:sortableColumn property="fechaProformaLiquidacion" title="${message(code: 'proformaGeneralLiquidacion.fechaProformaLiquidacion.label', default: 'Fecha Proforma Liquidacion')}" />

			<g:sortableColumn property="nombreProforma" title="${message(code: 'proformaGeneralLiquidacion.nombreProforma.label', default: 'Nombre Proforma')}" />

			<g:sortableColumn property="toneladasMetricasSecasFinales" title="${message(code: 'proformaGeneralLiquidacion.toneladasMetricasSecasFinales.label', default: 'Toneladas Metricas Secas Finales')}" />

			<g:sortableColumn property="leyPlomo" title="${message(code: 'proformaGeneralLiquidacion.leyPlomo.label', default: 'Ley Plomo')}" />

			<g:sortableColumn property="leyPlata" title="${message(code: 'proformaGeneralLiquidacion.leyPlata.label', default: 'Ley Plata')}" />

			<g:sortableColumn property="valorNetoTotalFinal" title="${message(code: 'proformaGeneralLiquidacion.valorNetoTotalFinal.label', default: 'Valor Neto Total Final')}" />

		</tr>
		</thead>
		<tbody>
		<g:each in="${proformaGeneralLiquidacionInstanceList}" status="i" var="proformaGeneralLiquidacionInstance">
			<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

				<td><g:link action="show" id="${proformaGeneralLiquidacionInstance.id}">${fieldValue(bean: proformaGeneralLiquidacionInstance, field: "numeroProformaLiquidacion")}</g:link></td>

				<td><g:formatDate date="${proformaGeneralLiquidacionInstance.fechaProformaLiquidacion}" /></td>

				<td>${fieldValue(bean: proformaGeneralLiquidacionInstance, field: "nombreProforma")}</td>

				<td>${fieldValue(bean: proformaGeneralLiquidacionInstance, field: "toneladasMetricasSecasFinales")}</td>

				<td>${fieldValue(bean: proformaGeneralLiquidacionInstance, field: "leyPlomo")}</td>

				<td>${fieldValue(bean: proformaGeneralLiquidacionInstance, field: "leyPlata")}</td>

				<td>${fieldValue(bean: proformaGeneralLiquidacionInstance, field: "valorNetoTotalFinal")}</td>

			</tr>
		</g:each>
		</tbody>
	</table>
	<div class="pagination">
		<g:paginate total="${proformaGeneralLiquidacionInstanceCount ?: 0}" />
	</div>
</div>
</body>
</html>
