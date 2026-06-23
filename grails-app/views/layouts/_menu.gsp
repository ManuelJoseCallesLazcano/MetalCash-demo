<sec:ifAnyGranted roles="ROLE_RECEPCION,ROLE_CONTROL_CALIDAD,ROLE_LIQUIDACION,ROLE_CAJA,ROLE_REPORTES,ROLE_ADMIN">

    <%-- PROCESO --%>
    <sec:ifAnyGranted roles="ROLE_RECEPCION,ROLE_CONTROL_CALIDAD,ROLE_LIQUIDACION,ROLE_CAJA,ROLE_ADMIN">
    <li class="nav-item has-treeview">
        <a href="#" class="nav-link">
            <i class="nav-icon fas fa-industry"></i>
            <p>PROCESO <i class="right fas fa-angle-left"></i></p>
        </a>
        <ul class="nav nav-treeview">
            <sec:ifAnyGranted roles="ROLE_RECEPCION,ROLE_ADMIN">
                <li class="nav-item">
                    <g:link controller="recepcionDeComplejo" action="list" class="nav-link">
                        <i class="far fa-circle nav-icon"></i><p>Recepción</p>
                    </g:link>
                </li>
            </sec:ifAnyGranted>
            <sec:ifAnyGranted roles="ROLE_CONTROL_CALIDAD,ROLE_LIQUIDACION,ROLE_ADMIN">
                <li class="nav-item">
                    <g:link controller="controlCalidadComplejo" action="list" class="nav-link">
                        <i class="far fa-circle nav-icon"></i><p>Análisis de Laboratorio</p>
                    </g:link>
                </li>
            </sec:ifAnyGranted>
            <sec:ifAnyGranted roles="ROLE_LIQUIDACION,ROLE_ADMIN">
                <li class="nav-item">
                    <g:link controller="liquidacionDeComplejo" action="list" class="nav-link">
                        <i class="far fa-circle nav-icon"></i><p>Liquidación</p>
                    </g:link>
                </li>
            </sec:ifAnyGranted>
        </ul>
    </li>
    </sec:ifAnyGranted>

    <%-- ANTICIPOS / PAGOS --%>
    <sec:ifAnyGranted roles="ROLE_LIQUIDACION,ROLE_ADMIN">
    <li class="nav-item has-treeview">
        <a href="#" class="nav-link">
            <i class="nav-icon fas fa-hand-holding-usd"></i>
            <p>ANTICIPOS <i class="right fas fa-angle-left"></i></p>
        </a>
        <ul class="nav nav-treeview">
            <li class="nav-item">
                <g:link controller="anticipo" action="list" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Anticipo c/Entrega</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="anticipoContraFuturaEntrega" action="list" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Anticipo c/Futura Entrega</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="amortizacion" action="list" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Amortización</p>
                </g:link>
            </li>
        </ul>
    </li>
    </sec:ifAnyGranted>

    <sec:ifAnyGranted roles="ROLE_LIQUIDACION,ROLE_ADMIN">
        <li class="nav-item has-treeview">
            <a href="#" class="nav-link">
                <i class="nav-icon fas fa-truck"></i>
                <p>TRANSPORTE <i class="right fas fa-angle-left"></i></p>
            </a>
            <ul class="nav nav-treeview">
                <li class="nav-item">
                    <g:link controller="pagoTransporte" action="list" class="nav-link">
                        <i class="far fa-circle nav-icon"></i><p>Pago por Transporte</p>
                    </g:link>
                </li>
                <li class="nav-item">
                    <g:link controller="anticipoPorTransporte" action="list" class="nav-link">
                        <i class="far fa-circle nav-icon"></i><p>Anticipo c/Transporte</p>
                    </g:link>
                </li>
            </ul>
        </li>
    </sec:ifAnyGranted>

    <%-- PROVEEDORES --%>
    <sec:ifAnyGranted roles="ROLE_RECEPCION,ROLE_LIQUIDACION,ROLE_ADMIN">
    <li class="nav-item has-treeview">
        <a href="#" class="nav-link">
            <i class="nav-icon fas fa-users"></i>
            <p>PROVEEDORES <i class="right fas fa-angle-left"></i></p>
        </a>
        <ul class="nav nav-treeview">
            <li class="nav-item">
                <g:link controller="empresa" action="list" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Empresas</p>
                </g:link>
            </li>
