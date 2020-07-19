let viewOrder, categoryId;
let nameVarOfLocaleString;
let nameVarOfLocaleStringWithId;

fetch('/admin/categories/getadmintree')
    .then(function (response) {
        return response.json()
    })
    .then(function (json) {
        cateroryArr = [];
        for (let i in json) {
            categoryId = json[i][0];
            categoryName = json[i][1];
            categoryPath = json[i][2];
            categoryParent = json[i][3];
            viewOrder = json[i][4];

            const category = {
                id: categoryId,
                categoryName: categoryName,
                path: categoryPath,
                parentId: categoryParent,
                viewOrder: viewOrder
            };
            cateroryArr.push(category);
        }
        let tree = getUnflatten(cateroryArr, null);
        setTreeView(tree);
    });

$(document).ready(function () {
    if (currentLang === '') {
        if (getCookieByName("lang")) {
            currentLang = getCookieByName("lang");
        } else {
            currentLang = 'en';
        }
    }
    console.log(document);
    getLanguage();
    setLocaleFields();
})

function getUnflatten(arr, parentId) {
    let output = [];
    for(const category of arr) {
        if(category.parentId == parentId) {
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
    let treeRow;
    for (let i in category) {
        $('#adminTree').append(`<figure id="${category[i].categoryName}"></figure><br>`);
        treeRow =
            `<ul class="col-12 tree">
                <li ondragstart="drag(event, this)" class="col-12">
                    <code draggable="true" ondrop="drop(event, this)" ondragover="allowDrop(event)" data-target="#category-modal" id="categoryEdit"
                    parent="${category[i].parentId}" view-order="${category[i].viewOrder}"
                    data-id="${category[i].id}" data-toggle="modal" class="btn-outline-primary">${category[i].categoryName}
                    </code>
                    <ul>
                     ${setChilds(category[i].childrens)}
                    </ul>
                </li>
            </ul>`;
        $(`#${category[i].categoryName}`).append(treeRow);
    }
}


function setChilds(category) {
    let row = '';
    for (let i in category) {
        if (category[i].childrens === undefined) {
            row +=
                `<li ondragstart="drag(event, this)">
                    <code draggable="true" ondrop="drop(event, this)" ondragover="allowDrop(event)" class="btn-outline-primary" data-target="#category-modal"
                     parent="${category[i].parentId}" view-order="${category[i].viewOrder}"
                      id="categoryEdit" data-id="${category[i].id}" data-toggle="modal">${category[i].categoryName}</code>
                </li>`;
        } else {
            row +=
                `<li ondragstart="drag(event, this)">
                    <code draggable="true" ondrop="drop(event, this)" ondragover="allowDrop(event)" class="btn-outline-primary" data-target="#category-modal"
                     parent="${category[i].parentId}" view-order="${category[i].viewOrder}"
                      id="categoryEdit" data-id="${category[i].id}" data-toggle="modal">${category[i].categoryName}</code>
                      <ul>${setChilds(category[i].childrens)}</ul>
                </li>`;
        }
    }
    return row;
}

$(document).on('click', '#categoryEdit', function (element) {
    $('#categoryModal').empty();
    categoryId = element.target.dataset.id;
    categoryName = element.target.innerText;
    viewOrder = $(this).attr('view-order');
    parentId = $(this).attr('parent');
    row =
        `<div class="modal-header">
                <h5 class="modal-title bold" id="logout-modal-title">Edit category: <b>${categoryName}</b></h5>
                <button aria-label="Close" class="close" data-dismiss="modal" type="button">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body modal-category" >
            <form id="updateCategoryForm" action="/categories/update" method="POST">
                  <div class="form-group"> 
                    <input type="number" class="form-control" id="formGroupExampleInput" hidden name="categoryId" value="${categoryId}">
                    <label for="formGroupExampleInput">Category name:</label>
                    <input type="text" class="form-control" id="formGroupExampleInput" name="categoryName" value="${categoryName}">
                    <br>
                    <label for="formGroupExampleInput2">View order:</label>
                    <input type="number" class="form-control" id="formGroupExampleInput2" name="viewOrder" value="${viewOrder}">
                  </div>
                  </form>
            </div>
            <div class="modal-footer">
               
                <div class="input-group mb-3">
                  <input type="text" class="form-control" name="newCategoryName" placeholder="Name of new category" aria-describedby="basic-addon2">
                  <div class="input-group-append">
                    <button class="btn btn-success" id="addNewCategory" type="button">Add new child category</button>
                  </div>
                  </div>
                  <div class="col alert alert-danger text-center" id="alert" hidden role="alert">
                  All childs categories will be deleted!<hr>
                  <button type="button" class="btn btn-danger btn-block" categoryId="${categoryId}" id="deleteSubmit" data-dismiss="modal">Delete anyway</button>
                </div>
                <button type="button" id="deleteAlert" class="btn btn-block btn-danger">Delete</button>
                <button id="updateCategory" data-dismiss="modal" class="btn btn-block btn-primary">Save changes</button>
            </div>`;
    $('#categoryModal').append(row);
});

$(document).on('click', '#deleteAlert', function () {
    $('#alert').attr('hidden', false);
});

$(document).on('click', '#deleteSubmit', function () {
    fetch('/admin/categories/delete', {
        method: 'POST',
        body: JSON.stringify({id: categoryId}),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    location.reload()
});

$(document).on('click', '#updateCategory', function () {
    categoryId = $('form input[name="categoryId"]').val();
    categoryName = $('form input[name="categoryName"]').val();
    viewOrder = $('form input[name="viewOrder"]').val();
    let map = {};
    map['en'] = categoryName;
    fetch('/admin/categories/update', {
        method: 'PUT',
        body: JSON.stringify({id: categoryId, name: map, viewOrder:viewOrder, parentId: parentId}),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    location.reload()
});

$(document).on('click', '#addNewCategory', function () {
    categoryName = $('input[name="newCategoryName"]').val();
    parentId = $('form input[name="categoryId"]').val();
    let map = {};
    map['en'] = categoryName;
    if (categoryName === '') {} //Вывести что-то на экран
    else {
        fetch('/admin/categories/add', {
            method: 'POST',
            body: JSON.stringify({name: map, parentId: parentId}),
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        });
        location.reload()
    }
});

$(document).on('click', '#addPrimary', function (element) {
    $('#categoryModal').empty();
    categoryName = $('input[name="primaryCategoryName"]').val();
    console.log("Добавляем: " + categoryName);
    //getAllLocales();
    row =
        `<div class="modal-header">
                <h5 class="modal-title bold" id="logout-modal-title">Add category: <b>${categoryName}</b></h5>
                <button aria-label="Close" class="close" data-dismiss="modal" type="button">
                    <span aria-hidden="true">&times;</span>
                </button>
        </div>
        <div class="modal-body modal-category" >
            <form id="addCategoryForm" action="/categories/add" method="POST">
                  <div class="form-group"> 
                    <label for="formGroupExampleInput">Category name:</label>
                    <input type="text" class="form-control" id="formGroupExampleInput" name="categoryName" value="${categoryName}">
                    <br>
                  </div>
            </form>
        </div>
        <div id="modal-row">
        </div>
        <div class="modal-footer">
            <button type="button" id="close" data-dismiss="modal" class="btn btn-block btn-danger">Close</button>
            <button id="addCategory" data-dismiss="modal" class="btn btn-block btn-primary">Add</button>
        </div>`;

    console.log(row);
    $('#categoryModal').append(row);
    getAllLocales();
    //$('#categoryModalAdd').append(row);


    // let map = {};
    // map['en'] = categoryName;
    // if (categoryName === '') {} //Вывести что-то на экран
    // else {
    //     fetch('/admin/categories/add', {
    //         method: 'POST',
    //         body: JSON.stringify({name: map}),
    //         headers: {
    //             'Content-Type': 'application/json',
    //             'Accept': 'application/json'
    //         }
    //     });
    //     location.reload()
    // }
});


function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData('text', ev.target.getAttribute('data-id'));
}

function drop(ev, target) {
    ev.preventDefault();
    let draggableCategoryId = ev.dataTransfer.getData("text");
    let targetCategoryId = target.getAttribute('data-id');
    fetch('/admin/categories/parentchange', {
        method: 'POST',
        body: JSON.stringify({id: draggableCategoryId, parentId: targetCategoryId}),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    location.reload();
}
function setTableRow(nameVarOfLocaleString) {
    let row = ``;
    let nameObject = "name"
    for (let tmpNameVar of nameVarOfLocaleString) {
        row +=
            `<div class='form-group mx-5'>
                <div class="row">
                    <div class="col-0" for=${nameObject}${tmpNameVar}>${nameObject} ${tmpNameVar}</div>
                        <div class="col-2 mr-1">
                            <input type="radio" class="transl-from-this-lang-loc" name="rb${nameObject}" id="rb${nameObject}${tmpNameVar}" value="${tmpNameVar}" autocomplete="off"> From this
                        </div>
                        <div class="col"> 
                            <input type='text' class='form-control' id='inp${nameObject}${tmpNameVar}' placeholder='${nameObject} ${tmpNameVar}'>
                        </div>
                        <div class="col">
                            <input type="checkbox" class="into-this-lang-loc" checked name="cb${nameObject}" value="${tmpNameVar}" autocomplete="off"> Into this language
                        </div>
                    </div>
                </div>
            `;
        if (tmpNameVar === "gr") {
            row += `<button type="button" onclick="translateText('${nameObject}')" class="btn btn-primary mx-3 translate-loc">Translate</button></div>`
        }
        
    }
    $('#modal-row').append(row);

    let checkedRadioButton = findRadioButton('rb'+nameObject);
    console.log(checkedRadioButton);
}


// html += `<div class="shadow p-4 mb-4 bg-white">
//                 <div class='form-group mx-5'>
//                 <div class="row">
//                 <div class="col-0" for=${tmpNameObject}${tmpNameVar}>${tmpNameObject} ${tmpNameVar}</div>
//                 <div class="col-2 mr-1">
//                 <input type="radio" class="transl-from-this-lang-loc" name="rb${tmpNameObject}" id="rb${tmpNameObject}${tmpNameVar}" value="${tmpNameVar}" autocomplete="off"> Translate from this language
//                 </div>
//                 <div class="col">
//                 <input type='text' class='form-control' id='inp${tmpNameObject}${tmpNameVar}'
//                 placeholder='${tmpNameObject} ${tmpNameVar}'>
//                 </div>
//                 <div class="col">
//                 <input type="checkbox" class="into-this-lang-loc" checked name="cb${tmpNameObject}" value="${tmpNameVar}" autocomplete="off"> Into this language
//                 </div></div></div></div>`;
// if (tmpNameVar === "gr") {
//     html += `<button type="button" onclick="translateText('${tmpNameObject}')" class="btn btn-primary mx-3 translate-loc">Translate</button></div>`
// }

function getAllLocales() {
    fetch("/lang")
        .then(status)
        .then(json)
        .then(function (resp) {
            nameVarOfLocaleStringWithId = resp;
            nameVarOfLocaleStringWithId.unshift("id");
            nameVarOfLocaleString = nameVarOfLocaleStringWithId.filter(t => t !== "id");
            setTableRow(nameVarOfLocaleString);
        });

}