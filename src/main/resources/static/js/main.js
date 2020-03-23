var currentLang = '';
var bottom = '';
$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
    buildPageByCurrentLang();
});

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
            bottom = localeFields['bookbotom'];
            $('#modalClose').text(localeFields['close']);
            $('#buttonBookPage').text(localeFields['pageofBook']);
        })
}

function buildLangPanel(x) {
    let selectedLang = x;
    fetch("/lang/" + selectedLang)
        .then(status)
        .then(text)
        .then(function (data) {
            currentLang = selectedLang;
            window.location.replace('home?LANG=' + currentLang);
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
                html += `<a class="dropdown-item lang" onclick="buildLangPanel('${listOfLanguage[language]}')" id="${listOfLanguage[language]}">
                            <img src="../static/icons/${listOfLanguage[language]}.png" 
                                alt="" height="16" width="16" class="lang-image"> - ${currentLangFull}
                         </a>`;
            }
            $('#dd_menu').html(html);
            $('#dd_menu_link').text(currentLang);
            $('#dd_menu_link').empty();
            $('#dd_menu_link').html(`<img src="../static/icons/${currentLang}.png"
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

function buildPageByCurrentLang() {
    $.ajax({
        url: "/admin/get20BookDTO/" + currentLang,
        method: 'GET',
    }).then(function (data) {
        $('#cardcolumns').empty();
        $.each(data, function (index) {
            let div = $('<div class="card"/>');
            div.append('<img class="card-img-top" src="images/book' + data[index].id + '/' + data[index].coverImage + '" alt="Card image cap">');
            let divBody = $('<div class="card-body" ></div>');
            divBody.append('<h4 class="card-title" style="overflow: auto; height:100px">' + data[index].nameAuthorDTOLocale + '</h4>');
            divBody.append('<p class="card-text">' + data[index].nameBookDTOLocale + '</p>');
            divBody.append('<br>');
            divBody.append('<a id="bookbotom"class="btn btn-primary" data-toggle="modal" data-target="#myModal" style="position:absolute;bottom:0; color:#39ff3b; " data-book-index="' + index + '">' + bottom + '</a>');
            div.append(divBody);
            div.appendTo('#cardcolumns');
        });
        $("#myModal").on('show.bs.modal', function (e) {
            let index = $(e.relatedTarget).data('book-index');
            $('#modalHeader').empty();
            $('#modalBody').empty();
            $('#modalHeader').append(data[index].nameAuthorDTOLocale);
            $('#modalBody').append('<p>' + data[index].nameBookDTOLocale + '</p>');
            $('#modalBody').append('<img class="card-img-top" src="images/book' + data[index].id + '/' + data[index].coverImage + '" alt="Card image cap">')
            $('#buttonOnBook').attr("action", '/page/' + data[index].id);
        });
    });
}