%{--            <li class="nav-item">--}%
%{--                <g:link controller="empresaSeccion" action="index" class="nav-link">--}%
%{--                    <i class="far fa-circle nav-icon"></i><p>Secciones</p>--}%
%{--                </g:link>--}%
%{--            </li>--}%
            <li class="nav-item">
                <g:link controller="cliente" action="list" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Clientes</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="chofer" action="list" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Choferes</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="automovil" action="list" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Automóviles</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="municipio" action="index" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Municipios</p>
                </g:link>
            </li>
        </ul>
    </li>
    </sec:ifAnyGranted>

<%-- RETENCIONES --%>
    <sec:ifAnyGranted roles="ROLE_RECEPCION,ROLE_LIQUIDACION,ROLE_ADMIN">
        <li class="nav-item has-treeview">
            <a href="#" class="nav-link">
                <i class="nav-icon fas fa-percentage"></i>
                <p>RETENCIONES <i class="right fas fa-angle-left"></i></p>
            </a>
            <ul class="nav nav-treeview">
                <li class="nav-item">
                    <g:link controller="retencion" action="list" class="nav-link">
                        <i class="far fa-circle nav-icon"></i><p>Retenciones</p>
                    </g:link>
                </li>
            </ul>
        </li>
    </sec:ifAnyGranted>

    <%-- PRECIOS / COTIZACIONES --%>
    <sec:ifAnyGranted roles="ROLE_LIQUIDACION,ROLE_ADMIN">
    <li class="nav-item has-treeview">
        <a href="#" class="nav-link">
            <i class="nav-icon fas fa-tags"></i>
            <p>PRECIOS <i class="right fas fa-angle-left"></i></p>
        </a>
        <ul class="nav nav-treeview">
            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <li class="nav-item">
                    <g:link controller="tablaOrigenCotizacionesComplejo" action="list" class="nav-link">
                        <i class="far fa-circle nav-icon"></i><p>Tablas Complejo</p>
                    </g:link>
                </li>
                <li class="nav-item">
                    <g:link controller="terminosDeContrato" action="list" class="nav-link">
                        <i class="far fa-circle nav-icon"></i><p>Términos de Contrato</p>
                    </g:link>
                </li>
            </sec:ifAnyGranted>
        </ul>
    </li>
    </sec:ifAnyGranted>

<%-- COTIZACIONES --%>
    <sec:ifAnyGranted roles="ROLE_LIQUIDACION,ROLE_ADMIN">
        <li class="nav-item has-treeview">
            <a href="#" class="nav-link">
                <i class="nav-icon fas fa-chart-line"></i>
                <p>COTIZACIONES <i class="right fas fa-angle-left"></i></p>
            </a>
            <ul class="nav nav-treeview">
                <li class="nav-item">
                    <g:link controller="cotizacionDiariaDeMinerales" action="index" class="nav-link">
                        <i class="far fa-circle nav-icon"></i><p>Cotización Diaria</p>
                    </g:link>
                </li>
                <li class="nav-item">
                    <g:link controller="cotizacionQuincenalDeMinerales" action="index" class="nav-link">
                        <i class="far fa-circle nav-icon"></i><p>Cotización Quincenal</p>
                    </g:link>
                </li>
                <li class="nav-item">
                    <g:link controller="alicuota" action="index" class="nav-link">
                        <i class="far fa-circle nav-icon"></i><p>Alícuotas</p>
                    </g:link>
                </li>
                <li class="nav-item">
                    <g:link controller="cotizacionDeDolar" action="list" class="nav-link">
                        <i class="far fa-circle nav-icon"></i><p>Dólar Americano</p>
                    </g:link>
                </li>
            </ul>
        </li>
    </sec:ifAnyGranted>

    <%-- COMPÓSITOS --%>
    <sec:ifAnyGranted roles="ROLE_RECEPCION,ROLE_LIQUIDACION,ROLE_ADMIN">
    <li class="nav-item has-treeview">
        <a href="#" class="nav-link">
            <i class="nav-icon fas fa-layer-group"></i>
            <p>COMPÓSITOS <i class="right fas fa-angle-left"></i></p>
        </a>
        <ul class="nav nav-treeview">
            <li class="nav-item">
                <g:link controller="reporteCompositoDeLotes" action="list" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Compósito de Lotes</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="comprador" action="index" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Compradores</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="ingenio" action="index" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Ingenios</p>
                </g:link>
            </li>
        </ul>
    </li>
    </sec:ifAnyGranted>

    <%-- REPORTES --%>
    <sec:ifAnyGranted roles="ROLE_LIQUIDACION,ROLE_ADMIN,ROLE_REPORTES">
    <li class="nav-item has-treeview">
        <a href="#" class="nav-link">
            <i class="nav-icon fas fa-file-alt"></i>
            <p>REPORTES <i class="right fas fa-angle-left"></i></p>
        </a>
        <ul class="nav nav-treeview">
            <li class="nav-item">
                <g:link controller="reporteLotesRecepcionados" action="create" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Lotes Recepcionados</p>
                </g:link>
            </li>
