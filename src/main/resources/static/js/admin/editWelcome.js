let tmp;
let nameVarOfLocaleString;
var nameObjectOfLocaleString;
var nameObjectOfLocaleStringWithId;
var varBookDTO;
var tmpEditBookId;
var arrAllBooksByNumberPage;
let nameVarOfLocaleStringWithId;
var idPageable;
let nameLocalesBooks;
var idChangeLang = "en";
let arrNameImageNew = [];
let pathImageDefault = 'images/tmp/';
var nameImageCover = '';
let welcomeText = [];
let toggleShowDisabled = $("#toggleShowDisabled");
let repliedOn = true;

$(document).ready(
    getVarBookDTO(),
    getAllLocales(),
    pageBook(0),
    getLocales()
);

$(document).ready(function () {
    $(document).keypress(function (event) {
        let keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13' && $("#search-input-admin").val().trim() !== '') {
            $('#button-search-input-admin').click();
        }
    });
});

async function getVarBookDTO() {
    await fetch("/getVarBookDTO")
        .then(status)
        .then(json)
        .then(function (resp) {
            varBookDTO = resp;
        })
}

async function getAllLocales() {
    await fetch("/api/welcome/1")
        .then(json)
        .then((data) => {
            welcomeText = Object.values(data.body);
        });
    await fetch("/lang")
        .then(status)
        .then(json)
        .then(function (resp) {
            tmp = resp;
            nameVarOfLocaleStringWithId = tmp;
            nameVarOfLocaleStringWithId.unshift("id");
            nameVarOfLocaleString = nameVarOfLocaleStringWithId.filter(t => t !== "id");
        })
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

async function getLocales() {
    await fetch("/lang")
        .then(json)
        .then(function (resp) {
            nameLocalesBooks = resp;
        });
}

async function pageBook(x) {
    idPageable = x;
    await fetch(`/api/admin/pageable/${x}?disabled=${repliedOn}`)
        .then(status)
        .then(json)
        .then(function (resp_tmp) {
            arrAllBooksByNumberPage = resp_tmp.listBookDTO;
            var htmlTempPager = '';
            for (var i = 0; i < resp_tmp.totalPages; i++) {
                var z = 1 + i;
                let isCurrent = x === i;
                htmlTempPager += `<li class='page-item ${isCurrent ? 'currentItem' : ''}'>
                                     <a class='page-link ${isCurrent ? 'currentLink' : ''}' href='#' onclick='pageBook(${i})'>${z}</a>
                                  </li>`;
            }
            $('#pagination00').html(htmlTempPager);
            $('#pagination01').html(htmlTempPager);

            let htmlAddPage = varBookDTO;
            nameObjectOfLocaleStringWithId = Object.values(htmlAddPage);
            nameObjectOfLocaleString = nameObjectOfLocaleStringWithId.filter(t => t !== "id");
            let htmlTable = `<th scope='col'>id </th>`;

            htmlTable +=
                `<th scope="col">Name</th>` +
                `<th scope="col">Author</th>` +
                `<th scope="col">Description ${idChangeLang}</th>` +
                `<th scope='col'>Edit</th>` +
                `<th scope='col'>Delete</th>`;
            $('#table0').html(htmlTable);
            var html = '';
            for (let tmp_html of resp_tmp.listBookDTO) {
                html += `<tr id=${tmp_html.id}>` +
                    `<td id=${tmp_html.id}>${tmp_html.id}</td>`;
                html += `<td id='n${tmp_html.id}'>${convertOriginalLanguageRows(tmp_html.originalLanguage.name, tmp_html.originalLanguage.nameTranslit)}</td>`;
                html += `<td id='n${tmp_html.id}'>${convertOriginalLanguageRows(tmp_html.originalLanguage.author, tmp_html.originalLanguage.authorTranslit)}</td>`;

                for (let key in tmp_html) {
                    if (tmp_html[key] !== null && key === "description") {
                        let ad = tmp_html[key][idChangeLang];
                        html += `<td width="600" id='n${tmp_html.id}'>${ad}</td>`;
                    }
                }
                html +=
                    `<td>` +
                    `<button class="btn btn-info" onclick="openEdit(${tmp_html.id})"> ` +
                    `Edit` +
                    `</button>` +
                    `</td>` +
                    `<td>` +
                    `<button type='button'  onclick='delBook(${tmp_html.id})'  class='btn btn-danger'>` +
                    `Delete` +
                    `</button>` +
                    `</td>` +
                    `</tr>`;
            }
            $('#extra').html(html);
        });
    $('#search-admin-local-id').html(idChangeLang);
    getLocales().then(buildChangeLang);

}

function buildChangeLang() {

    var htmllang = '';
    for (var i = 0; i < nameVarOfLocaleString.length; i++) {
        var gh = nameVarOfLocaleString[i];
        htmllang += ` <a  class='dropdown-item'  onclick='chanLang(${i})'>${gh}</a>`;
    }
    $('#chlang1').html(htmllang);
}

function chanLang(x) {
    idChangeLang = nameVarOfLocaleString[x];
    $('#search-input-admin').val('');
    pageBook(idPageable);
}

<!--  old search that uses languages -->
// async function searchBook() {
//     $('#pagination00').empty();
//     $('#extra').empty();
//     let searchWord = $('#search-input-admin').val();
//     let searchLang = idChangeLang;
//     await fetch("/searchResult?request=" + searchWord + "&LANG=" + idChangeLang, {
//         method: "GET",
//         headers: {
//             'Content-Type': 'application/json',
//             'Accept': 'application/json'
//         }
//     })
//         .then(status)
//         .then(json)
//         .then(function (data) {
//             for (let i = 0; i < data.length; i++) {
//                 $('#extra').append(`<tr id="${data[i].id}">
//                     <td id="${data[i].id}">${data[i].id}</td>
//                     <td>${data[i].nameAuthorDTOLocale}</td>
//                     <td>${data[i].nameBookDTOLocale}</td>
//                     <td>
//                     <button type='button' onclick='buildEditBook(${data[i].id})'  data-toggle='modal'
//                     data-target='#asdddd'  class='btn btn-primary'>
//                     Edit
//                     </button>
//                     </td>
//                     <td>
//                     <button type='button'  onclick='delBook(${data[i].id})'  class='btn btn-danger'>
//                     Delete
//                     </button>
//                     </td>
//                     </tr>`
//                 );
//             }
//         });
// }

async function searchBook() {
    $('#pagination00').empty();
    $('#pagination01').empty();
    $('#extra').empty();
    let searchWord = $('#search-input-admin').val().toLowerCase().trim();
    await fetch("/api/admin/searchResult?request=" + searchWord + "&Show=" + toggleShowDisabled.is(':checked'), {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    })
        .then(status)
        .then(json)
        .then(function (data) {
            let html = '';
            for (let key in data) {
                let book = data[key];
                html += `<tr id="${book.id}"><td>${book.id} </td>
                             <td style="width: 20%">${book.name}<br>(${book.nameTranslit})</td>
                             <td style="width: 15%">${book.author}<br>(${book.authorTranslit})</td>
                             <td style="width: 50%" > ${book.desc}</td> 
                             <td> <button class="btn btn-info" onclick="openEdit(${book.id})"> Edit </button></td>
                             <td> <button type='button'  onclick="delBook(${book.id})"  class='btn btn-danger'> Delete</button> </td>
                         </tr>`;
            }
            $('#extra').html(html)
        });
}

$(document).ready(() => {
    $("body").on('click', '#search-admin-close', () => {
        $('#search-input-admin').val('');
        pageBook(idPageable);
    });
});

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
    if (confirm('Do you really want to DELETE book?')) {
        fetch("admin/del/" + x);
        var elem = document.getElementById(x);
        elem.parentNode.removeChild(elem);
    }
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
    html1 += `<button type='submit' onclick='openEdit(${tmpEditBookId})' data-dismiss='modal' class='btn btn-primary custom-centered m-3'>` +
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


function openEdit(id) {
    window.open("/edit/" + id, '_blank');
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

toggleShowDisabled.change(() => {
    repliedOn = toggleShowDisabled.prop('checked');
    pageBook(0).then(r => {
    });
});

function showImage(x) {
    document.getElementById('myImage').src = x;
}

function doesFolderTmpExist() {
    fetch("admin/doesFolderTmpExist");
}
