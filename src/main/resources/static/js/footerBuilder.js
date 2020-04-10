const FOOTER_STORAGE_NAME = 'footer';
const API_URL = '/api/html/footer';
var footer = {};
let footerMenu = $("#footer-menu");

$(document).ready(async function () {
    if (localStorage.getItem(FOOTER_STORAGE_NAME)) {
        await checkFooterForUpdates();
    } else {
        await getFooter().then(buildFooter).then(renderBuildEditFooterList);
    }
});

async function setFooter(footer) {
    this.footer = footer;
    localStorage.setItem(FOOTER_STORAGE_NAME, JSON.stringify(footer));
}

async function checkFooterForUpdates() {
    this.footer = JSON.parse(localStorage.getItem(FOOTER_STORAGE_NAME));
    await fetch(API_URL + "/update-date")
        .then(json)
        .then((date) => {
            if (date > this.footer.updateDate) {
                getFooter();
            }
            buildFooter().then(renderBuildEditFooterList);
        });
}

async function getFooter() {
    await fetch(API_URL)
        .then(json)
        .then((footerResponse) => {
            setFooter(footerResponse);
        })
}

async function buildFooter() {
    footerMenu.empty();
    for (let link of this.footer.links) {
        footerMenu.append(`<a href="${link.link}" class="footer_link p-2" id="link_main_footer">${link.text[currentLang]}</a>`)
    }
}