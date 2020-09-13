let saveAddressAlert = $('#saveAddressAlert');
let savedAddressesSelect = $("#addressSelect");
let savedAddresses = [];
let contacts;

function setCurrentPageToCookie() {
    let cookie = `CURRENT_PAGE = ${window.location.pathname}; path = /; max-age = 60`;
    document.cookie = cookie;
}

$(document).ready(function () {
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

async function confirmAddress() {
    let isAuth = false;

    await POST('/api/user/order/confirmaddress'
        , JSON.stringify(userData))
        .then(json)
        .then((data) => {
            isAuth = true;
            order = data;
        }, () => {
            $("#signModal").modal('show');
            setCurrentPageToCookie();
        });
    if (!isAuth === null) return;
    else {
        showContacts();
    }
}

async function confirmAddressAutoReg() {
    let isAuth = false;

    await POST('/api/user/order/confirmaddress'
        , JSON.stringify(userData))
        .then(json)
        .then((data) => {
            isAuth = true;
            order = data;
        }, () => {
            $("#signModal").modal('show');
            setCurrentPageToCookie();
        });
    if (!isAuth === null) return;
    else {
        $('#forButtonCheckout').hide();
    }
}

async function saveAddress() {
    saveAddressAlert.removeClass('show');
    let address = {
        flat: $("#flat").val(),
        house: $("#street_number").val(),
        street: $("#route").val(),
        city: $("#locality").val(),
        state: $("#administrative_area_level_1").val(),
        postalCode: $("#postal_code").val(),
        country: $("#country").val(),
        firstName: $("#firstName").val(),
        lastName: $("#lastName").val()
    };
    await POST("/api/user/address", JSON.stringify(address), JSON_HEADER);
}

async function showAddressTab() {
    let isAuth = false;
    await GET("/api/user/address")
        .then(json)
        .then(resp => {
            isAuth = true;
            savedAddresses = resp;
        }, () => {
            $("#signModal").modal('show');
        });
    if (!isAuth) return;
    for (let address in savedAddresses) {
        let displayString = ``;
        for (let key in savedAddresses[address]) {
            if (key === 'id') continue;
            displayString += `${savedAddresses[address][key]}, `
        }
        savedAddressesSelect.append(`<option value="${address}">${displayString}</option>`);
    }

    if (totalPrice != 0) {
        $('#cartTab a[href="#delivery"]').tab('show');
    }
}

$(document).on('change', "#addressSelect", function () {
    fillAddress(savedAddresses[$(this).val()]);
});

function fillAddress(address) {
    $("#flat").val(address.flat);
    $("#street_number").val(address.house);
    $("#route").val(address.street);
    $("#locality").val(address.city);
    $("#administrative_area_level_1").val(address.state);
    $("#postal_code").val(address.postalCode);
    $("#country").val(address.country);
    $("#firstName").val(address.firstName);
    $("#lastName").val(address.lastName);
}

function showSummary() {
    $('#cartTab a[href="#Summary"]').tab('show');
}

function showContacts() {
    $('#email').val(order.userAccount.name);
    $('#firstName').val(order.userAccount.firstName);
    $('#lastName').val(order.userAccount.lastName);
    $('#phone').val(order.userAccount.phone);
    $('#cartTab a[href="#contacts"]').tab('show');
}

function showContacts1ClickReg() {
    $('#cartTab a[href="#contacts"]').tab('show');
}

async function confirmContacts() {
    contacts = {
        email: $("#email").val(),
        firstName: $("#firstName").val(),
        lastName: $("#lastName").val(),
        phone: $("#phone").val(),
        comment: $("#comment").val(),
    };
    if (isNumber(contacts.phone)) {
        await POST("/api/user/order/confirmContacts", JSON.stringify(contacts), JSON_HEADER);
        showSummary();
        showOrderSum();
    } else {
        showErrorPass1ClickReg('Phone incorrect', 'password-used-by-user-loc');
    }
}

function isNumber(n) {
    return !isNaN(parseFloat(n)) && !isNaN(n - 0)
}

function validateEmailAndPhone(contacts) {
    let phone = contacts.phone;
    let tmpSend2 = JSON.stringify(contacts);
    $.ajax({
        type: 'POST',
        url: "/checkEmail1ClickReg",
        contentType: "application/json;charset=UTF-8",
        data: tmpSend2,
        success: function(data) {
            if (data === "ok" && isNumber(phone)) {
                confirmContactsFor1Click2();
            } else if (data === "error" && !isNumber(phone)) {
                showErrorPass1ClickReg('Phone incorrect', 'password-used-by-user-loc');
                showError1ClickReg(' This email address is used by another user!', 'email-used-by-user-loc');
            } else if (data === "synError" && !isNumber(phone)) {
                showErrorPass1ClickReg('Phone incorrect', 'password-used-by-user-loc');
                showError1ClickReg('Invalid email format!', 'email-used-by-user-loc');
            } else if (data === "ok" && !isNumber(phone)) {
                showErrorPass1ClickReg('Phone incorrect', 'password-used-by-user-loc');
            } else if (data === "synError" && isNumber(phone)) {
                showError1ClickReg('Invalid email format!', 'email-used-by-user-loc');
            } else if (data === "error" && isNumber(phone)) {
                showError1ClickReg(' This email address is used by another user!', 'email-used-by-user-loc');
            }
        }
    });
}

function validateContacts() {
    contacts = {
        email: $("#email").val(),
        firstName: $("#firstName").val(),
        lastName: $("#lastName").val(),
        phone: $("#phone").val(),
        comment: $("#comment").val(),
    };
    validateEmailAndPhone(contacts);
}

async function confirmContactsFor1Click2() {
    showSummary();
    showOrderSum();
}

function showHome() {
    $('#cartTab a[href="#home"]').tab('show');
}
