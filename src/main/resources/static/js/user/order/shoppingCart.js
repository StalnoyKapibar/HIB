var currentLang = '';
let listOders = '';
let totalPrice = 0;
let currencyIcon = ' €';
let order = '';
var htmlForModalBody = ``;

$(document).ready(function () {
    openModalLoginWindowOnFailure();
    getShoppingCart();
    showListOrders().then(r => {
    });

    if (document.referrer.toString() === "" && userData.oauth2Acc === false) {
        confirmAddressAutoReg();

        confirmContactsFor1Click();
    }

    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }

    getLanguage();
    setLocaleFields();

});

function convertPrice(price) {
    return price / 100;
}

async function getShoppingCart() {
    const lastOrderedBooks = await getLastOrderedBooks();
    let isOrderEnable = true;

    setTimeout(async function () {
        await POST("/cart")
            .then(status)
            .then(json)
            .then(function (data) {
                $('#newTab').empty();
                totalPrice = 0;
                $('#sum').text(totalPrice);
                $.each(data, function (index) {
                    let book = data[index].book;
                    price = convertPrice(book.price);
                    totalPrice += price;
                    totalPrice = Number.parseFloat(totalPrice.toFixed(2));
                    let row = $('<tr id="trr"/>');
                    let cell = $('<td width="10"></td>');
                    row.append(cell);

                    let first = `<td class="align-middle"><img src="/images/book${book.id}/${book.coverImage}" style="max-width: 60px"></td>`;
                    let second = `<td class="align-middle">${convertOriginalLanguageRows(book.originalLanguage.name, book.originalLanguage.nameTranslit)} | ${convertOriginalLanguageRows(book.originalLanguage.author, book.originalLanguage.authorTranslit)}</td>`;
                    let third = `<td class="align-middle">${price + currencyIcon}</td>`;
                    let forth = `<td hidden id="book${book.id}">${price}</td>`;
                    let fifth = `<td class="align-middle"><button class="btn btn-info delete"  style="background-color: #ff4500" data-id="${book.id}">${deleteBottom}</button></td>`;

                    cell = first + second + third + forth + fifth;

                    row.append(cell);
                    row.appendTo('#newTab');
                    $('#sum').text(totalPrice + currencyIcon);



                });
                if (data.length === 0) {
                    isOrderEnable = false;
                }
                if (!isOrderEnable) {
                    $('#shoppingCardOrderDisabledMessage').addClass('resolveShopCart').text('Please resolve shopping cart warnings before proceeding');
                    $('#forButtonCheckout').html(`<div><button class="btn btn-primary checkout-btn" id="chechout" onclick="confirmAddress()" type="button" disabled="disabled">
                                    Checkout
                                </button></div>`);
                    $('#for-1click-reg').html(`<button class="btn btn-primary" id="1click-reg-btn"
                                               onclick="location.href='/1clickreg'" type="button" disabled="disabled">
                                               Buy without sign up</button>`);
                } else {
                    $('#shoppingCardOrderDisabledMessage').text('');
                    $('#forButtonCheckout').html(`<div><button class="btn btn-primary checkout-btn" id="chechout" onclick="confirmAddress()" type="button">
                                    Checkout
                                </button></div>`);
                    $('#for-1click-reg').html(`<button class="btn btn-primary" id="1click-reg-btn"
                                               onclick="location.href='/1clickreg'" type="button">
                                               Buy without sign up</button>`);
                }
          setLocaleFields();


            });
    }, 10);
}

async function updateQuantity(quatity, id) {
    await fetch("/cart", {
        method: 'POST',
        headers: {"Content-type": "application/x-www-form-urlencoded; charset=UTF-8"},
        body: 'id=' + id + '&quatity=' + quatity,
    }).then(function () {
        let oldVal = $('#value' + id).attr('data-value');
        $('#value' + id).attr('data-value', quatity);
        let price = $('#book' + id).text();
        totalPrice += price * (quatity - oldVal);
        totalPrice = Number.parseFloat(totalPrice.toFixed(2));
        $('#sum').text(totalPrice + currencyIcon);
    })
}

$(document).ready(function () {
    $("body").on('click', '.delete', function () {
        let id = $(this).attr("data-id");
        fetch('/cart/' + id, {
            method: 'DELETE',
        }).then(function () {
            getShoppingCart();
            showSizeCart();
        })
    });
    $("body").on('change', '.product-quantity input', function () {
        let id = $(this).attr("data-id");
        let quantity = $(this).val();
        updateQuantity(quantity, id)
    });
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

var placeSearch, autocomplete;
var componentForm = {
    street_number: 'short_name',
    route: 'long_name',
    locality: 'long_name',
    administrative_area_level_1: 'short_name',
    country: 'long_name',
    postal_code: 'short_name'
};

function initAutocomplete() {
    // Create the autocomplete object, restricting the search to geographical
    // location types.
    autocomplete = new google.maps.places.Autocomplete(
        /** @type {!HTMLInputElement} */(document.getElementById('autocomplete')),
        {types: ['geocode']});

    // When the user selects an address from the dropdown, populate the address
    // fields in the form.
    autocomplete.addListener('place_changed', fillInAddress);
}

function fillInAddress() {
    // Get the place details from the autocomplete object.
    var place = autocomplete.getPlace();

    for (var component in componentForm) {
        document.getElementById(component).value = '';
        document.getElementById(component).disabled = false;
    }

    // Get each component of the address from the place details
    // and fill the corresponding field on the form.
    for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];
        if (componentForm[addressType]) {
            var val = place.address_components[i][componentForm[addressType]];
            document.getElementById(addressType).value = val;
        }
    }
}

// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var geolocation = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            var circle = new google.maps.Circle({
                center: geolocation,
                radius: position.coords.accuracy
            });
            autocomplete.setBounds(circle.getBounds());
        });
    }
}

async function confirmPurchase() {
    await POST('/order')
        .then(r => getShoppingCart())
        .then(function () {
            window.location.href = "/profile/orders";
        });
}

$("#butToBuy").one('click',function() {
    // show preloader before action
    $(".preloader").show("slow");
    // add message to preloader
    $(".lds-ellipsis").html(`
        <span></span>
        <span></span>
        <span></span>
        <br>
        <div class="text-danger">We are processing your transaction.<br>
        Please wait a few seconds.<br>
        You will now be redirected to the order page.</div>
    `);
    confirmPurchase();
});

function enterData() {
    let data = '';
    if ($("#street_number").val() == '') {
        data = 'house';
    }
    if ($("#route").val() == '') {
        data = 'street'
    }
    if ($("#locality").val() == '') {
        data = 'city'
    }
    if ($("#administrative_area_level_1").val() == '') {
        data = 'state'
    }
    if ($("#postal_code").val() == '') {
        data = 'zip code'
    }
    if ($("#country").val() == '') {
        data = 'country'
    }
    if ($("#firstName").val() == '') {
        data = 'first name'
    }
    if ($("#lastName").val() == '') {
        data = 'last name'
    }
    return data;
}

function showOrderSum() {
    let items = order.items;
    $('#orderTab').empty();
    $.each(items, function (index) {
        let book = items[index].book;
        let row = $('<tr id="trr"/>');
        let cell = $('<td width="10"></td>');
        row.append(cell);
        cell = `<td class="align-middle"><img src="../images/book${book.id}/${book.coverImage}" style="max-width: 60px"></td>
            <td class="align-middle">${convertOriginalLanguageRows(book.originalLanguage.name, book.originalLanguage.nameTranslit)} | ${convertOriginalLanguageRows(book.originalLanguage.author, book.originalLanguage.authorTranslit)}</td>
            <td class="align-middle" id="book${book.id}">${convertPrice(book.price) + currencyIcon}</td>`;
        row.append(cell);
        row.appendTo('#orderTab');
    });
    $('#subtotal').text(totalPrice + currencyIcon);
    $('#pricetotal').text(totalPrice + currencyIcon);

    let html = ``;
    html += `
                    <div class="panel panel-primary">
                        <div class="panel-body">
                            <div class="container mt-2">
                                <div class="col-8 p-4 mb-4  alert alert-info" role="alert">
                                    <h6><label class="your-loc">Your</label> <strong class="contacts-loc">contacts </strong></h6>
                                </div>`;

    if (contacts.email !== '') {
        html += `<div class="form-group  row">
                        <label class="control-label col-sm-2 col-form-label email-label">Email</label>

                        <div class="col-md-5 pl-0 pr-1">
                            <input class="form-control" readonly  placeholder=${contacts.email}>
                        </div>
                    </div>`;

    }
    // добавли в блок подтвреждения контактной инфы перед заказом имя и фамилию
    if (contacts.firstName !== '') {
        html += `<div class="form-group  row">
                        <label class="control-label col-sm-2 col-form-label firstName-label">First name</label>

                        <div class="col-md-5 pl-0 pr-1">
                            <input class="form-control" readonly  placeholder=${contacts.firstName}>
                        </div>
                    </div>`;

    }
    if (contacts.lastName !== '') {
        html += `<div class="form-group  row">
                        <label class="control-label col-sm-2 col-form-label lastName-label">Last name</label>

                        <div class="col-md-5 pl-0 pr-1">
                            <input class="form-control" readonly  placeholder=${contacts.lastName}>
                        </div>
                    </div>`;

    }
    if (contacts.phone !== '') {
        html += `<div class="form-group row">
                        <label class="control-label col-sm-2 col-form-label phone-label">Phone</label>

                        <div class="col-sm-5 pl-0 pr-1">
                            <input class="form-control field" readonly  placeholder=${contacts.phone}>
                        </div></div>`;
    }
    if (contacts.comment !== " ") {
        html += `
                    <div class="form-group row">
                        <label class="control-label col-sm-2 col-form-label comment-label">Comment</label>
                        <div class="col-md-6 pl-0">
                            <textarea class="form-control" readonly  rows="5" placeholder="${contacts.comment}" ></textarea>
                        </div>
                    </div>`;
    }

    html += `</div></div>`;
    //присоеденяем введенные пользователем контакты для подтвержения.
    $('#shippingaddress').html(html);

    setLocaleFields();

}

