<%@ page import="org.socymet.proveedor.BonoEmpresa" %>


<div class="fieldcontain ${hasErrors(bean: bonoInstance, field: 'empresa', 'error')} ">
	<label for="empresa">
		<g:message code="bono.empresa.label" default="Empresa" />
		
	</label>
	<g:select id="empresa" name="empresa.id" from="${org.socymet.proveedor.Empresa.list()}" optionKey="id" value="${bonoInstance?.empresa?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Estano a la Calidad</h1>
</div>
<div>
<div class="fieldcontain">
    <label for="leyMinimaBonoCalidadEstano">Ley Minima</label>
    <input type="text" id="leyMinimaBonoCalidadEstano"/>
</div>

<div class="fieldcontain">
    <label for="leyMaximaBonoCalidadEstano">Ley Maxima</label>
    <input type="text" id="leyMaximaBonoCalidadEstano"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoCalidadEstano">Bono</label>
    <input type="text" id="bonoBonoCalidadEstano"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRow('tablaBonoCalidadEstano','bonoCalidadEstano','leyMinimaBonoCalidadEstano','leyMaximaBonoCalidadEstano','bonoBonoCalidadEstano')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRow('tablaBonoCalidadEstano','bonoCalidadEstano')" />
<br>

<br>
<table id="tablaBonoCalidadEstano" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="LEY_MINIMA">LEY MINIMA</th>
        <th data-override="LEY_MAXIMA">LEY MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoCalidadEstano" value="${bonoInstance?.bonoCalidadEstano}" />
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Estano a la Cantidad</h1>
</div>

<div>
<div class="fieldcontain">
    <label for="cantidadMinimaBonoCantidadEstano">Cantidad Minima</label>
    <input type="text" id="cantidadMinimaBonoCantidadEstano"/>
</div>

<div class="fieldcontain">
    <label for="cantidadMaximaBonoCantidadEstano">Cantidad Maxima</label>
    <input type="text" id="cantidadMaximaBonoCantidadEstano"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoCantidadEstano">Bono</label>
    <input type="text" id="bonoBonoCantidadEstano"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRowCantidad('tablaBonoCantidadEstano','bonoCantidadEstano','cantidadMinimaBonoCantidadEstano','cantidadMaximaBonoCantidadEstano','bonoBonoCantidadEstano')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRowCantidad('tablaBonoCantidadEstano','bonoCantidadEstano')" />
<br>

<br>
<table id="tablaBonoCantidadEstano" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="CANTIDAD_MINIMA">CANTIDAD MINIMA</th>
        <th data-override="CANTIDAD_MAXIMA">CANTIDAD MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoCantidadEstano" value="${bonoInstance?.bonoCantidadEstano}" />
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Estano como Incentivo</h1>
</div>

<div>
<div class="fieldcontain">
    <label for="cantidadMinimaBonoIncentivoEstano">Cantidad Minima</label>
    <input type="text" id="cantidadMinimaBonoIncentivoEstano"/>
</div>

<div class="fieldcontain">
    <label for="cantidadMaximaBonoIncentivoEstano">Cantidad Maxima</label>
    <input type="text" id="cantidadMaximaBonoIncentivoEstano"/>
</div>

<div class="fieldcontain">
    <label for="calidadMinimaBonoIncentivoEstano">Calidad Minima</label>
    <input type="text" id="calidadMinimaBonoIncentivoEstano"/>
</div>

<div class="fieldcontain">
    <label for="calidadMaximaBonoIncentivoEstano">Calidad Maxima</label>
    <input type="text" id="calidadMaximaBonoIncentivoEstano"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoIncentivoEstano">Bono</label>
    <input type="text" id="bonoBonoIncentivoEstano"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRowIncentivo('tablaBonoIncentivoEstano','bonoIncentivoEstano','cantidadMinimaBonoIncentivoEstano','cantidadMaximaBonoIncentivoEstano','calidadMinimaBonoIncentivoEstano','calidadMaximaBonoIncentivoEstano','bonoBonoIncentivoEstano')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRowIncentivo('tablaBonoIncentivoEstano','bonoIncentivoEstano')" />
<br>

<br>
<table id="tablaBonoIncentivoEstano" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="CANTIDAD_MINIMA">CANTIDAD MINIMA</th>
        <th data-override="CANTIDAD_MAXIMA">CANTIDAD MAXIMA</th>
        <th data-override="CALIDAD_MINIMA">CALIDAD MINIMA</th>
        <th data-override="CALIDAD_MAXIMA">CALIDAD MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoIncentivoEstano" value="${bonoInstance?.bonoIncentivoEstano}" />
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Plata a la Calidad</h1>
</div>

