let row, primary;
let isCheckedCategory = false;
let checkboxes = [];
let currentPage = 0;
let amountBooksInPage = 0;
let amountBooksInDb;
let ddmAmountBook = $("#ddmAmountBook");
let isAdmin = false;

$(document).ready(async function () {
    await buildSearchPage();
});

async function buildSearchPage(){
    getLanguage();
    getCategoryTree();
    setListeners();
    setLocaleFields();
    amountBooksInPage = ddmAmountBook.text();
    getPageWithBooks(ddmAmountBook.text(), currentPage++);
    $(document).ready(function () {
        setTimeout(() => {
            $(".preloader").show();
        }, 300)
        setTimeout(() => {
            $(".preloader").hide();
        }, 1700)
    });
    getAUTH();
}

async function getQuantityPage() {
        if (amountBooksInDb < amountBooksInPage) {
            return 1;
        }
        return Math.ceil(amountBooksInDb / amountBooksInPage);
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
                    <ul class="pagination" style="margin: auto">`;
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
    $("#rowForPagination").html(pag);
}

function loadMore(pageNumber) {
    currentPage = pageNumber;
    advancedSearch(amountBooksInPage, pageNumber - 1)
}

// function getPageWithBooks(amount, page) {
//     GET(`/api/book?limit=${amount}&start=${page}`)
//         .then(json)
//         .then((data) => {
//             amountBooksInDb = data.amountOfBooksInDb;
//             addBooksToPage(data.books);
//         })
// }

function setAmountBooksInPage(amount) {
    currentPage = 0;
    amountBooksInPage = amount;
    ddmAmountBook.text(amount);
    advancedSearch(amount, 0);
}

function setListeners () {
    $('#search-submit').on('click', () => {
        currentPage = 0;
        advancedSearch(ddmAmountBook.text(), currentPage++);
        getCategoryTreeWithoutRefreshing();
        addPagination();
    });

    $('#input-categories').on('click', '.custom-control-input', function () {
        let $category = $(this).closest('.category');
        if ($(this).is(':checked')) {
            $category.find('.custom-control-input').prop('checked', true);
        } else {
            $category.find('.custom-control-input').prop('checked', false);
        }
    });

    $('#input-categories').on('click', 'label', function () {
        if ($(this).is('.collapsed')) {
            $(this).children('i').removeClass('fa fa-plus-square-o').addClass('far fa-minus-square');
        } else {
            $(this).children('i').removeClass('far fa-minus-square').addClass('fa fa-plus-square-o');
        }
    });

    $('#input-categories').on('change', '.custom-control-input', function () {
        const getCheckedSiblings = (nearCategory) => {
            let isCheckedSibling = false;
            nearCategory.siblings().each((i, elem) => {
                if ($(elem).children().children("input").prop("checked")) {
                    isCheckedSibling = true;
                    return;
                }
            });
            return isCheckedSibling;
        };
        const isChecked = $(this).is(':checked');
        let nearCategory = $(this).parent().parent();
        let isCheckedSiblings = getCheckedSiblings(nearCategory);
        do {
            if (isCheckedSiblings) {
                return;
            }
            nearCategory = nearCategory.parent().parent().parent();
            nearCategory.children().children("input").prop(":checked", isChecked);
            isCheckedSiblings = getCheckedSiblings(nearCategory);
        } while (nearCategory.parent().parent().parent().hasClass("category"));
        let $checkboxes = $('#input-categories');
        isCheckedCategory = $checkboxes.find('.custom-control-input').filter(':checked').length > 0;
    });

    $(document).keypress(function (event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
            $('#search-submit').click();
        }
    });

    $('#search-input').on('input', function () {
        currentPage = 0;
        advancedSearch(ddmAmountBook.text(), currentPage++);
    })

    $('#check-available').on('click', function () {
        getCategoryTreeWithoutRefreshing();
    })
}

async function getCategoryTreeWithoutRefreshing(){
    fetch('/categories/gettree/' + currentLang)
        .then(json)
        .then(function (json) {
            categoryArr = [];
            for (let i in json) {
                categoryId = json[i][0];
                categoryName = json[i][1];
                categoryPath = json[i][2];
                categoryParent = json[i][3];
                const category = {
                    id: categoryId,
                    categoryName: categoryName,
                    path: categoryPath,
                    parentId: categoryParent
                };
                categoryArr.push(category);
            }
            let category = getUnflatten(categoryArr, null);
            setTreeViewWithoutRefreshing(category);

        });
}
async function setTreeViewWithoutRefreshing(categories) {
    for (let category of categories) {
        row =
            `<div id="${category.id}" class="category text-nowrap">
                <div class="custom-control custom-checkbox form-check-inline" id="heading-${category.id}">
                    <input class="custom-control-input" type="checkbox" id="check-${category.id}" value="${category.id}" ${checkboxes.includes(`${category.id}`) ? 'checked' : ''}>
                    <label class="custom-control-label" for="check-${category.id}"></label>
                    <label class="collapsed" data-toggle="collapse" data-parent="#accordion" data-target="#collapse-${category.id}" aria-expanded="false" aria-controls="collapse-${category.id}">
                       <label id="${category.categoryName.toLowerCase()}-rightbar">${category.categoryName}</label>(${await getCountBooksByCat(category.path, $('#check-available').is(':checked') ? true : false)})
                       ${category.childrens !== undefined ? '<i class="fa fa-plus-square-o" aria-hidden="true"></i>' : ''}
                    </label>
                </div>`;
        if (category.childrens != undefined) {
            row +=
                `<div class="ml-3">
                    <div id="collapse-${category.id}" class="collapse" data-parent="#accordion" aria-labelledby="heading-${category.id}">
                    ${await setChilds(category.childrens)}
                    </div>
                </div>`;
        }
        row +=
            `</div>`;

        $('#'+category.id).replaceWith(row);
    }
}


async function getCategoryTree() {
    if (currentLang === '') {
        if (getCookieByName("lang")) {
            currentLang = getCookieByName("lang");
        } else {
            currentLang = 'en';
        }
    }

    fetch('/categories/gettree/' + currentLang)
        .then(json)
        .then(function (json) {
            categoryArr = [];
            for (let i in json) {
                categoryId = json[i][0];
                categoryName = json[i][1];
                categoryPath = json[i][2];
                categoryParent = json[i][3];
                const category = {
                    id: categoryId,
                    categoryName: categoryName,
                    path: categoryPath,
                    parentId: categoryParent
                };
                categoryArr.push(category);
            }
            let tree = getUnflatten(categoryArr, null);

            setTreeView(tree);
        });
}

function getUnflatten(arr, parentid) {
    let output = [];
    for (const category of arr) {
        if (category.parentId == parentid) {
            let children = getUnflatten(arr, category.id);
            if (children.length) {
                category.childrens = children
            }
            output.push(category)
        }
    }
    return output
}

async function setTreeView(categories) {
    for (let category of categories) {
        row =
            `<div id="${category.id}" class="category text-nowrap">
                <div class="custom-control custom-checkbox form-check-inline" id="heading-${category.id}">
                    <input class="custom-control-input" type="checkbox" id="check-${category.id}" value="${category.id}">
                    <label class="custom-control-label" for="check-${category.id}"></label>
                    <label class="collapsed" data-toggle="collapse" data-parent="#accordion" data-target="#collapse-${category.id}" aria-expanded="false" aria-controls="collapse-${category.id}">
                       <label id="${category.categoryName.toLowerCase()}-rightbar">${category.categoryName}</label>(${await getCountBooksByCat(category.path, $('#check-available').is(':checked') ? true : false)})
                       ${category.childrens !== undefined ? '<i class="fa fa-plus-square-o" aria-hidden="true"></i>' : ''}
                    </label>
                </div>`;
        if (category.childrens != undefined) {
            row +=
                `<div class="ml-3">
                    <div id="collapse-${category.id}" class="collapse" data-parent="#accordion" aria-labelledby="heading-${category.id}">
                    ${await setChilds(category.childrens)}
                    </div>
                </div>`;
        }
        row +=
            `</div>`;

        $('#input-categories').append(row);
    }
    setLocaleFields();
}

async function setChilds(categories) {
    let row = '';
    for (let category of categories) {
        if (category.childrens === undefined) {
            row +=
                `<div class="category text-nowrap">
                    <div class="custom-control custom-checkbox form-check-inline" id="heading-${category.id}">
                        <input class="custom-control-input" type="checkbox" id="check-${category.id}" value="${category.id}" ${checkboxes.includes(`${category.id}`) ? 'checked' : ''}>
                        <label class="custom-control-label" for="check-${category.id}">
                            <label class="${category.categoryName.toLowerCase()}-rightbar">${category.categoryName}</label>(${await getCountBooksByCat(category.path, $('#check-available').is(':checked') ? true : false)})
                        </label>
                    </div>
                </div>`;
        } else {
            row +=
                `<div class="category text-nowrap">
                    <div class="custom-control custom-checkbox form-check-inline" id="heading-${category.id}">
                        <input class="custom-control-input" type="checkbox" id="check-${category.id}" value="${category.id}" ${checkboxes.includes(`${category.id}`) ? 'checked' : ''}>
                        <label class="custom-control-label" for="check-${category.id}"></label>
                        <label class="collapsed" data-toggle="collapse" data-target="#collapse-${category.id}" data-parent="#accordion" aria-expanded="false" aria-controls="collapse-${category.id}">
                           <label class="${category.categoryName.toLowerCase()}-rightbar">${category.categoryName}</label>(${await getCountBooksByCat(category.path, $('#check-available').is(':checked') ? true : false)})
                           <i class="fa fa-plus-square-o" aria-hidden="true"></i>
                        </label>
                    </div>`;
            if (category.childrens != undefined) {
                row +=
                    `<div class="ml-3">
                    <div id="collapse-${category.id}" class="collapse" data-parent="#accordion" aria-labelledby="heading-${category.id}">
                    ${await setChilds(category.childrens)}
                    </div>
                </div>`;
            }
            row +=
                `</div>`;
        }
    }
    setLocaleFields();
    return row;
}
//проверяем чек категорий и запускаем поиск по ним
async function advancedSearch(amount, page) {
    let request = $('#search-input').val();
    let priceFrom = $('#input-price-from').val() * 100;
    let priceTo = $('#input-price-to').val() * 100;
    let yearOfEditionFrom = $('#input-year-of-edition-from').val() * 1;
    let yearOfEditionTo = $('#input-year-of-edition-to').val() * 1;
    let pagesFrom = $('#input-pages-from').val();
    let pagesTo = $('#input-pages-to').val();
    let searchBy = $('#search-by input:checked').val();
    let isShow = $('#check-available').is(':checked') ? true : false;
    let categories = [];
    let searchAdvanced = '';
    if (isCheckedCategory) {
        categories = $("#input-categories input:checked").map(function () {
            return $(this).val();
        }).get();
        searchAdvanced = "/searchAdvanced"
    } else {
        searchAdvanced = "/searchAdvancedAllCategories"
    }
    checkboxes = categories;
    let categoryRequest = "";
    for (let i in categories) {
        categoryRequest += "&categories=" + categories[i];
    }
    fetch(searchAdvanced + "?request=" + request + "&searchBy=" + searchBy + categoryRequest +
        "&priceFrom=" + priceFrom + "&priceTo=" + priceTo + "&yearOfEditionFrom=" + yearOfEditionFrom + "&yearOfEditionTo=" + yearOfEditionTo +
        "&pagesFrom=" + pagesFrom + "&pagesTo=" + pagesTo + "&page=" + page + "&size=" + amount + "&show=" + isShow, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
        .then(data => data.json())
        .then(function (data) {
            setLocaleFields();
            amountBooksInDb = data.amountOfBooksInDb;

            addFoundBooks(data.books);
        });
}

function getPageWithBooks(amount, page) {
    setTimeout(function(){
        if (window.location.pathname.split("/").pop() === "search") {
            let request = decodeURIComponent(window.location.search).split("=").pop();
            $('#search-input').val(request);
            let url = window.location.pathname;
            history.pushState(null, null, url);
            advancedSearch(amount, page);
        } else {
            let arrPathname = window.location.pathname.split("/")
            let checkId = '#check-' + arrPathname[arrPathname.length - 1];
            $(checkId).click();
            advancedSearch(amount, page);
            let tmp = [];
            tmp = window.location.pathname.split("/");
            let url = tmp.join("/");
            history.pushState(null, null, url);
        }
    },2000);

}

async function addFoundBooks(data) {
    $('#search-table-result').empty();
    let table = [];
    table.push(`<thead>
                        <tr>
                            <th></th>
                            <th id="name_search_page" class="name-loc">Name</th>
                            <th id="author_search_page" class="author-loc">Author</th>
                            <th id="pages_search_page" class="pages-loc">Pages</th>
                            <th id="edition_search_page" class="year-of-edition-loc">Year of edition</th>
                            <th id="price_search_page"><span class="price-loc">Price</span>, €</th>
                            <th id="category_search_page" class="categories-loc">Category</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>`);
    $('#search-table-result').append($(table.join('')));
    let tr = [];
    let lengthUrl = window.location.pathname.split("/").length;
    let length = lengthUrl - 3;
    let prePathUrl = '';
    for (let i = 0; i < length; i++) {
        prePathUrl += '../'
    }
    for (let i = 0; i < data.length; i++) {
        if (currentLang === '') {
            if (getCookieByName("lang")) {
                currentLang = getCookieByName("lang");
            } else {
                currentLang = 'en';
            }
        }

        let urlImage;
        if (data[i].coverImage == "") {
            urlImage = "/images/service/noimage.png";
        } else {
            urlImage = prePathUrl + `../images/book${data[i].id}/${data[i].coverImage}`;
        }
        if (data[i].yearOfEdition == null) {
            data[i].yearOfEdition = "-";
        } if(data[i].category.name[currentLang] == null) {
            data[i].category.name = "-";
        } if(data[i].pages == null) {
            data[i].pages = "-";
        } if(data[i].price == null) {
            data[i].price = "-";
        }
        tr.push(`<tr>
                                <td class="align-middle">
                                    <img src=${urlImage} style="max-width: 60px; ${data[i].show === true ? '' : 'opacity: 0.3'}">
                                </td>
                                <td class="align-middle">${convertOriginalLanguageRows(data[i].name, data[i].nameTranslit)}</td>
                                <td class="align-middle">
                                    ${data[i].show === true ? '' : '<img src="../../static/images/outOfStock.png" style="max-width: 35px;"><br>'}
                                    ${convertOriginalLanguageRows(data[i].author, data[i].authorTranslit)}
                                </td>
                                <td class="align-middle">${data[i].pages}</td>
                                <td class="align-middle">${data[i].yearOfEdition}</td>
                                <td class="align-middle">${data[i].price / 100}</td>
                                <td class="align-middle">${data[i].category.name[currentLang]}</td>
                                <td class="align-middle">
                                ${isAdmin && (window.location.pathname === '/admin/panel/books') ? 
                                    `
                                    <div id="search-admin">
                                        <button class="btn btn-info mb-1" style="height: 2.5rem; width: 3.5rem" onclick="openEdit(${data[i].id})"><i class="material-icons">edit</i></button>
                                        <button class="btn btn-danger" style="height: 2.5rem; width: 3.5rem" onclick="preDeleteBook(${data[i].id})"><i class="material-icons">delete</i></button>
                                    </div>
                                    ` : 
                                    `
                                    <button class="btn btn-primary page-of-book-localize" id="buttonBookPage${i}" onclick="location.href = '/page/${data[i].id}';" >
                                            Book's page
                                    </button>
                                `}
                                </td>
                            </tr>`
        );
    }
    $('#search-table-result').append($(tr.join('')));
    addPagination();
}

async function getCountBooksByCat(category, isShow) {
    return "" + await fetch("/categories/getcount?path=" + category
    + "&show=" + isShow).then(json);
}

async function getAUTH() {
    await GET("/api/current-user")
        .then(status)
        .then(json)
        .then(function (resp) {
            isAdmin = resp.roles.authority === 'ROLE_ADMIN';
        });
}
