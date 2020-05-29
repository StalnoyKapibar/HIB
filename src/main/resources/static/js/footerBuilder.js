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
    setTextHeader()
});
function setTextHeader() {
    document.getElementById("greekText").innerHTML = "ΠΑΛΑΙΑ & Σπάνια βιβλία";
    document.getElementById("engText").innerHTML = "OLD & RARE BOOKS";
}

async function setFooter(footer) {
    this.footer = footer;
    localStorage.setItem(FOOTER_STORAGE_NAME, JSON.stringify(footer));
}

async function checkFooterForUpdates() {
    this.footer = JSON.parse(localStorage.getItem(FOOTER_STORAGE_NAME));
    await fetch(API_URL + "/update-date")
        .then(json)
        .then(async (date) => {
            if (date > this.footer.updateDate) {
                await getFooter();
            }
            await buildFooter();
            await renderBuildEditFooterList();

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
        console.log(link);
        let id = link.text['en'].toLowerCase().replace(/\s/ig, "-")
            .replace("'", "")
            .replace(/-+/, "-");

        footerMenu.append(`<a href="${link.link}" class="footer_link p-2" id="${id}-footer">${link.text[currentLang]}</a>`)
    }
}