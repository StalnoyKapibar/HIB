let idd;
let divAvatar;
let listImages;
let nameObjectOfLocaleString;
let nameVarOfLocaleString;
let tmpArr;
let nameVarOfLocaleStringWithId;
let pathImageFin;
let pathImageFinWithoutImage;
let nameImage;
let nameImageCover = '';
let pathImageDefault = '/images/book';
let categoryName, categoryIdSrc;
let isShow = false;

$(document).ready(getVarBookDTO());

//Проверка на заполненность требуемых полей
function checkEditRequired() {
    if (category === undefined ){
        alert('Required field is not filled in: category!');
        return false;
    }
    for (let tmpNameObject of nameObjectOfLocaleString) {
        let test = $("#inpt" + tmpNameObject).val();
        if (test === ''){
            alert('Required field is not filled in: ' + tmpNameObject + '!');
            return false;
        }
        for (let tmpNameVar of nameVarOfLocaleString) {
            let varTest = $("#inp" + tmpNameObject + tmpNameVar).val();
            if (varTest === '') {
                alert('Required field is not filled in: ' + tmpNameObject + ' ' + tmpNameVar + '!')
                return false;
            }
        }
    }

    if(category === ""){
        alert('Required field is not filled in!');
        return false;
    }
    return true;
}

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
                <input type="radio" name="rb${tmpNameObject}" id="rb${tmpNameObject}${tmpNameVar}" value="${tmpNameVar}" autocomplete="off"> Translate from this language</div>
                <div class="col">
                <textarea type='text' class='form-control' id='inp${tmpNameObject}${tmpNameVar}'
                placeholder='${tmpNameObject} ${tmpNameVar}'></textarea>
                </div>
                <div class="col">
                <input type="checkbox" checked name="cb${tmpNameObject}" value="${tmpNameVar}" autocomplete="off"> Into this language</div></div></div></div>`;
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
                <div class="col-5 pl-5 ml-5  "><input type='text'  class='form-control '  id='inpt${tmpNameObject}'>
                </div> </div>
                <div class="row my-2">
                <div class="col-0" for=${tmpNameObject}>${tmpNameObject} transliterate&nbsp;&nbsp; </div>
                <div class="col-5 pl-5 ml-5  mr-1 "><input type='text' class='form-control ' id='in${tmpNameObject}'>
                </div> </div>
                </div>
                    <button id="yourDivId" type="button" onclick="transliterationText('${tmpNameObject}')" class="btn btn-primary mx-3 w-25">Transliterate</button>
                </div>`;

                for (let tmpNameVar of nameVarOfLocaleString) {
                    html += `<div class="card p-4 mb-4 bg-light">
                <div class='form-group mx-5'>
                <div class="row">
                <div class="col-0" for=${tmpNameObject}${tmpNameVar}>${tmpNameObject} ${tmpNameVar}<span class="required">*</span></div>
                <div class="col-2 mr-1">
                <input type="radio" name="rb${tmpNameObject}" id="rb${tmpNameObject}${tmpNameVar}" value="${tmpNameVar}" autocomplete="off"> Translate from this language</div>
                <div class="col">
                <input type='text' class='form-control' id='inp${tmpNameObject}${tmpNameVar}'
                placeholder='${tmpNameObject} ${tmpNameVar}'>
                </div>
                <div class="col">
                <input type="checkbox" checked name="cb${tmpNameObject}" value="${tmpNameVar}" autocomplete="off"> Into this language</div></div></div></div>`;
                    if (tmpNameVar === "gr") {
                        html += `<button type="button" onclick="translateText('${tmpNameObject}')" class="btn btn-primary mx-3 w-25">Translate</button></div>`
                    }
                }
            }
            return html;
        }
    }
}

