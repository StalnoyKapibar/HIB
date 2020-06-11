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

            if (partsOfBook === "description") {
                for (let tmpNameVar of nameVarOfLocaleString) {

                    html += `<div class="shadow p-4 mb-4 bg-white">
                <div class='form-group mx-5'>
                <div class="row">
                <div class="col-0" for=${tmpNameObject}${tmpNameVar}>${tmpNameObject} ${tmpNameVar}</div>
                <div class="col-2 mr-1">
                <input type="radio" class="transl-from-this-lang-loc" name="rb${tmpNameObject}" id="rb${tmpNameObject}${tmpNameVar}" value="${tmpNameVar}" autocomplete="off"> Translate from this language
                </div>
                <div class="col">
                <textarea type='text' class='form-control' id='inp${tmpNameObject}${tmpNameVar}'
                placeholder='${tmpNameObject} ${tmpNameVar}'></textarea>
                </div>
                <div class="col">
                <input class="into-this-lang-loc" type="checkbox" checked name="cb${tmpNameObject}" value="${tmpNameVar}" autocomplete="off"> Into this language
                </div></div></div></div>`;
                    if (tmpNameVar === "gr") {
                        html += `<button type="button" onclick="translateText('${tmpNameObject}')" class="btn btn-primary mx-3 translate-loc">Translate</button></div>`
                    }
                }

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
                <div class="col-5 pl-5 ml-5  mr-1 ">
                <input type='text' class='form-control ' id='in${tmpNameObject}'>
                </div> </div>
                </div>
                    <button id="yourDivId" type="button" onclick="transliterationText('${tmpNameObject}')" class="btn btn-primary mx-3 big-transliterate-loc">Transliterate</button>
                </div>`;


                for (let tmpNameVar of nameVarOfLocaleString) {

                    html += `<div class="shadow p-4 mb-4 bg-white">
                <div class='form-group mx-5'>
                <div class="row">
                <div class="col-0" for=${tmpNameObject}${tmpNameVar}>${tmpNameObject} ${tmpNameVar}</div>
                <div class="col-2 mr-1">
                <input type="radio" class="transl-from-this-lang-loc" name="rb${tmpNameObject}" id="rb${tmpNameObject}${tmpNameVar}" value="${tmpNameVar}" autocomplete="off"> Translate from this language
                </div>
                <div class="col">
                <input type='text' class='form-control' id='inp${tmpNameObject}${tmpNameVar}'
                placeholder='${tmpNameObject} ${tmpNameVar}'>
                </div>
                <div class="col">
                <input type="checkbox" class="into-this-lang-loc" checked name="cb${tmpNameObject}" value="${tmpNameVar}" autocomplete="off"> Into this language
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

function buildPage() {
    let disabled = tmpArr.show ? '' : 'checked';
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
            '                  <input type="text" class="form-control" categoryid="' + tmpArr.category.id + '" id="categoryInput" readonly value="' + tmpArr.category.categoryName + '">\n' +
            '                  <div class="input-group-append">\n' +
            '                    <button class="btn btn-outline-secondary" id="selectCategory" data-toggle="modal" data-target=".bd-example-modal-lg" type="button">Change category</button>\n' +
            '                  </div>\n' +
            '                </div>\n' +
            '               </div>\n' +
            '            </div>\n' +
            '            </div>\n' +
            '            </div></div>';
    }

    html1 += `<div class="card card-header">
              <div class="row">
              <div class="col-1">
              <h4 class="book-old-loc">Book sold</h4></div>
              <div class="col"> <input id="disabled" class="big-checkbox"  type="checkbox" ${disabled}></div></div></div>`;

    $('#bookEditPage').html(`<div class="card card-header">
              <div class="row">
              <div class="col-1">
              <h4 >Book sold</h4></div>
              <div class="col"> <input id="disabled" class="big-checkbox"  type="checkbox" ${disabled}></div></div></div>
              <div class="tab-content" id="myTabContent">
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
            <h5 class="year-of-edition-loc"> Year Of Edition </h5>
            <input type="text" id="yearOfEdition"><br><br>
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
            <select id="originalLanguage">
            </select><br><br>
            </div>
            <div id = "allImage">
            <div class="shadow p-4 mb-4 bg-white">
            <div id="divLoadAvatar">
            <h4 class="avatar-loc">Avatar</h4>
            <div class="form-control-file" style="width: 18rem"">
              <div id="carouselExampleCaptions"  class="carousel card-body slide w-50" data-ride="carousel">
              <ol class="carousel-indicators" style="width: 18rem" id='test0'> </ol>
              <div class="carousel-inner" style="width: 18rem" id='test1'></div></div>
              <Label class="load-cover-loc">Load cover</Label><br>
              <input type="button" value="Choose file" style="width: 6rem" accept=".jpg"   onclick="setImgInCarousel()">
              </div>
            </div>
            </div><br><br>
            <div class="shadow p-4 mb-4 bg-white">
            <h4 class="another-image-loc">Another Image</h4>
            <Label class="load-another-image-loc">Load another image</Label>
            <input type="file" class="form-control-file" id="loadAnotherImage" accept=".jpg" onchange="loadImage('loadAnotherImage','imageList')">
            <div class='car' id='imageList' style='width: 18rem;'>
            
            </div></div></div></div>

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
    var myHTML = '';
    var myHTMLButtonDelete = '';
    for (let key in tmpArr.listImage) {
        myHTML += `<img id="carouselImage${key}" src ='${pathImageDefault + idd + '/'}${tmpArr.listImage[key].nameImage}'  class="pic" alt='...'>`;
    }
    for (let key in tmpArr.listImage) {
        if (tmpArr.listImage[key].nameImage !== "1.jpg") {
            myHTMLButtonDelete += '<button type="button" onclick="deleteTmpImage(' + key + ')"  class="btn btn-danger m-3 delete-loc">Delete</button>'
        }
    }
    $('#bookEditPageForImg').html(`
<div >
              <div class="row">
              <div class="col-4 ">
              <div class="card " style="width: 20rem;">
              <div class='car card-body' style='width: 20rem;'>
              <div class="pic-ctn">
    ${myHTML}
    
  </div>
  ${myHTMLButtonDelete}
            </div></div>
              </div>
              <div class="col"> 
              <div class="card " style="width: 20rem;">
              <h4 class="card-header">Cover Image</h4>
              <div class='car card-body' style='width: 20rem;'>
              <img id='myImage' src =''  class='card-img-top' alt='...'> 
              </div></div>
              <p><button type="button" onclick="deleteCoverImage(nameImageCover.toString())"  class="btn btn-danger m-3 delete-loc">Delete</button><p>
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
        <h4 class="modal-title selected-category-dots-loc" id="categoryLabel">Selected category: </h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">×</span>
        </button>
      </div>
      <div id="categoryModalBody" class="modal-body">
      </div>
       <div class="modal-footer">
        <button type="button" class="btn btn-secondary confirm-loc" data-dismiss="modal">Confirm</button>
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

function setImgInCarousel() {
    $('#exampleFormControlFile1').trigger('click')
}

function showImage(x) {
    document.getElementById('myImage').src = x;
}

function sendUpdateBook() {
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
    book['coverImage'] = nameImageCover;
    book['show'] = (!$("#disabled").is(':checked'));
    book['yearOfEdition'] = $('#yearOfEdition').val();
    book['pages'] = $('#pages').val();
    book['price'] = $('#price').val();
    book['originalLanguageName'] = $('#originalLanguage').val();
    book['category'] = category;
    let allImages = $("#allImage").find("img");
    let imageList = [];

    for (let img of allImages) {
        imageList.push(img.id);
    }
    let imageListTmpPattern = [];
    for (let index = 0; index < imageList.length; index++) {
        imageListTmpPattern[index] = JSON.parse(JSON.stringify(tmpArr.listImage[0]));
    }
    for (let index = 0; index < imageListTmpPattern.length; index++) {
        imageListTmpPattern[index].id = 0;
        imageListTmpPattern[index].nameImage = imageList[index];
    }

    if (imageListTmpPattern.length == 1 && imageListTmpPattern[0].nameImage == "") {
        book["listImage"] = tmpArr.listImage;
    } else {
        let indexListImage = tmpArr.listImage.length;
        for (let index = tmpArr.listImage.length + 1; index <= imageListTmpPattern.length + indexListImage; index++) {
            tmpArr.listImage.push(imageListTmpPattern[index - indexListImage - 1]);
        }
        book["listImage"] = tmpArr.listImage;
    }

    var body02 = JSON.stringify(book);
    sendUpdateBookReq(body02);
    confirm("Edit this book?");
    window.location.href = document.referrer;
}

async function sendUpdateBookReq(x) {
    await fetch("/admin/edit", {
        method: 'POST',
        body: x,
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Accept': 'application/json'
        }
    });
}


function buildCarousel() {
    var countForActive = 0;
    var tmpHtmlForCarousel = '';
    var tmpHtmlForCarouselIndicators = '';
    for (var i = 1; i < listImages.length; i++) {
        if (listImages[i].nameImage !== '') {
            countForActive++;
            if (countForActive === 1) {
                tmpHtmlForCarouselIndicators +=
                    `<li id="qw${i}" data-target='#carouselExampleCaptions' data-slide-to=${i} class='active'>` + `</li>`;
                tmpHtmlForCarousel +=
                    `<div id="qw${i}" class='carousel-item active'>` +
                    `<img src=${pathImageDefault}${idd}/${listImages[i].nameImage} class='d-block w-100' alt='...'>` +
                    `<div class='carousel-caption d-none d-md-block'>` +
                    `<button type="button" onclick="setImageCover(${i})" class="btn btn-success change-image-cover-loc">Change image cover</button>` +
                    `<p><button type="button" onclick="deleteTmpImage(${i})" class="btn btn-danger m-3 delete-loc">Delete</button><p>` +
                    `</div>` +
                    `</div>`;
            } else {
                tmpHtmlForCarouselIndicators +=
                    `<li id="qw${i}" data-target='#carouselExampleCaptions' data-slide-to=${i}></li>`;
                tmpHtmlForCarousel +=
                    ` <div id="qw${i}" class="carousel-item">` +
                    `<img src=${pathImageDefault}${idd}/${listImages[i].nameImage} class='d-block w-100' alt="...">` +
                    `<div class='carousel-caption d-none d-md-block'>` +
                    `<button type="button" onclick="setImageCover(${i})" class="btn btn-success change-image-cover-loc">Change image cover</button>` +
                    `<p><button type="button" onclick="deleteTmpImage(${i})" class="btn btn-danger m-3 delete-loc">Delete</button><p>` +
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

function setImageCarousel(x) {
    showImage(pathImageFinWithoutImage + nameImageCover);
}

function deleteTmpImage(x) {
    var delTmp = idd + '/' + tmpArr.listImage[x].nameImage;
    var tmpForShowImage = tmpArr.listImage[x].nameImage;
    deleteCarouselImageFromDB(x);
    listImages.splice(x, 1);
    $('#carouselImage' + x).attr("src", "");
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

function deleteCarouselImageFromDB(x) {
    var delTmp = tmpArr.listImage[x].id;
    delete tmpArr.listImage[x];
    fetch('/admin/deleteImageFromDB', {
        method: 'POST',
        body: delTmp
    }).then(r => {

    });
}


function deleteCoverImage(x) {
    var delTmp = idd + '/' + x;

    $('#myImage').attr("src", "");
    $('#carouselImage' + 0).attr("src", "");
    buildCarousel();
    fetch('/admin/deleteImageByEditPage', {
        method: 'POST',
        body: delTmp
    }).then(r => {

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

function deleteImage(id) {
    if (id === 'divAvatar') {
        divAvatar.empty();
    } else {
        document.getElementById(id).remove();
    }
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
            buildPage();
        });
}


