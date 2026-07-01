<%@ page import="org.socymet.proveedor.EmpresaSeccion; org.socymet.recepcion.RecepcionDeComplejo" %>

<%-- ── Campos de sistema ──────────────────────────────────────────────── --%>
<g:hiddenField name="recepcionId" value="${recepcionDeComplejoInstance.loteComplejo ? recepcionDeComplejoInstance.id : 0}"/>
<g:hiddenField name="documentacionCompleta" value="${recepcionDeComplejoInstance?.documentacionCompleta}"/>
<g:hiddenField name="detalleLaboratorio1" value="${fieldValue(bean: recepcionDeComplejoInstance, field: 'detalleLaboratorio1')}"/>
<g:hiddenField name="costoLaboratorio1" value="${fieldValue(bean: recepcionDeComplejoInstance, field: 'costoLaboratorio1')}"/>
<g:hiddenField name="totalCostoLaboratorio" value="${fieldValue(bean: recepcionDeComplejoInstance, field: 'totalCostoLaboratorio')}"/>

<%-- Código de lote (asignado por backend) --%>
<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Código Lote</label>
    <div class="col-sm-4">
        <g:textField name="codigoLote" value="${recepcionDeComplejoInstance?.codigoLote}" class="form-control" readonly="readonly"/>
    </div>
</div>

<%-- Depósito y gestión minera (asignados por backend) --%>
<div id="_deposito" class="form-group row" >
    <label class="col-sm-3 col-form-label">Depósito <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:select id="deposito" name="deposito.id" from="${org.socymet.proveedor.Deposito.list()}"
            optionKey="id" required="" value="${recepcionDeComplejoInstance?.deposito?.id}"
            class="form-control many-to-one"/>
    </div>
</div>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Gestión Minera</label>
    <div class="col-sm-4">
        <g:datepickerUI name="gestionMinera" value="${recepcionDeComplejoInstance?.gestionMinera ?: new Date()}"/>
    </div>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     LOTE DE TURNO
     ══════════════════════════════════════════════════════════════════════ --%>
<div id="_loteTurno" class="form-group row">
    <label class="col-sm-3 col-form-label font-weight-bold">Lote de Turno</label>
    <div class="col-sm-6">
        <div class="callout callout-success py-2 mb-0">
            <span id="loteTurno" class="font-weight-bold" style="font-size: 1.6em; color: #155724;">?</span>
        </div>
    </div>
</div>

<%-- Lote del registro (edición): mismo estilo que el lote de turno --%>
<g:if test="${recepcionDeComplejoInstance?.id}">
    <div id="_lote" class="form-group row">
        <label class="col-sm-3 col-form-label font-weight-bold">Lote</label>
        <div class="col-sm-6">
            <div class="callout callout-success py-2 mb-0">
                <span class="font-weight-bold" style="font-size: 1.6em; color: #155724;">${recepcionDeComplejoInstance?.toString()}</span>
            </div>
        </div>
    </div>
</g:if>

<%-- Lote asignado (solo visible en edición) --%>
%{--<g:if test="${recepcionDeComplejoInstance?.loteComplejo}">--}%
%{--    <div class="form-group row">--}%
%{--        <label class="col-sm-3 col-form-label font-weight-bold">Lote Complejo</label>--}%
%{--        <div class="col-sm-4">--}%
%{--            <span class="form-control-plaintext font-weight-bold" style="color: #b81900; font-size: 1.2em;">${recepcionDeComplejoInstance.toString()}</span>--}%
%{--        </div>--}%
%{--    </div>--}%
%{--</g:if>--}%
<g:if test="${recepcionDeComplejoInstance?.lotePlomoPlata}">
    <div class="form-group row">
        <label class="col-sm-3 col-form-label font-weight-bold">Lote Plomo Plata</label>
        <div class="col-sm-4">
            <span class="form-control-plaintext font-weight-bold" style="color: #b81900; font-size: 1.2em;">${recepcionDeComplejoInstance.toString()}</span>
        </div>
    </div>