//Функция, заполняющая поля данными из книги, полученной из базы
function buildEditPage() {
    $('#disabled').prop('checked', !tmpArr.show);
    var html1 = '';
    var htmlCategory = '';
    if (tmpArr.category === null) {

    } else {
        htmlCategory = '<div class="tab-pane fade" id="category" role="tabpanel" aria-labelledby="category-tab">\n' +
            '            <div class="card card-footer">\n' +
            '                <h5 class="bg-secondary p-2 text-white text-center">Category</h5>\n' +
            '               <div class="row">\n' +
            '               <div class="col-4" style="margin: 0 auto">\n' +
            '                <div class="input-group mb-3">\n' +
            '                  <input type="text" class="form-control" categoryid="' + tmpArr.category.id + '" id="categoryInput" readonly value="' + tmpArr.category.name['en'] + '">\n' +
            '                  <div class="input-group-append">\n' +
            '                    <button class="btn btn-outline-secondary" id="selectCategory" data-toggle="modal" data-target=".bd-example-modal-lg" type="button">Change category</button>\n' +
            '                  </div>\n' +
            '                </div>\n' +
            '               </div>\n' +
            '            </div>\n' +
            '            </div>\n' +
            '            </div></div>';
    }

    $('#bookEditForm').html(`<div class="tab-content" id="myTabContent">
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
                    <div class="card p-4 mb-4 bg-light">
            <h5> Year Of Edition </h5>
            <input type="text" class="w-25" id="yearOfEdition"><br><br>
            </div>
            <div class="card p-4 mb-4 bg-light">
            <h5> Pages </h5>
            <input type="number" class="w-25" id="pages" ><br><br>
            </div>
            <div class="card p-4 mb-4 bg-light">
            <h5> Price </h5>
            <input type="number" class="w-25" id="price" ><br><br>
            </div>
            <div class="card p-4 mb-4 bg-light">
            <h5> Original Language </h5>
            <select id="originalLanguage" class="w-25">
            </select><br><br>
            </div>
            <div id = "allImage">
            <div class="card p-4 mb-4 bg-light">
            <div id="divLoadAvatar">
                            <h4>Cover</h4>
                            <Label>Load cover</Label>
                            <input type="file" class="form-control-file" id="avatar" accept="image/jpeg,image/png,image/gif" onchange="loadTmpImage('avatar','divAvatar')">
                        </div>
                        <div class='car' id='divAvatar'>
                        </div>
            </div>
            <div class="card p-4 mb-4 bg-light">
            <h4>Another images</h4>
            <Label>Load another image</Label>
            <input type="file" class="form-control-file" id="loadAnotherImage" accept="image/jpeg,image/png,image/gif" onchange="loadTmpImage('loadAnotherImage','imageList')">
            <div class='car' id='imageList'>
            
            </div></div></div>
                </div>
            </div>

            ${htmlCategory}`);
    if (tmpArr.category === null) {

    } else {
        categoryName = tmpArr.category.categoryName;
        categoryIdSrc = tmpArr.category.id;
    }


    $('#categoryLabel').append(categoryName);
    $('#categoryModalBody').attr('data-id', categoryIdSrc);

    divAvatar = $("#divAvatar");
    listImages = $("#imageList");

    for (let tmpNameObject of nameObjectOfLocaleString) {

        html1 += `<div class="col card card-body my-2"><h5 class='bg-secondary p-2 text-white text-center'>${tmpNameObject}</h5>
                  <div class="row row-cols-2">`;
        for (let tmpNameVar of nameVarOfLocaleStringWithId) {
            if (tmpNameVar === "id") {
                html1 += `<div class='col  '><div class='form-group'>` +
                    `<label for='${tmpNameObject}${tmpNameVar}'>${tmpNameObject} ${tmpNameVar}</label>` +
                    `<input type='text' class='form-control' id='${tmpNameObject}${tmpNameVar}' ` +
                    `placeholder='${tmpNameObject} ${tmpNameVar}' readonly>` +
                    `</div></div>`;
            } else {

                html1 += `<div class='col'><div class='form-group'>` +
                    `<label for='${tmpNameObject}${tmpNameVar}'>${tmpNameObject} ${tmpNameVar}</label>` +
                    `<input type='text' class='form-control' id='${tmpNameObject}${tmpNameVar}' ` +
                    `placeholder='${tmpNameObject} ${tmpNameVar}'>` +
                    `</div></div>`;
            }
        }
        html1 += '</div></div>';
    }

    let originalLang = ``;
    for (let key in nameVarOfLocaleStringWithId) {
        originalLang += `<option >${nameVarOfLocaleStringWithId[key]}</option>`;
    }
    for (let tmpNameVar of nameVarOfLocaleString) {
        $('#originalLanguage').append(
            `<option value=${tmpNameVar.toUpperCase()}>${tmpNameVar.toUpperCase()}</option>`
        )
    }

    $('#inptname').attr("value", tmpArr.originalLanguage.name);
    $('#inpnameru').attr("value", tmpArr.name.ru);
    $('#inpnameen').attr("value", tmpArr.name.en);
    $('#inpnamefr').attr("value", tmpArr.name.fr);
    $('#inpnameit').attr("value", tmpArr.name.it);
    $('#inpnamede').attr("value", tmpArr.name.de);
    $('#inpnamecs').attr("value", tmpArr.name.cs);
    $('#inpnamegr').attr("value", tmpArr.name.gr);

    $('#inptauthor').attr("value", tmpArr.originalLanguage.author);
    $('#inpauthorru').attr("value", tmpArr.author.ru);
    $('#inpauthoren').attr("value", tmpArr.author.en);
    $('#inpauthorfr').attr("value", tmpArr.author.fr);
    $('#inpauthorit').attr("value", tmpArr.author.it);
    $('#inpauthorde').attr("value", tmpArr.author.de);
    $('#inpauthorcs').attr("value", tmpArr.author.cs);
    $('#inpauthorgr').attr("value", tmpArr.author.gr);

    document.getElementById("inpdescriptionru").value = tmpArr.description.ru;
    document.getElementById("inpdescriptionen").value = tmpArr.description.en;
    document.getElementById("inpdescriptionfr").value = tmpArr.description.fr;
    document.getElementById("inpdescriptionit").value = tmpArr.description.it;
    document.getElementById("inpdescriptionde").value = tmpArr.description.de;
    document.getElementById("inpdescriptioncs").value = tmpArr.description.cs;
    document.getElementById("inpdescriptiongr").value = tmpArr.description.gr;

    $('#inptedition').attr("value", tmpArr.originalLanguage.edition);
    $('#inpeditionru').attr("value", tmpArr.edition.ru);
    $('#inpeditionen').attr("value", tmpArr.edition.en);
    $('#inpeditionfr').attr("value", tmpArr.edition.fr);
    $('#inpeditionit').attr("value", tmpArr.edition.it);
    $('#inpeditionde').attr("value", tmpArr.edition.de);
    $('#inpeditioncs').attr("value", tmpArr.edition.cs);
    $('#inpeditiongr').attr("value", tmpArr.edition.gr);

    $('#yearOfEdition').attr("value", tmpArr.yearOfEdition);
    $('#pages').attr("value", tmpArr.pages);
    $('#price').attr("value", tmpArr.price);
    $('#inname').attr("value", transliterationText("name"));
    $('#inauthor').attr("value", transliterationText("author"));
    $('#inedition').attr("value", transliterationText("edition"));
    document.getElementById('originalLanguage').value = tmpArr.originalLanguageName;

    //Модальное окно со всеми изображениями книги, кроме обложки
    function buildEditImageModal(){
        let x = 0;
        for (let key in tmpArr.listImage) {
            if(tmpArr.listImage[key].nameImage !== tmpArr.coverImage){
                $('#editModalImages').append(`
                <div class="col w-50">
                    <div class="card mb-4" id="bookImage${key}">
                        <div class="card-header"><span class="font-weight-bold">Image:</span></br>${tmpArr.listImage[key].nameImage}</div>
                        <div class="card-body">
                            <img src ='${pathImageDefault + idd + '/'}${tmpArr.listImage[key].nameImage}' class="card-img" alt='...'>
                        </div>
                        <div class="card-footer">
                            <div class="row align-items-center">
                                <div class="btn-group col" role="group">
                                    <button type="button" class="btn btn-danger" onclick="deleteImage(${key})">Delete image</button>
                                    <button type="button" class="btn btn-info" onclick="changeCover(${key})">Choose as cover</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>`);
                x++;
            }
            if(x !== 0 && x % 2 === 0){
                $('#editModalImages').append(`<div class="w-100"></div>`);
            }
        }
        if(x % 2 !== 0){
            $('#editModalImages').append(`<div class="col container w-50"></div>`);
        }
    }

    $('#bookImages').on('show.bs.modal', buildEditImageModal);

    $('#bookImages').on('hidden.bs.modal', function() {
        $('#editModalImages').html("");
    });

    //Карточка с обложкой книги, с кнопкой на модалку с остальными изображениями
    $('#bookEditPageForImg').html(`
                <div class="row">
                    <div class="col-3"></div>
                    
                    <div class="col-6">
                        <div class="card">
                            <h4 class="card-header">Cover Image</h4>
                            <div class="car card-body">
                                <img id='myImage' src =''  class='card-img' alt='...'> 
                            </div>
                            <div class="card-footer">
                                <div class="row align-items-center">
                                    <div class="btn-group col" role="group">
                                    <button type="button" id="deleteImage-button" class="btn btn-danger" onclick="deleteCoverImage(nameImageCover.toString())">Delete cover</button>
                                    <button type="button" id="modal-button" class="btn btn-info" data-toggle="modal" data-target="#bookImages">Other images</button>
                                </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-3"></div>
                </div>`);

    //20 строчек наполнения оперативки картинками из книги
    for (let key in tmpArr) {
        if (key !== "id" && key !== "imageList" && key !== "coverImage") {
            for (let key0 of nameVarOfLocaleStringWithId) {
                document.getElementById(key + key0).value = tmpArr[key][key0];
            }
        }

        for (let key in tmpArr) {
            if (key === "coverImage") {
                nameImageCover = tmpArr[key];
                nameImage = tmpArr[key];
                pathImageFinWithoutImage = pathImageDefault + idd + '/';
                pathImageFin = pathImageFinWithoutImage + nameImage;
                showImage(pathImageFin);
            }
            if (key === "imageList") {
                listImages = tmpArr[key];
            }
        }
    }
}

