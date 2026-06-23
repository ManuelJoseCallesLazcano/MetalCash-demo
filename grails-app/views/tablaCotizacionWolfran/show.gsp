
<%@ page import="org.socymet.cotizaciones.TablaCotizacionWolfran" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tablaCotizacionWolfran.label', default: 'TablaCotizacionWolfran')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <script>
            //ES NECESARIO HACER ESTO PARA QUE LA COLUMNA PRECIO NO SEA EDITABLE
            //LA CAPACIDAD DE EDICION SOLO DEBE SER UTIL PARA create Y edit
            $(document).ready(function () {
                jQuery("#list4").jqGrid({
                    datatype: "local",
                    height: 300,
                    colNames: ["LEY","PRECIO $us"],
                    colModel:[ {name:'LEY',index:'LEY', width:100},
                        {name:'PRECIO',index:'PRECIO', width:100, editable: false}],
                    multiselect: false,
                    cellEdit: true,
                    cellsubmit: 'clientArray',
                    caption: "TABLA DE COTIZACIONES",
                });

                var mydata = $("#tablaDeCotizaciones").val();
                mydata = $.parseJSON(mydata);
                for(var i=0;i<=mydata.length;i++)
                    jQuery("#list4").jqGrid('addRowData',i+1,mydata[i]);
            });
        </script>
	</head>
	<body>
		<a href="#show-tablaCotizacionWolfran" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tablaCotizacionWolfran" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list tablaCotizacionWolfran">
			
				<g:if test="${tablaCotizacionWolfranInstance?.nombreDeTabla}">
				<li class="fieldcontain">
					<span id="nombreDeTabla-label" class="property-label"><g:message code="tablaCotizacionWolfran.nombreDeTabla.label" default="Nombre De Tabla" /></span>
					
						<span class="property-value" aria-labelledby="nombreDeTabla-label"><g:fieldValue bean="${tablaCotizacionWolfranInstance}" field="nombreDeTabla"/></span>
					
				</li>
				</g:if>

                <g:hiddenField name="tablaDeCotizaciones" value="${tablaCotizacionWolfranInstance?.tablaDeCotizaciones}"/>

                <br />
                <div style="width: 300px; margin-left: auto; margin-right: auto;"><table id="list4"></table></div>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${tablaCotizacionWolfranInstance?.id}" />
					<g:link class="edit" action="edit" id="${tablaCotizacionWolfranInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
