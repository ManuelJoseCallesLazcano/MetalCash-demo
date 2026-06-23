<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<title>Sistema de Compra de Minerales</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="description" content="UI Elements: Large Drop Down Menu" />
	<meta name="keywords" content="jquery, drop down, menu, navigation, large, mega "/>
	<link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
%{--	<link rel="stylesheet" href="css/main.css">--}%
%{--	<link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>--}%
	<link rel="stylesheet" href="css/dropdownMenu.css">
	<style>
	body{
		background-image: url("images/1.jpg");
		background-size: cover;
		font: 13px 'trebuchet MS', Arial, Helvetica;
	}
	#menu {
		width: 100%;
		height: auto;
	}
	</style>
	<script type='text/javascript' src='js/jquery-1.10.1.min.js'></script>
	<script type='text/javascript' src='js/jquery-ui-1.10.3.custom.min.js'></script>
	<script type='text/javascript' src='js/dropdownMenu.js'></script>
	<script type="text/javascript">
		$(document).ready(function() {
			recuperarCotizacionDiaria()

			function recuperarCotizacionDiaria(){
				let hoy = new Date();
				$.ajax({
					url: "/demo-liquidaciones/cotizacionDiariaDeMinerales/obtenerCotizaciones",
					data: {
						day: hoy.getDate(),
						month: hoy.getMonth()+1,
						year: hoy.getFullYear()
					},
					success: function(data){
						console.log("data.hayCotizacionQuincenal", data.hayCotizacionQuincenal)
						if(data.hayCotizacionQuincenal!==1) {
							console.log('desplegar dialog');
							$( "#dialog-message" ).dialog({
								modal: true,
								buttons: {
									// Cancelar: function() {
									// 	$( this ).dialog( "close" );
									// },
									Ok: function() {
										$( this ).dialog( "close" );
										let pathname = window.location.pathname;
										window.location.href=`${pathname}/demo-liquidaciones/cotizacionQuincenalDeMinerales/create`;
									}
								}
							});
							//$( "#dialog-message" ).dialog( "open" );
						}
					},
					error: function(){
					}
				});
			}
		});
	</script>
</head>
<body class="bg_img">
%{--<div style="background-color: #72BDA3; width: 100%; height: 100px; position: absolute;margin-top: 0px;">--}%
%{--	<img src="images/grails_logo.png" alt="" style="padding: 0.5% 20%">--}%
%{--</div>--}%
%{--<div style="position: absolute; margin-top: 100px; width: 100%">--}%
%{--	<g:render template="/layouts/menu" />--}%
%{--</div>--}%

<div style="background-color: #72BDA3; width: 100%; height: 80px; position: relative;margin-top: 0px;">
	<img src="images/grails_logo.png" alt="" style="padding: 0.5% 20%">
%{--	<div style="background-color: #72BDA3; position: absolute; right: 50px; top: 30px;">--}%
%{--		<form name="submitForm" method="POST" action="${createLink(controller: 'logout')}">--}%
%{--			<input type="hidden" name="" value="">--}%
%{--			<div style="color: #ffffff">--}%
%{--				Conectado:--}%
%{--				<sec:loggedInUserInfo field="username"></sec:loggedInUserInfo>--}%
%{--			</div>--}%
%{--			<a HREF="javascript:document.submitForm.submit()" style="color:#b81900;font-weight: bold;font-family: Helvetica">--}%
%{--				Cerrar Sesion--}%
%{--			</a>--}%
%{--			--}%%{--<input type="submit" value="logout">--}%
%{--		</form>--}%
%{--	</div>--}%
</div>

<g:render template="/layouts/menu" />

<div id="dialog-message" title="Cotización Quincenal" style="display: none">
	<p>
		<span class="ui-icon ui-icon-closethick" style="float:left; margin:0 7px 50px 0;"></span>
		No se encontró registro de la <span style="font-weight: bold">Cotización Quincenal</span> para la quincena actual. Se le enviará al formulario de registro de cotizaciones.
	</p>
</div>

<span id="hayCotizacionDiaria"></span>
<span id="hayCotizacionQuincenal"></span>
<span id="hayAlicuota"></span>

</body>
</html>
