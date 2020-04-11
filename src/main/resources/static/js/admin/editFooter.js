var currentLang = 'en';
const API_ADMIN_URL = "/api/admin/html/footer";
let nameLocales;
let footerLinksList = $("#footer-edit-link-list");
let addLinkText = $("#add-footer-link-text-en");
let addLinkLink = $("#add-footer-link-link");
let deleteButton;
let editableLink;
let deleteClick = false;

async function renderBuildEditFooterList() {
    footerLinksList.empty();
    for (let link of footer.links) {
        footerLinksList.append(`<li class="list-group-item link" data-link-index="${footer.links.indexOf(link)}">
                        <div class="container-fluid">
                            <div class="row">
                                <span class="col text-muted">${link.text[currentLang]}</span>
                                <span class="col text-muted">${link.link}</span>
                                <div>
                                    <a class="edit-footer-link"><i class="fas fa-edit fa-lg"></i></a>
                                     
                                    <a data-link-index="${footer.links.indexOf(link)}" 
                                    class="delete-footer-link"><i class="fas fa-trash fa-lg"></i></a>
                                </div>
                            </div>
                        </div>
                    </li>`)
    }
}

$(document).on('click', ".link", async function () {
    if (deleteClick) {
        deleteClick = false;
        return;
    }
    let detachedElement = $(".edit-tab").detach();
    detachedElement.addClass("deleted deleted-green");
    detachedElement.delay(400)
        .queue(function (next) {
            $(this).css('display', 'none');
            next();
        });
    if (editableLink === footer.links[$(this).attr("data-link-index")]) {
        $(this).find(".save-link").replaceWith(function () {
            return `<a data-link-index="${footer.links.indexOf($(this).attr("data-link-index"))}" 
                                    class="delete-footer-link"><i class="fas fa-trash fa-lg"></i></a>`;
        });
        editableLink = null;
        return;
    }
    deleteButton = $(this).find(".delete-footer-link");
    deleteButton.replaceWith(function () {
        return `<a type="button" class="save-link" data-link-index="${$(this).attr("data-link-index")}"><i class="fas fa-check fa-lg"></i></a>`;
    });

    editableLink = footer.links[$(this).attr("data-link-index")];
    delete editableLink.text.id;
    let fields = '';
    for (let localeText of Object.keys(editableLink.text)) {
        fields += `<div class="form-group col">
                    <label>${localeText.toUpperCase()}:</label>
                    <input class="form-control" data-lang="${localeText}" type="text" placeholder="${localeText.toUpperCase()}" value="${editableLink.text[localeText]}"/>
                       </div>`
    }
    $(this).after(`<li class="list-group-item edit-tab" data-link-index="${footer.links.indexOf(editableLink)}">
                            <form class="form">
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="form-group col">
                                            <label>Link:</label>
                                            <input class="form-control" type="text" placeholder="Link" value="${editableLink.link}"/>  
                                        </div>
                                <div class="form-group">
                                    ${fields}
                                        </div>
                                    </div>
                                </div>
                            </form>

                    </li>`)
});

$(document).on('click', ".save-link", async function () {
    let form;
    form = $(this).parent().parent().parent().parent().parent().find(".form");
    form.find('input').each(function (index) {
        if (index === 0) {
            editableLink.link = $(this).val();
        } else {
            editableLink.text[$(this).attr("data-lang")] = $(this).val();
        }
    });
    footer.links[editableLink.id - 1] = editableLink;
    let detachedElement = $(".edit-tab").detach();
    detachedElement.addClass("deleted deleted-green");
    detachedElement.delay(400)
        .queue(function (next) {
            $(this).css('display', 'none');
            next();
        });
    $(this).replaceWith(function () {
        return `<a data-link-index="${footer.links.indexOf($(this).attr("data-link-index"))}" 
                                    class="delete-footer-link"><i class="fas fa-trash fa-lg"></i></a>`;
    });
    await buildFooter();
});

$(document).on('click', ".delete-footer-link", async function () {
    deleteClick = true;
    console.log("delete");
    let tableElement = $(this).parent().parent().parent().parent();
    tableElement.removeClass("link");
    tableElement.addClass("deleted deleted-red");
    tableElement.delay(400)
        .queue(function (next) {
            $(this).css('display', 'none');
            next();
        });
    footer.links.splice($(this).attr('data-link-index'), 1);
    setTimeout(function () {
        renderBuildEditFooterList();
        buildFooter();
    }, 400);
});

$(document).on('click', "#save-footer-btn", async () => {
    await fetch(API_ADMIN_URL, {
        method: 'PUT',
        body: JSON.stringify(footer),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    }).then(json)
        .then((resp) => {
            footer = resp;
            localStorage.setItem(FOOTER_STORAGE_NAME, JSON.stringify(footer));
        })
        .then(buildFooter)
        .then(renderBuildEditFooterList);
});

$(document).on('click', "#add-footer-link-btn", async () => {
    if (!nameLocales) await getLocales();
    let link, linkText = {};
    for (let locale in Object.keys(nameLocales)) {
        linkText[nameLocales[locale].toString()] = nameLocales[locale].toString() === 'en' ? addLinkText.val() : '';
    }
    delete linkText.id;
    link = {
        text: linkText,
        link: addLinkLink.val()
    };
    footer.links.push(link);
    buildFooter().then(renderBuildEditFooterList);
});

async function getLocales() {
    await fetch("/lang")
        .then(json)
        .then(function (resp) {
            nameLocales = resp;
        });
}