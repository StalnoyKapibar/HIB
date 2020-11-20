let pathImageDefault = '../images/book';
let objectBook;
let idCoverImage;
let tmpEditBookId;

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
    // setPageFields();

})

function getCookie(name) {
    let value = "; " + document.cookie;
    let parts = value.split("; " + name + "=");
    if (parts.length === 2) return parts.pop().split(";").shift();
}

function setPageFields(event) {

    $('#page-content').css('display', 'none')
    $('#book-page').css('display', '')

    fetch($(event).attr('id') + "?locale=" + currentLang)
        .then(status)
        .then(json)
        .then(async function (data) {
        objectBook = data;
        tmpEditBookId = data.id;
        let listOrdersOfCart = [];
        listOrdersOfCart = await getListOrdersOfCart();
                if (listOrdersOfCart.includes(data.id)) {
                    $('#addToCart').removeClass("addToCartBtn").addClass("disabled").text(addedToshoppingCart).attr("disabled", "true");
                }
                if (!data.show) {
                    $('#addToCart').removeClass("addToCartBtn").removeClass("btn-warning").addClass("bought-btn-loc").addClass("btn-light").text(outOfStock).attr("disabled", "true");
                }
        $('title').text(data.name);
        $('#book-name').text(convertOriginalLanguageRows(data.name, data.nameTranslit));
        $('#book-author').text(convertOriginalLanguageRows(data.author, data.authorTranslit));
        $('#book-edition').text(convertOriginalLanguageRows(data.edition, data.editionTranslit));
        $('#addToCart').attr('data-id', data.id);
        $("#book-desc").html(data.description.ru);
        $("#book-original-language").text(data.originalLanguage);
        $("#book-amount-of-pages").text(data.pages);
        $("#book-year-of-edition").text(data.yearOfEdition);
        $("#book-price").text(convertPrice(data.price) + ' €');
        buildCardImageOrCarousel();
    })
}

function convertPrice(price) {
    return price / 100;
}

function buildCarousel() {
    if (objectBook.imageList.length === 0) {
        $('#picsParse').append(`<div class="carousel-item active">
                                            <a href="/images/service/noimage.png" target="_blank">
                                                <img id="bookcover" class="d-block w-100" style="object-fit: contain; height: 500px" src="/images/service/noimage.png" alt="No Image">
                                            </a>
                                        </div>`);
    }

    for (let i = 0; i < objectBook.imageList.length; i++) {
        if (objectBook.imageList[i].nameImage === objectBook.coverImage) {
            idCoverImage = i;
        }
    }

    for (let i = 0; i < objectBook.imageList.length; i++) {
        if (idCoverImage === undefined) {
            if (i === 0) {
                $('#picsParse').append(`<div class="carousel-item active">
                                            <a href="/images/service/noimage.png" target="_blank">
                                                <img id="bookcover" class="d-block w-100" style="object-fit: contain; height: 500px" src="/images/service/noimage.png" alt="No Image">
                                            </a>
                                        </div>
                                        <div class="carousel-item">
                                            <a href="${pathImageDefault}${objectBook.id}/${objectBook.imageList[i].nameImage}" target="_blank">
                                                <img class="d-block w-100" style="object-fit: contain; height: 500px" src="${pathImageDefault}${objectBook.id}/${objectBook.imageList[i].nameImage}" alt="...">
                                            </a>
                                        </div>`);
            } else {
                $('#picsParse').append(`<div class="carousel-item">
                                            <a href="${pathImageDefault}${objectBook.id}/${objectBook.imageList[i].nameImage}" target="_blank">
                                                <img class="d-block w-100" style="object-fit: contain; height: 500px" src="${pathImageDefault}${objectBook.id}/${objectBook.imageList[i].nameImage}" alt="...">
                                            </a>
                                        </div>`);
            }

        } else {
            if (i === 0 & idCoverImage === i) {
                $('#picsParse').append(`<div class="carousel-item active">
                                            <a href="${pathImageDefault}${objectBook.id}/${objectBook.imageList[i].nameImage}" target="_blank">
                                                <img id="bookcover" class="d-block w-100" style="object-fit: contain; height: 500px" src="${pathImageDefault}${objectBook.id}/${objectBook.imageList[i].nameImage}" alt="...">
                                            </a>
                                        </div>`);
            } else if (i === 0 & idCoverImage !== i){
                $('#picsParse').append(`<div class="carousel-item active">
                                            <a href="${pathImageDefault}${objectBook.id}/${objectBook.imageList[idCoverImage].nameImage}" target="_blank">
                                                <img id="bookcover" class="d-block w-100" style="object-fit: contain; height: 500px" src="${pathImageDefault}${objectBook.id}/${objectBook.imageList[idCoverImage].nameImage}" alt="...">
                                            </a>
                                        </div>
                                        <div class="carousel-item ">
                                            <a href="${pathImageDefault}${objectBook.id}/${objectBook.imageList[0].nameImage}" target="_blank">
                                                <img class="d-block w-100" style="object-fit: contain; height: 500px" src="${pathImageDefault}${objectBook.id}/${objectBook.imageList[0].nameImage}" alt="...">
                                            </a>
                                        </div>`);
            } else if (i !== 0 & idCoverImage !== i) {
                $('#picsParse').append(`<div class="carousel-item">
                                            <a href="${pathImageDefault}${objectBook.id}/${objectBook.imageList[i].nameImage}" target="_blank">
                                                <img class="d-block w-100" style="object-fit: contain; height: 500px" src="${pathImageDefault}${objectBook.id}/${objectBook.imageList[i].nameImage}" alt="...">
                                            </a>
                                        </div>`);
            }
        }
    }
}

function buildCardImageOrCarousel() {
    buildCarousel();
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

function checkParams() {
    if ($('#loginInput').val().length !== 0 && $('#passwordInput').val().length !== 0) {
        $('#sign_in_btn').removeAttr('hidden');
    } else {
        $('#sign_in_btn').attr('hidden', 'hidden');
    }
}

function doesFolderTmpExist() {
    fetch("admin/doesFolderTmpExist");
}

// Меняет информационое сообщение на разных языках о не корректности email
$('input').on('login-input-loc invalid', function() {
    this.setCustomValidity('')

    if (currentLang === 'en') {
        if (this.validity.typeMismatch) {
            this.setCustomValidity("Invalid email address entered")
        }
    }

    if (currentLang === 'ru') {
        if (this.validity.typeMismatch) {
            this.setCustomValidity("Введен некорректный email")
        }
    }


    if (currentLang === 'cs') {
        if (this.validity.typeMismatch) {
            this.setCustomValidity("Zadán nesprávný e-mail")
        }
    }

    if (currentLang === 'de') {
        if (this.validity.typeMismatch) {
            this.setCustomValidity("Falsche E-Mail eingegeben")
        }
    }

    if (currentLang === 'fr') {
        if (this.validity.typeMismatch) {
            this.setCustomValidity("Email incorrect entré")
        }
    }

    if (currentLang === 'gr') {
        if (this.validity.typeMismatch) {
            this.setCustomValidity("Ορίστηκε λάθος email")
        }
    }

    if (currentLang === 'it') {
        if (this.validity.typeMismatch) {
            this.setCustomValidity("Inserito email non corretta")
        }
    }
})