var currentLang = '';
var bottom = '';
var addToshoppingCart = '';
var deleteBottom = '';
let welcomeBlock = $("#welcome");

$(document).ready(function () {

    getLanguage();
    setLocaleFields();
    buildPageByCurrentLang();
    openModalLoginWindowOnFailure();
    showSizeCart();
    loadWelcome(currentLang);
    showOrderSize();
});

function buildPageByCurrentLang() {
    setTimeout(function () {
        fetch("/user/get20BookDTO/" + currentLang)
            .then(status)
            .then(json)
            .then(function (data) {
                $('#cardcolumns').empty();
                $.each(data, function (index) {
                    let div = $('<div class="card mb-4"/>');
                    div.append('<img class="card-img-top" src="images/book' + data[index].id + '/' + data[index].coverImage + '" alt="Card image cap">');
                    let divBody = $('<div class="card-body" ></div>');
                    divBody.append('<h4 class="card-title" style="overflow: auto; height:100px">' + data[index].nameAuthorDTOLocale + '</h4>');
                    divBody.append('<p class="card-text">' + data[index].nameBookDTOLocale + '</p>');
                    divBody.append('<br>');
                    divBody.append('<div style="position: absolute; bottom: 5px"><button id="bottomInCart"type="button" class="btn btn-success btn-sm  mr-1"  data-id="' + data[index].id + '">' + addToshoppingCart + '</button>' +
                        `<button type="button" id="bookbotom" class="btn btn-primary btn-sm mr-1" onclick="document.location='/page/${data[index].id}'"> ${bottom} </button></div>`);
                    div.append(divBody);
                    div.appendTo('#cardcolumns');
                });
                $("#myModal").on('show.bs.modal', function (e) {
                    let index = $(e.relatedTarget).data('book-index');
                    $('#modalHeader').empty();
                    $('#modalBody').empty();
                    $('#modalHeader').append(data[index].nameAuthorDTOLocale);
                    $('#modalBody').append('<p>' + data[index].nameBookDTOLocale + '</p>');
                    $('#modalBody').append('<img class="card-img-top" src="/images/book' + data[index].id + '/' + data[index].coverImage + '" alt="Card image cap">');
                    $('#buttonOnBook').attr("action", '/page/' + data[index].id);
                });
            });
    }, 10);
}

async function showSizeCart() {
    await fetch("/cart/size")
        .then(status)
        .then(json)
        .then(function (data) {
            if (data != 0) {
                $("#bucketIn").empty();
                $("#bucketIn").append(data)
            } else {
                $('#bucketIn').empty();
            }
        });
}

async function showOrderSize() {
    await fetch("/order/size")
        .then(status)
        .then(json)
        .then(function (data) {
            if (data != 0) {
                $("#orders-quantity").empty();
                $("#orders-quantity").append(data)
            } else {
                $('#orders-quantity').empty();
            }
        });
}

$(document).ready(function () {
    $("body").on('click', '.btn-success', function () {
        let id = $(this).attr("data-id");
        addToCart(id);
        setTimeout(function () {
            showSizeCart();
        }, 20)

    })
});

function addToCart(id) {
    fetch("/cart/" + id, {
        method: "POST"
    })
}

async function getCart() {
    await fetch("/cart")
        .then(status)
        .then(json)
        .then(function (data) {
            $("#shoppingCartDrop").empty();
            let table = $('<div class="dropdown-item-text"><table class="table table-striped table-sm bg-white" />');
            $.each(data, function (index) {
                let book = data[index].book;
                table.append('<tr></td><td><img src="../images/book' + book.id + '/' + book.coverImage + '" style="max-width: 60px"></td>+' +
                    '<td style="width: 20px"></td>' +
                    '<td>' + book.name[currentLang] + ' | ' + book.author[currentLang] + '</td>' +
                    '<td style="width: 20px"></td>' +
                    '<td>' + data[index].quantity + '</td>' +
                    '<td style="width: 20px"></td>' +
                    '<td class="mr-1"><button class="btn btn-info delete"  style="background-color: orangered" data-id="' + book.id + '">' + deleteBottom + '</button></td>' +
                    '</tr>');
                table.append('<tr style="height: 15px" />');
                $('#shoppingCartDrop').append(table);
            })
        })
}


$(document).ready(function () {
    $("#showCart").on('show.bs.dropdown', function () {
        getCart();
    });
    $("body").on('click', '.delete', function () {
        let id = $(this).attr("data-id");
        fetch('/cart/' + id, {
            method: 'DELETE',
        }).then(function () {
            showSizeCart();
        })
    });
});

function forgotPassword() {
    window.open('/resetPassword', '_blank');
}

async function loadWelcome(locale) {
    await fetch("/api/welcome/locale/" + locale)
        .then(json)
        .then((welcome) => {
            welcomeBlock.html(welcome.bodyWelcome);
        })
}

function checkParams() {
    if ($('#loginInput').val().length !== 0 && $('#passwordInput').val().length !== 0) {
        $('#sign_in_btn').removeAttr('hidden');
    } else {
        $('#sign_in_btn').attr('hidden', 'hidden');
    }
}