<div>
<div class="fieldcontain">
    <label for="leyMinimaBonoCalidadPlata">Ley Minima</label>
    <input type="text" id="leyMinimaBonoCalidadPlata"/>
</div>

<div class="fieldcontain">
    <label for="leyMaximaBonoCalidadPlata">Ley Maxima</label>
    <input type="text" id="leyMaximaBonoCalidadPlata"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoCalidadPlata">Bono</label>
    <input type="text" id="bonoBonoCalidadPlata"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRow('tablaBonoCalidadPlata','bonoCalidadPlata','leyMinimaBonoCalidadPlata','leyMaximaBonoCalidadPlata','bonoBonoCalidadPlata')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRow('tablaBonoCalidadPlata','bonoCalidadPlata')" />
<br>

<br>
<table id="tablaBonoCalidadPlata" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="LEY_MINIMA">LEY MINIMA</th>
        <th data-override="LEY_MAXIMA">LEY MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoCalidadPlata" value="${bonoInstance?.bonoCalidadPlata}" />
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Plata a la Cantidad</h1>
</div>
<div>
<div class="fieldcontain">
    <label for="cantidadMinimaBonoCantidadPlata">Cantidad Minima</label>
    <input type="text" id="cantidadMinimaBonoCantidadPlata"/>
</div>

<div class="fieldcontain">
    <label for="cantidadMaximaBonoCantidadPlata">Cantidad Maxima</label>
    <input type="text" id="cantidadMaximaBonoCantidadPlata"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoCantidadPlata">Bono</label>
    <input type="text" id="bonoBonoCantidadPlata"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRowCantidad('tablaBonoCantidadPlata','bonoCantidadPlata','cantidadMinimaBonoCantidadPlata','cantidadMaximaBonoCantidadPlata','bonoBonoCantidadPlata')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRowCantidad('tablaBonoCantidadPlata','bonoCantidadPlata')" />
<br>

<br>
<table id="tablaBonoCantidadPlata" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="CANTIDAD_MINIMA">CANTIDAD MINIMA</th>
        <th data-override="CANTIDAD_MAXIMA">CANTIDAD MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoCantidadPlata" value="${bonoInstance?.bonoCantidadPlata}" />
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Plata como Incentivo</h1>
</div>

<div>
<div class="fieldcontain">
    <label for="cantidadMinimaBonoIncentivoPlata">Cantidad Minima</label>
    <input type="text" id="cantidadMinimaBonoIncentivoPlata"/>
</div>

<div class="fieldcontain">
    <label for="cantidadMaximaBonoIncentivoPlata">Cantidad Maxima</label>
    <input type="text" id="cantidadMaximaBonoIncentivoPlata"/>
</div>

<div class="fieldcontain">
    <label for="calidadMinimaBonoIncentivoPlata">Calidad Minima</label>
    <input type="text" id="calidadMinimaBonoIncentivoPlata"/>
</div>

<div class="fieldcontain">
    <label for="calidadMaximaBonoIncentivoPlata">Calidad Maxima</label>
    <input type="text" id="calidadMaximaBonoIncentivoPlata"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoIncentivoPlata">Bono</label>
    <input type="text" id="bonoBonoIncentivoPlata"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRowIncentivo('tablaBonoIncentivoPlata','bonoIncentivoPlata','cantidadMinimaBonoIncentivoPlata','cantidadMaximaBonoIncentivoPlata','calidadMinimaBonoIncentivoPlata','calidadMaximaBonoIncentivoPlata','bonoBonoIncentivoPlata')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRowIncentivo('tablaBonoIncentivoPlata','bonoIncentivoPlata')" />
<br>

<br>
<table id="tablaBonoIncentivoPlata" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="CANTIDAD_MINIMA">CANTIDAD MINIMA</th>
        <th data-override="CANTIDAD_MAXIMA">CANTIDAD MAXIMA</th>
        <th data-override="CALIDAD_MINIMA">CALIDAD MINIMA</th>
        <th data-override="CALIDAD_MAXIMA">CALIDAD MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoIncentivoPlata" value="${bonoInstance?.bonoIncentivoPlata}" />
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Wolfran a la Calidad</h1>
</div>
<div>
<div class="fieldcontain">
    <label for="leyMinimaBonoCalidadWolfran">Ley Minima</label>
    <input type="text" id="leyMinimaBonoCalidadWolfran"/>
