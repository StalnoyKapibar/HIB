$(document).ready(function() {
    var s = document.getElementById("text");
    s.append("We have sent an email to activate your account. Please activate your account.\n" +
        "You will now be redirected to the main page.");
    setTimeout(function () {
        window.location.href = "home"; //will redirect to your blog page (an ex: blog.html)
    }, 3000);
});