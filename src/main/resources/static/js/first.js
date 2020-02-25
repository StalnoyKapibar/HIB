function changeLocale() {
    var a = document.getElementById("exampleFormControlSelect1").value;
    document.getElementById("start0").innerText = '';
    defaultLocale(a);
}

$(document).ready(defaultLocale('ru'));

async function defaultLocale(x) {
    let response = await fetch("/welcome", {
        method: 'POST',
        body: x,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    // $("#start0").append(tmp);
}