</g:if>
<g:if test="${recepcionDeComplejoInstance?.loteZincPlata}">
    <div class="form-group row">
        <label class="col-sm-3 col-form-label font-weight-bold">Lote Zinc Plata</label>
        <div class="col-sm-4">
            <span class="form-control-plaintext font-weight-bold" style="color: #b81900; font-size: 1.2em;">${recepcionDeComplejoInstance.toString()}</span>
        </div>
    </div>
</g:if>

<%-- ══════════════════════════════════════════════════════════════════════
     INFORMACIÓN GENERAL
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 class="form-section-title">Información General</h5>

<div class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'fechaDeRecepcion', 'has-error')}">
    <label class="col-sm-3 col-form-label">Fecha de Recepción <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:datepickerUI name="fechaDeRecepcion" value="${recepcionDeComplejoInstance?.fechaDeRecepcion ?: new Date()}"/>
    </div>
</div>

<%-- Proveedor (Select2 async) --%>
<div class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'cliente', 'has-error')}">
    <label class="col-sm-3 col-form-label">Proveedor <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <select id="clienteSelect" class="form-control" style="width: 100%">
            <g:if test="${recepcionDeComplejoInstance?.cliente?.id}">
                <option value="${recepcionDeComplejoInstance.cliente.id}" selected="selected">
                    ${recepcionDeComplejoInstance.cliente.ci} — ${recepcionDeComplejoInstance.cliente.nombre} [${recepcionDeComplejoInstance.empresa?.nombreDeEmpresa}]
                </option>
            </g:if>
        </select>
        <g:hiddenField name="cliente.id" value="${recepcionDeComplejoInstance?.cliente?.id}"/>
        <g:hiddenField name="empresa.id" value="${recepcionDeComplejoInstance?.empresa?.id}"/>
        <g:hiddenField name="costoTransporteComplejos"/>
        <g:hiddenField name="unidadMonetariaComplejos"/>
        <g:hiddenField name="unidadDeCobroComplejos"/>
        <g:hiddenField name="costoTransporteConcentrados"/>
        <g:hiddenField name="unidadMonetariaConcentrados"/>
        <g:hiddenField name="unidadDeCobroConcentrados"/>
        <g:hiddenField name="tipoDeCambioComercial"/>
    </div>
</div>

<div class="form-group row">
    <label class="col-sm-3 col-form-label">Empresa</label>
    <div class="col-sm-7">
        <g:textField name="nombreEmpresa" value="${recepcionDeComplejoInstance?.empresa?.nombreDeEmpresa}" class="form-control" readonly="true"/>
    </div>
</div>

<%-- Sección de empresa (mostrada por filtrarCuadrillas() cuando aplica) --%>
<div id="_empresaSeccion" class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'empresaSeccion', 'has-error')}" style="display: none">
    <label class="col-sm-3 col-form-label">Sección <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <g:select id="empresaSeccion" name="empresaSeccion.id"
            from="${org.socymet.proveedor.EmpresaSeccion.list(sort: 'nombreSeccion')}"
            optionKey="id" value="${recepcionDeComplejoInstance?.empresaSeccion?.id}"
            class="form-control many-to-one chosen-select"/>
    </div>
</div>

<%-- Chofer (Select2 async) --%>
<div class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'chofer', 'has-error')}">
    <label class="col-sm-3 col-form-label">Chofer <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <select id="choferSelect" class="form-control" style="width: 100%">
            <g:if test="${recepcionDeComplejoInstance?.chofer?.id}">
                <option value="${recepcionDeComplejoInstance.chofer.id}" selected="selected">
                    ${recepcionDeComplejoInstance.chofer.nombre} | ${recepcionDeComplejoInstance.chofer.ci}
                </option>
            </g:if>
        </select>
        <g:hiddenField name="chofer.id" value="${recepcionDeComplejoInstance?.chofer?.id}"/>
        <g:hiddenField name="nombreChofer" value="${recepcionDeComplejoInstance?.chofer?.nombre}"/>
        <g:hiddenField name="ciChofer" value="${recepcionDeComplejoInstance?.chofer?.ci}"/>
    </div>
