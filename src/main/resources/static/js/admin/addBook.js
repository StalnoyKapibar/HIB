let divAvatar, categoryName, selectedCategoryName, category, categoryTab, categoryTreeDiv, categoryHelperDiv;
let listImages;
let yearOfEdition;
let pages;
let price;
let originalLanguage;
let picsFolderName;
let tempPicsFolderCheck;

$(document).ready(getVarBookDTO());

//Проверка на заполненность требуемых полей
function checkRequired() {
    if (category === undefined ){
        alert(noRequiredField + ': ' + 'category!');
        return false;
    }
    for (let tmpNameObject of nameObjectOfLocaleString) {
        let test = $("#inpt" + tmpNameObject).val();
        if (test === ''){
            alert(noRequiredField + ': ' + tmpNameObject + '!');
            return false;
        }
        for (let tmpNameVar of nameVarOfLocaleString) {
            let varTest = $("#inp" + tmpNameObject + tmpNameVar).val();
            if (varTest === '') {
                alert(noRequiredField + ': ' + tmpNameObject + ' ' + tmpNameVar + '!')
                return false;
            }
        }
    }

    if(category === ""){
        alert(noRequiredField + '!');
        return false;
    }
    return true;
}

//Функция, выводящая поля для транслитерации и перевода
function addPartsOfBook(partsOfBook) {
    let html = ``;
    for (let tmpNameObject of nameObjectOfLocaleString) {

        if (tmpNameObject === partsOfBook) {

            html += `<div class="shadow p-4 mb-4 bg-white">`;

            if (partsOfBook === "description") {

                for (let tmpNameVar of nameVarOfLocaleString) {
                    html += `<div class="card p-4 mb-4 bg-light">
                                <div class='form-group mx-5'>
                                    <div class="row">
                                        <div class="col-0" for=${tmpNameObject}${tmpNameVar}>${tmpNameObject} ${tmpNameVar}</div>
                                        <div class="col-2 mr-1">
                                            <input type="radio" name="rb${tmpNameObject}" id="rb${tmpNameObject}${tmpNameVar}" 
                                                value="${tmpNameVar}" autocomplete="off"> Translate from this language</div>
                                        <div class="col-6">
                                            <textarea type='text' class='form-control' rows="10"  id='inp${tmpNameObject}${tmpNameVar}'
                                                placeholder='${tmpNameObject} ${tmpNameVar}'> </textarea>
                                        </div>
                                        <div class="col">
                                            <input type="checkbox" checked name="cb${tmpNameObject}" value="${tmpNameVar}" 
                                                autocomplete="off">Into this language
                                        </div>
                                    </div>
                                </div>
                             </div>`;
                    if (tmpNameVar === "gr") {
                        html += `<button type="button" onclick="translateText('${tmpNameObject}')" class="btn btn-primary mx-3 w-25">Translate</button></div>`
                    }
                }
            } else {

                html +=
                    `<div class="card p-4 mb-4 bg-light">
                        <div class='form-group mx-5 my-3'>
                            <div class="row">
                                <div class="col-0" for=${tmpNameObject}>${tmpNameObject}<span class="required">*</span> of other lang </div>
                                <div class="col-5 pl-5 ml-5  "><input type='text'  class='form-control '  id='inpt${tmpNameObject}'></div>
                            </div>
                            <div class="row my-2">
                                <div class="col-0" for=${tmpNameObject}>${tmpNameObject} transliterate&nbsp;&nbsp; </div>
                                <div class="col-5 pl-5 ml-5  mr-1 "><input type='text' class='form-control ' id='in${tmpNameObject}'></div>
                            </div>
                        </div>
                        <button type="button" onclick="transliterationText('${tmpNameObject}')" class="btn btn-primary mx-3 w-25">Transliterate</button>
                    </div>`;

                for (let tmpNameVar of nameVarOfLocaleString) {
                    html += `<div class="card p-4 mb-4 bg-light">
                                <div class='form-group mx-5'>
                                    <div class="row">
                                        <div class="col-0" for=${tmpNameObject}${tmpNameVar}>${tmpNameObject} ${tmpNameVar}<span class="required">*</span></div>
                                        <div class="col-2 mr-1">
                                            <input type="radio" name="rb${tmpNameObject}" id="rb${tmpNameObject}${tmpNameVar}" 
                                                value="${tmpNameVar}" autocomplete="off"> Translate from this language
                                        </div>
                                        <div class="col">
                                            <input type='text' class='form-control'  id='inp${tmpNameObject}${tmpNameVar}'
                                                placeholder='${tmpNameObject} ${tmpNameVar}'> 
                                        </div>
                                        <div class="col">
                                            <input type="checkbox" checked name="cb${tmpNameObject}" value="${tmpNameVar}" 
                                                autocomplete="off"> Into this language
                                        </div>
                                    </div>
                                </div>
                             </div>`;
                    if (tmpNameVar === "gr") {
                        html += `<button type="button" onclick="translateText('${tmpNameObject}')" class="btn btn-primary mx-3 w-25">Translate</button></div>`
                    }
                }
            }
            return html;
        }
    }
}

