var currentLang = '';
var bottom = '';
var addToshoppingCart = '';
var deleteBottom = '';
function sendSignInForm() {
    $('#hidden_submit_btn').click();
}

function sendSingUpForm() {
    $('#hiddenSingUpBtn').click();
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
            bottom = localeFields['bookbotom'];
            addToshoppingCart = localeFields['addToshoppingCart'];
            deleteBottom = localeFields['deleteBottom'];
            $('#modalClose').text(localeFields['close']);
            $('#buttonBookPage').text(localeFields['pageofBook']);
            $('#dropdownclose').text(localeFields['dropdownclose']);
            $('#toshoppingcart').text(localeFields['toshoppingcart']);
            $('#bottomInCart').text(localeFields['addToshoppingCart']);
            $('#delete').text(localeFields['deleteBottom']);
            $('#quantity').text(localeFields['quantity']);
            $('#price').text(localeFields['price']);
            $('#book_author').text(localeFields['book_author']);
            $('#totalPrice').text(localeFields['totalprice']);
            $('#headershoppingcart').text(localeFields['headershoppingcart']);
            $('#chechout').text(localeFields['chechout']);
            $('#shoppingcart').text(localeFields['headershoppingcart']);
            $('.myprof-loc').text(localeFields['myprofile']);
            $('#oders').text(localeFields['oders']);
            $('#settingprofile').text(localeFields['settingprofile']);
            $('#enter-name').text(localeFields['enter-name']);
            $('#enter-email').text(localeFields['enter-email']);
            $('#enter-message').text(localeFields['enter-message']);
            $('.contact-us').text(localeFields['contact-us']);
            $('#ask-question').text(localeFields['ask-question']);
            $('.closeModalBtn').text(localeFields['close']);
            $('#send-feedback-request').text(localeFields['send-feedback-request']);
            $('#logout-modal-title').text(localeFields['logout']);
        })
}

//function for chose language
function buildLangPanel(x) {
    let selectedLang = x;
    fetch("/lang/" + selectedLang)
        .then(status)
        .then(text)
        .then(function (data) {
            currentLang = selectedLang;
            //TODO some logic to processing data and reload page with chosen lang
            window.location.reload();
        });
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
                html += `<a class="dropdown-item lang" onclick="buildLangPanel('${listOfLanguage[language]}')" id="${listOfLanguage[language]}">
                            <img src="../static/icons/${listOfLanguage[language]}.png" 
                                alt="" height="16" width="16" class="lang-image"> - ${currentLangFull}
                         </a>`;
            }
            $('#dd_menu').html(html);
            $('#dd_menu_link').text(currentLang);
            $('#dd_menu_link').empty();
            $('#dd_menu_link').html(`<img src="../static/icons/${currentLang}.png"
                                alt="" height="20" width="16" class="lang-image">`);
        })
}

function getURLVariable() {
    return new URLSearchParams(document.location.search);
}

// function for open modal window in case bad authentication and show information message
function openModalLoginWindowOnFailure() {
    if (getURLVariable().get('failure') != null) {
        //TODO: this part just for hide bad view of path. In commercial level we must change protocol and hostname
        var domainAddress = window.location.protocol + '//' + window.location.hostname + ':8080/home?login=failure';
        history.pushState(null, null, domainAddress);
        $('#signModalBtn').click();
    }
}

//function to hide components when event of mouse click is not on they area
$(function ($) {
    $(document).mouseup(function (e) {
        // for sidebar. If we click outside this area, sidebar must be hide

        var wrapper = $("#wrapper");
        var btnMenuToggle = $('#menu-toggle');
        if (btnMenuToggle.is(e.target) || !wrapper.is(e.target) & wrapper.has(e.target).length === 0 & wrapper.hasClass('toggled')) {
            wrapper.toggleClass("toggled");
        }

        //for navbar. If we click outside this area, when navbar is show, it must be hide
        var navbar = $('#navbarCollapse');
        if (!navbar.is(e.target) & navbar.has(e.target).length === 0 & navbar.hasClass('show')) {
            $('#toggleBtn').click();
        }
    });
});

// For smooth closing of header in mobile view when we click 'Category'
$('#menu-toggle').click(function (e) {
    var navbar = $('#navbarCollapse');
    if (navbar.hasClass('show')) {
        $('#toggleBtn').click();
    }
});

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

function setCurrentPageToCookie() {
    let cookie = 'CURRENT_PAGE =' + window.location.pathname + ';' +
    'path = /; max-age = 60';
    document.cookie = cookie;
}
