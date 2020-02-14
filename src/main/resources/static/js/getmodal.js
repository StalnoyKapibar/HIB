function getmodal(userId) {
var temp;

    $.ajax({

        url: '/admin/alcoves',
        datatype: 'json',
        type: "post",
        contentType: "application/json",
        success: function (data) {
            temp = data;
        }

    });







    $.ajax({

        url: '/admin/getrolebyid/' + userId,
        datatype: 'json',
        type: "get",
        contentType: "application/json",
        success: function (data) {
            document.getElementById('exampleInputId').value = data.id;
            document.getElementById('exampleInputEmail11').value = data.username;
            document.getElementById('exampleInputPassword11').value = data.password;




            for (let item1 of temp) {



                $("#border1").append("<div class='form-check-inline'>" +
                    "<label class='font-weight-bold form-check-label' >" +
                    "<input type='checkbox' class='form-check-input' id = '" + item1.id + "' value = '" + item1.role + "'>" +

                    "                                " + item1.role + "        </label>" +
                    "                                   </div>");

            }






        }
    });


}