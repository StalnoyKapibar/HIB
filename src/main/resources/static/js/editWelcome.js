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
    await fetch("lang")
        .then(status)
        .then(json)
        .then(function (resp) {
            tmp = resp;
            nameVarOfLocaleStringWithId = tmp;
            nameVarOfLocaleStringWithId.unshift("id");
            nameVarOfLocaleString = nameVarOfLocaleStringWithId.filter(t => t !== "id");
            var html = '';
            for (let tmp_html of tmp) {
                html += "<label for = '" + tmp_html + "'>" + tmp_html + "</label>" +
                    "<input type='text' class='form-control' id='" + tmp_html + "' " +
                    "aria-describedby='emailHelp'>";
            }

            $('#form_id').html(html + "<button type='submit' onclick='funcStart()' class='btn btn-primary'>Submit" +
                "                                    </button>");
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
            arrAllBooksByNumberPage = resp_tmp.content;
            var htmlTempPager = '';
            for (var i = 0; i < resp_tmp.totalPages; i++) {
                var z = 1 + i;
                htmlTempPager += "<li class='page-item'><a class='page-link' onclick='pageBook(" + i + ")' href='#'>" + z + "</a></li>";
            }
            $('#pagination00').html(htmlTempPager);
            buildChangeLang();
            var htmlAddPage = varBookDTO;
            nameObjectOfLocaleStringWithId = Object.values(htmlAddPage);
            nameObjectOfLocaleString = nameObjectOfLocaleStringWithId.filter(t => t !== "id");
            var htmlTable = '';
            for (let dd of nameObjectOfLocaleStringWithId) {
                htmlTable += "<th scope='col'>" + dd + " " + idChangeLang + "</th>";
            }
            htmlTable += "<th scope='col'>Edit</th>" +
                "<th scope='col'>Delete</th>";
            $('#table0').html(htmlTable);
            var html = '';
            for (let tmp_html of resp_tmp.content) {
                html += "                                    <tr id='" + tmp_html.id + "'>" +
                    "                                        <td id='" + tmp_html.id + "'>" + tmp_html.id + "</td>";
                for (key in tmp_html) {
                    if (key !== "id") {
                        var ad = tmp_html[key][idChangeLang];
                        html += "<td id='n" + tmp_html.id + "'>" + ad + "</td>";
                    }
                }
                html += "                                        <td>" +
                    "                                            <button type='button' onclick='buildEditBook(" + tmp_html.id + ")'  data-toggle='modal'" +
                    "                                                                        data-target='#asdddd'  class='btn btn-primary'> " +
                    "                                                Edit" +
                    "                                            </button>" +
                    "                                        </td>" +
                    "                                        <td>" +
                    " <button type='button'  onclick='delBook(" + tmp_html.id + ")'  class='btn btn-primary btn-danger'> " +
                    "                                    Удалить" +
                    "                                            </button>" +
                    "                                        </td>" +
                    "                                    </tr>";
            }
            $('#extra').html(html);
        });
    buildChangeLang();
}

function addPage() {
    var html = '';
    for (let tmpNameObject of nameObjectOfLocaleString) {
        html += "<h5>" + tmpNameObject + "</h5>"
        for (let tmpNameVar of nameVarOfLocaleString) {
            html += "<div class='form-group'>" +
                "                                <label for='" + tmpNameObject + "" + tmpNameVar + "'>" + tmpNameObject + "  " + tmpNameVar + "</label>" +
                "                                <input type='text' class='form-control' id='" + "a" + tmpNameObject + "" + tmpNameVar + "' " +
                "                                       placeholder='" + tmpNameObject + " " + tmpNameVar + "'>" +
                "                            </div>";
        }
    }
    $('#newBookForm').html(html + "<button type='submit' onclick='addBook()' class='btn btn-primary custom-centered'>Add new" +
        "                                Book" +
        "                            </button>");
}

function addBook() {
    var add = {};
    for (let tmp of nameObjectOfLocaleString) {
        var asd = {};
        for (let tmpValue of nameVarOfLocaleString) {
            asd[tmpValue] = $('#' + "a" + tmp + tmpValue).val();
        }
        add[tmp] = asd;
    }
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
        html1 += "<h5>" + tmpNameObject + "</h5>";
        for (let tmpNameVar of nameVarOfLocaleStringWithId) {
            if (tmpNameVar === "id") {
                html1 += "<div class='form-group'>" +
                    "                                <label for='" + tmpNameObject + "" + tmpNameVar + "'>" + tmpNameObject + "  " + tmpNameVar + "</label>" +
                    "                                <input type='text' class='form-control' id='" + tmpNameObject + "" + tmpNameVar + "' " +
                    "                                       placeholder='" + tmpNameObject + " " + tmpNameVar + "' readonly>" +
                    "                            </div>";
            } else {
                html1 += "<div class='form-group'>" +
                    "                                <label for='" + tmpNameObject + "" + tmpNameVar + "'>" + tmpNameObject + "  " + tmpNameVar + "</label>" +
                    "                                <input type='text' class='form-control' id='" + tmpNameObject + "" + tmpNameVar + "' " +
                    "                                       placeholder='" + tmpNameObject + " " + tmpNameVar + "'>" +
                    "                            </div>";
            }
        }
    }
    html1 += "<button type='submit' onclick='sendUpdateBook()' data-dismiss='modal' class='btn btn-primary custom-centered'>Edit Book" +
        "                            </button>";
    $('#editBookForm').html(html1);
    for (var rt of arrAllBooksByNumberPage) {
        if (rt.id === tmpEditBookId) {
            var tmpArr = rt;
        }
    }
    for (key in tmpArr) {
        if (key !== "id") {
            for (key0 of nameVarOfLocaleStringWithId) {
                document.getElementById(key + key0).value = tmpArr[key][key0];
            }
        }
    }
}

function sendUpdateBook() {
    var add = {};
    add['id'] = tmpEditBookId;
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
    await pageBook(idPageable);
}

function buildChangeLang() {
    var htmllang = '';
    for (var i = 0; i < nameVarOfLocaleString.length; i++) {
        var gh = nameVarOfLocaleString[i];
        htmllang += "<button type='button' class='btn btn-secondary' onclick='chanLang(" + i + ")'>" + gh + "</button>";
    }
    $('#chlang1').html(htmllang);
}

function chanLang(x) {
    idChangeLang = nameVarOfLocaleString[x];
    pageBook(idPageable);
}