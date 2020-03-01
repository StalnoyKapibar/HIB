let tmp = [];
var nameVarOfLocaleString;
var nameObjectOfLocaleString;
var nameObjectOfLocaleStringWithId;
var varBookDTO;


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
    updateUser(body02);
}

async function updateUser(x) {
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
    await fetch("/admin/pageable/" + x)
        .then(status)
        .then(json)
        .then(function (resp_tmp) {
            var htmlTempPager = '';
            for (var i = 0; i < resp_tmp.totalPages; i++) {
                var z = 1 + i;
                htmlTempPager += "<li class='page-item'><a class='page-link' onclick='pageBook(" + i + ")' href='#'>" + z + "</a></li>";
            }

            $('#pagination00').html(htmlTempPager);


            var htmlAddPage = varBookDTO;
            nameObjectOfLocaleStringWithId = Object.values(htmlAddPage);
            nameObjectOfLocaleString = nameObjectOfLocaleStringWithId.filter(t => t !== "id");
            var htmlTable = '';
            for (let dd of nameObjectOfLocaleStringWithId) {
                htmlTable += "<th scope='col'>" + dd + "</th>";
            }

            htmlTable += "<th scope='col'>Edit</th>" +
                "<th scope='col'>Delete</th>";
            $('#table0').html(htmlTable);

            var html = '';
            for (let tmp_html of resp_tmp.content) {
                html +=
                    "                                    <tr id='" + tmp_html.id + "'>" +
                    "                                        <td id='" + tmp_html.id + "'>" + tmp_html.id + "</td>" +
                    "                                        <td id='n" + tmp_html.id + "'>" + tmp_html.name.ru + "</td>" +
                    "                                        <td id='a" + tmp_html.id + "'>" + tmp_html.author.ru + "</td>" +
                    "                                        <td>" +
                    "                                            <button type='button'  onclick='editBook(" + tmp_html.id + ")'  class='btn btn-primary' data-toggle='modal'" +
                    "                                                    data-target='#asdddd'>" +
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
        })
}

function addPage() {
    nameVarOfLocaleString = tmp;

    var html = '';
    for (let tmpNameObject of nameObjectOfLocaleString) {

        html += "<h5>" + tmpNameObject + "</h5>"
        for (let tmpNameVar of nameVarOfLocaleString) {
            html += "<div class='form-group'>" +
                "                                <label for='" + tmpNameObject + "" + tmpNameVar + "'>" + tmpNameObject + "  " + tmpNameVar + "</label>" +
                "                                <input type='text' class='form-control' id='" + tmpNameObject + "" + tmpNameVar + "' " +
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
            asd[tmpValue] = $('#' + tmp + tmpValue).val();
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
}