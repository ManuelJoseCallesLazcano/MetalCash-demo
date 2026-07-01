<%@ page import="org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo; org.socymet.cotizaciones.CotizacionDiariaDeMinerales; org.socymet.proveedor.Empresa" %>
<g:set var="i" value="${tablaOrigenCotizacionesComplejoInstance}"/>
<g:hiddenField name="id" value="${i?.id}"/>
<g:hiddenField name="version" value="${i?.version}"/>

<style>
    .tp-section {
        font-size: 0.86rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.08em;
        border-left: 5px solid #888; border-bottom: 1px solid #e0e0e0;
        padding: 0.5rem 0.9rem; margin: 1.2rem 0 0.8rem; border-radius: 0 4px 4px 0;
        box-shadow: 0 1px 2px rgba(0,0,0,.05);
    }
    .tp-general { border-left-color: #17a2b8; background: linear-gradient(to right, #d6f0f4, transparent); color: #0f6674; }
    .tp-zinc    { border-left-color: #1976d2; background: linear-gradient(to right, #e3f0fb, transparent); color: #0d47a1; }
    .tp-plomo   { border-left-color: #6d4c41; background: linear-gradient(to right, #efe9e6, transparent); color: #4e342e; }
    .tp-plata   { border-left-color: #00897b; background: linear-gradient(to right, #dff3f0, transparent); color: #00695c; }
</style>

<div class="form-group row">
    <label class="col-sm-2 col-form-label">Nombre <span class="text-danger">*</span></label>
    <div class="col-sm-8"><g:textField name="nombreTabla" value="${i?.nombreTabla}" class="form-control" required=""/></div>
</div>
<%-- Ocultos: naturaleza (blank:false → default SULFURO), empresa (se preserva), fecha (se setea en el servidor) --%>
<g:hiddenField name="naturalezaMineral" value="${i?.naturalezaMineral ?: 'SULFURO'}"/>
<g:hiddenField name="empresa.id" value="${i?.empresa?.id}"/>
<div class="form-group row">
    <label class="col-sm-2 col-form-label">Cotización Diaria (referencial)</label>
    <div class="col-sm-8">
        <select id="cotId" name="cotizacionDiariaDeMinerales.id" class="form-control">
            <option value="">(ninguna)</option>
            <g:each in="${CotizacionDiariaDeMinerales.list([sort: 'fecha', order: 'desc', max: 20])}" var="c">
                <option value="${c.id}" data-zinc="${c.zinc}" data-plomo="${c.plomo}" data-plata="${c.plata}" ${i?.cotizacionDiariaDeMinerales?.id == c.id ? 'selected' : ''}>${c.encodeAsHTML()}</option>
            </g:each>
        </select>
    </div>
</div>

<h5 class="tp-section tp-general">Cotización en Tonelada [$us/TM]</h5>
<div class="form-group row">
    <label class="col-sm-2 col-form-label">Zinc</label>
    <div class="col-sm-2"><input type="text" id="cotTonZinc" class="form-control text-right" readonly/></div>
    <label class="col-sm-2 col-form-label">Plomo</label>
    <div class="col-sm-2"><input type="text" id="cotTonPlomo" class="form-control text-right" readonly/></div>
    <label class="col-sm-2 col-form-label">Plata</label>
    <div class="col-sm-2"><input type="text" id="cotTonPlata" class="form-control text-right" readonly/></div>
</div>

<div class="row">
    <%-- ZINC --%>
    <div class="col-md-4">
        <h5 class="tp-section tp-zinc">Zinc</h5>
        <table class="table table-sm table-bordered tabla-puntos" id="tablaZinc" data-elemento="ZINC">
            <thead class="thead-light"><tr><th>LEY [%]</th><th>% PAGABLE</th><th>VPT $us</th><th></th></tr></thead>
            <tbody>
                <g:each in="${i?.puntos?.findAll { it.elemento == 'ZINC' }?.sort { it.ley }}" var="pt">
                    <tr class="punto-row">
                        <td><input type="number" step="any" name="leyZinc" class="form-control form-control-sm text-right punto-ley" value="${pt.ley}"/></td>
                        <td><input type="number" step="any" name="pagZinc" class="form-control form-control-sm text-right punto-pag" value="${pt.porcentajePagable}"/></td>
                        <td class="text-right punto-vpt"></td>
                        <td class="text-center"><button type="button" class="btn btn-sm btn-outline-danger btn-quitar-punto"><i class="fas fa-times"></i></button></td>
                    </tr>
                </g:each>
            </tbody>
        </table>
        <button type="button" class="btn btn-sm btn-outline-primary btn-add-punto" data-tabla="tablaZinc" data-elemento="ZINC"><i class="fas fa-plus mr-1"></i>Agregar fila</button>
    </div>
    <%-- PLOMO --%>
    <div class="col-md-4">
        <h5 class="tp-section tp-plomo">Plomo</h5>
        <table class="table table-sm table-bordered tabla-puntos" id="tablaPlomo" data-elemento="PLOMO">
            <thead class="thead-light"><tr><th>LEY [%]</th><th>% PAGABLE</th><th>VPT $us</th><th></th></tr></thead>
            <tbody>
                <g:each in="${i?.puntos?.findAll { it.elemento == 'PLOMO' }?.sort { it.ley }}" var="pt">
                    <tr class="punto-row">
                        <td><input type="number" step="any" name="leyPlomo" class="form-control form-control-sm text-right punto-ley" value="${pt.ley}"/></td>
                        <td><input type="number" step="any" name="pagPlomo" class="form-control form-control-sm text-right punto-pag" value="${pt.porcentajePagable}"/></td>
                        <td class="text-right punto-vpt"></td>
                        <td class="text-center"><button type="button" class="btn btn-sm btn-outline-danger btn-quitar-punto"><i class="fas fa-times"></i></button></td>
                    </tr>
                </g:each>
            </tbody>
        </table>
        <button type="button" class="btn btn-sm btn-outline-primary btn-add-punto" data-tabla="tablaPlomo" data-elemento="PLOMO"><i class="fas fa-plus mr-1"></i>Agregar fila</button>
    </div>
    <%-- PLATA --%>
    <div class="col-md-4">
        <h5 class="tp-section tp-plata">Plata</h5>
        <table class="table table-sm table-bordered tabla-puntos" id="tablaPlata" data-elemento="PLATA">
            <thead class="thead-light"><tr><th>LEY [DM]</th><th>% PAGABLE</th><th>VPT $us</th><th></th></tr></thead>
            <tbody>
                <g:each in="${i?.puntos?.findAll { it.elemento == 'PLATA' }?.sort { it.ley }}" var="pt">
                    <tr class="punto-row">
                        <td><input type="number" step="any" name="leyPlata" class="form-control form-control-sm text-right punto-ley" value="${pt.ley}"/></td>
                        <td><input type="number" step="any" name="pagPlata" class="form-control form-control-sm text-right punto-pag" value="${pt.porcentajePagable}"/></td>
                        <td class="text-right punto-vpt"></td>
                        <td class="text-center"><button type="button" class="btn btn-sm btn-outline-danger btn-quitar-punto"><i class="fas fa-times"></i></button></td>
                    </tr>
                </g:each>
            </tbody>
        </table>
        <button type="button" class="btn btn-sm btn-outline-primary btn-add-punto" data-tabla="tablaPlata" data-elemento="PLATA"><i class="fas fa-plus mr-1"></i>Agregar fila</button>
    </div>
</div>
