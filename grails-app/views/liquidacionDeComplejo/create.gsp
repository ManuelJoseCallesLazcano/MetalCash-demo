<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Nueva Liquidación de Complejo</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" type="text/css">
    <style>
        .select2-container--default .select2-selection--single { height: calc(1.5em + .75rem + 2px); padding: .375rem .75rem; border: 1px solid #ced4da; border-radius: .25rem; }
        .select2-container--default .select2-selection--single .select2-selection__rendered { padding: 0; line-height: 1.5; }
        .form-section-title { font-size: 0.78rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.07em; color: #2c3e50; border-left: 4px solid #17a2b8; background: linear-gradient(to right, #e5f6f8, transparent); padding: 0.45rem 0.85rem; margin: 1.5rem 0 1rem; border-radius: 0 3px 3px 0; }
        .amarillo { background-color: #fff8e1; }
        .liquido-box { display:flex; align-items:center; justify-content:space-between; padding:14px 22px; border-radius:6px; font-size:1.25rem; font-weight:700; margin-top:8px; border:2px solid; }
        .liquido-box .liquido-valor { font-size:1.9rem; }
        .liquido-positivo { background:#e8f5e9; border-color:#43a047; color:#1b5e20; }
        .liquido-negativo { background:#fdecea; border-color:#e53935; color:#b71c1c; }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
    <asset:javascript src="liquidacionDeComplejo/calculoLiquidacion.js"/>
</head>
<body>
<div class="card card-primary">
    <div class="card-header"><h3 class="card-title">Nueva Liquidación de Complejo</h3></div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div id="swalFlashMsg" style="display:none">${flash.message}</div>
            <script>document.addEventListener('DOMContentLoaded', function () { Swal.fire({ icon: '${flash.swalIcon ?: 'info'}', title: '${flash.swalTitle ?: 'Información'}', text: document.getElementById('swalFlashMsg').textContent.trim(), confirmButtonText: 'Aceptar' }); });</script>
        </g:if>
        <g:hasErrors bean="${liquidacionDeComplejoInstance}">
            <div id="swalErrorList" style="display:none"><ul style="text-align:left;margin:0;padding-left:1.2em"><g:eachError bean="${liquidacionDeComplejoInstance}" var="error"><li><g:message error="${error}"/></li></g:eachError></ul></div>
            <script>document.addEventListener('DOMContentLoaded', function () { Swal.fire({ icon: 'error', title: 'Error de validación', html: document.getElementById('swalErrorList').innerHTML, confirmButtonText: 'Corregir' }); });</script>
        </g:hasErrors>

        <%-- Selector de lote a liquidar --%>
        <div class="form-group row mb-0">
            <label class="col-sm-2 col-form-label">Lote a liquidar</label>
            <div class="col-sm-7">
                <select id="selectorLote" class="form-control" style="width:100%">
                    <option value="">-- Seleccione un lote (NO LIQUIDADO) --</option>
                    <g:each in="${recepcionesDisponibles}" var="rec">
                        <option value="${rec.id}" ${liquidacionDeComplejoInstance?.recepcionDeComplejo?.id == rec.id ? 'selected' : ''}>${rec.codigoLote} — ${rec.cliente?.nombre} [${rec.empresa?.nombreDeEmpresa}]</option>
                    </g:each>
                </select>
            </div>
            <div class="col-sm-3">
                <g:link action="list" class="btn btn-secondary"><i class="fas fa-times mr-1"></i>Cancelar</g:link>
            </div>
        </div>
    </div>

    <g:if test="${liquidacionDeComplejoInstance?.recepcionDeComplejo}">
        <g:form action="save" name="liquidacionForm">
            <div class="card-body pt-0">
                <g:render template="form"/>
            </div>
            <div class="card-footer d-flex align-items-center">
                <button type="button" class="btn btn-primary" id="btnGuardar"><i class="fas fa-save mr-1"></i>Guardar liquidación</button>
                <g:link action="list" class="btn btn-secondary ml-1">Cancelar</g:link>
                <button type="button" class="btn btn-outline-secondary ml-auto" id="btnBorrador"><i class="fas fa-print mr-1"></i>Imprimir borrador</button>
            </div>
        </g:form>
    </g:if>
    <g:else>
        <div class="card-body pt-0"><div class="alert alert-info py-2 mb-0">Seleccione un lote para iniciar la liquidación.</div></div>
    </g:else>
</div>

<script>
    $(function () {
        if ($.fn.select2) $('#selectorLote').select2({ language: 'es', width: '100%' });
        // Foco automático en el cuadro de búsqueda al abrir cualquier Select2
        $(document).on('select2:open', function () {
            var campo = document.querySelector('.select2-container--open .select2-search__field');
            if (campo) campo.focus();
        });
        $('#selectorLote').on('change', function () {
            if (this.value) window.location = '${createLink(action: "create")}?recepcionId=' + this.value;
        });

        // Diferencia ley final vs registrada
        function actualizarDiffLeyes() {
            $('.ley-final').each(function () {
                var base = parseFloat($(this).data('base')) || 0;
                var val = parseFloat(this.value) || 0;
                var diff = val - base;
                var $sp = $('.ley-diff[data-for="' + this.id + '"]');
                $sp.text((diff >= 0 ? '+' : '') + diff.toFixed(3));
                $sp.removeClass('text-danger text-success').addClass(diff === 0 ? '' : (diff > 0 ? 'text-success' : 'text-danger'));
            });
        }
        $(document).on('input', '.ley-final', actualizarDiffLeyes);
        actualizarDiffLeyes();

        // Promediado: al ingresar la ley del cliente, la ley final = (registrada + cliente)/2
        $(document).on('input', '.ley-cliente', function () {
            var base = parseFloat($(this).data('base')) || 0;
            var cli = parseFloat(this.value);
            var $final = $('#' + $(this).data('final'));
            $final.val((!isNaN(cli) && cli > 0) ? (base + cli) / 2 : base);
            actualizarDiffLeyes();
            if (window.LiquidacionComplejoCalc) LiquidacionComplejoCalc.recalcular();
        });

        // Anticipo contra entrega: no puede ser negativo ni superar el saldo por pagar del anticipo
        // (data-max), incluso si el anticipo cubre varios lotes. Si no hay anticipo, data-max=0.
        $(document).on('input', '#anticipoContraEntrega', function () {
            var max = parseFloat($(this).data('max')) || 0;
            var v = parseFloat(this.value);
            if (isNaN(v) || v < 0) this.value = 0;
            else if (v > max) this.value = max;
            actualizarAvisoResidual();
            if (window.LiquidacionComplejoCalc) LiquidacionComplejoCalc.recalcular();
        });

        // Aviso de traslado a ACFE: si es el último lote del anticipo (data-ultimo) y el cobro es
        // menor que el saldo por pagar (data-max), el residual se trasladará a un ACFE al guardar.
        function actualizarAvisoResidual() {
            var $f = $('#anticipoContraEntrega');
            if (!$f.length) { return; }
            var ultimo = ($f.data('ultimo') === true) || (String($f.data('ultimo')) === 'true');
            var max = parseFloat($f.data('max')) || 0;
            var v = parseFloat($f.val()) || 0;
            $('#avisoResidualACFE').toggle(ultimo && max > 0 && v < max);
        }
        actualizarAvisoResidual();

        // Cobro de saldo: no puede ser negativo ni superar el saldo anterior (deuda informativa)
        $(document).on('input', '#anticipoContraFuturaEntrega', function () {
            var deuda = parseFloat($('#saldoAnterior').val()) || 0;
            var v = parseFloat(this.value);
            if (isNaN(v) || v < 0) this.value = 0;
            else if (v > deuda) this.value = deuda;
            if (window.LiquidacionComplejoCalc) LiquidacionComplejoCalc.recalcular();
        });

        // Quitar fila de retención
        $(document).on('click', '.btn-quitar-retencion', function () {
            $(this).closest('tr').remove();
            if (window.LiquidacionComplejoCalc) LiquidacionComplejoCalc.recalcular();
        });

        // Confirmación al guardar
        $('#btnGuardar').on('click', function () {
            Swal.fire({
                title: '¿Registrar esta liquidación?',
                html: 'Se registrará la liquidación del lote y se marcará como LIQUIDADO.',
                icon: 'question', showCancelButton: true,
                confirmButtonText: 'Sí, registrar', cancelButtonText: 'Cancelar', reverseButtons: true
            }).then(function (result) { if (result.isConfirmed) document.forms['liquidacionForm'].submit(); });
        });

        // Imprimir BORRADOR (no guarda): envía los datos actuales del form a imprimirBorrador en una
        // pestaña nueva, sin salir del formulario, para negociar con el cliente.
        $('#btnBorrador').on('click', function () {
            var form = document.forms['liquidacionForm'];
            var accionOriginal = form.getAttribute('action'), destinoOriginal = form.getAttribute('target');
            form.setAttribute('action', '${createLink(action: "imprimirBorrador")}');
            form.setAttribute('target', '_blank');
            form.submit();
            // Restaurar para que "Guardar" siga funcionando normalmente en la misma pestaña.
            form.setAttribute('action', accionOriginal);
            if (destinoOriginal) form.setAttribute('target', destinoOriginal); else form.removeAttribute('target');
        });

        // Keep-alive: mantiene viva la sesión durante la negociación (varios minutos) mientras el
        // formulario esté abierto, para que no expire antes de guardar. Ping cada 4 minutos.
        setInterval(function () {
            fetch('${createLink(action: "keepAlive")}', { credentials: 'same-origin', cache: 'no-store' }).catch(function () {});
        }, 4 * 60 * 1000);
    });
</script>
</body>
</html>