//Заполнение категории
function getCategoryName(event) {
    $('.btn-outline-primary').removeClass('active');
    $(event).addClass('active');
    selectedCategoryName = event.innerText;
    categoryIdSrc = event.getAttribute('categoryid');
    category = {
        id: categoryIdSrc,
    };
    $('#categoryLabel').empty().append('Selected category: ' + selectedCategoryName);
    $("#categoryInput").attr('categoryid', categoryIdSrc).val(selectedCategoryName);

}

$(document).ready(() => {
    row =
        `<link href="/static/css/admin/adminTree.css" rel="stylesheet">
<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="categoryLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="categoryLabel">Selected category: </h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">×</span>
        </button>
      </div>
      <div id="categoryModalBody" class="modal-body">
      </div>
       <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Confirm</button>
      </div>
    </div>
  </div>
</div>`;
    $('body').append(row);
});

$(document).on('click', '#selectCategory', () => {
    if (isShow === false) {
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
                isShow = true;
            });

        function getUnflatten(arr, parentId) {
            let output = [];
            for (const category of arr) {
                if (category.parentId == parentId) {
                    let children = getUnflatten(arr, category.id);
                    if (children.length) {
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
                $('#categoryModalBody').append(`<figure id="${category[i].categoryName}"></figure><br>`);
                treeRow =
                    `<ul class="col-12 tree">
                <li class="col-12">
                    <code class="btn-outline-primary" parent="${category[i].parentId}" view-order="${category[i].viewOrder}"
                    categoryid="${category[i].id}" onclick="getCategoryName(this)">${category[i].categoryName}
                    </code>
                    <ul>
                     ${setChilds(category[i].childrens)}
                    </ul>
                </li>
            </ul>`;
                $(`#${category[i].categoryName}`).append(treeRow);
                $(`.btn-outline-primary[categoryid="${categoryIdSrc}"]`).addClass('active');
            }
        }


        function setChilds(category) {
            let row = '';
            for (let i in category) {
                if (category[i].childrens === undefined) {
                    row +=
                        `<li>
                    <code class="btn-outline-primary" parent="${category[i].parentId}" view-order="${category[i].viewOrder}"
                      categoryid="${category[i].id}" onclick="getCategoryName(this)">${category[i].categoryName}</code>
                </li>`;
                } else {
                    row +=
                        `<li>
                    <code class="btn-outline-primary"
                     parent="${category[i].parentId}" view-order="${category[i].viewOrder}"
                      categoryid="${category[i].id}" onclick="getCategoryName(this)">${category[i].categoryName}</code>
                      <ul>${setChilds(category[i].childrens)}</ul>
                </li>`;
                }
            }
            $(`.btn-outline-primary[categoryid="${categoryIdSrc}"]`).addClass('active');
            return row;
        }

    } else {
    }

});

