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
                    "                                    <tr>" +


                    "                                        <td id='asdd'>" + user.id + "</td>" +
                    "                                        <td text='" + roles0 + "'>" + roles0 + "</td>" +


                    "                                        <td text='" + user.username + "'>" + user.username + "</td>" +
                    "                                        <td text='" + user.password + "'>" + user.password + "</td>" +
                    "\n" +
                    "                                        <td>\n" +
                    "\n" +
                    "                                            <button type='button'  onclick='getmodal("+user.id+")'  class=\"btn btn-primary\" data-toggle=\"modal\"" +
                    "                                                    data-target='#asdddd'>" +
                    "                                                Edit" +
                    "                                            </button>\n" +
                    "\n" +

                    "\n" +
                    "                                        </td>" +


                    "\n" +
                    "\n" +
                    "                                        <td>\n" +
                    "                                            <a class=\"btn btn-primary btn-danger\"\n" +
                    "                                               th:href=\"@{'/admin/del?username=' + ${msg.username} + '&id=' + ${msg.id} + '&password=' + ${msg.password}}\"\n" +
                    "                                               role=\"button\">Удалить</a>\n" +
                    "\n" +
                    "                                        </td>\n" +
                    "\n" +
                    "\n" +
                    "                                    </tr>\n" +
                    "\n" +
                    "\n" +
                    "                                </block>\n" +
                    "                                </tbody>");

            }


        }

    });

}