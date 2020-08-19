$(document).ready(function () {
    let url = window.location.href;
    if (url.indexOf('localhost') > -1) {
        document.getElementById("gmail-access").href = gmailAccessUrlloc.fullUrl;
    } else {
        document.getElementById("gmail-access").href = gmailAccessUrlserv.fullUrl;
    }
});


/*
$(document).ready(function () {
    if (document.location.indexOf('localhost') > -1) {
        $(document).ready(function () {
            document.getElementById("gmail-access").href = gmailAccessUrlloc.fullUrl;

        });
    } else {
        $(document).ready(function () {
            document.getElementById("gmail-access").href = gmailAccessUrlserv.fullUrl;
        });
    }
});

 */
