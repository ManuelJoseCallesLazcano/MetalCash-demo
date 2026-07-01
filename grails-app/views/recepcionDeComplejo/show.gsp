<%@ page import="org.socymet.recepcion.RecepcionDeComplejo" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Recepción</title>
    <style>
        .show-section-title {
            font-size: 0.86rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.09em;
            color: #0f6674; border-left: 5px solid #17a2b8; border-bottom: 1px solid #bfe6ec;
            background: linear-gradient(to right, #d6f0f4, #eef9fb 55%, transparent);
            padding: 0.55rem 1rem; margin: 1.8rem 0 1rem; border-radius: 0 4px 4px 0;
            box-shadow: 0 1px 2px rgba(0,0,0,.05);
        }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Recepción</h3>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1">
            <i class="fas fa-list mr-1"></i>Lista
        </g:link>
        <g:link action="create" class="btn btn-primary btn-sm">
            <i class="fas fa-plus mr-1"></i>Nueva
        </g:link>
    </div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div class="alert alert-info alert-dismissible">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                ${flash.message}
            </div>
        </g:if>

        <%-- Identificación del Lote --%>
        <g:if test="${recepcionDeComplejoInstance?.loteComplejo}">
            <div class="alert alert-success py-2">
                <strong>Lote:</strong>
                <span class="h5 ml-2">${recepcionDeComplejoInstance.toString()}</span>
            </div>
        </g:if>
        <g:if test="${recepcionDeComplejoInstance?.lotePlomoPlata}">
            <div class="alert alert-danger py-2">
                <strong>Lote Plomo Plata:</strong>
                <span class="h5 ml-2">${recepcionDeComplejoInstance.toString()}</span>
            </div>
        </g:if>
        <g:if test="${recepcionDeComplejoInstance?.loteZincPlata}">
            <div class="alert alert-danger py-2">
                <strong>Lote Zinc Plata:</strong>
                <span class="h5 ml-2">${recepcionDeComplejoInstance.toString()}</span>
            </div>
        </g:if>
        <g:if test="${recepcionDeComplejoInstance?.loteCobrePlata}">
            <div class="alert alert-danger py-2">
                <strong>Lote Cobre Plata:</strong>
                <span class="h5 ml-2">${recepcionDeComplejoInstance.toString()}</span>
            </div>
        </g:if>

        <h5 class="show-section-title">Información General</h5>
        <dl class="row">
            <g:if test="${recepcionDeComplejoInstance?.fechaDeRecepcion}">
                <dt class="col-sm-3">Fecha de Recepción</dt>
                <dd class="col-sm-9"><g:formatDate date="${recepcionDeComplejoInstance?.fechaDeRecepcion}" format="dd/MM/yyyy"/></dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.cliente}">
                <dt class="col-sm-3">Cliente</dt>
                <dd class="col-sm-9">
                    <g:link controller="cliente" action="show" id="${recepcionDeComplejoInstance?.cliente?.id}">${recepcionDeComplejoInstance?.cliente?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.empresa}">
                <dt class="col-sm-3">Empresa</dt>
                <dd class="col-sm-9">
                    <g:link controller="empresa" action="show" id="${recepcionDeComplejoInstance?.empresa?.id}">${recepcionDeComplejoInstance?.empresa?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.empresaSeccion}">
                <dt class="col-sm-3">Empresa Sección</dt>
                <dd class="col-sm-9">
                    <g:link controller="empresaSeccion" action="show" id="${recepcionDeComplejoInstance?.empresaSeccion?.id}">${recepcionDeComplejoInstance?.empresaSeccion?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.chofer}">
                <dt class="col-sm-3">Chofer</dt>
                <dd class="col-sm-9">
                    <g:link controller="chofer" action="show" id="${recepcionDeComplejoInstance?.chofer?.id}">${recepcionDeComplejoInstance?.chofer?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.automovil}">
                <dt class="col-sm-3">Automóvil</dt>
                <dd class="col-sm-9">
                    <g:link controller="automovil" action="show" id="${recepcionDeComplejoInstance?.automovil?.id}">${recepcionDeComplejoInstance?.automovil?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
        </dl>

        <h5 class="show-section-title">Información del Producto</h5>
        <dl class="row">
            <g:if test="${recepcionDeComplejoInstance?.tipoDeMaterial}">
                <dt class="col-sm-3">Tipo de Material</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${recepcionDeComplejoInstance}" field="tipoDeMaterial"/></dd>
            </g:if>
            <dt class="col-sm-3">Cantidad de Sacos</dt>
            <dd class="col-sm-9">${recepcionDeComplejoInstance?.cantidadSacos ?: 0}</dd>
        </dl>

        <h5 class="show-section-title">Pesos y Costos</h5>
        <dl class="row">
            <g:if test="${recepcionDeComplejoInstance?.pesoNeto}">
                <dt class="col-sm-3">Peso Bruto [Kg]</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${recepcionDeComplejoInstance}" field="pesoNeto"/> <small class="text-muted">(Carga + Envase)</small></dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.pesoTara}">
                <dt class="col-sm-3">Tara [Kg]</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${recepcionDeComplejoInstance}" field="pesoTara"/> <small class="text-muted">(Envase)</small></dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.pesoBruto}">
                <dt class="col-sm-3">Peso Bruto Húmedo [Kg]</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${recepcionDeComplejoInstance}" field="pesoBruto"/></dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.costoDeTransporte}">
                <dt class="col-sm-3">Costo de Transporte</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${recepcionDeComplejoInstance}" field="costoDeTransporte"/></dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.transportePagado}">
                <dt class="col-sm-3">Transporte Pagado</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${recepcionDeComplejoInstance}" field="transportePagado"/></dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.anticipoAutorizado}">
                <dt class="col-sm-3">Anticipo Autorizado</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${recepcionDeComplejoInstance}" field="anticipoAutorizado"/></dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.estadoAnticipo}">
                <dt class="col-sm-3">Estado Anticipo</dt>
                <dd class="col-sm-9">
                    <g:if test="${recepcionDeComplejoInstance.estadoAnticipo == 'PAGADO'}">
                        <span class="badge badge-success">${recepcionDeComplejoInstance.estadoAnticipo}</span>
                    </g:if>
                    <g:elseif test="${recepcionDeComplejoInstance.estadoAnticipo == 'CON ANTICIPO'}">
                        <span class="badge badge-danger">${recepcionDeComplejoInstance.estadoAnticipo}</span>
                    </g:elseif>
                    <g:else>
                        <span class="badge badge-info">${recepcionDeComplejoInstance.estadoAnticipo}</span>
                    </g:else>
                </dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.estadoAnalisis}">
                <dt class="col-sm-3">Estado Análisis</dt>
                <dd class="col-sm-9">
                    <g:if test="${recepcionDeComplejoInstance.estadoAnalisis == 'CON ANALISIS'}">
                        <span class="badge badge-success">${recepcionDeComplejoInstance.estadoAnalisis}</span>
                    </g:if>
                    <g:else>
                        <span class="badge badge-danger">${recepcionDeComplejoInstance.estadoAnalisis}</span>
                    </g:else>
                </dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.estadoDelLote}">
                <dt class="col-sm-3">Estado del Lote</dt>
                <dd class="col-sm-9">
                    <g:if test="${recepcionDeComplejoInstance.estadoDelLote == 'LIQUIDADO'}">
                        <span class="badge badge-success">${recepcionDeComplejoInstance.estadoDelLote}</span>
                    </g:if>
                    <g:else>
                        <span class="badge badge-danger">${recepcionDeComplejoInstance.estadoDelLote}</span>
                    </g:else>
                </dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.nombreComposito}">
                <dt class="col-sm-3">Nombre Compósito</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${recepcionDeComplejoInstance}" field="nombreComposito"/></dd>
            </g:if>
        </dl>

        <h5 class="show-section-title">Cotizaciones</h5>
        <dl class="row">
            <g:if test="${recepcionDeComplejoInstance?.cotizacionDeDolar}">
                <dt class="col-sm-3">Cotización del Dólar</dt>
                <dd class="col-sm-9">
                    <g:formatDate date="${recepcionDeComplejoInstance.cotizacionDeDolar.fecha}" format="dd/MM/yyyy"/>
                    — Oficial: <g:formatNumber number="${recepcionDeComplejoInstance.cotizacionDeDolar.tipoDeCambioOficial}" type="number" maxFractionDigits="2" minFractionDigits="2"/>
                    / Comercial: <g:formatNumber number="${recepcionDeComplejoInstance.cotizacionDeDolar.tipoDeCambioComercial}" type="number" maxFractionDigits="2" minFractionDigits="2"/>
                </dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.cotizacionDiariaDeMinerales}">
                <dt class="col-sm-3">Cotización Diaria</dt>
                <dd class="col-sm-9">${recepcionDeComplejoInstance?.cotizacionDiariaDeMinerales?.encodeAsHTML()}</dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.cotizacionQuincenalDeMinerales}">
                <dt class="col-sm-3">Cotización Quincenal</dt>
                <dd class="col-sm-9">${recepcionDeComplejoInstance?.cotizacionQuincenalDeMinerales?.encodeAsHTML()}</dd>
            </g:if>
            <g:if test="${recepcionDeComplejoInstance?.alicuota}">
                <dt class="col-sm-3">Alícuota</dt>
                <dd class="col-sm-9">${recepcionDeComplejoInstance?.alicuota?.encodeAsHTML()}</dd>
            </g:if>
        </dl>
    </div>
    <div class="card-footer">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:if test="${recepcionDeComplejoInstance?.estadoDelLote?.equals('NO LIQUIDADO')}">
                <g:link action="edit" id="${recepcionDeComplejoInstance?.id}" class="btn btn-warning btn-sm">
                    <i class="fas fa-edit mr-1"></i>Editar
                </g:link>
                <g:form action="delete" method="post" class="d-inline">
                    <g:hiddenField name="id" value="${recepcionDeComplejoInstance?.id}"/>
                    <button type="button" class="btn btn-danger btn-sm" onclick="confirmarEliminacion(this.form)">
                        <i class="fas fa-trash mr-1"></i>Eliminar
                    </button>
                </g:form>
                <script>
                    function confirmarEliminacion(form) {
                        Swal.fire({
                            title: '¿Eliminar este registro?',
                            html: 'Se eliminará el lote <strong>${recepcionDeComplejoInstance?.toString()}</strong>.<br>Esta acción no se puede deshacer.',
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
