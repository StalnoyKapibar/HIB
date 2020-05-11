var currentLang = '';

$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
    setPageFields();
});

fetch('/categories/getnullparent', {
})
    .then(function (response) {
        return response.json()
    })
    .then(function (primaryCategories) {
        for (let i in primaryCategories) {
            categoryRow =
                `<div class="form-check">
                    <input class="form-check-input" type="radio" name="category" id="id-${primaryCategories[i].categoryName.toLowerCase()}" value="${primaryCategories[i].categoryName}">
                    <label class="form-check-label" for="id-${primaryCategories[i].categoryName.toLowerCase()}">
                        ${primaryCategories[i].categoryName}
                    </label>
                </div>`;
            $('#input-categories').append(categoryRow);
        }
    });

function advancedSearch() {
    let request = $('#search-input').val();
    let priceFrom = $('#input-price-from').val() * 100;
    let priceTo = $('#input-price-to').val() * 100;
    let yearOfEdition = $('#input-year-edition').val();
    let pages = $('#input-pages').val();
    let category = $('#input-categories input:checked').val();
    let searchBy = $('#search-by input:checked').val();
    fetch("/searchAdvanced?request=" + request + "&searchBy=" + searchBy + "&category=" + category +
        "&priceFrom=" + priceFrom + "&priceTo=" + priceTo + "&yearOfEdition=" + yearOfEdition + "&pages=" + pages+ "&LANG=" + currentLang, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
        .then(data => data.json())
        .then(function (data) {
            $('table').empty();
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