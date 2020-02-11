function myFunction() {
    var username = $('#username').val();
    var password = $('#password').val();
    var roles = [];
    var selectRoles = $('input:checked');

    for (var i = 0; i < selectRoles.length; i++) {
        roles.push(JSON.parse('{"id":"' + parseInt(selectRoles[i].id) + '", "role":"' + String(selectRoles[i].value) + '"}'));

    }


    var user = JSON.stringify({
        username: username,
        password: password,
        roles: roles

    });


    //      alert(user);

    $.ajax({

        url: '/admin/add',
        datatype: 'json',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        data: user

    });

}
