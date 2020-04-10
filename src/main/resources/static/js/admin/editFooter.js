var currentLang = 'en';
const API_ADMIN_URL = "/api/admin/html/footer";
let footerLinksList = $("#footer-edit-link-list");

async function renderBuildEditFooterList() {
    footerLinksList.empty();
    for (let link of footer.links) {
        footerLinksList.append(`<li class="list-group-item">
                        <div class="container-fluid">
                            <div class="row">
                                <span class="col text-muted">${link.text[currentLang]}</span>
                                <span class="col text-muted">${link.link}</span>
                                <div>
                                    <button class="btn btn-info edit-footer-link"><i class="fas fa-edit"></i></button>
                                    <button type="button" data-link-index="${footer.links.indexOf(link)}" 
                                    class="btn btn-danger delete-footer-link"><i class="fas fa-minus"></i></button>
                                </div>
                            </div>
                        </div>
                    </li>`)
    }
}

$(document).on('click', ".delete-footer-link", async function () {
    let id = $(this).attr('data-link-index');
    footer.links.splice(id, 1);
    await renderBuildEditFooterList();
    await buildFooter();
});

$(document).on('click', "#save-footer-btn", async () => {
    footer.links.reverse();
    await fetch(API_ADMIN_URL, {
        method: 'PUT',
        body: JSON.stringify(footer),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    }).then(footer.links.reverse()).then(buildFooter).then(renderBuildEditFooterList);
});