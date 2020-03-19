$(document).ready(function() {
    getGenres();
});

async function getGenres() {
    await fetch("/genres")
        .then(status)
        .then(json)
        .then(function(resp) {
            buildRows(resp);
        })
}

function buildRows(data) {
    var	rows = '';
    data.forEach(function(entry) {
        rows += '<tr>';
        rows += '<td>'+entry.id+'</td>';
        rows += '<td>'+entry.genreLocale.ru+'</td>';
        rows += '<td>'+entry.genreLocale.en+'</td>';
        rows += '<td>'+entry.genreLocale.fr+'</td>';
        rows += '<td>'+entry.genreLocale.it+'</td>';
        rows += '<td>'+entry.genreLocale.de+'</td>';
        rows += '<td>'+entry.genreLocale.cs+'</td>';
        rows += '<td data-id="'+entry.id+'">';
        rows += '<button data-toggle="modal" data-target="#edit-item" class="btn btn-primary edit-item">Edit</button> ';
        rows += '<button class="btn btn-danger" onclick="deleteGenre(parseInt($(this).parent(\'td\').data(\'id\'), 10))">Delete</button>';
        rows += '<button style="font-size:18px;color:green" onclick="moveUpGenre(parseInt($(this).parent(\'td\').data(\'id\'), 10))"><i class="fas fa-angle-up"></i></button>';
        rows += '<button style="font-size:18px;color:green" onclick="moveDownGenre(parseInt($(this).parent(\'td\').data(\'id\'), 10))"><i class="fas fa-angle-down"></i></button>';
        rows += '</td>';
        rows += '</tr>';
    });
    $("#genrestable").html(rows);
}

async function deleteGenre(id) {
    await fetch("/genre/" + id, {
        method: 'DELETE',
    });
    await getGenres();
}

async function moveUpGenre(id) {
    await fetch("/genre/" + id + "/" + (id-1), {
        method: 'PUT',
    });
    await getGenres();
}

async function moveDownGenre(id) {
    await fetch("/genre/" + id + "/" + (id+1), {
        method: 'PUT',
    });
    await getGenres();
}

async function addGenre() {
    await fetch("/genre", {
        method: 'POST',
        body: JSON.stringify(
            {
                'id': null,
                'genreLocale': {
                    'id': null,
                    'ru': $("#addGenreRu").val(),
                    'en': $("#addGenreEn").val(),
                    'fr': $("#addGenreFr").val(),
                    'it': $("#addGenreIt").val(),
                    'de': $("#addGenreDe").val(),
                    'cs': $("#addGenreCs").val()
                }
            }
        ),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    await getGenres();
    await window.location.replace("/admin");
}

function json(response) {
    return response.json()
}

function status(response) {
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}



















$("body").on("click", ".edit-item", function(){
    var id = $(this).parent("td").data('id');
    var userName = $(this).parent("td").prev("td").prev("td").prev("td").prev("td").prev("td").prev("td").text();
    var accNonExp = $(this).parent("td").prev("td").prev("td").prev("td").prev("td").prev("td").text() === 'true';
    var accNonLoc = $(this).parent("td").prev("td").prev("td").prev("td").prev("td").text() === 'true' ;
    var credNonExp = $(this).parent("td").prev("td").prev("td").prev("td").text() === 'true';
    var enabled = $(this).parent("td").prev("td").prev("td").text() === 'true';
    var roles = $(this).parent("td").prev("td").text();

    $('#text').html('Edit user ' + userName);
    $("#edit-item").find("input[name='editId']").val(id);
    $("#edit-item").find("input[name='editUserName']").val(userName);
    $("#edit-item").find("input[name='editAccNonExp']").prop('checked', accNonExp);
    $("#edit-item").find("input[name='editAccNonLoc']").prop('checked', accNonLoc);
    $("#edit-item").find("input[name='editCredNonExp']").prop('checked', credNonExp);
    $("#edit-item").find("input[name='editEnabled']").prop('checked', enabled);
    presetRoles(roles);
});

$(function() {
    $('#edit-btn').click(function(event) {
        event.preventDefault();
        var action = "/user/" + parseInt($("#editId").val(), 10);

        let roles = [];
        $("#editSelect option:selected").each(function() {
            roles.push({
                id: parseInt($(this).attr("value"), 10),
                type: $(this).html()
            });
        });

        let user = {
            'id': parseInt($("#editId").val(), 10),
            'userName': $("#editUserName").val(),
            'password': $("#editPassword").val(),
            'accountNonExpired': $('#editAccNonExp').is(":checked"),
            'accountNonLocked': $("#editAccNonLoc").is(":checked"),
            'credentialsNonExpired': $("#editCredNonExp").is(":checked"),
            'enabled': $("#editEnabled").is(":checked"),
            'roles': roles
        };

        $.ajax({
            type: 'PUT',
            url: action,
            dataType: 'json',
            contentType: 'application/json;',
            data: JSON.stringify(user),
            success: function() {
                getUsers();
                $(".modal").modal('hide');
            }
        });
    });
});

$("body").on("click", ".add-btn", function(event) {
    event.preventDefault();

    let roles = [];
    $("#addSelect option:selected").each(function() {
        roles.push({
            id: parseInt($(this).attr("value"), 10),
            type: $(this).html()
        });
    });

    let user = {
        'id': 0,
        'userName': $("#addUserName").val(),
        'password': $("#addPassword").val(),
        'accountNonExpired': true,
        'accountNonLocked': true,
        'credentialsNonExpired': true,
        'enabled': true,
        'roles': roles
    };

    $.ajax({
        type: 'POST',
        url: "/user",
        dataType: 'json',
        contentType: 'application/json;',
        data: JSON.stringify(user),
        statusCode: {
            201: function() {
                getUsers();
                window.location.replace("/admin");
            },
            406: function() {
                alert("что-то пошло не так :)");
            }
        }
    });
});