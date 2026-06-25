<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo; org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo; org.socymet.cotizaciones.TerminosDeContrato" %>
<g:set var="i" value="${liquidacionDeComplejoInstance}"/>

<%-- ── Campos ocultos de binding (vienen precargados del lote) ───────────── --%>
<g:hiddenField name="recepcionDeComplejo.id" value="${i?.recepcionDeComplejo?.id}"/>
<input type="hidden" id="recepcionDeComplejoId" value="${i?.recepcionDeComplejo?.id}"/>
<g:hiddenField name="cliente.id" value="${i?.cliente?.id}"/>
<g:hiddenField name="empresa.id" value="${i?.empresa?.id}"/>
<g:hiddenField name="deposito.id" value="${i?.deposito?.id}"/>
<g:hiddenField name="nombreCliente" value="${i?.nombreCliente}"/>
<g:hiddenField name="nombreEmpresa" value="${i?.nombreEmpresa}"/>
<g:hiddenField name="nombreDeposito" value="${i?.nombreDeposito}"/>
<g:hiddenField name="lote" value="${i?.lote}"/>
<g:hiddenField name="tipoDeMineral" value="${i?.tipoDeMineral}"/>
<g:hiddenField name="fechaDeRecepcion" value="${i?.fechaDeRecepcion}"/>
<g:hiddenField name="cantidadDeSacos" value="${i?.cantidadDeSacos}"/>
<g:hiddenField name="porcentajeZincPromexbol" value="${i?.porcentajeZincPromexbol}"/>
<g:hiddenField name="porcentajePlomoPromexbol" value="${i?.porcentajePlomoPromexbol}"/>
<g:hiddenField name="porcentajePlataPromexbol" value="${i?.porcentajePlataPromexbol}"/>
<g:hiddenField name="porcentajeHumedadPromexbol" value="${i?.porcentajeHumedadPromexbol}"/>
<g:hiddenField name="porcentajeMermaPromexbol" value="${i?.porcentajeMermaPromexbol}"/>
<g:hiddenField name="tipoDeCambioComercial" value="${i?.tipoDeCambioComercial}"/>

<%-- ── Datos del lote ───────────────────────────────────────────────────── --%>
<h5 class="form-section-title">Datos del Lote</h5>
<dl class="row mb-0">
    <dt class="col-sm-2">Cliente</dt><dd class="col-sm-4">${i?.nombreCliente}</dd>
    <dt class="col-sm-2">Empresa</dt><dd class="col-sm-4">${i?.nombreEmpresa}</dd>
    <dt class="col-sm-2">Lote</dt><dd class="col-sm-4">${i?.lote}</dd>
    <dt class="col-sm-2">Fecha Rec.</dt><dd class="col-sm-4">${i?.fechaDeRecepcion}</dd>
</dl>

<%-- ── Características del mineral ────────────────────────────────────────── --%>
<h5 class="form-section-title">Características del Mineral</h5>
<div class="form-group row">
    <label class="col-sm-2 col-form-label">Peso Bruto Húmedo [Kg]</label>
    <div class="col-sm-2"><input type="text" id="pesoBruto" name="pesoBruto" class="form-control amarillo" readonly value="${i?.pesoBruto}"/></div>
    <label class="col-sm-2 col-form-label">Humedad [%]</label>
    <div class="col-sm-2"><input type="number" step="any" id="humedad" name="porcentajeHumedadFinal" class="form-control" value="${i?.porcentajeHumedadFinal}"/></div>
    <label class="col-sm-2 col-form-label">Merma [%]</label>
    <div class="col-sm-2"><input type="number" step="any" id="merma" name="porcentajeMermaFinal" class="form-control" value="${i?.porcentajeMermaFinal ?: 1}"/></div>
</div>
<div class="form-group row">
    <label class="col-sm-2 col-form-label font-weight-bold text-info">Peso Neto Seco [Kg]</label>
    <div class="col-sm-3"><input type="text" id="out_kilosNetosSecos" class="form-control text-right font-weight-bold" readonly
        style="background:#e3f2fd; border:2px solid #17a2b8; color:#0c5460; font-size:1.1rem;"/></div>
    <label class="col-sm-2 col-form-label">Cantidad de Sacos</label>
    <div class="col-sm-2"><input type="number" id="cantidadSacos" name="cantidadSacos" class="form-control amarillo text-right" readonly value="${i?.cantidadSacos ?: 0}"/></div>
