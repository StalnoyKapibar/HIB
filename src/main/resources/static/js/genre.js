$(document).ready(function() {
    getGenres();
});

function getGenres() {
    $.ajax({
        url: "/genres",
        dataType: "json",
        method: "GET",
        success: function(data) {
            buildRows(data);
        }
    });
}














function presetRoles(roles) {
    $.ajax({
        url: "/roles",
        dataType: "json",
        method: "GET",
        success: function(data) {
            data.forEach( function(role) {
                var target = "#editSelect option:contains('" + role.type + "')";
                if(roles.includes(role.type)) {
                    $(target).prop('selected', true);
                } else {
                    $(target).prop('selected', false);
                }
            });
        }
    });
}

function buildRows(data) {
    var	rows = '';
    var roles;
    data.forEach(function(entry) {
        roles = '[ ';
        entry.roles.forEach( function(role) {
            roles = roles + role.type + ' ';
        });
        roles = roles + ']';
        rows = rows + '<tr>';
        rows = rows + '<td>'+entry.id+'</td>';
        rows = rows + '<td>'+entry.userName+'</td>';
        rows = rows + '<td>'+entry.accountNonExpired+'</td>';
        rows = rows + '<td>'+entry.accountNonLocked+'</td>';
        rows = rows + '<td>'+entry.credentialsNonExpired+'</td>';
        rows = rows + '<td>'+entry.enabled+'</td>';
        rows = rows + '<td>'+roles+'</td>';
        rows = rows + '<td data-id="'+entry.id+'">';
        rows = rows + '<button data-toggle="modal" data-target="#edit-item" class="btn btn-primary edit-item">Edit</button> ';
        rows = rows + '<button class="btn btn-danger remove-item">Delete</button>';
        rows = rows + '</td>';
        rows = rows + '</tr>';
    });
    $("tbody").html(rows);
}

$("body").on("click", ".remove-item", function() {
    var action = "/user/" + parseInt($(this).parent("td").data('id'), 10);
    $.ajax({
        type: 'DELETE',
        url: action,
        dataType: 'json',
        contentType: 'application/json;',
        success: function() {
            getUsers();
        }
    });
});

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