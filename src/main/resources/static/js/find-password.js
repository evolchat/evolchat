 $(document).ready(function() {
    $('#id').on('input', function() {
        if ($(this).val().length > 0) {
            $('#login-button').addClass('login-bc-activate');
        } else {
            $('#login-button').removeClass('login-bc-activate');
        }
    });
});