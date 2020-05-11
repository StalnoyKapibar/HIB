let idd;
let nameObjectOfLocaleString;
let nameVarOfLocaleString;
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

function addPartsOfBook(partsOfBook) {
    let html = ``;
    for (let tmpNameObject of nameObjectOfLocaleString) {

        if (tmpNameObject === partsOfBook) {

            html += `<div class="shadow p-4 mb-4 bg-white">`;

            if (partsOfBook !== "description") {

                html +=
                    `<div class="shadow p-4 mb-4 bg-white">
                <div class='form-group mx-5 my-3'>
                <div class="row">
                <div class="col-0" for=${tmpNameObject}>${tmpNameObject} of other lang </div>
                <div class="col-5 pl-5 ml-5  "><input type='text'  class='form-control '  id='inpt${tmpNameObject}'
                placeholder='${tmpNameObject} of other  lang '>
                </div> </div>
                <div class="row my-2">
                <div class="col-0" for=${tmpNameObject}>${tmpNameObject} transliterate&nbsp;&nbsp; </div>
                <div class="col-5 pl-5 ml-5  mr-1 "><input type='text' class='form-control ' id='in${tmpNameObject}'
                placeholder='${tmpNameObject} transliterate '>
                </div> </div>
                </div>
                <button type="button" onclick="transliterationText('${tmpNameObject}')" class="btn btn-primary mx-3">Transliterate</button>
                </div>`;

            }

            for (let tmpNameVar of nameVarOfLocaleString) {

                html += `<div class="shadow p-4 mb-4 bg-white">
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
                if (tmpNameVar === "gr") {
                    html += `<button type="button" onclick="translateText('${tmpNameObject}')" class="btn btn-primary mx-3">Translate</button></div>`
                }

            }

            return html;
        }

    }

}

function buildPage() {
    let disabled = tmpArr.show ? '' : 'checked';
    var html1 = '';
    html1 += `<div class="card card-header">
              <div class="row">
              <div class="col-1">
              <h4 >Book sold</h4></div>
              <div class="col"> <input id="disabled" class="big-checkbox"  type="checkbox" ${disabled}></div></div></div>`;

    $('#bookEditPage').html(`<div class="tab-content" id="myTabContent">
            
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
            </div></div></div></div></div>`);
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

  /*  html1 += `<div class="card card-footer">
              <div class="row  ">
              <div class="col p-4 mb-4">
              <h5> Year Of Edition </h5>
              <input type="text" class="form-control" id='yearOfEdition' value=${tmpArr.yearOfEdition}></div>
              <div class=" col p-4 mb-4 ">
              <h5> Pages </h5>
              <input type="number" class="form-control" id="pages" value=${tmpArr.pages} ></div>
              <div class=" col p-4 mb-4  ">
              <h5> Price </h5>
              <input type="number" class="form-control" id="price" value=${tmpArr.price} ></div>
              <div class="col p-4 mb-4 ">
              <div class="row-cols-1">
              <div class="col">
              <h5> Original Language </h5></div>
              <div class="col-4">
              <select id="originalLanguage" class="form-control" value=${originalLang} ></select></div></div>
              </div></div></div>`;*/

   /* $('#bookEditPage').html(html1);*/


    $('#bookEditPageForImg').html(`
              <div class="row">
              <div class="col-4 ">
              <div class="card" style="width: 21rem">
              <div id="carouselExampleCaptions"  class="carousel card-body slide w-50" data-ride="carousel">
              <ol class="carousel-indicators" style="width: 18rem" id='test0'> </ol>
              <div class="carousel-inner" style="width: 18rem" id='test1'></div></div>
              <div class="buttonCarousel card-footer ">
              <buttonCarouselEdit class="left" href="#carouselExampleCaptions"  data-slide="prev" > <</buttonCarouselEdit>     
              <buttonCarouselEdit class="right " href="#carouselExampleCaptions" data-slide="next" > ></buttonCarouselEdit>
              <buttonCarouselEdit class="center "   onclick="setImgInCarousel()" > Upload file</buttonCarouselEdit></div>
              </div>
              </div>
              <div class="col"> 
              <div class="card " style="width: 20rem;">
              <h4 class="card-header">Cover Image</h4>
              <div class='car card-body' style='width: 20rem;'>
              <img id='myImage' src =''  class='card-img-top' alt='...'> 
              </div></div>
              </div> </div>`);

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
                buildCarousel();
            }

        }
    }
}

function setImgInCarousel() {
    $('#exampleFormControlFile1').trigger('click')
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
    add['show'] = (!$("#disabled").is(':checked'));
    add['yearOfEdition'] = $('#yearOfEdition').val();
    add['pages'] = $('#pages').val();
    add['price'] = $('#price').val();
    add['originalLanguageName'] = $('#originalLanguage').val();

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
            nameVarOfLocaleString = nameVarOfLocaleStringWithId.filter(t => t !== "id");
            getBookDTOById(idd = localStorage.getItem('tmpEditBookId'));
        });
}

function getBookDTOById(id) {
    fetch("/api/book/" + id)
        .then(status)
        .then(json)
        .then(function (resp) {
            tmpArr = resp;
            console.log(tmpArr);
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

