var currentLang = '';
var bottom = '';

$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
});

function status(response) {
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}

function json(response) {
    return response.json()
}

function text(response) {
    return response.text()
}

function sendEmail() {
    let emailPass = $('#emailForgotPassword').val();
    if (emailPass === '') {
        showError('Invalid format email!');
        setTimeout(hideError, 5000);
    } else {
        fetch("/sendResetPassword", {
            method: 'POST',
            body: emailPass,
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        }).then(status)
            .then(text)
            .then(function (resp) {

                if (resp === "invalid format email") {
                    showError('Invalid format email!', 'invalidEmailFormat');
                    setTimeout(hideError, 5000);
                } else {
                    if (resp === "noEmail") {
                        showError('This email address is not registered!', 'notRegistredEmail');
                        setTimeout(hideError, 5000);
                    } else {
                        showModal('Check your email', 'success', 'checkEmail');
                        setTimeout(hideModal, 2000);
                    }
                }
                setLocaleFields();
            });
    }
}

function showModal(x, y, className) {
    $('#idMessagesSuccess').addClass('alert alert-' + y).addClass(className);
    $('#idMessagesSuccess').text(x);
    $('#staticBackdrop').modal();
}

function hideModal() {
    $('#staticBackdrop').modal('hide');
    redir();
}

function redir() {
    var delay = 1000;
    setTimeout("document.location.href='/'", delay);
}

function showError(x, className) {
    $('#errorMessagePassword').addClass(className).text(x);
    $('#collapsePassword').attr('class', 'collapse show');
}

function hideError() {
    $('#collapsePassword').attr('class', 'collapse');
}