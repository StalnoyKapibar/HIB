var currentLang = '';

$(document).ready(function () {
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
        "&priceFrom=" + priceFrom + "&priceTo=" + priceTo + "&yearOfEdition=" + yearOfEdition + "&pages=" + pages, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
        .then(data => data.json())
        .then(function (data) {
            $('table').empty();
            let table = [];
            table.push(`<thead>
                        <tr>
                            <th></th>
                            <th>Author</th>
                            <th>Name</th>
                            <th>Pages</th>
                            <th>Year of edition</th>
                            <th>Price, €</th>
                            <th>Category</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>`);
            $('table').append($(table.join('')));
            let tr = [];
            for (let i = 0; i < data.length; i++) {
                tr.push(`<tr>
                                <td class="align-middle"><img src="images/book${data[i].id}/${data[i].coverImage}" style="max-width: 60px">
                                <td class="align-middle">${data[i].author}</td>
                                <td class="align-middle">${data[i].name}</td>
                                <td class="align-middle">${data[i].pages}</td>
                                <td class="align-middle">${data[i].yearOfEdition}</td>
                                <td class="align-middle">${data[i].price / 100}</td>
                                <td class="align-middle">${data[i].category.categoryName}</td>
                                <td class="align-middle"><form id="bookButton" method="get" action="/page/${data[i].id}">
                                    <button class="btn btn-primary pageOfBook" id="buttonBookPage" name="bookPage">
                                        A page of book
                                    </button>
                                </td>
                            </tr>`
                );
            }
            $('table').append($(tr.join('')));
        });
}

function text(response) {
    return response.text()
}

function setPageFields() {
    let a = "/searchResult" + window.location.search;
    fetch("/searchResult" + window.location.search, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
        .then(data => data.json())
        .then(function (data) {
            $('table').empty();
            let table = [];
            table.push(`<thead>
                        <tr>
                            <th></th>
                            <th>Author</th>
                            <th>Name</th>
                            <th>Pages</th>
                            <th>Year of edition</th>
                            <th>Price, €</th>
                            <th>Category</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>`);
            $('table').append($(table.join('')));
            let tr = [];
            for (let i = 0; i < data.length; i++) {
                tr.push(`<tr>
                                <td class="align-middle"><img src="images/book${data[i].id}/${data[i].coverImage}" style="max-width: 60px">
                                <td class="align-middle">${data[i].author}</td>
                                <td class="align-middle">${data[i].name}</td>
                                <td class="align-middle">${data[i].pages}</td>
                                <td class="align-middle">${data[i].yearOfEdition}</td>
                                <td class="align-middle">${data[i].price / 100}</td>
                                <td class="align-middle">${data[i].category.categoryName}</td>
                                <td class="align-middle"><form id="bookButton" method="get" action="/page/${data[i].id}">
                                    <button class="btn btn-primary pageOfBook" id="buttonBookPage" name="bookPage">
                                        A page of book
                                    </button>
                                </td>
                            </tr>`
                );
            }
            $('table').append($(tr.join('')));
        });
}