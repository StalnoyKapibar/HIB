var currentLang = '';
var bottom = '';
$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
    openModalLoginWindowOnFailure();
});

$('#dd_menu').on('click', 'a', function (eventOnInnerTag) {
    eventOnInnerTag.preventDefault();
    const selectedLang = $(eventOnInnerTag.target).attr('id');
    fetch("/lang/" + selectedLang)
        .then(status)
        .then(text)
        .then(function (data) {
            currentLang = selectedLang;
            //TODO some logic to processing data and reload page with chosen lang
            getLanguage();
        });
    document.location.reload();
});

$("#menu-toggle").on('click', function (e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
});

$(document).on('click', "#sign_in_btn", function (e) {
    $("#hidden_submit_btn").click();
});

$(document).on('click', "#singUpButton", function (e) {
    $('#hiddenSingUpButton').click();
});

$(document).on('click', "#link_main_header", function (e) {
    document.location.href = '/home';
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
                html += `<a class="dropdown-item lang" id="${listOfLanguage[language]}">
                            <img src="../static/icons/${listOfLanguage[language]}.png" 
                                alt="" height="20" width="18" class="lang-image"> - ${currentLangFull}
                         </a>`;
            }
            $('#dd_menu').html(html);
            $('#dd_menu_link').text(currentLang);
            $('#dd_menu_link').empty();
            $('#dd_menu_link').append(`<img src="../static/icons/${currentLang}.png"
                                alt="" height="20" width="18" class="lang-image">`);
        })
}

function openModalLoginWindowOnFailure() {
    if (getURLVariable().get('failure') != null) {
        //TODO: this part just for hide bad view of path. In commercial level we must change protocol and hostname
        var domainAddress = window.location.protocol + '//' + window.location.hostname + ':8080/home?login=failure';
        history.pushState(null, null, domainAddress);
        $('#signModalBtn').click();
    }
}

function getURLVariable() {
    return new URLSearchParams(document.location.search);
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
$(document).ready(function() {
    setTimeout(function() {
        $.ajax({
            url: "/admin/get20BookDTO/"+currentLang,
            method: 'GET',
        }).then(function(data) {
            $('#cardcolumns').empty();
            $.each(data, function(index) {
                let div = $('<div class="card"/>');
                div.append('<img class="card-img-top" src="images/book'+data[index].id+'/'+data[index].coverImage+'" alt="Card image cap">');
                let divBody = $('<div class="card-body" ></div>');
                divBody.append('<h4 class="card-title" style="overflow: auto; height:100px">'+data[index].nameAuthorDTOLocale+'</h4>');
                divBody.append('<p class="card-text">'+data[index].nameBookDTOLocale+'</p>');
                divBody.append('<br>');
                divBody.append('<a id="bookbotom"class="btn btn-primary" data-toggle="modal" data-target="#myModal" style="position:absolute;bottom:0; color:white; " data-book-index="'+index+'">'+bottom+'</a>');
                div.append(divBody);
                div.appendTo('#cardcolumns');
            });
            $("#myModal").on('show.bs.modal', function(e) {
                let index = $(e.relatedTarget).data('book-index');
                $('#modalHeader').empty();
                $('#modalBody').empty();
                $('#modalHeader').append(data[index].nameAuthorDTOLocale);
                $('#modalBody').append('<p>'+data[index].nameBookDTOLocale+'</p>');
                $('#modalBody').append('<img class="card-img-top" src="images/book'+data[index].id+'/'+data[index].coverImage+'" alt="Card image cap">')
                $('#buttonOnBook').attr("action",'/page/'+ data[index].id);
            });
        });
    }, 10);
    });




