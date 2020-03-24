var currentLang = '';

$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
    setPageFields();
});

function setPageFields() {
    $.ajax({
        url: "/page/id/" + $("#bookid").attr("value"),
        method: 'GET',
    }).then(function (data) {
        $('#book-name').text(data.name[currentLang]);
        $('#book-author').text(data.author[currentLang]);
        $('#book-name1').text(data.name[currentLang]);
        $('#bookImg').attr('src', "../images/book" + data.id + '/' + data.coverImage);
    })
}
