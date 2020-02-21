var currentLang = '';

$(document).ready(getLanguage);

$('#dd_menu').on('click', 'a', async function (eventOnInnerTag) {
    eventOnInnerTag.preventDefault();
    const selectedLang = eventOnInnerTag.target.text
    await fetch("/lang/" + selectedLang)
        .then(status)
        .then(json)
        .then(function (data) {
            currentLang = selectedLang;
            //some logic to processing data and reload page with chosen lang
            getLanguage();
        });
});

async function getLanguage() {
    await fetch("/lang")
        .then(status)
        .then(json)
        .then(function (listOfLanguage) {
            var html = '';
            if (currentLang == '') {
                currentLang = $('#dd_menu_link').data('currentLang');
            }
            $('#dd_menu_link').text(currentLang);

            for (language in listOfLanguage) {
                if (currentLang == (listOfLanguage[language])) {
                    continue;
                }
                html += `<a class="dropdown-item" href="/home?ln=${listOfLanguage[language]}">${listOfLanguage[language]}</a>`;
            }
            $('#dd_menu').html(html);
            $('#dd_menu_link').text(currentLang);
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