</div>

<div class="table-responsive">
    <table class="table table-sm table-bordered mb-2">
        <thead class="thead-light"><tr>
            <th>Mineral</th><th class="text-right">Ley registrada [%]</th><th class="text-right">Ley cliente [%]</th>
            <th class="text-right">Ley final [%] <span class="text-danger">*</span></th><th class="text-right">Dif. vs registrada</th><th class="text-right">Peso fino [Kg]</th><th class="text-right">Peso fino [lf/ot]</th>
        </tr></thead>
        <tbody>
            <tr>
                <td>ZINC</td>
                <td class="text-right">${i?.porcentajeZincPromexbol}</td>
                <td><input type="number" step="any" id="leyClienteZinc" name="porcentajeZincCliente" class="form-control form-control-sm text-right ley-cliente" data-final="leyZinc" data-base="${i?.porcentajeZincPromexbol ?: 0}" value="${i?.porcentajeZincCliente}"/></td>
                <td><input type="number" step="any" id="leyZinc" name="porcentajeZincFinal" class="form-control form-control-sm text-right ley-final" data-base="${i?.porcentajeZincPromexbol ?: 0}" value="${i?.porcentajeZincFinal}"/></td>
                <td class="text-right"><span class="ley-diff" data-for="leyZinc"></span></td>
                <td class="text-right" id="out_finosZinc"></td>
                <td class="text-right"><span id="out_librasFinasZinc"></span> <small class="text-muted">lf</small></td>
            </tr>
            <tr>
                <td>PLOMO</td>
                <td class="text-right">${i?.porcentajePlomoPromexbol}</td>
                <td><input type="number" step="any" id="leyClientePlomo" name="porcentajePlomoCliente" class="form-control form-control-sm text-right ley-cliente" data-final="leyPlomo" data-base="${i?.porcentajePlomoPromexbol ?: 0}" value="${i?.porcentajePlomoCliente}"/></td>
                <td><input type="number" step="any" id="leyPlomo" name="porcentajePlomoFinal" class="form-control form-control-sm text-right ley-final" data-base="${i?.porcentajePlomoPromexbol ?: 0}" value="${i?.porcentajePlomoFinal}"/></td>
                <td class="text-right"><span class="ley-diff" data-for="leyPlomo"></span></td>
                <td class="text-right" id="out_finosPlomo"></td>
                <td class="text-right"><span id="out_librasFinasPlomo"></span> <small class="text-muted">lf</small></td>
            </tr>
            <tr>
                <td>PLATA</td>
                <td class="text-right">${i?.porcentajePlataPromexbol}</td>
                <td><input type="number" step="any" id="leyClientePlata" name="porcentajePlataCliente" class="form-control form-control-sm text-right ley-cliente" data-final="leyPlata" data-base="${i?.porcentajePlataPromexbol ?: 0}" value="${i?.porcentajePlataCliente}"/></td>
                <td><input type="number" step="any" id="leyPlata" name="porcentajePlataFinal" class="form-control form-control-sm text-right ley-final" data-base="${i?.porcentajePlataPromexbol ?: 0}" value="${i?.porcentajePlataFinal}"/></td>
                <td class="text-right"><span class="ley-diff" data-for="leyPlata"></span></td>
                <td class="text-right" id="out_finosPlata"></td>
                <td class="text-right"><span id="out_onzasTroyPlata"></span> <small class="text-muted">ot</small></td>
            </tr>
        </tbody>
    </table>
</div>

