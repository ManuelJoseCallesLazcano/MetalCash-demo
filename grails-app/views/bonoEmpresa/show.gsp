
<%@ page import="org.socymet.proveedor.BonoEmpresa" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bono.label', default: 'BonoEmpresa')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bono" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bono" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bono">
			
				<g:if test="${bonoInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="bono.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${bonoInstance?.empresa?.id}">${bonoInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoCalidadEstano}">
				<li class="fieldcontain">
					<span id="bonoCalidadEstano-label" class="property-label"><g:message code="bono.bonoCalidadEstano.label" default="Bono Calidad Estano" /></span>
					
						<span class="property-value" aria-labelledby="bonoCalidadEstano-label"><g:fieldValue bean="${bonoInstance}" field="bonoCalidadEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoCantidadEstano}">
				<li class="fieldcontain">
					<span id="bonoCantidadEstano-label" class="property-label"><g:message code="bono.bonoCantidadEstano.label" default="Bono Cantidad Estano" /></span>
					
						<span class="property-value" aria-labelledby="bonoCantidadEstano-label"><g:fieldValue bean="${bonoInstance}" field="bonoCantidadEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoIncentivoEstano}">
				<li class="fieldcontain">
					<span id="bonoIncentivoEstano-label" class="property-label"><g:message code="bono.bonoIncentivoEstano.label" default="Bono Incentivo Estano" /></span>
					
						<span class="property-value" aria-labelledby="bonoIncentivoEstano-label"><g:fieldValue bean="${bonoInstance}" field="bonoIncentivoEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoCalidadPlata}">
				<li class="fieldcontain">
					<span id="bonoCalidadPlata-label" class="property-label"><g:message code="bono.bonoCalidadPlata.label" default="Bono Calidad Plata" /></span>
					
						<span class="property-value" aria-labelledby="bonoCalidadPlata-label"><g:fieldValue bean="${bonoInstance}" field="bonoCalidadPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoCantidadPlata}">
				<li class="fieldcontain">
					<span id="bonoCantidadPlata-label" class="property-label"><g:message code="bono.bonoCantidadPlata.label" default="Bono Cantidad Plata" /></span>
					
						<span class="property-value" aria-labelledby="bonoCantidadPlata-label"><g:fieldValue bean="${bonoInstance}" field="bonoCantidadPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoIncentivoPlata}">
				<li class="fieldcontain">
					<span id="bonoIncentivoPlata-label" class="property-label"><g:message code="bono.bonoIncentivoPlata.label" default="Bono Incentivo Plata" /></span>
					
						<span class="property-value" aria-labelledby="bonoIncentivoPlata-label"><g:fieldValue bean="${bonoInstance}" field="bonoIncentivoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoCalidadWolfran}">
				<li class="fieldcontain">
					<span id="bonoCalidadWolfran-label" class="property-label"><g:message code="bono.bonoCalidadWolfran.label" default="Bono Calidad Wolfran" /></span>
					
						<span class="property-value" aria-labelledby="bonoCalidadWolfran-label"><g:fieldValue bean="${bonoInstance}" field="bonoCalidadWolfran"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoCantidadWolfran}">
				<li class="fieldcontain">
					<span id="bonoCantidadWolfran-label" class="property-label"><g:message code="bono.bonoCantidadWolfran.label" default="Bono Cantidad Wolfran" /></span>
					
						<span class="property-value" aria-labelledby="bonoCantidadWolfran-label"><g:fieldValue bean="${bonoInstance}" field="bonoCantidadWolfran"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoIncentivoWolfran}">
				<li class="fieldcontain">
					<span id="bonoIncentivoWolfran-label" class="property-label"><g:message code="bono.bonoIncentivoWolfran.label" default="Bono Incentivo Wolfran" /></span>
					
						<span class="property-value" aria-labelledby="bonoIncentivoWolfran-label"><g:fieldValue bean="${bonoInstance}" field="bonoIncentivoWolfran"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoCalidadAntimonio}">
				<li class="fieldcontain">
					<span id="bonoCalidadAntimonio-label" class="property-label"><g:message code="bono.bonoCalidadAntimonio.label" default="Bono Calidad Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="bonoCalidadAntimonio-label"><g:fieldValue bean="${bonoInstance}" field="bonoCalidadAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoCantidadAntimonio}">
				<li class="fieldcontain">
					<span id="bonoCantidadAntimonio-label" class="property-label"><g:message code="bono.bonoCantidadAntimonio.label" default="Bono Cantidad Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="bonoCantidadAntimonio-label"><g:fieldValue bean="${bonoInstance}" field="bonoCantidadAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoIncentivoAntimonio}">
				<li class="fieldcontain">
					<span id="bonoIncentivoAntimonio-label" class="property-label"><g:message code="bono.bonoIncentivoAntimonio.label" default="Bono Incentivo Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="bonoIncentivoAntimonio-label"><g:fieldValue bean="${bonoInstance}" field="bonoIncentivoAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoCalidadComplejo}">
				<li class="fieldcontain">
					<span id="bonoCalidadComplejo-label" class="property-label"><g:message code="bono.bonoCalidadComplejo.label" default="Bono Calidad Complejo" /></span>
					
						<span class="property-value" aria-labelledby="bonoCalidadComplejo-label"><g:fieldValue bean="${bonoInstance}" field="bonoCalidadComplejo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoCantidadComplejo}">
				<li class="fieldcontain">
					<span id="bonoCantidadComplejo-label" class="property-label"><g:message code="bono.bonoCantidadComplejo.label" default="Bono Cantidad Complejo" /></span>
					
						<span class="property-value" aria-labelledby="bonoCantidadComplejo-label"><g:fieldValue bean="${bonoInstance}" field="bonoCantidadComplejo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.bonoIncentivoComplejo}">
				<li class="fieldcontain">
					<span id="bonoIncentivoComplejo-label" class="property-label"><g:message code="bono.bonoIncentivoComplejo.label" default="Bono Incentivo Complejo" /></span>
					
						<span class="property-value" aria-labelledby="bonoIncentivoComplejo-label"><g:fieldValue bean="${bonoInstance}" field="bonoIncentivoComplejo"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${bonoInstance?.id}" />
					<g:link class="edit" action="edit" id="${bonoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
