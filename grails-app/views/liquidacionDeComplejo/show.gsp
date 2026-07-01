<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidación de Complejo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
    <style>
        .form-section-title { font-size:0.78rem; font-weight:700; text-transform:uppercase; letter-spacing:0.07em; color:#2c3e50; border-left:4px solid #17a2b8; background:linear-gradient(to right,#e5f6f8,transparent); padding:0.45rem 0.85rem; margin:1.5rem 0 1rem; border-radius:0 3px 3px 0; }
    </style>
</head>
<body>
<g:set var="i" value="${liquidacionDeComplejoInstance}"/>
<g:set var="fmt2" value="${{ v -> g.formatNumber(number: v ?: 0, type: 'number', maxFractionDigits: 2) }}"/>
<g:set var="fmt3" value="${{ v -> g.formatNumber(number: v ?: 0, type: 'number', maxFractionDigits: 3) }}"/>

<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Liquidación de Complejo
            <g:if test="${i?.numeroLiquidacionComplejo}"><span class="badge badge-info ml-1">N° ${i.numeroLiquidacionComplejo}/<g:formatDate date="${i.gestionMinera}" format="yy"/></span></g:if>
            <g:if test="${i?.anulado}"><span class="badge badge-danger ml-1">ANULADA</span></g:if>
        </h3>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1"><i class="fas fa-list mr-1"></i>Lista</g:link>
        <g:link action="create" class="btn btn-primary btn-sm"><i class="fas fa-plus mr-1"></i>Nueva</g:link>
    </div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div id="swalFlashMsg" style="display:none">${flash.message}</div>
            <script>document.addEventListener('DOMContentLoaded', function () { Swal.fire({ icon: '${flash.swalIcon ?: 'info'}', title: '${flash.swalTitle ?: 'Información'}', text: document.getElementById('swalFlashMsg').textContent.trim(), confirmButtonText: 'Aceptar' }); });</script>
        </g:if>

        <h5 class="form-section-title">Datos del Lote</h5>
        <dl class="row mb-0">
            <dt class="col-sm-2">Cliente</dt><dd class="col-sm-4">${i?.nombreCliente}</dd>
            <dt class="col-sm-2">Empresa</dt><dd class="col-sm-4">${i?.nombreEmpresa}</dd>
            <dt class="col-sm-2">Lote</dt>
            <dd class="col-sm-4"><g:link controller="recepcionDeComplejo" action="show" id="${i?.recepcionDeComplejo?.id}">${i?.lote}</g:link></dd>
            <dt class="col-sm-2">Fecha Rec.</dt><dd class="col-sm-4">${i?.fechaDeRecepcion}</dd>
            <dt class="col-sm-2">Peso Bruto Húmedo [Kg]</dt><dd class="col-sm-4">${fmt2(i?.pesoBruto)}</dd>
            <dt class="col-sm-2">Peso Neto Seco [Kg]</dt><dd class="col-sm-4">${fmt2(i?.kilosNetosSecos)}</dd>
        </dl>

        <h5 class="form-section-title">Características del Mineral</h5>
        <div class="table-responsive">
            <table class="table table-sm table-bordered mb-2" style="max-width:760px">
                <thead class="thead-light"><tr><th>Concepto</th><th class="text-right">Registrada</th><th class="text-right">Cliente</th><th class="text-right">Final</th><th class="text-right">Dif.</th><th class="text-right">Peso fino [Kg]</th><th class="text-right">Peso fino [lf/ot]</th></tr></thead>
                <tbody>
                    <tr><td>Ley Zinc [%]</td><td class="text-right">${fmt3(i?.porcentajeZincPromexbol)}</td><td class="text-right">${fmt3(i?.porcentajeZincCliente)}</td><td class="text-right">${fmt3(i?.porcentajeZincFinal)}</td><td class="text-right">${fmt3((i?.porcentajeZincFinal ?: 0) - (i?.porcentajeZincPromexbol ?: 0))}</td><td class="text-right">${fmt3(i?.kilosFinosZinc)}</td><td class="text-right">${fmt3(i?.librasFinasDeZinc)} <small class="text-muted">lf</small></td></tr>
                    <tr><td>Ley Plomo [%]</td><td class="text-right">${fmt3(i?.porcentajePlomoPromexbol)}</td><td class="text-right">${fmt3(i?.porcentajePlomoCliente)}</td><td class="text-right">${fmt3(i?.porcentajePlomoFinal)}</td><td class="text-right">${fmt3((i?.porcentajePlomoFinal ?: 0) - (i?.porcentajePlomoPromexbol ?: 0))}</td><td class="text-right">${fmt3(i?.kilosFinosPlomo)}</td><td class="text-right">${fmt3(i?.librasFinasDePlomo)} <small class="text-muted">lf</small></td></tr>
                    <tr><td>Ley Plata [DM]</td><td class="text-right">${fmt3(i?.porcentajePlataPromexbol)}</td><td class="text-right">${fmt3(i?.porcentajePlataCliente)}</td><td class="text-right">${fmt3(i?.porcentajePlataFinal)}</td><td class="text-right">${fmt3((i?.porcentajePlataFinal ?: 0) - (i?.porcentajePlataPromexbol ?: 0))}</td><td class="text-right">${fmt3(i?.kilosFinosPlata)}</td><td class="text-right">${fmt3(i?.onzasTroyDePlata)} <small class="text-muted">ot</small></td></tr>
                    <tr><td>Humedad [%]</td><td class="text-right">${fmt3(i?.porcentajeHumedadPromexbol)}</td><td class="text-right">${fmt3(i?.porcentajeHumedadCliente)}</td><td class="text-right">${fmt3(i?.porcentajeHumedadFinal)}</td><td></td><td></td><td></td></tr>
                </tbody>
            </table>
        </div>

        <h5 class="form-section-title">Valor Bruto de Venta y Regalía Minera</h5>
        <div class="table-responsive">
            <table class="table table-sm table-bordered mb-2">
                <thead class="thead-light"><tr><th>Mineral</th><th class="text-right">VBV [$us]</th><th class="text-right">VBV [Bs]</th><th class="text-right">RM [$us]</th><th class="text-right">RM [Bs]</th></tr></thead>
                <tbody>
                    <tr><td>ZINC</td><td class="text-right">${fmt2(i?.valorOficialBrutoDeZinc)}</td><td class="text-right">${fmt2(i?.valorOficialBrutoDeZincEnBolivianos)}</td><td class="text-right">${fmt2(i?.regaliaMineraDeZinc)}</td><td class="text-right">${fmt2(i?.regaliaMineraDeZincEnBolivianos)}</td></tr>
                    <tr><td>PLOMO</td><td class="text-right">${fmt2(i?.valorOficialBrutoDePlomo)}</td><td class="text-right">${fmt2(i?.valorOficialBrutoDePlomoEnBolivianos)}</td><td class="text-right">${fmt2(i?.regaliaMineraDePlomo)}</td><td class="text-right">${fmt2(i?.regaliaMineraDePlomoEnBolivianos)}</td></tr>
                    <tr><td>PLATA</td><td class="text-right">${fmt2(i?.valorOficialBrutoDePlata)}</td><td class="text-right">${fmt2(i?.valorOficialBrutoDePlataEnBolivianos)}</td><td class="text-right">${fmt2(i?.regaliaMineraDePlata)}</td><td class="text-right">${fmt2(i?.regaliaMineraDePlataEnBolivianos)}</td></tr>
                </tbody>
                <tfoot class="font-weight-bold"><tr><td>TOTAL</td><td class="text-right">${fmt2(i?.valorOficialBruto)}</td><td class="text-right">${fmt2(i?.valorOficialBrutoEnBolivianos)}</td><td class="text-right">${fmt2(i?.totalRegaliaMineraDolares)}</td><td class="text-right">${fmt2(i?.regaliaMinera)}</td></tr></tfoot>
            </table>
        </div>

        <h5 class="form-section-title">Valor Neto de Venta</h5>
        <dl class="row mb-0">
            <dt class="col-sm-2">Modo VPT</dt><dd class="col-sm-4">${i?.modoValoracion}</dd>
            <dt class="col-sm-2">Valor por Tonelada [$us/TM]</dt><dd class="col-sm-4">${fmt2(i?.valorPorTonelada)}</dd>
            <dt class="col-sm-2">VNV [$us]</dt><dd class="col-sm-4">${fmt2(i?.valorNetoMineral)}</dd>
            <dt class="col-sm-2">VNV [Bs]</dt><dd class="col-sm-4">${fmt2(i?.valorNetoMineralEnBolivianos)}</dd>
        </dl>

        <h5 class="form-section-title">Deducciones</h5>
        <div class="table-responsive">
            <table class="table table-sm table-bordered mb-2">
                <thead class="thead-light"><tr><th>Descripción</th><th>Tipo</th><th>Asignación</th><th class="text-right">Cantidad</th><th class="text-right">Monto [Bs]</th></tr></thead>
                <tbody>
                    <tr><td colspan="4" class="text-right font-weight-bold">Regalía Minera</td><td class="text-right font-weight-bold">${fmt2(i?.regaliaMinera)}</td></tr>
                    <g:each in="${i?.detalleRetenciones}" var="r">
                        <tr><td>${r.descripcion}</td><td>${r.tipoDeRetencion}</td><td>${r.asignacionDelDescuento}</td><td class="text-right">${fmt2(r.cantidadDescuento)} ${r.unidadDeDescuento}</td><td class="text-right">${fmt2(r.monto)}</td></tr>
                    </g:each>
                </tbody>
                <tfoot class="font-weight-bold"><tr><td colspan="4" class="text-right">Total Deducciones</td><td class="text-right">${fmt2(i?.totalRetenciones)}</td></tr></tfoot>
            </table>
        </div>
        <dl class="row mb-0">
            <dt class="col-sm-3">Valor Pagable del Mineral [Bs]</dt><dd class="col-sm-3">${fmt2(i?.valorPagableMineral)}</dd>
        </dl>

        <h5 class="form-section-title">Bonos y Anticipos</h5>
        <dl class="row mb-0">
            <dt class="col-sm-3">Bono Calidad [Bs]</dt><dd class="col-sm-3">${fmt2(i?.bonoCalidad)}</dd>
            <dt class="col-sm-3">Bono Transporte [Bs]</dt><dd class="col-sm-3">${fmt2(i?.bonoTransporte)}</dd>
            <dt class="col-sm-3">Bono Lealtad [Bs]</dt><dd class="col-sm-3">${fmt2(i?.bonoLealtad)}</dd>
            <dt class="col-sm-3">Total Bonos [Bs]</dt><dd class="col-sm-3">${fmt2(i?.totalBonos)}</dd>
            <dt class="col-sm-3">Anticipo contra entrega [Bs]</dt><dd class="col-sm-3">${fmt2(i?.totalAnticiposContraEntrega)}</dd>
            <dt class="col-sm-3">Anticipo c/futura entrega [Bs]</dt><dd class="col-sm-3">${fmt2(i?.totalAnticiposContraFuturaEntrega)}</dd>
            <dt class="col-sm-3">Saldo anterior [Bs]</dt><dd class="col-sm-3">${fmt2(i?.saldoAnterior)}</dd>
            <dt class="col-sm-3">Total Anticipos [Bs]</dt><dd class="col-sm-3">${fmt2(i?.totalAnticipos)}</dd>
        </dl>

        <h5 class="form-section-title">Líquido Pagable</h5>
        <div class="alert ${(i?.totalLiquidoPagable ?: 0) < 0 ? 'alert-danger' : 'alert-success'} py-2 d-flex align-items-center">
            <strong class="mr-2">LÍQUIDO PAGABLE [Bs]:</strong>
            <span class="h5 mb-0 mr-auto">${fmt2(i?.totalLiquidoPagable)}</span>
        </div>

        <g:if test="${(i?.totalLiquidoPagable ?: 0) < 0}">
            <g:set var="acfe" value="${org.socymet.anticipos.AnticipoContraFuturaEntrega.findByLiquidacionId(i.id)}"/>
            <g:if test="${acfe}">
                <div class="alert alert-warning py-2">
                    <strong>Anticipo Contra Futura Entrega generado por saldo negativo:</strong>
                    <g:link controller="anticipoContraFuturaEntrega" action="show" id="${acfe.id}" target="_blank" class="alert-link ml-2">Ver ACFE</g:link>
                </div>
            </g:if>
        </g:if>

        <g:if test="${i?.observaciones && i.observaciones != '-'}">
            <dl class="row mb-0"><dt class="col-sm-3">Observaciones</dt><dd class="col-sm-9">${i?.observaciones}</dd></dl>
        </g:if>
    </div>

    <div class="card-footer d-flex align-items-center">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:if test="${!i?.anulado}">
                <g:form action="anular" class="d-inline">
                    <g:hiddenField name="id" value="${i?.id}"/>
                    <button type="button" class="btn btn-danger btn-sm btn-anular"><i class="fas fa-ban mr-1"></i>Anular</button>
                </g:form>
            </g:if>
            <g:else><span class="text-muted"><i class="fas fa-ban mr-1"></i>Liquidación anulada</span></g:else>
        </sec:ifAnyGranted>
        <g:link action="imprimir" id="${i?.id}" target="_blank" class="btn btn-outline-secondary btn-sm ml-1">
            <i class="fas fa-print mr-1"></i>Imprimir comprobante
        </g:link>
        <div class="ml-auto">
            <g:jasperReport controller="liquidacionDeComplejo" action="crearReporte" jasper="liquidacion_complejo" format="PDF,RTF" name="ReporteLiquidacion${i.lote}">
                <input type="hidden" name="LIQ_SN_ID" value="${i.id}"/>
            </g:jasperReport>
        </div>
    </div>
</div>

<script>
    $(function () {
        $('.btn-anular').on('click', function () {
            var form = this.form;
            Swal.fire({ title: '¿Anular esta liquidación?',
                html: 'Se revertirán sus efectos: el lote vuelve a NO LIQUIDADO, se revierte el descuento de anticipo y los asientos del estado de cuenta. El registro queda marcado como ANULADA.',
                icon: 'warning', showCancelButton: true, confirmButtonColor: '#d33',
                confirmButtonText: 'Sí, anular', cancelButtonText: 'Cancelar', reverseButtons: true
            }).then(function (r) { if (r.isConfirmed) form.submit(); });
        });
    });
</script>
</body>
</html>
