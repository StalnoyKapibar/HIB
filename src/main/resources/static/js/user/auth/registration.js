var currentLang = '';
var bottom = '';

$(document).ready(function () {
    if (currentLang === '') {
        //currentLang = $('#dd_menu_link').data('currentLang');
        if (getCookieByName("lang")) {
            currentLang = getCookieByName("lang");
        } else {
            currentLang = 'en';
        }
    }
    getLanguage();
    setLocaleFields();
    buildPageByCurrentLang();
})

