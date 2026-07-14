<%-- Cuerpo compartido de conformación de compósitos (create/edit). Requiere modelo:
     reporteCompositoDeLotesInstance, formAction ('save'|'update'), preseleccionJson (String JSON, def '[]'). --%>
<g:set var="preseleccionJson" value="${preseleccionJson ?: '[]'}"/>
<script>window.COMPOSITO_PRESELECCION = ${raw(preseleccionJson)};</script>

<div class="card card-primary">
    <div class="card-header"><h3 class="card-title">${formAction == 'update' ? 'Editar' : 'Nuevo'} Compósito de Lotes</h3></div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div id="swalFlashMsg" style="display:none">${flash.message}</div>
            <script>document.addEventListener('DOMContentLoaded', function () { Swal.fire({ icon: 'warning', title: 'Atención', text: document.getElementById('swalFlashMsg').textContent.trim() }); });</script>
        </g:if>
        <g:hasErrors bean="${reporteCompositoDeLotesInstance}">
            <div id="swalErrorList" style="display:none"><ul style="text-align:left;margin:0;padding-left:1.2em"><g:eachError bean="${reporteCompositoDeLotesInstance}" var="error"><li><g:message error="${error}"/></li></g:eachError></ul></div>
            <script>document.addEventListener('DOMContentLoaded', function () { Swal.fire({ icon: 'error', title: 'Error de validación', html: document.getElementById('swalErrorList').innerHTML }); });</script>
        </g:hasErrors>

        <%-- Form plano (CSRF deshabilitado en la app): controlamos URL e id HTML explícitamente.
             OJO: en <g:form> el atributo id se interpreta como el id de la URL (/update/compositoForm),
             lo que hacía que update(Long id) recibiera null. Con createLink la URL queda limpia y el
             id de la ruta lo aporta el hidden field 'id'. --%>
        <form action="${createLink(action: formAction)}" method="post" name="compositoForm" id="compositoForm">
            <g:if test="${reporteCompositoDeLotesInstance?.id}">
                <g:hiddenField name="id" value="${reporteCompositoDeLotesInstance?.id}"/>
                <g:hiddenField name="version" value="${reporteCompositoDeLotesInstance?.version}"/>
            </g:if>
            <g:hiddenField name="recepcionIds" id="recepcionIds" value=""/>
            <g:hiddenField name="deposito.id" value="${reporteCompositoDeLotesInstance?.deposito?.id}"/>

            <div class="form-row">
                <div class="form-group col-md-3">
                    <label for="sigla">Sigla</label>
                    <g:textField name="sigla" id="sigla" class="form-control" value="${reporteCompositoDeLotesInstance?.sigla}" required="required"/>
                </div>
                <div class="form-group col-md-3">
                    <label for="destino">Destino</label>
                    <g:select name="destino" id="destino" class="form-control" from="${['VENTA','EXPORTACION','INGENIO']}" value="${reporteCompositoDeLotesInstance?.destino}"/>
                </div>
                <div class="form-group col-md-3" id="_comprador">
                    <label for="comprador">Comprador</label>
                    <g:select name="comprador.id" id="comprador" class="form-control" from="${org.smart.compositos.Comprador.list([sort: 'nombreComprador'])}" optionKey="id" value="${reporteCompositoDeLotesInstance?.comprador?.id}" noSelection="['': '-- Seleccione --']"/>
                </div>
                <div class="form-group col-md-3" id="_ingenio">
                    <label for="ingenio">Ingenio</label>
                    <g:select name="ingenio.id" id="ingenio" class="form-control" from="${org.smart.compositos.Ingenio.list([sort: 'nombreIngenio'])}" optionKey="id" value="${reporteCompositoDeLotesInstance?.ingenio?.id}" noSelection="['': '-- Seleccione --']"/>
                </div>
            </div>
            <%-- Elaborado por y Fecha de elaboración se asignan en el backend (usuario actual /
                 momento del registro o última modificación); no se editan en el formulario. --%>
            <div class="form-row">
                <div class="form-group col-md-3">
                    <label for="estadoDelComposito">Estado</label>
                    <g:select name="estadoDelComposito" id="estadoDelComposito" class="form-control" from="${['PROVISIONAL','DEFINITIVO']}" value="${reporteCompositoDeLotesInstance?.estadoDelComposito}"/>
                </div>
                <div class="form-group col-md-9">
                    <label for="observaciones">Observaciones</label>
                    <g:textField name="observaciones" id="observaciones" class="form-control" value="${reporteCompositoDeLotesInstance?.observaciones}"/>
                </div>
            </div>
        </form>
    </div>
