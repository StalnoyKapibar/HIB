$(document).ready(function () {
startCount();
});

function startCount() {
    fetch("/api/admin/order-count")
        .then(response => response.json())
        .then(function (data) {
            if (data !== 0 && data > 0) {
                $("#countOfOrder").html(`${data}`);
            } else {
                $("#countOfOrder").html(``);
            }
        });

    fetch("/api/admin/feedback-request-count")
        .then(response => response.json())
        .then(function (data) {
            if (data !== 0 && data > 0) {
                $("#countOfFeedback").html(`${data}`);
            } else {
                $("#countOfFeedback").html(``);
            }
        });
}