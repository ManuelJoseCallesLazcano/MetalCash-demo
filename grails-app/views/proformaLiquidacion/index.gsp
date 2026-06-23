
<%@ page import="org.socymet.proforma.ProformaLiquidacion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'proformaLiquidacion.label', default: 'ProformaLiquidacion')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-proformaLiquidacion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-proformaLiquidacion" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="numeroProformaLiquidacion" title="${message(code: 'proformaLiquidacion.numeroProformaLiquidacion.label', default: 'Numero Proforma Liquidacion')}" />
					
						<g:sortableColumn property="fechaProformaLiquidacion" title="${message(code: 'proformaLiquidacion.fechaProformaLiquidacion.label', default: 'Fecha Proforma Liquidacion')}" />
					
						<g:sortableColumn property="nombreProforma" title="${message(code: 'proformaLiquidacion.nombreProforma.label', default: 'Nombre Proforma')}" />
					
						<g:sortableColumn property="toneladasMetricasSecasFinales" title="${message(code: 'proformaLiquidacion.toneladasMetricasSecasFinales.label', default: 'Toneladas Metricas Secas Finales')}" />
					
						<g:sortableColumn property="leyPlomo" title="${message(code: 'proformaLiquidacion.leyPlomo.label', default: 'Ley Plomo')}" />
						
						<g:sortableColumn property="leyPlata" title="${message(code: 'proformaLiquidacion.leyPlata.label', default: 'Ley Plata')}" />
					
						<g:sortableColumn property="valorNetoTotalFinal" title="${message(code: 'proformaLiquidacion.valorNetoTotalFinal.label', default: 'Valor Neto Total Final')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${proformaLiquidacionInstanceList}" status="i" var="proformaLiquidacionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${proformaLiquidacionInstance.id}">${fieldValue(bean: proformaLiquidacionInstance, field: "numeroProformaLiquidacion")}</g:link></td>
					
						<td><g:formatDate date="${proformaLiquidacionInstance.fechaProformaLiquidacion}" /></td>
					
						<td>${fieldValue(bean: proformaLiquidacionInstance, field: "nombreProforma")}</td>
					
						<td>${fieldValue(bean: proformaLiquidacionInstance, field: "toneladasMetricasSecasFinales")}</td>
					
						<td>${fieldValue(bean: proformaLiquidacionInstance, field: "leyPlomo")}</td>

						<td>${fieldValue(bean: proformaLiquidacionInstance, field: "leyPlata")}</td>

						<td>${fieldValue(bean: proformaLiquidacionInstance, field: "valorNetoTotalFinal")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${proformaLiquidacionInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
