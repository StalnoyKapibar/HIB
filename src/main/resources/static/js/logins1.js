function logins1() {

    var login = $('#inputLogin').val();
    var password = $('#inputPassword').val();


    $.ajax({

        url: '/admin/add',
        datatype: 'json',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        data: user

    });


}