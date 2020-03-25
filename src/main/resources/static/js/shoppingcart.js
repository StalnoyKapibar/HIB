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
function getCart() {
    $.ajax({
        url: "/cart",
        method: 'GET',
    }).then(function (data) {
        $('#newTab').empty();
        totalPrice = 0;
        $.each(data, function (key, value) {
            let book = getBookDTO(key);
            totalPrice += book.price * value;
            let row = $('<tr id="trr"/>');
            let cell = $('<td width="10"></td>');
            row.append(cell);
            cell = '<td class="align-middle"><img src="../images/book' + book.id + '/' + book.coverImage + '" style="max-width: 60px"></td>' +
                '<td class="align-middle">' + book.name[currentLang] + ' | ' + book.author[currentLang] + '</td>' +
                '<td class="align-middle" id="book'+book.id+'">' + book.price + '</td>' +
                '<td class="align-middle"><div class="product-quantity" > <input id="value'+book.id+'" type="number" value="' + value + '" min="1" style="width: 45px" data-id="' + book.id + '" data-value="'+value+'"></div></td>' +
                '<td class="align-middle" ><button class="btn btn-info delete"  style="background-color: orangered" data-id="' + book.id + '">' + 'Delete' + '</button></td>';
            row.append(cell);
            row.appendTo('#newTab');
        });
        $('#totalPrice').text('Итого: ' + totalPrice)
    });
}



function updateQuantity(quatity, id) {
$.ajax({
    url: "/cart",
    type: "POST",
    data: {id: id, quatity: quatity},
    success: function () {
        let oldVal = $('#value'+id).attr('data-value');
        $('#value'+id).attr('data-value',quatity);
        let price = $('#book'+id).text();
        totalPrice = totalPrice + price*(quatity-oldVal);
        $('#totalPrice').text('Итого: ' + totalPrice);
    }
})
}

function getBookDTO(id) {
    let res;
    $.ajax({
        url: "/getBookDTOById/" + id,
        async: false,
        method: 'GET',
        success: function (data) {
            res = data;
        }
    });
    return res;
}

$(document).ready(function () {
    $("body").on('click', '.delete', function () {
        let id = $(this).attr("data-id");
        $.ajax({
            url: '/cart/' + id,
            type: 'DELETE',
            data: id,
            success: function () {
                getCart();
            },
        })
    });
    $("body").on('change', '.product-quantity input', function () {
        let id = $(this).attr("data-id");
        let quantity = $(this).val();
        updateQuantity(quantity,id)
    });
});

