var currentLang = '';
var bottom = '';
var addToshoppingCart = '';
var outOfStock = '';
let editBook = '';
var addedToshoppingCart = '';
var deleteBottom = '';
let welcomeBlock = $("#welcome");
let currencyIcon = ' €';
let currentPage = 0;
let amountBooksInPage = 0;
let amountBooksInDb;
let ddmAmountBook = $("#ddmAmountBook");
let isAdmin = false;

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

    getAUTH();
    amountBooksInPage = ddmAmountBook.text();
    if($.isNumeric(ddmAmountBook.text())){
        getPageWithBooks(ddmAmountBook.text(), currentPage++);
    }
    openModalLoginWindowOnFailure();
    loadWelcome(currentLang);

});

async function getQuantityPage() {
    const url = '/getPageBooks';
    const res = await fetch(url);
    const data = await res.json();
    if (data.length < amountBooksInPage) {
        return 1;
    }
    return Math.ceil(data.length / amountBooksInPage);
}

async function addBooksToPage(books) {
    let listOrdersOfCart = [];
    listOrdersOfCart = await getListOrdersOfCart();
    $('#cardcolumns').empty();
    $("#rowForPagination").empty();

    console.log(books);

    $.each(books, function (index) {
        let textOfBtn = listOrdersOfCart.includes(books[index].id) ? addedToshoppingCart : addToshoppingCart;
        let cssOfBtn = listOrdersOfCart.includes(books[index].id) ? "disabled" : "addToCartBtn";
        let card = `<div class="col mb-4">
                                    <a class="card border-0" href="/page/${books[index].id}" style="color: black">
                                        
                                        <img class="card-img-top mb-1" src="images/book${books[index].id}/${books[index].coverImage}" style="object-fit: contain; height: 400px; ${books[index].show === true ? '' : 'opacity: 0.3;'}" alt="Card image cap">
                                        
                                        <div class="card-body" style="padding-bottom: 30px">
                                            <h6 class="card-title">${convertOriginalLanguageRows(books[index].nameBookDTOLocale, books[index].nameTranslit)}</h6>
                                            <h7 class="card-text text-muted">${convertOriginalLanguageRows(books[index].nameAuthorDTOLocale, books[index].authorTranslit)}</h7>
                                            <h6 class="card-footer bg-transparent text-left pl-0">${covertPrice(books[index].price) + currencyIcon}</h6>
                                            
                                        </div>
                                    </a>
                                    ${isAdmin ? `<div style="position: absolute; bottom: 5px; left: 15px; right: 15px" id="bottomEditBook" type="button" 
                                                    class="btn btn-info"
                                                    onclick="openEdit(${books[index].id})">                        
                                                    ${editBook}
                                                  </div>` 
                                              : books[index].show === true 
                                                ? `<div style="position: absolute; bottom: 5px; left: 15px; right: 15px" id="bottomInCart" type="button" 
                                                      class="btn btn-success ${cssOfBtn} btn-metro"  data-id="${books[index].id}">                        
                                                    ${textOfBtn}
                                                </div>`
                                                : `<div style="position: absolute; bottom: 5px; left: 15px; right: 15px" id="bottomInCart" type="button" 
                                                      class="btn btn-light btn-metro bought-btn-loc"  data-id="${books[index].id}">                        
                                                    Out of stock
                                                </div>`
                                    }
                                 </div>`;
        $('#cardcolumns').append(card);
    });
    addPagination();
    setLocaleFields();
}

function openEdit(id) {
    window.open('/admin/edit/' + id, '_blank');
}

async function getAUTH() {
    await GET("/api/current-user")
        .then(status)
        .then(json)
        .then(function (resp) {
            console.log(resp)
            isAdmin = resp.roles.authority === 'ROLE_ADMIN';
        });
}

async function addPagination() {
    let numberOfPagesInPagination = 7;
    let quantityPage = await getQuantityPage();
    let startIter;
    let endIter = currentPage;
    let pag;
    let halfPages = Math.floor(numberOfPagesInPagination / 2);
    if (quantityPage <= numberOfPagesInPagination || quantityPage === 0) {
        startIter = 1;
        endIter = quantityPage;
    } else {
        if (currentPage - halfPages <= 0) {
            startIter = 1;
            endIter = numberOfPagesInPagination;
        } else if (currentPage + halfPages > quantityPage) {
            startIter = quantityPage - numberOfPagesInPagination + 1;
            endIter = quantityPage;
        } else {
            startIter = currentPage - halfPages;
            endIter = currentPage + halfPages;
        }
    }
    pag = `<nav aria-label="Page navigation example">
                    <ul class="pagination">`;
    pag += currentPage === 1 ? `<li class="page-item disabled"><a class="page-link" href="#" tabindex="-1">` :
        `<li class="page-item"><a class="page-link" onclick="loadMore(1)" href="#">`;
    pag += `<span aria-hidden="true">&laquo;</span></a></li>`;
    for (let i = startIter; i < endIter + 1; i++) {
        if (currentPage === i) {
            pag += `<li class="page-item active"><a class="page-link" onclick="loadMore(${i})">${i}</a></li>`;
        } else {
            pag += `<li class="page-item"><a class="page-link" onclick="loadMore(${i})">${i}</a></li>`;
        }
    }
    pag += currentPage === quantityPage ? `<li class="page-item disabled">` : `<li class="page-item">`
    pag += `<a class="page-link" onclick="loadMore(${quantityPage})" href="#"><span aria-hidden="true">&raquo;</span></a></li>
                    </ul>
                </nav>`;
    $("#rowForPagination").append(pag);
}

function loadMore(pageNumber) {
    currentPage = pageNumber;
    getPageWithBooks(amountBooksInPage, pageNumber - 1);
}

function getPageWithBooks(amount, page) {
    GET(`/api/book?limit=${amount}&start=${page}`)
        .then(json)
        .then((data) => {
            amountBooksInDb = data.amountOfBooksInDb;
            addBooksToPage(data.books);
        })
}

function setAmountBooksInPage(amount) {
    amountBooksInPage = amount;
    ddmAmountBook.text(amount);
    getPageWithBooks(amount, 0);
}

function covertPrice(price) {
    return price / 100;
}

$(document).ready(function () {
    $("body").on('click', '.addToCartBtn', function () {
        let id = $(this).attr("data-id");
        addToCart(id);
        $(this).removeClass("addToCartBtn")
            .addClass("disabled")
            .attr('disabled', 'true')
            .text(addedToshoppingCart);
        setTimeout(function () {
            showSizeCart();
        }, 100)
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

async function getListOrdersOfCart() {
    let listOrdersOfCart = [];
    await POST("/cart")
        .then(json)
        .then(function (data) {
            $.each(data, function (index) {
                listOrdersOfCart[index] = data[index].book.id;
            });
        });
    return listOrdersOfCart;
}
