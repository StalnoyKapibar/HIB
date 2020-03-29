var currentLang = '';
var bottom = '';

$(document).ready(function () {
    setCurrentLangFromSessionAttrLANG();
    getLanguage();
    setLocaleFieldsRegistrationPage();
    openModalLoginWindowOnFailure();
});

function setLocaleFieldsRegistrationPage() {
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

            //  Registration form
            $('#signUpHeader').text(localeFields['signUpHeader']);
            $('#labelEmail').text(localeFields['email']);
            $('#labelPasswordForm').text(localeFields['password']);
            $('#labelLoginForm').text(localeFields['login']);
            $('#labelConfirmPassword').text(localeFields['conformPassword']);
            $('#signUpBtn').text(localeFields['signUp']);
        })
}
