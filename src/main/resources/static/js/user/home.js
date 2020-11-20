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
    if ($.isNumeric(ddmAmountBook.text())) {
        getPageWithBooks(ddmAmountBook.text(), currentPage++);
    }
    loadWelcome(currentLang);


});

$(document).ready(function () {
    showAlertCookie();
})

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
    $('#page-content').css('display', '')
    $('[id^=e]').each(function (x, y) {
        console.log(y.id);
    })

    let i = $('[id^=e]')[0].id
    let ht = $(i).html()
    console.log(ht)

    let listOrdersOfCart = [];
    listOrdersOfCart = await getListOrdersOfCart();
    $('#cardcolumns').empty();
    $("#rowForPagination").empty();

    $.each(books, function (index) {
        let textOfBtn = listOrdersOfCart.includes(books[index].id) ? addedToshoppingCart : addToshoppingCart;
        let cssOfBtn = listOrdersOfCart.includes(books[index].id) ? "disabled" : "addToCartBtn";
        let coverImageLink;
        //Функция, подменяющая пустую обложку картинкой "noimage"
        if (books[index].coverImage == "") {
            coverImageLink = "/images/service/noimage.png";
        } else {
            coverImageLink = 'images/book' + books[index].id + '/' + books[index].coverImage;
        }
        let card = `<div class="col mb-5">
        <button type="button" class="card border-0" id="/api/book/${books[index].id}" style="color: black" onclick="setPageFields(this) ">
            <img class="card-img-top mb-1" src=${coverImageLink} style="object-fit: contain; height: 400px; ${books[index].show === true ? '' : 'opacity: 0.3;'}" alt="Card image cap">
            <div class="card-body" style="padding-bottom: 30px">
                <h7 class="card-title text-muted">${convertOriginalLanguageRows(books[index].nameAuthorDTOLocale, books[index].authorTranslit)}</h7>
                <h6>${convertOriginalLanguageRows(books[index].nameBookDTOLocale, books[index].nameTranslit)}</h6>
                <h6 class="card-footer bg-transparent text-left pl-0">${covertPrice(books[index].price) + currencyIcon}</h6>
            </div>
        </button>
        ${isAdmin ? `<div style="position: absolute; bottom: 5px; left: 15px; right: 15px" id="bottomEditBook" type="button" 
        class="btn btn-info"
        onclick="openEdit(${books[index].id})">                        
        ${editBook}
        </div>`
            : books[index].show === true
                ? `<button style="position: absolute; bottom: 5px; left: 15px; right: 15px" id="bottomInCart width: -moz-available"
                       class="btn btn-success ${cssOfBtn} btn-metro"  data-id="${books[index].id}">                        
                       ${textOfBtn}
                   </button>`
                : `<button style="position: absolute; bottom: 5px; left: 15px; right: 15px" id="bottomInCart" 
                       class="btn btn-light btn-metro bought-btn-loc"  data-id="${books[index].id}">                        
                       Out of stock
                   </button>`
        }
        </div>`;
        $('#cardcolumns').append(card);
    });
    addPagination();
    setLocaleFields();
}

function showAlertCookie() {

    if (getCookieByName('showBanner') === undefined) {
        document.cookie = "showBanner=true;";
    }

    if (getCookieByName('showBanner') === 'true') {
        let daysForDisabling = 365;

        Swal.fire({
            position: 'bottom-end',
            title: '<div><div class="use-cookie-loc" style="font-size: 22px; font-weight: bold;">We use cookie.</div>' +
                '<div class="use-cookie-text-loc" style="font-size: 14px; padding-top: 10px;">By continuing to use our site, you consent to the ' +
                'processing of cookies, which ensure the proper site functioning. Thanks to them, we managed to improve the ' +
                'site, service and products.</div></div>',
            width: 350
        })

        document.cookie = 'showBanner=false;path=/;max-age=' + daysForDisabling * 24 * 60 * 60 + ';';
    }
}

function openEdit(id) {
    window.open('/admin/edit/' + id, '_blank');
    //window.location.replace('/admin/edit/' + id);
}

async function getAUTH() {
    await GET("/api/current-user")
        .then(status)
        .then(json)
        .then(function (resp) {
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
    GET(`/api/book?limit=${amount}&start=${page}&locale=` + currentLang)
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
    localStorage.setItem("amountBooksPerPage", amount);
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
    })
});

function addToCart(id) {
    fetch("/cart/" + id, {
        method: "POST"
    }).then(function () {
        showSizeCart();
    }).then(function () {
        setPageFields();
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

if (typeof (Storage) != 'undefined') {
    let count = localStorage.getItem("amountBooksPerPage");
    if (count == null) {
        count = 10;
    }
    setAmountBooksInPage(count);
}