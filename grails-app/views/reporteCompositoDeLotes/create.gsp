<%@ page import="org.socymet.org.socymet.reportes.ReporteCompositoDeLotes" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Nuevo Compósito de Lotes</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" type="text/css">
    <style>
        .select2-container--default .select2-selection--single { height: calc(1.5em + .75rem + 2px); padding: .375rem .75rem; border: 1px solid #ced4da; border-radius: .25rem; }
        .select2-container--default .select2-selection--single .select2-selection__rendered { padding: 0; line-height: 1.5; }
        .amarillo { background-color: #fff8e1; }
        .tabla-lotes { font-size: .8rem; }
        .tabla-lotes th { white-space: nowrap; }
        .tabla-scroll { max-height: 55vh; overflow-y: auto; }
        .tabla-scroll thead th { position: sticky; top: 0; z-index: 2; }
        .resumen-box { border:1px solid #dee2e6; border-radius:6px; padding:10px 14px; text-align:center; background:#f8f9fa; }
        .resumen-box .valor { font-size:1.15rem; font-weight:700; color:#2c3e50; }
        .resumen-box .rotulo { font-size:.7rem; text-transform:uppercase; letter-spacing:.05em; color:#6c757d; }
        .btn-xs { padding:.1rem .35rem; font-size:.7rem; line-height:1; }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
    <asset:javascript src="composito/conjuntoComposito.js"/>
    <script>
        window.COMPOSITO_URLS = {
            disponibles: '${createLink(action: "lotesDisponiblesJSON")}',
            empresa: '${createLink(controller: "empresa", action: "empresaBusquedaJSON")}'
        };
    </script>
</head>
<body>
<g:render template="form" model="[reporteCompositoDeLotesInstance: reporteCompositoDeLotesInstance, formAction: 'save', preseleccionJson: '[]']"/>
</body>
</html>
