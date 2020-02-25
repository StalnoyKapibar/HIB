let tmp = [];

$(document).ready(editWelcome());

function editWelcome() {
    getAllLocales();


}


async function getAllLocales() {
    await fetch("/admin/allLocales")
        .then(status)
        .then(json)
        .then(function (resp) {
            tmp = resp;
            alert(tmp);
            var html = '';
            for (tmp_html of tmp) {
                html += "<label for = '" + tmp_html + "'>" + tmp_html + "</label>" +
                    "<input type='text' class='form-control' id='" + tmp_html + "' " +
                    "aria-describedby='emailHelp'>";
            }

            $('#form_id').html(html + "<button type=\"submit\" class=\"btn btn-primary\">Submit\n" +
                "                                    </button>");


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