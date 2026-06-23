$(document).ready(function() {

    // Estado de la tabla de retenciones
    var retencionesData = [];
    var filaSeleccionada = -1;

    // ── Select2 en el campo Municipio ───────────────────────────────────────
    $("#municipio").select2({
        placeholder: 'Seleccione municipio…',
        language: 'es',
        width: '100%'
    });

    // Poblar municipios según el departamento seleccionado al cargar
    recuperarMunicipiosDepartamento();

    $("#departamento").change(function() {
        recuperarMunicipiosDepartamento();
    });

    $("#municipio").change(function() {
        codigoMunicipio();
    });

    // ── Select2 en la lista de Retención + recuperación de datos ─────────────
    if ($("#lista_retencion").length) {
        $("#lista_retencion").select2({
            placeholder: 'Seleccione retención…',
            language: 'es',
            width: '100%'
        });

        // Poblar los textfields con la retención seleccionada al cargar
        recuperarRetencion();

        $("#lista_retencion").change(function() {
            recuperarRetencion();
        });

        // ── Tabla de retenciones (AdminLTE/Bootstrap) ────────────────────────
        cargarRetencionesIniciales();

        $("#agregar").on("click", agregarRetencion);
        $("#actualizar").on("click", actualizarRetencion);
        $("#quitar").on("click", quitarRetencion);

        // Selección de fila (cargar al formulario) y eliminación directa
        $("#tablaRetencionesBody").on("click", "tr", function() {
            var idx = $(this).data("index");
            if (idx === undefined) return;
            seleccionarFila(idx);
        });
        $("#tablaRetencionesBody").on("click", ".btn-eliminar-fila", function(e) {
            e.stopPropagation();
            eliminarFila($(this).closest("tr").data("index"));
        });
    }

    function recuperarRetencion(){
        $.ajax({
            url: "/demo-liquidaciones/retencion/mostrar",
            dataType: 'json',
            data: {
                retencionId: $("#lista_retencion").val()
            },
            success: function(data){
                $("#descripcion").val(data.descripcion);
                $("#tipoDeRetencion").val(data.tipoDeRetencion);
                $("#cantidad").val(data.cantidadDescuento);
                $("#unidadDeDescuento").val(data.unidadDeDescuento);
                $("#asignacion").val(data.asignacionDelDescuento);
            },
            error: function(){
            }
        });
    }

    function recuperarMunicipiosDepartamento(){
        $.ajax({
            url: "/demo-liquidaciones/municipio/municipiosDepartamento",
            data: {
                empresaId: $("#empresaId").val()===""?0:$("#empresaId").val(),
                departamento: $("#departamento").val()
            },
            success: function(html){
                // El endpoint devuelve un <select> completo: extraemos sus <option>
                var $respuesta = $(html);
                var seleccionado = $respuesta.val();
                $("#municipio").html($respuesta.html());
                if (seleccionado) $("#municipio").val(seleccionado);
                // Refresca la vista de Select2 y dispara codigoMunicipio()
                $("#municipio").trigger("change");
            },
            error: function(){
            }
        });
    }

    function codigoMunicipio(){
        let municipio = $("#municipio option:selected").text().trim();
        $("#codigoMunicipio").val(municipio.substring(0,5));
    }

    // ── Funciones de la tabla de retenciones ─────────────────────────────────
    function cargarRetencionesIniciales(){
        var raw = $("#retenciones").val();
        try {
            retencionesData = raw ? JSON.parse(raw) : [];
        } catch (e) {
            retencionesData = [];
        }
        filaSeleccionada = -1;
        renderTablaRetenciones();
    }

    function renderTablaRetenciones(){
        var $body = $("#tablaRetencionesBody").empty();

        if (!retencionesData.length){
            $body.append('<tr><td colspan="6" class="text-center text-muted py-3">No hay retenciones agregadas.</td></tr>');
            sincronizarRetenciones();
            return;
        }

        retencionesData.forEach(function(r, i){
            var $tr = $('<tr>')
                .attr('data-index', i)
                .css('cursor', 'pointer');
            if (i === filaSeleccionada) $tr.addClass('table-active');

            $tr.append($('<td>').text(r.DESCRIPCION));
            $tr.append($('<td>').html(badgeTipo(r.TIPO)));
            $tr.append($('<td class="text-right">').text(r.CANTIDAD));
            $tr.append($('<td>').text(r.UNIDAD));
            $tr.append($('<td>').text(r.ASIGNACION));
            $tr.append($('<td class="text-right">').html(
                '<button type="button" class="btn btn-danger btn-xs btn-eliminar-fila" title="Eliminar">' +
                '<i class="fas fa-trash"></i></button>'
            ));
            $body.append($tr);
        });

        sincronizarRetenciones();
    }

    function badgeTipo(tipo){
        var clase = ((tipo || '').toUpperCase() === 'DE LEY') ? 'danger' : 'secondary';
        return '<span class="badge badge-' + clase + '">' + $('<div>').text(tipo || '').html() + '</span>';
    }

    function leerRetencionFormulario(){
        return {
            CODIGO:      $("#lista_retencion").val(),
            DESCRIPCION: $("#descripcion").val(),
            TIPO:        $("#tipoDeRetencion").val(),
            CANTIDAD:    $("#cantidad").val(),
            UNIDAD:      $("#unidadDeDescuento").val(),
            ASIGNACION:  $("#asignacion").val()
        };
    }

    function seleccionarFila(idx){
        filaSeleccionada = idx;
        var r = retencionesData[idx];
        $("#lista_retencion").val(r.CODIGO).trigger('change.select2');
        $("#descripcion").val(r.DESCRIPCION);
        $("#tipoDeRetencion").val(r.TIPO);
        $("#cantidad").val(r.CANTIDAD);
        $("#unidadDeDescuento").val(r.UNIDAD);
        $("#asignacion").val(r.ASIGNACION);
        renderTablaRetenciones();
    }

    function agregarRetencion(){
        var fila = leerRetencionFormulario();
        if (!fila.CODIGO){
            avisoRetencion('Seleccione una retención.', 'warning');
            return;
        }
        if (existeCodigo(fila.CODIGO, -1)){
            avisoRetencion('La retención seleccionada ya está en la lista.', 'warning');
            return;
        }
        retencionesData.push(fila);
        filaSeleccionada = -1;
        renderTablaRetenciones();
    }

    function actualizarRetencion(){
        if (filaSeleccionada < 0){
            avisoRetencion('Seleccione una fila de la tabla para actualizar.', 'warning');
            return;
        }
        var fila = leerRetencionFormulario();
        if (existeCodigo(fila.CODIGO, filaSeleccionada)){
            avisoRetencion('Otra fila ya tiene esa retención.', 'warning');
            return;
        }
        retencionesData[filaSeleccionada] = fila;
        renderTablaRetenciones();
    }

    function quitarRetencion(){
        if (filaSeleccionada < 0){
            avisoRetencion('Seleccione una fila de la tabla para eliminar.', 'warning');
            return;
        }
        eliminarFila(filaSeleccionada);
    }

    function eliminarFila(idx){
        if (idx === undefined || idx < 0) return;
        retencionesData.splice(idx, 1);
        filaSeleccionada = -1;
        renderTablaRetenciones();
    }

    function existeCodigo(codigo, exceptoIdx){
        for (var i = 0; i < retencionesData.length; i++){
            if (i !== exceptoIdx && String(retencionesData[i].CODIGO) === String(codigo)) return true;
        }
        return false;
    }

    function sincronizarRetenciones(){
        $("#retenciones").val(JSON.stringify(retencionesData));
    }

    function avisoRetencion(mensaje, icono){
        if (typeof Swal !== 'undefined'){
            Swal.fire({
                toast: true,
                position: 'top-end',
                timer: 3000,
                showConfirmButton: false,
                icon: icono || 'info',
                title: mensaje
            });
        } else {
            alert(mensaje);
        }
    }
});
