
<%@ page import="org.socymet.caja.CierreCaja" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cierreCaja.label', default: 'CierreCaja')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
		<style>
		table{
			font-size: 10px;
		}
		</style>
		<g:javascript src="jquery-1.10.1.min.js" />
		<g:javascript src="i18n/grid.locale-es.js" />
		<g:javascript src="jquery.jqGrid.min.js" />
		<g:javascript src="jquery-ui-1.10.3.custom.min.js" />
		<g:javascript src="jquery.jgrowl.min.js" />
		<g:javascript src="notify.min.js" />
		<g:javascript src="NumerosALetras.js" />
		<g:javascript src="accounting.min.js" />
		<script>
			$(document).ready(function() {
				$("#detalleTabla").jqGrid({
					datatype: "local",
					height: 300,
					colNames: ["movimientoCajaId","FECHA","ingresoId","egresoId","CI","NOMBRE","DETALLE","DEBE","HABER","SALDO","cajaId","usuarioId"],
					colModel:[
						{name:'movimientoCajaId',index:'movimientoCajaId', width:100, hidden: true},
						{name:'fechaMovimiento',index:'fechaMovimiento', width:120},
						{name:'ingresoId',index:'ingresoId', width:100, hidden: true},
						{name:'egresoId',index:'egresoId', width:100, hidden: true},
						{name:'ci',index:'ci', width:80},
						{name:'nombre',index:'nombre', width:150},
						{name:'concepto',index:'concepto', width:200},
						{name:'debe',index:'debe', width:70, align: "right"},
						{name:'haber',index:'haber', width:70, align: "right"},
						{name:'saldo',index:'saldo', width:70, align: "right"},
						{name:'cajaId',index:'cajaId', width:100, hidden: true},
						{name:'usuarioId',index:'usuarioId', width:100, hidden: true}
					],
					multiselect: false,
					rownumbers: true,
					caption: "MOVIMIENTOS A CONSOLIDAR"
				});

				crearTabla();

				function crearTabla(){
					var objeto=null;
					var mydata = $("#detalle").val();
					if(mydata=="")
						mydata = [];
					else
						mydata = $.parseJSON(mydata);
					$("#detalleTabla").jqGrid("clearGridData", true);

					if(mydata.length>0){
						for(var i=0;i<mydata.length;i++){
							objeto = mydata[i];
							$("#detalleTabla").jqGrid('addRowData',i+1,objeto);
						}
					}
				}
			});
		</script>
	</head>
	<body>
		<a href="#show-cierreCaja" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-cierreCaja" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list cierreCaja">
			
				<g:if test="${cierreCajaInstance?.numeroCierreCaja}">
				<li class="fieldcontain">
					<span id="numeroCierreCaja-label" class="property-label"><g:message code="cierreCaja.numeroCierreCaja.label" default="Numero Cierre Caja" /></span>

					<span class="property-value" aria-labelledby="numeroCierreCaja-label"><g:formatNumber number="${cierreCajaInstance.numeroCierreCaja}" format="000000"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaInstance?.fechaCierreCaja}">
				<li class="fieldcontain">
					<span id="fechaCierreCaja-label" class="property-label"><g:message code="cierreCaja.fechaCierreCaja.label" default="Fecha Cierre Caja" /></span>
					
						<span class="property-value" aria-labelledby="fechaCierreCaja-label"><g:formatDate date="${cierreCajaInstance?.fechaCierreCaja}" /></span>
					
				</li>
				</g:if>
			
				%{--<g:if test="${cierreCajaInstance?.usuario}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="usuario-label" class="property-label"><g:message code="cierreCaja.usuario.label" default="Usuario" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="usuario-label"><g:link controller="secUser" action="show" id="${cierreCajaInstance?.usuario?.id}">${cierreCajaInstance?.usuario?.encodeAsHTML()}</g:link></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%

				<h1 style="font-weight: bold">Movimientos de Caja a consolidarse</h1>

				<div style="width: 750px; margin-left: auto; margin-right: auto;">
					<table id="detalleTabla"></table>
				</div>

				<div class="fieldcontain ${hasErrors(bean: cierreCajaInstance, field: 'detalle', 'error')} required" style="display: none">
					<label for="detalle">
						<g:message code="cierreCaja.detalle.label" default="Detalle" />
						<span class="required-indicator">*</span>
					</label>
					<g:textField name="detalle" required="" value="${cierreCajaInstance?.detalle}" size="90" readonly="readonly"/>

				</div>
			
				%{--<g:if test="${cierreCajaInstance?.detalle}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="detalle-label" class="property-label"><g:message code="cierreCaja.detalle.label" default="Detalle" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="detalle-label"><g:fieldValue bean="${cierreCajaInstance}" field="detalle"/></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			
				<g:if test="${cierreCajaInstance?.debeTotal}">
				<li class="fieldcontain">
					<span id="debeTotal-label" class="property-label"><g:message code="cierreCaja.debeTotal.label" default="Debe Total" /></span>
					
						<span class="property-value" aria-labelledby="debeTotal-label"><g:fieldValue bean="${cierreCajaInstance}" field="debeTotal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaInstance?.haberTotal}">
				<li class="fieldcontain">
					<span id="haberTotal-label" class="property-label"><g:message code="cierreCaja.haberTotal.label" default="Haber Total" /></span>
					
						<span class="property-value" aria-labelledby="haberTotal-label"><g:fieldValue bean="${cierreCajaInstance}" field="haberTotal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaInstance?.saldoTotal}">
				<li class="fieldcontain">
					<span id="saldoTotal-label" class="property-label"><g:message code="cierreCaja.saldoTotal.label" default="Saldo Total" /></span>
					
						<span class="property-value" aria-labelledby="saldoTotal-label"><g:fieldValue bean="${cierreCajaInstance}" field="saldoTotal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cierreCajaInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="cierreCaja.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${cierreCajaInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
				%{--<g:if test="${cierreCajaInstance?.dateCreated}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="dateCreated-label" class="property-label"><g:message code="cierreCaja.dateCreated.label" default="Date Created" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${cierreCajaInstance?.dateCreated}" /></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			
				%{--<g:if test="${cierreCajaInstance?.lastUpdated}">--}%
				%{--<li class="fieldcontain">--}%
					%{--<span id="lastUpdated-label" class="property-label"><g:message code="cierreCaja.lastUpdated.label" default="Last Updated" /></span>--}%
					%{----}%
						%{--<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${cierreCajaInstance?.lastUpdated}" /></span>--}%
					%{----}%
				%{--</li>--}%
				%{--</g:if>--}%
			
			</ol>
			%{--<g:form url="[resource:cierreCajaInstance, action:'delete']" method="DELETE">--}%
				%{--<fieldset class="buttons">--}%
					%{--<sec:ifAnyGranted roles="ROLE_ADMIN">--}%
						%{--<g:link class="edit" action="edit" resource="${cierreCajaInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
						%{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
					%{--</sec:ifAnyGranted>--}%
				%{--</fieldset>--}%
			%{--</g:form>--}%

			<fieldset class="buttons">
				<div style="float: left">
					<g:form>
						<g:hiddenField name="id" value="${cierreCajaInstance?.id}" />

					%{--<sec:ifAnyGranted roles="ROLE_ADMIN">--}%
					%{--<g:link class="edit" action="edit" id="${cierreCajaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
					%{--<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
					%{--</sec:ifAnyGranted>--}%
					</g:form>
				</div>
				<div style="float: right">
					<table>
						<tr>
							<td>
								<g:jasperReport controller="cierreCaja" action="createReport" jasper="cierre_caja" format="PDF" _format="PDF" name="REPORTE_CIERRE_CAJA_${cierreCajaInstance.numeroCierreCaja}">
									<input type="hidden" name="cierreCajaId" value="${cierreCajaInstance.id}" />
								</g:jasperReport>

								%{--<g:jasperReport controller="cierreCaja" action="createReport" jasper="reporte_cierre_caja_egresos" format="PDF" _format="PDF" name="REPORTE_CIERRE_CAJA_RESUMEN_EGRESOS_${cierreCajaInstance.numeroCierreCaja}">--}%
									%{--<input type="hidden" name="cierreCajaId" value="${cierreCajaInstance.id}" />--}%
								%{--</g:jasperReport>--}%
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
		</div>
	</body>
</html>
