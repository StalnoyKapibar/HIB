let divAvatar;
let listImages;
let yearOfEdition;
let pages;
let price;
let originalLanguage;
let pathToTmpPackage = '/images/tmp/';

function addPage() {
    getVarBookDTO();
    getAllLocales();
    doesFolderTmpExist();
    let html = '';
    for (let tmpNameObject of nameObjectOfLocaleString) {
        html += `<div class="shadow p-4 mb-4 bg-white">
                <h5>${tmpNameObject}</h5>`;
        for (let tmpNameVar of nameVarOfLocaleString) {
            html +=
                `<div class="shadow p-4 mb-4 bg-white">
                <div class='form-group mx-5'>
                <div class="row">
                <div class="col-0" for=${tmpNameObject}${tmpNameVar}>${tmpNameObject} ${tmpNameVar}</div>
                <div class="col-2 mr-1">
                <input type="radio" name="rb${tmpNameObject}" id="rb${tmpNameObject}${tmpNameVar}" value="${tmpNameVar}" autocomplete="off"> Translate from this language
                </div>
                <div class="col">
                <input type='text' class='form-control' id='inp${tmpNameObject}${tmpNameVar}'
                placeholder='${tmpNameObject} ${tmpNameVar}'>
                </div>
                <div class="col">
                <input type="checkbox" checked name="cb${tmpNameObject}" value="${tmpNameVar}" autocomplete="off"> Into this language
                </div></div></div></div>`;
        }
        html += `<button type="button" onclick="translateText('${tmpNameObject}')" class="btn btn-primary mx-3">Translate</button></div>`;
    }
    $('#newBookForm').html(html +
        `<div class="shadow p-4 mb-4 bg-white">
        <h5> Year Of Edition </h5>
        <input type="text" id="yearOfEdition" placeholder="Year Of Edition"><br><br>
        </div>
        <div class="shadow p-4 mb-4 bg-white">
        <h5> Pages </h5>
        <input type="number" id="pages" ><br><br>
        </div>
        <div class="shadow p-4 mb-4 bg-white">
        <h5> Price </h5>
        <input type="number" id="price" ><br><br>
        </div>
        <div class="shadow p-4 mb-4 bg-white">
        <h5> Original Language </h5>
        <select id="originalLanguage" >
        </select><br><br>
        </div>
        <div id = "allImage">
        <div class="shadow p-4 mb-4 bg-white">
        <div id="divLoadAvatar">
        <h4>Avatar</h4>
        <Label>Load avatar</Label>
        <input type="file" class="form-control-file" id="avatar" accept=".jpg" onchange="loadImage('avatar','divAvatar')">      
        </div>
        <div class='car' id='divAvatar' style='width: 18rem;'>    
        </div><br><br></div>
        <div class="shadow p-4 mb-4 bg-white">
        <h4>Another Image</h4>
        <Label>Load another image</Label>       
        <input type="file" class="form-control-file" id="loadAnotherImage" accept=".jpg" onchange="loadImage('loadAnotherImage','imageList')">
        <div class='car' id='imageList' style='width: 18rem;'>
        </div></div></div>`
    );
    divAvatar = $("#divAvatar");
    listImages = $("#imageList");
    yearOfEdition = $("#yearOfEdition");
    pages = $("#pages");
    price = $("#price");
    originalLanguage = $("#originalLanguage");

    for (let tmpNameVar of nameVarOfLocaleString) {
        originalLanguage.append(
            `<option value=${tmpNameVar.toUpperCase()}>${tmpNameVar.toUpperCase()}</option>`
        )
    }
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
        <button type="button" onclick="deleteImage('divAvatar')" class="btn btn-primary mx-3">Delete image</button>`
    )
}

function addImgToListAndBtn(divId, path) {
    listImages.append(
        `<div class="shadow p-4 mb-4 bg-white" id="${divId}">               
                <img src=${path} id=${divId} class='card-img-top'  alt='...'>
                <button type="button" onclick="deleteImage('${divId}')" class="btn btn-primary mx-3">Delete image</button><br>              
                </div>`
    )
}

function loadBookFile() {
    let file = $("#formBookFile").prop('files')[0];
    fetch('/loadFile', {
        method: 'PUT',
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
    yearOfEdition.val(`${book.yearOfEdition}`);
    pages.val(`${book.pages}`);
    price.val(`${book.price}`);
    originalLanguage.val(`${book.originalLanguage}`);
    let img = book.coverImage;
    addImgAvatarAndBtn(img, pathToTmpPackage + img)
    for (const imageListElement of book.imageList) {
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
    if (confirm("Add this book?")) {
        let book = {};
        for (let tmpNameObject of nameObjectOfLocaleString) {
            let bookFields = {};
            for (let tmpNameVar of nameVarOfLocaleString) {
                bookFields[tmpNameVar] = $("#inp" + tmpNameObject + tmpNameVar).val()
            }
            book[tmpNameObject] = bookFields;
        }
        book["yearOfEdition"] = yearOfEdition.val();
        book["pages"] = pages.val();
        book["price"] = price.val();
        book["originalLanguage"] = originalLanguage.val();
        book["coverImage"] = divAvatar.find("img")[0].id;
        let allImages = $("#allImage").find("img");
        let imageList = [];
        for (let img of allImages) {
            imageList.push(img.id);
        }
        book["imageList"] = imageList;
        fetch('/admin/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(book)
        });
        clearFields();
    }
}

function clearFields() {
    for (let tmpNameObject of nameObjectOfLocaleString) {
        for (let tmpNameVar of nameVarOfLocaleString) {
            $("#inp" + tmpNameObject + tmpNameVar).val('');
        }
    }
    yearOfEdition.val(``);
    pages.val(``);
    price.val(``);
    originalLanguage.val(``);
    divAvatar.empty();
    listImages.empty();
}

function doesFolderTmpExist() {
    fetch("admin/doesFolderTmpExist");
}