</div>

<%-- Vehículo (Select2 async) --%>
<div class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'automovil', 'has-error')}">
    <label class="col-sm-3 col-form-label">Vehículo <span class="text-danger">*</span></label>
    <div class="col-sm-7">
        <select id="automovilSelect" class="form-control" style="width: 100%">
            <g:if test="${recepcionDeComplejoInstance?.automovil?.id}">
                <option value="${recepcionDeComplejoInstance.automovil.id}" selected="selected">
                    ${recepcionDeComplejoInstance.placa}<g:if test="${recepcionDeComplejoInstance.automovil.modelo && recepcionDeComplejoInstance.automovil.modelo != '-'}"> — ${recepcionDeComplejoInstance.automovil.modelo}</g:if>
                </option>
            </g:if>
        </select>
        <g:hiddenField name="automovil.id" value="${recepcionDeComplejoInstance?.automovil?.id}"/>
        <g:hiddenField name="placa" value="${recepcionDeComplejoInstance?.placa}"/>
    </div>
</div>

<%-- Documentación --%>
<div class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'numeroDeDocumento', 'has-error')}" hidden>
    <label class="col-sm-3 col-form-label">N° Documento <span class="text-danger">*</span></label>
    <div class="col-sm-3">
        <g:textField name="numeroDeDocumento" inputmode="numeric" value="${recepcionDeComplejoInstance?.numeroDeDocumento}" class="form-control" required=""/>
    </div>
    <div class="col-sm-5 d-flex align-items-center">
        <g:checkBox name="tieneDocumentos" value="1" checked="true"/>
        <span class="ml-2 text-muted small">Desmarque si no existe documentación.</span>
    </div>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     INFORMACIÓN DEL PRODUCTO
     ══════════════════════════════════════════════════════════════════════ --%>
<h5 class="form-section-title">Información del Producto</h5>

<div class="form-group row">
    <label class="col-sm-3 col-form-label">Tipo de Material</label>
    <div class="col-sm-4">
        <g:select name="tipoDeMaterial" from="${['BROZA', 'CONCENTRADO']}"
            value="${recepcionDeComplejoInstance?.tipoDeMaterial}" class="form-control"/>
    </div>
</div>

<%-- Campos de producto controlados por JS (ocultos en creación) --%>
<div id="radio3" style="display: none">
    <div class="form-group row">
        <label class="col-sm-3 col-form-label">Condición de Entrega</label>
        <div class="col-sm-6 d-flex align-items-center">
            <input type="radio" id="radioSpot" value="spot" name="radio3" checked="checked">
            <label for="radioSpot" class="mr-3 mb-0 ml-1">SPOT</label>
            <input type="radio" id="radioTermCon" value="termCon" name="radio3">
            <label for="radioTermCon" class="mb-0 ml-1">TERM-CON</label>
        </div>
    </div>
</div>

<div id="radio2" style="display: none">
    <div class="form-group row">
        <label class="col-sm-3 col-form-label">Naturaleza Mineral</label>
        <div class="col-sm-6 d-flex align-items-center">
            <input type="radio" id="radioSulfuro" value="sulfuro" name="radio2" checked="checked">
            <label for="radioSulfuro" class="mr-3 mb-0 ml-1">SULFURO</label>
            <input type="radio" id="radioOxido" value="oxido" name="radio2">
            <label for="radioOxido" class="mb-0 ml-1">ÓXIDO</label>
        </div>
    </div>
</div>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Tipo de Mineral</label>
    <div class="col-sm-4">
        <g:select name="tipoDeMineral" from="${['ZN PB AG']}"
            value="${recepcionDeComplejoInstance?.tipoDeMineral}" class="form-control"/>
    </div>
</div>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Condición de Entrega</label>
    <div class="col-sm-4">
        <g:select name="condicionDeEntrega" from="${['SPOT', 'TERM-CON']}"
            value="${recepcionDeComplejoInstance?.condicionDeEntrega}" class="form-control"/>
    </div>
