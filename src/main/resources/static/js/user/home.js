var currentLang = '';
var bottom = '';
var addToshoppingCart = '';
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
    getAUTH();
    getLanguage();
    setLocaleFields();
    amountBooksInPage = ddmAmountBook.text();
    getPageWithBooks(ddmAmountBook.text(), currentPage++);
    openModalLoginWindowOnFailure();
    loadWelcome(currentLang);
});

function getQuantityPage() {
    if (amountBooksInDb < amountBooksInPage) {
        return 1;
    }
    return Math.ceil(amountBooksInDb / amountBooksInPage);
}

  async function addBooksToPage(books) {
      let listOrdersOfCart = [];
      listOrdersOfCart = await getListOrdersOfCart();
      $('#cardcolumns').empty();
      $("#rowForPagination").empty();
      $.each(books, function (index) {
          let textOfBtn = listOrdersOfCart.includes(books[index].id) ? addedToshoppingCart : addToshoppingCart;
          let cssOfBtn = listOrdersOfCart.includes(books[index].id) ? "disabled" : "addToCartBtn";
          let card = `<div class="col mb-4">
                                    <a class="card border-0" href="/page/${books[index].id}" style="color: black">
                                        <img class="card-img-top mb-1" src="images/book${books[index].id}/${books[index].coverImage}" style="object-fit: cover; height: 400px; " alt="Card image cap">
                                        <div class="card-body" style="padding-bottom: 30px">
                                            <h5 class="card-title">${convertOriginalLanguageRows(books[index].nameAuthorDTOLocale, books[index].authorTranslit)}</h5>
                                            <h6 class="card-text text-muted">${convertOriginalLanguageRows(books[index].nameBookDTOLocale, books[index].nameTranslit)}</h6>
                                            <h5 class="card-footer bg-transparent text-left pl-0">${covertPrice(books[index].price) + currencyIcon}</h5>
                                            
                                        </div>
                                    </a>
                                    ${isAdmin ? `<div style="position: absolute; bottom: 5px; left: 15px; right: 15px" id="bottomEditBook" type="button" 
                                                    class="btn btn-info"
                                                    onclick="openEdit(${books[index].id})"
                                                  >                        
                                                    ${editBook}
                                                  </div>`:
                                                `<div style="position: absolute; bottom: 5px; left: 15px; right: 15px" id="bottomInCart" type="button" 
                                                      class="btn btn-success ${cssOfBtn} btn-metro"  data-id="${books[index].id}">                        
                                                    ${textOfBtn}
                                                </div>`}
                                </div>`;
          $('#cardcolumns').append(card);
      });
      addPagination();
  }

function openEdit(id) {
    localStorage.setItem('tmpEditBookId', id);
    window.open('/edit', '_blank');
}

async function getAUTH() {
    await GET("/api/current-user")
        .then(status)
        .then(json)
        .then(function (resp) {
            isAdmin = resp.roles.authority === 'ROLE_ADMIN';
        });
}

function addPagination() {
    let numberOfPagesInPagination = 7;
    let quantityPage = getQuantityPage();
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

async function getAllBooksForLiveSearch() {
    const url ='/api/allBookForLiveSearch';
    const res = await fetch(url);
    const data = await res.json();
    return data;
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

//функция поиска совпадений вводимых символов
function findEl(el, array, value) {
    var coincidence = false;
    el.empty();//очищаем список совпадений
    for (var i = 0; i < array.length; i++){
        if (array[i].match('^'+value)){//проверяем каждый елемент на совпадение побуквенно
            el.children('li').each(function (){//проверяем есть ли совпавшие елементы среди выведенных
                if(array[i] === $(this).text()) {
                    coincidence = true;//если есть совпадения то true
                }
            });
            if(coincidence === false){
                el.append('<li class="js-searchInput">'+array[i]+'</li>');//если нету совпадений то добавляем уникальное название в список
            }
        }
    }
}

// var filterInput = $('#filter-books'),
var filterInput = $('#searchInput'),

    filterUl = $('.ul-books');
//проверка при каждом вводе символа
filterInput.bind('input propertychange', async function(){
    if($(this).val() !== ''){
        filterUl.fadeIn(100);
        let data = await getAllBooksForLiveSearch();
        let array = [];
        for (let key in data) {
            if (data.hasOwnProperty(key)) {
                let book = data[key];
                for( let field in book) {
                    const value = book[field];
                    if(typeof value === 'string') {
                        array.push(value);
                    }
                }
            }
        }
        findEl(filterUl, array, $(this).val());
    }
    else{
        filterUl.fadeOut(100);
    }
});
//при клике на елемент выпадалки присваиваем значение в инпут и ставим триггер на его изменение
filterUl.on('click','.js-searchInput', function(e){
    $('#searchInput').val('');
    filterInput.val($(this).text());
    filterInput.trigger('change');
    filterUl.fadeOut(100);
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