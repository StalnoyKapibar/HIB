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
        data: user,


    });

    var roles0 = '';
    for (let tmp of roles) {

        roles0 += " " + tmp.role;
    }

    // document.getElementById(id).innerHTML = "                                        <td id='" + id + "'>" + id + "</td>" +
    //     "                                                            <td>" + roles0 + "</td>" +
    //
    //
    //     "                                                            <td>" + username + "</td>"+
    //     "                                                            <td>" + password + "</td>"+
    //
    //     "                                                            <td>" +
    //     "                                                                <button type='button'  onclick='getmodal(" + id + ")'  class='btn btn-primary' data-toggle='modal'" +
    //     "                                                                        data-target='#asdddd'>" +
    //     "                                                                    Edit " +
    //     "                                                                </button> " +
    //
    //     "                                                            </td> " +
    //
    //     "                                                            <td> " +
    //
    //     "                     <button type='button'  onclick='deluser(" + id + ")'  class='btn btn-primary btn-danger'>"+
    //     "                                                        Удалить "+
    //     "                                                                </button>" +
    //     "                                                            </td>";
    document.getElementById('r'+id).innerText = roles0;
    document.getElementById('u'+id).innerText = username;
    document.getElementById('p'+id).innerText = password;

    asdqwe();




    // var elem=document.getElementById(id);
    // elem.parentNode.removeChild(elem);


}