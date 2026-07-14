<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><g:layoutTitle default="MetalCash - Lite"/></title>
    <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
    <link rel="icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <!-- AdminLTE 3 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/admin-lte@3.2.0/dist/css/adminlte.min.css">
    <!-- jQuery UI: hoja de estilos completa (misma versión que el JS) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.13.2/themes/base/jquery-ui.min.css">
    <!-- jQuery UI datepicker: ajustes AdminLTE (override del tema base) -->
    <style>
    .ui-datepicker{background:#fff!important;border:1px solid #dee2e6!important;border-radius:.35rem!important;padding:.5rem;z-index:9999!important;font-size:.875rem;width:auto}
    .ui-datepicker .ui-widget-header,
    .ui-datepicker .ui-datepicker-header{background:#343a40!important;color:#fff;border:0!important;border-radius:.25rem .25rem 0 0;padding:.25rem .5rem;margin:-.5rem -.5rem .5rem}
    .ui-datepicker .ui-datepicker-title{text-align:center;font-weight:600;color:#fff}
    .ui-datepicker .ui-datepicker-prev,.ui-datepicker .ui-datepicker-next{cursor:pointer;top:.25rem;height:1.4rem;width:1.4rem}
    .ui-datepicker .ui-datepicker-prev{left:.35rem}.ui-datepicker .ui-datepicker-next{right:.35rem}
    .ui-datepicker .ui-datepicker-prev span,.ui-datepicker .ui-datepicker-next span{filter:invert(1) brightness(2)}
    .ui-datepicker table{width:100%;margin:0}
    .ui-datepicker th{text-align:center;font-weight:600;padding:.2rem;color:#6c757d;font-size:.75rem;border:0}
    .ui-datepicker td{padding:.1rem;border:0}
    .ui-datepicker td a,.ui-datepicker td span,
    .ui-datepicker td a.ui-state-default,.ui-datepicker td span.ui-state-default{display:block;text-align:center;padding:.2rem .3rem;border:0!important;border-radius:.25rem!important;background:transparent!important;color:#495057!important;text-decoration:none;font-weight:400}
    .ui-datepicker td a.ui-state-default:hover{background:#e9ecef!important}
    .ui-datepicker td.ui-datepicker-today a{background:#17a2b8!important;color:#fff!important}
    .ui-datepicker td a.ui-state-active,.ui-datepicker td .ui-state-active{background:#007bff!important;color:#fff!important}
    .ui-datepicker-buttonpane button{font-size:.8rem}

    /* ── Tema de color corporativo (#2f7098) ─────────────────────────────── */
    /* Fondo del navbar superior */
    .main-header.navbar { background-color:#2f7098 !important; }
    /* Links dentro de las vistas de contenido (list, index, show); se excluyen los botones */
    .content-wrapper a:not(.btn) { color:#2f7098; }
    .content-wrapper a:not(.btn):hover { color:#245778; }
    /* Encabezados ordenables de las tablas (g:sortableColumn) */
    .content-wrapper th.sortable a, .content-wrapper a.sortAsc, .content-wrapper a.sortDesc { color:#2f7098; }
    /* Controles de paginación: enlaces y página actual (azules → #2f7098) */
    .pagination a, .pagination .page-link { color:#2f7098; }
    .pagination .currentStep, .page-item.active .page-link {
        background-color:#2f7098 !important; border-color:#2f7098 !important; color:#fff !important;
    }
    </style>

    <!-- jQuery (antes del layoutHead para que los scripts inline de vistas funcionen) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Bootstrap 4 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <!-- AdminLTE App -->
    <script src="https://cdn.jsdelivr.net/npm/admin-lte@3.2.0/dist/js/adminlte.min.js"></script>
    <!-- jQuery UI (datepicker) -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.13.2/jquery-ui.min.js"></script>
    <!-- jQuery UI datepicker i18n español -->
    <script src="${resource(dir: 'js/i18n', file: 'jquery.ui.datepicker-es.js')}"></script>

    <g:layoutHead/>
</head>
<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

    <!-- Navbar superior -->
    <nav class="main-header navbar navbar-expand navbar-dark navbar-primary">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" data-widget="pushmenu" href="#" role="button">
                    <i class="fas fa-bars"></i>
                </a>
            </li>
            <li class="nav-item d-none d-sm-inline-block">
                <a href="${createLink(controller:'dashboard', action:'index')}" class="nav-link font-weight-bold">
                    MetalCash - Lite | Minerales Complejos
                </a>
            </li>
        </ul>
        <cot:cotizacionesAdvertencia/>

        <ul class="navbar-nav ml-auto">
            <sec:ifAnyGranted roles="ROLE_RECEPCION,ROLE_CONTROL_CALIDAD,ROLE_LIQUIDACION,ROLE_CAJA,ROLE_ADMIN">
                <li class="nav-item">
                    <span class="nav-link">
                        <i class="fas fa-user-circle mr-1"></i>
                        <sec:loggedInUserInfo field="username"/>
                    </span>
                </li>
                <li class="nav-item">
                    <form name="logoutForm" method="POST" action="${createLink(controller:'logout')}">
                        <input type="hidden" name="" value="">
                        <a href="javascript:document.logoutForm.submit()" class="nav-link">
                            <i class="fas fa-sign-out-alt mr-1"></i>Salir
                        </a>
                    </form>
                </li>
            </sec:ifAnyGranted>
        </ul>
    </nav>

    <!-- Sidebar -->
    <aside class="main-sidebar sidebar-dark-primary elevation-4">
        <style>
            /* Logo completo cuando el menú muestra etiquetas; logo mínimo cuando solo íconos */
            #logo_sidebar .brand-logo-min  { display: none; }
            .sidebar-collapse #logo_sidebar .brand-logo-full { display: none; }
            .sidebar-collapse #logo_sidebar .brand-logo-min  { display: inline-block; }
            /* Al expandir el sidebar por hover (sidebar-mini), volver al logo completo */
            .sidebar-mini.sidebar-collapse .main-sidebar:hover #logo_sidebar .brand-logo-full { display: inline-block; }
            .sidebar-mini.sidebar-collapse .main-sidebar:hover #logo_sidebar .brand-logo-min  { display: none; }
        </style>
        <a id="logo_sidebar" href="${createLink(controller:'dashboard', action:'index')}" class="brand-link text-center">
            <asset:image src="logo_metalcash_menu.png" alt="MetalCash" class="img-fluid brand-logo-full" style="max-height:38px;"/>
            <asset:image src="logo_metalcash_menu_min.png" alt="MetalCash" class="img-fluid brand-logo-min" style="max-height:33px;"/>
        </a>

        <div class="sidebar">
%{--            <div class="user-panel mt-3 pb-3 mb-3 d-flex">--}%
%{--                <div class="image">--}%
%{--                    <i class="fas fa-user-circle fa-2x text-white ml-1"></i>--}%
%{--                </div>--}%
%{--                <div class="info">--}%
%{--                    <sec:loggedInUserInfo field="username" className="d-block text-white"/>--}%
%{--                </div>--}%
%{--            </div>--}%

            <nav class="mt-2">
                <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                    <g:render template="/layouts/menu"/>
                </ul>
            </nav>
        </div>
    </aside>

    <!-- Content Wrapper -->
    <div class="content-wrapper">
        <section class="content-header">
            <div class="container-fluid">
                <div class="row mb-2">
                    <div class="col-sm-6">
%{--                        <h1 class="m-0"><g:layoutTitle default=""/></h1>--}%
                    </div>
                    <div class="col-sm-6">
                        <g:render template="/layouts/footer"/>
                    </div>
                </div>
            </div>
        </section>

        <section class="content">
            <div class="container-fluid">
                <g:layoutBody/>
            </div>
        </section>
    </div>

    <!-- Footer -->
    <footer class="main-footer text-sm">
        <strong>MetalCash - Lite</strong> &copy; <g:formatDate date="${new Date()}" format="yyyy"/>
        <div class="float-right d-none d-sm-inline-block">
            <b>Desarrollado por</b> <span style="color: #2b2f3a; font-weight: bold">Metal</span><span style="color: #2f7098; font-weight: bold">Core</span>
        </div>
    </footer>

</div><!-- ./wrapper -->

<script>
document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll(
        'input[type="number"]:not([readonly]):not([disabled]):not([inputmode])'
    ).forEach(function (el) {
        el.setAttribute('inputmode', 'decimal');
    });
});
</script>
</body>
</html>
