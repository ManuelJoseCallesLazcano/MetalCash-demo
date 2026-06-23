// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
//= require jquery
// require_tree eliminado: los archivos en subdirectorios (ej. recepcionDeComplejo/)
// son scripts de página específica que se cargan individualmente con asset:javascript.
// Incluirlos aquí los ejecutaría en TODAS las páginas: sus $(document).ready()
// lanzarían llamadas AJAX innecesarias y fallidas contra endpoints que no existen
// en el contexto de otras vistas.
//= require_self

if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}
