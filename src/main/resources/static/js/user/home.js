var currentLang = '';
var bottom = '';
var addToshoppingCart = '';
var deleteBottom = '';
let welcomeBlock = $("#welcome");
let currencyIcon = ' €';
let currentPage = 0;
let amountBooksInPage = 0;
let amountBooksInDb;

$(document).ready(function () {

    getLanguage();
    setLocaleFields();
    getPageWithBooks(currentPage++);
    openModalLoginWindowOnFailure();
    showSizeCart();
    loadWelcome(currentLang);
    showOrderSize();
});


function addBooksToPage(books) {
    $.each(books, function (index) {
        let card = `<div class="col mb-4">
                                    <a class="card border-0" href="/page/${books[index].id}" style="color: black">
                                        <img class="card-img-top mb-1" src="images/book${books[index].id}/${books[index].coverImage}" alt="Card image cap">
                                        <div class="card-body">
                                            <h5 class="card-title">${convertOriginalLanguageRows(books[index].nameAuthorDTOLocale, books[index].authorTranslit)}</h5>
                                            <h6 class="card-text text-muted">${convertOriginalLanguageRows(books[index].nameBookDTOLocale, books[index].nameTranslit)}</h6>
                                            <h5 class="card-footer bg-transparent text-left pl-0">${covertPrice(books[index].price) + currencyIcon}</h5>
                                            <div class="card-footer bg-transparent"></div>
                                        </div>
                                    </a>
                                    <div style="position: absolute; bottom: 5px; left: 15px; right: 15px" id="bottomInCart" type="button" 
                                            class="btn btn-success btn-metro"  data-id="${books[index].id}">
                                        ${addToshoppingCart}
                                    </div>
                                </div>`;
        $('#cardcolumns').append(card);
    });
}

function loadMore() {
    getPageWithBooks(currentPage++);
}

function getPageWithBooks(page) {
    GET(`/api/book?limit=4&start=${page}`)
        .then(json)
        .then((data) => {
            addBooksToPage(data.books);
            amountBooksInDb = data.amountOfBooksInDb;
            amountBooksInPage += data.size;
            if (amountBooksInPage === amountBooksInDb) {
                $(".load-more-btn").remove();
            }
            setAmountBooksInPage();
        })
}

function setAmountBooksInPage() {
    $(".books-in-page").text(amountBooksInPage);
    $(".books-in-db").text(amountBooksInDb);
}

function covertPrice(price) {
    return price / 100;
}

async function showSizeCart() {
    await fetch("/cart/size")
        .then(status)
        .then(json)
        .then(function (data) {
            if (data != 0) {
                $("#bucketIn").empty();
                $("#bucketIn1").empty();
                $("#bucketIn").append(data)
                $("#bucketIn1").append(data)
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
                $("#orders-quantity1").empty();
                $("#orders-quantity").append(data)
                $("#orders-quantity1").append(data)

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
