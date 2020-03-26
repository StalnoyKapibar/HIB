var currentLang = '';
var bottom = '';

$(document).ready(function () {
    setCurrentLangFromSessionAttrLANG();
    getLanguage();
    setLocaleFields();
    buildPageByCurrentLang();
    openModalLoginWindowOnFailure();
});

function buildPageByCurrentLang() {
    setTimeout(function () {
        $.ajax({
            url: "/user/get20BookDTO/" + currentLang,
            method: 'GET',
        }).then(function (data) {
            $('#cardcolumns').empty();
            $.each(data, function (index) {
                let div = $('<div class="card my-1"/>');
                div.append('<img class="card-img-top" src="images/book' + data[index].id + '/' + data[index].coverImage + '" alt="Card image cap">');
                let divBody = $('<div class="card-body" ></div>');
                divBody.append('<h4 class="card-title" style="overflow: auto; height:100px">' + data[index].nameAuthorDTOLocale + '</h4>');
                divBody.append('<p class="card-text">' + data[index].nameBookDTOLocale + '</p>');
                divBody.append('<br>');
                divBody.append('<a id="bookbotom"class="btn btn-primary my-2" data-toggle="modal" data-target="#myModal" style="position:absolute;bottom:0; color:white; " data-book-index="' + index + '">' + bottom + '</a>');
                div.append(divBody);
                div.appendTo('#cardcolumns');
            });
            $("#myModal").on('show.bs.modal', function (e) {
                let index = $(e.relatedTarget).data('book-index');
                $('#modalHeader').empty();
                $('#modalBody').empty();
                $('#modalHeader').append(data[index].nameAuthorDTOLocale);
                $('#modalBody').append('<p>' + data[index].nameBookDTOLocale + '</p>');
                $('#modalBody').append('<img class="card-img-top" src="images/book' + data[index].id + '/' + data[index].coverImage + '" alt="Card image cap">')
                $('#buttonOnBook').attr("action", '/page/' + data[index].id);
            });
        });
    }, 10);
}

