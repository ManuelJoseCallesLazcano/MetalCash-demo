<%@ page import="org.socymet.cancelacion.PagoTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Pago de Transporte</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
    <style>
        .form-section-title {
            font-size: 0.78rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.07em;
            color: #2c3e50; border-left: 4px solid #17a2b8; background: linear-gradient(to right, #e5f6f8, transparent);
            padding: 0.45rem 0.85rem; margin: 1.5rem 0 1rem; border-radius: 0 3px 3px 0;
        }
    </style>
</head>
<body>
<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Pago de Transporte
            <span class="badge badge-info ml-1">N° ${pagoTransporteInstance}</span>
            <g:if test="${pagoTransporteInstance?.anulado}">
                <span class="badge badge-danger ml-1">ANULADO</span>
            </g:if>
        </h3>
        <%-- Impresión oficial: genera comprobante_pago_transporte.jasper (PDF) directo vía
             jasperService. El id de la URL lleva el N° de comprobante (título de la pestaña); el
             id real va en el query param 'lid'. --%>
        <g:set var="nombreArchivo" value="${('PagoTransporte-' + pagoTransporteInstance).replaceAll(/[^0-9A-Za-z._-]/, '-')}"/>
        <g:link controller="pagoTransporte" action="imprimirPdf" id="${nombreArchivo}" params="[lid: pagoTransporteInstance?.id]" target="_blank"
                class="btn btn-success btn-sm mr-1 font-weight-bold">
            <i class="fas fa-print mr-1"></i>Imprimir Comprobante
        </g:link>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1"><i class="fas fa-list mr-1"></i>Lista</g:link>
        <g:link action="create" class="btn btn-primary btn-sm"><i class="fas fa-plus mr-1"></i>Nuevo</g:link>
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

        <h5 class="form-section-title">Cobrador</h5>
        <dl class="row mb-0">
            <dt class="col-sm-3">CI</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${pagoTransporteInstance}" field="ci"/></dd>
            <dt class="col-sm-3">Cobrador</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${pagoTransporteInstance}" field="nombreCobrador"/></dd>
            <dt class="col-sm-3">Fecha de Pago</dt>
            <dd class="col-sm-9"><g:formatDate date="${pagoTransporteInstance?.fechaDePago}" format="dd/MM/yyyy"/></dd>
        </dl>

        <h5 class="form-section-title">Automóvil</h5>
        <dl class="row mb-0">
            <g:if test="${pagoTransporteInstance?.automovil}">
                <dt class="col-sm-3">Automóvil</dt>
                <dd class="col-sm-9">
                    <g:link controller="automovil" action="show" id="${pagoTransporteInstance?.automovil?.id}">${pagoTransporteInstance?.automovil?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
%{--            <g:if test="${pagoTransporteInstance?.empresa}">--}%
%{--                <dt class="col-sm-3">Empresa</dt>--}%
%{--                <dd class="col-sm-9">--}%
%{--                    <g:link controller="empresa" action="show" id="${pagoTransporteInstance?.empresa?.id}">${pagoTransporteInstance?.empresa?.encodeAsHTML()}</g:link>--}%
%{--                </dd>--}%
%{--            </g:if>--}%
        </dl>

        <h5 class="form-section-title">Lotes Pagados</h5>
        <div class="table-responsive">
            <table class="table table-sm table-bordered">
                <thead class="thead-light">
                    <tr>
                        <th>LOTE</th>
                        <th>CHOFER</th>
                        <th>PLACA</th>
                        <th>FECHA REC.</th>
                        <th>TIPO MAT.</th>
                        <th class="text-right">P. BRUTO KG</th>
                        <th class="text-right">COSTO TRANS.</th>
                    </tr>
                </thead>
                <tbody>
                    <g:each in="${detalles}" var="d">
                        <tr>
                            <td>${d.lote?.encodeAsHTML()}</td>
                            <td>${d.nombreChofer?.encodeAsHTML()}</td>
                            <td>${d.placaAutomovil?.encodeAsHTML()}</td>
                            <td>${d.fechaDeRecepcion?.encodeAsHTML()}</td>
                            <td>${d.tipoDeMaterial?.encodeAsHTML()}</td>
                            <td class="text-right"><g:formatNumber number="${d.pesoBruto}" type="number" maxFractionDigits="2"/></td>
                            <td class="text-right"><g:formatNumber number="${d.costoDeTransporte}" type="number" maxFractionDigits="2"/></td>
                        </tr>
                    </g:each>
                    <g:if test="${!detalles}">
                        <tr><td colspan="7" class="text-center text-muted">Sin lotes registrados.</td></tr>
                    </g:if>
                </tbody>
                <g:if test="${detalles}">
                    <tfoot class="thead-light font-weight-bold">
                        <tr>
                            <td colspan="5" class="text-right">TOTALES</td>
                            <td class="text-right"><g:formatNumber number="${detalles.sum { it.pesoBruto ?: 0 } ?: 0}" type="number" maxFractionDigits="2"/></td>
                            <td class="text-right"><g:formatNumber number="${detalles.sum { it.costoDeTransporte ?: 0 } ?: 0}" type="number" maxFractionDigits="2"/></td>
                        </tr>
                    </tfoot>
                </g:if>
            </table>
        </div>

        <h5 class="form-section-title">Liquidación del Pago</h5>
        <dl class="row mb-0">
            <dt class="col-sm-3">Total [Bs]</dt>
            <dd class="col-sm-9"><g:formatNumber number="${pagoTransporteInstance?.total ?: 0}" type="number" maxFractionDigits="2"/></dd>

            <dt class="col-sm-3">Anticipo Aplicado [Bs]</dt>
            <dd class="col-sm-9"><g:formatNumber number="${pagoTransporteInstance?.totalAnticipos ?: 0}" type="number" maxFractionDigits="2"/></dd>

            <dt class="col-sm-3">Total Pagable [Bs]</dt>
            <dd class="col-sm-9 font-weight-bold"><g:formatNumber number="${pagoTransporteInstance?.totalPagable ?: 0}" type="number" maxFractionDigits="2"/></dd>

            <g:if test="${pagoTransporteInstance?.totalPagableLiteral}">
                <dt class="col-sm-3">Literal</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${pagoTransporteInstance}" field="totalPagableLiteral"/></dd>
            </g:if>

            <g:if test="${pagoTransporteInstance?.observaciones}">
                <dt class="col-sm-3">Observaciones</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${pagoTransporteInstance}" field="observaciones"/></dd>
            </g:if>
        </dl>
    </div>

    <div class="card-footer d-flex align-items-center">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:if test="${!pagoTransporteInstance?.anulado}">
                <g:form action="anular" class="d-inline">
                    <g:hiddenField name="id" value="${pagoTransporteInstance?.id}"/>
                    <button type="button" class="btn btn-danger btn-sm btn-anular">
                        <i class="fas fa-ban mr-1"></i>Anular
                    </button>
                </g:form>
            </g:if>
            <g:else>
                <span class="text-muted"><i class="fas fa-ban mr-1"></i>Pago anulado</span>
            </g:else>
        </sec:ifAnyGranted>
    </div>
</div>

<script>
    $(function () {
        $('.btn-anular').on('click', function () {
            var form = this.form;
            Swal.fire({
                title: '¿Anular este pago?',
                html: 'Se devolverá el anticipo consumido al disponible del automóvil y los lotes volverán a quedar pendientes.<br>El registro no se elimina, queda marcado como ANULADO.',
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
    });
</script>
</body>
</html>
