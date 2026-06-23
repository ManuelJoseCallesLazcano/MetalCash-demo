<%@ page import="org.socymet.org.socymet.reportes.ReporteHojaDeCosto" %>



<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'elemento', 'error')} ">
	<label for="elemento">
		<g:message code="reporteHojaDeCosto.elemento.label" default="Elemento" />
		
	</label>
	<g:select name="elemento" from="${['Complejo','Plomo Plata','Zinc Plata']}" value="${reporteHojaDeCostoInstance?.elemento}" valueMessagePrefix="reporteHojaDeCosto.elemento" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'nombreDelConjunto', 'error')} ">
	<label for="nombreDelConjunto">
		<g:message code="reporteHojaDeCosto.nombreDelConjunto.label" default="Nombre Del Conjunto" />
		
	</label>
	<g:textField name="nombreDelConjunto" value="${reporteHojaDeCostoInstance?.nombreDelConjunto}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'destinoDelConjunto', 'error')} ">
	<label for="destinoDelConjunto">
		<g:message code="reporteHojaDeCosto.destinoDelConjunto.label" default="Destino Del Conjunto" />
		
	</label>
	<g:textField name="destinoDelConjunto" value="${reporteHojaDeCostoInstance?.destinoDelConjunto}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'asignarConjuntoALotes', 'error')} ">
	<label for="asignarConjuntoALotes">
		<g:message code="reporteHojaDeCosto.asignarConjuntoALotes.label" default="Asignar Conjunto AL otes" />
		
	</label>
	<g:select name="asignarConjuntoALotes" from="${['NO','SI']}" value="${reporteHojaDeCostoInstance?.asignarConjuntoALotes}" valueMessagePrefix="reporteHojaDeCosto.asignarConjuntoALotes" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'ignorarLotes', 'error')} ">
	<label for="ignorarLotes">
		<g:message code="reporteHojaDeCosto.ignorarLotes.label" default="Ignorar Lotes" />
		
	</label>
	<g:textField name="ignorarLotes" value="${reporteHojaDeCostoInstance?.ignorarLotes}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="reporteHojaDeCosto.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${reporteHojaDeCostoInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'fechaInicial', 'error')} ">
	<label for="fechaInicial">
		<g:message code="reporteHojaDeCosto.fechaInicial.label" default="Fecha Inicial" />
		
	</label>
	<g:datePicker name="fechaInicial" precision="day"  value="${reporteHojaDeCostoInstance?.fechaInicial}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'fechaFinal', 'error')} ">
	<label for="fechaFinal">
		<g:message code="reporteHojaDeCosto.fechaFinal.label" default="Fecha Final" />
		
	</label>
	<g:datePicker name="fechaFinal" precision="day"  value="${reporteHojaDeCostoInstance?.fechaFinal}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'loteInicial', 'error')} ">
	<label for="loteInicial">
		<g:message code="reporteHojaDeCosto.loteInicial.label" default="Lote Inicial" />
		
	</label>
	<g:textField name="loteInicial" inputmode="numeric" value="${reporteHojaDeCostoInstance?.loteInicial}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'loteFinal', 'error')} ">
	<label for="loteFinal">
		<g:message code="reporteHojaDeCosto.loteFinal.label" default="Lote Final" />
		
	</label>
	<g:textField name="loteFinal" inputmode="numeric" value="${reporteHojaDeCostoInstance?.loteFinal}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaEstano', 'error')} ">
	<label for="leyMinimaEstano">
		<g:message code="reporteHojaDeCosto.leyMinimaEstano.label" default="Ley Minima Estano" />
		
	</label>
	<g:field name="leyMinimaEstano" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaEstano')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaEstano', 'error')} ">
	<label for="leyMaximaEstano">
		<g:message code="reporteHojaDeCosto.leyMaximaEstano.label" default="Ley Maxima Estano" />
		
	</label>
	<g:field name="leyMaximaEstano" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaEstano')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlata', 'error')} ">
	<label for="leyMinimaPlata">
		<g:message code="reporteHojaDeCosto.leyMinimaPlata.label" default="Ley Minima Plata" />
		
	</label>
	<g:field name="leyMinimaPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlata')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlata', 'error')} ">
	<label for="leyMaximaPlata">
		<g:message code="reporteHojaDeCosto.leyMaximaPlata.label" default="Ley Maxima Plata" />
		
	</label>
	<g:field name="leyMaximaPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlata')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaWolfran', 'error')} ">
	<label for="leyMinimaWolfran">
		<g:message code="reporteHojaDeCosto.leyMinimaWolfran.label" default="Ley Minima Wolfran" />
		
	</label>
	<g:field name="leyMinimaWolfran" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaWolfran')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaWolfran', 'error')} ">
	<label for="leyMaximaWolfran">
		<g:message code="reporteHojaDeCosto.leyMaximaWolfran.label" default="Ley Maxima Wolfran" />
		
	</label>
	<g:field name="leyMaximaWolfran" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaWolfran')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaAntimonio', 'error')} ">
	<label for="leyMinimaAntimonio">
		<g:message code="reporteHojaDeCosto.leyMinimaAntimonio.label" default="Ley Minima Antimonio" />
		
	</label>
	<g:field name="leyMinimaAntimonio" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaAntimonio')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaAntimonio', 'error')} ">
	<label for="leyMaximaAntimonio">
		<g:message code="reporteHojaDeCosto.leyMaximaAntimonio.label" default="Ley Maxima Antimonio" />
		
	</label>
	<g:field name="leyMaximaAntimonio" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaAntimonio')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaZincComplejo', 'error')} ">
	<label for="leyMinimaZincComplejo">
		<g:message code="reporteHojaDeCosto.leyMinimaZincComplejo.label" default="Ley Minima Zinc Complejo" />
		
	</label>
	<g:field name="leyMinimaZincComplejo" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaZincComplejo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaZincComplejo', 'error')} ">
	<label for="leyMaximaZincComplejo">
		<g:message code="reporteHojaDeCosto.leyMaximaZincComplejo.label" default="Ley Maxima Zinc Complejo" />
		
	</label>
	<g:field name="leyMaximaZincComplejo" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaZincComplejo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlomoComplejo', 'error')} ">
	<label for="leyMinimaPlomoComplejo">
		<g:message code="reporteHojaDeCosto.leyMinimaPlomoComplejo.label" default="Ley Minima Plomo Complejo" />
		
	</label>
	<g:field name="leyMinimaPlomoComplejo" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlomoComplejo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlomoComplejo', 'error')} ">
	<label for="leyMaximaPlomoComplejo">
		<g:message code="reporteHojaDeCosto.leyMaximaPlomoComplejo.label" default="Ley Maxima Plomo Complejo" />
		
	</label>
	<g:field name="leyMaximaPlomoComplejo" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlomoComplejo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlataComplejo', 'error')} ">
	<label for="leyMinimaPlataComplejo">
		<g:message code="reporteHojaDeCosto.leyMinimaPlataComplejo.label" default="Ley Minima Plata Complejo" />
		
	</label>
	<g:field name="leyMinimaPlataComplejo" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlataComplejo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlataComplejo', 'error')} ">
	<label for="leyMaximaPlataComplejo">
		<g:message code="reporteHojaDeCosto.leyMaximaPlataComplejo.label" default="Ley Maxima Plata Complejo" />
		
	</label>
	<g:field name="leyMaximaPlataComplejo" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlataComplejo')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlomoPlomoPlata', 'error')} ">
	<label for="leyMinimaPlomoPlomoPlata">
		<g:message code="reporteHojaDeCosto.leyMinimaPlomoPlomoPlata.label" default="Ley Minima Plomo Plomo Plata" />
		
	</label>
	<g:field name="leyMinimaPlomoPlomoPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlomoPlomoPlata')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlomoPlomoPlata', 'error')} ">
	<label for="leyMaximaPlomoPlomoPlata">
		<g:message code="reporteHojaDeCosto.leyMaximaPlomoPlomoPlata.label" default="Ley Maxima Plomo Plomo Plata" />
		
	</label>
	<g:field name="leyMaximaPlomoPlomoPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlomoPlomoPlata')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlataPlomoPlata', 'error')} ">
	<label for="leyMinimaPlataPlomoPlata">
		<g:message code="reporteHojaDeCosto.leyMinimaPlataPlomoPlata.label" default="Ley Minima Plata Plomo Plata" />
		
	</label>
	<g:field name="leyMinimaPlataPlomoPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlataPlomoPlata')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlataPlomoPlata', 'error')} ">
	<label for="leyMaximaPlataPlomoPlata">
		<g:message code="reporteHojaDeCosto.leyMaximaPlataPlomoPlata.label" default="Ley Maxima Plata Plomo Plata" />
		
	</label>
	<g:field name="leyMaximaPlataPlomoPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlataPlomoPlata')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaZincZincPlata', 'error')} ">
	<label for="leyMinimaZincZincPlata">
		<g:message code="reporteHojaDeCosto.leyMinimaZincZincPlata.label" default="Ley Minima Zinc Zinc Plata" />
		
	</label>
	<g:field name="leyMinimaZincZincPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaZincZincPlata')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaZincZincPlata', 'error')} ">
	<label for="leyMaximaZincZincPlata">
		<g:message code="reporteHojaDeCosto.leyMaximaZincZincPlata.label" default="Ley Maxima Zinc Zinc Plata" />
		
	</label>
	<g:field name="leyMaximaZincZincPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaZincZincPlata')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlataZincPlata', 'error')} ">
	<label for="leyMinimaPlataZincPlata">
		<g:message code="reporteHojaDeCosto.leyMinimaPlataZincPlata.label" default="Ley Minima Plata Zinc Plata" />
		
	</label>
	<g:field name="leyMinimaPlataZincPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMinimaPlataZincPlata')}" inputmode="decimal"/>
</div>

<div class="fieldcontain ${hasErrors(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlataZincPlata', 'error')} ">
	<label for="leyMaximaPlataZincPlata">
		<g:message code="reporteHojaDeCosto.leyMaximaPlataZincPlata.label" default="Ley Maxima Plata Zinc Plata" />
		
	</label>
	<g:field name="leyMaximaPlataZincPlata" value="${fieldValue(bean: reporteHojaDeCostoInstance, field: 'leyMaximaPlataZincPlata')}" inputmode="decimal"/>
</div>

