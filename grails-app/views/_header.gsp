<div id="loginHeader" style="font-size: 14px; padding-top: 10px">
    <sec:ifAnyGranted roles="ROLE_RECEPCION,ROLE_CONTROL_CALIDAD,ROLE_LIQUIDACION,ROLE_CAJA,ROLE_ADMIN">
        Conectado:
        <sec:loggedInUserInfo field="username"></sec:loggedInUserInfo>
        <g:link controller="logout" action="index">
            Cerrar Sesion
        </g:link>
    </sec:ifAnyGranted>
</div>