var currentLang = '';

$(document).ready(function () {
    if (currentLang == '') {
        currentLang = $('#dd_menu_link').data('currentLang');
    }
    getLanguage();
    setLocaleFields();
    setPageFields();
});


function setPageFields() {
    fetch("/page/id/3")
        .then(status)
        .then(json)
        .then(function (jsn) {
            var jsn1 =  JSON.stringify(jsn);
            let v= [];

            const myMap = new Map();
            for (let [key, value] of Object.entries(jsn)) {
                    for (let [key1, value1] of Object.entries(value)){
                        if (key1 === currentLang) {
                            myMap.set(key, value1);
                          //  console.log(myMap);
                        }
                    }
            }


        /*    function traverse(o) {
                var i;
                for (var k in o) {
                    i = o[k];
                    if (typeof i === 'string') {
                        if (k===currentLang)
                            v.push(i)
                          //  console.log(v);

                    } else if (typeof i === 'object') {
                        traverse(i);
                    }
                }
            }
            traverse(jsn);*/


            /* $('#book-name').text(v[0]);
                $('#book-author').text(v[1]);
                $('#book-name1').text(v[0]);*/


            $('#book-name').text(myMap.get("name"));
            $('#book-author').text(myMap.get("author"));
            $('#book-name1').text(myMap.get("name"));



        })

        }




function setLocaleFields() {
    fetch("/properties/" + currentLang)
        .then(status)
        .then(json)
        .then(function (localeFields) {
            $('#link_main_footer').text(localeFields['main1']);
            $('#link_instruction').text(localeFields['instruction']);
            $('#link_authors').text(localeFields['authors']);
            $('#link_order').text(localeFields['order']);
            $('#link_contacts').text(localeFields['contacts']);
            $('#links').text(localeFields['links']);
            $('#made_by').text(localeFields['madeby']);
            $('#link_main_header').text(localeFields['main']);
            $('#link_books_header').text(localeFields['books']);
            $('#menu-toggle').text(localeFields['category']);
        })
}

$('#dd_menu').on('click', 'a', function (eventOnInnerTag) {
    eventOnInnerTag.preventDefault();
    const selectedLang = $(eventOnInnerTag.target).attr('id');
    fetch("/lang/" + selectedLang)
        .then(status)
        .then(text)
        .then(function (data) {
            currentLang = selectedLang;
            window.location.replace('page?LANG=' + currentLang);
            //TODO some logic to processing data and reload page with chosen lang
            getLanguage();
            getLocaleFields();
        });
    location.reload();
});

$("#menu-toggle").click(function (e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
});

function getLanguage() {
    function getFullNameOfLanguage(language) {
        switch (language) {
            case 'ru' :
                return 'Русский';
            case 'en' :
                return 'English';
            case 'de' :
                return 'Deutsch';
            case 'it' :
                return 'Italiano';
            case 'fr' :
                return 'Français';
            case 'cs' :
                return 'Český';
        }
        return "undef";
    }

    fetch("/lang")
        .then(status)
        .then(json)
        .then(function (listOfLanguage) {
            var currentLangFull = '';
            var html = '';

            for (language in listOfLanguage) {
                if (currentLang == (listOfLanguage[language])) {
                    continue;
                }
                currentLangFull = getFullNameOfLanguage(listOfLanguage[language]);
                html += `<a class="dropdown-item lang" id="${listOfLanguage[language]}">
                            <img src="../static/icons/${listOfLanguage[language]}.png" 
                                alt="" height="16" width="16" class="lang-image"> - ${currentLangFull}
                         </a>`;
            }
            $('#dd_menu').html(html);
            $('#dd_menu_link').text(currentLang);
            $('#dd_menu_link').empty();
            $('#dd_menu_link').append(`<img src="../static/icons/${currentLang}.png"
                                alt="" height="16" width="16" class="lang-image">`);
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


