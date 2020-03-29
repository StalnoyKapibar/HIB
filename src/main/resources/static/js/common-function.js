var bottom = '';

function sendSignInForm() {
    $('#hidden_submit_btn').click();
}

function setCurrentLangFromSessionAttrLANG() {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
}

function sendSingUpForm() {
    $('#hiddenSingUpBtn').click();
}

function setLocaleFields() {
    fetch("/properties/" + currentLang)
        .then(status)
        .then(json)
        .then(function (localeFields) {
            //  Footer
            $('#link_main_footer').text(localeFields['main1']);
            $('#link_instruction').text(localeFields['instruction']);
            $('#link_authors').text(localeFields['authors']);
            $('#link_order').text(localeFields['order']);
            $('#link_contacts').text(localeFields['contacts']);
            $('#links').text(localeFields['links']);
            $('#made_by').text(localeFields['madeby']);

            //   Header
            $('#link_main_header').text(localeFields['main']);
            $('#link_books_header').text(localeFields['books']);
            $('#menu-toggle').text(localeFields['category']);
            $('#headpost').text(localeFields['headpost']);

            //  Button card and card modal
            bottom = localeFields['bookbotom'];
            $('#modalClose').text(localeFields['close']);
            $('#buttonBookPage').text(localeFields['pageofBook']);

            //    Sign in form
            $('#modalLabel').text(localeFields['modalHeader']);
            $('#labelLogin').text(localeFields['login']);
            $('#labelPassword').text(localeFields['password']);
            $('#labelRememberMe').text(localeFields['rememberMe']);
            $('#registerBtn').text(localeFields['registerNewProfile']);
            $('#closeModalBtn').text(localeFields['close']);
            $('#signInBtn').text(localeFields['signIn']);
            $('#signInBtnText').text(localeFields['signIn']);
            $('#logoutButton').text(localeFields['logout']);
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
    if ($('#errorMessage').data('hasError')) {
        //delete parametrs after showing problem
        //open sign up modal window
        $('#signModal').modal('show');
    }
}

//just to delete an already displayed message
$(document).on('hidden.bs.modal','#signModal', function () {
    var errorMessage = $('#errorMessage');
    if (errorMessage.data('hasError')) {
        errorMessage.remove();
    }
});

// For smooth closing of header in mobile view when we click 'Category'
function checkSideBar() {
    if ($('#navbarCollapse').hasClass('show')) {
        $('#toggleBtn').click();
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