</div>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Naturaleza Mineral</label>
    <div class="col-sm-4">
        <g:select name="naturalezaMineral" from="${['SULFURO', 'OXIDO']}"
            value="${recepcionDeComplejoInstance?.naturalezaMineral}" class="form-control"/>
    </div>
</div>

<div class="form-group row">
    <label class="col-sm-3 col-form-label">Cantidad de Sacos</label>
    <div class="col-sm-4">
        <g:field type="number" name="cantidadSacos" min="0" inputmode="numeric" value="${recepcionDeComplejoInstance?.cantidadSacos ?: 0}" class="form-control"/>
    </div>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     PESOS Y COSTOS
     ══════════════════════════════════════════════════════════════════════ --%>
<%-- Los campos numéricos leídos por JS emiten el valor crudo (BigDecimal sin
     separador de miles) en lugar de fieldValue(); así el cálculo en cliente es
     independiente del locale (en_US/es). No reemplazar por fieldValue(). --%>
<h5 class="form-section-title">Pesos y Costos</h5>

<div class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'pesoNeto', 'has-error')}">
    <label class="col-sm-3 col-form-label">Peso Bruto [Kg]<span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <div class="input-group">
            <g:field name="pesoNeto" value="${recepcionDeComplejoInstance?.pesoNeto}" class="form-control" required="" inputmode="decimal"/>
            <div class="input-group-append">
                <span class="input-group-text text-muted small">Carga + Envase</span>
            </div>
        </div>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'pesoTara', 'has-error')}">
    <label class="col-sm-3 col-form-label">Tara [Kg]<span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <div class="input-group">
            <g:field name="pesoTara" value="${recepcionDeComplejoInstance?.pesoTara}" class="form-control" required="" inputmode="decimal"/>
            <div class="input-group-append">
                <span class="input-group-text text-muted small">Envase</span>
            </div>
        </div>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'pesoBruto', 'has-error')}">
    <label class="col-sm-3 col-form-label">Peso Bruto Húmedo [Kg]<span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="pesoBruto" value="${recepcionDeComplejoInstance?.pesoBruto}" class="form-control amarillo" required="" readonly="readonly"/>
    </div>
</div>

<div class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'costoDeTransporte', 'has-error')}">
    <label class="col-sm-3 col-form-label">Costo de Transporte [Bs]<span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="costoDeTransporte" value="${recepcionDeComplejoInstance?.costoDeTransporte}" class="form-control amarillo" required="" readonly="readonly"/>
    </div>
</div>

<%-- Campos financieros adicionales (ocultos, usados en flujos específicos) --%>
<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Anticipo Autorizado <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:field name="anticipoAutorizado" value="${fieldValue(bean: recepcionDeComplejoInstance, field: 'anticipoAutorizado')}" class="form-control" required="" inputmode="decimal"/>
    </div>
</div>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Estado del Análisis <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:select name="estadoAnalisis" from="${['CON ANALISIS', 'SIN ANALISIS']}" required=""
            value="${recepcionDeComplejoInstance?.estadoAnalisis}" class="form-control"/>
    </div>
</div>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Estado del Lote <span class="text-danger">*</span></label>
    <div class="col-sm-4">
        <g:select name="estadoDelLote" from="${['NO LIQUIDADO', 'LIQUIDADO', 'Baja']}" required=""
            value="${recepcionDeComplejoInstance?.estadoDelLote}" class="form-control"/>
    </div>
</div>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Nombre Compósito</label>
    <div class="col-sm-4">
        <g:textField name="nombreComposito" value="${recepcionDeComplejoInstance?.nombreComposito}" class="form-control" readonly="readonly"/>
    </div>
</div>

<div class="form-group row" style="display: none">
    <label class="col-sm-3 col-form-label">Observaciones</label>
    <div class="col-sm-8">
        <g:textField name="observaciones" value="${recepcionDeComplejoInstance?.observaciones}" class="form-control"/>
    </div>
</div>

<%-- ══════════════════════════════════════════════════════════════════════
     COTIZACIONES
     ══════════════════════════════════════════════════════════════════════ --%>
