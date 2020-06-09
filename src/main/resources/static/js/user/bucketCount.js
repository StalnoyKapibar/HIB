$(document).ready(function () {
    showSizeCart();
    showOrderSize();
    if (currentLang === '') {
        if (getCookieByName("lang")) {
            currentLang = getCookieByName("lang");
        } else {
            currentLang = 'en';
        }
    }

    getLanguage();
    setLocaleFields();
})

function showSizeCart() {
    fetch("/cart/size")
        .then(response => response.text())
        .then(function (data) {
            if (data !== "0") {
                $("#bucketIn").html(`${data}`);
                $("#bucketIn1").html(`${data}`);
            } else {
                $("#bucketIn").html(``);
                $("#bucketIn1").html(``);
            }
        });
}

function showOrderSize() {
    fetch("/order/size")
        .then(response => response.text())
        .then(function (data) {
            if (data !== "0") {
                $("#orders-quantity").html(`${data}`);
                $("#orders-quantity1").html(`${data}`);
            } else {
                $("#orders-quantity").html(``);
                $("#orders-quantity1").html(``);
            }
        });
}