<%@ page import="org.socymet.cotizaciones.TerminosDeContrato" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Término de Contrato</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
    <style>
        .tc-section {
            font-size: 0.84rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.08em;
            color: #0f6674; border-left: 5px solid #17a2b8; border-bottom: 1px solid #bfe6ec;
            background: linear-gradient(to right, #d6f0f4, #eef9fb 55%, transparent);
            padding: 0.5rem 0.9rem; margin: 1.3rem 0 0.9rem; border-radius: 0 4px 4px 0;
            box-shadow: 0 1px 2px rgba(0,0,0,.05);
        }
        .tc-label { font-weight: 600; color: #6c757d; font-size: 0.8rem; }
        .tc-value { font-size: 0.95rem; color: #343a40; }
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

            /* Secciones compactas */
            .tc-section {
                font-size: 11px !important; letter-spacing: 0.05em; margin: 7px 0 3px !important;
                padding: 3px 7px !important; border-left-width: 4px !important; border-bottom: none !important;
                border-radius: 0 !important; box-shadow: none !important;
            }
            .tc-label { font-size: 9px !important; margin-bottom: 0 !important; }
            .tc-value { font-size: 11px !important; line-height: 1.2 !important; }
            .row > [class*="col-"] { padding-top: 0 !important; padding-bottom: 0 !important; }
            .mb-2 { margin-bottom: 2px !important; }

            /* Tablas compactas */
            table { font-size: 10px !important; margin-bottom: 0 !important; }
            table td, table th { padding: 2px 6px !important; line-height: 1.2 !important; }
            .table-responsive { overflow: visible !important; }

            /* Evitar cortes feos */
            .tc-section, .row, table, tr { page-break-inside: avoid; }
        }
    </style>
</head>
<body>
<g:set var="i" value="${terminosDeContratoInstance}"/>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Término de Contrato <span class="badge badge-light ml-1">${i?.nombreContrato}</span></h3>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1 no-print"><i class="fas fa-list mr-1"></i>Lista</g:link>
        <g:link action="edit" id="${i?.id}" class="btn btn-warning btn-sm mr-1 no-print"><i class="fas fa-edit mr-1"></i>Editar</g:link>
        <button type="button" class="btn btn-info btn-sm no-print" onclick="window.print()"><i class="fas fa-print mr-1"></i>Imprimir</button>
    </div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div class="alert alert-info alert-dismissible no-print"><button type="button" class="close" data-dismiss="alert">&times;</button>${flash.message}</div>
        </g:if>

        <div class="print-only print-title">
            <h2>TÉRMINOS DE CONTRATO</h2>
            <div class="sub">${i?.nombreContrato} — ${i?.tipoDeMineral}</div>
            <div class="meta">Generado: <g:formatDate date="${new Date()}" format="dd/MM/yyyy HH:mm"/></div>
            <hr/>
        </div>

        <h5 class="tc-section">Datos del Contrato</h5>
        <div class="row">
            <div class="col-md-6"><div class="tc-label">Nombre del Contrato</div><div class="tc-value font-weight-bold">${i?.nombreContrato}</div></div>
            <div class="col-md-3"><div class="tc-label">Mineral</div><div class="tc-value">${i?.tipoDeMineral}</div></div>
        </div>

        <g:set var="esZn" value="${i?.tipoDeMineral == 'ZN-AG'}"/>
        <g:set var="grupos" value="${[
            ['Impurezas Referenciales [%]', [['porcentajeArsenico','Arsénico'],['porcentajeAntimonio','Antimonio'],['porcentajeBismuto','Bismuto'],['porcentajeEstano','Estaño'],['porcentajeHierro','Hierro'],['porcentajeSilice','Sílice'],['porcentajeZinc','Zinc']]],
            ['Deducciones Unitarias', [['deduccionUnitariaZinc','Zinc','zn','%'],['deduccionUnitariaPlomo','Plomo','pb','%'],['deduccionUnitariaPlata','Plata','both','ot']]],
            ['Cotización Pagable', [['porcentajePagableLMEZinc','Zinc','zn','%'],['porcentajePagableLMEPlomo','Plomo','pb','%'],['porcentajePagableLMEPlata','Plata','both','%']]],
            ['Maquila + Escalador', [['maquilaZincPlata','Maquila','zn','\$us'],['baseZincPlata','Base','zn','\$us'],['escaladorZincPlata','Escalador','zn'],['maquilaPlomoPlata','Maquila','pb','\$us'],['basePlomoPlata','Base','pb','\$us'],['escaladorPlomoPlata','Escalador','pb']]],
            ['Gastos de Refinación', [['deduccionRefinacionOnzaZincPlata','Gasto de Refinación','zn','\$us/ot'],['deduccionRefinacionOnzaPlomoPlata','Gasto de Refinación','pb','\$us/ot']]]
        ]}"/>
        <g:each in="${grupos}" var="g">
            <h5 class="tc-section">${g[0]}</h5>
            <div class="row">
                <g:each in="${g[1]}" var="f">
                    <g:if test="${!f[2] || f[2] == 'both' || (f[2] == 'zn' && esZn) || (f[2] == 'pb' && !esZn)}">
                        <div class="col-md-3 mb-2"><div class="tc-label">${f[1]}</div><div class="tc-value">${fieldValue(bean: i, field: f[0])}<g:if test="${g[0].startsWith('Impurezas')}"> %</g:if><g:if test="${f.size() > 3}"> ${f[3]}</g:if></div></div>
                    </g:if>
                </g:each>
            </div>
        </g:each>

        <h5 class="tc-section">Penalidades</h5>
        <div class="table-responsive">
            <table class="table table-sm table-bordered" style="max-width:680px">
                <thead class="thead-light"><tr><th>Elemento</th><th class="text-right">Límite</th><th class="text-right">Costo Unitario</th><th class="text-right">Porcentaje Unitario</th></tr></thead>
                <tbody>
                    <g:each in="${[['Arsénico','arsenicoLibre','costoUnitarioArsenico','porcentajeUnitarioArsenico'],['Antimonio','antimonioLibre','costoUnitarioAntimonio','porcentajeUnitarioAntimonio'],['Bismuto','bismutoLibre','costoUnitarioBismuto','porcentajeUnitarioBismuto'],['Estaño','estanoLibre','costoUnitarioEstano','porcentajeUnitarioEstano'],['Hierro','hierroLibre','costoUnitarioHierro','porcentajeUnitarioHierro'],['Sílice','siliceLibre','costoUnitarioSilice','porcentajeUnitarioSilice'],['Zinc','zincLibre','costoUnitarioZinc','porcentajeUnitarioZinc']]}" var="r">
                        <tr>
                            <td class="font-weight-bold">${r[0]}</td>
                            <td class="text-right">${fieldValue(bean: i, field: r[1])} %</td>
                            <td class="text-right">$us ${fieldValue(bean: i, field: r[2])}</td>
                            <td class="text-right">${fieldValue(bean: i, field: r[3])} %</td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
        </div>

        <h5 class="tc-section">Otras Deducciones</h5>
        <div class="row">
            <g:each in="${[['transporteInterno','Transporte Interno'],['laboratorio','Laboratorio'],['molienda','Molienda'],['manipuleo','Manipuleo'],['margenAdministrativo','Margen Administrativo'],['transporteAPuerto','Transporte a Puerto'],['rollBack','Roll Back']]}" var="f">
                <div class="col-md-3 mb-2"><div class="tc-label">${f[1]}</div><div class="tc-value">${fieldValue(bean: i, field: f[0])} $us/tn</div></div>
            </g:each>
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
                    title: '¿Eliminar este término de contrato?',
                    html: 'Se eliminará <strong>${i?.nombreContrato}</strong>.<br>Esta acción no se puede deshacer.',
                    icon: 'warning', showCancelButton: true, confirmButtonColor: '#d33', cancelButtonColor: '#6c757d',
                    confirmButtonText: 'Sí, eliminar', cancelButtonText: 'Cancelar', reverseButtons: true
                }).then(function (result) { if (result.isConfirmed) form.submit(); });
            }
        </script>
    </div>
</div>
</body>
</html>
