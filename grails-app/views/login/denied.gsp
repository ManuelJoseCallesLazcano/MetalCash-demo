<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="login"/>
    <title>Acceso denegado</title>
    <style>
        body.login-page {
            background: linear-gradient(145deg, #1a2332 0%, #2c3e50 55%, #1a5276 100%);
            min-height: 100vh;
        }
        .login-box { width: 400px; }
        .login-logo a {
            color: #ffffff;
            font-size: 1.9rem;
            text-shadow: 0 2px 8px rgba(0,0,0,0.4);
        }
        .login-logo a:hover { color: #aed6f1; text-decoration: none; }
        .login-subtitle {
            text-align: center;
            color: rgba(255,255,255,0.6);
            font-size: 0.78rem;
            text-transform: uppercase;
            letter-spacing: 0.12em;
            margin-top: 0.25rem;
            margin-bottom: 1.2rem;
        }
        .card { border: none; border-radius: 0.5rem; box-shadow: 0 10px 40px rgba(0,0,0,0.35); }
    </style>
</head>
<body>
<div class="login-box">
    <div class="login-logo">
        <a href="#"><i class="fas fa-gem mr-2"></i><b>SMART</b> Liquidaciones</a>
    </div>
    <div class="login-subtitle">Sistema de Gestión Minera</div>
    <div class="card">
        <div class="card-body p-4 text-center">
            <i class="fas fa-ban fa-3x text-danger mb-3"></i>
            <h5 class="font-weight-bold">Acceso Denegado</h5>
            <p class="text-muted mb-4"><g:message code="springSecurity.denied.message"/></p>
            <a href="${createLink(controller:'login', action:'auth')}" class="btn btn-secondary btn-sm">
                <i class="fas fa-sign-in-alt mr-1"></i> Volver al inicio de sesión
            </a>
        </div>
    </div>
</div>
</body>
</html>
