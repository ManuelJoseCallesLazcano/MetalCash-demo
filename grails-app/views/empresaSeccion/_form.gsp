<%@ page import="org.socymet.proveedor.EmpresaSeccion" %>



<div class="fieldcontain ${hasErrors(bean: empresaSeccionInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="empresaSeccion.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" required="" value="${empresaSeccionInstance?.empresa?.id}" class="many-to-one, chosen-select"/>

</div>

<div class="fieldcontain ${hasErrors(bean: empresaSeccionInstance, field: 'nombreSeccion', 'error')} required">
	<label for="nombreSeccion">
		<g:message code="empresaSeccion.nombreSeccion.label" default="Nombre Seccion" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreSeccion" required="" value="${empresaSeccionInstance?.nombreSeccion}" size="50"/>

</div>

