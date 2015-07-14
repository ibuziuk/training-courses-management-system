$(document).ready(function() {
    $("#logout").click(function() {
        $.post("logout", function(data) {
            location.reload();
        });
    });
});