// Вкладка заказы
async function showListOrders() {
    await fetch("/order/getorders")
        .then(status)
        .then(json)
        .then(function (data) {
            listOders = data;
            let html = ``;
            listOders.forEach(function (order, index) {
                html += `<tr><td></td>
                         <td>${index + 1}</td> 
                         <td>${order.data}</td> 
                         <td>${order.status}</td>
                         <td>${convertPrice(order.itemsCost)} ${currencyIcon}</td>

                         <td><button type="button" class="btn btn-info show-btn" data-toggle="modal" data-target="#ordermodal"  onclick="showCarrentOrder(${index})">Show</button>`;
                if (order.status === "UNPROCESSED") {
                    html += `<button type="button" class="btn btn-danger show-btn" onclick="orderCancel(${order.id})">Cancel</button></td></tr>`;
                } else {
                    html += `<button type="button" class="btn btn-danger show-btn" onclick="orderCancel(${order.id})" disabled="disabled">Cancel</button></td></tr>`;
                }
            });
            $('#listorders').html(html);
        });
}

// CANCEL order before PROCESSING
function orderCancel(id) {
    try {
        // try to proceed cancel order, show modal OK
        fetch("/api/user/orderCancel/" + id, {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(id),
        })
        .then(json)
        .then(function (data) {
            $("#successAction").html(data.response);
            $("#successActionModal").modal('show');
        })
        .then(function() {
            showOrderSize();
        })
        .then(function () {
            showListOrders()
        });
    } catch (error) {
        // catch error, show modal not OK
        console.log("Failed to cancel order: ", error)
        $("#successAction").html("Failed to cancel order");
        $("#successActionModal").modal('show');
    }

}

function showCarrentOrder(index) {
    let order = listOders[index];
    let items = order.items;
    $('#ordermodalbody').empty();
    $.each(items, function (index) {
        let book = items[index].book;
        let row = $('<tr id="trr"/>');
        let cell = $('<td width="10"></td>');
        row.append(cell);
        cell = `<td class="align-middle"><img src="../images/book${book.id}/${book.coverImage}" style="max-width: 60px"></td>
            <td class="align-middle">${convertOriginalLanguageRows(book.originalLanguage.name, book.originalLanguage.nameTranslit)} | ${convertOriginalLanguageRows(book.originalLanguage.author, book.originalLanguage.authorTranslit)}</td>
            <td class="align-middle" id="book${book.id}">${convertPrice(book.price) + currencyIcon}</td>`;
        row.append(cell);
        row.appendTo('#ordermodalbody');
    });
    $('#modalHeader').html('<label class="orderNo-loc">Order No.</label>' + order.id);
    $('#ordertrack').text(order.trackingNumber);
    $('#subtotalordermodal').text(convertPrice(order.itemsCost) + currencyIcon);
    $('#pricetotalordermodal').text(convertPrice(order.itemsCost) + currencyIcon);

    let html = ``;
    // блок отрисовки подтвержения контактной информации клиента перед заказом/покупкой
    html += `<div class="panel panel-primary">
                        <div class="panel-body">
                            <div class="container mt-2">
                                <div class="col-8 p-4 mb-4  alert alert-info" role="alert">
                                    <h6><label class="your-loc">Your</label> <strong class="contacts-loc">contacts </strong></h6>
                                </div>`;
    if (order.contacts.email !== '') {
        html += `<div class="form-group row">
                       <label class="control-label col-sm-2 col-form-label email-label">Email</label>
                        <div class="col-md-5 pl-0 pr-1">
                            <input class="form-control" readonly  placeholder=${order.contacts.email}>
                        </div>
                    </div>`;
    }
    if (order.contacts.firstName !== '') {
        html += `<div class="form-group row">
                       <label class="control-label col-sm-2 col-form-label firstName-label">First name</label>
                        <div class="col-md-5 pl-0 pr-1">
                            <input class="form-control" readonly  placeholder=${order.contacts.firstName}>
                        </div>
                    </div>`;
    }
    if (order.contacts.phone !== '') {
        html += `<div class="form-group row">
                        <label class="control-label col-sm-2 col-form-label phone-label">Phone</label>
                        <div class="col-sm-5 pl-0 pr-1">
                            <input class="form-control field" readonly  placeholder=${order.contacts.phone}>
                        </div></div>`;
    }
    if (order.comment !== " ") {
        html += `<div class="form-group row">
                        <label class="control-label col-sm-2 col-form-label comment-label">Comment</label>
                        <div class="col-md-6 pl-0">
                            <textarea class="form-control" readonly  rows="5" placeholder="${order.comment}" ></textarea>
                        </div>
                    </div>`;
    }

    html += `</div></div>`;
    setLocaleFields();
}

async function getLastOrderedBooks() {
    const url = '/api/book/lastOrderedBooks';
    const res = await fetch(url);
    const data = await res.json();
    return data;
}
