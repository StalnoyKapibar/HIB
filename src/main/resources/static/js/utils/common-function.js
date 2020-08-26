var currentLang = '';
var bottom = '';
var addToshoppingCart = '';
var noRequiredField = '';
var deleteBottom = '';
var outOfStock = '';


$(document).ready(function () {
    if (currentLang === '') {
        if (getCookieByName("lang")) {
            currentLang = getCookieByName("lang");
        } else {
            currentLang = 'en';
        }
    }
    setLocaleFields();
})

// pass /login as JSON, check response for errors and add errors to modal
function sendSignInForm() {
    $('#form-login').submit(function (event) {
        event.preventDefault();
        let data = 'username=' + $('#loginInput').val() + '&password=' + $('#passwordInput').val();
        $.ajax({
            data: data,
            type: 'POST',
            url: '/login'
        }).done(function(resp, textStatus, jqXHR) {
            if ('responseJSON' in jqXHR && resp['hasError'] === true) {
                let error = `<h5 class="col-12 p-3 rounded text-center alert-danger" id="errorMessage">${resp['message']}</h5>`;
                $('#loginErrors').html(error);
            } else {
                window.location.href = "/home";
            }
        }).fail(function(jqXHR, textStatus) {
        });
    });
    setLocaleFields();
}

function sendSingUpForm() {
    // show preloader before action
    $(".preloader").show();
    // add message to preloader
    $(".lds-ellipsis").html(`
        <span></span>
        <span></span>
        <span></span>
        <div class="text-danger">Registration proceed</div>
    `);
    $('#hiddenSingUpBtn').click();
}

