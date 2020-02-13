function userss() {

    $.ajax({

        url: '/admin/userss',
        datatype: 'json',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            var roles0 = '';
            for (let tmp of data.roles) {

                roles0 += " " + tmp.role;
            }


            $("#tbody1").append("<tr>" +
                "                                    <td>" + data.id + "</td>" +
                "                                    <td>" + roles0 + "</td>" +
                "                                    <td>" + data.username + "</td>" +
                "                                    <td>" + data.password + "</td>" +
                "                                </tr>")


        }


    });

}