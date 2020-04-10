let idd;
let nameObjectOfLocaleString;
let tmpArr;
let nameVarOfLocaleStringWithId;
let pathImageDefault = 'images/book';
let pathImageFin;
let pathImageFinWithoutImage;
let nameImage;
let listImages;
let nameImageCover = '';

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
        html1 += `<h5 class='bg-secondary text-white text-center'>${tmpNameObject}</h5><div class="row row-cols-2">`;
        for (let tmpNameVar of nameVarOfLocaleStringWithId) {
            if (tmpNameVar === "id") {
                html1 += `<div class='col'><div class='form-group'>` +
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
        html1 += '</div>';
    }
    html1 += `<h4>Cover Image</h4>` +
        `<div class='car' style='width: 18rem;'>` +
        `<img id='myImage' src =''  class='card-img-top' alt='...'> ` +
        `</div>`;
    $('#bookEditPage').html(html1);
    for (key in tmpArr) {
        if (key !== "id" && key !== "imageList" && key !== "coverImage") {
            for (key0 of nameVarOfLocaleStringWithId) {
                document.getElementById(key + key0).value = tmpArr[key][key0];
            }
        } else {
            if (key === "coverImage") {
                nameImageCover = tmpArr[key];
                nameImage = tmpArr[key];
                pathImageFinWithoutImage = pathImageDefault + idd + '/';
                pathImageFin = pathImageFinWithoutImage + nameImage;
                showImage(pathImageFin);
            }
            if (key === "imageList") {
                listImages = tmpArr[key];
                buildCarousel();
            }
        }
    }
}

function showImage(x) {
    document.getElementById('myImage').src = x;
}

function sendUpdateBook() {
    var add = {};
    add['id'] = idd;
    for (let tmp of nameObjectOfLocaleString) {
        var asd = {};
        for (let tmpValue of nameVarOfLocaleStringWithId) {
            asd[tmpValue] = $('#' + tmp + tmpValue).val();
        }
        add[tmp] = asd;
    }
    add['coverImage'] = nameImageCover;
    add['imageList'] = listImages;
    alert(listImages);
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

function buildCarousel() {
    var countForActive = 0;
    var tmpHtmlForCarousel = '';
    var tmpHtmlForCarouselIndicators = '';
    for (var i = 0; i < listImages.length; i++) {
        if (listImages[i].nameImage !== '') {
            countForActive++;
            if (countForActive === 1) {
                tmpHtmlForCarouselIndicators +=
                    `<li id="qw${i}" data-target='#carouselExampleCaptions' data-slide-to=${i} class='active'>` + `</li>`;
                tmpHtmlForCarousel +=
                    `<div id="qw${i}" class='carousel-item active'>` +
                    `<img src=${pathImageDefault}${idd}/${listImages[i].nameImage} class='d-block w-100' alt='...'>` +
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
                    `<img src=${pathImageDefault}${idd}/${listImages[i].nameImage} class='d-block w-100' alt="...">` +
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
    nameImageCover = listImages[x].nameImage;
    showImage(pathImageFinWithoutImage + nameImageCover);
}

function deleteTmpImage(x) {
    var delTmp = idd + '/' + listImages[x].nameImage;
    var tmpForShowImage = listImages[x].nameImage;
    listImages.splice(x, 1);

    buildCarousel();
    fetch('/admin/deleteImageByEditPage', {
        method: 'POST',
        body: delTmp
    }).then(r => {
        if (tmpForShowImage === nameImageCover) {
            showImage('');
        }
    });
}

$("#exampleFormControlFile1").change(function () {
    uploadImageNew();
});

function uploadImageNew() {
    const formData = new FormData();
    const fileField = document.getElementById("exampleFormControlFile1");
    listImages.push(JSON.parse('{"id":"' + '' + '", "nameImage":"' + fileField.files[0].name + '"}'));
    formData.append('file', fileField.files[0]);
    formData.append('idPaperForSaveImages', idd);
    fetch('/admin/uploadByEditPage', {
        method: 'POST',
        body: formData
    })
        .then(status)
        .then(json)
        .then(function (resp) {
            buildCarousel();
        });
}