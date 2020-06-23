$(document).ready(function() {
    var s = document.getElementById("texts");
    s.append("We are processing your transaction. Please wait a few seconds. You will now be redirected to the order page.");
    setTimeout(function () {
        window.location.href = "/profile/orders";
    }, 3500);
});