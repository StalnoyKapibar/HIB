var currentLang = 'en';
const API_ADMIN_URL = "/api/admin/html/footer";
let nameLocales;
let footerLinksList = $("#footer-edit-link-list");
let addLinkText = $("#add-footer-link-text-en");
let addLinkLink = $("#add-footer-link-link");
let alertFooterContainer = $(".alert-container");
let deleteButton;
let editableLink;
let blockOpenEditBlock = false;
let saveBtn = $("#save-footer-btn");

async function checkForAllLocalesExist(link) {
    for (let field of Object.keys(link.text)) {
        if (link.text[field] === '') {
            return false;
        }
    }
    return true;
}

async function renderBuildEditFooterList() {
    footerLinksList.empty();
    for (let link of footer.links) {
        let warning = await checkForAllLocalesExist(link) ? `` : `<i title="Link text is not available in all languages" 
                                                            class="fas fa-exclamation-circle fa-lg tooltip-warning"></i>`;
        let move = ``;
        if (footer.links.indexOf(link) !== footer.links.length - 1 && footer.links.indexOf(link) !== 0) {
            move += `<a type="button" class="move-link-down" data-link-index="${footer.links.indexOf(link)}">
                                                                <i class="fas fa-chevron-down fa-lg"></i></a>`;
            move += `<a type="button" class="move-link-up" data-link-index="${footer.links.indexOf(link)}" ><i class="fas fa-chevron-up fa-lg"></i></a>`;
        }
        footerLinksList.append(`<li class="list-group-item link" data-link-index="${footer.links.indexOf(link)}">
                        <div class="container-fluid">
                            <div class="row">
                                ${warning}
                                <span class="col text-muted">${link.text[currentLang]}</span>
                                <span class="col text-muted">${link.link}</span>
                                <div>
                                ${move}
                                <a class="edit-footer-link"><i class="fas fa-edit fa-lg"></i></a> 
                                <a data-link-index="${footer.links.indexOf(link)}" 
                                    class="delete-footer-link"><i class="fas fa-trash fa-lg"></i></a>
                                </div>
                            </div>
                        </div>
                    </li>`)
    }
    $('.tooltip-warning').tooltip();
}


$(document).on('click', ".link", async function () {
    if (blockOpenEditBlock) {
        blockOpenEditBlock = false;
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
                        <input class="form-control" data-lang="${localeText}" 
                        type="text" placeholder="${localeText.toUpperCase()}" 
                        value="${editableLink.text[localeText]}"/>
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
    //editableLink = footer.links[$(this).attr('data-link-index')];
    form = $(this).parent().parent().parent().parent().parent().find(".form");
    form.find('input').each(function (index) {
        console.log($(this));
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
    editableLink = null;
    buildFooter().then(renderBuildEditFooterList).then(showWarningAlert);
});

$(document).on('click', ".delete-footer-link", async function () {
    blockOpenEditBlock = true;
    await showWarningAlert();
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
        .then(showSaveAlert)
        .then(buildFooter)
        .then(renderBuildEditFooterList);
});

$(document).on('click', "#add-footer-link-btn", async () => {
    await showWarningAlert();
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

$(document).on('click', '.move-link-down', async function () {
    let linkIndex = parseInt($(this).attr("data-link-index"), 10);
    changeLinkListPositions(linkIndex, 1).then(buildFooter).then(renderBuildEditFooterList);
});

$(document).on('click', '.move-link-up', async function () {
    let linkIndex = parseInt($(this).attr("data-link-index"), 10);
    changeLinkListPositions(linkIndex, -1).then(buildFooter).then(renderBuildEditFooterList);
});

async function changeLinkListPositions(linkIndex, howMuch) {
    // console.log(`Меняю позицию ${linkIndex} на ${howMuch}`);
    // console.log("Входной массив: ");
    //   console.log(footer.links);
    [footer.links[linkIndex].id, footer.links[linkIndex + howMuch].id] =
        [footer.links[linkIndex + howMuch].id, footer.links[linkIndex].id];
    footer.links.sort((a, b) => a.id > b.id ? 1 : -1);
    // console.log("Выходной массив");
    // console.log(footer.links);
    await showWarningAlert();
}

async function getLocales() {
    await fetch("/lang")
        .then(json)
        .then(function (resp) {
            nameLocales = resp;
        });
}

async function showSaveAlert() {
    saveBtn.hide();
    alertFooterContainer.empty();
    alertFooterContainer.append(`<div class="alert alert-success alert-dismissible fade show" role="alert">
                                    <strong>Changes saved</strong> Changes will take effect in a few seconds.
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>`);

}

async function showWarningAlert() {
    saveBtn.show();
    alertFooterContainer.empty();
    alertFooterContainer.append(`<div class="alert alert-warning fade show" role="alert">
                                    <strong>Warning</strong> You have unsaved changes
                                </div>`)
}


