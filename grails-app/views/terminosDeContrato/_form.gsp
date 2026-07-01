<%@ page import="org.socymet.cotizaciones.TerminosDeContrato; org.socymet.proveedor.Empresa" %>
<g:set var="i" value="${terminosDeContratoInstance}"/>

<style>
    .tc-section {
        font-size: 0.84rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.08em;
        color: #0f6674; border-left: 5px solid #17a2b8; border-bottom: 1px solid #bfe6ec;
        background: linear-gradient(to right, #d6f0f4, #eef9fb 55%, transparent);
        padding: 0.5rem 0.9rem; margin: 1.4rem 0 0.9rem; border-radius: 0 4px 4px 0;
        box-shadow: 0 1px 2px rgba(0,0,0,.05);
    }
</style>

<g:hiddenField name="vista"/>
<%-- Ocultos: empresa y campos de cobre (se conservan con sus valores/por defecto) --%>
<g:hiddenField name="empresa.id" value="${i?.empresa?.id}"/>
<g:hiddenField name="deduccionUnitariaCobre" value="${i?.deduccionUnitariaCobre ?: 0}"/>
<g:hiddenField name="porcentajePagableLMECobre" value="${i?.porcentajePagableLMECobre ?: 0}"/>
<g:hiddenField name="maquilaCobre" value="${i?.maquilaCobre ?: 0}"/>
<g:hiddenField name="baseCobre" value="${i?.baseCobre ?: 0}"/>
<g:hiddenField name="escaladorCobre" value="${i?.escaladorCobre ?: 0}"/>
<g:hiddenField name="deduccionRefinacionOnzaCobrePlata" value="${i?.deduccionRefinacionOnzaCobrePlata ?: 0}"/>
<g:hiddenField name="deduccionRefinacionLibraCobre" value="${i?.deduccionRefinacionLibraCobre ?: 0}"/>

<h5 class="tc-section">Datos del Contrato</h5>
<div class="form-group row">
    <label class="col-sm-2 col-form-label">Nombre del Contrato <span class="text-danger">*</span></label>
    <div class="col-sm-7"><g:textField name="nombreContrato" value="${i?.nombreContrato}" class="form-control" required=""/></div>
    <label class="col-sm-1 col-form-label">Mineral</label>
    <div class="col-sm-2"><g:select name="tipoDeMineral" id="tipoDeMineral" from="${['PB-AG','ZN-AG']}" value="${i?.tipoDeMineral ?: 'PB-AG'}" class="form-control"/></div>
</div>

<h5 class="tc-section">Impurezas Referenciales [%]</h5>
<div class="form-row">
    <g:set var="imp" value="${[['porcentajeArsenico','Arsénico'],['porcentajeAntimonio','Antimonio'],['porcentajeBismuto','Bismuto'],['porcentajeEstano','Estaño'],['porcentajeHierro','Hierro'],['porcentajeSilice','Sílice'],['porcentajeZinc','Zinc']]}"/>
    <g:each in="${imp}" var="f">
        <div class="form-group col-md-3">
            <label>${f[1]}</label>
            <div class="input-group">
                <g:field type="number" step="any" inputmode="decimal" name="${f[0]}" value="${i?.getProperty(f[0])}" class="form-control" required=""/>
                <div class="input-group-append"><span class="input-group-text">%</span></div>
            </div>
        </div>
    </g:each>
</div>

<h5 class="tc-section">Deducciones Unitarias</h5>
<div class="form-row">
    <g:each in="${[['deduccionUnitariaZinc','Zinc','zn','%'],['deduccionUnitariaPlomo','Plomo','pb','%'],['deduccionUnitariaPlata','Plata','both','ot']]}" var="f">
        <div class="form-group col-md-4 tc-${f[2]}">
            <label>${f[1]}</label>
            <div class="input-group">
                <g:field type="number" step="any" inputmode="decimal" name="${f[0]}" value="${i?.getProperty(f[0])}" class="form-control" required=""/>
                <div class="input-group-append"><span class="input-group-text">${f[3]}</span></div>
            </div>
        </div>
    </g:each>
</div>

<h5 class="tc-section">Cotización Pagable</h5>
<div class="form-row">
    <g:each in="${[['porcentajePagableLMEZinc','Zinc','zn'],['porcentajePagableLMEPlomo','Plomo','pb'],['porcentajePagableLMEPlata','Plata','both']]}" var="f">
        <div class="form-group col-md-4 tc-${f[2]}">
            <label>${f[1]}</label>
            <div class="input-group">
                <g:field type="number" step="any" inputmode="decimal" name="${f[0]}" value="${i?.getProperty(f[0])}" class="form-control" required=""/>
                <div class="input-group-append"><span class="input-group-text">%</span></div>
            </div>
        </div>
    </g:each>
</div>

<h5 class="tc-section">Maquila + Escalador</h5>
<div class="form-row">
    <g:each in="${[['maquilaZincPlata','Maquila','zn','\$us'],['baseZincPlata','Base','zn','\$us'],['escaladorZincPlata','Escalador','zn',''],['maquilaPlomoPlata','Maquila','pb','\$us'],['basePlomoPlata','Base','pb','\$us'],['escaladorPlomoPlata','Escalador','pb','']]}" var="f">
        <div class="form-group col-md-4 tc-${f[2]}">
            <label>${f[1]}</label>
            <g:if test="${f[3]}">
                <div class="input-group">
                    <g:field type="number" step="any" inputmode="decimal" name="${f[0]}" value="${i?.getProperty(f[0])}" class="form-control" required=""/>
                    <div class="input-group-append"><span class="input-group-text">${f[3]}</span></div>
                </div>
            </g:if>
            <g:else>
                <g:field type="number" step="any" inputmode="decimal" name="${f[0]}" value="${i?.getProperty(f[0])}" class="form-control" required=""/>
            </g:else>
        </div>
    </g:each>
</div>

<h5 class="tc-section">Gastos de Refinación</h5>
<div class="form-row">
    <g:each in="${[['deduccionRefinacionOnzaZincPlata','Gasto de Refinación','zn'],['deduccionRefinacionOnzaPlomoPlata','Gasto de Refinación','pb']]}" var="f">
        <div class="form-group col-md-4 tc-${f[2]}">
            <label>${f[1]}</label>
            <div class="input-group">
                <g:field type="number" step="any" inputmode="decimal" name="${f[0]}" value="${i?.getProperty(f[0])}" class="form-control" required=""/>
                <div class="input-group-append"><span class="input-group-text">$us/ot</span></div>
            </div>
        </div>
    </g:each>
</div>

<%-- Sección "Gastos de Refinación por Libra" oculta; campos conservados con su valor/por defecto --%>
<g:hiddenField name="deduccionRefinacionLibraZinc" value="${i?.deduccionRefinacionLibraZinc ?: 0}"/>
<g:hiddenField name="deduccionRefinacionLibraPlomo" value="${i?.deduccionRefinacionLibraPlomo ?: 0}"/>

<h5 class="tc-section">Penalidades</h5>
<div class="table-responsive">
    <table class="table table-sm table-bordered" style="max-width:720px">
        <thead class="thead-light"><tr><th>Elemento</th><th>Límite</th><th>Costo Unitario</th><th>Porcentaje Unitario</th></tr></thead>
        <tbody>
            <g:each in="${[['Arsénico','arsenicoLibre','costoUnitarioArsenico','porcentajeUnitarioArsenico'],['Antimonio','antimonioLibre','costoUnitarioAntimonio','porcentajeUnitarioAntimonio'],['Bismuto','bismutoLibre','costoUnitarioBismuto','porcentajeUnitarioBismuto'],['Estaño','estanoLibre','costoUnitarioEstano','porcentajeUnitarioEstano'],['Hierro','hierroLibre','costoUnitarioHierro','porcentajeUnitarioHierro'],['Sílice','siliceLibre','costoUnitarioSilice','porcentajeUnitarioSilice'],['Zinc','zincLibre','costoUnitarioZinc','porcentajeUnitarioZinc']]}" var="r">
                <tr>
                    <td class="align-middle font-weight-bold">${r[0]}</td>
                    <td>
                        <div class="input-group input-group-sm">
                            <g:field type="number" step="any" inputmode="decimal" name="${r[1]}" value="${i?.getProperty(r[1])}" class="form-control form-control-sm text-right" required=""/>
                            <div class="input-group-append"><span class="input-group-text">%</span></div>
                        </div>
                    </td>
                    <td>
                        <div class="input-group input-group-sm">
                            <div class="input-group-prepend"><span class="input-group-text">$us</span></div>
                            <g:field type="number" step="any" inputmode="decimal" name="${r[2]}" value="${i?.getProperty(r[2])}" class="form-control form-control-sm text-right" required=""/>
                        </div>
                    </td>
                    <td>
                        <div class="input-group input-group-sm">
                            <g:field type="number" step="any" inputmode="decimal" name="${r[3]}" value="${i?.getProperty(r[3])}" class="form-control form-control-sm text-right" required=""/>
                            <div class="input-group-append"><span class="input-group-text">%</span></div>
                        </div>
                    </td>
                </tr>
            </g:each>
        </tbody>
    </table>
</div>

<h5 class="tc-section">Otras Deducciones</h5>
<div class="form-row">
    <g:each in="${[['transporteInterno','Transporte Interno'],['laboratorio','Laboratorio'],['molienda','Molienda'],['manipuleo','Manipuleo'],['margenAdministrativo','Margen Administrativo'],['transporteAPuerto','Transporte a Puerto'],['rollBack','Roll Back']]}" var="f">
        <div class="form-group col-md-3">
            <label>${f[1]}</label>
            <div class="input-group">
                <g:field type="number" step="any" inputmode="decimal" name="${f[0]}" value="${i?.getProperty(f[0])}" class="form-control" required=""/>
                <div class="input-group-append"><span class="input-group-text">$us/tn</span></div>
            </div>
        </div>
    </g:each>
</div>

<script>
    (function () {
        // Muestra los campos Zn-Ag o Pb-Ag según el tipo de mineral (plata y demás van en ambos).
        // Al ocultar: quita 'required' (para no bloquear el submit) y pone 0 si está vacío (nullable:false).
        function toggleGrupo(selector, visible) {
            document.querySelectorAll(selector).forEach(function (cont) {
                cont.style.display = visible ? '' : 'none';
                var inp = cont.querySelector('input, select');
                if (!inp) return;
                if (visible) {
                    inp.setAttribute('required', 'required');
                } else {
                    inp.removeAttribute('required');
                    if (inp.value === '' || inp.value == null) inp.value = '0';
                }
            });
        }
        function aplicarTipoMineral() {
            var sel = document.getElementById('tipoDeMineral');
            var pb = !sel || sel.value === 'PB-AG';
            toggleGrupo('.tc-pb', pb);
            toggleGrupo('.tc-zn', !pb);
        }
        var sel = document.getElementById('tipoDeMineral');
        if (sel) sel.addEventListener('change', aplicarTipoMineral);
        if (document.readyState !== 'loading') aplicarTipoMineral();
        else document.addEventListener('DOMContentLoaded', aplicarTipoMineral);
    })();
</script>