<%-- ── Cotizaciones (capturadas en el lote) ─────────────────────────────── --%>
<h5 class="form-section-title">Cotizaciones</h5>
<div class="table-responsive">
    <table class="table table-sm table-bordered mb-2" style="max-width:760px">
        <thead class="thead-light"><tr><th>Mineral</th><th class="text-right">Cot. Quincenal</th><th class="text-right">Alícuota [%]</th><th class="text-right">Cot. Diaria</th></tr></thead>
        <tbody>
            <tr><td>ZINC</td>
                <td><input type="text" id="cotQuincenalZinc" name="cotizacionQuincenalDeZinc" class="form-control form-control-sm amarillo text-right" readonly value="${i?.cotizacionQuincenalDeZinc}"/></td>
                <td><input type="text" id="alicuotaZinc" name="alicuotaDeZinc" class="form-control form-control-sm amarillo text-right" readonly value="${i?.alicuotaDeZinc}"/></td>
                <td><input type="text" id="cotDiariaZinc" name="cotizacionDiariaDeZinc" class="form-control form-control-sm amarillo text-right" readonly value="${i?.cotizacionDiariaDeZinc}"/></td>
            </tr>
            <tr><td>PLOMO</td>
                <td><input type="text" id="cotQuincenalPlomo" name="cotizacionQuincenalDePlomo" class="form-control form-control-sm amarillo text-right" readonly value="${i?.cotizacionQuincenalDePlomo}"/></td>
                <td><input type="text" id="alicuotaPlomo" name="alicuotaDePlomo" class="form-control form-control-sm amarillo text-right" readonly value="${i?.alicuotaDePlomo}"/></td>
                <td><input type="text" id="cotDiariaPlomo" name="cotizacionDiariaDePlomo" class="form-control form-control-sm amarillo text-right" readonly value="${i?.cotizacionDiariaDePlomo}"/></td>
            </tr>
            <tr><td>PLATA</td>
                <td><input type="text" id="cotQuincenalPlata" name="cotizacionQuincenalDePlata" class="form-control form-control-sm amarillo text-right" readonly value="${i?.cotizacionQuincenalDePlata}"/></td>
                <td><input type="text" id="alicuotaPlata" name="alicuotaDePlata" class="form-control form-control-sm amarillo text-right" readonly value="${i?.alicuotaDePlata}"/></td>
                <td><input type="text" id="cotDiariaPlata" name="cotizacionDiariaDePlata" class="form-control form-control-sm amarillo text-right" readonly value="${i?.cotizacionDiariaDePlata}"/></td>
            </tr>
        </tbody>
    </table>
</div>
<div class="form-group row">
    <label class="col-sm-2 col-form-label">Tipo de Cambio [Bs/$us]</label>
    <div class="col-sm-2"><input type="text" id="tipoCambio" name="tipoDeCambioOficial" class="form-control amarillo text-right" readonly value="${i?.tipoDeCambioOficial}"/></div>
</div>

<%-- ── VBV y Regalía Minera ─────────────────────────────────────────────── --%>
<h5 class="form-section-title">Valor Bruto de Venta y Regalía Minera</h5>
<div class="table-responsive">
    <table class="table table-sm table-bordered mb-2">
        <thead class="thead-light"><tr><th>Mineral</th><th class="text-right">VBV [$us]</th><th class="text-right">VBV [Bs]</th><th class="text-right">RM [$us]</th><th class="text-right">RM [Bs]</th></tr></thead>
        <tbody>
            <tr><td>ZINC</td><td class="text-right" id="out_vbvZincDolares"></td><td class="text-right" id="out_vbvZincBolivianos"></td><td class="text-right" id="out_rmZincDolares"></td><td class="text-right" id="out_rmZincBolivianos"></td></tr>
            <tr><td>PLOMO</td><td class="text-right" id="out_vbvPlomoDolares"></td><td class="text-right" id="out_vbvPlomoBolivianos"></td><td class="text-right" id="out_rmPlomoDolares"></td><td class="text-right" id="out_rmPlomoBolivianos"></td></tr>
            <tr><td>PLATA</td><td class="text-right" id="out_vbvPlataDolares"></td><td class="text-right" id="out_vbvPlataBolivianos"></td><td class="text-right" id="out_rmPlataDolares"></td><td class="text-right" id="out_rmPlataBolivianos"></td></tr>
        </tbody>
        <tfoot class="font-weight-bold"><tr><td>TOTAL</td><td class="text-right" id="out_totalVbvDolares"></td><td class="text-right" id="out_totalVbvBolivianos"></td><td class="text-right" id="out_totalRmDolares"></td><td class="text-right" id="out_totalRmBolivianos"></td></tr></tfoot>
    </table>
</div>

