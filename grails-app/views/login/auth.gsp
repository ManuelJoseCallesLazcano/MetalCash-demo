<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="login"/>
    <title>MetalCash - Lite — Acceso</title>
    <style>
        body.login-page {
            /* Degradado de grises acorde al tema AdminLTE (light-gray #f4f6f9 → gray-600 #6c757d) */
            background: linear-gradient(145deg, #f4f6f9 0%, #adb5bd 55%, #6c757d 100%);
            min-height: 100vh;
        }
        .login-box {
            width: 360px;
        }
        .login-logo a {
            color: #ffffff;
            font-size: 1.9rem;
            text-shadow: 0 2px 8px rgba(0,0,0,0.4);
        }
        .login-logo a:hover {
            color: #aed6f1;
            text-decoration: none;
        }
        .login-subtitle {
            text-align: center;
            color: rgba(255, 255, 255, 0.6);
            font-size: 0.78rem;
            text-transform: uppercase;
            letter-spacing: 0.12em;
            margin-top: 0.25rem;
            margin-bottom: 1.2rem;
        }
        .card.login-card {
            border: none;
            border-radius: 0.5rem;
            box-shadow: 0 10px 40px rgba(0,0,0,0.35);
        }
        .login-card-body {
            padding: 2rem 2.2rem;
        }
        .login-card-body .input-group-text {
            background-color: #f8f9fa;
            color: #6c757d;
        }
        .btn-login {
            background-color: #17a2b8;
            border-color: #17a2b8;
            font-weight: 600;
            letter-spacing: 0.04em;
            padding: 0.5rem;
        }
        .btn-login:hover {
            background-color: #138496;
            border-color: #117a8b;
        }
        .login-footer {
            text-align: center;
            color: rgba(255,255,255,0.4);
            font-size: 0.75rem;
            margin-top: 1.5rem;
        }
    </style>
</head>
<body>
<div class="login-box">

    <div class="login-logo">
        <img src="${assetPath(src: 'logo_metalcash_login.png')}" alt="MetalCash" class="img-fluid" style="max-height: 130px;"/>
    </div>
%{--    <div class="login-subtitle">Sistema de Gestión Minera</div>--}%

    <div class="card login-card">
        <div class="card-body login-card-body">

            <g:if test="${flash.message}">
                <div class="alert alert-danger d-flex align-items-center mb-3 py-2" role="alert">
                    <i class="fas fa-exclamation-circle mr-2"></i>
                    <span>${flash.message}</span>
                </div>
            </g:if>

            <form action="${request.contextPath}/login/authenticate" method="POST">
                <%
                    def csrfToken = request.getAttribute(org.springframework.security.web.csrf.CsrfToken.class.name)
                            ?: request.getAttribute('_csrf')
                %>
                <g:if test="${csrfToken}">
                    <input type="hidden" name="${csrfToken.parameterName}" value="${csrfToken.token}"/>
                </g:if>

                <div class="input-group mb-3">
                    <input type="text" name="username" id="username"
                        class="form-control" placeholder="Usuario" autofocus/>
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <i class="fas fa-user"></i>
                        </div>
                    </div>
                </div>

                <div class="input-group mb-4">
                    <input type="password" name="password" id="password"
                        class="form-control" placeholder="Contraseña"/>
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <i class="fas fa-lock"></i>
                        </div>
                    </div>
                </div>

                <button type="submit" class="btn btn-login btn-block text-white">
                    <i class="fas fa-sign-in-alt mr-1"></i> Iniciar Sesión
                </button>
            </form>

        </div>
    </div>

    <div class="login-footer">
        &copy; <g:formatDate date="${new Date()}" format="yyyy"/> &nbsp;&middot;&nbsp; Desarrollado por Ing. Manuel Calles
    </div>

</div>
</body>
</html>
