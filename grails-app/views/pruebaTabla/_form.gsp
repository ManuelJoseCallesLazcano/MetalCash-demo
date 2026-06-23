<%@ page import="org.socymet.proveedor.PruebaTabla" %>



<div class="fieldcontain ${hasErrors(bean: pruebaTablaInstance, field: 'nombreDeTabla', 'error')} ">
	<label for="nombreDeTabla">
		<g:message code="pruebaTabla.nombreDeTabla.label" default="Nombre De Tabla" />
		
	</label>
	<g:textField name="nombreDeTabla" value="${pruebaTablaInstance?.nombreDeTabla}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pruebaTablaInstance, field: 'contenido', 'error')} ">
	<label for="contenido">
		<g:message code="pruebaTabla.contenido.label" default="Contenido" />
		
	</label>
	<g:textField name="contenido" value="${pruebaTablaInstance?.contenido}" size="100"/>
    <g:hiddenField name="jsondata" value="${pruebaTablaInstance?.contenido}"/>
</div>

<div class="fieldcontain">
    <label for="codigo">Codigo</label>
    <input type="text" id="codigo" />
</div>

<div class="fieldcontain">
    <label for="descripcion">Descripcion</label>
    <input type="text" id="descripcion" />
</div>

<div class="fieldcontain">
    <label for="cantidad">Cantidad del Descuento</label>
    <input type="text" id="cantidad" />
</div>

<div class="fieldcontain">
    <label for="tipo">Tipo de Descuento</label>
    <input type="text" id="tipo" />
</div>

<div class="fieldcontain">
    <label for="asignacion">Asignacion del Descuento</label>
    <input type="text" id="asignacion" />
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRow('retenciones')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRow('retenciones')" />

<br>

<br>
<table id="retenciones" width="350px" class="hovertable">
    <tr>
        <th style="width: 10%">¿ELIMINAR?</th>
        <th style="width: 10%" data-override="CODIGO">CODIGO</th>
        <th style="width: 35%" data-override="DESCRIPCION">DESCRIPCION</th>
        <th style="width: 15%" data-override="CANTIDAD">CANTIDAD</th>
        <th style="width: 15%" data-override="TIPO">TIPO</th>
        <th style="width: 15%" data-override="ASIGNACION">ASIGNACION</th>
    </tr>
</table>