<%-- ── Valor Neto de Venta (VPT) ────────────────────────────────────────── --%>
<h5 class="form-section-title">Valor Neto de Venta</h5>
<div class="form-group row">
    <label class="col-sm-2 col-form-label">Modo VPT</label>
    <div class="col-sm-3">
        <g:select id="modoVPT" name="modoValoracion" class="form-control"
            from="${['MANUAL','TABLA','TERMINOS DE CONTRATO']}" value="${i?.modoValoracion ?: 'MANUAL'}"/>
    </div>
    <div class="col-sm-4" id="rowTabla" style="display:none">
        <g:select id="tablaComplejoId" name="tablaComplejo.id" class="form-control" noSelection="['':'(ninguna)']"
            from="${TablaOrigenCotizacionesComplejo.list()}" optionKey="id" value="${i?.tablaComplejo?.id}"/>
    </div>
    <div class="col-sm-4" id="rowTermino" style="display:none">
        <g:select id="terminosDeContratoId" name="terminosDeContrato.id" class="form-control" noSelection="['':'(ninguno)']"
            from="${TerminosDeContrato.list()}" optionKey="id" value="${i?.terminosDeContrato?.id}"/>
    </div>
</div>
<div class="form-group row">
    <label class="col-sm-2 col-form-label font-weight-bold text-success">Valor por Tonelada [$us/TM]</label>
    <div class="col-sm-3"><input type="number" step="any" id="vpt" name="valorPorTonelada" class="form-control text-right font-weight-bold"
        style="background:#e8f5e9; border:2px solid #43a047; color:#1b5e20; font-size:1.1rem;" value="${i?.valorPorTonelada}"/></div>
</div>
<div class="form-group row">
    <label class="col-sm-2 col-form-label">VNV [$us]</label>
    <div class="col-sm-2"><input type="text" id="out_vnvDolares" class="form-control amarillo text-right" readonly/></div>
    <label class="col-sm-2 col-form-label">VNV [Bs]</label>
    <div class="col-sm-2"><input type="text" id="out_vnvBolivianos" class="form-control amarillo text-right" readonly/></div>
</div>

<%-- ── Deducciones (retenciones) ────────────────────────────────────────── --%>
<h5 class="form-section-title">Deducciones</h5>
<div class="table-responsive">
    <table class="table table-sm table-bordered mb-2" id="tablaRetenciones">
        <thead class="thead-light"><tr><th>Descripción</th><th>Tipo</th><th>Asignación</th><th class="text-right">Cantidad</th><th class="text-right">Monto [Bs]</th><th style="width:40px"></th></tr></thead>
        <tbody>
            <tr><td colspan="4" class="text-right font-weight-bold">Regalía Minera (calculada)</td><td class="text-right font-weight-bold" id="out_regaliaDeducciones"></td><td></td></tr>
            <g:each in="${retencionesEmpresa}" var="r">
                <tr class="retencion-row" data-asignacion="${r.asignacionDelDescuento}" data-unidad="${r.unidadDeDescuento}">
                    <td>${r.descripcion}<g:hiddenField name="retDescripcion" value="${r.descripcion}"/></td>
                    <td>${r.tipoDeRetencion}<g:hiddenField name="retTipo" value="${r.tipoDeRetencion}"/></td>
                    <td>${r.asignacionDelDescuento}<g:hiddenField name="retAsignacion" value="${r.asignacionDelDescuento}"/><g:hiddenField name="retUnidad" value="${r.unidadDeDescuento}"/></td>
                    <td>
                        <div class="input-group input-group-sm">
                            <input type="number" step="any" name="retCantidad" class="form-control text-right retencion-cantidad" value="${r.cantidadDescuento}"/>
                            <div class="input-group-append"><span class="input-group-text">${r.unidadDeDescuento}</span></div>
                        </div>
                    </td>
                    <td class="text-right retencion-monto"></td>
                    <td class="text-center"><button type="button" class="btn btn-outline-danger btn-sm btn-quitar-retencion"><i class="fas fa-times"></i></button></td>
                </tr>
            </g:each>
        </tbody>
        <tfoot class="font-weight-bold"><tr><td colspan="4" class="text-right">Total Deducciones</td><td class="text-right" id="out_totalDeducciones"></td><td></td></tr></tfoot>
    </table>
