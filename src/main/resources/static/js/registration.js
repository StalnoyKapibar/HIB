var currentLang = '';
var bottom = '';

$(document).ready(function () {
    setCurrentLangFromSessionAttrLANG();
    getLanguage();
    setLocaleFields();
});

