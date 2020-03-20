function addNewBook() {
    let book = {
        name: $("#newBookName").val(),
        author: $("#newBookAuthor").val()
    };
    let bookDTO = JSON.stringify(book);
    $.ajax({})
}

function getText(numb) {
    if (numb===1){
        return"From";
    } else {
        return "To";
    }
}

function getLangMenu(t) {
    let id = '#ddmLang'+getText(t);
    $.ajax({
        type: "GET",
        url: "/admin/getAllLang",
        success: function (data) {
            $(id).empty();
            for (let i in data) {
                let lang = data[i];
                $(id).append(
                    "<a class=\"dropdown-item\" type='button' onclick='changeLang("+i+","+t+")' href=\"#\">" +
                    "<img src=\"/static/icons/" + lang + ".png\"> - " + lang + "</a>"
                );
            }
        }
    })
}

function changeLang(i, t) {
    let id = '#dropdownMenuLink'+getText(t);
    $.ajax({
        type: "GET",
        url: "/admin/getAllLang",
        success: function (data) {
            $(id).empty();
            $(id).append(`<img src="../static/icons/${data[i]}.png" alt="" height="16" width="16" class="lang-image">`);
        }
    })
}