//Заполнение категории
function getCategoryName(event) {
    $('.btn-outline-primary').removeClass('active');
    $(event).addClass('active');
    selectedCategoryName = event.innerText;
    id = event.getAttribute('data-id');
    category = {
        id: id,
    };
    $('#selectedCategory').empty().append('Selected category: ' + selectedCategoryName);

}

//Отрисовка основы для вывода категорий
function addCategory() {
    let row =
        `<div class="shadow p-4 mb-4 bg-white text-center">
                <h4 id="selectedCategory">Select category<span class="required">*</span></h4>
                <h4 id="categoryHelper"></h4><hr>
                <div id="categoryTree"></div>`;
    categoryTab.append(row);
    categoryHelperDiv = $("#categoryHelper");
    categoryTreeDiv = $("#categoryTree");
    getTree();
}


//Загрузка дерева категорий
function getTree() {
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
            let tree = getUnflattens(cateroryArr, null);
            categoryTreeDiv.append(setTreeViews(tree));
        });
}

//Функция, выводящая визуальное наполнение вкладок
function buildAddPage() {

    doesFolderTmpExist();

    $('#bookAddForm').html(`<div class="tab-content" id="myTabContent">
                                <div class="tab-pane fade show active" id="name" role="tabpanel" aria-labelledby="name-tab">
                                    ${addPartsOfBook("name")}
                                </div>
                                <div class="tab-pane fade" id="author" role="tabpanel" aria-labelledby="author-tab" >
                                    ${addPartsOfBook("author")} 
                                </div>
                                <div class="tab-pane fade" id="description" role="tabpanel" aria-labelledby="description-tab">
                                    ${addPartsOfBook("description")}
                                </div>
                                <div class="tab-pane fade" id="edition" role="tabpanel" aria-labelledby="edition-tab">
                                    ${addPartsOfBook("edition")}
                                </div>
                                <div class="tab-pane fade" id="other" role="tabpanel" aria-labelledby="other-tab">
                                    <div class="shadow p-4 mb-4 bg-white">
                                        <div class="card p-4 mb-4 bg-light">
                                            <h5> Year Of Edition </h5>
                                            <input type="text" class="w-25" id="yearOfEdition"><br><br>
                                        </div>
                                        <div class="card p-4 mb-4 bg-light">
                                            <h5> Pages </h5>
                                            <input type="number" class="w-25" id="pages"><br><br>
                                        </div>
                                        <div class="card p-4 mb-4 bg-light">
                                            <h5> Price </h5>
                                            <input type="number" class="w-25" id="price"><br><br>
                                        </div>
                                        <div class="card p-4 mb-4 bg-light">
                                        <h5> Original Language </h5>
                                            <select id="originalLanguage" class="w-25"></select><br><br>
                                        </div>
                                        <div id = "allImage">
                                            <div class="card p-4 mb-4 bg-light">
                                                <div id="divLoadAvatar">
                                                    <h4>Cover</h4>
                                                    <Label>Load cover</Label>
                                                    <input type="file" class="form-control-file" id="avatar" accept="image/jpeg" onchange="loadImage('avatar','divAvatar')">
                                                </div>
                                                <div class='car' id='divAvatar'></div>
                                            </div>
                                            <div class="card p-4 mb-4 bg-light">
                                                <h4>Another image</h4>
                                                <Label>Load another image</Label>
                                                <input type="file" multiple class="form-control-file" id="loadAnotherImage" accept="image/jpeg,image/png,image/gif" onchange="loadMultTmpImages('loadAnotherImage','imageList')">
                                                <div class='car' id='imageList'></div>
                                            </div>
                                        </div>
                                    </div>
                                </div> 
                                <div class="tab-pane fade" id="category" role="tabpanel" aria-labelledby="category-tab"></div>
                            </div>`);

    divAvatar = $("#divAvatar");
    listImages = $("#imageList");
    yearOfEdition = $("#yearOfEdition");
    pages = $("#pages");
    price = $("#price");
    originalLanguage = $("#originalLanguage");
    categoryTab = $("#category");
    addCategory();
    for (let tmpNameVar of nameVarOfLocaleString) {
        originalLanguage.append(
            `<option value=${tmpNameVar.toUpperCase()}>${tmpNameVar.toUpperCase()}</option>`
        )
    }
    originalLanguage.append(`<option value="OTHER">OTHER</option>`);

    setLocaleFields().then(r => setTimeout(function(){}, 200)); //need
}

