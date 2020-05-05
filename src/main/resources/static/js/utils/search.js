var currentLang = 'ru';

$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
    loadPage();
    setPageFields();
    setPageFieldsLocale();
});

function setPageFieldsLocale() {
    console.log(currentLang);
    fetch("/api/book/1" + "?locale=" + currentLang)
        .then(status)
        .then(json).then(function (data) {
        objectBook = data;
        tmpEditBookId = data.id;
        $('title').text(data.name);
        $('#book-name').text(data.name);
        $('#book-author').text(data.author);
        $('#book-edition').text(data.edition);
        $('#addToCart').attr('data-id', data.id);
        $("#book-desc").text(data.desc);
        $("#book-original-language").text(data.originalLanguage);
        $("#book-amount-of-pages").text(data.pages);
        $("#book-year-of-edition").text(data.yearOfEdition);
        $("#book-price").text(convertPrice(data.price) + ' €');
        buildCardImageOrCarousel();
    })
}

function status(response) {
    if (response.ok) {
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

function setPageFields() {
    let req = $("#request").attr("value");
    fetch("/searchResult?request=" + req + "&LANG=" + currentLang, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
        .then(status)
        .then(json)
        .then(function (data) {
            let tr = [];
            for (let i = 0; i < data.length; i++) {
                tr.push('<tr>');
                tr.push('<td class="align-middle"><img src="../images/book' + data[i].id + '/' + data[i].coverImage + '" style="max-width: 60px">');
                tr.push('<td class="align-middle">' + data[i].nameAuthorDTOLocale + '</td>>');
                tr.push('<td class="align-middle">' + data[i].nameBookDTOLocale + '</td>>');
                tr.push('<td class="align-middle"><form id="bookButton" method="get" action="/page/' + data[i].id + '">' +
                    '<button class="btn btn-primary pageOfBook" id="buttonBookPage" name="bookPage">A page of book</button></form></td>>');
                tr.push('</tr>');
            }
            $('table').append($(tr.join('')));
        });
}

function loadPage() {
    fetch("/searchResult?request=" + req + "&LANG=" + currentLang, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
        .then(response => response.json())
        .then(function (data) {

        });
}

