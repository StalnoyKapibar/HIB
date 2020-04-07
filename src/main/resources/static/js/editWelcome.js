let tmp;
let nameVarOfLocaleString;
var nameObjectOfLocaleString;
var nameObjectOfLocaleStringWithId;
var varBookDTO;
var tmpEditBookId;
var arrAllBooksByNumberPage;
let nameVarOfLocaleStringWithId;
var idPageable;
var idChangeLang = "en";
let arrNameImageNew = [];
let pathImageDefault = 'images/tmp/';
var nameImageCover = '';
let newBook;

$(document).ready(getVarBookDTO(), getAllLocales(), pageBook(0));

async function getVarBookDTO() {
    await fetch("/getVarBookDTO")
        .then(status)
        .then(json)
        .then(function (resp) {
            varBookDTO = resp;
        })
}

async function getAllLocales() {
    await fetch("/lang")
        .then(status)
        .then(json)
        .then(function (resp) {
            tmp = resp;
            nameVarOfLocaleStringWithId = tmp;
            nameVarOfLocaleStringWithId.unshift("id");
            nameVarOfLocaleString = nameVarOfLocaleStringWithId.filter(t => t !== "id");
            var html = '';
            for (let tmp_html of nameVarOfLocaleString) {
                html += `<label for = ${tmp_html}>${tmp_html}</label>` +
                    `<input type='text' class='form-control' id='${tmp_html}' ` +
                    `aria-describedby='emailHelp'>`;
            }
            $('#form_id').html(html + `<button type='submit' onclick='funcStart()' class='btn btn-primary'>Submit</button>`);
        })
}

function funcStart() {
    var aniArgs = {};
    for (let tmp_html of tmp) {
        aniArgs[tmp_html] = $('#' + tmp_html).val();
    }
    var body02 = JSON.stringify({
        body: aniArgs
    });
    updateWelcome(body02);
}