</div>

<div class="card card-outline card-info">
    <div class="card-header"><h3 class="card-title"><i class="fas fa-filter mr-1"></i>Filtrar lotes disponibles</h3></div>
    <div class="card-body">
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="empresa">Empresa</label>
                <g:select name="empresa.id" id="empresa" class="form-control" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${reporteCompositoDeLotesInstance?.empresa?.id}" noSelection="['': '-TODAS-']" form="compositoForm"/>
            </div>
            <div class="form-group col-md-3">
                <label for="f_lote">Buscar lote</label>
                <input type="text" id="f_lote" class="form-control" placeholder="código de lote…">
            </div>
            <div class="form-group col-md-2">
                <label for="ordenarElemento">Ordenar por</label>
                <g:select name="ordenarElemento" id="ordenarElemento" class="form-control" from="${['ZINC','PLOMO','PLATA']}" value="${reporteCompositoDeLotesInstance?.ordenarElemento}" form="compositoForm"/>
            </div>
            <div class="form-group col-md-2">
                <label for="f_orden">Sentido</label>
                <select id="f_orden" class="form-control"><option value="desc">Descendente</option><option value="asc">Ascendente</option></select>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-2"><label>Ley Zn mín</label>
                <div class="input-group"><g:field name="leyMinimaZinc" id="leyMinimaZinc" class="form-control" value="${reporteCompositoDeLotesInstance?.leyMinimaZinc}" form="compositoForm"/><div class="input-group-append"><span class="input-group-text">%</span></div></div>
            </div>
            <div class="form-group col-md-2"><label>Ley Zn máx</label>
                <div class="input-group"><g:field name="leyMaximaZinc" id="leyMaximaZinc" class="form-control" value="${reporteCompositoDeLotesInstance?.leyMaximaZinc}" form="compositoForm"/><div class="input-group-append"><span class="input-group-text">%</span></div></div>
            </div>
            <div class="form-group col-md-2"><label>Ley Pb mín</label>
                <div class="input-group"><g:field name="leyMinimaPlomo" id="leyMinimaPlomo" class="form-control" value="${reporteCompositoDeLotesInstance?.leyMinimaPlomo}" form="compositoForm"/><div class="input-group-append"><span class="input-group-text">%</span></div></div>
            </div>
            <div class="form-group col-md-2"><label>Ley Pb máx</label>
                <div class="input-group"><g:field name="leyMaximaPlomo" id="leyMaximaPlomo" class="form-control" value="${reporteCompositoDeLotesInstance?.leyMaximaPlomo}" form="compositoForm"/><div class="input-group-append"><span class="input-group-text">%</span></div></div>
            </div>
            <div class="form-group col-md-2"><label>Ley Ag mín</label>
                <div class="input-group"><g:field name="leyMinimaPlata" id="leyMinimaPlata" class="form-control" value="${reporteCompositoDeLotesInstance?.leyMinimaPlata}" form="compositoForm"/><div class="input-group-append"><span class="input-group-text">DM</span></div></div>
            </div>
            <div class="form-group col-md-2"><label>Ley Ag máx</label>
                <div class="input-group"><g:field name="leyMaximaPlata" id="leyMaximaPlata" class="form-control" value="${reporteCompositoDeLotesInstance?.leyMaximaPlata}" form="compositoForm"/><div class="input-group-append"><span class="input-group-text">DM</span></div></div>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3"><label for="f_fechaInicial_picker">Fecha de recepción desde</label><g:datepickerUI name="f_fechaInicial" class="form-control" placeholder="dd/mm/aaaa"/></div>
            <div class="form-group col-md-3"><label for="f_fechaFinal_picker">Fecha de recepción hasta</label><g:datepickerUI name="f_fechaFinal" class="form-control" placeholder="dd/mm/aaaa"/></div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3 mb-0">
                <button type="button" class="btn btn-info btn-block" id="btnBuscar"><i class="fas fa-search mr-1"></i>Buscar lotes</button>
            </div>
        </div>
    </div>
</div>

<div class="card card-outline card-secondary">
    <div class="card-header"><h3 class="card-title">Lotes disponibles</h3></div>
    <div class="card-body table-responsive tabla-scroll p-0">
        <table class="table table-sm table-hover tabla-lotes mb-0" id="tablaDisponibles">
            <thead class="thead-light"><tr>
                <th>Lote</th><th>Empresa</th><th>Fecha</th><th class="text-right">P. Bruto [Kg]</th><th class="text-right">Humedad [%]</th>
                <th class="text-right">PNS [Kg]</th><th class="text-right">Ley Zn [%]</th><th class="text-right">Ley Pb [%]</th><th class="text-right">Ley Ag [DM]</th>
                <th class="text-center">Estado</th><th class="text-right">Valor Neto [Bs]</th><th class="text-right">Líquido [Bs]</th><th></th>
            </tr></thead>
            <tbody><tr><td colspan="13" class="text-center text-muted py-2">Use los filtros y presione buscar.</td></tr></tbody>
        </table>
    </div>