//Функция отображения обложки
function showImage(x) {
    let y = 0;
    for(let i = 0; i < tmpArr.listImage.length; i++){
        if(tmpArr.listImage[i].nameImage.localeCompare(nameImageCover) === 0){
            y++;
        }
    }
    if(y > 0){
        document.getElementById('myImage').src = x;
    } else {
        document.getElementById('myImage').src = '/images/service/noimage.png';
        document.getElementById('deleteImage-button').setAttribute('disabled', 'disabled');
    }
}

//Функция отправки книги на обновление
function sendEditBook() {

    if(checkEditRequired() && confirm("Edit this book?")){
        let book = {};
        book['id'] = idd;
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
        category = {id: $('#categoryInput').attr('categoryid')};
        book["originalLanguage"] = otherLangFields;
        if(divAvatar.find("img")[0] != null) {
            book["coverImage"] = divAvatar.find("img")[0].id;
        } else {
            book['coverImage'] = nameImageCover;
        }
        book['show'] = (!$("#disabled").is(':checked'));
        book['yearOfEdition'] = $('#yearOfEdition').val();
        book['pages'] = $('#pages').val();
        book['price'] = $('#price').val();
        book['originalLanguageName'] = $('#originalLanguage').val();
        book['category'] = category;

        // Ниже формируется список изображений так, чтобы нельзя было загрузить изображение с именем,
        // которое уже числится в списке изображений (имена должны быть уникальны!)
        let allImages = $("#allImage").find("img");
        let imageList = [];

        for (let img of allImages) {
            let image = {};
            let x = 0;
            for (let i = 0; i < tmpArr.listImage.length; i++) {
                if(img.id == tmpArr.listImage[i].nameImage){
                    x++
                }
            }
            if(x === 0){
                image["nameImage"] = img.id;
                imageList.push(image);
            }
        }
        let imageListTmpPattern = [];
        for (let index = 0; index < imageList.length; index++) {
            imageListTmpPattern[index] = imageList[index];
        }
        for (let index = 0; index < imageListTmpPattern.length; index++) {
            imageListTmpPattern[index].id = imageList[index].id;
            imageListTmpPattern[index].nameImage = imageList[index].nameImage;
        }

        if (imageListTmpPattern.length === 1 && imageListTmpPattern[0].nameImage.localeCompare("") === 0) {
            book["listImage"] = tmpArr.listImage;
        } else {
            let indexListImage = tmpArr.listImage.length;
            for (let index = tmpArr.listImage.length + 1; index <= imageListTmpPattern.length + indexListImage; index++) {
                tmpArr.listImage.push(imageListTmpPattern[index - indexListImage - 1]);
            }
            book["listImage"] = tmpArr.listImage;
        } // Здесь заканчивается наполнение списка изображений книги новыми картинками

        sendUpdateBookReq(book).then(r => {
            window.close();
            //window.location.href = document.referrer;
        });
    } else {}
}

