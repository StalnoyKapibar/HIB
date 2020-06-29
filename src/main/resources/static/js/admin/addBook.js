let divAvatar, categoryName, selectedCategoryName, category, categoryHelper, categoryTab, categoryTreeDiv, categoryHelperDiv;
let listImages;
let yearOfEdition;
let pages;
let price;
let originalLanguage;
let disabledCheckBox = $('#disabled');
let pathToTmpPackage = '/images/tmp/';

function checkNamesNotNull() {
    for (let tmpNameVar of nameVarOfLocaleString) {
        if ($("#inpname" + tmpNameVar).val() !== '' ) {
            return true;
        }
    }
    alert("Enter name of the book");
    return false;
}


function addPartsOfBook(partsOfBook) {
    let html = ``;
    for (let tmpNameObject of nameObjectOfLocaleString) {

        if (tmpNameObject === partsOfBook) {

            html += `<div class="shadow p-4 mb-4 bg-white">`;

            if (partsOfBook === "description") {
            } else {

                html +=
                    `<div class="shadow p-4 mb-4 bg-white">
                <div class='form-group mx-5 my-3'>
                <div class="row">
                <div class="col-0" for=${tmpNameObject}>${tmpNameObject} <div class="of-other-lang-loc">of other lang</div> </div>
                <div class="col-5 pl-5 ml-5  "><input type='text'  class='form-control '  id='inpt${tmpNameObject}'>
                </div> </div>
                <div class="row my-2">
                <div class="col-0" for=${tmpNameObject}>${tmpNameObject} <div class="transliterate-loc">transliterate</div>&nbsp;&nbsp; </div>
                <div class="col-5 pl-5 ml-5  mr-1 "><input type='text' class='form-control ' id='in${tmpNameObject}'>
                </div> </div>
                </div>
                <button type="button" onclick="transliterationText('${tmpNameObject}')" class="btn btn-primary mx-3 big-transliterate-loc">Transliterate</button>
                </div>`;
            }

            if(partsOfBook === "description") {

                for (let tmpNameVar of nameVarOfLocaleString) {
                    html += `<div class="shadow p-4 mb-4 bg-white">
                <div class='form-group mx-5'>
                <div class="row">
                <div class="col-0" for=${tmpNameObject}${tmpNameVar}>${tmpNameObject} ${tmpNameVar}</div>
                <div class="col-2 mr-1">
                <input type="radio" name="rb${tmpNameObject}" id="rb${tmpNameObject}${tmpNameVar}" value="${tmpNameVar}" autocomplete="off"> 
                <div class="transl-from-this-lang-loc">Translate from this language</div> 
                </div>
                <div class="col-6">
                <textarea type='text' class='form-control' rows="10"  id='inp${tmpNameObject}${tmpNameVar}'
                placeholder='${tmpNameObject} ${tmpNameVar}'> </textarea>
                </div>
                <div class="col">
                <input type="checkbox" checked name="cb${tmpNameObject}" value="${tmpNameVar}" autocomplete="off"> 
                <div class="into-this-lang-loc">Into this language</div>
                </div></div></div></div>`;
                    if (tmpNameVar === "gr") {
                        html += `<button type="button" onclick="translateText('${tmpNameObject}')" class="btn btn-primary mx-3 translate-loc">Translate</button></div>`
                    }
                }

            } else {
                for (let tmpNameVar of nameVarOfLocaleString) {
                    html += `<div class="shadow p-4 mb-4 bg-white">
                <div class='form-group mx-5'>
                <div class="row">
                <div class="col-0" for=${tmpNameObject}${tmpNameVar}>${tmpNameObject} ${tmpNameVar}</div>
                <div class="col-2 mr-1">
                <input type="radio" name="rb${tmpNameObject}" id="rb${tmpNameObject}${tmpNameVar}" value="${tmpNameVar}" autocomplete="off"> 
                <div class="transl-from-this-lang-loc">Translate from this language</div> 
                </div>
                <div class="col">
                <input type='text' class='form-control'  id='inp${tmpNameObject}${tmpNameVar}'
                placeholder='${tmpNameObject} ${tmpNameVar}'> 
                </div>
                <div class="col">
                <input type="checkbox" checked name="cb${tmpNameObject}" value="${tmpNameVar}" autocomplete="off"> 
                <div class="into-this-lang-loc">Into this language</div>
                </div></div></div></div>`;
                    if (tmpNameVar === "gr") {
                        html += `<button type="button" onclick="translateText('${tmpNameObject}')" class="btn btn-primary mx-3 translate-loc">Translate</button></div>`
                    }
                }
            }
            return html;
        }
    }
}

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

function addCategory() {
    let row =
        `<div class="shadow p-4 mb-4 bg-white text-center">
                <h4 class="select-category-loc" id="selectedCategory">Select category</h4>
                <h4 id="categoryHelper"></h4><hr>
                <div id="categoryTree"></div>`;
    categoryTab.append(row);
    categoryHelperDiv = $("#categoryHelper");
    categoryTreeDiv = $("#categoryTree");
    getTree();
}

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

