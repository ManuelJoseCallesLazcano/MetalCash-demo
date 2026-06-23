
<%@ page import="org.socymet.cotizaciones.TablaCotizacionEstano" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tablaCotizacionEstano.label', default: 'TablaCotizacionEstano')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <g:javascript src="cotizaciones/tablaCotizacionEstano.js" />
	</head>
	<body>
		<a href="#show-tablaCotizacionEstano" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tablaCotizacionEstano" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list tablaCotizacionEstano">
			
				<g:if test="${tablaCotizacionEstanoInstance?.nombreDeTabla}">
				<li class="fieldcontain">
					<span id="nombreDeTabla-label" class="property-label"><g:message code="tablaCotizacionEstano.nombreDeTabla.label" default="Nombre De Tabla" /></span>
					
						<span class="property-value" aria-labelledby="nombreDeTabla-label"><g:fieldValue bean="${tablaCotizacionEstanoInstance}" field="nombreDeTabla"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tablaCotizacionEstanoInstance?.cotizacionInicial}">
				<li class="fieldcontain">
					<span id="cotizacionInicial-label" class="property-label"><g:message code="tablaCotizacionEstano.cotizacionInicial.label" default="Cotizacion Inicial" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionInicial-label"><g:fieldValue bean="${tablaCotizacionEstanoInstance}" field="cotizacionInicial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${tablaCotizacionEstanoInstance?.cotizacionFinal}">
				<li class="fieldcontain">
					<span id="cotizacionFinal-label" class="property-label"><g:message code="tablaCotizacionEstano.cotizacionFinal.label" default="Cotizacion Final" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionFinal-label"><g:fieldValue bean="${tablaCotizacionEstanoInstance}" field="cotizacionFinal"/></span>
					
				</li>
				</g:if>

            <h1 style="font-weight: bold">Leyes y Costos por Tonelada [$us/Ton]</h1>

            <table class="center" style="width: 750px;">
                <tbody>
                <tr>
                    <td>
                        &nbsp;</td>
                    <td class="fieldcontain">
                        <label style="width: 80%">
                            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 5%" />
                        </label>
                    </td>
                    <td class="fieldcontain">
                        <label style="width: 80%">
                            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 10%" />
                        </label>
                    </td>
                    <td class="fieldcontain">
                        <label style="width: 80%">
                            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 15%" />
                        </label>
                    </td>
                    <td class="fieldcontain">
                        <label style="width: 80%">
                            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 20%" />
                        </label>
                    </td>
                    <td class="fieldcontain">
                        <label style="width: 80%">
                            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 25%" />
                        </label>
                    </td>
                    <td class="fieldcontain">
                        <label style="width: 80%">
                            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 30%" />
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
                        ${tablaCotizacionEstanoInstance.ley5valorIncrementable}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley10valorIncrementable}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley15valorIncrementable}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley20valorIncrementable}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley25valorIncrementable}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley30valorIncrementable}
                    </td>
                </tr>
                <tr>
                    <td class="fieldcontain">
                        <label style="width: 95%">
                            <g:message code="tablaCotizacionEstano.valorInicial.label" default="Valor Inicial" />
                            <span class="required-indicator">*</span>
                        </label>
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley5valorInicial}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley10valorInicial}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley15valorInicial}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley20valorInicial}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley25valorInicial}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley30valorInicial}
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp;</td>
                    <td class="fieldcontain">
                        <label style="width: 80%">
                            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 35%" />
                        </label>
                    </td>
                    <td class="fieldcontain">
                        <label style="width: 80%">
                            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 40%" />
                        </label>
                    </td>
                    <td class="fieldcontain">
                        <label style="width: 80%">
                            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 50%" />
                        </label>
                    </td>
                    <td class="fieldcontain">
                        <label style="width: 80%">
                            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 60%" />
                        </label>
                    </td>
                    <td class="fieldcontain">
                        <label style="width: 80%">
                            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 70%" />
                        </label>
                    </td>
                    <td class="fieldcontain">
                        <label style="width: 80%">
                            <g:message code="tablaCotizacionEstano.ley5.label" default="Ley 75%" />
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
                        ${tablaCotizacionEstanoInstance.ley35valorIncrementable}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley40valorIncrementable}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley50valorIncrementable}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley60valorIncrementable}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley70valorIncrementable}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley75valorIncrementable}
                    </td>
                </tr>
                <tr>
                    <td class="fieldcontain">
                        <label style="width: 95%">
                            <g:message code="tablaCotizacionEstano.valorInicial.label" default="Valor Inicial" />
                            <span class="required-indicator">*</span>
                        </label>
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley35valorInicial}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley40valorInicial}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley50valorInicial}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley60valorInicial}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley70valorInicial}
                    </td>
                    <td>
                        ${tablaCotizacionEstanoInstance.ley75valorInicial}
                    </td>
                </tr>
                </tbody>
            </table>

            <h1 style="font-weight: bold">Tabla de Cotizaciones</h1>

                <g:hiddenField name="tablaDeCotizaciones" value="${tablaCotizacionEstanoInstance.tablaDeCotizaciones}" />
            <div style="width: 900px; margin-left: auto; margin-right: auto;"><table id="list4"></table></div>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${tablaCotizacionEstanoInstance?.id}" />
					<g:link class="edit" action="edit" id="${tablaCotizacionEstanoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
