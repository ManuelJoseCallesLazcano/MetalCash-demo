<%-- Ícono de advertencia (navbar) + modal de cotizaciones desactualizadas.
     Modelo: cotizacionesVencidas (lista de [nombre, fecha, controlador]), hoy (Date). --%>
<ul class="navbar-nav" id="advertencia">
    <g:if test="${cotizacionesVencidas}">
        <li class="nav-item">
            <a class="nav-link" href="#" data-toggle="modal" data-target="#modalCotizaciones" title="Cotizaciones desactualizadas">
                <i class="fas fa-exclamation-triangle text-warning"></i>
                <span class="badge badge-warning navbar-badge">${cotizacionesVencidas.size()}</span>
            </a>
        </li>
    </g:if>
</ul>

<g:if test="${cotizacionesVencidas}">
    <div class="modal fade" id="modalCotizaciones" tabindex="-1" role="dialog" aria-labelledby="modalCotizacionesLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header bg-warning">
                    <h5 class="modal-title" id="modalCotizacionesLabel"><i class="fas fa-exclamation-triangle mr-2"></i>Cotizaciones desactualizadas</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Cerrar"><span aria-hidden="true">&times;</span></button>
                </div>
                <div class="modal-body">
                    <p class="mb-2 small">Las siguientes cotizaciones no corresponden a la fecha actual (<g:formatDate date="${hoy}" format="dd/MM/yyyy"/>). Actualícelas para que las liquidaciones usen valores vigentes:</p>
                    <ul class="mb-0">
                        <g:each in="${cotizacionesVencidas}" var="cot">
                            <li>
                                <g:link controller="${cot.controlador}" action="create">${cot.nombre}</g:link>
                                <span class="text-muted small">
                                    <g:if test="${cot.fecha}">— última: <g:formatDate date="${cot.fecha}" format="dd/MM/yyyy"/></g:if>
                                    <g:else>— sin registros</g:else>
                                </span>
                            </li>
                        </g:each>
                    </ul>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Entendido</button>
                </div>
            </div>
        </div>
    </div>
    <script>
        // El modal se renderiza dentro del navbar; se mueve al <body> para que el backdrop no lo
        // tape (el navbar crea su propio contexto de apilamiento y dejaría el diálogo detrás).
        (function () {
            var m = document.getElementById('modalCotizaciones');
            if (m && m.parentNode !== document.body) document.body.appendChild(m);
        })();
    </script>
</g:if>