async function sendUpdateBookReq(x) {
    await fetch("/admin/edit", {
        method: 'POST',
        body: JSON.stringify(x),
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Accept': 'application/json'
        }
    });
}

//Функция смены обложки, кнопка в модалке
function changeCover(x){
    //sendEditBook(1);
    if(confirm('Set "' + tmpArr.listImage[x].nameImage + '" as cover? Page will be reloaded, all input data will be lost')){
        $.ajax({
            type: "GET",
            url: "/api/book/" + tmpArr.id,
            success: (book) => {
                book["coverImage"] = tmpArr.listImage[x].nameImage;
                sendUpdateBookReq(book).then(r => {
                    window.close();
                });
            }
        })
    }
}

//Функция удаления изображения из списка книги
function deleteImage(x) {
    let delImg = idd + '/' + tmpArr.listImage[x].nameImage;
    if (confirm('Delete "' + tmpArr.listImage[x].nameImage + '"?')) {
        deleteImageFromDB(tmpArr.listImage[x].id);
        tmpArr.listImage.splice(x, 1);
        if(tmpArr.coverImage === null && (tmpArr.listImage < 3)){
            $('#editModalImages').append(`<div class="col container w-50"></div>`);
        }
        $('#bookImage' + x).remove();
        $.ajax({
            type: 'POST',
            url: '/admin/deleteImageByEditPage',
            data: delImg,
            cache:false,
            contentType: false,
            processData: false
        })
    }
}