//
function getTempFolderName() {
    let picsFolderName;

    $.ajax({
        type: "GET",
        async: false,
        url: '/getPicsFolderName',
        success: (name) => {
            picsFolderName = name;
        },
        error: (e) => {
            console.log("ERROR: ", e);
        }
    });

    console.log("Временная папка хранения изображений: " + picsFolderName);
    return picsFolderName;
}

//Функция подгрузки нескольких изображений во временную папку
function loadMultTmpImages(nameId, div) {
    tempPicsFolderCheck = 1;
    let fileImg = document.getElementById(nameId).files;
    let fileNames = [];
    let data = new FormData();
    for (let i = 0; i < fileImg.length; i++) {
        let fileName = fileImg[i].name.replace(/([^A-Za-zА-Яа-я0-9.]+)/gi, '-');
        fileNames.push(fileName)
        data.append("files", fileImg[i], fileName);
    }
    $.ajax({
        type: 'POST',
        url: '/admin/uploadMulti?pics=' + picsFolderName,
        data: data,
        cache:false,
        contentType: false,
        processData: false
    }).then(function () {
        for (let i = 0; i < fileNames.length; i++) {
            addImageInDiv(fileNames[i], picsFolderName, div);
        }
    });
}

//Функция подгрузки обложки во временную папку
function loadImage(nameId, div) {
    tempPicsFolderCheck = 1;
    const formData = new FormData();
    let fileImg = $("#avatar").prop('files')[0];
    let fileName = "avatar.jpg";
    if( $("#divAvatar").html() !== "" ) {
        fetch('admin/deleteTmpCover', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: picsFolderName + "/avatar.jpg"
        })
    }
    formData.append('file', fileImg, fileName);
    $.ajax({
        type: 'POST',
        url: '/admin/upload?pics=' + picsFolderName,
        data: formData,
        cache:false,
        contentType: false,
        processData: false
    }).then(function () {
        addImageInDiv(fileName, picsFolderName, div);
    });
}

//Функция распределения картинок по "Обложка" или "Остальное"
function addImageInDiv(fileName, picsFolderName, divId) {
    let path = "/images/tmp/" + picsFolderName + "/" + fileName;
    if (divId === 'divAvatar') {
        divAvatar.empty();
        addImgAvatarAndBtn(fileName, path);
    } else {
        addImgToListAndBtn(fileName, path);
    }
}
//Обложка
function addImgAvatarAndBtn(divId, path) {
    divAvatar.append(
        `<div class="row align-items-center w-50 my-3" id=${divId}>
            <img src=${path} id=${divId} class='card-img-top col-8' alt='...'>
            <button type="button" onclick="deleteImage('${divId}')" class="btn btn-danger mx-3 col-2" style="height: 2.5rem"><i class="material-icons">delete</i></button>
        </div>`
    )
}
//Остальное
function addImgToListAndBtn(divId, path) {
    listImages.append(
        `<div class="row align-items-center w-50 my-3" id=${divId}>
            <img src=${path} id=${divId} class='card-img-top col-8' alt='...'>
            <button type="button" onclick="deleteImage('${divId}')" class="btn btn-danger mx-3 col-2" style="height: 2.5rem"><i class="material-icons">delete</i></button>
        </div>`
    )
}

//Функция удаления временного изображения
function deleteImage(id) {
    if (id === 'divAvatar') {
        divAvatar.empty();
    } else {
        document.getElementById(id).remove();
    }
}

//Функция отрисовки полей
function addValueToFields(book) {
    divAvatar.empty();
    listImages.empty();
    for (let tmpNameObject of nameObjectOfLocaleString) {
        for (let tmpNameVar of nameVarOfLocaleString) {
            $("#inp" + tmpNameObject + tmpNameVar).val(book[tmpNameObject][tmpNameVar]);
        }
    }
    categoryHelperDiv.append('Note: ' + book.category.categoryName);
    yearOfEdition.val(`${book.yearOfEdition}`);
    pages.val(`${book.pages}`);
    price.val(`${book.price}`);
    originalLanguage.val(`${book.originalLanguage}`);
}


