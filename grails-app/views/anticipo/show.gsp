<%@ page import="org.socymet.anticipos.Anticipo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Anticipo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
    <asset:javascript src="anticipo/anticipoUtilidades.js"/>
    <style>
        .form-section-title {
            font-size: 0.78rem;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 0.07em;
            color: #2c3e50;
            border-left: 4px solid #17a2b8;
            background: linear-gradient(to right, #e5f6f8, transparent);
            padding: 0.45rem 0.85rem;
            margin: 1.5rem 0 1rem;
            border-radius: 0 3px 3px 0;
        }
    </style>
</head>
<body>
<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Anticipo</h3>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1">
            <i class="fas fa-list mr-1"></i>Lista
        </g:link>
        <g:link action="create" class="btn btn-primary btn-sm">
            <i class="fas fa-plus mr-1"></i>Nuevo
        </g:link>
    </div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div id="swalFlashMsg" style="display:none">${flash.message}</div>
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    Swal.fire({ icon: '${flash.swalIcon ?: 'info'}', title: '${flash.swalTitle ?: 'Información'}',
                        text: document.getElementById('swalFlashMsg').textContent.trim(),
                        confirmButtonText: 'Aceptar' });
                });
            </script>
        </g:if>

        <%-- Aviso: todos los lotes liquidados y saldo no cobrado trasladado a un ACFE --%>
        <g:if test="${trasladadoACFE}">
            <div class="alert alert-info">
                <i class="fas fa-info-circle mr-1"></i>Todos los lotes de este anticipo han sido <strong>liquidados</strong>.
                El saldo no cobrado de <strong>Bs <g:formatNumber number="${residualTrasladado}" type="number" maxFractionDigits="2"/></strong>
                se registró como <strong>Anticipo contra Futura Entrega (ACFE)</strong>, quedando como deuda del cliente en su estado de cuenta.
            </div>
        </g:if>

        <%-- ── Proveedor ─────────────────────────────────────────────────── --%>
        <h5 class="form-section-title">Proveedor</h5>
        <dl class="row mb-0">
            <g:if test="${anticipoInstance?.cliente}">
                <dt class="col-sm-3">Cliente</dt>
                <dd class="col-sm-9">
                    <g:link controller="cliente" action="show" id="${anticipoInstance?.cliente?.id}">${anticipoInstance?.cliente?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
            <g:if test="${anticipoInstance?.empresa}">
                <dt class="col-sm-3">Empresa</dt>
                <dd class="col-sm-9">
                    <g:link controller="empresa" action="show" id="${anticipoInstance?.empresa?.id}">${anticipoInstance?.empresa?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
            <dt class="col-sm-3">Estado</dt>
            <dd class="col-sm-9">
                <g:if test="${anticipoInstance.totalPorPagar > 0}">
                    <span class="badge badge-danger">POR COBRAR</span>
                </g:if>
                <g:else>
                    <span class="badge badge-success">COBRADO</span>
                </g:else>
            </dd>
        </dl>

        <%-- ── Lotes asignados ───────────────────────────────────────────── --%>
        <h5 class="form-section-title">Lotes Asignados</h5>
        <g:hiddenField name="lotes" value="${anticipoInstance?.lotes}"/>
        <%-- Cobrado por lote (recepcionId → monto). Se pasa por un atributo data-* (escape HTML correcto,
             el navegador lo decodifica); anticipoUtilidades.js lo parsea para la columna "Cobrado". --%>
        <span id="cobradoPorLoteData" data-json="${cobradoPorLote.encodeAsJSON()}" hidden></span>
        <div class="table-responsive">
            <table id="lotesAsignados" class="table table-sm table-hover table-striped table-bordered mb-0" data-editable="false">
                <thead class="thead-light">
                    <tr>
                        <th>Lote</th>
                        <th>Fecha Rec.</th>
                        <th>Tipo Material</th>
                        <th class="text-right">Peso Bruto [Kg]</th>
                        <th class="text-right">Cobrado [Bs]</th>
                        <th style="width:48px"></th>
                    </tr>
                </thead>
                <tbody id="lotesAsignadosBody"></tbody>
                <tfoot class="font-weight-bold">
                    <tr>
                        <td class="text-right" colspan="4">Total Cobrado</td>
                        <td class="text-right">Bs <g:formatNumber number="${totalCobrado ?: 0}" type="number" maxFractionDigits="2"/></td>
                        <td></td>
                    </tr>
                </tfoot>
            </table>
        </div>

        <%-- ── Anticipos emitidos (cuotas) ───────────────────────────────── --%>
        <h5 class="form-section-title">Anticipos Emitidos</h5>
        <div class="table-responsive">
            <table class="table table-sm table-hover table-striped table-bordered mb-2">
                <thead class="thead-light">
                    <tr>
                        <th>Comprobante</th>
                        <th class="text-right">Importe [Bs]</th>
                        <th>Fecha</th>
                        <sec:ifAnyGranted roles="ROLE_ADMIN"><th style="width:48px"></th></sec:ifAnyGranted>
                    </tr>
                </thead>
                <tbody>
                    <g:if test="${!anticipoInstance.cuotas}">
                        <tr><td colspan="4" class="text-center text-muted py-3">No hay anticipos emitidos.</td></tr>
                    </g:if>
                    <g:each in="${anticipoInstance.cuotas.sort { it.fecha }}" var="cuota">
                        <tr>
                            <td>${cuota.numeroComprobante}/${cuota.gestionMinera ? new java.text.SimpleDateFormat('yy').format(cuota.gestionMinera) : '?'}</td>
                            <td class="text-right">Bs <g:formatNumber number="${cuota.monto}" type="number" maxFractionDigits="2"/></td>
                            <td><g:formatDate date="${cuota.fecha}" format="dd/MM/yyyy"/></td>
                            <sec:ifAnyGranted roles="ROLE_ADMIN">
                                <td class="text-center">
                                    <g:form action="eliminarCuota" class="d-inline">
                                        <g:hiddenField name="id" value="${cuota.id}"/>
                                        <button type="button" class="btn btn-outline-danger btn-sm btn-anular-cuota"
                                            data-comprobante="${cuota.numeroComprobante}/${cuota.gestionMinera ? new java.text.SimpleDateFormat('yy').format(cuota.gestionMinera) : '?'}" title="Anular">
                                            <i class="fas fa-times"></i>
                                        </button>
                                    </g:form>
                                </td>
                            </sec:ifAnyGranted>
                        </tr>
                    </g:each>
                </tbody>
                <tfoot>
                    <tr class="font-weight-bold">
                        <td class="text-right">Total Anticipos</td>
                        <td class="text-right">Bs <g:formatNumber number="${anticipoInstance.totalAnticipos}" type="number" maxFractionDigits="2"/></td>
                        <td></td>
                        <sec:ifAnyGranted roles="ROLE_ADMIN"><td></td></sec:ifAnyGranted>
                    </tr>
                </tfoot>
            </table>
        </div>

        <dl class="row mb-0">
            <g:if test="${anticipoInstance?.literalTotalAnticipos}">
                <dt class="col-sm-3">Literal</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${anticipoInstance}" field="literalTotalAnticipos"/></dd>
            </g:if>
            <dt class="col-sm-3">Total Pagado</dt>
            <dd class="col-sm-9">Bs <g:formatNumber number="${anticipoInstance.totalPagado ?: 0}" type="number" maxFractionDigits="2"/></dd>
            <dt class="col-sm-3">Total Por Pagar</dt>
            <dd class="col-sm-9">Bs <g:formatNumber number="${anticipoInstance.totalPorPagar ?: 0}" type="number" maxFractionDigits="2"/></dd>
        </dl>

        <%-- ── Emitir nuevo anticipo ─────────────────────────────────────── --%>
        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_RECEPCION,ROLE_CAJA">
            <div class="card card-outline card-success mt-3">
                <div class="card-header py-2"><h3 class="card-title mb-0" style="font-size:0.85rem; font-weight:600;">Emitir nuevo anticipo</h3></div>
                <div class="card-body">
                    <g:form action="agregarCuota">
                        <g:hiddenField name="id" value="${anticipoInstance?.id}"/>
                        <div class="form-row align-items-end">
                            <div class="form-group col-sm-5 mb-2">
                                <label class="mb-1">Importe [Bs] <span class="text-danger">*</span></label>
                                <input type="number" step="any" min="0" name="monto" class="form-control form-control-sm" required/>
                            </div>
                            <div class="form-group col-sm-4 mb-2">
                                <label class="mb-1">Fecha</label>
                                <input type="text" id="nuevaCuotaFecha" name="fecha" class="form-control form-control-sm" autocomplete="off"
                                       value="${new java.text.SimpleDateFormat('dd/MM/yyyy').format(new Date())}"/>
                            </div>
                            <div class="form-group col-sm-3 mb-2">
                                <button type="submit" class="btn btn-success btn-sm btn-block">
                                    <i class="fas fa-plus mr-1"></i>Emitir
                                </button>
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </sec:ifAnyGranted>

    </div>

    <div class="card-footer">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:set var="motivos" value="${anticipoInstance?.motivosBloqueo()}"/>
            <g:if test="${!motivos}">
                <g:form action="delete" class="d-inline">
                    <g:hiddenField name="id" value="${anticipoInstance?.id}"/>
                    <button type="button" class="btn btn-danger btn-sm btn-eliminar-anticipo">
                        <i class="fas fa-trash mr-1"></i>Eliminar
                    </button>
                </g:form>
            </g:if>
            <g:else>
                <div class="alert alert-warning mb-0 py-2">
                    <i class="fas fa-lock mr-1"></i>Este anticipo no se puede editar ni eliminar porque
                    ${motivos.join('; ')}.
                </div>
            </g:else>
        </sec:ifAnyGranted>
    </div>
</div>

<script>
    $(function () {
        if ($.fn.datepicker) {
            // Mismo datepicker que CotizacionDiariaDeMinerales (g:datepickerUI)
            var opciones = {
                dateFormat: 'dd/mm/yy',
                changeMonth: true,
                changeYear: true,
                beforeShow: function (input, inst) {
                    inst.dpDiv.css('z-index', 9999);
                    setTimeout(function () {
                        var offset = $(input).offset();
                        inst.dpDiv.css({ top: (offset.top + $(input).outerHeight()) + 'px', left: offset.left + 'px' });
                    }, 1);
                }
            };
            if ($.datepicker && $.datepicker.regional && $.datepicker.regional['es']) {
                opciones = $.extend({}, $.datepicker.regional['es'], opciones);
            }
            $('#nuevaCuotaFecha').datepicker(opciones);
        }
        $('.btn-anular-cuota').on('click', function () {
            var form = this.form;
            var comp = $(this).data('comprobante');
            Swal.fire({
                title: '¿Anular este anticipo?',
                html: 'Se anulará el anticipo del comprobante <strong>' + comp + '</strong>.<br>Esta acción no se puede deshacer.',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#6c757d',
                confirmButtonText: 'Sí, anular',
                cancelButtonText: 'Cancelar',
                reverseButtons: true
            }).then(function (result) {
                if (result.isConfirmed) form.submit();
            });
        });
        $('.btn-eliminar-anticipo').on('click', function () {
            var form = this.form;
            Swal.fire({
                title: '¿Eliminar este anticipo?',
                html: 'Se eliminará el registro y los lotes relacionados quedarán liberados (SIN ANTICIPO).<br>Esta acción no se puede deshacer.',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#6c757d',
                confirmButtonText: 'Sí, eliminar',
                cancelButtonText: 'Cancelar',
                reverseButtons: true
            }).then(function (result) {
                if (result.isConfirmed) form.submit();
            });
        });
    });
</script>
</body>
</html>
