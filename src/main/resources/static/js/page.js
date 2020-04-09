var currentLang = '';
let pathImageDefault = '../images/book';
let objectBook;
let idCoverImage;
let tmpEditBookId;

$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
    setPageFields();
    showSizeCart();
});

function setPageFields() {
    fetch("/page/id/" + $("#bookid").attr("value"))
        .then(status)
        .then(json).then(function (data) {
        objectBook = data;
        tmpEditBookId = data.id;
        $('title').text(data.name[currentLang]);
        $('#book-name').text(data.name[currentLang]);
        $('#book-author').text(data.author[currentLang]);
        $('#book-name1').text(data.name[currentLang]);
        $('#bottomInCart').attr('data-id', data.id);
        buildCardImageOrCarousel();
    })
}

function buildCarousel() {
    for (var i = 0; i < objectBook.imageList.length; i++) {
        if (objectBook.imageList[i].nameImage === objectBook.coverImage) {
            idCoverImage = i;
        }
    }
    var tmpHtmlForCarouselIndicators = '';
    var tmpHtmlForCarousel = '';
    tmpHtmlForCarouselIndicators +=
        `<li id="qw${idCoverImage}" data-target='#carouselImagePage' data-slide-to=${idCoverImage} class='active'>` + `</li>`;
    tmpHtmlForCarousel +=
        `<div id="qw${idCoverImage}" class='carousel-item active'>` +
        `<img src=${pathImageDefault}${objectBook.id}/${objectBook.coverImage} class='d-block w-100' alt='...'>` +
        `<div class='carousel-caption d-none d-md-block'>` +
        `</div>` +
        `</div>`;
    for (var i = 0; i < objectBook.imageList.length; i++) {
        if (i !== idCoverImage) {
            tmpHtmlForCarouselIndicators +=
                `<li id="qw${i}" data-target='#carouselImagePage' data-slide-to=${i}></li>`;
            tmpHtmlForCarousel +=
                ` <div id="qw${i}" class="carousel-item">` +
                `<img src=${pathImageDefault}${objectBook.id}/${objectBook.imageList[i].nameImage} class='d-block w-100' alt="...">` +
                `<div class='carousel-caption d-none d-md-block'>` +
                `</div>` +
                `</div>`;
        }
    }

    var htmlBodyCarousel = `<div id="carouselImagePage" class="carousel slide w-50" data-ride="carousel">
                    <ol class="carousel-indicators" id='testActive'>
                    </ol>
                    <div class="carousel-inner" id='testBody'>
                    </div>
                    <a class="carousel-control-prev" href="#carouselImagePage" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselImagePage" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>`;
    $('#CardImageOrCarousel').html(htmlBodyCarousel);
    $('#testActive').html(tmpHtmlForCarouselIndicators);
    $('#testBody').html(tmpHtmlForCarousel);
}

function buildCardImageOrCarousel() {
    if (objectBook.imageList.length === 1) {
        buildCardImage();
    } else {
        buildCarousel();
    }
}

function buildCardImage() {
    $('#CardImageOrCarousel').html(`<img id='bookImg' src=${pathImageDefault}${objectBook.id}/${objectBook.coverImage} alt='Card image cap'>`);
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

function openEdit() {
    localStorage.setItem('tmpEditBookId', tmpEditBookId);
    window.open('/edit', '_blank');
}
