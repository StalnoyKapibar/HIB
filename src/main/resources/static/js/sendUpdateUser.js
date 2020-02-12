function sendUpdateUser() {
    var id = $('#exampleInputId').val();
    var username = $('#exampleInputEmail11').val();
    var password = $('#exampleInputPassword11').val();
    var roles = [];
    var selectRoles1 = $('.modal-body input:checked');

    for (var i = 0; i < selectRoles1.length; i++) {
        roles.push(JSON.parse('{"id":"' + selectRoles1[i].id + '", "role":"' + selectRoles1[i].value + '"}'));
    }

    var user = JSON.stringify({
        id: id,
        username: username,
        password: password,
        roles: roles
    });


    //      alert(user);

    $.ajax({

        url: '/admin/update',
        datatype: 'json',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        data: user

    });







    asdqwe();
}