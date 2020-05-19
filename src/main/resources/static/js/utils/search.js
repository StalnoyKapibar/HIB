let row, primary;
let id = "";

$(document).ready(function () {
    setPageFields();
    getCategoryTree();
});

function getCategoryTree() {
    fetch('/categories/gettree', {
    })    .then(function (response) {
        return response.json()
    })
        .then(function (json) {
            categoryArr = [];
            for (let i in json) {
                categoryId = json[i][0];
                categoryName = json[i][1];
                categoryPath = json[i][2];
                categoryParent = json[i][3];
                const category = {
                    id: categoryId,
                    categoryName: categoryName,
                    path: categoryPath,
                    parentId: categoryParent
                };
                categoryArr.push(category);
            }
            let tree = getUnflatten(categoryArr, null);
            setTreeView(tree);
        })
}

function getUnflatten(arr, parentid) {
    let output = [];
    for(const category of arr) {
        if(category.parentId == parentid) {
            let children = getUnflatten(arr, category.id);

            if(children.length) {
                category.childrens = children
            }
            output.push(category)
        }
    }
    return output
}

function setTreeView(category) {
    for (let i in category) {
        row =
            `<div class="category">
                <div class="custom-control custom-checkbox form-check-inline" id="heading-${i}">
                    <input class="custom-control-input collapsed" type="checkbox" id="check-${i}" value="${category[i].categoryName}">
                    <label class="custom-control-label" for="check-${i}"></label>
                    <label data-toggle="collapse" data-target="#collapse-${i}" aria-expanded="false" aria-controls="collapse-${i}">
                       ${category[i].categoryName}
                       <i class="fa fa-plus-square-o" aria-hidden="true"></i>
                    </label>
                </div>
                <div class="ml-3">
                    <div id="collapse-${i}" class="collapse" aria-labelledby="heading-${i}" data-parent="#accordionExample">
                    ${setChilds(category[i].childrens, i)}
                    </div>
                </div>
            </div>`;
        $('#input-categories').append(row);
    }
}

function setChilds(category, count) {
    id += (count + "-");
    let row = '';
    for (let i in category) {
        if (category[i].childrens === undefined) {
            id += (count + "-");
            row +=
                `<div class="category">
                    <div class="custom-control custom-checkbox form-check-inline" id="heading-${id}${i}">
                        <input class="custom-control-input collapsed" type="checkbox" id="check-${id}${i}" value="${category[i].categoryName}">
                        <label class="custom-control-label" for="check-${id}${i}">
                            ${category[i].categoryName}
                        </label>
                    </div>
                </div>`;
        } else {
            row +=
                `<div class="category">
                    <div class="custom-control custom-checkbox form-check-inline" id="heading-${id}${i}">
                        <input class="custom-control-input collapsed" type="checkbox" id="check-${id}${i}" value="${category[i].categoryName}">
                        <label class="custom-control-label" for="check-${id}${i}"></label>
                        <label data-toggle="collapse" data-target="#collapse-${id}${i}" aria-expanded="false" aria-controls="collapse-${id}${i}">
                           ${category[i].categoryName}
                           <i class="fa fa-plus-square-o" aria-hidden="true"></i>
                        </label>
                    </div>
                    <div class="ml-3">
                        <div id="collapse-${id}${i}" class="collapse" aria-labelledby="heading-${id}${i}" data-parent="#accordionExample">
                            ${setChilds(category[i].childrens, i)}
                        </div>
                    </div>
                </div>`;
        }
    }
    id = "";
    return row;
}

function advancedSearch() {
    let request = $('#search-input').val();
    let priceFrom = $('#input-price-from').val() * 100;
    let priceTo = $('#input-price-to').val() * 100;
    let yearOfEdition = $('#input-year-edition').val();
    let pages = $('#input-pages').val();
    let searchBy = $('#search-by input:checked').val();
    let categories = $("#input-categories input:checked").map(function(){
        return $(this).val();
    }).get();
    let categoryRequest = "";
    for (let i in categories) {
        categoryRequest += "&categories="+categories[i];
    }
    fetch("/searchAdvanced?request=" + request + "&searchBy=" + searchBy + categoryRequest +
        "&priceFrom=" + priceFrom + "&priceTo=" + priceTo + "&yearOfEdition=" + yearOfEdition + "&pages=" + pages, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
        .then(data => data.json())
        .then(function (data) {
            addFindeBooks(data)
        });
}

// function advancedSearch() {
//     let map = new Map();
//     let categories = $("#input-categories input:checked").map(function(){
//         return $(this).val();
//     }).get();
//     map.set("1", "str1");    // строка в качестве ключа
//     map.set(1, "num1");      // цифра как ключ
//     map.set(true, "bool1");  // булево значение как ключ
//     map.entries();
//     fetch("/searchAdvanced?categories=mgr&categories=20160210", {
//         method: "GET",
//         headers: {
//             'Content-Type': 'application/json',
//             'Accept': 'application/json'
//         }
//     })
//         .then(data => data.json())
//         .then(function (data) {
//             addFindeBooks(data)
//         });
// }

function setPageFields() {
    if (window.location.search === "") {
        fetch("/api/booksSearchPage", {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        })
            .then(data => data.json())
            .then(function (data) {
                addFindeBooks(data)
            });
    } else {
        fetch("/searchResult" + window.location.search, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        })
            .then(data => data.json())
            .then(function (data) {
                addFindeBooks(data)
            });
    }
}

function addFindeBooks(data) {
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
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>`);
    $('table').append($(table.join('')));
    let tr = [];
    for (let i = 0; i < data.length; i++) {
        tr.push(`<tr>
                                <td class="align-middle"><img src="images/book${data[i].id}/${data[i].coverImage}" style="max-width: 60px"></td>
                                <td class="align-middle">${data[i].author} (${data[i].authorTranslit})</td>
                                <td class="align-middle">${data[i].name} (${data[i].nameTranslit})</td>
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
}