let tmp = [];

$(document).ready(editWelcome());

function editWelcome() {
    getAllLocales();
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
        body : aniArgs
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