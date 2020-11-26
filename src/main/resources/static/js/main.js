const JSON_HEADER = {'Content-Type': 'application/json;charset=utf-8'};
$(document).ready(function () {
    setTextHeader()
    $('.ul-books').hide();
});
const POST = async (url, data, headers) => {
    return await fetch(url, {
        method: 'POST',
        body: data,
        headers: headers
    })
};

const GET = async (url) => {
    return await fetch(url);
};

function json(response) {
    return response.json()
}

function convertOriginalLanguageRows(originalRow, transliteRow) {
    if (originalRow === transliteRow) return originalRow;
    return `${originalRow} (${transliteRow})`
}

function setTextHeader() {
    document.getElementById("greekText").innerHTML = "ΠΑΛΑΙΑ & Σπaνια βιβλiα";
    document.getElementById("engText").innerHTML = "OLD & RARE BOOKS";
}

function getCookieByName(name) {
    let value = "; " + document.cookie;
    let parts = value.split("; " + name + "=");
    if (parts.length === 2) return parts.pop().split(";").shift();
}