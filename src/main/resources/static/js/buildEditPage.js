let idd;
let nameObjectOfLocaleString;
let tmpArr;
let nameVarOfLocaleStringWithId;
let pathImageDefault = '../static/images/tempimage/';
let pathImageFin;
let nameImage;
let nameImageNew;

$(document).ready(getVarBookDTO());

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

function buildPage() {
    var html1 = '';
    for (let tmpNameObject of nameObjectOfLocaleString) {
        html1 += `<h5>${tmpNameObject}</h5>`;
        for (let tmpNameVar of nameVarOfLocaleStringWithId) {
            if (tmpNameVar === "id") {
                html1 += `<div class='form-group'>` +
                    `<label for='${tmpNameObject}${tmpNameVar}'>${tmpNameObject} ${tmpNameVar}</label>` +
                    `<input type='text' class='form-control' id='${tmpNameObject}${tmpNameVar}' ` +
                    `placeholder='${tmpNameObject} ${tmpNameVar}' readonly>` +
                    `</div>`;
            } else {
                html1 += `<div class='form-group'>` +
                    `<label for='${tmpNameObject}${tmpNameVar}'>${tmpNameObject} ${tmpNameVar}</label>` +
                    `<input type='text' class='form-control' id='${tmpNameObject}${tmpNameVar}' ` +
                    `placeholder='${tmpNameObject} ${tmpNameVar}'>` +
                    `</div>`;
            }
        }
    }
    html1 += ` <div class='car' style='width: 18rem;'>` +
        `<img id='myImage' src =''  class='card-img-top' alt='...'> ` +
        `</div>`;
    $('#bookEditPage').html(html1);

    for (key in tmpArr) {

        if (key !== "coverImage" && key !== "id") {
            for (key0 of nameVarOfLocaleStringWithId) {
                document.getElementById(key + key0).value = tmpArr[key][key0];
            }
        } else {
            nameImage = tmpArr[key];
            pathImageFin = pathImageDefault + nameImage;
            showImage(pathImageFin);
        }
    }
}


function showImage(x) {
    document.getElementById('myImage').src = x;
}

function sendUpdateBook() {
    var add = {};
    add['id'] = idd; ///
    for (let tmp of nameObjectOfLocaleString) {
        var asd = {};
        for (let tmpValue of nameVarOfLocaleStringWithId) {
            asd[tmpValue] = $('#' + tmp + tmpValue).val();
        }
        add[tmp] = asd;
    }
    var body02 = JSON.stringify(add);
    sendUpdateBookReq(body02);
}

async function sendUpdateBookReq(x) {
    await fetch("/admin/edit", {
        method: 'POST',
        body: x,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    // await pageBook(idPageable);
}

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
    fetch("lang")
        .then(status)
        .then(json)
        .then(function (resp) {
            nameVarOfLocaleStringWithId = resp;
            nameVarOfLocaleStringWithId.unshift("id");
            getBookDTOById(idd = localStorage.getItem('tmpEditBookId'));
        });
}

function getBookDTOById(id) {
    fetch("getBookDTOById/" + id)
        .then(status)
        .then(json)
        .then(function (resp) {
            tmpArr = resp;
            buildPage();
        });
}

function uploadFile() {
    const formData = new FormData();
    const fileField = document.getElementById("exampleFormControlFile1");
    nameImageNew = fileField.files[0].name;
    formData.append('file', fileField.files[0]);
    fetch('/admin/upload', {
        method: 'POST',
        body: formData
    });
}

$("#exampleFormControlFile1").change(function () {
    deleteOldImage(nameImage);
    uploadFile();
    showImage(pathImageDefault + nameImageNew);
});

function deleteOldImage(x) {
    fetch('/admin/deleteImage/', {
        method: 'POST',
        body: x
    });
}