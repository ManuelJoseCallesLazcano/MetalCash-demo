
<%@ page import="org.socymet.cotizaciones.TablaCotizacionPlata" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tablaCotizacionPlata.label', default: 'TablaCotizacionPlata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <g:javascript src="cotizaciones/tablaCotizacionPlata.js" />
	</head>
	<body>
		<a href="#show-tablaCotizacionPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tablaCotizacionPlata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list tablaCotizacionPlata">
			
				<g:if test="${tablaCotizacionPlataInstance?.nombreDeTabla}">
				<li class="fieldcontain">
					<span id="nombreDeTabla-label" class="property-label"><g:message code="tablaCotizacionPlata.nombreDeTabla.label" default="Nombre De Tabla" /></span>
					
						<span class="property-value" aria-labelledby="nombreDeTabla-label"><g:fieldValue bean="${tablaCotizacionPlataInstance}" field="nombreDeTabla"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tablaCotizacionPlataInstance?.cotizacionInicial}">
				<li class="fieldcontain">
					<span id="cotizacionInicial-label" class="property-label"><g:message code="tablaCotizacionPlata.cotizacionInicial.label" default="Cotizacion Inicial" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionInicial-label"><g:fieldValue bean="${tablaCotizacionPlataInstance}" field="cotizacionInicial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tablaCotizacionPlataInstance?.cotizacionFinal}">
				<li class="fieldcontain">
					<span id="cotizacionFinal-label" class="property-label"><g:message code="tablaCotizacionPlata.cotizacionFinal.label" default="Cotizacion Final" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionFinal-label"><g:fieldValue bean="${tablaCotizacionPlataInstance}" field="cotizacionFinal"/></span>
					
				</li>
				</g:if>

            <h1 style="font-weight: bold">Leyes y Costos por Tonelada [$us/Ton]</h1>

            <table class="center" style="width: 700px;">
            <tbody>
            <tr>
                <td>
                    &nbsp;</td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 10 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 20 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 30 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 40 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 50 DM" />
                    </label>
                </td>
            </tr>
            <tr>
                <td class="fieldcontain">
                    <label style="width: 95%">
                        <g:message code="tablaCotizacionEstano.valorIncrementable.label" default="Valor Incrementable" />
                        <span class="required-indicator">*</span>
                    </label>
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley10valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley20valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley30valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley40valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley50valorIncrementable}
                </td>
            </tr>
            <tr>
                <td class="fieldcontain">
                    <label style="width: 95%">
                        <g:message code="tablaCotizacionPlata.valorInicial.label" default="Valor Inicial" />
                        <span class="required-indicator">*</span>
                    </label>
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley10valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley20valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley30valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley40valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley50valorInicial}
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;</td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 60 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 70 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 80 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 90 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 100 DM" />
                    </label>
                </td>
            </tr>
            <tr>
                <td class="fieldcontain">
                    <label style="width: 95%">
                        <g:message code="tablaCotizacionPlata.valorIncrementable.label" default="Valor Incrementable" />
                        <span class="required-indicator">*</span>
                    </label>
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley60valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley70valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley80valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley90valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley100valorIncrementable}
                </td>
            </tr>
            <tr>
                <td class="fieldcontain">
                    <label style="width: 95%">
                        <g:message code="tablaCotizacionPlata.valorInicial.label" default="Valor Inicial" />
                        <span class="required-indicator">*</span>
                    </label>
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley60valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley70valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley80valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley90valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley100valorInicial}
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;</td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 150 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 200 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 300 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 400 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 500 DM" />
                    </label>
                </td>
            </tr>
            <tr>
                <td class="fieldcontain">
                    <label style="width: 95%">
                        <g:message code="tablaCotizacionPlata.valorIncrementable.label" default="Valor Incrementable" />
                        <span class="required-indicator">*</span>
                    </label>
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley150valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley200valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley300valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley400valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley500valorIncrementable}
                </td>
            </tr>
            <tr>
                <td class="fieldcontain">
                    <label style="width: 95%">
                        <g:message code="tablaCotizacionPlata.valorInicial.label" default="Valor Inicial" />
                        <span class="required-indicator">*</span>
                    </label>
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley150valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley200valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley300valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley400valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley500valorInicial}
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;</td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 600 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 700 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 800 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 900 DM" />
                    </label>
                </td>
                <td class="fieldcontain">
                    <label style="width: 80%">
                        <g:message code="tablaCotizacionPlata.ley5.label" default="Ley 1000 DM" />
                    </label>
                </td>
            </tr>
            <tr>
                <td class="fieldcontain">
                    <label style="width: 95%">
                        <g:message code="tablaCotizacionPlata.valorIncrementable.label" default="Valor Incrementable" />
                        <span class="required-indicator">*</span>
                    </label>
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley600valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley700valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley800valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley900valorIncrementable}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley1000valorIncrementable}
                </td>
            </tr>
            <tr>
                <td class="fieldcontain">
                    <label style="width: 95%">
                        <g:message code="tablaCotizacionPlata.valorInicial.label" default="Valor Inicial" />
                        <span class="required-indicator">*</span>
                    </label>
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley600valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley700valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley800valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley900valorInicial}
                </td>
                <td>
                    ${tablaCotizacionPlataInstance.ley1000valorInicial}
                </td>
            </tr>
            </tbody>
            </table>

            <h1 style="font-weight: bold">Tabla de Cotizaciones</h1>

            <g:hiddenField name="tablaDeCotizaciones" value="${tablaCotizacionPlataInstance.tablaDeCotizaciones}" />
            <div style="width: 900px; margin-left: auto; margin-right: auto;"><table id="list4"></table></div>

			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${tablaCotizacionPlataInstance?.id}" />
					<g:link class="edit" action="edit" id="${tablaCotizacionPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
