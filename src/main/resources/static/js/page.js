var currentLang = '';
let pathImageDefault = '../images/book';
let objectBook;
let idCoverImage;

$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
    setPageFields();
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

function setLocaleFields() {
    fetch("/properties/" + currentLang)
        .then(status)
        .then(json)
        .then(function (localeFields) {
            $('#link_main_footer').text(localeFields['main1']);
            $('#link_instruction').text(localeFields['instruction']);
            $('#link_authors').text(localeFields['authors']);
            $('#link_order').text(localeFields['order']);
            $('#link_contacts').text(localeFields['contacts']);
            $('#links').text(localeFields['links']);
            $('#made_by').text(localeFields['madeby']);
            $('#link_main_header').text(localeFields['main']);
            $('#link_books_header').text(localeFields['books']);
            $('#menu-toggle').text(localeFields['category']);
            $('#headpost').text(localeFields['headpost']);
        })
}

function buildLangPanel0(x) {
    let selectedLang = x;
    fetch("/lang/" + selectedLang)
        .then(status)
        .then(text)
        .then(function (data) {
            currentLang = selectedLang;
            window.location.replace($("#bookid").attr("value") + '?LANG=' + currentLang);
            //TODO some logic to processing data and reload page with chosen lang
        });
}

$("#menu-toggle").click(function (e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
});

function getLanguage() {
    function getFullNameOfLanguage(language) {
        switch (language) {
            case 'ru' :
                return 'Русский';
            case 'en' :
                return 'English';
            case 'de' :
                return 'Deutsch';
            case 'it' :
                return 'Italiano';
            case 'fr' :
                return 'Français';
            case 'cs' :
                return 'Český';
            case 'gr' :
                return 'Ελληνικά';
        }
        return "undef";
    }

    fetch("/lang")
        .then(status)
        .then(json)
        .then(function (listOfLanguage) {
            var currentLangFull = '';
            var html = '';
            for (language in listOfLanguage) {
                if (currentLang == (listOfLanguage[language])) {
                    continue;
                }
                currentLangFull = getFullNameOfLanguage(listOfLanguage[language]);
                html += `<a class="dropdown-item lang" onclick="buildLangPanel0('${listOfLanguage[language]}')" id="${listOfLanguage[language]}">
                            <img src="../static/icons/${listOfLanguage[language]}.png" 
                                alt="" height="16" width="16" class="lang-image"> - ${currentLangFull}
                         </a>`;
            }
            $('#dd_menu').html(html);
            $('#dd_menu_link').text(currentLang);
            $('#dd_menu_link').empty();
            $('#dd_menu_link').append(`<img src="../static/icons/${currentLang}.png"
                                alt="" height="16" width="16" class="lang-image">`);
        })
}

function status(response) {
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}

function json(response) {
    return response.json()
}

function text(response) {
    return response.text()
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