async function setLocaleFields() {
    fetch("/properties/" + currentLang)
        .then(status)
        .then(json)
        .then(function (localeFields) {
            //footer
            $('.main-footer').text(localeFields['mainFooter']);
            $('.users-manual-footer').text(localeFields['instruction']);
            $('.how-to-order-footer').text(localeFields['order']);
            $('.authors-list-footer').text(localeFields['authors']);
            $('.theme-links-footer').text(localeFields['links']);
            $('.contact-locations-footer').text(localeFields['contacts']);
            $('.use-cookie-loc').text(localeFields['useCookie']);
            $('.use-cookie-text-loc').text(localeFields['useCookieText']);
            $('.made-by').text(localeFields['madeby']);
            $('#made_by').text(localeFields['madeby']);

            //Left sidebar on mainpage
            // $('#history-sidebar').text(localeFields['history-sb']);
            // $('#documents-sidebar').text(localeFields['documents-sb']);
            // $('#magazines-sidebar').text(localeFields['magazines-sb']);
            // $('#culture-sidebar').text(localeFields['culture-sb']);
            $('#language-left-panel').text(localeFields['language']);

            //Боковая панель с категориями
            // $('#history-rightbar').text(localeFields['history-sb']);
            // $('#documents-rightbar').text(localeFields['documents-sb']);
            // $('#magazines-rightbar').text(localeFields['magazines-sb']);
            // $('#culture-rightbar').text(localeFields['culture-sb']);
            // $('.forein-rightbar').text(localeFields['forein-sb']);
            // $('.greek-rightbar').text(localeFields['greek-sb']);
            $('.from-form').attr('placeholder', localeFields['from']);
            $('.to-form').attr('placeholder', localeFields['to']);

            //Кусок с показом заказанного перед покупкой, либо модальное окно заказа в обработке
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
            $('.req-approve-loc').text(localeFields['reqApprove']);

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
            $('.phone-loc').text(localeFields['phone']);
            $('.old-pass-loc').text(localeFields['oldPass']);   //Enter old password
            $('.new-pass-loc').text(localeFields['newPass']);     //Enter new password
            $('.again-pass-loc').text(localeFields['againPass']);     //Enter new password again
            $('.orders-user-on-page-loc').text(localeFields['OrdersOnPage']);

            //Text and placeholders for email/phone/comment
            $('.email').attr('placeholder', localeFields['email']);
            $('.email-label').text(localeFields['email']);
            $('.phone').attr('placeholder', localeFields['phone']);
            $('.phone-label').text(localeFields['phone']);
            $('.comment').attr('placeholder', localeFields['comment']);
            $('.comment-label').text(localeFields['comment']);
            $('.login').attr('placeholder', localeFields['login']);
            $('.login-label').text(localeFields['email']);
            $('.password').attr('placeholder', localeFields['password']);
            $('.password-label').text(localeFields['password']);
            $('.confirm-password').attr('placeholder', localeFields['confirmPassword']);
            $('.confirm-password-label').text(localeFields['confirmPassword']);

            //resetPassword.js
            $('.notRegistredEmail').text(localeFields['notRegistredEmail']);
            $('.checkEmail').text(localeFields['checkEmail']);

            //profile.js and newPass.js
            $('.mess-success-loc').text(localeFields['messSuccess']);
            $('.incorrect-data-password-loc').text(localeFields['incorrectDataPassword']);
            $('.wrong-current-pass-loc').text(localeFields['wrongCurrentPass']);
            $('.pass-success-saved-loc').text(localeFields['passSuccessSaved']);
            $('.email-used-by-user-loc').text(localeFields['emailUsedByUser']);
            $('.invalid-email-format-loc').text(localeFields['invalidEmailFormat']);
            $('.dont-match-pass-loc').text(localeFields['dontMatchPass']);
            $('.not-valid-url-loc').text(localeFields['notValidUrl']);

            //user-page.html
            $('.my-orders-loc').text(localeFields['oders']);
            $('.myprof-loc').text(localeFields['myprofile']);
            $('.checkboxRememberMe-loc').text(localeFields['rememberMe']);
            $('.login-loc').text(localeFields['email']);
            $('.contact-us').text(localeFields['contact-us']);
            $('.ask-question-loc').text(localeFields['ask-question']);

            //Страница с обрабатываемыми заказами со стороны пользователя
            $(".orders-loc").text(localeFields['orders']);
            $(".orderNo-loc").text(localeFields['orderNo']);
            $(".dateOfOrder-loc").text(localeFields['dateOfOrder']);
            $(".orderStatus-loc").text(localeFields['orderStatus']);
            $(".show-btn").text(localeFields['show']);
            $(".close-order").text(localeFields['cancel']);

            //Регистрация
            $('.signin-loc').text(localeFields['signin']);
            $('.login-input-loc').attr('placeholder', localeFields['email']);
            $('.password-input-loc').attr('placeholder', localeFields['password']);
            $('.password-input-label').text(localeFields['password']);
            $('.register-new-btn').text(localeFields['registerNew']);
            $('.forgot-pass-btn').text(localeFields['forgotPass']);
            $('.signup-google-btn').text(localeFields['signUpGoogle']);
            $('.signup-btn').text(localeFields['signUp']);
            $('.signup-fb-btn').text(localeFields['signUpFB']);
            $('.create-new-profile-loc').text(localeFields['registerNew']);

            //search.html and search.js
            $('.search-by-dots-loc').text(localeFields['searchBy']);
            $('.name-and-author-loc').text(localeFields['titleAndAuthor']);
            $('.search-input-dots-loc').attr('placeholder', localeFields['searchDots']);
            $('.year-of-edition-loc').text(localeFields['yearOfEdition']);
            $('.number-of-pages-loc').text(localeFields['numberOfpages']);
            $('.search-submit-loc').text(localeFields['search']);
            $('.available-loc').text(localeFields['check-available']);

            //Admin left sidebar  on mainpage
            $('.admin-loc').text(localeFields['admin']);
            $('.admin-panel-loc').text(localeFields['adminPanel']);
            //Admin Panel
            $('.edit-loc').text(localeFields['edit']);
            $('.delete-loc').text(localeFields['delete']);
            $('.home-loc').text(localeFields['home']);
            $('.orders-loc').text(localeFields['orders']);
            $('.edit-welcome-loc').text(localeFields['editWelcome']);
            $('.books-loc').text(localeFields['books']);
            $('.feedback-request-loc').text(localeFields['feedbackRequest']);
            $('.user-loc-lang').text(localeFields['user']);
            $('.users-loc-lang').text(localeFields['usersLang']);
            $('.edit-footer-loc').text(localeFields['editFooter']);

            //AdminCategoryTree.js
            $('#editCategoryModal').text(localeFields['editCategory']);
            $('#categoryName').text(localeFields['nameCategory']);
            $('#editCategoryView').text(localeFields['view']);
            $('#addChildCategory').text(localeFields['addChild']);
            //$('#alert').text(localeFields['hiddenAlertCategory']);
            $('#deleteAlert').text(localeFields['delete']);
            $('#updateCategory').text(localeFields['changesSaved']);
            $('#deleteSubmit').text(localeFields['deleteAny']);
            $('#allChild').text(localeFields['hiddenAlertCategory']);
            $('#addCategoryModal').text(localeFields['addCategory']);
            $('#close').text(localeFields['close']);
            $('#addNewCategory').text(localeFields['add']);
            $('#editPlaceholder').attr('placeholder', localeFields['placeholderCategory']);



            //addBooks.js
            $('.years-of-edition-loc').text(localeFields['yearsOfEdition']);
            $('.pages-loc').text(localeFields['pages']);
            $('.price-loc').text(localeFields['Price']);
            $('.original-lang-loc').text(localeFields['originalLang']);
            $('.avatar-loc').text(localeFields['avatar']);
            $('.select-category-loc').html(localeFields['selectCategory'] + '<span class="required">*</span>');
            $('.load-avatar-loc').text(localeFields['loadAvatar']);
            $('.another-image-loc').text(localeFields['anotherImage']);
            $('.load-another-image-loc').text(localeFields['loadAnotherImage']);
            //books.html
            $('.all-books-loc').text(localeFields['allBooks']);
            $('.add-books-loc').html(localeFields['addBooks']);
            $('.add-book-loc').html(localeFields['addBook']);
            $('.add-hib-files-loc').html(localeFields['addHibFiles']);
            $('.show-disabled-book-loc').text(localeFields['showDisabledBook']);
            $('#add-new-book-loc1').html(localeFields['addNewBook'] + '<span style="float: right; text-align: right;"><span class="required">*</span><span id="requiredExample">- required field</span></span>');
            $('#add-new-book-loc2').text(localeFields['addNewBook']);
            $('.is-disabled-dots-loc').text(localeFields['isDisabledDots']);
            $('.add-name-loc').html(localeFields['addName'] + '<span class="required">*</span>');
            $('.add-author-loc').html(localeFields['addAuthor'] + '<span class="required">*</span>');
            $('.add-description-loc').text(localeFields['addDescription']);
            $('.add-addition-loc').html(localeFields['addAddition' + '<span class="required">*</span>']);
            $('.add-other-loc').text(localeFields['addOther']);
            $('.category-loc').html(localeFields['Category'] + '<span class="required">*</span>');
            noRequiredField = localeFields['noRequiredField'];
            $('.update-one-book-loc').text(localeFields['updateOneBook']);
            $('.edit-and-upload-loc').text(localeFields['editAndUpload']);
            $('.upload-several-longphrase-loc').text(localeFields['uploadSeveralLongphrase']);
            $('.upload-to-server-loc').text(localeFields['uploadToServer']);
            $('.edit-book-loc').text(localeFields['edit-Book']);
            //edit-footer-welcome-html
            $('.category-tree-loc').text(localeFields['categoryTree']);
            $('.create-new-primary-category-loc').attr('placeholder', localeFields['createNewPrimaryCategory']);
            $('.create-loc').text(localeFields['create']);
            $('.english-text-loc').text(localeFields['englishText']);
            $('.link-loc').text(localeFields['link']);
            //feedback.html
            $('.show-viewed-messages-loc').text(localeFields['showViewedMessages']);
            $('.sender-name-loc').text(localeFields['senderName']);
            $('.content-loc').text(localeFields['content']);
            $('.reply-loc').text(localeFields['reply']);
            $('.mark-as-loc').text(localeFields['markAs']);
            $('.recipient-dots-loc').text(localeFields['recipientDots']);
            $('.message-loc').text(localeFields['message']);
            $('.interested-book-loc').text(localeFields['interestedBook']);
            $('.subject-loc').text(localeFields['subject']);
            $('.reply-message-loc').text(localeFields['replyMessage']);
            $('.send-message-to-email-loc').text(localeFields['sendMessageToEmail']);
            $('.order-with-status-loc').text(localeFields['orderWithStatus']);
            $('.name-loc').text(localeFields['firstName']);
            $('.name-field').attr('placeholder', localeFields['firstName']);
            $('.author-loc').text(localeFields['Author']);
            //adminOrders.js
            $('.description-loc').text(localeFields['description']);
            $('.delete-image-loc').text(localeFields['deleteImage']);
            $('.date-of-order-loc').text(localeFields['dateOfOrder']);
            $('.status-loc').text(localeFields['status']);
            $('.details-of-order-loc').text(localeFields['detailsOfOrder']);
            $('.show-details-loc').text(localeFields['showDetails']);
            $('.complete-loc').text(localeFields['complete']);
            $('.send-loc').text(localeFields['send']);
            $('.confirm-gmail-longphrase-loc').text(localeFields['confirmGmailLongphrase']);
            $('.confirm-loc').text(localeFields['confirm']);
            $('.user-loc').text(localeFields['user']);
            $('.users-loc').text(localeFields['users']);
            //buildEditPage.js
            $('.transl-from-this-lang-loc').text(localeFields['translFromThisLang']);
            $('.into-this-lang-loc').text(localeFields['intoThisLang']);
            $('.translate-loc').text(localeFields['translate']);
            $('.of-other-lang-loc').text(localeFields['ofOtherLang']); //small letter and down
            $('.transliterate-loc').text(localeFields['transliterate']);
            $('.big-transliterate-loc').text(localeFields['bigTransliterate']);
            $('.book-old-loc').text(localeFields['bookOld']);
            $('.change-category-loc').text(localeFields['changeCategory']);
            $('.selected-category-dots-loc').text(localeFields['selectedCategoryDots']);
            $('.change-image-cover-loc').text(localeFields['changeImageCover']);
            //editFooter.js
            $('.changes-saved-loc').text(localeFields['changesSaved']);
            $('.changes-will-longphrase-loc').text(localeFields['changesWillLongphrase']); //Changes will take effect in a few seconds.
            $('.you-have-unsaved-changes-loc').text(localeFields['youHaveUnsavedChanges']);
            $('.warning-loc').text(localeFields['warning']);
            //feedbackRequest.js
            $('.read-loc').text(localeFields['read']);
            $('.unread-loc').text(localeFields['unread']);
            //realEditWelcome.js
            $('.submit-loc').text(localeFields['submit']);
            $('.upload-for-sale').text(localeFields['uploadForSale']);

            //other
            $('#header-general-loc').text(localeFields['general']);
            $('#engText').text(localeFields['engText']);
            $('#header-settings-loc').text(localeFields['settings']);
            $('#link_search_page_header').text(localeFields['searchPage']);
            $('.orders-on-page-loc').text(localeFields['OrdersOnPage']);
            $('.show-disabled-users-loc').text(localeFields['showDisabledUsers']);

            $('#link_main_header').text(localeFields['main']);
            $('.main-header-loc').text(localeFields['main']);
            $('#link_books_header').text(localeFields['books']);
            $('.books-header-loc').text(localeFields['books']);
            $('#categories').text(localeFields['category']);
            $('.categories-loc').text(localeFields['category']);
            $('#headpost').text(localeFields['headpost']);

            bottom = localeFields['bookbotom'];
            addToshoppingCart = localeFields['addToshoppingCart'];
            outOfStock = localeFields['outOfStock'];
            addedToshoppingCart = localeFields['addedToshoppingCart'];
            editBook = localeFields['editBook'];
            deleteBottom = localeFields['deleteBottom'];
            alertFileNotFound = localeFields['alertFileNotFound'];

            $('#modalClose').text(localeFields['close']);
            $('.book-on-page-loc').text(localeFields['bookOnPage']);
            $('.page-of-book-localize').text(localeFields['pageofBook']);
            $('.welcome-reader-loc').text(localeFields['welcomeReader']);
            $('.hello-dear-reader-loc').text(localeFields['helloDearReader']);
            $('.confirm-account-loc').text(localeFields['confirmAccount']);
            $('.confirm-letter-text-loc').text(localeFields['confirmLetterText']);
            $('.total-number-of-books-loc').text(localeFields['totalNumberOfBooks']);
            $('#dropdownclose').text(localeFields['dropdownclose']);
            $('#toshoppingcart').text(localeFields['toshoppingcart']);
            $('#bottomInCart').text(localeFields['addToshoppingCart']);
            $('#addToCart').text(localeFields['addToshoppingCart']);
            $('.bottomInCart').text(localeFields['addToshoppingCart']);
            $('.addToCart').text(localeFields['addToshoppingCart']);
            $('#delete').text(localeFields['deleteBottom']);
            $('#quantity').text(localeFields['quantity']);
            $('#price').text(localeFields['price']);
            $('#book_author').text(localeFields['book_author']);
            $('.name-author-loc').text(localeFields['book_author']);
            $('#totalPrice').text(localeFields['totalprice']);
            $('.total-loc').text(localeFields['totalPrice']);
            $('#gmail-access').text(localeFields['gmailAccess']);
            $('#headerShoppingCart').text(localeFields['headershoppingcart']);
            $('#home-tab').text(localeFields['YourShoppingCart']);
            $('#contacts-tab').text(localeFields['YourContacts']);
            $('#summary-tab').text(localeFields['Summary']);
            $('#enter_your_contacts').text(localeFields['EnterYourContactsAndIfYouWantYouCanLeaveAComment']);
            $('.checkout-btn').text(localeFields['checkout']);
            $('.resolveShopCart').text(localeFields['resolveShopCart']);
            $('#shoppingcart').text(localeFields['ShoppingCart']);
            $('#settingprofile').text(localeFields['settingprofile']);
            $('#enter-name').text(localeFields['enter-name']);
            $('#enter-email').text(localeFields['enter-email']);
            $('.enter-email-label').text(localeFields['enter-email']);
            $('#enter-message').text(localeFields['enter-message']);
            $('.closeModalBtn').text(localeFields['close']);
            $('.close-btn').text(localeFields['close']);
            $('#send-feedback-request').text(localeFields['send-feedback-request']);
            $('#logout-modal-title').text(localeFields['logout']);
            $('.logout-loc').text(localeFields['logoutShort']);
            $('.bought-btn-loc').text(localeFields['outOfStock']);
            // $('#sender-message').val(localeFields['hello-interested']);
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
            $("#editBtn").html(localeFields['editBook']);
            $('#requiredExample').html(localeFields['requiredExample']);

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
    getCategoriesLocal(lang);
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
                             <a href="#" onclick="chooseLanguage('${listOfLanguage[language]}')">${currentLangFull}</a>
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

// Функция поиска совпадений вводимых символов
function findElem(el, array, value) {
    var coincidence = false;
    el.empty();    // Очищаем список совпадений
    for (var i = 0; i < array.length; i++) {
        if (array[i].match(value) || array[i].toLowerCase().match(value)) {    // Проверяем каждый эллемент на совпадение по буквенно
            el.children('li').each(function () {    // Проверка на совпадающие эллементы среди выведенных
                if (array[i] === $(this).text()) {
                    coincidence = true;    // Если есть совпадения, то true
                }
            });
            if (coincidence === false) {
                el.append('<a class="js-searchInput dropdown-item">' + array[i] + '</a>');    // Если совпадений не обнаружено, то добавляем уникальное название в список
            }
        }
    }
}