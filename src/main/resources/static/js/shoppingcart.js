var currentLang = '';
var bottom = '';
$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
    showSizeCart();
    getCart();
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
            //      $('#headpost').text(localeFields['headpost']);
            bottom = localeFields['bookbotom'];
            $('#modalClose').text(localeFields['close']);
            $('#buttonBookPage').text(localeFields['pageofBook']);
        })
}

$('#dd_menu').on('click', 'a', function (eventOnInnerTag) {
    eventOnInnerTag.preventDefault();
    const selectedLang = $(eventOnInnerTag.target).attr('id');
    fetch("/lang/" + selectedLang)
        .then(status)
        .then(text)
        .then(function (data) {
            currentLang = selectedLang;
            window.location.replace('shoppingcart?LANG=' + currentLang);
            getLanguage();
            getLocaleFields();
        });
    location.reload();
});

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
                html += `<a class="dropdown-item lang" id="${listOfLanguage[language]}">
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

function showSizeCart() {
    $.get("/cart/size").then(function (data) {
        if (data != 0) {
            $("#bucketIn").empty();
            $("#bucketIn").append(data)
        } else {
            $('#bucketIn').empty();
        }
    });
}

function getCart() {
    $.ajax({
        url: "/cart",
        method: 'GET',
    }).then(function (data) {
        $('#newTab').empty();
        $.each(data, function (key, value) {
            let book = getBookDTO(key);
            let row = $('<tr id="trr"/>');
            let cell = $('<td width="10"></td>');
            row.append(cell);
            cell = '<td class="align-middle"><img src="../images/book' + book.id + '/' + book.coverImage + '" style="max-width: 60px"></td>'+
            '<td class="align-middle">' + book.name[currentLang] + ' | ' + book.author[currentLang] + '</td>' +
                '<td class="align-middle ">'+book.price*value+'</td>' +
                '<td class="align-middle ">' + value + '</td>'+
                '<td class="align-middle" ><button class="btn btn-info delete"  style="background-color: orangered" data-id="'+ book.id +'">' + 'Delete' + '</button></td>';
            row.append(cell);
            cell = $('<td/>');
            row.append(cell);
            row.appendTo('#newTab')
        });
    });
}

function getBookDTO(id) {
    let res;
    $.ajax({
        url: "/getBookDTOById/" + id,
        async: false,
        method: 'GET',
        success: function (data) {
            res = data;
        }
    });
    return res;
}
$(document).ready(function () {
       $("body").on('click', '.delete', function () {
        let id = $(this).attr("data-id");
        $.ajax({
            url: '/cart/' + id,
            type: 'DELETE',
            data: id,
            success: function () {
                showSizeCart();
                getCart();
            },
        })
    })
})

