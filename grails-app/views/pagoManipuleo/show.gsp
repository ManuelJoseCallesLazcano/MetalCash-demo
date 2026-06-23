
<%@ page import="org.socymet.cancelacion.PagoManipuleo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pagoManipuleo.label', default: 'PagoManipuleo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <style type="text/css" media="screen">
        th.ui-th-column div{
            white-space:normal !important;
            height:auto !important;
            padding:2px;
        }
        </style>
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="jquery.jgrowl.min.js" />
        <g:javascript src="notify.min.js" />
        <g:javascript src="NumerosALetras.js" />
        <script>
        $(document).ready(function() {
            $("#lotesAsignados").jqGrid({
                datatype: "local",
                height: 200,
                colNames: ["LOTE","recepcionId","FECHA REC.","P. BRUTO","TIPO MAT.","PESADA VACIADA","CARGUIO MAQUINA","EMBOLSADA ARRUMADA","SOLO COMUNEADA","SOLO VACIADA","SOLO PESADA","SOLO EMBOLSADA","COSTO MANIPULEO"],
                colModel:[ {name:'lote',index:'lote', width:70},
                    {name:'recepcionId',index:'recepcionId', width:5, hidden: true},
                    {name:'fechaDeRecepcion',index:'fechaDeRecepcion', width:70},
                    {name:'pesoBruto',index:'pesoBruto', width:60},
                    {name:'tipoDeMaterial',index:'tipoDeMaterial', width:80},
                    {name:'pesadaVaciada',index:'pesadaVaciada', width:70, edittype:"checkbox",editoptions: {value:"SI:NO"}, formatoptions: {disabled : false} },
                    {name:'carguioMaquina',index:'carguioMaquina', width:70, edittype:"checkbox",editoptions: {value:"SI:NO"}, formatoptions: {disabled : false} },
                    {name:'embolsadaArrumada',index:'embolsadaArrumada', width:70, edittype:"checkbox",editoptions: {value:"SI:NO"}, formatoptions: {disabled : false} },
                    {name:'soloComuneada',index:'soloComuneada', width:70, edittype:"checkbox",editoptions: {value:"SI:NO"}, formatoptions: {disabled : false} },
                    {name:'soloVaciada',index:'soloVaciada', width:70, edittype:"checkbox",editoptions: {value:"SI:NO"}, formatoptions: {disabled : false} },
                    {name:'soloPesada',index:'soloPesada', width:70, edittype:"checkbox",editoptions: {value:"SI:NO"}, formatoptions: {disabled : false} },
                    {name:'soloEmbolsada',index:'soloEmbolsada', width:70, edittype:"checkbox",editoptions: {value:"SI:NO"}, formatoptions: {disabled : false} },
                    {name:'costoManipuleo',index:'costoManipuleo', width:80} ],
                multiselect: false,
                caption: "LOTES A PAGAR"
            });
            var lotes=""
            var mydata = $("#lotes").val();
            if(mydata=="")
                mydata = [];
            else
                mydata = $.parseJSON(mydata);
            $("#lotesAsignados").jqGrid("clearGridData", true);

            for(var i=0;i<mydata.length;i++){
                $("#lotesAsignados").jqGrid('addRowData',i+1,mydata[i]);
            }
        });
        </script>
	</head>
	<body>
		<a href="#show-pagoManipuleo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-pagoManipuleo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list pagoManipuleo">
			
				<g:if test="${pagoManipuleoInstance?.numeroComprobante}">
				<li class="fieldcontain">
					<span id="numeroComprobante-label" class="property-label"><g:message code="pagoManipuleo.numeroComprobante.label" default="Numero Comprobante" /></span>
					
						<span class="property-value" aria-labelledby="numeroComprobante-label"><g:fieldValue bean="${pagoManipuleoInstance}" field="numeroComprobante"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoManipuleoInstance?.ci}">
				<li class="fieldcontain">
					<span id="ci-label" class="property-label"><g:message code="pagoManipuleo.ci.label" default="Ci" /></span>
					
						<span class="property-value" aria-labelledby="ci-label"><g:fieldValue bean="${pagoManipuleoInstance}" field="ci"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoManipuleoInstance?.nombreCobrador}">
				<li class="fieldcontain">
					<span id="nombreCobrador-label" class="property-label"><g:message code="pagoManipuleo.nombreCobrador.label" default="Nombre Cobrador" /></span>
					
						<span class="property-value" aria-labelledby="nombreCobrador-label"><g:fieldValue bean="${pagoManipuleoInstance}" field="nombreCobrador"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoManipuleoInstance?.fechaDePago}">
				<li class="fieldcontain">
					<span id="fechaDePago-label" class="property-label"><g:message code="pagoManipuleo.fechaDePago.label" default="Fecha De Pago" /></span>
					
						<span class="property-value" aria-labelledby="fechaDePago-label"><g:formatDate date="${pagoManipuleoInstance?.fechaDePago}" /></span>
					
				</li>
				</g:if>

                <div>
                    <h1 style="font-weight: bold">Lotes Asignados</h1>
                </div>
			
				<g:if test="${pagoManipuleoInstance?.deposito}">
				<li class="fieldcontain">
					<span id="deposito-label" class="property-label"><g:message code="pagoManipuleo.deposito.label" default="Deposito" /></span>
					
						<span class="property-value" aria-labelledby="deposito-label"><g:link controller="deposito" action="show" id="${pagoManipuleoInstance?.deposito?.id}">${pagoManipuleoInstance?.deposito?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:hiddenField name="lotes" value="${pagoManipuleoInstance?.lotes}" readonly="true" />

                <div style="width: 840px; margin-left: auto; margin-right: auto;">
                    <table id="lotesAsignados"></table>
                </div>
			
				<g:if test="${pagoManipuleoInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="pagoManipuleo.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${pagoManipuleoInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoManipuleoInstance?.totalPagable}">
				<li class="fieldcontain">
					<span id="totalPagable-label" class="property-label"><g:message code="pagoManipuleo.totalPagable.label" default="Total Pagable" /></span>
					
						<span class="property-value" aria-labelledby="totalPagable-label"><g:fieldValue bean="${pagoManipuleoInstance}" field="totalPagable"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoManipuleoInstance?.totalPagableLiteral}">
				<li class="fieldcontain">
					<span id="totalPagableLiteral-label" class="property-label"><g:message code="pagoManipuleo.totalPagableLiteral.label" default="Total Pagable Literal" /></span>
					
						<span class="property-value" aria-labelledby="totalPagableLiteral-label"><g:fieldValue bean="${pagoManipuleoInstance}" field="totalPagableLiteral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pagoManipuleoInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="pagoManipuleo.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${pagoManipuleoInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
			</ol>

            <fieldset class="buttons">
                <div style="float: left">
                    <g:form>
                        <g:hiddenField name="id" value="${pagoManipuleoInstance?.id}" />
                        <g:link class="edit" action="edit" id="${pagoManipuleoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    </g:form>
                </div>
                <div style="float: right">
                    <table>
                        <tr>
                            <td>
                                <g:jasperReport controller="pagoManipuleo" action="createReport" jasper="comprobante_pago_manipuleo" format="PDF" _format="PDF" name="ORDEN_PAGO_MANIPULEO_${pagoManipuleoInstance.numeroComprobante}">
                                    <input type="hidden" name="pagoManipuleoId" value="${pagoManipuleoInstance.id}" />
                                </g:jasperReport>
                            </td>
                            <td>
                                <g:jasperReport controller="pagoManipuleo" action="createReport" jasper="detalle_comprobante_pago_manipuleo" format="PDF" _format="PDF" name="DETALLE_ORDEN_PAGO_MANIPULEO_${pagoManipuleoInstance.numeroComprobante}">
                                    <input type="hidden" name="pagoId" value="${pagoManipuleoInstance.id}" />
                                </g:jasperReport>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
		</div>
	</body>
</html>