async function updateWelcome(x) {
    await fetch("/welcome/edit", {
        method: 'POST',
        body: x,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
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

async function pageBook(x) {
    idPageable = x;
    await fetch("/admin/pageable/" + x)
        .then(status)
        .then(json)
        .then(function (resp_tmp) {
            arrAllBooksByNumberPage = resp_tmp.listBookDTO;
            var htmlTempPager = '';
            for (var i = 0; i < resp_tmp.totalPages; i++) {
                var z = 1 + i;
                htmlTempPager += `<li class='page-item'><a class='page-link' href='#' onclick='pageBook(${i})'>${z}</a></li>`;
            }
            $('#pagination00').html(htmlTempPager);
            buildChangeLang();
            var htmlAddPage = varBookDTO;
            nameObjectOfLocaleStringWithId = Object.values(htmlAddPage);
            nameObjectOfLocaleString = nameObjectOfLocaleStringWithId.filter(t => t !== "id");
            var htmlTable = `<th scope='col'>id ${idChangeLang}</th>`;
            for (let dd of nameObjectOfLocaleStringWithId) {
                if (dd !== "desc" && dd !== "edition") {
                    htmlTable += `<th scope='col'>${dd} ${idChangeLang}</th>`;
                }
            }
            htmlTable +=
                `<th scope='col'>Edit</th>` +
                `<th scope='col'>Delete</th>`;
            $('#table0').html(htmlTable);
            var html = '';
            for (let tmp_html of resp_tmp.listBookDTO) {
                html += `<tr id=${tmp_html.id}>` +
                    `<td id=${tmp_html.id}>${tmp_html.id}</td>`;
                for (key in tmp_html) {
                    if (tmp_html[key] !== null) {
                        if (key !== "id" && key !== "coverImage" && key !== "imageList" && key !== "desc" && key !== "edition"
                            && key !== "yearOfEdition" && key !== "pages" && key !== "price" && key!=="originalLanguage") {
                            var ad = tmp_html[key][idChangeLang];
                            html += `<td id='n${tmp_html.id}'>${ad}</td>`;
                        }
                    }
                }
                html +=
                    `<td>` +
                    `<button type='button' onclick='buildEditBook(${tmp_html.id})'  data-toggle='modal'` +
                    `data-target='#asdddd'  class='btn btn-primary'> ` +
                    `Edit` +
                    `</button>` +
                    `</td>` +
                    `<td>` +
                    `<button type='button'  onclick='delBook(${tmp_html.id})'  class='btn btn-primary btn-danger'>` +
                    `Delete` +
                    `</button>` +
                    `</td>` +
                    `</tr>`;
            }
            $('#extra').html(html);
        });
    buildChangeLang();
}

function addPage() {
    doesFolderTmpExist();
    var html = '';
    for (let tmpNameObject of nameObjectOfLocaleString) {
        html += `<h5>` + tmpNameObject + `</h5>`;
        for (let tmpNameVar of nameVarOfLocaleString) {
            html +=
                `<div class='form-group mx-5'>` +
                `<div class="row">` +
                `<div class="col-0" for=${tmpNameObject}${tmpNameVar}>${tmpNameObject} ${tmpNameVar}</div>` +
                `<div class="col-2 mr-1">` +
                `<input type="radio" name="rb${tmpNameObject}" id="rb${tmpNameObject}${tmpNameVar}" value="${tmpNameVar}" autocomplete="off"> Translate from this language` +
                `</div>` +
                `<div class="col">` +
                `<input type='text' class='form-control' id='inp${tmpNameObject}${tmpNameVar}'` +
                `placeholder='${tmpNameObject} ${tmpNameVar}'>` +
                `</div>` +
                `<div class="col">` +
                `<input type="checkbox" checked name="cb${tmpNameObject}" value="${tmpNameVar}" autocomplete="off"> Into this language` +
                `</div>` +
                `</div>` +
                `</div>`;
        }
        html += `<button type="button" onclick="translateText('${tmpNameObject}')" class="btn btn-primary mx-3">Translate</button>`;
    }
    $('#newBookForm').html(html +
        `<h5> Year Of Edition </h5>` +
        `<input type="text" id="yearOfEdition" placeholder="Year Of Edition"><br><br> ` +
        `<h5> Pages </h5>` +
        `<input type="number" id="pages" ><br><br> ` +
        `<h5> Price </h5>` +
        `<input type="number" id="price" ><br><br> ` +
        `<h5> Original Language </h5>` +
        `<select id="originalLanguage" >`+
        `</select>`+
        `<h4>Cover Image</h4>` +
        `<div class='car' id='divImage' style='width: 18rem;'>` +
        `<img id='myImage' class='card-img-top' alt='...'> ` +
        `</div><br><br>` +
        `<div class='car' id='imageList' style='width: 18rem;'>` +
        `</div><br><br>`
    );
    for (let tmpNameVar of nameVarOfLocaleString) {
        $("#originalLanguage").append(
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
    $("#myImage").attr("src", ``);
    $("#imageList").empty();
    for (let tmpNameObject of nameObjectOfLocaleString) {
        for (let tmpNameVar of nameVarOfLocaleString) {
            $("#inp" + tmpNameObject + tmpNameVar).val(book[tmpNameObject][tmpNameVar]);
        }
    }
    $("#yearOfEdition").val(`${book.yearOfEdition}`);
    $("#pages").val(`${book.pages}`);
    $("#price").val(`${book.price}`);
    $("#originalLanguage").val(`${book.originalLanguage}`);
    $("#myImage").attr("src", `/images/book${book.id}/avatar.jpg`);
    for (const imageListElement of book.imageList) {
        let pathToImg = '/images/book' + book.id + '/' + imageListElement.nameImage;
        if (!imageListElement.nameImage.includes('avatar')) {
            $("#imageList").append(
                `<img src=${pathToImg} class='card-img-top' alt='...'><br><br> `
            )
        }
    }
    newBook = book;
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
        book["yearOfEdition"] = $("#yearOfEdition").val();
        book["pages"] = $("#pages").val();
        book["price"] = $("#price").val();
        book["originalLanguage"] = $("#originalLanguage").val();
        book["coverImage"] = "avatar.jpg";
        let listImage = [];
        for (const imageListElement of newBook.imageList) {
            listImage.push(imageListElement);
        }
        book["listImage"] = listImage;
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
    $("#yearOfEdition").val(``);
    $("#pages").val(``);
    $("#price").val(``);
    $("#originalLanguage").val(``);
    $("#myImage").attr("src", ``);
    $("#imageList").empty();
}

function addBook() {
    var add = {};
    for (let tmp of nameObjectOfLocaleString) {
        var asd = {};
        for (let tmpValue of nameVarOfLocaleString) {
            asd[tmpValue] = $('#' + "inp" + tmp + tmpValue).val();
        }
        add[tmp] = asd;
    }
    add['coverImage'] = nameImageCover;
    var arrImageTmp = arrNameImageNew.filter(t => t !== "");
    var arrImageFin = [];

    for (var tmp of arrImageTmp) {
        arrImageFin.push(JSON.parse('{"nameImage":"' + tmp + '"}'));
    }
    add['imageList'] = arrImageFin;
    var body02 = JSON.stringify(add);
    addBookReq(body02);
}

async function addBookReq(x) {
    await fetch("/admin/add", {
        method: 'POST',
        body: x,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    resetForms();
    pageBook(idPageable);
}

function delBook(x) {
    fetch("admin/del/" + x);
    var elem = document.getElementById(x);
    elem.parentNode.removeChild(elem);
}

function buildEditBook(xx) {
    tmpEditBookId = xx;
    var html1 = '';
    for (let tmpNameObject of nameObjectOfLocaleString) {
        html1 += `<div class="container">` +
            `<h5 id="ss${tmpNameObject}"></h5>` +
            `<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#${tmpNameObject}">open ${tmpNameObject}</button>` +
            `<div id="${tmpNameObject}" class="collapse">`;
        for (let tmpNameVar of nameVarOfLocaleStringWithId) {
            html1 += `<div class='form-group'>` +
                `<label for='${tmpNameObject}${tmpNameVar}'>${tmpNameObject} ${tmpNameVar}</label>` +
                `<input type='text' class='form-control' id='${tmpNameObject}${tmpNameVar}' ` +
                `placeholder='${tmpNameObject} ${tmpNameVar}' readonly>` +
                `</div>`;
        }
        html1 += `</div>` +
            `</div>`;
    }
    html1 += `<button type='submit' onclick='openEdit()' data-dismiss='modal' class='btn btn-primary custom-centered m-3'>` +
        `Edit Book` +
        `</button>`;
    $('#editBookForm').html(html1);
    for (var rt of arrAllBooksByNumberPage) {
        if (rt.id === tmpEditBookId) {
            var tmpArr = rt;
        }
    }
    for (key in tmpArr) {
        if (key !== "id" && key !== "coverImage" && key !== "imageList") {
            for (key0 of nameVarOfLocaleStringWithId) {
                document.getElementById(key + key0).value = tmpArr[key][key0];
                if (idChangeLang === key0) {
                    document.getElementById('ss' + key).innerText = key + ' ' + key0 + ': ' + tmpArr[key][key0];
                }
            }
        }
    }
}

function buildChangeLang() {
    var htmllang = '';
    for (var i = 0; i < nameVarOfLocaleString.length; i++) {
        var gh = nameVarOfLocaleString[i];
        htmllang += `<button type='button' class='btn btn-secondary' onclick='chanLang(${i})'>${gh}</button>`;
    }
    $('#chlang1').html(htmllang);
}

function chanLang(x) {
    idChangeLang = nameVarOfLocaleString[x];
    pageBook(idPageable);
}

function openEdit() {
    localStorage.setItem('tmpEditBookId', tmpEditBookId);
    window.open('/edit', '_blank');
}

function uploadImageNew() {
    const formData = new FormData();
    const fileField = document.getElementById("exampleFormFile");
    arrNameImageNew.push(fileField.files[0].name);
    formData.append('file', fileField.files[0]);
    fetch('/admin/upload', {
        method: 'POST',
        body: formData
    })
        .then(status)
        .then(json)
        .then(function (resp) {
            buildCarousel();
        });
}

$("#exampleFormFile").change(function () {
    uploadImageNew();
});

function buildCarousel() {
    var countForActive = 0;
    var tmpHtmlForCarousel = '';
    var tmpHtmlForCarouselIndicators = '';
    for (var i = 0; i < arrNameImageNew.length; i++) {
        if (arrNameImageNew[i] !== '') {
            countForActive++;
            if (countForActive === 1) {
                tmpHtmlForCarouselIndicators +=
                    `<li id="qw${i}" data-target='#carouselExampleCaptions' data-slide-to=${i} class='active'>` + `</li>`;
                tmpHtmlForCarousel +=
                    `<div id="qw${i}" class='carousel-item active'>` +
                    `<img src=${pathImageDefault}${arrNameImageNew[i]} class='d-block w-100' alt='...'>` +
                    `<div class='carousel-caption d-none d-md-block'>` +
                    `<button type="button" onclick="setImageCover(${i})" class="btn btn-success">Change image cover</button>` +
                    `<p><button type="button" onclick="deleteTmpImage(${i})" class="btn btn-danger m-3">Delete</button><p>` +
                    `</div>` +
                    `</div>`;
            } else {
                tmpHtmlForCarouselIndicators +=
                    `<li id="qw${i}" data-target='#carouselExampleCaptions' data-slide-to=${i}></li>`;
                tmpHtmlForCarousel +=
                    ` <div id="qw${i}" class="carousel-item">` +
                    `<img src=${pathImageDefault}${arrNameImageNew[i]} class='d-block w-100' alt="...">` +
                    `<div class='carousel-caption d-none d-md-block'>` +
                    `<button type="button" onclick="setImageCover(${i})" class="btn btn-success">Change image cover</button>` +
                    `<p><button type="button" onclick="deleteTmpImage(${i})" class="btn btn-danger m-3">Delete</button><p>` +
                    `</div>` +
                    `</div>`;
            }
        }
    }

    $('#test0').html(tmpHtmlForCarouselIndicators);
    $('#test1').html(tmpHtmlForCarousel);
}

function setImageCover(x) {
    nameImageCover = arrNameImageNew[x];
    showImage(pathImageDefault + nameImageCover);
}

function deleteTmpImage(x) {
    var delTmp = arrNameImageNew[x];
    arrNameImageNew[x] = '';
    buildCarousel();
    fetch('/admin/deleteImage', {
        method: 'POST',
        body: delTmp
    }).then(r => {
        if (delTmp === nameImageCover) {
            showImage(pathImageDefault + nameImageCover)
        }
    });
}

function resetForms() {
    document.getElementById('newBookForm').reset();
    $("#exampleFormFile").val('');
    $("#carouselExampleCaptions").html('');
    nameImageCover = '';
    showImage('');
}

function showImage(x) {
    document.getElementById('myImage').src = x;
}

function doesFolderTmpExist() {
    fetch("admin/doesFolderTmpExist");
}