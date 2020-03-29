let AU;

$(document).ready(getAU());

function getAU() {
    fetch("/cabinet/getAU")
        .then(status)
        .then(json)
        .then(function (resp) {
            AU = resp;
        });
}

function setFieldsChangePersonalInformation() {
    $('#fieldEmail').val(AU.email);
    $('#fieldFirstName').val(AU.firstName);
    $('#fieldLastName').val(AU.lastName);
}

function savePersonalInformation() {
    if (validMail()) {
        let email = $('#fieldEmail').val();
        let firstName = $('#fieldFirstName').val();
        let lastName = $('#fieldLastName').val();
        let tmp = {};
        tmp['userId'] = AU.userId;
        tmp['email'] = email;
        tmp['firstName'] = firstName;
        tmp['lastName'] = lastName;
        let tmpSend = JSON.stringify(tmp);
        savePersonalInformationRequest(tmpSend);
    } else {
        showError('Invalid email format!');
        setTimeout(hideError, 5000);
    }
}

function savePersonalInformationRequest(x) {
    fetch("/cabinet/savePersonalInformation", {
        method: 'POST',
        body: x,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    }).then(status)
        .then(text)
        .then(function (resp) {
            if (resp === "error") {
                showError(' This email address is used by another user!');
                setTimeout(hideError, 5000);
            } else {
                showSuccess('Changes to personal information are successfully saved!');
                setTimeout(hideSuccess, 2000);
            }
        });
}

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

function showError(x) {
    $('#errorMessageEmail').text(x);
    $('#collapseExample').attr('class', 'collapse show');
}

function hideError() {
    $('#collapseExample').attr('class', 'collapse');
}

function showSuccess(x) {
    $('#idMessagesSuccess').text(x);
    $('#staticBackdrop').modal();
}

function hideSuccess() {
    $('#staticBackdrop').modal('hide');
}

function validMail() {
    let re = /^[\w-\.]+@[\w-]+\.[a-z]{2,4}$/i;
    let myMail = $('#fieldEmail').val();
    return re.test(myMail);
}

function savePassword() {
    let password0 = $('#enterPassword').val();
    let password1 = $('#enterPasswordAgain').val();
    if (password0 === password1) {
        if (!checkPassword()) {
            showErrorPassword('The password must be between 8 and 64 and must contain numbers and characters in the upper and lower registers, without spaces!');
            setTimeout(hideErrorPassword, 5000);
        } else {
            savePasswordReq(password0);
            showSuccess('Password successfully saved!');
            setTimeout(hideSuccess, 2000);
        }
    } else {
        showErrorPassword('Passwords don\'t match!');
        setTimeout(hideErrorPassword, 5000);
    }
}

function showErrorPassword(x) {
    $('#errorMessagePassword').text(x);
    $('#collapsePassword').attr('class', 'collapse show');
}

function hideErrorPassword() {
    $('#collapsePassword').attr('class', 'collapse');
}

function checkPassword() {
    let pswd = $('#enterPassword').val();
    return pswd.match(/[A-z]/) &&
        pswd.match(/[A-Z]/) &&
        pswd.match(/[0-9]/) &&
        !pswd.match(/ /) &&
        (pswd.length >= 8 && pswd.length <= 64);
}

function savePasswordReq(x) {
    let tmp = {};
    tmp['userId'] = AU.userId;
    tmp['password'] = x;
    let tmpSend = JSON.stringify(tmp);
    fetch("/cabinet/savePassword", {
        method: 'POST',
        body: tmpSend,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
}