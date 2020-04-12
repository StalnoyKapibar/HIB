let tmp;
let langNames;
let nameObjectOfLocaleString;
let nameObjectOfLocaleStringWithId;
let tmpEditBookId;
let nameVarOfLocaleStringWithId;
let welcomeText = [];

// $(document).ready(getVarBookDTO(), getAllLocales(), pageBook(0));
$(document).ready(function () {
    getAllLocales().then();
});


function json(response) {
    return response.json()
}

async function getAllLocales() {
    await fetch("/api/welcome/1")
        .then(json)
        .then((data) => {
            welcomeText = Object.values(data.body);
        });
    await fetch("/lang")
        .then(json)
        .then(function (resp) {
            tmp = resp;
            nameVarOfLocaleStringWithId = tmp;
            nameVarOfLocaleStringWithId.unshift("id");
            langNames = nameVarOfLocaleStringWithId.filter(t => t !== "id");
            let html = `<input id="welcome-id" type="hidden" value="${welcomeText[0]}"/>`;
            for (let i = 0; i < langNames.length; i++) {
                let tmp_html = langNames[i];
                html += `<label for = ${tmp_html}>${tmp_html}</label>` +
                    `<textarea type='text' class='form-control' id='${tmp_html}' ` +
                    `aria-describedby='emailHelp'>${welcomeText[i + 1]}</textarea>`;
            }
            $('#form_id').html(html + `<button type='submit' onclick='funcStart()' class='btn btn-primary'>Submit</button>`);
        })
}

function funcStart() {
    var aniArgs = {};
    for (let tmp_html of tmp) {
        aniArgs[tmp_html] = $('#' + tmp_html).val();
    }
    aniArgs['id'] = welcomeText[0];
    var body02 = JSON.stringify({
        id: 1,
        body: aniArgs
    });
    updateWelcome(body02);
}

async function updateWelcome(x) {
    await fetch("/api/admin/welcome/edit", {
        method: 'POST',
        body: x,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
}

