$(document).ready(function () {
    $(function() {
        $( "#radio2" ).buttonset();
    });

    $('input:radio[name="radio2"]').change(
        function(){
            if (this.checked && this.value == 'sulfuro') {
                $("#naturalezaMineral").val("SULFURO");
            }
            if (this.checked && this.value == 'oxido') {
                $("#naturalezaMineral").val("OXIDO");
            }
        }
    );

    establecerRadios();

    function establecerRadios(){
        var n = $("#naturalezaMineral").val();
        if(n=="SULFURO"){
            $('#radioSulfuro').prop('checked',true);
        }
        if(n=="OXIDO"){
            $('#radioOxido').prop('checked',true);
        }
    }

});
