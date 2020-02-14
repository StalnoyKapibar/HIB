function getuser() {

    $.ajax({

        url: '/admin/getuser',
        datatype: 'json',
        type: "post",
        contentType: "application/json",
        success: function (data) {


            for (var user of data) {

                allroles = user.roles;
                var roles0 = '';
                for (let tmp of user.roles) {

                    roles0 += " " + tmp.role;
                }


                $("#extra").append("<tbody>" +
                    "                                <block>" +
                    "                                    <tr id='" + user.id + "'>" +


                    "                                        <td id='" + user.id + "'>" + user.id + "</td>" +
                    "                                        <td id='r" + user.id + "'>" + roles0 + "</td>" +


                    "                                        <td id='u" + user.id + "'>" + user.username + "</td>" +
                    "                                        <td id='p" + user.id + "'>" + user.password + "</td>" +

                    "                                        <td>" +
                    "                                            <button type='button'  onclick='getmodal(" + user.id + ")'  class='btn btn-primary' data-toggle='modal'" +
                    "                                                    data-target='#asdddd'>" +
                    "                                                Edit" +
                    "                                            </button>" +

                    "                                        </td>" +

                    "                                        <td>" +

                    " <button type='button'  onclick='deluser(" + user.id + ")'  class='btn btn-primary btn-danger'> " +
                    "                                    Удалить" +
                    "                                            </button>" +
                    "                                        </td>" +

                    "                                    </tr>" +

                    "                                </block>" +
                    "                                </tbody>");

            }


        }

    });

}