</div>

<div class="fieldcontain">
    <label for="leyMaximaBonoCalidadWolfran">Ley Maxima</label>
    <input type="text" id="leyMaximaBonoCalidadWolfran"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoCalidadWolfran">Bono</label>
    <input type="text" id="bonoBonoCalidadWolfran"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRow('tablaBonoCalidadWolfran','bonoCalidadWolfran','leyMinimaBonoCalidadWolfran','leyMaximaBonoCalidadWolfran','bonoBonoCalidadWolfran')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRow('tablaBonoCalidadWolfran','bonoCalidadWolfran')" />
<br>

<br>
<table id="tablaBonoCalidadWolfran" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="LEY_MINIMA">LEY MINIMA</th>
        <th data-override="LEY_MAXIMA">LEY MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoCalidadWolfran" value="${bonoInstance?.bonoCalidadWolfran}" />
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Wolfran a la Cantidad</h1>
</div>
<div>
<div class="fieldcontain">
    <label for="cantidadMinimaBonoCantidadWolfran">Cantidad Minima</label>
    <input type="text" id="cantidadMinimaBonoCantidadWolfran"/>
</div>

<div class="fieldcontain">
    <label for="cantidadMaximaBonoCantidadWolfran">Cantidad Maxima</label>
    <input type="text" id="cantidadMaximaBonoCantidadWolfran"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoCantidadWolfran">Bono</label>
    <input type="text" id="bonoBonoCantidadWolfran"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRowCantidad('tablaBonoCantidadWolfran','bonoCantidadWolfran','cantidadMinimaBonoCantidadWolfran','cantidadMaximaBonoCantidadWolfran','bonoBonoCantidadWolfran')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRowCantidad('tablaBonoCantidadWolfran','bonoCantidadWolfran')" />
<br>

<br>
<table id="tablaBonoCantidadWolfran" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="CANTIDAD_MINIMA">CANTIDAD MINIMA</th>
        <th data-override="CANTIDAD_MAXIMA">CANTIDAD MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoCantidadWolfran" value="${bonoInstance?.bonoCantidadWolfran}" />
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Wolfran como Incentivo</h1>
</div>
<div>
<div class="fieldcontain">
    <label for="cantidadMinimaBonoIncentivoWolfran">Cantidad Minima</label>
    <input type="text" id="cantidadMinimaBonoIncentivoWolfran"/>
</div>

<div class="fieldcontain">
    <label for="cantidadMaximaBonoIncentivoWolfran">Cantidad Maxima</label>
    <input type="text" id="cantidadMaximaBonoIncentivoWolfran"/>
</div>

<div class="fieldcontain">
    <label for="calidadMinimaBonoIncentivoWolfran">Calidad Minima</label>
    <input type="text" id="calidadMinimaBonoIncentivoWolfran"/>
</div>

<div class="fieldcontain">
    <label for="calidadMaximaBonoIncentivoWolfran">Calidad Maxima</label>
    <input type="text" id="calidadMaximaBonoIncentivoWolfran"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoIncentivoWolfran">Bono</label>
    <input type="text" id="bonoBonoIncentivoWolfran"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRowIncentivo('tablaBonoIncentivoWolfran','bonoIncentivoWolfran','cantidadMinimaBonoIncentivoWolfran','cantidadMaximaBonoIncentivoWolfran','calidadMinimaBonoIncentivoWolfran','calidadMaximaBonoIncentivoWolfran','bonoBonoIncentivoWolfran')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRowIncentivo('tablaBonoIncentivoWolfran','bonoIncentivoWolfran')" />
<br>

<br>
<table id="tablaBonoIncentivoWolfran" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="CANTIDAD_MINIMA">CANTIDAD MINIMA</th>
        <th data-override="CANTIDAD_MAXIMA">CANTIDAD MAXIMA</th>
        <th data-override="CALIDAD_MINIMA">CALIDAD MINIMA</th>
        <th data-override="CALIDAD_MAXIMA">CALIDAD MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoIncentivoWolfran" value="${bonoInstance?.bonoIncentivoWolfran}" />
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Antimonio a la Calidad</h1>
</div>
<div>

<div class="fieldcontain">
    <label for="leyMinimaBonoCalidadAntimonio">Ley Minima</label>
    <input type="text" id="leyMinimaBonoCalidadAntimonio"/>
</div>

