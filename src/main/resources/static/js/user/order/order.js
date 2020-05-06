let saveAddressAlert = $('#saveAddressAlert');
let savedAddressesSelect = $("#addressSelect");
let savedAddresses = [];

function setCurrentPageToCookie() {
    let cookie = `CURRENT_PAGE = ${window.location.pathname}; path = /; max-age = 60`;
    document.cookie = cookie;
}

async function confirmAddress() {
    let isAuth = false;
    await POST('/order/confirmaddress')
        .then(json)
        .then((data) => {
            isAuth = true;
            order = data;
        }, () => {
        $("#signModal").modal('show');
        setCurrentPageToCookie();
    });
    if (!isAuth) return;
    showSummary();
    showOrderSum();
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

