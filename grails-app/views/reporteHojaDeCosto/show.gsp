
<%@ page import="org.socymet.org.socymet.reportes.ReporteHojaDeCosto" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteHojaDeCosto.label', default: 'ReporteHojaDeCosto')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reporteHojaDeCosto" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reporteHojaDeCosto" class="content scaffold-show" role="main">
        <g:form>
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reporteHojaDeCosto">
			
				<g:if test="${reporteHojaDeCostoInstance?.elemento}">
				<li class="fieldcontain">
					<span id="elemento-label" class="property-label"><g:message code="reporteHojaDeCosto.elemento.label" default="Elemento" /></span>
					
						<span class="property-value" aria-labelledby="elemento-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="elemento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.nombreDelConjunto}">
				<li class="fieldcontain">
					<span id="nombreDelConjunto-label" class="property-label"><g:message code="reporteHojaDeCosto.nombreDelConjunto.label" default="Nombre Del Conjunto" /></span>
					
						<span class="property-value" aria-labelledby="nombreDelConjunto-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="nombreDelConjunto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.destinoDelConjunto}">
				<li class="fieldcontain">
					<span id="destinoDelConjunto-label" class="property-label"><g:message code="reporteHojaDeCosto.destinoDelConjunto.label" default="Destino Del Conjunto" /></span>
					
						<span class="property-value" aria-labelledby="destinoDelConjunto-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="destinoDelConjunto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.asignarConjuntoALotes}">
				<li class="fieldcontain">
					<span id="asignarConjuntoALotes-label" class="property-label"><g:message code="reporteHojaDeCosto.asignarConjuntoALotes.label" default="Asignar Conjunto AL otes" /></span>
					
						<span class="property-value" aria-labelledby="asignarConjuntoALotes-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="asignarConjuntoALotes"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.ignorarLotes}">
				<li class="fieldcontain">
					<span id="ignorarLotes-label" class="property-label"><g:message code="reporteHojaDeCosto.ignorarLotes.label" default="Ignorar Lotes" /></span>
					
						<span class="property-value" aria-labelledby="ignorarLotes-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="ignorarLotes"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="reporteHojaDeCosto.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${reporteHojaDeCostoInstance?.empresa?.id}">${reporteHojaDeCostoInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.fechaInicial}">
				<li class="fieldcontain">
					<span id="fechaInicial-label" class="property-label"><g:message code="reporteHojaDeCosto.fechaInicial.label" default="Fecha Inicial" /></span>
					
						<span class="property-value" aria-labelledby="fechaInicial-label"><g:formatDate date="${reporteHojaDeCostoInstance?.fechaInicial}" format="dd/MM/yyyy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.fechaFinal}">
				<li class="fieldcontain">
					<span id="fechaFinal-label" class="property-label"><g:message code="reporteHojaDeCosto.fechaFinal.label" default="Fecha Final" /></span>
					
						<span class="property-value" aria-labelledby="fechaFinal-label"><g:formatDate date="${reporteHojaDeCostoInstance?.fechaFinal}" format="dd/MM/yyyy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.loteInicial}">
				<li class="fieldcontain">
					<span id="loteInicial-label" class="property-label"><g:message code="reporteHojaDeCosto.loteInicial.label" default="Lote Inicial" /></span>
					
						<span class="property-value" aria-labelledby="loteInicial-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="loteInicial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.loteFinal}">
				<li class="fieldcontain">
					<span id="loteFinal-label" class="property-label"><g:message code="reporteHojaDeCosto.loteFinal.label" default="Lote Final" /></span>
					
						<span class="property-value" aria-labelledby="loteFinal-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="loteFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMinimaEstano}">
				<li class="fieldcontain">
					<span id="leyMinimaEstano-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMinimaEstano.label" default="Ley Minima Estano" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaEstano-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMinimaEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMaximaEstano}">
				<li class="fieldcontain">
					<span id="leyMaximaEstano-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMaximaEstano.label" default="Ley Maxima Estano" /></span>
					
						<span class="property-value" aria-labelledby="leyMaximaEstano-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMaximaEstano"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMinimaPlata}">
				<li class="fieldcontain">
					<span id="leyMinimaPlata-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMinimaPlata.label" default="Ley Minima Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaPlata-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMinimaPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMaximaPlata}">
				<li class="fieldcontain">
					<span id="leyMaximaPlata-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMaximaPlata.label" default="Ley Maxima Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyMaximaPlata-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMaximaPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMinimaWolfran}">
				<li class="fieldcontain">
					<span id="leyMinimaWolfran-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMinimaWolfran.label" default="Ley Minima Wolfran" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaWolfran-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMinimaWolfran"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMaximaWolfran}">
				<li class="fieldcontain">
					<span id="leyMaximaWolfran-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMaximaWolfran.label" default="Ley Maxima Wolfran" /></span>
					
						<span class="property-value" aria-labelledby="leyMaximaWolfran-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMaximaWolfran"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMinimaAntimonio}">
				<li class="fieldcontain">
					<span id="leyMinimaAntimonio-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMinimaAntimonio.label" default="Ley Minima Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaAntimonio-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMinimaAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMaximaAntimonio}">
				<li class="fieldcontain">
					<span id="leyMaximaAntimonio-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMaximaAntimonio.label" default="Ley Maxima Antimonio" /></span>
					
						<span class="property-value" aria-labelledby="leyMaximaAntimonio-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMaximaAntimonio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMinimaZincComplejo}">
				<li class="fieldcontain">
					<span id="leyMinimaZincComplejo-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMinimaZincComplejo.label" default="Ley Minima Zinc Complejo" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaZincComplejo-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMinimaZincComplejo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMaximaZincComplejo}">
				<li class="fieldcontain">
					<span id="leyMaximaZincComplejo-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMaximaZincComplejo.label" default="Ley Maxima Zinc Complejo" /></span>
					
						<span class="property-value" aria-labelledby="leyMaximaZincComplejo-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMaximaZincComplejo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMinimaPlomoComplejo}">
				<li class="fieldcontain">
					<span id="leyMinimaPlomoComplejo-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMinimaPlomoComplejo.label" default="Ley Minima Plomo Complejo" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaPlomoComplejo-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMinimaPlomoComplejo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMaximaPlomoComplejo}">
				<li class="fieldcontain">
					<span id="leyMaximaPlomoComplejo-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMaximaPlomoComplejo.label" default="Ley Maxima Plomo Complejo" /></span>
					
						<span class="property-value" aria-labelledby="leyMaximaPlomoComplejo-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMaximaPlomoComplejo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMinimaPlataComplejo}">
				<li class="fieldcontain">
					<span id="leyMinimaPlataComplejo-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMinimaPlataComplejo.label" default="Ley Minima Plata Complejo" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaPlataComplejo-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMinimaPlataComplejo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMaximaPlataComplejo}">
				<li class="fieldcontain">
					<span id="leyMaximaPlataComplejo-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMaximaPlataComplejo.label" default="Ley Maxima Plata Complejo" /></span>
					
						<span class="property-value" aria-labelledby="leyMaximaPlataComplejo-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMaximaPlataComplejo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMinimaPlomoPlomoPlata}">
				<li class="fieldcontain">
					<span id="leyMinimaPlomoPlomoPlata-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMinimaPlomoPlomoPlata.label" default="Ley Minima Plomo Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaPlomoPlomoPlata-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMinimaPlomoPlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMaximaPlomoPlomoPlata}">
				<li class="fieldcontain">
					<span id="leyMaximaPlomoPlomoPlata-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMaximaPlomoPlomoPlata.label" default="Ley Maxima Plomo Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyMaximaPlomoPlomoPlata-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMaximaPlomoPlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMinimaPlataPlomoPlata}">
				<li class="fieldcontain">
					<span id="leyMinimaPlataPlomoPlata-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMinimaPlataPlomoPlata.label" default="Ley Minima Plata Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaPlataPlomoPlata-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMinimaPlataPlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMaximaPlataPlomoPlata}">
				<li class="fieldcontain">
					<span id="leyMaximaPlataPlomoPlata-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMaximaPlataPlomoPlata.label" default="Ley Maxima Plata Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyMaximaPlataPlomoPlata-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMaximaPlataPlomoPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMinimaZincZincPlata}">
				<li class="fieldcontain">
					<span id="leyMinimaZincZincPlata-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMinimaZincZincPlata.label" default="Ley Minima Zinc Zinc Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaZincZincPlata-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMinimaZincZincPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMaximaZincZincPlata}">
				<li class="fieldcontain">
					<span id="leyMaximaZincZincPlata-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMaximaZincZincPlata.label" default="Ley Maxima Zinc Zinc Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyMaximaZincZincPlata-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMaximaZincZincPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMinimaPlataZincPlata}">
				<li class="fieldcontain">
					<span id="leyMinimaPlataZincPlata-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMinimaPlataZincPlata.label" default="Ley Minima Plata Zinc Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyMinimaPlataZincPlata-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMinimaPlataZincPlata"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteHojaDeCostoInstance?.leyMaximaPlataZincPlata}">
				<li class="fieldcontain">
					<span id="leyMaximaPlataZincPlata-label" class="property-label"><g:message code="reporteHojaDeCosto.leyMaximaPlataZincPlata.label" default="Ley Maxima Plata Zinc Plata" /></span>
					
						<span class="property-value" aria-labelledby="leyMaximaPlataZincPlata-label"><g:fieldValue bean="${reporteHojaDeCostoInstance}" field="leyMaximaPlataZincPlata"/></span>
					
				</li>
				</g:if>
			
			</ol>

				<fieldset class="buttons">
					<g:hiddenField name="id" value="${reporteHojaDeCostoInstance?.id}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />

                    <g:if test="${reporteHojaDeCostoInstance.elemento.equals("Estano")}">
                    <div id="_resultadosEstano">
                        <div style="text-align: center;">
                            <g:hiddenField name="hcid" value="${reporteHojaDeCostoInstance?.id}" />
                            <g:actionSubmit class="reporte" controller="reporteHojaDeCosto" action="recrearReporteEstano" value="Generar Reporte" />
                        </div>
                    </div>
                    </g:if>

                    <g:if test="${reporteHojaDeCostoInstance.elemento.equals("Plata")}">
                        <div id="_resultadosPlata">
                            <div style="text-align: center;">
                                <g:hiddenField name="hcid" value="${reporteHojaDeCostoInstance?.id}" />
                                <g:actionSubmit class="reporte" controller="reporteHojaDeCosto" action="recrearReportePlata" value="Generar Reporte" />
                            </div>
                        </div>
                    </g:if>

                    <g:if test="${reporteHojaDeCostoInstance.elemento.equals("Wolfran")}">
                        <div id="_resultadosWolfran">
                            <div style="text-align: center;">
                                <g:hiddenField name="hcid" value="${reporteHojaDeCostoInstance?.id}" />
                                <g:actionSubmit class="reporte" controller="reporteHojaDeCosto" action="recrearReporteWolfran" value="Generar Reporte" />
                            </div>
                        </div>
                    </g:if>

                    <g:if test="${reporteHojaDeCostoInstance.elemento.equals("Antimonio")}">
                        <div id="_resultadosAntimonio">
                            <div style="text-align: center;">
                                <g:hiddenField name="hcid" value="${reporteHojaDeCostoInstance?.id}" />
                                <g:actionSubmit class="reporte" controller="reporteHojaDeCosto" action="recrearReporteAntimonio" value="Generar Reporte" />
                            </div>
                        </div>
                    </g:if>

                    <g:if test="${reporteHojaDeCostoInstance.elemento.equals("Complejo")}">
                        <div id="_resultadosComplejo">
                            <div style="text-align: center;">
                                <g:hiddenField name="hcid" value="${reporteHojaDeCostoInstance?.id}" />
                                <g:actionSubmit class="reporte" controller="reporteHojaDeCosto" action="recrearReporteComplejo" value="Generar Reporte" />
                            </div>
                        </div>
                    </g:if>
				</fieldset>
			</g:form>


		</div>
	</body>
</html>
