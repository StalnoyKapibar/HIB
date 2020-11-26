$(document).ready(function () {
    startCountOfOrder();
    startCountOfFeedback();
});

function startCountOfOrder() {
    fetch("/api/admin/order-count")
        .then(response => response.json())
        .then(function (data) {
            if (data !== 0 && data > 0) {
                $("#countOfOrder").html(`${data}`);
            } else {
                $("#countOfOrder").html(``);
            }
        });
}

function startCountOfFeedback() {
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

function totalNumberOfBooks() {
    fetch("/api/admin/book-count")
        .then(response => response.json())
        .then(function (data) {
            $("#countOfBooks").html(`${data}`);
        });
}