</div>
<div class="form-group row">
    <label class="col-sm-3 col-form-label">Valor Pagable del Mineral [Bs]</label>
    <div class="col-sm-3"><input type="text" id="out_valorPagable" class="form-control amarillo text-right" readonly/></div>
</div>

<%-- ── Bonos (manuales) ─────────────────────────────────────────────────── --%>
<h5 class="form-section-title">Bonos</h5>
<div class="form-group row">
    <label class="col-sm-2 col-form-label">Bono Calidad [Bs]</label>
    <div class="col-sm-2"><input type="number" step="any" id="bonoCalidad" name="bonoCalidad" class="form-control text-right" value="${i?.bonoCalidad ?: 0}"/></div>
    <label class="col-sm-2 col-form-label">Bono Transporte [Bs]</label>
    <div class="col-sm-2"><input type="number" step="any" id="bonoTransporte" name="bonoTransporte" class="form-control text-right" value="${i?.bonoTransporte ?: 0}"/></div>
    <label class="col-sm-2 col-form-label">Bono Lealtad [Bs]</label>
    <div class="col-sm-2"><input type="number" step="any" id="bonoLealtad" name="bonoLealtad" class="form-control text-right" value="${i?.bonoLealtad ?: 0}"/></div>
</div>
<div class="form-group row">
    <label class="col-sm-2 col-form-label">Total Bonos [Bs]</label>
    <div class="col-sm-2"><input type="text" id="out_totalBonos" class="form-control amarillo text-right" readonly/></div>
</div>

<%-- ── Anticipos y otros saldos ─────────────────────────────────────────── --%>
<h5 class="form-section-title">Anticipos y Otros Saldos</h5>
<g:if test="${anticipoLote}">
    <div class="alert ${anticipoLote.unico ? 'alert-info' : 'alert-warning'} py-2">
        <i class="fas ${anticipoLote.unico ? 'fa-info-circle' : 'fa-exclamation-triangle'} mr-1"></i>El lote tiene un <strong>anticipo</strong> con saldo por pagar de <strong>Bs ${anticipoLote.totalPorPagar}</strong> (${anticipoLote.lotes} lote${anticipoLote.lotes == 1 ? '' : 's'}).
        <g:if test="${anticipoLote.unico}">Se descuenta completo (lote único).</g:if>
        <g:else><strong>El anticipo cubre varios lotes:</strong> el descuento quedó en 0 — ingrese la <strong>fracción</strong> a descontar en esta liquidación (no puede superar el saldo por pagar).</g:else>
    </div>
</g:if>
<div class="form-group row">
    <label class="col-sm-2 col-form-label">Anticipo contra entrega [Bs]</label>
    <div class="col-sm-2"><input type="number" step="any" id="anticipoContraEntrega" name="totalAnticiposContraEntrega" class="form-control text-right" value="${i?.totalAnticiposContraEntrega ?: 0}"/></div>
    <label class="col-sm-2 col-form-label">Anticipo c/futura entrega [Bs]</label>
    <div class="col-sm-2"><input type="number" step="any" id="anticipoContraFuturaEntrega" name="totalAnticiposContraFuturaEntrega" class="form-control text-right" value="${i?.totalAnticiposContraFuturaEntrega ?: 0}"/></div>
    <label class="col-sm-2 col-form-label">Saldo anterior [Bs]</label>
    <div class="col-sm-2"><input type="number" step="any" id="saldoAnterior" name="saldoAnterior" class="form-control text-right" value="${i?.saldoAnterior ?: 0}"/></div>
</div>
<div class="form-group row">
    <label class="col-sm-2 col-form-label">Total Anticipos [Bs]</label>
    <div class="col-sm-2"><input type="text" id="out_totalAnticipos" class="form-control amarillo text-right" readonly/></div>
</div>

<%-- ── Líquido pagable ──────────────────────────────────────────────────── --%>
<h5 class="form-section-title">Líquido Pagable</h5>
<div id="liquidoBox" class="liquido-box liquido-positivo">
    <span>LÍQUIDO PAGABLE [Bs]</span>
    <span class="liquido-valor" id="out_liquidoPagable">0</span>
</div>
<input type="hidden" id="out_precioCalculado"/>
