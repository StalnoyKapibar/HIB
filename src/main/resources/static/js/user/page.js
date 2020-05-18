var currentLang = 'en';
let pathImageDefault = '../images/book';
let objectBook;
let idCoverImage;
let tmpEditBookId;

$(document).ready(function () {

    getLanguage();
    setLocaleFields();
    setPageFields();
    showSizeCart();
});

function getCookie(name) {
    let value = "; " + document.cookie;
    let parts = value.split("; " + name + "=");
    if (parts.length === 2) return parts.pop().split(";").shift();
}

function setPageFields() {
    console.log(currentLang);
    fetch("/api/book/" + $("#bookid").attr("value") + "?locale=" + currentLang)
        .then(status)
        .then(json).then(async function (data) {
        objectBook = data;
        tmpEditBookId = data.id;
        let listOrdersOfCart = [];
        listOrdersOfCart = await getListOrdersOfCart();
        if (listOrdersOfCart.includes(data.id)){
            $('#addToCart').removeClass("btn-info").addClass("btn-primary disabled").text(addedToshoppingCart);
        }
        $('title').text(data.name);
        $('#book-name').text(convertOriginalLanguageRows(data.name, data.nameTranslit));
        $('#book-author').text(convertOriginalLanguageRows(data.author, data.authorTranslit));
        $('#book-edition').text(convertOriginalLanguageRows(data.edition, data.editionTranslit));
        $('#addToCart').attr('data-id', data.id);
        $("#book-desc").text(data.desc);
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

    var htmlBodyCarousel =
        `<div class="card">
       <div id="carouselImagePage" class="carousel slide 100 card-header" data-ride="carousel">

                    <ol class="carousel-indicators" id='testActive'>
                    </ol>
                    <div class="carousel-inner" id='testBody'>
                    </div></div> 
   
       <div class="buttonCarousel card-footer">
       <buttonCarousel class="left" href="#carouselImagePage"  data-slide="prev" > 
         <</buttonCarousel>     
       <buttonCarousel class="right "  href="#carouselImagePage" data-slide="next" > ></buttonCarousel></div>`;

    $('#CardImageOrCarousel').html(htmlBodyCarousel);
    $('#testActive').html(tmpHtmlForCarouselIndicators);
    $('#testBody').html(tmpHtmlForCarousel);

}

function buildCardImageOrCarousel() {
    buildCarousel();
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

$("body").on('click', '#addToCart', function () {
    let id = $(this).attr("data-id");
    addToCart(id);
    $('#addToCart').removeClass("btn-info").addClass("btn-primary disabled").text(addedToshoppingCart);
    setTimeout(function () {
        showSizeCart();
    }, 20)

});

function openEdit() {
    localStorage.setItem('tmpEditBookId', tmpEditBookId);
    window.open('/edit', '_blank');
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