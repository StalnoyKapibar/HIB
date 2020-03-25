let genresDto;

$(document).ready(function() {
    getGenresDto();
});

async function getGenresDto() {
    await fetch("/genresDto")
        .then(status)
        .then(json)
        .then(function(resp) {
            genresDto = resp;
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
                'number': $("#addGenreNum").val(),
                'genreLocale': {
                    'id': null,
                    'ru': $("#addGenreRu").val(),
                    'en': $("#addGenreEn").val(),
                    'fr': $("#addGenreFr").val(),
                    'it': $("#addGenreIt").val(),
                    'de': $("#addGenreDe").val(),
                    'cs': $("#addGenreCs").val(),
                    'gr': $("#addGenreGr").val()
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