//Функция отправки книги на создание
function sendAddBook() {
    if (checkRequired() && confirm("Add this book?")) {
        let book = {};
        let otherLangFields = {};
        for (let tmpNameObject of nameObjectOfLocaleString) {
            let bookFields = {};
            for (let tmpNameVar of nameVarOfLocaleString) {
                bookFields[tmpNameVar] = $("#inp" + tmpNameObject + tmpNameVar).val();
            }
            book[tmpNameObject] = bookFields;
            if (tmpNameObject !== "description") {
                otherLangFields[tmpNameObject] = $("#inpt" + tmpNameObject).val();
                otherLangFields[tmpNameObject + "Translit"] = $("#in" + tmpNameObject).val();
            }
        }
        book["originalLanguage"] = otherLangFields;
        book["yearOfEdition"] = yearOfEdition.val();
        book["pages"] = pages.val();
        book["price"] = price.val();
        book['originalLanguageName'] = $('#originalLanguage').val();
        book["show"] = (!$("#disabled").is(':checked'));
        book['category'] = category;
        let imageList = [];
        if(divAvatar.find("img")[0] != null) {
            book["coverImage"] = divAvatar.find("img")[0].id;
        }
        let allImages = $('#allImage').find("img");
        for (let img of allImages) {
            let image = {};
            image["nameImage"] = img.id;
            imageList.push(image);
        }
        book["listImage"] = imageList;
        let url;
        if (tempPicsFolderCheck === 1 || $("#divAvatar").html() !== "" || $("#imageList").html() !== "") {
            url = '/admin/add?pics=' + picsFolderName;
        } else {
            url = '/admin/addNoPics';
        }
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(book)
        }).then(r =>{
            tempPicsFolderCheck = undefined;
            if (window.opener !== null) {
                opener.location.reload();
            }
            window.close();
        });

    }
    if (uploadedBookName) {
        sendDeleteRequest(uploadedBookName);
        uploadedBookName = null;
    }
}

//Проверка наличия временной папки, не удалять!
function doesFolderTmpExist() {
    fetch("admin/doesFolderTmpExist");
}

//Далее отрисовка дерева категорий
function getUnflattens (arr, parentId) {
    let output = [];
    for (const category of arr) {
        if (category.parentId == parentId) {
            let children = getUnflattens(arr, category.id);
            if (children.length) {
                category.childrens = children
            }
            output.push(category)
        }
    }
    return output;
}


//Отрисовка дерева категорий
function setTreeViews(category) {
    let treeRow = '';
    for (let i in category) {
        treeRow =
            `<figure id="${category[i].categoryName}">
                <ul class="col-12 tree">
                    <li class="col-12">
                        <code parent="${category[i].parentId}" view-order="${category[i].viewOrder}"
                            data-id="${category[i].id}" path="${category[i].path}" class="btn-outline-primary" onclick="getCategoryName(this)">${category[i].categoryName}
                        </code>
                        <ul>
                            ${setChildren(category[i].childrens)}
                        </ul>
                    </li>
                </ul>
            </figure><br>`;
        categoryTreeDiv.append(treeRow);
    }
}

function setChildren(category) {
    let row = '';
    for (let i in category) {
        if (category[i].childrens === undefined) {
            row +=
                `<li>
                    <code class="btn-outline-primary" parent="${category[i].parentId}" view-order="${category[i].viewOrder}"
                    data-id="${category[i].id}" path="${category[i].path}" onclick="getCategoryName(this)">${category[i].categoryName}</code>
                </li>`;
        } else {
            row +=
                `<li>
                    <code class="btn-outline-primary" parent="${category[i].parentId}" view-order="${category[i].viewOrder}"
                    data-id="${category[i].id}" path="${category[i].path}" onclick="getCategoryName(this)">${category[i].categoryName}</code>
                    <ul>${setChildren(category[i].childrens)}</ul>
                </li>`;
        }
    }
    return row;
}

//Получение полей книги (name, author, description, edition)
function getVarBookDTO() {
    fetch("/getVarBookDTO")
        .then(status)
        .then(json)
        .then(function (resp) {
            nameObjectOfLocaleString = Object.values(resp).filter(t => t !== "id");
            getAllLocales();
            picsFolderName = getTempFolderName();
        });
}

//Получение языковых полей (ru, en, fr, it, de, cs, gr)
function getAllLocales() {
    var full_url = document.URL; // Get current url
    var url_array = full_url.split('/')
    var last_segment = url_array[url_array.length - 1];
    fetch("/lang")
        .then(status)
        .then(json)
        .then(function (resp) {
            nameVarOfLocaleStringWithId = resp;
            nameVarOfLocaleStringWithId.unshift("id");
            nameVarOfLocaleString = nameVarOfLocaleStringWithId.filter(t => t !== "id");
            buildAddPage();
        });
}
