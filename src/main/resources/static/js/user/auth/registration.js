var currentLang = '';
var bottom = '';

$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
        console.log(currentLang);
    }
    getLanguage();
    setLocaleFields();
    buildPageByCurrentLang();
});

