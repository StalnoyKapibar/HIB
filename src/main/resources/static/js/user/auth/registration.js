var currentLang = '';
var bottom = '';

$(document).ready(function () {
    if (currentLang === '') {
        //currentLang = $('#dd_menu_link').data('currentLang');
        if (getCookieByName("lang")) {
            currentLang = getCookieByName("lang");
        } else {
            currentLang = 'en';
        }
    }
    getLanguage();
    setLocaleFields();
    buildPageByCurrentLang();
})

$('#registerNewProfile').on('click', function () {
    $('#newAndEditUserModal').modal();
});

$('#buttonCreatNewUser').on('click', () => {
    let firstname = $('#firstname').val();
    let lastname = $('#lastname').val();
    let email = $('#email').val();
    let phone = $('#phone').val();
    let confirmPassword = $('#confirmPassword').val();
    let password = $('#passwordModalWindow').val();

    fetch('/registration', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstName: firstname,
            lastName: lastname,
            email: email,
            password: password,
            phone: phone,
            confirmPassword: confirmPassword
        })
    }).then(response => response.json())
        .then(data => {
            if (data.errorMessage.hasError)
                alert(data.errorMessage.message)
            else
                $("#closeModal").click();
        });

});

