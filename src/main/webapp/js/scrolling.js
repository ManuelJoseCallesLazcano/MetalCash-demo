$(function() {
    $('#nav_up').fadeIn('slow');
    $('#nav_down').fadeIn('slow');

    $('#nav_down').click(
        function (e) {
            $('html, body').animate({scrollTop: $(document).height()}, 500);
        }
    );
    $('#nav_up').click(
        function (e) {
            $('html, body').animate({scrollTop: '0px'}, 500);
        }
    );
});
