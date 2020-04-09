let divAvatar;
let imageList;
let yearOfEdition;
let pages;
let price;
let originalLanguage;
let newBook;
let pathToImgPackage = '/images/book';

function addPage() {
    getVarBookDTO();
    getAllLocales();
    doesFolderTmpExist();
    let html = '';
    for (let tmpNameObject of nameObjectOfLocaleString) {
        html += `<h5>${tmpNameObject}</h5>`;
        for (let tmpNameVar of nameVarOfLocaleString) {
            html +=
                `<div class='form-group mx-5'>
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
                </div>
                </div>
                </div>`;
        }
        html += `<button type="button" onclick="translateText('${tmpNameObject}')" class="btn btn-primary mx-3">Translate</button>`;
    }
    $('#newBookForm').html(html +
        `<h5> Year Of Edition </h5>
        <input type="text" id="yearOfEdition" placeholder="Year Of Edition"><br><br>
        <h5> Pages </h5>
        <input type="number" id="pages" ><br><br>
        <h5> Price </h5>
        <input type="number" id="price" ><br><br>
        <h5> Original Language </h5>
        <select id="originalLanguage" >
        </select>
        <h4>Cover Image</h4>
        <div class='car' id='divAvatar' style='width: 18rem;'>
        </div><br><br>
        <h4>Another Image</h4>
        <div class='car' id='imageList' style='width: 18rem;'>
        </div><br><br>`
    );
    divAvatar = $("#divAvatar");
    imageList = $("#imageList");
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
    let pathWithBookId = pathToImgPackage + book.id + "/";
    divAvatar.empty();
    imageList.empty();
    for (let tmpNameObject of nameObjectOfLocaleString) {
        for (let tmpNameVar of nameVarOfLocaleString) {
            $("#inp" + tmpNameObject + tmpNameVar).val(book[tmpNameObject][tmpNameVar]);
        }
    }
    yearOfEdition.val(`${book.yearOfEdition}`);
    pages.val(`${book.pages}`);
    price.val(`${book.price}`);
    originalLanguage.val(`${book.originalLanguage}`);
    divAvatar.attr("src", `${pathWithBookId}${book.coverImage}`);
    divAvatar.append(
        `<img id="avatarImage" src="${pathWithBookId}${book.coverImage}" class='card-img-top' alt='...'>
    <button type="button" onclick="deleteImage('avatarImage', '${book.coverImage}')" class="btn btn-primary mx-3">Delete image </button>`
    );
    for (const imageListElement of book.imageList) {
        let nameImg = imageListElement.nameImage;
        let pathToImg = '/images/book' + book.id + '/' + nameImg;
        if (!nameImg.includes('avatar')) {
            let newId = nameImg.replace(/\./g, '');
            imageList.append(
                `<div  id="${newId}">
                <img src=${pathToImg} class='card-img-top'  alt='...'>
                <button type="button" onclick="deleteImage('${newId}','${nameImg}')" class="btn btn-primary mx-3">Delete image</button>
                </div>
                <br><br>`
            )
        }
    }
    newBook = book;
}

function deleteImage(id, name) {
    if (id === 'avatarImage') {
        divAvatar.empty();
        newBook.coverImage = null;
    } else {
        $("#" + id).empty();
    }
    newBook.imageList = newBook.imageList.filter(function (img) {
        return img.nameImage !== name;
    });

    fetch('/admin/deleteImg', {
        method: 'POST',
        body: "img/book"+ newBook.id + "/" + name
    })
}

function isCoverImageNotNull() {
    if (newBook.coverImage == null) {
        alert("select avatar for book")
        return false;
    }
    return true;
}

function addNewBook() {
    if (isCoverImageNotNull() && confirm("Add this book?")) {
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
        book["coverImage"] = "avatar.jpg";
        let imageList = [];
        for (const imageListElement of newBook.imageList) {
            imageList.push(imageListElement);
        }
        book["imageList"] = imageList;
        fetch('/admin/addBook', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(book)
        });
        clearFields();
    }
}

function clearFields() {
    newBook = null;
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
    imageList.empty();
}

function doesFolderTmpExist() {
    fetch("admin/doesFolderTmpExist");
}