<div class="fieldcontain">
    <label for="leyMaximaBonoCalidadAntimonio">Ley Maxima</label>
    <input type="text" id="leyMaximaBonoCalidadAntimonio"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoCalidadAntimonio">Bono</label>
    <input type="text" id="bonoBonoCalidadAntimonio"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRow('tablaBonoCalidadAntimonio','bonoCalidadAntimonio','leyMinimaBonoCalidadAntimonio','leyMaximaBonoCalidadAntimonio','bonoBonoCalidadAntimonio')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRow('tablaBonoCalidadAntimonio','bonoCalidadAntimonio')" />
<br>

<br>
<table id="tablaBonoCalidadAntimonio" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="LEY_MINIMA">LEY MINIMA</th>
        <th data-override="LEY_MAXIMA">LEY MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoCalidadAntimonio" value="${bonoInstance?.bonoCalidadAntimonio}" />
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Antimonio a la Cantidad</h1>
</div>

<div>
<div class="fieldcontain">
    <label for="cantidadMinimaBonoCantidadAntimonio">Cantidad Minima</label>
    <input type="text" id="cantidadMinimaBonoCantidadAntimonio"/>
</div>

<div class="fieldcontain">
    <label for="cantidadMaximaBonoCantidadAntimonio">Cantidad Maxima</label>
    <input type="text" id="cantidadMaximaBonoCantidadAntimonio"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoCantidadAntimonio">Bono</label>
    <input type="text" id="bonoBonoCantidadAntimonio"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRowCantidad('tablaBonoCantidadAntimonio','bonoCantidadAntimonio','cantidadMinimaBonoCantidadAntimonio','cantidadMaximaBonoCantidadAntimonio','bonoBonoCantidadAntimonio')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRowCantidad('tablaBonoCantidadAntimonio','bonoCantidadAntimonio')" />
<br>

<br>
<table id="tablaBonoCantidadAntimonio" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="CANTIDAD_MINIMA">CANTIDAD MINIMA</th>
        <th data-override="CANTIDAD_MAXIMA">CANTIDAD MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoCantidadAntimonio" value="${bonoInstance?.bonoCantidadAntimonio}" />
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Antimonio como Incentivo</h1>
</div>

<div>
<div class="fieldcontain">
    <label for="cantidadMinimaBonoIncentivoAntimonio">Cantidad Minima</label>
    <input type="text" id="cantidadMinimaBonoIncentivoAntimonio"/>
</div>

<div class="fieldcontain">
    <label for="cantidadMaximaBonoIncentivoAntimonio">Cantidad Maxima</label>
    <input type="text" id="cantidadMaximaBonoIncentivoAntimonio"/>
</div>

<div class="fieldcontain">
    <label for="calidadMinimaBonoIncentivoAntimonio">Calidad Minima</label>
    <input type="text" id="calidadMinimaBonoIncentivoAntimonio"/>
</div>

<div class="fieldcontain">
    <label for="calidadMaximaBonoIncentivoAntimonio">Calidad Maxima</label>
    <input type="text" id="calidadMaximaBonoIncentivoAntimonio"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoIncentivoAntimonio">Bono</label>
    <input type="text" id="bonoBonoIncentivoAntimonio"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRowIncentivo('tablaBonoIncentivoAntimonio','bonoIncentivoAntimonio','cantidadMinimaBonoIncentivoAntimonio','cantidadMaximaBonoIncentivoAntimonio','calidadMinimaBonoIncentivoAntimonio','calidadMaximaBonoIncentivoAntimonio','bonoBonoIncentivoAntimonio')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRowIncentivo('tablaBonoIncentivoAntimonio','bonoIncentivoAntimonio')" />
<br>

<br>
<table id="tablaBonoIncentivoAntimonio" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="CANTIDAD_MINIMA">CANTIDAD MINIMA</th>
        <th data-override="CANTIDAD_MAXIMA">CANTIDAD MAXIMA</th>
        <th data-override="CALIDAD_MINIMA">CALIDAD MINIMA</th>
        <th data-override="CALIDAD_MAXIMA">CALIDAD MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoIncentivoAntimonio" value="${bonoInstance?.bonoIncentivoAntimonio}" />
</div>
<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Complejo a la Calidad</h1>
</div>
<div>
<div class="fieldcontain">
    <label for="leyMinimaBonoCalidadComplejo">Ley Minima</label>
    <input type="text" id="leyMinimaBonoCalidadComplejo"/>
</div>

