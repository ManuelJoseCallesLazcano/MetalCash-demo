<div id="loginHeader" style="font-size: 14px; padding-top: 10px">
    <sec:ifAnyGranted roles="ROLE_RECEPCION,ROLE_CONTROL_CALIDAD,ROLE_LIQUIDACION,ROLE_CAJA,ROLE_ADMIN">
        %{--Conectado:--}%
        %{--<sec:loggedInUserInfo field="username"></sec:loggedInUserInfo>--}%
        %{--<g:link controller="logout" action="index">--}%
            %{--Cerrar Sesion--}%
        %{--</g:link>--}%

        <form name="submitForm" method="POST" action="${createLink(controller: 'logout')}">
            <input type="hidden" name="" value="">
            <div style="color: #000">
                Conectado:º
                <sec:loggedInUserInfo field="username"></sec:loggedInUserInfo>
                <a HREF="javascript:document.submitForm.submit()" style="color:#b81900;font-weight: bold;font-family: Helvetica">
                    Cerrar Sesion
                </a>

%{--                		<form name="submitForm" method="POST" action="${createLink(controller: 'logout')}">--}%
%{--                			<input type="hidden" name="" value="">--}%
%{--                			<div style="color: #ffffff">--}%
%{--                				Conectado:--}%
%{--                				<sec:loggedInUserInfo field="username"></sec:loggedInUserInfo>--}%
%{--                			</div>--}%
%{--                			<a HREF="javascript:document.submitForm.submit()" style="color:#b81900;font-weight: bold;font-family: Helvetica">--}%
%{--                				Cerrar Sesion--}%
%{--                			</a>--}%
%{--                			<input type="submit" value="logout">--}%
%{--                		</form>--}%
            </div>
            %{--<input type="submit" value="logout">--}%
        </form>
    </sec:ifAnyGranted>
</div>