function asd() {
    var a = document.getElementById("exampleFormControlSelect1").value;

    document.getElementById("start0").innerText = '';

    ru0(a);

}


async function ru0(x) {

    let response = await fetch("/welcome", {
        method: 'POST', // или 'PUT'
        body: x, // данные могут быть 'строкой' или {объектом}!
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }


    });


    var tmp = response.headers.get("qwe");


    $("#start0").append(tmp);


}