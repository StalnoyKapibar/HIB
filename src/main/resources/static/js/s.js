function startProgress() {

    $.ajax({

        url: '/admin/alcoves',
        datatype: 'json',
        type: "post",
        contentType: "application/json",
        success: function (data) {

            for (let item of data) {
                $("#border").append("<div class='form-check-inline'>" +
                    "<label class='font-weight-bold form-check-label' >" +
                    "<input type='checkbox' class='form-check-input' id = '" + item.id + "' value = '" + item.role + "'>" +

                    "                                " + item.role + "        </label>" +
                    "                                    </div>");

            }

        }
    });


}
