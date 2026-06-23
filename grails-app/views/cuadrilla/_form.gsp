<%@ page import="org.socymet.proveedor.Cuadrilla" %>



<div class="fieldcontain ${hasErrors(bean: cuadrillaInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="cuadrilla.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" required="" value="${cuadrillaInstance?.empresa?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: cuadrillaInstance, field: 'nombreCuadrilla', 'error')} required">
	<label for="nombreCuadrilla">
		<g:message code="cuadrilla.nombreCuadrilla.label" default="Nombre Cuadrilla" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nombreCuadrilla" required="" value="${cuadrillaInstance?.nombreCuadrilla}"/>
</div>

