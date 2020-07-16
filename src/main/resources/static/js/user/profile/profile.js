let AU;

$(document).ready(function () {

    let tabhash = document.location.hash;

    if (tabhash == '#cart') {
        $('#myTab a[href="#Basket"]').tab('show');
    }

    if (currentLang === '') {
        if (getCookieByName("lang")) {
            currentLang = getCookieByName("lang");
        } else {
            currentLang = 'en';
        }
    }

    getLanguage();
    setLocaleFields();
});

function showShoppingCart() {
    $('#cartTab a[href="#home"]').tab('show');
}

$(document).ready(function () {
    getAU().then(setFieldsChangePersonalInformation).then(oAuth2AccHandle).then(firstLastNames);
});

async function getAU() {
    await GET("/api/current-user")
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
    $('#fieldPhone').val(AU.phone);
}

function oAuth2AccHandle() {
    document.getElementById("PasswordChange-tab").hidden = AU.oauth2Acc;
}

function firstLastNames() {
    if (AU.lastName === null) {
        document.getElementById("first-last-name").innerHTML = AU.firstName;
    } else {
        document.getElementById("first-last-name").innerHTML = AU.firstName + ' ' + AU.lastName;
    }
}

function refreshUserNames(firstName, lastName, email, phone) {
    if (lastName === null) {
        document.getElementById("first-last-name").innerHTML = firstName;
    }
    else {
        document.getElementById("first-last-name").innerHTML = firstName + ' ' + lastName;
    }
    document.getElementById("user-email").innerHTML = email;
    document.getElementById("user-phone").innerHTML = phone;
}

function savePersonalInformation() {
    console.log("Hello");
    let email = $('#fieldEmail').val();
    let firstName = $('#fieldFirstName').val();
    let lastName = $('#fieldLastName').val();
    let phone = $('#fieldPhone').val();
    let tmp = {};
    tmp['userId'] = AU.userId;
    tmp['email'] = email;
    tmp['firstName'] = firstName;
    tmp['lastName'] = lastName;
    tmp['phone'] = phone;
    let tmpSend = JSON.stringify(tmp);
    savePersonalInformationRequest(tmpSend);
    refreshUserNames(firstName, lastName, email, phone);
    setLocaleFields();
}

function savePersonalInformationRequest(personalInformation) {
    POST("/cabinet/savePersonalInformation", personalInformation, JSON_HEADER)
        .then(status)
        .then(text)
        .then(function (resp) {
            if (resp === "error") {
                showError(' This email address is used by another user!', 'email-used-by-user-loc');
                setTimeout(hideError, 5000);
            } else {
                if (resp === "synError") {
                    showError('Invalid email format!', 'invalid-email-format-loc');
                    setTimeout(hideError, 5000);
                } else {
                    showSuccess('Changes to personal information are successfully saved!', 'mess-success-loc');
                    setTimeout(hideSuccess, 2000);
                }
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

function text(response) {
    return response.text()
}

function showError(message, className) {
    $('#errorMessageEmail').addClass(className).text(message);
    $('#collapseExample').attr('class', 'collapse show');
    setLocaleFields();
}

function hideError() {
    $('#collapseExample').attr('class', 'collapse');
}

function showSuccess(message, className) {
    $('#idMessagesSuccess').addClass(className).text(message);
    $('#staticBackdrop').modal();
    setLocaleFields();
}

function hideSuccess() {
    $('#staticBackdrop').modal('hide');
}

function savePassword() {
    let password0 = $('#enterPassword').val();
    let password1 = $('#enterPasswordAgain').val();
    if (password0 === password1) {
        savePasswordReq(password0);
    } else {
        showErrorPassword('Passwords don\'t match!', 'dont-match-pass-loc');
        setTimeout(hideErrorPassword, 5000);
    }
}

function showErrorPassword(message, className) {
    $('#errorMessagePassword').addClass(className).text(message);
    $('#collapsePassword').attr('class', 'collapse show');
    setLocaleFields();
}

function hideErrorPassword() {
    $('#collapsePassword').attr('class', 'collapse');
}

function savePasswordReq(newPassword) {
    let passwordDto = {
        userId: AU.userId,
        newPassword: newPassword,
        oldPassword: $('#oldPassword').val(),
    };
    POST("/cabinet/savePassword", JSON.stringify(passwordDto), JSON_HEADER)
        .then(status)
        .then(text)
        .then(function (resp) {
            if (resp === "passError") {
                showErrorPassword('The password must be between 8 and 64 and can contain numbers and characters in the upper and lower registers, without spaces!',
                    'incorrect-data-password-loc');
                setTimeout(hideErrorPassword, 5000);
            } else {
                if (resp === "wrongPassword") {
                    showErrorPassword('Wrong current password!', 'wrong-current-pass-loc');
                    setTimeout(hideErrorPassword, 5000);
                } else {
                    showSuccess('Password successfully saved!', 'pass-success-saved-loc');
                    setTimeout(hideSuccess, 2000);
                }
            }
        });
}