</div>

<div class="card card-outline card-primary">
    <div class="card-header"><h3 class="card-title">Lotes en el compósito</h3></div>
    <div class="card-body table-responsive tabla-scroll p-0">
        <table class="table table-sm table-hover tabla-lotes mb-0" id="tablaConjunto">
            <thead class="thead-light"><tr>
                <th>Lote</th><th>Empresa</th><th>Fecha</th><th class="text-right">P. Bruto [Kg]</th><th class="text-right">Humedad [%]</th>
                <th class="text-right">PNS [Kg]</th><th class="text-right">Ley Zn [%]</th><th class="text-right">Ley Pb [%]</th><th class="text-right">Ley Ag [DM]</th>
                <th class="text-center">Estado</th><th class="text-right">Valor Neto [Bs]</th><th class="text-right">Líquido [Bs]</th><th></th>
            </tr></thead>
            <tbody><tr><td colspan="13" class="text-center text-muted py-2">Aún no hay lotes en el compósito.</td></tr></tbody>
            <tfoot class="font-weight-bold bg-light"><tr>
                <td colspan="3" class="text-right">TOTALES / PONDERADOS</td>
                <td class="text-right"><span id="pie_pesoBruto">0,00</span></td>
                <td class="text-right"><span id="pie_humedad">0,00</span></td>
                <td class="text-right"><span id="pie_pns">0,00</span></td>
                <td class="text-right"><span id="pie_leyZinc">0,00</span></td>
                <td class="text-right"><span id="pie_leyPlomo">0,00</span></td>
                <td class="text-right"><span id="pie_leyPlata">0,00</span></td>
                <td></td>
                <td class="text-right"><span id="pie_valorNeto">0,00</span></td>
                <td class="text-right"><span id="pie_liquido">0,00</span></td>
                <td></td>
            </tr></tfoot>
        </table>
    </div>
</div>

<div class="card card-success">
    <div class="card-header"><h3 class="card-title">Resumen del compósito</h3></div>
    <div class="card-body">
        <div class="form-row">
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><span id="out_totalPesoBruto">0,00</span></div><div class="rotulo">Peso bruto [Kg]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><span id="out_totalPNS">0,00</span></div><div class="rotulo">Peso neto seco [Kg]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><span id="out_valorNeto">0,00</span></div><div class="rotulo">Valor neto [Bs]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><span id="out_liquido">0,00</span></div><div class="rotulo">Líquido pagable [Bs]</div></div></div>
        </div>
        <div class="form-row">
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><span id="out_finosZinc">0,00</span></div><div class="rotulo">K. Finos Zn [Kg]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><span id="out_finosPlomo">0,00</span></div><div class="rotulo">K. Finos Pb [Kg]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><span id="out_finosPlata">0,00</span></div><div class="rotulo">K. Finos Ag [Kg]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><span id="out_humedad">0,00</span></div><div class="rotulo">Humedad pond. [%]</div></div></div>
        </div>
        <div class="form-row">
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><span id="out_leyZinc">0,00</span></div><div class="rotulo">Ley Zn pond. [%]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><span id="out_leyPlomo">0,00</span></div><div class="rotulo">Ley Pb pond. [%]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><span id="out_leyPlata">0,00</span></div><div class="rotulo">Ley Ag pond. [DM]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><span id="out_cantLotes">0</span> / <span id="out_cantNoLiq" class="text-warning">0</span></div><div class="rotulo">Lotes / sin liquidar</div></div></div>
        </div>
        <div class="alert alert-light border small mb-0"><i class="fas fa-info-circle mr-1"></i>Valor neto y líquido pagable suman <b>solo lotes liquidados</b>; el contador indica cuántos están sin liquidar.</div>
    </div>
    <div class="card-footer d-flex align-items-center">
        <button type="button" class="btn btn-primary" id="btnRegistrar"><i class="fas fa-save mr-1"></i>${formAction == 'update' ? 'Guardar cambios' : 'Registrar compósito'}</button>
        <g:link action="list" class="btn btn-secondary ml-1">Cancelar</g:link>
    </div>
</div>