//Функция удаления изображения из базы
function deleteImageFromDB(id) {
    $.ajax({
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        url: '/admin/deleteImageFromDB',
        data: JSON.stringify(id),
        cache:false,
        processData: false
    });
}

//Функция удаления обложки
function deleteCoverImage() {
    if (confirm('Delete cover image "' + nameImageCover + '"? Page will be reloaded, all input data will be lost')) {
        let delImg = idd + '/' + tmpArr.coverImage.replace(/([^A-Za-zА-Яа-я0-9.]+)/gi, '-');
        let coverId; //получение id обложки в списке изображений книги
        let spliceId;
        for(let i = 0; i < tmpArr.listImage.length; i++){
            if(tmpArr.listImage[i].nameImage.localeCompare(nameImageCover) === 0){
                coverId = tmpArr.listImage[i].id;
                spliceId = i;
            }
        }
        tmpArr.coverImage = null; //обнуление обложки временной книги
        if(coverId != null){
            deleteImageFromDB(coverId);
        }
        tmpArr.listImage.splice(spliceId, 1); //удаление обложки из списка изображений
        showImage('/images/service/noimage.png');
        document.getElementById('deleteImage-button').setAttribute('disabled', 'disabled');

        $.ajax({    //Удаление файла обложки из папки изображений книги
            type: 'POST',
            url: '/admin/deleteImageByEditPage',
            data: delImg,
            cache:false,
            contentType: false,
            processData: false,
            success:
                $.ajax({    //Присвоение книге обложки с пустым названием "" для корректного отображение картинки "noimage" на главной странице
                    type: "GET",
                    url: "/api/book/" + tmpArr.id,
                    success: (book) => {
                        book["listImage"] = tmpArr.listImage;
                        book["coverImage"] = "";
                        sendUpdateBookReq(book).then(r => {
                            window.close()
                        });
                    }
                }),
        });
    }
}

//Функция подгрузки картинок во временную папку
function loadTmpImage(nameId, div) {
    const formData = new FormData();
    let fileImg = $("#" + nameId).prop('files')[0];
    let fileName = fileImg.name.replace(/([^A-Za-zА-Яа-я0-9.]+)/gi, '-');
    formData.append('file', fileImg, fileName);
    $.ajax({
        type: 'POST',
        url: '/admin/upload',
        data: formData,
        cache:false,
        contentType: false,
        processData: false
    }).then(function () {
        addImageInDiv(fileName, div);
    });
}

//Функция распределения картинок по "Обложка" или "Остальное"
function addImageInDiv(fileName, divId) {
    let path = "/images/tmp/" + fileName;
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
            <button type="button" onclick="deleteTmpImage('${divId}')" class="btn btn-danger mx-3 col-2" style="height: 2.5rem"><i class="material-icons">delete</i></button>
        </div>`
    )
}
//Остальное
function addImgToListAndBtn(divId, path) {
    listImages.append(
        `<div class="row align-items-center w-50 my-3" id=${divId}>
            <img src=${path} id=${divId} class='card-img-top col-8' alt='...'>
            <button type="button" onclick="deleteTmpImage('${divId}')" class="btn btn-danger mx-3 col-2" style="height: 2.5rem"><i class="material-icons">delete</i></button>
        </div>`
    )
}
//Функция удаления временного изображения
function deleteTmpImage(id) {
    if (id === 'divAvatar') {
        divAvatar.empty();
    } else {
        document.getElementById(id).remove();
    }
}

//Наполнение временной книги данными из базы (насколько я понял)
function getVarBookDTO() {
    fetch("/getVarBookDTO")
        .then(status)
        .then(json)
        .then(function (resp) {
            nameObjectOfLocaleString = Object.values(resp).filter(t => t !== "id");
            getAllLocales();
        });
}

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
            getBookDTOById(idd = last_segment);
        });
}

function getBookDTOById(id) {
    fetch("/api/book/" + id)
        .then(status)
        .then(json)
        .then(function (resp) {
            tmpArr = resp;
            console.log(tmpArr);
            buildEditPage();
        });
}


