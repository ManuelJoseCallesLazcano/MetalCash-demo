<html>
<head>
    <meta name="layout" content="main">
    <title>Nueva Alícuota</title>
</head>
<body>
<div class="card card-primary">
    <div class="card-header">
        <h3 class="card-title">Nueva Alícuota</h3>
    </div>
    <g:form url="[resource:alicuotaInstance, action:'save']">
        <div class="card-body">
            <div class="alert alert-info alert-dismissible" id="msg-anterior" style="display:none">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                Se recuperaron las últimas alícuotas registradas.
            </div>
            <g:hasErrors bean="${alicuotaInstance}">
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <ul class="mb-0">
                        <g:eachError bean="${alicuotaInstance}" var="error">
                            <li><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </div>
            </g:hasErrors>
            <g:render template="form"/>
        </div>
        <div class="card-footer">
            <g:submitButton name="create" class="btn btn-primary" value="Guardar"/>
            <g:link action="index" class="btn btn-secondary ml-1">Cancelar</g:link>
        </div>
    </g:form>
</div>
<script>
$(document).ready(function() {
    var za = $("#zincAnterior").val();
    if (za) {
        $("#zinc").val(za);
        $("#plomo").val($("#plomoAnterior").val());
        $("#plata").val($("#plataAnterior").val());
        $("#msg-anterior").show();
    }
});
</script>
</body>
</html>
