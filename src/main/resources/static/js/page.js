var currentLang = '';
let pathImageDefault = '../images/book';
let objectBook;
let idCoverImage;

$(document).ready(function () {
    setCurrentLangFromSessionAttrLANG();
    getLanguage();
    setLocaleFields();
    setPageFields();
    openModalLoginWindowOnFailure();
});

function setPageFields() {
    $.ajax({
        url: "/page/id/" + $("#bookid").attr("value"),
        method: 'GET',
    }).then(function (data) {
        objectBook = data;
        $('#book-name').text(data.name[currentLang]);
        $('#book-author').text(data.author[currentLang]);
        $('#book-name1').text(data.name[currentLang]);
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