function addPage() {
    getVarBookDTO();
    getAllLocales();
    doesFolderTmpExist();

    $('#newBookForm').html(`<div class="tab-content" id="myTabContent">
            
            <div class="tab-pane fade show active" id="name" role="tabpanel" aria-labelledby="name-tab">
             ${addPartsOfBook("name")}</div>
             
            <div class="tab-pane fade" id="author" role="tabpanel" aria-labelledby="author-tab" >
             ${addPartsOfBook("author")} </div>
            <div class="tab-pane fade" id="description" role="tabpanel" aria-labelledby="description-tab">
             ${addPartsOfBook("description")}</div>
             <div class="tab-pane fade" id="edition" role="tabpanel" aria-labelledby="edition-tab">
             ${addPartsOfBook("edition")}</div>
                <div class="tab-pane fade" id="other" role="tabpanel" aria-labelledby="other-tab">
                    <div class="shadow p-4 mb-4 bg-white">
                        <h5 class="years-of-edition-loc"> Year Of Edition </h5>
                        <input class="years-of-edition-loc" type="text" id="yearOfEdition" ><br><br>
                    </div>
                    <div class="shadow p-4 mb-4 bg-white">
                        <h5 class="pages-loc"> Pages </h5>
                        <input type="number" id="pages" ><br><br>
                    </div>
                    <div class="shadow p-4 mb-4 bg-white">
                        <h5 class="price-loc"> Price </h5>
                        <input type="number" id="price" ><br><br>
                    </div>
                    <div class="shadow p-4 mb-4 bg-white">
                    <h5 class="original-lang-loc"> Original Language </h5>
                        <select id="originalLanguage" >
                        </select><br><br>
                    </div>
                    <div id = "allImage">
                        <div class="shadow p-4 mb-4 bg-white">
                        <div id="divLoadAvatar">
                            <h4 class="avatar-loc">Avatar</h4>
                            <Label class="load-avatar-loc">Load avatar</Label>
                            <input type="file" class="form-control-file" id="avatar" accept=".jpg" onchange="loadImage('avatar','divAvatar')">
                        </div>
                        <div class='car' id='divAvatar' style='width: 18rem;'>
                        </div><br><br>
                        </div>
                        <div class="shadow p-4 mb-4 bg-white">
                            <h4 class="another-image-loc">Another Image</h4>
                            <Label class="load-another-image-loc">Load another image</Label>
                            <input type="file" class="form-control-file" id="loadAnotherImage" accept=".jpg" onchange="loadImage('loadAnotherImage','imageList')">
                        <div class='car' id='imageList' style='width: 18rem;'>
                        </div>
                        </div>
                    </div>
                </div> 
             <div class="tab-pane fade" id="category" role="tabpanel" aria-labelledby="category-tab">
             </div>
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

    setLocaleFields(); //need
}

function loadImage(nameId, div) {
    const formData = new FormData();
    let fileImg = $("#" + nameId).prop('files')[0];
    formData.append('file', fileImg);
    fetch('/admin/upload', {
        method: 'POST',
        body: formData
    })
        .then(function (body) {
            return body.text();
        }).then(function (data) {
        addImageInDiv(data, div);
    });
}

function addImageInDiv(fileName, divId) {
    let path = "/images/tmp/" + fileName;
    let imageId = fileName.replace(/\./g, '');
    if (divId === 'divAvatar') {
        divAvatar.empty();
        addImgAvatarAndBtn(fileName, path);
    } else {
        addImgToListAndBtn(fileName, path);
    }
}

function addImgAvatarAndBtn(divId, path) {
    divAvatar.append(
        `<img src=${path} class='card-img-top' id=${divId} alt='...'>
        <button type="button" onclick="deleteImage('divAvatar')" class="btn btn-primary mx-3 delete-image-loc">Delete image</button>`
    )
}

function addImgToListAndBtn(divId, path) {
    listImages.append(
        `<div class="shadow p-4 mb-4 bg-white" id="${divId}">               
                <img src=${path} id=${divId} class='card-img-top'  alt='...'>
                <button type="button" onclick="deleteImage('${divId}')" class="btn btn-primary mx-3 delete-image-loc">Delete image</button><br>              
                </div>`
    )
}

function loadBookFile() {
    let file = $("#add-hib-file-input").prop('files')[0];
    fetch('/api/admin/upload-file', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: file
    })
        .then(json)
        .then(function (resp) {
            addValueToFields(resp);
        });
}

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
    let img = book.coverImage;
    addImgAvatarAndBtn(img, pathToTmpPackage + img);
    for (const imageListElement of book.listImage) {
        if (img !== imageListElement.nameImage) {
            let nameImg = imageListElement.nameImage;
            let pathToImg = pathToTmpPackage + nameImg;
            addImgToListAndBtn(nameImg, pathToImg)
        }
    }
}

function deleteImage(id) {
    if (id === 'divAvatar') {
        divAvatar.empty();
    } else {
        document.getElementById(id).remove();
    }
}

function addNewBook() {
    // checkBoxOnOrOf();
    if (checkRequired() && confirm("Add this book?")) {
        let book = {};
        let otherLangFields = {};
        for (let tmpNameObject of nameObjectOfLocaleString) {
            let bookFields = {};
            for (let tmpNameVar of nameVarOfLocaleString) {
                bookFields[tmpNameVar] = $("#inp" + tmpNameObject + tmpNameVar).val()
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
        book["originalLanguageName"] = originalLanguage.val();
        book["show"] = disabledCheckBox.is(':checked');
        book['category'] = category;
        let imageList = [];
        if(divAvatar.find("img")[0] != null) {
            book["coverImage"] = divAvatar.find("img")[0].id;
            let allImages = $("#allImage").find("img");
            for (let img of allImages) {
                imageList.push(img.id);
            }
        }
        book["listImage"] = imageList;
        fetch('/admin/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(book)
        });
        location.reload();
    }
    if (uploadedBookName) {
        sendDeleteRequest(uploadedBookName);
        uploadedBookName = null;
    }
}


function doesFolderTmpExist() {
    fetch("admin/doesFolderTmpExist");
}


function getUnflattens(arr, parentId) {
    let output = [];
    for(const category of arr) {
        if(category.parentId == parentId) {
            let children = getUnflattens(arr, category.id);
            if(children.length) {
                category.childrens = children
            }
            output.push(category)
        }
    }
    return output;
}

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
