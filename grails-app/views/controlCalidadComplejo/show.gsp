<%@ page import="org.socymet.calidad.ControlCalidadComplejo" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Análisis de Laboratorio</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
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
        <h3 class="card-title mr-auto">Análisis de Laboratorio</h3>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1">
            <i class="fas fa-list mr-1"></i>Lista
        </g:link>
        <g:link action="create" class="btn btn-primary btn-sm">
            <i class="fas fa-plus mr-1"></i>Nuevo
        </g:link>
    </div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div class="alert alert-info alert-dismissible">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                ${flash.message}
            </div>
        </g:if>

        <g:if test="${controlCalidadComplejoInstance?.recepcionDeComplejo}">
            <div class="alert alert-success py-2">
                <strong>Lote:</strong>
                <g:link controller="recepcionDeComplejo" action="show" id="${controlCalidadComplejoInstance?.recepcionDeComplejo?.id}" class="h5 ml-2">
                    ${controlCalidadComplejoInstance?.recepcionDeComplejo?.encodeAsHTML()}
                </g:link>
            </div>
        </g:if>

        <h5 class="form-section-title">Información del Lote</h5>
        <dl class="row mb-0">
            <g:if test="${controlCalidadComplejoInstance?.nombreCliente}">
                <dt class="col-sm-3">Cliente</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="nombreCliente"/></dd>
            </g:if>
            <g:if test="${controlCalidadComplejoInstance?.nombreEmpresa}">
                <dt class="col-sm-3">Empresa</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="nombreEmpresa"/></dd>
            </g:if>
            <g:if test="${controlCalidadComplejoInstance?.fechaDeRecepcion}">
                <dt class="col-sm-3">Fecha de Recepción</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="fechaDeRecepcion"/></dd>
            </g:if>
            <g:if test="${controlCalidadComplejoInstance?.pesoBruto}">
                <dt class="col-sm-3">Peso Bruto Húmedo [Kg]</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="pesoBruto"/></dd>
            </g:if>
            <g:if test="${controlCalidadComplejoInstance?.estadoDelLote}">
                <dt class="col-sm-3">Estado del Lote</dt>
                <dd class="col-sm-9">
                    <g:if test="${controlCalidadComplejoInstance.recepcionDeComplejo?.estadoDelLote == 'LIQUIDADO'}">
                        <span class="badge badge-success">${controlCalidadComplejoInstance.recepcionDeComplejo.estadoDelLote}</span>
                    </g:if>
                    <g:else>
                        <span class="badge badge-danger">${controlCalidadComplejoInstance.recepcionDeComplejo.estadoDelLote}</span>
                    </g:else>
                </dd>
            </g:if>
        </dl>

        <h5 class="form-section-title">Datos del Análisis</h5>
        <dl class="row mb-0">
            <g:if test="${controlCalidadComplejoInstance?.nombreLaboratorio}">
                <dt class="col-sm-3">Laboratorio</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="nombreLaboratorio"/></dd>
            </g:if>
            <g:if test="${controlCalidadComplejoInstance?.numeroAnalisis}">
                <dt class="col-sm-3">Análisis N°</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="numeroAnalisis"/></dd>
            </g:if>
            <g:if test="${controlCalidadComplejoInstance?.fechaAnalisis}">
                <dt class="col-sm-3">Fecha</dt>
                <dd class="col-sm-9"><g:formatDate date="${controlCalidadComplejoInstance?.fechaAnalisis}" format="dd/MM/yyyy"/></dd>
            </g:if>
        </dl>

        <h5 class="form-section-title">Detalle de Leyes</h5>
        <dl class="row mb-0">
            <dt class="col-sm-3"><g:message code="controlCalidadComplejo.porcentajeZincPromexbol.label" default="Zinc"/></dt>
            <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="porcentajeZincPromexbol"/> <span class="text-muted">%</span></dd>

            <dt class="col-sm-3"><g:message code="controlCalidadComplejo.porcentajePlomoPromexbol.label" default="Plomo"/></dt>
            <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="porcentajePlomoPromexbol"/> <span class="text-muted">%</span></dd>

            <dt class="col-sm-3"><g:message code="controlCalidadComplejo.porcentajePlataPromexbol.label" default="Plata"/></dt>
            <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="porcentajePlataPromexbol"/> <span class="text-muted">DM</span></dd>

            <dt class="col-sm-3"><g:message code="controlCalidadComplejo.porcentajeHumedadPromexbol.label" default="Humedad"/></dt>
            <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="porcentajeHumedadPromexbol"/> <span class="text-muted">%</span></dd>
        </dl>

        <g:if test="${controlCalidadComplejoInstance?.porcentajeArsenico || controlCalidadComplejoInstance?.porcentajeAntimonio || controlCalidadComplejoInstance?.porcentajeSilice || controlCalidadComplejoInstance?.porcentajeBismuto || controlCalidadComplejoInstance?.porcentajeEstano || controlCalidadComplejoInstance?.porcentajeZinc}">
            <h5 class="form-section-title">Elementos Penalizables</h5>
            <dl class="row mb-0">
                <g:if test="${controlCalidadComplejoInstance?.porcentajeArsenico}">
                    <dt class="col-sm-3">Arsénico</dt>
                    <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="porcentajeArsenico"/></dd>
                </g:if>
                <g:if test="${controlCalidadComplejoInstance?.porcentajeAntimonio}">
                    <dt class="col-sm-3">Antimonio</dt>
                    <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="porcentajeAntimonio"/></dd>
                </g:if>
                <g:if test="${controlCalidadComplejoInstance?.porcentajeSilice}">
                    <dt class="col-sm-3">Sílice</dt>
                    <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="porcentajeSilice"/></dd>
                </g:if>
                <g:if test="${controlCalidadComplejoInstance?.porcentajeBismuto}">
                    <dt class="col-sm-3">Bismuto</dt>
                    <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="porcentajeBismuto"/></dd>
                </g:if>
                <g:if test="${controlCalidadComplejoInstance?.porcentajeEstano}">
                    <dt class="col-sm-3">Estaño</dt>
                    <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="porcentajeEstano"/></dd>
                </g:if>
                <g:if test="${controlCalidadComplejoInstance?.porcentajeZinc}">
                    <dt class="col-sm-3">Zinc</dt>
                    <dd class="col-sm-9"><g:fieldValue bean="${controlCalidadComplejoInstance}" field="porcentajeZinc"/></dd>
                </g:if>
            </dl>
        </g:if>
    </div>
    <div class="card-footer">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:if test="${controlCalidadComplejoInstance?.recepcionDeComplejo?.estadoDelLote?.equals('NO LIQUIDADO')}">
                <g:link action="edit" id="${controlCalidadComplejoInstance?.id}" class="btn btn-warning btn-sm">
                    <i class="fas fa-edit mr-1"></i>Editar
                </g:link>
                <g:form action="delete" method="post" class="d-inline">
                    <g:hiddenField name="id" value="${controlCalidadComplejoInstance?.id}"/>
                    <button type="button" class="btn btn-danger btn-sm" onclick="confirmarEliminacion(this.form)">
                        <i class="fas fa-trash mr-1"></i>Eliminar
                    </button>
                </g:form>
                <script>
                    function confirmarEliminacion(form) {
                        Swal.fire({
                            title: '¿Eliminar este análisis?',
                            html: 'Se eliminará el análisis del lote <strong>${controlCalidadComplejoInstance?.recepcionDeComplejo?.encodeAsHTML()}</strong>.<br>El lote volverá a quedar <strong>SIN ANALISIS</strong>. Esta acción no se puede deshacer.',
                            icon: 'warning',
                            showCancelButton: true,
                            confirmButtonColor: '#d33',
                            cancelButtonColor: '#6c757d',
                            confirmButtonText: 'Sí, eliminar',
                            cancelButtonText: 'Cancelar',
                            reverseButtons: true
                        }).then(function (result) {
                            if (result.isConfirmed) {
                                form.submit();
                            }
                        });
                    }
                </script>
            </g:if>
        </sec:ifAnyGranted>
    </div>
</div>
</body>
</html>
