var currentLang = '';
$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
    openModalLoginWindowOnFailure();
    getShoppingCart();
    showListOrders().then(r => {
    });
});
let totalPrice = 0;

function getShoppingCart() {
    setTimeout(async function () {
        await fetch("/cart")
            .then(status)
            .then(json)
            .then(function (data) {
                $('#newTab').empty();
                totalPrice = 0;
                $('#sum').text(totalPrice);
                $.each(data, function (index) {
                    let book = data[index].book;
                    totalPrice += book.price * data[index].quantity;
                    let row = $('<tr id="trr"/>');
                    let cell = $('<td width="10"></td>');
                    row.append(cell);
                    cell = '<td class="align-middle"><img src="/images/book' + book.id + '/' + book.coverImage + '" style="max-width: 60px"></td>' +
                        '<td class="align-middle">' + book.name[currentLang] + ' | ' + book.author[currentLang] + '</td>' +
                        '<td class="align-middle" id="book' + book.id + '">' + book.price + '</td>' +
                        '<td class="align-middle"><div class="product-quantity" > <input id="value' + book.id + '" type="number" value="' + data[index].quantity + '" min="1" style="width: 45px" data-id="' + book.id + '" data-value="' + data[index].quantity + '"></div></td>' +
                        '<td class="align-middle" ><button class="btn btn-info delete"  style="background-color: orangered" data-id="' + book.id + '">' + deleteBottom + '</button></td>';
                    row.append(cell);
                    row.appendTo('#newTab');
                    $('#sum').text(totalPrice);
                });
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
        totalPrice = totalPrice + price * (quatity - oldVal);
        $('#sum').text(totalPrice);
    })
}


$(document).ready(function () {
    $("body").on('click', '.delete', function () {
        let id = $(this).attr("data-id");
        fetch('/cart/' + id, {
            method: 'DELETE',
        }).then(function () {
            getShoppingCart();
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


let order = '';

function confirmPurchase() {
    fetch('/order').then(r => getShoppingCart());
    showShoppingCart();
}

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
        cell = '<td class="align-middle"><img src="../images/book' + book.id + '/' + book.coverImage + '" style="max-width: 60px"></td>' +
            '<td class="align-middle">' + book.name[currentLang] + ' | ' + book.author[currentLang] + '</td>' +
            '<td class="align-middle" id="book' + book.id + '">' + book.price + '</td>' +
            '<td class="align-middle"><div class="product-quantity" >' + items[index].quantity + '</div></td>';
        row.append(cell);
        row.appendTo('#orderTab');
    });
    $('#subtotal').text(totalPrice);
    $('#shippingcost').text(order.shippingCost);
    $('#pricetotal').text((totalPrice + order.shippingCost));

    let flat = '';
    if (order.address.flat != "") {
        flat = '-' + order.address.flat;
    }

    let addressDelivery = {
        "Country/Zip code": ` ${order.address.country} , ${order.address.postalCode}`,
        "City/State": `${order.address.city} , ${order.address.state}`,
        "Street": `${order.address.street}`,
        "House/Flat": `${order.address.house}${flat}`,
        "First name , Last name": `${order.address.firstName} ${order.address.lastName}`
    };

    let html = ``;
    let x = 36;
    for (let key in addressDelivery) {
        (x < 34) ? x = x + 2 : x = x - 2;
        html += `<div class="input-group mb-3 shadow adressDelivery "  style="width: ${x}rem;">
            <div class="input-group-prepend "  ><span class="input-group-text"  id="basic-addon3">${key}</span></div>
            <h1 class="form-control  "  aria-describedby="basic-addon3"> ${addressDelivery[key]} </h1></div>`
    }
    $('#shippingaddress').html(html);
}

let listOders = '';

async function showListOrders() {
    await fetch("/order/getorders")
        .then(status)
        .then(json)
        .then(function (data) {
            $('#listorders').empty();
            listOders = data;
            $.each(data, function (index) {
                let row = $('<tr>');
                let cell = $('<td width="10"></td>');
                row.append(cell);
                cell = '<td>' + data[index].id + '</td><td>' + data[index].data + '</td><td>' + (data[index].itemsCost + data[index].shippingCost) + '</td><td><a href="#" data-toggle="modal" data-target="#ordermodal"  onclick="showCarrentOrder(' + index + ')">Show</a></td>'
                row.append(cell);
                row.appendTo('#listorders');
            })
        });
}

function showCarrentOrder(index) {
    let order = listOders[index]
    let items = order.items;
    $('#ordermodalbody').empty();
    $.each(items, function (index) {
        let book = items[index].book;
        let row = $('<tr id="trr"/>');
        let cell = $('<td width="10"></td>');
        row.append(cell);
        cell = '<td class="align-middle"><img src="../images/book' + book.id + '/' + book.coverImage + '" style="max-width: 60px"></td>' +
            '<td class="align-middle">' + book.name[currentLang] + ' | ' + book.author[currentLang] + '</td>' +
            '<td class="align-middle" id="book' + book.id + '">' + book.price + '</td>' +
            '<td class="align-middle"><div class="product-quantity" >' + items[index].quantity + '</div></td>';
        row.append(cell);
        row.appendTo('#ordermodalbody');
    });
    $('#modalHeader').text('Order No. ' + order.id);
    $('#ordestatus').text(order.status);
    $('#ordertrack').text(order.trackingNumber);
    $('#subtotalordermodal').text(order.itemsCost);
    $('#shippingcostordermodal').text(order.shippingCost);
    $('#pricetotalordermodal').text((order.itemsCost + order.shippingCost));
    $('#shippingaddressordermodal').empty();
    let address = order.address;
    let flat = '';
    if (address.flat != "") {
        flat = '-' + address.flat;
    }
    let shipping = $('<p>' + address.firstName + ' ' + address.lastName + '</p>' +
        '<p>' + address.street + ' ' + address.house + flat + '</p>' +
        '<p>' + address.postalCode + ' ' + address.city + ' ' + address.state + '</p>' +
        '<p>' + address.country + '</p>');
    shipping.appendTo('#shippingaddressordermodal');

}


