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
        url: "/searchResult",
        method: 'GET',
        data: {
            request: $("#request").attr("value"),
            LANG: currentLang
        }
    }).then(function (data) {
        let tr = [];
        for (let i = 0; i < data.length; i++) {
            tr.push('<tr>');
            tr.push('<td class="align-middle"><img src="../images/book' + data[i].id + '/' + data[i].coverImage + '" style="max-width: 60px">');
            tr.push('<td class="align-middle">' + data[i].nameAuthorDTOLocale + '</td>>');
            tr.push('<td class="align-middle">' + data[i].nameBookDTOLocale + '</td>>');
            tr.push('<td class="align-middle"><form id="bookButton" method="get" action="/page/' + data[i].id + '">' +
                '<button class="btn btn-primary pageOfBook" id="buttonBookPage" name="bookPage">A page of book</button></form></td>>');
            tr.push('</tr>');
        }
        $('table').append($(tr.join('')));
    });
}



