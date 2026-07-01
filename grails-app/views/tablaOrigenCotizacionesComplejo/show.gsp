<%@ page import="org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Tabla de Precios</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
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
        .print-only { display: none; }
        @media print {
            @page { size: letter portrait; margin: 9mm 10mm; }
            .main-sidebar, .main-header, .main-footer, .no-print { display: none !important; }
            .content-wrapper, body, html { margin: 0 !important; padding: 0 !important; background: #fff !important; }
            body { font-size: 11px !important; -webkit-print-color-adjust: exact; print-color-adjust: exact; }
            .card, .card-body { border: none !important; box-shadow: none !important; padding: 0 !important; margin: 0 !important; }
            .print-only { display: block !important; }

            /* Encabezado ejecutivo */
            .print-title { text-align: center; margin-bottom: 6px; }
            .print-title h2 { font-size: 18px; font-weight: 700; margin: 0; letter-spacing: 0.04em; color: #0f6674; }
            .print-title .sub { font-size: 13px; font-weight: 600; color: #333; }
            .print-title .meta { font-size: 9.5px; color: #777; }
            .print-title hr { margin: 5px 0; border-top: 1px solid #0f6674; }

            /* Secciones y datos compactos */
            .tp-section {
                font-size: 11px !important; letter-spacing: 0.05em; margin: 7px 0 3px !important;
                padding: 3px 7px !important; border-left-width: 4px !important; border-bottom: none !important;
                border-radius: 0 !important; box-shadow: none !important;
            }
            dl.row { margin-bottom: 3px !important; }
            dt, dd { font-size: 11px !important; line-height: 1.25 !important; margin-bottom: 2px !important; }
            .row > [class*="col-"] { padding-top: 0 !important; padding-bottom: 0 !important; }

            /* Tablas compactas */
            table { font-size: 10px !important; margin-bottom: 0 !important; }
            table td, table th { padding: 2px 6px !important; line-height: 1.2 !important; }
            .table-responsive { overflow: visible !important; }

            .tp-section, .row, table, tr, dl { page-break-inside: avoid; }
        }
    </style>
</head>
<body>
<g:set var="i" value="${tablaOrigenCotizacionesComplejoInstance}"/>
<g:set var="cot" value="${i?.cotizacionDiariaDeMinerales}"/>
<g:set var="cotTonZn" value="${((cot?.zinc ?: 0) as BigDecimal) * 2204.6223}"/>
<g:set var="cotTonPb" value="${((cot?.plomo ?: 0) as BigDecimal) * 2204.6223}"/>
<g:set var="cotTonAg" value="${((cot?.plata ?: 0) as BigDecimal) * 1000 * 1000 / 31.1035}"/>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Tabla de Precios <span class="badge badge-light ml-1">${i?.nombreTabla}</span></h3>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1 no-print"><i class="fas fa-list mr-1"></i>Lista</g:link>
        <g:link action="edit" id="${i?.id}" class="btn btn-warning btn-sm mr-1 no-print"><i class="fas fa-edit mr-1"></i>Editar</g:link>
        <button type="button" class="btn btn-info btn-sm no-print" onclick="window.print()"><i class="fas fa-print mr-1"></i>Imprimir</button>
    </div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div class="alert alert-info alert-dismissible no-print"><button type="button" class="close" data-dismiss="alert">&times;</button>${flash.message}</div>
        </g:if>

        <div class="print-only print-title">
            <h2>TABLA DE PRECIOS</h2>
            <div class="sub">${i?.nombreTabla}</div>
            <div class="meta">Cotización referencial: ${cot?.encodeAsHTML() ?: '—'} · Generado: <g:formatDate date="${new Date()}" format="dd/MM/yyyy HH:mm"/></div>
            <hr/>
        </div>

        <h5 class="tp-section tp-general">Datos Generales</h5>
        <dl class="row">
            <dt class="col-sm-3">Nombre</dt><dd class="col-sm-9">${i?.nombreTabla}</dd>
%{--            <dt class="col-sm-3">Empresa</dt><dd class="col-sm-9">${i?.empresa?.encodeAsHTML() ?: '—'}</dd>--}%
%{--            <dt class="col-sm-3">Naturaleza Mineral</dt><dd class="col-sm-9">${i?.naturalezaMineral}</dd>--}%
            <dt class="col-sm-3">Cotización Diaria (referencial)</dt><dd class="col-sm-9">${cot?.encodeAsHTML() ?: '—'}</dd>
            <dt class="col-sm-3">Fecha de Actualización</dt><dd class="col-sm-9"><g:formatDate date="${i?.fechaActualizacion}" format="dd/MM/yyyy HH:mm"/></dd>
        </dl>

        <h5 class="tp-section tp-general">Cotización en Tonelada [$us/TM]</h5>
        <dl class="row">
            <dt class="col-sm-3">Zinc</dt><dd class="col-sm-3"><g:formatNumber number="${cotTonZn}" type="number" maxFractionDigits="2"/></dd>
            <dt class="col-sm-3">Plomo</dt><dd class="col-sm-3"><g:formatNumber number="${cotTonPb}" type="number" maxFractionDigits="2"/></dd>
            <dt class="col-sm-3">Plata</dt><dd class="col-sm-9"><g:formatNumber number="${cotTonAg}" type="number" maxFractionDigits="2"/></dd>
        </dl>

        <div class="row">
            <div class="col-md-4">
                <h5 class="tp-section tp-zinc">Zinc</h5>
                <table class="table table-sm table-bordered">
                    <thead class="thead-light"><tr><th>LEY [%]</th><th>% PAGABLE</th><th class="text-right">VPT $us</th></tr></thead>
                    <tbody>
                        <g:each in="${i?.puntos?.findAll { it.elemento == 'ZINC' }?.sort { it.ley }}" var="pt">
                            <tr>
                                <td class="text-right">${pt.ley}</td>
                                <td class="text-right">${pt.porcentajePagable}</td>
                                <td class="text-right"><g:formatNumber number="${cotTonZn * pt.ley / 100 * pt.porcentajePagable / 100}" type="number" maxFractionDigits="2"/></td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
            <div class="col-md-4">
                <h5 class="tp-section tp-plomo">Plomo</h5>
                <table class="table table-sm table-bordered">
                    <thead class="thead-light"><tr><th>LEY [%]</th><th>% PAGABLE</th><th class="text-right">VPT $us</th></tr></thead>
                    <tbody>
                        <g:each in="${i?.puntos?.findAll { it.elemento == 'PLOMO' }?.sort { it.ley }}" var="pt">
                            <tr>
                                <td class="text-right">${pt.ley}</td>
                                <td class="text-right">${pt.porcentajePagable}</td>
                                <td class="text-right"><g:formatNumber number="${cotTonPb * pt.ley / 100 * pt.porcentajePagable / 100}" type="number" maxFractionDigits="2"/></td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
            <div class="col-md-4">
                <h5 class="tp-section tp-plata">Plata</h5>
                <table class="table table-sm table-bordered">
                    <thead class="thead-light"><tr><th>LEY [DM]</th><th>% PAGABLE</th><th class="text-right">VPT $us</th></tr></thead>
                    <tbody>
                        <g:each in="${i?.puntos?.findAll { it.elemento == 'PLATA' }?.sort { it.ley }}" var="pt">
                            <tr>
                                <td class="text-right">${pt.ley}</td>
                                <td class="text-right">${pt.porcentajePagable}</td>
                                <td class="text-right"><g:formatNumber number="${cotTonAg * pt.ley / 10000 * pt.porcentajePagable / 100}" type="number" maxFractionDigits="2"/></td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="card-footer no-print">
        <g:link action="edit" id="${i?.id}" class="btn btn-warning btn-sm"><i class="fas fa-edit mr-1"></i>Editar</g:link>
        <g:form action="delete" method="post" class="d-inline">
            <g:hiddenField name="id" value="${i?.id}"/>
            <button type="button" class="btn btn-danger btn-sm" onclick="confirmarEliminacion(this.form)"><i class="fas fa-trash mr-1"></i>Eliminar</button>
        </g:form>
        <script>
            function confirmarEliminacion(form) {
                Swal.fire({
                    title: '¿Eliminar esta tabla?',
                    html: 'Se eliminará <strong>${i?.nombreTabla}</strong> y sus puntos.<br>Esta acción no se puede deshacer.',
                    icon: 'warning', showCancelButton: true, confirmButtonColor: '#d33', cancelButtonColor: '#6c757d',
                    confirmButtonText: 'Sí, eliminar', cancelButtonText: 'Cancelar', reverseButtons: true
                }).then(function (result) { if (result.isConfirmed) form.submit(); });
            }
        </script>
    </div>
</div>
</body>
</html>
