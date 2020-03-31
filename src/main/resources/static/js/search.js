var currentLang = '';

$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
    setPageFields();
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

function setPageFields() {
    let req = $("#request").attr("value");
    fetch("/searchResult?request=" + req + "&LANG=" + currentLang, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
        .then(status)
        .then(json)
        .then(function (data) {
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