<div id="_cotizaciones">
    <h5 class="form-section-title">Cotizaciones</h5>

    <%-- Cotización del Dólar: últimos 10 registros (fecha desc). Si el valor actual no está en los 10, se antepone. --%>
    <g:set var="cotizacionesDolar" value="${org.socymet.cotizaciones.CotizacionDeDolar.list(max: 10, sort: 'fecha', order: 'desc')}"/>
    <g:set var="cotDolarActual" value="${recepcionDeComplejoInstance?.cotizacionDeDolar}"/>
    <div class="form-group row">
        <label class="col-sm-3 col-form-label">Cotización del Dólar</label>
        <div class="col-sm-6">
            <select name="cotizacionDeDolar.id" class="form-control">
                <g:if test="${cotDolarActual && !cotizacionesDolar*.id.contains(cotDolarActual.id)}">
                    <option value="${cotDolarActual.id}" selected="selected"><g:formatDate date="${cotDolarActual.fecha}" format="dd/MM/yyyy"/> — Of: ${cotDolarActual.tipoDeCambioOficial} / Com: ${cotDolarActual.tipoDeCambioComercial}</option>
                </g:if>
                <g:each in="${cotizacionesDolar}" var="cd">
                    <option value="${cd.id}" ${cotDolarActual?.id == cd.id ? 'selected="selected"' : ''}><g:formatDate date="${cd.fecha}" format="dd/MM/yyyy"/> — Of: ${cd.tipoDeCambioOficial} / Com: ${cd.tipoDeCambioComercial}</option>
                </g:each>
            </select>
        </div>
    </div>

    <div class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'cotizacionDiariaDeMinerales', 'has-error')}">
        <label class="col-sm-3 col-form-label">Cot. Diaria <span class="text-danger">*</span></label>
        <div class="col-sm-6">
            <g:select id="cotizacionDiariaDeMinerales" name="cotizacionDiariaDeMinerales.id"
                from="${org.socymet.cotizaciones.CotizacionDiariaDeMinerales.list(sort: 'fecha', order: 'desc', max: 20)}"
                optionKey="id" required="" value="${recepcionDeComplejoInstance?.cotizacionDiariaDeMinerales?.id}"
                class="form-control many-to-one"/>
        </div>
        <div class="col-sm-2 d-flex align-items-center">
            <span id="hayCotizacionDiaria" style="color: red"></span>
        </div>
    </div>

    <div class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'cotizacionQuincenalDeMinerales', 'has-error')}">
        <label class="col-sm-3 col-form-label">Cot. Quincenal <span class="text-danger">*</span></label>
        <div class="col-sm-6">
            <g:select id="cotizacionQuincenalDeMinerales" name="cotizacionQuincenalDeMinerales.id"
                from="${org.socymet.cotizaciones.CotizacionQuincenalDeMinerales.list(sort: 'fecha', order: 'desc', max: 10)}"
                optionKey="id" required="" value="${recepcionDeComplejoInstance?.cotizacionQuincenalDeMinerales?.id}"
                class="form-control many-to-one"/>
        </div>
        <div class="col-sm-2 d-flex align-items-center">
            <span id="hayCotizacionQuincenal" style="color: red"></span>
        </div>
    </div>

    <div class="form-group row ${hasErrors(bean: recepcionDeComplejoInstance, field: 'alicuota', 'has-error')}">
        <label class="col-sm-3 col-form-label">Alícuota <span class="text-danger">*</span></label>
        <div class="col-sm-6">
            <g:select id="alicuota" name="alicuota.id"
                from="${org.socymet.cotizaciones.Alicuota.list(sort: 'fecha', order: 'desc', max: 10)}"
                optionKey="id" required="" value="${recepcionDeComplejoInstance?.alicuota?.id}"
                class="form-control many-to-one"/>
        </div>
        <div class="col-sm-2 d-flex align-items-center">
            <span id="hayAlicuota" style="color: red"></span>
        </div>
    </div>
</div>