%{--            <li class="nav-item">--}%
%{--                <g:link controller="reporteLotesAnalisis" action="create" class="nav-link">--}%
%{--                    <i class="far fa-circle nav-icon"></i><p>Lotes y Análisis</p>--}%
%{--                </g:link>--}%
%{--            </li>--}%
            <li class="nav-item">
                <g:link controller="reporteLotesLiquidados" action="create" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Lotes Liquidados</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="reporteLotesCompositos" action="create" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Lotes y Compósitos</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="planillaDeLiquidacion" action="create" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Planilla Liquidación</p>
                </g:link>
            </li>
%{--            <li class="nav-item">--}%
%{--                <g:link controller="detalleCompras" action="create" class="nav-link">--}%
%{--                    <i class="far fa-circle nav-icon"></i><p>Detalle de Compras</p>--}%
%{--                </g:link>--}%
%{--            </li>--}%
%{--            <li class="nav-item">--}%
%{--                <g:link controller="detalleCanjeTornaguias" action="create" class="nav-link">--}%
%{--                    <i class="far fa-circle nav-icon"></i><p>Canje Tornaguías</p>--}%
%{--                </g:link>--}%
%{--            </li>--}%
            <li class="nav-item">
                <g:link controller="libroRegaliasMineras" action="create" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Libro RM Compras</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="reporteAnticipos" action="create" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Anticipos c/Entrega</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="reportePagoDeTransporte" action="create" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Pago de Transporte</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="reporteRetenciones" action="create" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Retenciones</p>
                </g:link>
            </li>
%{--            <li class="nav-item">--}%
%{--                <g:link controller="reporteGraficoCantidad" action="create" class="nav-link">--}%
%{--                    <i class="far fa-circle nav-icon"></i><p>Graf. Acum. Empresa</p>--}%
%{--                </g:link>--}%
%{--            </li>--}%
%{--            <li class="nav-item">--}%
%{--                <g:link controller="reporteGraficoAcumuladoEmpresaCliente" action="create" class="nav-link">--}%
%{--                    <i class="far fa-circle nav-icon"></i><p>Graf. Acum. Emp/Cliente</p>--}%
%{--                </g:link>--}%
%{--            </li>--}%
            <li class="nav-item">
                <g:link controller="reporteEstadoCuentaCliente" action="create" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Estado Cuenta Cliente</p>
                </g:link>
            </li>
        </ul>
    </li>
    </sec:ifAnyGranted>

    <%-- CONFIGURACIÓN --%>
    <sec:ifAnyGranted roles="ROLE_ADMIN">
    <li class="nav-item has-treeview">
        <a href="#" class="nav-link">
            <i class="nav-icon fas fa-warehouse"></i>
            <p>CONFIGURACIÓN <i class="right fas fa-angle-left"></i></p>
        </a>
        <ul class="nav nav-treeview">
            <li class="nav-item">
                <g:link controller="deposito" action="list" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Depósitos</p>
                </g:link>
            </li>
            <li class="nav-item">
                <g:link controller="gestionMinera" action="index" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Gestión Minera</p>
                </g:link>
            </li>
        </ul>
    </li>
    </sec:ifAnyGranted>

    <%-- UTILIDADES --%>
    <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_LIQUIDACION">
    <li class="nav-item has-treeview">
        <a href="#" class="nav-link">
            <i class="nav-icon fas fa-cogs"></i>
            <p>UTILITARIOS <i class="right fas fa-angle-left"></i></p>
        </a>
        <ul class="nav nav-treeview">
            <li class="nav-item">
                <g:link controller="buscadorLotes" action="create" class="nav-link">
                    <i class="far fa-circle nav-icon"></i><p>Buscador de Lotes</p>
                </g:link>
            </li>
            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <li class="nav-item">
                    <g:link controller="user" class="nav-link" target="_blank">
                        <i class="far fa-circle nav-icon"></i><p>Usuarios</p>
                    </g:link>
                </li>
            </sec:ifAnyGranted>
        </ul>
    </li>
    </sec:ifAnyGranted>

</sec:ifAnyGranted>
