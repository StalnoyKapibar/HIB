var currentLang = '';
$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
    openModalLoginWindowOnFailure();
    getCart();
});
let totalPrice = 0;

// function getCart() {
//     setTimeout(async function () {
//         await fetch("/cart")
//             .then(status)
//             .then(json)
//             .then(function (data) {
//                 $('#newTab').empty();
//                 totalPrice = 0;
//                 $.each(data, async function (key, value) {
//                     let book = await getBookDTO(key);
//                     totalPrice += book.price * value;
//                     let row = $('<tr id="trr"/>');
//                     let cell = $('<td width="10"></td>');
//                     row.append(cell);
//                     cell = '<td class="align-middle"><img src="../images/book' + book.id + '/' + book.coverImage + '" style="max-width: 60px"></td>' +
//                         '<td class="align-middle">' + book.name[currentLang] + ' | ' + book.author[currentLang] + '</td>' +
//                         '<td class="align-middle" id="book' + book.id + '">' + book.price + '</td>' +
//                         '<td class="align-middle"><div class="product-quantity" > <input id="value' + book.id + '" type="number" value="' + value + '" min="1" style="width: 45px" data-id="' + book.id + '" data-value="' + value + '"></div></td>' +
//                         '<td class="align-middle" ><button class="btn btn-info delete"  style="background-color: orangered" data-id="' + book.id + '">' + deleteBottom + '</button></td>';
//                     row.append(cell);
//                     row.appendTo('#newTab');
//                     $('#sum').text(totalPrice);
//                 });
//
//             });
//     }, 10);
// }

function getCart() {
    setTimeout(async function () {
        await fetch("/cart")
            .then(status)
            .then(json)
            .then(function (data) {
                $('#newTab').empty();
                totalPrice = 0;
                $.each(data, function (index) {
                    let book = data[index].book;
                    totalPrice += book.price * data[index].quantity;
                    let row = $('<tr id="trr"/>');
                    let cell = $('<td width="10"></td>');
                    row.append(cell);
                    cell = '<td class="align-middle"><img src="../images/book' + book.id + '/' + book.coverImage + '" style="max-width: 60px"></td>' +
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

async function getBookDTO(id) {
    let res;
    await fetch("/getBookDTOById/" + id)
        .then(status)
        .then(json)
        .then(function (data) {
            res = data;
        });
    return res;
}

$(document).ready(function () {
    $("body").on('click', '.delete', function () {
        let id = $(this).attr("data-id");
        fetch('/cart/' + id, {
            method: 'DELETE',
        }).then(function () {
            getCart();
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


