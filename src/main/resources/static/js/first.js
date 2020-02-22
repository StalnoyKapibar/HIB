function changeLocale() {
    var a = document.getElementById("exampleFormControlSelect1").value;

    document.getElementById("start0").innerText = '';

    defaultLocale(a);

}


async function defaultLocale(x) {

    let response = await fetch("/welcome", {
        method: 'POST',
        body: x,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }


    });


    var tmp = response.headers.get("qwe");


    $("#start0").append(tmp);


}