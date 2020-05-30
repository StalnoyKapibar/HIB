var currentLang = '';
var bottom = '';
var addToshoppingCart = '';
var deleteBottom = '';

$(document).ready(function () {
    if (currentLang === '') {
        if (getCookieByName("lang")) {
            currentLang = getCookieByName("lang");
        } else {
            currentLang = 'en';
        }
    }
})

function sendSignInForm() {
    $('#hidden_submit_btn').click();
}

function sendSingUpForm() {
    $('#hiddenSingUpBtn').click();
}

async function setLocaleFields() {
    fetch("/properties/" + currentLang)
        .then(status)
        .then(json)
        .then(function (localeFields) {
            //Со страницы 1-click-reg
            $('#link_instruction').text(localeFields['instruction']);
            $('#link_order').text(localeFields['order']);
            $('#link_authors').text(localeFields['authors']);
            $('#links').text(localeFields['links']);
            $('#link_contacts').text(localeFields['contacts']);

            //footer
            $('#main-footer').text(localeFields['main1']);
            $('#users-manual-footer').text(localeFields['instruction']);
            $('#how-to-order-footer').text(localeFields['order']);
            $('#authors-list-footer').text(localeFields['authors']);
            $('#theme-links-footer').text(localeFields['links']);
            $('#contact-locations-footer').text(localeFields['contacts']);
            $('#made_by').text(localeFields['madeby']);

            //Левый sidebar на главной
            $('#history-sidebar').text(localeFields['history-sb']);
            $('#documents-sidebar').text(localeFields['documents-sb']);
            $('#magazines-sidebar').text(localeFields['magazines-sb']);
            $('#culture-sidebar').text(localeFields['culture-sb']);
            $('#language-left-panel').text(localeFields['language']);

            //Боковая панель с категориями
            $('#history-rightbar').text(localeFields['history-sb']);
            $('#documents-rightbar').text(localeFields['documents-sb']);
            $('#magazines-rightbar').text(localeFields['magazines-sb']);
            $('#culture-rightbar').text(localeFields['culture-sb']);
            $('.forein-rightbar').text(localeFields['forein-sb']);
            $('.greek-rightbar').text(localeFields['greek-sb']);
            $('.from-form').attr('placeholder', localeFields['from']);
            $('.to-form').attr('placeholder', localeFields['to']);

            //Админ
            $('.admin-loc').text(localeFields['admin']);
            $('.admin-panel-loc').text(localeFields['adminPanel']);

            //Кусок с показом заказанного перед покупкой, либо просмотр заказа в обработке
            $('.back-btn').text(localeFields['back']);
            $('.next-btn').text(localeFields['next']);
            $('.items-loc').text(localeFields['items']);
            $('.unit-price-loc').text(localeFields['unitPrice']);
            $('.subtotal-loc').text(localeFields['subtotal']);
            $('.pricetotal-loc').text(localeFields['totalPrice']);
            $('.buynow-btn').text(localeFields['buyNow']);
            $('.purchase-loc').text(localeFields['purchase']);
            $('#1click-reg-btn').text(localeFields['1clickreg']);
            $('.your-loc').text(localeFields['your']);
            $('.contacts-loc').text(localeFields['contacts-strong']);

            //cabinet.html
            $('.shopping-cart-loc').text(localeFields['ShoppingCart']);
            $('.in-progress-loc').text(localeFields['inProgress']);
            $('.delivered-loc').text(localeFields['delivered']);
            $('.setting-profile-loc').text(localeFields['settProf']);
            $('.personal-info-loc').text(localeFields['persInfo']);
            $('.password-change-loc').text(localeFields['passChange']);
            $('.save-loc').text(localeFields['save']);
            $('.first-name-loc').text(localeFields['firstName']);
            $('.last-name-loc').text(localeFields['lastName']);
            $('.old-pass-loc').text(localeFields['oldPass']);   //Enter old password
            $('.new-pass-loc').text(localeFields['newPass']);     //Enter new password
            $('.again-pass-loc').text(localeFields['againPass']);     //Enter new password again
            $('.email').attr('placeholder', localeFields['email']);
            $('.email-label').text(localeFields['email']);
            $('.phone').attr('placeholder', localeFields['phone']);
            $('.phone-label').text(localeFields['phone']);
            $('.comment').attr('placeholder', localeFields['comment']);
            $('.comment-label').text(localeFields['comment']);

            //resetPassword.js
            $('.notRegistredEmail').text(localeFields['notRegistredEmail']);
            $('.checkEmail').text(localeFields['checkEmail']);

            //profile.js and newPass.js
            $('.messSuccess').text(localeFields['messSuccess']);
            $('.incorrectDataPassword').text(localeFields['incorrectDataPassword']);
            $('.wrongCurrentPass').text(localeFields['wrongCurrentPass']);
            $('.passSuccessSaved').text(localeFields['passSuccessSaved']);
            $('.emailUsedByUser').text(localeFields['emailUsedByUser']);
            $('.invalidEmailFormat').text(localeFields['invalidEmailFormat']);
            $('.dontMatchPass').text(localeFields['dontMatchPass']);
            $('.notValidUrl').text(localeFields['notValidUrl']);


            //user.html
            $('.signin-loc').text(localeFields['signin']);
            $('.my-orders-loc').text(localeFields['oders']);
            $('.myprof-loc').text(localeFields['myprofile']);
            $('.checkboxRememberMe-loc').text(localeFields['rememberMe']);
            $('.login-loc').text(localeFields['login']);
            $('.contact-us').text(localeFields['contact-us']);
            $('.ask-question-loc').text(localeFields['ask-question']);

            //Страница с обрабатываемыми заказами со стороны пользователя
            $(".orders-loc").text(localeFields['orders']);
            $(".orderNo-loc").text(localeFields['orderNo']);
            $(".dateOfOrder-loc").text(localeFields['dateOfOrder']);
            $(".orderStatus-loc").text(localeFields['orderStatus']);
            $(".show-btn").text(localeFields['show']);

            //Регистрация
            //$('#exampleModalLabel').text(localeFields['signin']);
            $('.signin-loc').text(localeFields['signin']);
            $('.login-input-loc').attr('placeholder', localeFields['login']);
            $('.password-input-loc').attr('placeholder', localeFields['password']);
            $('.password-input-label').text(localeFields['password']);
            $('.register-new-btn').text(localeFields['registerNew']);
            $('.forgot-pass-btn').text(localeFields['forgotPass']);
            $('.signup-google-btn').text(localeFields['signupGoogle']);
            $('.signup-fb-btn').text(localeFields['signupFB']);

            //search.html and search.js
            $('#search_by_search_page_menu').text(localeFields['searchBy']);
            $('#name_author_search_by').text(localeFields['titleAndAuthor']);
            $('.search-input-dots-loc').attr('placeholder', localeFields['searchDots']);
            $('#name_search_by').text(localeFields['name']);
            $('#author_search_by').text(localeFields['authorr']);
            $('#categories_search_page_menu').text(localeFields['category']);
            $('#price_search_page_menu').text(localeFields['price']);
            $('#edition_search_page_menu').text(localeFields['yearOfEdition']);
            $('#pages_search_page_menu').text(localeFields['numberOfpages']);
            $('.search-submit-loc').text(localeFields['search']);
            $('#author_search_page').text(localeFields['Author']);
            $('#name_search_page').text(localeFields['Name']);
            $('#pages_search_page').text(localeFields['pages']);
            $('#edition_search_page').text(localeFields['yearOfEdition']);
            $('#price_search_page').text(localeFields['Price']);
            $('#category_search_page').text(localeFields['category']);

            //other
            $('#header-general-loc').text(localeFields['general']);
            $('#header-settings-loc').text(localeFields['settings']);
            $('#link_search_page_header').text(localeFields['searchPage']);
            $('#link_main_header').text(localeFields['main']);
            $('#link_books_header').text(localeFields['books']);
            $('#categories').text(localeFields['category']);
            $('#headpost').text(localeFields['headpost']);
            bottom = localeFields['bookbotom'];
            addToshoppingCart = localeFields['addToshoppingCart'];
            addedToshoppingCart = localeFields['addedToshoppingCart'];
            editBook = localeFields['editBook'];
            deleteBottom = localeFields['deleteBottom'];
            $('#modalClose').text(localeFields['close']);
            $('#book-on-page-loc').text(localeFields['bookOnPage']);
            $('.page-of-book-localize').text(localeFields['pageofBook']);
            $('#dropdownclose').text(localeFields['dropdownclose']);
            $('#toshoppingcart').text(localeFields['toshoppingcart']);
            $('.bottomInCart').text(localeFields['addToshoppingCart']);
            $('.addToCart').text(localeFields['addToshoppingCart']);
            $('#delete').text(localeFields['deleteBottom']);
            $('#quantity').text(localeFields['quantity']);
            $('#price').text(localeFields['price']);
            $('#book_author').text(localeFields['book_author']);
            $('#totalPrice').text(localeFields['totalprice']);
            $('#headerShoppingCart').text(localeFields['headershoppingcart']);
            $('#home-tab').text(localeFields['YourShoppingCart']);
            $('#contacts-tab').text(localeFields['YourContacts']);
            $('#summary-tab').text(localeFields['Summary']);
            $('#enter_your_contacts').text(localeFields['EnterYourContactsAndIfYouWantYouCanLeaveAComment']);
            //$('#chechout').text(localeFields['checkout']);
            $('.checkout-btn').text(localeFields['checkout']);
            $('.resolveShopCart').text(localeFields['resolveShopCart']);
            $('#shoppingcart').text(localeFields['ShoppingCart']);
            $('#settingprofile').text(localeFields['settingprofile']);
            $('#enter-name').text(localeFields['enter-name']);
            $('#enter-email').text(localeFields['enter-email']);
            $('#enter-message').text(localeFields['enter-message']);
            $('.closeModalBtn').text(localeFields['close']);
            $('#send-feedback-request').text(localeFields['send-feedback-request']);
            $('#logout-modal-title').text(localeFields['logout']);
            $('#sender-message').val(localeFields['hello-interested']);
            let title = $(".title");
            let author = $(".author");
            let edition = $(".edition");
            let originalLanguage = $(".original-language");
            let amountOfPages = $(".amount-of-pages");
            let yearOfEdition = $(".year-of-edition");
            let price = $(".price");
            $(".load-more-btn").html(localeFields['load-more']);
            $(".displayed").html(localeFields['displayed']);
            $(".of").html(localeFields['of']);


            //should be down
            title.html(title.html().replace('Title:', localeFields['title']));
            author.html(author.html().replace('Author:', localeFields['author']));
            edition.html(edition.html().replace('Edition:', localeFields['edition']));
            originalLanguage.html(originalLanguage.html().replace('Original language:', localeFields['original-language']));
            amountOfPages.html(amountOfPages.html().replace('Amount of pages:', localeFields['amount-of-pages']));
            yearOfEdition.html(yearOfEdition.html().replace('Year of edition:', localeFields['year-of-edition']));
            price.html(price.html().replace('Price:', localeFields['price']));
        })
}

//function for chose language
function chooseLanguage(lang) {
    document.cookie = `lang=${lang}; path=/`;
    window.location.reload();
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
            for (let language in listOfLanguage) {
                if (currentLang === (listOfLanguage[language])) {
                    continue;
                }
                currentLangFull = getFullNameOfLanguage(listOfLanguage[language]);
                html += `<li>
                             <a onclick="chooseLanguage('${listOfLanguage[language]}')">${currentLangFull}</a>
                        </li>`
                // html += `<a class="dropdown-item lang" onclick="chooseLanguage('${listOfLanguage[language]}')" id="${listOfLanguage[language]}">
                //             <img src="../static/icons/${listOfLanguage[language]}.png"
                //                 alt="" height="16" width="16" class="lang-image"> - ${currentLangFull}
                //          </a>`;
            }
            $('#sidebarLanguages').html(html);
            // $('#dd_menu_link').text(currentLang);
            // $('#dd_menu_link').empty();
            // $('#dd_menu_link').html(`<img src="../static/icons/${currentLang}.png"
            //                     alt="" height="20" width="16" class="lang-image">`);
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
    let cookie = `CURRENT_PAGE = ${window.location.pathname}; path = /; max-age = 60`;
    document.cookie = cookie;
}

