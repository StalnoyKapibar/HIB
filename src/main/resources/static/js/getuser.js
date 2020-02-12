function getuser() {

    $.ajax({

        url: '/admin/getuser',
        datatype: 'json',
        type: "post",
        contentType: "application/json",
        success: function (data) {


            for (let user of data) {
                var roles0 = '';
                for (let tmp of user.roles) {

                    roles0 += " " + tmp.role;
                }


                $("#extra").append("<tbody>" +
                    "                                <block>" +
                    "                                    <tr>" +


                    "                                        <td  value='"+user.id+"' >" + user.id + "</td >" +
                    "                                        <td text='" + roles0 + "'>" + roles0 + "</td>" +


                    "                                        <td text='" + user.username + "'>" + user.username + "</td>" +
                    "                                        <td text='" + user.password + "'>" + user.password + "</td>" +
                    "\n" +
                    "                                        <td>\n" +
                    "\n" +
                    "                                            <button type='button'  onclick='getrolesbyid()'  class=\"btn btn-primary\" data-toggle=\"modal\"\n" +
                    "                                                    data-target=" + "#" + "a" + user.id  + ">" +
                    "                                                Edit" +
                    "                                            </button>\n" +
                    "\n" +
                    "                                            <div class=\"modal fade\"  id=" + "a" +  user.id  + " tabindex=\"-1\" role=\"dialog\"\n" +
                    "                                                 aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n" +
                    "                                                <div class=\"modal-dialog\" role=\"document\">\n" +
                    "                                                    <div class=\"modal-content\">\n" +
                    "                                                        <div class=\"modal-header\">\n" +
                    "                                                            <h5 class=\"modal-title\" id=\"exampleModalLabel\">Edit\n" +
                    "  " + user.username + ""+

                    "                                                            </h5>\n" +
                    "                                                            <button type=\"button\" class=\"close\" data-dismiss=\"modal\"\n" +
                    "                                                                    aria-label=\"Close\">\n" +
                    "                                                                <span aria-hidden=\"true\">&times;</span>\n" +
                    "                                                            </button>\n" +
                    "                                                        </div>\n" +
                    "                                                        <div class=\"modal-body\">\n" +
                    "\n" +
                    "\n" +
                    "                                                            <form role=\"form\" class=\"custom-centered\"\n" +
                    "                                                                  th:action=\"@{/admin/edit}\" method='POST'>\n" +
                    "\n" +
                    "                                                                <div class=\"form-group\">\n" +
                    "                                                                    <label for=\"exampleInputId\">Id</label>\n" +
                    "                                                                    <input type=\"text\" class=\"form-control\" name='id'\n" +
                    "                                                                           value='" + user.id + "' id=\"exampleInputId\"\n" +
                    "                                                                           aria-describedby=\"emailHelp\" placeholder=\"Id\"\n" +
                    "                                                                           readonly>\n" +
                    "\n" +
                    "                                                                </div>\n" +
                    "\n" +
                    "                                                                <div class=\"form-group\">\n" +
                    "                                                                    <label for=\"exampleInputEmail11\">Username</label>\n" +
                    "                                                                    <input type=\"text\" class=\"form-control\"\n" +
                    "                                                                           name='username' value='" + user.username + "'" +
                    "                                                                           id=\"exampleInputEmail11\"\n" +
                    "                                                                           aria-describedby=\"emailHelp\"\n" +
                    "                                                                           placeholder=\"Enter Username\">\n" +
                    "\n" +
                    "                                                                </div>\n" +
                    "                                                                <div class=\"form-group\">\n" +
                    "                                                                    <label for=\"exampleInputPassword11\">Password</label>\n" +
                    "                                                                    <input type=\"password\" class=\"form-control\"\n" +
                    "                                                                           name='password' value='" + user.password + "'" +
                    "                                                                           id=\"exampleInputPassword11\"\n" +
                    "                                                                           placeholder=\"Password\">\n" +
                    "                                                                </div>\n" +
                    "\n" +
                    "\n" +
                    "                                                                <div class=\"form-group\">\n" +
                    "                                                                    <label for=\"exampleInput11\">Role</label>\n" +
                    "                                                                    <div class=\"border\" id='rolesafterboredr'>\n" +





                    "                                                                    </div>\n" +
                    "                                                                </div>\n" +
                    "\n" +
                    "\n" +
                    "                                                                <div class=\"modal-footer\">\n" +
                    "                                                                    <button type=\"button\" class=\"btn btn-secondary\"\n" +
                    "                                                                            data-dismiss=\"modal\">Close\n" +
                    "                                                                    </button>\n" +
                    "                                                                    <button type=\"submit\" class=\"btn btn-primary\"\n" +
                    "                                                                            role=\"button\">Save changes\n" +
                    "                                                                    </button>\n" +
                    "                                                                </div>\n" +
                    "                                                            </form>\n" +
                    "\n" +
                    "\n" +
                    "                                                        </div>\n" +
                    "\n" +
                    "                                                    </div>\n" +
                    "                                                </div>\n" +
                    "                                            </div>\n" +
                    "\n" +
                    "\n" +
                    "                                        </td>\n" +
                    "\n" +
                    "                                        <!-- Button trigger modal -->\n" +
                    "\n" +
                    "\n" +
                    "                                        <!-- Modal -->\n" +
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