<div class="fieldcontain">
    <label for="leyMaximaBonoCalidadComplejo">Ley Maxima</label>
    <input type="text" id="leyMaximaBonoCalidadComplejo"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoCalidadComplejo">Bono</label>
    <input type="text" id="bonoBonoCalidadComplejo"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRow('tablaBonoCalidadComplejo','bonoCalidadComplejo','leyMinimaBonoCalidadComplejo','leyMaximaBonoCalidadComplejo','bonoBonoCalidadComplejo')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRow('tablaBonoCalidadComplejo','bonoCalidadComplejo')" />
<br>

<br>
<table id="tablaBonoCalidadComplejo" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="LEY_MINIMA">LEY MINIMA</th>
        <th data-override="LEY_MAXIMA">LEY MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoCalidadComplejo" value="${bonoInstance?.bonoCalidadComplejo}" />
</div>
<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Complejo a la Cantidad</h1>
</div>
<div>
<div class="fieldcontain">
    <label for="cantidadMinimaBonoCantidadComplejo">Cantidad Minima</label>
    <input type="text" id="cantidadMinimaBonoCantidadComplejo"/>
</div>

<div class="fieldcontain">
    <label for="cantidadMaximaBonoCantidadComplejo">Cantidad Maxima</label>
    <input type="text" id="cantidadMaximaBonoCantidadComplejo"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoCantidadComplejo">Bono</label>
    <input type="text" id="bonoBonoCantidadComplejo"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRowCantidad('tablaBonoCantidadComplejo','bonoCantidadComplejo','cantidadMinimaBonoCantidadComplejo','cantidadMaximaBonoCantidadComplejo','bonoBonoCantidadComplejo')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRowCantidad('tablaBonoCantidadComplejo','bonoCantidadComplejo')" />
<br>

<br>
<table id="tablaBonoCantidadComplejo" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="CANTIDAD_MINIMA">CANTIDAD MINIMA</th>
        <th data-override="CANTIDAD_MAXIMA">CANTIDAD MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoCantidadComplejo" value="${bonoInstance?.bonoCantidadComplejo}" />
</div>

<div class="collapsible" style="cursor: pointer">
    <h1 style="font-weight: bold">Bono de Complejo como Incentivo</h1>
</div>
<div>
<div class="fieldcontain">
    <label for="cantidadMinimaBonoIncentivoComplejo">Cantidad Minima</label>
    <input type="text" id="cantidadMinimaBonoIncentivoComplejo"/>
</div>

<div class="fieldcontain">
    <label for="cantidadMaximaBonoIncentivoComplejo">Cantidad Maxima</label>
    <input type="text" id="cantidadMaximaBonoIncentivoComplejo"/>
</div>

<div class="fieldcontain">
    <label for="calidadMinimaBonoIncentivoComplejo">Calidad Minima</label>
    <input type="text" id="calidadMinimaBonoIncentivoComplejo"/>
</div>

<div class="fieldcontain">
    <label for="calidadMaximaBonoIncentivoComplejo">Calidad Maxima</label>
    <input type="text" id="calidadMaximaBonoIncentivoComplejo"/>
</div>

<div class="fieldcontain">
    <label for="bonoBonoIncentivoComplejo">Bono</label>
    <input type="text" id="bonoBonoIncentivoComplejo"/>
</div>

<br>
<input type="button" value="Adicionar Fila" onclick="addRowIncentivo('tablaBonoIncentivoComplejo','bonoIncentivoComplejo','cantidadMinimaBonoIncentivoComplejo','cantidadMaximaBonoIncentivoComplejo','calidadMinimaBonoIncentivoComplejo','calidadMaximaBonoIncentivoComplejo','bonoBonoIncentivoComplejo')" />
<input type="button" value="Eliminar Fila Seleccionada" onclick="deleteRowIncentivo('tablaBonoIncentivoComplejo','bonoIncentivoComplejo')" />
<br>

<br>
<table id="tablaBonoIncentivoComplejo" class="center" style="width: 700px;">
    <tr>
        <th>¿ELIMINAR?</th>
        <th data-override="CANTIDAD_MINIMA">CANTIDAD MINIMA</th>
        <th data-override="CANTIDAD_MAXIMA">CANTIDAD MAXIMA</th>
        <th data-override="CALIDAD_MINIMA">CALIDAD MINIMA</th>
        <th data-override="CALIDAD_MAXIMA">CALIDAD MAXIMA</th>
        <th data-override="BONO">BONO</th>
    </tr>
</table>

<g:hiddenField name="bonoIncentivoComplejo" value="${bonoInstance?.bonoIncentivoComplejo}" />
</div>